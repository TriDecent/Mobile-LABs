package com.example.lab04;

import android.view.animation.Animation;

public interface IAnimationGenerator {
    Animation getFadeIn();

    Animation getFadeOut();

    Animation getBlink();

    Animation getZoomIn();

    Animation getZoomOut();

    Animation getRotate();

    Animation getMove();

    Animation getSlideUp();

    Animation getBounce();

    Animation getCombine();
}
