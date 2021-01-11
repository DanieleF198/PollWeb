<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="Creazione sondaggi - impostazioni generali">
<html lang="it">
    <head>
        <title>${title}</title>
        <meta charset="${charset}">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <@macros.style imagePath="../images/favicon.ico" stylePath="../css/style.css" bootstrapPath="../css/bootstrap.css"/>
    </head>
    <body class="bg-light">
        <div class="d-flex flex-column min-vh-100">
            <@macros.header imagePath="../images/logoDDP.png"/>
            <main class="flex-fill">
                <div class="header-margin"></div>
                <div class="container-fluid">
                    <!-- General information -->
                    <div class="container">
                        <h1 class="h3 mb-3 font-weight-normal text-center">Ehy, i sondaggi non si completano da soli</h1>
                        <div class="mt-5"></div>
                        <div class="row justify-content-center">
                            <div class="col-6">
                                <p class="text-center">Stavi scrivendo il sondaggio <b>${titoloSondaggio}</b>. Vuoi continuare a scrivere questo sondaggio oppure vuoi iniziare a crearne uno nuovo?<p>
                                <p class="text-center text-muted">(tranquillo, potrai recuperare il sondaggio <b>${titoloSondaggio}</b> in qualsiasi momento nella tua zona utente)</p>
                            </div>
                        </div>
                        <form method="post" action="firstSection">
                            <div class="row justify-content-center">
                                <div class="col-6">
                                    <button name ="buttonContinue" value="continue" class="btn btn-warning btn-lg btn-block">Continua sondaggio</button>
                                </div>
                            </div>
                            <div class="mt-3"></div>
                            <div class="row justify-content-center">
                                <div class="col-6">
                                    <button name ="buttonNew" value="new" class="btn btn-secondary btn-lg btn-block">Crea nuovo sondaggio</button>
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
