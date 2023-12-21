package com.example.stagetech.Étudiant.CéerÉtudiant.Présentateur

import com.example.stagetech.Entité.Étudiant

interface IÉtudiant {

    interface Vue {
        fun VoirÉtudiants(étudiants: List<Étudiant>)
        fun afficherUnÉtudiant(étudiant: Étudiant)
        fun ApresAjoutEtudiant()
        fun affichermessageErreur(message: String)
        fun affichermessageSucces()
        fun affichermessageCreationcompteAvecSucces(étudiant: Étudiant)


//        val idÉtudiant: Int,
//        val nom: String,
//        val prénom: String,
//        val description: String,
// courriel: String,
// adresse: String,
// collège: String,
// spécialité: String,
// disponibilité: String,
// bulletinScolaire : String,
// Cv : String,
//        val mesApplications: MutableList<Stage> = mutableListOf(),
//        val stagesEnregistrés: MutableList<Stage> = mutableListOf()

    }

    interface Présentateur {
//        fun VoirÉtudiants()
//        fun AjouterÉtudiant(nom: String,
//                            prénom: String,
//                            description: String,
//                            courriel: String,
//                            adresse: String,
//                            collège: String,
//                            spécialité: String,
//                            disponibilité: String,
//                            bulletinScolaire : String,
//                            Cv : String,
//                            mesApplication: MutableList<Stage>,
//                            stagesEnregistrés : MutableList<Stage>
//                            )
//
//        fun supprimerÉtudiant(IdÉtudiant: String)
//        fun modifierÉtudiant(étudiant: Étudiant)
       // fun CreerCompteÉtudiant(nom: String, prénom: String, description: String)

        fun ajouterÉtudiant(étudiant: Étudiant)
        fun getEtudiantParCourriel(courriel: String)
        fun ajouterNouveauÉtudiantsÀApi2(
            nom: String,
            prenom: String,
            courriel: String,
            motDePasse: String
        )
    }

}