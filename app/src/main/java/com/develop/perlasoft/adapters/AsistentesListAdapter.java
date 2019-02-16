package com.develop.perlasoft.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.develop.perlasoft.entities.Asistentes;
import com.develop.perlasoft.model.AsistentesData;
import com.develop.perlasoft.sigecap.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AsistentesListAdapter extends RecyclerView.Adapter<AsistentesListAdapter.MyViewHolder> {
    private Context context;
    private List<AsistentesData> list;
    private final AsistentesAdapterOnLongPressHandler longPressHandler;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener
    {
        @BindView(R.id.txtNameItem)
        public TextView txtNombres;
        @BindView(R.id.txtCcItem)
        public TextView txtCc;
        @BindView(R.id.txtAreaItem)
        public TextView txtArea;
        @BindView(R.id.txtCargoItem)
        public TextView txtCargo;
        @BindView(R.id.thumbnail)
        public ImageView image;
        @BindView(R.id.view_foreground)
        RelativeLayout viweForeground;

        public MyViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this,view);


            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            AsistentesData assD = list.get(position);
            longPressHandler.onLongPress(assD);

            return true;
        }
    }

    public AsistentesListAdapter(Context context, List<AsistentesData> list, AsistentesAdapterOnLongPressHandler aaopa) {
        this.context = context;
        this.list = list;
        this.longPressHandler = aaopa;
    }

    public interface AsistentesAdapterOnLongPressHandler{
        void onLongPress(AsistentesData asistentesData);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_asistentes_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AsistentesData asistentes = list.get(position);

        holder.txtNombres.setText(asistentes.nombres);
        holder.txtCc.setText("C.C: "+asistentes.cedula);
        holder.txtArea.setText(asistentes.area);
        holder.txtCargo.setText(asistentes.cargo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




}
