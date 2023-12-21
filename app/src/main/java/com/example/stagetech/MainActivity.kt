package com.example.stagetech

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.stagetech.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.stagetech.Étudiant.profile.Vue.VueÉtudiant
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI


class MainActivity : AppCompatActivity() {

    private lateinit var navControlleur: NavController
    private lateinit var bottomNavView: BottomNavigationView

    private lateinit var binding: ActivityMainBinding

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainActivityId) as NavHostFragment
        navControlleur = navHostFragment.navController

        bottomNavView = findViewById(R.id.bottomNavView)
        bottomNavView.setupWithNavController(navControlleur)

        NavigationUI.setupWithNavController(bottomNavView, navControlleur)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.accueilApp -> {
                navControlleur.navigate(R.id.accueilApp)
                return true
            }
            R.id.pageRechercheStageFragment -> {
                navControlleur.navigate(R.id.pageRechercheStageFragment)
                return true
            }
            R.id.ajoutÉtudiantFragment -> {
                navControlleur.navigate(R.id.ajoutÉtudiantFragment)
                return true
            }
            R.id.vueÉtudiant -> {
                navControlleur.navigate(R.id.vueÉtudiant)
                return true
            }
            R.id.vueStagesSauvegardes-> {
                navControlleur.navigate(R.id.vueStagesSauvegardes)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
}