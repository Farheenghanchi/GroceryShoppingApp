package com.farheen.grossary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.farheen.grossary.lottie_anim.LottieFontViewGroup;


/**
 * Created by Farheen on 05/03/2017.
 */

public class SplashScreen extends AppCompatActivity {
    LottieFontViewGroup fontView;
    ScrollView scrollView;

    private final ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            scrollView.fullScroll(View.FOCUS_DOWN);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        fontView = (LottieFontViewGroup) findViewById(R.id.font_view);
//        fontView.getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
    }


    @Override
    protected void onDestroy() {
//        fontView.getViewTreeObserver().removeOnGlobalLayoutListener(layoutListener);
        super.onDestroy();
    }

}
