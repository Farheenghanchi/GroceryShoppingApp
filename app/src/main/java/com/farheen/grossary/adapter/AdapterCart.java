package com.farheen.grossary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.farheen.grossary.R;
import com.farheen.grossary.Singleton;
import com.farheen.grossary.model.ItemsPojo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Parth Modi on 30/03/2017.
 */

public class AdapterCart extends BaseAdapter {

    private Context mContext;
    public ArrayList<ItemsPojo> cartItemList = new ArrayList<>();
    private static LayoutInflater inflater = null;

    // Constructor
    public AdapterCart(Context c, ArrayList<ItemsPojo> cartItemList) {
        mContext = c;
        this.cartItemList = cartItemList;
        inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return cartItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder {

        public ImageView imageView;
        public TextView tv_item_name;
        public TextView tv_item_qty;
        public TextView tv_item_price;
        private Button btn_remove_from_cart;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.row_cart, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.imageView = (ImageView) vi.findViewById(R.id.iv_item);
            holder.tv_item_name = (TextView) vi.findViewById(R.id.tv_item_name);
            holder.tv_item_qty = (TextView) vi.findViewById(R.id.tv_item_qty);
            holder.tv_item_price = (TextView) vi.findViewById(R.id.tv_item_ptice);
            holder.btn_remove_from_cart = (Button) vi.findViewById(R.id.btn_add_to_cart);

            /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (cartItemList.size() <= 0) {
//            holder.text.setText("No Data");
//
        } else {
            /***** Get each Model object from Arraylist ********/
            final ItemsPojo mItemPojo = cartItemList.get(position);

            /************  Set Model values in Holder elements ***********/

            holder.tv_item_name.setText(mItemPojo.getItem_name());
            holder.tv_item_qty.setText(mItemPojo.getItem_qty());
            holder.tv_item_price.setText("₹ "+mItemPojo.getItem_price());
            Picasso.with(mContext).load(mItemPojo.getItem_img()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.imageView);
            holder.btn_remove_from_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Singleton.getInstance().getCartProductsList().remove(position);
                    Toast.makeText(mContext, "" + mItemPojo.getItem_name() + " Removed from cart.", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            });

            /******** Set Item Click Listner for LayoutInflater for each row *******/

//            vi.setOnClickListener(new OnItemClickListener( position ));
        }
        return vi;

    }

}