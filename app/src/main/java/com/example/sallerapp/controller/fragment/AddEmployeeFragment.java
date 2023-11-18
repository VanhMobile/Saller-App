package com.example.sallerapp.controller.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sallerapp.MainActivity;
import com.example.sallerapp.R;
import com.example.sallerapp.database.EmployeeDao;
import com.example.sallerapp.databinding.BottomDialogCameraBinding;
import com.example.sallerapp.databinding.FragmentAddEmployeeBinding;
import com.example.sallerapp.desgin_pattern.build_pantter.EmployeeBuilder;
import com.example.sallerapp.desgin_pattern.single_pantter.SingleAccount;
import com.example.sallerapp.funtions.IdGenerator;
import com.example.sallerapp.funtions.MyDialog;
import com.example.sallerapp.funtions.RequestPermissions;
import com.example.sallerapp.funtions.Validations;
import com.example.sallerapp.model.Employee;
import com.example.sallerapp.model.ShopAccount;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class AddEmployeeFragment extends Fragment {

    private FragmentAddEmployeeBinding employeeBinding;
    Bitmap bitmap;
    BottomSheetDialog dialog;
    String id;
    ShopAccount shopAccount = SingleAccount.getInstance().getShopAccount();


    public AddEmployeeFragment() {
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
        employeeBinding = FragmentAddEmployeeBinding.inflate(inflater, container, false);
        initView();
        return employeeBinding.getRoot();
    }

    private void initView() {
        employeeBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), MainActivity.class));
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        employeeBinding.adView.loadAd(adRequest);
        employeeBinding.addImgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RequestPermissions.requestReadImgGalleryCamera(requireContext())) {
                    showDialog();
                }
            }
        });
        employeeBinding.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        employeeBinding.btnSaveEm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertEmployee();
                Log.e("click","nhấn btn");
            }

        });
        employeeBinding.iconCartShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertEmployee();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            employeeBinding.imgProduct.setImageBitmap(bitmap);
            employeeBinding.addImgProduct.setVisibility(View.GONE);
            employeeBinding.imgProduct.setVisibility(View.VISIBLE);
            dialog.dismiss();
        } else if (requestCode == 1000 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                employeeBinding.imgProduct.setImageBitmap(bitmap);
                employeeBinding.addImgProduct.setVisibility(View.GONE);
                employeeBinding.imgProduct.setVisibility(View.VISIBLE);
                dialog.dismiss();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    private void clearData() {
        employeeBinding.edtNameEmployee.setText("");
        employeeBinding.edtEmail.setText("");
        employeeBinding.edtPass.setText("");
        employeeBinding.edtsdt.setText("");
        employeeBinding.addImgProduct.setVisibility(View.VISIBLE);
        employeeBinding.imgProduct.setVisibility(View.GONE);
        employeeBinding.edtNote.setText("");
    }

    private void insertEmployee() {
        EmployeeDao.getEmployees(shopAccount.getShopId(), new EmployeeDao.GetData() {
            @Override
            public void getData(ArrayList<Employee> employees) {
                id = IdGenerator.generateNextShopId(employees.size(), "NV_");
                int count = 0;
                if (Validations.isEmptyPress(employeeBinding.edtNameEmployee)) {
                    count++;
                }
                if (!Validations.isEmptyPress(employeeBinding.edtEmail)) {
                    if (!Validations.isEmailPress(employeeBinding.edtEmail)) {
                        count++;
                    }
                } else {
                    count++;
                }

                if (!Validations.isEmptyPress(employeeBinding.edtsdt)) {
                    if (!Validations.isPhoneNumberPress(employeeBinding.edtsdt)) {
                        count++;
                    }
                } else {
                    count++;
                }

                if (!Validations.isEmptyPress(employeeBinding.edtPass)) {
                    if (!Validations.isPasswordPress(employeeBinding.edtPass)) {
                        count++;
                    }
                } else {
                    count++;
                }

                for (int i = 0; i < employees.size(); i++){
                    if (employees.get(i).getNumberPhone().equals(employeeBinding.edtsdt.getText().toString().trim())){
                        count++;
                        Toast.makeText(requireActivity(),"Số điện thoại đã có",Toast.LENGTH_SHORT).show();
                    }
                }

                for (int i = 0; i < employees.size(); i++){
                    if (employees.get(i).getEmail().equals(employeeBinding.edtEmail.getText().toString().trim())){
                        count++;
                        Toast.makeText(requireActivity(),"Email đã có",Toast.LENGTH_SHORT).show();
                    }
                }

                if (count != 0) {
                    return;
                }
                if (bitmap != null) {
                    StorageReference sdb = FirebaseStorage.getInstance().getReference().child(id);
                    ByteArrayOutputStream ops = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ops);
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
                                    createEm(imgUrl);
                                }
                            });
                        }
                    });

                } else {
                    createEm("");
                    Toast.makeText(requireContext(), "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createEm(String imPath) {
        String name = employeeBinding.edtNameEmployee.getText().toString();
        String email = employeeBinding.edtEmail.getText().toString();
        String sdt = employeeBinding.edtsdt.getText().toString();
        String pass = employeeBinding.edtPass.getText().toString();
        String note = employeeBinding.edtNote.getText().toString();
        Employee employee = new EmployeeBuilder()
                .addId(id+"_"+shopAccount.getShopId())
                .addName(name)
                .addNumberPhone(sdt)
                .addPassword(pass)
                .addEmail(email)
                .addImgPath(imPath)
                .addIdShop(shopAccount.getShopId())
                .addNote(note)
                .build();
        EmployeeDao.insertEmployee(employee, shopAccount.getShopId());
        clearData();
    }
}