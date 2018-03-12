package com.example.app;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;
import com.example.app.MainActivity;
import com.example.app.R;


public class ScanSuccess extends AppCompatActivity {
    String location = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        final MediaPlayer mediaPlayerSuccess = MediaPlayer.create(this, R.raw.unlock_success);
        final MediaPlayer mediaPlayerFailure = MediaPlayer.create(this, R.raw.unlock_failure);
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
                break;
            case "jorvik":
                tv.setText(R.string.jorvik);
                mediaPlayerSuccess.start();
                break;
            default:
                CharSequence error = "Invalid QR Code";
                mediaPlayerFailure.start();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this,error,duration);
                toast.show();
                Intent i =new Intent(this,MainActivity.class);
                startActivity(i);
        }
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}