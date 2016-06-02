package bamin.com.kepiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import bamin.com.kepiao.R;
import bamin.com.kepiao.constant.Constant;


public class WelcomeActivity extends Activity
{
    Handler hand = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        SharedPreferences sp = getSharedPreferences(Constant.SP_KEY.SP_ISFRIST, 0);
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