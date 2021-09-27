package com.example.apicallmvvm.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apicallmvvm.data.model.Shop
import com.example.apicallmvvm.data.repository.MainRepository
import com.example.apicallmvvm.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val shops = MutableLiveData<Resource<List<Shop>>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchShops()
    }

    private fun fetchShops() {
        shops.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.getShops()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ shopList ->
                    shops.postValue(Resource.success(shopList))
                }, { throwable ->
                    shops.postValue(Resource.error("Something Went Wrong", null))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getShops(): LiveData<Resource<List<Shop>>> {
        return shops
    }
}