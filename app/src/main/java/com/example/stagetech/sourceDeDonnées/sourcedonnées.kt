package com.example.stagetech.sourceDeDonnées

import com.example.stagetech.Entité.Entreprise
import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage
import com.example.stagetech.R

class sourcedonnées {

    private val listesDeStage: MutableList<Stage> = mutableListOf()
    private val listesDEntreprises: MutableList<Entreprise> = mutableListOf()

    init {

        // Exemple : Ajout de quelques entreprises fictives
        ajouterEntreprise(Entreprise(1, "CWP", "Notre spécialité, c'est d'optimiser! Et nous sommes fiers de notre expertise." +
                " Nous mettons à profit notre intelligence collective pour impacter la vie des gens en améliorant l'efficacité de la mobilité urbaine." +
                " Nos solutions logicielles innovantes et nos services experts dans les domaines du transport collectif et des opérations postales sont " +
                "reconnus partout dans le monde.", R.drawable.cwp, 300,"HR@giro.ca", "https://www.CWPEnergy.com./","Montréal, QC",

            ))
        ajouterEntreprise(Entreprise(2, "workland", "Notre spécialité, c'est d'optimiser! Et nous sommes fiers de notre expertise." +
                " Nous mettons à profit notre intelligence collective pour impacter la vie des gens en améliorant l'efficacité de la mobilité urbaine." +
                " Nos solutions logicielles innovantes et nos services experts dans les domaines du transport collectif et des opérations postales sont " +
                "reconnus partout dans le monde.",
            R.drawable.workland, 300,"HR@giro.ca", "https://www.workland.com/","Montréal, QC",
            ))
//        ajouterEntreprise(Entreprise(3, "Apple", "Notre spécialité, c'est d'optimiser! Et nous sommes fiers de notre expertise." +
//                " Nous mettons à profit notre intelligence collective pour impacter la vie des gens en améliorant l'efficacité de la mobilité urbaine." +
//                " Nos solutions logicielles innovantes et nos services experts dans les domaines du transport collectif et des opérations postales sont " +
//                "reconnus partout dans le monde.", R.drawable.p, 300,"HR@giro.ca", "https://www.Apple.com/","Montréal, QC",
//
//            )
//                )
        ajouterEntreprise(Entreprise(4, "GIRO", "Notre spécialité, c'est d'optimiser! Et nous sommes fiers de notre expertise." +
                " Nous mettons à profit notre intelligence collective pour impacter la vie des gens en améliorant l'efficacité de la mobilité urbaine." +
                " Nos solutions logicielles innovantes et nos services experts dans les domaines du transport collectif et des opérations postales sont " +
                "reconnus partout dans le monde.", R.drawable.giro, 300,"HR@giro.ca", "https://www.giro.com.mx/","Montréal, QC" ,
           ))

        // Exemple : Ajout de quelques offres de stage fictives
//        ajouterOffreStage(
//            Stage(1,"Analyste développeur Python", getEntrepriseById(1), R.drawable.cwp, "Montréal, QC", 1.0,"Nous recherchons actuellement un développeur ou une développeuse " +
//                    "de logiciels intermédiaire/sénior pour compléter notre équipe de Montréal.\n" +
//                "\n" +
//                "Tu souhaites rejoindre l'équipe d'un leader mondial en technologie?", "De développer de nouvelles fonctionnalités dans les applications",
//                "Tu aimes et connais bien la programmation web ou la programmation orienté objet","hybrid")
//                )
//        ajouterOffreStage(
//            Stage(2, "Développeur(euse)logiciel Java", getEntrepriseById(2), R.drawable.workland, "Montréal, QC", 2.0,"Nous recherchons actuellement un développeur ou une développeuse de logiciels intermédiaire/sénior pour compléter notre" +
//                    " équipe de Montréal.\n" +
//                "\n" +
//                "Tu souhaites rejoindre l'équipe d'un leader mondial en technologie?", "De développer de nouvelles fonctionnalités dans les applications",
//                "Tu aimes et connais bien la programmation web ou la programmation orienté objet","télétravail")
//                    )
//        ajouterOffreStage(
//            Stage(3, "Développeur C#", getEntrepriseById(3), R.drawable.p, "Montréal, QC", 2.0,"Nous recherchons actuellement un développeur ou une développeuse\n" +
//                    " de logiciels intermédiaire/sénior pour compléter notre équipe de Montréal.\n" +
//                "\n" +
//                "Tu souhaites rejoindre l'équipe d'un leader mondial en technologie?","De développer de nouvelles fonctionnalités dans les applications",
//                "Tu aimes et connais bien la programmation web ou la programmation orienté objet", "hybrid")
//                    )
//        ajouterOffreStage(
//            Stage(4, " Stagiaire en développement logiciel", getEntrepriseById(4), R.drawable.giro, "Montréal, QC", 2.0,
    //            "GIRO est à la recherche de plusieurs stagiaires en développement de logiciel pour la session d'hiver 2024. Chez GIRO, tu feras partie intégrante d’une équipe de développement et collaborera avec des experts logiciels qui ont une riche expertise dans le domaine du transport en commun et des services postaux. Viens grandir et apprendre parmi nous ! Et qui sait ? Peut-être que tu tomberas en amour avec notre domaine d’expertise et désireras y débuter ta carrière .", "De développer de nouvelles fonctionnalités dans les applications",
//                "Tu aimes et connais bien la programmation web ou la programmation orienté objet","hybrid"
//        ))

    }


    fun ajouterEntreprise(entreprise: Entreprise) {
        listesDEntreprises.add(entreprise)
    }

    fun obtenirEntreprises(): List<Entreprise> {
        return listesDEntreprises
    }

    fun rechercherStages(query: String): List<Stage> {
        // Implémentez la logique de recherche en fonction de la query
        return listesDeStage.filter { it.titreStage.contains(query, ignoreCase = true) }
    }




    fun getDescriptionStageById(stageId: Int?): String? {
        val stage = listesDeStage.find { it.idStage == stageId }
        return stage?.descriptionPoste
    }
    fun getNomEntrepriseById(stageId: Int): String? {
        val entreprise = listesDEntreprises.find { it.idEntreprise == stageId }
        return entreprise?.nomEntreprise
    }


    fun ajouterOffreStage(stage: Stage) {
        listesDeStage.add(stage)
    }


    fun obtenirOffresStages(): List<Stage> {
        return listesDeStage
    }


    // Méthode pour obtenir une offre de stage par ID
    fun getOffreStageById(id: Int?): Stage? {
        return listesDeStage.find { it.idStage == id }
    }

    private fun getEntrepriseById(id: Int): Entreprise? {
        return listesDEntreprises.find { it.idEntreprise == id }
    }




}