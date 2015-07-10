package com.itesm.labs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itesm.labs.R;
import com.itesm.labs.rest.models.Laboratory;

import java.util.ArrayList;

/**
 * Created by miguel on 15/11/14.
 */
public class LaboratoriesModelAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<Laboratory> DATA = new ArrayList<Laboratory>();

    public LaboratoriesModelAdapter(Context context, ArrayList<Laboratory> DATA) {
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
            convertView = mLayoutInflater.inflate(R.layout.list_item_laboratory, parent, false);

            holder = new ViewHolder();
            holder.laboratory_image = (ImageView) convertView.findViewById(R.id.laboratory_item_icon);
            holder.laboratory_name = (TextView) convertView.findViewById(R.id.laboratory_item_text);

            //convertView.startAnimation(new AnimationUtils().loadAnimation(context, R.anim.categories_gridview_anim));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.laboratory_image.setImageResource(DATA.get(position).getImageResource());
        holder.laboratory_name.setText(DATA.get(position).getName());

        return convertView;
    }

    static class ViewHolder {
        ImageView laboratory_image;
        TextView laboratory_name;
    }
}
