package com.example.sallerapp.controller.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import com.example.sallerapp.MainActivity;
import com.example.sallerapp.R;
import com.example.sallerapp.adapter.ListCustomerAdapter;
import com.example.sallerapp.database.CustomerDao;
import com.example.sallerapp.databinding.FragmentListCustomersBinding;
import com.example.sallerapp.funtions.MyFragment;
import com.example.sallerapp.model.Customer;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;


public class Fragment_list_customers extends Fragment {

    private FragmentListCustomersBinding listCustomerBinding;
    private ArrayList<Customer> customerArrayList = new ArrayList<>();
    private ArrayList<Customer> listTemp = new ArrayList<>();
    private ListCustomerAdapter adapter;

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


        CustomerDao.getCustomers("Shop_1", new CustomerDao.GetData() {
            @Override
            public void getData(ArrayList<Customer> customers) {
                customerArrayList.addAll(customers);
                listTemp = customers;
                if (isAdded()){
                    adapter = new ListCustomerAdapter(customerArrayList, new ListCustomerAdapter.Click() {
                        @Override
                        public void clickBtnCall(Customer customer) {
                            // Tạo một Intent với hành động ACTION_DIAL
                            Intent intent = new Intent(Intent.ACTION_DIAL);

                            // Đặt dữ liệu Uri cho số điện thoại cần gọi
                            intent.setData(Uri.parse("tel:" + customer.getNumberPhone()));

                            // Kiểm tra xem ứng dụng Gọi điện thoại có sẵn trên thiết bị hay chưa
                            if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                                // Nếu có, mở ứng dụng Gọi điện thoại
                                startActivity(intent);
                            } else {
                                Toast.makeText(requireContext(), "Không tìm thấy ứng dụng phù hợp", Toast.LENGTH_SHORT).show();
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
}