package com.MohaddisMedia.UrduFatwa;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.MohaddisMedia.UrduFatwa.KututbNetworking.Fatwa_ListActivity_Networking;
import com.MohaddisMedia.UrduFatwa.NavigationDrawerActivity.OtherProjects;
import com.MohaddisMedia.UrduFatwa.NavigationDrawerActivity.Subscribe;
import com.MohaddisMedia.UrduFatwa.NavigationDrawerActivity.TodayStats;
import com.MohaddisMedia.UrduFatwa.Question.QuestinActivity;
import com.MohaddisMedia.UrduFatwa.Topics.TopicsActivity;

public class MainActivity extends AppCompatActivity {



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch ( item.getItemId())
            {
                case R.id.fist_page:
                    //startActivity(new Intent(MainActivity.this, MainActivity.class));
                    break;
                case R.id.kutub:
                    startActivity(new Intent(MainActivity.this, KutubActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.question:
                    startActivity(new Intent(MainActivity.this, QuestinActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.topic:
                    Intent intent = new Intent(MainActivity.this, TopicsActivity.class);
                    intent.putExtra(Constants.TOPIC,0);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.fav:
                    startActivity(new Intent(MainActivity.this, FavouriteFatwaActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
    }
    BottomNavigationView navigation;
    EditText searchBar;
    ImageView search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_main);
        setTitle("First Page");
        final CardView searchView = findViewById(R.id.cardView2);
        searchBar = findViewById(R.id.editText2);
        search = findViewById(R.id.imageView41);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!searchBar.getText().toString().equals("")){
//                    Intent intent = new Intent(MainActivity.this, FatwaSearchActivity.class);
//                    intent.putExtra(Constants.SEARCH, searchBar.getText().toString());
//                    startActivity(intent);
                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,(View)searchView,"searchbartrans");
                    Intent intent = new Intent(MainActivity.this, FatwaSearchActivity.class);
                    intent.putExtra(Constants.SEARCH, searchBar.getText().toString());
                    startActivity(intent,activityOptionsCompat.toBundle());
                }else{
                    searchBar.setError("لکھیں");;
                }
            }
        });

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //navigation.setSelectedItemId(R.id.navigation_home);

        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);


        //TODO navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == R.id.firstpg) {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

                } else if (id == R.id.kutub) {
                    Intent intent = new Intent(MainActivity.this, Subscribe.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.qs) {
                    Intent intent = new Intent(MainActivity.this, QuestinActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.latest) {
                    Intent intent = null;
                    intent = new Intent(MainActivity.this, Fatwa_ListActivity_Networking.class);
                    intent.putExtra(Constants.TITLE, "تازہ ترین جوابات");
                    intent.putExtra(Constants.LATEST,true);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                }  else if (id == R.id.subs) {
                    Intent intent = new Intent(MainActivity.this, Subscribe.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.other) {
                    Intent intent = new Intent(MainActivity.this, OtherProjects.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.favo) {
                    Intent intent = new Intent(MainActivity.this, FavouriteFatwaActivity.class);
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


        //TODO cards code for home screen
        CardView importantFatwa = findViewById(R.id.important_fatwa);
        importantFatwa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(MainActivity.this, Fatwa_ListActivity_Networking.class);
                intent.putExtra(Constants.TITLE, "اہم فتاویٰ");
                intent.putExtra(Constants.IMPORTANT,true);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        CardView updates = findViewById(R.id.cardViewmiscell);
        updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(MainActivity.this, Fatwa_ListActivity_Networking.class);
                intent.putExtra(Constants.TITLE, "تازہ ترین جوابات");
                intent.putExtra(Constants.LATEST,true);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        final CardView numberingFatwa = findViewById(R.id.cardView4);
        numberingFatwa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,(View)numberingFatwa,"searchbartrans");
                Intent intent = new Intent(MainActivity.this, TodayStats.class);
                intent.putExtra(Constants.SEARCH, searchBar.getText().toString());
                startActivity(intent,activityOptionsCompat.toBundle());
//                Intent intent = null;
//                intent = new Intent(MainActivity.this, TodayStats.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        CardView miscellFatwa = findViewById(R.id.cardView);
        miscellFatwa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(MainActivity.this, Fatwa_ListActivity_Networking.class);
                intent.putExtra(Constants.MISCELLANEOUS,true);
                intent.putExtra(Constants.TITLE, "متفرق فتاویٰ");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        System.exit(0);
    }

    public void onBack(View v)
    {
        onBackPressed();
    }

}
