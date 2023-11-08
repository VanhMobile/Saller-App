package com.example.sallerapp.controller.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.sallerapp.MainActivity;
import com.example.sallerapp.R;
import com.example.sallerapp.adapter.AttributeProductAdapter;
import com.example.sallerapp.adapter.CateProductDialogAdapter;
import com.example.sallerapp.controller.view.ProductActivity;
import com.example.sallerapp.database.CategoryProductDao;
import com.example.sallerapp.databinding.BottomDialogCameraBinding;
import com.example.sallerapp.databinding.BottomDialogCategoryProductBinding;
import com.example.sallerapp.databinding.DialogAttributeProductBinding;
import com.example.sallerapp.databinding.FragmentAddProductBinding;
import com.example.sallerapp.funtions.MyFragment;
import com.example.sallerapp.funtions.RequestPermissions;
import com.example.sallerapp.funtions.Validations;
import com.example.sallerapp.model.AttributeProduct;
import com.example.sallerapp.model.CartShop;
import com.example.sallerapp.model.CategoryProduct;
import com.google.android.gms.ads.AdRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AddProductFragment extends Fragment implements AttributeProductAdapter.Click {

    private FragmentAddProductBinding productBinding;
    Bitmap imageBitmap;
    BottomSheetDialog dialog;
    ArrayList<AttributeProduct> attributeProducts ;
    AttributeProductAdapter attributeProductAdapter;


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
        productBinding = FragmentAddProductBinding.inflate(inflater, container, false);
        initView();
        return productBinding.getRoot();
    }

    private void initView() {
        attributeProducts = new ArrayList<>();
        AdRequest adRequest = new AdRequest.Builder().build();
        productBinding.adView.loadAd(adRequest);
        attributeProductAdapter = new AttributeProductAdapter(attributeProducts,this);
        productBinding.recListAttPro.setAdapter(attributeProductAdapter);
        productBinding.recListAttPro.setLayoutManager(new LinearLayoutManager(requireContext()));
        productBinding.addImgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RequestPermissions.requestReadImgGalleryCamera(requireContext())) {
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

        productBinding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog();
            }
        });

        productBinding.tvCategoryProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiaLogCategory();
            }
        });

        Date today = new Date();
        // Định dạng ngày
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        productBinding.date.setText(dateFormat.format(today));

        productBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), MainActivity.class));
                requireActivity().finish();
            }
        });
    }

    private void datePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year1, int monthOfYear, int dayOfMonth) {
                        // Lưu lại ngày được chọn
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year1, monthOfYear, dayOfMonth);

                        // Hiển thị ngày đã chọn trên TextView
                        productBinding.date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR)
                , Calendar.getInstance().get(Calendar.MONTH)
                , Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }

    private void bottomSheetAtt() {
        DialogAttributeProductBinding attributeProductBinding = DialogAttributeProductBinding.inflate(getLayoutInflater());
        Dialog dialogAtt = new Dialog(requireContext());
        dialogAtt.setContentView(attributeProductBinding.getRoot());
        dialogAtt.getWindow().setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT
                , WindowManager.LayoutParams.WRAP_CONTENT);
        dialogAtt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Validations.isEmpty(attributeProductBinding.name);
        Validations.isQuantity(attributeProductBinding.quantity);
        attributeProductBinding.saveDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0;
                if (Validations.isEmptyPress(attributeProductBinding.name)){
                    count ++;
                }
                if (!Validations.isEmptyPress(attributeProductBinding.name)){
                    if (!Validations.isQuantityPress(attributeProductBinding.quantity)){
                        count ++;
                    }
                }else{
                    count ++;
                }
                if (count != 0){
                    return;
                }

                String nameAttPro = attributeProductBinding.name.getText().toString();
                int quantityAttPro = Integer.parseInt(attributeProductBinding.quantity.getText().toString());
                AttributeProduct attributeProduct = new AttributeProduct(nameAttPro,quantityAttPro);
                attributeProducts.add(attributeProduct);
                attributeProductAdapter.notifyDataSetChanged();
                dialogAtt.dismiss();
            }
        });
        dialogAtt.show();
    }

    private void showDiaLogCategory() {
        BottomDialogCategoryProductBinding categoryProductBinding = BottomDialogCategoryProductBinding.inflate(getLayoutInflater());
        BottomSheetDialog cateProDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogThem);
        cateProDialog.setContentView(categoryProductBinding.getRoot());
        categoryProductBinding.btnAddProDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFragment.replaceFragment(requireActivity().getSupportFragmentManager()
                        , R.id.fragmentAddPro
                        , new AddCategoryProductFragment()
                        , true);
                cateProDialog.dismiss();

            }
        });

        CategoryProductDao.getCategoryProduct("Shop_1", new CategoryProductDao.GetData() {
            @Override
            public void getData(ArrayList<CategoryProduct> categoryProducts) {
                CateProductDialogAdapter cateProductDialogAdapter = new CateProductDialogAdapter(categoryProducts,
                        new CateProductDialogAdapter.Click() {
                            @Override
                            public void click(String nameCatePro) {
                                productBinding.tvCategoryProduct.setText(nameCatePro);
                                cateProDialog.dismiss();
                            }
                        });
                categoryProductBinding.reyCateProductDialog.setAdapter(cateProductDialogAdapter);
                categoryProductBinding.reyCateProductDialog.setLayoutManager(new LinearLayoutManager(requireContext()));
            }
        });
        cateProDialog.show();
    }

    private void showDialog() {
        BottomDialogCameraBinding cameraBinding = BottomDialogCameraBinding.inflate(getLayoutInflater());
        dialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogThem);
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
        if (requestCode == 100 && resultCode == RESULT_OK) {
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

    @Override
    public void delete(AttributeProduct attributeProduct) {
        attributeProducts.remove(attributeProduct);
        attributeProductAdapter.notifyDataSetChanged();
        Toast.makeText(requireContext(),"xóa thành công",Toast.LENGTH_SHORT).show();
    }
}