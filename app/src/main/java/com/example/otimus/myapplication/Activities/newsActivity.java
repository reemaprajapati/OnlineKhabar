package com.example.otimus.myapplication.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;


import android.widget.TextView;
import android.widget.Toast;

import com.example.otimus.myapplication.Adapters.CustomArrayAdapter;
import com.example.otimus.myapplication.DataModels.Entity;
import com.example.otimus.myapplication.DataModels.Entity1;
import com.example.otimus.myapplication.R;
import com.squareup.picasso.Picasso;

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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class newsActivity extends AppCompatActivity {
    private static final String REGISTER_URL = "http://192.168.1.2/khabar/setcomment.php";
    private static final String url = "http://192.168.1.2/khabar/getcomment.php";
    ArrayList<Entity1> centityArrayList = new ArrayList<>();
    CustomArrayAdapter.CustomArrayAdapter1 arrayAdapter;
    TextView tvnewstitle, tvdate, tvnewsbody;
    ImageView ivimageview;
    Entity entity;
    Button comment, done;
    EditText etcomment;
    public static int news_id;
    String username, response;
    HttpClient client;
    ListView clistView;
    private ShareActionProvider mShareActionProvider;


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.share_menu, menu);
        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        setShareIntent(shareIntent());

        // Return true to display menu
        return true;
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }


    }

    private Intent shareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        String sharingbody = entity.getNewstitle() + entity.getNewsbody();
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, sharingbody);

        return shareIntent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        //  getMenuInflater().inflate(R.menu.share_menu, menu);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.mytoolbar);


        myToolbar.setTitle("पूरा समाचार");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(newsActivity.this, MainActivity.class);

                startActivity(intent);
                Toast.makeText(newsActivity.this, "back button", Toast.LENGTH_SHORT).show();
            }

        });
        clistView = (ListView) findViewById(R.id.clistView);
        View header = getLayoutInflater().inflate(R.layout.header_news, null);
        clistView.addHeaderView(header, null, false);


        tvnewstitle = (TextView) header.findViewById(R.id.tvnewstitle);
        ivimageview = (ImageView) header.findViewById(R.id.ivimageview);
        tvnewsbody = (TextView) header.findViewById(R.id.tvnewsbody);
        tvdate = (TextView) header.findViewById(R.id.tvdate);
        Intent intent = getIntent();
        entity = (Entity) intent.getSerializableExtra("data");
        news_id = entity.getId();
        // Log.d("hello", String.valueOf(news_id));


        tvnewstitle.setText(entity.getNewstitle());
        Log.d("title", entity.getNewstitle());
        tvnewsbody.setText(entity.getNewsbody());

        Picasso.with(this).load("http://192.168.1.2/khabar/images/" + entity.getImages()).into(ivimageview);
        Log.d("date", entity.getDate());
        tvdate.setText(entity.getDate());

        username = getIntent().getStringExtra("user");
//        Log.d("aaaaa", username);
//
        comment = (Button) header.findViewById(R.id.btn_comment);
        etcomment = (EditText) header.findViewById(R.id.et_comment);

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setComment();

            }
        });

        clistView.setAdapter(new CustomArrayAdapter.CustomArrayAdapter1(getApplicationContext(), centityArrayList));
        client = new DefaultHttpClient();
        new LongOperation().execute(url);


    }

    private void setComment() {
        String comment1 = etcomment.getText().toString().trim();
        doComment(username, comment1, news_id);


    }

    private void doComment(final String username, final String comment1, int news_id) {
        String urlSuffix = "?username=" + username + "&comment_text=" + comment1 + "&news_id=" + news_id;

        class setComment extends AsyncTask<String, String, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(newsActivity.this, "Please wait", null, true, true);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                //   Log.d("comment",s);
                Toast.makeText(newsActivity.this, "Commented", Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());
            }

            @Override
            protected String doInBackground(String... strings) {

                String s = strings[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(REGISTER_URL + s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;

                    result = bufferedReader.readLine();

                    return result;
                } catch (Exception e) {
                    return null;
                }
            }


        }
        setComment su = new setComment();
        su.execute(urlSuffix);
    }

    private class LongOperation extends AsyncTask<String, String, Void> {
        String suffix = "?news_id=" + news_id;

        private String error = null;
        private ProgressDialog progressDialog = new ProgressDialog(newsActivity.this);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(newsActivity.this, "Please wait", null, true, true);
        }

        @Override
        protected Void doInBackground(String... urls) {

            HttpGet request = new HttpGet(urls[0].toString() + suffix);

            HttpResponse httpResponse;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                httpResponse = client.execute(request);
                HttpEntity entity = httpResponse.getEntity();
                InputStream stream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));


                String line = null;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (ClientProtocolException e) {
                error = "Error";
            } catch (IOException e) {
                error = "Error";
            }
            response = stringBuilder.toString();
            return null;


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            if (error == null) {
                try {

                    if (response.contains("[") && response.contains("]")) {
                        response = response.substring(response.indexOf('['), response.indexOf(']') + 1);
                        JSONArray jsonArray = new JSONArray(response);
                        // Toast.makeText(getActivity(),""+jsonArray.length(),Toast.LENGTH_LONG).show();
                        JSONObject jsonObject = new JSONObject();

                        Log.d("json array length", String.valueOf(jsonArray.length()));

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            centityArrayList.add(new Entity1(jsonObject.getInt("id"), jsonObject.getString("username"), jsonObject.getString("comment_text"), jsonObject.getInt("news_id")));
                        }

                        Log.d("comment size", String.valueOf(centityArrayList.size()));

                        clistView.setAdapter(new CustomArrayAdapter.CustomArrayAdapter1(getApplicationContext(), centityArrayList));
                        clistView.invalidateViews();


                    }


                } catch (JSONException e) {
                    Toast.makeText(newsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            } else {
                Toast.makeText(newsActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        }
    }


}
