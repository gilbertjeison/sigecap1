package com.develop.perlasoft.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.develop.perlasoft.entities.Capacitaciones;
import com.develop.perlasoft.model.TiposDataCk;
import com.develop.perlasoft.sigecap.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CargosMiniAdapter extends RecyclerView.Adapter<CargosMiniAdapter.MyViewHolder> {
    private Context context;
    private List<TiposDataCk> list;
    private final CmiAdapterOnPressHandler pressHandler;
    TiposDataCk aD;

    public CargosMiniAdapter(Context context, List<TiposDataCk> list, CmiAdapterOnPressHandler pressHandler) {
        this.context = context;
        this.list = list;
        this.pressHandler = pressHandler;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.checkboxmci)
        public CheckBox chkCmi;
        @BindView(R.id.txtCargomci)
        public TextView txtCargomci;

        @BindView(R.id.view_foregroundmci)
        RelativeLayout viewForeground;

        public MyViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this,view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if (list.get(position).checked)
            {
                list.get(position).checked = false;
            }
            else{
                list.get(position).checked = true;
            }

            notifyItemChanged(position);
            aD = list.get(position);
            pressHandler.onPress(aD);
        }
    }

    public interface CmiAdapterOnPressHandler{
        void onPress(TiposDataCk data);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_minicargos_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TiposDataCk data = list.get(position);

        holder.chkCmi.setChecked(data.checked);
        holder.txtCargomci.setText(data.descripcion);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<TiposDataCk> getCargosListChecked() {
        List<TiposDataCk> list1 = new ArrayList<>();
        for (TiposDataCk tdc: list)
        {
            if (tdc.checked)
            {
                list1.add(tdc);
            }
        }
        return list1;
    }
}
