package com.winson.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by zhou on 2014/10/6.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected int layout;
    protected List<T> datas;
    protected Context context;

    public CommonAdapter(Context context, int layout, List<T> datas) {
        this.context = context;
        this.datas = datas;
        this.layout = layout;
    }

    public List<T> getData() {
        return datas;
    }

    public void replaceData(List<T> datas) {
        if (this.datas == null) {
            this.datas = datas;
        } else {
            this.datas.clear();
            if (datas != null) {
                this.datas.addAll(datas);
            }
        }
        notifyDataSetChanged();
    }

    public void addData(List<T> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addData(T data) {
        this.datas.add(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (datas == null) {
            return 0;
        }
        return datas.size();
    }

    @Override
    public T getItem(int i) {
        if (datas == null) {
            return null;
        }
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(layout, view, viewGroup);
        T data = getItem(i);
        if (data != null) {
            convert(viewHolder, data, i);
        }
        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder viewHolder, T obj, int position);

}
