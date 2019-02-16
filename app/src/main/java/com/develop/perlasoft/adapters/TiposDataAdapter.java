package com.develop.perlasoft.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.develop.perlasoft.entities.TiposData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TiposDataAdapter extends ArrayAdapter<TiposData> {

    private Context context;
    private List<TiposData> list;

    public TiposDataAdapter(@NonNull Context context, int resource, @NonNull List<TiposData> objects) {
        super(context, resource, objects);

        this.context = context;
        list = objects;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public TiposData getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).id;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }
}
