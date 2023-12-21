package com.example.stagetech.Étudiant.CéerÉtudiant.Présentateur

import android.content.ContentValues.TAG
import android.util.Log
import com.example.stagetech.Entité.Étudiant
import com.example.stagetech.sourceDeDonnées.ÉtudiantRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


class ÉtudiantPrésentateur(
    private val repository: ÉtudiantRepository,
    private val vue: IÉtudiant.Vue
) : IÉtudiant.Présentateur {

    override fun getEtudiantParCourriel(courriel: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                repository.getEtudiantParCourriel(courriel)
                vue.affichermessageSucces()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    vue.affichermessageErreur(e.message ?: "ERREUR!")
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    vue.affichermessageErreur("ERREUR Network : ${e.message}")
                    Log.e(TAG, "ERREUR Network", e)
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    vue.affichermessageErreur("ERREUR HTTP : ${e.message}")
                    e.printStackTrace()
                    Log.e(TAG, "ERREUR HTTP ", e)
                    val statusCode = e.code() // Retrieve the HTTP status code
                    vue.affichermessageErreur("ERREUR HTTP $statusCode: ${e.message}")
                    Log.e(TAG, "ERREUR HTTP $statusCode", e)
                }
            }
        }

    }


    override fun ajouterNouveauÉtudiantsÀApi2(
        nom: String,
        prenom: String,
        courriel: String,
        motDePasse: String
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
//                if (repository.getEtudiantParCourriel(courriel)!= null){
//                    vue.affichermessageErreur("ERREUR Courriel exist deja!")
//                }else{

                repository.ajouterNouveauÉtudiantsÀApi(nom, prenom, courriel, motDePasse)
                vue.affichermessageSucces()
                //}
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    vue.affichermessageErreur(e.message ?: "ERREUR!")
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    vue.affichermessageErreur("ERREUR Network : ${e.message}")
                    Log.e(TAG, "ERREUR Network", e)
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    vue.affichermessageErreur("ERREUR HTTP : ${e.message}")
                    Log.e(TAG, "ERREUR HTTP", e)
                }
            }
        }

    }

    override fun ajouterÉtudiant(étudiant: Étudiant) {
        //vue.afficherChargement()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                repository.ajouterÉtudiantsÀApi(étudiant)
                Log.d("ApiService", "add student: $étudiant")
                vue.affichermessageSucces()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    vue.affichermessageErreur(e.message ?: "ERREUR!")
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    vue.affichermessageErreur("ERREUR Network : ${e.message}")
                    Log.e(TAG, "ERREUR Network", e)
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    vue.affichermessageErreur("ERREUR HTTP : ${e.message}")
                    Log.e(TAG, "ERREUR HTTP ", e)
                }
            }
        }
        // vue.cacherChargement()
    }

}

//    override fun CreerCompteÉtudiant(nom: String, prénom: String, description: String) {
//        repository.CreerCompteÉtudiant(nom,prénom,description)
//        vue.ApresAjoutEtudiant()
//    }

//    override fun VoirÉtudiants() {
//        vue.VoirÉtudiants(étudiants)
//
//    }
//


//    override fun AjouterÉtudiant(
//        nom: String,
//        prénom: String,
//        description: String,
//        courriel: String,
//        adresse: String,
//        collège: String,
//        spécialité: String,
//        disponibilité: String,
//        bulletinScolaire: String,
//        Cv: String,
//        mesApplication: MutableList<Stage>,
//        stagesEnregistrés: MutableList<Stage>
//    ) {
////        val idÉtudiant = dbRéf.push().key!!
//        val étudiant = Étudiant(idÉtudiant, nom, prénom, description, courriel, adresse, collège, spécialité, disponibilité, bulletinScolaire, Cv,mesApplication, stagesEnregistrés )
////        étudiants.add(étudiant)
////
//
//        dbRéf.child(idÉtudiant).setValue(étudiant)
//            .addOnSuccessListener {
//                vue.affichermessageCreationcompteAvecSucces(étudiant)
//            }
//            .addOnFailureListener {
//                vue.affichermessageErreur("Erreur: ${it.message}")
//            }

//  }

//    override fun CreerCompteÉtudiant( nom: String,
//                                      prénom: String,
//                                      courriel: String,){
//
//        if (nom.isEmpty() || prénom.isEmpty() || courriel.isEmpty()) {
//            vue.affichermessageErreur("Veulliez remplir toutes les champs.")
//            return
//        }
//        val id_Étudiant = dbRéf.push().key!!
//        // Create a new student object
//        val étudiant = Étudiant(
//            idÉtudiant =id_Étudiant,
//            nom = nom,
//            prénom = prénom,
//            description = "",
//            courriel = courriel,
//            adresse = "",
//            collège = "",
//            spécialité = "",
//            disponibilité = "",
//            bulletinScolaire = "",
//            Cv = "",
//            )
//        dbRéf.child(id_Étudiant).setValue(étudiant)
//            .addOnSuccessListener {
//                vue.affichermessageCreationcompteAvecSucces(étudiant)
//            }
//            .addOnFailureListener {
//                vue.affichermessageErreur("Erreur: ${it.message}")
//            }
//
//    }
//
//    override fun supprimerÉtudiant(idÉtudiant: String) {
//        val étudiantSupprimer = étudiants.find { it.idÉtudiant == idÉtudiant }
//        if (étudiantSupprimer != null) {
//            étudiants.remove(étudiantSupprimer)
//            vue.VoirÉtudiants(étudiants)
//        } else {
//            vue.affichermessageErreur("Cet Étudiant ne se figure pas dans notre listes d'utilisateurs!")
//        }
//    }
//        override fun modifierÉtudiant(étudiant: Étudiant) {
//        val étudiantÀModifier = étudiants.find { it.idÉtudiant == étudiant.idÉtudiant }
//        if (étudiantÀModifier != null) {
//            étudiantÀModifier.nom = étudiant.nom
//            étudiantÀModifier.prénom = étudiant.prénom
//            étudiantÀModifier.courriel = étudiant.courriel
//            étudiantÀModifier.adresse = étudiant.adresse
//            étudiantÀModifier.collège = étudiant.collège
//            étudiantÀModifier.spécialité = étudiant.spécialité
//            étudiantÀModifier.disponibilité = étudiant.disponibilité
//            étudiantÀModifier.bulletinScolaire = étudiant.bulletinScolaire
//            étudiantÀModifier.Cv = étudiant.Cv
//            vue.VoirÉtudiants(étudiants)
//        } else {
//            vue.affichermessageErreur("Cet Étudiant ne se figure pas dans notre listes d'utilisateurs!" )
//        }
//    }


