package org.kccc.gateway;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.jzvd.JZUserAction;
import cn.jzvd.JZUserActionStandard;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerManager;
import cn.jzvd.JZVideoPlayerStandard;
import io.github.lizhangqu.coreprogress.ProgressHelper;
import io.github.lizhangqu.coreprogress.ProgressUIListener;
import io.realm.Realm;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * Created by LG on 2016-06-22.
 * 영상보기에 대한 클래스 카테고리에 따라 영상의 디자인이 다르기 때문에
 * 중간에 카테고리에 따라 사용하게 되는 xml이 다르다.
 */
@SuppressLint("ValidFragment")
public class Fragment_Watch extends Fragment implements View.OnClickListener {
    private View view;
    private RelativeLayout videoContainer;
//    private VideoView videoView;
    private TextView titleBar;
    private TextView title;
    private TextView subTitle;
    private TextView keyWord;
    private TextView time;
    private TextView description;
//    private ImageButton fullScreen;
    private ImageButton back;
    private ImageButton favorite;
    private ImageButton download;
    private ImageButton share;

    private Button question;
    private Button next;

//    private int category;
//    private int index;
    private int flag;

    private String MOVIE_URL = "";
    private String MOVIE_INTERNAL_PATH;
//    private String fileName = "";

//    private SharedPreferences downloadPref;
//    private SharedPreferences.Editor downloadEditor;
//    private SharedPreferences videoPref;
//    private SharedPreferences.Editor videoEditor;
//    private int curPos;
//    private boolean isPlaying;
//    private boolean isError;

    private Dialog dialog;
    private RecyclerView nextView;
    private NextListAdapter nextAdapter;
    private RecyclerView.LayoutManager nextListLayoutManager;
    private ImageButton button;
    private List<NextVO> nextList;

    private TextView title1;
    private TextView title2;
    private TextView question1;
    private TextView question2;
    private Window window;
    private WindowManager.LayoutParams params;
    private WindowManager windowManager;
    private Display display;

    private  ProgressBar downloadProgress;
    private CircularProgressBar circularProgressBar;

    JZVideoPlayerStandard jzVideoPlayerStandard;

    private ProgressBar pbDLs;

    private ContentsVO contentsVO;

    private MediaController mediaController;
    public Fragment_Watch(){}

//    public Fragment_Watch(int category, int position, String URL, String fileName, int flag) {
//        this.category = category;
//        this.index = position;
//        this.MOVIE_URL = URL;
//        this.fileName = fileName;
//        this.flag = flag;
//    }

    public Fragment_Watch(ContentsVO contentsVO, int flag) {
        this.contentsVO = contentsVO;
//        this.category = contentsVO.getCategory();
//        this.index = contentsVO.getIndex();
//        this.MOVIE_URL = contentsVO.getURL();
//        this.fileName = contentsVO.getTitle();
        this.flag = flag;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(false);
    }


    @Override
    public void onResume() {
        super.onResume();

//        setVideoPos();
//        isPlaying = videoPref.getBoolean("curPlay", false);
//        if(isPlaying) {
//            videoView.start();
//        }

//        videoEditor = videoPref.edit();
//        videoEditor.putInt("curPos",curPos);
//        videoEditor.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        Realm realm = Realm.getDefaultInstance();
//
//        final ContentsVO realmContentsVO = realm.where(ContentsVO.class)
//                .equalTo("category",contentsVO.getCategory())
//                .equalTo("index",contentsVO.getIndex())
//                .findFirst();
//
//        this.contentsVO = realm.copyFromRealm(realmContentsVO);

        MOVIE_URL = "http://cccvlm.com/sfproject/movies/kor/" + contentsVO.getURL();

        super.onCreateView(inflater, container, savedInstanceState);

        switch (contentsVO.getCategory()){
            case 0:
                view = inflater.inflate(R.layout.fragment_watch_1, container, false);
//            contentsVO = DataBaseHandler.getInstance(view.getContext()).read(category, index);
                keyWord = (TextView) view.findViewById(R.id.keyWord);
                keyWord.setText(contentsVO.getKeyWord());
                question = (Button)view.findViewById(R.id.questionBtn);
                next = (Button)view.findViewById(R.id.nextBtn);
                question.setOnClickListener(this);
                next.setOnClickListener(this);
                break;
            case 1:case 2:
                view = inflater.inflate(R.layout.fragment_watch_1, container, false);
//            contentsVO = DataBaseHandler.getInstance(view.getContext()).read(category, index);
                question = (Button)view.findViewById(R.id.questionBtn);
                next = (Button)view.findViewById(R.id.nextBtn);
                question.setOnClickListener(this);
                next.setOnClickListener(this);
                break;
            case 3:case 4:
                view = inflater.inflate(R.layout.fragment_watch_1, container, false);
//            contentsVO = DataBaseHandler.getInstance(view.getContext()).read(category, index);
                break;
        }

//        MOVIE_INTERNAL_PATH = view.getContext().getFilesDir().getAbsolutePath()+"/"+fileName+".mp4";
//        MOVIE_INTERNAL_PATH = Environment.getExternalStorageDirectory() + "/Android/data/org.kccc.gateway/cache/"+fileName+".mp4";
        MOVIE_INTERNAL_PATH = Environment.getExternalStorageDirectory() + "/Android/data/org.kccc.gateway/cache/"+App.md5(contentsVO.getTitle().getBytes());

//        isError = false;

//        videoPref = view.getContext().getSharedPreferences("videoPos", view.getContext().MODE_PRIVATE);
//        downloadPref = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/nanumgothic.ttf");
        Typeface typefaceBold = Typeface.createFromAsset(getActivity().getAssets(),"fonts/nanumgothicbold.ttf");
        titleBar = (TextView) view.findViewById(R.id.titleBar);
        title = (TextView) view.findViewById(R.id.Main_title);
        subTitle = (TextView) view.findViewById(R.id.subTitle);
        time = (TextView)view.findViewById(R.id.time);
        description = (TextView) view.findViewById(R.id.description);

        videoContainer = (RelativeLayout)view.findViewById(R.id.videoContainer);
//        fullScreen = (ImageButton)view.findViewById(R.id.fullScreen);
        back = (ImageButton)view.findViewById(R.id.backBtn);
        favorite = (ImageButton)view.findViewById(R.id.favoriteBtn);
        download = (ImageButton)view.findViewById(R.id.downloadBtn);
        share = (ImageButton)view.findViewById(R.id.shareBtn);
//        downloadProgress = (ProgressBar)view.findViewById(R.id.downloadProgress);

        circularProgressBar = (CircularProgressBar)view.findViewById(R.id.yourCircularProgressbar);
//        circularProgressBar.setColor(ContextCompat.getColor(this, R.color.progressBarColor));
//        circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundProgressBarColor));
//        circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.progressBarWidth));
//        circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.backgroundProgressBarWidth));
        circularProgressBar.setVisibility(View.INVISIBLE);


//        videoView = (VideoView) view.findViewById(R.id.videoView);

        setVideoPath(contentsVO,view);
        /*if(contentsVO.getDownload() != 2) {

            setVideoPos();
            isPlaying = videoPref.getBoolean("curPlay", false);
//            if(isPlaying)
//                videoView.start();
        }*/
        setContents(contentsVO);

        titleBar.setTypeface(typefaceBold);
        title.setTypeface(typefaceBold);
        subTitle.setTypeface(typefaceBold);
        time.setTypeface(typeface);


        videoContainer.setOnClickListener(this);
//        fullScreen.setOnClickListener(this);
        back.setOnClickListener(this);
//        videoView.setOnClickListener(this);
        favorite.setOnClickListener(this);
        download.setOnClickListener(this);
        share.setOnClickListener(this);

        /*if(contentsVO.getDownload() == 2) {
            download.setVisibility(View.GONE);
            fullScreen.setVisibility(View.GONE);
        }*/

//        realm.close();

        return view;
    }

    public void setVideoPath(ContentsVO contentsVO, View view){

        //다운로드
        String Play_url = contentsVO.getDownload()==1?MOVIE_INTERNAL_PATH:MOVIE_URL;

        jzVideoPlayerStandard = (JZVideoPlayerStandard) view.findViewById(R.id.videoplayer);
        jzVideoPlayerStandard.setUp(Play_url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
//                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, contentsVO.getTitle());

        Bitmap thumbImage = new ThumbnailHandler(this.view.getContext()).getBitmapThumbnail(contentsVO,512);
        thumbImage = new BitmapControlHelper().fastblur(thumbImage, (float) 1.0,20);
        jzVideoPlayerStandard.thumbImageView.setImageBitmap(thumbImage);
        jzVideoPlayerStandard.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        JZVideoPlayer.setJzUserAction(new MyUserActionStandard());
        JZVideoPlayer.SAVE_PROGRESS = false;
//        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

//        jzVideoPlayerStandard.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");


        File file = new File(MOVIE_INTERNAL_PATH);
//        if(!file.exists())
//            contentsVO.setDownload(0);
//        videoView.setVideoPath(contentsVO.getURL());
//        if(contentsVO.getDownload()==0)

//            videoView.setVideoURI(Uri.parse(MOVIE_URL));
//        else
//            videoView.setVideoPath(MOVIE_INTERNAL_PATH);

//        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//            @Override
//            public boolean onError(MediaPlayer mp, int what, int extra) {
//                AlertDialog.Builder alert = new AlertDialog.Builder(Fragment_Watch.this.view.getContext());
//                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        isError = true;
//                        dialog.dismiss();
//                    }
//                });
//                alert.setMessage("다운받은 영상이 없을 경우 오프라인에서 재생할수 없습니다.");
//                alert.show();
//                return true;
//            }
//        });
    }



    public void setVideoPos(){
//        curPos = videoPref.getInt("curPos", 0);
//        if(videoPref.getInt("curPos", 0) == 0)
//            videoView.seekTo(curPos+100);
//        else
//            videoView.seekTo(curPos-100);
//        if(!isPlaying)
//            videoView.setBackgroundDrawable(new ThumbnailHandler(view.getContext()).setThumbnail(category, index));

    }

    public void setContents(ContentsVO contentsVO){
        titleBar.setText(contentsVO.getTitle());
        title.setText(contentsVO.getTitle());
//        title.setText("test");
        if(contentsVO.getSubTitle()!=null)
            subTitle.setText("("+contentsVO.getSubTitle()+")");
        else
            subTitle.setVisibility(View.GONE);

        if(contentsVO.getTime() != null)
            time.setText(contentsVO.getTime());
        else
            time.setVisibility(View.GONE);

        if(contentsVO.getDescription() != null) {
            description.setText(contentsVO.getDescription());
            description.setMovementMethod(new ScrollingMovementMethod());
        }
        else
            description.setVisibility(View.GONE);

        if(contentsVO.getFavorite() == 0)
            favorite.setImageDrawable(getResources().getDrawable(R.drawable.star));
        else
            favorite.setImageDrawable(getResources().getDrawable(R.drawable.star_click));

//        mediaController = new MediaController(videoView.getContext());
//        mediaController.hide();
//        videoView.setMediaController(mediaController);
//        videoView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                /*if(contentsVO.getDownload() == 2){
//                    Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse((contentsVO.getURL())));
//                    view.getContext().startActivity(youtubeIntent);
//                }
//                else {
//                    if(!isError) {
//                        videoView.start();
//                        videoView.setBackgroundDrawable(null);
//
//                        if(mediaController.isShowing())
//                            mediaController.hide();
//                        else
//                            mediaController.show();
//
//                        if (!isPlaying) {
//                            videoEditor = videoPref.edit();
//                            videoEditor.putBoolean("curPlay", true);
//                            videoEditor.commit();
//                            isPlaying = true;
//                            contentsVO.setHistory(1);
//                            contentsVO.setHistoryDate(System.currentTimeMillis());
//                            DataBaseHandler.getInstance(view.getContext()).updateHistory(contentsVO);
//                        }
//                    }
//                    fullScreen.setVisibility(View.VISIBLE);
//                    fullScreen.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            fullScreen.setVisibility(View.INVISIBLE);
//                        }
//                    }, 3500);
//                }*/
//
//                return true;
//            }
//        });
//        fullScreen.setVisibility(View.INVISIBLE);
    }


    private void initializationNextData() {
//        List<NextVO> dbQuestionList = DataBaseHandler.getInstance(view.getContext()).readNextQuestionList(category, index);
        List<NextVO> dbQuestionList = contentsVO.getNext();
        this.nextList = dbQuestionList;
    }
    private void initializationNextListView(Dialog dialog) {

        nextListLayoutManager = new LinearLayoutManager(view.getContext());
        nextView.setLayoutManager(nextListLayoutManager);
        if(nextList.size() != 0){
            nextAdapter = new NextListAdapter(view.getContext(), nextList, dialog);
            nextView.setAdapter(nextAdapter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        Realm realm = Realm.getDefaultInstance();

        final ContentsVO realmContentsVO = realm.where(ContentsVO.class)
                .equalTo("category",this.contentsVO.getCategory())
                .equalTo("index",this.contentsVO.getIndex())
                .findFirst();

        RealmHelper realmHelper = new RealmHelper();

        Intent intent;
//        videoEditor = videoPref.edit();
//        downloadEditor = downloadPref.edit();


        switch (v.getId()){
            case R.id.backBtn:
//                JZVideoPlayerManager.
//                jzVideoPlayerStandard.

                //videoEditor.putBoolean("curPlay", false);
                //videoEditor.commit();
                if(flag<4)
                    ((MainActivity)view.getContext()).fragmentReplaceWithMyVideo(flag);
                else
                    ((MainActivity)view.getContext()).fragmentReplaceWithCategory(contentsVO.getCategory());
                break;
            case R.id.videoContainer:

                break;

            /*case R.id.fullScreen:
                videoEditor.putInt("category", contentsVO.getCategory());
                videoEditor.putInt("index", contentsVO.getIndex());
//                videoEditor.putInt("curPos", videoView.getCurrentPosition());
//                videoEditor.putBoolean("curPlay", videoView.isPlaying());
                videoEditor.commit();


                view.getContext().startActivity(intent);
                break;*/

            case R.id.favoriteBtn:

//                realm.beginTransaction();
                if(contentsVO.getFavorite() == 0) {

                    contentsVO.setFavorite(1);
                    contentsVO.setFavoriteDate(System.currentTimeMillis());

                    favorite.setImageDrawable(getResources().getDrawable(R.drawable.star_click));
                }
                else {

                    contentsVO.setFavorite(0);
                    contentsVO.setFavoriteDate(0);

                    favorite.setImageDrawable(getResources().getDrawable(R.drawable.star));
                }

//                realm.commitTransaction();


                realmHelper.saveContents(contentsVO);

                break;

            case R.id.downloadBtn:


                //PermissionCheck
                //https://github.com/yanzhenjie/AndPermission
                AndPermission.with(MainActivity.getActivity())
                        .requestCode(100)
                        .permission(Permission.STORAGE)
//                        .callback(listener)
                        .start();

                DownloadStart(MOVIE_URL);
                contentsVO.setDownload(1);
                contentsVO.setDownloadDate(System.currentTimeMillis());

                realmHelper.saveContents(contentsVO);

                /*if(wifi.isConnected()) {
                    if (contentsVO.getDownload() == 0) {
                        contentsVO.setDownload(1);
                        contentsVO.setDownloadDate(System.currentTimeMillis());
                        DataBaseHandler.getInstance(view.getContext()).updateDownload(contentsVO);

                        downloadEditor.putString("url", MOVIE_URL);
                        downloadEditor.putString("fileName", fileName);
                        downloadEditor.commit();

                        intent = new Intent("org.kccc.gateway.DownloadService");
                        intent.setPackage(view.getContext().getPackageName());
                        view.getContext().startService(intent);
                    } else if (contentsVO.getDownload() == 1) {
                        Toast.makeText(view.getContext(), "이미 다운로드 하셨습니다.", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                    alert.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();     //닫기
                        }
                    });
                    alert.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (contentsVO.getDownload() == 0) {
                                contentsVO.setDownload(1);
                                contentsVO.setDownloadDate(System.currentTimeMillis());
                                DataBaseHandler.getInstance(view.getContext()).updateDownload(contentsVO);

                                downloadEditor.putString("url", MOVIE_URL);
                                downloadEditor.putString("fileName", fileName);
                                downloadEditor.commit();

                                Intent downIntent = new Intent("org.kccc.gateway.DownloadService");
                                downIntent.setPackage(view.getContext().getPackageName());
                                view.getContext().startService(downIntent);
                            } else if (contentsVO.getDownload() == 1) {
                                Toast.makeText(view.getContext(), "이미 다운로드 하셨습니다.", Toast.LENGTH_LONG).show();
                            }
                            dialog.dismiss();
                        }
                    });
                    alert.setMessage("Wi-Fi 환경이 아닙니다. 그래도 다운로드 하시겠습니까?");
                    alert.show();
                }*/
                break;

            case R.id.shareBtn:
                intent = new Intent(Intent.ACTION_SEND);
                String shareDescription=contentsVO.getTitle()+"("+contentsVO.getSubTitle()+")\n"
                                +contentsVO.getKeyWord()+"\n";
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, shareDescription);
                intent.putExtra(Intent.EXTRA_TEXT, contentsVO.getURL());
                startActivity(Intent.createChooser(intent, "Sharing"));
                break;

            case R.id.questionBtn:
//                videoEditor.putInt("curPos",videoView.getCurrentPosition());
//                videoEditor.commit();
                dialog = new Dialog(view.getContext(), R.style.NoActionBarDialog);
                dialog.setContentView(R.layout.activity_question);

//                contentsVO = DataBaseHandler.getInstance(dialog.getContext()).read(category, index);

                question1 = (TextView)dialog.findViewById(R.id.question1);
                question2 = (TextView)dialog.findViewById(R.id.question2);
                title1 = (TextView)dialog.findViewById(R.id.title1);
                title2 = (TextView)dialog.findViewById(R.id.title2);

                if(contentsVO.getQuestion2().equals("")){
                    title1.setText("질문");
                    title2.setText("");
                }
//                question1.setText(contentsVO.getQuestion1());
//                question2.setText(contentsVO.getQuestion2());

                String tmp = "";
                for(int i=0; i<contentsVO.getQuestion1().size(); i++){
                    tmp += contentsVO.getQuestion1().get(i).getQuestion() + "\n\n";
                }
                question1.setText(tmp);

                tmp = "";
                for(int i=0; i<contentsVO.getQuestion2().size(); i++){
                    tmp += contentsVO.getQuestion2().get(i).getQuestion() + "\n\n";
                }
                question2.setText(tmp);

                button = (ImageButton)dialog.findViewById(R.id.button_finish);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                window = dialog.getWindow();
                params = window.getAttributes();
                windowManager = (WindowManager)view.getContext().getSystemService(view.getContext().WINDOW_SERVICE);
                display = windowManager.getDefaultDisplay();
                params.gravity = Gravity.TOP;
                params.y = 120;
                params.x = 0;
                window.setAttributes(params);
                window.setLayout(params.width, display.getHeight() - params.y *4);

                dialog.show();
                break;

            case R.id.nextBtn:
//                videoEditor.putInt("curPos",videoView.getCurrentPosition());
//                videoEditor.commit();

                dialog = new Dialog(view.getContext(), R.style.NoActionBarDialog);
                dialog.setContentView(R.layout.activity_next);

                nextView = (RecyclerView)dialog.findViewById(R.id.next_list);
                button = (ImageButton)dialog.findViewById(R.id.button_finish);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                initializationNextData();
                initializationNextListView(dialog);
                window = dialog.getWindow();
                params = window.getAttributes();
                windowManager = (WindowManager)view.getContext().getSystemService(view.getContext().WINDOW_SERVICE);
                display = windowManager.getDefaultDisplay();
                params.gravity = Gravity.TOP;
                params.y = 120;
                params.x = 0;
                window.setAttributes(params);
                window.setLayout(params.width, display.getHeight() - params.y *4);

                dialog.show();
                break;
        }

        realm.close();
    }

//    https://github.com/lizhangqu/CoreProgress
    private void DownloadStart(String url){
        download.setVisibility(View.INVISIBLE);
        circularProgressBar.setVisibility(View.VISIBLE);

        //client
        OkHttpClient okHttpClient = new OkHttpClient();
//request builder
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.get();
//call
        Call call = okHttpClient.newCall(builder.build());
//enqueue
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "=============onFailure===============");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("TAG", "=============onResponse===============");
                Log.e("TAG", "request headers:" + response.request().headers());
                Log.e("TAG", "response headers:" + response.headers());

                //your original response body
                ResponseBody body = response.body();
                //wrap the original response body with progress
                ResponseBody responseBody = ProgressHelper.withProgress(body, new ProgressUIListener() {

                    //if you don't need this method, don't override this methd. It isn't an abstract method, just an empty method.
                    @Override
                    public void onUIProgressStart(long totalBytes) {
                        super.onUIProgressStart(totalBytes);
                        Log.e("TAG", "onUIProgressStart:" + totalBytes);
                    }

                    @Override
                    public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
                        Log.e("TAG", "=============start===============");
                        Log.e("TAG", "numBytes:" + numBytes);
                        Log.e("TAG", "totalBytes:" + totalBytes);
                        Log.e("TAG", "percent:" + percent);
                        Log.e("TAG", "speed:" + speed);
                        Log.e("TAG", "============= end ===============");
//                        downloadProgress.setProgress((int) (100 * percent));

                        int animationDuration = 0; // 2500ms = 2,5s

                        percent *= 100;
                        circularProgressBar.setProgress(percent);

//                      circularProgressBar.setProgressWithAnimation(100 * percent, animationDuration); // Default duration = 1500ms

                        Log.d("download INFO", "onUIProgressChanged: "+"numBytes:" + numBytes + " bytes" + "\ntotalBytes:" + totalBytes + " bytes" + "\npercent:" + percent * 100 + " %" + "\nspeed:" + speed * 1000 / 1024 / 1024 + " MB/秒");
                    }

                    //if you don't need this method, don't override this methd. It isn't an abstract method, just an empty method.
                    @Override
                    public void onUIProgressFinish() {
                        super.onUIProgressFinish();
                        download.setVisibility(View.VISIBLE);
                        circularProgressBar.setVisibility(View.INVISIBLE);
//                        Noty();

                        Log.e("TAG", "onUIProgressFinish:");
                        //Toast.makeText(MainActivity.getActivity(), "结束上传", Toast.LENGTH_SHORT).show();
                    }

                });
                //read the body to file
                BufferedSource source = responseBody.source();
                File outFile = new File(MOVIE_INTERNAL_PATH);
                Log.d("file_Path", "onResponse: "+outFile);
//                File outFile = new File("sdcard/"+fileName+".mp4");
                outFile.delete();
                outFile.getParentFile().mkdirs();
                outFile.createNewFile();
                BufferedSink sink = Okio.buffer(Okio.sink(outFile));
                source.readAll(sink);
                sink.flush();
                source.close();
            }
        });
    }
    private void Noty(){
        NotificationManager notificationManager = (NotificationManager)MainActivity.getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(MainActivity.getActivity(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(MainActivity.getActivity());
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),android.R.drawable.star_on));
        builder.setSmallIcon(android.R.drawable.star_on);
        builder.setTicker("알람 간단한 설명");
        builder.setContentTitle("알람 제목");
        builder.setContentText("알람 내용");
        builder.setWhen(System.currentTimeMillis());
//        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setNumber(999);
        notificationManager.notify(0, builder.build());

    }

    //    https://github.com/lipangit/JiaoZiVideoPlayer
    class MyUserActionStandard implements JZUserActionStandard {

        @Override
        public void onEvent(int type, Object url, int screen, Object... objects) {
            switch (type) {
                case JZUserAction.ON_CLICK_START_ICON:
                    Log.d("JZplayer play", "onEvent: start!");

                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    final ContentsVO realmContentsVO = realm.where(ContentsVO.class)
                            .equalTo("category",contentsVO.getCategory())
                            .equalTo("index",contentsVO.getCategory())
                            .findFirst();
                    realmContentsVO.setHistory(1);
                    realmContentsVO.setHistoryDate(System.currentTimeMillis());
                    realm.commitTransaction();
                    realm.close();

//                    Log.i("USER_EVENT", "ON_CLICK_START_ICON" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
//                case JZUserAction.ON_CLICK_START_ERROR:
//                    Log.i("USER_EVENT", "ON_CLICK_START_ERROR" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
//                case JZUserAction.ON_CLICK_START_AUTO_COMPLETE:
//                    Log.i("USER_EVENT", "ON_CLICK_START_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
//                case JZUserAction.ON_CLICK_PAUSE:
//                    Log.i("USER_EVENT", "ON_CLICK_PAUSE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
//                case JZUserAction.ON_CLICK_RESUME:
//                    Log.i("USER_EVENT", "ON_CLICK_RESUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
//                case JZUserAction.ON_SEEK_POSITION:
//                    Log.i("USER_EVENT", "ON_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
//                case JZUserAction.ON_AUTO_COMPLETE:
//                    Log.i("USER_EVENT", "ON_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
//                case JZUserAction.ON_ENTER_FULLSCREEN:
//                    Log.i("USER_EVENT", "ON_ENTER_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
//                case JZUserAction.ON_QUIT_FULLSCREEN:
//                    Log.i("USER_EVENT", "ON_QUIT_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
//                case JZUserAction.ON_ENTER_TINYSCREEN:
//                    Log.i("USER_EVENT", "ON_ENTER_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
//                case JZUserAction.ON_QUIT_TINYSCREEN:
//                    Log.i("USER_EVENT", "ON_QUIT_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
//                case JZUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME:
//                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
//                case JZUserAction.ON_TOUCH_SCREEN_SEEK_POSITION:
//                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
//
//                case JZUserActionStandard.ON_CLICK_START_THUMB:
//                    Log.i("USER_EVENT", "ON_CLICK_START_THUMB" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
//                case JZUserActionStandard.ON_CLICK_BLANK:
//                    Log.i("USER_EVENT", "ON_CLICK_BLANK" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
//                    break;
//                default:
//                    Log.i("USER_EVENT", "unknow");
//                    break;
            }
        }
    }
}