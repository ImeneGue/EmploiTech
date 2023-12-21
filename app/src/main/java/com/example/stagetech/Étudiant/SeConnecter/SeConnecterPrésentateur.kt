package com.example.stagetech.Étudiant.SeConnecter

import android.content.ContentValues
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.stagetech.Entité.Étudiant
import com.example.stagetech.Entité.ÉtudiantResponse
import com.example.stagetech.sourceDeDonnées.ÉtudiantRepository
import com.example.stagetech.Étudiant.Session
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Response
import java.io.IOException

class SeConnecterPrésentateur(private val repository: ÉtudiantRepository,
                              private val session: Session,
                              private val vue: ISeConnecterPrésentateur.Vue) : ISeConnecterPrésentateur.Présentateur {
    override fun seConnecter(courriel: String, motDePasse: String) {
        TODO("Not yet implemented")
    }

    override fun seConnecterResponseBody(courriel: String, motDePasse: String) {

        CoroutineScope(Dispatchers.Main).launch {
            try {

                val reponse : ÉtudiantResponse? = repository.seConnecter2(courriel, motDePasse)
                //val response: Étudiant? = repository.seConnecter2(courriel, motDePasse)
                Log.d("Presenter", "Etudiant: $reponse")

                if (reponse != null) {
                    val etudiant: Étudiant? = reponse.etudiant

                    if (session?.idEtudiant != null){
                        session.idEtudiant = etudiant?.idEtudiant
                    }


                    //vue.affichermessageSucces("Connection avec success ${etudiant?.nom} and session is ${session.idEtudiant}")
                    if (etudiant != null) {
                        vue.apresConnection(etudiant)
                    }
                } else {
                    vue.affichermessageErreur("ERREUR! pas de compte avec le courriel : $courriel")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    vue.affichermessageErreur("ERREUR Exception")
                    Log.d("exception","E")
                    e.printStackTrace()
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    //vue.affichermessageErreur("ERREUR Network")
                    Log.e(ContentValues.TAG, "ERREUR Network", e)
                    e.printStackTrace()

                }
            }

        }
    }



}