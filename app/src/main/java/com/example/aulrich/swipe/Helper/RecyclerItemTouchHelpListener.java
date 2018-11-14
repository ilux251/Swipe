package com.example.aulrich.swipe.Helper;

import android.support.v7.widget.RecyclerView;

public interface RecyclerItemTouchHelpListener {

    void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
