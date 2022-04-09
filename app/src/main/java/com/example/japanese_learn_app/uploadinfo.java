package com.example.japanese_learn_app;

public class uploadinfo {
    public String imageName;
    public String imageURL;
    public uploadinfo(){}

    public uploadinfo(String picQuestion, String image_link) {
        this.imageName = picQuestion;
        this.imageURL = image_link;
    }

    public String getImageName() {
        return imageName;
    }
    public String getImageURL() {
        return imageURL;
    }
}
