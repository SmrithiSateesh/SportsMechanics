package com.example.smrithi.sportsmechanics.adapter

import android.annotation.SuppressLint
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.smrithi.sportsmechanics.R
import com.example.smrithi.sportsmechanics.interfaces.SearchClickListener
import com.example.smrithi.sportsmechanics.model.SearchResponse

@SuppressLint("SetTextI18n")
class SearchAdapter(private val dataList: List<SearchResponse>, val clickListener: SearchClickListener) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.row_search, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
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
    }

    override fun getItemCount(): Int {
        return dataList.size
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
            txtID = itemView.findViewById<TextView>(R.id.txt_id)
            striker_name = itemView.findViewById(R.id.striker_name)
            bowler_name = itemView.findViewById(R.id.txt_Bowler_Name)
            match_name = itemView.findViewById(R.id.txt_match_name)
            match_type = itemView.findViewById(R.id.txt_match_type)
            match_year = itemView.findViewById(R.id.txt_match_year)
            row = itemView.findViewById(R.id.row)
        }
    }
}
