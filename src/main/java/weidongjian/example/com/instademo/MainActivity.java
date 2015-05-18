package weidongjian.example.com.instademo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

import weidongjian.example.com.instademo.adapter.FeedAdapter;


public class MainActivity extends BaseActivity {
    private Toolbar toolbar;
    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;
    private RecyclerView rvFeed;
    private ImageButton btnCreate;
    private ImageView ivLogo;
    private Context context;
    private View popupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        context = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white);

        init();
    }

    private void init() {
        rvFeed = (RecyclerView) findViewById(R.id.rvFeed);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this){
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);
        FeedAdapter adapter = new FeedAdapter(this);
        rvFeed.setAdapter(adapter);
        btnCreate = (ImageButton) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        ivLogo = (ImageView) findViewById(R.id.ivLogo);
        adapter.setOnFeedItemClickListener(new FeedAdapter.OnFeedItemClickListener() {
            @Override
            public void onCommentClick(View view, int position) {
                int[] positions = new int[2];
                view.getLocationOnScreen(positions);
                Log.d("debug", "onCommentClick " + positions[0] + " " + positions[1]);
            }

            @Override
            public void onLikeClick(View view, int position) {
                Log.d("debug", "onLikeClick " + position);
            }

            @Override
            public void onProfileClick(View view, int position) {
                Log.d("debug", "onProfileClick " + position);
            }

            @Override
            public void onMoreClick(View view, int position) {
                int[] positions = new int[2];
                view.getLocationOnScreen(positions);
                popupView = LayoutInflater.from(context).inflate(R.layout.popup, null);
//                PopupWindow popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                popupWindow.showAtLocation(view, Gravity.TOP | Gravity.LEFT, positions[0], positions[1]);
//                popupWindow.setBackgroundDrawable(new ColorDrawable(0));
//                animPopupWindow(popupWindow.getContentView());
                ((ViewGroup)view.getRootView().findViewById(android.R.id.content)).addView(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupView.setTranslationX(positions[0]);
                popupView.setTranslationY(positions[1] - utils.dpToPx(100));
                animPopupWindow(popupView);
            }
        });
    }

    private void animPopupWindow(View view) {
        popupView.setScaleX(0.1f);
        popupView.setScaleY(0.1f);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "scaleX", 1f);
        animator1.setInterpolator(new OvershootInterpolator());
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(popupView, "scaleY", 1f);
        animator2.setInterpolator(new OvershootInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIM_DURATION_FAB);
//        animatorSet.setInterpolator(new OvershootInterpolator());
        animatorSet.playTogether(animator1, animator2);
        animatorSet.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        startIntroAnimation();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startIntroAnimation() {
        float actionbarSize = utils.dpToPx(56);
        btnCreate.setTranslationY(2 * actionbarSize);
        ivLogo.setTranslationY(-actionbarSize);
        toolbar.setTranslationY(-actionbarSize);
        rvFeed.setTranslationY(utils.getScreenHeight(MainActivity.this));

        animToolbar();
        animLogo();
        animRecyclerView();
    }


    private void animToolbar() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(toolbar, "translationY", 0f);
        animator.setDuration(ANIM_DURATION_TOOLBAR);
        animator.setStartDelay(300);
        animator.start();
    }

    private void animLogo() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(ivLogo, "translationY", 0f);
        animator.setDuration(ANIM_DURATION_TOOLBAR);
        animator.setStartDelay(400);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animCreateBtn();
            }
        });
    }

    private void animCreateBtn() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(btnCreate, "translationY", 0f);
        animator.setDuration(ANIM_DURATION_FAB);
        animator.setInterpolator(new OvershootInterpolator());
        animator.setStartDelay(300);
        animator.start();
    }

    private void animRecyclerView() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(rvFeed, "translationY", 0f);
        animator.setDuration(ANIM_DURATION_FAB);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(700);
        animator.start();
    }
}
