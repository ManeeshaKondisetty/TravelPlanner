package com.umkc.travelplanner

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.Transliterator
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.util.*


class HotelListItemAdapter(
        private var context: Context,
        private var nameList: ArrayList<String>,
        private var URIList: ArrayList<String>,
        private var reviewList: ArrayList<Int>
) : BaseAdapter() {

    override fun getCount(): Int {
        return nameList.size
    }

    override fun getItem(arg0: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater: LayoutInflater = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        val row: View = inflater.inflate(R.layout.hotel_list_item, parent, false)
        val title: TextView
        val i1 = row.findViewById(R.id.imgIcon) as ImageView
        val r1 = row.findViewById(R.id.review1) as ImageView
        val r2 = row.findViewById(R.id.review2) as ImageView
        val r3 = row.findViewById(R.id.review3) as ImageView
        val r4 = row.findViewById(R.id.review4) as ImageView
        val r5 = row.findViewById(R.id.review4) as ImageView



        title = row.findViewById(R.id.txtTitle)
        title.text = nameList.get(position)
        //Glide.with(context).load(URIList.get(position)).into(i1)

        when (reviewList.get(position)) {
            1 -> {
                r1.visibility = View.VISIBLE
                r2.visibility = View.GONE
                r3.visibility = View.GONE
                r4.visibility = View.GONE
                r5.visibility = View.GONE
            }
            2 -> {
                r1.visibility = View.VISIBLE
                r2.visibility = View.VISIBLE
                r3.visibility = View.GONE
                r4.visibility = View.GONE
                r5.visibility = View.GONE
            }
            3 -> {
                r1.visibility = View.VISIBLE
                r2.visibility = View.VISIBLE
                r3.visibility = View.VISIBLE
                r4.visibility = View.GONE
                r5.visibility = View.GONE
            }
            4 -> {
                r1.visibility = View.VISIBLE
                r2.visibility = View.VISIBLE
                r3.visibility = View.VISIBLE
                r4.visibility = View.VISIBLE
                r5.visibility = View.GONE
            }
            5 -> {
                r1.visibility = View.VISIBLE
                r2.visibility = View.VISIBLE
                r3.visibility = View.VISIBLE
                r4.visibility = View.VISIBLE
                r5.visibility = View.VISIBLE
            }
            else -> {
                r1.visibility = View.GONE
                r2.visibility = View.GONE
                r3.visibility = View.GONE
                r4.visibility = View.GONE
                r5.visibility = View.GONE
            }
        }
        return row
    }
}