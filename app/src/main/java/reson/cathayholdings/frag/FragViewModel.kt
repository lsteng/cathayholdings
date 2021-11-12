package reson.cathayholdings.frag

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
import reson.cathayholdings.Constant
import reson.cathayholdings.data.PlantModel
import reson.cathayholdings.data.PlantSubResult
import reson.chocomedia.util.HttpUtil

class FragViewModel(application: Application): AndroidViewModel(application) {
    val TAG = "FragViewModel"
    val context = getApplication<Application>().applicationContext
    val isShowProgress = MutableLiveData<Boolean>()
    val plantResultList = MutableLiveData<List<PlantSubResult>>()
    var keyword = ""

    //此errorHandler用來接 CoroutineScope 沒有被 try-catch 包起來的 exceptions
    val errorHandler = CoroutineExceptionHandler { _, error ->
        Log.e(TAG, error.toString())
        isShowProgress.value = false
    }
    val coroutineContext = Dispatchers.IO + errorHandler

    init {
        isShowProgress.value = true
    }

    fun setQueryKey(key: String){
        keyword = "&q=$key"
        getPlantData()
    }

    fun getPlantData(){
        isShowProgress.value = true
        viewModelScope.launch(coroutineContext) {
            val response = HttpUtil.getDataSting(Constant.PlantApi + keyword)
            val plantResult = Gson().fromJson(response, PlantModel::class.java)
            plantResultList.postValue(plantResult.result.results)
        }
    }
}