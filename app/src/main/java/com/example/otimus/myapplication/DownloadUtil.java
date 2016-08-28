package com.example.otimus.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class DownloadUtil {
	public static String NotOnline = "1";


	private String link;
	private Context context;
	private String encoding = "utf-8";

	public DownloadUtil(String link, Context context) {
		this.link = link;
		this.context = context;
	}



	public String downloadStringContent() {
		if (isOnline()) {
				String responseString = "";
// Making HTTP request
				try {
					// defaultHttpClient
					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(link);



					HttpResponse httpResponse = httpClient.execute(httpGet);
					HttpEntity httpEntity = httpResponse.getEntity();

					responseString = EntityUtils.toString(httpEntity);

					// Log.i(TAG, json + "------" );
				} catch (UnsupportedEncodingException e) {





				} catch (IOException e) {


				} catch (Exception e) {


				}

				return responseString;
		} else
			return NotOnline;

	}

	//




	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
}
