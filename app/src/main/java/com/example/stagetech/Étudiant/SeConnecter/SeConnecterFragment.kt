package com.example.stagetech.Étudiant.SeConnecter

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.stagetech.Entité.Étudiant
import com.example.stagetech.R
import com.example.stagetech.sourceDeDonnées.ServiceApi.Retrofit
import com.example.stagetech.sourceDeDonnées.ÉtudiantRepository
import com.example.stagetech.Étudiant.Session
import com.google.android.material.bottomnavigation.BottomNavigationView


class SeConnecterFragment : Fragment(),ISeConnecterPrésentateur.Vue {

    private lateinit var présentateur: ISeConnecterPrésentateur.Présentateur
    private lateinit var navControlleur: NavController
    private lateinit var session: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {

            override fun handleOnBackPressed() {
                navigateUpOrCustomLogic()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
//        navControlleur = view.findNavController()
//        navControlleur = Navigation.findNavController(view)
       // navControlleur = Navigation.findNavController(requireActivity(), R.id.mainActivityId)



//        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavView)
//        bottomNavigationView.visibility = View.GONE

        session = Session(requireContext())
        val repository = ÉtudiantRepository(Retrofit.serviceApi, requireContext())
        présentateur = SeConnecterPrésentateur(repository, session,this)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_se_connecter, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navControlleur = view.findNavController()

        val courrielEditText = view.findViewById<EditText>(R.id.editTextEmail)
        val motDePasseEditText = view.findViewById<EditText>(R.id.editTextmotDePasse)
        val btnSeConnecter = view.findViewById<Button>(R.id.btnSeConnecter)
        val btnAjouterEtudiant = view.findViewById<Button>(R.id.btnAjouterEtudiant)

        btnSeConnecter.setOnClickListener {
            val courriel = courrielEditText.text.toString()
            val motDePasse = motDePasseEditText.text.toString()
            if (courriel.isEmpty() || motDePasse.isEmpty()) {
                affichermessageErreur(" Champs vide!")
                //Toast.makeText(context, "Champs vide!", Toast.LENGTH_SHORT).show()
            }else{
            présentateur.seConnecterResponseBody(courriel, motDePasse)

             }
        }
        btnAjouterEtudiant.setOnClickListener{
            navControlleur.navigate(R.id.action_seConnecterFragment_to_ajoutÉtudiantFragment)
        }
    }
    private fun navigateUpOrCustomLogic() {
        navControlleur = findNavController()
        if (!findNavController().navigateUp()) {

            requireActivity().finish()
        }
    }

    override fun apresConnection(etudiant: Étudiant) {
        //Toast.makeText(context, "Bienvenue $etudiant.idEtudiant !", Toast.LENGTH_SHORT).show()
        session.idEtudiant = etudiant.idEtudiant
        val action = R.id.action_seConnecterFragment_to_vueÉtudiant
        navControlleur.navigate(action)


    }
    override fun affichermessageSucces(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Succès")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()

    }

    override fun affichermessageErreur(s : String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Erreur")
            .setMessage(s)
            .setNegativeButton("ok", null)
            .show()    }
    override fun onDestroyView() {
        super.onDestroyView()
        session.fermerLaSession()
    }
}