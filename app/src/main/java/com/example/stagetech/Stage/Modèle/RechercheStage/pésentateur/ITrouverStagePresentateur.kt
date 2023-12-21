package com.example.stagetech.Stage.Modèle.RechercheStage.pésentateur

import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage
import com.example.stagetech.sourceDeDonnées.StageRepository


interface ITrouverStagePresentateur {

interface vue {

        fun afficherListeStages(stages: List<Stage>) {}

        //
        fun afficherDetailsStage(offreStage: Stage) {}
        fun onItemClick(stageId: Int)
        fun afficherDetails(stageId: Int)
        fun showFilteredStages(stagesListParNomEntreprise: List<Stage>)

        fun voirTousLesStages()
        fun affichermessageErreur(message: String)
        fun affichermessageSucces()


        fun cacherChargement() {
        }
        fun afficherChargement() {
        }

        fun SauvegarderStageLocallement(stage: Stage) {

        }

        fun affichermessageSauvegardeSucces() {
        }


}
        interface présentateur {

                fun stageClické(stage: Stage) {}
                fun detailsStageClické(stage: Stage) {}
                fun stageSelectionné(stageId: Int)
                fun afficherTousLesStages()

                //fun trouverStageParTitreStageBDLocal(titre: String)
                fun trouverStageParTitreStage(titre: String)
                //fun trouverStageParNomEntrepriseBDLocal(nomEntreprise: String)
                fun trouverStageParNomEntreprise(nomEntreprise: String)

                fun SauvegarderStageLocallement(stage: Stage)
        }




}