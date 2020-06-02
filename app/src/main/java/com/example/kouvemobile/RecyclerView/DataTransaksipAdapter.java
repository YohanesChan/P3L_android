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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouvemobile.Frag.detilTPdkFragment;
import com.example.kouvemobile.Model.TransaksiP;
import com.example.kouvemobile.TransaksiPActivity;
import com.example.kouvemobile.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DataTransaksipAdapter extends RecyclerView.Adapter<DataTransaksipAdapter.CardViewViewHolder> implements Filterable {

    private List<TransaksiP> listTransaksiP;
    private List<TransaksiP> mlistTransaksiP;
    private Context context;

    public DataTransaksipAdapter(List<TransaksiP> list, Context context) {
        this.listTransaksiP = list;
        this.context = context;
        mlistTransaksiP = new ArrayList<>(listTransaksiP);
    }


    @NonNull
    @Override
    public DataTransaksipAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_transaksi_produk, parent, false);
        DataTransaksipAdapter.CardViewViewHolder vHolder = new DataTransaksipAdapter.CardViewViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DataTransaksipAdapter.CardViewViewHolder holder, final int position) {

        final TransaksiP tr = listTransaksiP.get(position);
        holder.tvKode.setText(tr.getKode_tproduk());
        holder.tvStatus.setText(tr.getStatus_tproduk());
        holder.tvTotal.setText(tr.getTotal_tproduk().toString());
        holder.cardview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detilTPdkFragment detiltpdk = new detilTPdkFragment();

                SharedPreferences sp = context.getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();

                ed.putInt("idp", listTransaksiP.get(position).getId_tproduk());
                ed.apply();

                Bundle bundle = new Bundle();
                bundle.putInt("id", tr.getId_tproduk());
                bundle.putString("kode", tr.getKode_tproduk());
                bundle.putString("status", tr.getStatus_tproduk());
                bundle.putInt("total", tr.getTotal_tproduk());
                detiltpdk.setArguments(bundle);

                SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("id", tr.getId_tproduk());
                editor.putString("kode", tr.getKode_tproduk());
                editor.putString("status", tr.getStatus_tproduk());
                editor.putInt("total", tr.getTotal_tproduk());
                editor.commit();

                if(tr.getStatus_tproduk().equals("proses"))
                {
                    ((TransaksiPActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            detiltpdk).commit();
                }else{
                    Toast.makeText(context, "Transaksi Produk telah selesai", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return listTransaksiP.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<TransaksiP> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mlistTransaksiP);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (TransaksiP item : mlistTransaksiP) {
                    if (item.getKode_tproduk().toLowerCase().contains(filterPattern)) {
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
            listTransaksiP.clear();
            listTransaksiP.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView tvKode,tvStatus,tvTotal;
        CardView cardview_btn;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKode = itemView.findViewById(R.id.kode_tr_produk);
            tvStatus = itemView.findViewById(R.id.status_tr_produk);
            tvTotal = itemView.findViewById(R.id.total_tr_produk);
            cardview_btn = itemView.findViewById(R.id.cardview_btn);
        }

    }


    public void filterList(List<TransaksiP> filteredList) {
        listTransaksiP = filteredList;
        notifyDataSetChanged();
    }
}
