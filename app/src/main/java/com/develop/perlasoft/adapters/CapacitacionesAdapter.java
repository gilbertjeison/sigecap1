package com.develop.perlasoft.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.develop.perlasoft.model.CapacitacionesData;
import com.develop.perlasoft.sigecap.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CapacitacionesAdapter extends RecyclerView.Adapter<CapacitacionesAdapter.MyViewHolder>{
    private Context context;
    private List<CapacitacionesData> list;
    private final OnPressHandler longPressHandler;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener
    {

        @BindView(R.id.txtNameItem)
        public TextView txtNombre;
        @BindView(R.id.txtTemas)
        public TextView txtTemas;
        @BindView(R.id.txtDuracion)
        public TextView txtDuracion;
        @BindView(R.id.txtNumAsistentes)
        public TextView txtAsistentes;
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
            CapacitacionesData cD = list.get(position);
            longPressHandler.onLongPress(cD);

            return true;
        }
    }

    public CapacitacionesAdapter(Context context, List<CapacitacionesData> list, OnPressHandler opa) {
        this.context = context;
        this.list = list;
        this.longPressHandler = opa;
    }

    public interface OnPressHandler{
        void onLongPress(CapacitacionesData cD);
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
        final CapacitacionesData cdata = list.get(position);

        holder.txtNombre.setText(cdata.nombre);
        holder.txtTemas.setText("TEMAS: "+cdata.temas);
        holder.txtDuracion.setText(cdata.duracion);
        holder.txtAsistentes.setText(cdata.asistentes);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
