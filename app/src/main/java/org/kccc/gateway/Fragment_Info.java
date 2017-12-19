package org.kccc.gateway;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by LG on 2016-06-09.
 * information 탭을 눌렀을 때 보이는 프래그먼트
 */
public class Fragment_Info extends Fragment implements View.OnClickListener{
    private View view;
    private TextView info;
    private ImageButton mailBtn;
    private ImageButton kakaotalkBtn;
    private ImageButton facebookBtn;
    private String shareURL;
    private Intent intent;
    private Intent chooserIntent;
    private List<ResolveInfo> resInfo;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // we have no menu item to show in action bar
        setHasOptionsMenu(false);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_info, container, false);
        shareURL = "https://play.google.com/store/apps/details?id="+view.getContext().getPackageName();

        info = (TextView)view.findViewById(R.id.infoView);
        mailBtn = (ImageButton)view.findViewById(R.id.mailBtn);
        kakaotalkBtn = (ImageButton)view.findViewById(R.id.kakaotalkBtn);
        facebookBtn = (ImageButton)view.findViewById(R.id.facebookBtn);
        info.setText("게이트웨이 앱은 짧은 영상(Short Film, Jesus Film)을 본 후, 질문과 대화를 통해 상대방의 생각이나 영적인 필요를 파악하고 마음을 여는 대화를 나누면서 복음을 전할 수 있는 ‘미디어전도’ 도구입니다.\n" +
                "\n" +
                "이 앱을 통해 영적인 대화를 이끌어 가는데 도움이 되는 좋은 영상을 편리하게 활용할 수 있습니다.\n" +
                "각 영상을 보고 난 후 대화에 도움을 주는 질문들이 어플안에 준비되어 있습니다. 상대방의 이야기를 경청하고 공감하는 것은 마음을 여는데 많은 도움이 됩니다. 우리 삶에 친숙한 스마트폰과 영상미디어를 활용하여 복음을 전하는데 도움이 되길 바랍니다.\n" +
                "\n" +
                "자세한 문의나 오류 신고는 다음의 이메일을 이용해 주시기 바랍니다.\n" +
                "\n" +
                "이메일: cccvlm@kccc.org");
        mailBtn.setOnClickListener(this);
        kakaotalkBtn.setOnClickListener(this);
        facebookBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mailBtn:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");

                resInfo = view.getContext().getPackageManager().queryIntentActivities(intent, 0);
                if (resInfo.isEmpty()) {
                    return;
                }
                for (ResolveInfo info : resInfo) {
                    Intent shareIntent = (Intent) intent.clone();
                    if (info.activityInfo.packageName.toLowerCase().equals("com.google.android.gm")){
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareURL);
                        shareIntent.setPackage(info.activityInfo.packageName);
                        startActivity(shareIntent);
                        break;
                    }
                }
                /*intent = new Intent(Intent.ACTION_SEND);
                intent.setType("application/octet-stream");
                intent.putExtra(Intent.EXTRA_TEXT, shareURL);
                view.getContext().startActivity(intent);*/
                break;
            case R.id.kakaotalkBtn:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");

                resInfo = view.getContext().getPackageManager().queryIntentActivities(intent, 0);
                if (resInfo.isEmpty()) {
                    return;
                }
                for (ResolveInfo info : resInfo) {
                    Intent shareIntent = (Intent) intent.clone();
                    if (info.activityInfo.packageName.toLowerCase().equals("com.kakao.talk")){
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareURL);
                        shareIntent.setPackage(info.activityInfo.packageName);
                        startActivity(shareIntent);
                        break;
                    }
                }

                break;
            case R.id.facebookBtn:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");

                resInfo = view.getContext().getPackageManager().queryIntentActivities(intent, 0);
                if (resInfo.isEmpty()) {
                    return;
                }
                for (ResolveInfo info : resInfo) {
                    Intent shareIntent = (Intent) intent.clone();
                    if (info.activityInfo.packageName.toLowerCase().equals("com.facebook.katana")) {
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareURL);
                        shareIntent.setPackage(info.activityInfo.packageName);
                        startActivity(shareIntent);
                        break;
                    }
                }
                break;
        }
    }
}
