package com.MohaddisMedia.UrduFatwa.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MohaddisMedia.UrduFatwa.Constants;

import com.MohaddisMedia.UrduFatwa.DataModels.JildDataModel;
import com.MohaddisMedia.UrduFatwa.KututbNetworking.Fatwa_ListActivity_Networking;
import com.MohaddisMedia.UrduFatwa.R;

import java.util.ArrayList;

public class JildAdapter extends RecyclerView.Adapter<JildAdapter.viewHolder> {
    Context context;
    ArrayList<JildDataModel> dataList;
    String searchText;
    String language;
    Boolean showMasadir = false;
    Activity activity;

    public JildAdapter(Context context,Activity activity)
    {
        this.context = context;
        dataList = new ArrayList<>();
        this.activity = activity;
    }
    JildAdapter(Context context,ArrayList<JildDataModel> model)
    {
        this.context = context;
        this.dataList = model;
    }

    public JildAdapter(Context context, String searchText, String language) {
        this.context = context;
        this.searchText = searchText;
        this.language = language;
        dataList = new ArrayList<>();
    }

    public JildAdapter(){
        this.searchText = searchText;
        this.language = language;
        dataList = new ArrayList<>();
        this.showMasadir = showMasadir;
    }

    public JildDataModel getJild(int postition){
        return dataList.get(postition);
    }
    public void addKitab(JildDataModel model)
    {
        dataList.add(model);
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_for_jild_recycler,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int i) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "fonts/urdu_font.ttf");
        holder.jildName.setTypeface(tf);
        holder.q_no.setTypeface(tf);
        holder.jildName.setText(dataList.get(i).getJild_title());
        holder.q_no.setText( "کل فتویٰ:" +   dataList.get(i).getQuestion_count());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO check weather adapter was shown in kutbSearch or kutubView
                Intent intent = null;
                    intent = new Intent(context, Fatwa_ListActivity_Networking.class);
                    intent.putExtra(Constants.KUTUB, getJild(i).getJild_Id());
                    intent.putExtra(Constants.TITLE, getJild(i).getJild_title());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView jildName;
        TextView q_no;
        ConstraintLayout layout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            jildName = (TextView)itemView.findViewById(R.id.textView1B);
            q_no = (TextView)itemView.findViewById(R.id.textview25);
            layout = (ConstraintLayout)itemView.findViewById(R.id.jildcard);

        }
    }
}
