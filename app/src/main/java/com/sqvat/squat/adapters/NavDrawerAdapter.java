package com.sqvat.squat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sqvat.squat.R;

/**
 * Created by GAL on 1/22/2015.
 */
public class NavDrawerAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private String[] categories;
    private int[] icons;

    private class ViewHolder {
        ImageView icon;
        TextView category;
    }

    public NavDrawerAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.categories = new String[]{"Your routine", "History", "Achievements", "Settings"};
        this.icons = new int[]{R.drawable.ic_star, R.drawable.ic_history, R.drawable.ic_medal, R.drawable.ic_settings};
    }

    public int getCount() {
        return categories.length;
    }

    public String getItem(int position) {
        return categories[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.nav_drawer_li, null);
            holder.icon = (ImageView) convertView.findViewById(R.id.drawe_li_icon);
            holder.category = (TextView) convertView.findViewById(R.id.drawer_li_category);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.icon.setImageResource(icons[position]);
        holder.category.setText(getItem(position));

        return convertView;
    }

}
