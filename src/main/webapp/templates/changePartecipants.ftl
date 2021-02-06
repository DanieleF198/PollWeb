<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="Creazione sondaggi - conferma sondaggi">
<html lang="it">
    <head>
        <title>${title}</title>
        <meta charset="${charset}">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <@macros.style imagePath="images/favicon.ico" stylePath="css/style.css" bootstrapPath="css/bootstrap.css"/>
    </head>
    <body class="bg-light">
        <div class="d-flex flex-column min-vh-100">
            <@macros.header imagePath="images/logoDDP.png"/>
            <main class="flex-fill">
                <div class="header-margin"></div>
                <div class="container-fluid">
                    <!-- General information -->
                    <div class="container">
                        <h1 class="h3 mb-3 font-weight-normal">Gestione sondaggio - modifica partecipanti</h1>
                        <form id="returnDashboard" action="dashboard" method="GET"></form>
                        <form id="changePartecipants" method="POST" action="changePartecipants" class="needs-validation" enctype="multipart/form-data" novalidate>
                            <div class="row mt-3 javaScriptVisibility">
                                <div class="col-lg-12">
                                    <div class="row mb-2">
                                        <div class="col-lg-11 col-md-11 col-sx-11 col-11">
                                            <p><b>modifica partecipanti</b> - manuale</p>
                                        </div>
                                        <div class="col-lg-1 col-md-1 col-sx-1 col-1">
                                            <div class="row pl-0 pr-0 pt-0 pb-0 ml-0 mr-0 mt-0 mb-0 justify-content-end">
                                                <button type="button" id="addMore" class="btn btn-warning" title="aggiungi utente">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus" viewBox="0 0 16 16">
                                                        <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
                                                    </svg>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                    <#if partecipantsError?? || CSVerror??>
                                        <p class="text-danger mb-1"><b>Pare che ci siano dei problemi:</b> finch&#233; non li avrai risolti non potrai rendere pubblico il sondaggio (ma potrai salvarlo) e non potrai invitare alcun partecipante.</p>
                                        <#if partecipantsError?? || CSVerror??>
                                            <p class="text-danger mb-1"><b>-</b>Per ogni partecipante invitato devi inserire tutti i campi, inoltre le email e le password (che devono rispettare i requisiti posti durante la registrazione) devono essere tutte differenti tra di loro.</p>
                                        </#if>
                                    </#if>
                                    <#if partecipants??>
                                        <div id="fieldList" class="row mb-3">
                                            <#assign c = 0>
                                            <#list partecipants as partecipant>
                                                <#if partecipant?has_next>
                                                    <#if partecipant.getNome()?? && partecipant.getNome()!="">
                                                        <div class="col-lg-3 col-md-3 col-sx-12 col-12 mb-2">
                                                            <input type="text" name="usersName[]" id="name${c}" class="form-control" placeholder="Nome Utente" value="${partecipant.getNome()}">
                                                        </div>
                                                    <#else>
                                                        <div class="col-lg-3 col-md-3 col-sx-10 col-10 mb-2">
                                                            <input type="text" name="usersName[]" id="name${c}" class="form-control" placeholder="Nome Utente" >
                                                        </div>
                                                    </#if>
                                                    <#if partecipant.getEmail()?? && partecipant.getEmail()!="">
                                                        <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                            <input type="email" name="usersMail[]" id="mail${c}" class="form-control" placeholder="E-mail utente" value="${partecipant.getEmail()}" readonly>
                                                        </div>
                                                    <#else>
                                                        <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                            <input type="email" name="usersMail[]" id="mail${c}" class="form-control" placeholder="E-mail utente" readonly>
                                                        </div>
                                                    </#if>
                                                    <#if partecipant.getPassword()?? && partecipant.getPassword()!="">
                                                        <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                            <input type="password" name="usersPass[]" id="pass${c}" class="form-control" placeholder="Password utente" value="${partecipant.getPassword()}">
                                                        </div>
                                                    <#else>
                                                        <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                            <input type="password" name="usersPass[]" id="pass${c}" class="form-control" placeholder="Password utente" >
                                                        </div>
                                                    </#if>
                                                    <div class="col-lg-1 col-md-1 col-sx-12 col-12 mb-1 justify-content-end">
                                                        <div class="row pl-0 pr-0 pt-0 pb-0 ml-0 mr-0 mt-0 mb-0 justify-content-end">
                                                            <button type="button" id="remove${c}" class="btn btn-danger" title="rimuovi utente" onClick="removeOne(${c})">
                                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-x" viewBox="0 0 16 16">
                                                                    <path d="M6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H1s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C9.516 10.68 8.289 10 6 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z"/>
                                                                    <path fill-rule="evenodd" d="M12.146 5.146a.5.5 0 0 1 .708 0L14 6.293l1.146-1.147a.5.5 0 0 1 .708.708L14.707 7l1.147 1.146a.5.5 0 0 1-.708.708L14 7.707l-1.146 1.147a.5.5 0 0 1-.708-.708L13.293 7l-1.147-1.146a.5.5 0 0 1 0-.708z"/>
                                                                </svg>
                                                            </button>
                                                        </div>
                                                    </div>
                                                    <div class="col-12 border-bottom mb-2"></div>
                                                <#else>
                                                    <#if partecipant.getNome()?? && partecipant.getNome()!="">
                                                        <div class="col-lg-3 col-md-3 col-sx-12 col-12 mb-2">
                                                            <input type="text" name="usersName[]" id="firstUserName" class="form-control" placeholder="Nome Utente" value="${partecipant.getNome()}">
                                                        </div>
                                                    <#else>
                                                        <div class="col-lg-3 col-md-3 col-sx-10 col-10 mb-2">
                                                            <input type="text" name="usersName[]" id="firstUserName" class="form-control" placeholder="Nome Utente"  >
                                                        </div>
                                                    </#if>
                                                    <#if partecipant.getEmail()?? && partecipant.getEmail()!="">
                                                        <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                            <input type="email" name="usersMail[]" id="firstUserMail" class="form-control" placeholder="E-mail utente" value="${partecipant.getEmail()}"  readonly>
                                                        </div>
                                                    <#else>
                                                        <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                            <input type="email" name="usersMail[]" id="firstUserMail" class="form-control" placeholder="E-mail utente"   readonly>
                                                        </div>
                                                    </#if>
                                                    <#if partecipant.getPassword()?? && partecipant.getPassword()!="">
                                                        <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                            <input type="password" name="usersPass[]" id="firstUserPass" class="form-control" placeholder="Password utente" value="${partecipant.getPassword()}">
                                                        </div>
                                                    <#else>
                                                        <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                            <input type="password" name="usersPass[]" id="firstUserPass" class="form-control" placeholder="Password utente"  >
                                                        </div>
                                                    </#if>
                                                    <div class="col-lg-1 col-md-1 col-sx-12 col-12 mb-1">
                                                        <div class="row pl-0 pr-0 pt-0 pb-0 ml-0 mr-0 mt-0 mb-0 justify-content-end">
                                                            <button type="button" id="removeLast" class="btn btn-danger" title="rimuovi utente" onClick="removeLast()">
                                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-x" viewBox="0 0 16 16">
                                                                    <path d="M6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H1s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C9.516 10.68 8.289 10 6 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z"/>
                                                                    <path fill-rule="evenodd" d="M12.146 5.146a.5.5 0 0 1 .708 0L14 6.293l1.146-1.147a.5.5 0 0 1 .708.708L14.707 7l1.147 1.146a.5.5 0 0 1-.708.708L14 7.707l-1.146 1.147a.5.5 0 0 1-.708-.708L13.293 7l-1.147-1.146a.5.5 0 0 1 0-.708z"/>
                                                                </svg>
                                                            </button>
                                                        </div>
                                                    </div>
                                                    <div class="col-12 border-bottom mb-2"></div>
                                                </#if>
                                                <#assign c = c + 1>
                                            </#list>
                                        </div>
                                    <#else>
                                        <div id="fieldList" class="row mb-3">
                                            <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                <input type="text" name="usersName[]" id="firstUserName" class="form-control" placeholder="Nome Utente"  >
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                <input type="text" name="usersMail[]" id="firstUserMail" class="form-control" placeholder="E-mail utente"  >
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sx-12 col-12 mb-2">
                                                <input type="text" name="usersPass[]" id="firstUserPass" class="form-control" placeholder="Password utente"  >
                                            </div>
                                            <div class="col-12 border-bottom mb-2"></div>
                                        </div>
                                    </#if>
                                    <p class="text-muted"><b>Nota: </b> se provi a modificare una password di un utente gi&#224; iscritto, questo non cambier&#224;.</p>
                                    <p><b>modifica partecipanti</b> - tramite csv</p>
                                    <div class="row">
                                        <div class="col-11">
                                            <input type="file" name="file" id="file" class="form-control-file" disabled>
                                        </div>
                                        <div class="col-1">
                                            <label class="checkbox">
                                                <input type="checkbox" name="withCSV" id="withCSV" value="withCSV" onclick="disable()">
                                                <span class="warning"></span>
                                            </label>
                                        </div>
                                    </div>
                                    <p class="mt-3"><b>Attenzione: il file CSV verr&#224; considerato solo se il check alla sua destra sar&#224; spuntato</b>. Se si decide di invitare i partecipanti tramite file csv, i partecipanti presenti nella sezione "inserimento manuale" verranno sostituiti da quelli presenti nel csv</p>
                                </div>
                            </div>
                            <noscript>
                                <div class="row mb-3">
                                    <div class="col-12">
                                        <p><b>Inserimento partecipanti</b> - tramite csv</p>
                                        <input type="file" name="file" id="file" class="form-control-file" disabled>
                                    </div>
                                </div>
                            </noscript>
                            <p class="text-muted"><b>Nota:</b> i partecipanti verranno invitati effettivamente soltanto quando il sondaggio diventa pubblico per la prima volta dal momento in cui tali partecipanti sono stati inseriti. In ogni caso, un utente a cui &#232; stata gi&#224; inviata la mail d'invito, non ricever&#224; altre mail di questo tipo, anche se venisse rimosso e reinserito tra i partecipanti.</p>
                        </form>
                        <div class="row">
                            <div class="col-lg-6 mb-2 pr-1 pl-1 custom-left">
                                <button type="submit" name="returnDashboard" value="returnDashboardButton"  form="returnDashboard" class="btn btn-lg btn-warning btn-block">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person" viewBox="0 0 16 16">
                                        <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z"/>
                                    </svg>
                                    Ritorna alla zona utente
                                </button>
                            </div>
                            <div class="col-lg-6 mb-2 pr-1 pl-1 custom-right">
                                    <button type="submit" name="changePartecipants" value="${idSondaggio}"  form="changePartecipants" class="btn btn-lg btn-warning btn-block">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-save" viewBox="0 0 16 16">
                                        <path d="M2 1a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H9.5a1 1 0 0 0-1 1v7.293l2.646-2.647a.5.5 0 0 1 .708.708l-3.5 3.5a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L7.5 9.293V2a2 2 0 0 1 2-2H14a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h2.5a.5.5 0 0 1 0 1H2z"/>
                                    </svg>
                                    Modifica partecipanti
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
                            $("#fieldList").append("<div class='col-lg-3 col-md-3 col-sx-12 col-12 mb-2'><input type='text' name='usersName[]' class='form-control' placeholder='Nome Utente'></div>");
                            $("#fieldList").append("<div class='col-lg-4 col-md-4 col-sx-12 col-12 mb-2'><input type='text' name='usersMail[]' class='form-control' placeholder='E-mail utente'></div>"); 
                            $("#fieldList").append("<div class='col-lg-4 col-md-4 col-sx-12 col-12 mb-2'><input type='text' name='usersPass[]' class='form-control' placeholder='Password utente'></div>"); 
                            $("#fieldList").append("<div class='col-lg-1 col-md-1 col-sx-1 col-1 mb-2'></div>"); 
                            $("#fieldList").append("<div class='col-12 border-bottom mb-2'></div>"); 
                        } else {
                            $("#fieldList").append("<div class='col-lg-3 col-md-3 col-sx-12 col-12 mb-2'><input type='text' name='usersName[]' class='form-control' placeholder='Nome Utente' disabled></div>");
                            $("#fieldList").append("<div class='col-lg-4 col-md-4 col-sx-12 col-12 mb-2'><input type='text' name='usersMail[]' class='form-control' placeholder='E-mail utente' disabled></div>"); 
                            $("#fieldList").append("<div class='col-lg-4 col-md-4 col-sx-12 col-12 mb-2'><input type='text' name='usersPass[]' class='form-control' placeholder='Password utente' disabled></div>"); 
                            $("#fieldList").append("<div class='col-lg-1 col-md-1 col-sx-1 col-1 mb-2'></div>"); 
                            $("#fieldList").append("<div class='col-12 border-bottom mb-2'></div>"); 
                        }
                    });
                });    
                function disable(){
                    $(document).ready(function(){
                        var btnArr = document.querySelectorAll('.btn-danger');
                        var emailArr = document.querySelectorAll('input[type=email]');    
                        var i;
                        if(document.getElementById('firstUserName').disabled === false){
                            $("input[type=text]").prop("disabled", true);
                            $("input[type=password]").prop("disabled", true);
                        } else {
                            $("input[type=text]").prop("disabled", false);
                            $("input[type=password]").prop("disabled", false);
                        }
                        if(document.getElementById('file').disabled === true){
                            $('#file').prop("disabled", false);
                        } else {
                            $('#file').prop("disabled", true);
                        }
                        for (i = 0; i < emailArr.length; i++) {
                            if(emailArr[i].disabled === true){
                                emailArr[i].disabled = false;
                            }
                        }
                        for (i = 0; i < btnArr.length; i++) {
                            if(btnArr[i].disabled === true){
                                btnArr[i].disabled = false;
                            } else {
                                btnArr[i].disabled = true;
                            }
                        }
                    });
                };
                    
                function removeOne(parameter1){
                    var str1 = 'name' + parameter1;
                    var str2 = 'mail' + parameter1;
                    var str3 = 'pass' + parameter1;
                    var res1 = '#' + str1;
                    var res2 = '#' + str2;
                    var res3 = '#' + str3;
                    if(document.getElementById(str1).disabled === true){
                        $(res1).prop("disabled", false);
                        $(res2).prop("disabled", false);
                        $(res3).prop("disabled", false); 
                    } else {
                        $(res1).prop("disabled", true);
                        $(res2).prop("disabled", true);
                        $(res3).prop("disabled", true); 
                    }
                                
                };
                    
                function removeLast(){
                    if(document.getElementById('#firstUserName').disabled === true){
                        $('#firstUserName').prop("disabled", false);
                        $('#firstUserMail').prop("disabled", false);
                        $('#firstUserPass').prop("disabled", false);
                    } else {
                        $('#firstUserName').prop("disabled", true);
                        $('#firstUserMail').prop("disabled", true);
                        $('#firstUserPass').prop("disabled", true);
                    }
                } 
            </script>
        </div>
    </body>
</html>
