package com.example.layout.exp2d;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.view.View.*;

public class feedback extends Activity {
    Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        button1 = (Button)findViewById(R.id.button);
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent( feedback.this , MainActivity.class );
                startActivity(i3);
            }
        });
    }
}
