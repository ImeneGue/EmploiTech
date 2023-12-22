package com.example.stagetech.Stage.Modèle.StageSauvegardees.Vue

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stagetech.R
import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage
import com.example.stagetech.Stage.Modèle.RechercheStage.Vue.UnStageAdaptateur
import com.example.stagetech.Stage.Modèle.RechercheStage.pésentateur.TrouverStagePrésentateur
import com.example.stagetech.Stage.Modèle.StageSauvegardees.Présentateur.IstageEnregistréContrat
import com.example.stagetech.Stage.Modèle.StageSauvegardees.Présentateur.StageEnregistréPrésentateur
import com.example.stagetech.sourceDeDonnées.ServiceApi.Retrofit
import com.example.stagetech.sourceDeDonnées.StageRepository
import kotlinx.coroutines.launch

class VueStagesSauvegardes : Fragment(), IstageEnregistréContrat.vue {

    private lateinit var recyclerView2: RecyclerView
    private lateinit var savedStagesAdapter: StageEntregistréApadter
    private lateinit var navControlleur: NavController
    private lateinit var présentateur: IstageEnregistréContrat.Présentateur
    private lateinit var dialog: Dialog


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


        val stageRepository = StageRepository(Retrofit.serviceApi, requireContext())
        présentateur = StageEnregistréPrésentateur(this, stageRepository)
        recyclerView2 = view.findViewById(R.id.recyclerView2)
        dialog = Dialog(requireActivity())
        présentateur.afficherTousLesStagesDeBDLocal()


        savedStagesAdapter = StageEntregistréApadter(emptyList(), onItemClick = { stageSelectione ->
            afficherDetailsStage(stageSelectione)

        }, onButtonClickListener = { stage ->

            lifecycleScope.launch {
                présentateur.supprimerStageParId(stage.idStage!!)

            }
        })

        recyclerView2.layoutManager = LinearLayoutManager(requireContext())
        recyclerView2.adapter = savedStagesAdapter



        setupOnBackPressedCallback()
        return view
    }

    override fun afficherListeStages(listeStages: List<Stage>) {
        savedStagesAdapter.updateData(listeStages)
    }


    override fun afficherDetails(stageId: Int) {
        présentateur.stageSelectionné(stageId)
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
        présentateur.stageSelectionné(stageId)
    }

    override fun affichermessageErreur(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Erreur")
            .setMessage("Erreur, Stage Introuvable!!")
            .setNegativeButton("OK", null)
            .show()
    }

    override fun affichermessageSauvegardeSucces() {
        Toast.makeText(requireContext(), "Stage sauvgardé avec succès", Toast.LENGTH_SHORT).show()
    }

    override fun supprimerStageParId(stageId: Int) {
        présentateur.supprimerStageParId(stageId)    }

    override fun affichermessageSucces() {
        AlertDialog.Builder(requireContext())
            .setTitle("Succès")
            .setMessage("succès!")
            .setPositiveButton("OK", null)
            .show()
    }


    override fun afficherToast(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navControlleur = Navigation.findNavController(view)
    }




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
        if (!findNavController().navigateUp()) {
            requireActivity().finish()
        }
    }
}