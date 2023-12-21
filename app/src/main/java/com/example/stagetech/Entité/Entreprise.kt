package com.example.stagetech.Entité

import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage

data class Entreprise (
    val idEntreprise: Int?,
    val nomEntreprise: String,
    val descriptionEntreprise: String,
    val logoEntreprise: Int,
    val nombreEmployers: Int,
    val emailHR : String,
    val siteWeb: String,
    val adresse: String,
    val stagesOfferts: MutableList<Stage>? = mutableListOf()


)