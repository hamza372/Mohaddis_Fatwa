package com.MohaddisMedia.UrduFatwa.Topics;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.MohaddisMedia.UrduFatwa.Adapters.TopicAdapter;
import com.MohaddisMedia.UrduFatwa.Constants;
import com.MohaddisMedia.UrduFatwa.DB.CategoryDBHelper;
import com.MohaddisMedia.UrduFatwa.DBHelper;
import com.MohaddisMedia.UrduFatwa.DataModels.TopicDataModel;
import com.MohaddisMedia.UrduFatwa.FavouriteFatwaActivity;
import com.MohaddisMedia.UrduFatwa.KutubActivity;
import com.MohaddisMedia.UrduFatwa.KututbNetworking.Fatwa_ListActivity_Networking;
import com.MohaddisMedia.UrduFatwa.MainActivity;
import com.MohaddisMedia.UrduFatwa.NavigationDrawerActivity.OtherProjects;
import com.MohaddisMedia.UrduFatwa.NavigationDrawerActivity.Subscribe;
import com.MohaddisMedia.UrduFatwa.NavigationDrawerActivity.Updates;
import com.MohaddisMedia.UrduFatwa.Question.QuestinActivity;
import com.MohaddisMedia.UrduFatwa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.MohaddisMedia.UrduFatwa.Constants.SEARCHTEXT;

public class TopicsActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch ( item.getItemId()) {
                case R.id.fist_page:
                    startActivity(new Intent(TopicsActivity.this, MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.kutub:
                    startActivity(new Intent(TopicsActivity.this, KutubActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.question:
                    startActivity(new Intent(TopicsActivity.this, QuestinActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.topic:
                    Intent intent = new Intent(TopicsActivity.this, TopicsActivity.class);
                    intent.putExtra(Constants.TOPIC,0);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.fav:
                    startActivity(new Intent(TopicsActivity.this, FavouriteFatwaActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
            }
            return  true;
        };
    };

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
    }
    RecyclerView topicRecycler;
    Handler handler;
    BottomNavigationView navigation;
    ProgressBar progressBar;
    int parentId = 0;
    String query;
    TextView topicTitle;

    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_topics);
        progressBar = findViewById(R.id.progressBartop);

        parentId = getIntent().getExtras().getInt(Constants.TOPIC,0);
        title = getIntent().getExtras().getString(Constants.TITLE,"موضوعاتی فہرست");
        topicRecycler = findViewById(R.id.topic_recycler);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getApplicationContext());
        topicRecycler.setLayoutManager(gridLayoutManager);
        handler = new Handler();

        topicTitle = findViewById(R.id.topictvid);
        topicTitle.setText(title);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        //TODO navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == R.id.firstpg) {
                    Intent intent = new Intent(TopicsActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.kutub) {
                    Intent intent = new Intent(TopicsActivity.this, KutubActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.qs) {
                    Intent intent = new Intent(TopicsActivity.this, QuestinActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.latest) {
                    Intent intent = null;
                    intent = new Intent(TopicsActivity.this, Fatwa_ListActivity_Networking.class);
                    intent.putExtra(Constants.TITLE, "تازہ ترین جوابات");
                    intent.putExtra(Constants.LATEST,true);
                    startActivity(intent);
                } else if (id == R.id.subs) {
                    Intent intent = new Intent(TopicsActivity.this, Subscribe.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.other) {
                    Intent intent = new Intent(TopicsActivity.this, OtherProjects.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.favo) {
                    Intent intent = new Intent(TopicsActivity.this, FavouriteFatwaActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        final ImageView layout = findViewById(R.id.imageView2);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        drawer.setStatusBarBackgroundColor(
                Color.BLACK);

        query= "SELECT * from " +
                DBHelper.TopicEntry.TABLE_NAME+" WHERE "+ DBHelper.TopicEntry.PARENT_ID+" == "+parentId;
        topicSearchAdapter = null;
       // topicSearch(query);
        topicSearchAPI(parentId);


        //TODO search code
        final EditText search = findViewById(R.id.editText);
        ImageView searchbtn = findViewById(R.id.imageView8);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!search.getText().toString().equals("")) {
                    searchText = search.getText().toString();
                    query = "SELECT * from " + DBHelper.TopicEntry.TABLE_NAME + " WHERE " + DBHelper.TopicEntry.TITLE + " LIKE '%" + search.getText().toString() + "%'";
                    topicSearch(query);
                }else{
                    searchText = "-1";
                    query= "SELECT * from " +
                            DBHelper.TopicEntry.TABLE_NAME+" WHERE "+ DBHelper.TopicEntry.PARENT_ID+" == "+parentId;
                    topicSearchAdapter = null;
                    topicSearch(query);
                }
            }
        });

    }
    public void onBack(View v)
    {
        onBackPressed();
    }

    TopicDataModel topicDataModel;
    ProgressDialog progressDialog;
    TopicAdapter topicSearchAdapter;
    String searchText = "-1";
    public void topicSearch(final String query)
    {
        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                topicSearchAdapter = new TopicAdapter(getApplicationContext(),TopicsActivity.this);
                topicSearchAdapter.setSearchText(searchText);
                topicRecycler.setAdapter(topicSearchAdapter);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                loadFromAPI();
                searchInTopics(query);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressBar.setVisibility(View.GONE);
            }
        }.execute();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void topicSearchAPI(int parent)
    {
        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                topicSearchAdapter = new TopicAdapter(getApplicationContext(),TopicsActivity.this);
                topicSearchAdapter.setSearchText(searchText);
                topicRecycler.setAdapter(topicSearchAdapter);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                if(arrayList == null) {
                    loadFromAPI();
                }
                for(int i = 0;i<arrayList.size();i++){
                    if(arrayList.get(i).getParent_id() == parentId) {
                        final int finalI = i;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                topicSearchAdapter.addTopic(arrayList.get(finalI));
                                topicSearchAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressBar.setVisibility(View.GONE);
            }
        }.execute();
    }


    ArrayList<TopicDataModel> arrayList;
    public void loadFromAPI(){
        Log.d("tryTopic","before loading");
        arrayList = new ArrayList<>();
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://urdufatwa.com/api/categories")
                    .build();
            Response response = client.newCall(request).execute();
            String str = response.body().string();
            JSONObject json = new JSONObject(str);
            JSONArray array = json.getJSONArray("category");
            for(int i =0;i<array.length();i++){
                JSONObject obj = (JSONObject) array.get(i);
                arrayList.add(new TopicDataModel(obj.getInt("id"),obj.getInt("parent_id"),obj.getString("title"),obj.getInt("question_count")));
            }
        }catch (IOException | JSONException i){

        }
        Log.d("tryTopic","After loading");
    }

    public void searchInTopics(String query)
    {
        CategoryDBHelper topicDBHelper = new CategoryDBHelper(getApplicationContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = topicDBHelper.getReadableDatabase();
        Cursor cursor1 = db.rawQuery(query,null);
        Log.d("trySearchResult",cursor1.getCount()+"   "+query);
        for(int i =0;i<cursor1.getCount();i++)
        {
            cursor1.moveToNext();
            final TopicDataModel model = new TopicDataModel();
            model.setId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.ID)));
            model.setParent_id(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.PARENT_ID)));
            model.setDescription(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.DESCRIPTION)));
            model.setCreated_at(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.CREATED_AT)));
            model.setQuestion_count(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.QUESTION_COUNT)));
            model.setTitle(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.TITLE)));

            //TODO updating recyclerview
            handler.post(new Runnable() {
                @Override
                public void run() {
                    topicSearchAdapter.addTopic(model);
                    topicSearchAdapter.notifyDataSetChanged();
                }
            });
            Log.d("trySesultsHadees", cursor1.getString(0)+"  "+cursor1.getString(1)+"  "+cursor1.getInt(2));
        }
        topicDBHelper.close();
    }

}
