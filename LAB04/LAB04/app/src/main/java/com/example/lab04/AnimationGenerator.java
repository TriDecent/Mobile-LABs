package com.example.lab04;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class AnimationGenerator implements IAnimationGenerator {
    public Animation getFadeIn() {
        var animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(3000);
        animation.setFillAfter(true);

        return animation;
    }

    public Animation getFadeOut() {
        var animation = new AlphaAnimation(1f, 0f);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        return animation;
    }

    public Animation getBlink() {
        var animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }

    public Animation getZoomIn() {
        var animation = new ScaleAnimation(1f, 3f, 1f, 3f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        return animation;
    }

    public Animation getZoomOut() {
        var animation = new ScaleAnimation(1f, 0.5f, 1f, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        return animation;
    }

    public Animation getRotate() {
        var animation = new RotateAnimation(0, 1080,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        animation.setDuration(3000);
        animation.setFillAfter(true);
        return animation;
    }

    public Animation getMove() {
        var animation = new TranslateAnimation(0, 1080, 0, 0);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        return animation;
    }

    public Animation getSlideUp() {
        var animation = new TranslateAnimation(0, 0, 500, 0);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        return animation;
    }

    public Animation getBounce() {
        var animation = new ScaleAnimation(1f, 0.5f, 1f, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        animation.setDuration(500);
        animation.setInterpolator(new BounceInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        return animation;
    }

    public Animation getCombine() {
        var set = new AnimationSet(true);
        set.setInterpolator(new LinearInterpolator());

        var scaleAnimation = new ScaleAnimation(1f, 3f, 1f, 3f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(4000);

        var rotateAnimation = new RotateAnimation(0, 1080,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setDuration(4000);
        rotateAnimation.setRepeatCount(2);

        set.addAnimation(scaleAnimation);
        set.addAnimation(rotateAnimation);
        set.setFillAfter(true);

        return set;
    }
}
