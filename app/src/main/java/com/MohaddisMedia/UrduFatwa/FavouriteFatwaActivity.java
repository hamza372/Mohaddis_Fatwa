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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.MohaddisMedia.UrduFatwa.Adapters.FatwaListAdapter;
import com.MohaddisMedia.UrduFatwa.DB.FavouriteDBHelper;
import com.MohaddisMedia.UrduFatwa.DB.JildDBHelper;
import com.MohaddisMedia.UrduFatwa.DataModels.FatwaDataModel;
import com.MohaddisMedia.UrduFatwa.KututbNetworking.Fatwa_ListActivity_Networking;
import com.MohaddisMedia.UrduFatwa.NavigationDrawerActivity.OtherProjects;
import com.MohaddisMedia.UrduFatwa.NavigationDrawerActivity.Subscribe;
import com.MohaddisMedia.UrduFatwa.Question.QuestinActivity;
import com.MohaddisMedia.UrduFatwa.Topics.TopicsActivity;

public class FavouriteFatwaActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch ( item.getItemId()) {
                case R.id.fist_page:
                    startActivity(new Intent(FavouriteFatwaActivity.this, MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.kutub:
                    startActivity(new Intent(FavouriteFatwaActivity.this, KutubActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.question:
                    startActivity(new Intent(FavouriteFatwaActivity.this, QuestinActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.topic:
                    Intent intent = new Intent(FavouriteFatwaActivity.this, TopicsActivity.class);
                    intent.putExtra(Constants.TOPIC,1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.fav:
                    startActivity(new Intent(FavouriteFatwaActivity.this, FavouriteFatwaActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
            }
            return  true;
        }
    };

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
    }

    BottomNavigationView navigation;
    RecyclerView fatwaRecycler;
    Handler handler;
    FatwaListAdapter fatwaListAdapter;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_fav_fatwa);
        Constants.SEARCHTEXT = "-1";

        progressBar = findViewById(R.id.progressBarfav);
        //navigation.setSelectedItemId(R.id.navigation_home);
        fatwaRecycler = findViewById(R.id.updateslistrecycler);
        LinearLayoutManager linearLayoutManager  =new LinearLayoutManager(getApplicationContext());
        fatwaRecycler.setLayoutManager(linearLayoutManager);
        handler = new Handler();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //navigation.setSelectedItemId(R.id.navigation_home);


        //TODO navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == R.id.firstpg) {
                    Intent intent = new Intent(FavouriteFatwaActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.kutub) {
                    Intent intent = new Intent(FavouriteFatwaActivity.this, KutubActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.qs) {
                    Intent intent = new Intent(FavouriteFatwaActivity.this, QuestinActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.latest) {
                    Intent intent = null;
                    intent = new Intent(FavouriteFatwaActivity.this, Fatwa_ListActivity_Networking.class);
                    intent.putExtra(Constants.TITLE, "تازہ ترین جوابات");
                    intent.putExtra(Constants.LATEST,true);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.subs) {
                    Intent intent = new Intent(FavouriteFatwaActivity.this, Subscribe.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.other) {
                    Intent intent = new Intent(FavouriteFatwaActivity.this, OtherProjects.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.favo) {
                    Intent intent = new Intent(FavouriteFatwaActivity.this, FavouriteFatwaActivity.class);
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


    }
    public void onBack(View v)
    {
        onBackPressed();
    }

    ProgressDialog progressDialog;
    public void fatwaSearch(final String query)
    {
        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                fatwaListAdapter = new FatwaListAdapter(getApplicationContext(), FavouriteFatwaActivity.this);
                fatwaRecycler.setAdapter(fatwaListAdapter);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                searchForFavourite(query);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressBar.setVisibility(View.GONE);
            }
        }.execute();
    }

    public void searchForFavourite(String query)
    {
        FavouriteDBHelper favouriteDBHelper = new FavouriteDBHelper(getApplicationContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = favouriteDBHelper.getReadableDatabase();
        Cursor cursor1 = db.rawQuery(query,null);
        Log.d("trySearchResult",cursor1.getCount()+"   "+query);
        for(int i =0;i<cursor1.getCount();i++)
        {
            cursor1.moveToNext();
            FatwaDataModel model  = new FatwaDataModel();
            model.setId(cursor1.getInt(cursor1.getColumnIndex(DBHelper.FavouriteEntry.FATWA_ID)));
            model.setFatwa_no(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.FatwaEntry.FATWA_NO)));
            model.setView_count(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.FatwaEntry.VIEW_COUNT)));
            model.setQuestion(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.FatwaEntry.QUESTION)));
            model.setAnswer(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.FatwaEntry.ANSWER)));
            model.setCreation_date(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.FatwaEntry.CREATION_DATE)));
            //TODO updating recyclerview
            final FatwaDataModel finalModel = model;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    fatwaListAdapter.addFatwa(finalModel);
                    fatwaListAdapter.notifyDataSetChanged();
                }
            });
         //   Log.d("trySesultsHadees", cursor1.getString(0)+"  "+cursor1.getString(1)+"  "+cursor1.getInt(2));
        }
        favouriteDBHelper.close();
    }

    public void searchForFatwa(String query)
    {
        JildDBHelper jildDBHelper = new JildDBHelper(getApplicationContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = jildDBHelper.getReadableDatabase();
        Cursor cursor1 = db.rawQuery(query,null);
        Log.d("trySearchResult",cursor1.getCount()+"   "+query);
        for(int i =0;i<cursor1.getCount();i++)
        {
            cursor1.moveToNext();
            FatwaDataModel model  = new FatwaDataModel();
            model.setId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.FatwaEntry.ID)));
            model.setFatwa_no(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.FatwaEntry.FATWA_NO)));
            model.setQuestion(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.FatwaEntry.QUESTION)));
            model.setAnswer(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.FatwaEntry.ANSWER)));
            model.setIs_important(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.FatwaEntry.IS_IMPORTANT)));
       //     model.setCreation_date(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.FatwaEntry.CREATION_DATE)));
            model.setIs_miscellaneous(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.FatwaEntry.IS_MISCELLANEOUS)));
            //TODO updating recyclerview
            final FatwaDataModel finalModel = model;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    fatwaListAdapter.addFatwa(finalModel);
                    fatwaListAdapter.notifyDataSetChanged();
                }
            });
            Log.d("trySesultsHadees", cursor1.getString(0)+"  "+cursor1.getString(1)+"  "+cursor1.getInt(2));
        }
        jildDBHelper.close();
    }



    @Override
    protected void onResume() {
        super.onResume();
        String query= null;
        query = "SELECT * from " +
                DBHelper.FavouriteEntry.TABLE_NAME;

        fatwaListAdapter = null;
        fatwaSearch(query);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
