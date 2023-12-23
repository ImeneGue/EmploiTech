package com.example.stagetech.Stage.Modèle.AjoutDeStage

import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage

interface IContratVPAjoutStage {

        interface Vue {
            fun VoirlistesDeStages(stages: List<Stage>)
            fun afficherUnStage(stage: Stage)
            fun affichermessageErreur(message: String)
            fun affichermessageSucces()

        }

        interface Présentateur {
            fun VoirlistesDeStages()
//            fun AjouterStage(titreStage: String,
//                             nomEntreprise: String,
//                             logoEntreprise: Int,
//                             localisation: String,
//                             salaire: Double,
//                             descriptionPoste: String,
//                             taches: String,
//                             competences: String,
//                             modeStage: String,)
        }
    }
