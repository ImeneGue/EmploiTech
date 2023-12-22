package com.example.stagetech.Étudiant.profile.Présentateur

import android.content.ContentValues
import android.util.Log
import com.example.stagetech.Entité.Étudiant
import com.example.stagetech.sourceDeDonnées.ÉtudiantRepository
import com.example.stagetech.Étudiant.Session
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import androidx.lifecycle.lifecycleScope
import com.example.stagetech.Entité.ÉtudiantResponse


class ÉtudiantProfilePrésentateur( private val repository: ÉtudiantRepository,
                                   private val session: Session,
                                   private val vue: IÉtudiantPrésentateur.Vue) : IÉtudiantPrésentateur.Présentateur {

override suspend fun getÉtudiantsDeApiParId3(id: Int): Étudiant? {
    return withContext(Dispatchers.Main) {
        try {
            val etudiantResponse: ÉtudiantResponse? = repository.getÉtudiantsDeApiParId3(id)
            if (etudiantResponse != null) {
                val etudiant= etudiantResponse.etudiant

                session.idEtudiant = etudiant.idEtudiant

                vue.affichermessageSucces("Connection avec success: name is ${etudiant.nom} and session is ${session.idEtudiant}")
                etudiant
            }
            else {
                null
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
            vue.affichermessageErreur(e.message ?: "ERREUR exception1 de getÉtudiantsDeApiParId!")}
            null
        } catch (e: IOException) {
            vue.affichermessageErreur("ERREUR exception2 de getÉtudiantsDeApiParId!  ${e.message}")
            null
        }
    }
}
    override suspend fun mettreAJourÉtudiantsApi(id: Int, modifierEtudiant :Étudiant ){
        try {
            val success = repository.modifierEtudiant(id, modifierEtudiant)

            if (success) {
                vue.affichermessageSucces("Profile modifié")
            } else {
                vue.affichermessageErreur("Erreur! ")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            vue.affichermessageErreur("Erreur!")
        }

    }

}

