package com.ep.simpletodo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.customViewHolder> {

    private List<Todo> todoList;
    private Context mContext;


    public recyclerViewAdapter(List<Todo> todoList, Context context) {
        this.todoList = todoList;
        mContext = context;
    }

    //create new view
    @Override
    public recyclerViewAdapter.customViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_item, parent, false);

        return new customViewHolder(itemView);
    }

    //provide a reference to the view for each data item

    @Override
    public void onBindViewHolder(final customViewHolder holder, int i) {
        final Todo todo = todoList.get(i);
        holder.todoTitle_tv.setText(todo.getTodo_name());
        holder.todoDate_tv.setText(null);
        if (todo.isHasDate())
            holder.todoDate_tv.setText(todo.getDate());
        if (todo.isHasTime())
            holder.todoDate_tv.append(" at " + todo.getTime());
        final Button optionsButton = holder.optionsButton;
        //if options button is clicked
        holder.optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mContext, optionsButton);
                popupMenu.inflate(R.menu.recyclerview_item_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.action_editItem:
                                openEditActivity();
                                return true;
                            case R.id.action_deleteItem:
                                todoList.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        //if row is clicked
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "you checked off: " + todo.getTodo_name(), Toast.LENGTH_SHORT).show();
                holder.todoCheck_cb.setChecked(!todo.getIsChecked());
                todo.setIsChecked(!todo.getIsChecked());
            }
        });
    }

    private void openEditActivity() {
        //TODO: open edit todo activity
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void updateList(List<Todo> newList) {
        todoList = new ArrayList<>();
        todoList = newList;
        notifyDataSetChanged();
    }

    public class customViewHolder extends RecyclerView.ViewHolder {
        public TextView todoTitle_tv;
        public CheckBox todoCheck_cb;
        public Button optionsButton;
        public TextView todoDate_tv;
        public View mView;

        public customViewHolder(View view) {
            super(view);
            todoTitle_tv = view.findViewById(R.id.todoTitle);
            todoCheck_cb = view.findViewById(R.id.todoCheckbox);
            optionsButton = view.findViewById(R.id.buttonOptions);
            todoDate_tv = view.findViewById(R.id.todoDate);
            mView = itemView;
        }
    }
}
