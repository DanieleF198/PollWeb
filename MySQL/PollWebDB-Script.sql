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
  bloccato boolean not null,
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
    visibilita boolean NOT NULL,
    dataCreazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, /*non che serva not null ma ce lo metto uguale*/
    dataChiusura datetime,
    privato boolean NOT NULL,
    modificabile boolean NOT NULL,
    compilazioni int DEFAULT 0,
	PRIMARY KEY (idSondaggio),
    FOREIGN KEY (idUtente) REFERENCES Utente (idUtente) on update cascade on delete cascade
  );
  
  CREATE TABLE ListaPartecipanti(
	idListaPartecipanti int NOT NULL AUTO_INCREMENT,
    idUtente int,
    idSondaggio int NOT NULL,
    nome varchar(45) NOT NULL,
    email varchar(45) NOT NULL,
    password varchar(45) NOT NULL,
    scaduto boolean NOT NULL,
    emailMandata boolean NOT NULL,
    PRIMARY KEY (idListaPartecipanti),
    FOREIGN KEY (idUtente) REFERENCES Utente (idUtente) on update cascade on delete cascade,
    FOREIGN KEY (idSondaggio) REFERENCES Sondaggio (idSondaggio) on update cascade on delete cascade
  );
  
  CREATE TABLE Risposta(
	idRisposta int NOT NULL AUTO_INCREMENT,
    idUtente int NOT NULL,
    dataCreazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    nomeUtenteRisposta varchar(45) NOT NULL,
    PRIMARY KEY (idRisposta),
	FOREIGN KEY (idUtente) REFERENCES Utente (idUtente) on update cascade on delete cascade
  );
  
  CREATE TABLE Domanda(
	idDomanda int NOT NULL AUTO_INCREMENT,
    idSondaggio int NOT NULL,
    titolo varchar(128),
    obbligatoria boolean,
    descrizione varchar(245),
    posizione int NOT NULL,
    opzioni JSON,
    tipo varchar(45),
    vincolo varchar(45),
    PRIMARY KEY (idDomanda),
    FOREIGN KEY (idSondaggio) REFERENCES Sondaggio (idSondaggio) on update cascade on delete cascade
  );
  
  CREATE TABLE RispostaDomanda(
	idRisposta int NOT NULL AUTO_INCREMENT,
    idDomanda int NOT NULL,
    risposta JSON,
    PRIMARY KEY (idRisposta, idDomanda),
    FOREIGN KEY (idRisposta) REFERENCES Risposta (idRisposta) on update cascade on delete cascade,
    FOREIGN KEY (idDomanda) REFERENCES Domanda (idDomanda) on update cascade on delete cascade
  );
  
INSERT INTO `pollwebdb`.`Gruppo`(`idGruppo`,`nomeGruppo`)VALUES('1','Utente base');
INSERT INTO `pollwebdb`.`Gruppo`(`idGruppo`,`nomeGruppo`)VALUES('2','Responsabile');
INSERT INTO `pollwebdb`.`Gruppo`(`idGruppo`,`nomeGruppo`)VALUES('3','Admin');

INSERT INTO `pollwebdb`.`Utente`(`idUtente`,`idGruppo`,`nome`,`cognome`,`dataNascita`,`username`,`password`,`email`,`bloccato`)VALUES('1','2','Arturito', 'benedito', '2000-12-12', 'artrito', 'Pass2021!', 'arturitoBenedito@gmail.com',false);
INSERT INTO `pollwebdb`.`Utente`(`idUtente`,`idGruppo`,`nome`,`cognome`,`dataNascita`,`username`,`password`,`email`,`bloccato`)VALUES('2','2','Rinaldo', 'baualdo', '1999-12-12', 'zawardo', 'mudamuda', 'dioBrando@gmail.com',false);
INSERT INTO `pollwebdb`.`Utente`(`idUtente`,`idGruppo`,`nome`,`cognome`,`dataNascita`,`username`,`password`,`email`,`bloccato`)VALUES('3','3','Jotaro', 'JoeStar', '1970-12-12', 'starplatinum', 'horahora', 'Jotaro.Kujo@gmail.com',false);
INSERT INTO `pollwebdb`.`Utente`(`idUtente`,`idGruppo`,`nome`,`cognome`,`dataNascita`,`username`,`password`,`email`,`bloccato`)VALUES('4','2','Abdul', 'AbdulU', '1970-12-12', 'magitian', 'red', 'Magitian.Red@gmail.com',false);
INSERT INTO `pollwebdb`.`Utente`(`idUtente`,`idGruppo`,`nome`,`cognome`,`dataNascita`,`username`,`password`,`email`,`bloccato`)VALUES('5','2','Abdul', 'Abdul', '1970-12-12', 'magitian', 'red', 'Magitian.Red@gmail.com',false);

  
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('1','2','Test 1', 'Primo sondaggio serio, ora scriverò cose a caso per vedere se la descrizione nelle card funziona a dovere o se devo modificare qualcosa. Questa descrizione si autodistruggerà tra 3...2...1...BABABABAAAAAAAAM', 'Pare abbia funzionato', true, true,'2021-01-11','2021-02-12',true,false,2000);

INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('1', '1', 'Domanda 1', true, 'sono la 1 domanda', 0, '{"opzioni": ["risposta 1", "risposta 2", "risposta 3", "risposta 4"]}',  'open Single',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('2', '1', 'Domanda 2', true, 'sono la 2 domanda', 1, '{}', 'closeShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('3', '1', 'Domanda 3', true, 'sono la 3 domanda', 2, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('4', '1', 'Domanda 4', true, 'sono la 4 domanda', 3, '{}', 'openNumber',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('5', '1', 'Domanda 5', true, 'sono la 5 domanda', 4, '{}', 'openDate',null );

INSERT INTO	`pollwebdb`.`Risposta`(`idRisposta`,`idUtente`,`dataCreazione`,`nomeUtenteRisposta`)VALUES('1', '2', '2021-01-11', 'zawardo');

INSERT INTO `pollwebdb`.`RispostaDomanda`(`idRisposta`,`idDomanda`,`risposta`)VALUES('1', '1', '{}');
INSERT INTO `pollwebdb`.`RispostaDomanda`(`idRisposta`,`idDomanda`,`risposta`)VALUES('1', '2', '{}');
INSERT INTO `pollwebdb`.`RispostaDomanda`(`idRisposta`,`idDomanda`,`risposta`)VALUES('1', '3', '{}');
INSERT INTO `pollwebdb`.`RispostaDomanda`(`idRisposta`,`idDomanda`,`risposta`)VALUES('1', '4', '{}');
INSERT INTO `pollwebdb`.`RispostaDomanda`(`idRisposta`,`idDomanda`,`risposta`)VALUES('1', '5', '{}');

INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('1', '1', '1', 'artrito', 'arturitoBenedito@gmail.com', 'password',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('2', '2', '1', 'zawardo', 'dioBrando@gmail.com', 'mudamuda',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('3', '4', '1', 'magitian', 'Magitian.Red@gmail.com', 'red',false,true);




INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('2','2','Prova 1', 'Hey questo è il primo sondaggio di prova guardatemiiiii', 'Bravo che hai concluso con successo questo sondaggio di prova..Ah ma non vedrai mai questo messaggio...eh vab', true, true,'2021-01-11','2021-02-12',true,false,1000);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('3','2','Prova 2', 'Hey questo è il secondo sondaggio di prova guardatemiiiii', 'Immagina aver concluso questo sondggio', true, true, '2021-02-11','2021-03-12',true,false,106);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('4','2','Prova 3', 'Hey questo è il terzo sondaggio di prova guardatemiiiii', 'Complimenti per aver conseguto la compilazione di questo sondaggio. I tuoi dati sono pronti per essere inviati a Google e alle industrie cinesi', true, false,'2021-03-11','2021-04-12',false,false,10);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('5','2','Prova 4', 'Hey questo è il quarto sondaggio di prova guardatemiiiii', 'La torta è una menzogna', true, true,'2021-04-11','2021-05-12',false,false,1);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('6','2','Prova 5', 'Hey questo è il quinto sondaggio di prova guardatemiiiii', 'stavolta senza data', true, true,'2021-04-12',null,false,false,104);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('7','2','Prova 6', 'Hey questo è il sesto sondaggio di prova guardatemiiiii', 'stavolta senza data', true, true,'2021-04-13',null,true,false,147);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('8','2','Prova 7', 'Hey questo è il settimo sondaggio di prova guardatemiiiii', 'stavolta senza data', true, true,'2021-04-14',null,false,false,10);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('9','2','Prova 8', 'Hey questo è il ottavo sondaggio di prova guardatemiiiii', 'stavolta senza data', true, true,'2021-04-15',null,false,false,11);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('10','2','Prova 9', 'Hey questo è il decimo sondaggio di prova guardatemiiiii, ah no sono il nono no?', 'stavolta senza data', true, true,'2021-04-16',null,false,false,10);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('11','2','indeterminata', 'Hey questo è il decimo sondaggio di prova guardatemiiiii, ah no sono il nono no?', 'stavolta senza data', true, true,'2021-04-17',null,false,false,10);

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('12','1','Prova 1', 'Hey questo è il settimo sondaggio di prova guardatemiiiii', 'stavolta senza data', true, true,'2021-04-18',null,false,false,10);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('13','1','Prova 2', 'Hey questo è il ottavo sondaggio di prova guardatemiiiii', 'stavolta senza data', true, true,'2021-04-19',null,false,false,10);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('14','1','Prova 3', 'Hey questo è il decimo sondaggio di prova guardatemiiiii, ah no sono il nono no?', 'stavolta senza data', true, true,'2021-04-20',null,false,false,10);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('15','2','Prova 1', 'Hey questo è il primo sondaggio di prova guardatemiiiii', 'Bravo che hai concluso con successo questo sondaggio di prova..Ah ma non vedrai mai questo messaggio...eh vab', true, true,'2021-01-21','2021-02-12',false,false,10);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('16','2','Prova 2', 'Hey questo è il secondo sondaggio di prova guardatemiiiii', 'Immagina aver concluso questo sondggio', true, true, '2021-02-11','2021-03-12',false,false,10);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('17','2','Prova 3', 'Hey questo è il terzo sondaggio di prova guardatemiiiii', 'Complimenti per aver conseguto la compilazione di questo sondaggio. I tuoi dati sono pronti per essere inviati a Google e alle industrie cinesi', true, false,'2021-03-11','2021-04-12',false,false,10);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('18','2','Prova 4', 'Hey questo è il quarto sondaggio di prova guardatemiiiii', 'La torta è una menzogna', true, true,'2021-04-23','2021-05-12',false,false,10);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('19','2','Prova 5', 'Hey questo è il quinto sondaggio di prova guardatemiiiii', 'stavolta senza data', true, true,'2021-04-22',null,false,false,10);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('20','2','Prova 6', 'Hey questo è il sesto sondaggio di prova guardatemiiiii', 'stavolta senza data', true, true,'2021-04-27',null,false,false,10);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('21','2','Prova 7', 'Hey questo è il settimo sondaggio di prova guardatemiiiii', 'stavolta senza data', true, true,'2021-04-26',null,false,false,10);
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('22','2','Prova 8', 'Hey questo è il ottavo sondaggio di prova guardatemiiiii', 'stavolta senza data', true, true,'2021-04-25',null,false,false,10);





INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('4', '2', '2', 'zawardo', 'dioBrando@gmail.com', 'mudamuda',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('5', '2', '3', 'zawardo', 'dioBrando@gmail.com', 'mudamuda',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('6', '2', '7', 'zawardo', 'dioBrando@gmail.com', 'mudamuda',false,true);



