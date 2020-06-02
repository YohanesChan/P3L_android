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

import com.example.kouvemobile.Frag.editDTransaksipFragment;
import com.example.kouvemobile.Model.DTProduk;
import com.example.kouvemobile.Model.TransaksiP;
import com.example.kouvemobile.R;
import com.example.kouvemobile.TransaksiPActivity;

import java.util.ArrayList;
import java.util.List;

public class DataTpAdapter extends RecyclerView.Adapter<DataTpAdapter.CardViewViewHolder> implements Filterable {

    private List<DTProduk> listDTProduk;
    private List<DTProduk> mlistDTProduk;
    private Context context;

    public DataTpAdapter(List<DTProduk> list, Context context) {
        this.listDTProduk = list;
        this.context = context;
        mlistDTProduk = new ArrayList<>(listDTProduk);
    }


    @NonNull
    @Override
    public DataTpAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detil_tproduk, parent, false);
        DataTpAdapter.CardViewViewHolder vHolder = new DataTpAdapter.CardViewViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DataTpAdapter.CardViewViewHolder holder, final int position) {

        final DTProduk dtrp = listDTProduk.get(position);


        holder.tvNama.setText(dtrp.getNama_pproduk());
        holder.tvJml.setText(dtrp.getJml_pproduk().toString());
        holder.tvHarga.setText(dtrp.getHarga_pproduk().toString());
        holder.cardview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDTransaksipFragment dialogEdit = new editDTransaksipFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("idtp", dtrp.getId_pproduk());
                bundle.putInt("jml", dtrp.getJml_pproduk());
                bundle.putInt("harga", dtrp.getHarga_pproduk());

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


                dialogEdit.show(((TransaksiPActivity) context).getSupportFragmentManager(), "dialog");
            }
        });
    }


    @Override
    public int getItemCount() {
        return listDTProduk.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DTProduk> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mlistDTProduk);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (DTProduk item : mlistDTProduk) {
                    if (item.getNama_pproduk().toLowerCase().contains(filterPattern)) {
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
            listDTProduk.clear();
            listDTProduk.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama,tvJml,tvHarga;
        CardView cardview_btn;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.nama_detil_tpdk);
            tvJml = itemView.findViewById(R.id.jml_detil_tpdk);
            tvHarga = itemView.findViewById(R.id.hrg_detil_tpdk);
            cardview_btn = itemView.findViewById(R.id.cardview_btn);
        }

    }


    public void filterList(List<DTProduk> filteredList) {
        listDTProduk = filteredList;
        notifyDataSetChanged();
    }
}
