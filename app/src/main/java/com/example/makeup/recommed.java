package com.example.makeup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class recommed extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommed);

        Intent myIntent = getIntent();
        Bitmap bitmap = (Bitmap) myIntent.getParcelableExtra("Images");
        String[] txt = myIntent.getStringArrayExtra("Result");

        TextView txtResult = findViewById(R.id.textView11);
        TextView กลับหน้าแรก = findViewById(R.id.textView7);
        ImageView imageViewResult = findViewById(R.id.imageView5);

        imageViewResult.setImageBitmap(bitmap);

        for(String el: txt){
            txtResult.append(el + "\n");
        }

        กลับหน้าแรก.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(recommed.this, MakeUp_H.class);
                startActivity(i); }
        });

    }
}