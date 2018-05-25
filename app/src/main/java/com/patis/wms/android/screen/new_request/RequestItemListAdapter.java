package com.patis.wms.android.screen.new_request;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.patis.wms.android.R;
import com.patis.wms.android.dto.DistributionDTO;
import com.patis.wms.android.dto.RequestItemDTO;
import com.patis.wms.android.screen.request.RequestListAdapter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RequestItemListAdapter extends RecyclerView.Adapter<RequestItemListAdapter.ViewHolder> {

    private List<RequestItemDTO> data;

    private RequestItemListClickListener clickListener;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);

    private boolean deleteButtonVisible;

    public void setDeleteButtonVisible(boolean visibility){
        deleteButtonVisible = visibility;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View root;
        TextView tvName;
        TextView tvCount;
        TextView tvCell;
        Button btnDone;
        TextView tvDate;

        public ViewHolder(View v, boolean deleteButtonVisible) {
            super(v);
            root = v;
            tvName = root.findViewById(R.id.tvName);
            tvCount = root.findViewById(R.id.tvCount);
            tvCell = root.findViewById(R.id.tvCell);
            btnDone = root.findViewById(R.id.btnDone);
            tvDate = root.findViewById(R.id.tvDate);

            tvCell.setVisibility(View.GONE);
            tvDate.setVisibility(View.GONE);

            btnDone.setBackgroundResource(R.drawable.ic_delete);

            if(! deleteButtonVisible){
                btnDone.setVisibility(View.GONE);
            }


        }
    }

    public void setData(List<RequestItemDTO> data){
        this.data = data;
    }
    public void setListener(RequestItemListClickListener listener){
        this.clickListener = listener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RequestItemListAdapter(List<RequestItemDTO> data, RequestItemListClickListener clickListener) {
        this.data = data;
        this.clickListener = clickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RequestItemListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_distribution_list_item, parent, false);
        return new ViewHolder(v, deleteButtonVisible);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        holder.tvName.setText(String.format("%s (id %d)", data.get(position).getProduct().getName(), data.get(position).getProduct().getId()));
        holder.tvCount.setText(String.valueOf(data.get(position).getCount()));

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


    public interface RequestItemListClickListener {
        void handle(RequestItemDTO requestItem) throws IllegalAccessException, InstantiationException;
    }
}
