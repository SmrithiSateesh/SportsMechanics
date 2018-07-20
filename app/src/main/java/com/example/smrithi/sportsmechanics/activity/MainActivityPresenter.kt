package com.example.smrithi.sportsmechanics.activity

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.util.Log
import com.example.smrithi.sportsmechanics.network.RetrofitInstance
import com.example.smrithi.sportsmechanics.interfaces.ResponseInterface
import com.example.smrithi.sportsmechanics.model.SearchList
import com.example.smrithi.sportsmechanics.network.GetSearchDataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityPresenter {

    @SuppressLint("StaticFieldLeak")
    fun loadSearchResult(searchText: String, currentPage: Int, firstQuery: Boolean, listener: ResponseInterface) {
        object : AsyncTask<Void, Void, Unit>() {
            override fun doInBackground(vararg params: Void?) {
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
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }
}