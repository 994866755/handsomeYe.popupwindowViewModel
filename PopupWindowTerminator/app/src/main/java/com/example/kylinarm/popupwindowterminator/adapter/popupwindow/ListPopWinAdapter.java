package com.example.kylinarm.popupwindowterminator.adapter.popupwindow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kylinarm.popupwindowterminator.R;
import com.example.kylinarm.popupwindowterminator.entity.popupwindow.ListPopWinEntiyt;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kylinARM on 2017/8/20.
 */

public class ListPopWinAdapter extends RecyclerView.Adapter<ListPopWinAdapter.ListPopWinViewHolder> {

    private List<ListPopWinEntiyt> datalist;
    private Context context;

    public ListPopWinAdapter(Context context, List<ListPopWinEntiyt> datalist){
        this.context = context;
        this.datalist = datalist;
    }

    @Override
    public ListPopWinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListPopWinViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_textview,null));
    }

    @Override
    public void onBindViewHolder(ListPopWinViewHolder holder, int position) {
        holder.data = datalist.get(position);
        holder.setDataToView();
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ListPopWinViewHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.tv_content)
        TextView tvContent;

        public ListPopWinEntiyt data;

        public ListPopWinViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);
        }

        public void setDataToView(){
            tvContent.setText(data.content);
        }

    }

}
