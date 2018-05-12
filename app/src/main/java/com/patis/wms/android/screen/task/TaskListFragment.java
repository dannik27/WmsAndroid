package com.patis.wms.android.screen.task;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.patis.wms.android.App;
import com.patis.wms.android.R;
import com.patis.wms.android.dto.RequestDTO;
import com.patis.wms.android.dto.TaskDTO;
import com.patis.wms.android.dto.create.RequestCreateDTO;
import com.patis.wms.android.dto.create.RequestItemCreateDTO;
import com.patis.wms.android.screen.request.RequestListAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskListFragment extends Fragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private View root;

    private CheckBox cbMy;
    private CheckBox cbActive;

    TextView tvEmpty;

    TaskListAdapter listAdapter;

    public TaskListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_task_list, container, false);
        recyclerView = root.findViewById(R.id.requestList);
        swipeRefresh = root.findViewById(R.id.swipeRefresh);
        cbMy = root.findViewById(R.id.cbMy);
        cbActive = root.findViewById(R.id.cbActive);
        tvEmpty = root.findViewById(R.id.tvEmpty);


        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        listAdapter = new TaskListAdapter();
        recyclerView.setAdapter(listAdapter);
        listAdapter.setListener(task->{
            Intent intent = new Intent(getActivity(), TaskActivity.class);
            intent.putExtra("taskId", task.getId());
            startActivity(intent);
        });

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorAccent,
                R.color.colorPrimaryDark,
                R.color.colorAccent);

        swipeRefresh.setOnRefreshListener(()-> updateList());
        fireSwipeRefresh();

        cbMy.setOnClickListener(e-> fireSwipeRefresh());
        cbActive.setOnClickListener(e-> fireSwipeRefresh());

        return root;
    }

    private void fireSwipeRefresh(){
        swipeRefresh.post(() -> {
            if(swipeRefresh != null) {
                swipeRefresh.setRefreshing(true);
            }
            updateList();
        });
    }

    private void updateList(){

        boolean onlyActive = cbActive.isChecked();
        int id_worker;
        if(cbMy.isChecked()){
            id_worker = (int) App.local().getLong("currentUserId");
        }else{
            id_worker = 0;
        }

        App.getBackendApi().getTasksByWorker(id_worker, onlyActive).enqueue(new Callback<List<TaskDTO>>() {
            @Override
            public void onResponse(Call<List<TaskDTO>> call, Response<List<TaskDTO>> response) {
                if(response.body() != null){
                    setListContent(response.body());
                    swipeRefresh.setRefreshing(false);
                }
            }
            @Override public void onFailure(Call<List<TaskDTO>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setListContent(List<TaskDTO> data){
        listAdapter.setData(data);
        listAdapter.notifyDataSetChanged();
        if(data.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fireSwipeRefresh();
    }
}
