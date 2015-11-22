package com.itesm.labs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.itesm.labs.R;
import com.itesm.labs.rest.models.CartItem;

import java.util.ArrayList;

/**
 * Created by miguel on 26/10/14.
 */
public class RequestItemModelAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<CartItem> DATA = new ArrayList<>();

    public RequestItemModelAdapter(Context context, ArrayList<CartItem> DATA) {
        this.context = context;
        this.DATA = DATA;
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

        ViewHolder holder;
        LayoutInflater mLayoutInflater = (LayoutInflater.from(context));

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item_request_item, parent, false);

            holder = new ViewHolder();
            holder.component_category = (TextView) convertView.findViewById(R.id.request_item_list_component_category);
            holder.component_name = (TextView) convertView.findViewById(R.id.request_item_list_component_name);
            holder.component_total = (TextView) convertView.findViewById(R.id.request_item_list_component_total);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CartItem tempCartItem = DATA.get(position);

        holder.component_category.setText(tempCartItem.getCategory().getName());
        holder.component_name.setText(tempCartItem.getComponent().getName() + " " + tempCartItem.getComponent().getNote());
        holder.component_total.setText(" " + tempCartItem.getQuantity());

        return convertView;
    }

    public void refresh(ArrayList<CartItem> newData) {
        DATA = newData;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView component_category;
        TextView component_name;
        TextView component_total;
    }
}
