package com.elijahbosley.cavetracker


import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.FrameLayout
import com.elijahbosley.cavetracker.objects.TripItem
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import android.widget.TextView
import org.jetbrains.anko.find


class MyCaveRecyclerViewAdapter(options: FirestoreRecyclerOptions<TripItem>)
    : FirestoreRecyclerAdapter<TripItem, MyCaveRecyclerViewAdapter.ViewHolder>(options) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: TripItem) {
        Log.d("MODEL", model.toString())
        holder.mCaveName.text = model.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new instance of the ViewHolder, in this case we are using a custom
        // layout called R.layout.fragment_cave for each item
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_cave, parent, false)

        return ViewHolder(view)
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        var mCaveName: TextView = mView.find(R.id.cave_name)
        var mTripDate: TextView = mView.find(R.id.trip_date)

        override fun toString(): String {
            return super.toString() + " '" + mCaveName.text + "'"
        }
    }
}
