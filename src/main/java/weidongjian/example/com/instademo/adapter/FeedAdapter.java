package weidongjian.example.com.instademo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import weidongjian.example.com.instademo.R;

/**
 * Created by Administrator on 2015/5/15.
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int itemCount = 10;
    private Context context;

    public FeedAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_feed, null);
        FeedViewHolder holder = new FeedViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FeedViewHolder feedHolder = (FeedViewHolder) holder;
        feedHolder.imageView.setImageResource(R.drawable.img_feed_center_1);
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public FeedViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.image);
        }
    }
}
