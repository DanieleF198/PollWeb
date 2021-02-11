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
                                    
                                    <p>PENSARE AD UNA FRASE DECENTE DA METTERE QUI O ELIMINARE LA SEZIONE</p>
                                    
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
                                    <h2>Sondaggi</h2>
                                </div>
                            </div>
                            <#if noTuoiSondaggi?? && noTuoiSondaggi!="">
                                <div class="row mt-3">
                                    <div class="col-lg-12 mb-2">
                                        <p class="h4 text-muted">Ops! pare che tu non abbia mai creato un tuo sondaggio</h2>
                                    </div>
                                </div>
                            <#else>
                                <div class="row mt-3 javaScriptVisibility">
                                    <div class="col-lg-12 mb-2">
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
                                                                <td>
                                                                    <form id="deleteSondaggio" method="POST" action="dashboard">
                                                                        <button name="btnDeleteSondaggio" value="${sondaggio.getKey()}" class="btn brn-lg btn-danger" type="submit">
                                                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x" viewBox="0 0 16 16">
                                                                                <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
                                                                            </svg>
                                                                        </button>
                                                                    </form>
                                                                </td>
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
                                                    <input type="text" name="header-search-sondaggi" class="form-control" id="header-search" placeholder="Cerca..." />
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
                                                <p class="h4 text-muted">Ops! Pare che la tua ricerca non ti abbia portato a nessun sondaggio</h2>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-9 col-md-8 col-sm-7 col-6"></div>
                                            <div class="col-lg-3 col-md-4 col-sm-5 col-6 mb-2 d-flex justify-content-end" >
                                                <a href="dashboard" class="btn btn-warning">annulla ricerca</a>
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
                                                                <td>${sondaggio.getTitolo()}</td>
                                                                <td>${sondaggio.getCreazione()}</td>
                                                                <#if sondaggio.getScadenza()??>
                                                                    <td>${sondaggio.getScadenza()}</td>
                                                                <#else>
                                                                    <td>Indeterminata</td>
                                                                </#if>
                                                                <td>
                                                                    <form id="deleteSondaggio" method="POST" action="dashboard">
                                                                        <button name="btnDeleteSondaggio" value="${sondaggio.getKey()}" class="btn brn-lg btn-danger" type="submit">
                                                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x" viewBox="0 0 16 16">
                                                                                <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
                                                                            </svg>
                                                                        </button>
                                                                    <form>
                                                                </td>
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
                
                    <!-- Users -->
                    <div class="container-fluid mt-5">
                        <div class="container bg-white border rounded-lg">
                            <div class="row pt-3 pb-2 pl-2 pr-2 bg-warning margin-bottom">
                                <div class="col-lg-9 col-md-8 col-sm-7 col-7">
                                    <h2>Utenti</h2>
                                </div>
                            </div>
                            <#if listaUtentiVuota?? && listaUtentiVuota!="">
                                <div class="row mt-3">
                                    <div class="col-lg-12 mb-2">
                                        <p class="h4 text-muted">Nessun utente</h2>
                                    </div>
                                </div>
                            <#else>
                                <div class="row mt-3 javaScriptVisibility">
                                    <div class="col-lg-12 mb-2">
                                        <div class="table-responsive">
                                            <table id='table-utenti' class="table table-striped table-curved">
                                                <thead>
                                                    <tr class="bg-warning">
                                                        <th scope="col">#</th>
                                                        <th scope="col">Nome</th>
                                                        <th scope="col">Cognome</th>
                                                        <th scope="col">E-mail</th>
                                                        <th scope="col">Tipo</th>
                                                        <th scope="col">operazioni</th>
                                                    </tr>
                                                </thead>
                                                <#assign c = 1>
                                                <tbody>
                                                    <#list utenti as utente>  
                                                        <#if utente.getIdGruppo()!= 3>
                                                            <tr>
                                                                <th scope="row">${c}</th>
                                                                <td>${utente.getNome()}</td>
                                                                <td>${utente.getCognome()}</td>
                                                                <td>${utente.getEmail()}</td>
                                                                <#if utente.getIdGruppo()== 1>
                                                                    <td>utente base</td>
                                                                <#else>
                                                                    <td>responsabile</td>
                                                                </#if>

                                                                <td style="text-align:center;">
                                                                    <div class="row justify-content-center">
                                                                        <div class="row mr-3 mt-2">
                                                                            <div class="col-6 pl-1 pr-1">
                                                                                 
                                                                                <form id="deleteUser" method="POST" action="dashboard">
                                                                                    <button name="btnDeleteUser" value="${utente.getKey()}" class="btn brn-lg btn-danger" type="submit">
                                                                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x" viewBox="0 0 16 16">
                                                                                            <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
                                                                                        </svg>
                                                                                    </button>
                                                                                </form>
                                                                            </div>
                                                                            <div class="col-6 pl-1 pr-1">
                                                                                <form id="banUser" method="POST" action="dashboard">
                                                                                    <#if utente.isBloccato()>
                                                                                        <button name="btnSbanUser" value="${utente.getKey()}" class="btn brn-lg btn-warning" type="submit">
                                                                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-lock" viewBox="0 0 16 16">
                                                                                                <path d="M8 1a2 2 0 0 1 2 2v4H6V3a2 2 0 0 1 2-2zm3 6V3a3 3 0 0 0-6 0v4a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2zM5 8h6a1 1 0 0 1 1 1v5a1 1 0 0 1-1 1H5a1 1 0 0 1-1-1V9a1 1 0 0 1 1-1z"/>
                                                                                            </svg> 
                                                                                        </button>
                                                                                    <#else>
                                                                                        <button name="btnBanUser" value="${utente.getKey()}" class="btn brn-lg btn-warning" type="submit">
                                                                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-unlock" viewBox="0 0 16 16">
                                                                                                <path d="M11 1a2 2 0 0 0-2 2v4a2 2 0 0 1 2 2v5a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V9a2 2 0 0 1 2-2h5V3a3 3 0 0 1 6 0v4a.5.5 0 0 1-1 0V3a2 2 0 0 0-2-2zM3 8a1 1 0 0 0-1 1v5a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V9a1 1 0 0 0-1-1H3z"/>
                                                                                            </svg>
                                                                                        </button>
                                                                                    </#if>
                                                                                </form>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                            <#assign c = c + 1> <!--non è l'ID-->
                                                        </#if>
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
                                                    <input type="text" name="header-search-utenti" class="form-control" id="header-search" placeholder="Cerca..." />
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

                                    <#if listaSearchUtentiVuota?? && listaSearchUtentiVuota!=""> <!-- TODO - barra di ricerca non ancora funzionante in noscript -->
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <p class="h4 text-muted">Nessun utente corrisponde alla tua ricerca</h2>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-9 col-md-8 col-sm-7 col-6"></div>
                                            <div class="col-lg-3 col-md-4 col-sm-5 col-6 mb-2 d-flex justify-content-end" >
                                                <a href="dashboard" class="btn btn-warning">Annulla ricerca</a>
                                            </div>
                                        </div>
                                    <#else>
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <div class="table-responsive">
                                                    <table id='table-utenti-noscript' class="table table-striped table-curved">
                                                        <thead>
                                                            <tr class="bg-warning">
                                                            <th scope="col">#</th>
                                                            <th scope="col">Nome</th>
                                                            <th scope="col">Cognome</th>
                                                            <th scope="col">E-mail</th>
                                                            <th scope="col">Tipo</th>
                                                            <th scope="col">operazioni</th>
                                                        </tr>
                                                        </thead>
                                                        <#assign c = 1>
                                                        <tbody>
                                                            <#list utenti as utente>  
                                                                <#if utente.getIdGruppo()!= 3>
                                                                    
                                                                        <tr>
                                                                            <th scope="row">${c}</th>
                                                                            <td>${utente.getNome()}</td>
                                                                            <td>${utente.getCognome()}</td>
                                                                            <td>${utente.getEmail()}</td>
                                                                            <#if utente.getIdGruppo()== 1>
                                                                                <td>utente base</td>
                                                                            <#else>
                                                                                <td>responsabile</td>
                                                                            </#if>
                                                                            <td style="text-align:center;">
                                                                                <div class="row justify-content-center">
                                                                                    <div class="row mr-3 mt-2">
                                                                                        <div class="col-6 pl-1 pr-1">

                                                                                            <form id="deleteUser" method="POST" action="dashboard">
                                                                                                <button name="btnDeleteUser" value="${utente.getKey()}" class="btn brn-lg btn-danger" type="submit">
                                                                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x" viewBox="0 0 16 16">
                                                                                                        <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
                                                                                                    </svg>
                                                                                                </button>
                                                                                            </form>
                                                                                        </div>
                                                                                        <div class="col-6 pl-1 pr-1">
                                                                                            <form id="banUser" method="POST" action="dashboard">
                                                                                                <#if utente.isBloccato()>
                                                                                                    <button name="btnSbanUser" value="${utente.getKey()}" class="btn brn-lg btn-warning" type="submit">
                                                                                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-lock" viewBox="0 0 16 16">
                                                                                                            <path d="M8 1a2 2 0 0 1 2 2v4H6V3a2 2 0 0 1 2-2zm3 6V3a3 3 0 0 0-6 0v4a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2zM5 8h6a1 1 0 0 1 1 1v5a1 1 0 0 1-1 1H5a1 1 0 0 1-1-1V9a1 1 0 0 1 1-1z"/>
                                                                                                        </svg> 
                                                                                                    </button>
                                                                                                <#else>
                                                                                                    <button name="btnBanUser" value="${utente.getKey()}" class="btn brn-lg btn-warning" type="submit">
                                                                                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-unlock" viewBox="0 0 16 16">
                                                                                                            <path d="M11 1a2 2 0 0 0-2 2v4a2 2 0 0 1 2 2v5a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V9a2 2 0 0 1 2-2h5V3a3 3 0 0 1 6 0v4a.5.5 0 0 1-1 0V3a2 2 0 0 0-2-2zM3 8a1 1 0 0 0-1 1v5a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V9a1 1 0 0 0-1-1H3z"/>
                                                                                                        </svg>
                                                                                                    </button>
                                                                                                </#if>
                                                                                            </form>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </td>
                                                                        </tr>
                                                                    
                                                                    <#assign c = c + 1>
                                                                </#if>
                                                            </#list>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <#if ricercaUtente?? && ricercaUtente!="">
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
                    <!-- End Users -->
                
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
                
            $('#table-utenti').DataTable({
                "pageLength": 3,
                "bLengthChange": false,
                "bInfo": false,
                "bAutoWidth": false,
                "language": {
                    "search": "",
                    "zeroRecords": "Pare che non ci sia nessun utente attribuito alla tua ricerca",
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
                    $("#table-utenti_filter input").prop('id', 'header-search');
                    $('.dataTables_filter input').prop('type', 'text');
                    $(".dataTables_filter input").prop('class', 'form-control');
                    $(".dataTables_filter input").prop("placeholder", "Cerca...");
                    $(".dataTables_filter label").prop('class', 'label-width-searchBox');
                    $("#table-utenti_filter").prop('id', 'search-field1');               
                    $("#search-field1").append('<svg id="search-icon" class="search-icon" viewBox="0 0 24 24">' + '<path d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z" />' + '<path d="M0 0h24v24H0z" fill="none" />' + '</svg>');
                    $(".dataTables_filter").prop('class', '');
                }
            });
        } );
    </script>

</html>
