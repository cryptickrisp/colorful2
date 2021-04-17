package com.example.tg_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TG_TEST";
    TextView tBox, tBox2;
    Translate translate;
    private boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tBox = (TextView) findViewById(R.id.textbox);
        tBox2 = (TextView) findViewById(R.id.textbox2);
        Button change = (Button) findViewById(R.id.changeButton);
        Button clear = (Button) findViewById(R.id.clearButton);
        Button copy = (Button) findViewById(R.id.copyButton);
        translate = getTranslateService();

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkInternetConnection()) {
                    Log.e(TAG, "no internet connection :(");
                    return;
                }

                String str1 = String.valueOf(tBox.getText());
//                tBox2.setText("「いろはにほへと」" + str1 + "「ゑひもせす」");
                String res = translate(str1);
                tBox2.setText(res);

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tBox.setText("");
                tBox2.setText("");

            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str2 = String.valueOf(tBox2.getText());

                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("変更　された　テキスト",str2);
                clipboard.setPrimaryClip(clip);

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

    // https://medium.com/@yeksancansu/how-to-use-google-translate-api-in-android-studio-projects-7f09cae320c7
    public Translate getTranslateService() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try (InputStream is = getResources().openRawResource(R.raw.credentials)) {
            //Get credentials:
            final GoogleCredentials myCredentials = GoogleCredentials.fromStream(is);

            Log.i(TAG, "probably authenticated" + myCredentials.toString());

            //Set credentials and get translate service:
            TranslateOptions translateOptions = TranslateOptions.newBuilder().setCredentials(myCredentials).build();
            return translateOptions.getService();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    public String translate(String in) {
        Log.i(TAG, "requesting translation of " + in);
        Translation translation = translate.translate(in, Translate.TranslateOption.targetLanguage("ja"), Translate.TranslateOption.model("base"));
        Log.i(TAG, "got translation: " + translation.toString());
        return translation.getTranslatedText();
    }

    public boolean checkInternetConnection() {
        //Check internet connection:
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //Means that we are connected to a network (mobile or wi-fi)
        connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;

        return connected;
    }
}