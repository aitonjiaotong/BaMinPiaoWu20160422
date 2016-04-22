package bamin.com.kepiao.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import bamin.com.kepiao.R;


public class WelcomeActivity extends AppCompatActivity
{
    Handler hand = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        SharedPreferences sp = getSharedPreferences("isfrist", 0);
        final boolean isfrist = sp.getBoolean("isfrist", true);
        hand.postDelayed(new Runnable()
        {

            @Override
            public void run()
            {
                Intent intent = new Intent();
                if (isfrist)
                {

                    intent.setClass(WelcomeActivity.this, GuideActivity.class);
                } else
                {
                    intent.setClass(WelcomeActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}