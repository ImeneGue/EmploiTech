package com.example.stagetech.Étudiant.CéerÉtudiant.Modèle

import com.example.stagetech.Entité.Étudiant

interface IÉtudiantModèle {

        fun CréerÉtudiant(étudiant: Étudiant)
        fun supprimerÉtudiant(id: Int)
        fun modifierÉtudiant(étudiant: Étudiant)

}