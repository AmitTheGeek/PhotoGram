package com.amit.photogram;


import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class CommentDetailActivity extends AppCompatActivity {
    private static final String TAG = "CommentDetailActivity";
    ImageView image;
    TextView userName;
    EditText editTextComment;
    Button btnAddComment;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    RecyclerView RvComment;
    CommentAdapter commentAdapter;
    List<Comment> listComment;
    static String COMMENT_KEY = "Comment" ;
    DatabaseReference database;
    private String imageUrl;
    private String imageKey;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);


        // let's set the statue bar to transparent
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getSupportActionBar().hide();

        // ini Views
        RvComment = findViewById(R.id.rv_comment);
        image =findViewById(R.id.image);

        userName = findViewById(R.id.user_name);

        editTextComment = findViewById(R.id.detail_comment);
        btnAddComment = findViewById(R.id.add_comment_btn);
        listComment = new ArrayList<>();

        RvComment.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(getApplicationContext(),listComment);
        RvComment.setAdapter(commentAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        database = FirebaseDatabase.getInstance().getReference();


        imageUrl = getIntent().getExtras().getString("downloadUrl") ;
        imageKey = getIntent().getExtras().getString("imageKey") ;
        Glide.with(this).load(imageUrl).into(image);

        String displayName = getIntent().getExtras().getString("displayName");
        userName.setText(displayName);

        // add Comment button click listner

        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnAddComment.setVisibility(View.INVISIBLE);
                String key = database.child(COMMENT_KEY).push().getKey();
                String text = editTextComment.getText().toString();
                String uid = firebaseUser.getUid();
                String uname = firebaseUser.getDisplayName();
                final Comment comment = new Comment(text,uid,imageUrl,uname);

                database.child(COMMENT_KEY).child(key).setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showMessage("comment added");
                        editTextComment.setText("");
                        btnAddComment.setVisibility(View.VISIBLE);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("fail to add comment : "+e.getMessage());
                    }
                });



            }
        });

        // ini Recyclerview Comment
        iniRvComment();

    }

    private void iniRvComment() {

        Query commentsQuery = database.child(COMMENT_KEY).orderByKey().limitToFirst(100);

        commentsQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final Comment comment =  dataSnapshot.getValue(Comment.class);
                if(imageUrl.equals(comment.getImageUrl()))
                    commentAdapter.addComment(comment);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    private void showMessage(String message) {

        Toast.makeText(this,message, Toast.LENGTH_LONG).show();

    }
}
