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

import com.example.kouvemobile.Frag.editLayananFragment;
import com.example.kouvemobile.Model.Layanan;
import com.example.kouvemobile.PengLynActivity;
import com.example.kouvemobile.R;

import java.util.ArrayList;
import java.util.List;

public class DataLayananAdapter extends RecyclerView.Adapter<DataLayananAdapter.CardViewViewHolder> implements Filterable {

    private List<Layanan> listLayanan;
    private List<Layanan> mlistLayanan;
    private Context context;

    public DataLayananAdapter(List<Layanan> list, Context context) {
        this.listLayanan = list;
        this.context = context;
        mlistLayanan = new ArrayList<>(listLayanan);
    }


    @NonNull
    @Override
    public DataLayananAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layanan, parent, false);
        DataLayananAdapter.CardViewViewHolder vHolder = new DataLayananAdapter.CardViewViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DataLayananAdapter.CardViewViewHolder holder, final int position) {

        final Layanan lyn = listLayanan.get(position);


        holder.tvNama.setText(lyn.getNama_layanan());
        holder.tvHarga.setText(lyn.getHarga_layanan().toString());
        holder.tvId.setText(lyn.getNo_layanan());

        holder.cardview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLayananFragment dialogEdit = new editLayananFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("id", lyn.getId_layanan());
                bundle.putString("no", lyn.getNo_layanan());
                bundle.putString("nama", lyn.getNama_layanan());
                bundle.putInt("harga", lyn.getHarga_layanan());

                dialogEdit.setArguments(bundle);

                dialogEdit.show(((PengLynActivity) context).getSupportFragmentManager(), "dialog");
            }
        });
    }


    @Override
    public int getItemCount() {
        return listLayanan.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Layanan> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mlistLayanan);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Layanan item : mlistLayanan) {
                    if (item.getNama_layanan().toLowerCase().contains(filterPattern)) {
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
            listLayanan.clear();
            listLayanan.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvHarga, tvId;
        CardView cardview_btn;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.nama_layanan);
            tvId = itemView.findViewById(R.id.id_layanan);
            tvHarga = itemView.findViewById(R.id.harga_layanan);
            cardview_btn = itemView.findViewById(R.id.cardview_btn);
        }

    }


    public void filterList(List<Layanan> filteredList) {
        listLayanan = filteredList;
        notifyDataSetChanged();
    }
}
