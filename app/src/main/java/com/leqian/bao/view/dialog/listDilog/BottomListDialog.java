package com.leqian.bao.view.dialog.listDilog;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.leqian.bao.R;
import com.leqian.bao.common.util.DeviceUtil;
import com.nxin.base.view.dialog.BaseDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * 底部列表提示框
 */
public class BottomListDialog extends BaseDialog implements AdapterView.OnItemClickListener {

    private List<String> nameList;                                              //数据源:字符串
    private ListView listview;
    private BottomListAdapter adapter;
    private IBottomListItemListener listener;


    public BottomListDialog(Context context, int style, List<String> nameList) {
        super(context, style);
        this.nameList = nameList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_list_bottom, null);
        listview = view.findViewById(R.id.listView);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = DeviceUtil.getScreenWidth();
        getWindow().setAttributes(p);

        getWindow().getAttributes().gravity = Gravity.BOTTOM;

        //设置动画
        getWindow().getAttributes().windowAnimations = R.style.mypopwindow_anim_style;


        adapter = new BottomListAdapter(mContext, nameList);
//        if (nameList.size() == 0) {
//            adapter.setCreditList(creditListData);
//        }
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listener.onBottomListItemListener(position);
    }

    public void setOnBottomListItemListener(IBottomListItemListener listener) {
        this.listener = listener;
    }

    public interface IBottomListItemListener {
        void onBottomListItemListener(int position);
    }
}
