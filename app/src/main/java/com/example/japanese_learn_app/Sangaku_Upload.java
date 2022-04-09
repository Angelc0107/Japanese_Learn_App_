package com.example.japanese_learn_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.UUID;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

//import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.squareup.picasso.Picasso;
//import com.google.mlkit.vision.text.TextRecognizerOptions;


import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;


public class Sangaku_Upload extends AppCompatActivity {
    FirebaseStorage fire_storage;
    StorageReference fire_storage_Reference;
    DatabaseReference Firedatabase_ref;
    // mongodb+srv://StartJPapp:<password>@cluster0.f6l9p.mongodb.net/myFirstDatabase?retryWrites=true&w=majority
    private Bitmap pic ;
    private ImageView imageupload;
    private Button Selbtn, uploadbtn,transformbtn,tranENGbtn ;
    private TextView textview;
    private  static final  int img_re_code = 21;
    private Uri filepath ;
    private Bitmap imageBitmap;
    EditText edit_txtdata ;
    ProgressDialog progressimagecirlce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sangaku_upload);

        // textview = (TextView) findViewById(R.id.text_transf);
        imageupload = findViewById(R.id.image_upload);
        Selbtn = findViewById(R.id.Selectbtn);
        uploadbtn = findViewById(R.id.uplbtn);
        transformbtn=findViewById(R.id.transbtn);
        tranENGbtn = findViewById(R.id.transEnbtn);
        edit_txtdata = (EditText)findViewById(R.id.editTextTextPersonName);
        fire_storage = FirebaseStorage.getInstance();
        fire_storage_Reference = fire_storage.getReference();
        Firedatabase_ref = FirebaseDatabase.getInstance().getReference("Images");
        Login_Activity.member=true;

        Selbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view_page) {
                Login_Activity.member=true;
                Intent open_img = new Intent();
                //open the image gallery to import the image
                open_img.setType("image/*");
                //Intent intent1 = new Intent(this, activity_sangaku_upload.class);
                open_img.setAction(Intent.ACTION_GET_CONTENT);
                //allow to select the image with image_request
                startActivityForResult(
                        Intent.createChooser(
                                open_img,
                                "try to select a tragert image"),
                        img_re_code);

            }
        });



        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view_page) {
                uploadImage();
            }
        });


        transformbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view_page) {
                try {
                    detectimage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        tranENGbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view_page) {
                //set the japanese and english translator
                TranslatorOptions JEopt =
                        new TranslatorOptions.Builder()
                                .setSourceLanguage(TranslateLanguage.JAPANESE)
                                .setTargetLanguage(TranslateLanguage.ENGLISH)
                                .build();
                //build the transalator
                final Translator JapaneseenglishTranslator = Translation.getClient(JEopt);
                //reference from Google ML kit
                //to verfify the text is not  null
                if (edit_txtdata != null) {
                    //download the model from the Japanese and english
                    JapaneseenglishTranslator.downloadModelIfNeeded()
                            .addOnSuccessListener(
                                    new OnSuccessListener() {
                                        @Override
                                        public void onSuccess(Object o) {
                                            Toast.makeText(Sangaku_Upload.this, "convert to english success", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                //if the function fail, it will prompt msg
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Model couldn’t be downloaded or other internal error.
                                            Toast.makeText(Sangaku_Upload.this, "convert to english failed", Toast.LENGTH_SHORT).show();
                                            // ...
                                        }
                                    });


                    //translatetoJP();
                    //transaltor to get the text from the image
                    JapaneseenglishTranslator.translate(edit_txtdata.getText().toString())
                            .addOnSuccessListener(
                                    new OnSuccessListener() {
                                        @Override
                                        public void onSuccess(Object o) {
                                            Toast.makeText(Sangaku_Upload.this, "convert to english success, string is out", Toast.LENGTH_SHORT).show();
                                            edit_txtdata.setText(o.toString());
                                        }

                                    })
                            //if the the functioni is failed, it will acll this
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Error.
                                            Toast.makeText(Sangaku_Upload.this, "convert to english failed, string is not out", Toast.LENGTH_SHORT).show();
                                            // ...
                                        }
                                    });
                }
            }
        });



    }

    @Override
    protected void onActivityResult(int re_code,int resultCode,Intent image_data) {
        super.onActivityResult(re_code, resultCode, image_data);
       // switch (requestCode) {
        if(re_code==img_re_code){
        //to verfy the upload status
            //if theese are not null, then it can find the file path
            if (resultCode == RESULT_OK  && resultCode == RESULT_OK
                    && image_data != null
                    && image_data.getData() != null) {

            }
            filepath = image_data.getData();


            try {

                //to get the data from the image
                Bundle bundle_image = image_data.getExtras();

                //using image bitmap to get the data
                imageBitmap = (Bitmap) bundle_image.get("data");

                // below line is to set the
                // image bitmap to our image.


                // Setting image on image view using Bitmap
                Bitmap image_data_bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filepath);
                //set the image on the mobile phone
                imageupload.setImageBitmap(image_data_bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
            //case img_re_code:

        //}


    }

    public String GetFileExtension(Uri uri) {

        ContentResolver Contentres = getContentResolver();
        MimeTypeMap typeMap = MimeTypeMap.getSingleton();
        return typeMap.getExtensionFromMimeType(Contentres.getType(uri)) ;

    }

    private void uploadImage() {
        if (filepath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressimagecirlce = new ProgressDialog(this);

            //progress the dialog
            String title = "Uploading the image...";
            progressimagecirlce.setTitle(title);
            progressimagecirlce.show();

            // Defining the child of storageReference

            String open = "images/";


            StorageReference DBref = fire_storage_Reference.child(open + UUID.randomUUID().toString());
            // adding listeners on upload
            // or failure of image

            System.out.println( filepath);
            DBref.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            //upload the task
                @Override
                public void onSuccess(UploadTask.TaskSnapshot Task) {
                //get the download URL for the image uploaed
                    DBref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            progressimagecirlce.dismiss();

                            //edit_txtdata has set the text inside

                            String TempImageQuestion_Dscr = edit_txtdata.getText().toString().trim();
                            String url = uri.toString();

                            uploadinfo imageUploadInfo = new uploadinfo(TempImageQuestion_Dscr, url);
                            String Imagekey_push_ID = Firedatabase_ref.push().getKey();
                            Firedatabase_ref.child(Imagekey_push_ID).setValue(imageUploadInfo);
                            Toast.makeText(Sangaku_Upload.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                        }


                        // Image uploaded successfully
                        // Dismiss dialog

                        // @SuppressWarnings("VisibleForTests")
                    });
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Error, Image not uploaded
                            progressimagecirlce.dismiss();
                            Toast
                                    .makeText(Sangaku_Upload.this,
                                            "The upload is failed" + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot minitask) {
                                    double progress
                                            = (100.0
                                            * minitask.getBytesTransferred()
                                            / minitask.getTotalByteCount());
                                    progressimagecirlce.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
                                }
                            });
        }
        else{
            Toast
                    .makeText(Sangaku_Upload.this,
                            "Please Select a image and input text",
                            Toast.LENGTH_SHORT)
                    .show();
        }

    }
    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void detectimage() throws IOException {

        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
        TextRecognizer imagedect =
                TextRecognition.getClient(new JapaneseTextRecognizerOptions.Builder().build());

        //input the image and use to upload to recognize
        InputImage imageupload = InputImage.fromBitmap(bitmap, 0);

        //image for detect
        Task<Text> result =
                imagedect.process(imageupload)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text engtranslationtxt) {
                                // Task completed successfully
                                // ...

                                //process(result);

                                Toast
                                        .makeText(Sangaku_Upload.this,
                                                "Success recognize" ,
                                                Toast.LENGTH_SHORT)
                                        .show();
                                //if this is success, it will go to translate english function
                                convert_to_english(engtranslationtxt);
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                        Toast
                                                .makeText(Sangaku_Upload.this,
                                                        "Failed to translate " + e.getMessage(),
                                                        Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                });





    }

    private void convert_to_english(Text engtranslationtxt) {

        String result_Text_from_image = engtranslationtxt.getText();
        edit_txtdata.setText(result_Text_from_image);
    }

    private void translatetoJP() throws IOException {

    }


}