package com.example.sushrut.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;


public class SigninActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new loginActivity().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public class loginActivity  extends AsyncTask<String,Void,String> {
        private TextView statusField,roleField,usnm,pswd,btn;

        private Context context;
        private int byGetOrPost = 0;
        public String rest,fest;
        public loginActivity(){

        }
        //flag 0 means get and 1 means post.(By default it is get.)
        public loginActivity(Context context,TextView us,TextView ps,TextView roleField,int flag) {
            this.context = context;
            byGetOrPost = flag;
            this.roleField = roleField;
            this.usnm = us;
            this.pswd = ps;



        }
        //  GoogleCloudMessaging mGcm;
        protected void onPreExecute(){
            //        mGcm = GoogleCloudMessaging.getInstance(getApplicationContext());
        }

        @Override
        protected String doInBackground(String... arg0) {
            if(byGetOrPost == 0){ //means by Get Method

                try{
                    String username = (String)arg0[0];
                    String password = (String)arg0[1];
                    String link = "http://sushrut.site88.net/andr.php?keyt="+password;

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
                    return new String("Exception: " + e.getMessage());
                }
            }
            return new String("Exception: ");
        }

        @Override
        protected void onPostExecute(String result){
            if(result == "Incorrect"){
                this.statusField.setText("Key was incorrect Please exit the app and try again.");
                pswd.setVisibility(View.VISIBLE);
            }

            else{
                //this.statusField.setText("Login Successful");
                this.roleField.setText(result);
                this.pswd.setText("confirm key");
                this.usnm.setText("friend's name");
            }
            //Intent intent  =
            //rest = result;

        }


    }

}
