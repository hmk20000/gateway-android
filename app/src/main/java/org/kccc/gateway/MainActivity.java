package org.kccc.gateway;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerManager;
import cn.jzvd.JZVideoPlayerStandard;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button home;
    private Button myVideo;
    private Button info;
    private static Context context;

    private String CurState = "Home";
    private int Category = 0;

    private boolean Category_Edit = true;
    private boolean Home_Category = true;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home = (Button)findViewById(R.id.Home);
        myVideo = (Button)findViewById(R.id.Video);
        info = (Button)findViewById(R.id.Info);

        home.setOnClickListener(this);
        myVideo.setOnClickListener(this);
        info.setOnClickListener(this);

        Home_Category = getIntent().getBooleanExtra("Home_Category", true);
        if(Home_Category)
            fragmentReplaceWithHome();
        else
            fragmentReplaceWithCategory(3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Home:
                fragmentReplaceWithHome();
                break;
            case R.id.Video:
                fragmentReplaceWithMyVideo(0);
                break;
            case R.id.Info:
//                fragmentReplaceWithInfo();
                break;
        }
    }
    /*프래그 먼트를 전환하는 함수들*/
    public void fragmentReplaceWithHome() {
        Fragment newFragment = new Fragment_Home();
        CurState = "Home";
        // replace fragment
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.main_fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        home.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.house_click),null,null);
        myVideo.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.video),null,null);
        info.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.info),null,null);
    }
    public void fragmentReplaceWithCategory(int category) {
        Fragment newFragment = new Fragment_Category(category);
        CurState = "Category";
        Category = category;
        // replace fragment
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.main_fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }
    public void fragmentReplaceWithMyVideo(int flag) {
        Fragment newFragment = new Fragment_MyVideos(flag);
        CurState = "MyVideo";
//        Flag = flag;
        // replace fragment
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.main_fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        home.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.house),null,null);
        myVideo.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.video_click),null,null);
        info.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.info),null,null);
    }
    public void fragmentReplaceWithMyVideoEdit(int flag) {
        Fragment newFragment = new Fragment_MyVideos_Edit(flag);
        CurState = "MyVideoEdit";
//        Flag = flag;
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.main_fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        home.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.house),null,null);
        myVideo.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.video_click),null,null);
        info.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.info),null,null);
    }
    public void fragmentReplaceWithWatch(int category, int position, boolean category_edit, int flag) {

        Realm realm = Realm.getDefaultInstance();

        final ContentsVO contentsVO = realm.where(ContentsVO.class)
                .equalTo("category",category)
                .equalTo("index",position)
                .findFirst();

        String fileName = contentsVO.getTitle();
        String URL = contentsVO.getURL();

        realm.close();

//        String fileName = DataBaseHandler.getInstance(this).read(category, position).getTitle();
//        String URL = DataBaseHandler.getInstance(this).read(category, position).getURL();

        Fragment newFragment = new Fragment_Watch(category, position, URL, fileName, flag);
//        Fragment newFragment = new Fragment_Watch(contentsVO, flag);
        CurState = "Watch";
        Category = category;
        Category_Edit = category_edit;

        SharedPreferences videoPref = getSharedPreferences("videoPos", MODE_PRIVATE);
        SharedPreferences.Editor videoEditor = videoPref.edit();
        videoEditor.putInt("curPos", 0);
        videoEditor.commit();

        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.main_fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commitAllowingStateLoss();
    }
    @Override
    public void onBackPressed() {
        Log.d("backBtnTest", "onBackPressed: "+CurState);
        switch (CurState){
            case "Home":
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                    }
                });
                alert.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                alert.setMessage("정말 종료하시겠습니까?");
                alert.show();
                break;
            case "Category":
                fragmentReplaceWithHome();
                break;
            case "MyVideo":
                fragmentReplaceWithHome();
//        else if(CurState.equals("MyVideoEdit"))
//            fragmentReplaceWithMyVideo(Flag);
                break;
            case "Info":
                fragmentReplaceWithHome();
                break;
            case "Watch":
//                boolean fullScreen = (getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != 0;
//                boolean forceNotFullScreen = (getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN) != 0;
//                boolean actionbarVisible = getActionBar().isShowing();
                boolean fullScreen = (getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != 0;
                if(fullScreen){
                    Log.d("FULLSCREEN!", "wow check : "+CurState);
//                    JZVideoPlayerStandard.goOnPlayOnPause();
//                    JZVideoPlayerStandard.quitFullscreenOrTinyWindow();
                    JZVideoPlayerManager.getFirstFloor().clearFloatScreen();
                }else{

                    SharedPreferences videoPref = getSharedPreferences("videoPos", MODE_PRIVATE);;
                    SharedPreferences.Editor videoEditor;
                    videoEditor = videoPref.edit();
                    videoEditor.putBoolean("curPlay", false);
                    videoEditor.commit();
                    if(Category_Edit)
                        fragmentReplaceWithCategory(Category);
//            else
//                fragmentReplaceWithMyVideo(Flag);
                }

                break;
        }
//        JZVideoPlayerStandard.goOnPlayOnPause();
    }
    public static MainActivity getActivity(){
        return (MainActivity) context;
    }
}
