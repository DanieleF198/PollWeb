<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="Quack, Duck, Poll!">
<html lang="it">
    <head>
        <title>${title}</title>
        <meta charset="${charset}">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <@macros.style imagePath="images/favicon.ico" stylePath="css/style.css" bootstrapPath="css/bootstrap.css"/>
        <@macros.carouselOvverridingStyle />
    </head>
    <body class="bg-light">
        <div class="d-flex flex-column min-vh-100">
            <@macros.header imagePath="images/logoDDP.png"/>
            <main class="flex-fill">
                <!-- Welcome section -->
                <div class="container-fluid" style="background-color: #FFFDEB">
                    <div class="container pl-5 pr-5">
                        <div class="row pt-5 pb-5">
                            <div class="col-lg-5 pl-5 align-self-center">
                                <h1>Quack, Duck, Poll!</h1>
                                <h2>Uno spazio confortevole dove creare sondaggi in base alle tue esigenze</h2>
                                <div class="pt-3">
                                    <#if sessioned?? && sessioned=="no">
                                        <a href="register" class="btn btn-warning">Comincia adesso!</a>
                                    <#else>
                                        <a href="/PollWeb/makerPoll/firstSection" class="btn btn-warning">Comincia adesso!</a>
                                    </#if>
                                </div>
                            </div>
                            <div class="col-lg-7 pr-5">
                                <img src="images/welcomeImage.png" alt="" loading="lazy" class ="img-fluid" style="margin-top:6%; height: auto">
                            </div>
                        </div>
                    </div>
                </div>
                <!-- End welcome section -->
                <!-- Mini-banners section -->
                <div class="container-fluid mb-5" style="background-color: #FFFBC9">  
                    <div class="container">
                        <div class="row pt-5 pb-5">
                            <div class="col-lg-1 align-self-center">
                                <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" fill="currentColor" class="bi bi-people" viewBox="0 0 16 16">
                                    <path d="M15 14s1 0 1-1-1-4-5-4-5 3-5 4 1 1 1 1h8zm-7.978-1A.261.261 0 0 1 7 12.996c.001-.264.167-1.03.76-1.72C8.312 10.629 9.282 10 11 10c1.717 0 2.687.63 3.24 1.276.593.69.758 1.457.76 1.72l-.008.002a.274.274 0 0 1-.014.002H7.022zM11 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4zm3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0zM6.936 9.28a5.88 5.88 0 0 0-1.23-.247A7.35 7.35 0 0 0 5 9c-4 0-5 3-5 4 0 .667.333 1 1 1h4.216A2.238 2.238 0 0 1 5 13c0-1.01.377-2.042 1.09-2.904.243-.294.526-.569.846-.816zM4.92 10A5.493 5.493 0 0 0 4 13H1c0-.26.164-1.03.76-1.724.545-.636 1.492-1.256 3.16-1.275zM1.5 5.5a3 3 0 1 1 6 0 3 3 0 0 1-6 0zm3-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4z"/>
                                </svg>
                            </div>
                            <div class="col-lg-3 align-self-center">
                                <p style="font-size: 20px; font-weight: 500;">Sondaggi pubblici</p>
                                <p>Crea o compila i sondaggi pubblici creati dalla community</p>
                            </div>
                            <div class="col-lg-1 align-self-center">
                                <svg width="64" height="64" viewBox="0 0 16 16" class="bi bi-file-text" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                    <path fill-rule="evenodd" d="M4 0h8a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2zm0 1a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H4z"/>
                                    <path fill-rule="evenodd" d="M4.5 10.5A.5.5 0 0 1 5 10h3a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zm0-2A.5.5 0 0 1 5 8h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zm0-2A.5.5 0 0 1 5 6h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zm0-2A.5.5 0 0 1 5 4h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5z"/>
                                </svg>
                            </div>
                            <div class="col-lg-3 align-self-center">
                                <p style="font-size: 20px; font-weight: 500;">Espording dei dati</p>
                                <p>Esporta e analizza i risultati del tuo sondaggio tramite un file .csv</p>
                            </div>
                            <div class="col-lg-1 align-self-center">
                                <svg width="64" height="64" viewBox="0 0 16 16" class="bi bi-lock" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                    <path fill-rule="evenodd" d="M11.5 8h-7a1 1 0 0 0-1 1v5a1 1 0 0 0 1 1h7a1 1 0 0 0 1-1V9a1 1 0 0 0-1-1zm-7-1a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h7a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2h-7zm0-3a3.5 3.5 0 1 1 7 0v3h-1V4a2.5 2.5 0 0 0-5 0v3h-1V4z"/>
                                </svg>
                            </div>
                            <div class="col-lg-3 align-self-center">
                                <p style="font-size: 20px; font-weight: 500;">Sondaggi privati</p>
                                <p>Crea un sondaggio e decidi chi pu&#242; rispondere tramite invito</p>                                
                            </div>
                        </div>
                    </div>
                </div>
                <!-- End mini-banners section -->
                <@macros.publicSurveyCorousel />
                
                <!-- Info section -->
                <div class="container-fluid mb-5">
                    <div class="container border-bottom">
                        <div class="row mb-5 border-bottom">
                            <div class="col-lg-8 mb-3">
                                <img src="images/compilazioneSondaggio.jpg" width="750" height="350" class ="img-fluid" alt="" loading="lazy">
                            </div>
                            <div class="col-lg-4">
                                <h3> compila i sondaggi...</h3>
                                <p> Se non ti basta rispondere ai vari sondaggi che i nostri utenti propongono ogni giorno, in pochi secondi, potresti iniziare a far parte della nostra community iscrivendoti al sito. </p>
                                <p> Dopo la registrazione avrai la possibilit&#224; di creare i tuoi sondaggi personali e di farli compilare a tutta la community di Quack, Duck, Poll! Avrai Inoltre la possibilità di condividere i tuoi sondaggi con gli amici (anche se non registrati) invitandoli ai tuoi sondaggi privati. </p>
                            </div>
                        </div>
                        <div class="row mb-5 border-bottom">
                            <div class="col-lg-4 order-2 order-lg-1">
                                <h3> ... oppure creali!</h3>
                                <p> La prerogativa principale di Quack, Duck, Poll! &#232; quella di poter essere uno strumento per la creazione e la compilazione di sondaggi in maniera rapida, semplice e intuitiva.  </p>
                                <p> Se non sei registrato puoi rispondere ai numerosi sondaggi proposti dai nostri utenti, o, se sei fortunato, qualcuno potrebbe invitarti attraverso un’e-mail a partecipare ad un suo sondaggio privato. </p>
                            </div>
                            <div class="col-lg-8 order-1 order-lg-2 mb-3">
                                <img src="images/creazioneSondaggio.jpg" width="750" height="350" class ="img-fluid" alt="" loading="lazy">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-8 mb-3">
                                <img src="images/dashboard.jpg" width="750" height="350" class ="img-fluid" alt="" loading="lazy">
                            </div>
                            <div class="col-lg-4">
                                <h3> La tua zona personale</h3>
                                <p> Una volta registrato al sito avrai accesso alla tua zona utente, dove potrai gestire tutti i tuoi sondaggi e visualizzare quelli a cui sei stato invitato.  </p>
                                <p> In questa sezione sarai in grado di sfruttare le principali funzioni messe a disposizione dal nostro sito, come modificare i tuoi sondaggi, disabilitarli temporaneamente, o scaricare le risposte di tutti gli utenti che li hanno compilati. </p>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- End info section -->
                <!-- Join us section -->
                <div class="container-fluid">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-12">
                                <#if sessioned?? && sessioned=="no">
                                    <p class="h3 text-center mb-5">Noi non ti chiediamo nulla, quindi non hai nulla da perdere.</p>
                                    <div class="form-inline">
                                        <a href="register" class="btn btn-warning btn-lg btn-block">Unisciti a noi</a>
                                    </div>
                                <#else>
                                    <p class="h3 text-center mb-5">Grazie di esserti unito alla community, ora &#232; tempo di sondaggi!</p>
                                    <div class="form-inline">
                                        <a href="/PollWeb/makerPoll/firstSection" class="btn btn-warning btn-lg btn-block">Unisciti a noi</a>
                                    </div>
                                </#if>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- End join us section -->
            </main>
            <@macros.footer />
            <@macros.script />
        </div>
    </body>
</html>
