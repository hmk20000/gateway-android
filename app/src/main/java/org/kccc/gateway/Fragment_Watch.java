package org.kccc.gateway;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.jzvd.JZUserAction;
import cn.jzvd.JZUserActionStandard;
import cn.jzvd.JZVideoPlayer;
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

    private ImageButton back;
    private ImageButton favorite;
    private ImageButton download;
    private ImageButton share;

    private int myVideoFlag;

    private String MOVIE_URL = "";
    private String MOVIE_INTERNAL_PATH;

    private Dialog dialog;
    private RecyclerView nextView;
    private List<NextVO> nextList;

    private CircularProgressBar circularProgressBar;

    JZVideoPlayerStandard jzVideoPlayerStandard;

    private ContentsVO contentsVO;

    public Fragment_Watch(){}

    public Fragment_Watch(ContentsVO contentsVO, int myVideoFlag) {
        this.contentsVO = contentsVO;
        this.myVideoFlag = myVideoFlag;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(false);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        MOVIE_URL = "http://cccvlm.com/sfproject/movies/kor/" + contentsVO.getURL();

        super.onCreateView(inflater, container, savedInstanceState);
        setContents(inflater,container);

        back.setOnClickListener(this);
        favorite.setOnClickListener(this);
        download.setOnClickListener(this);
        share.setOnClickListener(this);

        return view;
    }

    private void setContents(LayoutInflater inflater, ViewGroup container){
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/nanumgothic.ttf");
        Typeface typefaceBold = Typeface.createFromAsset(getActivity().getAssets(),"fonts/nanumgothicbold.ttf");

        TextView keyWord;
        switch (contentsVO.getCategory()){
            case 0:case 1:case 2:
                view = inflater.inflate(R.layout.fragment_watch_1, container, false);
                LinearLayout linearLayout = view.findViewById(R.id.keyword_list);

                String[] array = contentsVO.getKeyWord().replace(" ","").split(",");
                keyWord = view.findViewById(R.id.keyword_contents);
                for(String cha:array){
                    TextView tmpTextView = new TextView(getActivity());
                    tmpTextView.setText("#"+cha);
                    tmpTextView.setLayoutParams(keyWord.getLayoutParams());
                    tmpTextView.setBackground(keyWord.getBackground());
                    tmpTextView.setTextColor(keyWord.getTextColors());
                    tmpTextView.setPadding(20,5,30,10);

                    linearLayout.addView(tmpTextView);
                }
                ((ViewGroup) keyWord.getParent()).removeView(keyWord);

                Button question = (Button) view.findViewById(R.id.questionBtn);
                Button next = (Button) view.findViewById(R.id.nextBtn);
                question.setOnClickListener(this);
                next.setOnClickListener(this);
                question.setTypeface(typefaceBold);
                next.setTypeface(typefaceBold);
                TextView time = (TextView) view.findViewById(R.id.time);
                time.setText(contentsVO.getTime());
                time.setTypeface(typeface);
                break;
//            case 1:case 2:
//                view = inflater.inflate(R.layout.fragment_watch_2, container, false);
////            contentsVO = DataBaseHandler.getInstance(view.getContext()).read(category, index);
//                question = (Button)view.findViewById(R.id.questionBtn);
//                next = (Button)view.findViewById(R.id.nextBtn);
//                question.setOnClickListener(this);
//                next.setOnClickListener(this);
//                break;
            case 3:case 4:
                view = inflater.inflate(R.layout.fragment_watch_1, container, false);
                time = (TextView)view.findViewById(R.id.time);
                time.setText(contentsVO.getTime());
                time.setTypeface(typeface);
                keyWord = view.findViewById(R.id.keyword_contents);
                ((ViewGroup) keyWord.getParent()).removeView(keyWord);
//            contentsVO = DataBaseHandler.getInstance(view.getContext()).read(category, index);
                break;
        }

        MOVIE_INTERNAL_PATH = Environment.getExternalStorageDirectory() + "/Android/data/org.kccc.gateway/cache/"+App.md5(contentsVO.getTitle().getBytes());

        TextView titleBar = (TextView) view.findViewById(R.id.titleBar);
        TextView title = (TextView) view.findViewById(R.id.Main_title);
        TextView subTitle = (TextView) view.findViewById(R.id.subTitle);

        TextView description = (TextView) view.findViewById(R.id.description);

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

        jzVideoPlayerStandard = (JZVideoPlayerStandard) view.findViewById(R.id.videoplayer);
        setVideoPath(contentsVO);

        titleBar.setText(contentsVO.getTitle());
        title.setText(contentsVO.getTitle());
//        title.setText("test");
        if(contentsVO.getSubTitle()!=null)
            subTitle.setText("("+contentsVO.getSubTitle()+")");
        else
            subTitle.setVisibility(View.GONE);

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

        titleBar.setTypeface(typefaceBold);
        title.setTypeface(typefaceBold);
        subTitle.setTypeface(typefaceBold);
        description.setTypeface(typeface);
    }

    public void setVideoPath(ContentsVO contentsVO){

        //download check ->
        String Play_url = contentsVO.getDownload()==1 ? MOVIE_INTERNAL_PATH : MOVIE_URL;
        jzVideoPlayerStandard.setUp(Play_url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
//                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, contentsVO.getTitle());

        Bitmap thumbImage = new ThumbnailHandler(this.view.getContext()).getBitmapThumbnail(contentsVO,512);
        thumbImage = new BitmapControlHelper().fastblur(thumbImage, (float) 1.0,20);
        jzVideoPlayerStandard.thumbImageView.setImageBitmap(thumbImage);
        jzVideoPlayerStandard.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        JZVideoPlayer.setJzUserAction(new MyUserActionStandard());
        JZVideoPlayer.SAVE_PROGRESS = false;
    }

    private void initializationNextData() {
//        List<NextVO> dbQuestionList = DataBaseHandler.getInstance(view.getContext()).readNextQuestionList(category, index);
        List<NextVO> dbQuestionList = contentsVO.getNext();
        this.nextList = dbQuestionList;
    }
    private void initializationNextListView(Dialog dialog) {

        RecyclerView.LayoutManager nextListLayoutManager = new LinearLayoutManager(view.getContext());
        nextView.setLayoutManager(nextListLayoutManager);
        if(nextList.size() != 0){
            NextListAdapter nextAdapter = new NextListAdapter(view.getContext(), nextList, dialog);
            nextView.setAdapter(nextAdapter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        final RealmHelper realmHelper = new RealmHelper();
        Intent intent;

        switch (v.getId()){
            case R.id.backBtn:
                if(myVideoFlag<4)
                    ((MainActivity)view.getContext()).fragmentReplaceWithMyVideo(myVideoFlag);
                else
                    ((MainActivity)view.getContext()).fragmentReplaceWithCategory(contentsVO.getCategory());
                JZVideoPlayerStandard.goOnPlayOnPause();
                break;

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

                realmHelper.saveContents(contentsVO);

                break;

            case R.id.downloadBtn:


                //----- ----- PermissionCheck
                //----- https://github.com/yanzhenjie/AndPermission
                AndPermission.with(MainActivity.getActivity())
                        .requestCode(100)
                        .permission(Permission.STORAGE)
//                        .callback(listener)
                        .start();




                //wifi check
                ConnectivityManager cm =
                        (ConnectivityManager)MainActivity.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if (contentsVO.getDownload() != 1) {
                    if(isConnected){
                        boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
                        if(!isWiFi){

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
                                    DownloadStart(MOVIE_URL);

                                    dialog.dismiss();
                                }
                            });
                            alert.setMessage("Wi-Fi 환경이 아닙니다. 그래도 다운로드 하시겠습니까?");
                            alert.show();
                        }
                    }
                } else {
                    //todo 이미 다운로드
                    Toast.makeText(view.getContext(), "이미 다운로드 하셨습니다.", Toast.LENGTH_LONG).show();
                }

//                if(wifi.isConnected()) {
//                    if (contentsVO.getDownload() == 0) {
//                        contentsVO.setDownload(1);
//                        contentsVO.setDownloadDate(System.currentTimeMillis());
//                        DataBaseHandler.getInstance(view.getContext()).updateDownload(contentsVO);
//
//                        downloadEditor.putString("url", MOVIE_URL);
//                        downloadEditor.putString("fileName", fileName);
//                        downloadEditor.commit();
//
//                        intent = new Intent("org.kccc.gateway.DownloadService");
//                        intent.setPackage(view.getContext().getPackageName());
//                        view.getContext().startService(intent);
//                    } else if (contentsVO.getDownload() == 1) {
//                        Toast.makeText(view.getContext(), "이미 다운로드 하셨습니다.", Toast.LENGTH_LONG).show();
//                    }
//                }
//                else{
//                    final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
//                    alert.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();     //닫기
//                        }
//                    });
//                    alert.setPositiveButton("네", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            if (contentsVO.getDownload() == 0) {
//                                contentsVO.setDownload(1);
//                                contentsVO.setDownloadDate(System.currentTimeMillis());
//                                DataBaseHandler.getInstance(view.getContext()).updateDownload(contentsVO);
//
//                                downloadEditor.putString("url", MOVIE_URL);
//                                downloadEditor.putString("fileName", fileName);
//                                downloadEditor.commit();
//
//                                Intent downIntent = new Intent("org.kccc.gateway.DownloadService");
//                                downIntent.setPackage(view.getContext().getPackageName());
//                                view.getContext().startService(downIntent);
//                            } else if (contentsVO.getDownload() == 1) {
//                                Toast.makeText(view.getContext(), "이미 다운로드 하셨습니다.", Toast.LENGTH_LONG).show();
//                            }
//                            dialog.dismiss();
//                        }
//                    });
//                    alert.setMessage("Wi-Fi 환경이 아닙니다. 그래도 다운로드 하시겠습니까?");
//                    alert.show();
//                }
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

                TextView question1 = (TextView) dialog.findViewById(R.id.question1);
                TextView question2 = (TextView) dialog.findViewById(R.id.question2);
                TextView title1 = (TextView) dialog.findViewById(R.id.title1);
                TextView title2 = (TextView) dialog.findViewById(R.id.title2);

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

                ImageButton button = (ImageButton) dialog.findViewById(R.id.button_finish);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Window window = dialog.getWindow();
                WindowManager.LayoutParams params = window.getAttributes();
                WindowManager windowManager = (WindowManager) view.getContext().getSystemService(view.getContext().WINDOW_SERVICE);
                Display display = windowManager.getDefaultDisplay();
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

//        realm.close();
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

//                        percent *= 100;
                        circularProgressBar.setProgress(percent*100);

//                      circularProgressBar.setProgressWithAnimation(100 * percent, animationDuration); // Default duration = 1500ms

                        Log.d("download INFO", "onUIProgressChanged: "+"numBytes:" + numBytes + " bytes" + "\ntotalBytes:" + totalBytes + " bytes" + "\npercent:" + percent * 100 + " %" + "\nspeed:" + speed * 1000 / 1024 / 1024 + " MB/秒");
                    }

                    //if you don't need this method, don't override this methd. It isn't an abstract method, just an empty method.
                    @Override
                    public void onUIProgressFinish() {
                        super.onUIProgressFinish();
                        download.setVisibility(View.VISIBLE);
                        circularProgressBar.setVisibility(View.INVISIBLE);


                        contentsVO.setDownload(1);
                        contentsVO.setDownloadDate(System.currentTimeMillis());

                        RealmHelper realmHelper = new RealmHelper();
                        realmHelper.saveContents(contentsVO);

                        setVideoPath(contentsVO);

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
            //Event case check - [ ]
            switch (type) {
                case JZUserAction.ON_CLICK_START_ICON:
                    Log.d("JZplayer play", "onEvent: start!");

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