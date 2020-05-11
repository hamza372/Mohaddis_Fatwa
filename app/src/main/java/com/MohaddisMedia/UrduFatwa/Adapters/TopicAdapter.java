package com.MohaddisMedia.UrduFatwa.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MohaddisMedia.UrduFatwa.Constants;
import com.MohaddisMedia.UrduFatwa.DB.CategoryDBHelper;
import com.MohaddisMedia.UrduFatwa.DBHelper;
import com.MohaddisMedia.UrduFatwa.DataModels.TopicDataModel;
import com.MohaddisMedia.UrduFatwa.KututbNetworking.Fatwa_ListActivity_Networking;
import com.MohaddisMedia.UrduFatwa.R;
import com.MohaddisMedia.UrduFatwa.Topics.TopicsActivity;

import java.util.ArrayList;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.viewHolder> {
    Context context;
    ArrayList<TopicDataModel> dataList;
    String searchText = "-1";
    String language;
    Boolean showMasadir = false;
    Activity activity;

    public TopicAdapter(Context context,Activity activity)
    {
        this.context = context;
        dataList = new ArrayList<>();
        this.activity = activity;
    }
    TopicAdapter(Context context,ArrayList<TopicDataModel> model)
    {
        this.context = context;
        this.dataList = model;
    }

    public TopicAdapter(Context context, String searchText, String language) {
        this.context = context;
        this.searchText = searchText;
        this.language = language;
        dataList = new ArrayList<>();
    }

    public void setSearchText(String searchText) {


        this.searchText = searchText;
    }

    public TopicAdapter(){
        this.searchText = searchText;
        this.language = language;
        dataList = new ArrayList<>();
        this.showMasadir = showMasadir;
    }

    public TopicDataModel getTopic(int postition){
        return dataList.get(postition);
    }
    public void addTopic(TopicDataModel model)
    {
        dataList.add(model);
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_for_topic_recycler,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int i) {
        String str =(i+1)+": "+ (dataList.get(i).getTitle()+"("+dataList.get(i).getQuestion_count()+")").replaceAll(searchText,"<span style=\"background-color:yellow;\">" + searchText + "</span>");
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "fonts/urdu_font.ttf");
        holder.title.setTypeface(tf);
        holder.title.setText(Html.fromHtml(str));
        Log.d("trySearchWord",str+"    "+searchText);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("tryTopic",getTopic(i).getQuestion_count()+"");
                //TODO check weather adapter was shown in kutbSearch or kutubView
                if(isSubCategories("SELECT * FROM "+ DBHelper.TopicEntry.TABLE_NAME+" WHERE "+ DBHelper.TopicEntry.PARENT_ID+" == "+getTopic(i).getId())) {
                    Intent intent = null;
                    intent = new Intent(context, TopicsActivity.class);
                    intent.putExtra(Constants.TOPIC, getTopic(i).getId());
                    intent.putExtra(Constants.TITLE, getTopic(i).getTitle());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                }else{
                    Intent intent = null;
                    intent = new Intent(context, Fatwa_ListActivity_Networking.class);
                    intent.putExtra(Constants.TOPIC, getTopic(i).getId());
                    intent.putExtra(Constants.TITLE, getTopic(i).getTitle());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ConstraintLayout layout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.textView7);
            layout = (ConstraintLayout)itemView.findViewById(R.id.topiccard);

        }
    }

    public boolean isSubCategories(String query)
    {
        CategoryDBHelper topicDBHelper = new CategoryDBHelper(context);

        // Gets the data repository in write mode
        SQLiteDatabase db = topicDBHelper.getReadableDatabase();
        Cursor cursor1 = db.rawQuery(query,null);
        if(cursor1.getCount()>0)
        {
            topicDBHelper.close();
            return true;
        }
        topicDBHelper.close();
        return  false;


    }
}
