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
                            <#if errors?? || partecipantsError??>
                                <#if private?? && private=="yes">
                                    <p class="text-danger mb-1"><b>Pare che ci siano dei problemi:</b> finch&#233; non li avrai risolti non potrai rendere pubblico il sondaggio (ma potrai salvarlo) e non potrai invitare alcun partecipante.</p>
                                <#else>
                                    <p class="text-danger mb-1"><b>Pare che ci siano dei problemi:</b> finch&#233; non li avrai risolti non potrai rendere pubblico il sondaggio (ma potrai salvarlo).</p>                                
                                </#if>
                                <#if errors??>
                                    <#list errors as error>
                                        <p class="text-danger mb-1"><b>-</b>${error}</p>
                                    </#list>
                                </#if>
                                <#if partecipantsError??>
                                    <p class="text-danger mb-1"><b>-</b>Per ogni partecipante invitato manualmente devi inserire tutti i campi, inoltre le email e le password (che devono rispettare i requisiti posti durante la registrazione) devono essere tutte differenti tra di loro.</p>
                                    <p class="text-danger mb-1"><b>NOTA: </b>I partecipanti che non verranno corretti non verranno invitati, inclusi le coppie di utenti con medesima email e/o password</p>
                                </#if>
                                <#if errors?? && partecipantsError??>
                                    <p class="text-danger mb-1"><b>ATTENZIONE: </b>Pare che tu abbia degli errori sia relativi alle domande che ai partecipanti. In questo caso dovrai prima risolvere i problemi relativi alle domande, e soltanto dopo reinserire correttamente i partecipanti.</p>
                                </#if>
                            </#if>
                            <#if private?? && private=="yes">
                                <#if errors??>
                                    <div class="row mt-3 javaScriptVisibility">
                                        <div class="col-lg-12">
                                            <div class="row mb-2">
                                                <div class="col-lg-11 col-md-10 col-sx-9 col-9">
                                                    <p><b>Inserimento partecipanti</b> - manuale</p>
                                                </div>
                                                <div class="col-lg-1 col-md-1 col-sx-1 col-1">
                                                    <button type="button" id="addMore" class="btn btn-warning" title="aggiungi utente" disabled>
                                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus" viewBox="0 0 16 16">
                                                            <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
                                                    </button>
                                                </div>
                                            </div>
                                            <#if partecipants??>
                                                <div id="fieldList" class="row mb-3">
                                                    <#list partecipants as partecipant>
                                                        <#if partecipant_has_next>
                                                            <#if partecipant.getNome()?? && partecipant.getNome()!="">
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersName[]" class="form-control" placeholder="Nome Utente" value="${partecipant.getNome()}" disabled>
                                                                </div>
                                                            <#else>
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersName[]" class="form-control" placeholder="Nome Utente" disabled>
                                                                </div>
                                                            </#if>
                                                            <#if partecipant.getEmail()?? && partecipant.getEmail()!="">
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersMail[]" class="form-control" placeholder="E-mail utente" value="${partecipant.getEmail()}" disabled>
                                                                </div>
                                                            <#else>
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersMail[]" class="form-control" placeholder="E-mail utente" disabled>
                                                                </div>
                                                            </#if>
                                                            <#if partecipant.getPassword()?? && partecipant.getPassword()!="">
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersPass[]" class="form-control" placeholder="Password utente" value="${partecipant.getPassword()}" disabled>
                                                                </div>
                                                            <#else>
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersPass[]" class="form-control" placeholder="Password utente" disabled>
                                                                </div>
                                                            </#if>
                                                            <div class="col-12 border-bottom mb-2"></div>
                                                        <#else>
                                                            <#if partecipant.getNome()?? && partecipant.getNome()!="">
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersName[]" id="firstUserName" class="form-control" placeholder="Nome Utente" value="${partecipant.getNome()}" onkeyup="disabled()" disabled>
                                                                </div>
                                                            <#else>
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersName[]" id="firstUserName" class="form-control" placeholder="Nome Utente" onkeyup="disabled()" disabled>
                                                                </div>
                                                            </#if>
                                                            <#if partecipant.getEmail()?? && partecipant.getEmail()!="">
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersMail[]" id="firstUserMail" class="form-control" placeholder="E-mail utente" value="${partecipant.getEmail()}" onkeyup="disabled()" disabled>
                                                                </div>
                                                            <#else>
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersMail[]" id="firstUserMail" class="form-control" placeholder="E-mail utente" onkeyup="disabled()" disabled>
                                                                </div>
                                                            </#if>
                                                            <#if partecipant.getPassword()?? && partecipant.getPassword()!="">
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersPass[]" id="firstUserPass" class="form-control" placeholder="Password utente" value="${partecipant.getPassword()}" onkeyup="disabled()" disabled>
                                                                </div>
                                                            <#else>
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersPass[]" id="firstUserPass" class="form-control" placeholder="Password utente" onkeyup="disabled()" disabled>
                                                                </div>
                                                            </#if>
                                                            <div class="col-12 border-bottom mb-2"></div>
                                                        </#if>
                                                    </#list>
                                                </div>
                                            <#else>
                                                <div id="fieldList" class="row mb-3">
                                                    <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                        <input type="text" name="usersName[]" id="firstUserName" class="form-control" placeholder="Nome Utente" onkeyup="disabled()" disabled>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                        <input type="text" name="usersMail[]" id="firstUserMail" class="form-control" placeholder="E-mail utente" onkeyup="disabled()" disabled>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                        <input type="text" name="usersPass[]" id="firstUserPass" class="form-control" placeholder="Password utente" onkeyup="disabled()" disabled>
                                                    </div>
                                                    <div class="col-12 border-bottom mb-2"></div>
                                                </div>
                                            </#if>
                                            <p><b>Inserimento partecipanti</b> - tramite csv</p>
                                            <div class="row">
                                                <div class="col-11">
                                                    <input type="file" id="partecipants" class="form-control-file" id="exampleFormControlFile1" disabled>
                                                </div>
                                                <div class="col-1">
                                                    <label class="checkbox">
                                                        <input type="checkbox" name="withCSV" id="withCSV" value="withCSV" onclick="disable()" disabled>
                                                        <span class="warning"></span>
                                                    </label>
                                                </div>
                                            </div>
                                            <p class="mt-3">Attenzione:</b> se si decide di invitare i partecipanti tramite file csv, i partecipanti inseriti nell'inserimento manuale saranno ignorati</p>
                                        </div>
                                    </div>
                                    <noscript>
                                        <div class="row mb-3">
                                            <div class="col-12">
                                                <p><b>Inserimento partecipanti</b> - tramite csv</p>
                                                <input type="file" id="partecipants" class="form-control-file" id="exampleFormControlFile1" disabled>
                                            </div>
                                        </div>
                                    </noscript>
                                    <p class="text-muted"><b>Nota:</b> i partecipanti verranno invitati effettivamente soltanto quando il sondaggio diventa pubblico per la prima volta</p>
                                <#else>
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
                                                    </button>
                                                </div>
                                            </div>
                                            <#if partecipants??>
                                                <div id="fieldList" class="row mb-3">
                                                    <#list partecipants as partecipant>
                                                        <#if partecipant_has_next>
                                                            <#if partecipant.getNome()?? && partecipant.getNome()!="">
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersName[]" class="form-control" placeholder="Nome Utente" value="${partecipant.getNome()}">
                                                                </div>
                                                            <#else>
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersName[]" class="form-control" placeholder="Nome Utente">
                                                                </div>
                                                            </#if>
                                                            <#if partecipant.getEmail()?? && partecipant.getEmail()!="">
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersMail[]" class="form-control" placeholder="E-mail utente" value="${partecipant.getEmail()}">
                                                                </div>
                                                            <#else>
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersMail[]" class="form-control" placeholder="E-mail utente">
                                                                </div>
                                                            </#if>
                                                            <#if partecipant.getPassword()?? && partecipant.getPassword()!="">
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersPass[]" class="form-control" placeholder="Password utente" value="${partecipant.getPassword()}">
                                                                </div>
                                                            <#else>
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersPass[]" class="form-control" placeholder="Password utente">
                                                                </div>
                                                            </#if>
                                                            <div class="col-12 border-bottom mb-2"></div>
                                                        <#else>
                                                            <#if partecipant.getNome()?? && partecipant.getNome()!="">
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersName[]" id="firstUserName" class="form-control" placeholder="Nome Utente" value="${partecipant.getNome()}" onkeyup="disabled()">
                                                                </div>
                                                            <#else>
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersName[]" id="firstUserName" class="form-control" placeholder="Nome Utente" onkeyup="disabled()">
                                                                </div>
                                                            </#if>
                                                            <#if partecipant.getEmail()?? && partecipant.getEmail()!="">
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersMail[]" id="firstUserMail" class="form-control" placeholder="E-mail utente" value="${partecipant.getEmail()}" onkeyup="disabled()">
                                                                </div>
                                                            <#else>
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersMail[]" id="firstUserMail" class="form-control" placeholder="E-mail utente" onkeyup="disabled()">
                                                                </div>
                                                            </#if>
                                                            <#if partecipant.getPassword()?? && partecipant.getPassword()!="">
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersPass[]" id="firstUserPass" class="form-control" placeholder="Password utente" value="${partecipant.getPassword()}" onkeyup="disabled()">
                                                                </div>
                                                            <#else>
                                                                <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                                    <input type="text" name="usersPass[]" id="firstUserPass" class="form-control" placeholder="Password utente" onkeyup="disabled()">
                                                                </div>
                                                            </#if>
                                                            <div class="col-12 border-bottom mb-2"></div>
                                                        </#if>
                                                    </#list>
                                                </div>
                                            <#else>
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
                                            </#if>
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
                                            <p class="mt-3">Attenzione:</b> se si decide di invitare i partecipanti tramite file csv, i partecipanti inseriti nell'inserimento manuale saranno ignorati</p>
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
                                    <p class="text-muted"><b>Nota:</b> i partecipanti verranno invitati effettivamente soltanto quando il sondaggio diventa pubblico per la prima volta. Cliccato su "Torna alle domande" o "torna alle informazioni generali" i partecipanti immessi fin'ora non verranno salvati.</p>
                                </#if>
                            </#if>
                        </form>
                        <form id="returnQuestions" action="questionsMaker" method="GET"></form>
                        <form id="returnFirstSection" action="firstSection" method="GET"></form>
                        <div class="row mt-3">
                            <div class="col-lg-6 mb-2 pr-1 pl-1 custom-left">
                                    <button type="submit" name="createOnly" value="create"  form="confirmForm" class="btn btn-lg btn-warning btn-block">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-left" viewBox="0 0 16 16">
                                        <path d="M4 0h5.5v1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V4.5h1V14a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2z"/>
                                        <path d="M9.5 3V0L14 4.5h-3A1.5 1.5 0 0 1 9.5 3z"/>
                                        <path fill-rule="evenodd" d="M8 6.5a.5.5 0 0 1 .5.5v1.5H10a.5.5 0 0 1 0 1H8.5V11a.5.5 0 0 1-1 0V9.5H6a.5.5 0 0 1 0-1h1.5V7a.5.5 0 0 1 .5-.5z"/>
                                    </svg>
                                    Salva sondaggio
                                </button>
                            </div>
                            <div class="col-lg-6 mb-2 pr-1 pl-1 custom-right">
                                <#if errors??>
                                    <button type="submit" name="createAndPublic" value="public" form="confirmForm" class="btn btn-lg btn-warning btn-block" disabled>
                                <#else>
                                    <button type="submit" name="createAndPublic" value="public" form="confirmForm" class="btn btn-lg btn-warning btn-block">
                                </#if>
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-left" viewBox="0 0 16 16">
                                        <path fill-rule="evenodd" d="M15 14s1 0 1-1-1-4-5-4-5 3-5 4 1 1 1 1h8zm-7.978-1h7.956a.274.274 0 0 0 .014-.002l.008-.002c-.002-.264-.167-1.03-.76-1.72C13.688 10.629 12.718 10 11 10c-1.717 0-2.687.63-3.24 1.276-.593.69-.759 1.457-.76 1.72a1.05 1.05 0 0 0 .022.004zM11 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4zm3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0zM6.936 9.28a5.88 5.88 0 0 0-1.23-.247A7.35 7.35 0 0 0 5 9c-4 0-5 3-5 4 0 .667.333 1 1 1h4.216A2.238 2.238 0 0 1 5 13c0-1.01.377-2.042 1.09-2.904.243-.294.526-.569.846-.816zM4.92 10c-1.668.02-2.615.64-3.16 1.276C1.163 11.97 1 12.739 1 13h3c0-1.045.323-2.086.92-3zM1.5 5.5a3 3 0 1 1 6 0 3 3 0 0 1-6 0zm3-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4z"/>
                                    </svg>
                                    Salva e pubblica Sondaggio
                                </button>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-6 mb-2 pr-1 pl-1 custom-left">
                                <button type="submit" name="returnInfo" value="returnInfoButton"  form="returnFirstSection" class="btn btn-lg btn-warning btn-block">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-text" viewBox="0 0 16 16">
                                        <path d="M5 4a.5.5 0 0 0 0 1h6a.5.5 0 0 0 0-1H5zm-.5 2.5A.5.5 0 0 1 5 6h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zM5 8a.5.5 0 0 0 0 1h6a.5.5 0 0 0 0-1H5zm0 2a.5.5 0 0 0 0 1h3a.5.5 0 0 0 0-1H5z"/>
                                        <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2zm10-1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1z"/>
                                    </svg>
                                    Ritorna alle informazioni generali
                                </button>
                            </div>
                            <div class="col-lg-6 mb-2 pr-1 pl-1 custom-right">
                                    <button type="submit" name="returnQuestions" value="returnQuestionsButton"  form="returnQuestions" class="btn btn-lg btn-warning btn-block">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-patch-question" viewBox="0 0 16 16">
                                        <path d="M8.05 9.6c.336 0 .504-.24.554-.627.04-.534.198-.815.847-1.26.673-.475 1.049-1.09 1.049-1.986 0-1.325-.92-2.227-2.262-2.227-1.02 0-1.792.492-2.1 1.29A1.71 1.71 0 0 0 6 5.48c0 .393.203.64.545.64.272 0 .455-.147.564-.51.158-.592.525-.915 1.074-.915.61 0 1.03.446 1.03 1.084 0 .563-.208.885-.822 1.325-.619.433-.926.914-.926 1.64v.111c0 .428.208.745.585.745z"/>
                                        <path d="M10.273 2.513l-.921-.944.715-.698.622.637.89-.011a2.89 2.89 0 0 1 2.924 2.924l-.01.89.636.622a2.89 2.89 0 0 1 0 4.134l-.637.622.011.89a2.89 2.89 0 0 1-2.924 2.924l-.89-.01-.622.636a2.89 2.89 0 0 1-4.134 0l-.622-.637-.89.011a2.89 2.89 0 0 1-2.924-2.924l.01-.89-.636-.622a2.89 2.89 0 0 1 0-4.134l.637-.622-.011-.89a2.89 2.89 0 0 1 2.924-2.924l.89.01.622-.636a2.89 2.89 0 0 1 4.134 0l-.715.698a1.89 1.89 0 0 0-2.704 0l-.92.944-1.32-.016a1.89 1.89 0 0 0-1.911 1.912l.016 1.318-.944.921a1.89 1.89 0 0 0 0 2.704l.944.92-.016 1.32a1.89 1.89 0 0 0 1.912 1.911l1.318-.016.921.944a1.89 1.89 0 0 0 2.704 0l.92-.944 1.32.016a1.89 1.89 0 0 0 1.911-1.912l-.016-1.318.944-.921a1.89 1.89 0 0 0 0-2.704l-.944-.92.016-1.32a1.89 1.89 0 0 0-1.912-1.911l-1.318.016z"/>
                                        <path d="M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0z"/>
                                    </svg>
                                    Ritorna alla sezione domande
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
