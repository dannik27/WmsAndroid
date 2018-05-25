package com.patis.wms.android.screen.new_request;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.patis.wms.android.App;
import com.patis.wms.android.R;
import com.patis.wms.android.dto.CustomerDTO;
import com.patis.wms.android.dto.entity.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestItemDialogView extends LinearLayout{

    private Spinner product;
    private EditText count;


    public RequestItemDialogView() {
        super(App.getContext());

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_new_request_item_dialog, this, true);

        product = findViewById(R.id.product);
        count = findViewById(R.id.count);

        App.getBackendApi().getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.body() != null){
                    ArrayAdapter<Product> adapter1 = new ArrayAdapter<>(App.getContext(), android.R.layout.simple_spinner_item, response.body());
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    product.setAdapter(adapter1);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

    }

    public Product getProduct(){
        return (Product) product.getSelectedItem();
    }

    public int getCount(){
        try{
            return Integer.valueOf(count.getText().toString());
        }catch (NumberFormatException ex){
            return 0;
        }

    }



}
