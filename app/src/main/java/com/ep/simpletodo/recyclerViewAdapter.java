package com.ep.simpletodo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Eesaa Philips
 * @version 1.0
 * @since 1.0
 */
public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.customViewHolder> {

    private List<Todo> todoList = Collections.emptyList();
    private Context mContext;

    //used to communicate the task's position in the list
    public static final String TODO_POSITION = "TODO_POS";

    /**
     * Constructor of recyclerViewAdapter
     *
     * @param context : context of initial activity
     */
    public recyclerViewAdapter(Context context) {
        mContext = context;
    }

    /**
     * Inflates the todo_list_item xml layout
     *
     * @param parent   : the parent viewGroup of the layout
     * @param viewType : the type of the view to be inflated
     * @see RecyclerView.Adapter
     */
    @Override
    public recyclerViewAdapter.customViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_item, parent, false);

        return new customViewHolder(itemView);
    }


    /**
     * This onCreate method is started automatically when the app starts
     *
     * @param holder : customViewHolder that contains the task
     * @param i      : portion of holder
     */
    @Override
    public void onBindViewHolder(final customViewHolder holder, int i) {
        if (todoList != null) {
            final Todo todo = todoList.get(i);
            holder.todoTitle_tv.setText(todo.getTodo_name());
            holder.todoDate_tv.setText(null);
            if (todo.isHasDate()) {
                String date = convertDateFormat(NewTodo.dateFormat, "MMM dd", todo.getDate());
                holder.todoDate_tv.setText(date);
                String time = convertDateFormat(NewTodo.timeFormat, "hh:mm a", todo.getTime());
                holder.todoDate_tv.append(" at " + time);
            }
            final Button optionsButton = holder.optionsButton;

            //Handle options button click
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
                                    openEditActivity(todoList.get(holder.getAdapterPosition()), holder.getAdapterPosition());
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
        } else {
            holder.todoTitle_tv.setText("No Todo");
        }
    }

    /**
     * Converts {@link SimpleDateFormat}
     *
     * @param oldFormat : the initial format
     * @param newFormat : the new format
     * @param date      : the date to be converted
     * @return converted date
     */
    public String convertDateFormat(String oldFormat, String newFormat, String date) {
        SimpleDateFormat format = new SimpleDateFormat(oldFormat);
        Date newDate = null;
        try {
            newDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        format = new SimpleDateFormat(newFormat);
        return format.format(newDate);
    }

    /**
     * Starts {@link EditTodo} from {@link MainActivity} using EDIT_TODO_REQUEST_CODE
     *
     * @param todo : the todo to be passed to the {@link EditTodo} activity
     */
    private void openEditActivity(Todo todo, int pos) {
        Intent intent = new Intent(mContext, EditTodo.class);
        intent.putExtra(NewTodo.TASK_ID, todo);
        intent.putExtra(TODO_POSITION, pos);
        ((Activity) mContext).startActivityForResult(intent, MainActivity.EDIT_TODO_REQUEST_CODE);
    }

    @Override
    public int getItemCount() {
        if (todoList != null)
            return todoList.size();
        return 0;
    }

    /**
     * Updates todoList to newList
     *
     * @param newList : the new List that todoList should be set to
     */
    public void updateList(List<Todo> newList) {
        todoList = newList;
        notifyDataSetChanged();
    }

    public class customViewHolder extends RecyclerView.ViewHolder {
        public TextView todoTitle_tv;
        public CheckBox todoCheck_cb;
        public Button optionsButton;
        public TextView todoDate_tv;
        public View mView;

        /**
         * Constructor binds all the elements from todo_list_item
         *
         * @param view : the view that contains the elements
         */
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
