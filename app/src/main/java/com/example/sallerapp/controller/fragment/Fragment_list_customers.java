package com.example.sallerapp.controller.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sallerapp.MainActivity;
import com.example.sallerapp.R;
import com.example.sallerapp.adapter.ListCustomerAdapter;
import com.example.sallerapp.database.CustomerDao;
import com.example.sallerapp.databinding.FragmentListCustomersBinding;
import com.example.sallerapp.desgin_pattern.single_pantter.SingleAccount;
import com.example.sallerapp.funtions.MyFragment;
import com.example.sallerapp.model.Customer;
import com.example.sallerapp.model.ShopAccount;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;


public class Fragment_list_customers extends Fragment {

    private FragmentListCustomersBinding listCustomerBinding;
    private ListCustomerAdapter adapter;
    ShopAccount shopAccount = SingleAccount.getInstance().getShopAccount();

    private final String TAG = Fragment_list_customers.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listCustomerBinding = FragmentListCustomersBinding.inflate(inflater,container,false);
        initView();


        return listCustomerBinding.getRoot();
    }

    private void initView() {
        AdRequest adRequest = new AdRequest.Builder().build();
        listCustomerBinding.adView.loadAd(adRequest);

        CustomerDao.getCustomers(shopAccount.getShopId(), new CustomerDao.GetData() {
            @Override
            public void getData(ArrayList<Customer> customers) {
                if (isAdded()){
                    adapter = new ListCustomerAdapter(customers, new ListCustomerAdapter.Click() {
                        @Override
                        public void clickBtnCall(Customer customer) {
                            String phoneNumber = "tel:" + customer.getNumberPhone();

                            // Tạo Intent với hành động ACTION_CALL
                            Intent callIntent = new Intent(Intent.ACTION_CALL);

                            // Đặt dữ liệu URI cho số điện thoại
                            callIntent.setData(Uri.parse(phoneNumber));

                            // Kiểm tra xem có quyền CALL_PHONE hay không
                            if (requireActivity().checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                // Nếu có quyền, bắt đầu Intent
                                startActivity(callIntent);
                            } else {
                                // Nếu không có quyền, yêu cầu quyền từ người dùng
                                requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                            }
                        }

                        @Override
                        public void clickItem(Customer customer) {

                        }
                    });
                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(),
                            DividerItemDecoration.VERTICAL);
                    listCustomerBinding.recyclerViewListCustomer.addItemDecoration(dividerItemDecoration);
                    listCustomerBinding.recyclerViewListCustomer.setAdapter(adapter);
                    listCustomerBinding.recyclerViewListCustomer.setLayoutManager(layoutManager);
                    listCustomerBinding.edtSearchCustomer.addTextChangedListener(new TextWatcher() {
                        //ArrayList<Customer> listTemp = customerArrayList;
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            adapter.filterListCustomer(charSequence.toString().toLowerCase());
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                }
            }
        });

        listCustomerBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reaLoad();
                listCustomerBinding.swipeRefresh.setRefreshing(false);
            }
        });
        listCustomerBinding.addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFragment.replaceFragment(requireActivity().getSupportFragmentManager()
                        , R.id.fragmentCustomer
                        , new Fragment_add_customer()
                        , true);
            }
        });

        listCustomerBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireActivity(), MainActivity.class));
                requireActivity().finish();
            }
        });


    }

    private void reaLoad() {
       CustomerDao.getCustomers(shopAccount.getShopId(), new CustomerDao.GetData() {
           @Override
           public void getData(ArrayList<Customer> customers) {
               adapter.setDATA(customers);
           }
       });
    }
}