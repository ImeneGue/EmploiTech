package com.example.stagetech.Stage.Modèle.AjoutDeStage

import com.example.stagetech.sourceDeDonnées.StageRepository


class PrésentateurAjoutStage(private val repository: StageRepository, private var vue: IContratVPAjoutStage.Vue) : IContratVPAjoutStage.Présentateur {



    override fun VoirlistesDeStages() {

    }

//     fun ajouterStage(stage: Stage) {
//            repository.ajouterStage(stage)
//            vue.affichermessageSucces()
//     }


}

