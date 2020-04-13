package com.example.kouvemobile.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.kouvemobile.API.ApiClient;
import com.example.kouvemobile.Model.ProdukShow;
import com.example.kouvemobile.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.kouvemobile.API.ApiClient.URL_IMAGE;

public class ShowProdukAdapter extends RecyclerView.Adapter<ShowProdukAdapter.GridViewHolder>{
    private List<ProdukShow> listProduk;
    private Context context;
    String image;
    public ShowProdukAdapter(List<ProdukShow> list, Context context) {
        this.listProduk = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ShowProdukAdapter.GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produk_show, parent, false);
        ShowProdukAdapter.GridViewHolder vHolder = new ShowProdukAdapter.GridViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowProdukAdapter.GridViewHolder holder, int position) {
        image = ApiClient.getImageURL();
        final ProdukShow pdk = listProduk.get(position);

        holder.tvNama.setText(pdk.getNama_produk());
        holder.tvHarga.setText(pdk.getHarga_produk().toString());
        holder.tvStok.setText(pdk.getStok_produk().toString());
        //holder.image.setImageResource(Integer.parseInt(String.valueOf(pdk.getGambar())));
        Picasso.with(context).load(URL_IMAGE+pdk.getGambar()).resize(200,200)
                .centerCrop().into(holder.image);

    }

    @Override
    public int getItemCount() {
        return listProduk.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama,tvHarga, tvStok;
        ImageView image;
        public GridViewHolder(@NonNull View itemView) {
                super(itemView);
                tvNama = itemView.findViewById(R.id.namas_produk);
                tvHarga = itemView.findViewById(R.id.hrgs_produk);
                tvStok = itemView.findViewById(R.id.stoks_produk);
                image = itemView.findViewById(R.id.imgs_produk);
        }
    }
}

