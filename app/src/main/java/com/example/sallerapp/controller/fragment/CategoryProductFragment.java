package com.example.sallerapp.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sallerapp.MainActivity;
import com.example.sallerapp.R;
import com.example.sallerapp.adapter.CategoryProductAdapter;
import com.example.sallerapp.controller.view.BillActivity;
import com.example.sallerapp.database.CategoryProductDao;
import com.example.sallerapp.databinding.FragmentCategoryProductBinding;
import com.example.sallerapp.desgin_pattern.single_pantter.SingleAccount;
import com.example.sallerapp.funtions.MyFragment;
import com.example.sallerapp.model.CategoryProduct;
import com.example.sallerapp.model.ShopAccount;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;
import java.util.List;


public class CategoryProductFragment extends Fragment {

    public static String TAG = CategoryProductFragment.class.getSimpleName();
    private FragmentCategoryProductBinding cateProBinding;
    ShopAccount shopAccount = SingleAccount.getInstance().getShopAccount();
    private CategoryProductAdapter adapter;
    public CategoryProductFragment() {
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
        cateProBinding = FragmentCategoryProductBinding.inflate(inflater,container,false);
        initView();
        cateProBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), MainActivity.class));
            }
        });
        return cateProBinding.getRoot();
    }

    private void initView() {
        AdRequest adRequest = new AdRequest.Builder().build();
        cateProBinding.adView.loadAd(adRequest);

        CategoryProductDao.getCategoryProduct(shopAccount.getShopId(), new CategoryProductDao.GetData() {
            @Override
            public void getData(ArrayList<CategoryProduct> categoryProducts) {
                Log.e(TAG, "getData: " + categoryProducts.size() );
                adapter = new CategoryProductAdapter(categoryProducts);
                cateProBinding.rcvCatePro.setAdapter(adapter);
                cateProBinding.rcvCatePro.setLayoutManager(new LinearLayoutManager(requireContext()));
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
                cateProBinding.rcvCatePro.addItemDecoration(dividerItemDecoration);
            }
        });

        cateProBinding.searchCategoryPro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.TK(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cateProBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reaLoad();
                cateProBinding.swipeRefresh.setRefreshing(false);
            }
        });

        cateProBinding.addCatePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFragment.replaceFragment(requireActivity().getSupportFragmentManager()
                        , R.id.fragmentAddPro
                        , new AddCategoryProductFragment()
                        , true);
            }
        });
    }

    private void reaLoad() {
        CategoryProductDao.getCategoryProduct(shopAccount.getShopId(), new CategoryProductDao.GetData() {
            @Override
            public void getData(ArrayList<CategoryProduct> categoryProducts) {
                adapter.setData(categoryProducts);
            }
        });
    }
}