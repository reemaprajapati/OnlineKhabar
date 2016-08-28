package com.example.otimus.myapplication.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.otimus.myapplication.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Register extends AppCompatActivity implements View.OnClickListener {
    EditText etname,etage,etusername,etpassword;
    Button bregister;
    private static final String REGISTER_URL = "http://192.168.1.2/oregister/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etname=(EditText)findViewById(R.id.etname);
        etage=(EditText)findViewById(R.id.etage);
        etusername=(EditText)findViewById(R.id.etusername);
        etpassword=(EditText)findViewById(R.id.etpassword);
        bregister=(Button)findViewById(R.id.bregister);
        bregister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bregister:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String name=etname.getText().toString().trim().toLowerCase();
        String age=etage.getText().toString().trim().toLowerCase();
        String username=etusername.getText().toString().trim().toLowerCase();
        String password=etpassword.getText().toString().trim().toLowerCase();

        register(name,age,username,password);


    }

    private void register(String name, String age, String username, String password) {

        String urlSuffix="?name="+name+"&age="+age+"&username="+username+"&password="+password;

        class registerUser extends AsyncTask<String,String,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading=ProgressDialog.show(Register.this,"Please Wait",null,true,true);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                startActivity(new Intent(Register.this,Login.class));



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

        registerUser ru = new registerUser();
        ru.execute(urlSuffix);
    }
}
