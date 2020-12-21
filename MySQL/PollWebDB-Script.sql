DROP DATABASE IF exists pollwebdb;
CREATE DATABASE pollwebdb;
USE pollwebdb;

DROP TABLE IF EXISTS `Gruppo`;
DROP TABLE IF EXISTS `Utente`;
DROP TABLE IF EXISTS `Sondaggio`;
DROP TABLE IF EXISTS `ListaPartecipanti`;
DROP TABLE IF EXISTS `Risposta`;
DROP TABLE IF EXISTS `Domanda`;
DROP TABLE IF EXISTS `RispostaDomanda`;

CREATE TABLE Gruppo(
	idGruppo int NOT NULL,
    nomeGruppo varchar(45) NOT NULL,
    PRIMARY KEY (idGruppo)
);

CREATE TABLE Utente (
  idUtente int NOT NULL AUTO_INCREMENT,
  idGruppo int NOT NULL,
  nome varchar(45) NOT NULL,
  cognome varchar(45) NOT NULL,
  username varchar(45) NOT NULL,
  password varchar(45) NOT NULL,
  email varchar(45) NOT NULL,
  eta int NOT NULL,
  PRIMARY KEY (idUtente),
  FOREIGN KEY (idGruppo) REFERENCES Gruppo (idGruppo) on update cascade on delete cascade
  );
  
  CREATE TABLE Sondaggio(
	idSondaggio int NOT NULL AUTO_INCREMENT,
    idUtente int NOT NULL,
    testoApertura varchar(245),
    testoChiusura varchar(245),
    stato int NOT NULL,
    quiz boolean NOT NULL,
    visibilita boolean NOT NULL,
    dataCreazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, /*non che serva not null ma ce lo metto uguale*/
    dataChiusura TIMESTAMP,
	PRIMARY KEY (idSondaggio),
    FOREIGN KEY (idUtente) REFERENCES Utente (idUtente) on update cascade on delete cascade
  );
  
  CREATE TABLE ListaPartecipanti(
	idListaPartecipanti int NOT NULL AUTO_INCREMENT,
    idUtente int NOT NULL,
    idSondaggio int NOT NULL,
    email varchar(45) NOT NULL,
    PRIMARY KEY (idListaPartecipanti),
    FOREIGN KEY (idUtente) REFERENCES Utente (idUtente) on update cascade on delete cascade,
    FOREIGN KEY (idSondaggio) REFERENCES Sondaggio (idSondaggio) on update cascade on delete cascade
  );
  
  CREATE TABLE Risposta(
	idRisposta int NOT NULL AUTO_INCREMENT,
    idUtente int NOT NULL,
    dataCreazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    punteggio int,
    nomeUtenteRisposta varchar(45) NOT NULL,
    PRIMARY KEY (idRisposta),
	FOREIGN KEY (idUtente) REFERENCES Utente (idUtente) on update cascade on delete cascade
  );
  
  CREATE TABLE Domanda(
	idDomanda int NOT NULL AUTO_INCREMENT,
    idSondaggio int NOT NULL,
    titolo varchar(45) NOT NULL,
    obbligatoria boolean NOT NULL,
    descrizione varchar(245) NOT NULL,
    posizione int NOT NULL,
    opzioni JSON NOT NULL,
    rispostaCorretta JSON,
    tipo varchar(45) NOT NULL,
    PRIMARY KEY (idDomanda),
    FOREIGN KEY (idSondaggio) REFERENCES Sondaggio (idSondaggio) on update cascade on delete cascade
  );
  
  CREATE TABLE RispostaDomanda(
	idRisposta int NOT NULL AUTO_INCREMENT,
    idDomanda int NOT NULL,
    risposta varchar(245) NOT NULL,
    PRIMARY KEY (idRisposta, idDomanda),
    FOREIGN KEY (idRisposta) REFERENCES Risposta (idRisposta) on update cascade on delete cascade,
    FOREIGN KEY (idDomanda) REFERENCES Domanda (idDomanda) on update cascade on delete cascade
  );
  
INSERT INTO `pollwebdb`.`Gruppo`(`idGruppo`,`nomeGruppo`)VALUES('1','Utente');
INSERT INTO `pollwebdb`.`Gruppo`(`idGruppo`,`nomeGruppo`)VALUES('2','Responsabile');
INSERT INTO `pollwebdb`.`Gruppo`(`idGruppo`,`nomeGruppo`)VALUES('3','Admin');

INSERT INTO `pollwebdb`.`Utente`(`idUtente`,`idGruppo`,`nome`,`cognome`,`username`,`password`,`email`,`eta`)VALUES('1','1','Arturito', 'benedito', 'artrito', 'password', 'arturitoBenedito@gmail.com', '26');
INSERT INTO `pollwebdb`.`Utente`(`idUtente`,`idGruppo`,`nome`,`cognome`,`username`,`password`,`email`,`eta`)VALUES('2','2','Rinaldo', 'baualdo', 'zawardo', 'mudamuda', 'dioBrando@gmail.com', '21');
  
  