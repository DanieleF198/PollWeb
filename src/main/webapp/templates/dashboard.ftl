<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="Dashboard">
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
                    <!-- Personal info -->
                    <div class="container-fluid">
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
                                    <p>Non sei ancora abilitato alla creazione di sondaggi? <a href="sendRespRequest" class="text-muted">clicca qui</a> per mandare la richiesta, richieder&#224; solo l'aggiunta del tuo codice fiscale!</p>
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
                                                    <p><a href="changeMail" class="text-info">Cambia email</a></p>
                                                </div>
                                                <div class="col-lg-12">
                                                    <p><a href="changePassword" class="text-info">Cambia password</a></p>
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
                                <div class="col-lg-9 col-md-8 col-sm-7 col-6">
                                    <h2>I Tuoi sondaggi</h2>
                                </div>
                                <div class="col-lg-3 col-md-4 col-sm-5 col-6 d-flex justify-content-end" >
                                    <a href="#" class="btn btn-light">Crea un sondaggio</a>
                                </div>
                            </div>
                            <div class="row mt-3">
                                <div class="col-lg-12">
                                    <div id="search-field">
                                        <input type="text" class="form-control "id="header-search" placeholder="Cerca..." />
                                        <svg id="search-icon" class="search-icon" viewBox="0 0 24 24">
                                            <path d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z" />
                                            <path d="M0 0h24v24H0z" fill="none" />
                                        </svg>
                                    </div>
                                </div>
                            </div>
                            <div class="row mt-3">
                                <div class="col-lg-12">
                                    <div class="table-responsive">
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
                            <div class="margin-pagination-scroll-bar"></div>
                            <div class="container-fluid">
                                <div class="container">
                                    <nav aria-label="Page navigation example">
                                        <ul class="pagination justify-content-end">
                                            <li class="page-item">
                                                <a class="page-link" href="#" aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                    <span class="sr-only">Previous</span>
                                                </a>
                                            </li>
                                            <li class="page-item"><a class="page-link" href="#">1</a></li>
                                            <li class="page-item"><a class="page-link" href="#">2</a></li>
                                            <li class="page-item"><a class="page-link" href="#">3</a></li>
                                            <li class="page-item">
                                                <a class="page-link" href="#" aria-label="Next">
                                                    <span aria-hidden="true">&raquo;</span>
                                                    <span class="sr-only">Next</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End personal polls -->
                
                    <!-- Private polls -->
                    <div class="container-fluid mt-5">
                        <div class="container bg-white border rounded-lg">
                            <div class="row pt-3 pb-2 pl-2 pr-2 bg-warning margin-bottom">
                                <div class="col-lg-12">
                                    <h2>Sondaggi privati</h2>
                                </div>
                            </div>
                            <div class="row mt-3">
                                <div class="col-lg-12">
                                    <div id="search-field">
                                        <input type="text" class="form-control "id="header-search" placeholder="Cerca..." />
                                        <svg id="search-icon" class="search-icon" viewBox="0 0 24 24">
                                            <path d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z" />
                                            <path d="M0 0h24v24H0z" fill="none" />
                                        </svg>
                                    </div>
                                </div>
                            </div>
                            <div class="row mt-3">
                                <div class="col-lg-12">
                                    <div class="table-responsive">
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
                                                    <td>Sondaggio 4</td>
                                                    <td>03/12/2020</td>
                                                    <td>05/12/2020</td>
                                                    <td>da fare</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">2</th>
                                                    <td>sondaggio 5</td>
                                                    <td>28/11/2020</td>
                                                    <td>28/12/2020</td>
                                                    <td>da fare</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">3</th>
                                                    <td>sondaggio 6</td>
                                                    <td>01/12/2020</td>
                                                    <td>31/01/2021</td>
                                                    <td>da fare</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="margin-pagination-scroll-bar"></div>
                            <div class="container-fluid">
                                <div class="container">
                                    <nav aria-label="Page navigation example">
                                        <ul class="pagination justify-content-end">
                                            <li class="page-item">
                                                <a class="page-link" href="#" aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                    <span class="sr-only">Previous</span>
                                                </a>
                                            </li>
                                            <li class="page-item"><a class="page-link" href="#">1</a></li>
                                            <li class="page-item"><a class="page-link" href="#">2</a></li>
                                            <li class="page-item"><a class="page-link" href="#">3</a></li>
                                            <li class="page-item">
                                                <a class="page-link" href="#" aria-label="Next">
                                                    <span aria-hidden="true">&raquo;</span>
                                                    <span class="sr-only">Next</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End private polls -->
                
                    <!-- Compilated polls -->
                    <div class="container-fluid mt-5">
                        <div class="container bg-white border rounded-lg">
                            <div class="row pt-3 pb-2 pl-2 pr-2 bg-warning margin-bottom">
                                <div class="col-lg-9 col-md-8 col-sm-7 col-6">
                                    <h2>Sondaggi compilati</h2>
                                </div>
                                <div class="col-lg-3 col-md-4 col-sm-5 col-6 d-flex justify-content-end" >
                                    <a href="#" class="btn btn-light">Vai ai sondaggi pubblici</a>
                                </div>
                            </div>
                            <div class="row mt-3">
                                <div class="col-lg-12">
                                    <div id="search-field">
                                        <input type="text" class="form-control "id="header-search" placeholder="Cerca..." />
                                        <svg id="search-icon" class="search-icon" viewBox="0 0 24 24">
                                            <path d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z" />
                                            <path d="M0 0h24v24H0z" fill="none" />
                                        </svg>
                                    </div>
                                </div>
                            </div>
                            <div class="row mt-3">
                                <div class="col-lg-12">
                                    <div class="table-responsive">
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
                                                    <td>Sondaggio 7</td>
                                                    <td>03/12/2020</td>
                                                    <td>05/12/2020</td>
                                                    <td>da fare</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">8</th>
                                                    <td>sondaggio 5</td>
                                                    <td>28/11/2020</td>
                                                    <td>28/12/2020</td>
                                                    <td>da fare</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">9</th>
                                                    <td>sondaggio 6</td>
                                                    <td>01/12/2020</td>
                                                    <td>31/01/2021</td>
                                                    <td>da fare</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="margin-pagination-scroll-bar"></div>
                            <div class="container-fluid">
                                <div class="container">
                                    <nav aria-label="Page navigation example">
                                        <ul class="pagination justify-content-end">
                                            <li class="page-item">
                                                <a class="page-link" href="#" aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                    <span class="sr-only">Previous</span>
                                                </a>
                                            </li>
                                            <li class="page-item"><a class="page-link" href="#">1</a></li>
                                            <li class="page-item"><a class="page-link" href="#">2</a></li>
                                            <li class="page-item"><a class="page-link" href="#">3</a></li>
                                            <li class="page-item">
                                                <a class="page-link" href="#" aria-label="Next">
                                                    <span aria-hidden="true">&raquo;</span>
                                                    <span class="sr-only">Next</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End compilated polls -->   
                </div>
            </main>
            <@macros.footer />
            <@macros.script />
        </div>
    </body>
</html>
