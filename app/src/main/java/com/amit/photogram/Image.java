package com.amit.photogram;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

@IgnoreExtraProperties
public class Image {
    String key;
    String userId;
    String downloadUrl;

    private Object timeStamp;

    // these properties will not be saved to the database
    @Exclude
    User user;

    @Exclude
    int likes = 0;

    @Exclude
    boolean hasLiked = false;

    @Exclude
    String userLike;

    public Image() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    Image(String key, String userId, String downloadUrl) {
        this.key = key;
        this.userId = userId;
        this.downloadUrl = downloadUrl;

        timeStamp = ServerValue.TIMESTAMP;

    }

    void addLike() {
        this.likes++;
    }

    void removeLike() {
        this.likes--;
    }

    public String getUserId() {
        return userId;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public User getUser() {
        return user;
    }

    public int getLikes() {
        return likes;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public String getKey() {
        return key;
    }
}
