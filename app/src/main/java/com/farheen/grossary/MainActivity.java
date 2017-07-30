package com.farheen.grossary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.farheen.grossary.fragments.FragmentCategories;
import com.farheen.grossary.fragments.FragmentLoginSignup;
import com.farheen.grossary.fragments.FragmentMyCart;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toolbar.setTitle("Login");
        pushFragment(new FragmentLoginSignup(this), "Login", false);
//        toolbar.setTitle("Detail");
//        pushFragment(new FragmentDetails(this), "Detail", false);
        /*toolbar.setTitle("Cart");
        pushFragment(new FragmentMyCart(this), "Cart", false);*/

        /*toolbar.setTitle("Category");
        pushFragment(new FragmentCategories(this,toolbar), "Category", false);*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cart) {
            if(Singleton.getInstance().getCurrentFragmentName() == "Login"){
                Toast.makeText(MainActivity.this, "First Login", Toast.LENGTH_SHORT).show();
            }else {
                pushFragment(new FragmentMyCart(this),"Cart",true);
            }
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if(Singleton.getInstance().getCurrentFragmentName() == "Login" || Singleton.getInstance().getCurrentFragmentName() == "Category"){
            finish();
        }else if(Singleton.getInstance().getCurrentFragmentName() == "Cart"){
            pushFragment(new FragmentCategories(MainActivity.this), "Category", false);
        }
        else {
            super.onBackPressed();
        }

    }

    private void pushFragment(Fragment fragment, String tag, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//        ft.setCustomAnimations(R.anim.enter_bottom,R.anim.exit_top);
        ft.replace(R.id.content_main, fragment, tag);

        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commitAllowingStateLoss();
    }
    private void pushInnerFragment(Fragment fragment, String tag, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.content_main, fragment, tag);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commitAllowingStateLoss();
    }
}
