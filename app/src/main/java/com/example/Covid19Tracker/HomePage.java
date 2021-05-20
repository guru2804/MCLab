package com.example.Covid19Tracker;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    ImageView india;
    Spinner spinner;
    Button call,message,statistics;
    ImageView button_notify,symptoms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        india=findViewById(R.id.indiaflag);
        statistics=findViewById(R.id.stat);
        call=findViewById(R.id.call);
        message = findViewById(R.id.sms);
        symptoms=findViewById(R.id.dourtest);

        india.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomePage.this, Statistics.class);
                startActivity(i);
            }

        });


        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomePage.this, Statistics.class);
                startActivity(i);

            }

        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+"91-11-23978046"));
                startActivity(intent);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNumber = "1123978046";
                String message = "Hi! Help me I need assistance";
                boolean installed = appInstalledOrNot("com.whatsapp");
                if (installed){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+91"+mobileNumber + "&text="+message));
                    startActivity(intent);
                }else {
                    Toast.makeText(HomePage.this, "Whatsapp is not installed on your device", Toast.LENGTH_SHORT).show();
                }

            }
        });
        symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, Symptoms.class);
                startActivity(intent);
            }
        });



    }

    private boolean appInstalledOrNot(String url) {
        PackageManager packageManager =getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e){
            app_installed = false;
        }
        return app_installed;
    }
}
