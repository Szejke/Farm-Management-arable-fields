package com.example.farm.myfarms.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farm.myfarms.R;

import java.util.ArrayList;


/**
 * Created by cotam on 13.11.2018.
 */

public class FieldListAdapter extends ArrayAdapter<String> {
    ArrayList<String> names;
    ImageView flags;
    Context mContext;

    public FieldListAdapter(Context context, ArrayList<String> countryNames, ImageView countryFlags) {
        super(context, R.layout.list_view_field);
        this.names = countryNames;
        this.flags = countryFlags;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_view_field, parent, false);
            mViewHolder.mFlag = (ImageView) convertView.findViewById(R.id.imageListWeather);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.mFlag = flags;
        mViewHolder.mName.setText(names.get(position));

        return convertView;
    }

    static class ViewHolder {
        ImageView mFlag;
        TextView mName;
    }

}
