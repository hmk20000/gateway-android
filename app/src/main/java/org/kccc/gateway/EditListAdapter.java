package org.kccc.gateway;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * Created by LG on 2016-02-16.
 * Fragment MyVideos_Edit 클래스에서 사용하는 리스트 아이템을 리스테에 연결시키는 클래스
 */
public class EditListAdapter extends RecyclerView.Adapter<EditListHolder>  {
    private Context context;
    private List<ContentsVO> itemList;
    private int flag;

    public EditListAdapter(Context context, List<ContentsVO> itemList, int flag) {
        this.context = context;
        this.itemList = itemList;
        this.flag = flag;
    }

    @Override
    public EditListHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category_list_thumbnail, viewGroup, false);
        return new EditListHolder(view);
    }

    @Override
    public void onBindViewHolder(final EditListHolder holder, final int position) {
        holder.Thumbnail.setImageDrawable(new ThumbnailHandler(context).
                setThumbnail(itemList.get(position).getCategory(),itemList.get(position).getIndex()));
        holder.Title.setText(itemList.get(position).getTitle());
        holder.subTitle.setText(itemList.get(position).getSubTitle());

        holder.Thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContentsVO contentsVO = new ContentsVO();
                        contentsVO.setCategory(itemList.get(position).getCategory());
                        contentsVO.setIndex(itemList.get(position).getIndex());
                        contentsVO.setFavorite(0);
                        contentsVO.setFavoriteDate(0);
                        /*contentsVO.setHistory(0);
                        contentsVO.setHistoryDate(0);
                        contentsVO.setDownload(0);
                        contentsVO.setDownloadDate(0);
                        if(flag == 0)
                            DataBaseHandler.getInstance(context).updateFavorite(contentsVO);
                        else if(flag == 1)
                            DataBaseHandler.getInstance(context).updateHistory(contentsVO);
                        else if(flag == 2)
                            DataBaseHandler.getInstance(context).updateDownload(contentsVO);
*/
                        ((MainActivity)context).fragmentReplaceWithMyVideoEdit(flag);
                    }
                });
                alert.setMessage("정말로 지우시겠습니까?");
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (itemList.size() > 0) ? itemList.size() : 0;
    }
}
