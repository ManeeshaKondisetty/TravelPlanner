package com.umkc.travelplanner

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.amadeus.android.Amadeus
import com.amadeus.android.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*


class HotelFragment : Fragment() {
    var progress_show : ProgressBar? = null
    lateinit var listOfHotels: ListView

    lateinit var actContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.actContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.activity_hotel, null) as ViewGroup
        progress_show = root.findViewById<View>(R.id.progressBar3) as ProgressBar
        listOfHotels = root.findViewById(R.id.list_of_hotels)
        return root
    }



    override fun onResume() {
        super.onResume()
        progress_show?.setVisibility(View.VISIBLE)
        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Main)

        val amadeus = Amadeus.Builder(this.requireContext())
                .setClientId("SP5M72pMXkK2AnzqT9PMPp15dKVIgUTj")
                .setClientSecret("9sTwg1D5KHNOFY0w")
                .build()

        scope.launch {
            when (val checkinLinks = amadeus.shopping.hotelOffers.get("MCI")) {
                is ApiResult.Success -> {
                    val list = checkinLinks.data

                    val name : ArrayList<String> = ArrayList<String>()
                    val uri : ArrayList<String> = ArrayList<String>()
                    val review: ArrayList<Int> = ArrayList()
                    for(each in list) {
                        name.add(each.hotel?.name ?: "Unknown")
                        uri.add(each.hotel?.media?.get(0)?.uri ?: "Unknown")
                        review.add(each.hotel?.rating ?: 1)
                    }
                    progress_show?.visibility = View.GONE

                    val hotelAdapter = HotelListItemAdapter(actContext, name, uri, review)
                    listOfHotels.adapter = hotelAdapter


                }
                is ApiResult.Error -> {
                    // Handle your error
                }
            }
        }

    }
}