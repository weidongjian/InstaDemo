package weidongjian.example.com.instademo.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import weidongjian.example.com.instademo.R;
import weidongjian.example.com.instademo.utils;

/**
 * Created by Administrator on 2015/5/19.
 */
public class FeedContextMenu extends LinearLayout {
    private static final int LAYOUT_WIDTH = (int) utils.dpToPx(240);

    public FeedContextMenu(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_context_menu, this, true);
        setOrientation(VERTICAL);
        setBackgroundResource(R.drawable.bg_container_shadow);
        setLayoutParams(new ViewGroup.LayoutParams(LAYOUT_WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void dismiss() {
        ((ViewGroup)getParent()).removeView(FeedContextMenu.this);
    }
}
