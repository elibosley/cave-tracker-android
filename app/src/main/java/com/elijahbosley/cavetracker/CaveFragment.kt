package com.elijahbosley.cavetracker

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.elijahbosley.cavetracker.objects.TripItem
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.toast

class CaveFragment : Fragment() {

    // TODO: Customize parameters
    private lateinit var authorUID: String


    // Firebase Params
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var adapter: MyCaveRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        authorUID = if (auth.currentUser == null) "" else auth.currentUser!!.uid

        val query: Query = db.collection("trips").whereEqualTo("authorUID", authorUID)
        val options: FirestoreRecyclerOptions<TripItem> = FirestoreRecyclerOptions.Builder<TripItem>().setQuery(query, TripItem::class.java).build()
        val view = inflater.inflate(R.layout.fragment_cave_list, container, false)
        // Set the adapter
        val tripsRecyclerView: RecyclerView = view.find(R.id.trip_list)
            tripsRecyclerView.layoutManager = LinearLayoutManager(context)
            adapter = MyCaveRecyclerViewAdapter(options)
            tripsRecyclerView.adapter = adapter

        val addTripButton: FloatingActionButton = view.find(R.id.add_trip_button)
        addTripButton.setOnClickListener { addTrip() }
        return view
    }

    private fun addTrip() {
        if (auth.currentUser != null) {
            showAddTripDialog()
        } else {
            Toast.makeText(context, "You must log in to add a trip", Toast.LENGTH_LONG)
        }
    }

    private fun showAddTripDialog() {
        val fragmentManager = fragmentManager
        val newFragment = AddTripFragment()

        if (false)
        //TODO check the device size here and change based on the result
        {
            // The device is using a large layout, so show the fragment as a dialog
            newFragment.show(fragmentManager, "dialog")
        } else {
            // The device is smaller, so show the fragment fullscreen
            val transaction = fragmentManager!!.beginTransaction()
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction.add(android.R.id.content, newFragment)
                    .addToBackStack(null).commit()
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }


    companion object {

        @JvmStatic
        fun newInstance() =
                CaveFragment()
    }


}
