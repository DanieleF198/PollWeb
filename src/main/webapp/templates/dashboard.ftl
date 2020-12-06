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
                <!-- Personal info -->
                <div class="container-fluid">
                    <div style="margin-top:5%"></div>
                    <div class="container bg-white border rounded-lg">
                        <div class="row pt-3 pb-2 pl-2 pr-2 bg-warning margin-bottom">
                            <div class="col-lg-12">
                                <h2>Informazioni utente</h2>
                            </div>
                        </div>
                        <div class="row mt-2 pt-2 pb-1 pl-2 pr-2">
                            <div class="col-lg-12">
                                <p>Ciao <b>${username}</b>, questa &#232; la tua area personale! </p>
                                <p>Da questa pagina potrai facilmente consultare e modificare i tuoi dati, rivedere i sondaggi che hai compilato, e se sei abilitato alla creazione dei sondaggi, gestire i tuoi sondaggi.</p>
                                <p>Non sei ancora abilitato alla creazione di sondaggi? <a href="#" class="text-muted">clicca qui</a> per mandare la richiesta, richieder&#224; solo l'aggiunta del tuo codice fiscale!</p>
                                <p><b>I tuoi dati personali:</b></p>
                                <div class="row">
                                    <div class="col-lg-3">
                                        <div class="row">
                                            <div class="col-lg-4">
                                                <p class="text-left"><b>Nome: </b></p>
                                            </div>
                                            <div class="col-lg-8">
                                                <p class="text-left">nome</p>
                                            </div>
                                            <div class="col-lg-4">
                                                <p class="text-left"><b>Cognome: </b></p>
                                            </div>
                                            <div class="col-lg-8">
                                                <p class="text-left">cognome</p>
                                            </div>
                                            <div class="col-lg-4">
                                                <p class="text-left"><b>Et&#224;: </b></p>
                                            </div>
                                            <div class="col-lg-8">
                                                <p class="text-left">et&#224;</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-4">
                                        <div class="row">
                                            <div class="col-lg-3">
                                                <p class="text-left"><b>Email: </b></p>
                                            </div>
                                            <div class="col-lg-9">
                                                <p class="text-left">indirizzo.mail@prova.it</p>
                                            </div>
                                            <div class="col-lg-3">
                                                <p class="text-left"><b>Username: </b></p>
                                            </div>
                                            <div class="col-lg-9">
                                                <p class="text-left">Username</p>
                                            </div>
                                            <div class="col-lg-3">
                                                <p class="text-left"><b>tipologia: </b></p>
                                            </div>
                                            <div class="col-lg-9">
                                                <p class="text-left">tipologia</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-4">
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <p><a href="#" class="text-info">Cambia email</a></p>
                                            </div>
                                            <div class="col-lg-12">
                                                <p><a href="#" class="text-info">Cambia password</a></p>
                                            </div>
                                        </div>
                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- End personal info -->
                <!-- Personal polls -->
                <div class="container-fluid mt-5">
                    <div class="container bg-white border rounded-lg">
                        <div class="row pt-3 pb-2 pl-2 pr-2 bg-warning margin-bottom">
                            <div class="col-lg-12">
                                <h2>I sondaggi creati da te</h2>
                            </div>
                        </div>
                        <div class="row mt-3">
                            <div class="col-lg-12">
                                <input type="text" class="form-control" placeholder="Cerca" style="border-radius: 1em 1em 1em 1em !important;">
                            </div>
                        </div>
                        <div class="row mt-3">
                            <div class="col-lg-12">
                                <table class="table table-striped table-curved">
                                    <thead>
                                        <tr class="bg-warning">
                                            <th scope="col">#</th>
                                            <th scope="col">Titolo</th>
                                            <th scope="col">Data creazione</th>
                                            <th scope="col">data scadenza</th>
                                            <th scope="col">operazioni</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <th scope="row">1</th>
                                            <td>sondaggio 1</td>
                                            <td>03/12/2020</td>
                                            <td>05/12/2020</td>
                                            <td>da fare</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">2</th>
                                            <td>sondaggio 2</td>
                                            <td>28/11/2020</td>
                                            <td>28/12/2020</td>
                                            <td>da fare</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">3</th>
                                            <td>sondaggio 3</td>
                                            <td>01/12/2020</td>
                                            <td>31/01/2021</td>
                                            <td>da fare</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- End personal polls -->
            </main>
            <@macros.footer />
            <@macros.script />
        </div>
    </body>
</html>
