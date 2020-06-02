package com.example.kouvemobile.RecyclerView;

//import android.app.Dialog;

import android.content.Context;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouvemobile.API.ApiClient;
import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.Frag.addPegawaiFragment;
import com.example.kouvemobile.Frag.editPegawaiFragment;
import com.example.kouvemobile.Model.Pegawai;
import com.example.kouvemobile.PengPgwActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.Response.showPegawai;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPegawaiAdapter extends RecyclerView.Adapter<DataPegawaiAdapter.CardViewViewHolder> implements Filterable {

    private List<Pegawai> listPegawai;
    private List<Pegawai> mlistPegawai;
    private Context context;

    public DataPegawaiAdapter(List<Pegawai> list, Context context) {
        this.listPegawai = list;
        this.context = context;
        mlistPegawai = new ArrayList<>(listPegawai);
    }


    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pegawai, parent, false);
        CardViewViewHolder vHolder = new CardViewViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, final int position) {

        final Pegawai pgw = listPegawai.get(position);


        holder.tvNama.setText(pgw.getNama_pegawai());
        holder.tvStatus.setText(pgw.getRole_pegawai());
        holder.tvTelp.setText(pgw.getTelp_pegawai());
        holder.tvId.setText(pgw.getNo_pegawai());
        holder.cardview_btn.setOnClickListener(      new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPegawaiFragment dialogEdit = new editPegawaiFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("id", pgw.getId_pegawai());
                bundle.putString("no", pgw.getNo_pegawai());
                bundle.putString("nama", pgw.getNama_pegawai());
                bundle.putString("telp", pgw.getTelp_pegawai());
                bundle.putString("bday", pgw.getBirthday_pegawai());
                bundle.putString("alamat", pgw.getAlamat_pegawai());

                dialogEdit.setArguments(bundle);

                dialogEdit.show(((PengPgwActivity) context).getSupportFragmentManager(), "dialog");
            }
        });
    }


    @Override
    public int getItemCount() {
        return listPegawai.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Pegawai> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mlistPegawai);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Pegawai item : mlistPegawai) {
                    if (item.getNama_pegawai().toLowerCase().contains(filterPattern)) {
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
            listPegawai.clear();
            listPegawai.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvId, tvTelp, tvStatus;
        CardView cardview_btn;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.nama_pegawai);
            tvStatus = itemView.findViewById(R.id.status_pegawai);
            tvId = itemView.findViewById(R.id.id_pegawai);
            tvTelp = itemView.findViewById(R.id.tlp_pegawai);
            cardview_btn = itemView.findViewById(R.id.cardview_btn);
        }

    }


    public void filterList(List<Pegawai> filteredList) {
        listPegawai = filteredList;
        notifyDataSetChanged();
    }
}

