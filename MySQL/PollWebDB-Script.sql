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
    idUtente int,
    dataCreazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    usernameUtenteRisposta varchar(45),
    ipUtenteRisposta varchar(45) NOT NULL,
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

INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('1', '1', 'Domanda 1', true, 'sono la 1 domanda', 0, '{"opzioni": ["risposta 1", "risposta 2", "risposta 3", "risposta 4"]}',  'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('2', '1', 'Domanda 2', true, 'sono la 2 domanda', 1, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('3', '1', 'Domanda 3', true, 'sono la 3 domanda', 2, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('4', '1', 'Domanda 4', true, 'sono la 4 domanda', 3, '{}', 'openNumber',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('5', '1', 'Domanda 5', true, 'sono la 5 domanda', 4, '{}', 'openDate',null );

INSERT INTO	`pollwebdb`.`Risposta`(`idRisposta`,`idUtente`,`dataCreazione`,`usernameUtenteRisposta`,`ipUtenteRisposta`)VALUES('1', '2', '2021-01-11', 'zawardo', '0:0:0:0:0:0:0:1');

INSERT INTO `pollwebdb`.`RispostaDomanda`(`idRisposta`,`idDomanda`,`risposta`)VALUES('1', '1', '{}');
INSERT INTO `pollwebdb`.`RispostaDomanda`(`idRisposta`,`idDomanda`,`risposta`)VALUES('1', '2', '{}');
INSERT INTO `pollwebdb`.`RispostaDomanda`(`idRisposta`,`idDomanda`,`risposta`)VALUES('1', '3', '{}');
INSERT INTO `pollwebdb`.`RispostaDomanda`(`idRisposta`,`idDomanda`,`risposta`)VALUES('1', '4', '{}');
INSERT INTO `pollwebdb`.`RispostaDomanda`(`idRisposta`,`idDomanda`,`risposta`)VALUES('1', '5', '{}');

INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('1', '1', '1', 'artrito', 'arturitoBenedito@gmail.com', 'password',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('2', '2', '1', 'zawardo', 'dioBrando@gmail.com', 'mudamuda',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('3', '4', '1', 'magitian', 'Magitian.Red@gmail.com', 'red',false,true);




INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('2','2','Che cibo preferisci?', 'Ho creato questo sondaggio perchè devo creare un menu per il mio nuovo ristorante e mi piacerebbe sapere cosa preferite.', 'Grazie infinite per aver compilato, magari se passi riceverai uno sconticino :)', true, true,'2021-01-11','2021-02-12',false,false,1000);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('6', '2', 'Quale è il tuo piatto preferito?', true, 'per avere un idea generale su cosa preferiate mangiare', 0, '{}', 'openShort','Constraint: 35' );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('7', '2', 'Dovendo scegliere una sola portata preferiresti un primo o un secondo?', true, '', 1, '{"opzioni": ["Un primo", "Un secondo"]}', 'closeSingle','Constraint: 200' );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('42', '2', 'Quante volte hai mangiato in un ristorante negli ultimi tempi?', true, '', 2, '{}', 'openNumber','Constraint: 0 -- 20' );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('8', '2', 'Che genere preferisci tra questi?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 3, '{"opzioni": ["Si", "No"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('9', '2', 'A fine pasto meglio un dolce o un gelato?', true, 'in base alle preferenze vedrò di cosa fare maggior rifornimento', 4, '{"opzioni": ["Dolce", "Gelato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('43', '2', 'In che giorno saresti disponibile per un eventuale innaugurazione', true, 'in base alle preferenze vedrò di cosa fare maggior rifornimento', 5, '{}', 'openDate','Constraint: 2021-02-10 -- 2021-12-30' );


INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('3','2','Che guardi in tv?', 'Per una ricerca scolastica ho bisogno di un pochino di informazioni da parte vostra. Mi aiutate?', 'La tua risposta è stata preziosa, GRAZIE', true, true, '2021-02-11','2021-03-12',false,false,999);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('10', '3', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('11', '3', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('12', '3', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('13', '3', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );


INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('4','2','Libri e lettura', 'Ho bisogno che rspondiate a queste piccolissime domande per scrivere un articolo, giusto 2 minuti', 'Grazie per avermi dedicato un po del tuo tempo', true, true,'2021-03-11','2021-04-12',false,false,998);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('14', '4', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('15', '4', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('16', '4', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('17', '4', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );



INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('5','2','Compri online?', 'Vorrei capire come appprocciarmi alla vendita di articoli on-line. Le vostre risposte saranno fondamentali', 'Grazie', true, true,'2021-04-11','2021-05-12',true,false,997);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('18', '5', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('19', '5', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('20', '5', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('21', '5', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );


INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('6','2','E voi credete agli alieni?', 'Ero curioso di vedere se qualcuno come me crede in altre forme di vita', 'Grazie per la compilazione amico marziano', true, true,'2021-04-12',null,true,false,996);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('22', '6', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('23', '6', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('24', '6', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('25', '6', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );


INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('7','2','Quanto spesso fate sondaggi?', 'Beh in un sito di sondaggi volevo semplicemente capire se si preferisce creare o rispondere', 'Grazie', true, true,'2021-04-13',null,true,false,995);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('26', '7', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('27', '7', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('28', '7', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('29', '7', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );


INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('8','2','Motori di ricerca', 'E voi che motori di ricerca preferite?', 'Grazie', true, true,'2021-04-14',null,false,false,994);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('30', '8', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('31', '8', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('32', '8', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('33', '8', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );


INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('9','2','Store online', 'Buongiorno o buonasera, volevo fare un sondaggio riguardante le vostre preferenze sui vari siti web dove fare acquisti', 'Grazie per la compilazione', true, true,'2021-04-15',null,false,false,900);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('34', '9', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('35', '9', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('36', '9', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('37', '9', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );


INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('10','2','Ristorantiii', 'Volevo chiedere riguardo le vostre preferenze su location e menu', 'Grazie', true, true,'2021-04-16',null,false,false,800);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('38', '10', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('39', '10', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('40', '10', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('41', '10', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );


INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('11','2','Questionario di storia', 'Vediamo come ve  la cavate a rispondere a queste domande, mettetevi alla prova', 'Ti farò sapere come sei andato! Speriamo bene', true, true,'2021-04-17',null,true,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('88', '11', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('89', '11', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('90', '11', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('91', '11', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('12','1','Tecnologia o vecchia scuola?', 'Vorrei capire come le generazioni moderne si approcciano a varie situazioni di vita', 'Grazie per la compilazione', true, true,'2021-04-18',null,true,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('44', '12', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('45', '12', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('46', '12', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('47', '12', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('13','1','Ricerca di psicologia', 'Sono una studentessa di psicologia e ho bisogno di dati per portare avanti una ricerca. Mi aiutereste?', 'Grazie infinite', true, true,'2021-04-19',null,false,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('48', '13', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('49', '13', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('50', '13', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('51', '13', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('14','1','Dimmi di te', 'Domande personali per noia, mi divertirò a leggere le vostre risposte', 'Graziee', true, true,'2021-04-20',null,false,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('52', '14', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('53', '14', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('54', '14', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('55', '14', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('15','2','Arte e quadri', 'Sondaggio per capire i vostri gusti in fatto di arte', 'Bravo che hai concluso con successo questo sondaggio di prova..Ah ma non vedrai mai questo messaggio...eh vab', true, true,'2021-01-21','2021-02-12',false,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('56', '15', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('57', '15', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('58', '15', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('59', '15', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('16','2','E voi videogiocate?', 'Vorrei capire che idea ci sia generalmente riguardo il mondo dei videogiochi', 'La torta è una menzogna', true, true, '2021-02-11','2021-03-12',true,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('60', '16', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('61', '16', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('62', '16', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('63', '16', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('17','2','YouTube', 'Mi servono informazioni dagli utenti che frequentano di frequente questa piattaforma', 'Grazie popolo dell internet', true, false,'2021-03-11','2021-04-12',false,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('64', '17', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('65', '17', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('66', '17', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('67', '17', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('18','2','Cyber security', 'Sondaggio per capire come gli utenti utilizzino la rete e come si proteggono, se si proteggono', 'Complimenti per aver conseguto la compilazione di questo sondaggio. I tuoi dati sono pronti per essere inviati a Google e alle industrie cinesi', true, true,'2021-04-23','2021-05-12',true,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('68', '18', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('69', '18', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('70', '18', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('71', '18', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('19','2','Blocco artistico', 'Sono in blocco e vorrei capire come fate voi ad uscire da questa scomoda situazione', 'I tuoi consigli saranno preziosi, grazie', true, true,'2021-04-22',null,false,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('72', '19', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('73', '19', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('74', '19', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('75', '19', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('20','2','Prova 18', 'Hey questo è il sesto sondaggio di prova guardatemiiiii', 'stavolta senza data', true, true,'2021-04-27',null,false,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('76', '20', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('77', '20', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('78', '20', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('79', '20', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('21','2','Attività sportiva', 'Come vivete lo sport in questa situazione di quarantena?', 'Grazie', true, true,'2021-04-26',null,true,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('80', '21', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('81', '21', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('82', '21', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('83', '21', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );

INSERT INTO `pollwebdb`.`Sondaggio`(`idSondaggio`,`idUtente`,`titolo`,`testoApertura`,`testoChiusura`,`completo`,`visibilita`,`dataCreazione`,`dataChiusura`,`privato`,`modificabile`,`compilazioni`)VALUES('22','2','Giochi da tavola', 'Anche voi come me giocate da tavola? Vediamo un po le vostre preferenze', 'Grazie', true, true,'2021-04-25',null,false,false,10);
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('84', '22', 'In effetti guardi ancora la TV? Se la risposta è no cosa usi?', true, 'Beh ci sono tanti mezzi online per vedere le nostre serie preferite no?', 0, '{}', 'openShort',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('85', '22', 'Cosa preferisci tra queste opzioni?', true, '', 1, '{"opzioni": ["Un bel Film", "Una Serie TV", "Un Documentario", "Un Cartone Animato"]}', 'closeSingle',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('86', '22', 'Ti piacerebbe poter scegliere una pizza?', true, 'vorrei capire se nel mio ristorante dovrei inserire un angolo pizzeria', 2, '{"opzioni": ["Azione", "Avventura", "Fantasy", "Fantascienza", "Animazione", "Romantico", "Thriller", "Horror"]}', 'closeMultiple',null );
INSERT INTO `pollwebdb`.`Domanda`(`idDomanda`,`idSondaggio`,`titolo`,`obbligatoria`,`descrizione`,`posizione`,`opzioni`,`tipo`,`vincolo`)VALUES('87', '22', 'Qual è il tuo film preferito?', true, 'questa è solo per curiosità', 3, '{}', 'openShort',null );




INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('4', '2', '2', 'zawardo', 'dioBrando@gmail.com', 'mudamuda',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('5', '2', '3', 'zawardo', 'dioBrando@gmail.com', 'mudamuda',false,true);
INSERT INTO `pollwebdb`.`ListaPartecipanti`(`idListaPartecipanti`,`idUtente`,`idSondaggio`,`nome`,`email`,`password`,`scaduto`,`emailMandata`)VALUES('6', '2', '7', 'zawardo', 'dioBrando@gmail.com', 'mudamuda',false,true);