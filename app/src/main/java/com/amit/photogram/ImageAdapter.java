package com.amit.photogram;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private static final String TAG = "ImageAdapter";
    private ArrayList<Image> mDataset;
    private FeedActivity mActivity;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView mImageView;
        public Button mLikeButton;
        public Button mCommentButton;

        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.textView2);
            mImageView = v.findViewById(R.id.imageView);
            mLikeButton = v.findViewById(R.id.likeButton);
            mCommentButton = v.findViewById(R.id.commentButton);
        }

    }

    public ImageAdapter(ArrayList<Image> myDataset, FeedActivity activity) {
        this.mDataset = myDataset;
        this.mActivity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_view, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Image image = (Image) mDataset.get(position);
        if (image.user != null) {
            holder.mTextView.setText(image.user.getDisplayName());
        }

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
        Glide.with(mActivity).load(image.downloadUrl).apply(options).into(holder.mImageView);


        holder.mLikeButton.setText("Like (" + image.likes + ")");
        if(image.hasLiked) {
            holder.mLikeButton.setBackgroundColor(mActivity.getResources().getColor(R.color.colorAccent));
        } else {
            holder.mLikeButton.setBackgroundColor(mActivity.getResources().getColor(R.color.colorPrimary));
        }

        holder.mCommentButton.setBackgroundColor(mActivity.getResources().getColor(R.color.colorPrimary));
        holder.mCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent commentDetailActivity = new Intent(ImageAdapter.this.mActivity, CommentDetailActivity.class);
                commentDetailActivity.putExtra("downloadUrl", mDataset.get(position).getDownloadUrl());
                commentDetailActivity.putExtra("displayName",mDataset.get(position).getUser().getDisplayName());
                commentDetailActivity.putExtra("userId",mDataset.get(position).getUser().getUid());
                commentDetailActivity.putExtra("imageKey",mDataset.get(position).getKey());
                mActivity.startActivity(commentDetailActivity);
            }
        });

        holder.mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.setLiked(image);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addImage(Image image) {
        mDataset.add(0, image);
        notifyDataSetChanged();
    }
}