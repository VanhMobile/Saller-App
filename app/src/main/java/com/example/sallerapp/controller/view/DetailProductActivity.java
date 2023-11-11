package com.example.sallerapp.controller.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.sallerapp.MainActivity;
import com.example.sallerapp.R;
import com.example.sallerapp.databinding.ActivityDetailProductBinding;
import com.example.sallerapp.funtions.MoneyFormat;
import com.example.sallerapp.model.Product;

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
    }
}