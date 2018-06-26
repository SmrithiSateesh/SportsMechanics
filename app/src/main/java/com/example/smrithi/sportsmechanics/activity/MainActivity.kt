package com.example.smrithi.sportsmechanics.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.basavaraj.sportsmechanics.network.GetSearchDataService
import com.example.basavaraj.sportsmechanics.network.RetrofitInstance
import com.example.smrithi.sportsmechanics.R
import com.example.smrithi.sportsmechanics.adapter.SearchAdapter
import com.example.smrithi.sportsmechanics.interfaces.SearchClickListener
import com.example.smrithi.sportsmechanics.model.SearchList
import com.example.smrithi.sportsmechanics.model.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent

class MainActivity : AppCompatActivity(), SearchClickListener {
    override fun onClick(dataList: SearchResponse) {

        val intent = Intent(this@MainActivity, WebViewActivity::class.java)
        intent.putExtra("videoPath", dataList.video_location)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressDialog!!.visibility = View.GONE


        imgSearch.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if(edtSearch!!.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter field name to search.", Toast.LENGTH_LONG).show()
                }else{
                    loadSearchResults()
                }
            }
        })

      /*  rvSearchResult.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView!!.canScrollVertically(1)) {
                   pageNo = pageNo + 1
                   loadSearchResults(pageNo)
                }
                if (!recyclerView!!.canScrollVertically(-1)) {
                    if(pageNo > 1)
                    pageNo = pageNo - 1
                    loadSearchResults(pageNo)
                }
            }
        })*/
    }

    private fun loadSearchResults() {
        val retrofitInstance = RetrofitInstance()
        val service = retrofitInstance.getRetrofitInstance().create(GetSearchDataService::class.java)

        progressDialog!!.visibility = View.VISIBLE
      /*  val searchRequest = SearchRequest()
        searchRequest.page = 1
        searchRequest.per_page = 10
        val videosearchBean = SearchRequest.VideoSearchBean()
        videosearchBean.key = edtSearch.text.toString()
        searchRequest.setVideo_search(videosearchBean)
        val call = service.createUser(searchRequest)*/
        /*call.enqueue(object : Callback<SearchList> {
            override fun onFailure(call: Call<SearchList>?, t: Throwable?) {
                Toast.makeText(this@MainActivity, "Something went wrong. Please try later!", Toast.LENGTH_SHORT).show()
                progressDialog!!.visibility = View.GONE
            }

            override fun onResponse(call: Call<SearchList>?, response: Response<SearchList>?) {
                generateSearchList(response!!.body()!!.getData()!!, progressDialog)
            }
        })*/
        val call = service.createSearchResquest(edtSearch.text.toString())
        call.enqueue(object : Callback<SearchList>{
            override fun onFailure(call: Call<SearchList>?, t: Throwable?) {
                Toast.makeText(this@MainActivity, "Something went wrong. Please try later!", Toast.LENGTH_SHORT).show()
                progressDialog!!.visibility = View.GONE
                txt_no_result.visibility = View.GONE
            }

            override fun onResponse(call: Call<SearchList>?, response: Response<SearchList>?) {
                if(response!!.body()!!.data != null){
                    generateSearchList(response.body()!!.getData()!!)
                } else {
                    progressDialog!!.visibility = View.GONE
                    txt_no_result.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun generateSearchList(searchDataList: List<SearchResponse>) {
        if (searchDataList.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No results found.", Toast.LENGTH_LONG).show()
        } else {
            val adapter = SearchAdapter(searchDataList, this)
            val layoutManager = LinearLayoutManager(this@MainActivity)
            rvSearchResult!!.layoutManager = layoutManager
            rvSearchResult!!.adapter = adapter
            progressDialog!!.visibility = View.GONE
        }
    }
}
