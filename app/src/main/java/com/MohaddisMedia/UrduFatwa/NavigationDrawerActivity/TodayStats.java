package com.MohaddisMedia.UrduFatwa.NavigationDrawerActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.MohaddisMedia.UrduFatwa.Constants;
import com.MohaddisMedia.UrduFatwa.FavouriteFatwaActivity;
import com.MohaddisMedia.UrduFatwa.KutubActivity;
import com.MohaddisMedia.UrduFatwa.KututbNetworking.Fatwa_ListActivity_Networking;
import com.MohaddisMedia.UrduFatwa.MainActivity;
import com.MohaddisMedia.UrduFatwa.Question.QuestinActivity;
import com.MohaddisMedia.UrduFatwa.R;
import com.MohaddisMedia.UrduFatwa.Topics.TopicsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TodayStats extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch ( item.getItemId()) {
                case R.id.fist_page:
                    startActivity(new Intent(TodayStats.this, MainActivity.class));
                    break;
                case R.id.kutub:
                    startActivity(new Intent(TodayStats.this, KutubActivity.class));
                    break;
                case R.id.question:
                    startActivity(new Intent(TodayStats.this, QuestinActivity.class));
                    break;
                case R.id.topic:
                    Intent intent = new Intent(TodayStats.this, TopicsActivity.class);
                    intent.putExtra(Constants.TOPIC,0);
                    startActivity(intent);
                    break;
                case R.id.fav:
                    startActivity(new Intent(TodayStats.this, FavouriteFatwaActivity.class));
                    break;
            }
            return  true;
        };
    };
    TextView today,week,month,total;
    Handler handler;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_todays_hadees);
        progressBar = findViewById(R.id.progressBar8);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        handler = new Handler();
        //navigation.setSelectedItemId(R.id.navigation_home);
        Toast.makeText(this, "loading ...", Toast.LENGTH_SHORT).show();

        //TODO layout elements
        today = findViewById(R.id.textView10);
        week = findViewById(R.id.textView15);
        month = findViewById(R.id.textView17);
        total = findViewById(R.id.textView16);


        //TODO navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == R.id.firstpg) {
                    Intent intent = new Intent(TodayStats.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.kutub) {
                    Intent intent = new Intent(TodayStats.this, KutubActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.qs) {
                    Intent intent = new Intent(TodayStats.this, QuestinActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.latest) {
                    Intent intent = null;
                    intent = new Intent(TodayStats.this, Fatwa_ListActivity_Networking.class);
                    intent.putExtra(Constants.TITLE, "تازہ ترین جوابات");
                    intent.putExtra(Constants.LATEST,true);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.subs) {
                    Intent intent = new Intent(TodayStats.this, Subscribe.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.other) {
                    Intent intent = new Intent(TodayStats.this, OtherProjects.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.favo) {
                    Intent intent = new Intent(TodayStats.this, FavouriteFatwaActivity.class);
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
                if(hasNetwork){
                    loadStats();
                }else{
                    showDialogue();
                }
            }
        }.execute();
    }

    Activity activity = TodayStats.this;
    public void showDialogue()
    {
        final Dialog dialog = new Dialog(TodayStats.this);
        dialog.setContentView(R.layout.no_internet_dialogue);;
        TextView retry = dialog.findViewById(R.id.textView21);
        dialog.setCanceledOnTouchOutside(false);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable() && isOnline())
                {
                    progressBar.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                    loadStats();
                }else{
                    Toast.makeText(activity, "No Connection...", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();
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

    public void loadStats()
    {
        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                HttpURLConnection connection = null;
                BufferedReader reader = null;

                try {
                    URL url = new URL("https://urdufatwa.com/api/visitors");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();


                    InputStream stream = connection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();
                    String line = "";

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line+"\n");
                        Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                    }

                    String str =  buffer.toString();
                    final JSONObject json = new JSONObject(str);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                today.setText(json.getInt("today")+"");
                                week.setText(json.getInt("week")+"");
                                month.setText(json.getInt("month")+"");
                                total.setText(json.getInt("total")+"");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        }.execute();


    }
    public void onBack(View v)
    {
        onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
