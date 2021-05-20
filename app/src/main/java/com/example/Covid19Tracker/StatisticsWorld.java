package com.example.Covid19Tracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StatisticsWorld extends AppCompatActivity {
    TextView confirmed, active, death, recovered, date, time;
    ImageView information, ret;
    Button country,statewise;
    Integer c;
    String str_confirmed, str_active, str_recovered, str_death, str_last_update_time;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statisticsworld);
        initialization();
        fetchData();
        statewise=findViewById(R.id.states);
        statewise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(StatisticsWorld.this, StateWise.class);
                startActivity(i);
                finish();

            }
        });
        country=findViewById(R.id.ind);
        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(StatisticsWorld.this, Statistics.class);
                startActivity(i);
                finish();

            }
        });


        ret = findViewById(R.id.imageView);
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(StatisticsWorld.this, HomePage.class);
                startActivity(i);
                finish();

            }
        });
    }


    private void initialization() {
        confirmed = findViewById(R.id.confirmed);
        active = findViewById(R.id.active);
        recovered = findViewById(R.id.recovered);
        death = findViewById(R.id.death);

        date = findViewById(R.id.date);
        time = findViewById(R.id.time);

        pieChart = findViewById(R.id.activity_main_piechart);
    }

    private void fetchData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String apiUrl = "https://corona.lmao.ninja/v2/all";
        pieChart.clearChart();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //As the data of the json are in a nested array, so we need to define the array from which we want to fetch the data.
                        JSONArray all_state_jsonArray = null;
                        JSONArray testData_jsonArray = null;

                        try {                            //Fetching data for India and storing it in String
                            str_confirmed = response.getString("cases");   //Confirmed cases in India
                            str_active = response.getString("active");    //Active cases in India
                            str_recovered = response.getString("recovered");  //Total recovered cased in India
                            str_death = response.getString("deaths");     //Total deaths in India
                            //str_last_update_time = data_india.getString("lastupdatedtime"); //Last update date and time

                            Handler delayToshowProgess = new Handler();

                            delayToshowProgess.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Setting text in the textview
                                    confirmed.setText(NumberFormat.getInstance().format(Integer.parseInt(str_confirmed)));
                                    c = Integer.parseInt(str_confirmed);
                                    active.setText(NumberFormat.getInstance().format(Integer.parseInt(str_active)));

                                    recovered.setText(NumberFormat.getInstance().format(Integer.parseInt(str_recovered)));

                                    death.setText(NumberFormat.getInstance().format(Integer.parseInt(str_death)));
                                    //tv_date.setText(FormatDate(str_last_update_time, 1));
                                    //tv_time.setText(FormatDate(str_last_update_time, 2));

                                    pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(str_active), Color.parseColor("#8051D3")));
                                    pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(str_recovered), Color.parseColor("#FFC107")));
                                    pieChart.addPieSlice(new PieModel("Deceased", Integer.parseInt(str_death), Color.parseColor("#F6404F")));

                                    pieChart.startAnimation();

                                }
                            }, 1000);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    private String FormatDate(String date, int testCase) {
        Date mDate = null;
        String dateFormat;
        try {
            mDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).parse(date);
            if (testCase == 0) {
                dateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a").format(mDate);
                return dateFormat;
            } else if (testCase == 1) {
                dateFormat = new SimpleDateFormat("dd MMM yyyy").format(mDate);
                return dateFormat;
            } else if (testCase == 2) {
                dateFormat = new SimpleDateFormat("hh:mm a").format(mDate);
                return dateFormat;
            } else {
                Log.d("error", "Wrong input! Choose from 0 to 2");
                return "Error";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }
}