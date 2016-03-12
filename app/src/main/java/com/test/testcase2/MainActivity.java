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
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
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
    boolean addList=true;
    String arr[] = new String[9000];
    ArrayAdapter adapter;
    ArrayList<String> listItems=new ArrayList<String>();
    int page=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.listViewBuku);
        LoadData();

    }
    private void LoadData(){
        final ProgressDialog loading = ProgressDialog.show(this,"Loading....","Loading...",false,false);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ROOT_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Rest service = retrofit.create(Rest.class);
        Call<List<Model>> call = service.getData("734fc03dc9ee26f4c1a4841d0b6f00e4",1,10);
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                try {
                    loading.dismiss();
                    List<Model> commitall = response.body();
                    int counter = 0;
                    for (Model models : response.body()) {
                        for (Map.Entry<String, Object> user : models.user.entrySet()) {
                            if (user.getKey().equalsIgnoreCase("username")) {
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

        for (int i = 0; i < commit.size(); i++) {

            listItems.add("Title : "+commit.get(i).getTitle() +"\n\nArtwork_url : "+commit.get(i).getArtwork_url()+"\n\nWaveForm_Url : "+commit.get(i).getWaveform_url()+"\n\nUsername : "+arr[i]);
            Log.e("test",commit.get(i).getTitle());
        }
        adapter = new ArrayAdapter<String>(this, R.layout.activity_listview,listItems);

        listview.setAdapter(adapter);
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {


            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listview.getLastVisiblePosition() == listview.getAdapter().getCount() - 1
                        && listview.getChildAt(listview.getChildCount() - 1).getBottom() <= listview.getHeight()) {
                        Retrofit retrofit = new Retrofit.Builder().baseUrl(ROOT_URL).addConverterFactory(GsonConverterFactory.create()).build();
                        Rest service = retrofit.create(Rest.class);
                        Call<List<Model>> call = service.getData("734fc03dc9ee26f4c1a4841d0b6f00e4", page++, 10);
                        call.enqueue(new Callback<List<Model>>() {
                            @Override
                            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                                try {
                                    List<Model> commitall = response.body();
                                    int counter = 0;
                                    String temp="";
                                    for (Model models : response.body()) {
                                        for (Map.Entry<String, Object> user : models.user.entrySet()) {
                                            if (user.getKey().equalsIgnoreCase("username")) {
                                                Log.e("File", user.getValue().toString());

                                                Log.i("Info", "Scroll Bottom");
                                               temp = user.getValue().toString();


                                                listItems.add("Title : "+models.getTitle() +"\n\nArtwork_url : "+models.getArtwork_url()+"\n\nWaveForm_Url : "+models.getWaveform_url()+"\n\nUsername : "+temp);

                                                adapter.notifyDataSetChanged();
                                            }

                                        }
                                        counter++;
                                    }
                                    commit = commitall;
                                } catch (Exception e) {

                                }
                            }

                            @Override
                            public void onFailure(Call<List<Model>> call, Throwable t) {

                            }
                        });


                }
            }
        });

    }

}
