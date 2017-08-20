package com.example.kylinarm.popupwindowterminator;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.example.kylinarm.popupwindowterminator.entity.popupwindow.ListPopWinEntiyt;
import com.example.kylinarm.popupwindowterminator.viewmodel.popwinviewmodel.EditPopWinViewModel;
import com.example.kylinarm.popupwindowterminator.viewmodel.popwinviewmodel.ListPopWinViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private PopupWindowBaseViewModel pwViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R.id.btn_editpw,R.id.btn_listpw})
    public void click(View v){
        switch (v.getId()){
            case R.id.btn_editpw:
                pwViewModel = new EditPopWinViewModel(new PopupWindowBuilder(
                        this, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
                        .setAnchor(v.getRootView())
                        .setGravityAt(Gravity.BOTTOM)
                        .builder()
                );
                pwViewModel.showAt();
                break;
            case R.id.btn_listpw:
                pwViewModel = new ListPopWinViewModel(new PopupWindowBuilder(
                        this, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
                        .setAnchor(v)
                        .setGravityAs(Gravity.CENTER)
                        .builder()
                );
                //假设获取数据
                setData();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setData(){
        List<ListPopWinEntiyt> datalist = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ListPopWinEntiyt entity = new ListPopWinEntiyt();
            entity.content = "item" + i;
            entity.id = "123" + i;
            datalist.add(entity);
        }
        pwViewModel.setDataToView(datalist);
        pwViewModel.showAs();
    }

}
