package com.example.sallerapp.controller.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.sallerapp.database.CategoryProductDao;
import com.example.sallerapp.database.ProductDao;
import com.example.sallerapp.databinding.BottomDialogCameraBinding;
import com.example.sallerapp.databinding.BottomDialogCategoryProductBinding;
import com.example.sallerapp.databinding.DialogAttributeProductBinding;
import com.example.sallerapp.databinding.FragmentAddProductBinding;
import com.example.sallerapp.desgin_pattern.build_pantter.ProductBuilder;
import com.example.sallerapp.funtions.FirebaseUtil;
import com.example.sallerapp.funtions.MyDialog;
import com.example.sallerapp.funtions.MyFragment;
import com.example.sallerapp.funtions.RequestPermissions;
import com.example.sallerapp.funtions.Validations;
import com.example.sallerapp.model.AttributeProduct;
import com.example.sallerapp.model.CategoryProduct;
import com.example.sallerapp.model.Product;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AddProductFragment extends Fragment implements AttributeProductAdapter.Click {

    private FragmentAddProductBinding productBinding;
    private static final String TAG = AddProductFragment.class.getSimpleName();
    Bitmap imageBitmap;
    BottomSheetDialog dialog;
    ArrayList<AttributeProduct> attributeProducts ;
    AttributeProductAdapter attributeProductAdapter;

    Date today = new Date();
    // Định dạng ngày
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    ProgressDialog progressDialog;


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

        productBinding.date.setText(dateFormat.format(today));

        productBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), MainActivity.class));
                requireActivity().finish();
            }
        });

        ProductDao.getProducts("Shop_1", new ProductDao.GetData() {
            @Override
            public void getData(ArrayList<Product> products) {
                productBinding.edtProductId.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        products.forEach(o -> {
                            String[] id = o.getProductId().split("_");
                            if (id[0].equals(charSequence.toString())){
                                productBinding.edtProductId.setError("ID sản phẩm không thể trùng");
                            }
                        });
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
        });
        productBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertProd();
            }
        });
        productBinding.imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertProd();
            }
        });
    }

    // hàm đưa sp lên database
    private void insertProd() {
        int count = 0;
        if (Validations.isEmptyPress(productBinding.edtProductId)){
            count ++;
        }

        if (Validations.isEmptyPress(productBinding.edtNameProduct)){
            count ++;
        }
        if(!Validations.isEmptyPress(productBinding.edtProductCost)){
            if (!Validations.isQuantityPress(productBinding.edtProductCost)){
                count ++;
            }
        }else {
            count ++;
        }

        if(!Validations.isEmptyPress(productBinding.edtRetailProduct)){
            if (!Validations.isQuantityPress(productBinding.edtRetailProduct)){
                count ++;
            }
        }else {
            count ++;
        }

        if(!Validations.isEmptyPress(productBinding.edtWholeSalePriceProduct)){
            if (!Validations.isQuantityPress(productBinding.edtWholeSalePriceProduct)){
                count ++;
            }
        }else {
            count ++;
        }

        if(!Validations.isEmptyPress(productBinding.edtQuantityProduct)){
            if (!Validations.isQuantityPress(productBinding.edtQuantityProduct)){
                count ++;
            }
        }else {
            count ++;
        }

        if (productBinding.tvCategoryProduct.getText().toString().equals("Chọn loại hàng hóa")){
            Toast.makeText(requireActivity(),"Loại sản phận rỗng",Toast.LENGTH_SHORT).show();
            count++;
        }

        if (count != 0){
            return;
        }

        if (imageBitmap != null){
            StorageReference sdb = FirebaseStorage.getInstance().getReference().child(productBinding.edtProductId.getText().toString());
            ByteArrayOutputStream ops = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,ops);
            byte[] imgData = ops.toByteArray();

            UploadTask uploadTask = sdb.putBytes(imgData);
            MyDialog.showProgressDialog("Đang tải lên...", requireContext());
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    MyDialog.dismissProgressDialog();
                    sdb.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imgUrl = uri.toString();
                            createPro(imgUrl);
                        }
                    });
                }
            });

        }else {
            createPro("");
            Toast.makeText(requireContext(),"Thêm sản phẩm thành công",Toast.LENGTH_SHORT).show();
        }
    }

    // tạo hàm tạo cho sp vs 4 trường hợp
    private void createPro(String imgPath) {
       if (attributeProducts.isEmpty()){
           String idPro = productBinding.edtProductId.getText().toString();
           String namePro = productBinding.edtNameProduct.getText().toString();
           int cost = Integer.parseInt(productBinding.edtProductCost.getText().toString());
           int retailPrice = Integer.parseInt(productBinding.edtRetailProduct.getText().toString());
           int wholeSalePrice = Integer.parseInt(productBinding.edtWholeSalePriceProduct.getText().toString());
           int quantity = Integer.parseInt(productBinding.edtQuantityProduct.getText().toString());
           String categoryPro = productBinding.tvCategoryProduct.getText().toString();
           String dateTime = productBinding.date.getText().toString();
           String note = productBinding.edtNote.getText().toString();
           Product product = new ProductBuilder()
                   .addImgPath(imgPath)
                   .addId(idPro)
                   .addProductName(namePro)
                   .addCost(cost)
                   .addRetailPrice(retailPrice)
                   .addWholeSalePrice(wholeSalePrice)
                   .addQuantity(quantity)
                   .addCategory(categoryPro)
                   .addDate(dateTime)
                   .addNote(note)
                   .build();
           ProductDao.insertProduct(product, "Shop_1");
           clearData();
       }else {
           for (int i = 0; i < attributeProducts.size(); i++){
               String idPro = productBinding.edtProductId.getText().toString() + "_BT_"+i;
               String namePro = productBinding.edtNameProduct.getText().toString()+ " " + attributeProducts.get(i).getAttribute();
               int cost = Integer.parseInt(productBinding.edtProductCost.getText().toString());
               int retailPrice = Integer.parseInt(productBinding.edtRetailProduct.getText().toString());
               int wholeSalePrice = Integer.parseInt(productBinding.edtWholeSalePriceProduct.getText().toString());
               int quantity = attributeProducts.get(i).getQuantity();
               String categoryPro = productBinding.tvCategoryProduct.getText().toString();
               String dateTime = productBinding.date.getText().toString();
               String note = productBinding.edtNote.getText().toString();
               if (categoryPro.equals("Chọn loại hàng hóa")){
                   Toast.makeText(requireActivity(),"Loại sản phận rỗng",Toast.LENGTH_SHORT).show();
                   return;
               }
               Product product = new ProductBuilder()
                       .addImgPath(imgPath)
                       .addId(idPro)
                       .addProductName(namePro)
                       .addCost(cost)
                       .addRetailPrice(retailPrice)
                       .addWholeSalePrice(wholeSalePrice)
                       .addQuantity(quantity)
                       .addCategory(categoryPro)
                       .addDate(dateTime)
                       .addNote(note)
                       .build();
               ProductDao.insertProduct(product, "Shop_1");
           }
           clearData();
       }
    }

    // thêm xong xóa bỏ data
    private void clearData() {
        productBinding.edtProductId.setText("");
        productBinding.edtProductId.clearFocus();
        productBinding.edtNameProduct.setText("");
        productBinding.edtProductCost.setText("");
        productBinding.edtRetailProduct.setText("");
        productBinding.edtWholeSalePriceProduct.setText("");
        productBinding.edtQuantityProduct.setText("");
        productBinding.tvCategoryProduct.setText("Chọn loại hang hóa");
        productBinding.addImgProduct.setVisibility(View.VISIBLE);
        productBinding.imgProduct.setVisibility(View.GONE);
        productBinding.date.setText(dateFormat.format(today));
        productBinding.edtNote.setText("");
        attributeProducts.clear();
        attributeProductAdapter.notifyDataSetChanged();
    }

    // dialog lấy tg tùy chọn
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

    // dialog thêm thuộc tính cho sản phẩm
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

    // chọn loại sản phẩm
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

    // dialog chọn ảnh từ cam hay thư viện
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

    // sử lý lấy ảnh ra
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

    // xóa thuộc tính sản phẩm
    @Override
    public void delete(AttributeProduct attributeProduct) {
        attributeProducts.remove(attributeProduct);
        attributeProductAdapter.notifyDataSetChanged();
        Toast.makeText(requireContext(),"xóa thành công",Toast.LENGTH_SHORT).show();
    }
}