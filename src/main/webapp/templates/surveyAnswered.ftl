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
                <div class="header-margin"></div>
                <div class="container-fluid">
                    <div class="container">
                        <div class="row">
                            <div class="col-12">
                                <p>questa pagina e' da realizzare, comunque questo e' il sondaggio con titolo ${sondaggio.getTitolo()}</p>
                                <p>Per inciso, l'utente che ha risposto ha indirizzo: ${remoteAddr}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
            <@macros.footer />
            <@macros.script />
        </div>
    </body>
</html>

