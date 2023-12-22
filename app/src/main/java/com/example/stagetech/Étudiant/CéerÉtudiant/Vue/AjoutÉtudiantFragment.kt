package com.example.stagetech.Étudiant.CéerÉtudiant.Vue

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.stagetech.Entité.Étudiant
import com.example.stagetech.R
import com.example.stagetech.sourceDeDonnées.ServiceApi.Retrofit
import com.example.stagetech.sourceDeDonnées.ÉtudiantRepository
import com.example.stagetech.Étudiant.CéerÉtudiant.Présentateur.IÉtudiant
import com.example.stagetech.Étudiant.CéerÉtudiant.Présentateur.ÉtudiantPrésentateur
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AjoutÉtudiantFragment : Fragment(), IÉtudiant.Vue {

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private lateinit var présentateurÉtudiant: IÉtudiant.Présentateur
    private lateinit var nom: EditText
    private lateinit var prenom: EditText
    private lateinit var courriel: EditText
    private lateinit var motDePasse1: EditText
    private lateinit var motDePasse2: EditText
    private lateinit var ajouterEtudiant: Button
    private lateinit var seConnecter : TextView

    lateinit var navControlleur: NavController

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
        val view = inflater.inflate(R.layout.fragment_ajoutetudiant, container, false)

        val repository = ÉtudiantRepository(Retrofit.serviceApi, requireContext())
        présentateurÉtudiant = ÉtudiantPrésentateur(repository, this)


        ajouterEtudiant = view.findViewById(R.id.btnAjouterEtudiant)
        nom = view.findViewById<EditText>(R.id.editTextNom)
        prenom = view.findViewById<EditText>(R.id.editTextprenom)
        courriel = view.findViewById<EditText>(R.id.editTextEmail)
        motDePasse1 = view.findViewById<EditText>(R.id.editTextmotDePasse)
        motDePasse2 = view.findViewById<EditText>(R.id.editTextmotDePasse2)

        seConnecter = view.findViewById<TextView>(R.id.seConnecterTextView)

        ajouterEtudiant.setOnClickListener {
            var job: Job? = null
            job?.cancel()

            job = lifecycleScope.launch {
            creerCompte(
                nom.text.toString(),
                prenom.text.toString(),
                courriel.text.toString(),
                motDePasse1.text.toString(),
                motDePasse2.text.toString(),
                view
            )
                delay(500)
                ApresAjoutEtudiant()
            }


        }


        validation()
       // ApresAjoutEtudiant()




        seConnecter.setOnClickListener {
            ApresAjoutEtudiant()

        }

        setupOnBackPressedCallback()



        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navControlleur = Navigation.findNavController(view)


//        val repository = ÉtudiantRepository(Retrofit.serviceApi, requireContext())
//        présentateurÉtudiant = ÉtudiantPrésentateur(repository, this)

//
//        val ajouterEtudiant: Button = view.findViewById(R.id.btnAjouterEtudiant)
//        val nom = view.findViewById<EditText>(R.id.editTextNom).text.toString()
//        val prenom = view.findViewById<EditText>(R.id.editTextprenom).text.toString()
//        // val textInputCourriel = view.findViewById<EditText>(R.id.textInputEmail)
//        val courriel = view.findViewById<EditText>(R.id.editTextEmail)
//        //val textInputmotDePasse = view.findViewById<EditText>(R.id.textInputmotDePasse)
//        val motDePasse = view.findViewById<EditText>(R.id.editTextmotDePasse)
//        // val textInputmotDePasse2 = view.findViewById<EditText>(R.id.textInputmotDePasse2)
//        val motDePasse2 = view.findViewById<EditText>(R.id.editTextmotDePasse2)
//
//        ajouterEtudiant.setOnClickListener {
//
//            creerCompte(nom, prenom, courriel.text.toString(), motDePasse.text.toString(),motDePasse2.text.toString(),view)
//        }

    }

    private fun validation() {
        courriel.addTextChangedListener(object : TextWatcher {
            var job: Job? = null
            override fun afterTextChanged(s: Editable?) {
                job?.cancel()

                job = lifecycleScope.launch {
                    delay(500)
                    val email = s.toString()
                    if (validerCourriel(email)) {
                        courriel.error = null
                    } else {

                        courriel.error = "courriel format incorrect!"

                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            }
        })

        motDePasse1.addTextChangedListener(object : TextWatcher {
            var job: Job? = null
            override fun afterTextChanged(s: Editable?) {
                job?.cancel()

                job = lifecycleScope.launch {
                    delay(500)
                val password = s.toString()
                if (validerMotDePasseChar(password)) {

                    motDePasse1.error = null
                } else {
                    motDePasse1.error = "8 car, un chiffre et un caractere special"

                }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        motDePasse2.addTextChangedListener(object : TextWatcher {
            var job: Job? = null

            override fun afterTextChanged(s: Editable?) {
                job?.cancel()

                job = lifecycleScope.launch {
                    delay(1500)
                    val password = s.toString()
                    if (validerMotDePasses(password, motDePasse1.text.toString())) {

                        motDePasse2.error = null
                    } else {

                        motDePasse2.error = "mot de passe ne corresponds pas"
                        view?.let { viderChampsMotDePasse(it) }
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

    }

    private fun creerCompte(
        nom: String,
        prenom: String,
        courriel: String,
        motDePasse: String,
        motDePasse2: String,
        view: View
    ) {
        if (validerChamps(
                nom,
                prenom,
                courriel,
                motDePasse,
                motDePasse2
            )
        ) {
            présentateurÉtudiant.ajouterNouveauÉtudiantsÀApi2(
                nom,
                prenom,
                courriel,
                motDePasse2
            )
            viderChampsTextes(view)
        } else {
            //affichermessageErreur("Champs vide!!")
            viderChampsTextes(view)
        }

    }


    override fun VoirÉtudiants(étudiants: List<Étudiant>) {
        for (étudiant in étudiants) {
            Log.d(
                "étudiant",
                "ID: ${étudiant.idEtudiant}, Nom: ${étudiant.nom}, Prénom: ${étudiant.prenom}" + "motDePasse: ${étudiant.motDePasse}" +
                        "description: ${étudiant.description}, courriel: ${étudiant.courriel}, adresse: ${étudiant.adresse}" +
                        "collège: ${étudiant.college}, spécialité: ${étudiant.specialite}, disponibilité: ${étudiant.disponibilite}" +
                        "bulletin Scolaire: ${étudiant.bulletinScolaire}, Cv: ${étudiant.cv}"
            )
        }
    }

    override fun afficherUnÉtudiant(étudiant: Étudiant) {
        Log.d(
            "unÉtudiant",
            "ID: ${étudiant.idEtudiant}, Nom: ${étudiant.nom}, Prénom: ${étudiant.prenom}" + "motDePasse: ${étudiant.motDePasse}" +
                    "description: ${étudiant.description}, courriel: ${étudiant.courriel}, adresse: ${étudiant.adresse}" +
                    "collège: ${étudiant.college}, spécialité: ${étudiant.specialite}, disponibilité: ${étudiant.disponibilite}" +
                    "bulletin Scolaire: ${étudiant.bulletinScolaire}, Cv: ${étudiant.cv}"
        )
    }

    fun validerChamps(
        nom: String,
        prenom: String,
        email: String,
        motDePasse1: String,
        motDePasse2: String
    ): Boolean {

        return if (nom.isNotEmpty() && prenom.isNotEmpty() && email.isNotEmpty() && motDePasse1.isNotEmpty() && motDePasse2.isNotEmpty()) {
            true
        } else {
            affichermessageErreur("Champs vide!.")
            false
        }
    }

    fun validerMotDePasses(motDePasse1: String, motDePasse2: String): Boolean {
        val errorTextViewMDP2 = view?.findViewById<TextView>(R.id.errorTextViewMDP2)
        return if (motDePasse1.equals(motDePasse2)) {
            View.GONE
            true
        } else {
            View.VISIBLE
            errorTextViewMDP2!!.text = "Mot de passe ne corresponds pas! ."
           // affichermessageErreur("All fields are required.")
            false
        }
    }

    fun validerCourriel(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}".toRegex()
        val errorTextViewEmail = view?.findViewById<TextView>(R.id.errorTextViewEmail)

        return if (email.matches(emailPattern)) {
            View.GONE
            true
        } else {
             View.VISIBLE
            errorTextViewEmail!!.text = "format du courriel invalid! ."
            //affichermessageErreur("Invalid email format.")
            false
        }
    }

    fun validerMotDePasseChar(motDePasse: String): Boolean {
        val errorTextViewMDP = view?.findViewById<TextView>(R.id.errorTextViewMDP1)
        val minLength = 4
        val passwordPattern =
            "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+\$).{$minLength,}\$".toRegex()

        return if (motDePasse.matches(passwordPattern)) {
            View.GONE
            true
        } else {
            View.VISIBLE
            errorTextViewMDP!!.text = "Mot de passe doit y avoir au moins 8 characters ! ."
            //affichermessageErreur("")
            false
        }
    }

    fun viderChampsTextes(view: View) {
        view.findViewById<EditText>(R.id.editTextNom).text.clear()
        view.findViewById<EditText>(R.id.editTextprenom).text.clear()
        view.findViewById<EditText>(R.id.editTextEmail).text.clear()
        view.findViewById<EditText>(R.id.editTextmotDePasse).text.clear()
        view.findViewById<EditText>(R.id.editTextmotDePasse2).text.clear()
    }

    fun viderChampsMotDePasse(view: View) {
        //view.findViewById<EditText>(R.id.editTextmotDePasse).text.clear()
        view.findViewById<EditText>(R.id.editTextmotDePasse2).text.clear()
    }

    override fun ApresAjoutEtudiant() {
//        findNavController().navigate(R.id.seConnecterFragment)
        findNavController().navigate(R.id.action_ajoutÉtudiantFragment_to_seConnecterFragment)
        val transaction = fragmentManager?.beginTransaction()
        transaction?.addToBackStack(null)
        transaction?.commit()
}



    override fun affichermessageErreur(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Erreur")
            // .setMessage("Erreur, Champs vide ou incorrect!!")
            .setMessage(message)
            .setNegativeButton("OK", null)
            .show()
    }

    override fun affichermessageSucces() {
        AlertDialog.Builder(requireContext())
            .setTitle("Succès")
            .setMessage("Création de compte avec succès!")
            .setPositiveButton("Se connecter", null)
            .show()
    }

    override fun affichermessageCreationcompteAvecSucces(étudiant: Étudiant) {
        Toast.makeText(context, "Création de compte avec success!", Toast.LENGTH_SHORT).show()
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

