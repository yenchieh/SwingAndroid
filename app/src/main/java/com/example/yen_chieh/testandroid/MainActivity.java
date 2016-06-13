package com.example.yen_chieh.testandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    ImageView uvButton, activityButton, weatherButton, debuggerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("Avenir_bold.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        uvButton = (ImageView) findViewById(R.id.uvButton);
        activityButton = (ImageView) findViewById(R.id.activityButton);
        weatherButton = (ImageView) findViewById(R.id.weatherButton);
        debuggerButton = (ImageView) findViewById(R.id.debuggerButton);

        attachEvent();
    }

    private void attachEvent(){
        uvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToUvPage();
            }
        });

        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToSportPage();
            }
        });

        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToWeatherPage();
            }
        });

        debuggerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToDebuggerPage();
            }
        });
    }

    private void switchToUvPage(){
        Intent intent = new Intent(this, UvActivity.class);
        startActivity(intent);
    }

    private void switchToSportPage(){
        Intent intent = new Intent(this, SportActivity.class);
        startActivity(intent);
    }

    private void switchToWeatherPage(){
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }

    private void switchToDebuggerPage(){
        Intent intent = new Intent(this, BleDebuggerActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
}
