const express = require('express');
const bodyParser = require('body-parser');
const sqlite3 = require('sqlite3').verbose();

const app = express();
const port = 3000;

app.use(bodyParser.json());

app.use(bodyParser.urlencoded({ extended: true }));

app.disable("x-powered-by");
app.use(express.urlencoded({ extended: false }));
app.use(express.json());

const db = new sqlite3.Database('stageTech.db');

db.run(`
CREATE TABLE IF NOT EXISTS stages (
    idStage INTEGER PRIMARY KEY AUTOINCREMENT,
    titreStage TEXT,
    nomEntreprise TEXT,
    courrielEntreprise TEXT,
    logoEntreprise INTEGER,
    localisation TEXT,
    salaire REAL,
    descriptionPoste TEXT,
    taches TEXT,
    competences TEXT,
    modeStage TEXT
    )
`);

db.run(`
  CREATE TABLE IF NOT EXISTS etudiants (
    idEtudiant INTEGER PRIMARY KEY AUTOINCREMENT,
    nom TEXT NOT NULL,
    prenom TEXT NOT NULL,
    motDePasse TEXT NOT NULL,
    description TEXT,
    courriel TEXT NOT NULL,
    adresse TEXT,
    college TEXT,
    specialite TEXT,
    disponibilite TEXT,
    bulletinScolaire TEXT,
    cv TEXT
    )
`);
app.get('/stages', (req, res) => {
    db.all('SELECT * FROM stages', (err, rows) => {
      if (err) {
        res.status(500).json({ error: err.message });
        return;
      }
      res.json(rows);
    });
  });
  
  app.post('/stages', (req, res) => {
    const {
      titreStage,
      nomEntreprise,
      courrielEntreprise,
      logoEntreprise,
      localisation,
      salaire,
      descriptionPoste,
      taches,
      competences,
      modeStage,
    } = req.body;
  
    db.run(
      `INSERT INTO stages 
        (titreStage, nomEntreprise, courrielEntreprise, logoEntreprise, localisation, salaire, descriptionPoste, taches, competences, modeStage) 
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)`,
      [
        titreStage,
        nomEntreprise,
        courrielEntreprise,
        logoEntreprise,
        localisation,
        salaire,
        descriptionPoste,
        taches,
        competences,
        modeStage,
      ],
      function (err) {
        if (err) {
          res.status(500).json({ error: err.message });
          return;
        }
        res.json({ idStage: this.lastID });
      }
    );
  });
  
  // Endpoints for etudiants
  app.get('/etudiants', (req, res) => {
    db.all('SELECT * FROM etudiants', (err, rows) => {
      if (err) {
        res.status(500).json({ error: err.message });
        return;
      }
      res.json(rows);
    });
  });

  app.get('/etudiants/:idEtudiant', (req, res) => {
    const idEtudiant = req.params.idEtudiant;
    console.log('Received request to /etudiants/:idEtudiant');
    console.log('courriel:', idEtudiant);
    db.get('SELECT * FROM etudiants WHERE idEtudiant = ?', [idEtudiant], function (err, row) {
        if (err) {
            res.status(500).json({ error: err.message });
            return;
        }

        if (!row) {
            res.status(404).json({ message: 'Etudiant not found' });
            return;
        }

        // Send the student data as a JSON response
        res.json({ etudiant: row });

    });
});

  
  app.post('/etudiants', (req, res) => {
    const {
      nom,
      prenom,
      motDePasse,
      description,
      courriel,
      adresse,
      college,
      specialite,
      disponibilite,
      bulletinScolaire,
      cv,
    } = req.body;
  
    db.run(
      `INSERT INTO etudiants 
        (nom, prenom, motDePasse, description, courriel, adresse, college, specialite, disponibilite, bulletinScolaire, cv) 
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)`,
      [
        nom,
        prenom,
        motDePasse,
        description,
        courriel,
        adresse,
        college,
        specialite,
        disponibilite,
        bulletinScolaire,
        cv,
      ],
      function (err) {
        if (err) {
          res.status(500).json({ error: err.message });
          return;
        }
        res.json({ idEtudiant: this.lastID });
      }
    );
  });
  
  app.get('/stages/:idStage', (req, res) => {
    const idStage = parseInt(req.params.idStage);
  
    console.log('Received request for stage with idStage:', idStage);
  
    db.get('SELECT * FROM stages WHERE idStage = ?', [idStage], (err, row) => {
      if (err) {
        console.error('Database error:', err.message);
        res.status(500).json({ error: err.message });
        return;
      }
  
      if (!row) {
        console.log('Stage not found for idStage:', idStage);
        res.status(404).json({ error: 'Stage not found' });
        return;
      }
  
      console.log('Returning stage details:', row);
      res.json(row);
    });
  });
  
  
  app.get('/stages/stage/:titreStage', (req, res) => {
    const { titreStage } = req.params;
    db.all('SELECT * FROM stages WHERE titreStage LIKE ?', [`%${titreStage}%`], [titreStage], (err, rows) => {
      if (err) {
        res.status(500).json({ error: err.message });
        return;
      }
      res.json(rows);
    });
  });
  
  app.get('/stages/entreprise/:nomEntreprise', (req, res) => {
    const { nomEntreprise } = req.params;
   
    db.all('SELECT * FROM stages WHERE nomEntreprise = ?', [nomEntreprise], (err, rows) => {
      if (err) {
        res.status(500).json({ error: err.message });
        return;
      }
      res.json(rows);
    });
  });
  
  app.post('/stages', (req, res) => {
    const {
      titreStage, nomEntreprise, courrielEntreprise, logoEntreprise, localisation, salaire,
      descriptionPoste, taches, competences, modeStage
    } = req.body;
  
    db.run(`
      INSERT INTO stages
      (titreStage, nomEntreprise, courrielEntreprise, logoEntreprise, localisation, salaire,
      descriptionPoste, taches, competences, modeStage)
      VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    `, [titreStage, nomEntreprise, courrielEntreprise, logoEntreprise, localisation, salaire,
         descriptionPoste, taches, competences, modeStage], function(err) {
      if (err) {
        res.status(500).json({ error: err.message });
        return;
      }
      res.json({ id: this.lastID });
    });
  });
  
  app.put('/stages/:idStage', (req, res) => {
    const { idStage } = req.params;
    const {
      titreStage, nomEntreprise, courrielEntreprise, logoEntreprise, localisation, salaire,
      descriptionPoste, taches, competences, modeStage
    } = req.body;
  
    db.run(`
      UPDATE stages
      SET titreStage = ?, nomEntreprise = ?, courrielEntreprise = ?, logoEntreprise = ?,
      localisation = ?, salaire = ?, descriptionPoste = ?, taches = ?, competences = ?, modeStage = ?
      WHERE idStage = ?
    `, [titreStage, nomEntreprise, courrielEntreprise, logoEntreprise, localisation, salaire,
         descriptionPoste, taches, competences, modeStage, idStage], function(err) {
      if (err) {
        res.status(500).json({ error: err.message });
        return;
      }
      res.json({ id: idStage });
    });
  });
  
  app.delete('/stages/:idStage', (req, res) => {
    const { idStage } = req.params;
  
    db.run('DELETE FROM stages WHERE idStage = ?', [idStage], function(err) {
      if (err) {
        res.status(500).json({ error: err.message });
        return;
      }
      res.json({ message: 'Stage deleted successfully' });
    });
  });
  
  app.post('/etudiants', (req, res) => {
    const {
      nom,
      prenom,
      motDePasse,
      description,
      courriel,
      adresse,
      college,
      specialite,
      disponibilite,
      bulletinScolaire,
      cv
    } = req.body;
  
    db.run(
      `INSERT INTO etudiants 
        (nom, prenom, motDePasse, description, courriel, adresse, college, specialite, disponibilite, bulletinScolaire, cv) 
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)`,
      [nom, prenom, motDePasse, description, courriel, adresse, college, specialite, disponibilite, bulletinScolaire, cv],
      function (err) {
        if (err) {
          res.status(500).json({ error: err.message });
          return;
        }
        res.json({ id: this.lastID });
      }
    );
  });
app.put('/etudiants/modifier/:idEtudiant', (req, res) => {
  const id = req.params.idEtudiant;
  const etudiant = req.body;

  db.run(
    `UPDATE etudiants
     SET nom=?, prenom=?, motDePasse=?, description=?, courriel=?, adresse=?, college=?, specialite=?, 
         disponibilite=?, bulletinScolaire=?, cv=?
     WHERE idEtudiant=?`,
    [
      etudiant.nom,
      etudiant.prenom,
      etudiant.motDePasse,
      etudiant.description,
      etudiant.courriel,
      etudiant.adresse,
      etudiant.college,
      etudiant.specialite,
      etudiant.disponibilite,
      etudiant.bulletinScolaire,
      etudiant.cv,
      id,
    ],
    function (err) {
      if (err) {
        res.status(500).json({ error: err.message });
        return;
      }
      res.json({ rowsAffected: this.changes });
    }
  );
});
  
  
  app.delete('/etudiants/:id', (req, res) => {
    const id = req.params.id;
  
    db.run('DELETE FROM etudiants WHERE idEtudiant=?', [id], function (err) {
      if (err) {
        res.status(500).json({ error: err.message });
        return;
      }
      res.json({ rowsAffected: this.changes });
    });
  });
  
//   app.get('/etudiants/:courriel', (req, res) => {
//     const courriel = req.params.courriel;

//     db.get('SELECT * FROM etudiants WHERE LOWER(courriel) = LOWER(?)', [courriel], function (err, row) {
//         if (err) {
//             res.status(500).json({ error: err.message });
//             return;
//         }
        
//         if (!row) {
//             res.status(404).json({ message: 'Etudiant not found' });
//             return;
//         }

//         res.json({ etudiant: row });
//     });
// });


app.post('/etudiants/seconnecter', (req, res) => {
  const { courriel, motDePasse } = req.body;
  console.log('Received request to /etudiants/seconnecter');
  console.log('courriel:', courriel);
  console.log('motDePasse:', motDePasse);
  db.get('SELECT * FROM etudiants WHERE LOWER(courriel) = LOWER(?)', [courriel], function (err, row) {
      if (err) {
          res.status(500).json({ error: err.message });
          return;
      }

      if (!row) {
          res.status(401).json({ message: 'Authentication failed. Invalid email (or password).' + courriel +" "+ motDePasse });
          return;
      }
      if (row.motDePasse === motDePasse) {
       
          const { motDePasse, ...etudiantWithoutPassword } = row;
          res.json({ etudiant: etudiantWithoutPassword });
      } else {
          res.status(401).json({ message: 'Authentication failed. Invalid email or password.' });
          
      }
  });
});

  // Start the server
  app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
  });

