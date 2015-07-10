package com.itesm.labs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.itesm.labs.R;
import com.itesm.labs.rest.models.History;

import java.util.ArrayList;

/**
 * Created by miguel on 26/10/14.
 */
public class HistoryModelAdapter extends BaseAdapter {

    private Context context;
    private int[] colors;

    private ArrayList<History> DATA = new ArrayList<>();

    public HistoryModelAdapter(Context context, ArrayList<History> data) {
        this.context = context;
        this.DATA = data;
        this.colors = context.getResources().getIntArray(R.array.material_colors);
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
            convertView = mLayoutInflater.inflate(R.layout.list_item_detail_history, parent, false);

            holder = new ViewHolder();
            holder.category_name = (TextView) convertView.findViewById(R.id.detail_history_item_category_name);
            holder.component_name_note = (TextView) convertView.findViewById(R.id.detail_history_item_component_name_note);
            holder.component_quantity = (TextView) convertView.findViewById(R.id.detail_history_item_component_quantity);
            holder.date_out = (TextView) convertView.findViewById(R.id.detail_history_item_date_out);
            holder.date_in = (TextView) convertView.findViewById(R.id.detail_history_item_date_in);

            //convertView.startAnimation(new AnimationUtils().loadAnimation(context, R.anim.categories_gridview_anim));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.category_name.setText(DATA.get(position).getCategoryName());
        holder.component_name_note.setText(DATA.get(position).getComponentNameNote());
        holder.component_quantity.setText("" + DATA.get(position).getQuantity());
        holder.date_out.setText("Fecha pedido:\n" + DATA.get(position).getDateOut());
        holder.date_in.setText("Fecha entregado:\n" + DATA.get(position).getDateIn());

        return convertView;
    }

    static class ViewHolder {
        TextView category_name, component_name_note, component_quantity, date_out, date_in;
    }
}
