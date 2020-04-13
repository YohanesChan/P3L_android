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

import com.example.kouvemobile.Frag.editUkuranFragment;
import com.example.kouvemobile.Model.Ukuran;
import com.example.kouvemobile.PengSupActivity;
import com.example.kouvemobile.PengUkActivity;
import com.example.kouvemobile.R;

import java.util.ArrayList;
import java.util.List;

public class DataUkuranAdapter extends RecyclerView.Adapter<DataUkuranAdapter.CardViewViewHolder> implements Filterable {

    private List<Ukuran> listUkuran;
    private List<Ukuran> mlistUkuran;
    private Context context;

    public DataUkuranAdapter(List<Ukuran> list, Context context) {
        this.listUkuran = list;
        this.context = context;
        mlistUkuran = new ArrayList<>(listUkuran);
    }


    @NonNull
    @Override
    public DataUkuranAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ukuran, parent, false);
        DataUkuranAdapter.CardViewViewHolder vHolder = new DataUkuranAdapter.CardViewViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DataUkuranAdapter.CardViewViewHolder holder, final int position) {

        final Ukuran sup = listUkuran.get(position);


        holder.tvNama.setText(sup.getNama_ukuran_hewan());
        holder.cardview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUkuranFragment dialogEdit = new editUkuranFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("id", sup.getId_ukuran());
                bundle.putString("no", sup.getNo_ukuran());
                bundle.putString("nama", sup.getNama_ukuran_hewan());

                dialogEdit.setArguments(bundle);

                dialogEdit.show(((PengUkActivity) context).getSupportFragmentManager(), "dialog");
            }
        });
    }


    @Override
    public int getItemCount() {
        return listUkuran.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Ukuran> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mlistUkuran);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Ukuran item : mlistUkuran) {
                    if (item.getNama_ukuran_hewan().toLowerCase().contains(filterPattern)) {
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
            listUkuran.clear();
            listUkuran.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama,tvId;
        CardView cardview_btn;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.nama_ukuran);
            cardview_btn = itemView.findViewById(R.id.cardview_btn);
        }

    }


    public void filterList(List<Ukuran> filteredList) {
        listUkuran = filteredList;
        notifyDataSetChanged();
    }
}
