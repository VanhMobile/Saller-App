package com.example.sallerapp.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sallerapp.MainActivity;
import com.example.sallerapp.R;
import com.example.sallerapp.adapter.DetailBillAdapter;
import com.example.sallerapp.databinding.FragmentDetailBillBinding;
import com.example.sallerapp.desgin_pattern.single_pantter.BillSingle;
import com.example.sallerapp.funtions.MoneyFormat;
import com.example.sallerapp.model.Bill;

public class DetailBillFragment extends Fragment {

    private FragmentDetailBillBinding detailBillBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        detailBillBinding = FragmentDetailBillBinding.inflate(inflater,container,false);
        initView();
        return detailBillBinding.getRoot();
    }

    private void initView() {
        Bill bill = BillSingle.getInstance().getBill();

        if (bill == null){
            return;
        }
        int cost = 0;
        for (int i = 0; i < bill.getListProduct().size(); i++){
            cost += (bill.getListProduct().get(i).getProduct().getCost() * bill.getListProduct().get(i).getQuantity());
        }
        detailBillBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), MainActivity.class));
                requireActivity().finish();
            }
        });
        detailBillBinding.BillId.setText("Mã hóa đơn: " + bill.getBillId());
        detailBillBinding.date.setText(bill.getDate());
        detailBillBinding.quantity.setText(bill.getQuantity()+"");
        detailBillBinding.cost.setText(MoneyFormat.moneyFormat(cost));
        detailBillBinding.total.setText(MoneyFormat.moneyFormat(bill.getSumPrice()));
        detailBillBinding.numberPhoneCustomer.setText(bill.getCustomer().getNumberPhone());
        detailBillBinding.addressCustomer.setText(bill.getCustomer().getAddress());
        detailBillBinding.CustomerType.setText(bill.getCustomer().getCustomerType());
        detailBillBinding.payMethod.setText(bill.getPayMethod());
        detailBillBinding.sumPrice.setText(MoneyFormat.moneyFormat(bill.getSumPrice()));
        detailBillBinding.typeBill.setText(bill.getBillType());
        DetailBillAdapter billAdapter  = new DetailBillAdapter(bill.getListProduct(), bill.getBillType());
        detailBillBinding.recycBill.setAdapter(billAdapter);
        detailBillBinding.IdAccount.setText(bill.getIdAccount());
        detailBillBinding.recycBill.setLayoutManager(new LinearLayoutManager(requireActivity()));
    }
}