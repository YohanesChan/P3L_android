package com.example.kouvemobile.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.kouvemobile.Frag.detilPgdFragment;
import com.example.kouvemobile.Model.Pengadaan;
import com.example.kouvemobile.PengadaanActivity;
import com.example.kouvemobile.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DataPengadaanAdapter extends RecyclerView.Adapter<DataPengadaanAdapter.CardViewViewHolder> implements Filterable {

    private List<Pengadaan> listPengadaan;
    private List<Pengadaan> mlistPengadaan;
    private Context context;

    public DataPengadaanAdapter(List<Pengadaan> list, Context context) {
        this.listPengadaan = list;
        this.context = context;
        mlistPengadaan = new ArrayList<>(listPengadaan);
    }


    @NonNull
    @Override
    public DataPengadaanAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pengadaan, parent, false);
        DataPengadaanAdapter.CardViewViewHolder vHolder = new DataPengadaanAdapter.CardViewViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DataPengadaanAdapter.CardViewViewHolder holder, final int position) {

        final Pengadaan pgd = listPengadaan.get(position);


        holder.tvKode.setText(pgd.getKode_pengadaan());
        holder.tvStatus.setText(pgd.getStatus_PO());
        holder.tvTotal.setText(pgd.getTotal_pengadaan().toString());
        holder.cardview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detilPgdFragment detilpgd = new detilPgdFragment();

                SharedPreferences sp = context.getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();

                ed.putInt("idp", listPengadaan.get(position).getId_pengadaan());
                ed.apply();

                Bundle bundle = new Bundle();
                bundle.putInt("id", pgd.getId_pengadaan());
                bundle.putString("kode", pgd.getKode_pengadaan());
                bundle.putString("status", pgd.getStatus_PO());
                bundle.putInt("total", pgd.getTotal_pengadaan());
                detilpgd.setArguments(bundle);

                SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("idp",pgd.getId_pengadaan());
                editor.putString("kode", pgd.getKode_pengadaan());
                editor.putString("status", pgd.getStatus_PO());
                editor.putInt("total", pgd.getTotal_pengadaan());
                editor.commit();

                if(pgd.getStatus_PO().equals("proses"))
                {
                    ((PengadaanActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            detilpgd).commit();
                }else{
                    Toast.makeText(context, "Pengadaan telah selesai", Toast.LENGTH_SHORT).show();
                }

             }
        });
    }


    @Override
    public int getItemCount() {
        return listPengadaan.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Pengadaan> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mlistPengadaan);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Pengadaan item : mlistPengadaan) {
                    if (item.getKode_pengadaan().toLowerCase().contains(filterPattern)) {
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
            listPengadaan.clear();
            listPengadaan.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView tvKode,tvStatus,tvTotal;
        CardView cardview_btn;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKode = itemView.findViewById(R.id.kode_pgd_produk);
            tvStatus = itemView.findViewById(R.id.status_pgd_produk);
            tvTotal = itemView.findViewById(R.id.total_pgd_produk);
            cardview_btn = itemView.findViewById(R.id.cardview_btn);
        }

    }


    public void filterList(List<Pengadaan> filteredList) {
        listPengadaan = filteredList;
        notifyDataSetChanged();
    }

}
