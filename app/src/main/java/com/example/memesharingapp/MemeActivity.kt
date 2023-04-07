package com.example.memesharingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.memesharingapp.databinding.ActivityMemeBinding

class MemeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMemeBinding
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_meme)

        drawerLayout = binding.drawerLayout
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
//        navController = this.findNavController(R.id.nav_host_fragment)


        navController.addOnDestinationChangedListener{ controller , destination , arguments ->
            if (destination.id == controller.graph.startDestinationId) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)

        appBarConfiguration = AppBarConfiguration(navGraph = navController.graph, drawerLayout = drawerLayout)
        NavigationUI.setupWithNavController(binding.navView,navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        return NavigationUI.navigateUp(navController,drawerLayout) || super.onSupportNavigateUp()
    }
}