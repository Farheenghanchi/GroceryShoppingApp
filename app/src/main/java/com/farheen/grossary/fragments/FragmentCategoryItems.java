package com.farheen.grossary.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.farheen.grossary.R;
import com.farheen.grossary.Singleton;
import com.farheen.grossary.adapter.AdapterItems;
import com.farheen.grossary.data.InternetConnection;
import com.farheen.grossary.data.QueryUtils;
import com.farheen.grossary.model.ItemsPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Parth Modi on 30/03/2017.
 */

public class FragmentCategoryItems extends Fragment {

    private Context mContext;
    private View rootView;
    private ArrayList<ItemsPojo> CategoryItemsList = new ArrayList<>();
    private String category_selected = "2";
//    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private ListView mListView;
    private AdapterItems adapter;
    private String title;
    public FragmentCategoryItems(){

    }

    @SuppressLint("ValidFragment")
    public FragmentCategoryItems(Context mContext,String Category_selected,String Category_name) {
        this.mContext = mContext;
        this.category_selected  = Category_selected;
        this.title = Category_name;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_category_items, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
        Singleton.getInstance().setCurrentFragmentName("CategoryItems");
        // initialize views
        initView();

        return rootView;
    }

    private void initView() {

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.loading_indicator);
        mListView = (ListView) rootView.findViewById(R.id.list);
        adapter = new AdapterItems(mContext,CategoryItemsList);
        mListView.setAdapter(adapter);





        if (InternetConnection.checkConnection(getActivity().getApplicationContext())) {
//            CategoryList = new ArrayList<>();
            new GetDataTask().execute();
        } else {
            Toast.makeText(mContext, "Please check Internet connection.", Toast.LENGTH_SHORT).show();
//            Snackbar.make(view, "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
        }

    }

    /**
     * Creating Get Data Task for Getting Data From Web
     */
    class GetDataTask extends AsyncTask<Void, Void, Void> {

//        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */
            /*dialog = new ProgressDialog(mContext);
            dialog.setTitle("Hey Wait Please...");
            dialog.setMessage("I am getting your JSON");
            dialog.show();*/
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            /**
             * Getting JSON Object from Web Using okHttp
             */
//            Log.i("mytag", "inside class:GetDataTask \t "+ QueryUtils.getDataFromWeb());
            JSONObject jsonObject = QueryUtils.getDataFromWeb("http://gtuimp.com/sub_category.php?cat_id="+category_selected);

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {
                    /**
                     * Check Length...
                     */
                    if(jsonObject.length() > 0) {
                        /**
                         * Getting Array named "contacts" From MAIN Json Object
                         */
                        JSONArray array = jsonObject.getJSONArray("farhin");

                        /**
                         * Check Length of Array...
                         */
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for(int jIndex = 0; jIndex < lenArray; jIndex++) {

                                /**
                                 * Creating Every time New Object
                                 * and
                                 * Adding into List
                                 */
                                ItemsPojo model = new ItemsPojo();

                                /**
                                 * Getting Inner Object from contacts array...
                                 * and
                                 * From that We will get Name of that Contact
                                 *
                                 */
                                JSONObject innerObject = array.getJSONObject(jIndex);
                                String id = innerObject.getString("id");
                                String img = innerObject.getString("img");
                                String item_name = innerObject.getString("item_name");
                                String item_quantity = innerObject.getString("item_quantity");
                                String item_price = innerObject.getString("item_price");

                                /**
                                 * Getting Object from Object "phone"
                                 */
//                                JSONObject phoneObject = innerObject.getJSONObject(Keys.KEY_PHONE);
//                                String phone = phoneObject.getString(Keys.KEY_MOBILE);
//
                                model.setItem_id(id);
                                model.setItem_name(item_name);
                                model.setItem_img(img);
                                model.setItem_qty(item_quantity);
                                model.setItem_price(item_price);

                                /**
                                 * Adding name and phone concatenation in List...
                                 */
                                CategoryItemsList.add(model);
                            }
                        }
                    }
                } else {

                }
            } catch (JSONException je) {
                Log.i("mytag", "inside class:GetDataTask \t ");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            dialog.dismiss();
            mProgressBar.setVisibility(View.GONE);

            /**
             * Checking if List size if more than zero then
             * Update ListView
             */
            Log.i("mytag", "inside class:GetDataTask \t "+CategoryItemsList.size());
            if(CategoryItemsList.size() > 0) {
                adapter.notifyDataSetChanged();
//                adapter = new AdapterCategory(mContext,CategoryList);
//                mListView.setAdapter(adapter);
            } else {
//                Snackbar.make(findViewById(R.id.parentLayout), "No Data Found", Snackbar.LENGTH_LONG).show();
            }
        }
    }

}
