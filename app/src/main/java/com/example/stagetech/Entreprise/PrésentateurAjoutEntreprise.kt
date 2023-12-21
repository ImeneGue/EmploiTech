package com.example.stagetech.Entreprise

import com.example.stagetech.Entité.Entreprise
import com.example.stagetech.Stage.Modèle.AjoutDeStage.IContratVPAjoutStage
import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage
import com.example.stagetech.sourceDeDonnées.EntrepriseRepository
import com.example.stagetech.sourceDeDonnées.StageRepository

class PrésentateurAjoutEntreprise(private val repository: EntrepriseRepository, private var vue: ContratEntreprise.Vue) :
    ContratEntreprise.Présentateur {





    fun ajouterEntreprise(entreprise: Entreprise) {
        repository.ajouterEntreprise(entreprise)
        vue.affichermessageSucces()
    }
}