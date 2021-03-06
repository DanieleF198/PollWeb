<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="Cambia password">
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
            
            <#if error?? && error!="">
                <div>
                    <p class="text-danger d-flex justify-content-center">${error}</p>
                </div>
            </#if>

            <#if success?? && success!="">
                <div>
                    <p class="d-flex justify-content-center">${success}</p>
                </div>
            </#if>
            
            <div class="mt-3"></div>
            <form class="needs-validation" novalidate>
                <label for="currentPassword">Password corrente</label>
                <input type="password" class="form-control" id="currentPassword" name="currentPassword" placeholder="" value="" required> 
                <div class="invalid-feedback"> <!-- non so esattamente come funzioni ma per il momento ce lo lascio -->
                    La password inserita non coincide
                </div>
                <div class="mb-1"></div>
                <label for="newPassword">Nuova password</label>
                <input type="password" class="form-control" id="newPassword" name="newPassword" placeholder="" value="" required> 
                <div class="invalid-feedback"> <!-- non so esattamente come funzioni ma per il momento ce lo lascio -->
                    La password inserita non va bene
                </div>
                <div class="mb-1"></div>
                <label for="confirmNewPassword">Conferma password</label>
                <input type="password" class="form-control" id="confirmNewPassword" name="confirmNewPassword" placeholder="" value="" required> 
                <div class="invalid-feedback"> <!-- non so esattamente come funzioni ma per il momento ce lo lascio -->
                    La password inserita non va bene o non coincide
                </div>
                <div class="mb-3"></div>
                <button class="btn btn-lg btn-warning btn-block" name="btnChangePassword" type="submit">Cambia password</button>
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