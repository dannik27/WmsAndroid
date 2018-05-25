package com.patis.wms.android.screen.new_request;


import android.graphics.Color;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.patis.wms.android.App;
import com.patis.wms.android.MainActivity;
import com.patis.wms.android.R;
import com.patis.wms.android.dto.CustomerDTO;
import com.patis.wms.android.dto.StorehouseDTO;
import com.patis.wms.android.dto.entity.OperationType;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewRequestFragment extends Fragment {

    private View root;

    private TextView tvTittle;

    private View boxCustomer;
    private View boxFrom;
    private View boxTo;

    private Spinner sCustomer;
    private Spinner sFrom;
    private Spinner sTo;


    public NewRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_new_request, container, false);

        tvTittle = root.findViewById(R.id.tvTittle);

        boxCustomer = root.findViewById(R.id.boxCustomer);
        boxFrom = root.findViewById(R.id.boxFrom);
        boxTo = root.findViewById(R.id.boxTo);

        sCustomer = root.findViewById(R.id.sCustomer);
        sFrom = root.findViewById(R.id.sFrom);
        sTo = root.findViewById(R.id.sTo);

        App.getBackendApi().getCustomers().enqueue(new Callback<List<CustomerDTO>>() {
            @Override
            public void onResponse(Call<List<CustomerDTO>> call, Response<List<CustomerDTO>> response) {
                if(response.body() != null){
                    ArrayAdapter<CustomerDTO> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, response.body());
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sCustomer.setAdapter(adapter1);
                }
            }

            @Override
            public void onFailure(Call<List<CustomerDTO>> call, Throwable t) {

            }
        });

        App.getBackendApi().getStorehouses().enqueue(new Callback<List<StorehouseDTO>>() {
            @Override
            public void onResponse(Call<List<StorehouseDTO>> call, Response<List<StorehouseDTO>> response) {
                if(response.body() != null){
                    ArrayAdapter<StorehouseDTO> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, response.body());
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sFrom.setAdapter(adapter1);

                    ArrayAdapter<StorehouseDTO> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, response.body());
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sTo.setAdapter(adapter2);
                }
            }

            @Override
            public void onFailure(Call<List<StorehouseDTO>> call, Throwable t) {

            }
        });



        if(getArguments() != null){
            //tvTittle.setText(String.valueOf(getArguments().getInt("operationType")));
            OperationType operationType = OperationType.values()[getArguments().getInt("operationType")];
            switch (operationType){
                case IN:
                    tvTittle.setText("Укажите заказчика и склад приёма");
                    boxFrom.setVisibility(View.GONE);
                    break;
                case OUT:
                    tvTittle.setText("Укажите заказчика и склад отгрузки");
                    boxTo.setVisibility(View.GONE);
                    break;
                case IN_OUT:
                    tvTittle.setText("Укажите склады отгрузки и приёма");
                    boxCustomer.setVisibility(View.GONE);
                    break;
            }

        }

        return root;
    }

}
