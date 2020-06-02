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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouvemobile.Frag.editDTransaksilFragment;
import com.example.kouvemobile.Model.DTLayanan;
import com.example.kouvemobile.R;
import com.example.kouvemobile.TransaksiLActivity;

import java.util.ArrayList;
import java.util.List;

public class DataTlAdapter extends RecyclerView.Adapter<DataTlAdapter.CardViewViewHolder> implements Filterable {

    private List<DTLayanan> listDTLayanan;
    private List<DTLayanan> mlistDTLayanan;
    private Context context;

    public DataTlAdapter(List<DTLayanan> list, Context context) {
        this.listDTLayanan = list;
        this.context = context;
        mlistDTLayanan = new ArrayList<>(listDTLayanan);
    }


    @NonNull
    @Override
    public DataTlAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detil_tlayanan, parent, false);
        DataTlAdapter.CardViewViewHolder vHolder = new DataTlAdapter.CardViewViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DataTlAdapter.CardViewViewHolder holder, final int position) {

        final DTLayanan dtrp = listDTLayanan.get(position);


        holder.tvNama.setText(dtrp.getNama_playanan());
        holder.tvJml.setText(dtrp.getJml_playanan().toString());
        holder.tvHarga.setText(dtrp.getHarga_playanan().toString());
        holder.cardview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDTransaksilFragment dialogEdit = new editDTransaksilFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("idtp", dtrp.getId_playanan());
                bundle.putInt("jml", dtrp.getJml_playanan());
                bundle.putInt("harga", dtrp.getHarga_playanan());

                SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                Integer id = pref.getInt("idp",0);
                String kode = pref.getString("kode","");
                String status = pref.getString("status", "");
                Integer total = pref.getInt("total", 0);

                bundle.putInt("id", id);
                bundle.putString("kode", kode);
                bundle.putString("status", status);
                bundle.putInt("total", total);
                dialogEdit.setArguments(bundle);


                dialogEdit.show(((TransaksiLActivity) context).getSupportFragmentManager(), "dialog");
            }
        });
    }


    @Override
    public int getItemCount() {
        return listDTLayanan.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DTLayanan> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mlistDTLayanan);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (DTLayanan item : mlistDTLayanan) {
                    if (item.getNama_playanan().toLowerCase().contains(filterPattern)) {
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
            listDTLayanan.clear();
            listDTLayanan.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama,tvJml,tvHarga;
        CardView cardview_btn;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.nama_detil_tlyn);
            tvJml = itemView.findViewById(R.id.jml_detil_tlyn);
            tvHarga = itemView.findViewById(R.id.hrg_detil_tlyn);
            cardview_btn = itemView.findViewById(R.id.cardview_btn);
        }

    }


    public void filterList(List<DTLayanan> filteredList) {
        listDTLayanan = filteredList;
        notifyDataSetChanged();
    }
}
