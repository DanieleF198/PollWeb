<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="Partecipant dashboard">
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
                                    <p>Ciao <b>${email}</b>, questa &#232; la tua area personale! </p>
                                    <p>Da questa pagina potrai facilmente consultare i sondaggi a cui sei stato invitato con le credenziali che hai inserito per entrare in questa sezione</p>
                                    <p>Puoi effettuare il logout da questa sezione <a href="logout" class="text-info">cliccando qui</a>.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End personal info -->
                    <!-- Sondaggi PRIVATI -->
                    <div class="container-fluid mt-5">
                        <div class="container bg-white border rounded-lg">
                            <div class="row pt-3 pb-2 pl-2 pr-2 bg-warning margin-bottom">
                                <div class="col-lg-9 col-md-8 col-sm-7 col-7">
                                    <h2>Sondaggi privati</h2>
                                </div>
                            </div>
                            <#if noSondaggiPriv?? && noSondaggiPriv!="">
                                <div class="row mt-3">
                                    <div class="col-lg-12 mb-2">
                                        <p class="h4 text-muted">Ops! pare che tu non abbia sondaggi da compilare!</h2>
                                    </div>
                                </div>
                            <#else>
                                <div class="row mt-3 javaScriptVisibility">
                                    <div class="col-lg-12 mb-2">
                                        <div class="table-responsive" style="overflow-x: hidden;">
                                            <table id='table-sondaggi-privati' class="table table-striped table-curved" style="width:99.5%">
                                                <thead>
                                                    <tr class="bg-warning">
                                                        <th scope="col">#</th>
                                                        <th scope="col">Titolo</th>
                                                        <th scope="col">Data creazione</th>
                                                        <th scope="col">Data scadenza </th>
                                                        <th scope="col" class="w-25"style="min-width:126px"></th>
                                                        <th scope="col" class="particular-width"></th>
                                                    </tr>
                                                </thead>
                                                <#assign c = 1>
                                                <tbody>
                                                    <#list sondaggiPriv as sondaggio>  
                                                        <tr>
                                                            <td>${c}</td>
                                                            <#if sondaggio.getTitolo()?length \gt 42>
                                                                <#assign titolo = sondaggio.getTitolo()?substring(0,42)>
                                                                <td>${titolo}...</td>
                                                            <#else>
                                                                <#assign titolo = sondaggio.getTitolo()>
                                                                <td>${titolo}</td>
                                                            </#if>
                                                            <td>${sondaggio.getCreazione()}</td>
                                                            <#if sondaggio.getScadenza()??>
                                                                <td>${sondaggio.getScadenza()}</td>
                                                            <#else>
                                                                <td>N/B</td>
                                                            </#if>
                                                            <td style="text-align:center;">
                                                                <div class="row justify-content-center">
                                                                    <div class="row mr-3 mt-2">
                                                                        <div class="col-6 pl-1 pr-1">
                                                                            <form id="compileSurvey${sondaggio.getKey()}" method="POST" action="survey">
                                                                                <button name="btnCompile" value="${sondaggio.getKey()}" class="btn brn-lg btn-warning" type="submit" title="compila">
                                                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-list-check" viewBox="0 0 16 16">
                                                                                        <path fill-rule="evenodd" d="M5 11.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM3.854 2.146a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708L2 3.293l1.146-1.147a.5.5 0 0 1 .708 0zm0 4a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708L2 7.293l1.146-1.147a.5.5 0 0 1 .708 0zm0 4a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 0 1 .708-.708l.146.147 1.146-1.147a.5.5 0 0 1 .708 0z"/>
                                                                                    </svg>
                                                                                </button> 
                                                                            </form>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td></td>
                                                        </tr>
                                                        <#assign c = c + 1>
                                                    </#list>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <div class="javascriptNoVisibility">
                                    <form method="post" action="partecipantDashboard" class="needs-validation" novalidate>
                                        <div class="row mt-3">
                                            <div class="col-lg-12">
                                                <div id="search-field">
                                                    <input type="text" name="header-search-sondaggi-privati" class="form-control" id="header-search" placeholder="Cerca..." />
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

                                    <#if listaSondaggiPrivatiVuota?? && listaSondaggiPrivatiVuota!="">
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <p class="h4 text-muted">Ops! pare che la tua ricerca non ti abbia portato a nessun sondaggio!</h2>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-9 col-md-8 col-sm-7 col-6"></div>
                                            <div class="col-lg-3 col-md-4 col-sm-5 col-6 mb-2 d-flex justify-content-end" >
                                                <a href="partecipantDashboard" class="btn btn-warning">Torna ai tuoi sondaggi</a>
                                            </div>
                                        </div>
                                    <#else>
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <div class="table-responsive">
                                                    <table id='table-sondaggi-privati-noscript' class="table table-striped table-curved">
                                                        <thead>
                                                            <tr class="bg-warning">
                                                                <th scope="col">#</th>
                                                                <th scope="col">Titolo</th>
                                                                <th scope="col">Data creazione</th>
                                                                <th scope="col">data scadenza</th>
                                                                <th scope="col"></th>
                                                            </tr>
                                                        </thead>
                                                        <#assign c = 1>
                                                        <tbody>
                                                            <#list sondaggiPriv as sondaggio>  
                                                                <tr>
                                                                    <th scope="row">${c}</th>
                                                                    <td >${sondaggio.getTitolo()}</td>
                                                                    <td>${sondaggio.getCreazione()}</td>
                                                                    <#if sondaggio.getScadenza()??>
                                                                        <td>${sondaggio.getScadenza()}</td>
                                                                    <#else>
                                                                        <td>Indeterminata</td>
                                                                    </#if>
                                                                    <td style="text-align:center;">
                                                                        <div class="row justify-content-center">
                                                                            <div class="row mr-3 mt-2">
                                                                                <div class="col-6 pl-1 pr-1">
                                                                                    <form id="compileSurvey${sondaggio.getKey()}" method="POST" action="survey">
                                                                                        <button name="btnCompile" value="${sondaggio.getKey()}" class="btn brn-lg btn-warning" type="submit" title="compila">
                                                                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-list-check" viewBox="0 0 16 16">
                                                                                                <path fill-rule="evenodd" d="M5 11.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM3.854 2.146a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708L2 3.293l1.146-1.147a.5.5 0 0 1 .708 0zm0 4a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708L2 7.293l1.146-1.147a.5.5 0 0 1 .708 0zm0 4a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 0 1 .708-.708l.146.147 1.146-1.147a.5.5 0 0 1 .708 0z"/>
                                                                                            </svg>
                                                                                        </button> 
                                                                                    </form>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <#assign c = c + 1> <!--non è l'ID-->
                                                            </#list>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <#if ricercaSondaggiPrivati?? && ricercaSondaggiPrivati!="">
                                            <div class="row">
                                                <div class="col-lg-9 col-md-8 col-sm-7 col-6"></div>
                                                <div class="col-lg-3 col-md-4 col-sm-5 col-6 mb-2 d-flex justify-content-end" >
                                                    <a href="partecipantDashboard" class="btn btn-warning">Annulla Ricerca</a>
                                                </div>
                                            </div>
                                        </#if>
                                    </#if>
                                </div>
                            </#if>
                            <div class="margin-pagination-scroll-bar"></div>      
                        </div>
                    </div>
                    <!-- END Sondaggi PRIVATI -->
                </div>
            </main>
            <@macros.footer />

            <@macros.script />
        </div>
    </body>


    <script>
        $(document).ready(function() {
            $('#table-sondaggi-privati').DataTable({
                "pageLength": 3,
                "bLengthChange": false,
                "bInfo": false,
                "bAutoWidth": false,
                "language": {
                    "search": "",
                    "zeroRecords": "Pare che non ci sia nessun sondaggio attribuito alla tua ricerca",
                    "paginate": {
                    "previous": "<",
                    "next": ">"
                    }},
                "aoColumns": [
                    { "bSortable": false },
                    { "bSortable": false },
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
                    $("#table-sondaggi-privati_filter input").prop('id', 'header-search');
                    $('.dataTables_filter input').prop('type', 'text');
                    $(".dataTables_filter input").prop('class', 'form-control');
                    $(".dataTables_filter input").prop("placeholder", "Cerca...");
                    $(".dataTables_filter label").prop('class', 'label-width-searchBox');
                    $("#table-sondaggi-privati_filter").prop('id', 'search-field1');               
                    $("#search-field1").append('<svg id="search-icon" class="search-icon" viewBox="0 0 24 24">' + '<path d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z" />' + '<path d="M0 0h24v24H0z" fill="none" />' + '</svg>');
                    $(".dataTables_filter").prop('class', '');
                }
            });
        } );
    </script>

</html>
