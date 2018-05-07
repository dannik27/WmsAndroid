package com.patis.wms.android.screen.request;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.patis.wms.android.R;
import com.patis.wms.android.dto.RequestDTO;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.ViewHolder> {

    private List<RequestDTO> data;

    private RequestListClickListener clickListener;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View root;
        TextView tvDate;
        TextView tvAuthor;
        TextView tvType;
        TextView tvCustomer;

        public ViewHolder(View v) {
            super(v);
            root = v;
            tvDate = root.findViewById(R.id.tvDate);
            tvAuthor = root.findViewById(R.id.tvAuthor);
            tvType = root.findViewById(R.id.tvType);
            tvCustomer = root.findViewById(R.id.tvCustomer);


        }
    }

    public void setData(List<RequestDTO> data){
        this.data = data;
    }
    public void setListener(RequestListClickListener listener){
        this.clickListener = listener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RequestListAdapter(List<RequestDTO> data, RequestListClickListener clickListener) {
        this.data = data;
        this.clickListener = clickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RequestListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_request_list_item, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(data.get(position).getCustomer() != null){
            holder.tvCustomer.setText(data.get(position).getCustomer().getCompany().getName());
        }else{
            holder.tvCustomer.setText("Перенос со склада на склад");
        }

        holder.tvAuthor.setText(data.get(position).getAuthor().getPerson().getLastName());
        holder.tvType.setText(data.get(position).getOperationType().toString());
        holder.tvDate.setText(dateFormat.format(data.get(position).getDateBegin()));

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


    public interface RequestListClickListener {
        void handle(RequestDTO request) throws IllegalAccessException, InstantiationException;
    }
}
