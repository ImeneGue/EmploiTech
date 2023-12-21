package com.example.stagetech.Stage.Modèle.DescriptionStage.pésentateur

import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage

interface IDescriptionStage {
    interface Vue {
        fun showStageDetails(stage: Stage)
        fun afficherDetailsStage(offreStage: Stage)
        fun afficherChargement()
        fun affichermessageErreur(messageErreur: String)
        fun afficherCourrielEntreprise(courrielEntreprise: String) {

        }
    }

    interface Présentateur {
        fun loadStageDetails(stageId: Int?)
        fun onStageClicked(offreStage: Stage)
        fun onPostulerClicked(stageId: Int?)
        fun retournerStageRHCourriel(stageId: Int?)
    }

    interface Modèle {
        fun getStageById(stageId: Int?): Stage?
        //fun postulerCandidature(candidature: Cadidature)
    }

}