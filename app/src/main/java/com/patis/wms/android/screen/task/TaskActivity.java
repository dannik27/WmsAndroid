package com.patis.wms.android.screen.task;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.patis.wms.android.App;
import com.patis.wms.android.R;
import com.patis.wms.android.dto.DistributionDTO;
import com.patis.wms.android.dto.TaskDTO;
import com.patis.wms.android.dto.entity.TaskStatus;

import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskActivity extends AppCompatActivity {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US);


    private TextView tvCustomer;
    private TextView tvStatus;
    private TextView tvWorker;
    private TextView tvStorehouse;
    private TextView tvTimeBegin;
    private TextView tvTimeEnd;
    private TextView tvDone;
    private TextView tvCount;

    private Button btnStart;

    private RecyclerView recyclerView;
    private DistributionListAdapter listAdapter;

    private long taskId;
    private long workerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        tvCustomer = findViewById(R.id.tvCustomer);
        tvStatus = findViewById(R.id.tvStatus);
        tvWorker = findViewById(R.id.tvWorker);
        tvStorehouse = findViewById(R.id.tvStorehouse);
        tvTimeBegin = findViewById(R.id.tvTimeBegin);
        tvTimeEnd = findViewById(R.id.tvTimeEnd);
        tvDone = findViewById(R.id.tvDone);
        tvCount = findViewById(R.id.tvCount);
        recyclerView = findViewById(R.id.recyclerView);

        btnStart = findViewById(R.id.btnStart);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(llm);
        listAdapter = new DistributionListAdapter();
        recyclerView.setAdapter(listAdapter);

        if(getIntent().hasExtra("taskId")){
            taskId = getIntent().getLongExtra("taskId", 0);
            updateData();
        }

        listAdapter.setListener(distribution->{

            if(distribution.isDone()){
                return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(distribution.getProduct().getName() + " " + distribution.getCount() + " шт." + " ячейка: " + distribution.getCell().getName())
                    .setPositiveButton("Выполнено", (dialog, id) ->
                            App.getBackendApi().completeDistribution(taskId, distribution.getId()).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    updateData();
                                }
                                @Override public void onFailure(Call<Void> call, Throwable t) {}}))

                    .setNegativeButton("Отмена", (dialog, id) -> {
                        // User cancelled the dialog
                    });


            builder.create().show();

        });

        btnStart.setOnClickListener(e->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Взять задачу в работу?")
                    .setPositiveButton("Да", (dialog, id) -> {
                        App.getBackendApi().startTask(taskId).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                updateData();
                            }
                            @Override public void onFailure(Call<Void> call, Throwable t) {}
                        });
                    })
                    .setNegativeButton("Нет", (dialog, id) -> {
                        // User cancelled the dialog
                    });

            builder.create().show();

        });



    }

    public void updateData(){

        App.getBackendApi().getTaskById(taskId).enqueue(new Callback<TaskDTO>() {
            @Override
            public void onResponse(Call<TaskDTO> call, Response<TaskDTO> response) {
                TaskDTO task = response.body();
                if(task != null){

                    workerId = task.getWorker().getId();

                    if(task.getTaskStatus() != TaskStatus.READY){
                        btnStart.setVisibility(View.GONE);
                    }

                    int done = 0;
                    for (DistributionDTO distribution : task.getDistributions()){
                        if(distribution.isDone()){
                            done += 1;
                        }
                    }

                    tvCustomer.setText(task.getCustomerName());
                    tvStatus.setText(task.getTaskStatus().toString());
                    tvWorker.setText(task.getWorker().getPerson().getFio());
                    tvStorehouse.setText(task.getStorehouseName());
                    tvTimeBegin.setText(dateFormat.format(task.getTimeBegin()));
                    if(task.getTimeEnd() != null){
                        tvTimeEnd.setText(dateFormat.format(task.getTimeEnd()));
                    }else{
                        tvTimeEnd.setText("-");
                    }

                    tvDone.setText(String.valueOf(done));
                    tvCount.setText(String.valueOf(task.getDistributions().size()));

                    listAdapter.setData(task.getDistributions());
                    listAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TaskDTO> call, Throwable t) {

            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
