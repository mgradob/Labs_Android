package com.itesm.labs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.itesm.labs.R;
import com.itesm.labs.rest.models.CartItem;
import com.itesm.labs.rest.models.Category;
import com.itesm.labs.rest.models.Component;

import java.util.ArrayList;

/**
 * Created by miguel on 26/10/14.
 */
public class RequestItemModelAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<CartItem> DATA = new ArrayList<>();
    private ArrayList<Category> CATEGORIES_DATA = new ArrayList<>();
    private ArrayList<Component> COMPONENTS_DATA = new ArrayList<>();

    public RequestItemModelAdapter(Context context, ArrayList<CartItem> DATA, ArrayList<Category> CATEGORIES_DATA, ArrayList<Component> COMPONENTS_DATA) {
        this.context = context;
        this.DATA = DATA;
        this.CATEGORIES_DATA = CATEGORIES_DATA;
        this.COMPONENTS_DATA = COMPONENTS_DATA;
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
        Category tempCategory = CATEGORIES_DATA.get(position);
        Component tempComponent = COMPONENTS_DATA.get(position);

        holder.component_category.setText(tempCategory.getName());
        holder.component_name.setText(tempComponent.getName() + " " + tempComponent.getNote());
        holder.component_total.setText(" " + tempCartItem.getQuantity());

        return convertView;
    }

    static class ViewHolder {
        TextView component_category, component_name, component_total;
    }
}
