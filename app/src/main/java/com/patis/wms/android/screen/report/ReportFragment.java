package com.patis.wms.android.screen.report;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.patis.wms.android.App;
import com.patis.wms.android.R;
import com.patis.wms.android.dto.StorehouseDTO;
import com.patis.wms.android.screen.Initializable;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment implements Initializable {

    private View root;
    private Button btnWorker;
    private Button btnStorehouse;
    private Button btnMoves;

    private EditText workerDateFrom;
    private EditText workerDateTo;

    private EditText movesDateFrom;
    private EditText movesDateTo;

    private Spinner sStorehouse;
    private Spinner sMoves;

    private List<StorehouseDTO> storehouses;

    private ArrayAdapter<StorehouseDTO> firstAdapter;
    private ArrayAdapter<StorehouseDTO> secondAdapter;

    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_report, container, false);

        btnWorker = root.findViewById(R.id.btnWorker);
        workerDateFrom = root.findViewById(R.id.workerDateFrom);
        workerDateTo = root.findViewById(R.id.workerDateTo);
        btnStorehouse = root.findViewById(R.id.btnStorehouse);
        btnMoves = root.findViewById(R.id.btnMoves);
        movesDateFrom = root.findViewById(R.id.movesDateFrom);
        movesDateTo = root.findViewById(R.id.movesDateTo);
        sStorehouse = root.findViewById(R.id.sStorehouse);
        sMoves = root.findViewById(R.id.sMoves);


        firstAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        firstAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sStorehouse.setAdapter(firstAdapter);

        secondAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        secondAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sMoves.setAdapter(secondAdapter);

        btnWorker.setOnClickListener(e->{

            List<String> paramPairs = new LinkedList<>();
            if(validateDate(workerDateFrom.getText().toString()))
                paramPairs.add("dateFrom=" + workerDateFrom.getText().toString());
            if(validateDate(workerDateTo.getText().toString()))
                paramPairs.add("dateTo=" + workerDateTo.getText().toString());
            showReport("report/worker", paramPairs);
        });

        btnStorehouse.setOnClickListener(e->{
            List<String> paramPairs = new LinkedList<>();
            int storehouseId = (int)((StorehouseDTO) sStorehouse.getSelectedItem()).getId();
            showReport("report/storehouse/" + storehouseId, paramPairs);
        });

        btnMoves.setOnClickListener(e->{
            List<String> paramPairs = new LinkedList<>();
            if(validateDate(movesDateFrom.getText().toString()))
                paramPairs.add("dateFrom=" + movesDateFrom.getText().toString());
            if(validateDate(movesDateTo.getText().toString()))
                paramPairs.add("dateTo=" + movesDateTo.getText().toString());
            int storehouseId = (int)((StorehouseDTO) sMoves.getSelectedItem()).getId();
            showReport("report/storehouse/"+storehouseId+"/moves", paramPairs);
        });


        startInitialization(null);

        return root;
    }

    private void showReport(String url, List<String> paramPairs){
        StringBuilder params = new StringBuilder();
        if(paramPairs.size() > 0){
            params.append("?");
            for(String pair : paramPairs){
                params.append(pair).append("%26");
            }
            params.replace(params.length() - 3, params.length(), "");
        }

        String pdfUrl = String.format("http://docs.google.com/gview?embedded=true&url=http://kokoserver.me:8090/%s%s", url, params.toString());

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(pdfUrl ), "text/html");
        startActivity(intent);

    }

    private boolean validateDate(String date){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            simpleDateFormat.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public void initializeData(Bundle bundle) {
        try {
            storehouses = App.getBackendApi().getStorehouses().execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initializeView(Bundle bundle) {
        firstAdapter.addAll(storehouses);
        secondAdapter.addAll(storehouses);
    }
}
