package com.example.twittersearch.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.twittersearch.R;
import com.example.twittersearch.data.model.Tweet;
import com.example.twittersearch.databinding.ItemTweetBinding;
import com.example.twittersearch.managers.DateUtils;
import com.example.twittersearch.managers.UtilsManager;

import java.util.List;

public class SearchTweetsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Tweet> tweetList;

    SearchTweetsAdapter(Context context, List<Tweet> tweetList) {
        this.context = context;
        this.tweetList = tweetList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.item_tweet, parent, false);
        return new TweetViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bindTweet((TweetViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }

    private void bindTweet(TweetViewHolder holder, int position) {
        Tweet tweet = tweetList.get(position);
        tweet.setCreatedAt(DateUtils.formatDatePerPattern(tweet.getCreatedAt()));
        holder.binder.setTweet(tweet);
        Glide.with(context)
                .load(tweet.getUser().getUserAvatar())
                .apply(RequestOptions.circleCropTransform())
                .placeholder(UtilsManager.createCircularProgressDrawable(context))
                .into(holder.binder.avatar);
    }

    static class TweetViewHolder extends RecyclerView.ViewHolder {

        ItemTweetBinding binder;

        TweetViewHolder(@NonNull View itemView) {
            super(itemView);
            binder = DataBindingUtil.bind(itemView);
        }
    }
}
