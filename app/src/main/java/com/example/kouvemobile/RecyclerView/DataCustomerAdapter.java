package com.example.kouvemobile.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.kouvemobile.Frag.editCustomerFragment;
import com.example.kouvemobile.Model.Customer;
import com.example.kouvemobile.PengCustActivity;
import com.example.kouvemobile.R;

import java.util.ArrayList;
import java.util.List;

public class DataCustomerAdapter extends RecyclerView.Adapter<DataCustomerAdapter.CardViewViewHolder> implements Filterable {

    private List<Customer> listCustomer;
    private List<Customer> mlistCustomer;
    private Context context;

    public DataCustomerAdapter(List<Customer> list, Context context) {
        this.listCustomer = list;
        this.context = context;
        mlistCustomer = new ArrayList<>(listCustomer);
    }


    @NonNull
    @Override
    public DataCustomerAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_customer, parent, false);
        DataCustomerAdapter.CardViewViewHolder vHolder = new DataCustomerAdapter.CardViewViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DataCustomerAdapter.CardViewViewHolder holder, final int position) {

        final Customer cst = listCustomer.get(position);


        holder.tvNama.setText(cst.getNama_customer());
        holder.tvTelp.setText(cst.getTelp_customer());
        holder.tvId.setText(cst.getNo_customer());
        holder.cardview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCustomerFragment dialogEdit = new editCustomerFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("id", cst.getId_customer());
                bundle.putString("no", cst.getNo_customer());
                bundle.putString("nama", cst.getNama_customer());
                bundle.putString("telp", cst.getTelp_customer());
                bundle.putString("bday", cst.getBirthday_customer());
                bundle.putString("alamat", cst.getAlamat_customer());

                dialogEdit.setArguments(bundle);

                dialogEdit.show(((PengCustActivity) context).getSupportFragmentManager(), "dialog");
            }
        });
    }


    @Override
    public int getItemCount() {
        return listCustomer.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Customer> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mlistCustomer);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Customer item : mlistCustomer) {
                    if (item.getNama_customer().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listCustomer.clear();
            listCustomer.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama,tvId,tvTelp;
        CardView cardview_btn;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.nama_customer);
            tvId = itemView.findViewById(R.id.id_customer);
            tvTelp = itemView.findViewById(R.id.tlp_customer);
            cardview_btn = itemView.findViewById(R.id.cardview_btn);
        }

    }


    public void filterList(List<Customer> filteredList) {
        listCustomer = filteredList;
        notifyDataSetChanged();
    }
}
