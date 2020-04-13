package com.example.kouvemobile.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouvemobile.Frag.editSupplierFragment;
import com.example.kouvemobile.Model.Supplier;
import com.example.kouvemobile.PengPgwActivity;
import com.example.kouvemobile.PengSupActivity;
import com.example.kouvemobile.R;

import java.util.ArrayList;
import java.util.List;

public class DataSupplierAdapter extends RecyclerView.Adapter<DataSupplierAdapter.CardViewViewHolder> implements Filterable {

    private List<Supplier> listSupplier;
    private List<Supplier> mlistSupplier;
    private Context context;

    public DataSupplierAdapter(List<Supplier> list, Context context) {
        this.listSupplier = list;
        this.context = context;
        mlistSupplier = new ArrayList<>(listSupplier);
    }


    @NonNull
    @Override
    public DataSupplierAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_supplier, parent, false);
        DataSupplierAdapter.CardViewViewHolder vHolder = new DataSupplierAdapter.CardViewViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DataSupplierAdapter.CardViewViewHolder holder, final int position) {

        final Supplier sup = listSupplier.get(position);

        holder.tvId.setText(sup.getNo_supplier());
        holder.tvNama.setText(sup.getNama_supplier());
        holder.tvTelp.setText(sup.getTelp_supplier());
        holder.tvAlamat.setText(sup.getAlamat_supplier());
        holder.cardview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSupplierFragment dialogEdit = new editSupplierFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("id", sup.getId_supplier());
                bundle.putString("no", sup.getNo_supplier());
                bundle.putString("nama", sup.getNama_supplier());
                bundle.putString("telp", sup.getTelp_supplier());
                bundle.putString("alamat", sup.getAlamat_supplier());

                dialogEdit.setArguments(bundle);

                dialogEdit.show(((PengSupActivity) context).getSupportFragmentManager(), "dialog");
            }
        });
    }


    @Override
    public int getItemCount() {
        return listSupplier.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Supplier> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mlistSupplier);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Supplier item : mlistSupplier) {
                    if (item.getNama_supplier().toLowerCase().contains(filterPattern)) {
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
            listSupplier.clear();
            listSupplier.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvId, tvTelp, tvAlamat;
        CardView cardview_btn;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.nama_supplier);
            tvId = itemView.findViewById(R.id.id_supplier);
            tvAlamat = itemView.findViewById(R.id.alamat_supplier);
            tvTelp = itemView.findViewById(R.id.tlp_supplier);
            cardview_btn = itemView.findViewById(R.id.cardview_btn);
        }

    }


    public void filterList(List<Supplier> filteredList) {
        listSupplier = filteredList;
        notifyDataSetChanged();
    }
}
