<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="Responsable request">
<html lang="it">
    <head>
        <title>${title}</title>
        <meta charset="${charset}">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <@macros.style imagePath="images/favicon.ico" stylePath="css/style.css" bootstrapPath="css/bootstrap.css"/>
        <style>
            .footer {
                position: absolute;
                width: 100%;
                bottom: 0;
            }    
            
            @media screen and (max-height: 645px){
                .footer{
                    position: relative;
                }
            }
        </style>
    </head>
    <body class="bg-light">
        <!-- Login form -->
        <div class="container" style="max-width: 500px;">
            <div class="text-center">
                <img class="mb-4" src="images/logoDDP.png" alt="" width="140" height="90">
            </div>
            <p class="h3 mb-3 font-weight-normal text-center">Quindi vuoi diventare responsabile?</p>
            <p class="h5 font-weight-normal text-center">Da grandi poteri, derivano grandi responsabilit&#224;... Ma la responsabilit&#224; &#232; anche nostra che ti offriamo questo servizio.</p>
            <p class="h5 font-weight-normal text-center">Per questo motivo ti chiediamo di fornirci il tuo codice fiscale, per tutelare tutti da comportamenti scorretti.</p>
            <div class="mt-3"></div>
            <form class="needs-validation" novalidate>
                <label for="CF">Codice Fiscale</label>
                <input type="text" class="form-control" name ="CF" id="CF" placeholder="" value="" required> 
                <div class="invalid-feedback"> <!-- non so esattamente come funzioni ma per il momento ce lo lascio -->
                    Il codice fiscale inserito non è valido.
                </div>
                <div class="mb-3"></div>
                <button name ="buttonSendRespRequest" value="sendRespRequest" class="btn btn-lg btn-warning btn-block" type="submit">Manda la richiesta!</button>
                <div class="mt-2 mb-3 text-center">
                    <a href="dashboard" class="text-muted">Torna alla zona utente</a>
                </div>
            </form>
        </div>
        <!-- End login form -->
        <@macros.footer />
        <@macros.script />
    </body>
</html>