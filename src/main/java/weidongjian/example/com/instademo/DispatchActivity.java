package weidongjian.example.com.instademo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


/**
 * Created by Administrator on 2015/5/15.
 */
public class DispatchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goToMainActivity();
            }
        }, 300);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(DispatchActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
