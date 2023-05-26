package com.example.watchwizard


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = Home()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, homeFragment).commit()


        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {

                    val homeFragment = Home()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, homeFragment).commit()
                    true
                }
                R.id.nav_liked -> {

                    val likedFragment = Liked()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, likedFragment).commit()
                    true
                }
                R.id.nav_watched -> {

                    val watchedFragment = Watched()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, watchedFragment).commit()
                    true
                }
                R.id.nav_search -> {

                    val searchFragment = Search()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, searchFragment).commit()
                    true
                }
                else -> false
            }
        }
    }
}
