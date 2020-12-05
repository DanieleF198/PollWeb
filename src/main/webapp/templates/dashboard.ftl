<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="Dashboard">
<html lang="it">
    <head>
        <title>${title}</title>
        <meta charset="${charset}">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <@macros.style />
        <@macros.carouselOvverridingStyle />
    </head>
    <body class="bg-light">
        <div class="d-flex flex-column min-vh-100">
            <@macros.header />
            <main class="flex-fill">
                <div class="container-fluid">
                    <div style="margin-top:5%"></div>
                    <div class="container border rounded-lg">
                        <div class="row pt-3 pb-2 pl-2 pr-2 bg-warning margin-bottom">
                            <div class="col-lg-12">
                                <h2>Informazioni utente</h2>
                            </div>
                        </div>
                        <div class="row mt-2 pt-2 pb-1 pl-2 pr-2">
                            <div class="col-lg-1">
                                <p><b>Username: </b></p>
                            </div>
                            <div class="col-lg-3">
                                <p>Username</p>
                            </div>
                            <div class="col-lg-1">
                                <p><b>Mail: </b></p>
                            </div>
                            <div class="col-lg-3">
                                <p>indirizzo.mail@prova.it</p>
                            </div>
                            <div class="col-lg-1">
                                <p><b>tipologia: </b></p>
                            </div>
                            <div class="col-lg-3">
                                <p>responsabile</p>
                            </div>
                        </div>
                        <div class="row pt-1 pb-2 pl-2 pr-2">
                            <div class="col-lg-1">
                                <p><b>Nome: </b></p>
                            </div>
                            <div class="col-lg-3">
                                <p>nome</p>
                            </div>
                            <div class="col-lg-1">
                                <p><b>Cognome: </b></p>
                            </div>
                            <div class="col-lg-3">
                                <p>cognome</p>
                            </div>
                            <div class="col-lg-1">
                                <p><b>Et&#224;: </b></p>
                            </div>
                            <div class="col-lg-3">
                                <p>et&#224;</p>
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
