package com.example.administrateur.webservicedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText txtCode;
    private TextView txtResult;
    private TextView txtName;
    private ListView lstResult;

    private final String URL_WS1 = "http://@services.groupkt.com/country/get/iso2code/";
    private final String URL_WS2 = "http://services.groupkt.com/country/get/all";

    ArrayList<String> pays = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCode = (EditText) findViewById(R.id.txtCode);
        txtResult = (TextView) findViewById(R.id.txtResult);
        txtName = (TextView) findViewById(R.id.txtName);
        lstResult = (ListView)findViewById(R.id.lstResult);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, pays);

    }

    public void Search (View v) throws InterruptedException, JSONException {
        // Ce Web Service retourne du JSON :

        httpClient hc = new httpClient();

        String code = txtCode.getText().toString().toUpperCase();
        String adr = URL_WS1 + code;
        hc.setAdr(adr);

        hc.start();
        hc.join();

        JSONObject jo = new JSONObject(hc.getResponse());
        JSONObject jo2 = jo.getJSONObject("RestResponse");
        JSONObject jo3 = jo2.getJSONObject("result");

        txtName.setText(jo3.getString("name"));
    }

    public void All (View v) throws InterruptedException, JSONException {

        String adr = URL_WS2;
        httpClient hc = new httpClient();
        hc.setAdr(adr);

        hc.start();
        hc.join();

        JSONObject jo = new JSONObject(hc.getResponse());
        JSONObject jo2 = jo.getJSONObject("RestResponse");
        JSONArray jo3 = jo2.getJSONArray("result");


        for (int i = 0; i < jo3.length(); i++ ){

            String nom = jo3.getJSONObject(i).getString("name");
            String alpha = jo3.getJSONObject(i).getString("alpha2_code");
            pays.add(nom +" "+ alpha);

            // 2ème méthode
            //pays.add(String.valueOf(jo3.getJSONObject(i).getString("name")+" "+ String.valueOf(jo3.getJSONObject(i).getString("alpha2_code"))));

        }
        lstResult.setAdapter(adapter);
    }
}
