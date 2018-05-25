package com.patis.wms.android.screen.new_request;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.patis.wms.android.App;
import com.patis.wms.android.MainActivity;
import com.patis.wms.android.R;
import com.patis.wms.android.dto.CustomerDTO;
import com.patis.wms.android.dto.DistributionDTO;
import com.patis.wms.android.dto.RequestDTO;
import com.patis.wms.android.dto.RequestItemDTO;
import com.patis.wms.android.dto.StorehouseDTO;
import com.patis.wms.android.dto.TaskDTO;
import com.patis.wms.android.dto.create.RequestCreateDTO;
import com.patis.wms.android.dto.create.RequestItemCreateDTO;
import com.patis.wms.android.dto.entity.OperationType;
import com.patis.wms.android.dto.entity.Product;
import com.patis.wms.android.dto.entity.TaskStatus;
import com.patis.wms.android.screen.request.RequestListAdapter;
import com.patis.wms.android.screen.task.DistributionListAdapter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestActivity extends AppCompatActivity {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US);




    private TextView tvTittle;

    private View boxCustomer;
    private View boxFrom;
    private View boxTo;

    private Spinner sCustomer;
    private Spinner sFrom;
    private Spinner sTo;

    private Button btnAddItem;

    private RecyclerView recyclerView;

    RequestItemListAdapter listAdapter;

    List<RequestItemDTO> data;

    private RequestDTO request;

    ArrayAdapter<CustomerDTO> customerAdapter;
    ArrayAdapter<StorehouseDTO> fromAdapter;
    ArrayAdapter<StorehouseDTO> toAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvTittle = findViewById(R.id.tvTittle);

        boxCustomer = findViewById(R.id.boxCustomer);
        boxFrom = findViewById(R.id.boxFrom);
        boxTo = findViewById(R.id.boxTo);

        sCustomer = findViewById(R.id.sCustomer);
        sFrom = findViewById(R.id.sFrom);
        sTo = findViewById(R.id.sTo);

        btnAddItem = findViewById(R.id.btnAddItem);

        recyclerView = findViewById(R.id.recyclerView);


        customerAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item);
        customerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCustomer.setAdapter(customerAdapter);

        fromAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sFrom.setAdapter(fromAdapter);

        toAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sTo.setAdapter(toAdapter);


        btnAddItem.setOnClickListener(e->{

            RequestItemDialogView dialogView = new RequestItemDialogView();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView)
                    .setPositiveButton("Да", (dialog, id) ->{
                        RequestItemDTO dto = new RequestItemDTO();
                        dto.setProduct(dialogView.getProduct());
                        dto.setCount(dialogView.getCount());
                        data.add(dto);
                        listAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Отмена", (dialog, id) -> {
                        // User cancelled the dialog
                    });


            builder.create().show();
        });

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(llm);
        listAdapter = new RequestItemListAdapter();
        recyclerView.setAdapter(listAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                llm.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        data = new ArrayList<>();
        listAdapter.setData(data);

        listAdapter.setListener(requestItem->{

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Удалить " + requestItem.getProduct().getName() + " ?")
                    .setPositiveButton("Да", (dialog, id) ->{
                        data.remove(requestItem);
                        listAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Отмена", (dialog, id) -> {
                        // User cancelled the dialog
                    });


            builder.create().show();

        });

        if(getIntent().getExtras() != null){
            new ActivityUpdateTask().execute(getIntent().getExtras());
        }

    }


    class ActivityUpdateTask extends AsyncTask<Bundle, Void, Bundle>{

        List<CustomerDTO> customers;
        List<StorehouseDTO> storehouses;
        List<RequestItemDTO> items;


        @Override
        protected void onPostExecute(Bundle b) {
            super.onPostExecute(b);

            customerAdapter.addAll(customers);
            fromAdapter.addAll(storehouses);
            toAdapter.addAll(storehouses);

            sCustomer.setSelection(0);
            sTo.setSelection(0);
            sFrom.setSelection(0);


            if(request.getId() != 0){
                if(request.getCustomer() != null){
                    sCustomer.setSelection(customerAdapter.getPosition(request.getCustomer()));
                }
                if(request.getStorehouseFrom() != null){
                    sFrom.setSelection(fromAdapter.getPosition(request.getStorehouseFrom()));
                }
                if(request.getStorehouseTo() != null){
                    sTo.setSelection(toAdapter.getPosition(request.getStorehouseTo()));
                }

                data.addAll(items);

                tvTittle.setVisibility(View.GONE);
                btnAddItem.setVisibility(View.GONE);
                sCustomer.setEnabled(false);
                sTo.setEnabled(false);
                sFrom.setEnabled(false);

            }

            switch (request.getOperationType()){
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

            invalidateOptionsMenu();
        }

        @Override
        protected Bundle doInBackground(Bundle[] objects) {
            Bundle bundle = objects[0];
            try {
                customers = App.getBackendApi().getCustomers().execute().body();
                storehouses = App.getBackendApi().getStorehouses().execute().body();
                if(bundle.containsKey("request_id")){
                    request = App.getBackendApi().getRequestById(bundle.getInt("request_id")).execute().body();
                    items = App.getBackendApi().getRequestItems(bundle.getInt("request_id")).execute().body();
                }else{
                    request = new RequestDTO();
                    request.setOperationType(OperationType.values()[getIntent().getExtras().getInt("operationType")]);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return bundle;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.request, menu);
        if(request != null && request.getId() != 0){
            menu.findItem(R.id.action_save).setVisible(false);
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

            RequestCreateDTO requestCreateDTO = new RequestCreateDTO();
            requestCreateDTO.setId_author(App.local().getLong("currentUserId"));
            requestCreateDTO.setOperationType(request.getOperationType());
            requestCreateDTO.setDateBegin(new Date(System.currentTimeMillis()));
            requestCreateDTO.setId_customer(((CustomerDTO) sCustomer.getSelectedItem()).getId());
            requestCreateDTO.setId_storehouse_from(((StorehouseDTO) sFrom.getSelectedItem()).getId());
            requestCreateDTO.setId_storehouse_to(((StorehouseDTO) sTo.getSelectedItem()).getId());

            List<RequestItemCreateDTO> items = new LinkedList<>();
            for(RequestItemDTO dto : data){
                items.add(new RequestItemCreateDTO(dto.getCount(), dto.getProduct().getId()));
            }

            requestCreateDTO.setItems(items);

            App.getFromCustomerApi().start(requestCreateDTO).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    onBackPressed();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
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
