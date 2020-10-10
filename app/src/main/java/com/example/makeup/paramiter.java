package com.example.makeup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
import android.widget.Toast;

import com.example.makeup.api.api;
import com.example.makeup.model.Fodation;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class paramiter extends AppCompatActivity {
    ImageView im1;
    TextView tx1,tx2;
    Button bt1;
    Bitmap btm;
    List<Fodation.ContentsBean> contents;
    private double[] pinkTone = {156.8215,156.8615,159.0612,162}; //103L,101.5L,122F,125F
    private double[] yellowTone = {125.1024,148.2261,177.1572,188}; //105.5L,104L,128F,120F
    private double[] blackTone = {101.0661,119.5245,124.1606,135.3333}; //108L,322F,107L,310F

    List<Fodation.ContentsBean> arrayOfYellow = new ArrayList<>();
    List<Fodation.ContentsBean> arrayOfBlack = new ArrayList<>();
    List<Fodation.ContentsBean> arrayOfPink = new ArrayList<>();
    List<Fodation.ContentsBean> arrayOfResult = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paramiter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.137.1/server2020foudation/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api service = retrofit.create(api.class);

       Call<Fodation> call = service.GetItemFodation();
       call.enqueue(new Callback<Fodation>() {
           @Override
           public void onResponse(Call<Fodation> call, Response<Fodation> response) {
               contents = response.body().getContents();
               Toast.makeText(paramiter.this, "" + response.body().getMassage(), Toast.LENGTH_SHORT).show();
               for (Fodation.ContentsBean f : contents){
                   switch (f.getColor().toUpperCase()){
                       case "BLACK":
                           arrayOfBlack.add(new Fodation.ContentsBean(f.getColor(),f.getFound_code(),f.getName_found(),f.getName_brand(),f.getName_ver(),f.getColor(),f.getAvgtone()));
                           break;
                           case "YELLOW":
                               arrayOfYellow.add(new Fodation.ContentsBean(f.getColor(),f.getFound_code(),f.getName_found(),f.getName_brand(),f.getName_ver(),f.getColor(),f.getAvgtone()));
                               break;
                       case "PINK":
                           arrayOfPink.add(new Fodation.ContentsBean(f.getColor(),f.getFound_code(),f.getName_found(),f.getName_brand(),f.getName_ver(),f.getColor(),f.getAvgtone()));
                           break;
                       default:
                           break;
                   }
               }

               for (Fodation.ContentsBean f : arrayOfYellow){
                   Log.d("ArrayOfYellow", "" + f.getAvgtone());
               }
           }

           @Override
           public void onFailure(Call<Fodation> call, Throwable t) {
               Toast.makeText(paramiter.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
               Log.d("onFailure", "" + t.getMessage());
           }
       });
//        ArrayList<Double> s = new ArrayList<Double>(Arrays.<Double>asList(8.1, 2.0, 13.0, 4.0, 5.0, 6.0));
//        Log.d("TAG",  "" + sortArray(s)[0]);

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

        // getIntent() is a method from the started activity
        Intent myIntent = getIntent(); // gets the previously created intent
        Bitmap bitmap = (Bitmap) myIntent.getParcelableExtra("firstKeyName"); // will return "FirstKeyValue"
        int xx = myIntent.getIntExtra("secondKeyX", 0); // will return "SecondKeyValue"
        int yy = myIntent.getIntExtra("secondKeyY", 0);

//        int width = btm.getWidth();
//        int height = btm.getHeight();
//        float scaleWidth = ((float) 200) / width;
//        float scaleHeight = ((float) 200) / height;
//
         btm =  getCircularBitmap(bitmap, xx, yy);
//        Matrix matrix = new Matrix();
//        matrix.postScale(scaleWidth,scaleHeight);
//        Bitmap ImgResize = Bitmap.createBitmap(btm, btm.getWidth(), btm.getHeight(),width, height, matrix, true);
//        imgView.setImageBitmap(btm);
        imgView.setImageBitmap(btm);

        bt1 = findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RGB565(btm);
            }
        });
    }

    String tone;
    double blueColors = 0;
    int pixelCount = 0;
    int[] level = null;

    private void RGB565(Bitmap img) {
        if (img == null){
            Log.d("Img", "Img is NULL");
            return;
        }

        double sumB = 0;

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                //Retrieving contents of a pixel
//                int blueValue = Color.blue(img.getPixel(x, y));
//                Bc = blueValue;
                int pixel = img.getPixel(x, y);
                pixelCount++;
                blueColors += Color.blue(pixel) * 100;
            }
        }

        double avgB = 0;
        avgB = (blueColors/pixelCount);
//        avgR  ค่าเฉลี่ยของ R ในรูปภาพ
        Log.d("Avg", "=> " + avgB);


        if(avgB < 126.77){
            // pink
            ArrayList<Double> arrTone = new ArrayList<>();
            for(int i = 0; i < pinkTone.length; i++){
                arrTone.add(avgB - pinkTone[i]);// [3,2,1,0]
                System.out.println("black.");
            }

//            for (Double member : arrTone){
//                Log.i("Member name: ", "" + member);
//            }
            level = new int[arrTone.size()];
            level = sortArray(arrTone);
            tone = "BLACK";

        }else if(avgB >= 126.77 && avgB <= 188){
            // yellow
            ArrayList<Double> arrTone = new ArrayList<>();
            for(int i = 0; i < yellowTone.length; i++){
               arrTone.add(avgB - yellowTone[i]);
                System.out.println("yellow.");
            }

//            for (Double member : arrTone){
//                Log.i("Member name: ", "" + member);
//            }

            level = new int[arrTone.size()];
            level = sortArray(arrTone);
            tone = "YELLOW";
        }else if(avgB >=126.77 && avgB >=150.88 ){
            // black
            ArrayList<Double> arrTone = new ArrayList<>();
           for(int i = 0; i < blackTone.length; i++){
               arrTone.add(avgB - blackTone[i]);
                System.out.println("pink.");
            }

//            for (Double member : arrTone){
//                Log.i("Member name: ", "" + member);
//            }
            level = new int[arrTone.size()];
            level = sortArray(arrTone);
            tone = "PINK";
        }

        Log.d("Level",  "" + Arrays.toString(level));   //Example [2, 3, 1, 0]
        for (Integer i : level) {
            switch (tone){
                case "BLACK":
                    arrayOfResult.add(new Fodation.ContentsBean(arrayOfBlack.get(i).getColor(),arrayOfBlack.get(i).getFound_code(),arrayOfBlack.get(i).getName_found(),arrayOfBlack.get(i).getName_brand(),arrayOfBlack.get(i).getName_ver(),arrayOfBlack.get(i).getColor(),arrayOfBlack.get(i).getAvgtone()));
                    break;
                case "YELLOW":
                    arrayOfResult.add(new Fodation.ContentsBean(arrayOfYellow.get(i).getColor(),arrayOfYellow.get(i).getFound_code(),arrayOfYellow.get(i).getName_found(),arrayOfYellow.get(i).getName_brand(),arrayOfYellow.get(i).getName_ver(),arrayOfYellow.get(i).getColor(),arrayOfYellow.get(i).getAvgtone()));
                    break;
                case "PINK":
                    arrayOfResult.add(new Fodation.ContentsBean(arrayOfPink.get(i).getColor(),arrayOfPink.get(i).getFound_code(),arrayOfPink.get(i).getName_found(),arrayOfPink.get(i).getName_brand(),arrayOfPink.get(i).getName_ver(),arrayOfPink.get(i).getColor(),arrayOfPink.get(i).getAvgtone()));
                    break;
                default:
                    break;
            }
        }

        String[] array = new String[arrayOfResult.size()];
        int index = 0;
        for (Fodation.ContentsBean el: arrayOfResult){
            array[index] = "[" + (index+=1) +"]" + el.getFound_code() + " " + el.getName_brand() + " " + el.getName_found();
        }

        Log.d("RESULT", Arrays.toString(array));

        Intent i = new Intent(this, recommed.class);
        i.putExtra("Images", btm);
        i.putExtra("Result", array);
        startActivity(i);
    }

    private int[] sortArray(ArrayList<Double> array){
        ArrayList<Double> store = new ArrayList<Double>();

        for (Double member : array){
            if(member >= 0){
                store.add(member);
            }else {
                store.add(member /= -1);
            }
        }

        for (Double member : store){
            Log.d("Store",  "" + member);
        }
        ArrayList<Double> nstore = new ArrayList<Double>(store);
        Log.d("indexes",  "" + array.size());
        Collections.sort(store);
        int[] indexes = new int[store.size()];
        for (int n = 0; n < store.size(); n++){
            indexes[n] = nstore.indexOf(store.get(n));
        }
        return indexes;
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