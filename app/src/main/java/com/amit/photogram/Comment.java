package com.amit.photogram;


import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

@IgnoreExtraProperties
public class Comment {

    private String text;
    private String userId;
    private String imageUrl;
    private Object timeStamp;

    private String userName;

    public Comment(){

    }

    public Comment(String text, String userId, String imageKey, String userName){
        this.userId = userId;
        this.text = text;
        this.imageUrl = imageKey;
        this.userName = userName;

        timeStamp = ServerValue.TIMESTAMP;
    }

    public String getText() {
        return text;
    }

    public String getUserId() {
        return userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserName() {
        return userName;
    }

}
