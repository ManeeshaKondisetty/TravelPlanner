package com.umkc.travelplanner.eat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.umkc.travelplanner.R;
import java.util.ArrayList;

public class CustomAdaptor extends ArrayAdapter<Venue> {
    private Context mContext;
    private int mResource;
    public CustomAdaptor(@NonNull Context context, int resource, @NonNull ArrayList<Venue> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String contexLine = getItem(position).getContextLine();
        String formattedAddress = getItem(position).getFormattedaAddress();


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView nameView = (TextView) convertView.findViewById(R.id.name);
        TextView addressView = (TextView) convertView.findViewById(R.id.address);
        TextView placeView = (TextView) convertView.findViewById(R.id.place);

        nameView.setText(name);
        placeView.setText(contexLine);
        addressView.setText(formattedAddress);

        return convertView;
    }
}