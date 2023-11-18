package com.example.sallerapp.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sallerapp.R;
import com.example.sallerapp.adapter.ListTypeCustomerDialogAdapter;
import com.example.sallerapp.controller.view.CustomerActivity;
import com.example.sallerapp.controller.view.ProductActivity;
import com.example.sallerapp.database.CategoryCustomerDao;
import com.example.sallerapp.database.CustomerDao;
import com.example.sallerapp.databinding.BottomDialogCustomerTypeBinding;
import com.example.sallerapp.databinding.FragmentAddCustomerBinding;
import com.example.sallerapp.desgin_pattern.build_pantter.CustomerBuilder;
import com.example.sallerapp.desgin_pattern.single_pantter.SingleAccount;
import com.example.sallerapp.funtions.IdGenerator;
import com.example.sallerapp.funtions.Validations;
import com.example.sallerapp.model.CategoryCustomer;
import com.example.sallerapp.model.Customer;
import com.example.sallerapp.model.ShopAccount;
import com.google.android.gms.ads.AdRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.HashSet;


public class Fragment_add_customer extends Fragment {

    private FragmentAddCustomerBinding binding;
    private ArrayList<Customer> customerArrayList = new ArrayList<>();
    ShopAccount shopAccount = SingleAccount.getInstance().getShopAccount();
    private ListTypeCustomerDialogAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddCustomerBinding.inflate(inflater,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        binding.btnAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inSertCustomer();
            }
        });

        binding.backFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(),  CustomerActivity.class);
                intent.putExtra("customer", "listCustomer");
                startActivity(intent);
            }
        });

        binding.CustomerType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomDialogCustomerTypeBinding typeBinding = BottomDialogCustomerTypeBinding.inflate(getLayoutInflater());
                BottomSheetDialog customerDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogThem);
                customerDialog.setContentView(typeBinding.getRoot());

                typeBinding.btnAddTypeDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(requireContext(), ProductActivity.class);
                        intent.putExtra("product", "addCustomerType");
                        startActivity(intent);
                    }
                });

                CategoryCustomerDao.getCategoryCustomers(shopAccount.getShopId(), new CategoryCustomerDao.GetData() {
                    @Override
                    public void getData(ArrayList<CategoryCustomer> customers) {
                        ArrayList<CategoryCustomer> categoryCustomers = customers;
                        if (isAdded()){
                            adapter = new ListTypeCustomerDialogAdapter
                                    (categoryCustomers, requireContext());
                            typeBinding.rcvTyeCustomer.setAdapter(adapter);
                            typeBinding.rcvTyeCustomer.setLayoutManager(new LinearLayoutManager(requireContext()));
                            adapter.setOnItemClickListener(new ListTypeCustomerDialogAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(CategoryCustomer customerType) {
                                    // Gọi dialog mới và gán giá trị vào widget
                                    binding.CustomerType.setText(customerType.getNameCategory());
                                    customerDialog.dismiss();
                                }
                            });
                        }else {
                            Toast.makeText(getContext(), "Đã xảy ra lỗi ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                customerDialog.show();

            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inSertCustomer();
            }
        });
    }

    private void inSertCustomer() {
        if (!Validations.isEmptyPress(binding.CustomerName) &&
                !Validations.isEmptyPress(binding.CustomerAddress) &&
                Validations.isPhoneNumberPress(binding.CustomerPhoneNumber) &&
                !binding.CustomerType.getText().toString().equals("Loại khách hàng")){

            CustomerDao.getCustomers(shopAccount.getShopId(), new CustomerDao.GetData() {
                @Override
                public void getData(ArrayList<Customer> customers) {
                    Customer customer = new CustomerBuilder()
                            .addId(IdGenerator.generateNextShopId(customers.size()+1, "KH_"))
                            .addName(binding.CustomerName.getText().toString())
                            .addAddress(binding.CustomerAddress.getText().toString())
                            .addNumberPhone(binding.CustomerPhoneNumber.getText().toString())
                            .addCustomerType(binding.CustomerType.getText().toString())
                            .addNote(binding.CustomerAddNote.getText().toString()).build();

                    CustomerDao.insertCustomer(customer,shopAccount.getShopId());
                    Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    clearData();
                }
            });

        }

    }


    public ArrayList<String> getUniqueCustomerTypes(ArrayList<Customer> customerArrayList) {
        ArrayList<String> uniqueCustomerTypes = new ArrayList<>();
        HashSet<String> uniqueSet = new HashSet<>();

        for (Customer customer : customerArrayList) {
            String customerType = customer.getCustomerType();
            if (!uniqueSet.contains(customerType)) {
                uniqueSet.add(customerType);
                uniqueCustomerTypes.add(customerType);
            }
        }

        return uniqueCustomerTypes;
    }

    public void clearData(){
        binding.CustomerAddress.setText("");
        binding.CustomerPhoneNumber.setText("");
        binding.CustomerName.setText("");
        binding.CustomerType.setText("Loại khách hàng");
        binding.CustomerAddNote.setText("");
    }


}