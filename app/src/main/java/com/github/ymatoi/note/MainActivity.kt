package com.github.ymatoi.note

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.main_bottom_navigation_view)
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigationView.visibility = when (bottomNavigationView.isBottomMenu(destination.id)) {
                true -> View.VISIBLE
                false -> View.GONE
            }
        }
    }

    private fun BottomNavigationView.isBottomMenu(fragmentId: Int) = this.menu.children.firstOrNull { it.itemId == fragmentId }.let {
        when (it) {
            null -> false
            else -> true
        }
    }
}
