package com.example.stagetech.Stage.Modèle.DescriptionStage.pésentateur

import android.content.pm.PackageManager
import android.util.Log
import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage
import com.example.stagetech.sourceDeDonnées.StageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DescriptionStagePrésentateur (
    private val vue : IDescriptionStage.Vue,
    private val modèle: StageRepository
    ): IDescriptionStage.Présentateur {


    override fun loadStageDetails(stageId: Int?) {
        //vue.afficherChargement()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val stage = modèle.getStageById(stageId!!)
                vue.showStageDetails(stage)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    vue.affichermessageErreur(e.message ?: "ERREUR!")

                }
            }
        }
    }

    override fun retournerStageRHCourriel(stageId: Int?) {
        //vue.afficherChargement()
        val gmailPackage = "com.google.android.gm"
        // return true if gmail is installed
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val stage = modèle.getStageById(stageId!!)
                val courrielEntreprise = stage.courrielEntreprise
                vue.afficherCourrielEntreprise(courrielEntreprise)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    vue.affichermessageErreur(e.message ?: "Courriel introuvable!!!")

                }
            }
        }
    }




    override fun onStageClicked(offreStage: Stage) {
        vue.afficherDetailsStage(offreStage)
    }

    override fun onPostulerClicked(stageId: Int?) {
        Log.INFO
    }


}