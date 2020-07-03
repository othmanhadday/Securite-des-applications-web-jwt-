package com.othmanehadday.jwtapk;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.othmanehadday.jwtapk.activities.LoginActivity;
import com.othmanehadday.jwtapk.adapter.TasksAdapter;
import com.othmanehadday.jwtapk.model.Task;
import com.othmanehadday.jwtapk.services.AuthentcationService;
import com.othmanehadday.jwtapk.services.SharedPref;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class AllTasksFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button buttonAddTask;
    private SharedPref sharedPref;

    private List<Task> tasks = new ArrayList<Task>();
    public TasksAdapter tasksAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_tasks, container, false);

        buttonAddTask = view.findViewById(R.id.buttonGoAddTask);
        recyclerView = view.findViewById(R.id.recyclerView);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedPref = new SharedPref(getActivity());
        if (!sharedPref.isAdmin()) {
            buttonAddTask.setVisibility(View.GONE);
        }

        buttonAddTask.setOnClickListener(v -> {
            getActivity().findViewById(R.id.AddTask).setVisibility(View.VISIBLE);
            getFragmentManager().beginTransaction()
                    .replace(R.id.AddTask, new AddTasksFragment()).addToBackStack("").commit();

        });

        if(tasks.size()==0) {
            getTasks(sharedPref.getToken());
        }
        tasksAdapter = new TasksAdapter(tasks, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        recyclerView.setAdapter(tasksAdapter);
    }


    public void getTasks(String jwt) {
        OkHttpClient client = new OkHttpClient();
        Request newReq = new Request.Builder()
                .url(AuthentcationService.host + "/tasks")
                .get()
                .addHeader("Authorization", "Bearer " + jwt)
                .addHeader("Accept", "application/json; charset=utf-8")
//                .addHeader("Content-Type", "application/json")
//                .addHeader("X-COM-PERSIST", "TRUE")
                .build();

        client.newCall(newReq).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("okhttp", "excepton request----------" + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d("okhttp", response.body().toString());

                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Task task = new Task();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        task.setId(jsonObject.getInt("id"));
                        task.setTaskName(jsonObject.getString("taskName"));
                        tasks.add(task);
                    }
                    getActivity().runOnUiThread(() -> {
                        tasksAdapter.notifyDataSetChanged();
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
