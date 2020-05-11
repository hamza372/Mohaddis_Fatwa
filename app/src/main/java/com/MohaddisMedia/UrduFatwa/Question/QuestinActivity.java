package com.MohaddisMedia.UrduFatwa.Question;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.MohaddisMedia.UrduFatwa.Constants;
import com.MohaddisMedia.UrduFatwa.FatwaViewActivity;
import com.MohaddisMedia.UrduFatwa.FavouriteFatwaActivity;
import com.MohaddisMedia.UrduFatwa.KutubActivity;
import com.MohaddisMedia.UrduFatwa.KututbNetworking.Fatwa_ListActivity_Networking;
import com.MohaddisMedia.UrduFatwa.MainActivity;
import com.MohaddisMedia.UrduFatwa.NavigationDrawerActivity.OtherProjects;
import com.MohaddisMedia.UrduFatwa.NavigationDrawerActivity.Subscribe;
import com.MohaddisMedia.UrduFatwa.R;
import com.MohaddisMedia.UrduFatwa.Topics.TopicsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class QuestinActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch ( item.getItemId())
            {
                case R.id.fist_page:
                    startActivity(new Intent(QuestinActivity.this, MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.kutub:
                    startActivity(new Intent(QuestinActivity.this, KutubActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.question:
                    startActivity(new Intent(QuestinActivity.this, QuestinActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.topic:
                    Intent intent = new Intent(QuestinActivity.this, TopicsActivity.class);
                    intent.putExtra(Constants.TOPIC,0);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;
                case R.id.fav:
                    startActivity(new Intent(QuestinActivity.this, FavouriteFatwaActivity.class));
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
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
    }
    BottomNavigationView navigation;
    ProgressBar progressBar;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_question);
        handler = new Handler();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        progressBar = findViewById(R.id.progressBarQ);


        //navigation.setSelectedItemId(R.id.navigation_home);


        //TODO navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == R.id.firstpg) {
                    Intent intent = new Intent(QuestinActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.kutub) {
                    Intent intent = new Intent(QuestinActivity.this, KutubActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.qs) {
                    Intent intent = new Intent(QuestinActivity.this, QuestinActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.latest) {
                    Intent intent = null;
                    intent = new Intent(QuestinActivity.this, Fatwa_ListActivity_Networking.class);
                    intent.putExtra(Constants.TITLE, "تازہ ترین جوابات");
                    intent.putExtra(Constants.LATEST,true);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.subs) {
                    Intent intent = new Intent(QuestinActivity.this, Subscribe.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.other) {
                    Intent intent = new Intent(QuestinActivity.this, OtherProjects.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (id == R.id.favo) {
                    Intent intent = new Intent(QuestinActivity.this, FavouriteFatwaActivity.class);
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

        final EditText email = (EditText)findViewById(R.id.editText42);
        final EditText name = (EditText)findViewById(R.id.editText22);
        final EditText question = (EditText)findViewById(R.id.editText2);

        Button ask = findViewById(R.id.button);
        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.getText().toString().contains("@"))
                {
                    email.setError("آپ کا ایمیل");
                }else if(name.getText().toString().equals(""))
                {
                    name.setError("آپ کا نام ");
                }else if(question.getText().toString().equals(""))
                {
                    question.setError("اپنا سوال بیان کریں");
                }else{
                    new AsyncTask<Void,Void,Void>()
                    {
                        boolean isAvailable;
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            isAvailable = false;
                            progressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        protected Void doInBackground(Void... voids) {
                            if(isOnline() && isNetworkAvailable()) {
                                makePost(email.getText().toString(), name.getText().toString(), question.getText().toString());
                            }else{
                                //progressBar.setVisibility(View.GONE);
                                isAvailable = true;
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            if(isAvailable){
                                showDialogue(email.getText().toString(), name.getText().toString(), question.getText().toString());
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    }.execute();
                }
            }
        });


    }

    Activity activity = QuestinActivity.this;
    public void showDialogue(final String mail, final String name, final String question)
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
                    progressBar.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            makePost(mail,name,question);
                        }
                    }).start();
                }else{
                    Toast.makeText(getApplicationContext(), "No internet", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();
    }

    private final OkHttpClient client = new OkHttpClient();
    private void makePost(String email,String name,String question){
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("email", email)
                .addFormDataPart("name", name)
                .addFormDataPart("question", question)
                .build();

        Request request = new Request.Builder()
                .url("https://urdufatwa.com/api/ask-question")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
               // Log.d("tryLogLog",responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }

            //Log.d("tryLogLog", response.body().string());
            final JSONObject res= new JSONObject(response.body().string());
            final String res2 = res.getString("success");
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();
                        fn_showAlertDialog(res2);

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void onBack(View v)
    {
        onBackPressed();
    }

    private void fn_showAlertDialog(String message) {
        Dialog dialog = new Dialog(QuestinActivity.this);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialogue_for_post_requests,null);
        TextView textView = v.findViewById(R.id.textView18);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(message, Html.FROM_HTML_MODE_COMPACT));
        } else {
            textView.setText(Html.fromHtml(message));
        }
        dialog.setContentView(v);
        dialog.show();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
