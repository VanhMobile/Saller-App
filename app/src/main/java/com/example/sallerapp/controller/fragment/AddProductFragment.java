package com.example.sallerapp.controller.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.sallerapp.R;
import com.example.sallerapp.databinding.BottomDialogCameraBinding;
import com.example.sallerapp.databinding.DialogAttributeProductBinding;
import com.example.sallerapp.databinding.FragmentAddProductBinding;
import com.example.sallerapp.funtions.RequestPermissions;
import com.example.sallerapp.funtions.Validations;
import com.google.android.gms.ads.AdRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddProductFragment extends Fragment {

    private FragmentAddProductBinding productBinding;
    Bitmap imageBitmap;
    BottomSheetDialog dialog;

    public AddProductFragment() {
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
        // Inflate the layout for this fragment
        productBinding = FragmentAddProductBinding.inflate(inflater,container,false);
        initView();
        return productBinding.getRoot();
    }

    private void initView() {
        AdRequest adRequest = new AdRequest.Builder().build();
        productBinding.adView.loadAd(adRequest);
        productBinding.addImgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RequestPermissions.requestReadImgGalleryCamera(requireContext())){
                    showDialog();
                }
            }
        });

        productBinding.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        Validations.isEmpty(productBinding.edtProductId);
        Validations.isEmpty(productBinding.edtNameProduct);
        Validations.isQuantity(productBinding.edtProductCost);
        Validations.isQuantity(productBinding.edtRetailProduct);
        Validations.isQuantity(productBinding.edtWholeSalePriceProduct);
        Validations.isQuantity(productBinding.edtQuantityProduct);

        productBinding.btnAddAttPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetAtt();
            }
        });

        productBinding.tvCategoryProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiaLogCategory();
            }
        });
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String now = dateFormat.format(new Date());
        productBinding.date.setText(now);
    }

    private void bottomSheetAtt() {
        DialogAttributeProductBinding attributeProductBinding = DialogAttributeProductBinding.inflate(getLayoutInflater());
        Dialog dialogAtt = new Dialog(requireContext());
        dialogAtt.setContentView(attributeProductBinding.getRoot());
        dialogAtt.getWindow().setLayout(
                  WindowManager.LayoutParams.WRAP_CONTENT
                , WindowManager.LayoutParams.WRAP_CONTENT);
        dialogAtt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogAtt.show();
    }

    private void showDiaLogCategory() {

    }

    private void showDialog() {
        BottomDialogCameraBinding cameraBinding = BottomDialogCameraBinding.inflate(getLayoutInflater());
        dialog = new BottomSheetDialog(requireContext(),R.style.BottomSheetDialogThem);
        dialog.setContentView(cameraBinding.getRoot());
        cameraBinding.btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });

        cameraBinding.btnLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1000);
            }
        });

        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            productBinding.imgProduct.setImageBitmap(imageBitmap);
            productBinding.addImgProduct.setVisibility(View.GONE);
            productBinding.imgProduct.setVisibility(View.VISIBLE);
            dialog.dismiss();
        } else if (requestCode == 1000 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                productBinding.imgProduct.setImageBitmap(imageBitmap);
                productBinding.addImgProduct.setVisibility(View.GONE);
                productBinding.imgProduct.setVisibility(View.VISIBLE);
                dialog.dismiss();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}