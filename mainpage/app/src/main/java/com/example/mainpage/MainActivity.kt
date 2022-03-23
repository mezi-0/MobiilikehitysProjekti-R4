package com.example.mainpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mainpage.fragments.MenuFragment
import com.example.mainpage.fragments.PremiumFragment
import com.example.mainpage.fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val menuFragment = MenuFragment()
    private val premiumFragment = PremiumFragment()
    private val profileFragment = ProfileFragment ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(menuFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
            R.id.mainpage -> replaceFragment(menuFragment)
            R.id.premium -> replaceFragment(premiumFragment)
            R.id.profile -> replaceFragment(profileFragment)

            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment !=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}