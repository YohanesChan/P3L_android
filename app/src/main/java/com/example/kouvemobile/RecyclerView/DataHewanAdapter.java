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

import com.example.kouvemobile.Frag.editHewanFragment;
import com.example.kouvemobile.Model.Hewan;
import com.example.kouvemobile.PengHewActivity;
import com.example.kouvemobile.R;

import java.util.ArrayList;
import java.util.List;

public class DataHewanAdapter extends RecyclerView.Adapter<DataHewanAdapter.CardViewViewHolder> implements Filterable {

    private List<Hewan> listHewan;
    private List<Hewan> mlistHewan;
    private Context context;

    public DataHewanAdapter(List<Hewan> list, Context context) {
        this.listHewan = list;
        this.context = context;
        mlistHewan = new ArrayList<>(listHewan);
    }


    @NonNull
    @Override
    public DataHewanAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_hew, parent, false);
        DataHewanAdapter.CardViewViewHolder vHolder = new DataHewanAdapter.CardViewViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DataHewanAdapter.CardViewViewHolder holder, final int position) {

        final Hewan hew = listHewan.get(position);


        holder.tvNama.setText(hew.getNama_hewan());
        holder.tvId.setText(hew.getNo_hewan());
        holder.tvCust.setText(hew.getId_customer_fk().toString());
        holder.cardview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editHewanFragment dialogEdit = new editHewanFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("id", hew.getId_hewan());
                bundle.putString("no", hew.getNo_hewan());
                bundle.putString("nama", hew.getNama_hewan());
                bundle.putInt("id_cust", hew.getId_customer_fk());

                dialogEdit.setArguments(bundle);

                dialogEdit.show(((PengHewActivity) context).getSupportFragmentManager(), "dialog");
            }
        });
    }


    @Override
    public int getItemCount() {
        return listHewan.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Hewan> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mlistHewan);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Hewan item : mlistHewan) {
                    if (item.getNama_hewan().toLowerCase().contains(filterPattern)) {
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
            listHewan.clear();
            listHewan.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvId, tvCust;
        CardView cardview_btn;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.nama_hew);
            tvId = itemView.findViewById(R.id.id_hew);
            tvCust = itemView.findViewById(R.id.cust_hew);
            cardview_btn = itemView.findViewById(R.id.cardview_btn);
        }

    }


    public void filterList(List<Hewan> filteredList) {
        listHewan = filteredList;
        notifyDataSetChanged();
    }
}
