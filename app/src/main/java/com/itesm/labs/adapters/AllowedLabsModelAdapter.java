package com.itesm.labs.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.itesm.labs.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mgradob on 11/16/15.
 */
public class AllowedLabsModelAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<String> DATA = new ArrayList<>();

    public AllowedLabsModelAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return DATA.size();
    }

    @Override
    public Object getItem(int position) {
        return DATA.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater.from(mContext));
            convertView = inflater.inflate(R.layout.list_item_allowed_lab, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.laboratory.setText(DATA.get(position));

        return convertView;
    }

    public void refresh(ArrayList<String> newData) {
        DATA = newData;
        notifyDataSetChanged();
    }

    class ViewHolder {
        @Bind(R.id.allowed_lab_item)
        AppCompatCheckBox laboratory;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
