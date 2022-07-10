package com.denisatrif.truthdare.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisatrif.truthdare.network.UnsplashApi
import com.denisatrif.truthdare.network.model.UnsplashImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class UnsplashApiStatus { LOADING, ERROR, DONE }

class TruthDaresViewModel : ViewModel() {

    fun getUnsplashPhotos(): LiveData<UnsplashImage> {
        val liveData = MutableLiveData<UnsplashImage>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val photos = UnsplashApi.retrofitService.getRandomPhotoWithTopic()
                liveData.postValue(photos)
            } catch (e: Exception) {
                Log.e("TruthDaresViewModel", "getUnsplashPhotos exception: ", e)
            }
        }
        return liveData
    }

}