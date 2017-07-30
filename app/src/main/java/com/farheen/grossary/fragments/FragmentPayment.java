package com.farheen.grossary.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.farheen.grossary.R;
import com.farheen.grossary.Singleton;

/**
 * Created by Parth Modi on 13/04/2017.
 */

public class FragmentPayment extends Fragment {
    private Context mContext;
    private View rootView;
    private TextView bill_amount;
    private EditText input_card_num,input_card_exp,input_card_cvc,input_card_pin;
    private Button make_payment;
    private int totalBill = 0;

    public FragmentPayment() {

    }

    @SuppressLint("ValidFragment")
    public FragmentPayment(Context mContext,int totalBill) {
        this.mContext = mContext;
        this.totalBill = totalBill;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_payment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Make Payment");
        Singleton.getInstance().setCurrentFragmentName("Payment");

        // initialize views
        initView();

        return rootView;
    }

    private void initView() {
        bill_amount = (TextView) rootView.findViewById(R.id.tv_amount);
        bill_amount.setText("₹ "+totalBill);
        input_card_num = (EditText) rootView.findViewById(R.id.input_card_num);
        input_card_exp = (EditText) rootView.findViewById(R.id.input_card_exp);
        input_card_cvc = (EditText) rootView.findViewById(R.id.input_card_cvc);
        input_card_pin = (EditText) rootView.findViewById(R.id.input_card_pin);
        make_payment = (Button) rootView.findViewById(R.id.btn_submit);

        make_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String str_card_num = input_card_num.getText().toString();
                 String str_card_exp = input_card_exp.getText().toString();
                 String str_card_cvc = input_card_cvc.getText().toString();
                 String str_card_pin = input_card_pin.getText().toString();
                if(!str_card_num.equals("") && str_card_num.length() == 16 && !str_card_exp.equals("") && str_card_exp.length() == 7 && !str_card_cvc.equals("") && str_card_cvc.length() == 3 && !str_card_pin.equals("") && str_card_pin.length() == 6){
                    Toast.makeText(mContext, "Payment Done Successfully.", Toast.LENGTH_SHORT).show();
                }else {
                    if(str_card_num.equals("") || str_card_num.length() != 16){
                        Toast.makeText(mContext, "Please insert valid Card Number.", Toast.LENGTH_SHORT).show();
                        input_card_num.requestFocus();
                    }else if(str_card_exp.equals("") || str_card_exp.length() != 7){
                        Toast.makeText(mContext, "Please insert valid Card Exp date in 01/2017 format.", Toast.LENGTH_SHORT).show();
                        input_card_exp.requestFocus();
                    }else if(str_card_cvc.equals("") && str_card_cvc.length() != 3){
                        Toast.makeText(mContext, "Please insert valid Card CVC.", Toast.LENGTH_SHORT).show();
                        input_card_cvc.requestFocus();
                    }else if(str_card_pin.equals("") && str_card_pin.length() != 6){
                        Toast.makeText(mContext, "Please insert valid Postal Code.", Toast.LENGTH_SHORT).show();
                        input_card_pin.requestFocus();
                    }
                }
            }
        });
    }


}