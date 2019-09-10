package com.amit.photogram;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseInstanceIDSer";
    @Override
    public void onNewToken(@NonNull String s) {

        super.onNewToken(s);
        sendRegistrationToServer(s);

        Log.e("NEW_TOKEN",s);
    }


    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    public static void sendRegistrationToServer(String token) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            User user = new User(firebaseUser.getUid(), firebaseUser.getDisplayName(), token);
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            database.child("users").child(user.getUid()).setValue(user);
        }
    }
}
