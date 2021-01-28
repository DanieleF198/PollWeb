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
                        <form id="operations" name="operations" action="confirmSection" method="POST">
                        <form id="confirmForm" method="post" action="confirmSection" class="needs-validation" enctype="multipart/form-data" novalidate>
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
                                                <#list domande as domanda>  
                                                    <tr>
                                                        <#assign posizione = domanda.getPosizione() + 1>
                                                        <th scope="row">${posizione}</th>
                                                        <#if domanda.getTitolo()?? && domanda.getTitolo()!="">
                                                            <#if domanda.getTitolo()?length \gt 42>
                                                                <#assign titolo = domanda.getTitolo()?substring(0,42)>
                                                                <td>${titolo}...</td>
                                                            <#else>
                                                                <#assign titolo = domanda.getTitolo()>
                                                                <td>${titolo}</td>
                                                            </#if>
                                                        <#else>
                                                            <td> N/A </td>
                                                        </#if>
                                                        <#if domanda.getDescrizione()?? && domanda.getDescrizione()!="">
                                                            <#if domanda.getDescrizione()?length \gt 35>
                                                                <#assign descrizione = domanda.getDescrizione()?substring(0,35)>
                                                                <td>${descrizione}...</td>
                                                            <#else>
                                                                <#assign descrizione = domanda.getDescrizione()>
                                                                <td>${descrizione}</td>
                                                            </#if>
                                                        <#else>
                                                            <td> N/A </td>
                                                        </#if>
                                                        <td>${domanda.getTipo()}</td>
                                                        <td>
                                                            <#if domanda?is_first>
                                                                <button form="operations" type="submit" name="goUpQuestion" value="goUpQuestionButton${domanda.getPosizione()}" class="btn btn-light mr-1 mb-1" title="Sposta in alto" disabled>
                                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-up-short" viewBox="0 0 16 16">
                                                                        <path fill-rule="evenodd" d="M8 12a.5.5 0 0 0 .5-.5V5.707l2.146 2.147a.5.5 0 0 0 .708-.708l-3-3a.5.5 0 0 0-.708 0l-3 3a.5.5 0 1 0 .708.708L7.5 5.707V11.5a.5.5 0 0 0 .5.5z"/>
                                                                    </svg>
                                                                </button>
                                                            <#else>
                                                                <button form="operations" type="submit" name="goUpQuestion" value="goUpQuestionButton${domanda.getPosizione()}" class="btn btn-light mr-1 mb-1" title="Sposta in alto">
                                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-up-short" viewBox="0 0 16 16">
                                                                        <path fill-rule="evenodd" d="M8 12a.5.5 0 0 0 .5-.5V5.707l2.146 2.147a.5.5 0 0 0 .708-.708l-3-3a.5.5 0 0 0-.708 0l-3 3a.5.5 0 1 0 .708.708L7.5 5.707V11.5a.5.5 0 0 0 .5.5z"/>
                                                                    </svg>
                                                                </button>
                                                            </#if>
                                                            <#if domanda?is_last>
                                                                <button form="operations" type="submit" name="goDownQuestion" value="goDownQuestionButton${domanda.getPosizione()}" class="btn btn-light mr-1 mb-1" title="sposta in basso" disabled>
                                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-down-short" viewBox="0 0 16 16">
                                                                      <path fill-rule="evenodd" d="M8 4a.5.5 0 0 1 .5.5v5.793l2.146-2.147a.5.5 0 0 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 1 1 .708-.708L7.5 10.293V4.5A.5.5 0 0 1 8 4z"/>
                                                                    </svg>
                                                                </button>
                                                            <#else>
                                                                <button form="operations" type="submit" name="goDownQuestion" value="goDownQuestionButton${domanda.getPosizione()}" class="btn btn-light mr-1 mb-1" title="sposta in basso">
                                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-down-short" viewBox="0 0 16 16">
                                                                      <path fill-rule="evenodd" d="M8 4a.5.5 0 0 1 .5.5v5.793l2.146-2.147a.5.5 0 0 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 1 1 .708-.708L7.5 10.293V4.5A.5.5 0 0 1 8 4z"/>
                                                                    </svg>
                                                                </button>
                                                            </#if>
                                                            <button form="operations" type="submit" name="removeQuestion" value="removeQuestionButton${domanda.getPosizione()}" class="btn btn-danger" title="elimina">
                                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x" viewBox="0 0 16 16">
                                                                    <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
                                                                </svg>
                                                            </button>
                                                        </td>
                                                    </tr>
                                                </#list>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <#if errors??>
                                <p class="text-danger mb-1"><b>Pare che ci siano dei problemi:</b> non ti preoccupare, puoi sempre salvare il sondaggio, ma finch&#233; non li avrai risolti, il sondaggio rimarr&#224; disattivato</p>
                                <#list errors as error>
                                    <p class="text-danger mb-1"><b>-</b>${error}</p>
                                </#list>
                            </#if>
                            <div class="row mt-3 javaScriptVisibility">
                                <div class="col-lg-12">
                                    <div class="row mb-2">
                                        <div class="col-lg-11 col-md-10 col-sx-9 col-9">
                                            <p><b>Inserimento partecipanti</b> - manuale</p>
                                        </div>
                                        <div class="col-lg-1 col-md-1 col-sx-1 col-1">
                                            <button type="button" id="addMore" class="btn btn-warning" title="aggiungi utente">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus" viewBox="0 0 16 16">
                                                    <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
                                                </svg>
                                            </button>
                                        </div>
                                    </div>
                                    <div id="fieldList" class="row mb-3">
                                        <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                            <input type="text" name="usersName[]" id="firstUserName" class="form-control" placeholder="Nome Utente" onkeyup="disabled()">
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                            <input type="text" name="usersMail[]" id="firstUserMail" class="form-control" placeholder="E-mail utente" onkeyup="disabled()">
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                            <input type="text" name="usersPass[]" id="firstUserPass" class="form-control" placeholder="Password utente" onkeyup="disabled()">
                                        </div>
                                        <div class="col-12 border-bottom mb-2"></div>
                                    </div>
                                    <p><b>Inserimento partecipanti</b> - tramite csv</p>
                                    <div class="row">
                                        <div class="col-11">
                                            <input type="file" id="partecipants" class="form-control-file" id="exampleFormControlFile1">
                                        </div>
                                        <div class="col-1">
                                            <label class="checkbox">
                                                <input type="checkbox" name="withCSV" id="withCSV" value="withCSV" onclick="disable()">
                                                <span class="warning"></span>
                                            </label>
                                        </div>
                                    </div>
                                    <p class="mt-3"><b>Attenzione</b> se si decide di invitare i partecipanti tramite file csv, i partecipanti inseriti nell'inserimento manuale saranno ignorati</p>
                                </div>
                            </div>
                            <noscript>
                                <div class="row mb-3">
                                    <div class="col-12">
                                        <p><b>Inserimento partecipanti</b> - tramite csv</p>
                                        <input type="file" id="partecipants" class="form-control-file" id="exampleFormControlFile1">
                                    </div>
                                </div>
                            </noscript>
                        </form>
                        <form id="returnQuestions" action="questionsMaker" method="GET"></form>
                            <div class="row mt-3">
                                <div class="col-lg-4 col-md-6 mb-2 custom-right">
                                    <button type="submit" name="createOnly" value="create"  form="confirmForm" class="btn btn-lg btn-warning btn-block">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-left" viewBox="0 0 16 16">
                                            <path d="M4 0h5.5v1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V4.5h1V14a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2z"/>
                                            <path d="M9.5 3V0L14 4.5h-3A1.5 1.5 0 0 1 9.5 3z"/>
                                            <path fill-rule="evenodd" d="M8 6.5a.5.5 0 0 1 .5.5v1.5H10a.5.5 0 0 1 0 1H8.5V11a.5.5 0 0 1-1 0V9.5H6a.5.5 0 0 1 0-1h1.5V7a.5.5 0 0 1 .5-.5z"/>
                                        </svg>
                                        Crea sondaggio
                                    </button>
                                </div>
                                <div class="col-lg-4 mb-2 custom-center">
                                        <button type="submit" name="returnQuestions" value="returnQuestionsButton"  form="returnQuestions" class="btn btn-lg btn-warning custom-block">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-left" viewBox="0 0 16 16">
                                            <path d="M3 0h10a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2v-1h1v1a1 1 0 0 0 1 1h10a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H3a1 1 0 0 0-1 1v1H1V2a2 2 0 0 1 2-2z"/>
                                            <path d="M1 5v-.5a.5.5 0 0 1 1 0V5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1zm0 3v-.5a.5.5 0 0 1 1 0V8h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1zm0 3v-.5a.5.5 0 0 1 1 0v.5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1z"/>
                                            <path fill-rule="evenodd" d="M8 11a.5.5 0 0 0 .5-.5V6.707l1.146 1.147a.5.5 0 0 0 .708-.708l-2-2a.5.5 0 0 0-.708 0l-2 2a.5.5 0 1 0 .708.708L7.5 6.707V10.5a.5.5 0 0 0 .5.5z"/>                                    
                                        </svg>
                                        Ritorna a creazione domande
                                    </button>
                                </div>
                                <div class="col-lg-4 col-md-6 mb-2 custom-left">
                                    <#if errors??>
                                        <button type="submit" name="createAndPublic" value="public" form="confirmForm" class="btn btn-lg btn-warning btn-block" disabled>
                                    <#else>
                                        <button type="submit" name="createAndPublic" value="public" form="confirmForm" class="btn btn-lg btn-warning btn-block">
                                    </#if>
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-left" viewBox="0 0 16 16">
                                            <path fill-rule="evenodd" d="M15 14s1 0 1-1-1-4-5-4-5 3-5 4 1 1 1 1h8zm-7.978-1h7.956a.274.274 0 0 0 .014-.002l.008-.002c-.002-.264-.167-1.03-.76-1.72C13.688 10.629 12.718 10 11 10c-1.717 0-2.687.63-3.24 1.276-.593.69-.759 1.457-.76 1.72a1.05 1.05 0 0 0 .022.004zM11 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4zm3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0zM6.936 9.28a5.88 5.88 0 0 0-1.23-.247A7.35 7.35 0 0 0 5 9c-4 0-5 3-5 4 0 .667.333 1 1 1h4.216A2.238 2.238 0 0 1 5 13c0-1.01.377-2.042 1.09-2.904.243-.294.526-.569.846-.816zM4.92 10c-1.668.02-2.615.64-3.16 1.276C1.163 11.97 1 12.739 1 13h3c0-1.045.323-2.086.92-3zM1.5 5.5a3 3 0 1 1 6 0 3 3 0 0 1-6 0zm3-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4z"/>
                                        </svg>
                                        Crea sondaggio e pubblicalo
                                    </button>
                                </div>
                            </div>
                    </div>
                    <!-- End general information -->
                </div>
            </main>
    <@macros.footer />
    <@macros.script />
            <script>
                $(function() {
                    $("#addMore").click(function(e) {
                        e.preventDefault();
                        if(document.getElementById('firstUserName').disabled === false){
                            $("#fieldList").append("<div class='col-lg-4 col-md-4 col-sx-12 col-12 mb-2'><input type='text' name='usersName[]' class='form-control' placeholder='Nome Utente'></div>");
                            $("#fieldList").append("<div class='col-lg-4 col-md-4 col-sx-12 col-12 mb-2'><input type='text' name='usersMail[]' class='form-control' placeholder='E-mail utente'></div>"); 
                            $("#fieldList").append("<div class='col-lg-4 col-md-4 col-sx-12 col-12 mb-2'><input type='text' name='usersPass[]' class='form-control' placeholder='Password utente'></div>"); 
                            $("#fieldList").append("<div class='col-12 border-bottom mb-2'></div>"); 
                        } else {
                            $("#fieldList").append("<div class='col-lg-4 col-md-4 col-sx-12 col-12 mb-2'><input type='text' name='usersName[]' class='form-control' placeholder='Nome Utente' disabled></div>");
                            $("#fieldList").append("<div class='col-lg-4 col-md-4 col-sx-12 col-12 mb-2'><input type='text' name='usersMail[]' class='form-control' placeholder='E-mail utente' disabled></div>"); 
                            $("#fieldList").append("<div class='col-lg-4 col-md-4 col-sx-12 col-12 mb-2'><input type='text' name='usersPass[]' class='form-control' placeholder='Password utente' disabled></div>"); 
                            $("#fieldList").append("<div class='col-12 border-bottom mb-2'></div>"); 
                        }
                    });
                });    
                function disable(){
                    $(document).ready(function(){
                        if(document.getElementById('firstUserName').disabled === false){
                            $("input[type=text]").prop("disabled", true);
                                
                        } else {
                            $("input[type=text]").prop("disabled", false);
                        }
                    });
                }
            </script>
        </div>
    </body>
</html>
