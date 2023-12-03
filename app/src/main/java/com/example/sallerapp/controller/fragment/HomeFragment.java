package com.example.sallerapp.controller.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sallerapp.controller.view.BillActivity;
import com.example.sallerapp.controller.view.CustomerActivity;
import com.example.sallerapp.controller.view.EmployeeActivity;
import com.example.sallerapp.controller.view.NetworkChangeActivity;
import com.example.sallerapp.controller.view.ProductActivity;
import com.example.sallerapp.database.BillDao;
import com.example.sallerapp.database.CategoryProductDao;
import com.example.sallerapp.database.ProductDao;
import com.example.sallerapp.databinding.FragmentHomeBinding;
import com.example.sallerapp.desgin_pattern.build_pantter.CategoryProductBuilder;
import com.example.sallerapp.desgin_pattern.build_pantter.ProductBuilder;
import com.example.sallerapp.desgin_pattern.single_pantter.CartShopSingle;
import com.example.sallerapp.desgin_pattern.single_pantter.SingleAccount;
import com.example.sallerapp.funtions.IdGenerator;
import com.example.sallerapp.funtions.MessengerManager;
import com.example.sallerapp.funtions.MoneyFormat;
import com.example.sallerapp.funtions.RequestPermissions;
import com.example.sallerapp.model.Bill;
import com.example.sallerapp.model.CartShop;
import com.example.sallerapp.model.CategoryProduct;
import com.example.sallerapp.model.Product;
import com.example.sallerapp.model.ShopAccount;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment{

    private FragmentHomeBinding homeBinding;

    private NetworkChangeActivity networkChangeActivity;
    float inventoryQuantity = 0;
    float exportQuantity = 0;

    ShopAccount shopAccount = SingleAccount.getInstance().getShopAccount();
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        initView();
        return homeBinding.getRoot();
    }

    private void initView() {

        AdRequest adRequest = new AdRequest.Builder().build();
        homeBinding.adView.loadAd(adRequest);
        homeBinding.adView2.loadAd(adRequest);

        CartShopSingle cartShopSingle = CartShopSingle.getInstance();

        if (cartShopSingle.getProducts().size() == 0){
            homeBinding.cartSize.setVisibility(View.GONE);
        }else{
            homeBinding.cartSize.setVisibility(View.VISIBLE);
            homeBinding.cartSize.setText(cartShopSingle.getProducts().size() + "");
        }

        homeBinding.shortcut.btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), ProductActivity.class);
                intent.putExtra("product", "addProduct");
                startActivity(intent);
            }
        });

        homeBinding.help.btnMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessengerManager.openMessengerWithLink("https://www.facebook.com/messages/148593518345206",requireActivity());
            }
        });
        homeBinding.iconCartShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), BillActivity.class);
                intent.putExtra("bill", "AddBill");
                startActivity(intent);
            }
        });

        homeBinding.shortcut.createBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), BillActivity.class);
                intent.putExtra("bill", "AddBill");
                startActivity(intent);
            }
        });


        homeBinding.shortcut.addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), EmployeeActivity.class));
            }
        });

        homeBinding.shortcut.customerManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(),  CustomerActivity.class);
                intent.putExtra("customer", "listCustomer");
                startActivity(intent);
            }
        });

        homeBinding.shortcut.customerCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(),  CustomerActivity.class);
                intent.putExtra("customer", "categoryCustomer");
                startActivity(intent);
            }
        });

        homeBinding.shortcut.productCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), ProductActivity.class);
                intent.putExtra("product", "categoryProduct");
                startActivity(intent);
            }
        });

        homeBinding.help.btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + "quanlykhohang204@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Cần giúp đỡ");
                intent.putExtra(Intent.EXTRA_TEXT, "Viết vấn đề của bạn vào đây");

                startActivity(Intent.createChooser(intent, "Choose an Email Client"));
            }
        });

        statistical();
        barChart();

        homeBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                statistical();
                barChart();
                CartShopSingle cartShopSingle = CartShopSingle.getInstance();

                if (cartShopSingle.getProducts().size() == 0){
                    homeBinding.cartSize.setVisibility(View.GONE);
                }else{
                    homeBinding.cartSize.setVisibility(View.VISIBLE);
                    homeBinding.cartSize.setText(cartShopSingle.getProducts().size() + "");
                }
                homeBinding.swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void statistical(){
        BillDao.bill30Day(shopAccount.getShopId(), new BillDao.GetData() {
            @Override
            public void getData(ArrayList<Bill> bills) {
                double totalRevenue = 0;
                double totalEx = 0;

                for (Bill bill : bills){
                    totalRevenue += bill.getSumPrice();
                    for (CartShop cartShop: bill.getListProduct()){
                        totalEx += (cartShop.getProduct().getCost() * cartShop.getQuantity());
                    }
                }

                homeBinding.tvTotalRevenue.setText(MoneyFormat.moneyFormat(totalRevenue));
                homeBinding.tvTotalExpenditure.setText(MoneyFormat.moneyFormat(totalEx));
                homeBinding.sumPrice.setText("Lợi nhuận: "+MoneyFormat.moneyFormat(totalRevenue - totalEx));
            }
        });

        BillDao.billDay(shopAccount.getShopId(), new BillDao.GetData() {
            @Override
            public void getData(ArrayList<Bill> bills) {
                double totalRevenue = 0;
                double totalEx = 0;

                for (Bill bill : bills){
                    totalRevenue += bill.getSumPrice();
                    for (CartShop cartShop: bill.getListProduct()){
                        totalEx += (cartShop.getProduct().getCost() * cartShop.getQuantity());
                    }
                }

                homeBinding.tvTotalRevenueDay.setText(MoneyFormat.moneyFormat(totalRevenue));
                homeBinding.tvTotalExpenditureDay.setText(MoneyFormat.moneyFormat(totalEx));
                homeBinding.sumPirceDay.setText("Lợi nhuận: "+MoneyFormat.moneyFormat(totalRevenue - totalEx));
            }
        });
    }

    private void barChart(){
        ArrayList<String> xValue = new ArrayList<>();
        xValue.add("Nhập");
        xValue.add("Xuất");
        xValue.add("Tồn");
        ArrayList<BarEntry> entries = new ArrayList<>();
        ProductDao.getProducts(shopAccount.getShopId(), new ProductDao.GetData() {
            @Override
            public void getData(ArrayList<Product> products) {
                if (inventoryQuantity == 0){
                    for (Product product: products){
                        inventoryQuantity += product.getQuantity();
                    }
                }
            }
        });
        BillDao.GetBills(shopAccount.getShopId(), new BillDao.GetData() {
            @Override
            public void getData(ArrayList<Bill> bills) {
                if (exportQuantity == 0){
                    for (Bill bill : bills){
                        exportQuantity += bill.getQuantity();
                    }
                }
            }
        });
        entries.add(new BarEntry(0,inventoryQuantity + exportQuantity));
        entries.add(new BarEntry(1,exportQuantity));
        entries.add(new BarEntry(2,inventoryQuantity));

        YAxis yAxis = new YAxis();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(100f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

        BarDataSet barDataSet = new BarDataSet(entries,"Chú thích");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData barData = new BarData(barDataSet);

        homeBinding.barChart.setData(barData);

        homeBinding.barChart.getDescription().setEnabled(false);
        homeBinding.barChart.invalidate();

        homeBinding.barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValue));
        homeBinding.barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        homeBinding.barChart.getXAxis().setGranularity(1f);
        homeBinding.barChart.getXAxis().setGranularityEnabled(true);
    }
}