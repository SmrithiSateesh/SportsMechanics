package com.example.smrithi.sportsmechanics.adapter

import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smrithi.sportsmechanics.R
import com.example.smrithi.sportsmechanics.model.SearchResponse

class SearchAdapter(private val dataList: List<SearchResponse>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.row_search, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.txtID.setText("ID: " + dataList[position].id.toString())
        holder.txtVideoFile.setText("Video File: " + dataList[position].video_file)
        holder.txtVideoPath.setText("Video Path: " + dataList[position].video_location)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtID: TextView
        var txtVideoFile: TextView
        var txtVideoPath: TextView

        init {
            txtID = itemView.findViewById<TextView>(R.id.txt_id)
            txtVideoFile = itemView.findViewById(R.id.txt_video_file)
            txtVideoPath = itemView.findViewById(R.id.txt_video_path)
        }
    }
}
