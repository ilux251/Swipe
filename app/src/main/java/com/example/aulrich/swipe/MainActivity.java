package com.example.aulrich.swipe;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aulrich.swipe.Adapter.CardListAdapter;
import com.example.aulrich.swipe.Fragment.AddFragment;
import com.example.aulrich.swipe.Helper.RecyclerItemTouchHelpListener;
import com.example.aulrich.swipe.Helper.RecyclerItemTouchHelper;
import com.example.aulrich.swipe.Model.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelpListener {

    private List<Item> itemList;
    private RecyclerView recyclerView;
    private CoordinatorLayout rootLayout;
    private CardListAdapter adapter;

    public MainActivity()
    {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        rootLayout = findViewById(R.id.root_layout);
        itemList = new ArrayList<>();
        adapter = new CardListAdapter(this, itemList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


        // TODO: Es sollte eine funktionale Checkbox geben, die entscheidet,ob die RecyclerView getrennt wird oder nicht

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView name = v.findViewById(R.id.productName);
                name.setText("View OnClicked");

                v.setVisibility(View.GONE);

            }
        });

        addItems();
    }

    private void addItems()
    {
        itemList.add(new Item("Test1"));
        itemList.add(new Item("Test2"));
        itemList.add(new Item("Test3"));
        itemList.add(new Item("Test4"));
        itemList.add(new Item("Test5"));
        itemList.add(new Item("Test6"));
        itemList.add(new Item("Test7"));
        itemList.add(new Item("Test8"));
        itemList.add(new Item("Test9"));
        itemList.add(new Item("Test10"));
    }

    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof CardListAdapter.MyViewHolder)
        {
            final int adapterPosition = viewHolder.getAdapterPosition();
            final Item deletedItem = itemList.get(adapterPosition);

            if(direction == ItemTouchHelper.RIGHT)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                LayoutInflater inflater = this.getLayoutInflater();

                builder.setView(inflater.inflate(R.layout.amount_dialog, null))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id){

                            }
                        })
                        .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();


                Snackbar snackbar = Snackbar.make(rootLayout, "Test removed from cart!", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Hello again", Toast.LENGTH_SHORT).show();
                    }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
            else
            {
                String name = itemList.get(adapterPosition).getName();

                adapter.removeItemAndNotify(adapterPosition);

                Snackbar snackbar = Snackbar.make(rootLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        adapter.restoreItemAndNotify(deletedItem, adapterPosition);
                    }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        }
    }


}
