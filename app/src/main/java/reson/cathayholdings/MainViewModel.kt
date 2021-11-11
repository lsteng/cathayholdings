package reson.cathayholdings

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import reson.cathayholdings.data.ZooModel
import reson.cathayholdings.data.ZooSubResult
import reson.chocomedia.util.HttpUtil

class MainViewModel(application: Application): AndroidViewModel(application) {
    val TAG = "MainViewModel"
    val context = getApplication<Application>().applicationContext
    val isShowProgress = MutableLiveData<Boolean>()
    val alertMessage = MutableLiveData<String>()
    val zooResultList = MutableLiveData<List<ZooSubResult>>()

    //此errorHandler用來接 CoroutineScope 沒有被 try-catch 包起來的 exceptions
    val errorHandler = CoroutineExceptionHandler { _, error ->
        Log.e(TAG, error.toString())
        isShowProgress.value = false
    }
    val coroutineContext = Dispatchers.IO + errorHandler

    init {
        isShowProgress.value = true
        getZooData()
    }

    fun getZooData(){
        if (HttpUtil.haveInternet(context)){
            alertMessage.value = ""
            isShowProgress.value = true

            viewModelScope.launch(coroutineContext) {
                val response = HttpUtil.getDataSting(Constant.ZooApi)
                val ZooResult = Gson().fromJson(response, ZooModel::class.java)
                zooResultList.postValue(ZooResult.result.results)
            }

        } else{
            val alertString = "No Internet Connection"
            isShowProgress.value = false
            alertMessage.value = alertString
        }
    }


}