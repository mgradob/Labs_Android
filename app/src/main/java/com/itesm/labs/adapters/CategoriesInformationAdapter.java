package com.itesm.labs.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.itesm.labs.R;
import com.itesm.labs.content.CategoriesContent;
import com.itesm.labs.models.CategoryInformation;
import com.itesm.labs.rest.models.Category;
import com.itesm.labs.rest.models.CategoryWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miguel on 26/10/14.
 */
public class CategoriesInformationAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CategoryInformation> DATA;

    public CategoriesInformationAdapter(Context context, ArrayList<CategoryInformation> info) {
        this.context = context;
        this.DATA = info;

        Log.d("ADAPTER", "adapter created");
        Log.d("ADAPTER", "context: " + context.toString());
        Log.d("ADAPTER", "data: " + info.toString());
    }

    public ArrayList<CategoryInformation> getDATA() {
        return DATA;
    }

    public void setDATA(ArrayList<CategoryInformation> DATA) {
        this.DATA = DATA;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolderItem;

        // When converterView isn't null we can reuse it, only inflate it when the convertedView supplied by listView is null.
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.category_list_item, parent, false);

            viewHolderItem = new ViewHolder();
            viewHolderItem.holderImageView = (ImageView) convertView.findViewById(R.id.category_item_icon);
            viewHolderItem.holderTextView = (TextView) convertView.findViewById(R.id.category_item_text);

            convertView.setTag(viewHolderItem);
        } else {
            viewHolderItem = (ViewHolder) convertView.getTag();
        }

        CategoryInformation categoryInformation = DATA.get(position);

        if (categoryInformation != null) {
            viewHolderItem.holderImageView.setImageResource(categoryInformation.getImageResource());
            viewHolderItem.holderTextView.setText(categoryInformation.getTitle());
        }

        Log.d("ADAPTER", "returning view");
        return convertView;
    }

    static class ViewHolder {
        ImageView holderImageView;
        TextView holderTextView;
    }
}
