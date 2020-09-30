package com.br.API;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.swing.text.StringContent;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import com.br.Entity.receber.parse.Mentions;
import com.br.Entity.receber.parse.Parse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AcessAPI {
	
	String path = "https://api.infermedica.com/v2/";

	public String endpoint(String referencia, String type, String case_id) {
		
		String id = "xxxxx";
		String key = "xxxxxx";
		String language_model = "infermedica-en";
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				RequestBody body = RequestBody.create(mediaType, type);
				Request request = new Request.Builder()
				  .url(path+referencia)
				  .method("POST", body)
				  .addHeader("App-Id", id)
				  .addHeader("App-Key", key)
				  .addHeader("Content-Type", "application/json")
				  .addHeader("Accept", "application/json")
				  .build();
				try {
					Response response = client.newCall(request).execute();
					return response.body().string();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
		
		return	"Requisição falhou, cheque os argumentos";
					
		
	
	}
	
}
