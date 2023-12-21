package com.example.stagetech.Modèle

//import com.example.stagetech.Entité.Dispo
import com.example.stagetech.Entité.Étudiant
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoèleEtudiantTest {
//
//    private lateinit var modèle: Étudiant
//    @Before
//    fun setUp() {
//        modèle = Étudiant()
//    }
//
//    @Test
//    fun `étant donné un Modèle Étudiant nouvellement instancié lorsqu'on récupère tout, on obtient null`(){
//        assertEquals(null, modèle.id)
//        assertEquals(null, modèle.nom)
//        assertEquals(null, modèle.prénom)
//        assertEquals(null, modèle.description)
//        assertEquals(null, modèle.adresse)
//        assertEquals(null, modèle.collège)
//        assertEquals(null, modèle.spécialité)
//        assertEquals(null, modèle.disponibilité)
//        assertEquals(null, modèle.bulletinScolaire)
//        assertEquals(null, modèle.Cv)
//        assertEquals(null, modèle.mesApplications)
//        assertEquals(null, modèle.stagesEnregistrés)
//        assertEquals(Dispo.invisible, modèle.état)
//
//    }
//
//    @Test
//    fun `étant donné un Modèle Étudiant nouvellement instancié lorsqu'on récupère son état, on obtient dispo`() {
//
//        modèle.état = Dispo.dispo
//
//        assertEquals( Dispo.dispo, modèle.état )
//    }
//


}