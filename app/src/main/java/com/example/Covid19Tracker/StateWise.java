package com.example.Covid19Tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.Covid19Tracker.Adapters.StateClass;
import com.example.Covid19Tracker.Models.StateModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StateWise extends AppCompatActivity {

    private RecyclerView rv_state;
    private StateClass countryWiseAdapter;
    private ArrayList<StateModel> stateModelArrayList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText et_search;

    private String str_country, str_confirmed, str_confirmed_new, str_active, str_active_new, str_recovered, str_recovered_new,
            str_death, str_death_new, str_tests;

    private MainActivity activity = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_wise);
        Init();
        FetchCountryWiseData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchCountryWiseData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //Search
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Filter(s.toString());
            }
        });
    }

    private void Filter(String text) {
        ArrayList<StateModel> filteredList = new ArrayList<>();
        for (StateModel item : stateModelArrayList) {
            if (item.getState().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        countryWiseAdapter.filterList(filteredList, text);
    }


    private void FetchCountryWiseData() {
        //Show progress dialog
       // activity.ShowDialog(this);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String apiURL ="https://api.covid19india.org/data.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            stateModelArrayList.clear();
                            JSONArray all_state_jsonArray = null;
                            all_state_jsonArray = response.getJSONArray("statewise");
                            for (int i=1;i<all_state_jsonArray.length(); i++){
                                JSONObject countryJSONObject = all_state_jsonArray.getJSONObject(i);

                                str_country = countryJSONObject.getString("state");
                                str_confirmed = countryJSONObject.getString("confirmed");
                                str_active = countryJSONObject.getString("active");
                                str_recovered = countryJSONObject.getString("recovered");
                                str_death = countryJSONObject.getString("deaths");


                                //Creating an object of our country model class and passing the values in the constructor
                                StateModel stateModel = new StateModel(str_country, str_confirmed, str_active,
                                        str_death, str_recovered);
                                //adding data to our arraylist
                                stateModelArrayList.add(stateModel);
                            }
                            Collections.sort(stateModelArrayList, new Comparator<StateModel>() {
                                @Override
                                public int compare(StateModel o1, StateModel o2) {
                                    if (Integer.parseInt(o1.getConfirmed())>Integer.parseInt(o2.getConfirmed())){
                                        return -1;
                                    } else {
                                        return 1;
                                    }
                                }
                            });
                            Handler makeDelay = new Handler();
                            makeDelay.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    countryWiseAdapter.notifyDataSetChanged();
                                    //activity.DismissDialog();
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

                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    private void Init() {
        swipeRefreshLayout = findViewById(R.id.activity_country_wise_swipe_refresh_layout);
        et_search = findViewById(R.id.activity_country_wise_search_editText);

        rv_state = findViewById(R.id.activity_country_wise_recyclerview);
        rv_state.setHasFixedSize(true);
        rv_state.setLayoutManager(new LinearLayoutManager(this));

        stateModelArrayList = new ArrayList<>();
        countryWiseAdapter = new StateClass(StateWise.this, stateModelArrayList);
        rv_state.setAdapter(countryWiseAdapter);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}