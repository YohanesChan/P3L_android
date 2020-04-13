package com.example.kouvemobile.RecyclerView;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public interface ClickListener {
    public void onClick(RecyclerView.ViewHolder view, int position);
    public void onLongClick(View view, int Position);
}
