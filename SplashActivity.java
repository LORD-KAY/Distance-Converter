package com.map.distanceconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
/**
 * Created by lord on 10/30/16.
 */

public class SplashActivity extends AppCompatActivity{
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loader_main);

        Thread welcomeThread = new Thread(){
            public void run(){
                try{
                    super.run();
                    sleep(2000);

                }catch (Exception e){

                }finally {
                    Intent i = new Intent(SplashActivity.this,
                            MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}
