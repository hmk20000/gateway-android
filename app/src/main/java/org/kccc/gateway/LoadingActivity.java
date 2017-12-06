package org.kccc.gateway;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ming on 2017. 11. 29..
 */

public class LoadingActivity extends Activity {

    private String version = "1.1.1";


    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        mainProcessing();


//        startLoading();
    }

    private void mainProcessing()
    {
        // 시간이 많이 드는 작업을 자식 스레드로 옮긴다.
        Thread thread = new Thread(null, doBackgroundThreadProcessing, "Background");
        thread.start();
    }

    // 백그라운드 처리 메서드를 실행하는 Runnable.
    private Runnable doBackgroundThreadProcessing = new Runnable()
    {
        public void run()
        {
            backgroundThreadProcessing();
        }
    };

    // 백그라운드에서 몇 가지 처리를 수행하는 메서드.
    private void backgroundThreadProcessing()
    {

        Log.d("Thread Start", "Thread Start~!");

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
        Realm realm = Realm.getDefaultInstance();

        ContentsVO[] contentsVOList = null;
        RealmResults<ContentsVO> OldRealm = realm.where(ContentsVO.class).findAll();
        List<ContentsVO> OldData = realm.copyFromRealm(OldRealm);

        realmHelper helper = new realmHelper();
        helper.clearContentsHelper(realm);

        try
        {
            String ServerJSON = getServerJSON();

            //json 파싱
            Gson gson = new Gson();
            if(ServerJSON!=null){
//online 파일이 있다면 Realm 에 바로 저장
                contentsVOList = gson.fromJson(ServerJSON, ContentsVO[].class);
                helper.saveContentsList(realm,contentsVOList);
                Log.d("RealmGson", "Currently Version Update Complete : "+contentsVOList[3].getTitle());

            }else{

                //인터넷 연결이 안되었을 경우
//                ContentsVO currentTmp = realm.where(ContentsVO.class).findFirst();

                if (OldData.size() == 0) {
//                if (currentTmp == null) {
                    //realm 이 없을 경우. 기기에 있는 파일을 이용
                    byte[] data = readAssetFile("media_list.json");
                    String listString = new String(data);

                    contentsVOList = gson.fromJson(listString, ContentsVO[].class);
                    helper.saveContentsList(realm,contentsVOList);
                    Log.d("RealmGson", "Wifi disconnected But AssetFile Read Complete: "+contentsVOList[3].getTitle());
                }else{
                    //realm 이 있는 경우. Realm 을 이용
//                    Log.d("RealmGson", "Wifi disconnected But Aready Realm Exist : "+currentTmp.getTitle());
                    Log.d("RealmGson", "Wifi disconnected But Aready Realm Exist : "+OldData.get(0).getTitle());
                }
            }
            realm.close();

            if(OldData.size()!=0){
                datebaseManagement(OldData);
            }

            //            handler.post(doUpdateGUI);
            handler.postDelayed(doUpdateGUI,500);
        }
        catch (Exception ex)
        {
//            ex.toString();
            Log.d("Exception", "error : "+ex.toString());
        }
    }

    private void datebaseManagement(List<ContentsVO> OldContentsVO){
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ContentsVO> currentDB = realm.where(ContentsVO.class).findAll();

        realm.beginTransaction();


        for(int i=0; i<OldContentsVO.size() && i<currentDB.size(); i++){
            ContentsVO tmp = OldContentsVO.get(i);
            int index = tmp.getIndex();
            int category = tmp.getCategory();

            ContentsVO newContents = realm.where(ContentsVO.class)
                    .equalTo("index",index)
                    .equalTo("category",category)
                    .findFirst();
            newContents.setDownload(tmp.getDownload());
            newContents.setDownloadDate(tmp.getDownloadDate());
            newContents.setHistory(tmp.getHistory());
            newContents.setHistoryDate(tmp.getHistoryDate());
            newContents.setFavorite(tmp.getFavorite());
            newContents.setHistoryDate(tmp.getHistoryDate());
        }

        realm.commitTransaction();

        realm.close();
    }

    //Online 파일 받아오기.
    private String getServerJSON(){
        //            OKHttp 출처 : http://whereisusb.tistory.com/55
        //            http://wanjanes.blogspot.kr/2017/01/okhttp.html
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://cccvlm.com/API/gateway/android/"+version+"/index.php")
                .build();
        Response response = null;
        String ServerJSON = null;
        try {
            response = client.newCall(request).execute();
            ServerJSON = response.body().string();
            Log.d("RealmGson", "Currently Version Download Complete : "+ServerJSON);

        } catch (IOException e) {
            Log.d("RealmGson", "Wifi disconnected");
//                e.printStackTrace();
        }
        return ServerJSON;
    }

    private byte[] readAssetFile(String assetName) {
        AssetManager assets = getResources().getAssets();
        byte[] data = null;
        try {
            InputStream is = assets.open(assetName);
            int available = is.available();
            data = new byte[available];
            int readByte = is.read(data);
            is.close();
            if (readByte > 0) return data;
        } catch (IOException ignored) {
        }
        return data;
    }

    // GUI 업데이트 메서드를 실행하는 Runnable.
    public Runnable doUpdateGUI = new Runnable()
    {
        public void run()
        {
            updateGUI();
        }
    };

    public void updateGUI()
    {
        // [...다이얼로그를 오픈하거나 GUI 요소를 수정할 수 있다...]
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

//    출처: http://newkie.tistory.com/15 [뉴키]
//    http://dudmy.net/android/2015/08/11/create-android-loading/
}
