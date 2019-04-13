package com.leqian.bao.view.dialog.listDilog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.leqian.bao.R;

import java.util.List;

/**
 * Created by fcl on 2017/8/17.
 */

public class BottomListAdapter extends BaseAdapter {

    private List<String> listData;
    private Context mContext;
    private boolean isShowDesc = true;

    public BottomListAdapter(Context mContext, List<String> listData) {
        this.listData = listData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if (listData.size() > 0) {
            return listData.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (listData.size() > 0) {
            return listData.get(position);
        } else {
            return null;
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BottomListViewHolder holder;
        if (convertView == null) {
            holder = new BottomListViewHolder();
            convertView = View.inflate(mContext, R.layout.item_bottom_dialog_list, null);
            holder.lv_item_margin = convertView.findViewById(R.id.lv_item_margin);
            holder.bottom_list_tv = convertView.findViewById(R.id.bottom_list_tv);
            holder.bottom_list_tv_desc = convertView.findViewById(R.id.bottom_list_tv_desc);
            convertView.setTag(holder);
        } else {
            holder = (BottomListViewHolder) convertView.getTag();
        }

        //show data
        if (listData.size() > 0) {
            if (position == listData.size() - 1) {
                holder.lv_item_margin.setVisibility(View.VISIBLE);
            } else {
                holder.lv_item_margin.setVisibility(View.GONE);
            }
            holder.bottom_list_tv.setText(listData.get(position));
        }
        return convertView;
    }


    class BottomListViewHolder {
        View lv_item_margin;
        TextView bottom_list_tv;
        TextView bottom_list_tv_desc;
    }
}
