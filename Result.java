package com.example.layout.exp2d;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * Created by MEENU SAKHANA S on 22-02-2018.
 */

public class Result extends Activity{
    DatePicker dp1;
    TextView tv1;
    Button b1;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_xml);

        dp1 = (DatePicker)findViewById(R.id.datePicker);
        tv1 = (TextView)findViewById(R.id.textView);
        b1 = (Button)findViewById(R.id.button7);

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                tv1.setText(getCurrentDate());
            }
        });

    }
    public String getCurrentDate()
    {
        StringBuilder sb = new StringBuilder();
        sb.append((dp1.getMonth()+1)+"/");
        sb.append(dp1.getDayOfMonth()+"/");
        sb.append(dp1.getYear());
        return sb.toString();
    }
}
