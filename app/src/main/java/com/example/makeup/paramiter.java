package com.example.makeup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class paramiter extends AppCompatActivity {
    ImageView im1;
    TextView tx1,tx2;
    Button bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paramiter);

        ImageView imgView = (ImageView) findViewById(R.id.im1);

        TextView tx1 = (TextView) findViewById(R.id.tx1);
        tx1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(paramiter.this, MakeUp_M.class);
                startActivity(i);
            }
        });

        TextView tx2 = (TextView) findViewById(R.id.tx2);
        tx2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(paramiter.this, recommed.class);
                startActivity(i);
            }
        });

        bt1 = findViewById(R.id.bt1);



        // getIntent() is a method from the started activity
        Intent myIntent = getIntent(); // gets the previously created intent
        Bitmap bitmap = (Bitmap) myIntent.getParcelableExtra("firstKeyName"); // will return "FirstKeyValue"
        int xx = myIntent.getIntExtra("secondKeyX", 0); // will return "SecondKeyValue"
        int yy = myIntent.getIntExtra("secondKeyY", 0);

        Bitmap btm =  getCircularBitmap(bitmap, xx, yy);
        Matrix matrix = new Matrix();
        matrix.postScale(0.5f,0.5f);
//        Bitmap ImgResize = Bitmap.createBitmap(btm, 200, 200,200, 200, matrix, true);
//        imgView.setImageBitmap(btm);
        imgView.setImageBitmap(btm);

    }

    protected Bitmap getCircularBitmap(Bitmap srcBitmap, int x, int y) {
        // Calculate the circular bitmap width with border
        int squareBitmapWidth = Math.min(srcBitmap.getWidth(), srcBitmap.getHeight());
        // Initialize a new instance of Bitmap
        Bitmap dstBitmap = Bitmap.createBitmap (
                squareBitmapWidth, // Width
                squareBitmapWidth, // Height
                Bitmap.Config.ARGB_8888 // Config
        );
        Canvas canvas = new Canvas(dstBitmap);
        // Initialize a new Paint instance
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawCircle(x, y, 20, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // Calculate the left and top of copied bitmap
        canvas.drawBitmap(srcBitmap, 0, 0, paint);
        // Free the native object associated with this bitmap.
        srcBitmap.recycle();
        // Return the circular bitmap
        return dstBitmap;





    }
} 