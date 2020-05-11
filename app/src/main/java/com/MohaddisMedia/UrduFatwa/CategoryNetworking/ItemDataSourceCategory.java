package com.MohaddisMedia.UrduFatwa.CategoryNetworking;


import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.MohaddisMedia.UrduFatwa.Constants;
import com.MohaddisMedia.UrduFatwa.DataModels.FatwaDataModel;

import org.json.JSONArray;
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

public class ItemDataSourceCategory extends PageKeyedDataSource<Integer, FatwaDataModel> {

    //the size of a page that we want
    public static final int PAGE_SIZE = 15;

    //we will start from the first page which is 1
    private static final int FIRST_PAGE = 1;


    //this will be called once to load the initial data
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, FatwaDataModel> callback) {
        Log.d("tryPage",params.toString()+"    "+callback.toString());

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("https://urdufatwa.com/api/category/"+ Constants.CategoryId +"?page="+FIRST_PAGE+"&perPage="+PAGE_SIZE);
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
            JSONObject json = new JSONObject(str);
            JSONArray array = json.getJSONObject("questions").getJSONArray("data");
            Constants.currentPage = json.getJSONObject("questions").getInt("current_page");
            Constants.lastPage = json.getJSONObject("questions").getInt("last_page");
           // JSONObject book = json.getJSONObject("book");
            List<FatwaDataModel> list = new ArrayList<>();
            for(int i = 0;i<array.length();i++)
            {
                FatwaDataModel fatwaLink = new FatwaDataModel();
                fatwaLink.setId(array.getJSONObject(i).getInt("question_id"));
                fatwaLink.setQuestion(array.getJSONObject(i).getJSONObject("question").getString("question"));
                fatwaLink.setAnswer(array.getJSONObject(i).getJSONObject("question").getString("answer"));
                fatwaLink.setFatwa_no(array.getJSONObject(i).getJSONObject("question").getInt("fatwa_no"));
               // fatwaLink.setType(array.getJSONObject(i).getString("type"));
                fatwaLink.setView_count(array.getJSONObject(i).getJSONObject("question").getInt("view_count"));
                fatwaLink.setCreation_date(array.getJSONObject(i).getJSONObject("question").getString("created_at"));
                fatwaLink.setKitab_title("book_title");
               // fatwaLink.(array.getJSONObject(i).getJSONObject("question").getString("updated_at"));
                list.add(fatwaLink);
            }
            Log.d("tryPData",array.get(0).toString());
            callback.onResult(list, null, FIRST_PAGE + 1);
            Log.d("tryData",str);

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
    }



    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, FatwaDataModel> callback) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("https://urdufatwa.com/api/category/"+ Constants.CategoryId +"?page="+params.key+"&perPage="+PAGE_SIZE);
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
            JSONObject json = new JSONObject(str);
            JSONArray array = json.getJSONObject("questions").getJSONArray("data");
            Constants.currentPage = json.getJSONObject("questions").getInt("current_page");
            Constants.lastPage = json.getJSONObject("questions").getInt("last_page");
            List<FatwaDataModel> list = new ArrayList<>();
            for(int i = 0;i<array.length();i++)
            {
                FatwaDataModel fatwaLink = new FatwaDataModel();
                fatwaLink.setId(array.getJSONObject(i).getInt("question_id"));
                fatwaLink.setQuestion(array.getJSONObject(i).getJSONObject("question").getString("question"));
                fatwaLink.setAnswer(array.getJSONObject(i).getJSONObject("question").getString("answer"));
                fatwaLink.setFatwa_no(array.getJSONObject(i).getJSONObject("question").getInt("fatwa_no"));
                fatwaLink.setView_count(array.getJSONObject(i).getJSONObject("question").getInt("view_count"));
                fatwaLink.setCreation_date(array.getJSONObject(i).getJSONObject("question").getString("created_at"));
                // fatwaLink.(array.getJSONObject(i).getJSONObject("question").getString("updated_at"));
                list.add(fatwaLink);
            }
            Log.d("tryPData",array.get(0).toString());
            Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
            callback.onResult(list, adjacentKey);
            //callback.onResult(list, null, FIRST_PAGE + 1);
            Log.d("tryData",str);

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

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, FatwaDataModel> callback) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("https://urdufatwa.com/api/category/"+ Constants.CategoryId +"?page="+params.key+"&perPage="+PAGE_SIZE);
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
            JSONObject json = new JSONObject(str);
            JSONArray array = json.getJSONObject("questions").getJSONArray("data");
            int currentPage = json.getJSONObject("questions").getInt("current_page");
            int lastPage = json.getJSONObject("questions").getInt("last_page");
            Constants.currentPage = json.getJSONObject("questions").getInt("current_page");
            Constants.lastPage = json.getJSONObject("questions").getInt("last_page");
            List<FatwaDataModel> list = new ArrayList<>();
            for(int i = 0;i<array.length();i++)
            {
                FatwaDataModel fatwaLink = new FatwaDataModel();
                fatwaLink.setId(array.getJSONObject(i).getInt("question_id"));
                fatwaLink.setQuestion(array.getJSONObject(i).getJSONObject("question").getString("question"));
                fatwaLink.setAnswer(array.getJSONObject(i).getJSONObject("question").getString("answer"));
                fatwaLink.setFatwa_no(array.getJSONObject(i).getJSONObject("question").getInt("fatwa_no"));
                fatwaLink.setView_count(array.getJSONObject(i).getJSONObject("question").getInt("view_count"));
                fatwaLink.setCreation_date(array.getJSONObject(i).getJSONObject("question").getString("created_at"));
                // fatwaLink.(array.getJSONObject(i).getJSONObject("question").getString("updated_at"));
                list.add(fatwaLink);
            }
//            Log.d("tryPData",array.get(0).toString());
            Integer key = null;
            if(currentPage >= lastPage)
                            {
                                key = null;
                            }else{
                                key = params.key+1;
                            }
            callback.onResult(list,  key);
            Log.d("tryData",str);

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
    }




}
