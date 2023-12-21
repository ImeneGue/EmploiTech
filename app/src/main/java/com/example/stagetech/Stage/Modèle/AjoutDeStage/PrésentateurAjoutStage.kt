package com.example.stagetech.Stage.Modèle.AjoutDeStage

import com.example.stagetech.Entité.Entreprise
import com.example.stagetech.Entité.Étudiant
import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage
import com.example.stagetech.sourceDeDonnées.StageRepository


class PrésentateurAjoutStage(private val repository: StageRepository, private var vue: IContratVPAjoutStage.Vue) : IContratVPAjoutStage.Présentateur {



    override fun VoirlistesDeStages() {

    }

//     fun ajouterStage(stage: Stage) {
//            repository.ajouterStage(stage)
//            vue.affichermessageSucces()
//     }


}

