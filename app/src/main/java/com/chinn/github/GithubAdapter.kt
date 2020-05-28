package com.chinn.github

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class GithubAdapter(private val context: Context, private val dataList: ArrayList<Repository>) :
    BaseAdapter() {
    private val inflater: LayoutInflater =
        this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val rowView = inflater.inflate(R.layout.list_row, parent, false)
        val repoName = rowView.findViewById<TextView>(R.id.row_name)
        val repoDescription = rowView.findViewById<TextView>(R.id.row_description)

        val repository = getItem(position)

        repoName.text = repository.name
        repoDescription.text = repository.description

        return rowView
    }

    override fun getItem(position: Int): Repository {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return 15
    }
}
