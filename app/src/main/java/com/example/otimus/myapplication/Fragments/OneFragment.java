package com.example.otimus.myapplication.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;


import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.otimus.myapplication.Activities.MainActivity;
import com.example.otimus.myapplication.Activities.newsActivity;
import com.example.otimus.myapplication.Adapters.CustomArrayAdapter;
import com.example.otimus.myapplication.DataModels.Entity;
import com.example.otimus.myapplication.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * Created by Otimus on 8/2/2016.
 */public class OneFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    private static final String REGISTER_URL = "http://192.168.1.2/khabar/getrajniti.php?category_id=1";
    ListView listView;
    Button btn_loadmore;
    ArrayList<Entity> entityArrayList = new ArrayList<>();
    HttpClient client;
    String response;
    public View ftView;
    boolean isFinished=false;
    int id=0;






      private int[] image_resources={R.drawable.one,R.drawable.two,R.drawable.three};

    SliderLayout mDemoSlider;
    View rootView;


    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_one, container, false);
        LayoutInflater li=(LayoutInflater)getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
      ftView=li.inflate(R.layout.footer_view,null);
        btn_loadmore=(Button)ftView.findViewById(R.id.btn_loadmore);

        MainActivity activity = (MainActivity) getActivity();
        final String username = activity.getMyData();
      //  Log.d("oneuser",username);

//
       listView= (ListView) rootView.findViewById(R.id.listview);
        View header = inflater.inflate(R.layout.header, listView, false);
       listView.addHeaderView(header, null, false);
        listView.addFooterView(ftView,null,false);






        mDemoSlider = (SliderLayout) header.findViewById(R.id.slider);
        int[] ress = new int[3];
        ress[0] = R.drawable.one;
        ress[1] = R.drawable.two;
        ress[2] = R.drawable.three;

        for (int i = 0; i < ress.length; i++) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            String name = "";
            textSliderView
                    .description(name)
                    .image(ress[i])
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        // mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(3000);
        mDemoSlider.addOnPageChangeListener(this);
        mDemoSlider.setPresetTransformer("Default");

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent=new Intent(getActivity(),newsActivity.class);
               intent.putExtra("data",entityArrayList.get(position-1));
               intent.putExtra("user",username);

               startActivity(intent);

           }

       });


        btn_loadmore.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (isFinished) {
                    Toast.makeText(getActivity().getApplicationContext(), "No more stories", Toast.LENGTH_SHORT).show();
                } else {
                    new LongOperation().execute(REGISTER_URL);
                    Toast.makeText(getActivity().getApplicationContext(), "loading", Toast.LENGTH_SHORT).show();

                }            }
        });






        client = new DefaultHttpClient();

        new LongOperation().execute(REGISTER_URL);
        return rootView;
    }

    private class LongOperation extends AsyncTask<String,String,Void>{
        private String error = null;
        private ProgressDialog progressDialog = new ProgressDialog(getActivity());




        @Override
        protected void onPreExecute() {

            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait..");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(String... urls) {

            HttpGet request = new HttpGet(urls[0].toString()+"&id="+id);

            Log.d("url", urls[0].toString()+"&id="+id);

            HttpResponse httpResponse;
            StringBuilder stringBuilder = new StringBuilder();

            try {
                httpResponse = client.execute(request);
                HttpEntity entity = httpResponse.getEntity();
                InputStream stream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream,"UTF-8"));


                String line=null;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (ClientProtocolException e) {
                error="Error";
            } catch (IOException e) {
                error="Error";
            }
            response=stringBuilder.toString();
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {


            progressDialog.dismiss();
            if(error==null){
                try {

                    if(response.contains("[") && response.contains("]")) {
                        response = response.substring(response.indexOf('['), response.indexOf(']') + 1);
                        JSONArray jsonArray = new JSONArray(response);
                        if(jsonArray.length()<10){
                            isFinished=true;
                        }
                        // Toast.makeText(getActivity(),""+jsonArray.length(),Toast.LENGTH_LONG).show();
                        JSONObject jsonObject = new JSONObject();

                        Log.d("json array length", String.valueOf(jsonArray.length()));

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);


                            entityArrayList.add(new Entity(jsonObject.getInt("id"),jsonObject.getString("newstitle"),jsonObject.getString("newsbody"),jsonObject.getString("image"),jsonObject.getString("date"),jsonObject.getInt("category_id")));
                        }

                        Log.d("ITEMS SIZE", String.valueOf(entityArrayList.size()));

                        CustomArrayAdapter adapter=new CustomArrayAdapter(getActivity(), entityArrayList);
                       // listView.setAdapter(adapter);
                        if(id==0){
                            listView.setAdapter(adapter);
                        }else{
                            listView.invalidateViews();
                        }

                        id=jsonObject.getInt("id");

                        Log.d("last id", String.valueOf(id));


                    }


                }
                catch (JSONException e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }else{
                Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();
            }


        }


    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }


}

