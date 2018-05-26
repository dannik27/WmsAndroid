package com.patis.wms.android.screen.transportation;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.patis.wms.android.App;
import com.patis.wms.android.R;
import com.patis.wms.android.dto.TaskDTO;
import com.patis.wms.android.dto.TransportationDTO;
import com.patis.wms.android.screen.request.RequestListAdapter;
import com.patis.wms.android.screen.task.TaskActivity;
import com.patis.wms.android.screen.task.TaskListAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransportationListFragment extends Fragment {

    private SwipeRefreshLayout swipeRefresh;
    private TextView tvEmpty;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private View root;

    private TransportationListAdapter listAdapter;

    private RequestListAdapter adapter;

    public TransportationListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_transportation_list, container, false);

        swipeRefresh = root.findViewById(R.id.swipeRefresh);
        tvEmpty = root.findViewById(R.id.tvEmpty);
        recyclerView = root.findViewById(R.id.recyclerView);
        fab = root.findViewById(R.id.fab);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        listAdapter = new TransportationListAdapter();
        recyclerView.setAdapter(listAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                llm.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        listAdapter.setListener(transportation->{
        //    Intent intent = new Intent(getActivity(), TransportationActivity.class);
        //    intent.putExtra("transportationId", transportation.getId());
        //    startActivity(intent);
        });

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorAccent,
                R.color.colorPrimaryDark,
                R.color.colorAccent);

        swipeRefresh.setOnRefreshListener(this::updateList);
        fireSwipeRefresh();

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

        App.getBackendApi().getTransportations().enqueue(new Callback<List<TransportationDTO>>() {
            @Override
            public void onResponse(Call<List<TransportationDTO>> call, Response<List<TransportationDTO>> response) {
                if(response.body() != null){
                    setListContent(response.body());
                }
                swipeRefresh.setRefreshing(false);
            }
            @Override public void onFailure(Call<List<TransportationDTO>> call, Throwable t) {
                swipeRefresh.setRefreshing(false);

            }
        });
    }

    private void setListContent(List<TransportationDTO> data){
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
