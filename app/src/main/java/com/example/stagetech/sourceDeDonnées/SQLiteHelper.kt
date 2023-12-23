package com.example.stagetech.sourceDeDonnées

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage

class SQLiteHelper (context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "stageTech"
        private const val DATABASE_VERSION = 5

    }
    object etudiants {
        const val TABLE_NAME = "etudiants"
        const val COLUMN_ID = "idEtudiant"
        const val COLUMN_NOM = "nom"
        const val COLUMN_PRENOM = "prenom"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_COURRIEL = "courriel"
        const val COLUMN_ADRESSE = "adresse"
        const val COLUMN_COLLEGE = "college"
        const val COLUMN_SPECIALITE = "spécialite"
        const val COLUMN_DISPONIBILITE = "disponibilite"
        const val COLUMN_BULLETIN_SCOLAIRE = "bulletinScolaire"
        const val COLUMN_CV = "cv"



        const val CREATE_TABLE_ÉTUDIANT = """
        CREATE TABLE $TABLE_NAME (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_NOM TEXT NOT NULL,
            $COLUMN_PRENOM TEXT NOT NULL,
            $COLUMN_DESCRIPTION TEXT,
            $COLUMN_COURRIEL TEXT NOT NULL,
            $COLUMN_ADRESSE TEXT,
            $COLUMN_COLLEGE TEXT,
            $COLUMN_SPECIALITE TEXT,
            $COLUMN_DISPONIBILITE TEXT,
            $COLUMN_BULLETIN_SCOLAIRE TEXT,
            $COLUMN_CV TEXT
            )
    """
    }
    object stages {
        const val TABLE_NAME = "stages"
        const val COLUMN_ID = "idStage"
        const val COLUMN_TITRE_STAGE = "titreStage"
        const val COLUMN_NOM_ENTREPRISE = "nomEntreprise"
        const val COLUMN_COURRIEL_ENTREPRISE = "courrielEntreprise"
        const val COLUMN_LOGO_ENTREPRISE = "logoEntreprise"
        const val COLUMN_LOCALISATION = "localisation"
        const val COLUMN_SALAIRE = "salaire"
        const val COLUMN_DESCRIPTION_POSTE = "descriptionPoste"
        const val COLUMN_TACHES = "taches"
        const val COLUMN_COMPETENCES = "competences"
        const val COLUMN_MODE_STAGE = "modeStage"

        const val CREATE_TABLE_STAGE = """
        CREATE TABLE $TABLE_NAME (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_TITRE_STAGE TEXT,
            $COLUMN_NOM_ENTREPRISE TEXT,
            $COLUMN_COURRIEL_ENTREPRISE TEXT,
            $COLUMN_LOGO_ENTREPRISE INTEGER,
            $COLUMN_LOCALISATION TEXT,
            $COLUMN_SALAIRE REAL,
            $COLUMN_DESCRIPTION_POSTE TEXT,
            $COLUMN_TACHES TEXT,
            $COLUMN_COMPETENCES TEXT,
            $COLUMN_MODE_STAGE TEXT
        )
    """
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(etudiants.CREATE_TABLE_ÉTUDIANT)
        db?.execSQL(stages.CREATE_TABLE_STAGE)
           }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${etudiants.TABLE_NAME}")
        db?.execSQL("DROP TABLE IF EXISTS ${stages.TABLE_NAME}")
        onCreate(db)    }



}