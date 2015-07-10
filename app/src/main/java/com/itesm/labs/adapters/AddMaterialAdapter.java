package com.itesm.labs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itesm.labs.R;

import java.util.ArrayList;

/**
 * Created by miguel on 26/10/14.
 */
public class AddMaterialAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> DATA;

    public AddMaterialAdapter(Context context, ArrayList<String> data) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater mLayoutInflater = (LayoutInflater.from(context));

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item_add_material, parent, false);

            holder = new ViewHolder();
            holder.addMaterialIcon = (ImageView) convertView.findViewById(R.id.add_material_item_icon);
            holder.addMaterialCategory = (TextView) convertView.findViewById(R.id.add_material_item_category);
            holder.addMaterialComponent = (TextView) convertView.findViewById(R.id.add_material_item_component);
            holder.addMaterialAvailable = (TextView) convertView.findViewById(R.id.add_material_item_available);
            holder.addMaterialButton = (ImageButton) convertView.findViewById(R.id.add_material_item_button);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //holder.addMaterialIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_dummy_category));
        holder.addMaterialCategory.setText(DATA.get(position));
        holder.addMaterialComponent.setText("Componente Dummy");
        holder.addMaterialAvailable.setText("53");
        holder.addMaterialButton.setBackground(context.getResources().getDrawable(R.drawable.add_material));
        holder.addMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        ImageView addMaterialIcon;
        TextView addMaterialCategory, addMaterialComponent, addMaterialAvailable;
        ImageButton addMaterialButton;
    }
}