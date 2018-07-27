package com.example.smrithi.sportsmechanics.activity

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.util.Log
import com.example.smrithi.sportsmechanics.network.RetrofitInstance
import com.example.smrithi.sportsmechanics.interfaces.ResponseInterface
import com.example.smrithi.sportsmechanics.model.SearchList
import com.example.smrithi.sportsmechanics.model.SearchPlayerResponse
import com.example.smrithi.sportsmechanics.network.GetSearchDataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("StaticFieldLeak")
class MainActivityPresenter {

    fun loadSearchResult(key: String, batsman: String, bowler: String, fielder: String, match_type: Array<String>, currentPage: Int, firstQuery: Boolean, listener: ResponseInterface) {
        object : AsyncTask<Void, Void, Unit>() {
            override fun doInBackground(vararg params: Void?) {
                Log.d("PAGE "," loadFirstPage()")
                val retrofitInstance = RetrofitInstance()
                val client_id = 1
                val service = retrofitInstance.getRetrofitInstance().create(GetSearchDataService::class.java)
                val call = service.createSearchResquest(key, batsman, bowler, fielder, match_type, currentPage, client_id.toString())

                call.enqueue(object : Callback<SearchList> {
                    override fun onFailure(call: Call<SearchList>?, t: Throwable?) {
                        listener.onFailure(t, firstQuery)
                    }

                    override fun onResponse(call: Call<SearchList>?, response: Response<SearchList>?) {
                        listener.onSuccess(response, firstQuery)
                    }

                })
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    fun loadPlayerName(key : String, field_name : String, listener: ResponseInterface){
        object : AsyncTask<Void, Void, Unit>() {
            override fun doInBackground(vararg params: Void?) {
                Log.d("PAGE "," loadFirstPage()")
                val retrofitInstance = RetrofitInstance()
                val service = retrofitInstance.getRetrofitInstance().create(GetSearchDataService::class.java)
                val call = service.searchPlayer(key, field_name)

                call.enqueue(object : Callback<SearchPlayerResponse> {
                    override fun onFailure(call: Call<SearchPlayerResponse>?, t: Throwable?) {
                        listener.onFailure_SearchPlayer(t)
                    }

                    override fun onResponse(call: Call<SearchPlayerResponse>?, response: Response<SearchPlayerResponse>?) {
                       listener.onSuccess_SearchPlayer(response, field_name)
                    }
                })
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }
}