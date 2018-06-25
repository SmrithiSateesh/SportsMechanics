package com.example.smrithi.sportsmechanics.adapter

import android.annotation.SuppressLint
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        holder.txtVideoPath.setText(" " + dataList[position].video_location)
        holder.txtVideoPath.getPaint().setUnderlineText(true)

        holder.txtVideoPath.setOnClickListener(object : View.OnClickListener{
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
        var txtVideoPath: TextView

        init {
            txtID = itemView.findViewById<TextView>(R.id.txt_id)
            striker_name = itemView.findViewById(R.id.striker_name)
            txtVideoPath = itemView.findViewById(R.id.txt_video_path)
        }
    }
}
