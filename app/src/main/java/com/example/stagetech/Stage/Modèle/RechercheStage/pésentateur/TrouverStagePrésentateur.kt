package com.example.stagetech.Stage.Modèle.RechercheStage.pésentateur

import android.content.ContentValues.TAG
import android.util.Log
import com.example.stagetech.R
import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage
import com.example.stagetech.sourceDeDonnées.StageRepository
import com.example.stagetech.Étudiant.Session
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


class TrouverStagePrésentateur (private val vue : ITrouverStagePresentateur.vue,
                                private val repository: StageRepository,
                                ): ITrouverStagePresentateur.présentateur {

    //private val repository: StageRepository,


    override fun afficherTousLesStages() {
        vue.afficherChargement()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val stages = repository.getStages()
                vue.afficherListeStages(stages)

            }

         catch (e: HttpException) {
            withContext(Dispatchers.Main) {
                vue.affichermessageErreur("HTTP error: ${e.message}")
                Log.e(TAG, "HTTP error", e)
            }
        }finally {
                withContext(Dispatchers.Main) {
                    Log.d(TAG, "Hiding loading indicator...")
                    vue.cacherChargement()
                    Log.d(TAG, "Loading indicator hidden.")
                }
            }
        }

    }



    override fun trouverStageParTitreStage(titreStage: String) {
        //vue.afficherChargement()
        CoroutineScope(Dispatchers.Main).launch  {
            try {
                val stagesParTitre = repository.getStagesByTitre(titreStage)
                vue.afficherListeStages(stagesParTitre)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    //vue.affichermessageErreur(e.message ?: "ERREUR!")
                }
            }
            finally {
                vue.cacherChargement()
            }
        }
    }

    override fun trouverStageParNomEntreprise(nomEntreprise: String) {
        vue.afficherChargement()
        CoroutineScope(Dispatchers.Main).launch  {
            try {
        val listStagesParNomEntreprise = repository.getStagesByNomEntreprise(nomEntreprise)
        vue.afficherListeStages(listStagesParNomEntreprise)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    vue.affichermessageErreur(e.message ?: "ERREUR!")

                }
            }finally {
                vue.cacherChargement()
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

    override fun SauvegarderStageLocallement(stage: Stage) {
        try {
        repository.ajouterStageBDLocal(stage)

            vue.SauvegarderStageLocallement(stage)
            Log.e(TAG,stage.titreStage+ "Stage sauvegardé!")

            vue.affichermessageSauvegardeSucces()

        }catch (e: Exception) {
            vue.affichermessageErreur(e.message ?: "$stage ERREUR!")

        }

        }
}