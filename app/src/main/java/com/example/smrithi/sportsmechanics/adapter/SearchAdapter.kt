package com.example.smrithi.sportsmechanics.adapter

import android.annotation.SuppressLint
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.example.smrithi.sportsmechanics.R
import com.example.smrithi.sportsmechanics.interfaces.SearchClickListener
import com.example.smrithi.sportsmechanics.model.SearchResponse


@SuppressLint("SetTextI18n")
class SearchAdapter(val clickListener: SearchClickListener, val key: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList = ArrayList<SearchResponse>()
    private var isLoadingAdded = false


    val ITEM = 0
    val LOADING = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            ITEM -> {
                val v1 = inflater.inflate(R.layout.row_search, parent, false)
                viewHolder = SearchViewHolder(v1)
            }
            LOADING -> {
                val v2 = inflater.inflate(R.layout.item_progress, parent, false)
                viewHolder = LoadingViewHolder(v2)
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            ITEM -> {
                val searchViewHolder = holder as SearchViewHolder
                searchViewHolder.key.setText(dataList[position].striker_name + " " + dataList[position].match_name)
                searchViewHolder.match_year.setText(dataList[position].match_year)
                searchViewHolder.match_date.setText(dataList[position].match_date)

                searchViewHolder.playVideo.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(v: View?) {
                        clickListener.onClick(dataList[position])
                    }
                })
            }

            LOADING -> {

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == dataList.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun add(searchResponse: SearchResponse) {
        dataList.add(searchResponse)
        notifyItemInserted(dataList.size - 1)
    }

    fun addAll(moveResults: List<SearchResponse>) {
        for (result in moveResults) {
            add(result)
        }
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(SearchResponse())
    }

    fun removeLoadingFooter() {
        Log.d("PAGE ", " removeLoadingFooter")
        if( isLoadingAdded ) {
            isLoadingAdded = false
            val position = dataList.size - 1
            dataList.removeAt(position)
            notifyItemRemoved(position)
        }

    }

    fun clearAll(){
        dataList.clear()
    }

    fun getItem(position: Int): SearchResponse? {
        return dataList.get(position)
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var key: TextView
        var match_year : TextView
        var playVideo : ImageView
        var match_date : TextView


        init {
            key = itemView.findViewById(R.id.txt_key)
            match_year = itemView.findViewById(R.id.txt_match_year)
            playVideo = itemView.findViewById(R.id.imgPlayVideo)
            match_date = itemView.findViewById(R.id.txt_match_date)
        }
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val progressDialog : ProgressBar
        init {
            progressDialog = itemView.findViewById(R.id.item_progress_Dialog)
        }
    }
}
