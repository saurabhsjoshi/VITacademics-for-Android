package com.karthikb351.vitinfo2.api;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.karthikb351.vitinfo2.api.Objects.Response;

/**
 * Created by sreeram on 7/12/14.
 */
public class HomeCall {
    public static final String HOST_VELLORE = "vitacademics-dev.herokuapp.com/api/vellore";
    public static final String HOST_CHENNAI = "vitacademics-dev.herokuapp.com/api/chennai";


    public static Response sendRequest(String regno, String dob, String campus, String path) throws Exception{

        final String USER_AGENT = "Mozilla/5.0";
        final String base_path= path;
        final String base_host;
        if(campus  == "Vellore"){
            base_host = HOST_VELLORE;
        }
        else if(campus == "Chennai"){
            base_host = HOST_CHENNAI;
        }
        else{
            base_host = HOST_VELLORE;
        }
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("regno", regno));
        params.add(new BasicNameValuePair("dob", dob));
        final URI uri = URIUtils.createURI("http", base_host, -1, base_path, URLEncodedUtils.format(params,"UTF-8"), null);

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet();
        request.addHeader("User-Agent", USER_AGENT);
        request.setURI(uri);
        final HttpResponse httpResponse = httpClient.execute(request);
        final HttpEntity responseEntity = httpResponse.getEntity();
        String json_response =  EntityUtils.toString(responseEntity);
        Gson gson = new Gson();
        Response response = gson.fromJson(json_response, Response.class);
        return response;
    }

    public static void main(String[] args){
        try {
            Response response = sendRequest("11bce0088", "05111993", "vellore", "/login/auto");
            System.out.print(response.getStatus().getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
