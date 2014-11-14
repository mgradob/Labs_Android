package com.itesm.labs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.itesm.labs.R;
import com.itesm.labs.models.CategoryInformation;

import java.util.ArrayList;

/**
 * Created by miguel on 26/10/14.
 */
public class CategoriesInformationAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<CategoryInformation> DATA = new ArrayList<CategoryInformation>();

    public CategoriesInformationAdapter(Context context, ArrayList<CategoryInformation> data) {
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
            convertView = mLayoutInflater.inflate(R.layout.category_list_item, parent, false);

            holder = new ViewHolder();
            holder.category_image = (ImageView) convertView.findViewById(R.id.category_item_icon);
            holder.category_name = (TextView) convertView.findViewById(R.id.category_item_text);

            convertView.startAnimation(new AnimationUtils().loadAnimation(context, R.anim.categories_gridview_anim));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.category_image.setImageResource(DATA.get(position).getImageResource());
        holder.category_name.setText(DATA.get(position).getTitle());

        return convertView;
    }

    static class ViewHolder {
        ImageView category_image;
        TextView category_name;
    }
}
