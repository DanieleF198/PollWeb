<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="Creazione sondaggi">
<html lang="it">
    <head>
        <title>${title}</title>
        <meta charset="${charset}">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <@macros.style imagePath="../images/favicon.ico" stylePath="../css/style.css"/>
    </head>
    <body class="bg-light">
        <div class="d-flex flex-column min-vh-100">
            <@macros.header imagePath="../images/logoDDP.png"/>
            <main class="flex-fill">
                <div class="header-margin"></div>
                <div class="container-fluid">
                    <!-- General information -->
                    <div class="container">
                        <h1 class="h3 mb-3 font-weight-normal">Creazione sondaggio - informazioni generali</h1>
                        <form class="needs-validation" novalidate>
                            <div class="row mb-3">
                                <div class="col-lg-12">
                                    <label class="h5" for="title">Titolo</label>
                                    <input type="text" class="form-control" id="title" placeholder="" value="" required> 
                                    <div class="invalid-feedback"> <!-- non so esattamente come funzioni ma per il momento ce lo lascio -->
                                        Il titolo inserito non è valido.
                                    </div>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-lg-12 mb-3">
                                    <label class="h5" for="description">Descrizione (facoltativa)</label>
                                    <textarea rows="5" class="form-control" id="description" placeholder="Questa descrizione apparir&#224; sia nella preview del sondaggio che nella pagina prima della compilazione" value=""></textarea>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-lg-12 mb-3">
                                    <label class="h5" for="description">Messaggio di completamento (facoltativo)</label>
                                    <textarea rows="3" class="form-control" id="description" placeholder="Messaggio che apparir&#224; alla fine della compilazione, se non riempito (min. x caratteri) apparir&#224; il messaggio di default" value=""></textarea>
                                </div>
                            </div>
                            <div class="border-bottom mb-3"></div>
                            <div class="row">
                                <div class="col-lg-12 mb-2">
                                    <p class="h3 mb-3 font-weight-normal">Creazione sondaggio - impostazioni</h1>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12 mb-2">
                                    <p>Se il sondaggio privato non &#232; disponibile, &#232; perch&#232; devi richiedere l'abilitazione, <a href="/PollWeb/sendRespRequest" class="text-info">clicca qui</a> per mandare la richiesta, richieder&#224; solo l'aggiunta del tuo codice fiscale!</p>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-lg-3 mb-3">
                                    <div>
                                        <label class="checkbox mb-3">
                                            <input type="checkbox" value="remember-me"/>
                                            <span class="warning"></span>
                                        </label>
                                        &nbspSondaggio privato
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <!-- End general information -->
                </div>
            </main>
    <@macros.footer />
    <@macros.script />
        </div>
    </body>
</html>
