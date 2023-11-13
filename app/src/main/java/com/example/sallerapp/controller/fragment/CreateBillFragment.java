package com.example.sallerapp.controller.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.sallerapp.MainActivity;
import com.example.sallerapp.R;
import com.example.sallerapp.adapter.CartShopAdapter;
import com.example.sallerapp.adapter.ListCustomerAdapter;
import com.example.sallerapp.adapter.ListProductAdapter;
import com.example.sallerapp.controller.view.ProductActivity;
import com.example.sallerapp.database.BillDao;
import com.example.sallerapp.database.CustomerDao;
import com.example.sallerapp.database.ProductDao;
import com.example.sallerapp.databinding.BottomDialogPaymentMotherBinding;
import com.example.sallerapp.databinding.BottomDialogPriceListBinding;
import com.example.sallerapp.databinding.DialogAddCustomerBinding;
import com.example.sallerapp.databinding.DialogAddProductBinding;
import com.example.sallerapp.databinding.FragmentCreateBillBinding;
import com.example.sallerapp.desgin_pattern.build_pantter.BillBuilder;
import com.example.sallerapp.desgin_pattern.single_pantter.CartShopSingle;
import com.example.sallerapp.funtions.IdGenerator;
import com.example.sallerapp.funtions.MoneyFormat;
import com.example.sallerapp.funtions.MyFragment;
import com.example.sallerapp.model.Bill;
import com.example.sallerapp.model.CartShop;
import com.example.sallerapp.model.Customer;
import com.example.sallerapp.model.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CreateBillFragment extends Fragment {

    private FragmentCreateBillBinding createBillBinding;
    ListProductAdapter productAdapter;
    ListCustomerAdapter customerAdapter;
    CartShopAdapter cartShopAdapter;
    Customer cus;
    Date today = new Date();
    // Định dạng ngày
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public CreateBillFragment() {
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
        createBillBinding = FragmentCreateBillBinding.inflate(getLayoutInflater());
        initView();
        return createBillBinding.getRoot();
    }

    private void initView() {
        cartShopAdapter = new CartShopAdapter(CartShopSingle.getInstance().getCartShops(), "Giá bán lẻ", new CartShopAdapter.Click() {
            @Override
            public void up(CartShop cartShop, String typeBill) {
                ArrayList<Product> cartData = CartShopSingle.getInstance().getProducts();
                if (cartShop.getQuantity() >= cartShop.getProduct().getQuantity()){
                    return;
                }
                cartData.add(cartShop.getProduct());
                CartShopSingle.getInstance().setProducts(cartData);
                cartShopAdapter.setData(CartShopSingle.getInstance().getCartShops());
                UpdateQuantityAndPrice(typeBill);
            }

            @Override
            public void down(CartShop cartShop, String typeBill) {
                ArrayList<Product> cartData = CartShopSingle.getInstance().getProducts();
                cartData.remove(cartShop.getProduct());
                CartShopSingle.getInstance().setProducts(cartData);
                cartShopAdapter.setData(CartShopSingle.getInstance().getCartShops());
                UpdateQuantityAndPrice(typeBill);
            }
        });
        UpdateQuantityAndPrice("Giá bán lẻ");
        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        createBillBinding.recyclerViewProductBill.addItemDecoration(itemDecoration);
        createBillBinding.recyclerViewProductBill.setAdapter(cartShopAdapter);
        createBillBinding.recyclerViewProductBill.setLayoutManager(new LinearLayoutManager(requireContext()));

        createBillBinding.tvAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDiaLogAddPro();
            }
        });

        createBillBinding.tablePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiaLogTablePrice();
            }
        });

        createBillBinding.payMethods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiaLogPayMethod();
            }
        });

        createBillBinding.addCusTomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAddCustomer();
            }
        });

        createBillBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertBill();
            }
        });

        createBillBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), MainActivity.class));
                requireActivity().finish();
            }
        });

        createBillBinding.imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertBill();
            }
        });
    }

    private void insertBill() {
        int count = 0;
        if(cus == null){
            count ++;
            Toast.makeText(requireActivity(),"Chưa chọn khách hàng",Toast.LENGTH_SHORT).show();
        }

        if (CartShopSingle.getInstance().getCartShops().size() == 0){
            count++;
            Toast.makeText(requireActivity(),"bạn chưa chọn sản phẩm",Toast.LENGTH_SHORT).show();
        }

        if (count != 0){
            return;
        }
        BillDao.GetBills("Shop_1", new BillDao.GetData() {
            @Override
            public void getData(ArrayList<Bill> bills) {
                String id = IdGenerator.generateNextShopId(bills.size(),"HD_");
                Bill bill = new BillBuilder()
                        .addId(id)
                        .addCustomer(cus)
                        .addBillType(createBillBinding.tablePrice.getText().toString())
                        .addPayMethod(createBillBinding.payMethods.getText().toString())
                        .addProducts(CartShopSingle.getInstance().getCartShops())
                        .addQuantity(CartShopSingle.getInstance().SumQuantity())
                        .addTotalPrice(CartShopSingle.getInstance().SumPrice(createBillBinding.tablePrice.getText().toString()))
                        .addDate(dateFormat.format(today))
                        .addNote(createBillBinding.edtNote.getText().toString())
                        .addIdAccount("Shop_1")
                        .build();
                CartShopSingle.getInstance().getCartShops().forEach(o -> {
                    int quantity = o.getProduct().getQuantity() - o.getQuantity();
                    Product product = o.getProduct();
                    product.setQuantity(quantity);
                    ProductDao.insertProduct(product,"Shop_1");
                });
                BillDao.insertBill(bill,"Shop_1");
                if (isAdded()){
                    Toast.makeText(requireContext(),"Tạo hóa đơn thành công",Toast.LENGTH_SHORT);
                }
                clearData();
            }
        });
    }

    private void clearData() {
        ArrayList<Product> cartData = new ArrayList<>();
        CartShopSingle.getInstance().setProducts(cartData);
        cartShopAdapter.setData(CartShopSingle.getInstance().getCartShops());
        UpdateQuantityAndPrice("Giá bán lẻ");
        createBillBinding.numberPhoneCustomer.setText("số điện thoại");
        createBillBinding.nameCustomer.setText("Tên khách hàng");
        createBillBinding.tablePrice.setText("Giá bán lẻ");
        createBillBinding.payMethods.setText("Tiền mặt");
        createBillBinding.edtNote.setText("");
    }

    private void showDialogAddCustomer() {
        DialogAddCustomerBinding addCustomerBinding = DialogAddCustomerBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(addCustomerBinding.getRoot());
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addCustomerBinding.saveDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        CustomerDao.getCustomers("Shop_1", new CustomerDao.GetData() {
            @Override
            public void getData(ArrayList<Customer> customers) {
                customerAdapter = new ListCustomerAdapter(customers, new ListCustomerAdapter.Click() {
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
                        createBillBinding.nameCustomer.setText(customer.getCustomerName());
                        createBillBinding.numberPhoneCustomer.setText(customer.getNumberPhone());
                        cus = customer;
                        dialog.dismiss();
                    }
                });
                addCustomerBinding.reyCustomerDialog.setAdapter(customerAdapter);
                DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
                addCustomerBinding.reyCustomerDialog.addItemDecoration(itemDecoration);
                if (isAdded()) {
                    addCustomerBinding.reyCustomerDialog.setLayoutManager(new LinearLayoutManager(requireActivity()));
                }
                customerAdapter.setDATA(customers);

                addCustomerBinding.searchCustomerDialog.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        customerAdapter.filterListCustomer(charSequence.toString().toLowerCase());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
        });

        addCustomerBinding.tvAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFragment.replaceFragment(requireActivity().getSupportFragmentManager()
                        , R.id.fragmentBill
                        , new Fragment_add_customer()
                        , true);
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void showDiaLogPayMethod() {
        BottomDialogPaymentMotherBinding paymentMotherBinding = BottomDialogPaymentMotherBinding.inflate(getLayoutInflater());
        BottomSheetDialog payMethodDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogThem);
        payMethodDialog.setContentView(paymentMotherBinding.getRoot());
        paymentMotherBinding.btnPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createBillBinding.payMethods.setText("Tiền mặt");
                payMethodDialog.dismiss();
            }
        });

        paymentMotherBinding.btnBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createBillBinding.payMethods.setText("Chuyển khoản");
                payMethodDialog.dismiss();
            }
        });

        payMethodDialog.show();
    }

    private void showDiaLogTablePrice() {
        BottomDialogPriceListBinding priceListBinding = BottomDialogPriceListBinding.inflate(getLayoutInflater());
        BottomSheetDialog priceListDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogThem);
        priceListDialog.setContentView(priceListBinding.getRoot());
        priceListBinding.btnRetailPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createBillBinding.tablePrice.setText("Giá bán lẻ");
                cartShopAdapter.setTypeBill("Giá bán lẻ");
                int SumPrice = CartShopSingle.getInstance().SumPrice("Giá bán lẻ");
                createBillBinding.sumPrice.setText(MoneyFormat.moneyFormat(SumPrice));
                priceListDialog.dismiss();
            }
        });

        priceListBinding.btnDealerPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createBillBinding.tablePrice.setText("Giá bán sỉ");
                cartShopAdapter.setTypeBill("Giá bán sỉ");
                int SumPrice = CartShopSingle.getInstance().SumPrice("Giá bán sỉ");
                createBillBinding.sumPrice.setText(MoneyFormat.moneyFormat(SumPrice));
                priceListDialog.dismiss();
            }
        });
        priceListDialog.show();
    }

    private void UpdateQuantityAndPrice(String typeBill) {
        int sumQuantity = CartShopSingle.getInstance().SumQuantity();
        createBillBinding.quantityProduct.setText(sumQuantity + "");
        int SumPrice = CartShopSingle.getInstance().SumPrice(typeBill);
        createBillBinding.sumPrice.setText(MoneyFormat.moneyFormat(SumPrice));
    }

    private void ShowDiaLogAddPro() {
        DialogAddProductBinding addProductBinding = DialogAddProductBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(addProductBinding.getRoot());
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT
                , WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ProductDao.getProducts("Shop_1", new ProductDao.GetData() {
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
                        cartShopAdapter.setData(CartShopSingle.getInstance().getCartShops());
                        UpdateQuantityAndPrice(createBillBinding.tablePrice.getText().toString());

                    }

                    @Override
                    public void clickItem(Product product) {


                    }
                });

                addProductBinding.reyAddProDialog.setAdapter(productAdapter);
                DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
                addProductBinding.reyAddProDialog.addItemDecoration(itemDecoration);
                if (isAdded()) {
                    addProductBinding.reyAddProDialog.setLayoutManager(new LinearLayoutManager(requireActivity()));
                }

                addProductBinding.searchProDialog.addTextChangedListener(new TextWatcher() {
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
            }
        });

        addProductBinding.tvAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), ProductActivity.class);
                intent.putExtra("product", "addProduct");
                startActivity(intent);
            }
        });


        addProductBinding.saveDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}