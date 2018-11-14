package com.example.aulrich.swipe.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.aulrich.swipe.R;

import com.example.aulrich.swipe.Model.Item;

import java.util.List;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.MyViewHolder> {

    private Context context;
    private List<Item> list;

    public CardListAdapter(Context context, List<Item> list) {
        this.context = context;
        this.list = list;
    }

    //MyViewHolder Inflate auf ein anderes Layout
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_list, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Item item = list.get(i);
        myViewHolder.name.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeItemAndNotify(int position)
    {
        list.remove(position);
        notifyDataSetChanged();
    }

    public void restoreItemAndNotify(Item item, int position)
    {
        list.add(position, item);
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name, action_text;
        public View viewForeground, viewRemoveBackground, viewAddBackground;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            viewRemoveBackground = itemView.findViewById(R.id.view_remove_background);
            viewAddBackground = itemView.findViewById(R.id.view_add_background);

            name = itemView.findViewById(R.id.cardname);
        }
    }
}
