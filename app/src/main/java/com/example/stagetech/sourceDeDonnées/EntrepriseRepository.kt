package com.example.stagetech.sourceDeDonnées

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.stagetech.Entité.Entreprise
import com.example.stagetech.R
import com.example.stagetech.sourceDeDonnées.SQLiteHelper.entreprises.COLUMN_ADRESSE
import com.example.stagetech.sourceDeDonnées.SQLiteHelper.entreprises.COLUMN_DESCRIPTION
import com.example.stagetech.sourceDeDonnées.SQLiteHelper.entreprises.COLUMN_EMAIL_HR
import com.example.stagetech.sourceDeDonnées.SQLiteHelper.entreprises.COLUMN_LOGO
import com.example.stagetech.sourceDeDonnées.SQLiteHelper.entreprises.COLUMN_NOM
import com.example.stagetech.sourceDeDonnées.SQLiteHelper.entreprises.COLUMN_NOMBRE_EMPLOYERS
import com.example.stagetech.sourceDeDonnées.SQLiteHelper.entreprises.COLUMN_SITE_WEB

class EntrepriseRepository (private val context: Context){

    private val dbHelper = SQLiteHelper(context)

    fun getEmailRH(nomEntreprise : String): String {
        val db = dbHelper.readableDatabase

        val cursor = db.query(
            "entreprises",
            arrayOf(COLUMN_EMAIL_HR),
            "$COLUMN_NOM  = ?",
            arrayOf(nomEntreprise),
            null,
            null,
            null
        )

        var hrEmail = ""

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(COLUMN_EMAIL_HR)
            hrEmail = cursor.getString(columnIndex)
            //hrEmail = "apple@email.com"
        }
        else {
            hrEmail = "apple@email.com"}

        cursor.close()
        db.close()
        Log.d("EmailDebug", "Company: $nomEntreprise, HR Email: $hrEmail")

        return hrEmail
    }

    fun ajouterEntreprise(entreprise: Entreprise) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOM, entreprise.nomEntreprise)
            put(COLUMN_DESCRIPTION, entreprise.descriptionEntreprise)
            put(COLUMN_LOGO, entreprise.logoEntreprise)
            put(COLUMN_NOMBRE_EMPLOYERS, entreprise.nombreEmployers)
            put(COLUMN_EMAIL_HR, entreprise.emailHR)
            put(COLUMN_SITE_WEB, entreprise.siteWeb)
            put(COLUMN_ADRESSE, entreprise.adresse)
        }

        // Insert the entreprise into the database
        db.insert("entreprises", null, values)

        db.close()
    }

    fun getImageResourceIdFromCompanyName(nomEntreprise: String): Int {
        return when (nomEntreprise.lowercase()) {
            "cwp" -> R.drawable.cwp
            "workland" -> R.drawable.workland
            "apple" -> R.drawable.apple
            "giro" -> R.drawable.giro
            "paypal" -> R.drawable.paypal
            "samsung" -> R.drawable.samsung
            // Add more cases for other companies as needed
            else -> R.drawable.default_logo // Default image for unknown companies
        }}
}