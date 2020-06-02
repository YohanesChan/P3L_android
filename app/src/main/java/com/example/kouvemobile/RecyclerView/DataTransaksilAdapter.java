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

import com.example.kouvemobile.Frag.detilTLynFragment;
import com.example.kouvemobile.Model.TransaksiL;
import com.example.kouvemobile.R;
import com.example.kouvemobile.TransaksiLActivity;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DataTransaksilAdapter extends RecyclerView.Adapter<DataTransaksilAdapter.CardViewViewHolder> implements Filterable {

    private List<TransaksiL> listTransaksiL;
    private List<TransaksiL> mlistTransaksiL;
    private Context context;

    public DataTransaksilAdapter(List<TransaksiL> list, Context context) {
        this.listTransaksiL = list;
        this.context = context;
        mlistTransaksiL = new ArrayList<>(listTransaksiL);
    }


    @NonNull
    @Override
    public DataTransaksilAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_transaksi_layanan, parent, false);
        DataTransaksilAdapter.CardViewViewHolder vHolder = new DataTransaksilAdapter.CardViewViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DataTransaksilAdapter.CardViewViewHolder holder, final int position) {

        final TransaksiL tr = listTransaksiL.get(position);
        holder.tvKode.setText(tr.getKode_tlayanan());
        holder.tvStatus.setText(tr.getStatus_tlayanan());
        holder.tvTotal.setText(tr.getTotal_tlayanan().toString());
        holder.cardview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detilTLynFragment detiltlyn = new detilTLynFragment();

                SharedPreferences sp = context.getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();

                ed.putInt("idp", listTransaksiL.get(position).getId_tlayanan());
                ed.apply();

                Bundle bundle = new Bundle();
                bundle.putInt("id", tr.getId_tlayanan());
                bundle.putString("kode", tr.getKode_tlayanan());
                bundle.putString("status", tr.getStatus_tlayanan());
                bundle.putInt("total", tr.getTotal_tlayanan());
                detiltlyn.setArguments(bundle);

                SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("id", tr.getId_tlayanan());
                editor.putString("kode", tr.getKode_tlayanan());
                editor.putString("status", tr.getStatus_tlayanan());
                editor.putInt("total", tr.getTotal_tlayanan());
                editor.commit();

                if(tr.getStatus_tlayanan().equals("proses"))
                {
                    ((TransaksiLActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            detiltlyn).commit();
                }else{
                    Toast.makeText(context, "Transaksi Produk telah selesai", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return listTransaksiL.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<TransaksiL> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mlistTransaksiL);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (TransaksiL item : mlistTransaksiL) {
                    if (item.getKode_tlayanan().toLowerCase().contains(filterPattern)) {
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
            listTransaksiL.clear();
            listTransaksiL.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView tvKode,tvStatus,tvTotal;
        CardView cardview_btn;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKode = itemView.findViewById(R.id.kode_tr_layanan);
            tvStatus = itemView.findViewById(R.id.status_tr_layanan);
            tvTotal = itemView.findViewById(R.id.total_tr_layanan);
            cardview_btn = itemView.findViewById(R.id.cardview_btn);
        }

    }


    public void filterList(List<TransaksiL> filteredList) {
        listTransaksiL = filteredList;
        notifyDataSetChanged();
    }
}
