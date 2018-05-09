package com.patis.wms.android.screen.request;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.patis.wms.android.App;
import com.patis.wms.android.MainActivity;
import com.patis.wms.android.R;
import com.patis.wms.android.dto.RequestDTO;
import com.patis.wms.android.dto.create.RequestCreateDTO;
import com.patis.wms.android.dto.create.RequestItemCreateDTO;
import com.patis.wms.android.dto.entity.OperationType;
import com.patis.wms.android.screen.new_request.NewRequestFragment;

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


    public RequestListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_request_list, container, false);
        recyclerView = root.findViewById(R.id.requestList);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        RequestListAdapter adapter = new RequestListAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setListener(request->{
            Fragment fragment = new NewRequestFragment();
            ((MainActivity) getActivity()).setContent(fragment);
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

        FloatingActionButton fabIn = root.findViewById(R.id.menu_in);
        FloatingActionButton fabOut = root.findViewById(R.id.menu_out);
        FloatingActionButton fabOutIn = root.findViewById(R.id.menu_out_in);

        fabIn.setOnClickListener(e-> newRequest(OperationType.IN));
        fabOut.setOnClickListener(e-> newRequest(OperationType.OUT));
        fabOutIn.setOnClickListener(e-> newRequest(OperationType.IN_OUT));


        return root;
    }

    void newRequest(OperationType operationType){
        Fragment fragment = new NewRequestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("operationType", operationType.ordinal());
        fragment.setArguments(bundle);
        ((MainActivity) getActivity()).setContent(fragment);
    }

}
