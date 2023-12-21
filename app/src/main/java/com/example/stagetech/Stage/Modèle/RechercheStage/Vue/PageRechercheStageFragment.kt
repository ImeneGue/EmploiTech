package com.example.stagetech.Stage.Modèle.RechercheStage.Vue

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stagetech.R
import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage
import com.example.stagetech.Stage.Modèle.RechercheStage.pésentateur.ITrouverStagePresentateur
import com.example.stagetech.Stage.Modèle.RechercheStage.pésentateur.TrouverStagePrésentateur
import com.example.stagetech.sourceDeDonnées.ServiceApi.Retrofit
import com.example.stagetech.sourceDeDonnées.StageRepository


class PageRechercheStageFragment : Fragment(), ITrouverStagePresentateur.vue {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adaptateur : UnStageAdaptateur
    lateinit var navControlleur: NavController
    private lateinit var présentateur: ITrouverStagePresentateur.présentateur
    lateinit var dialog : Dialog

//    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_page_recherche_stage, container, false)

        val stageRepository = StageRepository(Retrofit.serviceApi ,requireContext())
        présentateur = TrouverStagePrésentateur(this, stageRepository)


        recyclerView = view.findViewById(R.id.recyclerView)
        searchView = view.findViewById(R.id.searchView)
        dialog = Dialog(requireActivity())
        //voirTousLesStages()
        //présentateur.afficherTousLesStages()


        adaptateur = UnStageAdaptateur(emptyList(), onItemClick ={stageSelectione ->
            afficherDetailsStage(stageSelectione)

        }, onButtonClickListener = { stage ->
            présentateur.SauvegarderStageLocallement(stage)
        })

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adaptateur

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(titreStgeATrouver: String?): Boolean {
                présentateur.trouverStageParTitreStage(titreStgeATrouver.orEmpty())
                return true
            }
        })
        voirTousLesStages()
        présentateur.afficherTousLesStages()

        voirTousLesStages()
        présentateur.afficherTousLesStages()

        setupOnBackPressedCallback()
        return view
    }

    override
    fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navControlleur = Navigation.findNavController(view)

    }


    override fun afficherListeStages(listeStages: List<Stage>) {
        adaptateur.updateData(listeStages)
    }



    override fun afficherDetails(stageId: Int) {
        présentateur.stageSelectionné(stageId)    }


    override fun voirTousLesStages() {
        présentateur.afficherTousLesStages()

    }

    override fun showFilteredStages(stages: List<Stage>) {
        adaptateur.updateData(stages)
    }



    override fun afficherDetailsStage(offreStage: Stage) {
        val bundle = Bundle()
        offreStage.idStage?.let { bundle.putInt("idStage", it) }
        navControlleur = findNavController()
        findNavController().navigate(R.id.DescriptionStageFragement, bundle)
        val transaction = fragmentManager?.beginTransaction()
        transaction?.setReorderingAllowed(true)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    override fun onItemClick(stageId: Int) {
        présentateur.stageSelectionné(stageId)    }


    override fun affichermessageErreur(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Erreur")
            .setMessage("Erreur, Stage Introuvable!!")
            .setNegativeButton("OK", null)
            .show()
    }

    override fun affichermessageSauvegardeSucces(){
        Toast.makeText(requireContext(), "Stage sauvgardé avec succes", Toast.LENGTH_SHORT).show()
    }
    override fun affichermessageSucces() {
        AlertDialog.Builder(requireContext())
            .setTitle("Succès")
            .setMessage("succès!")
            .setPositiveButton("OK", null)
            .show()    }




    override fun cacherChargement() {
        if (dialog != null && dialog.isShowing) {
            dialog.dismiss()
        }

    }
    override fun afficherChargement() {
        if (dialog == null || !dialog.isShowing) {
            dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.chargement)
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()
        }
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
        navControlleur = findNavController()
        if (!findNavController().navigateUp()) {
            requireActivity().finish()
        }
    }
}


