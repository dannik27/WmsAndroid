package com.patis.wms.android.screen.task;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.patis.wms.android.R;
import com.patis.wms.android.dto.RequestDTO;
import com.patis.wms.android.dto.TaskDTO;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private List<TaskDTO> data;

    private TaskListClickListener clickListener;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View root;
        TextView tvDate;
        TextView tvWorker;
        TextView tvStatus;
        TextView tvCustomer;

        public ViewHolder(View v) {
            super(v);
            root = v;
            tvDate = root.findViewById(R.id.tvDate);
            tvStatus = root.findViewById(R.id.tvStatus);
            tvWorker = root.findViewById(R.id.tvWorker);
            tvCustomer = root.findViewById(R.id.tvCustomer);


        }
    }

    public void setData(List<TaskDTO> data){
        this.data = data;
    }
    public void setListener(TaskListClickListener listener){
        this.clickListener = listener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TaskListAdapter(List<TaskDTO> data, TaskListClickListener clickListener) {
        this.data = data;
        this.clickListener = clickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TaskListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_task_list_item, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        holder.tvWorker.setText(data.get(position).getWorker().getPerson().getFio());
        holder.tvStatus.setText(data.get(position).getTaskStatus().toString());
        holder.tvDate.setText(dateFormat.format(data.get(position).getTimeBegin()));
        holder.tvCustomer.setText(data.get(position).getCustomerName());

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


    public interface TaskListClickListener {
        void handle(TaskDTO task) throws IllegalAccessException, InstantiationException;
    }
}
