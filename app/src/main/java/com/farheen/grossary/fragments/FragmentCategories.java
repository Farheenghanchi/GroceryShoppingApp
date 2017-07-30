package com.farheen.grossary.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.farheen.grossary.R;
import com.farheen.grossary.Singleton;
import com.farheen.grossary.adapter.AdapterCategory;
import com.farheen.grossary.data.InternetConnection;
import com.farheen.grossary.data.QueryUtils;
import com.farheen.grossary.model.CategoryPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Parth Modi on 29/03/2017.
 */

public class FragmentCategories extends Fragment {

    private Context mContext;
    private View rootView;
    private ArrayList<CategoryPojo> CategoryList = new ArrayList<>();
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private ListView mListView;
    private AdapterCategory adapter;
    public FragmentCategories(){

    }

    @SuppressLint("ValidFragment")
    public FragmentCategories(Context mContext) {
//        toolbar.setTitle("Category");
        this.mContext = mContext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_category, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Category");
        Singleton.getInstance().setCurrentFragmentName("Category");
        // initialize views
        initView();

        return rootView;
    }

    private void initView() {
        CategoryList = new ArrayList<>();
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.loading_indicator);
        mListView = (ListView) rootView.findViewById(R.id.list);
        adapter = new AdapterCategory(mContext,CategoryList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pushInnerFragment(new FragmentCategoryItems(mContext,""+(i+1),CategoryList.get(i).getCategoryName()),"Items",true);
            }
        });


        if (InternetConnection.checkConnection(getActivity().getApplicationContext())) {

            new GetDataTask().execute();
        } else {
            Toast.makeText(mContext, "Please check Internet connection.", Toast.LENGTH_SHORT).show();
//            Snackbar.make(view, "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
        }
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
    private void pushInnerFragment(Fragment fragment, String tag, boolean addToBackStack) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.content_main, fragment, tag);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commitAllowingStateLoss();
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
//            Log.i("mytag", "inside class:GetDataTask \t "+QueryUtils.getDataFromWeb("http://gtuimp.com/get_category.php"));
            JSONObject jsonObject = QueryUtils.getDataFromWeb("http://gtuimp.com/get_category.php");

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
                                CategoryPojo model = new CategoryPojo();

                                /**
                                 * Getting Inner Object from contacts array...
                                 * and
                                 * From that We will get Name of that Contact
                                 *
                                 */
                                JSONObject innerObject = array.getJSONObject(jIndex);
                                String id = innerObject.getString("id");
                                String cate_name = innerObject.getString("cate_name");
                                String img = innerObject.getString("img");
                                String desc = innerObject.getString("desc");

                                /**
                                 * Getting Object from Object "phone"
                                 */
//                                JSONObject phoneObject = innerObject.getJSONObject(Keys.KEY_PHONE);
//                                String phone = phoneObject.getString(Keys.KEY_MOBILE);
//
                                model.setCat_id(id);
                                model.setCategoryName(cate_name);
                                model.setImageSrc(img);
                                model.setCategoryDesc(desc);

                                /**
                                 * Adding name and phone concatenation in List...
                                 */
                                CategoryList.add(model);
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
            Log.i("mytag", "inside class:GetDataTask \t "+CategoryList.size());
            if(CategoryList.size() > 0) {
                adapter.notifyDataSetChanged();
//                adapter = new AdapterCategory(mContext,CategoryList);
//                mListView.setAdapter(adapter);
            } else {
//                Snackbar.make(findViewById(R.id.parentLayout), "No Data Found", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
