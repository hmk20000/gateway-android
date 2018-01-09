package org.kccc.gateway;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by LG on 2016-08-08.
 * 어플 사용설명서나 사영리 같은 이미지를 슬라이드 해서 봐야 하는 경우에 볼 수 있도록 하는 클래스
 */
public class ImageSlideActivity extends AppCompatActivity {
    private ImagePagerAdapter mImagePagerAdapter;
    private ViewPager mViewPager;
    private int flag;
    private int isBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slide);

        SharedPreferences orientationPref = getSharedPreferences("orientationPref", MODE_PRIVATE);
        flag = orientationPref.getInt("flag", 0);
        if(flag == 1 || flag == 2)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // 가로전환
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 세로전환

        mImagePagerAdapter = new ImagePagerAdapter(this, flag);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mImagePagerAdapter);
    }


    public void onBackPressed() {
        // TODO Auto-generated method stub

        SharedPreferences isBackPref = getSharedPreferences("isBack", MODE_PRIVATE);
        isBack = isBackPref.getInt("isBack", 0);
        if(isBack == 1) {
            super.onBackPressed();
            if(flag==2){
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("Home_Category", false);
                startActivity(intent);
            }
        }
    }
}
