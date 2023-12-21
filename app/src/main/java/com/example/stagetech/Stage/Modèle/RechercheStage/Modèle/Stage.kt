package com.example.stagetech.Stage.Modèle.RechercheStage.Modèle

import com.example.stagetech.Entité.Entreprise

data class Stage (
    val idStage: Int?,
    val titreStage: String,
    val nomEntreprise: String,
    val courrielEntreprise: String,
    val logoEntreprise: Int,
    val localisation: String,
    val salaire: Double,
    val descriptionPoste: String,
    val taches: String,
    val competences: String,
    val modeStage: String,
  //  var etudiantsPostules: MutableList<Etudiant> = mutableListOf(),
){


}