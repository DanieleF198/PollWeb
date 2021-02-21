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
  version int DEFAULT 1,
  PRIMARY KEY (idUtente),
  FOREIGN KEY (idGruppo) REFERENCES Gruppo (idGruppo) on update cascade on delete cascade
  );
  
  CREATE TABLE Sondaggio(
	idSondaggio int NOT NULL AUTO_INCREMENT,
    idUtente int NOT NULL,
    titolo varchar(128) NOT NULL,
    testoApertura varchar(2048),
    testoChiusura varchar(1024),
    completo boolean NOT NULL,
    visibilita boolean NOT NULL,
    dataCreazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, /*non che serva not null ma ce lo metto uguale*/
    dataChiusura datetime,
    privato boolean NOT NULL,
    modificabile boolean NOT NULL,
    compilazioni int DEFAULT 0,
    version int DEFAULT 1,
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
    version int DEFAULT 1,
    PRIMARY KEY (idListaPartecipanti),
    FOREIGN KEY (idUtente) REFERENCES Utente (idUtente) on update cascade on delete cascade,
    FOREIGN KEY (idSondaggio) REFERENCES Sondaggio (idSondaggio) on update cascade on delete cascade
  );
  
  CREATE TABLE Risposta(
	idRisposta int NOT NULL AUTO_INCREMENT,
    idUtente int,
    dataCreazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    usernameUtenteRisposta varchar(45),
    ipUtenteRisposta varchar(45) NOT NULL,
    version int DEFAULT 1,
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
    version int DEFAULT 1,
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

INSERT INTO `pollwebdb`.`Utente`(`idUtente`,`idGruppo`,`nome`,`cognome`,`dataNascita`,`username`,`password`,`email`,`bloccato`)VALUES('1','2','Daniele', 'Fossemo', '2000-12-12', 'danieleFossemo', 'Pass2021!', 'DanieleFossemo@gmail.com',false);
INSERT INTO `pollwebdb`.`Utente`(`idUtente`,`idGruppo`,`nome`,`cognome`,`dataNascita`,`username`,`password`,`email`,`bloccato`)VALUES('2','2','Davide', 'Ricci', '1999-06-23', 'davideRicci', 'Password2021!', 'davideRicci@gmail.com',false);
INSERT INTO `pollwebdb`.`Utente`(`idUtente`,`idGruppo`,`nome`,`cognome`,`dataNascita`,`username`,`password`,`email`,`bloccato`)VALUES('3','3','Admin', 'Admin', '1970-12-12', 'Admin', 'Admin2021!', 'Admin@gmail.com',false);
INSERT INTO `pollwebdb`.`Utente`(`idUtente`,`idGruppo`,`nome`,`cognome`,`dataNascita`,`username`,`password`,`email`,`bloccato`)VALUES('4','2','Francesco', 'Brando', '1970-02-22', 'francescoBrando', 'francescoBrando2021!', 'francescoBrando@gmail.com',false);
INSERT INTO `pollwebdb`.`Utente`(`idUtente`,`idGruppo`,`nome`,`cognome`,`dataNascita`,`username`,`password`,`email`,`bloccato`)VALUES('5','2','Michele', 'Castagna', '1980-05-15', 'micheleCastagna', 'micheleCastagna2021!', 'micheleCastagna@gmail.com',false);

  
INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('1','2','Motori e motoretti', 'Ehy amici miei, ho scoperto questo nuovo sito incredibile, venite e rispondete alle domande come quando stiamo al bar. Se vi ho invitati è perchè siete le mie fonti più attendibili in merito, aspetto con ansia le vostre risposte.', 'Grazie per la risposta, fatemi sapere cosa ne pensate di questo sito che a me piace molto. Userò i vostri consigli per comprarmi un auto.', true, true,'2021-01-11','2021-02-12',true,false,2000);

INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('1', '1', 'Qual è la marca che reputi migliore?', true, 'rispondete cercando di rimanere oggettivi', 0, '{"opzioni": ["Ferrari", "Lamborghini", "Maserati"]}',  'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('2', '1', 'Cosa preferisci?', true, 'qui puoi essere soggettivo', 1, '{"opzioni": ["benzina", "GPL", "metano", "elettrica"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('3', '1', 'Motiva la tua risposta precedente', true, 'vorrei sapere perchè la pensi cosi', 2, '{}', 'openLong',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('4', '1', 'Quante auto hai posseduto?', true, '', 3, '{}', 'openNumber',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('5', '1', 'Quando hai preso la patente', true, '', 4, '{}', 'openDate',null );

INSERT INTO	`pollwebdb`.`Risposta`(`idRisposta`,`dataCreazione`,`usernameUtenteRisposta`,`ipUtenteRisposta`)VALUES('1', '2021-01-11', 'edvige@gmail.com', '0:0:0:0:0:0:0:1');

INSERT INTO `pollwebdb`.`RispostaDomanda`(`idRisposta`,`idDomanda`,`risposta`)VALUES('1', '1', '{"risposta": ["Lamborghini"]}');
INSERT INTO `pollwebdb`.`RispostaDomanda`(`idRisposta`,`idDomanda`,`risposta`)VALUES('1', '2', '{"risposta": ["GPL", "elettrica"]}');
INSERT INTO `pollwebdb`.`RispostaDomanda`(`idRisposta`,`idDomanda`,`risposta`)VALUES('1', '3', '{"risposta": ["Trovo fantastico poter guidare delle auto ecologiche ma al contempo molto prestanti"]}');
INSERT INTO `pollwebdb`.`RispostaDomanda`(`idRisposta`,`idDomanda`,`risposta`)VALUES('1', '4', '{"risposta": ["3"]}');
INSERT INTO `pollwebdb`.`RispostaDomanda`(`idRisposta`,`idDomanda`,`risposta`)VALUES('1', '5', '{"risposta": ["2021-02-02"]}');

INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('1', '1', '1', 'danieleFossemo', 'DanieleFossemo@gmail.com', 'Pass2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('50', null, '1', 'edvige', 'edvige@gmail.com', 'Edvige2021!',true,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('51', null, '1', 'giuseppe', 'giuseppe@gmail.com', 'Giuseppe2021!',false,true);



INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('2','2','Che cibo preferisci?', 'Ho creato questo sondaggio perchè devo creare un menu per il mio nuovo ristorante e mi piacerebbe sapere cosa preferite.', 'Grazie infinite per aver compilato, magari se passi riceverai uno sconticino :)', true, false,'2021-01-11','2021-06-12',false,true,1000);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('6', '2', 'Quale è il tuo piatto preferito?', true, 'per avere un idea generale su cosa preferiate mangiare', 0, '{}', 'openShort','Constraint: 35' );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('7', '2', 'Dovendo scegliere una sola portata preferiresti un primo o un secondo?', true, '', 1, '{"opzioni": ["Un primo", "Un secondo"]}', 'closeSingle','Constraint: 200' );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('42', '2', 'Quante volte hai mangiato in un ristorante negli ultimi tempi?', true, '', 2, '{}', 'openNumber','Constraint: 0 -- 20' );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('8', '2', 'Quali pizze ti piacciono?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 3, '{"opzioni": ["Margherita", "Salsiccia e patate", "Diavola", "Quattro formaggi", "Napoletana", "Capricciosa", "Paperina", "Funghi e tartufo"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('9', '2', 'A fine pasto meglio un dolce o un gelato?', true, 'in base alle preferenze vedrò di cosa fare maggior rifornimento', 4, '{"opzioni": ["Dolce", "Gelato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('43', '2', 'In che giorno saresti disponibile per un eventuale innaugurazione', true, 'in base alle preferenze vedrò di cosa fare maggior rifornimento', 5, '{}', 'openDate','Constraint: 2021-02-10 -- 2021-12-30' );


INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('3','2','Che guardi in tv?', 'Per una ricerca scolastica ho bisogno di un pochino di informazioni da parte vostra. Mi aiutate?', 'La tua risposta è stata preziosa, GRAZIE', true, true, '2021-02-11','2021-03-12',false,true,999);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('10', '3', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('11', '3', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('12', '3', 'Quale genere ti piace?', true, 'se vuoi selezionali anche tutti', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('13', '3', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );


INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('4','2','Libri e lettura', 'Ho bisogno che rspondiate a queste piccolissime domande per scrivere un articolo, giusto 2 minuti', 'Grazie per avermi dedicato un po del tuo tempo', true, true,'2021-02-11','2021-01-12',false,false,998);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('14', '4', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('15', '4', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('16', '4', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('17', '4', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );



INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('5','2','Compri online?', 'Vorrei capire come appprocciarmi alla vendita di articoli on-line. Le vostre risposte saranno fondamentali', 'Grazie', true, true,'2021-02-11','2021-05-12',true,false,997);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('18', '5', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('19', '5', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('20', '5', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('21', '5', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );


INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('6','2','E voi credete agli alieni?', 'Ero curioso di vedere se qualcuno come me crede in altre forme di vita', 'Grazie per la compilazione amico marziano', true, true,'2021-01-12',null,true,false,996);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('22', '6', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('23', '6', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('24', '6', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('25', '6', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );


INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('7','2','Quanto spesso fate sondaggi?', 'Beh in un sito di sondaggi volevo semplicemente capire se si preferisce creare o rispondere', 'Grazie', true, true,'2021-02-13',null,true,false,995);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('26', '7', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('27', '7', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('28', '7', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('29', '7', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );


INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('8','2','Motori di ricerca', 'E voi che motori di ricerca preferite?', 'Grazie', true, true,'2021-02-14',null,false,false,994);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('30', '8', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('31', '8', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('32', '8', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('33', '8', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );


INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('9','2','Store online', 'Buongiorno o buonasera, volevo fare un sondaggio riguardante le vostre preferenze sui vari siti web dove fare acquisti', 'Grazie per la compilazione', true, true,'2021-02-15',null,false,true,900);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('34', '9', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('35', '9', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('36', '9', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('37', '9', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );


INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('10','2','Ristorantiii', 'Volevo chiedere riguardo le vostre preferenze su location e menu', 'Grazie', true, true,'2021-02-16',null,false,false,800);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('38', '10', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('39', '10', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('40', '10', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('41', '10', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );


INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('11','2','Questionario di storia', 'Vediamo come ve  la cavate a rispondere a queste domande, mettetevi alla prova', 'Ti farò sapere come sei andato! Speriamo bene', true, true,'2021-02-17',null,true,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('88', '11', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('89', '11', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('90', '11', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('91', '11', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('12','1','Tecnologia o vecchia scuola?', 'Vorrei capire come le generazioni moderne si approcciano a varie situazioni di vita', 'Grazie per la compilazione', true, true,'2021-02-18',null,true,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('44', '12', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('45', '12', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('46', '12', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('47', '12', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('13','1','Ricerca di psicologia', 'Sono una studentessa di psicologia e ho bisogno di dati per portare avanti una ricerca. Mi aiutereste?', 'Grazie infinite', true, true,'2021-02-19',null,false,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('48', '13', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('49', '13', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('50', '13', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('51', '13', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('14','1','Dimmi di te', 'Domande personali per noia, mi divertirò a leggere le vostre risposte', 'Graziee', true, true,'2021-02-20',null,false,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('52', '14', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('53', '14', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('54', '14', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('55', '14', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('15','2','Arte e quadri', 'Sondaggio per capire i vostri gusti in fatto di arte', 'Bravo che hai concluso con successo questo sondaggio di prova..Ah ma non vedrai mai questo messaggio...eh vab', true, true,'2021-01-21','2021-12-12',false,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('56', '15', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('57', '15', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('58', '15', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('59', '15', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('16','2','E voi videogiocate?', 'Vorrei capire che idea ci sia generalmente riguardo il mondo dei videogiochi', 'La torta è una menzogna', true, true, '2021-02-11','2021-03-12',true,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('60', '16', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('61', '16', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('62', '16', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('63', '16', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('17','2','YouTube', 'Mi servono informazioni dagli utenti che frequentano di frequente questa piattaforma', 'Grazie popolo dell internet', true, false,'2021-02-11','2021-04-12',false,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('64', '17', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('65', '17', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('66', '17', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('67', '17', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('18','2','Cyber security', 'Sondaggio per capire come gli utenti utilizzino la rete e come si proteggono, se si proteggono', 'Complimenti per aver conseguto la compilazione di questo sondaggio. I tuoi dati sono pronti per essere inviati a Google e alle industrie cinesi', true, true,'2021-02-23','2021-05-12',true,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('68', '18', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('69', '18', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('70', '18', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('71', '18', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('19','2','Blocco artistico', 'Sono in blocco e vorrei capire come fate voi ad uscire da questa scomoda situazione', 'I tuoi consigli saranno preziosi, grazie', true, true,'2021-02-22',null,false,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('72', '19', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('73', '19', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('74', '19', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('75', '19', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('20','2','Prova 18', 'Hey questo è il sesto sondaggio di prova guardatemiiiii', 'stavolta senza data', true, true,'2021-02-23',null,false,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('76', '20', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('77', '20', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('78', '20', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('79', '20', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('21','2','Attività sportiva', 'Come vivete lo sport in questa situazione di quarantena?', 'Grazie', true, true,'2021-02-26',null,true,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('80', '21', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('81', '21', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('82', '21', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('83', '21', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('22','2','Giochi da tavola', 'Anche voi come me giocate da tavola? Vediamo un po le vostre preferenze', 'Grazie', true, true,'2021-02-25',null,false,true,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('84', '22', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('85', '22', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('86', '22', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('87', '22', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('4', '2', '5', 'davideRicci', 'davideRicci@gmail.com', 'Password2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('5', '2', '6', 'davideRicci', 'davideRicci@gmail.com', 'Password2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('6', '2', '7', 'davideRicci', 'davideRicci@gmail.com', 'Password2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('7', '2', '11', 'davideRicci', 'davideRicci@gmail.com', 'Password2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('8', '2', '12', 'davideRicci', 'davideRicci@gmail.com', 'Password2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('9', '2', '16', 'davideRicci', 'davideRicci@gmail.com', 'Password2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('10', '2', '18', 'davideRicci', 'davideRicci@gmail.com', 'Password2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('11', '2', '21', 'davideRicci', 'davideRicci@gmail.com', 'Password2021!',false,true);

INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('12', null, '5', 'marco', 'marchetto@gmail.com', 'Marco2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('13', null, '5', 'gioia', 'gioia98@gmail.com', 'Gioia2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('14', null, '5', 'lorenzo', 'lorenzo.lollo@gmail.com', 'Lorenzo2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('16', null, '7', 'anna', 'anna.volpi@gmail.com', 'Anna2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('17', null, '7', 'matteo', 'matteo@gmail.com', 'Matteo2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('18', null, '11', 'isabella', 'isa.bella@gmail.com', 'Isabella2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('19', null, '11', 'emma', 'emma@gmail.com', 'Emma2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('20', null, '12', 'daniele', 'daniele@gmail.com', 'Daniele2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('21', null, '21', 'gaviscon', 'danielehalagastrite@gmail.com', 'Gaviscon2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('22', null, '12', 'davide', 'davide@gmail.com', 'Davide2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('23', null, '16', 'gemma', 'gemma@gmail.com', 'Gemma2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('24', null, '16', 'antonio', 'antonio@gmail.com', 'Antonio!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('25', null, '18', 'ilaria', 'ilaria@gmail.com', 'Ilaria!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('26', null, '18', 'raffalele', 'raffalele@gmail.com', 'Raffalele2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('27', null, '21', 'franco', 'franco@gmail.com', 'Franco2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('28', null, '21', 'samira', 'samira@gmail.com', 'Samira2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('29', null, '5', 'sara', 'sara@gmail.comail.com', 'Simona2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('30', null, '5', 'francesco', 'francesco@gmail.com', 'Francesco2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('31', null, '7', 'federico', 'federico@gmail.com', 'Federico2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('32', null, '7', 'gaetano', 'gaetano@gmail.com', 'Gaetano2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('33', null, '7', 'dario', 'dario@gmail.com', 'Dario2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('34', null, '11', 'alberto', 'alberto@gmail.com', 'Alberto2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('35', null, '11', 'andrea', 'andrea@gmail.com', 'Andrea2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('36', null, '11', 'gennaro', 'gennaro.ilBullo@gmail.com', 'Gennaro2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('37', null, '12', 'gaetano', 'gaetano@gmail.com', 'Gaetano2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('38', null, '18', 'bender', 'bender@gmail.com', 'Futur4ma!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('39', null, '16', 'leila', 'leila@gmail.com', 'Leila2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('40', null, '16', 'luke', 'luke@gmail.com', 'Luke2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('41', null, '16', 'gianni', 'gianni@gmail.com', 'Gianni2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('42', null, '16', 'geronimo', 'geronimo@gmail.com', 'Geronimo2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('43', null, '16', 'salvatore', 'salvatore@gmail.com', 'Salvatore2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('44', null, '16', 'barbara', 'barbara@gmail.com', 'Barbara2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('45', null, '18', 'lalla', 'lalla@gmail.com', 'Lalla2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('46', null, '18', 'loretta', 'loretta@gmail.com', 'Loretta2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('47', null, '21', 'edward', 'edward.drake@gmail.com', 'EDvisione2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('48', null, '21', 'jimmy', 'jimmy@gmail.com', 'Jimmy2021!',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('49', null, '21', 'renato', 'renato@gmail.com', 'Renato2021!',false,true);


