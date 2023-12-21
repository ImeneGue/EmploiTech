package com.example.stagetech.Étudiant.profile.Présentateur

import com.example.stagetech.Entité.Étudiant
import com.example.stagetech.Entité.ÉtudiantResponse

interface IÉtudiantPrésentateur {
    interface Vue {
        fun affichermessageSucces(message: String)
        fun affichermessageErreur(s: String)


    }
    interface Présentateur {

//        suspend fun getÉtudiantsDeApiParId(id: Int): ÉtudiantResponse?
        suspend fun getÉtudiantsDeApiParId3(id: Int): Étudiant?

        suspend fun mettreAJourÉtudiantsApi(id: Int, updatedEtudiant: Étudiant)
    }


}