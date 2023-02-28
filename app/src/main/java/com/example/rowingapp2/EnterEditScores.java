package com.example.rowingapp2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;

public class EnterEditScores extends AppCompatActivity {

    private static final String TAG = "MAIN_TAG";

    private Uri imageUri = null;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;

    private String[] cameraPermissions;
    private String[] storagePermissions;

    private TextRecognizer textRecognizer;

    private int scoreId;
    private double duration;
    private int distance;
    private double split;
    private int stroke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_edit_scores);

        ImageView camera = (ImageView) findViewById(R.id.camera);
        Button match = (Button) findViewById(R.id.match);

        Button save = (Button) findViewById(R.id.save);
        Button cancel = (Button) findViewById(R.id.cancel);

        EditText editDuration = (EditText) findViewById(R.id.editDuration);
        EditText editDistance = (EditText) findViewById(R.id.editDistance);
        EditText editSplit = (EditText) findViewById(R.id.editSplit);
        EditText editStroke = (EditText) findViewById(R.id.editStroke);

        Intent i = getIntent();
        if (i != null)
        {
            scoreId = i.getIntExtra("scoreId", -1);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EnterEditScores.this, WorkoutEnterScores.class);
                duration = Double.parseDouble(editDuration.getText().toString());
                distance = Integer.parseInt(editDistance.getText().toString());
                split = Double.parseDouble(editSplit.getText().toString());
                stroke = Integer.parseInt(editStroke.getText().toString());
                //i.putExtra("scoresEntered", true);
                i.putExtra("scoreId", scoreId);
                i.putExtra("duration", duration);
                i.putExtra("distance", distance);
                i.putExtra("split", split);
                i.putExtra("stroke", stroke);
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(EnterEditScores.this, WorkoutEnterScores.class);
//                startActivity(i);
                finish();
            }
        });


        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(EnterEditScores.this, camera);
                popupMenu.inflate(R.menu.camera_gallery_menu);
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.camera)
                        {
                            showInputImageDialog();
                            return true;
                        }
                        else if (item.getItemId() == R.id.gallery)
                        {
                            if (checkStoragePermission())
                            {
                                pickImageGallery();
                            }
                            else
                            {
                                requestStoragePermission();
                            }
                            return true;
                        }
                        return false;
                    }
                });

                //recognizeScoreFromImage();
            }
        });

        match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null)
                {
                    recognizeScoreFromImage();
                    editDuration.setText(String.valueOf(duration));
                }
            }
        });

    }

    private void matchScores(String t) {

        while (!t.isEmpty())
        {
            //DURATION
            for (int i = 0; i < t.length(); i++)
            {
                if (t.substring(i, i+1).equals(":"))    //go till you find what's hopefully time
                {
                        //Log.d("duration", "duration: " + t.substring(i-1, i));
                    duration = 60 * (Integer.parseInt(t.substring(i-1,i)));
                        //Log.d("duration", "duration extra: " + t.substring(i+1, i+5));
                    duration = duration + (Double.parseDouble(t.substring(i+1, i+5)));
                        Log.d("duration", "total duration: " + duration);
                        //Log.d("recognizedText", "updated text: " + t.substring(i+6));
                    t = t.substring(i+6);
                    break;
                }
            }
            break;
            /*
            //DISTANCE
            for (int i = 0; i < t.length(); i++)
            {
                if(!t.substring(i, i+1).equals(" "))        //if it's not empty
                {
                    Log.d("distance", "distance: " + t.substring(i, i+4));
                    distance = Integer.parseInt(t.substring(i, i+4));
                    t = t.substring(i+5);
                    break;
                }
            }

            //SPLIT
            for (int i = 0; i < t.length(); i++)
            {

            }


            //STROKE
            for (int i = 0; i < t.length(); i++)
            {

            }

             */
        }

        /*
        Log.d("distance", "distance: " + t.substring(0, 4));
        distance = Integer.parseInt(t.substring(0, 4));
        t = t.substring(5);

        Log.d("split", "split: " + t.substring(0, 1));
        split = 60 * (Integer.parseInt(t.substring(0,1)));
        Log.d("split", "split extra: " + t.substring(2, 6));
        split += (Double.parseDouble(t.substring(2,6)));
        t = t.substring(7);

        Log.d("stroke", "stroke: " + t.substring(0, 2));
        stroke = Integer.parseInt(t.substring(0,2));
         */
    }

    /**********************************************
     *                CAMERA STUFF
     *********************************************/
    private void recognizeScoreFromImage(){
        try {
            InputImage inputImage = InputImage.fromFilePath(this, imageUri);
            Task<Text> textTaskResult = textRecognizer.process(inputImage)
                    .addOnSuccessListener(new OnSuccessListener<Text>() {
                        @Override
                        public void onSuccess(Text text) {
                            String recognizedText = text.getText();
                            Log.d("recognized text", "text: \n" + text.getText());
                            matchScores(recognizedText);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //can put a toast here saying 'it didn't work'
                            Toast.makeText(EnterEditScores.this, "Failed recognizing text due to "+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        } catch (Exception e) { //if nothing is recognized
            Toast.makeText(this, "Failed preparing image due to "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void showInputImageDialog() {
        //this assumes that we're using the camera
        if (checkCameraPermission())
        {
            pickImageCamera();
        }
        else
        {
            requestCameraPermission();
        }
    }


    private void pickImageCamera(){

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Sample Title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Sample Description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        image.launch(i);
    }

    private boolean checkCameraPermission(){
        //check if both permissions are granted
        boolean result1 = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result2 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result1 && result2;
    }

    private void requestCameraPermission(){

        ActivityCompat.requestPermissions(this,cameraPermissions,CAMERA_REQUEST_CODE);
    }

    private ActivityResultLauncher<Intent> image = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //here we handle the image if picked from gallery
                    if (result.getResultCode() == Activity.RESULT_OK)
                    {

                    }
                    else
                    {

                    }
                }
            }
    );

    /**********************************************
     *                STORAGE STUFF
     *********************************************/
    private void pickImageGallery() {

        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        gallery.launch(i);
    }

    private boolean checkStoragePermission(){
        //check if the permission is granted
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission(){

        ActivityCompat.requestPermissions(this,storagePermissions,STORAGE_REQUEST_CODE);
    }

    private ActivityResultLauncher<Intent> gallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //here we handle the image if picked from gallery
                    if (result.getResultCode() == Activity.RESULT_OK)
                    {
                        Intent data = result.getData();
                        imageUri = data.getData();
                        //image,setImageURI(imageUri);   <-- we can use this in other things
                    }
                    else
                    {

                    }
                }
            }
    );

    //check for permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0)
                {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted)
                    {
                        pickImageCamera();
                    }
                    else
                    {
                        //we can make a toast here reminding them that we need permission
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0)
                {
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted)
                    {
                        pickImageGallery();
                    }
                    else
                    {
                        //we can make a toast here reminding them that we need permission
                    }
                }
            }
            break;
        }
    }


}