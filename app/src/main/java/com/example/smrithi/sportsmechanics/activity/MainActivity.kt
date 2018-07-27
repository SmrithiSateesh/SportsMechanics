package com.example.smrithi.sportsmechanics.activity

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.smrithi.sportsmechanics.R
import com.example.smrithi.sportsmechanics.adapter.SearchAdapter
import com.example.smrithi.sportsmechanics.interfaces.SearchClickListener
import com.example.smrithi.sportsmechanics.model.SearchList
import com.example.smrithi.sportsmechanics.model.SearchResponse
import retrofit2.Response
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.example.smrithi.sportsmechanics.interfaces.ResponseInterface
import com.example.smrithi.sportsmechanics.utils.PaginationScrollListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.smrithi.sportsmechanics.model.SearchPlayerResponse

class MainActivity : AppCompatActivity(), SearchClickListener, ResponseInterface {


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
    private var mPresenter: MainActivityPresenter ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val watcher = object : TextWatcher {

            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                layoutMatchType.visibility = View.VISIBLE
                txtMatchType.visibility = View.VISIBLE
            }
        }

        etBatsman.addTextChangedListener(watcher)
        etBowler.addTextChangedListener(watcher)
        etFielder.addTextChangedListener(watcher)
        etGeneralSearch.addTextChangedListener(watcher)

        val matchType = ArrayList<String>()

        matchType.clear()
        if (radioIPL.isChecked){
            matchType.add("IPL")
        }
        if (radioODI.isChecked){
            matchType.add("ODI")
        }
        if (radioMultiDay.isChecked){
            matchType.add("TEST")
        }

        val arrayMatchType = arrayOf<String>()
        for (j in 0 until matchType.size) {
            arrayMatchType[j] = matchType.get(j)
        }

        mPresenter = MainActivityPresenter()
        adapter = SearchAdapter(this)

        progressDialog!!.visibility = View.GONE

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        imgSearch.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                searchDialog.visibility = View.VISIBLE
            }
        })

        btnSearch.setOnClickListener{
                searchDialog.visibility = View.GONE
                adapter.clearAll()
                adapter.notifyDataSetChanged()
                txt_no_result.visibility = View.GONE
                txt_related_result.visibility = View.GONE
                if (edtSearch!!.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter field name to search.", Toast.LENGTH_LONG).show()
                    txt_related_result.visibility = View.GONE
                } else {
                    currentPage = 1
                    loadFirstPage(arrayMatchType)
                }
            }

        ivBack.setOnClickListener{
            searchDialog.visibility = View.GONE
        }
        rvSearchResult.setLayoutManager(linearLayoutManager)
        rvSearchResult.setAdapter(adapter)
        rvSearchResult.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager){
            override fun loadMoreItems() {
                progressDialog!!.visibility = View.GONE
                Log.d("PAGE "," loadMoreItems()")
                isLoading = true
                currentPage += 1
                Log.d("Total_page", TOTAL_PAGES.toString())
                Log.d("current_page", currentPage.toString())
                if(TOTAL_PAGES >= currentPage) {
                    Log.d("PAGE "," loadNextPage()")
                    loadNextPage(arrayMatchType)
                } else {
                    adapter.removeLoadingFooter()
                }
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

        initRxObservable_Batsman()
        initRxObservable_Bowler()
        initRxObservable_Fielder()

        etBatsman.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                spinnerBatsman.visibility = View.GONE }
        }
        etBowler.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                spinnerBowler.visibility = View.GONE }
        }
        etFielder.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                spinnerFielder.visibility = View.GONE }
        }
    }

    private fun initRxObservable_Batsman() { //debounce for 1sec

        RxSearchObservable.fromView(etBatsman)
                .debounce(300, TimeUnit.MILLISECONDS) // -> 1 second
                .filter { text -> !text.isEmpty() }
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { keyword ->
                    mPresenter!!.loadPlayerName(etBatsman.getText().toString(), "striker_name", this)
                }
    }

    private fun initRxObservable_Bowler() { //debounce for 1sec

        RxSearchObservable.fromView(etBowler)
                .debounce(300, TimeUnit.MILLISECONDS) // -> 1 second
                .filter { text -> !text.isEmpty() }
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { keyword ->
                    mPresenter!!.loadPlayerName(etBowler.getText().toString(), "bowler_name", this)
                }
    }

    private fun initRxObservable_Fielder() { //debounce for 1sec

        RxSearchObservable.fromView(etFielder)
                .debounce(300, TimeUnit.MILLISECONDS) // -> 1 second
                .filter { text -> !text.isEmpty() }
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { keyword ->
                    mPresenter!!.loadPlayerName(etFielder.getText().toString(), "fielder_name", this)
                }
    }


    private fun loadFirstPage(arrayMatchType: Array<String>) {

        progressDialog.visibility = View.VISIBLE
        mPresenter!!.loadSearchResult(etGeneralSearch.getText().toString(), etBatsman.getText().toString(), etBowler.getText().toString(), etFielder.getText().toString(), arrayMatchType , currentPage, true,this)
    }

    private fun loadNextPage(arrayMatchType: Array<String>) {
        progressDialog.visibility = View.VISIBLE
       // mPresenter!!.loadSearchResult(edtSearch.text.toString(), currentPage, false, this)
        mPresenter!!.loadSearchResult(etGeneralSearch.getText().toString(), etBatsman.getText().toString(), etBowler.getText().toString(), etFielder.getText().toString(), arrayMatchType , currentPage, false,this)
    }

    @SuppressLint("SetTextI18n")
    override fun onSuccess(response: Response<SearchList>?, firstQuery: Boolean) {
        if(firstQuery) {
            if(response!!.body()!!.data != null){
                TOTAL_PAGES = response.body()!!.total_pages
                txt_no_result.visibility = View.GONE
                txt_related_result.visibility = View.VISIBLE
                txt_related_result.text = "Total related videos: " + response.body()!!.total_count
                rvSearchResult.visibility = View.VISIBLE
                adapter.addAll(response.body()!!.getData()!!)
                Log.d("PAGE "," first response")
                progressDialog!!.visibility = View.GONE
            } else {
                progressDialog!!.visibility = View.GONE
                txt_no_result.visibility = View.VISIBLE
                rvSearchResult.visibility = View.GONE
                rvSearchResult.visibility = View.GONE
            }
            if (currentPage <= TOTAL_PAGES) {
                adapter.removeLoadingFooter()
                progressDialog!!.visibility = View.GONE
                isLoading = false
                Log.d("PAGE "," current <= total")
            }
            else {
                isLastPage = true
            }
        } else {
            adapter.removeLoadingFooter()
            isLoading = false

            adapter.addAll(response?.body()!!.getData()!!)

            if (currentPage != TOTAL_PAGES)
                adapter.addLoadingFooter()
            else
                isLastPage = true
            progressDialog!!.visibility = View.GONE
        }

    }

    override fun onFailure(t: Throwable?, firstQuery: Boolean) {
        if(firstQuery) {
            Toast.makeText(this@MainActivity, "Something went wrong. Please try later!", Toast.LENGTH_SHORT).show()
            progressDialog!!.visibility = View.GONE
            txt_no_result.visibility = View.GONE
        } else {
            t?.printStackTrace()
        }
    }

    override fun onSuccess_SearchPlayer(response: Response<SearchPlayerResponse>?, field_name: String) {
       // Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
       when(field_name){
           "striker_name" -> {
               spinnerBatsman.visibility = View.VISIBLE
               showListInSpinner(spinnerBatsman, response)
           }
           "bowler_name" -> {
               spinnerBowler.visibility = View.VISIBLE
               showListInSpinner(spinnerBowler, response)
           }
           "fielder_name" -> {
               spinnerFielder.visibility = View.VISIBLE
               showListInSpinner(spinnerFielder, response)
           }
       }
    }

    override fun onFailure_SearchPlayer(t: Throwable?) {

    }

    private fun showListInSpinner(listView: ListView, response: Response<SearchPlayerResponse>?) {
        val items = arrayOfNulls<String>(response!!.body()!!.data.total_count)

        for (i in 0 until response.body()!!.data.total_count) {
            //Storing names to string array
            items[i] = response.body()!!.data.names[i];
        }
        val adapter: ArrayAdapter<String>
        adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, items)
        listView.setAdapter(adapter)

        listView.setOnItemClickListener { parent, view, position, id ->
            if (spinnerBatsman.visibility == View.VISIBLE){
                etBatsman.setText(items[position])
                spinnerBatsman.visibility = View.GONE
            }
            if (spinnerBowler.visibility == View.VISIBLE){
                etBowler.setText(items[position])
                spinnerBowler.visibility = View.GONE
            }
            if (spinnerFielder.visibility == View.VISIBLE){
                etFielder.setText(items[position])
                spinnerFielder.visibility = View.GONE
            }
        }
    }
}
