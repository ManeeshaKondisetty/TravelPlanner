package com.umkc.travelplanner.explore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.umkc.travelplanner.R;
import com.umkc.travelplanner.eat.Venue;

import java.util.ArrayList;

public class ActivityAdapter extends ArrayAdapter<Activity> {
    private Context mContext;
    private int mResource;

    public ActivityAdapter(@NonNull Context context, int resource,  @NonNull ArrayList<Activity> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Activity a = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView name = (TextView) convertView.findViewById(R.id.fullName);
        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView a1 = (TextView) convertView.findViewById(R.id.a1);
        TextView a2 = (TextView) convertView.findViewById(R.id.a2);
        TextView a3 = (TextView) convertView.findViewById(R.id.a3);
        TextView a4 = (TextView) convertView.findViewById(R.id.a4);

        name.setText(a.getFullName());
        description.setText(a.getDescription());
        int length = a.getActivities().length;
        if(length > 0)
            a1.setText(a.getActivities()[0]);
        if(length>1)
            a2.setText(a.getActivities()[1]);
        if(length>2)
            a3.setText(a.getActivities()[2]);
        if(length>3)
            a4.setText(a.getActivities()[3]);





        return convertView;
    }
}
