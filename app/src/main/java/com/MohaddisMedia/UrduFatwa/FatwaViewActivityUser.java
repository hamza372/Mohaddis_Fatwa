package com.MohaddisMedia.UrduFatwa;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.MohaddisMedia.UrduFatwa.DB.FavouriteDBHelper;
import com.MohaddisMedia.UrduFatwa.DataModels.FatwaDataModel;
import com.MohaddisMedia.UrduFatwa.KututbNetworking.Fatwa_ListActivity_Networking;
import com.MohaddisMedia.UrduFatwa.NavigationDrawerActivity.OtherProjects;
import com.MohaddisMedia.UrduFatwa.NavigationDrawerActivity.Subscribe;
import com.MohaddisMedia.UrduFatwa.Question.QuestinActivity;
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
import java.util.ArrayList;
import java.util.List;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class FatwaViewActivityUser extends AppCompatActivity{
        private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId()) {
                    case R.id.fist_page:
                        startActivity(new Intent(FatwaViewActivityUser.this, MainActivity.class));
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        break;
                    case R.id.kutub:
                        startActivity(new Intent(FatwaViewActivityUser.this, KutubActivity.class));
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        break;
                    case R.id.question:
                        startActivity(new Intent(FatwaViewActivityUser.this, QuestinActivity.class));
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        break;
                    case R.id.topic:
                        Intent intent = new Intent(FatwaViewActivityUser.this, TopicsActivity.class);
                        intent.putExtra(Constants.TOPIC,0);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        break;
                    case R.id.fav:
                        startActivity(new Intent(FatwaViewActivityUser.this, FavouriteFatwaActivity.class));
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        break;
                }
                return  true;
            };
        };
        FatwaDataModel fatwaDataModel;
        TextView hawaleTV;
        Handler handler;
        TextView questionNo;
        TextView questionDate;
        TextView question;
        TextView questionObs;
        TextView answer;
        ProgressBar progressBar;
        SharedPreferences pref;
        WebView webAnswer;
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.nav_activity_fatwa_view);
            handler = new Handler();
            pref = getSharedPreferences("com.MohaddisMedia.UrduFatwa",MODE_PRIVATE);
            fatwaDataModel = (FatwaDataModel)getIntent().getExtras().get(Constants.FATWA);
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
                        Intent intent = new Intent(FatwaViewActivityUser.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

                    } else if (id == R.id.kutub) {
                        Intent intent = new Intent(FatwaViewActivityUser.this, KutubActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    } else if (id == R.id.qs) {
                        Intent intent = new Intent(FatwaViewActivityUser.this, QuestinActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    } else if (id == R.id.latest) {
                        Intent intent = null;
                        intent = new Intent(FatwaViewActivityUser.this, Fatwa_ListActivity_Networking.class);
                        intent.putExtra(Constants.TITLE, "تازہ ترین جوابات");
                        intent.putExtra(Constants.LATEST,true);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    } else if (id == R.id.subs) {
                        Intent intent = new Intent(FatwaViewActivityUser.this, Subscribe.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    } else if (id == R.id.other) {
                        Intent intent = new Intent(FatwaViewActivityUser.this, OtherProjects.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    } else if (id == R.id.favo) {
                        Intent intent = new Intent(FatwaViewActivityUser.this, FavouriteFatwaActivity.class);
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

            //TODO populate view
            questionNo = findViewById(R.id.textViewqno);
            questionDate = findViewById(R.id.textView8);
            question = findViewById(R.id.tvq);
            questionObs = findViewById(R.id.textViewobs);
            answer = findViewById(R.id.tvans);
            hawaleTV = findViewById(R.id.hawale);
            progressBar = findViewById(R.id.progressBar4);
            progressBar.setVisibility(View.VISIBLE);
            webAnswer = findViewById(R.id.fweb);
            //TODO change
            questionNo.         setTextSize(((int) pref.getFloat(Constants.FONT,answer.getTextSize()) + 150)/10);
            questionObs.        setTextSize(((int) pref.getFloat(Constants.FONT,answer.getTextSize()) + 150)/10);
            questionDate.       setTextSize(((int) pref.getFloat(Constants.FONT,answer.getTextSize()) + 150)/10);
            question.           setTextSize(((int) pref.getFloat(Constants.FONT,answer.getTextSize()) + 150)/10);
            answer.             setTextSize(((int) pref.getFloat(Constants.FONT,answer.getTextSize()) + 150)/10);
            ImageView fontChange = findViewById(R.id.imageView22);
            fontChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showFontDialogue();
                }
            });

            question.setText(fatwaDataModel.getQuestion().equals("null")?"":fatwaDataModel.getQuestion());
            questionObs.setText( "مشاہدات: "+fatwaDataModel.getView_count());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                answer.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            }

            Log.d("tryFatwaDate","a = "+fatwaDataModel.getAnswer());

            if(fatwaDataModel.getAnswer() != null && !fatwaDataModel.getAnswer().equals("")) {
                progressBar.setVisibility(View.GONE);
                Log.d("tryFatwaDate",fatwaDataModel.getCreation_date());
                questionDate.setText("تاریخ اشاعت: " + fatwaDataModel.getCreation_date().split(" ")[0]);
                questionNo.setText("سوال نمبر: " + fatwaDataModel.getFatwa_no());
                String str = null;
                if(!Constants.SEARCHTEXT.equals("-1"))
                {
                    str = fatwaDataModel.getAnswer();
                    if(fatwaDataModel.getAnswer().contains(Constants.SEARCHTEXT)) {
                        str = str.replaceAll(Constants.SEARCHTEXT, "<span style=\"background-color: #ffff00;\">" + Constants.SEARCHTEXT + "</span>");
                    }else{
                        String arr[] = Constants.SEARCHTEXT.split(" ");
                        for(int i=0;i<arr.length;i++){
                            if(fatwaDataModel.getAnswer().contains(arr[i])){
                                Log.d("trySearch",arr[i]);
                                str = str.replaceAll(arr[i], "<span style=\"background-color: #ffff00;\">" + arr[i] + "</span>");
                            }
                        }
                    }
                }else{
                    str = fatwaDataModel.getAnswer();
                }
                str = str.replaceAll("style=\"font-family: KFGQPC Uthman Taha Naskh;\"","id = \"arbi_text\"");
                str ="<!DOCTYPE html>\n" +
                        "<html dir=\"rtl\">\n" +
                        "<head>\n" +
                        "<link rel=\"stylesheet\" type=\"text/css\" href=\"simplecss.css\"/>\n" +
                        "<style>@charset \"utf-8\";\n" +
                        "@font-face {\n" +
                        "  font-family: arabic_font;\n" +
                        "  font-weight: normal;\n" +
                        "  src: url(\"fonts/arabic_font.ttf\");\n" +
                        "}\n" +
                        "        #arbi_text{\n" +
                        "            font-family: 'arabic_font' !important;\n" +
                        "        }\n" +
                        "\n" +
                        "@font-face {\n" +
                        "    font-family: urdu_font;\n" +
                        "    src: url('fonts/urdu_font.ttf');\n" +
                        "    font-weight: normal;\n" +
                        "    font-style: normal;\n" +
                        "}\n" +
                        "body {\n" +
                        "    font-color:#004a99;\n"+
                        "    padding: 0px;\n" +
                        "    margin: 0px;\n" +
                        "    font-family: 'urdu_font';\n" +
                        "    font-weight: normal;\n" +
                        "}\n" +
                        "</style>"+
                        "</head>"+
                        "<body style = \"padding:10px 10px 10px  10px;background-color:#d8f3ff;\">\n" +
                        str+ "</body>\n" +
                        "</html>";
                Log.d("trySearchRes",str);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    webAnswer.loadDataWithBaseURL("file:///android_asset/", str, "text/html", "utf-8", null);
                    //answer.setText(Html.fromHtml(str, Html.FROM_HTML_MODE_COMPACT));
                    //answer.setText(Html.fromHtml(fatwaDataModel.getAnswer(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    webAnswer.loadDataWithBaseURL("file:///android_asset/", str, "text/html", "utf-8", null);
                    //answer.setText(Html.fromHtml(fatwaDataModel.getAnswer()));
                    //  Log.d("tryAnswer",Html.fromHtml(fatwaDataModel.getAnswer()).toString());
                }
            }else {
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
                            fetchFatwa(fatwaDataModel.getType(),fatwaDataModel.getId());
                        }else{
                            progressBar.setVisibility(View.GONE);
                            showDialogue(fatwaDataModel.getType(),fatwaDataModel.getId());
                        }
                    }
                }.execute();
            }

            //TODO imageview menubar
            final ImageView favourite = findViewById(R.id.imageView3);
            FavouriteDBHelper favouriteDBHelper = FavouriteDBHelper.getInstance(getApplicationContext());
            if(favouriteDBHelper.isFatwaFavourite(fatwaDataModel.getId())) {
                favourite.setImageResource(R.drawable.ic_star_black_24dp);
            }
            favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FavouriteDBHelper favouriteDBHelper = FavouriteDBHelper.getInstance(getApplicationContext());
                    if(!favouriteDBHelper.isFatwaFavourite(fatwaDataModel.getId())) {
                        favouriteDBHelper.addFatwa(fatwaDataModel.getId(),fatwaDataModel.getFatwa_no(), fatwaDataModel.getQuestion(), fatwaDataModel.getAnswer(),fatwaDataModel.getCreation_date(),fatwaDataModel.getView_count());
                        Toast.makeText(FatwaViewActivityUser.this, "Added to favourites", Toast.LENGTH_SHORT).show();
                        favourite.setImageResource(R.drawable.ic_star_black_24dp);
                    }else {
                        favourite.setImageResource(R.drawable.ic_star_border_black_24dp);
                        favouriteDBHelper.deleteFatwa(fatwaDataModel.getId());
                        Toast.makeText(FatwaViewActivityUser.this, "Removed from favourites", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            hawaleTV.setText(fatwaDataModel.getKitab_title()+"\n"+fatwaDataModel.getJild_Title()+  "ج1ص665"  +"\n" +"محدث فتویٰ");
            //TODO imageview menubar
            final ScrollView scrollView = findViewById(R.id.fatwaview);
            final ImageView pdf = findViewById(R.id.imageView6);
            pdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(FatwaViewActivityUser.this, "Starting...", Toast.LENGTH_SHORT).show();
                            // Bitmap bitmap = loadBitmapFromView(scrollView, scrollView.getWidth(), scrollView.getHeight());
                            //createPdf(bitmap);
                            String url = "https://urdufatwa.com/download-fatwa/" + fatwaDataModel.getType() + "/" + fatwaDataModel.getFatwa_no();
                            Log.d("tryDownload", url);
                            DownloadManager.Request r = new DownloadManager.Request(Uri.parse(url));

                            // This put the download in the same Download dir the browser uses
                            r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "FatwaNo " + fatwaDataModel.getFatwa_no()+".pdf");

                            // When downloading music and videos they will be listed in the player
                            // (Seems to be available since Honeycomb only)
                            r.allowScanningByMediaScanner();

                            // Notify user when download is completed
                            // (Seems to be available since Honeycomb only)
                            r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                            // Start download
                            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                            dm.enqueue(r);
                        } else {
                            ActivityCompat.requestPermissions(FatwaViewActivityUser.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    1);
                        }
                    }else{
                        Toast.makeText(FatwaViewActivityUser.this, "Starting...", Toast.LENGTH_SHORT).show();
                        // Bitmap bitmap = loadBitmapFromView(scrollView, scrollView.getWidth(), scrollView.getHeight());
                        //createPdf(bitmap);
                        String url = "https://urdufatwa.com/download-fatwa/" + fatwaDataModel.getType() + "/" + fatwaDataModel.getFatwa_no();
                        Log.d("tryDownload", url);
                        DownloadManager.Request r = new DownloadManager.Request(Uri.parse(url));

                        // This put the download in the same Download dir the browser uses
                        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "FatwaNo " + fatwaDataModel.getFatwa_no()+".pdf");

                        // When downloading music and videos they will be listed in the player
                        // (Seems to be available since Honeycomb only)
                        r.allowScanningByMediaScanner();

                        // Notify user when download is completed
                        // (Seems to be available since Honeycomb only)
                        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                        // Start download
                        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                        dm.enqueue(r);
                    }
                }
            });

            //TODO copy code
            final ImageView copy = findViewById(R.id.imageView10);
            copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = "";
                    str+=questionNo.getText()+"\n"+questionDate.getText()+"\n";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                        str+=Html.fromHtml(fatwaDataModel.getAnswer(), Html.FROM_HTML_MODE_COMPACT);
                    } else {
                        str+=Html.fromHtml(fatwaDataModel.getAnswer());
                    }
                    Log.d("tryFatwa",str);
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("copy",str);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(FatwaViewActivityUser.this, "Copied", Toast.LENGTH_SHORT).show();
                }
            });

            //TODO share code
            final ImageView share = findViewById(R.id.imageView11);
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = "";
                    str+=questionNo.getText()+"\n"+questionDate.getText()+"\n";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                        str+=Html.fromHtml(fatwaDataModel.getAnswer(), Html.FROM_HTML_MODE_COMPACT);
                    } else {
                        str+=Html.fromHtml(fatwaDataModel.getAnswer());
                    }
                    Intent intent =new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT,str);
                    intent.setType("text/plain");
                    startActivity(intent);
                }
            });

        }
    Activity activity = FatwaViewActivityUser.this;
    public void showDialogue(final String type, final int id)
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
                    Toast.makeText(activity, "Retrying...", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                    fetchFatwa(type,id);
                }else{
                    Toast.makeText(activity, "No internet", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();
    }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if(requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(FatwaViewActivityUser.this, "Starting...", Toast.LENGTH_SHORT).show();
                // Bitmap bitmap = loadBitmapFromView(scrollView, scrollView.getWidth(), scrollView.getHeight());
                //createPdf(bitmap);
                String url = "https://urdufatwa.com/download-fatwa/" + fatwaDataModel.getType() + "/" + fatwaDataModel.getFatwa_no();
                Log.d("tryDownload", url);
                DownloadManager.Request r = new DownloadManager.Request(Uri.parse(url));

                // This put the download in the same Download dir the browser uses
                r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "FatwaNo " + fatwaDataModel.getFatwa_no()+".pdf");

                // When downloading music and videos they will be listed in the player
                // (Seems to be available since Honeycomb only)
                r.allowScanningByMediaScanner();

                // Notify user when download is completed
                // (Seems to be available since Honeycomb only)
                r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                // Start download
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(r);
            }
        }

        public void fetchFatwa(final String type, final int id)
        {
            new AsyncTask<Void,Void,Void>()
            {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progressBar.setVisibility(View.VISIBLE);
                }
                @Override
                protected Void doInBackground(Void... voids) {
                    HttpURLConnection connection = null;
                    BufferedReader reader = null;

                    try {
                        Log.d("tryUrl","https://urdufatwa.com/api/view/"+type+"/"+id);
                        URL url = new URL("https://urdufatwa.com/api/view/"+type+"/"+id);
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
                        final JSONObject questionObj = json.getJSONObject("question");
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    fatwaDataModel.setAnswer(questionObj.getJSONObject("data").getString("answer"));
                                    fatwaDataModel.setQuestion(questionObj.getString("question"));
                                    fatwaDataModel.setCreation_date(questionObj.getString("created_at"));
                                    fatwaDataModel.setFatwa_no(questionObj.getInt("fatwa_no"));
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

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    // Toast.makeText(FatwaViewActivity.this, "therre", Toast.LENGTH_SHORT).show();
                    questionDate.setText( "تاریخ اشاعت: "+ fatwaDataModel.getCreation_date().split(" ")[0]);
                    questionNo.setText( "سوال نمبر: " +fatwaDataModel.getFatwa_no());

                    Log.d("tryIndex","There "+fatwaDataModel.getAnswer());
                    if(fatwaDataModel.getAnswer() != null) {

                        String str = null;
                        if(!Constants.SEARCHTEXT.equals("-1"))
                        {
                            str = fatwaDataModel.getAnswer();
                            if(fatwaDataModel.getAnswer().contains(Constants.SEARCHTEXT)) {
                                str = str.replaceAll(Constants.SEARCHTEXT, "<span style=\"background-color: #ffff00;\">" + Constants.SEARCHTEXT + "</span>");
                            }else{
                                String arr[] = Constants.SEARCHTEXT.split(" ");
                                for(int i=0;i<arr.length;i++){
                                    if(fatwaDataModel.getAnswer().contains(arr[i])){
                                        Log.d("trySearch",arr[i]);
                                        str = str.replaceAll(arr[i], "<span style=\"background-color: #ffff00;\">" + arr[i] + "</span>");
                                    }
                                }
                            }
                        }else{
                            str = fatwaDataModel.getAnswer();
                        }
                        str = str.replaceAll("style=\"font-family: KFGQPC Uthman Taha Naskh;\"","id = \"arbi_text\"");
                        str ="<!DOCTYPE html>\n" +
                                "<html dir=\"rtl\">\n" +
                                "<head>\n" +
                                "<link rel=\"stylesheet\" type=\"text/css\" href=\"simplecss.css\"/>\n" +
                                "<style>@charset \"utf-8\";\n" +
                                "@font-face {\n" +
                                "  font-family: arabic_font;\n" +
                                "  font-weight: normal;\n" +
                                "  src: url(\"fonts/arabic_font.ttf\");\n" +
                                "}\n" +
                                "        #arbi_text{\n" +
                                "            font-family: 'arabic_font' !important;\n" +
                                "        }\n" +
                                "\n" +
                                "@font-face {\n" +
                                "    font-family: urdu_font;\n" +
                                "    src: url('fonts/urdu_font.ttf');\n" +
                                "    font-weight: normal;\n" +
                                "    font-style: normal;\n" +
                                "}\n" +
                                "body {\n" +
                                "    font-color:#004a99;\n"+
                                "    padding: 0px;\n" +
                                "    margin: 0px;\n" +
                                "    font-family: 'urdu_font';\n" +
                                "    font-weight: normal;\n" +
                                "}\n" +
                                "</style>"+
                                "</head>"+
                                "<body style = \"padding:10px 10px 10px  10px;background-color:#d8f3ff;\">\n" +
                                str+ "</body>\n" +
                                "</html>";
                        Log.d("trySearchRes",str);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            webAnswer.loadDataWithBaseURL("file:///android_asset/", str, "text/html", "utf-8", null);
                            //answer.setText(Html.fromHtml(str, Html.FROM_HTML_MODE_COMPACT));
                            //answer.setText(Html.fromHtml(fatwaDataModel.getAnswer(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            webAnswer.loadDataWithBaseURL("file:///android_asset/", str, "text/html", "utf-8", null);
                           // answer.setText(Html.fromHtml(fatwaDataModel.getAnswer()));
                            //  Log.d("tryAnswer",Html.fromHtml(fatwaDataModel.getAnswer()).toString());
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }.execute();

        }


        public List<Integer> findWordUpgrade(String textString, String word) {
            List<Integer> indexes = new ArrayList<Integer>();
            StringBuilder output = new StringBuilder();
            String lowerCaseTextString = textString.toLowerCase();
            String lowerCaseWord = word.toLowerCase();
            int wordLength = 0;

            int index = 0;
            while(index != -1){
                index = lowerCaseTextString.indexOf(lowerCaseWord, index + wordLength);  // Slight improvement
                if (index != -1) {
                    indexes.add(index);
                }
                wordLength = word.length();
            }
            return indexes;
        }


        public void onBack(View v)
        {
            onBackPressed();
        }






        public static Bitmap loadBitmapFromView(View v, int width, int height) {
            Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.draw(c);

            return b;
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

    public void showFontDialogue()
    {
        Dialog dialog = new Dialog(FatwaViewActivityUser.this);
        dialog.setContentView(R.layout.font_slider_dialogue);
        SeekBar seekBar = dialog.findViewById(R.id.seekBar3);
        seekBar.setMax(170);
        //Log.d("tryFont",pageWebView.getTextSize()+"");
        //webSettings.setDefaultFontSize(10);
        if(pref.getFloat(Constants.FONT,-1) != -1) {
            seekBar.setProgress((int) pref.getFloat(Constants.FONT,(int) webAnswer.getSettings().getDefaultFontSize()));
        }else{
            seekBar.setProgress((int) webAnswer.getSettings().getDefaultFontSize());
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float newfontsize = seekBar.getProgress();
                pref.edit().putFloat(Constants.FONT,newfontsize).apply();
                //Log.d("tryFontSize",newfontsize+"");
                //webSettings.setDefaultFontSize((int) newfontsize);
                //answer.setTextSize((newfontsize + 150)/10);
                webAnswer.getSettings().setDefaultFontSize((((int) newfontsize)+150)/10);
                question.setTextSize((newfontsize + 150)/10);
                questionDate.setTextSize((newfontsize + 150)/10);
                questionNo.setTextSize((newfontsize + 150)/10);
                questionObs.setTextSize((newfontsize + 150)/10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        dialog.show();
    }

}
