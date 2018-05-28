package com.patis.wms.android.screen.transportation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.clans.fab.FloatingActionMenu;
import com.patis.wms.android.App;
import com.patis.wms.android.R;
import com.patis.wms.android.dto.CustomerDTO;
import com.patis.wms.android.dto.PackingListDTO;
import com.patis.wms.android.dto.RequestDTO;
import com.patis.wms.android.dto.RequestItemDTO;
import com.patis.wms.android.dto.StorehouseDTO;
import com.patis.wms.android.dto.TransportCompanyDTO;
import com.patis.wms.android.dto.TransportationDTO;
import com.patis.wms.android.dto.create.RequestCreateDTO;
import com.patis.wms.android.dto.create.RequestItemCreateDTO;
import com.patis.wms.android.dto.create.TransportationCreateDTO;
import com.patis.wms.android.dto.create.WaybillCreateDTO;
import com.patis.wms.android.dto.entity.OperationType;
import com.patis.wms.android.screen.Initializable;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransportationActivity extends AppCompatActivity implements Initializable{

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US);

    private TransportationDTO transportation;

    private SwipeRefreshLayout swipeRefresh;
    private View content;

    private ArrayAdapter<RequestDTO> requestAdapter;
    private ArrayAdapter<TransportCompanyDTO> transportCompanyAdapter;

    private Spinner sRequest;
    private Spinner sTransCompany;
    private EditText etWeight;
    private EditText etDateFrom;
    private EditText etDateTo;
    private EditText etInfo;


    private List<RequestDTO> requests;
    private List<TransportCompanyDTO> transCompanies;

    private FloatingActionMenu fam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        swipeRefresh = findViewById(R.id.swipeRefresh);
        content = findViewById(R.id.content);
        sRequest = findViewById(R.id.sRequest);
        sTransCompany = findViewById(R.id.sTransCompany);
        etWeight = findViewById(R.id.etWeight);
        etDateFrom = findViewById(R.id.etDateFrom);
        etDateTo = findViewById(R.id.etDateTo);
        etInfo = findViewById(R.id.etInfo);

        fam = findViewById(R.id.fam);
        com.github.clans.fab.FloatingActionButton fabIn = findViewById(R.id.menu_in);

        fabIn.setOnClickListener(e->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Укажите дату приёма");

            final EditText input = new EditText(this);
            //input.setInputType(InputType.TYPE_DATETIME_VARIATION_NORMAL);
            builder.setView(input);

            builder.setPositiveButton("Сохранить", (dialog, which) ->
                    receiveTransportation(input.getText().toString()));
            builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());

            builder.show();
        });

        content.setVisibility(View.GONE);
        swipeRefresh.setEnabled(true);
        swipeRefresh.setRefreshing(true);

        requestAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item);
        requestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sRequest.setAdapter(requestAdapter);

        transportCompanyAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item);
        transportCompanyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sTransCompany.setAdapter(transportCompanyAdapter);


        startInitialization(getIntent().getExtras());



    }

    private void receiveTransportation(String dateString){
        try {
            Date date = dateTimeFormat.parse(dateString);
        //    dateString = "\"" + dateString + "\"";
            App.getFromCustomerApi().receiveTransportation(dateString, transportation.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initializeData(Bundle bundle) {
        try {
            requests = App.getBackendApi().getRequests().execute().body();
            transCompanies = App.getBackendApi().getTransportCompanies().execute().body();
            if(bundle.containsKey("transportationId")){
                transportation = App.getBackendApi().getTransportationById((int)bundle.getLong("transportationId")).execute().body();
            }else{
                transportation = new TransportationDTO();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initializeView(Bundle bundle) {
        requestAdapter.addAll(requests);
        transportCompanyAdapter.addAll(transCompanies);

        sRequest.setSelection(0);
        sTransCompany.setSelection(0);

        if(transportation.getId() != 0){
            if(transportation.getRequest() != null){
                sRequest.setSelection(requestAdapter.getPosition(transportation.getRequest()));
            }
            if(transportation.getWaybill().getTransportCompany() != null){
                sTransCompany.setSelection(transportCompanyAdapter.getPosition(transportation.getWaybill().getTransportCompany()));
            }

            etWeight.setText(String.valueOf(transportation.getGrossWeight()));
            if(transportation.getDateShipped() != null){
                etDateFrom.setText(dateFormat.format(transportation.getDateShipped()));
            }else{
                etDateFrom.setText("-");
            }
            if(transportation.getDateReceived() != null){
                etDateTo.setText(dateFormat.format(transportation.getDateReceived()));
            }else{
                etDateTo.setText("-");
            }
            etInfo.setText(transportation.getWaybill().getInfo());

            sRequest.setEnabled(false);
            sTransCompany.setEnabled(false);
            etWeight.setEnabled(false);
            etDateFrom.setEnabled(false);
            etDateTo.setEnabled(false);
            etInfo.setEnabled(false);

            fam.setVisibility(View.VISIBLE);

        }

        invalidateOptionsMenu();

        content.setVisibility(View.VISIBLE);
        swipeRefresh.setRefreshing(false);
        swipeRefresh.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.request, menu);
        if(transportation != null && transportation.getId() == 0){
            menu.findItem(R.id.action_save).setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {

            PackingListDTO packingList = new PackingListDTO();
            packingList.setInfo("some info");

            WaybillCreateDTO waybill = new WaybillCreateDTO();
            waybill.setTransportCompanyId(((TransportCompanyDTO) sTransCompany.getSelectedItem()).getId());
            waybill.setInfo(etInfo.getText().toString());

            TransportationCreateDTO dto = new TransportationCreateDTO();
            dto.setGrossWeight(Float.valueOf(etWeight.getText().toString()));
            dto.setId_request(((RequestDTO) sRequest.getSelectedItem()).getId());
            dto.setPackingList(packingList);
            dto.setWaybill(waybill);

            App.getFromCustomerApi().createTransportation(dto).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    onBackPressed();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    t.printStackTrace();
                }
            });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
