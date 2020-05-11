package com.MohaddisMedia.UrduFatwa.CategoryNetworking;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

import com.MohaddisMedia.UrduFatwa.DataModels.FatwaDataModel;


public class ItemViewModelCategory extends ViewModel {

    //creating livedata for PagedList  and PagedKeyedDataSource
    public LiveData<PagedList<FatwaDataModel>> itemPagedList;
    LiveData<PageKeyedDataSource<Integer, FatwaDataModel>> liveDataSource;

    //constructor
    public ItemViewModelCategory() {
        //getting our data source factory
        ItemDataSourceFactoryCategory itemDataSourceFactory = new ItemDataSourceFactoryCategory();

        //getting the live data source from data source factory
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ItemDataSourceCategory.PAGE_SIZE).build();

        //Building the paged list
        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig))
                .build();
    }
}