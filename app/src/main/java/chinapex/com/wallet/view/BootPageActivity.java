package chinapex.com.wallet.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import chinapex.com.wallet.R;
import chinapex.com.wallet.base.BaseActivity;
import chinapex.com.wallet.global.ApexWalletApplication;
import chinapex.com.wallet.global.Constant;
import chinapex.com.wallet.utils.CpLog;
import chinapex.com.wallet.utils.SharedPreferencesUtils;

public class BootPageActivity extends BaseActivity {

    private static final String TAG = BootPageActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置透明导航键
        setNavigationBarColorTransparent();
        setContentView(R.layout.activity_boot_page);
        initView();
    }

    private void initView() {
        ImageView iv_boot_page = (ImageView) findViewById(R.id.iv_boot_page);
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv_boot_page, "alpha", 0, 1, 1);
        animator.setDuration(3000);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isFirstEnter();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void setNavigationBarColorTransparent() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

    }

    private void isFirstEnter() {
        boolean isFirstEnter = (boolean) SharedPreferencesUtils.getParam(ApexWalletApplication
                .getInstance(), Constant.IS_FIRST_ENTER_APP, true);
        if (isFirstEnter) {
            CpLog.i(TAG, "this is first enter!");
            SharedPreferencesUtils.putParam(ApexWalletApplication.getInstance(), Constant
                    .IS_FIRST_ENTER_APP, false);
            startActivity(NewVisitorActivity.class, true);
        } else {
            startActivity(MainActivity.class, true);
        }
    }

}
