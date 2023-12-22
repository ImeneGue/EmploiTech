package com.example.stagetech.Étudiant.profile.Vue

import android.app.AlertDialog

import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.stagetech.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

import com.example.stagetech.Entité.Étudiant
import com.example.stagetech.sourceDeDonnées.ServiceApi.Retrofit
import com.example.stagetech.sourceDeDonnées.ÉtudiantRepository

import com.example.stagetech.Étudiant.Session
import com.example.stagetech.Étudiant.profile.Présentateur.IÉtudiantPrésentateur
import com.example.stagetech.Étudiant.profile.Présentateur.ÉtudiantProfilePrésentateur

import kotlinx.coroutines.launch

class VueÉtudiant : Fragment(), IÉtudiantPrésentateur.Vue {


    private lateinit var présentateur: IÉtudiantPrésentateur.Présentateur
    private lateinit var navControlleur: NavController
    private lateinit var session: Session

    private lateinit var nom: EditText
    private lateinit var prenom: EditText
    private lateinit var courriel: EditText
    private lateinit var motDePasse: EditText
    private lateinit var adresse: EditText
    private lateinit var college: EditText
    private lateinit var specialite: EditText
    private lateinit var disponibilite: EditText
    private lateinit var description: EditText
    private lateinit var cv: EditText
    private lateinit var buttontelechargerCv: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_profile_etudiant, container, false)



        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavView)
        bottomNavigationView.visibility = View.VISIBLE
        setupOnBackPressedCallback()
        return view
    }

    override
    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = Session(requireContext())

        navControlleur = view.findNavController()
        navControlleur = Navigation.findNavController(view)


        val repository = ÉtudiantRepository(Retrofit.serviceApi, requireContext())
        présentateur = ÉtudiantProfilePrésentateur(repository, session, this)

        val idEtudiant = session.idEtudiant
        val btnDeconnecter = view.findViewById<Button>(R.id.btndeconnecter)
        val btnmodifierProfile = view.findViewById<Button>(R.id.btnmodifierProfile)

        if (idEtudiant != null) {

            lifecycleScope.launch {
                try {
                    val etudiant =
                        (présentateur as ÉtudiantProfilePrésentateur).getÉtudiantsDeApiParId3(
                            idEtudiant
                        )

                    nom = view.findViewById<EditText>(R.id.editTextNom)
                    prenom = view.findViewById<EditText>(R.id.editTextprenom)
                    courriel = view.findViewById<EditText>(R.id.editTextEmail)
                    motDePasse = view.findViewById<EditText>(R.id.editTextmotDePasse)
                    adresse = view.findViewById<EditText>(R.id.editTextLocalisation)
                    college = view.findViewById<EditText>(R.id.editTextcollege)
                    specialite = view.findViewById<EditText>(R.id.editTextSpecialite)
                    disponibilite = view.findViewById<EditText>(R.id.editTextdisponibilites)
                    description = view.findViewById<EditText>(R.id.editTextDescription)
//                    var cv = view.findViewById<ImageView>(R.id.imageCv)
                    afficherInfoEtudiant(etudiant!!)


                    btnmodifierProfile.setOnClickListener {
                        Log.d("VueEtudiant", "Modifier Profile button clicked")


                        mettreAJourProfileEtudiant(etudiant!!)
                        lifecycleScope.launch {

                            présentateur.mettreAJourÉtudiantsApi(idEtudiant, etudiant!!)
                            val etudiantAjour = (présentateur as ÉtudiantProfilePrésentateur).getÉtudiantsDeApiParId3(
                                idEtudiant
                            )
                            affichermessageSucces("Profile modifier avec success!")
                            afficherInfoEtudiant(etudiantAjour!!)
                        }
                    }

            }catch (e: Exception) {
                Log.e("vueEtudiant", "Erreur d'avoir etudiant de l'api", e)
            }}
        }

        btnDeconnecter.setOnClickListener {
            affichermessageSucces("merci! au revoir")
            session.fermerLaSession()
            navControlleur.navigate(R.id.action_vueÉtudiant_to_accueilApp)
        }
    }
     fun  afficherInfoEtudiant(etudiant: Étudiant){
        nom.setText(etudiant?.nom)
        prenom.setText(etudiant?.prenom)
        courriel.setText(etudiant?.courriel)
        motDePasse.setText(etudiant?.motDePasse)
        adresse.setText(etudiant?.adresse)
        college.setText(etudiant?.college)
        specialite.setText(etudiant?.specialite)
        disponibilite.setText(etudiant?.disponibilite)
        description.setText(etudiant?.description)
    }
    fun  mettreAJourProfileEtudiant(etudiant: Étudiant){
       etudiant?.nom = nom.text.toString()
       etudiant?.prenom = prenom.text.toString()
       etudiant?.courriel = courriel.text.toString()
       etudiant?.motDePasse = motDePasse.text.toString()
       etudiant?.adresse = adresse.text.toString()
       etudiant?.college = college.text.toString()
       etudiant?.specialite = specialite.text.toString()
       etudiant?.disponibilite = disponibilite.text.toString()
       etudiant?.description = description.text.toString()
        }


    override fun affichermessageSucces(s:String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Bienvenue!")
            .setPositiveButton("D'accord", null)
            .setMessage(s)
            .show()
        Toast.makeText(context, "Success", Toast.LENGTH_SHORT)
    }

    override fun affichermessageErreur(s: String) {
        Toast.makeText(context, "ErreurErreur", Toast.LENGTH_SHORT)
        AlertDialog.Builder(requireContext())
            .setTitle("Erreur")
            .setNegativeButton("ok", null)
            .setMessage(s)
            .show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        session.fermerLaSession()
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
        if (navControlleur.currentBackStackEntry != null && navControlleur.currentBackStackEntry!!.savedStateHandle.keys().isNotEmpty()) {
            navControlleur.popBackStack()
        } else {
            if (!navControlleur.navigateUp()) {
                requireActivity().finish()
            }
        }
    }

}