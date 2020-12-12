<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="Cambia email">
<html lang="it">
    <head>
        <title>${title}</title>
        <meta charset="${charset}">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <@macros.style imagePath="images/favicon.ico" stylePath="css/style.css"/>
        <style>
            .form-signin {
                width: 100%;
                max-width: 330px;
                padding: 15px;
                margin: auto;
            }
            .form-signin {
                font-weight: 400;
            }
            .form-signin .form-control {
                position: relative;
                box-sizing: border-box;
                height: auto;
                padding: 10px;
                font-size: 16px;
            }
            .form-signin .form-control:focus {
                z-index: 2;
            }
            .form-signin input[type="email"] {
                margin-bottom: -1px;
                border-bottom-right-radius: 0;
                border-bottom-left-radius: 0;
            }
            .form-signin input[type="password"] {
                margin-bottom: 10px;
                border-top-left-radius: 0;
                border-top-right-radius: 0;
            }
            
            .footer {
                position: absolute;
                width: 100%;
                bottom: 0;
            }    
            
            @media screen and (max-height: 775px){
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
            <p class="h3 mb-3 font-weight-normal text-center">La sicurezza prima di tutto!</p>
            <p class="h5 font-weight-normal text-center">Per questo motivo ti chiediamo di contattarci se stai avendo problemi di sicurezza col tuo account.</p>
            <p class="h5 font-weight-normal text-center">Il nostro team &#232; sempre disposto ad aiutarti per ogni genere di problematica!</p>
            <div class="mt-3"></div>
            <form class="needs-validation" novalidate>
                <label for="currentMail">Email corrente</label>
                <input type="email" class="form-control" id="currentMail" placeholder="" value="" required> 
                <div class="invalid-feedback"> <!-- non so esattamente come funzioni ma per il momento ce lo lascio -->
                    La email inserita non coincide
                </div>
                <div class="mb-1"></div>
                <label for="newMail">Nuova email</label>
                <input type="email" class="form-control" id="newMail" placeholder="" value="" required> 
                <div class="invalid-feedback"> <!-- non so esattamente come funzioni ma per il momento ce lo lascio -->
                    La email inserita non va bene
                </div>
                <div class="mb-1"></div>
                <label for="confirmNewMail">Conferma email</label>
                <input type="email" class="form-control" id="confirmNewMail" placeholder="" value="" required> 
                <div class="invalid-feedback"> <!-- non so esattamente come funzioni ma per il momento ce lo lascio -->
                    La email inserita non va bene o non coincide
                </div>
                <div class="mb-3"></div>
                <button class="btn btn-lg btn-warning btn-block" type="submit">Cambia email</button>
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