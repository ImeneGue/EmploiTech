package com.example.stagetech.Stage.Modèle.DescriptionStage.Vue

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.stagetech.R
import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage
import com.example.stagetech.Stage.Modèle.DescriptionStage.pésentateur.DescriptionStagePrésentateur
import com.example.stagetech.Stage.Modèle.DescriptionStage.pésentateur.IDescriptionStage
import com.example.stagetech.sourceDeDonnées.StageRepository
import android.net.Uri
import androidx.lifecycle.lifecycleScope
import com.example.stagetech.Stage.Modèle.DescriptionStage.pésentateur.IDescriptionStage.Vue
import com.example.stagetech.sourceDeDonnées.ServiceApi.Retrofit
import com.example.stagetech.Étudiant.Session
import kotlinx.coroutines.launch


class DescriptionStageFragement : Fragment(), Vue {


    private lateinit var idStage: TextView
    private lateinit var titreStage: TextView
    private lateinit var logoEntreprise: ImageView
    private lateinit var nomEntreprise: TextView
    private lateinit var courrielEntreprise: TextView
    private lateinit var address: TextView
    private lateinit var salaire: TextView
    private lateinit var DescriptionPoste: TextView
    private lateinit var tachesAfaire: TextView
    private lateinit var modeStage: TextView
    private lateinit var competences: TextView
    private lateinit var session: Session


    private var stageId: Int = -1

    lateinit var navControlleur: NavController
    private lateinit var présentateur: IDescriptionStage.Présentateur



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                navigateUpOrCustomLogic()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
        session = Session(requireContext())

    }
    private fun navigateUpOrCustomLogic() {
        if (!findNavController().navigateUp()) {

            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_description_stage, container, false)

        val stageRepository = StageRepository(Retrofit.serviceApi ,requireContext())
        présentateur = DescriptionStagePrésentateur(this, stageRepository)
        stageId = arguments?.getInt("idStage") ?: -1

        lifecycleScope.launch {
            val stage = stageRepository.getStageById(stageId)


        idStage = view.findViewById(R.id.idStage)
        titreStage = view.findViewById(R.id.titreStage)
        logoEntreprise = view.findViewById(R.id.logoEntreprise)
        nomEntreprise = view.findViewById(R.id.nomEntreprise)
        courrielEntreprise = view.findViewById(R.id.courrielEntreprise)
        address = view.findViewById(R.id.address)
        salaire = view.findViewById(R.id.salaire)
        DescriptionPoste = view.findViewById(R.id.DescriptionPoste)
        tachesAfaire = view.findViewById(R.id.tachesAfaire)
        competences = view.findViewById(R.id.competences)
        modeStage = view.findViewById(R.id.modeStage)


        présentateur.loadStageDetails(stageId)
        présentateur.retournerStageRHCourriel(stageId)

            showStageDetails(stage)
//
//        val buttonPostuler: Button = view?.findViewById(R.id.buttonPostuler)!!
//        buttonPostuler.setOnClickListener {
//            //présentateur.onPostulerClicked(stageId)
//
//        }

        val EnvoyerCourrielHR: Button = view?.findViewById(R.id.EnvoyerCourrielHR)!!
        EnvoyerCourrielHR.setOnClickListener {
//            val centreprise = retournerStageRHCourriel(idStage)
            envoyerCourriel(stage.courrielEntreprise)
        }}
        return view
    }
    private fun envoyerCourriel(courrielEntreprise : String) {


        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${courrielEntreprise}")

//            Toast.makeText(requireContext(), "Courriel envoyé à $courrielEntreprise", Toast.LENGTH_SHORT).show()

            //putExtra(Intent.EXTRA_EMAIL, courrielEntreprise)
            putExtra(Intent.EXTRA_SUBJECT, "Demande de Stage")
            putExtra(Intent.EXTRA_TEXT, "Monsieur,Madame,\n" +
                    "\n" +
                    "Je me permets de vous adresser la présente afin de manifester mon vif intérêt pour un éventuel stage au sein de votre entreprise. \n" +
                    "\n" +
                    "Veuillez trouver ci-joint mon curriculum vitae qui détaille mon parcours académique.\n" +
                    "\n" +
                    "Je vous remercie par avance pour l'attention que vous porterez à ma candidature et je reste à votre disposition pour toute information supplémentaire.\n" +
                    "\n" +
                    "Cordialement,\n")

        }
        try {
                startActivity(intent)
        //    } else {
//                Toast.makeText(requireContext(), "Courriel introuvable", Toast.LENGTH_SHORT).show()
//            }
        }catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Aucune application de courriel n'est installée", Toast.LENGTH_SHORT).show()
        }
    }



    override fun showStageDetails(stage: Stage) {
        titreStage.text = stage.titreStage
        //logoEntreprise.setImageResource(stage.logoEntreprise)
        logoEntreprise.setImageResource(imagedeLentreprise(stage.nomEntreprise))
        //logoEntreprise.setImageResource(R.drawable.default_logo)
        nomEntreprise.text = stage.nomEntreprise
        courrielEntreprise.text = stage.courrielEntreprise
        address.text = stage.localisation
        salaire.text = stage.salaire.toString()
        DescriptionPoste.text = stage.descriptionPoste
        tachesAfaire.text = stage.taches
        competences.text = stage.competences
        modeStage.text = stage.modeStage
    }
    private fun imagedeLentreprise(nomEntreprise: String): Int {
        return when (nomEntreprise.lowercase()) {
            "cwp" -> R.drawable.cwp
            "workland" -> R.drawable.workland
            "apple" -> R.drawable.apple
            "giro" -> R.drawable.giro
            "paypal" -> R.drawable.paypal
            "samsung" -> R.drawable.samsung
            "desjardins" -> R.drawable.desjardins
            "rbc" -> R.drawable.rbc
            else -> R.drawable.default_logo
        }}

      override fun afficherDetailsStage(offreStage: Stage) {
        val bundle = Bundle()
        offreStage.idStage?.let { bundle.putInt("stageId", it) }
        navControlleur.navigate(R.id.action_pageRechercheStageFragment_to_DescriptionStageFragement, bundle)
//          val transaction = fragmentManager?.beginTransaction()
//          transaction?.setReorderingAllowed(true)
//
//          transaction?.replace(R.id.pageRechercheStageFragment, DescriptionStageFragement::class.java, bundle)
//          transaction?.addToBackStack(null)
//          transaction?.commit()
    }

    override fun afficherChargement() {
        TODO("Not yet implemented")
    }

    override fun affichermessageErreur(s: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Erreur")
            .setMessage("Erreur, Stage introuvable!!")
            .setNegativeButton("OK", null)
            .show()
    }


}