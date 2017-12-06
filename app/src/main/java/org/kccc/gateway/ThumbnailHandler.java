package org.kccc.gateway;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Created by LG on 2016-09-28.
 * 섬네일을 강제로 가져오는 클래스
 */

public class ThumbnailHandler {
    private Context context;
    public ThumbnailHandler(Context context) {
        this.context = context;
    }

    public int setThumbnail(ContentsVO contentsVO){
            String mDrawableName = "category_" +
                    (contentsVO.getCategory() + 1) + "_" + contentsVO.getIndex();
            int resID = context.getResources().getIdentifier(mDrawableName , "drawable", context.getPackageName());
            return resID;
    }
    public Drawable setThumbnail(int category, int position){
        String mDrawableName = "category_" +
                (category + 1) + "_" + position;
        int resID = context.getResources().getIdentifier(mDrawableName , "drawable", context.getPackageName());
        return context.getResources().getDrawable(resID);
    }
    public Bitmap getBitmapThumbnail(ContentsVO contentsVO){

        int resourceID = new ThumbnailHandler(context).setThumbnail(contentsVO);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceID);
        int height = (bitmap.getHeight() * 256 / bitmap.getWidth());
        Bitmap scale = Bitmap.createScaledBitmap(bitmap, 256, height, true);

        return scale;
    }
    public Bitmap getBitmapThumbnail(ContentsVO contentsVO,int width){

        int resourceID = new ThumbnailHandler(context).setThumbnail(contentsVO);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceID);
        int height = (bitmap.getHeight() * width / bitmap.getWidth());
        Bitmap scale = Bitmap.createScaledBitmap(bitmap, width, height, true);

        return scale;
    }



//    public Drawable setThumbnail(int category, int position){
//        if(category == 0) {
//
//            ArrayList<Integer> list = new ArrayList<Integer>();
//            list.add(R.drawable.category_1_1);
//            list.add(R.drawable.category_1_2);
//            list.add(R.drawable.category_1_3);
//            list.add(R.drawable.category_1_4);
//            list.add(R.drawable.category_1_5);
//            list.add(R.drawable.category_1_6);
//            list.add(R.drawable.category_1_7);
//            list.add(R.drawable.category_1_8);
//            list.add(R.drawable.category_1_9);
//            list.add(R.drawable.category_1_10);
//            list.add(R.drawable.category_1_11);
//            list.add(R.drawable.category_1_12);
//            list.add(R.drawable.category_1_13);
//            list.add(R.drawable.category_1_14);
//
//            return context.getResources().getDrawable(list.get(position-1));
////            return context.getResources().getDrawable(0);
//        }
//
//        else if(category == 1) {
//            ArrayList<Integer> list = new ArrayList<Integer>();
//
//            list.add(R.drawable.category_2_1);
//            list.add(R.drawable.category_2_2);
//            list.add(R.drawable.category_2_3);
//            list.add(R.drawable.category_2_4);
//            list.add(R.drawable.category_2_5);
//            list.add(R.drawable.category_2_6);
//            list.add(R.drawable.category_2_7);
//            list.add(R.drawable.category_2_8);
//            list.add(R.drawable.category_2_9);
//
//            return context.getResources().getDrawable(list.get(position-1));
//        }
//
//        else if(category == 2) {
//            ArrayList<Integer> list = new ArrayList<Integer>();
//
//            list.add(R.drawable.category_3_1);
//            list.add(R.drawable.category_3_2);
//            list.add(R.drawable.category_3_3);
//            list.add(R.drawable.category_3_4);
//
//            return context.getResources().getDrawable(list.get(position-1));
//        }
//
//        else if(category == 3) {
//            ArrayList<Integer> list = new ArrayList<Integer>();
//
//            list.add(R.drawable.category_4_1);
//            list.add(R.drawable.category_4_2);
//            list.add(R.drawable.category_4_3);
//
//            return context.getResources().getDrawable(list.get(position-1));
//        }
//
//        else if(category == 4) {
//            ArrayList<Integer> list = new ArrayList<Integer>();
//
//            list.add(R.drawable.category_5_1);
//            list.add(R.drawable.category_5_2);
//            list.add(R.drawable.category_5_3);
//
//            return context.getResources().getDrawable(list.get(position-1));
//        }
//        return null;
//    }
}
