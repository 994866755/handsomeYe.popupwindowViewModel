package com.example.kylinarm.popupwindowterminator.viewmodel.popwinviewmodel;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.kylinarm.popupwindowterminator.PopupWindowBaseViewModel;
import com.example.kylinarm.popupwindowterminator.PopupWindowBuilder;
import com.example.kylinarm.popupwindowterminator.R;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kylinARM on 2017/8/20.
 */

public class EditPopWinViewModel extends PopupWindowBaseViewModel{

    @InjectView(R.id.edt_content)
    EditText edtContent;
    @InjectView(R.id.btn_sure)
    Button btnSure;

    public EditPopWinViewModel(PopupWindowBuilder builder) {
        super(builder);
    }

    @Override
    protected PopupWindow getPopupWindow() {
        return new PopupWindow(contentView,width,hight);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_popwin_edit;
    }

    @Override
    protected void initView() {
        btnSure.setEnabled(false);
        edtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edtContent.getText().toString().length() > 0){
                    btnSure.setEnabled(true);
                    btnSure.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                }else {
                    btnSure.setEnabled(false);
                    btnSure.setBackgroundColor(context.getResources().getColor(R.color.gray));
                }
            }
        });
    }

    @Override
    protected void setDataToView(Object data) {

    }

    @OnClick(R.id.btn_sure)
    public void click(){
        popupwindow.dismiss();
        Toast.makeText(context,edtContent.getText().toString(),Toast.LENGTH_SHORT).show();
    }

}
