package com.farheen.grossary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.farheen.grossary.R;
import com.farheen.grossary.model.CategoryPojo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.farheen.grossary.R.id.tv_cat_desc;
import static com.farheen.grossary.R.id.tv_cat_name;

/**
 * Created by Parth Modi on 29/03/2017.
 */

public class AdapterCategory extends BaseAdapter {
    private Context mContext;

    public ArrayList<CategoryPojo> CategoryList = new ArrayList<>();
    private static LayoutInflater inflater=null;
    // Constructor
    public AdapterCategory(Context c,ArrayList<CategoryPojo> CategoryList){
        mContext = c;
        this.CategoryList = CategoryList;
        inflater = ( LayoutInflater )mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return CategoryList.size();
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
    public static class ViewHolder{

        public ImageView imageView;
        public TextView tv_cat_name;
        public TextView tv_cat_desc;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.row_category, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.imageView = (ImageView)vi.findViewById(R.id.iv_cat);
            holder.tv_cat_name=(TextView) vi.findViewById(tv_cat_name);
            holder.tv_cat_desc=(TextView) vi.findViewById(tv_cat_desc);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(CategoryList.size()<=0)
        {
//            holder.text.setText("No Data");
//
        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            CategoryPojo mCategoryPojo = CategoryList.get(position);

            /************  Set Model values in Holder elements ***********/

            String desc = shortnDesc(mCategoryPojo.getCategoryDesc());
            holder.tv_cat_name.setText(mCategoryPojo.getCategoryName());
            holder.tv_cat_desc.setText(desc);
            Picasso.with(mContext).load(mCategoryPojo.getImageSrc()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.imageView);


            /******** Set Item Click Listner for LayoutInflater for each row *******/

//            vi.setOnClickListener(new OnItemClickListener( position ));
        }
        return vi;

    }

    private String shortnDesc(String categoryDesc) {
        String[] strArray = categoryDesc.split(",");
        String finalDesc = "";

        for (int i = 0; i < 2; i++) {
            finalDesc += strArray[i]+" , ";
        }
        finalDesc += strArray[2];
        return finalDesc;
    }

}