package com.example.stagetech.sourceDeDonnées.ServiceApi

import com.example.stagetech.Entité.Étudiant
import com.example.stagetech.Entité.ÉtudiantResponse
import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage
import okhttp3.Call
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface Api {



    @GET("/stages")
    suspend fun getStages(): List<Stage>

    @GET("/etudiants")
    suspend fun getEtudiants(): List<Étudiant>

    @POST("/etudiants")
    suspend fun ajouterEtudiant(@Body etudiant: Étudiant): retrofit2.Response<ResponseBody>
    @GET("/etudiants/courriel/{courriel}")
    suspend fun getEtudiantParCourriel(@Path("courriel") courriel: String): Étudiant
    @GET("/etudiants/{idEtudiant}")
    suspend fun getEtudiantParId(@Path("idEtudiant") id: Int): Étudiant

    @GET("/etudiants/{idEtudiant}")
    suspend fun getEtudiantParId2(@Path("idEtudiant") id: Int): retrofit2.Response<ÉtudiantResponse>
    @PUT("/etudiants/modifier/{idEtudiant}")
    suspend fun modifierEtudiant(@Path("idEtudiant") id: Int, @Body etudiant: Étudiant): retrofit2.Response<Étudiant>
    @GET("/stages/{id}")
    suspend fun getStageParId(@Path("id") id: Int): Stage

    @GET("/stages/stage/{titreStage}")
    //suspend fun getStageParTitreStage(@Query("titreStage") titreStage: String): List<Stage>
    suspend fun getStageParTitreStage(@Path("titreStage") titreStage: String): List<Stage>

    @GET("/stages/entreprise/{nomEntreprise}")
    suspend fun getStageParNomEntreprise(@Path("nomEntreprise") nomEntreprise: String): List<Stage>
    @DELETE("etudiants/{id}")
    suspend fun supprimerEtudiant(@Path("id") etudiantId: Int): Response

    @DELETE("stages/{id}")
    suspend fun supprimerStage(@Path("id") stageId: Int): Response

    @POST("/stages")
    suspend fun enregistrerStage(@Body stage: Stage): Stage

    @FormUrlEncoded
    @POST("/etudiants/seconnecter")
    suspend fun seConnecter(
        @Field("courriel") courriel: String,
        @Field("motDePasse") motDePasse: String
    ):retrofit2.Response<ÉtudiantResponse>
    // ):retrofit2.Response<Étudiant>
}