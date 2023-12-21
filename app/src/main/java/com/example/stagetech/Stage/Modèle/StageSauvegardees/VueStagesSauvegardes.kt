package com.example.stagetech.Stage.Modèle.StageSauvegardees

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stagetech.R
import com.example.stagetech.Stage.Modèle.RechercheStage.Vue.UnStageAdaptateur

class VueStagesSauvegardes : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var savedStagesAdapter: UnStageAdaptateur
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
        val view = inflater.inflate(R.layout.fragment_vue_stages_sauvegardes, container, false)



        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navControlleur = Navigation.findNavController(view)

    }
    private fun navigateUpOrCustomLogic() {
        if (!findNavController().navigateUp()) {

            requireActivity().finish()
        }
    }
}