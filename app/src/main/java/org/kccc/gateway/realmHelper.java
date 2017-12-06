package org.kccc.gateway;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Created by ming on 2017. 11. 28..
 */

public class realmHelper {

    public void saveContents(Realm realm, ContentsVO contentsVO){



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
