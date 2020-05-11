package com.MohaddisMedia.UrduFatwa;

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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.MohaddisMedia.UrduFatwa.Adapters.KutubSearchAdapter;
import com.MohaddisMedia.UrduFatwa.DB.KutubDBHelper;
import com.MohaddisMedia.UrduFatwa.DataModels.KutubDataModel;
import com.MohaddisMedia.UrduFatwa.KututbNetworking.Fatwa_ListActivity_Networking;
import com.MohaddisMedia.UrduFatwa.NavigationDrawerActivity.OtherProjects;
import com.MohaddisMedia.UrduFatwa.NavigationDrawerActivity.Subscribe;
import com.MohaddisMedia.UrduFatwa.Question.QuestinActivity;
import com.MohaddisMedia.UrduFatwa.Topics.TopicsActivity;

public class KutubActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch ( item.getItemId()) {
                case R.id.fist_page:
                    startActivity(new Intent(KutubActivity.this, MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.kutub:
                    startActivity(new Intent(KutubActivity.this, KutubActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.question:
                    startActivity(new Intent(KutubActivity.this, QuestinActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.topic:
                    Intent intent = new Intent(KutubActivity.this, TopicsActivity.class);
                    intent.putExtra(Constants.TOPIC,0);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.fav:
                    startActivity(new Intent(KutubActivity.this, FavouriteFatwaActivity.class));
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
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
    }
    RecyclerView kutubRecycler;
    Handler handler;
    BottomNavigationView navigation;
    String query;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_kutub_activity);
        progressBar = findViewById(R.id.progressBar2);
        kutubRecycler = findViewById(R.id.kutub_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        kutubRecycler.setLayoutManager(gridLayoutManager);
        handler = new Handler();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        //TODO navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == R.id.firstpg) {
                    Intent intent = new Intent(KutubActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.kutub) {
                    Intent intent = new Intent(KutubActivity.this, KutubActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.qs) {
                    Intent intent = new Intent(KutubActivity.this, QuestinActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.latest) {
                    Intent intent = null;
                    intent = new Intent(KutubActivity.this, Fatwa_ListActivity_Networking.class);
                    intent.putExtra(Constants.TITLE, "تازہ ترین جوابات");
                    intent.putExtra(Constants.LATEST,true);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                }  else if (id == R.id.subs) {
                    Intent intent = new Intent(KutubActivity.this, Subscribe.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.other) {
                    Intent intent = new Intent(KutubActivity.this, OtherProjects.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.favo) {
                    Intent intent = new Intent(KutubActivity.this, FavouriteFatwaActivity.class);
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
                DBHelper.KutubEntry.TABLE_NAME+" where "+ DBHelper.KutubEntry.PARENT_ID+" IS NULL";
        kutubSearchAdapter = null;
        kutubSearch(query);

        //TODO search code
        final EditText search = findViewById(R.id.editText2);
        ImageView searchbtn = findViewById(R.id.imageView41);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = "SELECT * from "+ DBHelper.KutubEntry.TABLE_NAME+" WHERE "+ DBHelper.KutubEntry.BOOK_TITLE+" LIKE '%"+search.getText().toString()+"%'";
                kutubSearch(query);
            }
        });

    }
    public void onBack(View v)
    {
        onBackPressed();
    }

    KutubDataModel kutubDataModel;
    ProgressDialog progressDialog;
    KutubSearchAdapter kutubSearchAdapter;
    public void kutubSearch(final String query)
    {
        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                kutubSearchAdapter = new KutubSearchAdapter(getApplicationContext(),KutubActivity.this);
                kutubRecycler.setAdapter(kutubSearchAdapter);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... voids) {

                searchInKutub(query);
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

    public void searchInKutub(String query)
    {

        KutubDBHelper kutubDBHelper = new KutubDBHelper(getApplicationContext());

        // Gets the data repository in write mode
        SQLiteDatabase db = kutubDBHelper.getReadableDatabase();
        Cursor cursor1 = db.rawQuery(query,null);
        Log.d("trySearchResult",cursor1.getCount()+"   "+query);
        for(int i =0;i<cursor1.getCount();i++)
        {
            cursor1.moveToNext();
            final KutubDataModel model = new KutubDataModel();
            model.setKitab_Id(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.ID)));
            model.setKitabparent_id(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.PARENT_ID)));
            model.setHasParts(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.HAS_PARTS)));
            model.setNo_of_parts(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.NO_OF_PARTS)));
            model.setQuestion_count(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.QUESTION_COUNT)));
            model.setKitab_title(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.BOOK_TITLE)));
            model.setBookImage(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.BOOK_IMAGE)));
            Cursor c2 = db.rawQuery("SELECT * from "+ DBHelper.KutubEntry.TABLE_NAME+" WHERE "+ DBHelper.KutubEntry.PARENT_ID+" == "+model.getKitab_Id(),null);
            if(c2.getCount()>0)
            {
                model.setHasJilds(true);
            }else{
                model.setHasJilds(false);
            }
            //TODO updating recyclerview
            handler.post(new Runnable() {
                @Override
                public void run() {
                    kutubSearchAdapter.addKitab(model);
                    kutubSearchAdapter.notifyDataSetChanged();
                }
            });
            Log.d("trySesultsHadees", cursor1.getString(0)+"  "+cursor1.getString(1)+"  "+cursor1.getInt(2));
        }

        final KutubDataModel model = new KutubDataModel();
        model.setKitab_Id(1212);
        model.setKitab_title( "محدث کمیٹی کے فتاوی");
        model.setBookImage("public/assets-frontend/imgs/no_book.png");
        model.setHasJilds(false);
        //TODO updating recyclerview
        handler.post(new Runnable() {
            @Override
            public void run() {
                kutubSearchAdapter.addKitab(model);
                kutubSearchAdapter.notifyDataSetChanged();
            }
        });
        kutubDBHelper.close();
    }



}

