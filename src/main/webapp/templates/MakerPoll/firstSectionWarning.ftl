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
                        <p>vuoi continuare con il sondaggio ${titoloSondaggio} oppure vuoi iniziare a crearne uno nuovo?<p>
                        <p class="text-muted">(tranquillo, potrai recuperare il tuo sondaggio nella tua zona utente)</p>
                        <form method="post" action="firstSection">
                            <button name ="buttonContinue" value="continue" class="btn btn-warning btn-lg">Unisciti alla community</button>
                            <button name ="buttonNew" value="new" class="btn btn-secondary btn-lg btn-block">Unisciti alla community</button>
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
