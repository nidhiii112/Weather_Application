package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText city;
    Button btn;
    TextView result;
    weatherSingleton weatherSingleton;

    String baseURL = "https://api.openweathermap.org/data/2.5/weather?q=";
    String API = "&appid=311b5f8dba149b726da73b0cb67ecdf6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        city = findViewById(R.id.getCity);
       result = findViewById(R.id.result);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myURL = baseURL + city.getText().toString() + API;

                JsonObjectRequest jsonObjectRequest = new
                        JsonObjectRequest(Request.Method.GET, myURL,
                        null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i("JSON", "JSON" + jsonObject);

                        try {

                            String info = jsonObject.getString("weather");
                            JSONArray ar = new JSONArray(info);

                            for (int i = 0; i < ar.length(); i++) {
                                JSONObject parobj = ar.getJSONObject(i);
                                String myweather = parobj.getString("main");
                                result.setText(myweather);
                                Log.i("ID", "ID" + parobj.getString("id"));
                                Log.i("MAIN", "MAIN" + parobj.getString("main"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("Error", "Something went wrong" + error);
                            }
                        }

                );
                weatherSingleton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);
            }
        });



    }
}