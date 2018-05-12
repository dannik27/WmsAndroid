package com.patis.wms.android.screen.task;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.patis.wms.android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {

    private View root;

    private TextView tvCustomer;
    private TextView tvStatus;
    private TextView tvWorker;
    private TextView tvStorehouse;
    private TextView tvTimeBegin;
    private TextView tvTimeEnd;
    private TextView tvDone;
    private TextView tvCount;


    public TaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_task, container, false);

        tvCustomer = root.findViewById(R.id.tvCustomer);
        tvStatus = root.findViewById(R.id.tvStatus);
        tvWorker = root.findViewById(R.id.tvWorker);
        tvStorehouse = root.findViewById(R.id.tvStorehouse);
        tvTimeBegin = root.findViewById(R.id.tvTimeBegin);
        tvTimeEnd = root.findViewById(R.id.tvTimeEnd);
        tvDone = root.findViewById(R.id.tvDone);
        tvCount = root.findViewById(R.id.tvCount);




        return root;
    }

}
