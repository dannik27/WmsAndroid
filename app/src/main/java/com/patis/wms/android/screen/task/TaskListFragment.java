package com.patis.wms.android.screen.task;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.patis.wms.android.App;
import com.patis.wms.android.R;
import com.patis.wms.android.dto.RequestDTO;
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
    private FloatingActionButton fab;
    private View root;


    public TaskListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_request_list, container, false);
        recyclerView = root.findViewById(R.id.requestList);
        fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(e->{
            System.out.println("sdadasdas");
        });

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        RequestListAdapter adapter = new RequestListAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setListener(request->{
            System.out.println("uytjk");
        });

        App.getBackendApi().getRequests().enqueue(new Callback<List<RequestDTO>>() {
            @Override
            public void onResponse(Call<List<RequestDTO>> call, Response<List<RequestDTO>> response) {
                if(response.body() != null){
                    adapter.setData(response.body());
                    adapter.notifyDataSetChanged();
                }
            }
            @Override public void onFailure(Call<List<RequestDTO>> call, Throwable t) {
                t.printStackTrace();
            }
        });


        RequestCreateDTO dto = new RequestCreateDTO();
        dto.getItems().add(new RequestItemCreateDTO());
        App.getBackendApi().send(dto);

        return root;
    }

}
