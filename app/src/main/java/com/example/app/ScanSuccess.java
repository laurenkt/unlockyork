package com.example.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

public class ScanSuccess extends AppCompatActivity {
    String location = "null";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        final MediaPlayer mediaPlayerSuccess = MediaPlayer.create(this, R.raw.unlock_success);
        final MediaPlayer mediaPlayerFailure = MediaPlayer.create(this, R.raw.unlock_failure);

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        setContentView(R.layout.activity_scan_success);

        TextView tv = findViewById(R.id.scan_text);
        if (b!=null) {
            location = b.getString("Location");
        }
        else location = "invalid";

        switch (location){
            case "minster":
                tv.setText(R.string.minster);
                mediaPlayerSuccess.start();
                toast.show();
                break;

            case "jorvik":
                tv.setText(R.string.jorvik);
                mediaPlayerSuccess.start();
                toast.show();
                break;

            default:
                CharSequence error = "Invalid QR Code";
                mediaPlayerFailure.start();
                int duration = Toast.LENGTH_SHORT;
                Toast toastText = Toast.makeText(this,error,duration);
                toastText.show();
                Intent i = new Intent(this,MainActivity.class);

                startActivity(i);
        }
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}