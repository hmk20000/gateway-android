package org.kccc.gateway;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Created by ming on 2017. 11. 28..
 */

public class RealmHelper {
    static int FAVORITE = 0;
    static int HISTORY = 1;
    static int DOWNLOAD = 2;
    private String[] fieldName = {"favorite","history","download"};

    public ContentsVO getContentsVO(int category, int index){
        Realm realm = Realm.getDefaultInstance();

        ContentsVO RealmContentsVO = realm.where(ContentsVO.class)
                .equalTo("category",category)
                .equalTo("index",index)
                .findFirst();
        ContentsVO rtnContentsVO = realm.copyFromRealm(RealmContentsVO);

        realm.close();

        return rtnContentsVO;
    }
    public boolean FirstTimeCheck(){
        boolean rtn = true;

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        AppInfoVO appInfoVO = realm.where(AppInfoVO.class).findFirst();

        if(appInfoVO == null){
            AppInfoVO appInfoVO1 = realm.createObject(AppInfoVO.class);
            appInfoVO1.setFirstTimeUse(1);

            rtn = true;
        }else {
            rtn = false;
        }
        realm.commitTransaction();
        realm.close();

        return rtn;
    }

    public void saveContents(ContentsVO contentsVO){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        ContentsVO RealmContentsVO = realm.where(ContentsVO.class)
                .equalTo("category",contentsVO.getCategory())
                .equalTo("index",contentsVO.getIndex())
                .findFirst();

        RealmContentsVO.setDownloadDate(contentsVO.getDownloadDate());
        RealmContentsVO.setDownload(contentsVO.getDownload());
        RealmContentsVO.setHistoryDate(contentsVO.getHistoryDate());
        RealmContentsVO.setHistory(contentsVO.getHistory());
        RealmContentsVO.setFavoriteDate(contentsVO.getFavoriteDate());
        RealmContentsVO.setFavorite(contentsVO.getFavorite());

        realm.commitTransaction();
        realm.close();
    }

    public void saveContentsList(Realm realm, ContentsVO[] contentsVOS){
        realm.beginTransaction();
        for(int i=0; i<contentsVOS.length; i++){
            ContentsVO realmcontentsVO = realm.copyToRealm(contentsVOS[i]);
//            Log.d("contents", "saveContentsList: "+contentsVOS[i].getTitle());
        }

        realm.commitTransaction();
    }
    public void clearContentsHelper(Realm realm){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(ContentsVO.class);
            }
        });
    }

    public List<ContentsVO> getMyVideos(int flag){

        List<ContentsVO> dbList = null;

        Realm realm = Realm.getDefaultInstance();
        RealmResults<ContentsVO> realmContentsVO = null;
        realmContentsVO = realm.where(ContentsVO.class)
                .equalTo(fieldName[flag],1)
                .findAll();
        dbList = realm.copyFromRealm(realmContentsVO);


        switch (flag){
            case 0:
                Collections.sort(dbList,new CompareFavorite());
                break;
            case 1:
                Collections.sort(dbList,new CompareHistory());
                break;
            case 2:
                Collections.sort(dbList,new CompareDownload());
                break;
        }


        realm.close();

        return dbList;
    }


    //    출처: http://kanzler.tistory.com/63 [kanzler의 세상 이야기]
    static class CompareDownload implements Comparator<ContentsVO> {

        @Override
        public int compare(ContentsVO o1, ContentsVO o2) {
            return o1.getDownloadDate() > o2.getDownloadDate() ? -1 : o1.getDownloadDate() < o2.getDownloadDate() ? 1:0;
        }
    }
    static class CompareHistory implements Comparator<ContentsVO> {

        @Override
        public int compare(ContentsVO o1, ContentsVO o2) {
            return o1.getHistoryDate() > o2.getHistoryDate() ? -1 : o1.getHistoryDate() < o2.getHistoryDate() ? 1:0;
        }
    }
    static class CompareFavorite implements Comparator<ContentsVO> {

        @Override
        public int compare(ContentsVO o1, ContentsVO o2) {
            return o1.getFavoriteDate() > o2.getFavoriteDate() ? -1 : o1.getFavoriteDate() < o2.getFavoriteDate() ? 1:0;
        }
    }


    //-----------하단 : REALM 공부용 자료-------------
    // 출처 : http://blog.eyegoodsoft.com/entry/Android-Realm-LocalDataBase-%EB%A1%9C%EC%BB%AC%EB%94%94%EB%B9%84-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0

//    private String returnImageUrl ="";
//    public void createTest(Realm realm, final String test) {
//        Log.e("테스트", "DB생성");
//        // All writes must be wrapped in a transaction to facilitate safe multi threading
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//
//                realmDB tmp = realm.createObject(realmDB.class);
//                tmp.setTest(test);
//                tmp.getCategory(1);
//            }
//        });
//    }
//
//    public boolean isCheckClassFile(Realm realm) {
//        final Boolean[] result = {false};
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realmDB currentTmp = realm.where(realmDB.class).findFirst();
//
//                if (currentTmp == null) {
//                    result[0] = false;
//                } else {
//                    result[0] = true;
//                }
//            }
//        });
//        return result[0];
//    }
//
//    public String getImageUrl(Realm realm){
//
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realmDB tmp = realm.where(realmDB.class).findFirst();
//                returnImageUrl = tmp.getTest();
//            }
//        });
//
//        return returnImageUrl;
//    }
//
//    public void clearImageHelper(Realm realm){
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.delete(realmDB.class);
//            }
//        });
//    }
}
