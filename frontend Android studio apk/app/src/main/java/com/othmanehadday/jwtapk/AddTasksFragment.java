package com.othmanehadday.jwtapk;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.othmanehadday.jwtapk.model.Task;
import com.othmanehadday.jwtapk.services.AuthentcationService;
import com.othmanehadday.jwtapk.services.SharedPref;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AddTasksFragment extends Fragment {
    private Button buttonAddTask;
    private EditText editTextNewTask;
    private SharedPref sharedPref;
    private Task task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_tasks, container, false);

        buttonAddTask = view.findViewById(R.id.buttonAddTask);
        editTextNewTask = view.findViewById(R.id.editTextNewTask);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedPref = new SharedPref(getActivity());

        buttonAddTask.setOnClickListener(v -> {
            this.postData(editTextNewTask.getText().toString(), sharedPref.getToken());

            getActivity().findViewById(R.id.AddTask).setVisibility(View.GONE);

            getFragmentManager().beginTransaction().remove(AddTasksFragment.this).commit();
            getFragmentManager().beginTransaction().remove(new AllTasksFragment()).commit();

            getFragmentManager().beginTransaction()
                    .replace(R.id.AllTasks, new AllTasksFragment()).commit();

        });

    }


    public void postData(String task, String jwt) {
        Log.d("tasks", task);
        String url = AuthentcationService.host + "/tasks";
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        JSONObject data = new JSONObject();
        try {
            data.put("taskName", task);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(JSON, data.toString());
        Request newReq = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Authorization", "Bearer " + jwt)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build();

        okHttpClient.newCall(newReq).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("okhttp", "excepton request----------" + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d("okhttp", "" + response.body().string());
            }
        });
    }

}
