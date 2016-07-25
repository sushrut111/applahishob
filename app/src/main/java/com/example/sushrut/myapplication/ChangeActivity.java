package com.example.sushrut.myapplication;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

public class ChangeActivity  extends AsyncTask<String,Void,String>{
    private TextView statusField,roleField,usnm,pswd,btn;

    private Context context;
    private int byGetOrPost = 0;
    public String rest,fest;
    public ChangeActivity(){

    }
    //flag 0 means get and 1 means post.(By default it is get.)
    public ChangeActivity(Context context,TextView roleField,int flag) {
        this.context = context;
        this.statusField = statusField;
        this.roleField = roleField;
        this.usnm = usnm;
        this.pswd = pswd;
        byGetOrPost = flag;

    }

    protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(String... arg0) {
        if(byGetOrPost == 0){ //means by Get Method

            try{


                String name = (String)arg0[0];
                String password = (String)arg0[2];
                String value = (String)arg0[1];
                int val = Integer.parseInt(value);
                String link = "http://sushrut.site88.net/andrch.php?keyt="+password+"&clname="+name+"&val="+val;
                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line="";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                return sb.toString();
            }

            catch(Exception e){
                return new String(e.getMessage());
            }
        }
        return new String("Exception: ");
    }

    @Override
    protected void onPostExecute(String result){
        //this.statusField.setText("Login Successful");
        this.roleField.setText(result);
        //this.pswd.setText("Key");
        //this.usnm.setText("Username");

        //rest = result;

    }


}