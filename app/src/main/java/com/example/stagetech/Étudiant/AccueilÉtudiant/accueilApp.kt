package com.example.stagetech.Étudiant.AccueilÉtudiant

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.stagetech.R
import com.example.stagetech.Étudiant.Session
import com.google.android.material.bottomnavigation.BottomNavigationView


class accueilApp : Fragment() {
    private lateinit var navControlleur: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateUpOrCustomLogic()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_accueil_app, container, false)

       setupOnBackPressedCallback()

     return view
}

    override
    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navControlleur = Navigation.findNavController(view)
        //navControlleur = view.findNavController()

        Log.d("Navigation", "navControlleur : $navControlleur")

        val btnAjouterEtudiant = activity?.findViewById<AppCompatButton>(R.id.btnAjouterEtudiant)
        //val btnAjouterEtudiant = activity?.findViewById<Button>(R.id.btnAjouterEtudiant)
        btnAjouterEtudiant?.setOnClickListener( View.OnClickListener {
            Log.d("Navigation", "btnAjouterEtudiant clicked")

            if (navControlleur.currentDestination?.id == R.id.accueilApp) {

                navControlleur.navigate(R.id.action_accueilApp_to_ajoutÉtudiantFragment)
            }
            //navControlleur.navigate(R.id.action_accueilApp_to_ajoutÉtudiantFragment)
        })

//        val btnAjouterEntreprise = activity?.findViewById<Button>(R.id.btnAjouterEntreprise)
//        btnAjouterEntreprise?.setOnClickListener( View.OnClickListener {
//            navControlleur.navigate(R.id.action_accueilApp_to_ajoutEntreprise)
//        })
        val btnSeConnecter = activity?.findViewById<AppCompatButton>(R.id.btnSeConnecter)
        //val btnSeConnecter = activity?.findViewById<Button>(R.id.btnSeConnecter)
        btnSeConnecter?.setOnClickListener( View.OnClickListener {
            Log.d("Navigation", "btnSeConnecter clicked")
            Log.d("Navigation", "Navigating to action_accueilApp_to_seConnecterFragment")
            //navControlleur.navigate(R.id.action_accueilApp_to_seConnecterFragment)
            //findNavController().navigate(R.id.action_ajoutÉtudiantFragment_to_seConnecterFragment)
            if (findNavController().currentDestination?.id == R.id.accueilApp) {

                navControlleur.navigate(R.id.action_accueilApp_to_seConnecterFragment)
            }

            //navControlleur.navigate(R.id.action_ajoutÉtudiantFragment_to_seConnecterFragment)



        })

    }

    private fun setupOnBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateUpOrCustomLogic()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

        private fun navigateUpOrCustomLogic() {
        if (!findNavController().navigateUp()) {

            requireActivity().finish()
        }
    }

}