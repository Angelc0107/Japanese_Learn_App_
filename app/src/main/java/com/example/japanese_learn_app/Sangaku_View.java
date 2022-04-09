package com.example.japanese_learn_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Sangaku_View extends AppCompatActivity {

    //progress bar
    private ProgressBar progress_circle_bar;
    //Recycler view for the recycler adapter
    RecyclerView  RecView;
    // firedatabase reference

    private DatabaseReference fireDB;
    //upload info variavle
    private ArrayList<uploadinfo> uploadinfro_list;

    //recyclerAdapter;
    //private recycler_adapter RecViewAdapter;
    //image adapater
    private ImageAdapterView recViewimageadapter;
    //firedatabase reference
    private DatabaseReference firedatabaseDB;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sangaku_view);

        RecView = findViewById(R.id.recycle_view);

        //https://www.kurims.kyoto-u.ac.jp/~okamoto/sangaku/sangaku.html
        //set the
        progress_circle_bar = findViewById(R.id.pro_circle);
        //loading circle for the user interface

        //set the managemet layout
        RecView.setHasFixedSize(true);
        LinearLayoutManager layout_management_manager= new LinearLayoutManager(this);
        RecView.setLayoutManager(layout_management_manager);

        uploadinfro_list = new ArrayList<>();

        //connect with the firedatabse
        // retrieve the image and the opload link which is the image link
        String uploadlink = "upload";
        //StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        //DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        String image_link = "Images";
        firedatabaseDB = FirebaseDatabase.getInstance().getReference(image_link);
        //mdatabseRef = FirebaseDatabase.getInstance().getReference();
        firedatabaseDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot minitask) {

                for(DataSnapshot datatask :minitask.getChildren()){
                    //messages message = new messages();
                    //message.setImageURL(postsnapshot.child("imageURL").getValue().toString());
                    //message.setImageName(postsnapshot.child("imageName").getValue().toString());
                    //displaylist.add(message);
                    uploadinfo upload =  datatask.getValue(uploadinfo.class);
                    uploadinfro_list.add(upload);
                }
                recViewimageadapter = new ImageAdapterView(Sangaku_View.this,uploadinfro_list);

               //set adapter to recview imaage adapter
                RecView.setAdapter(recViewimageadapter);
                progress_circle_bar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Sangaku_View.this,"The databse read failed",Toast.LENGTH_SHORT).show();

            }
        });
        //Firebase
        //myfer = FirebaseDatabase.getInstance().getReference();
        //get data
        //displaylist = new ArrayList<>();
        //clear array list
        //ClearAll();
        //get data method
       // GetDataFromFirebase();
        //private Storage


    }



    /*
    private void ClearAll() {
       t.clear();

          notifyDataSetChanged();
            }
        }

        displaylist = new ArrayList<>();
    }*/
}