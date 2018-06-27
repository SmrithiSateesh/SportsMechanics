package com.example.smrithi.sportsmechanics.adapter

import android.annotation.SuppressLint
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.example.smrithi.sportsmechanics.R
import com.example.smrithi.sportsmechanics.interfaces.SearchClickListener
import com.example.smrithi.sportsmechanics.model.SearchResponse


@SuppressLint("SetTextI18n")
class SearchAdapter(val clickListener: SearchClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



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
                searchViewHolder.txtID.setText("ID: " + dataList[position].id.toString())
                searchViewHolder.striker_name.setText("Striker Name: " + dataList[position].striker_name)
                searchViewHolder.bowler_name.setText("Bowler Name: " + dataList[position].bowler_name)
                searchViewHolder.match_name.setText("Match Name: " + dataList[position].match_name)
                searchViewHolder.match_type.setText("Match Type: " + dataList[position].match_type_name)
                searchViewHolder.match_year.setText("Match Year: " + dataList[position].match_year)

                searchViewHolder.row.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(v: View?) {
                        clickListener.onClick(dataList[position])
                    }
                })
            }

            LOADING -> {

            }
        }
    }
    /*override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.txtID.setText("ID: " + dataList[position].id.toString())
        holder.striker_name.setText("Striker Name: " + dataList[position].striker_name)
        holder.bowler_name.setText("Bowler Name: " + dataList[position].bowler_name)
        holder.match_name.setText("Match Name: " + dataList[position].match_name)
        holder.match_type.setText("Match Type: " + dataList[position].match_type_name)
        holder.match_year.setText("Match Year: " + dataList[position].match_year)

        holder.row.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                clickListener.onClick(dataList[position])
            }
        })
    }*/

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
        isLoadingAdded = false

        val position = dataList.size - 1
        val result = getItem(position)

        if (result != null) {
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

        var txtID: TextView
        var striker_name: TextView
        var bowler_name : TextView
        var match_name : TextView
        var match_type : TextView
        var match_year : TextView
        var row : LinearLayout


        init {
            txtID = itemView.findViewById(R.id.txt_id)
            striker_name = itemView.findViewById(R.id.striker_name)
            bowler_name = itemView.findViewById(R.id.txt_Bowler_Name)
            match_name = itemView.findViewById(R.id.txt_match_name)
            match_type = itemView.findViewById(R.id.txt_match_type)
            match_year = itemView.findViewById(R.id.txt_match_year)
            row = itemView.findViewById(R.id.row)
        }
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val progressDialog : ProgressBar
        init {
            progressDialog = itemView.findViewById(R.id.item_progress_Dialog)
        }
    }
}
