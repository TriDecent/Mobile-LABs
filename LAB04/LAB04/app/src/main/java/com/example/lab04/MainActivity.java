package com.example.lab04;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnFadeInXml, btnFadeInCode,
            btnFadeOutXml, btnFadeOutCode,
            btnBlinkXml, btnBlinkCode,
            btnZoomInXml, btnZoomInCode,
            btnZoomOutXml, btnZoomOutCode,
            btnRotateXml, btnRotateCode,
            btnMoveXml, btnMoveCode,
            btnSlideUpXml, btnSlideUpCode,
            btnBounceXml, btnBounceCode,
            btnCombineXml, btnCombineCode;

    private ImageView ivUitLogo;
    private Animation.AnimationListener animationListener;

    private final IAnimationGenerator animationGenerator = new AnimationGenerator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViewReferences();
        setupAnimationListener();

        setupAnimationForButtons();
    }

    private void setupAnimationForButtons() {
        initializeXmlAnimationsForButtons();
        initializeCodeAnimationsForButtons();
    }

    private void initializeXmlAnimationsForButtons() {
        setupButtonWithXMLAnimation(btnFadeInXml, R.anim.anim_fade_in);
        setupButtonWithXMLAnimation(btnFadeOutXml, R.anim.anim_fade_out);
        setupButtonWithXMLAnimation(btnBlinkXml, R.anim.anim_blink);
        setupButtonWithXMLAnimation(btnZoomInXml, R.anim.anim_zoom_in);
        setupButtonWithXMLAnimation(btnZoomOutXml, R.anim.anim_zoom_out);
        setupButtonWithXMLAnimation(btnRotateXml, R.anim.anim_rotate);
        setupButtonWithXMLAnimation(btnMoveXml, R.anim.anim_move);
        setupButtonWithXMLAnimation(btnSlideUpXml, R.anim.anim_slide_up);
        setupButtonWithXMLAnimation(btnBounceXml, R.anim.anim_bounce);
        setupButtonWithXMLAnimation(btnCombineXml, R.anim.anim_combine);
    }

    private void initializeCodeAnimationsForButtons() {
        setupButtonWithCodeAnimation(btnFadeInCode, animationGenerator.getFadeIn());
        setupButtonWithCodeAnimation(btnFadeOutCode, animationGenerator.getFadeOut());
        setupButtonWithCodeAnimation(btnBlinkCode, animationGenerator.getBlink());
        setupButtonWithCodeAnimation(btnZoomInCode, animationGenerator.getZoomIn());
        setupButtonWithCodeAnimation(btnZoomOutCode, animationGenerator.getZoomOut());
        setupButtonWithCodeAnimation(btnRotateCode, animationGenerator.getRotate());
        setupButtonWithCodeAnimation(btnMoveCode, animationGenerator.getMove());
        setupButtonWithCodeAnimation(btnSlideUpCode, animationGenerator.getSlideUp());
        setupButtonWithCodeAnimation(btnBounceCode, animationGenerator.getBounce());
        setupButtonWithCodeAnimation(btnCombineCode, animationGenerator.getCombine());
    }

    private void initializeViewReferences() {
        ivUitLogo = findViewById(R.id.iv_uit_logo);
        btnFadeInXml = findViewById(R.id.btn_fade_in_xml);
        btnFadeInCode = findViewById(R.id.btn_fade_in_code);
        btnFadeOutXml = findViewById(R.id.btn_fade_out_xml);
        btnFadeOutCode = findViewById(R.id.btn_fade_out_code);
        btnBlinkXml = findViewById(R.id.btn_blink_xml);
        btnBlinkCode = findViewById(R.id.btn_blink_code);
        btnZoomInXml = findViewById(R.id.btn_zoom_in_xml);
        btnZoomInCode = findViewById(R.id.btn_zoom_in_code);
        btnZoomOutXml = findViewById(R.id.btn_zoom_out_xml);
        btnZoomOutCode = findViewById(R.id.btn_zoom_out_code);
        btnRotateXml = findViewById(R.id.btn_rotate_xml);
        btnRotateCode = findViewById(R.id.btn_rotate_code);
        btnMoveXml = findViewById(R.id.btn_move_xml);
        btnMoveCode = findViewById(R.id.btn_move_code);
        btnSlideUpXml = findViewById(R.id.btn_slide_up_xml);
        btnSlideUpCode = findViewById(R.id.btn_slide_up_code);
        btnBounceXml = findViewById(R.id.btn_bounce_xml);
        btnBounceCode = findViewById(R.id.btn_bounce_code);
        btnCombineXml = findViewById(R.id.btn_combine_xml);
        btnCombineCode = findViewById(R.id.btn_combine_code);
    }

    private void setupButtonWithXMLAnimation(Button button, int animationId) {
        final var loadedAnimation = AnimationUtils.loadAnimation(this, animationId);

        loadedAnimation.setAnimationListener(animationListener);

        button.setOnClickListener(v -> ivUitLogo.startAnimation(loadedAnimation));
    }

    private void setupAnimationListener() {
        animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(getApplicationContext(), "Animation Ended",
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

    private void setupButtonWithCodeAnimation(Button button, Animation animation) {
        animation.setAnimationListener(animationListener);

        button.setOnClickListener(v -> ivUitLogo.startAnimation(animation));
    }
}

