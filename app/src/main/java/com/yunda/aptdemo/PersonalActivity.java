package com.yunda.aptdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yunda.annotation.Arouter;

import androidx.appcompat.app.AppCompatActivity;

@Arouter(path = "/app/PersonalActivity")
public class PersonalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("===PersonalActivity===","PersonalActivity");
    }
    public void jump(View view) {
        Class<?> targetClass = OrderActivity$$ARouter.findTargetClass("/app/OrderActivity");
        startActivity(new Intent(this,targetClass));
    }
}
