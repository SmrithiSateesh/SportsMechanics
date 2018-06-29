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
import android.os.Handler
import com.example.smrithi.sportsmechanics.util.PaginationScrollListener


class MainActivity : AppCompatActivity(), SearchClickListener {
    override fun onClick(dataList: SearchResponse) {

        val intent = Intent(this@MainActivity, WebViewActivity::class.java)
        intent.putExtra("videoPath", dataList.video_location)
        startActivity(intent)
    }

    private val PAGE_START = 1
    private var isLoading = false
    private var isLastPage = false
    private var TOTAL_PAGES = 0
    private var currentPage = PAGE_START
    lateinit var adapter : SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = SearchAdapter(this, edtSearch!!.getText().toString())

        progressDialog!!.visibility = View.GONE

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        imgSearch.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                adapter.clearAll()
                adapter.notifyDataSetChanged()
                txt_no_result.visibility = View.GONE
                txt_related_result.visibility = View.GONE
                if (edtSearch!!.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter field name to search.", Toast.LENGTH_LONG).show()
                    txt_related_result.visibility = View.GONE
                } else {
                    loadFirstPage()
                }
            }
        })

        rvSearchResult.setLayoutManager(linearLayoutManager)
        rvSearchResult.setAdapter(adapter)
        rvSearchResult.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager){
            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
                loadNextPage()
            }

            override fun getTotalPageCount(): Int {
                return TOTAL_PAGES
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })
    }

    private fun loadFirstPage() {
        progressDialog!!.visibility = View.VISIBLE
        val retrofitInstance = RetrofitInstance()
        val service = retrofitInstance.getRetrofitInstance().create(GetSearchDataService::class.java)
        val call = service.createSearchResquest(edtSearch.text.toString(), currentPage)

        call.enqueue(object : Callback<SearchList>{
            override fun onFailure(call: Call<SearchList>?, t: Throwable?) {
                Toast.makeText(this@MainActivity, "Something went wrong. Please try later!", Toast.LENGTH_SHORT).show()
                progressDialog!!.visibility = View.GONE
                txt_no_result.visibility = View.GONE
            }

            override fun onResponse(call: Call<SearchList>?, response: Response<SearchList>?) {
                progressDialog.setVisibility(View.GONE)
                if(response!!.body()!!.data != null){
                    txt_no_result.visibility = View.GONE
                    txt_related_result.visibility = View.VISIBLE
                    txt_related_result.text = "Total related videos: " + response.body()!!.total_count
                    rvSearchResult.visibility = View.VISIBLE
                    adapter.addAll(response.body()!!.getData()!!)
                    TOTAL_PAGES = response.body()!!.total_pages
                } else {
                    progressDialog!!.visibility = View.GONE
                    txt_no_result.visibility = View.VISIBLE
                    rvSearchResult.visibility = View.GONE
                    rvSearchResult.visibility = View.GONE
                }
                if (currentPage <= TOTAL_PAGES)
                    adapter.addLoadingFooter()
                else
                    isLastPage = true
            }

        })
    }

    private fun loadNextPage() {
        progressDialog!!.visibility = View.VISIBLE

        val retrofitInstance = RetrofitInstance()
        val service = retrofitInstance.getRetrofitInstance().create(GetSearchDataService::class.java)
        val call = service.createSearchResquest(edtSearch.text.toString(), currentPage)

        call.enqueue(object : Callback<SearchList> {
            override fun onResponse(call: Call<SearchList>, response: Response<SearchList>) {
                adapter.removeLoadingFooter()
                isLoading = false

                adapter.addAll(response.body()!!.getData()!!)

                if (currentPage !== TOTAL_PAGES)
                    adapter.addLoadingFooter()
                else
                    isLastPage = true
                progressDialog!!.visibility = View.GONE
            }

            override fun onFailure(call: Call<SearchList>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}
