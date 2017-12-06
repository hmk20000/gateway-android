package org.kccc.gateway;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by ming on 2017. 3. 30..
 * 애니메이션 효과를 위한 클래스
 */

public class MyItemAnimator {
    //위로 올라오면서 보이게 하는 애니매이션 효과
    public void setFadeAnimation(View view){

        /*ObjectAnimator move=ObjectAnimator.ofFloat(view, "translationY",100f);
        move.setDuration(500);
        ObjectAnimator alpha1=ObjectAnimator.ofFloat(view, "alpha",0.5f);
        alpha1.setDuration(500);

        ObjectAnimator alpha2= ObjectAnimator.ofFloat(view, "alpha",0);
        alpha2.setDuration(2000);
        AnimatorSet animset=new AnimatorSet();
        animset.play(alpha2).before(alpha1).with(move);*/

        ObjectAnimator move=ObjectAnimator.ofFloat(view, "translationY",100f,0f);
        move.setDuration(500);

        ObjectAnimator alpha=ObjectAnimator.ofFloat(view, "alpha",0f,1f);
        alpha.setDuration(500);

        AnimatorSet animset=new AnimatorSet();
        animset.play(move).with(alpha);

        animset.start();
    }
}