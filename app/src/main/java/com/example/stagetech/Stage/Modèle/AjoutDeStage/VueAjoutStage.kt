package com.example.stagetech.Stage.Modèle.AjoutDeStage

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.stagetech.R
import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage

class VueAjoutStage : Fragment(), IContratVPAjoutStage.Vue {

    private lateinit var présentateurAjoutStage: IContratVPAjoutStage.Présentateur

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setupOnBackPressedCallback()

        return inflater.inflate(R.layout.fragment_ajout_stage, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        val repository = StageRepository(requireContext())
//        présentateurAjoutStage = PrésentateurAjoutStage(repository , this)
//
//        val btnPublier: Button = view.findViewById(R.id.btnPublier)
//
//        btnPublier.setOnClickListener {
//            val NomStage = view.findViewById<EditText>(R.id.NomStage).text.toString()
//            val NomEntreprise = view.findViewById<EditText>(R.id.NomEntreprise).text.toString()
//            //val logoEntreprise = view.findViewById<EditText>(R.id.logoEntreprise).text.toString()
//            val logoEntreprise = repository.getImageResourceIdFromCompanyName(NomEntreprise)
//            val Localisation = view.findViewById<EditText>(R.id.Localisation).text.toString()
//            val Salaire = view.findViewById<EditText>(R.id.Salaire).text.toString()
//            val Description = view.findViewById<EditText>(R.id.Description).text.toString()
//            val taches = view.findViewById<EditText>(R.id.taches).text.toString()
//            val competences = view.findViewById<EditText>(R.id.competences).text.toString()
//            val modeStage = view.findViewById<EditText>(R.id.modeStage).text.toString()
//
//
//            val stage = Stage(null, NomStage, NomEntreprise,logoEntreprise,Localisation,Salaire.toDouble(),Description,taches,competences,modeStage)
//
//            (présentateurAjoutStage as PrésentateurAjoutStage).ajouterStage(stage)
//        }

    }

        override fun VoirlistesDeStages(stages: List<Stage>) {
        TODO("Not yet implemented")
    }

    override fun afficherUnStage(stage: Stage) {
        TODO("Not yet implemented")
    }

    override fun affichermessageErreur(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Erreur")
            .setMessage("Erreur, Champs vide ou valeur incorrect!!")
            .setNegativeButton("OK", null)
            .show()
    }

    override fun affichermessageSucces() {
        AlertDialog.Builder(requireContext())
            .setTitle("Succès")
            .setMessage("Stage ajouté avec succès.")
            .setPositiveButton("OK", null)
            .show()    }

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
