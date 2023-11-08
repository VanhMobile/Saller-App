package com.example.sallerapp.adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sallerapp.databinding.ItemListBillBinding;
import com.example.sallerapp.model.Bill;
import java.util.List;

public class ListBillAdapter extends RecyclerView.Adapter<ListBillAdapter.ViewHolder> {


    private List<Bill> listbill;
    private ListBillAdapter.ILISTBILL linstener;
    public interface ILISTBILL{
        void click (Bill bill);
        void update (Bill bill);
    }
    @NonNull
    @Override
    public ListBillAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListBillBinding binding = ItemListBillBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bill bill = listbill.get(position);

    }

    @Override
    public int getItemCount() {
        return listbill.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        ItemListBillBinding binding ;
        public ViewHolder(@NonNull ItemListBillBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
