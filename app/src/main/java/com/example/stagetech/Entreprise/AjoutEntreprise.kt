package com.example.stagetech.Entreprise

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.stagetech.Entité.Entreprise
import com.example.stagetech.R
import com.example.stagetech.Stage.Modèle.AjoutDeStage.IContratVPAjoutStage
import com.example.stagetech.Stage.Modèle.AjoutDeStage.PrésentateurAjoutStage
import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage
import com.example.stagetech.sourceDeDonnées.EntrepriseRepository
import com.example.stagetech.sourceDeDonnées.StageRepository

class AjoutEntreprise : Fragment(), ContratEntreprise.Vue {

    private lateinit var présentateurAjoutEntreprise: ContratEntreprise.Présentateur


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setupOnBackPressedCallback()

        return inflater.inflate(R.layout.fragment_ajout_entreprise, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = EntrepriseRepository(requireContext())
        présentateurAjoutEntreprise = PrésentateurAjoutEntreprise(repository, this)

        val btnCreationEntreprise: Button = view.findViewById(R.id.btnCreationEntreprise)

        btnCreationEntreprise.setOnClickListener {

            val nomEntreprise = view.findViewById<EditText>(R.id.NomEntreprise).text.toString()
            val descriptionEntreprise = view.findViewById<EditText>(R.id.descriptionEntreprise).text.toString()
            val logoEntreprise = repository.getImageResourceIdFromCompanyName(nomEntreprise)
            val nombreEmployers = view.findViewById<EditText>(R.id.nombreEmployers).text.toString()
            val emailHR = view.findViewById<EditText>(R.id.emailHR).text.toString()
            val siteWeb = view.findViewById<EditText>(R.id.siteWeb).text.toString()
            val adresse = view.findViewById<EditText>(R.id.adresse).text.toString()


            val entreprise = Entreprise(
                null, nomEntreprise,descriptionEntreprise,logoEntreprise,nombreEmployers.toInt(),emailHR,siteWeb,adresse)

            (présentateurAjoutEntreprise as PrésentateurAjoutEntreprise).ajouterEntreprise(entreprise)


        }
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
            .setMessage("Entreprise ajouté avec succès.")
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