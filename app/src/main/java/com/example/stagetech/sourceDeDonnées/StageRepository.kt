package com.example.stagetech.sourceDeDonnées

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.widget.Toast
import com.example.stagetech.Entité.Étudiant
import com.example.stagetech.R
import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage
import com.example.stagetech.sourceDeDonnées.SQLiteHelper.stages.COLUMN_COMPETENCES
import com.example.stagetech.sourceDeDonnées.SQLiteHelper.stages.COLUMN_COURRIEL_ENTREPRISE
import com.example.stagetech.sourceDeDonnées.SQLiteHelper.stages.COLUMN_DESCRIPTION_POSTE
import com.example.stagetech.sourceDeDonnées.SQLiteHelper.stages.COLUMN_ID
import com.example.stagetech.sourceDeDonnées.SQLiteHelper.stages.COLUMN_LOCALISATION
import com.example.stagetech.sourceDeDonnées.SQLiteHelper.stages.COLUMN_LOGO_ENTREPRISE
import com.example.stagetech.sourceDeDonnées.SQLiteHelper.stages.COLUMN_MODE_STAGE
import com.example.stagetech.sourceDeDonnées.SQLiteHelper.stages.COLUMN_NOM_ENTREPRISE
import com.example.stagetech.sourceDeDonnées.SQLiteHelper.stages.COLUMN_SALAIRE
import com.example.stagetech.sourceDeDonnées.SQLiteHelper.stages.COLUMN_TACHES
import com.example.stagetech.sourceDeDonnées.SQLiteHelper.stages.COLUMN_TITRE_STAGE
import com.example.stagetech.sourceDeDonnées.ServiceApi.Api


class StageRepository (private val serviceApi: Api, private val context: Context){


        private val dbHelper = SQLiteHelper(context)


    suspend fun getStages(): List<Stage> {
        val stageDeMonApi = serviceApi.getStages()
//        stagesFromApi.forEach { stage ->
//            ajouterStageBDLocal(stage)
//        }
        return stageDeMonApi
    }

     suspend fun addEtudiant(etudiant: Étudiant): Boolean {
        return try {
            serviceApi.ajouterEtudiant(etudiant)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getStageById(id: Int): Stage {
        val stageDeMonApi = serviceApi.getStageParId(id)
        ajouterStageBDLocal(stageDeMonApi)
        return stageDeMonApi
    }
    suspend fun SauvegarderStageLocallement(id: Int): Stage {
        val stageDeMonApi = serviceApi.getStageParId(id)
        ajouterStageBDLocal(stageDeMonApi)
        return stageDeMonApi
    }
    suspend fun AjouterStageBDLocalParId(idStage: Int, idEtudiant: Int): Stage {
        val stageDeMonApi = serviceApi.getStageParId(idStage)
        ajouterStageBDLocal(stageDeMonApi)
        return stageDeMonApi
    }
    suspend fun getStagesByTitre(titre: String): List<Stage> {
        return serviceApi.getStageParTitreStage(titre)
    }

    suspend fun getStagesByNomEntreprise(nomEntreprise: String): List<Stage> {
        return serviceApi.getStageParNomEntreprise(nomEntreprise)
    }
    fun ajouterStageBDLocal(stage: Stage) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("titreStage", stage.titreStage)
            put("nomEntreprise", stage.nomEntreprise)
            put("courrielEntreprise", stage.courrielEntreprise)
            put("logoEntreprise", stage.logoEntreprise)
            put("localisation", stage.localisation)
            put("salaire", stage.salaire)
            put("descriptionPoste", stage.descriptionPoste)
            put("taches", stage.taches)
            put("competences", stage.competences)
            put("modeStage", stage.modeStage)


        }
        db.insert("stages", null, values)
        db.close()

}


    fun imagedeLentreprise(nomEntreprise: String): Int {
        return when (nomEntreprise.lowercase()) {
            "cwp" -> R.drawable.cwp
            "workland" -> R.drawable.workland
            "apple" -> R.drawable.apple
            "giro" -> R.drawable.giro
            "paypal" -> R.drawable.paypal
            "samsung" -> R.drawable.samsung
            "desjardins" -> R.drawable.samsung
            "rbc" -> R.drawable.samsung
            else -> R.drawable.default_logo
        }}




    fun voirTousLesStagesBDLocal(): List<Stage> {
        val stages = mutableListOf<Stage>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM stages", null)

        try {
            while (cursor.moveToNext()) {
                val idIndex = cursor.getColumnIndex("id")
                val titreStageIndex = cursor.getColumnIndex("titreStage")
                val nomEntrepriseIndex = cursor.getColumnIndex("nomEntreprise")
                val courrielEntrepriseIndex = cursor.getColumnIndex("courrielEntreprise")
                val logoEntrepriseIndex = cursor.getColumnIndex("logoEntreprise")
                val localisationIndex = cursor.getColumnIndex("localisation")
                val salaireIndex = cursor.getColumnIndex("salaire")
                val descriptionPosteIndex = cursor.getColumnIndex("descriptionPoste")
                val tachesIndex = cursor.getColumnIndex("taches")
                val competencesIndex = cursor.getColumnIndex("competences")
                val modeStageIndex = cursor.getColumnIndex("modeStage")

                if (idIndex >= 0 && titreStageIndex >= 0 && nomEntrepriseIndex >= 0 && logoEntrepriseIndex >= 0
                    && localisationIndex >= 0 && salaireIndex >= 0 && descriptionPosteIndex >= 0
                    && tachesIndex >= 0 && competencesIndex >= 0 && modeStageIndex >= 0
                ) {
                    stages.add(
                        Stage(
                            idStage = cursor.getInt(idIndex),
                            titreStage = cursor.getString(titreStageIndex),
                            nomEntreprise = cursor.getString(nomEntrepriseIndex),
                            courrielEntreprise = cursor.getString(courrielEntrepriseIndex),
                            logoEntreprise = cursor.getInt(logoEntrepriseIndex),
                            localisation = cursor.getString(localisationIndex),
                            salaire = cursor.getDouble(salaireIndex),
                            descriptionPoste = cursor.getString(descriptionPosteIndex),
                            taches = cursor.getString(tachesIndex),
                            competences = cursor.getString(competencesIndex),
                            modeStage = cursor.getString(modeStageIndex)
                        )
                    )
                } else {
                    Toast.makeText(context, "liste vide", Toast.LENGTH_SHORT).show()
                }
            }
        } finally {
            cursor.close()
            db.close()
        }

        return stages
    }
    fun getAllStagesBDLocal(): List<Stage> {
        val stages = mutableListOf<Stage>()
        val cursor = dbHelper.readableDatabase.query("stages", null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            stages.add(cursor.toStage())
        }

        cursor.close()
        return stages
    }
    fun searchStagesByTitleBDLocal(title: String): List<Stage> {
        val stages = mutableListOf<Stage>()
        val cursor = dbHelper.readableDatabase.query(
            "stages",
            null,
            "$COLUMN_TITRE_STAGE LIKE ?",
            arrayOf("%$title%"),
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            stages.add(cursor.toStage())
        }

        cursor.close()
        return stages
    }

    fun searchStagesByCompanyBDLocal(company: String): List<Stage> {
        val stages = mutableListOf<Stage>()
        val cursor = dbHelper.readableDatabase.query(
            "stages",
            null,
            "$COLUMN_NOM_ENTREPRISE LIKE ?",
            arrayOf("%$company%"),
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            stages.add(cursor.toStage())
        }

        cursor.close()
        return stages
    }

    fun Cursor.toStage(): Stage {
        return Stage(

            idStage = getInt(getColumnIndexOrThrow(COLUMN_ID)),
            titreStage = getString(getColumnIndexOrThrow(COLUMN_TITRE_STAGE)),
            nomEntreprise = getString(getColumnIndexOrThrow(COLUMN_NOM_ENTREPRISE)),
            courrielEntreprise = getString(getColumnIndexOrThrow(COLUMN_COURRIEL_ENTREPRISE)),
            logoEntreprise = getInt(getColumnIndexOrThrow(COLUMN_LOGO_ENTREPRISE)),
            localisation = getString(getColumnIndexOrThrow(COLUMN_LOCALISATION)),
            salaire = getDouble(getColumnIndexOrThrow(COLUMN_SALAIRE)),
            descriptionPoste = getString(getColumnIndexOrThrow(COLUMN_DESCRIPTION_POSTE)),
            taches = getString(getColumnIndexOrThrow(COLUMN_TACHES)),
            competences = getString(getColumnIndexOrThrow(COLUMN_COMPETENCES)),
            modeStage = getString(getColumnIndexOrThrow(COLUMN_MODE_STAGE))
        )
    }

     fun getStageByIdBDLocal(stageId: Int?): Stage {

        val db = dbHelper.readableDatabase
        var stage: Stage? = null

        val projection = arrayOf(
            "idStage",
            "titreStage",
            "nomEntreprise",
            "courrielEntreprise",
            "logoEntreprise",
            "localisation",
            "salaire",
            "descriptionPoste",
            "taches",
            "competences",
            "modeStage"
            // Add more fields as needed
        )

        val selection = "idStage = ?"
        val selectionArgs = arrayOf(stageId.toString())

        val cursor = db.query(
            "stages",  // Replace with your table name
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        cursor.use {
            if (it.moveToFirst()) {
                stage = cursor.toStage()
            }
        }

        return stage!!
    }
}