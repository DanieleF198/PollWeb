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
  idGruppo int default 1,
  nome varchar(45) NOT NULL,
  cognome varchar(45) NOT NULL,
  dataNascita datetime NOT NULL,
  username varchar(45) NOT NULL,
  password varchar(45) NOT NULL,
  email varchar(45) NOT NULL,
  PRIMARY KEY (idUtente),
  FOREIGN KEY (idGruppo) REFERENCES Gruppo (idGruppo) on update cascade on delete cascade
  );
  
  CREATE TABLE Sondaggio(
	idSondaggio int NOT NULL AUTO_INCREMENT,
    idUtente int NOT NULL,
    titolo varchar(128) NOT NULL,
    testoApertura varchar(245),
    testoChiusura varchar(245),
    completo boolean NOT NULL,
    quiz boolean NOT NULL,
    visibilita boolean NOT NULL,
    dataCreazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, /*non che serva not null ma ce lo metto uguale*/
    dataChiusura datetime,
    privato boolean NOT NULL,
    modificabile boolean NOT NULL,
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
  
INSERT INTO `pollwebdb`.`Gruppo`(`idGruppo`,`nomeGruppo`)VALUES('1','Utente base');
INSERT INTO `pollwebdb`.`Gruppo`(`idGruppo`,`nomeGruppo`)VALUES('2','Responsabile');
INSERT INTO `pollwebdb`.`Gruppo`(`idGruppo`,`nomeGruppo`)VALUES('3','Admin');

INSERT INTO `pollwebdb`.`Utente`(`idUtente`,`idGruppo`,`nome`,`cognome`,`dataNascita`,`username`,`password`,`email`)VALUES('1','1','Arturito', 'benedito', '2000-12-12', 'artrito', 'password', 'arturitoBenedito@gmail.com');
INSERT INTO `pollwebdb`.`Utente`(`idUtente`,`idGruppo`,`nome`,`cognome`,`dataNascita`,`username`,`password`,`email`)VALUES('2','2','Rinaldo', 'baualdo', '1999-12-12', 'zawardo', 'mudamuda', 'dioBrando@gmail.com');
  
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`quiz`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`)VALUES('1','2','Prova 1', 'Hey questo è il primo sondaggio di prova guardatemiiiii', 'Bravo che hai concluso con successo questo sondaggio di prova..Ah ma non vedrai mai questo messaggio...eh vab', true, false, true,'2021-01-11','2021-02-12',false,false);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`quiz`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`)VALUES('2','2','Prova 2', 'Hey questo è il secondo sondaggio di prova guardatemiiiii', 'Immagina aver concluso questo sondggio', true, false, true,'2021-02-11','2021-03-12',false,false);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`quiz`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`)VALUES('3','2','Prova 3', 'Hey questo è il terzo sondaggio di prova guardatemiiiii', 'Complimenti per aver conseguto la compilazione di questo sondaggio. I tuoi dati sono pronti per essere inviati a Google e alle industrie cinesi', true, false, true,'2021-03-11','2021-04-12',false,false);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`quiz`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`)VALUES('4','2','Prova 4', 'Hey questo è il quarto sondaggio di prova guardatemiiiii', 'La torta è una menzogna', true, false, true,'2021-04-11','2021-05-12',false,false);