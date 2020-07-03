package com.othmanehadday.jwtapk.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.othmanehadday.jwtapk.AddTasksFragment;
import com.othmanehadday.jwtapk.AllTasksFragment;
import com.othmanehadday.jwtapk.R;
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


public class MainActivity extends AppCompatActivity {

    private SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.AllTasks, new AllTasksFragment()).commitNow();
        }

        sharedPref = new SharedPref(getApplicationContext());
        if (!sharedPref.isAuthenticated()) {
            finish();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutItem: {
                SharedPreferences.Editor editor = sharedPref.getSharedpreferences().edit();
                editor.remove("jwtToken");
                editor.commit();

                finish();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.side_menu, menu);
        menu.findItem(R.id.usernameItem).setTitle(sharedPref.getSub());

        return super.onCreateOptionsMenu(menu);
    }
}
