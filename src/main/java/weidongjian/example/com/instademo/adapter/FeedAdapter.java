package weidongjian.example.com.instademo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import weidongjian.example.com.instademo.R;

/**
 * Created by Administrator on 2015/5/15.
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private int itemCount = 15;
    private Context context;
    private OnFeedItemClickListener onFeedItemClickListener;

    public FeedAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("debug", "onCreateViewHolder viewType" + viewType);
        View view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false);
        FeedViewHolder holder = new FeedViewHolder(view);
        holder.ivLike.setOnClickListener(this);
        holder.ivComment.setOnClickListener(this);
        holder.ivMore.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Log.d("debug", "onBindViewHolder position" + position);
        FeedViewHolder feedHolder = (FeedViewHolder) holder;
        if ((position % 2) == 0) {
            feedHolder.imageView.setImageResource(R.drawable.img_feed_center_1);
        } else {
            feedHolder.imageView.setImageResource(R.drawable.img_feed_center_2);
        }
        feedHolder.tvName.setText("goPro " + position);
        feedHolder.ivLike.setTag(position);
        feedHolder.ivMore.setTag(position);
        feedHolder.ivComment.setTag(position);
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    @Override
    public void onClick(View v) {
        if (onFeedItemClickListener == null)
            return;

        switch (v.getId()) {
            case R.id.heart:
                onFeedItemClickListener.onLikeClick(v, (Integer) v.getTag());
                break;
            case R.id.more:
                onFeedItemClickListener.onMoreClick(v, (Integer) v.getTag());
                break;
            case R.id.comment:
                onFeedItemClickListener.onCommentClick(v, (Integer) v.getTag());
                break;
            case R.id.name:
                onFeedItemClickListener.onProfileClick(v, (Integer) v.getTag());
                break;
        }
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private ImageView ivLike, ivComment, ivMore;
        private TextView tvName;

        public FeedViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.image);
            ivLike = (ImageView) v.findViewById(R.id.heart);
            ivComment = (ImageView) v.findViewById(R.id.comment);
            ivMore = (ImageView) v.findViewById(R.id.more);
            tvName = (TextView) v.findViewById(R.id.name);
        }
    }

    public void setOnFeedItemClickListener(OnFeedItemClickListener listener) {
        this.onFeedItemClickListener = listener;
    }

    public interface OnFeedItemClickListener {
        void onCommentClick(View view, int position);

        void onLikeClick(View view, int position);

        void onProfileClick(View view, int position);

        void onMoreClick(View view, int position);
    }
}
