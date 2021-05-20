package com.example.Covid19Tracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;

import static com.example.Covid19Tracker.Constants.STATE_ACTIVE;
import static com.example.Covid19Tracker.Constants.STATE_CONFIRMED;
import static com.example.Covid19Tracker.Constants.STATE_DECEASED;
import static com.example.Covid19Tracker.Constants.STATE_NAME;
import static com.example.Covid19Tracker.Constants.STATE_RECOVERED;


public class StateData extends AppCompatActivity {

    private TextView tv_confirmed, tv_confirmed_new, tv_active, tv_active_new, tv_death, tv_death_new,
            tv_recovered, tv_recovered_new, tv_tests;

    Button countryname;

    private String str_countryName, str_confirmed, str_confirmed_new, str_active, str_active_new, str_death, str_death_new,
            str_recovered, str_recovered_new, str_tests;
    private PieChart pieChart;
    private MainActivity activity = new MainActivity();
    ImageView back,info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_each_state);



        back = findViewById(R.id.imageView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(StateData.this, StateWise.class);
                startActivity(i);
                finish();

            }
        });


        //Fetching data which is passed from previous activity into this activity
        GetIntent();

        //Setting up the title of actionbar as State name
        //getSupportActionBar().setTitle(str_countryName);

        //back menu icon on toolbar
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Initialise all textviews
        Init();

        LoadCountryData();
    }

    private void Init() {
        countryname=findViewById(R.id.countrynamecc);
        tv_confirmed = findViewById(R.id.confirmedcc);
        //tv_confirmed_new = findViewById(R.id.activity_each_country_data_confirmed_new_textView);
        tv_active = findViewById(R.id.activecc);
        //tv_active_new = findViewById(R.id.activity_each_country_data_active_new_textView);
        tv_recovered = findViewById(R.id.recoveredcc);
        //tv_recovered_new = findViewById(R.id.activity_each_country_data_recovered_new_textView);
        tv_death = findViewById(R.id.deathcc);
        //tv_death_new = findViewById(R.id.activity_each_country_data_death_new_textView);
        //tv_tests = findViewById(R.id.activity_each_country_data_tests_textView);
        pieChart = findViewById(R.id.activity_main_piechartcc);
    }

    private void LoadCountryData() {
        //Show dialog
        //activity.ShowDialog(this);

        Handler postDelayToshowProgress = new Handler();
        postDelayToshowProgress.postDelayed(new Runnable() {
            @Override
            public void run() {
                countryname.setText(str_countryName);
                tv_confirmed.setText(NumberFormat.getInstance().format(Integer.parseInt(str_confirmed)));
                //tv_confirmed_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_confirmed_new)));

                tv_active.setText(NumberFormat.getInstance().format(Integer.parseInt(str_active)));
                /*int int_active_new = Integer.parseInt(str_confirmed_new)
                        - (Integer.parseInt(str_recovered_new) + Integer.parseInt(str_death_new));
                tv_active_new.setText("+"+NumberFormat.getInstance().format(int_active_new<0 ? 0 : int_active_new));*/
                //tv_active_new.setText("N/A");

                tv_death.setText(NumberFormat.getInstance().format(Integer.parseInt(str_death)));
                //tv_death_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_death_new)));

                tv_recovered.setText(NumberFormat.getInstance().format(Integer.parseInt(str_recovered)));
                //tv_recovered_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_recovered_new)));
                //tv_recovered_new.setText("N/A");

                //tv_tests.setText(NumberFormat.getInstance().format(Integer.parseInt(str_tests)));

                //setting piechart
                pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(str_active), Color.parseColor("#8051D3")));
                pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(str_recovered), Color.parseColor("#FFC107")));
                pieChart.addPieSlice(new PieModel("Deceased", Integer.parseInt(str_death), Color.parseColor("#F6404F")));

                pieChart.startAnimation();

                //activity.DismissDialog();
            }
        },1000);

    }

    private void GetIntent() {
        Intent intent = getIntent();
        str_countryName = intent.getStringExtra(STATE_NAME);
        str_confirmed = intent.getStringExtra(STATE_CONFIRMED);
        str_active = intent.getStringExtra(STATE_ACTIVE);
        str_death = intent.getStringExtra(STATE_DECEASED);
        str_recovered = intent.getStringExtra(STATE_RECOVERED);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}