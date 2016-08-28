package com.example.otimus.myapplication.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otimus.myapplication.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Login extends AppCompatActivity implements View.OnClickListener {
    Button blogin;
    public static String username;
    EditText etusername,etpassword;
    TextView tvregisterlink;
    private static final String REGISTER_URL = "http://192.168.1.2/oregister/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        blogin=(Button)findViewById(R.id.blogin);
        etusername= (EditText) findViewById(R.id.etusername);
        etpassword= (EditText) findViewById(R.id.etpassword);
        tvregisterlink=(TextView)findViewById(R.id.tvregisterlink) ;
        blogin.setOnClickListener(this);
        tvregisterlink.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.blogin:
                login();
                break;
            case R.id.tvregisterlink:
                startActivity(new Intent(this,Register.class));
                break;
        }
    }

    private void login() {
        final String username=etusername.getText().toString().trim().toLowerCase();
        String password=etpassword.getText().toString().trim().toLowerCase();

        loginUser(username,password);



    }

    private void loginUser( final String username, String password) {

        String urlSuffix="?username="+username+"&password="+password;
        Log.d("url",urlSuffix);

        class login extends AsyncTask<String,String,String>{
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog=ProgressDialog.show(Login.this,"Please wait",null,true,true);


            }

            @Override
            protected void onPostExecute(String s) {

                super.onPostExecute(s);
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

                if(s.equals("Login Successful")) {
                    Intent intent=new Intent(Login.this, MainActivity.class);
                    intent.putExtra("user",username);

                  startActivity(intent);
                }



            }

            @Override
            protected String doInBackground(String... strings) {
                String s=strings[0];
                BufferedReader bufferedReader=null;
                try {
                    URL url = new URL(REGISTER_URL+s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;

                    result = bufferedReader.readLine();

                    return result;
                }catch(Exception e){
                    return null;
                }


            }
        }

        login lg=new login();
        lg.execute(urlSuffix);


    }
}
