package com.yunda.aptdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yunda.annotation.Arouter;

@Arouter(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("===MainActivity===","MainActivity");
    }

    public void jump(View view) {
        Class<?> targetClass = PersonalActivity$$ARouter.findTargetClass("/app/PersonalActivity");
        startActivity(new Intent(this,targetClass));
    }
}
