package com.custom.listview;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "MainActivity";
    public static final int PERMISSION_CODE = 1;


    ListView listView;
    public static ArrayList<Container> containers = new ArrayList<>();
    CustomListViewAdapter customListViewAdapter;

    public static int position1;
    Timestamp ts1 = new Timestamp(System.currentTimeMillis());

    File TphotoFile = null;
    String TPhotoPath = "";
    private static final String IMAGE_DIRECTORY = "/LIST";
    public static String GetImagePath() {
        String path = Environment.getExternalStorageDirectory()
                + IMAGE_DIRECTORY;

        File file = new File(path);
        if (!file.exists())
            file.mkdirs();

        return path;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        //   add this line in manifest that help not to crash the app because of Out of Memory  android:largeHeap="true"
        listView = findViewById(R.id.listView);


        // Camera permission is required to proceed the next step

        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ){
            Log.d(TAG, " Already Permission Granted");
        }else{
            requestPermission();
        }



        fillList();
        setAdapter();

    }

    private void requestPermission() {

        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)){
            new AlertDialog.Builder(this).setTitle("Permission needed").setMessage("This Permission is needed to use Camera and Storage")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
                        }
                    }).setCancelable(false)
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Camera and Storage Permission Required to Proceed",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", getPackageName(), null)));
                finish();
            }

        }
    }

    public void fillList(){
        Bitmap photo = BitmapFactory.decodeResource(getResources(), R.drawable.photo_icon);

        containers = new ArrayList<>();
        containers.add(new Container(1, photo));
        containers.add(new Container(2, photo));
        containers.add(new Container(3, photo));
        containers.add(new Container(4, photo));
        containers.add(new Container(5, photo));
        containers.add(new Container(6, photo));
        containers.add(new Container(7, photo));
        containers.add(new Container(8, photo));
        containers.add(new Container(9, photo));
        containers.add(new Container(10, photo));
        containers.add(new Container(11, photo));
        containers.add(new Container(12, photo));
        containers.add(new Container(13, photo));
        containers.add(new Container(14, photo));
        containers.add(new Container(15, photo));
        containers.add(new Container(16, photo));
        containers.add(new Container(17, photo));
        containers.add(new Container(18, photo));
    }

    public void setAdapter(){
        customListViewAdapter = new CustomListViewAdapter(MainActivity.this, containers);
        listView.setAdapter(customListViewAdapter);
    }



    public void startCameraActivity()
    {
        TPhotoPath = GetImagePath() +  File.separator + position1 + ".jpg";
        TphotoFile =  new File(TPhotoPath);
        Intent intent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(TphotoFile));

        try
        {
            startActivityForResult(intent, 34);
            //startActivity();
        }
        catch (Exception e)
        {
            Toast.makeText(MainActivity.this, "Failed to open the camera.", Toast.LENGTH_SHORT).show();
        }

    }



    private int getOrientationFromImage(String path)
    {
        int orientation = 0;

        try
        {
            ExifInterface exif = new ExifInterface(path);
            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return orientation;
    }

    @Override
    public void onResume() {
        Timestamp ts2 = new Timestamp(System.currentTimeMillis());
        Timestamp tsd = new Timestamp(ts2.getTime());
        Timestamp tsa = new Timestamp(ts1.getTime());
        long milliseconds = tsd.getTime() - tsa.getTime();
        if(milliseconds > 10000 && customListViewAdapter!= null){
            customListViewAdapter.notifyDataSetChanged();
        }

        super.onResume();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 34) {


                int orientation = getOrientationFromImage(TphotoFile.getAbsolutePath());
//                String timestamp = getTimestampFromImage(TphotoFile.getAbsolutePath());
                //resize photo
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inSampleSize = 2;

                Bitmap bitmap = BitmapFactory.decodeFile(TphotoFile.getAbsolutePath(), options);
                bitmap.setDensity(Bitmap.DENSITY_NONE);

                Matrix matrix = new Matrix();
                Bitmap bMapRotated;

                if(orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                    matrix.postRotate(90);
                    bMapRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    Log.d("MainActivity", "position : " + position1 );
                    containers.get(position1).setPhoto(bMapRotated);
                }else if(orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                    matrix.postRotate(180);
                    bMapRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    Log.d("MainActivity", "position : " + position1 );
                    containers.get(position1).setPhoto(bMapRotated);
                }else if(orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                    matrix.postRotate(270);
                    bMapRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    Log.d("MainActivity", "position : " + position1 );
                    containers.get(position1).setPhoto(bMapRotated);
                }else{
                    Log.d("MainActivity", "position : " + position1 );
                    containers.get(position1).setPhoto(bitmap);
                }
            }
        }

//        super.onActivityResult(requestCode, resultCode, data);
    }
}
