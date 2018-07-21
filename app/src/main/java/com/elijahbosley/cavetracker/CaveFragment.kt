package com.elijahbosley.cavetracker

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elijahbosley.cavetracker.objects.TripItem
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [CaveFragment.OnListFragmentInteractionListener] interface.
 */
class CaveFragment : Fragment() {

    // TODO: Customize parameters
    private lateinit var authorUID: String


    // Firebase Params
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var adapter: MyCaveRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val query: Query = db.collection("trips").limit(50)
        val options: FirestoreRecyclerOptions<TripItem> = FirestoreRecyclerOptions.Builder<TripItem>().setQuery(query, TripItem::class.java).build()
        val view = inflater.inflate(R.layout.fragment_cave_list, container, false)
        // Set the adapter
        if (view is RecyclerView) {
            view.layoutManager = LinearLayoutManager(context)
            adapter = MyCaveRecyclerViewAdapter(options)
            view.adapter = adapter
        }
        return view
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
