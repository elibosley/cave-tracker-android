package com.elijahbosley.cavetracker.objects

import android.location.Location
import com.google.firebase.Timestamp

class TripItem {
    lateinit var id: String
    lateinit var name: String
    lateinit var caveName: String
    lateinit var location: Location
    lateinit var verticalDistance: Number
    lateinit var timeUndergroundMs: Number
    lateinit var startTime: Timestamp
    lateinit var endTime: Timestamp
    lateinit var teamMembers: List<String>
    var batsSeen: Boolean = false
    lateinit var batCount: Number

    fun setTripId(tripId: String) {
        id = tripId
    }
}
