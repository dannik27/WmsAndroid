package com.patis.wms.android.screen.request;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.patis.wms.android.App;
import com.patis.wms.android.R;
import com.patis.wms.android.dto.RequestDTO;
import com.patis.wms.android.dto.entity.OperationType;
import com.patis.wms.android.screen.new_request.RequestActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestListFragment extends Fragment {

    private RecyclerView recyclerView;
    private View root;

    private SwipeRefreshLayout swipeRefresh;
    private TextView tvEmpty;

    private RequestListAdapter listAdapter;

    public RequestListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_request_list, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);

        swipeRefresh = root.findViewById(R.id.swipeRefresh);
        tvEmpty = root.findViewById(R.id.tvEmpty);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        listAdapter = new RequestListAdapter();
        recyclerView.setAdapter(listAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                llm.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        listAdapter.setListener(request -> {
            Bundle bundle = new Bundle();
            bundle.putInt("request_id", (int) request.getId());
            Intent intent = new Intent(getActivity(), RequestActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });


        FloatingActionMenu fam = root.findViewById(R.id.fam);
        fam.setClosedOnTouchOutside(true);

        FloatingActionButton fabIn = root.findViewById(R.id.menu_in);
        FloatingActionButton fabOut = root.findViewById(R.id.menu_out);
        FloatingActionButton fabOutIn = root.findViewById(R.id.menu_out_in);
        fabIn.setOnClickListener(e -> {
            newRequest(OperationType.IN);
            fam.close(false);
        });
        fabOut.setOnClickListener(e -> {
            newRequest(OperationType.OUT);
            fam.close(false);
        });
        fabOutIn.setOnClickListener(e -> {
            newRequest(OperationType.IN_OUT);
            fam.close(false);
        });

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorAccent,
                R.color.colorPrimaryDark,
                R.color.colorAccent);

        swipeRefresh.setOnRefreshListener(this::updateList);
        fireSwipeRefresh();


        return root;
    }

    void newRequest(OperationType operationType) {

        Bundle bundle = new Bundle();
        bundle.putInt("operationType", operationType.ordinal());
        Intent intent = new Intent(getActivity(), RequestActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    private void fireSwipeRefresh(){
        swipeRefresh.post(() -> {
            if(swipeRefresh != null) {
                swipeRefresh.setRefreshing(true);
            }
            updateList();
        });
    }

    private void setListContent(List<RequestDTO> data){
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

    private void updateList() {
        App.getBackendApi().getRequests().enqueue(new Callback<List<RequestDTO>>() {
            @Override
            public void onResponse(Call<List<RequestDTO>> call, Response<List<RequestDTO>> response) {
                if (response.body() != null) {
                    listAdapter.setData(response.body());
                    listAdapter.notifyDataSetChanged();
                }
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<RequestDTO>> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                t.printStackTrace();
            }
        });
    }
}
