package org.kccc.gateway;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cn.jzvd.JZVideoPlayerStandard;
import io.realm.Realm;

/**
 * 这里可以监听到视频播放的生命周期和播放状态
 * 所有关于视频的逻辑都应该写在这里
 * Created by Nathen on 2017/7/2.
 */

public class MyJZVideoPlayerStandard extends JZVideoPlayerStandard {
    ContentsVO contentsVO;
    public MyJZVideoPlayerStandard(Context context) {
        super(context);
    }

    public MyJZVideoPlayerStandard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void startVideo() {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final ContentsVO realmContentsVO = realm.where(ContentsVO.class)
                .equalTo("category",contentsVO.getCategory())
                .equalTo("index",contentsVO.getIndex())
                .findFirst();
        realmContentsVO.setHistory(1);
        realmContentsVO.setHistoryDate(System.currentTimeMillis());
        realm.commitTransaction();
        realm.close();

        super.startVideo();
    }

    @Override
    public void setUp(Object[] dataSourceObjects, int defaultUrlMapIndex, int screen, Object... objects) {
        contentsVO = (ContentsVO)objects[0];
        super.setUp(dataSourceObjects, defaultUrlMapIndex, screen);
    }
}
