package com.itesm.labs.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.itesm.labs.R;
import com.itesm.labs.models.UserModel;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by miguel on 26/10/14.
 */
public class UsersModelAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<UserModel> DATA = new ArrayList<UserModel>();
    int[] colors;

    public UsersModelAdapter(Context context, ArrayList<UserModel> data) {
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
            convertView = mLayoutInflater.inflate(R.layout.users_list_item, parent, false);

            holder = new ViewHolder();
            holder.user_initial = (TextView) convertView.findViewById(R.id.user_item_user_initial);
            holder.user_name = (TextView) convertView.findViewById(R.id.user_item_user_name);
            holder.user_id = (TextView) convertView.findViewById(R.id.user_item_user_id);
            holder.user_career = (TextView) convertView.findViewById(R.id.user_item_user_carrer);

            //convertView.startAnimation(new AnimationUtils().loadAnimation(context, R.anim.categories_gridview_anim));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        char[] letters = DATA.get(position).getFirstName().toCharArray();
        holder.user_initial.setText("" + letters[0]);
        GradientDrawable gradientDrawable = (GradientDrawable) holder.user_initial.getBackground();
        gradientDrawable.setColor(DATA.get(position).getBackgroundColor());

        holder.user_name.setText(DATA.get(position).getFullName());
        holder.user_id.setText(DATA.get(position).getUserId());
        holder.user_career.setText(DATA.get(position).getUserCareer());

        return convertView;
    }

    static class ViewHolder {
        TextView user_initial;
        TextView user_name;
        TextView user_id;
        TextView user_career;
    }
}
