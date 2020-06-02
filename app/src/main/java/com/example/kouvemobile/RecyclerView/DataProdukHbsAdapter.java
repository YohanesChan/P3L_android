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

import com.example.kouvemobile.Frag.editProdukFragment;
import com.example.kouvemobile.Model.Produk;
import com.example.kouvemobile.PengPdkActivity;
import com.example.kouvemobile.R;

import java.util.ArrayList;
import java.util.List;

public class DataProdukHbsAdapter extends RecyclerView.Adapter<DataProdukHbsAdapter.CardViewViewHolder> implements Filterable {

    private List<Produk> listProduk;
    private List<Produk> mlistProduk;
    private Context context;

    public DataProdukHbsAdapter(List<Produk> list, Context context) {
        this.listProduk = list;
        this.context = context;
        mlistProduk = new ArrayList<>(listProduk);
    }


    @NonNull
    @Override
    public DataProdukHbsAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk_habis, parent, false);
        DataProdukHbsAdapter.CardViewViewHolder vHolder = new DataProdukHbsAdapter.CardViewViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DataProdukHbsAdapter.CardViewViewHolder holder, final int position) {

        final Produk pdk = listProduk.get(position);


        holder.tvNama.setText(pdk.getNama_produk());
        holder.tvId.setText(pdk.getNo_produk());
        holder.tvStok.setText(pdk.getStok_produk().toString());
        holder.cardview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProdukFragment dialogEdit = new editProdukFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("id", pdk.getId_produk());
                bundle.putString("no", pdk.getNo_produk());
                bundle.putString("nama", pdk.getNama_produk());
                bundle.putInt("stok", pdk.getStok_produk());

                dialogEdit.setArguments(bundle);

                dialogEdit.show(((PengPdkActivity) context).getSupportFragmentManager(), "dialog");
            }
        });
    }


    @Override
    public int getItemCount() {
        return listProduk.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Produk> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mlistProduk);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Produk item : mlistProduk) {
                    if (item.getNama_produk().toLowerCase().contains(filterPattern)) {
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
            listProduk.clear();
            listProduk.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvStok, tvId;
        CardView cardview_btn;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.nama_produk_hbs);
            tvStok = itemView.findViewById(R.id.stok_produk_hbs);
            tvId = itemView.findViewById(R.id.id_produk_hbs);
            cardview_btn = itemView.findViewById(R.id.cardview_btn);
        }

    }


    public void filterList(List<Produk> filteredList) {
        listProduk = filteredList;
        notifyDataSetChanged();
    }
}
