package com.elijahbosley.cavetracker


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * A simple [Fragment] subclass.
 *
 */
class StatisticsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return TextView(activity as Context?).apply {
            setText(R.string.hello_blank_fragment)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                StatisticsFragment()
    }
}
