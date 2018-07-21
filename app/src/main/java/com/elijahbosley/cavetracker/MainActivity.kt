package com.elijahbosley.cavetracker

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.elijahbosley.cavetracker.dummy.DummyContent
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find

class MainActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 123;
    private lateinit var viewPager: ViewPager
    private lateinit var prevMenuItem: MenuItem
    private val providers: List<AuthUI.IdpConfig> = listOf(AuthUI.IdpConfig.EmailBuilder().build())
    private lateinit var menu: Menu
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Initialize FirebaseAuth */
        auth = FirebaseAuth.getInstance()
        auth.addAuthStateListener { setMenuIcon() }

        /* Initialize Content */
        setContentView(R.layout.activity_main)
        viewPager = find(R.id.fragment_container)
        val adapter = PaneFragmentAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                if (::prevMenuItem.isInitialized) {
                    prevMenuItem.isChecked = false
                } else {
                    navigation.menu.getItem(0).isChecked = false
                }

                navigation.menu.getItem(position).isChecked = true
                prevMenuItem = navigation.menu.getItem(position)
                super.onPageSelected(position)
            }
        })
        val navigation = find<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        this.menu = menu
        //setMenuIcon()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.user_menu_item -> return addFirebaseUser()
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun addFirebaseUser(): Boolean {
        if (FirebaseAuth.getInstance().currentUser != null) {
            //TODO launch user management page here
            val alertDialog = AlertDialog.Builder(this).create()
            alertDialog.setTitle("Log Out")
            alertDialog.setMessage("Are you sure you wish to log out")
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL") { dialog, which -> dialog.dismiss() }
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "LOG OUT"
            ) { dialog, _ ->
                FirebaseAuth.getInstance().signOut()
                dialog.dismiss()
            }
            alertDialog.show()
            return true
        } else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN)
            return true
        }
    }


    private fun setMenuIcon() {
        if (::menu.isInitialized) {
            if (FirebaseAuth.getInstance().currentUser != null) {
                menu.getItem(0).icon = ContextCompat.getDrawable(applicationContext, R.drawable.ic_account_circle_white_24dp)
            } else {
                menu.getItem(0).icon = ContextCompat.getDrawable(applicationContext, R.drawable.ic_person_add_white_24dp)
            }
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                viewPager.currentItem = 0

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_statistics -> {
                viewPager.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}


