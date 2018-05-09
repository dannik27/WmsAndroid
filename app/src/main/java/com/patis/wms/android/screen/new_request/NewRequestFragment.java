package com.patis.wms.android.screen.new_request;


import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.patis.wms.android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewRequestFragment extends Fragment {

    private View root;

    private TextView text;

    public NewRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_new_request, container, false);

        text = root.findViewById(R.id.text);

        if(getArguments() != null){
            text.setText(String.valueOf(getArguments().getInt("operationType")));
        }

        return root;
    }

}
