package com.patis.wms.android.screen.transportation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.patis.wms.android.R;
import com.patis.wms.android.dto.RequestDTO;
import com.patis.wms.android.dto.TransportationDTO;
import com.patis.wms.android.dto.entity.OperationType;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TransportationListAdapter extends RecyclerView.Adapter<TransportationListAdapter.ViewHolder> {

    private List<TransportationDTO> data;

    private TransportationListClickListener clickListener;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View root;
        TextView tvDateFrom;
        TextView tvDateTo;
        TextView tvStorehouse;
        TextView tvType;
        TextView tvCustomer;

        public ViewHolder(View v) {
            super(v);
            root = v;
            tvDateFrom = root.findViewById(R.id.tvDateFrom);
            tvDateTo = root.findViewById(R.id.tvDateTo);
            tvStorehouse = root.findViewById(R.id.tvStorehouse);
            tvType = root.findViewById(R.id.tvType);
            tvCustomer = root.findViewById(R.id.tvCustomer);


        }
    }

    public void setData(List<TransportationDTO> data){
        this.data = data;
    }
    public void setListener(TransportationListClickListener listener){
        this.clickListener = listener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TransportationListAdapter(List<TransportationDTO> data, TransportationListClickListener clickListener) {
        this.data = data;
        this.clickListener = clickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TransportationListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_transportation_list_item, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(data.get(position).getRequest().getCustomer() != null){
            holder.tvCustomer.setText(data.get(position).getRequest().getCustomer().getCompany().getName());

            if(data.get(position).getRequest().getOperationType() == OperationType.IN){
                holder.tvStorehouse.setText(data.get(position).getRequest().getStorehouseTo().getName());
            }else{
                holder.tvStorehouse.setText(data.get(position).getRequest().getStorehouseFrom().getName());

            }

        }else{
            holder.tvCustomer.setText("Перенос со склада на склад");
            holder.tvStorehouse.setText("-");
        }


        holder.tvType.setText(data.get(position).getRequest().getOperationType().toString());

        if(data.get(position).getDateShipped() != null){
            holder.tvDateFrom.setText("c: " + dateFormat.format(data.get(position).getDateShipped()));
        }
        if(data.get(position).getDateReceived() != null){
            holder.tvDateTo.setText("по: " + dateFormat.format(data.get(position).getDateReceived()));
        }



        holder.root.setOnClickListener(v -> {
            if(clickListener != null){
                try {
                    clickListener.handle(data.get(position));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(data == null){
            return 0;
        }else{
            return data.size();
        }
    }


    public interface TransportationListClickListener {
        void handle(TransportationDTO transportation) throws IllegalAccessException, InstantiationException;
    }
}
