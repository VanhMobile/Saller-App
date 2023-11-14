package com.example.sallerapp.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sallerapp.R;
import com.example.sallerapp.databinding.ItemListProductsBinding;
import com.example.sallerapp.funtions.MoneyFormat;
import com.example.sallerapp.model.Product;

import java.util.ArrayList;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ViewHolder> {

    private ArrayList<Product> products;

    private ArrayList<Product> filteredList;

    Click click;

    public ListProductAdapter(ArrayList<Product> products, Click click) {
        this.products = products;
        this.click = click;
        this.filteredList = new ArrayList<>(products);
    }

    public void setData(ArrayList<Product> products){
        this.filteredList = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListProductsBinding productsBinding = ItemListProductsBinding.inflate(
                LayoutInflater.from(parent.getContext())
                , parent
                , false);
        return new ViewHolder(productsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = filteredList.get(position);
        int index = position + 1;
        if (product.getQuantity() == 0){
            holder.productsBinding.btnAddBill.setVisibility(View.GONE);
        }else{
            holder.productsBinding.btnAddBill.setVisibility(View.VISIBLE);
        }
        holder.productsBinding.nameProduct.setText(index + "." + product.getProductName());
        holder.productsBinding.costProduct.setText("Giá vốn: " + MoneyFormat.moneyFormat(product.getCost()));
        holder.productsBinding.priceProduct.setText(MoneyFormat.moneyFormat(product.getRetailPrice()));
        holder.productsBinding.quantityProduct.setText("Tồn kho: " + product.getQuantity());
        Glide.with(holder.productsBinding.imgProduct.getContext())
                .load(product.getImgPath())
                .error(R.drawable.product_img)
                .into(holder.productsBinding.imgProduct);

        holder.productsBinding.btnAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.clickBtnAdd(product);
                addCarAnimation(holder.productsBinding.addOne);
            }
        });

        holder.productsBinding.itemProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.clickItem(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemListProductsBinding productsBinding;

        public ViewHolder(@NonNull ItemListProductsBinding productsBinding) {
            super(productsBinding.getRoot());
            this.productsBinding = productsBinding;
        }
    }

    public interface Click {
        void clickBtnAdd(Product product);
        void clickItem(Product product);
    }

    public void addCarAnimation(TextView textView){
        if (textView.getVisibility() == View.GONE) {
            // Nếu TextView đang ẩn, hiển thị nó với hiệu ứng
            textView.setAlpha(0f);
            textView.setVisibility(View.VISIBLE);

            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f);
            fadeIn.setDuration(1000); // 1 giây

            ObjectAnimator translateY = ObjectAnimator.ofFloat(textView, "translationY", 0f, -60f);
            translateY.setDuration(500); // 0.5 giây

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(fadeIn, translateY);
            animatorSet.start();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ObjectAnimator fadeOut = ObjectAnimator.ofFloat(textView, "alpha", 1f, 0f);
                    fadeOut.setDuration(1000); // 1 giây
                    fadeOut.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            textView.setVisibility(View.GONE);
                        }
                    });
                    fadeOut.start();
                }
            }, 500);
        }
    }


    public void filter(String s) {
        filteredList.clear();
        if (s.isEmpty()) {
            filteredList.addAll(products);
        } else {
            products.forEach(o -> {
                if (o.getProductName().toLowerCase().contains(s.toLowerCase()) || o.getCate().toLowerCase().contains(s.toLowerCase())){
                    filteredList.add(o);
                }
            });
        }
        notifyDataSetChanged();
    }

}
