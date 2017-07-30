package com.farheen.grossary.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.farheen.grossary.R;
import com.farheen.grossary.Singleton;
import com.farheen.grossary.adapter.AdapterCart;
import com.farheen.grossary.model.ItemsPojo;

import java.util.ArrayList;

/**
 * Created by Parth Modi on 30/03/2017.
 */

public class FragmentMyCart extends Fragment {

    private Context mContext;
    private View rootView;
    private ArrayList<ItemsPojo> CartItemsList = new ArrayList<>();
//    private String category_selected = "2";
    //    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private ListView mListView;
    private AdapterCart adapter;
    private TextView total;
    private int totalBill = 0;
    private Button btn_pay;
    public FragmentMyCart(){

    }

    @SuppressLint("ValidFragment")
    public FragmentMyCart(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_cart, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Cart");
        Singleton.getInstance().setCurrentFragmentName("Cart");

        // initialize views
        initView();

        return rootView;
    }

    private void initView() {

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.loading_indicator);
        mListView = (ListView) rootView.findViewById(R.id.list);
        adapter = new AdapterCart(mContext, Singleton.getInstance().getCartProductsList());
        mListView.setAdapter(adapter);

        total = (TextView) rootView.findViewById(R.id.tv_cart_total);
        btn_pay = (Button) rootView.findViewById(R.id.btn_pay);


        updateBill();

        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                updateBill();
//                Toast.makeText(mContext, ""+Singleton.getInstance().getCartProductsList().size(), Toast.LENGTH_SHORT).show();

            }
        });


    }
    private void updateBill(){
        totalBill = 0;
//        Toast.makeText(mContext, ""+Singleton.getInstance().getCartProductsList().size(), Toast.LENGTH_SHORT).show();
        for (ItemsPojo i :
                Singleton.getInstance().getCartProductsList()) {
            totalBill += Integer.parseInt(i.getItem_price());
        }
        total.setText("Total : "+ "₹ "+totalBill);
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create custom dialog object
                final Dialog dialog = new Dialog(mContext,R.style.theme_sms_receive_dialog);
                // Include dialog.xml file
                dialog.setContentView(R.layout.custome_dialog);
                // Set dialog title
                dialog.setTitle("Choose Method");

                // set values for custom dialog components - text, image and button
                TextView tv_mp = (TextView) dialog.findViewById(R.id.tv_make_payment);
                TextView tv_cod = (TextView) dialog.findViewById(R.id.tv_cod);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

//                Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
                // if decline button is clicked, close the custom dialog
                tv_mp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pushInnerFragment(new FragmentPayment(mContext, totalBill), "Payment", true);
                        dialog.dismiss();
                    }
                });
                tv_cod.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Singleton.getInstance().setCartProductsList(new ArrayList<ItemsPojo>());
                        Toast.makeText(mContext, "Thank You", Toast.LENGTH_SHORT).show();
                        pushInnerFragment(new FragmentCategories(mContext), "Category", true);
                        dialog.dismiss();
                    }
                });

            }
        });
    }

    private void pushInnerFragment(Fragment fragment, String tag, boolean addToBackStack) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.content_main, fragment, tag);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commitAllowingStateLoss();
    }

}
