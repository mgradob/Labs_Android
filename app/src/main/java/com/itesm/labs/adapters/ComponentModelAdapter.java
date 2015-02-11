package com.itesm.labs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itesm.labs.R;
import com.itesm.labs.rest.models.Component;

import java.util.ArrayList;

/**
 * Created by miguel on 26/10/14.
 */
public class ComponentModelAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<Component> DATA = new ArrayList<>();

    public ComponentModelAdapter(Context context, ArrayList<Component> data) {
        this.context = context;
        this.DATA = data;
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
            convertView = mLayoutInflater.inflate(R.layout.inventory_list_item_component, parent, false);

            holder = new ViewHolder();
            holder.component_name = (TextView) convertView.findViewById(R.id.inventory_item_component_name);
            holder.component_note = (TextView) convertView.findViewById(R.id.inventory_item_component_note);
            holder.component_total = (TextView) convertView.findViewById(R.id.inventory_item_component_total);
            holder.component_available = (TextView) convertView.findViewById(R.id.inventory_item_component_available);

            //convertView.startAnimation(new AnimationUtils().loadAnimation(context, R.anim.categories_gridview_anim));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.component_name.setText(DATA.get(position).getName());
        holder.component_note.setText(DATA.get(position).getNote());
        holder.component_total.setText("" + DATA.get(position).getTotal());
        holder.component_available.setText("" + DATA.get(position).getAvailable());

        return convertView;
    }

    static class ViewHolder {
        TextView component_name, component_note, component_total, component_available;
    }
}
