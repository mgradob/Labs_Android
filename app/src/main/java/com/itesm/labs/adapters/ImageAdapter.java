package com.itesm.labs.adapters;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.itesm.labs.R;

import java.util.ArrayList;

/**
 * Created by miguel on 31/10/14.
 */
public class ImageAdapter extends BaseAdapter{

    private Context mContext;

    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return images.length;
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
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(this.images[position]);

        return imageView;
    }

    Integer[] images = {
            R.drawable.ic_dummy_category,
            R.drawable.ic_dummy_category,
            R.drawable.ic_dummy_category,
            R.drawable.ic_dummy_category,
            R.drawable.ic_dummy_category
    };
}
