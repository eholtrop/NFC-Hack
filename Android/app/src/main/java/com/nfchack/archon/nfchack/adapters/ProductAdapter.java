package com.nfchack.archon.nfchack.adapters;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nfchack.archon.nfchack.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Evan on 12/21/2015.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<String> Items;
    private Context _context;

    public ProductAdapter(Context context) {
        _context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(_context).inflate(R.layout.product_adapter, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.Name.setText(Items.get(position));
    }

    public void add(String item) {
        Items.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text)
        private TextView Name;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
