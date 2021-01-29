<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="Dashboard">
<html lang="it">
    <head>
        <title>${title}</title>
        <meta charset="${charset}">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.23/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.css">
        <@macros.style imagePath="images/favicon.ico" stylePath="css/style.css" bootstrapPath="css/bootstrap.css"/>
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
                                                    <p class="text-left">${nome}</p>
                                                </div>
                                                <div class="col-lg-4">
                                                    <p class="text-left"><b>Cognome: </b></p>
                                                </div>
                                                <div class="col-lg-8">
                                                    <p class="text-left">${cognome}</p>
                                                </div>
                                                <div class="col-lg-4">
                                                    <p class="text-left"><b>Et&#224;: </b></p>
                                                </div>
                                                <div class="col-lg-8">
                                                    <p class="text-left">${eta}</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-4">
                                            <div class="row">
                                                <div class="col-lg-3">
                                                    <p class="text-left"><b>Email: </b></p>
                                                </div>
                                                <div class="col-lg-9">
                                                    <p class="text-left">${email}</p>
                                                </div>
                                                <div class="col-lg-3">
                                                    <p class="text-left"><b>Username: </b></p>
                                                </div>
                                                <div class="col-lg-9">
                                                    <p class="text-left">${username}</p>
                                                </div>
                                                <div class="col-lg-3">
                                                    <p class="text-left"><b>tipologia: </b></p>
                                                </div>
                                                <div class="col-lg-9">
                                                    <p class="text-left">${gruppo}</p>
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
                                <div class="col-lg-9 col-md-8 col-sm-7 col-7">
                                    <h2>I Tuoi sondaggi</h2>
                                </div>
                                <div class="col-lg-3 col-md-4 col-sm-5 col-5 d-flex justify-content-end pb-2" >
                                    <a href="/PollWeb/makerPoll/firstSection" class="btn btn-light">Crea un sondaggio</a>
                                </div>
                            </div>
                            <#if noTuoiSondaggi?? && noTuoiSondaggi!="">
                                <div class="row mt-3">
                                    <div class="col-lg-12 mb-2">
                                        <p class="h4 text-muted">Ops! pare che tu non abbia mai creato un tuo sondaggio</h2>
                                    </div>
                                </div>
                            <#else>gitgit 
                                <div class="row mt-3 javaScriptVisibility">
                                    <div class="col-lg-12">
                                        <div class="table-responsive">
                                            <table id='table-sondaggi' class="table table-striped table-curved">
                                                <thead>
                                                    <tr class="bg-warning">
                                                        <th scope="col">#</th>
                                                        <th scope="col">Titolo</th>
                                                        <th scope="col">Data creazione</th>
                                                        <th scope="col">data scadenza</th>
                                                        <th scope="col">operazioni</th>
                                                    </tr>
                                                </thead>
                                                <#assign c = 1>
                                                <tbody>
                                                    <#list sondaggi as sondaggio>  
                                                        <tr>
                                                            <th scope="row">${c}</th>
                                                            <td>${sondaggio.getTitolo()}</td>
                                                            <td>${sondaggio.getCreazione()}</td>
                                                            <#if sondaggio.getScadenza()??>
                                                                <td>${sondaggio.getScadenza()}</td>
                                                            <#else>
                                                                <td>Indeterminata</td>
                                                            </#if>
                                                            <td>da fare</td>
                                                        </tr>
                                                        <#assign c = c + 1> <!--non è l'ID-->
                                                    </#list>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <div class="javascriptNoVisibility">
                                    <form method="post" action="dashboard" class="needs-validation" novalidate>
                                        <div class="row mt-3">
                                            <div class="col-lg-12">
                                                <div id="search-field">
                                                    <input type="text" name="header-search-tuoi-sondaggi" class="form-control" id="header-search" placeholder="Cerca..." />
                                                    <button style="visibility: hidden; font-size:0;">
                                                        <svg id="search-icon" class="search-icon" viewBox="0 0 24 24" style="visibility: visible">
                                                            <path d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z" />
                                                            <path d="M0 0h24v24H0z" fill="none" />
                                                        </svg>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </form>

                                    <#if listaTuoiSondaggiVuota?? && listaTuoiSondaggiVuota!="">
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <h2>Ops! Pare che la tua ricerca non ti abbia portato a nessun sondaggio</h2>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-9 col-md-8 col-sm-7 col-6"></div>
                                            <div class="col-lg-3 col-md-4 col-sm-5 col-6 mb-2 d-flex justify-content-end" >
                                                <a href="dashboard" class="btn btn-warning">Torna ai tuoi sondaggi</a>
                                            </div>
                                        </div>
                                    <#else>
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <div class="table-responsive">
                                                    <table id='table-sondaggi-noscript' class="table table-striped table-curved">
                                                        <thead>
                                                            <tr class="bg-warning">
                                                                <th scope="col">#</th>
                                                                <th scope="col">Titolo</th>
                                                                <th scope="col">Data creazione</th>
                                                                <th scope="col">data scadenza</th>
                                                                <th scope="col">operazioni</th>
                                                            </tr>
                                                        </thead>
                                                        <#assign c = 1>
                                                        <tbody>
                                                            <#list sondaggi as sondaggio>  
                                                                <tr>
                                                                    <th scope="row">${c}</th>
                                                                    <td >${sondaggio.getTitolo()}</td>
                                                                    <td>${sondaggio.getCreazione()}</td>
                                                                    <#if sondaggio.getScadenza()??>
                                                                        <td>${sondaggio.getScadenza()}</td>
                                                                    <#else>
                                                                        <td>Indeterminata</td>
                                                                    </#if>
                                                                    <td>da fare</td>
                                                                </tr>
                                                                <#assign c = c + 1> <!--non è l'ID-->
                                                            </#list>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <#if ricercaTuoiSondaggi?? && ricercaTuoiSondaggi!="">
                                            <div class="row">
                                                <div class="col-lg-9 col-md-8 col-sm-7 col-6"></div>
                                                <div class="col-lg-3 col-md-4 col-sm-5 col-6 mb-2 d-flex justify-content-end" >
                                                    <a href="dashboard" class="btn btn-warning">Annulla Ricerca</a>
                                                </div>
                                            </div>
                                        </#if>
                                    </#if>
                                </div>
                            </#if>
                            
                            
                            
                            <div class="margin-pagination-scroll-bar"></div>      
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


    <script>
        $(document).ready(function() {
            $('#table-sondaggi').DataTable({
                "pageLength": 3,
                "bLengthChange": false,
                "bInfo": false,
                "bAutoWidth": false,
                "language": {
                    "search": "", 
                    "paginate": {
                    "previous": "<",
                    "next": ">"
                    }},
                "aoColumns": [
                    { "bSortable": true },
                    { "bSortable": false },
                    { "bSortable": false },
                    { "bSortable": false },
                    { "bSortable": false }
                ],
                //non so come funziona ma funziona quindi ok
                "dom": "<'row'<'col-lg-12 col-md-12 col-xs-12'f>>" +
                        "<'row'<'col-sm-12'tr>>" +
                        "<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",       
                            

                initComplete : function() {
                    $("#table-sondaggi_filter input").prop('id', 'header-search');
                    $('.dataTables_filter input').prop('type', 'text');
                    $(".dataTables_filter input").prop('class', 'form-control');
                    $(".dataTables_filter input").prop("placeholder", "Cerca...");
                    $(".dataTables_filter label").prop('class', 'label-width-searchBox');
                    $("#table-sondaggi_filter").prop('id', 'search-field');               
                    $("#search-field").append('<svg id="search-icon" class="search-icon" viewBox="0 0 24 24">' + '<path d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z" />' + '<path d="M0 0h24v24H0z" fill="none" />' + '</svg>');
                    $(".dataTables_filter").prop('class', '');
                }
            });
        } );
    </script>

</html>
