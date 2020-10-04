package com.example.makeup;


import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MakeUp_M extends AppCompatActivity {

    public static final int REQUEST_GALLERY = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    public static final int REQUEST_CAMERA = 2;

//    ImageView imageView;
    Uri uri;
    Bitmap editedBitmap;
    private FaceDetector detector;

    ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makeup_m);

        detector = new FaceDetector.Builder(getApplicationContext())
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .setMode(FaceDetector.FAST_MODE)
                .build();



        TextView ย้อนกลับ = (TextView) findViewById(R.id.textView2);
        ย้อนกลับ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MakeUp_M.this, MakeUp_H.class);
                startActivity(i);
            }
        });

        imageView1 = (ImageView) findViewById(R.id.imageView);

        TextView ถัดไป = (TextView)findViewById(R.id.nextp);
        ถัดไป.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MakeUp_M.this, paramiter.class);
                myIntent.putExtra("firstKeyName", bitmap);
                myIntent.putExtra("secondKeyX", x);
                myIntent.putExtra("secondKeyY", y);
                startActivity(myIntent);
            }
        });

        Button btnSelected = (Button) findViewById(R.id.button);
        btnSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent
                        , "Select Picture"), REQUEST_GALLERY);
            }
        });

        Button buttonIntent = (Button) findViewById(R.id.button1);
        buttonIntent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                String timeStamp =
//                        new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//
////                File folder = new File(Environment.getExternalStorageDirectory()  + "/example");
////
////                if (!folder.exists()) {
////                    folder.mkdirs();
////                    Log.d("Folder", "This folder created.");
////                }
//                String imageFileName = "IMG_" + timeStamp + ".jpg";
//                String path = Environment.getExternalStorageDirectory().toString();
//                Log.d("Path", path);
//                File f = new File(path, imageFileName);
//                uri = FileProvider.getUriForFile(getApplicationContext(),"com.example.file.provider", f);
//                Log.d("URI", uri.getPath());
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                startActivityForResult(intent, REQUEST_CAMERA);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String timeStamp =
                        new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "IMG_" + timeStamp + ".jpg";
                File f = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera/" + imageFileName);
                uri = FileProvider.getUriForFile(getApplicationContext(), "com.example.makeup.provider", f);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(Intent.createChooser(intent
                        , "Take a picture with"), REQUEST_CAMERA);
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
             uri = data.getData();
            try {
//               Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                processCameraPicture();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            getContentResolver().notifyChange(uri, null);
            ContentResolver cr = getContentResolver();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(cr, uri);
                imageView1.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap decodeBitmapUri(Context ctx, Uri uri) throws FileNotFoundException {
        int targetW = 300;
        int targetH = 300;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeStream(ctx.getContentResolver()
                .openInputStream(uri), null, bmOptions);
    }

    int x = 0, y = 0;
    Bitmap bitmap;
    private void processCameraPicture() throws Exception {
         bitmap = decodeBitmapUri(this, uri);
        if (detector.isOperational() && bitmap != null) {
            editedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                    .getHeight(), bitmap.getConfig());
            float scale = getResources().getDisplayMetrics().density;
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.GREEN);
            paint.setTextSize((int) (16 * scale));
            paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(6f);
            Canvas canvas = new Canvas(editedBitmap);
            canvas.drawBitmap(bitmap, 0, 0, paint);
            Frame frame = new Frame.Builder().setBitmap(editedBitmap).build();
            SparseArray<Face> faces = detector.detect(frame);

            for (int index = 0; index < faces.size(); ++index) {
                Face face = faces.valueAt(index);
                canvas.drawRect(
                        face.getPosition().x,
                        face.getPosition().y,
                        face.getPosition().x + face.getWidth(),
                        face.getPosition().y + face.getHeight(), paint);


//                canvas.drawText("Face " + (index + 1), face.getPosition().x + face.getWidth(), face.getPosition().y + face.getHeight(), paint);

                for (Landmark landmark : face.getLandmarks()) {
                    if(landmark.getType() == Landmark.LEFT_CHEEK){
                         x = (int) (landmark.getPosition().x);
                         y = (int) (landmark.getPosition().y);

                        canvas.drawCircle(x, y, 8, paint);
                        Log.d("ValueX", "==> " + x);
                        Log.d("ValueY", "==> " + y);
                    }
                }


            }

            if (faces.size() == 0) {
                Toast.makeText(this, "Scan Failed: Found nothing to scan", Toast.LENGTH_SHORT).show();
            } else {
//                editedBitmap = getCircularBitmap(editedBitmap, x, y);   เอาตรงนี้ไปใส่ในปุ่ม
                imageView1.setImageBitmap(editedBitmap);
                Toast.makeText(this, "No of Faces Detected: " + " " + String.valueOf(faces.size()), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Could not set up the detector!", Toast.LENGTH_SHORT).show();
        }
    }
}
//public class MakeUp_M1 extends AppCompatActivity {
//    @SuppressLint("MissingSuperCall")
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
//            Uri uri = data.getData();
//            try{
//                bitmap = Media.getBitmap(this.getContentResolver(), uri);
//                imageView1.setImageBitmap(bitmap);
//            } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//            } catch (IOException e){
//                    e.printStackTrace();
//            }
//        }else if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
//            getContentResolver().notifyChange(uri, null);
//            ContentResolver cr = getContentResolver();
//            try {
//                Bitmap bitmap = Media.getBitmap(cr, uri);
//                imageView1.setImageBitmap(bitmap);
//                Toast.makeText(getApplicationContext()
//                        , uri.getPath(), Toast.LENGTH_SHORT).show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    public void clk(View view){
//        Intent i = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(i,123);
//    }

//    @SuppressLint("MissingSuperCall")
//    protected void onActivityResult(int requestCode, int resultCode, Intent data ){
//
//        Bundle extras = data.getExtras();
//        Bitmap bitmap = (Bitmap) extras.get("data");
//        ImageView imageView = (ImageView) findViewById(R.id.imageView);
//        imageView.setImageBitmap(bitmap);
//    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
//            getContentResolver().notifyChange(uri, null);
//            ContentResolver cr = getContentResolver();
//            try {
//                Bitmap bitmap = Media.getBitmap(cr, uri);
//                imageView.setImageBitmap(bitmap);
//                Toast.makeText(getApplicationContext()
//                        , uri.getPath(), Toast.LENGTH_SHORT).show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
