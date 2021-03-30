package com.example.tg_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView tBox = (TextView) findViewById(R.id.textbox);
        TextView tBox2 = (TextView) findViewById(R.id.textbox2);
        Button change = (Button) findViewById(R.id.changeButton);
        Button copy = (Button) findViewById(R.id.copyButton);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str1 = String.valueOf(tBox.getText());
                tBox2.setText("「いろはにほへと」" + str1 + "「ゑひもせず」");

            }
        });

        /*
        copy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ClipboardManager copiedText = (ClipboardManager) tBox2.getSystemService

            }
        });

         */




    }
}