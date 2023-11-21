package com.example.sallerapp.controller.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.example.sallerapp.MainActivity;
import com.example.sallerapp.R;
import com.example.sallerapp.adapter.ListBillAdapter;
import com.example.sallerapp.database.BillDao;
import com.example.sallerapp.database.ProductDao;
import com.example.sallerapp.databinding.ActivityDetailProductBinding;
import com.example.sallerapp.databinding.DialogUpdateProductBinding;
import com.example.sallerapp.desgin_pattern.single_pantter.SingleAccount;
import com.example.sallerapp.funtions.MoneyFormat;
import com.example.sallerapp.funtions.Validations;
import com.example.sallerapp.model.Bill;
import com.example.sallerapp.model.Product;
import com.example.sallerapp.model.ShopAccount;

import java.util.ArrayList;

public class DetailProductActivity extends AppCompatActivity {

    private ActivityDetailProductBinding detailProductBinding;
    ShopAccount shopAccount = SingleAccount.getInstance().getShopAccount();

    ListBillAdapter billAdapter;

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
        if (product.getStatus().equals("ẩn")){
            detailProductBinding.statusProduct.setText("Hiện");
        }else{
            detailProductBinding.statusProduct.setText("Ẩn");
        }
        getInForProduct(product);
        Glide.with(this)
                .load(product.getImgPath())
                .error(R.drawable.product_img)
                .into(detailProductBinding.imgProduct);

        detailProductBinding.btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData(product);
            }
        });

        detailProductBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUpdateProductBinding updateProductBinding = DialogUpdateProductBinding.inflate(getLayoutInflater());
                Dialog dialog = new Dialog(DetailProductActivity.this);
                dialog.setContentView(updateProductBinding.getRoot());
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                updateProductBinding.nameProduct.setText(product.getProductName());
                updateProductBinding.quantityProduct.setText(product.getQuantity()+"");
                updateProductBinding.cost.setText(product.getCost()+"");
                updateProductBinding.wholeSalePrice.setText(product.getWholeSalePrice()+"");
                updateProductBinding.retailPrice.setText(product.getRetailPrice()+"");


                updateProductBinding.btnUpData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int count = 0;

                        if (Validations.isEmptyPress(updateProductBinding.nameProduct)){
                            count ++;
                        }

                        if (!Validations.isEmptyPress(updateProductBinding.quantityProduct)){
                            if (!Validations.isQuantityPress(updateProductBinding.quantityProduct)){
                                count++;
                            }
                        } else {
                            count++;
                        }

                        if (!Validations.isEmptyPress(updateProductBinding.wholeSalePrice)){
                            if (!Validations.isQuantityPress(updateProductBinding.wholeSalePrice)){
                                count++;
                            }
                        } else {
                            count++;
                        }

                        if (!Validations.isEmptyPress(updateProductBinding.retailPrice)){
                            if (!Validations.isQuantityPress(updateProductBinding.retailPrice)){
                                count++;
                            }
                        } else {
                            count++;
                        }

                        if (!Validations.isEmptyPress(updateProductBinding.cost)){
                            if (!Validations.isQuantityPress(updateProductBinding.cost)){
                                count++;
                            }
                        } else {
                            count++;
                        }

                        if (count != 0){
                            return;
                        }
                        String name = updateProductBinding.nameProduct.getText().toString();
                        int quantity = Integer.parseInt(updateProductBinding.quantityProduct.getText().toString().trim());
                        int cost = Integer.parseInt(updateProductBinding.cost.getText().toString().trim());
                        int retailPrice = Integer.parseInt(updateProductBinding.retailPrice.getText().toString().trim());
                        int wholeSalePrice = Integer.parseInt(updateProductBinding.wholeSalePrice.getText().toString().trim());
                        product.setProductName(name);
                        product.setQuantity(quantity + product.getQuantity());
                        product.setCost(cost);
                        product.setRetailPrice(retailPrice);
                        product.setWholeSalePrice(wholeSalePrice);
                        getInForProduct(product);
                        ProductDao.insertProduct(product, shopAccount.getShopId());
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        BillDao.GetBills(shopAccount.getShopId(), new BillDao.GetData() {
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
                billAdapter = new ListBillAdapter(data, new ListBillAdapter.Click() {
                    @Override
                    public void clickItem(Bill bill) {

                    }
                });
                DividerItemDecoration itemDecoration = new DividerItemDecoration(DetailProductActivity.this,DividerItemDecoration.VERTICAL);
                detailProductBinding.RecycBill.addItemDecoration(itemDecoration);
                detailProductBinding.RecycBill.setAdapter(billAdapter);
                detailProductBinding.RecycBill.setLayoutManager(new LinearLayoutManager(DetailProductActivity.this));
                detailProductBinding.exportQuantity.setText(exportQuantity+"");
                detailProductBinding.revenue.setText(MoneyFormat.moneyFormat(revenue));
            }
        });

        detailProductBinding.statusProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (product.getStatus().equals("ẩn")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailProductActivity.this);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn muốn hiển thị sản phẩm này?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            product.setStatus("hiện");
                            detailProductBinding.statusProduct.setText("Ẩn");
                            ProductDao.insertProduct(product, shopAccount.getShopId());
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailProductActivity.this);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn muốn ẩn sản phẩm này?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            product.setStatus("ẩn");
                            detailProductBinding.statusProduct.setText("Hiện");
                            ProductDao.insertProduct(product, shopAccount.getShopId());
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    private void getInForProduct(Product product) {
        detailProductBinding.productId.setText("Mã sản phẩm: " + product.getProductId());
        detailProductBinding.date.setText(product.getDate());
        detailProductBinding.productName.setText(product.getProductName());
        detailProductBinding.productQuantity.setText(product.getQuantity()+"");
        detailProductBinding.catePro.setText(product.getCate());
        detailProductBinding.cost.setText(MoneyFormat.moneyFormat(product.getCost()));
        detailProductBinding.retailPrice.setText(MoneyFormat.moneyFormat(product.getRetailPrice()));
        detailProductBinding.wholeSalePrice.setText(MoneyFormat.moneyFormat(product.getWholeSalePrice()));
    }

    private void loadData(Product product) {
       BillDao.GetBills(shopAccount.getShopId(), new BillDao.GetData() {
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

               billAdapter.setData(data);
               detailProductBinding.exportQuantity.setText(exportQuantity+"");
               detailProductBinding.revenue.setText(MoneyFormat.moneyFormat(revenue));
           }
       });
    }
}