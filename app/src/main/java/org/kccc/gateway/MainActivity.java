package org.kccc.gateway;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerManager;
import cn.jzvd.JZVideoPlayerStandard;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
//    private Button home;
//    private Button myVideo;
//    private Button info;

    private ArrayList<ImageButton> TabBtn;
    ArrayList<TextView> TabBtnText;

    private static Context context;

    private String CurState = "Home";
    private String BeforeState = "Home";
    private int Category = 0;

    private boolean Category_Edit = true;
    private boolean Home_Category = true;

    private ContentsVO contentsVO;

    private int myVideoFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("notice");

        RealmHelper realmHelper = new RealmHelper();
        if(realmHelper.FirstTimeCheck()){
            SharedPreferences orientationPref = getSharedPreferences("orientationPref",MODE_PRIVATE);
            SharedPreferences.Editor orientationEditor = orientationPref.edit();
            orientationEditor.putInt("flag",-1);
            orientationEditor.commit();

            Intent intent = new Intent(this, ImageSlideActivity.class);
            startActivity(intent);
        }

        context = this;
        TabBtn = new ArrayList<ImageButton>();
        TabBtn.add(0,(ImageButton)findViewById(R.id.Home));
        TabBtn.add(1,(ImageButton)findViewById(R.id.Video));
        TabBtn.add(2,(ImageButton)findViewById(R.id.Info));

        TabBtnText = new ArrayList<TextView>();
        TabBtnText.add((TextView)findViewById(R.id.Home_txt));
        TabBtnText.add((TextView)findViewById(R.id.Video_txt));
        TabBtnText.add((TextView)findViewById(R.id.Info_txt));

//        home = (Button)findViewById(R.id.Home);
//        myVideo = (Button)findViewById(R.id.Video);
//        info = (Button)findViewById(R.id.Info);

//        home.setOnClickListener(this);
//        myVideo.setOnClickListener(this);
//        info.setOnClickListener(this);

        for(int i=0; i<TabBtn.size(); i++){
            TabBtn.get(i).setOnClickListener(this);
            TabBtnText.get(i).setOnClickListener(this);
        }

        Home_Category = getIntent().getBooleanExtra("Home_Category", true);
        if(Home_Category)
            fragmentReplaceWithHome();//            fragmentReplaceWithHome();
        else
            fragmentReplaceWithCategory(3);
    }

    @Override
    public void onClick(View v) {
        JZVideoPlayerStandard.goOnPlayOnPause();
        switch (v.getId()){
            case R.id.Home:case R.id.Home_txt:
                fragmentReplaceWithHome(); //fragmentReplaceWithHome();
                break;
            case R.id.Video:case R.id.Video_txt:
                fragmentReplaceWithMyVideo(0);
                break;
            case R.id.Info:case R.id.Info_txt:
                fragmentReplaceWithInfo();
                break;
        }
    }
    private void btnImgChange(ImageButton btn,int drawable){
        btn.setImageDrawable(getResources().getDrawable(drawable));
//        btn.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(drawable),null,null);
    }
    public void tabIconSelect(int TabNum){
        int[] tabBtnImgOff = {R.drawable.house,R.drawable.video,R.drawable.info};
        int[] tabBtnImgOn = {R.drawable.house_click,R.drawable.video_click,R.drawable.info_click};

        for(int i=0; i<TabBtn.size(); i++){
            if(TabNum==i){
                TabBtnText.get(i).setTextColor(getResources().getColor(R.color.white));
                btnImgChange(TabBtn.get(i),tabBtnImgOn[i]);
            }else{
                TabBtnText.get(i).setTextColor(getResources().getColor(R.color.lightGray));
                btnImgChange(TabBtn.get(i),tabBtnImgOff[i]);
            }
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

        tabIconSelect(0);
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
        myVideoFlag = flag;
//        Flag = flag;
        // replace fragment
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.main_fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        tabIconSelect(1);
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

        tabIconSelect(1);
    }
    public void fragmentReplaceWithInfo() {
        Fragment newFragment = new Fragment_Info();
//        Fragment newFragment = new Fragment_Setting();
        CurState = "Info";

        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.main_fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        tabIconSelect(2);
    }
    public void fragmentReplaceWithWatch(int category, int position, boolean category_edit, int flag) {

        Realm realm = Realm.getDefaultInstance();

        final ContentsVO RealmcontentsVO = realm.where(ContentsVO.class)
                .equalTo("category",category)
                .equalTo("index",position)
                .findFirst();


        contentsVO = realm.copyFromRealm(RealmcontentsVO);
        String fileName = contentsVO.getTitle();
        String URL = contentsVO.getURL();

        realm.close();

//        String fileName = DataBaseHandler.getInstance(this).read(category, position).getTitle();
//        String URL = DataBaseHandler.getInstance(this).read(category, position).getURL();

//        Fragment newFragment = new Fragment_Watch(category, position, URL, fileName, flag);
        Fragment newFragment = new Fragment_Watch(contentsVO, flag);
        BeforeState = CurState;
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
//                fragmentReplaceWithMyVideo(myVideoFlag);
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
                    Log.d("Fullscreen Test", " OK ");
//                    JZVideoPlayerStandard.goOnPlayOnPause();
//                    JZVideoPlayerStandard.quitFullscreenOrTinyWindow();
                    JZVideoPlayerManager.getFirstFloor().backPress();
                }else{
                    Log.d("Fullscreen Test", " NO ");
                    JZVideoPlayerStandard.goOnPlayOnPause();
                    if(BeforeState.equals("MyVideo")){
                        fragmentReplaceWithMyVideo(myVideoFlag);
                    }else {
                        fragmentReplaceWithCategory(Category);
                    }

//                    SharedPreferences videoPref = getSharedPreferences("videoPos", MODE_PRIVATE);;
//                    SharedPreferences.Editor videoEditor;
//                    videoEditor = videoPref.edit();
//                    videoEditor.putBoolean("curPlay", false);
//                    videoEditor.commit();
//                    if(Category_Edit)
//                        fragmentReplaceWithCategory(Category);
//            else
//                fragmentReplaceWithMyVideo(Flag);
                }

                Log.d("Fullscreen Test", " Before break; ");
                break;
        }
//        JZVideoPlayerStandard.goOnPlayOnPause();
    }
    public static MainActivity getActivity(){
        return (MainActivity) context;
    }
}
