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

import com.example.kouvemobile.Frag.editJenisFragment;
import com.example.kouvemobile.Model.Jenis;
import com.example.kouvemobile.PengJnsActivity;
import com.example.kouvemobile.PengUkActivity;
import com.example.kouvemobile.R;

import java.util.ArrayList;
import java.util.List;

public class DataJenisAdapter extends RecyclerView.Adapter<DataJenisAdapter.CardViewViewHolder> implements Filterable {

    private List<Jenis> listJenis;
    private List<Jenis> mlistJenis;
    private Context context;

    public DataJenisAdapter(List<Jenis> list, Context context) {
        this.listJenis = list;
        this.context = context;
        mlistJenis = new ArrayList<>(listJenis);
    }


    @NonNull
    @Override
    public DataJenisAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_jenis, parent, false);
        DataJenisAdapter.CardViewViewHolder vHolder = new DataJenisAdapter.CardViewViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DataJenisAdapter.CardViewViewHolder holder, final int position) {

        final Jenis jns = listJenis.get(position);


        holder.tvNama.setText(jns.getNama_jenis_hewan());
        holder.cardview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editJenisFragment dialogEdit = new editJenisFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("id", jns.getId_jenis());
                bundle.putString("nama", jns.getNama_jenis_hewan());

                dialogEdit.setArguments(bundle);

                dialogEdit.show(((PengJnsActivity) context).getSupportFragmentManager(), "dialog");
            }
        });
    }


    @Override
    public int getItemCount() {
        return listJenis.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Jenis> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mlistJenis);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Jenis item : mlistJenis) {
                    if (item.getNama_jenis_hewan().toLowerCase().contains(filterPattern)) {
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
            listJenis.clear();
            listJenis.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama,tvId;
        CardView cardview_btn;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.nama_jenis);
            tvId = itemView.findViewById(R.id.id_layanan);
            cardview_btn = itemView.findViewById(R.id.cardview_btn);
        }

    }


    public void filterList(List<Jenis> filteredList) {
        listJenis = filteredList;
        notifyDataSetChanged();
    }
}
