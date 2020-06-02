package com.example.kouvemobile.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouvemobile.Frag.editDPengadaanFragment;
import com.example.kouvemobile.Model.DPengadaan;
import com.example.kouvemobile.Model.Pengadaan;
import com.example.kouvemobile.PengadaanActivity;
import com.example.kouvemobile.R;

import java.util.ArrayList;
import java.util.List;

public class DataDpAdapter extends RecyclerView.Adapter<DataDpAdapter.CardViewViewHolder> implements Filterable {

    private List<DPengadaan> listDPengadaan;
    private List<DPengadaan> mlistDPengadaan;
    private Context context;
    private Pengadaan pgd;

    public DataDpAdapter(List<DPengadaan> list, Context context) {
        this.listDPengadaan = list;
        this.context = context;
        mlistDPengadaan = new ArrayList<>(listDPengadaan);
    }


    @NonNull
    @Override
    public DataDpAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detil_pengadaan, parent, false);
        DataDpAdapter.CardViewViewHolder vHolder = new DataDpAdapter.CardViewViewHolder(view);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DataDpAdapter.CardViewViewHolder holder, final int position) {

        final DPengadaan dpgd = listDPengadaan.get(position);

        holder.tvNama.setText(dpgd.getNama_produk());
        holder.tvJml.setText(dpgd.getJml_produk().toString());
        holder.tvHarga.setText(dpgd.getHarga_produk().toString());
        holder.cardview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDPengadaanFragment dialogEdit = new editDPengadaanFragment();

                Bundle b = new Bundle();
                b.putInt("iddp", dpgd.getId_detil_pengadaan());
                b.putInt("jml", dpgd.getJml_produk());
                b.putInt("harga", dpgd.getHarga_produk());

                SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                Integer id = pref.getInt("idp",0);
                String kode = pref.getString("kode","");
                String status = pref.getString("status", "");
                Integer total = pref.getInt("total", 0);

                b.putInt("id", id);
                b.putString("kode", kode);
                b.putString("status", status);
                b.putInt("total", total);
                dialogEdit.setArguments(b);

                dialogEdit.show(((PengadaanActivity) context).getSupportFragmentManager(), "dialog");
            }
        });
    }


    @Override
    public int getItemCount() {
        return listDPengadaan.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DPengadaan> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mlistDPengadaan);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (DPengadaan item : mlistDPengadaan) {
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
            listDPengadaan.clear();
            listDPengadaan.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama,tvJml,tvHarga;
        CardView cardview_btn;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.nama_detil_pgd);
            tvJml = itemView.findViewById(R.id.jml_detil_pgd);
            tvHarga = itemView.findViewById(R.id.hrg_detil_pgd);
            cardview_btn = itemView.findViewById(R.id.cardview_dpgd_btn);
        }

    }


    public void filterList(List<DPengadaan> filteredList) {
        listDPengadaan = filteredList;
        notifyDataSetChanged();
    }


}
