package com.example.servicioaudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx=this;

        ((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startService(new Intent(ctx, MyService.class));
            }
        });

        ((Button)findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                stopService(new Intent(ctx, MyService.class));
            }
        });


    }
}
