package com.example.otimus.myapplication.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
 */
public class TwoFragment extends Fragment{
    Button btn_loadmore;
    ListView listView;
    View rootView1;
    HttpClient client;
    String response;
    private static final String REGISTER_URL = "http://192.168.1.2/khabar/getrajniti.php?category_id=2";
    ArrayList<Entity> entityArrayList= new ArrayList<>();
    public View ftView;
    boolean isFinished=false;
    int id=0;

    public TwoFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         rootView1=inflater.inflate(R.layout.fragment_two, container, false);
         listView= (ListView) rootView1.findViewById(R.id.listview1);
        LayoutInflater li=(LayoutInflater)getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        ftView=li.inflate(R.layout.footer_view,null);
        btn_loadmore=(Button)ftView.findViewById(R.id.btn_loadmore);
        listView.addFooterView(ftView,null,false);

        MainActivity activity = (MainActivity) getActivity();
        final String username = activity.getMyData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent=new Intent(getActivity(),newsActivity.class);
                intent.putExtra("data",entityArrayList.get(position));
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

                    //Constants.serverURl+Constants.page+"/index.php"
                }            }
        });

        client = new DefaultHttpClient();

        new LongOperation().execute(REGISTER_URL);


      return rootView1;
    }


     private class LongOperation extends AsyncTask<String,String,Void>{
        private String error=null;
        private ProgressDialog progressDialog1=new ProgressDialog(getActivity());



        @Override
        protected void onPreExecute() {
            progressDialog1.setCancelable(false);
            progressDialog1.setMessage("Please Wait");
            progressDialog1.show();

           }



        @Override
        protected Void doInBackground(String... urls) {
            HttpGet request = new HttpGet(urls[0].toString()+"&id="+id);

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
            progressDialog1.dismiss();
            if (error==null){
                try{

                    if(response.contains("[")&&response.contains("]")){
                        response=response.substring(response.indexOf('['),response.indexOf(']')+1);
                        JSONArray jsonArray=new JSONArray(response);
                        if(jsonArray.length()<10){
                            isFinished=true;
                        }
                        JSONObject jsonObject=new JSONObject();

                        for(int i=0;i<jsonArray.length();i++){
                            jsonObject= jsonArray.getJSONObject(i);
                            entityArrayList.add(new Entity(jsonObject.getInt("id"),jsonObject.getString("newstitle"),jsonObject.getString("newsbody"),jsonObject.getString("image"),jsonObject.getString("date"),jsonObject.getInt("category_id")));
                        }
                     //   listView.setAdapter(new CustomArrayAdapter(getActivity(),entityArrayList));


                        CustomArrayAdapter adapter=new CustomArrayAdapter(getActivity(), entityArrayList);
                        // listView.setAdapter(adapter);
                        if(id==0){
                            listView.setAdapter(adapter);
                        }else{
                            listView.invalidateViews();
                        }

                        id=jsonObject.getInt("id");

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
}

