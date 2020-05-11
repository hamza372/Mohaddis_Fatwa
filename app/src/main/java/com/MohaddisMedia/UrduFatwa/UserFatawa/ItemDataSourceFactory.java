package com.MohaddisMedia.UrduFatwa.UserFatawa;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.MohaddisMedia.UrduFatwa.DataModels.FatwaDataModel;


public class ItemDataSourceFactory extends DataSource.Factory {

    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, FatwaDataModel>> itemLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource<Integer, FatwaDataModel> create() {
        //getting our data source object
        ItemDataSource itemDataSource = new ItemDataSource();

        //posting the datasource to get the values
        itemLiveDataSource.postValue(itemDataSource);

        //returning the datasource
        return itemDataSource;
    }


    //getter for itemlivedatasource
    public MutableLiveData<PageKeyedDataSource<Integer, FatwaDataModel>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
