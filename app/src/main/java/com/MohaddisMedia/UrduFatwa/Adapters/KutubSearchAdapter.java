package com.MohaddisMedia.UrduFatwa.Adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.MohaddisMedia.UrduFatwa.Constants;
import com.MohaddisMedia.UrduFatwa.DataModels.KutubDataModel;
import com.MohaddisMedia.UrduFatwa.JildActivity;
import com.MohaddisMedia.UrduFatwa.KututbNetworking.Fatwa_ListActivity_Networking;
import com.MohaddisMedia.UrduFatwa.R;
import com.MohaddisMedia.UrduFatwa.UserFatawa.UserFatwaListActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class KutubSearchAdapter extends RecyclerView.Adapter<KutubSearchAdapter.viewHolder> {
    Context context;
    ArrayList<KutubDataModel> dataList;
    String searchText;
    String language;
    Boolean showMasadir = false;
    Activity activity;

    public KutubSearchAdapter(Context context,Activity activity)
    {
        this.context = context;
        dataList = new ArrayList<>();
        this.activity = activity;
    }
    KutubSearchAdapter(Context context,ArrayList<KutubDataModel> model)
    {
        this.context = context;
        this.dataList = model;
    }

    public KutubSearchAdapter(Context context, String searchText, String language) {
        this.context = context;
        this.searchText = searchText;
        this.language = language;
        dataList = new ArrayList<>();
    }

    public KutubSearchAdapter(Context context, String searchText, String language,Boolean showMasadir) {
        this.context = context;
        this.searchText = searchText;
        this.language = language;
        dataList = new ArrayList<>();
        this.showMasadir = showMasadir;
    }

    public KutubDataModel getKutub(int postition){
        return dataList.get(postition);
    }
    public void addKitab(KutubDataModel model)
    {
        dataList.add(model);
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_for_kutub_recycler,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int i) {
        try {
            Typeface tf = Typeface.createFromAsset(context.getAssets(),
                    "fonts/urdu_font.ttf");
            holder.kutubName.setTypeface(tf);
            holder.kutubName.setText(new String(dataList.get(i).getKitab_title().getBytes(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("tryKutub",dataList.get(i).getBookImage()+"a");
        if(dataList.get(i).getBookImage() != null) {
            Picasso.get().load(Uri.parse("https://urdufatwa.com/"+dataList.get(i).getBookImage())).fit().into(holder.bookImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    holder.bookImage.setImageResource(R.drawable.kutubsmaple);
                }
            });
        }else{
            holder.bookImage.setImageResource(R.drawable.kutubsmaple);
        }
        if(!getKutub(i).isHasJilds()){
            holder.smallBooksImage.setVisibility(View.GONE);
        }else{
            holder.smallBooksImage.setVisibility(View.VISIBLE);
        }

        holder.kutubNumber.setText(dataList.get(i).getKitab_Id()+"");
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO check weather adapter was shown in kutbSearch or kutubView
                if(getKutub(i).isHasJilds())
                {
                    Intent intent = null;
                    intent = new Intent(context, JildActivity.class);
                    intent.putExtra(Constants.KUTUB, getKutub(i));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                }else{
                    if(getKutub(i).getKitab_Id() == 1212){
                        Intent intent = null;
                        intent = new Intent(context, UserFatwaListActivity.class);
                        intent.putExtra(Constants.KUTUB, 0);
                        intent.putExtra(Constants.TITLE, getKutub(i).getKitab_title());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        activity.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    }else{
                        Intent intent = null;
                        intent = new Intent(context, Fatwa_ListActivity_Networking.class);
                        intent.putExtra(Constants.KUTUB, getKutub(i).getKitab_Id());
                        intent.putExtra(Constants.TITLE, getKutub(i).getKitab_title());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        activity.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    }
                }

//                if(searchText != null)
//                {
//                    intent = new Intent(context, AbwabSearchResult.class);
//                    intent.putExtra(Constants.LANGUAGE, language);
//                    intent.putExtra(Constants.SEARCHTEXT, searchText);
//                }else
//                {
//                    intent = new Intent(context, AbwabViewActivity.class);
//                }

//                intent = new Intent(context, AbwabViewActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Log.d("tryExploring",dataList.get(i).getKutubNameArabic()+"   "+dataList.get(i).getId());
//                intent.putExtra(Constants.KUTUB,dataList.get(i));
//                context.startActivity(intent);
//                activity.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView kutubName;
        TextView kutubNumber;
        ImageView bookImage;
        ConstraintLayout layout;
        ImageView smallBooksImage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            smallBooksImage = itemView.findViewById(R.id.imageView5);
            bookImage = itemView.findViewById(R.id.imageView4);
            kutubName = (TextView)itemView.findViewById(R.id.textView5);
            kutubNumber = (TextView)itemView.findViewById(R.id.textView9);
            layout = (ConstraintLayout)itemView.findViewById(R.id.kcard);

        }
    }
}
