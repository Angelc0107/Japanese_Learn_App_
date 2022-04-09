package com.example.japanese_learn_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapterView extends RecyclerView.Adapter<ImageAdapterView.ImageViewHolder> {
    private Context cont;
    private List<uploadinfo> uploadinfo_list;

    //costruct a struct
    public ImageAdapterView(Context cont_from_dB, List<uploadinfo> ups){
        cont = cont_from_dB;
        uploadinfo_list=ups;
    }


    @NonNull
    @Override
    //create the view --> merge the view into the dis_item
    //using dis-item rec view, then add into the view layout
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(cont).inflate(R.layout.dis_item,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int pos) {
        //hold the uploadlisy

        uploadinfo display_current = uploadinfo_list.get(pos);
        String txt = display_current.getImageName();
        // then add the image and text name to the gallery
        holder.textViewQuestion.setText(txt);
        //Picasso function may have error
        //if there is loading, it will use ic_launcher
        Picasso.get().load(display_current.getImageURL()).placeholder(R.mipmap.ic_launcher)
                .fit().into(holder.quest_imageView);

        //center crop --> may more good

    }

    @Override
    //to get the total size of the item from the uploadinfo
    public int getItemCount() {
        return uploadinfo_list.size();
    }

    //set the text and image to the textview name and image
    public  class ImageViewHolder extends  RecyclerView.ViewHolder{
        public TextView textViewQuestion;
        public ImageView quest_imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
        //find the text element
            textViewQuestion = itemView.findViewById(R.id.text_view_name);
            quest_imageView  =  itemView.findViewById(R.id.image_view_name);

        }
    }

}
