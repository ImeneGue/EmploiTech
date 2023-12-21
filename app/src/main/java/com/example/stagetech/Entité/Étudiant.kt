package com.example.stagetech.Entité

import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage

data class Étudiant(

    val idEtudiant: Int?,
    var nom: String,
    var prenom: String,
    var motDePasse: String,
    var description: String,
    var courriel: String,
    var adresse: String,
    var college: String,
    var specialite: String,
    var disponibilite: String,
    var bulletinScolaire: String,
    var cv: String,
    var mesApplications: MutableList<Stage> = mutableListOf(),
    var stagesEnregistrés: MutableList<Stage> = mutableListOf()



 )

