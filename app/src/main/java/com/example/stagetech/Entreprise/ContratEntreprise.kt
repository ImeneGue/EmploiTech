package com.example.stagetech.Entreprise

import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage

interface ContratEntreprise {

    interface Vue {

        fun affichermessageErreur(message: String)
        fun affichermessageSucces()

    }
    interface Présentateur {

    }
}