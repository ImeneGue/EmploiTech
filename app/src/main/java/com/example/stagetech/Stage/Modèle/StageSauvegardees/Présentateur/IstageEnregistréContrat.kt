package com.example.stagetech.Stage.Modèle.StageSauvegardees.Présentateur

import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage

interface IstageEnregistréContrat {

    interface vue {
        fun afficherDetails(stageId: Int) {

        }

        fun afficherDetailsStage(offreStage: Stage) {

        }

        fun cacherChargement() {
            TODO("Not yet implemented")
        }

        fun affichermessageErreur(s: String) {

        }

        fun afficherListeStages(stages: List<Stage>) {

        }

        fun afficherChargement() {
            TODO("Not yet implemented")
        }

        fun onItemClick(stageId: Int)
        fun afficherToast(s: String)
        fun affichermessageSucces()
        fun affichermessageSauvegardeSucces()
        fun supprimerStageParId(stageId: Int)
    }


    interface Présentateur {

        fun afficherTousLesStagesDeBDLocal()
        fun stageSelectionné(stageId: Int)
        fun stageClické(offreStage: Stage)
        fun detailsStageClické(offreStage: Stage)
        fun supprimerStageParId(stageId: Int)
    }
}