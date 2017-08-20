package com.example.kylinarm.popupwindowterminator.viewmodel.popwinviewmodel;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.PopupWindow;

import com.example.kylinarm.popupwindowterminator.PopupWindowBaseViewModel;
import com.example.kylinarm.popupwindowterminator.PopupWindowBuilder;
import com.example.kylinarm.popupwindowterminator.R;
import com.example.kylinarm.popupwindowterminator.adapter.popupwindow.ListPopWinAdapter;
import com.example.kylinarm.popupwindowterminator.entity.popupwindow.ListPopWinEntiyt;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by kylinARM on 2017/8/20.
 */

public class ListPopWinViewModel extends PopupWindowBaseViewModel<List<ListPopWinEntiyt>> {

    @InjectView(R.id.rv_content)
    RecyclerView rvContent;

    private ListPopWinAdapter adapter;

    public ListPopWinViewModel(PopupWindowBuilder builder) {
        super(builder);
    }

    @Override
    protected PopupWindow getPopupWindow() {
        return new PopupWindow(contentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_popwin_list;
    }

    @Override
    protected void initPopView() {
        popupwindow.setWidth(width);
        popupwindow.setHeight(hight);
        super.initPopView();
    }

    @Override
    protected void initView() {
        rvContent.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    protected void setDataToView(List<ListPopWinEntiyt> data) {
        adapter = new ListPopWinAdapter(context,data);
        rvContent.setAdapter(adapter);
    }

}
