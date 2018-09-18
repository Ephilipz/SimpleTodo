package com.ep.simpletodo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.customViewHolder> {

    private List<Todo> todoList;

    //provide a reference to the view for each data item
    public static class customViewHolder extends RecyclerView.ViewHolder {
        public TextView todoTitle_tv;
        public CheckBox todoCheck_cb;

        public customViewHolder(View view) {
            super(view);
            todoTitle_tv = view.findViewById(R.id.todoTitle);
            todoCheck_cb = view.findViewById(R.id.todoCheckbox);
        }
    }

    public recyclerViewAdapter(List<Todo> todoList) {
        this.todoList = todoList;
    }

    //create new view
    @Override
    public recyclerViewAdapter.customViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_item, parent, false);

        return new customViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(customViewHolder holder, int i) {
        Todo todo = todoList.get(i);
        holder.todoTitle_tv.setText(todo.getTodo_name());
        holder.todoCheck_cb.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }
}
