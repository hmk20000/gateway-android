package org.kccc.gateway;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by LG on 2016-08-08.
 * 이미지 슬라이드를 통해서 볼 수 있는 이미지를 설정하는 클래스
 */
public class ImagePagerAdapter extends PagerAdapter implements View.OnClickListener{

    private Context context;
    private LayoutInflater mLayoutInflater;
    private int flag;
    private int[] PForU = {
            R.drawable.p_1,
            R.drawable.p_2,
            R.drawable.p_3,
            R.drawable.p_4,
            R.drawable.p_5,
            R.drawable.p_6,
            R.drawable.p_7,
            R.drawable.p_8,
            R.drawable.p_9,
            R.drawable.p_10,
            R.drawable.p_11,
            R.drawable.p_12,
            R.drawable.p_13,
            R.drawable.p_14,
            R.drawable.p_15,
            R.drawable.p_16,
            R.drawable.p_17,
            R.drawable.p_18,
            R.drawable.p_19,
            R.drawable.p_20,
            R.drawable.p_21,
            R.drawable.p_22,
            R.drawable.p_23
    };
    private int[] Guide = {
            R.drawable.guide_1,
            R.drawable.guide_2,
            R.drawable.guide_3,
            R.drawable.guide_4,
            R.drawable.guide_5
    };
    public ImagePagerAdapter(Context context, int flag) {
        this.flag = flag;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(flag == 1 || flag == 2)
            return PForU.length;
        else
            return Guide.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.item_image_pager, container, false);

        Button button = (Button) itemView.findViewById(R.id.button);
        button.setOnClickListener(this);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        if(flag == 1 || flag == 2)
            imageView.setImageResource(PForU[position]);
        else {
            imageView.setImageResource(Guide[position]);
            if(position == Guide.length-1){
                button.setVisibility(View.VISIBLE);
            }
        }
        container.addView(itemView);

        SharedPreferences isBackPref = context.getSharedPreferences("isBack", context.MODE_PRIVATE);
        SharedPreferences.Editor isBackEditor = isBackPref.edit();
        isBackEditor.putInt("isBack", 1);
        isBackEditor.commit();

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }

    @Override
    public void onClick(View v) {
        ((ImageSlideActivity)context).finish();
    }
}