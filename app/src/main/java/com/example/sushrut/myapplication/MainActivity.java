package com.example.sushrut.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.Random;


public class MainActivity extends Activity {
    EditText usN,psW,amt;
    TextView us,ps,resp,val,hello;
    Button log,cng,cnf,ext1;
    RelativeLayout la;
    String [] cols;
    GoogleCloudMessaging mGcm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usN = (EditText)findViewById(R.id.editText);
        psW = (EditText)findViewById(R.id.editText2);
        log = (Button)findViewById(R.id.button);
        us =(TextView)findViewById(R.id.textView2);
        ps = (TextView)findViewById(R.id.textView3);
        resp = (TextView)findViewById(R.id.textView4);
        cng = (Button)findViewById(R.id.button2);
        cnf = (Button)findViewById(R.id.button3);
        amt = (EditText)findViewById(R.id.editText3);
        val = (TextView)findViewById(R.id.textView5);
        hello = (TextView)findViewById(R.id.textView);
        la = (RelativeLayout)findViewById(R.id.ma);
        ext1 = (Button)findViewById(R.id.button4);
        cols = new String[7];
        cols[0] = "#ff6666";
        cols[1] = "#d9ff66";
        cols[2] = "#d966ff";
        cols[3] = "#6264e6";
        cols[4] = "#f3d14d";
        cols[5] = "#f3d1ce";
        cols[6] = "#b40eb0";
//0 lal 1 pivla 2
        mGcm = GoogleCloudMessaging.getInstance(getApplicationContext());

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void login(View view){
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String usnm =usN.getText().toString();
            //la.setBackgroundColor(Color.parseColor("#bfff00"));
            String pswd=psW.getText().toString();
            usN.setVisibility(View.INVISIBLE);
            psW.setVisibility(View.INVISIBLE);
            log.setVisibility(View.INVISIBLE);
            us.setVisibility(View.INVISIBLE);
            ps.setVisibility(View.INVISIBLE);
            cng.setVisibility(View.VISIBLE);
            ext1.setVisibility(View.VISIBLE);
            resp.setText("Please wait while ur request is processed...");
            Random r = new Random();
            int i1 = (r.nextInt(80) + 65);
            i1 = i1%7;
            la.setBackgroundColor(Color.parseColor(cols[i1]));

            new loginActivity(this,us,ps,resp,0).execute(usnm,pswd);
            hello.setText("Almost logged in!! Good!!");
        } else {
            setContentView(R.layout.activity_login);
        }
    }
    public void change(View view){
        hello.setText("Wanna change something??? Just tap the Change Button!!");
        usN.setVisibility(View.VISIBLE);
        psW.setVisibility(View.VISIBLE);
        usN.setText("");
        psW.setText("");
        //log.setVisibility(View.INVISIBLE);
        us.setVisibility(View.VISIBLE);
        ext1.setVisibility(View.INVISIBLE);
        ps.setVisibility(View.VISIBLE);
        cng.setVisibility(View.INVISIBLE);
        cnf.setVisibility(View.VISIBLE);
        amt.setVisibility(View.VISIBLE);
        val.setVisibility(View.VISIBLE);
        //la.setBackgroundColor(Color.parseColor("#66ccff"));
        Random r = new Random();
        int i1 = (r.nextInt(80) + 65);
        i1 = i1%7;
        la.setBackgroundColor(Color.parseColor(cols[i1]));

    }
    public void cnf(View view){
        String usnm =usN.getText().toString();
        String amount = amt.getText().toString();
        String pswd=psW.getText().toString();
        ext1.setVisibility(View.VISIBLE);
        new ChangeActivity(this,resp,0).execute(usnm,amount,pswd);
        Random r = new Random();
        int i1 = (r.nextInt(80) + 65);
        i1 = i1%7;
        la.setBackgroundColor(Color.parseColor(cols[i1]));
        hello.setText("Done!! Any more? Press Exit to Exit!!");
    }
    public void ext(View view){
        setContentView(R.layout.activity_change);
    }
    public void asdf(View view){
        finish();
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
                    String token = mGcm.register("sendoj");
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
