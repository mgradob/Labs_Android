package com.itesm.labs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itesm.labs.R;
import com.itesm.labs.rest.models.Cart;

import java.util.ArrayList;

/**
 * Created by miguel on 21/11/14.
 */
public class RequestsModelAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<Cart> DATA = new ArrayList<Cart>();

    public RequestsModelAdapter(Context context, ArrayList<Cart> DATA) {
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
            convertView = mLayoutInflater.inflate(R.layout.list_item_requests, parent, false);

            holder = new ViewHolder();
            holder.requestImage = (ImageView) convertView.findViewById(R.id.request_item_image);
            holder.requestUserName = (TextView) convertView.findViewById(R.id.request_item_user_name);
            holder.requestUserId = (TextView) convertView.findViewById(R.id.request_item_user_id);
            holder.requestUserDate = (TextView) convertView.findViewById(R.id.request_item_user_date);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.requestUserName.setText(DATA.get(position).getUserName());
        holder.requestUserId.setText(DATA.get(position).getUserId());
        holder.requestUserDate.setText(DATA.get(position).getCartDate());

        if (DATA.get(position).isReady()) {
            holder.requestImage.setImageResource(R.drawable.ic_done_white);
            holder.requestImage.setBackground(context.getResources().getDrawable(R.drawable.request_indicator_done));
        } else {
            holder.requestImage.setImageResource(R.drawable.ic_cancel_white);
            holder.requestImage.setBackground(context.getResources().getDrawable(R.drawable.request_indicator_pending));
        }

        return convertView;
    }

    public void refreshList(ArrayList<Cart> newData) {
        DATA = newData;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView requestImage;
        TextView requestUserName;
        TextView requestUserId;
        TextView requestUserDate;
    }
}
