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
    <body>
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
                    <!-- Personal info end -->
                
                    <!-- Polls start -->
                    <div class="container-fluid mt-5">
                    <div class="container bg-white border rounded-lg">
                        <div class="row pt-3 pb-2 pl-2 pr-2 bg-warning margin-bottom">
                            <div class="col-lg-9 col-md-8 col-sm-7 col-6">
                                <h2>I Tuoi sondaggi</h2>
                            </div>
                        </div>
                        <#if listaTuoiSondaggiVuota?? && listaTuoiSondaggiVuota!="">
                            <div class="row mt-3">
                                <div class="col-lg-12">
                                    <h2>Nessun sondaggio da OBLITERARE!</h2>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-9 col-md-8 col-sm-7 col-6"></div>
                            </div>
                        <#else>
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
                                                <#assign c = 1>
                                                <tbody>
                                                    <#list sondaggi as sondaggio>
                                                        <form>
                                                            <tr>
                                                                <th scope="row">${c}</th>
                                                                <td>${sondaggio.getTitolo()}</td>
                                                                <td>${sondaggio.getCreazione()}</td>
                                                                <#if sondaggio.getScadenza()??>
                                                                    <td>${sondaggio.getScadenza()}</td>
                                                                <#else>
                                                                    <td>Indeterminata</td>
                                                                </#if>
                                                                <td><button name="btnDeleteSondaggio" value="${sondaggio.getKey()}" class="btn brn-lg btn-warning" type="submit">elimina</button></td>
                                                            </tr>
                                                        <form>
                                                        <#assign c = c + 1> <!--non è l'ID-->
                                                    </#list>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#if>
                    </div>
                    <!-- Polls end -->
                    <!-- Utenti end -->
                    <div class="container-fluid mt-5">
                        <div class="container bg-white border rounded-lg">
                            <div class="row pt-3 pb-2 pl-2 pr-2 bg-warning margin-bottom">
                                <div class="col-lg-9 col-md-8 col-sm-7 col-6">
                                    <h2>Lista Utenti</h2>
                                </div>
                            </div>
                            <#if listaUtentiVuota?? && listaUtentiVuota!="">
                            <div class="row mt-3">
                                <div class="col-lg-12">
                                    <h2>Nessun utente da STERMINARE!</h2>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-9 col-md-8 col-sm-7 col-6"></div>
                            </div>
                            <#else>
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
                                                            <form>
                                                                <tr>
                                                                    <form method="post" action="firstSection" class="update">
                                                                    <th scope="row">${c}</th>
                                                                    <td>${utente.getNome()}</td>
                                                                    <td>${utente.getCognome()}</td>
                                                                    <td>${utente.getEmail()}</td>
                                                                    <#if utente.getIdGruppo()== 1>
                                                                        <td>utente base</td>
                                                                    <#else>
                                                                        <td>responsabile</td>
                                                                    </#if>
                                                                    <td><button name="btnDeleteUser" value="${utente.getKey()}" class="btn brn-lg btn-warning" type="submit">elimina</button></td>
                                                                </tr>
                                                            </form>
                                                            <#assign c = c + 1> <!--non è l'ID-->
                                                        </#if>
                                                    </#list>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#if>
                        </div>


                    <!-- Utenti end -->
                
                </div>    
            </main>
            <@macros.footer />
            <@macros.script />
        </div>
    </body>
</html>
