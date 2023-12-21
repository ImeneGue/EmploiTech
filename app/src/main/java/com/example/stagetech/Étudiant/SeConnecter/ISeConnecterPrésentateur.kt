package com.example.stagetech.Étudiant.SeConnecter

import com.example.stagetech.Entité.Étudiant

interface ISeConnecterPrésentateur {
    interface Vue {
        fun affichermessageSucces(message: String)
        fun affichermessageErreur(message: String)
        fun apresConnection(etudiant: Étudiant)
    }

    interface Présentateur {
        fun seConnecter(courriel: String, motDePasse: String)

        fun seConnecterResponseBody(courriel: String, motDePasse: String)
    }
}