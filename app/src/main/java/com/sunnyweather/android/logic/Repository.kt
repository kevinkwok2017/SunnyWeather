package com.sunnyweather.android.logic

import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
/**
 * 调用：val liveData = Repository.searchPlaces("北京")
 * 拿到的是 LiveData；观察它收到结果后，才能取出真正的城市列表。
 * 如果没有 liveData { ... } 这层封装，
 * 而是直接写一个返回 Result.success(places) 的普通函数，
 * 那才是直接返回 Result 对象。*/
object Repository {
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        //result==Result.success(places) 返回的对象
        val result = try{
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok"){
                val places = placeResponse.places
                Result.success(places)
            }
            else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }
        catch (e: Exception){
        }
        emit(result)//把result发送给LiveData
    }
}