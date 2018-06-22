package com.example.smrithi.sportsmechanics.activity

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.view.View
import android.widget.Toast
import com.example.basavaraj.sportsmechanics.network.GetSearchDataService
import com.example.basavaraj.sportsmechanics.network.RetrofitInstance
import kotlinx.android.synthetic.main.activity_main.*
import com.example.smrithi.sportsmechanics.R
import com.example.smrithi.sportsmechanics.model.SearchList
import com.example.smrithi.sportsmechanics.model.SearchRequest
import javax.security.auth.callback.Callback


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val retrofitInstance = RetrofitInstance()
        val service = retrofitInstance.getRetrofitInstance().create(GetSearchDataService::class.java)

        progressDialog!!.visibility = View.GONE

        imgSearch.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if(edtSearch!!.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter field name to search.", Toast.LENGTH_LONG).show()
                }else{
                    progressDialog!!.visibility = View.VISIBLE
                    val searchRequest = SearchRequest()
                    searchRequest.page = 1
                    searchRequest.per_page = 10
                    val videosearchBean = SearchRequest.VideoSearchBean()
                    videosearchBean.key = edtSearch.text.toString()
                    searchRequest.setVideo_search(videosearchBean)
                    val call = service.createUser(searchRequest)
                    call.enqueue(object : Callback<SearchList> {
                        override fun onFailure(call: Call<SearchList>?, t: Throwable?) {
                            Toast.makeText(this@MainActivity, "Something went wrong. Please try later!", Toast.LENGTH_SHORT).show()
                            progressDialog!!.visibility = View.GONE
                        }

                        override fun onResponse(call: Call<SearchList>?, response: Response<SearchList>?) {
                            generateSearchList(response!!.body()!!.getData()!!, progressDialog)
                        }
                    })
                }
            }
        })
    }
}
