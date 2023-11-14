package com.example.sallerapp.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sallerapp.MainActivity;
import com.example.sallerapp.R;
import com.example.sallerapp.adapter.CategoryCustomerAdapter;
import com.example.sallerapp.database.CategoryCustomerDao;
import com.example.sallerapp.databinding.FragmentListCategoryCustomerBinding;
import com.example.sallerapp.funtions.MyFragment;
import com.example.sallerapp.model.CategoryCustomer;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;

public class ListCategoryCustomerFragment extends Fragment {

    private FragmentListCategoryCustomerBinding cateCusBinding;

    private ArrayList<CategoryCustomer> list;

    private CategoryCustomerAdapter adapter;

    public ListCategoryCustomerFragment() {
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
        cateCusBinding = FragmentListCategoryCustomerBinding.inflate(inflater,container,false);
        initView();
        return cateCusBinding.getRoot();
    }

    private void initView() {
        AdRequest adRequest = new AdRequest.Builder().build();
        cateCusBinding.adView.loadAd(adRequest);

        cateCusBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), MainActivity.class));
                requireActivity().finish();
            }
        });
        cateCusBinding.addCateCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFragment.replaceFragment(requireActivity().getSupportFragmentManager()
                        , R.id.fragmentCustomer
                        , new AddCategoryCustomerFragment()
                        , true);
            }
        });

       reaLoad();

       cateCusBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               reaLoad();
               cateCusBinding.swipeRefresh.setRefreshing(false);
           }
       });
    }

    private void reaLoad() {
        CategoryCustomerDao.getCategoryCustomers("Shop_1", new CategoryCustomerDao.GetData() {
            @Override
            public void getData(ArrayList<CategoryCustomer> categoryCustomers) {
                list = categoryCustomers;
                adapter = new CategoryCustomerAdapter(list);
                if (isAdded()){
                    // Áp dụng DividerItemDecoration cho RecyclerView
                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(),
                            layoutManager.getOrientation());
                    cateCusBinding.recListCategoryCus.addItemDecoration(dividerItemDecoration);
                    cateCusBinding.recListCategoryCus.setAdapter(adapter);
                    cateCusBinding.recListCategoryCus.setLayoutManager(layoutManager);

                    cateCusBinding.edtSearch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            adapter.filterCategoryCustomer(charSequence.toString());
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                }
            }
        });
    }
}