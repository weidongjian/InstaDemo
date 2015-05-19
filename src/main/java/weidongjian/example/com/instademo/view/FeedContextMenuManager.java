package weidongjian.example.com.instademo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import weidongjian.example.com.instademo.utils;

/**
 * Created by Administrator on 2015/5/19.
 */
public class FeedContextMenuManager extends RecyclerView.OnScrollListener implements View.OnAttachStateChangeListener{
    private static FeedContextMenuManager instance;
    private FeedContextMenu menuView;
    private boolean isContextMenuMissing;

    public static FeedContextMenuManager getInstance() {
        if (instance == null) {
            instance = new FeedContextMenuManager();
        }
        return instance;
    }

    private FeedContextMenuManager() {
        isContextMenuMissing = true;
    }

    public void toggleContextMenuView(final View view) {
        if (menuView == null) {
            menuView = new FeedContextMenu(view.getContext());
            menuView.addOnAttachStateChangeListener(this);
            ((ViewGroup)view.getRootView().findViewById(android.R.id.content)).addView(menuView);
            isContextMenuMissing = false;
            menuView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    menuView.getViewTreeObserver().removeOnPreDrawListener(this);
                    setContextMenuPosition(view);
                    performShowAnim();
                    return false;
                }
            });
        }
    }

    private void setContextMenuPosition(View openingView) {
        int[] positions = new int[2];
        openingView.getLocationOnScreen(positions);
        Log.d("debug", "width " + openingView.getWidth() + " height" + openingView.getHeight());
        int margin = (int) utils.dpToPx(12);
        menuView.setTranslationX(positions[0] - menuView.getWidth()/3);
        menuView.setTranslationY(positions[1] - menuView.getHeight() - margin);
    }

    private void performShowAnim() {
        menuView.setScaleX(0.1f);
        menuView.setScaleY(0.1f);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(menuView, "scaleX", 1f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(menuView, "scaleY", 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator1, animator2);
        animatorSet.setDuration(150);
        animatorSet.setInterpolator(new OvershootInterpolator());
        animatorSet.start();
    }

    private void performHideAnim() {
        if (isContextMenuMissing)
            return;
        isContextMenuMissing = true;
        menuView.setPivotX(menuView.getWidth()/2);
        menuView.setPivotY(menuView.getHeight());
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(menuView, "scaleX", 0.1f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(menuView, "scaleY", 0.1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator1, animator2);
        animatorSet.setDuration(150);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                menuView.dismiss();
            }
        });
        animatorSet.start();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (menuView != null){
            performHideAnim();
            menuView.setTranslationY(menuView.getTranslationY() - dy);
        }
    }

    @Override
    public void onViewAttachedToWindow(View v) {

    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        menuView = null;
    }
}
