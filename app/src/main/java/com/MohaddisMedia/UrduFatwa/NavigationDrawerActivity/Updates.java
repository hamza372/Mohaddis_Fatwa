package com.MohaddisMedia.UrduFatwa.NavigationDrawerActivity;

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
import android.widget.TextView;

import com.MohaddisMedia.UrduFatwa.Adapters.FatwaListAdapter;
import com.MohaddisMedia.UrduFatwa.Constants;
import com.MohaddisMedia.UrduFatwa.DB.JildDBHelper;
import com.MohaddisMedia.UrduFatwa.DB.KutubDBHelper;
import com.MohaddisMedia.UrduFatwa.DB.Question_Links_DBHelper;
import com.MohaddisMedia.UrduFatwa.DBHelper;
import com.MohaddisMedia.UrduFatwa.DataModels.FatwaDataModel;
import com.MohaddisMedia.UrduFatwa.FavouriteFatwaActivity;
import com.MohaddisMedia.UrduFatwa.KutubActivity;
import com.MohaddisMedia.UrduFatwa.MainActivity;
import com.MohaddisMedia.UrduFatwa.Question.QuestinActivity;
import com.MohaddisMedia.UrduFatwa.R;
import com.MohaddisMedia.UrduFatwa.Topics.TopicsActivity;

public class Updates extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch ( item.getItemId()) {
                case R.id.fist_page:
                    startActivity(new Intent(Updates.this, MainActivity.class));
                    break;
                case R.id.kutub:
                    startActivity(new Intent(Updates.this, KutubActivity.class));
                    break;
                case R.id.question:
                    startActivity(new Intent(Updates.this, QuestinActivity.class));
                    break;
                case R.id.topic:
                    Intent intent = new Intent(Updates.this, TopicsActivity.class);
                    intent.putExtra(Constants.TOPIC,0);
                    startActivity(intent);
                    break;
                case R.id.fav:
                    startActivity(new Intent(Updates.this, FavouriteFatwaActivity.class));
                    break;
            }
            return  true;
        };
    };

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
    }
    BottomNavigationView navigation;
    RecyclerView fatwaRecycler;
    Handler handler;
    FatwaListAdapter fatwaListAdapter;
    TextView title;
    ProgressBar progressBar;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_updates);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        progressBar = findViewById(R.id.progressBartop2);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //navigation.setSelectedItemId(R.id.navigation_home);
        fatwaRecycler = findViewById(R.id.updateslistrecycler);
        LinearLayoutManager linearLayoutManager  =new LinearLayoutManager(getApplicationContext());
        fatwaRecycler.setLayoutManager(linearLayoutManager);
        handler = new Handler();

        //TODO navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.firstpg) {
                    Intent intent = new Intent(Updates.this, MainActivity.class);
                    startActivity(intent);

                } else if (id == R.id.kutub) {
                    Intent intent = new Intent(Updates.this, KutubActivity.class);
                    startActivity(intent);
                } else if (id == R.id.qs) {
                    Intent intent = new Intent(Updates.this, QuestinActivity.class);
                    startActivity(intent);
                } else if (id == R.id.latest) {
                    Intent intent = new Intent(Updates.this, Updates.class);
                    startActivity(intent);
                }   else if (id == R.id.subs) {
                    Intent intent = new Intent(Updates.this, Subscribe.class);
                    startActivity(intent);
                } else if (id == R.id.other) {
                    Intent intent = new Intent(Updates.this, OtherProjects.class);
                    startActivity(intent);
                } else if (id == R.id.favo) {
                    Intent intent = new Intent(Updates.this, FavouriteFatwaActivity.class);
                    startActivity(intent);
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

        String query= null;
        query = "SELECT * from " +
                DBHelper.FatwaEntry.TABLE_NAME
                + " ORDER BY date("+ DBHelper.FatwaEntry.CREATION_DATE+ ") DESC LIMIT 500";

        fatwaListAdapter = null;
        fatwaSearch(query);

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
                fatwaListAdapter = new FatwaListAdapter(getApplicationContext(), Updates.this);
                fatwaRecycler.setAdapter(fatwaListAdapter);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                searchForFatwa(query);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressBar.setVisibility(View.GONE);
            }
        }.execute();
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
            model  = fetchFatwaBackground(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.FatwaEntry.ID)));
            model.setId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.FatwaEntry.ID)));
            model.setFatwa_no(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.FatwaEntry.FATWA_NO)));
            model.setView_count(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.FatwaEntry.VIEW_COUNT)));
            model.setQuestion(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.FatwaEntry.QUESTION)));
            model.setAnswer(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.FatwaEntry.ANSWER)));
            model.setIs_important(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.FatwaEntry.IS_IMPORTANT)));
         //   model.setCreation_date(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.FatwaEntry.CREATION_DATE)));
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

    public FatwaDataModel fetchFatwaBackground(int fatwaId) {
        String query = "SELECT * FROM " + DBHelper.QuestionLink.TABLE_NAME + " WHERE " + DBHelper.QuestionLink.QUESTION_ID + " == " + fatwaId;
        Question_Links_DBHelper question_links_dbHelper = new Question_Links_DBHelper(getApplicationContext());
        FatwaDataModel fatwaDataModel2 = new FatwaDataModel();
        // Gets the data repository in write mode
        SQLiteDatabase db = question_links_dbHelper.getReadableDatabase();
        Cursor cursor1 = db.rawQuery(query, null);
        Log.d("trySearchResult", cursor1.getCount() + "   " + query);
        for (int i = 0; i < cursor1.getCount(); i++) {
            cursor1.moveToNext();
            fatwaDataModel2.setBook_id(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.QuestionLink.BOOK_ID)));
            fatwaDataModel2.setJild_Id(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.QuestionLink.CHAPTER_ID)));
            String query2 = null;

            if (fatwaDataModel2.getJild_Id() != 0) {
                query2 = "SELECT * FROM " + DBHelper.BabEntry.TABLE_NAME + " WHERE " + DBHelper.BabEntry.ID + " == " + fatwaDataModel2.getJild_Id();
                JildDBHelper dbHelper = new JildDBHelper(getApplicationContext());
                SQLiteDatabase db2 = dbHelper.getReadableDatabase();
                Cursor cursor2 = db2.rawQuery(query2, null);
                if (cursor2.getCount() > 0) {
                    cursor2.moveToNext();
                    fatwaDataModel2.setJild_Title(cursor2.getString(cursor2.getColumnIndexOrThrow(DBHelper.BabEntry.JILD_TITLE)));
                    fatwaDataModel2.setBook_id(cursor2.getInt(cursor2.getColumnIndexOrThrow(DBHelper.BabEntry.BOOK_ID)));
                    dbHelper.close();

                    KutubDBHelper kutubDBHelper = new KutubDBHelper(getApplicationContext());
                    SQLiteDatabase db3 = kutubDBHelper.getReadableDatabase();
                    String query3 = "SELECT * FROM " + DBHelper.KutubEntry.TABLE_NAME + " WHERE " + DBHelper.KutubEntry.ID + "==" + fatwaDataModel2.getBook_id();
                    Cursor cursor3 = db3.rawQuery(query3, null);
                    if (cursor3.getCount() > 0) {
                        cursor3.moveToNext();
                        fatwaDataModel2.setKitab_title(cursor3.getString(cursor3.getColumnIndexOrThrow(DBHelper.KutubEntry.BOOK_TITLE)));
                        kutubDBHelper.close();
                    }
                }
            } else if (fatwaDataModel2.getBook_id() != 0) {
                KutubDBHelper kutubDBHelper = new KutubDBHelper(getApplicationContext());
                SQLiteDatabase db3 = kutubDBHelper.getReadableDatabase();
                String query3 = "SELECT * FROM " + DBHelper.KutubEntry.TABLE_NAME + " WHERE " + DBHelper.KutubEntry.ID + "==" + fatwaDataModel2.getBook_id();
                Cursor cursor3 = db3.rawQuery(query3, null);
                if (cursor3.getCount() > 0) {
                    cursor3.moveToNext();
                    fatwaDataModel2.setJild_Id(0);
                    fatwaDataModel2.setKitab_title(cursor3.getString(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.BOOK_TITLE)));
                    kutubDBHelper.close();
                }
            }
        }
        return fatwaDataModel2;
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
