<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="Creazione sondaggi - conferma sondaggi">
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
                        <h1 class="h3 mb-3 font-weight-normal">Creazione sondaggio - Ultime informazioni e conferma</h1>
                        <form method="post" action="questionsMaker" class="needs-validation" enctype="multipart/form-data" novalidate>
                            <div class="row mt-3">
                                <div class="col-lg-12">
                                    <p><b>Ordinamento Domande</b></p>
                                    <div class="table-responsive">
                                        <table class="table table-striped table-curved">
                                            <thead>
                                                <tr class="bg-warning">
                                                    <th scope="col">#</th>
                                                    <th scope="col">Titolo</th>
                                                    <th scope="col">descrizione</th>
                                                    <th scope="col">tipo</th>
                                                    <th scope="col">operazioni</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <th scope="row">1</th>
                                                    <td>Domanda 1</td>
                                                    <td>descrizione</td>
                                                    <td>scelta singola</td>
                                                    <td>da fare</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">2</th>
                                                    <td>Domanda 2</td>
                                                    <td>descrizione</td>
                                                    <td>scelta multipla</td>
                                                    <td>da fare</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">3</th>
                                                    <td>Domanda 3</td>
                                                    <td>descrizione</td>
                                                    <td>testo breve</td>
                                                    <td>da fare</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">4</th>
                                                    <td>Domanda 4</td>
                                                    <td>descrizione</td>
                                                    <td>testo lungo</td>
                                                    <td>da fare</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">5</th>
                                                    <td>Domanda 5</td>
                                                    <td>descrizione</td>
                                                    <td>numero</td>
                                                    <td>da fare</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="row mt-3">
                                <div class="col-lg-12">
                                    <p><b>Partecipanti da invitare</b></p>
                                    <p><b>Attenzione</b> il file da caricare deve essere un file con estenzione .csv</p>
                                    <input type="file" id="partecipants" class="form-control-file" id="exampleFormControlFile1">
                                </div>
                            </div>
                            <div class="row mt-3">
                                <div class="col-lg-6 col-md-6 mb-2 custom-right">
                                    <button type="submit" name="createOnly" value="create" class="btn btn-lg btn-warning btn-block">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-left" viewBox="0 0 16 16">
                                            <path d="M4 0h5.5v1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V4.5h1V14a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2z"/>
                                            <path d="M9.5 3V0L14 4.5h-3A1.5 1.5 0 0 1 9.5 3z"/>
                                            <path fill-rule="evenodd" d="M8 6.5a.5.5 0 0 1 .5.5v1.5H10a.5.5 0 0 1 0 1H8.5V11a.5.5 0 0 1-1 0V9.5H6a.5.5 0 0 1 0-1h1.5V7a.5.5 0 0 1 .5-.5z"/>
                                        </svg>
                                        Crea sondaggio
                                    </button>
                                </div>
                                <div class="col-lg-6 col-md-6 mb-2 custom-left">
                                    <button type="submit" name="createAndPublic" value="public" class="btn btn-lg btn-warning btn-block">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-left" viewBox="0 0 16 16">
                                            <path fill-rule="evenodd" d="M15 14s1 0 1-1-1-4-5-4-5 3-5 4 1 1 1 1h8zm-7.978-1h7.956a.274.274 0 0 0 .014-.002l.008-.002c-.002-.264-.167-1.03-.76-1.72C13.688 10.629 12.718 10 11 10c-1.717 0-2.687.63-3.24 1.276-.593.69-.759 1.457-.76 1.72a1.05 1.05 0 0 0 .022.004zM11 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4zm3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0zM6.936 9.28a5.88 5.88 0 0 0-1.23-.247A7.35 7.35 0 0 0 5 9c-4 0-5 3-5 4 0 .667.333 1 1 1h4.216A2.238 2.238 0 0 1 5 13c0-1.01.377-2.042 1.09-2.904.243-.294.526-.569.846-.816zM4.92 10c-1.668.02-2.615.64-3.16 1.276C1.163 11.97 1 12.739 1 13h3c0-1.045.323-2.086.92-3zM1.5 5.5a3 3 0 1 1 6 0 3 3 0 0 1-6 0zm3-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4z"/>
                                        </svg>
                                        Crea sondaggio e pubblicalo
                                    </button>
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
