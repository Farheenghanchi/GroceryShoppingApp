package com.farheen.grossary.fragments;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.farheen.grossary.R;
import com.farheen.grossary.Singleton;
import com.farheen.grossary.Utils;
import com.farheen.grossary.data.UsersContract;
import com.farheen.grossary.data.UsersDbHelper;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.animation.arcanimator.ArcAnimator;
import io.codetail.animation.arcanimator.Side;


/**
 * Created by Farheen on 05/03/2017.
 */

public class FragmentLoginSignup extends Fragment {

    private Context mContext;
    private View rootView;

    View mParent;
    ImageButton mBlue;
    FrameLayout mBluePair;
    RelativeLayout login;

    ImageButton mRed;

    float startBlueX;
    float startBlueY;

    int endBlueX;
    int endBlueY;

    float startRedX;
    float startRedY;

    int startBluePairBottom;

    final static AccelerateInterpolator ACCELERATE = new AccelerateInterpolator();
    final static AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
    final static DecelerateInterpolator DECELERATE = new DecelerateInterpolator();

    private EditText loginUsername, loginPassword,signupUsername,signupPassword1, signupPassword2;
    private Button loginButton, signupButton;

    UsersDbHelper mDbHelper;
    SQLiteDatabase db;

    public FragmentLoginSignup(){
    }

    @SuppressLint("ValidFragment")
    public FragmentLoginSignup(Context context){
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.cart).setVisible(false);
        super.onPrepareOptionsMenu(menu);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_login_signup, container, false);

        // set toolbar
//        CustomToolbar.setToolbar(getActivity(), toolbar, "Provider Code");

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Login");

        Singleton.getInstance().setCurrentFragmentName("Login");
        // initialize views
        initView();

        return rootView;
    }

    private void initView() {

        mDbHelper = new UsersDbHelper(mContext);
        db = mDbHelper.getWritableDatabase();


        mParent = rootView;
        mBlue = (ImageButton) rootView.findViewById(R.id.transition_blue);
        mBluePair = (FrameLayout) rootView.findViewById(R.id.transition_blue_pair);
        mRed = (ImageButton) rootView.findViewById(R.id.transition_red);
        mBlue.setOnClickListener(mClicker);
        mRed.setOnClickListener(mRedClicker);
        login = (RelativeLayout) rootView.findViewById(R.id.rl_login);

//        loginUsername, loginPassword,signupUsername,signupPassword1, signupPassword2;
//        private Button loginButton, signupButton;
        loginUsername = (EditText) rootView.findViewById(R.id.input_username);
        loginPassword = (EditText) rootView.findViewById(R.id.input_password);
        signupUsername = (EditText) rootView.findViewById(R.id.input_username_r);
        signupPassword1 = (EditText) rootView.findViewById(R.id.input_password_r);
        signupPassword2 = (EditText) rootView.findViewById(R.id.input_password_r1);

        loginButton = (Button) rootView.findViewById(R.id.btn_login);
        signupButton = (Button) rootView.findViewById(R.id.btn_signup);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = loginUsername.getText().toString();
                String password = loginPassword.getText().toString();
                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(mContext, "username or password can't be empty.", Toast.LENGTH_SHORT).show();
                }else if(checkCorrectUsernameAndPass(username,password)){
                    Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();

                    pushFragment(new FragmentCategories(mContext), "Category", false);
//                    pushFragment(new FragmentDetails(mContext), "Details", false);
//                    pushFragment(new FragmentProducts(mContext), "Products", false);
//                    pushFragment(new FragmentCategories(mContext), "Menu", false);
                }
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(signupUsername.getText().toString().isEmpty() || signupPassword1.getText().toString().isEmpty() || signupPassword2.getText().toString().isEmpty() ){
                    Toast.makeText(mContext, "Username or Passwork Can't be null", Toast.LENGTH_SHORT).show();
                }else if(!signupPassword1.getText().toString().equals(signupPassword2.getText().toString())){
                    Toast.makeText(mContext, "Password and conform Password should match.", Toast.LENGTH_SHORT).show();
                }else if(checkForRepeat(signupUsername.getText().toString())){
                    Toast.makeText(mContext, "Username already exist", Toast.LENGTH_SHORT).show();
                }else {
                    insetUser(signupUsername.getText().toString(),signupPassword1.getText().toString());
                    Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                    signupUsername.setText("");
                    signupPassword1.setText("");
                    signupPassword2.setText("");
                }
            }
        });
    }



    private void pushFragment(Fragment fragment, String tag, boolean addToBackStack) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//        ft.setCustomAnimations(R.anim.enter_bottom,R.anim.exit_top);
        ft.replace(R.id.content_main, fragment, tag);

        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commitAllowingStateLoss();
    }

    private boolean checkCorrectUsernameAndPass(String username, String password) {
        Boolean player1 = checkLoginDettails(username,password);
        if(player1){
            return true;
        }else {
                Toast.makeText(mContext, "username or password is incorrect.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean checkLoginDettails(String username, String password) {
        String selection = UsersContract.MaterialUserEntry.COLUMN_USERNAME + "=? AND "+UsersContract.MaterialUserEntry.COLUMN_PASSWORD + "=?";
        String[] selectionArgs = new String[] {username,password};
        Cursor c = db.query(UsersContract.MaterialUserEntry.TABLE_NAME,null,selection,selectionArgs,null,null,null);
        if(c.getCount() != 0){
//            Toast.makeText(SignUpActivity.this, "count is"+c.getCount(), Toast.LENGTH_SHORT).show();
            return true;
        }else {
//            Toast.makeText(SignUpActivity.this, "count is"+c.getCount(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void displayDatabaseInfo() {


        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        Cursor cursor = db.rawQuery("SELECT * FROM " + UsersContract.MaterialUserEntry.TABLE_NAME, null);
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).

            Log.i("mytag", "inside class:FragmentLoginSignup \t count :: "+cursor.getCount());
//            displayView.setText("Number of rows in pets database table: " + cursor.getCount());
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }

    }

    private boolean checkForRepeat(String uname) {
        String selection = UsersContract.MaterialUserEntry.COLUMN_USERNAME + "=?";
        String[] selectionArgs = new String[] {uname};

        Cursor c = db.query(UsersContract.MaterialUserEntry.TABLE_NAME,null,selection,selectionArgs,null,null,null);
        if(c.getCount() != 0){
//            Toast.makeText(SignUpActivity.this, "count is"+c.getCount(), Toast.LENGTH_SHORT).show();
            return true;
        }else {
//            Toast.makeText(SignUpActivity.this, "count is"+c.getCount(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void insetUser( String uname, String pass) {
//        GameDbHelper mDbHelper = new GameDbHelper(SignUpActivity.this);
//        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Create a ContentValues object where column names are the keys,
        ContentValues values = new ContentValues();
        values.put(UsersContract.MaterialUserEntry.COLUMN_USERNAME, uname);
        values.put(UsersContract.MaterialUserEntry.COLUMN_PASSWORD, pass);

        long newRowId = db.insert(UsersContract.MaterialUserEntry.TABLE_NAME,null, values);
        displayDatabaseInfo();
        /*Intent i = new Intent(mContext, LoginActivity.class);
        startActivity(i);
        finish();*/
    }

    View.OnClickListener mClicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startBlueX = Utils.centerX(mBlue);
            startBlueY = Utils.centerY(mBlue);

            endBlueX = mParent.getRight()/2;
            endBlueY = (int) (mParent.getHeight()/2);
            ArcAnimator arcAnimator = ArcAnimator.createArcAnimator(mBlue, endBlueX,
                    endBlueY, 90, Side.LEFT)
                    .setDuration(500);
            arcAnimator.addListener(new SimpleListener(){
                @Override
                public void onAnimationEnd(Animator animation) {
                    mBlue.setVisibility(View.INVISIBLE);
                    appearBluePair();
                }
            });
            arcAnimator.start();
        }
    };

    View.OnClickListener mRedClicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            disappearRed();
            downRed();
        }

    };

    void appearBluePair(){
        mBluePair.setVisibility(View.VISIBLE);

        float finalRadius = Math.max(mBluePair.getWidth(), mBluePair.getHeight()) * 1.5f;

        SupportAnimator animator = io.codetail.animation.ViewAnimationUtils.createCircularReveal(mBluePair, endBlueX, endBlueY, mBlue.getWidth() / 2f,
                500);
        animator.setDuration(500);
        animator.setInterpolator(ACCELERATE);
        animator.addListener(new SimpleListener(){
            @Override
            public void onAnimationEnd() {
                login.setVisibility(View.GONE);
//                raise();
//                appearRed();
                upRed();
            }
        });
        animator.start();
    }

    void raise(){
        startBluePairBottom = mBluePair.getBottom();
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(mBluePair, "bottom", mBluePair.getBottom(), mBluePair.getTop() + dpToPx(100));
        objectAnimator.addListener(new SimpleListener(){
            @Override
            public void onAnimationEnd(Animator animation) {
                appearRed();
            }
        });
        objectAnimator.setInterpolator(ACCELERATE_DECELERATE);
        objectAnimator.start();
    }

    void appearRed(){
        mRed.setVisibility(View.VISIBLE);

        int cx = mRed.getWidth() / 2;
        int cy = mRed.getHeight() / 2;

        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(mRed,cx, cy, 0, mRed.getWidth()/2);
        animator.addListener(new SimpleListener(){
            @Override
            public void onAnimationEnd() {
                mRed.setVisibility(View.VISIBLE);
                upRed();
            }
        });
        animator.setInterpolator(ACCELERATE);
        animator.start();
    }

    void upRed(){
        startRedX = ViewHelper.getX(mRed);
        startRedY = ViewHelper.getY(mRed);
        mRed.setVisibility(View.VISIBLE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mRed, "y", ViewHelper.getY(mRed),
                mBluePair.getTop()+20);
        objectAnimator.addListener(new SimpleListener(){
            @Override
            public void onAnimationEnd(Animator animation) {
//                disappearRed();
            }
        });
        objectAnimator.setDuration(650);
        objectAnimator.setInterpolator(ACCELERATE_DECELERATE);
        objectAnimator.start();
    }
    void downRed(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mRed, "y", ViewHelper.getY(mRed),
                mBluePair.getBottom()+ 20);
        objectAnimator.addListener(new SimpleListener(){
            @Override
            public void onAnimationEnd(Animator animation) {
                login.setVisibility(View.VISIBLE);
                disappearRed();
            }
        });
        objectAnimator.setDuration(650);
        objectAnimator.setInterpolator(ACCELERATE_DECELERATE);
        objectAnimator.start();
    }


    void disappearRed(){

        int cx = mRed.getWidth() / 2;
        int cy = mRed.getHeight() / 2;

        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(mRed,cx, cy, mRed.getWidth()/2, 0);
        animator.addListener(new SimpleListener(){
            @Override
            public void onAnimationEnd() {
                mRed.setVisibility(View.INVISIBLE);
//                mRed.setX(startRedX);
//                mRed.setY(startRedY);
//                ViewHelper.setX(mRed,startRedX);
//                ViewHelper.setY(mRed,startRedY);
                release();
            }
        });
        animator.setInterpolator(DECELERATE);
        animator.start();
    }

    void release(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(mBluePair, "bottom", mBluePair.getBottom(),startBluePairBottom);
        objectAnimator.addListener(new SimpleListener(){
            @Override
            public void onAnimationEnd(Animator animator) {

                disappearBluePair();
            }
        });
        objectAnimator.setInterpolator(ACCELERATE_DECELERATE);
        objectAnimator.start();
    }

    void disappearBluePair(){
        float finalRadius = Math.max(mBluePair.getWidth(), mBluePair.getHeight()) * 1.5f;

        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(mBluePair, endBlueX, endBlueY,
                finalRadius, mBlue.getWidth() / 2f);
        animator.setDuration(500);
        animator.addListener(new SimpleListener() {
            @Override
            public void onAnimationEnd() {
                mBluePair.setVisibility(View.GONE);
                login.setVisibility(View.VISIBLE);
                returnBlue();
            }
        });
        animator.setInterpolator(DECELERATE);
        animator.start();
    }

    void returnBlue(){

        mBlue.setVisibility(View.VISIBLE);
        ArcAnimator arcAnimator = ArcAnimator.createArcAnimator(mBlue, startBlueX,
                startBlueY, 90, Side.LEFT)
                .setDuration(500);
        arcAnimator.start();

    }

    public int dpToPx(int dp){
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    private static class SimpleListener implements SupportAnimator.AnimatorListener, ObjectAnimator.AnimatorListener{

        @Override
        public void onAnimationStart() {

        }

        @Override
        public void onAnimationEnd() {

        }

        @Override
        public void onAnimationCancel() {

        }

        @Override
        public void onAnimationRepeat() {

        }

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
