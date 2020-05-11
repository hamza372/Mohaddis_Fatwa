package com.MohaddisMedia.UrduFatwa;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.MohaddisMedia.UrduFatwa.Adapters.JildAdapter;
import com.MohaddisMedia.UrduFatwa.DB.JildDBHelper;

import com.MohaddisMedia.UrduFatwa.DataModels.BabDataModel;
import com.MohaddisMedia.UrduFatwa.DataModels.JildDataModel;
import com.MohaddisMedia.UrduFatwa.DataModels.KutubDataModel;
import com.MohaddisMedia.UrduFatwa.KututbNetworking.Fatwa_ListActivity_Networking;
import com.MohaddisMedia.UrduFatwa.NavigationDrawerActivity.OtherProjects;
import com.MohaddisMedia.UrduFatwa.NavigationDrawerActivity.Subscribe;
import com.MohaddisMedia.UrduFatwa.Question.QuestinActivity;
import com.MohaddisMedia.UrduFatwa.Topics.TopicsActivity;

public class JildActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch ( item.getItemId())
            {
                case R.id.fist_page:
                    startActivity(new Intent(JildActivity.this, MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.kutub:
                    startActivity(new Intent(JildActivity.this, KutubActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.question:
                    startActivity(new Intent(JildActivity.this, QuestinActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.topic:
                    Intent intent = new Intent(JildActivity.this, TopicsActivity.class);
                    intent.putExtra(Constants.TOPIC,0);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.fav:
                    startActivity(new Intent(JildActivity.this, FavouriteFatwaActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
            }
            return true;
        }
    };
    RecyclerView jildRecycler;
    Handler handler;
    KutubDataModel kutubDataModel;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_jild_activity);
        progressBar = findViewById(R.id.progressBar3);

        kutubDataModel = (KutubDataModel) getIntent().getExtras().get(Constants.KUTUB);
        jildRecycler = findViewById(R.id.jildrecycler);
        LinearLayoutManager linearLayoutManager  =new LinearLayoutManager(getApplicationContext());
        jildRecycler.setLayoutManager(linearLayoutManager);
        handler = new Handler();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //navigation.setSelectedItemId(R.id.navigation_home);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        //TODO navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == R.id.firstpg) {
                    Intent intent = new Intent(JildActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.kutub) {
                    Intent intent = new Intent(JildActivity.this, KutubActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.qs) {
                    Intent intent = new Intent(JildActivity.this, QuestinActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.latest) {
                    Intent intent = null;
                    intent = new Intent(JildActivity.this, Fatwa_ListActivity_Networking.class);
                    intent.putExtra(Constants.TITLE, "تازہ ترین جوابات");
                    intent.putExtra(Constants.LATEST,true);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.subs) {
                    Intent intent = new Intent(JildActivity.this, Subscribe.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.other) {
                    Intent intent = new Intent(JildActivity.this, OtherProjects.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.favo) {
                    Intent intent = new Intent(JildActivity.this, FavouriteFatwaActivity.class);
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
        TextView title = findViewById(R.id.topictvid);
        title.setText(kutubDataModel.getKitab_title());

        final String query= "SELECT * from " +
                DBHelper.KutubEntry.TABLE_NAME
                +" where "+ DBHelper.KutubEntry.PARENT_ID+" == "+kutubDataModel.getKitab_Id();
        jildAdapter = null;


        final LinearLayout reloadLayout = findViewById(R.id.reloadlayout);
        new AsyncTask<Void,Void,Void>(){
            boolean hasNetwork;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                hasNetwork = false;
            }

            @Override
            protected Void doInBackground(Void... voids) {
                hasNetwork = (isOnline() && isNetworkAvailable());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(hasNetwork) {
                    jildSearch(query);
                }else{
                    jildRecycler.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    showDialogue(query);
                }
            }
        }.execute();


    }


    Activity activity = JildActivity.this;
    public void showDialogue(final String query)
    {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.no_internet_dialogue);;
        TextView retry = dialog.findViewById(R.id.textView21);
        dialog.setCanceledOnTouchOutside(false);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable() && isOnline())
                {
                    jildRecycler.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                    jildSearch(query);
                }else{
                    Toast.makeText(JildActivity.this, "No internet", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();
    }

    public void onBack(View v)
    {
        onBackPressed();
    }

    BabDataModel jildDataModel;
    ProgressDialog progressDialog;
    JildAdapter jildAdapter;
    public void jildSearch(final String query)
    {
        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                jildAdapter = new JildAdapter(getApplicationContext(),JildActivity.this);
                jildRecycler.setAdapter(jildAdapter);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... voids) {

                searchInJilds(query);
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

    public void searchInJilds(String query)
    {

        JildDBHelper jildDBHelper = new JildDBHelper(getApplicationContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = jildDBHelper.getReadableDatabase();
        Cursor cursor1 = db.rawQuery(query,null);
        Log.d("trySearchResult",cursor1.getCount()+"   "+query);
        for(int i =0;i<cursor1.getCount();i++)
        {
            cursor1.moveToNext();
            final JildDataModel model = new JildDataModel();
            model.setKitab_Id(kutubDataModel.getKitab_Id());
            model.setKitab_title(kutubDataModel.getKitab_title());
            model.setJild_Id(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.ID)));
            model.setJildparent_id(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.PARENT_ID)));
            model.setHasParts(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.HAS_PARTS)));
            model.setNo_of_parts(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.NO_OF_PARTS)));
            model.setQuestion_count(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.QUESTION_COUNT)));
            model.setJild_title(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.BOOK_TITLE)));
            model.setBookImage(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.BOOK_IMAGE)));
            //TODO updating recyclerview
            handler.post(new Runnable() {
                @Override
                public void run() {
                    jildAdapter.addKitab(model);
                    jildAdapter.notifyDataSetChanged();
                }
            });
            Log.d("trySesultsHadees", cursor1.getString(0)+"  "+cursor1.getString(1)+"  "+cursor1.getInt(2));
        }
        jildDBHelper.close();
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
    public Boolean isOnline() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
