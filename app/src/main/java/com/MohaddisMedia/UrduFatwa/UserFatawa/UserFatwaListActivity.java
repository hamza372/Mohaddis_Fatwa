package com.MohaddisMedia.UrduFatwa.UserFatawa;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.MohaddisMedia.UrduFatwa.CategoryNetworking.ItemViewModelCategory;
import com.MohaddisMedia.UrduFatwa.Constants;
import com.MohaddisMedia.UrduFatwa.DataModels.FatwaDataModel;
import com.MohaddisMedia.UrduFatwa.FavouriteFatwaActivity;
import com.MohaddisMedia.UrduFatwa.ImportantFatwaNetworking.ItemViewModelImportant;
import com.MohaddisMedia.UrduFatwa.JildActivity;
import com.MohaddisMedia.UrduFatwa.KutubActivity;
import com.MohaddisMedia.UrduFatwa.KututbNetworking.Fatwa_ListActivity_Networking;
import com.MohaddisMedia.UrduFatwa.KututbNetworking.ItemAdapter;

import com.MohaddisMedia.UrduFatwa.MainActivity;
import com.MohaddisMedia.UrduFatwa.NavigationDrawerActivity.OtherProjects;
import com.MohaddisMedia.UrduFatwa.NavigationDrawerActivity.Subscribe;
import com.MohaddisMedia.UrduFatwa.NavigationDrawerActivity.Updates;
import com.MohaddisMedia.UrduFatwa.Question.QuestinActivity;
import com.MohaddisMedia.UrduFatwa.R;
import com.MohaddisMedia.UrduFatwa.Topics.TopicsActivity;

public class UserFatwaListActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch ( item.getItemId())
            {
                case R.id.fist_page:
                    startActivity(new Intent(UserFatwaListActivity.this, MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.kutub:
                    startActivity(new Intent(UserFatwaListActivity.this, KutubActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.question:
                    startActivity(new Intent(UserFatwaListActivity.this, QuestinActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.topic:
                    Intent intent = new Intent(UserFatwaListActivity.this, TopicsActivity.class);
                    intent.putExtra(Constants.TOPIC,0);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.fav:
                    startActivity(new Intent(UserFatwaListActivity.this, FavouriteFatwaActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
            }
            return true;
        }
    };
    RecyclerView recyclerView;
    String type;
    boolean first = true;
    ProgressBar proBar6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_fatwa_list_networking);
        Constants.SEARCHTEXT = "-1";

        type = getIntent().getExtras().getString(Constants.Type);

        TextView titleTV = findViewById(R.id.topictvid);
        titleTV.setText(getIntent().getExtras().getString(Constants.TITLE));

        //setting up recyclerview
        recyclerView = findViewById(R.id.fatwalistrecycler);
        final LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        proBar6 = findViewById(R.id.progressBar6);


        final Bundle b = getIntent().getExtras();
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
                    loadingData(b);
                }else{
                    recyclerView.setVisibility(View.GONE);
                    proBar6.setVisibility(View.GONE);
                    showDialogue(b);
                }
            }
        }.execute();


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                if(first)
                {
                    proBar6.setVisibility(View.GONE);
                    first = false;
                }
                // total number of items in the data set held by the adapter
                int totalItemCount = linearLayoutManager.getItemCount();
                //adapter position of the first visible view.
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                Log.d("tryRecyclerAnalysis",totalItemCount+"    "+lastVisibleItem);

                if(Constants.currentPage == Constants.lastPage)
                {
                    progressBar.setVisibility(View.GONE);
                }
                else if(totalItemCount - lastVisibleItem == 1)
                {
                    progressBar.setVisibility(View.VISIBLE);
                }else{
                    progressBar.setVisibility(View.GONE);
                }


            }
        });

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
                    Intent intent = new Intent(UserFatwaListActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.kutub) {
                    Intent intent = new Intent(UserFatwaListActivity.this, KutubActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.qs) {
                    Intent intent = new Intent(UserFatwaListActivity.this, QuestinActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.latest) {
                    Intent intent = null;
                    intent = new Intent(UserFatwaListActivity.this, Fatwa_ListActivity_Networking.class);
                    intent.putExtra(Constants.TITLE, "تازہ ترین جوابات");
                    intent.putExtra(Constants.LATEST,true);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                }  else if (id == R.id.subs) {
                    Intent intent = new Intent(UserFatwaListActivity.this, Subscribe.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.other) {
                    Intent intent = new Intent(UserFatwaListActivity.this, OtherProjects.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.favo) {
                    Intent intent = new Intent(UserFatwaListActivity.this, FavouriteFatwaActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setStatusBarBackgroundColor(Color.BLACK);

        final ImageView layout = findViewById(R.id.imageView2);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
    }

    Activity activity = UserFatwaListActivity.this;
    public void showDialogue(final Bundle b)
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
                    recyclerView.setVisibility(View.VISIBLE);
                    proBar6.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                    loadingData(b);
                }else{
                    Toast.makeText(UserFatwaListActivity.this, "No internet", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();
    }

    public void loadingData(Bundle b)
    {
        if(b.getInt(Constants.KUTUB,-1) != -1) {
            Constants.BookNo = b.getInt(Constants.KUTUB, 1);

            //getting our ItemViewModel
            ItemViewModel itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

            //creating the Adapter
            final ItemAdapter adapter = new ItemAdapter(this,UserFatwaListActivity.this);

            //observing the itemPagedList from view model
            itemViewModel.itemPagedList.observe(this, new Observer<PagedList<FatwaDataModel>>() {
                @Override
                public void onChanged(@Nullable PagedList<FatwaDataModel> items) {
                    adapter.submitList(items);
                }
            });

            //setting the adapter
            recyclerView.setAdapter(adapter);
        }else if(b.getInt(Constants.TOPIC,-1) != -1)
        {
            Constants.CategoryId = b.getInt(Constants.TOPIC, 1);


            //getting our ItemViewModel
            ItemViewModelCategory itemViewModel = ViewModelProviders.of(this).get(ItemViewModelCategory.class);

            //creating the Adapter
            final ItemAdapter adapter = new ItemAdapter(this,UserFatwaListActivity.this);


            //observing the itemPagedList from view model
            itemViewModel.itemPagedList.observe(this, new Observer<PagedList<FatwaDataModel>>() {
                @Override
                public void onChanged(@Nullable PagedList<FatwaDataModel> items) {

                    //in case of any changes
                    //submitting the items to adapter
                    adapter.submitList(items);
                }
            });

            //setting the adapter
            recyclerView.setAdapter(adapter);
        }else if(b.getBoolean(Constants.IMPORTANT,false) == true)
        {
            Constants.FatwaType = Constants.IMPORTANTTYPE;


            //getting our ItemViewModel
            ItemViewModelImportant itemViewModel = ViewModelProviders.of(this).get(ItemViewModelImportant.class);

            //creating the Adapter
            final ItemAdapter adapter = new ItemAdapter(this,UserFatwaListActivity.this);

            //observing the itemPagedList from view model
            itemViewModel.itemPagedList.observe(this, new Observer<PagedList<FatwaDataModel>>() {
                @Override
                public void onChanged(@Nullable PagedList<FatwaDataModel> items) {

                    //in case of any changes
                    //submitting the items to adapter
                    adapter.submitList(items);
                }
            });

            //setting the adapter
            recyclerView.setAdapter(adapter);
        }else if(b.getBoolean(Constants.MISCELLANEOUS,false) == true)
        {
            Constants.FatwaType = Constants.MISCELLTYPE;


            //getting our ItemViewModel
            ItemViewModelImportant itemViewModel = ViewModelProviders.of(this).get(ItemViewModelImportant.class);

            //creating the Adapter
            final ItemAdapter adapter = new ItemAdapter(this,UserFatwaListActivity.this);

            //observing the itemPagedList from view model
            itemViewModel.itemPagedList.observe(this, new Observer<PagedList<FatwaDataModel>>() {
                @Override
                public void onChanged(@Nullable PagedList<FatwaDataModel> items) {

                    //in case of any changes
                    //submitting the items to adapter
                    adapter.submitList(items);
                }
            });

            //setting the adapter
            recyclerView.setAdapter(adapter);
        }else if(b.getBoolean(Constants.LATEST,false) == true)
        {
            Constants.FatwaType = Constants.LATESTTYPE;


            //getting our ItemViewModel
            ItemViewModelImportant itemViewModel = ViewModelProviders.of(this).get(ItemViewModelImportant.class);

            //creating the Adapter
            final ItemAdapter adapter = new ItemAdapter(this,UserFatwaListActivity.this);

            //observing the itemPagedList from view model
            itemViewModel.itemPagedList.observe(this, new Observer<PagedList<FatwaDataModel>>() {
                @Override
                public void onChanged(@Nullable PagedList<FatwaDataModel> items) {

                    //in case of any changes
                    //submitting the items to adapter
                    adapter.submitList(items);
                }
            });

            //setting the adapter
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void onStop() {

        super.onStop();
        Constants.currentPage = 0;
        Constants.lastPage = 100;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constants.currentPage = 0;
        Constants.lastPage = 100;
    }

    public void onBack(View v)
    {
        onBackPressed();
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
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}

