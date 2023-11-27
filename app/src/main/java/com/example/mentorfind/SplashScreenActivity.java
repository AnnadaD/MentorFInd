package com.example.mentorfind;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageView=findViewById(R.id.imageView);
        ObjectAnimator fadeout= ObjectAnimator.ofFloat(imageView,"alpha",1,0.3f);
        fadeout.setDuration(2200);
        ObjectAnimator fadeIn= ObjectAnimator.ofFloat(imageView,"alpha",0.3f,1);
        fadeIn.setDuration(2200);

        AnimatorSet mAnimatorSet=new AnimatorSet();
        mAnimatorSet.play(fadeIn).after(fadeout);
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationEnd(animation);
                mAnimatorSet.start();
            }
        });
        mAnimatorSet.start();

        new CountDownTimer(3000,1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent=new Intent(SplashScreenActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        }.start();
    }
}