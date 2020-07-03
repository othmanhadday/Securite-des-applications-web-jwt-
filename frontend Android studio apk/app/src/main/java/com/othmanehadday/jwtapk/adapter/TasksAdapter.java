package com.othmanehadday.jwtapk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.othmanehadday.jwtapk.R;
import com.othmanehadday.jwtapk.model.Task;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private List<Task> listTasks;
    private Context context;


    public TasksAdapter(List<Task> list , Context context) {
        this.listTasks = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listView = layoutInflater.inflate(R.layout.task_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(listView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewTask.setText(listTasks.get(position).getTaskName());
    }

    @Override
    public int getItemCount() {
        return listTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTask;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTask = itemView.findViewById(R.id.textViewTask);
        }
    }
}
