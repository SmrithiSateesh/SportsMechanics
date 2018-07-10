package com.example.smrithi.sportsmechanics.activity

import android.util.Log
import com.example.smrithi.sportsmechanics.network.RetrofitInstance
import com.example.smrithi.sportsmechanics.interfaces.ResponseInterface
import com.example.smrithi.sportsmechanics.model.SearchList
import com.example.smrithi.sportsmechanics.network.GetSearchDataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityPresenter {

    fun loadSearchResult(searchText: String, currentPage: Int, firstQuery: Boolean, listener: ResponseInterface) {
        Log.d("PAGE "," loadFirstPage()")
        val retrofitInstance = RetrofitInstance()
        val service = retrofitInstance.getRetrofitInstance().create(GetSearchDataService::class.java)
        val call = service.createSearchResquest(searchText, currentPage)

        call.enqueue(object : Callback<SearchList> {
            override fun onFailure(call: Call<SearchList>?, t: Throwable?) {
                listener.onFailure(t, firstQuery)
            }

            override fun onResponse(call: Call<SearchList>?, response: Response<SearchList>?) {
                listener.onSuccess(response, firstQuery)
            }

        })
    }
}