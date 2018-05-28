package com.patis.wms.android.screen.task;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.patis.wms.android.R;
import com.patis.wms.android.dto.DistributionDTO;
import com.patis.wms.android.dto.TaskDTO;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DistributionListAdapter extends RecyclerView.Adapter<DistributionListAdapter.ViewHolder> {

    @Setter @Getter
    private boolean buttonVisible;

    private List<DistributionDTO> data;

    private DistributionListClickListener clickListener;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View root;
        TextView tvName;
        TextView tvCount;
        TextView tvCell;
        Button btnDone;

        public ViewHolder(View v) {
            super(v);
            root = v;
            tvName = root.findViewById(R.id.tvName);
            tvCount = root.findViewById(R.id.tvCount);
            tvCell = root.findViewById(R.id.tvCell);
            btnDone = root.findViewById(R.id.btnDone);


        }
    }

    public void setData(List<DistributionDTO> data){
        this.data = data;
    }
    public void setListener(DistributionListClickListener listener){
        this.clickListener = listener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DistributionListAdapter(List<DistributionDTO> data, DistributionListClickListener clickListener) {
        this.data = data;
        this.clickListener = clickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DistributionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_distribution_list_item, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.btnDone.setVisibility(buttonVisible ? View.VISIBLE : View.GONE);

        holder.tvName.setText(String.format("%s (id %d)", data.get(position).getProduct().getName(), data.get(position).getProduct().getId()));
        holder.tvCount.setText(String.valueOf(data.get(position).getCount()));
        holder.tvCell.setText(data.get(position).getCell().getName());

        if(data.get(position).isDone()){
            holder.btnDone.setBackgroundResource(R.drawable.ic_check);
        }

        holder.btnDone.setOnClickListener(e -> {
            if(clickListener != null){
                try {
                    clickListener.handle(data.get(position));
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                } catch (InstantiationException ex) {
                    ex.printStackTrace();
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


    public interface DistributionListClickListener {
        void handle(DistributionDTO distribution) throws IllegalAccessException, InstantiationException;
    }
}
