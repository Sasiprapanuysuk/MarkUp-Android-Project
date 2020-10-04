package com.example.makeup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MakeUp_H extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makeup_h);

        Button เริ่มต้นใช้งาน = (Button)findViewById(R.id.button);
        เริ่มต้นใช้งาน.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MakeUp_H.this, MakeUp_M.class);
                startActivity(i); }
        });
    }
}