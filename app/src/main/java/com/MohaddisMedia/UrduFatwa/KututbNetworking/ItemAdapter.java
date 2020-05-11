package com.MohaddisMedia.UrduFatwa.KututbNetworking;

import android.app.Activity;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.MohaddisMedia.UrduFatwa.Constants;
import com.MohaddisMedia.UrduFatwa.DataModels.FatwaDataModel;
import com.MohaddisMedia.UrduFatwa.FatwaViewActivity;
import com.MohaddisMedia.UrduFatwa.FatwaViewActivityUser;
import com.MohaddisMedia.UrduFatwa.R;

import static android.media.CamcorderProfile.get;

public class ItemAdapter extends PagedListAdapter<FatwaDataModel, RecyclerView.ViewHolder> {



    private Context mCtx;
    private Activity activity;
    String searchText = "-1";
    public ItemAdapter(Context mCtx,Activity activity) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
        this.activity = activity;
        searchText = "-1";
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder vH;
        if (viewType == ITEM_VIEW_TYPE_BASIC) {
            view = LayoutInflater.from(mCtx).inflate(R.layout.card_for_fatwa_listview,parent,false);
            vH = new ItemViewHolder(view);
        } else{
            view = LayoutInflater.from(mCtx).inflate(R.layout.card_for_progress_bar,parent,false);
            vH = new ProgressBarviewHolder(view);
        }

        return vH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder) {
            final FatwaDataModel item = getItem(position);
            if (item != null) {
                if(item.getQuestion() == null){
                    item.setQuestion("");
                }
                ((ItemViewHolder) holder).q_no.setText(item.getFatwa_no() + "");
                ((ItemViewHolder) holder).fatwaName.setText(Html.fromHtml(item.getQuestion().replaceAll(searchText,"<span style=\"background-color:yellow;\">" + searchText + "</span>")));
                Log.d("tryVal",((ItemViewHolder) holder).fatwaName.getText().toString());
                if((((ItemViewHolder) holder).fatwaName.getText().toString()).equals("null")){
                    ((ItemViewHolder) holder).fatwaName.setText("");
                }
                ((ItemViewHolder) holder).manazir.setText("مناضر: " +item.getView_count() + "");
//            Glide.with(mCtx)
//                    .load(item.question.question)
//                    .into(holder.imageView);
            } else {
                Toast.makeText(mCtx, "Item is null", Toast.LENGTH_LONG).show();
            }
            ((ItemViewHolder) holder).layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(item.getType() != null && !item.getType().equals("2")) {
                        Intent intent = null;
                        intent = new Intent(mCtx, FatwaViewActivity.class);
                        intent.putExtra(Constants.FATWA, item);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(Constants.SEARCHTEXT,searchText);
                        mCtx.startActivity(intent);
                        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    }else{
                        Intent intent = null;
                        intent = new Intent(mCtx, FatwaViewActivityUser.class);
                        intent.putExtra(Constants.FATWA, item);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mCtx.startActivity(intent);
                        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    }
                }
            });
        }


    }

    private static DiffUtil.ItemCallback<FatwaDataModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<FatwaDataModel>() {
                @Override
                public boolean areItemsTheSame(FatwaDataModel oldItem, FatwaDataModel newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(FatwaDataModel oldItem, FatwaDataModel newItem) {
                    return oldItem.equals(newItem);
                }
            };


    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView fatwaName;
        TextView q_no;
        TextView manazir;
        ConstraintLayout layout;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            fatwaName = (TextView)itemView.findViewById(R.id.textView1B);
            q_no = (TextView)itemView.findViewById(R.id.textView6);
            manazir = (TextView)itemView.findViewById(R.id.textview25);
            layout = (ConstraintLayout)itemView.findViewById(R.id.jildcard);

        }

    }

    private final int ITEM_VIEW_TYPE_BASIC = 0;
    private final int ITEM_VIEW_TYPE_FOOTER = 1;

    @Override
    public int getItemViewType(int position) {
        //check for the pre-defined value that will indicate footer
        return getItem(position) != null ? ITEM_VIEW_TYPE_BASIC : ITEM_VIEW_TYPE_FOOTER;
    }


    public class ProgressBarviewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public ProgressBarviewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar5);
        }
    }
}
