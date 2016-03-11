package com.test.testcase2;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String ROOT_URL = "https://api.soundcloud.com/";
    private ListView listview;
    private List<Model> commit;

    String arr[] = new String[9000];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.listViewBuku);

        final ProgressDialog loading = ProgressDialog.show(this,"Loading....","Loading...",false,false);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ROOT_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Rest service = retrofit.create(Rest.class);
        Call<List<Model>> call = service.getData();
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                try {
                    loading.dismiss();
                    List<Model> commitall = response.body();
                    int counter=0;
                    for (Model models : response.body()) {
                        for (Map.Entry<String, Object> user : models.user.entrySet()) {
                            if(user.getKey().equalsIgnoreCase("username")) {
                                Log.e("File", user.getValue().toString());
                                arr[counter] = user.getValue().toString();
                            }
                        }
                        counter++;
                    }
                    commit = commitall;
                    showList();
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {

            }
        });
    }
    private void showList() {
        String[] items = new String[commit.size()];

        for (int i = 0; i < commit.size(); i++) {

            items[i] = "Title : "+commit.get(i).getTitle() +"\n\nArtwork_url : "+commit.get(i).getArtwork_url()+"\n\nWaveForm_Url"+commit.get(i).getWaveform_url()+"\n\nUsername : "+arr[i];
            Log.e("test",commit.get(i).getTitle());
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, items);
        listview.setAdapter(adapter);

    }

}
