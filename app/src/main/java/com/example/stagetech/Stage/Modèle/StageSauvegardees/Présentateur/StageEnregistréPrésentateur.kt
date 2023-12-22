package com.example.stagetech.Stage.Modèle.StageSauvegardees.Présentateur

import android.content.ContentValues
import android.util.Log
import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage
import com.example.stagetech.sourceDeDonnées.StageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class StageEnregistréPrésentateur (private val vue : IstageEnregistréContrat.vue,
    private val repository: StageRepository): IstageEnregistréContrat.Présentateur{


    override fun afficherTousLesStagesDeBDLocal() {
        vue.afficherChargement()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val stages = repository.voirTousLesStagesBDLocal()
                vue.afficherListeStages(stages)
            }

            catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    vue.affichermessageErreur("HTTP error: ${e.message}")
                    Log.e(ContentValues.TAG, "HTTP error", e)
                }
            }finally {
                withContext(Dispatchers.Main) {
                    vue.cacherChargement()
                }
            }
        }

    }

    override fun stageSelectionné(stageId: Int) {
        vue.afficherDetails(stageId)
    }

    override fun stageClické(offreStage: Stage) {
        vue.afficherDetailsStage(offreStage)
    }

    override fun detailsStageClické(offreStage: Stage) {
        vue.afficherDetailsStage(offreStage)
    }

    override fun supprimerStageParId(stageId : Int){
        repository.supprimerStageParId(stageId)
    }

}