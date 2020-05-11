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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.MohaddisMedia.UrduFatwa.Constants;
import com.MohaddisMedia.UrduFatwa.DataModels.FatwaDataModel;

import com.MohaddisMedia.UrduFatwa.FatwaViewActivity;
import com.MohaddisMedia.UrduFatwa.R;

import java.util.ArrayList;

public class FatwaListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<FatwaDataModel> dataList;
    String searchText;
    String language;
    Boolean showMasadir = false;
    Activity activity;

    public FatwaListAdapter(Context context,Activity activity)
    {
        this.context = context;
        dataList = new ArrayList<>();
        this.activity = activity;
    }
    FatwaListAdapter(Context context,ArrayList<FatwaDataModel> model)
    {
        this.context = context;
        this.dataList = model;
    }

    public FatwaListAdapter(Context context, String searchText, String language) {
        this.context = context;
        this.searchText = searchText;
        this.language = language;
        dataList = new ArrayList<>();
    }

    public FatwaListAdapter(){
        this.searchText = searchText;
        this.language = language;
        dataList = new ArrayList<>();
        this.showMasadir = showMasadir;
    }

    public FatwaDataModel getFatwa(int postition){
        return dataList.get(postition);
    }
    public void addFatwa(FatwaDataModel model)
    {
        dataList.add(model);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        RecyclerView.ViewHolder vH;
        if (i == ITEM_VIEW_TYPE_BASIC) {
            view = LayoutInflater.from(context).inflate(R.layout.card_for_fatwa_listview,viewGroup,false);
            vH = new viewHolder(view);
        } else{
            view = LayoutInflater.from(context).inflate(R.layout.card_for_progress_bar,viewGroup,false);
            vH = new ProgressBarviewHolder(view);
        }

        return vH;
    }

    private final int ITEM_VIEW_TYPE_BASIC = 0;
    private final int ITEM_VIEW_TYPE_FOOTER = 1;

    @Override
    public int getItemViewType(int position) {
        //check for the pre-defined value that will indicate footer
        return dataList.get(position) != null ? ITEM_VIEW_TYPE_BASIC : ITEM_VIEW_TYPE_FOOTER;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        if(holder instanceof viewHolder) {
            Typeface tf = Typeface.createFromAsset(context.getAssets(),
                    "fonts/urdu_font.ttf");
            ((viewHolder) holder).fatwaName.setTypeface(tf);
            ((viewHolder)holder).fatwaName.setText(dataList.get(i).getQuestion());
            ((viewHolder)holder).q_no.setText("(" + dataList.get(i).getFatwa_no() + ")");
            ((viewHolder) holder).manazir.setTypeface(tf);
            ((viewHolder)holder).manazir.setText("مناضر: " + dataList.get(i).getView_count());
            ((viewHolder)holder).layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO check weather adapter was shown in kutbSearch or kutubView
                    Intent intent = null;
                    intent = new Intent(context, FatwaViewActivity.class);
                    intent.putExtra(Constants.FATWA, getFatwa(i));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                }
            });
        }else{

        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView fatwaName;
        TextView q_no;
        TextView manazir;
        ConstraintLayout layout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            fatwaName = (TextView)itemView.findViewById(R.id.textView1B);
            q_no = (TextView)itemView.findViewById(R.id.textView6);
            manazir = (TextView)itemView.findViewById(R.id.textview25);
            layout = (ConstraintLayout)itemView.findViewById(R.id.jildcard);

        }
    }

    public class ProgressBarviewHolder extends RecyclerView.ViewHolder{

        ProgressBar progressBar;

        public ProgressBarviewHolder(@NonNull View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.progressBar5);

        }
    }
}

