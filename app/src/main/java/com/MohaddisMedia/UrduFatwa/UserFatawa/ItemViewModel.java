package com.MohaddisMedia.UrduFatwa.UserFatawa;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

import com.MohaddisMedia.UrduFatwa.DataModels.FatwaDataModel;


public class ItemViewModel extends ViewModel {

    //creating livedata for PagedList  and PagedKeyedDataSource
    LiveData<PagedList<FatwaDataModel>> itemPagedList;
    LiveData<PageKeyedDataSource<Integer, FatwaDataModel>> liveDataSource;

    //constructor
    public ItemViewModel() {
        //getting our data source factory
        ItemDataSourceFactory itemDataSourceFactory = new ItemDataSourceFactory();

        //getting the live data source from data source factory
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ItemDataSource.PAGE_SIZE).build();

        //Building the paged list
        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig))
                .build();
    }
}