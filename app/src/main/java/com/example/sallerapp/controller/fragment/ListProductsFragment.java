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

import com.example.sallerapp.R;
import com.example.sallerapp.adapter.CateProductDialogAdapter;
import com.example.sallerapp.adapter.ListProductAdapter;
import com.example.sallerapp.controller.view.DetailProductActivity;
import com.example.sallerapp.controller.view.ProductActivity;
import com.example.sallerapp.database.CategoryProductDao;
import com.example.sallerapp.database.ProductDao;
import com.example.sallerapp.databinding.BottomDialogFilterProBinding;
import com.example.sallerapp.databinding.FragmentListProductsBinding;
import com.example.sallerapp.desgin_pattern.single_pantter.CartShopSingle;
import com.example.sallerapp.desgin_pattern.single_pantter.SingleAccount;
import com.example.sallerapp.model.CartShop;
import com.example.sallerapp.model.CategoryProduct;
import com.example.sallerapp.model.Product;
import com.example.sallerapp.model.ShopAccount;
import com.google.android.gms.ads.AdRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

public class ListProductsFragment extends Fragment {

    private FragmentListProductsBinding productsBinding;

    ListProductAdapter productAdapter;
    ArrayList<CartShop> cartShops;

    ShopAccount shopAccount = SingleAccount.getInstance().getShopAccount();

    public ListProductsFragment() {
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
        productsBinding = FragmentListProductsBinding.inflate(inflater,container,false);
        initView();
        return productsBinding.getRoot();
    }

    private void initView() {

        AdRequest adRequest = new AdRequest.Builder().build();

        productsBinding.adView.loadAd(adRequest);
        productsBinding.btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), ProductActivity.class);
                intent.putExtra("product", "addProduct");
                startActivity(intent);
            }
        });

        reaLoad();

        productsBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reaLoad();
                productsBinding.swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void reaLoad() {
        ProductDao.getProducts(shopAccount.getShopId(), new ProductDao.GetData() {
            @Override
            public void getData(ArrayList<Product> products) {
                productAdapter = new ListProductAdapter(products, new ListProductAdapter.Click() {
                    @Override
                    public void clickBtnAdd(Product product) {
                        ArrayList<CartShop> shopArrayList = CartShopSingle.getInstance().getCartShops();
                        for (int i = 0; i < shopArrayList.size(); i++){
                            if (shopArrayList.get(i).getProduct().getProductId().equals(product.getProductId())){
                                if (shopArrayList.get(i).getQuantity() >= product.getQuantity()){
                                    return;
                                }
                            }
                        }
                        ArrayList<Product> dataCart = CartShopSingle.getInstance().getProducts();
                        dataCart.add(product);
                        CartShopSingle.getInstance().setProducts(dataCart);
                    }

                    @Override
                    public void clickItem(Product product) {
                        Intent intent = new Intent(requireContext(), DetailProductActivity.class);
                        intent.putExtra("product",product);
                        startActivity(intent);
                    }
                });

                productsBinding.recyclerListProducts.setAdapter(productAdapter);

                if (isAdded()){
                    DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
                    productsBinding.recyclerListProducts.addItemDecoration(itemDecoration);
                    productsBinding.recyclerListProducts.setLayoutManager(new LinearLayoutManager(requireActivity()));
                }
                productsBinding.edtSearchProduct.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        productAdapter.filter(charSequence.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                productsBinding.tvFilter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDiaLogCatePro(products);
                    }
                });
            }
        });
    }

    private void showDiaLogCatePro(ArrayList<Product> products) {
        BottomDialogFilterProBinding filterProBinding = BottomDialogFilterProBinding.inflate(getLayoutInflater());
        BottomSheetDialog bottomFilter= new BottomSheetDialog(requireContext(),R.style.BottomSheetDialogThem);
        bottomFilter.setContentView(filterProBinding.getRoot());

        filterProBinding.filterAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productAdapter.setData(products);
                productsBinding.tvFilter.setText("Tất cả");
                bottomFilter.dismiss();
            }
        });

        CategoryProductDao.getCategoryProduct(shopAccount.getShopId(), new CategoryProductDao.GetData() {
            @Override
            public void getData(ArrayList<CategoryProduct> categoryProducts) {
                CateProductDialogAdapter adapter = new CateProductDialogAdapter(categoryProducts, new CateProductDialogAdapter.Click() {
                    @Override
                    public void click(String nameCatePro) {
                        ArrayList<Product> filterList = (ArrayList<Product>)  products.stream()
                                .filter(item -> item.getCate().equals(nameCatePro))
                                .collect(Collectors.toList());
                        productAdapter.setData(filterList);
                        productsBinding.tvFilter.setText(nameCatePro);
                        bottomFilter.dismiss();
                    }
                });
                filterProBinding.reyCateProductDialog.setAdapter(adapter);
                filterProBinding.reyCateProductDialog.setLayoutManager(new LinearLayoutManager(requireContext()));
            }
        });

        bottomFilter.show();
    }
}