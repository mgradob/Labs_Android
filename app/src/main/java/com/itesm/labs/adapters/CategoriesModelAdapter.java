package com.itesm.labs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itesm.labs.R;
import com.itesm.labs.rest.models.Category;

import java.util.ArrayList;

/**
 * Created by miguel on 26/10/14.
 */
public class CategoriesModelAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<Category> DATA = new ArrayList<Category>();

    public CategoriesModelAdapter(Context context) {
        this.context = context;
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
            convertView = mLayoutInflater.inflate(R.layout.list_item_inventory_category, parent, false);

            holder = new ViewHolder();
            holder.category_image = (ImageView) convertView.findViewById(R.id.inventory_item_category_icon);
            holder.category_name = (TextView) convertView.findViewById(R.id.inventory_item_category_text);

            //convertView.startAnimation(new AnimationUtils().loadAnimation(context, R.anim.categories_gridview_anim));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.category_image.setImageResource(DATA.get(position).getImageResource());
        holder.category_name.setText(DATA.get(position).getName());

        return convertView;
    }

    public void refreshList(ArrayList<Category> newData) {
        DATA = newData;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView category_image;
        TextView category_name;
    }
}
