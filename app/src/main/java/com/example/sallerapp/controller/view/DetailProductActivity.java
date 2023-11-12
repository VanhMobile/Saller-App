package com.example.sallerapp.controller.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.sallerapp.MainActivity;
import com.example.sallerapp.R;
import com.example.sallerapp.adapter.ListBillAdapter;
import com.example.sallerapp.database.BillDao;
import com.example.sallerapp.databinding.ActivityDetailProductBinding;
import com.example.sallerapp.funtions.MoneyFormat;
import com.example.sallerapp.model.Bill;
import com.example.sallerapp.model.Product;

import java.util.ArrayList;

public class DetailProductActivity extends AppCompatActivity {

    private ActivityDetailProductBinding detailProductBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailProductBinding = ActivityDetailProductBinding.inflate(getLayoutInflater());
        setContentView(detailProductBinding.getRoot());
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("product");
        if (product == null) {
            return;
        }
        detailProductBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailProductActivity.this, MainActivity.class));
                finish();
            }
        });
        detailProductBinding.productId.setText("Mã sản phẩm: " + product.getProductId());
        detailProductBinding.date.setText(product.getDate());
        detailProductBinding.productName.setText(product.getProductName());
        detailProductBinding.productQuantity.setText(product.getQuantity()+"");
        detailProductBinding.catePro.setText(product.getCate());
        detailProductBinding.retailPrice.setText(MoneyFormat.moneyFormat(product.getRetailPrice()));
        detailProductBinding.wholeSalePrice.setText(MoneyFormat.moneyFormat(product.getWholeSalePrice()));
        Glide.with(this)
                .load(product.getImgPath())
                .error(R.drawable.product_img)
                .into(detailProductBinding.imgProduct);

        loadData(product);
        detailProductBinding.btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData(product);
            }
        });
    }

    private void loadData(Product product) {
        BillDao.GetBills("Shop_1", new BillDao.GetData() {
            @Override
            public void getData(ArrayList<Bill> bills) {
                int exportQuantity = 0;
                int revenue = 0;
                ArrayList<Bill> data = new ArrayList<>();
                for (int i = 0; i < bills.size(); i++){
                    for (int j = 0; j < bills.get(i).getListProduct().size(); j++){
                        if (bills.get(i).getListProduct().get(j)
                                .getProduct().getProductId().equals(product.getProductId())){
                            exportQuantity += bills.get(i).getListProduct().get(j).getQuantity();
                            data.add(bills.get(i));
                            if(bills.get(i).getBillType().equals("Giá bán lẻ")){
                                revenue += (bills.get(i).getListProduct().get(j).getProduct().getRetailPrice() * bills.get(i).getListProduct().get(j).getQuantity());
                            }else{
                                revenue += (bills.get(i).getListProduct().get(j).getProduct().getWholeSalePrice() * bills.get(i).getListProduct().get(j).getQuantity());
                            }
                        }
                    }
                }
                ListBillAdapter billAdapter = new ListBillAdapter(data);
                DividerItemDecoration itemDecoration = new DividerItemDecoration(DetailProductActivity.this,DividerItemDecoration.VERTICAL);
                detailProductBinding.RecycBill.addItemDecoration(itemDecoration);
                detailProductBinding.RecycBill.setAdapter(billAdapter);
                detailProductBinding.RecycBill.setLayoutManager(new LinearLayoutManager(DetailProductActivity.this));
                detailProductBinding.exportQuantity.setText(exportQuantity+"");
                detailProductBinding.revenue.setText(MoneyFormat.moneyFormat(revenue));
            }
        });
    }
}