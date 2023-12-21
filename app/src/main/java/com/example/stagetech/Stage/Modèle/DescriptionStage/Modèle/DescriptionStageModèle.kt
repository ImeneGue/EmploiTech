package com.example.stagetech.Stage.Modèle.DescriptionStage.Modèle

import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage
import com.example.stagetech.Stage.Modèle.DescriptionStage.pésentateur.IDescriptionStage
import com.example.stagetech.sourceDeDonnées.sourcedonnées

class DescriptionStageModèle (private val dataSource: sourcedonnées) : IDescriptionStage.Modèle {

    override fun getStageById(idStage: Int?): Stage? {
        val stage = dataSource.getOffreStageById(idStage)
        return stage?.let {
            Stage(it.idStage, it.titreStage, it.nomEntreprise,it.courrielEntreprise, it.logoEntreprise, it.localisation, it.salaire, it.descriptionPoste, it.taches, it.competences, it.modeStage
            //, it.candidatures
            )
        }
    }


//    override fun postulerCandidature(candidature: candidature) {
//        dataSource.ajouterCandidature(candidature)
//    }
}