package com.example.stagetech.sourceDeDonnées

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.stagetech.Entité.Étudiant
import com.example.stagetech.Entité.ÉtudiantResponse
import com.example.stagetech.sourceDeDonnées.ServiceApi.Api
import com.example.stagetech.Étudiant.Session
import okhttp3.Response
import okhttp3.ResponseBody
import java.net.HttpCookie.parse

class ÉtudiantRepository (private val serviceApi: Api, private val context: Context){

    private val dbHelper = SQLiteHelper(context )
    val session = Session(context)

    suspend fun getÉtudiantsDeApi(): List<Étudiant> {
        val étudiantDeMonApi = serviceApi.getEtudiants()
//        stagesFromApi.forEach { stage ->
//            ajouterStageBDLocal(stage)
//        }
        return étudiantDeMonApi
    }



    suspend fun getEtudiantParCourriel(courriel: String){
        serviceApi.getEtudiantParCourriel(courriel)
    }

    suspend fun seConnecter(courriel: String, motDePasse: String){
        serviceApi.seConnecter(courriel,motDePasse)
    }
    suspend fun modifierEtudiant(id: Int, updatedEtudiant: Étudiant): Boolean {
        try {
            val response = serviceApi.modifierEtudiant(id, updatedEtudiant)

            return response.isSuccessful
        } catch (e: Exception) {
            // Handle exceptions
            e.printStackTrace()
        }

        return false
    }



//    suspend fun seConnecter2(courriel: String, motDePasse: String): Étudiant? {
//        try {
//            val response = serviceApi.seConnecter(courriel, motDePasse)
//
//            if (response.isSuccessful) {
//                val etudiant = response.body()
//                etudiant?.etudioant?.idEtudiant?.let {
//                    //etudiant?.let {
//                    //session.idEtudiant = it.idEtudiant
//                    session.idEtudiant = it
//                }
//                return etudiant
//            }
//        } catch (e: Exception) {
//
//        }
//        return null
//    }

// suspend fun getÉtudiantsDeApiParId(id:Int): Étudiant? {
//
//    try {
//        val étudiantDeMonApi = serviceApi.getEtudiantParId(id)
//        if (étudiantDeMonApi != null) {
//            étudiantDeMonApi?.idEtudiant?.let {
//
//                if (session != null){
//                    session.idEtudiant = it}
//                Log.e("Repository", "API Error" )
//            }
//            return étudiantDeMonApi
//        }else {
//            Log.e("Repository", "API Error" )
//        }
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//    return null
//
//}
    suspend fun getÉtudiantsDeApiParId3(id:Int): ÉtudiantResponse? {

        try {
            val response = serviceApi.getEtudiantParId2(id)


            if (response.isSuccessful) {
                val étudiantDeMonApi = response.body()
                étudiantDeMonApi?.etudiant?.idEtudiant?.let {

                    if (session != null){session.idEtudiant = it}
                }
                return étudiantDeMonApi


            }else {
                Log.e("Repository", "API Error" )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null

    }
    suspend fun seConnecter2(courriel: String, motDePasse: String): ÉtudiantResponse? {
        try {
            val response = serviceApi.seConnecter(courriel, motDePasse)
            if (response.isSuccessful) {
                val etudiant = response.body()
                etudiant?.etudiant?.idEtudiant?.let {

                    if (session != null){session.idEtudiant = it}
                }
                return etudiant
            }else {
                val errorMessage = response.errorBody()?.string()
                Log.e("Repository", "API Error: $errorMessage")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    suspend fun ajouterNouveauÉtudiantsÀApi(nom: String,prenom: String, courriel: String, motDePasse: String) {

                val nouveauCompte = Étudiant(
                idEtudiant = null,
                nom = nom,
                prenom = prenom,
                motDePasse = motDePasse,
                description = "",
                courriel = courriel,
                adresse = "",
                college = "",
                specialite = "",
                bulletinScolaire = "",
                cv = "",
                disponibilite = ""
            )
            serviceApi.ajouterEtudiant(nouveauCompte)
//            if (response.isSuccessful) {
//
//                println("Compte ajouté avec succees.")
//            } else {
//                println("Erreur adding student: ${response.code()} - ${response.message()}")
//            }
//        } else {
//            println("Compte avec ce courriel exist deja.")
//        }
        }

    suspend fun ajouterÉtudiantsÀApi(étudiant: Étudiant) {

        val étudiantÀApi = serviceApi.ajouterEtudiant(étudiant)
//        stagesFromApi.forEach { stage ->
//            ajouterStageBDLocal(stage)
//        }

    }



    fun ajouterÉtudiant(étudiant: Étudiant) {
        val db = dbHelper.writableDatabase
        db.beginTransaction()

        val values = ContentValues().apply {
            put("nom", étudiant.nom)
            put("prénom", étudiant.prenom)
            put("motDePasse", étudiant.motDePasse)
            put("description", étudiant.description)
            put("courriel", étudiant.courriel)
            put("adresse", étudiant.adresse)
            put("collège", étudiant.college)
            put("spécialité", étudiant.specialite)
            put("disponibilité", étudiant.disponibilite)
            put("bulletinScolaire", étudiant.bulletinScolaire)
            put("Cv", étudiant.cv)

        }
        db.insert("étudiants", null, values)
        db.setTransactionSuccessful()
        db.endTransaction()
        db.close()
    }

//    private fun ajouterStageÀMesApplications(étudiantId: Int, stageId: Int) {
//        val db = dbHelper.writableDatabase
//        val values = ContentValues().apply {
//            put(COLUMN_ÉTUDIANT_ID, étudiantId)
//            put(COLUMN_STAGE_ID, stageId)
//        }
//
//        // Insert the association into the table linking étudiants to stages (assuming you have such a table)
//        db.insert(TABLE_ÉTUDIANTS_STAGES, null, values)
//
//        db.close()
//    }







//    fun getÉtudiants(): List<Étudiant> {
//        val étudiants = mutableListOf<Étudiant>()
//        val db = dbHelper.readableDatabase
//        val cursor = db.rawQuery("SELECT * FROM ${SQLiteHelper.étudiants.TABLE_NAME}", null)
//
//        while (cursor.moveToNext()) {
//            // Map cursor values to Etudiant properties
//            val étudiant = Étudiant(/* Map cursor values */)
//            étudiants.add(étudiant)
//        }
//
//        cursor.close()
//        db.close()
//        return étudiants}
    }

