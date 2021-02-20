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

<<<<<<< HEAD
                                        
=======
                                    <p>Ciao <b>${username}</b>, questa &#232; la tua area personale! </p>
                                    <p>Da questa pagina potrai facilmente consultare e modificare i tuoi dati, rivedere i sondaggi che hai compilato, gestire i tuoi sondaggi, e visualizzare i sondaggi alla quale sei stato invitato.</p>
                                    <p><b>I tuoi dati personali:</b></p>
>>>>>>> 483e1b5ac0ed01b45c7c45b69193e4b3559bb898

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
                                        <p class="h4 text-muted">Ops! pare che tu non abbia mai creato un sondaggio!</h2>
                                    </div>
                                </div>
                            <#else>
                                <div class="row mt-3 javaScriptVisibility">
                                    <div class="col-lg-12 mb-2">
                                        <div class="table-responsive" style="overflow-x: hidden;">
                                            <table id='table-sondaggi' class="table table-striped table-curved" style="width:99.5%">
                                                <thead>
                                                    <tr class="bg-warning">
                                                        <th scope="col">#</th>
                                                        <th scope="col">Titolo</th>
                                                        <th scope="col" class="particular-visibility">Data creazione</th>
                                                        <th scope="col">Data scadenza </th>
                                                        <th scope="col" style="width: 40%; min-width:126px"></th>
                                                        <th scope="col" class="particular-width"></th>
                                                    </tr>
                                                </thead>
                                                <#assign c = 1>
                                                <tbody>
                                                    <#list sondaggi as sondaggio>  
                                                        <tr>
                                                            <td>${c}</td>
                                                            <#if sondaggio.getTitolo()?length \gt 42>
                                                                <#assign titolo = sondaggio.getTitolo()?substring(0,42)>
                                                                <td>${titolo}...</td>
                                                            <#else>
                                                                <#assign titolo = sondaggio.getTitolo()>
                                                                <td>${titolo}</td>
                                                            </#if>
                                                            <td class="particular-visibility">${sondaggio.getCreazione()}</td>
                                                            <#if sondaggio.getScadenza()??>
                                                                <td>${sondaggio.getScadenza()}</td>
                                                            <#else>
                                                                <td>N/B</td>
                                                            </#if>
                                                            <td style="text-align:center;">
                                                                <div class="row justify-content-center">
                                                                    <div class="row mr-3 mt-2">
                                                                        <div class="col-6 pl-1 pr-1">
                                                                            <form id="changeVisibility${sondaggio.getKey()}" method="POST" action="dashboard">
                                                                                <#if sondaggio.isVisibilita()>
                                                                                    <button name="changeVisibility" value="${sondaggio.getKey()}" class="btn brn-lg btn-warning" type="submit" title="attivo">
                                                                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
                                                                                            <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
                                                                                            <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
                                                                                        </svg>
                                                                                    </button>
                                                                                <#else>
                                                                                    <button name="changeVisibility" value="${sondaggio.getKey()}" class="btn brn-lg btn-warning" type="submit" title="disattivo">
                                                                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye-slash" viewBox="0 0 16 16">
                                                                                            <path d="M13.359 11.238C15.06 9.72 16 8 16 8s-3-5.5-8-5.5a7.028 7.028 0 0 0-2.79.588l.77.771A5.944 5.944 0 0 1 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.134 13.134 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755-.165.165-.337.328-.517.486l.708.709z"/>
                                                                                            <path d="M11.297 9.176a3.5 3.5 0 0 0-4.474-4.474l.823.823a2.5 2.5 0 0 1 2.829 2.829l.822.822zm-2.943 1.299l.822.822a3.5 3.5 0 0 1-4.474-4.474l.823.823a2.5 2.5 0 0 0 2.829 2.829z"/>
                                                                                            <path d="M3.35 5.47c-.18.16-.353.322-.518.487A13.134 13.134 0 0 0 1.172 8l.195.288c.335.48.83 1.12 1.465 1.755C4.121 11.332 5.881 12.5 8 12.5c.716 0 1.39-.133 2.02-.36l.77.772A7.029 7.029 0 0 1 8 13.5C3 13.5 0 8 0 8s.939-1.721 2.641-3.238l.708.709zm10.296 8.884l-12-12 .708-.708 12 12-.708.708z"/>
                                                                                        </svg>    
                                                                                    </button>
                                                                                </#if>
                                                                            </form>
                                                                        </div>
                                                                        <div class="col-6 pl-1 pr-1">
                                                                            <form id="modSurvey${sondaggio.getKey()}" method="POST" action="dashboard">
                                                                                <button name="modSurvey" value="${sondaggio.getKey()}" class="btn brn-lg btn-light" type="submit" title="modifica">
                                                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">
                                                                                        <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5L13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175l-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
                                                                                    </svg>
                                                                                </button> 
                                                                            </form>
                                                                        </div>
                                                                    </div>
                                                                    <div class="row mr-3 mt-2">
                                                                        <div class="col-6 pl-1 pr-1">
                                                                            <form id="changePartecipants${sondaggio.getKey()}" method="POST" action="changePartecipants">
                                                                                <#if sondaggio.isPrivato()>
                                                                                    <button name="changePartecipantsForm" value="${sondaggio.getKey()}" class="btn brn-lg btn-info" type="submit" title="modifica partecipanti">
                                                                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-people" viewBox="0 0 16 16">
                                                                                            <path d="M15 14s1 0 1-1-1-4-5-4-5 3-5 4 1 1 1 1h8zm-7.978-1A.261.261 0 0 1 7 12.996c.001-.264.167-1.03.76-1.72C8.312 10.629 9.282 10 11 10c1.717 0 2.687.63 3.24 1.276.593.69.758 1.457.76 1.72l-.008.002a.274.274 0 0 1-.014.002H7.022zM11 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4zm3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0zM6.936 9.28a5.88 5.88 0 0 0-1.23-.247A7.35 7.35 0 0 0 5 9c-4 0-5 3-5 4 0 .667.333 1 1 1h4.216A2.238 2.238 0 0 1 5 13c0-1.01.377-2.042 1.09-2.904.243-.294.526-.569.846-.816zM4.92 10A5.493 5.493 0 0 0 4 13H1c0-.26.164-1.03.76-1.724.545-.636 1.492-1.256 3.16-1.275zM1.5 5.5a3 3 0 1 1 6 0 3 3 0 0 1-6 0zm3-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4z"/>
                                                                                        </svg>
                                                                                    </button>
                                                                                <#else>
                                                                                    <button name="changePartecipantsForm" value="${sondaggio.getKey()}" class="btn brn-lg btn-info" type="submit" title="modifica partecipanti" disabled>
                                                                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-people" viewBox="0 0 16 16">
                                                                                            <path d="M15 14s1 0 1-1-1-4-5-4-5 3-5 4 1 1 1 1h8zm-7.978-1A.261.261 0 0 1 7 12.996c.001-.264.167-1.03.76-1.72C8.312 10.629 9.282 10 11 10c1.717 0 2.687.63 3.24 1.276.593.69.758 1.457.76 1.72l-.008.002a.274.274 0 0 1-.014.002H7.022zM11 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4zm3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0zM6.936 9.28a5.88 5.88 0 0 0-1.23-.247A7.35 7.35 0 0 0 5 9c-4 0-5 3-5 4 0 .667.333 1 1 1h4.216A2.238 2.238 0 0 1 5 13c0-1.01.377-2.042 1.09-2.904.243-.294.526-.569.846-.816zM4.92 10A5.493 5.493 0 0 0 4 13H1c0-.26.164-1.03.76-1.724.545-.636 1.492-1.256 3.16-1.275zM1.5 5.5a3 3 0 1 1 6 0 3 3 0 0 1-6 0zm3-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4z"/>
                                                                                        </svg>
                                                                                    </button>
                                                                                </#if>
                                                                            </form>
                                                                        </div>
                                                                        <div class="col-6 pl-1 pr-1">
                                                                            <form id="removeSurvey${sondaggio.getKey()}" method="POST" action="dashboard">
                                                                                <button name="removeSurvey" value="${sondaggio.getKey()}" class="btn brn-lg btn-danger" type="submit" title="elimina">
                                                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x" viewBox="0 0 16 16">
                                                                                        <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
                                                                                    </svg>
                                                                                </button>
                                                                            </form>
                                                                        </div>
                                                                    </div>
                                                                    <div class="row mr-3 mt-2">
                                                                        <div class="col-6 pl-1 pr-1">
                                                                            <form id="downloadAnswer${sondaggio.getKey()}" method="POST" action="dashboard">
                                                                                <button name="downloadAnswer" value="${sondaggio.getKey()}" class="btn brn-lg btn-success" type="submit" title="scarica risposte">
                                                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark-arrow-down" viewBox="0 0 16 16">
                                                                                        <path d="M8.5 6.5a.5.5 0 0 0-1 0v3.793L6.354 9.146a.5.5 0 1 0-.708.708l2 2a.5.5 0 0 0 .708 0l2-2a.5.5 0 0 0-.708-.708L8.5 10.293V6.5z"/>
                                                                                        <path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2zM9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5v2z"/>
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
                                                <p class="h4 text-muted">Ops! Pare che la tua ricerca non ti abbia portato a nessun sondaggio!</h2>
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
                                                                <th scope="col" class="particular-visibility">Data creazione</th>
                                                                <th scope="col">Data scadenza </th>
                                                                <th scope="col" style="width: 40%; min-width:126px"></th>
                                                                <th scope="col" class="particular-width"></th>
                                                            </tr>
                                                        </thead>
                                                        <#assign c = 1>
                                                        <tbody>
                                                            <#list sondaggi as sondaggio>  
                                                                <tr>
                                                                    <th scope="row">${c}</th>
                                                                    <td >${sondaggio.getTitolo()}</td>
                                                                    <td class="particular-visibility">${sondaggio.getCreazione()}</td>
                                                                    <#if sondaggio.getScadenza()??>
                                                                        <td>${sondaggio.getScadenza()}</td>
                                                                    <#else>
                                                                        <td>N/B</td>
                                                                    </#if>
                                                                    <td style="text-align:center;">
                                                                        <div class="row justify-content-center">
                                                                            <div class="row mr-3 mt-2">
                                                                                <div class="col-6 pl-1 pr-1">
                                                                                    <form id="changeVisibility${sondaggio.getKey()}" method="POST" action="dashboard">
                                                                                        <#if sondaggio.isVisibilita()>
                                                                                            <button name="changeVisibility" value="${sondaggio.getKey()}" class="btn brn-lg btn-warning" type="submit" title="attivo">
                                                                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
                                                                                                    <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
                                                                                                    <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
                                                                                                </svg>
                                                                                            </button>
                                                                                        <#else>
                                                                                            <button name="changeVisibility" value="${sondaggio.getKey()}" class="btn brn-lg btn-warning" type="submit" title="disattivo">
                                                                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye-slash" viewBox="0 0 16 16">
                                                                                                    <path d="M13.359 11.238C15.06 9.72 16 8 16 8s-3-5.5-8-5.5a7.028 7.028 0 0 0-2.79.588l.77.771A5.944 5.944 0 0 1 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.134 13.134 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755-.165.165-.337.328-.517.486l.708.709z"/>
                                                                                                    <path d="M11.297 9.176a3.5 3.5 0 0 0-4.474-4.474l.823.823a2.5 2.5 0 0 1 2.829 2.829l.822.822zm-2.943 1.299l.822.822a3.5 3.5 0 0 1-4.474-4.474l.823.823a2.5 2.5 0 0 0 2.829 2.829z"/>
                                                                                                    <path d="M3.35 5.47c-.18.16-.353.322-.518.487A13.134 13.134 0 0 0 1.172 8l.195.288c.335.48.83 1.12 1.465 1.755C4.121 11.332 5.881 12.5 8 12.5c.716 0 1.39-.133 2.02-.36l.77.772A7.029 7.029 0 0 1 8 13.5C3 13.5 0 8 0 8s.939-1.721 2.641-3.238l.708.709zm10.296 8.884l-12-12 .708-.708 12 12-.708.708z"/>
                                                                                                </svg>    
                                                                                            </button>
                                                                                        </#if>
                                                                                    </form>
                                                                                </div>
                                                                                <div class="col-6 pl-1 pr-1">
                                                                                    <form id="modSurvey${sondaggio.getKey()}" method="POST" action="dashboard">
                                                                                        <button name="modSurvey" value="${sondaggio.getKey()}" class="btn brn-lg btn-light" type="submit" title="modifica">
                                                                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">
                                                                                                <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5L13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175l-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
                                                                                            </svg>
                                                                                        </button> 
                                                                                    </form>
                                                                                </div>
                                                                            </div>
                                                                            <div class="row mr-3 mt-2">
                                                                                <div class="col-6 pl-1 pr-1">
                                                                                    <form id="changePartecipants${sondaggio.getKey()}" method="POST" action="changePartecipants">
                                                                                        <#if sondaggio.isPrivato()>
                                                                                            <button name="changePartecipantsForm" value="${sondaggio.getKey()}" class="btn brn-lg btn-info" type="submit" title="modifica partecipanti">
                                                                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-people" viewBox="0 0 16 16">
                                                                                                    <path d="M15 14s1 0 1-1-1-4-5-4-5 3-5 4 1 1 1 1h8zm-7.978-1A.261.261 0 0 1 7 12.996c.001-.264.167-1.03.76-1.72C8.312 10.629 9.282 10 11 10c1.717 0 2.687.63 3.24 1.276.593.69.758 1.457.76 1.72l-.008.002a.274.274 0 0 1-.014.002H7.022zM11 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4zm3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0zM6.936 9.28a5.88 5.88 0 0 0-1.23-.247A7.35 7.35 0 0 0 5 9c-4 0-5 3-5 4 0 .667.333 1 1 1h4.216A2.238 2.238 0 0 1 5 13c0-1.01.377-2.042 1.09-2.904.243-.294.526-.569.846-.816zM4.92 10A5.493 5.493 0 0 0 4 13H1c0-.26.164-1.03.76-1.724.545-.636 1.492-1.256 3.16-1.275zM1.5 5.5a3 3 0 1 1 6 0 3 3 0 0 1-6 0zm3-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4z"/>
                                                                                                </svg>
                                                                                            </button>
                                                                                        <#else>
                                                                                            <button name="changePartecipantsForm" value="${sondaggio.getKey()}" class="btn brn-lg btn-info" type="submit" title="modifica partecipanti" disabled>
                                                                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-people" viewBox="0 0 16 16">
                                                                                                    <path d="M15 14s1 0 1-1-1-4-5-4-5 3-5 4 1 1 1 1h8zm-7.978-1A.261.261 0 0 1 7 12.996c.001-.264.167-1.03.76-1.72C8.312 10.629 9.282 10 11 10c1.717 0 2.687.63 3.24 1.276.593.69.758 1.457.76 1.72l-.008.002a.274.274 0 0 1-.014.002H7.022zM11 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4zm3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0zM6.936 9.28a5.88 5.88 0 0 0-1.23-.247A7.35 7.35 0 0 0 5 9c-4 0-5 3-5 4 0 .667.333 1 1 1h4.216A2.238 2.238 0 0 1 5 13c0-1.01.377-2.042 1.09-2.904.243-.294.526-.569.846-.816zM4.92 10A5.493 5.493 0 0 0 4 13H1c0-.26.164-1.03.76-1.724.545-.636 1.492-1.256 3.16-1.275zM1.5 5.5a3 3 0 1 1 6 0 3 3 0 0 1-6 0zm3-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4z"/>
                                                                                                </svg>
                                                                                            </button>
                                                                                        </#if>
                                                                                    </form>
                                                                                </div>
                                                                                <div class="col-6 pl-1 pr-1">
                                                                                    <form id="removeSurvey${sondaggio.getKey()}" method="POST" action="dashboard">
                                                                                        <button name="removeSurvey" value="${sondaggio.getKey()}" class="btn brn-lg btn-danger" type="submit" title="elimina">
                                                                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x" viewBox="0 0 16 16">
                                                                                                <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
                                                                                            </svg>
                                                                                        </button>
                                                                                    </form>
                                                                                </div>
                                                                            </div>
                                                                            <div class="row mr-3 mt-2">
                                                                                <div class="col-6 pl-1 pr-1">
                                                                                    <form id="downloadAnswer${sondaggio.getKey()}" method="POST" action="dashboard">
                                                                                        <button name="downloadAnswer" value="${sondaggio.getKey()}" class="btn brn-lg btn-success" type="submit" title="scarica risposte">
                                                                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark-arrow-down" viewBox="0 0 16 16">
                                                                                                <path d="M8.5 6.5a.5.5 0 0 0-1 0v3.793L6.354 9.146a.5.5 0 1 0-.708.708l2 2a.5.5 0 0 0 .708 0l2-2a.5.5 0 0 0-.708-.708L8.5 10.293V6.5z"/>
                                                                                                <path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2zM9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5v2z"/>
                                                                                            </svg>
                                                                                        </button>
                                                                                    </form>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </td>
                                                                    <td></td>
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
                                        <p class="h4 text-muted">Ops! pare che tu non abbia sondaggi privati!</h2>
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
                                    <form method="post" action="dashboard" class="needs-validation" novalidate>
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
                                                <a href="dashboard" class="btn btn-warning">Torna ai tuoi sondaggi</a>
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
                    <!-- END Sondaggi PRIVATI -->





                    <!-- Sondaggi COMPILATI -->
                    <div class="container-fluid mt-5">
                        <div class="container bg-white border rounded-lg">
                            <div class="row pt-3 pb-2 pl-2 pr-2 bg-warning margin-bottom">
                                <div class="col-lg-9 col-md-8 col-sm-7 col-7">
                                    <h2>Sondaggi compilati</h2>
                                </div>
                            </div>
                            <#if noSondaggiComp?? && noSondaggiComp!="">
                                <div class="row mt-3">
                                    <div class="col-lg-12 mb-2">
                                        <p class="h4 text-muted">Ops! pare che tu non abbia compilato alcun sondaggio!</h2>
                                    </div>
                                </div>
                            <#else>
                                <div class="row mt-3 javaScriptVisibility">
                                    <div class="col-lg-12 mb-2">
                                        <div class="table-responsive" style="overflow-x: hidden;">
                                            <table id='table-sondaggi-compilati' class="table table-striped table-curved" style="width:99.5%">
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
                                                    <#list sondaggiComp as sondaggio>  
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
                                                                            <form id="answerView${sondaggio.getKey()}" method="POST" action="dashboard">
                                                                                <button name="btnAnswerView" value="${sondaggio.getKey()}" class="btn brn-lg btn-warning" type="submit" title="Visualizza risposta">
                                                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
                                                                                        <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
                                                                                        <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
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
                                    <form method="post" action="dashboard" class="needs-validation" novalidate>
                                        <div class="row mt-3">
                                            <div class="col-lg-12">
                                                <div id="search-field">
                                                    <input type="text" name="header-search-sondaggi-compilati" class="form-control" id="header-search" placeholder="Cerca..." />
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
                                    <#if listaSondaggiCompilatiVuota?? && listaSondaggiCompilatiVuota!="">
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <p class="h4 text-muted">Ops! Pare che la tua ricerca non ti abbia portato a nessun sondaggio!</h2>
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
                                                    <table id='table-sondaggi-compilati-noscript' class="table table-striped table-curved">
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
                                                            <#list sondaggiComp as sondaggio>  
                                                                <tr>
                                                                    <th scope="row">${c}</th>
                                                                    <td >${sondaggio.getTitolo()}</td>
                                                                    <td>${sondaggio.getCreazione()}</td>
                                                                    <#if sondaggio.getScadenza()??>
                                                                        <td>${sondaggio.getScadenza()}</td>
                                                                    <#else>
                                                                        <td>Indeterminata</td>
                                                                    </#if>
                                                                    <td>
                                                                        <form id="answerView${sondaggio.getKey()}" method="POST" action="dashboard">
                                                                            <button name="btnAnswerView" value="${sondaggio.getKey()}" class="btn brn-lg btn-warning" type="submit" title="Visualizza risposta">
                                                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
                                                                                    <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
                                                                                    <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
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
                                        <#if ricercaSondaggiCompilati?? && ricercaSondaggiCompilati!="">
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
                    <!-- END Sondaggi COMPILATI -->
                
                
                
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
                
            $('#table-sondaggi-compilati').DataTable({
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
                    $("#table-sondaggi-compilati_filter input").prop('id', 'header-search');
                    $('.dataTables_filter input').prop('type', 'text');
                    $(".dataTables_filter input").prop('class', 'form-control');
                    $(".dataTables_filter input").prop("placeholder", "Cerca...");
                    $(".dataTables_filter label").prop('class', 'label-width-searchBox');
                    $("#table-sondaggi-compilati_filter").prop('id', 'search-field2');               
                    $("#search-field2").append('<svg id="search-icon" class="search-icon" viewBox="0 0 24 24">' + '<path d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z" />' + '<path d="M0 0h24v24H0z" fill="none" />' + '</svg>');
                    $(".dataTables_filter").prop('class', '');
                }
            });
        } );
    </script>

</html>
