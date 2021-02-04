<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="Creazione sondaggi - impostazioni generali">
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
                        <h1 class="h3 mb-3 font-weight-normal">Creazione sondaggio - informazioni generali</h1>
                        <#if error?? && error!="">
                            <div>
                                <p class="text-danger">${error}</p>
                            </div>
                        </#if>
                        <form method="post" action="firstSection" class="needs-validation" novalidate>
                            <div class="row mb-3">
                                <div class="col-lg-12">
                                    <label class="h5" for="title">Titolo</label>
                                    <#if sondaggio??>
                                        <#if sondaggio.getTitolo()??>
                                            <input type="text" name="title" value="${sondaggio.getTitolo()}" class="form-control" id="title" placeholder="almeno 3 caratteri" maxlength="128" required> 
                                        <#else>
                                            <input type="text" name="title" class="form-control" id="title" placeholder="almeno 3 caratteri" maxlength="128" required> 
                                        </#if>
                                    <#else>
                                        <input type="text" name="title" class="form-control" id="title" placeholder="almeno 3 caratteri" maxlength="128" required> 
                                    </#if>
                                    <div class="invalid-feedback"> <!-- non so esattamente come funzioni ma per il momento ce lo lascio -->
                                        Il titolo inserito non è valido.
                                    </div>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-lg-12 mb-3">
                                    <label class="h5" for="description">Descrizione (facoltativa)</label>
                                    <#if sondaggio??>
                                        <#if sondaggio.getTestoApertura()??>
                                            <textarea rows="5" name="description" class="form-control" id="description" placeholder="Questa descrizione apparir&#224; sia nella preview del sondaggio che nella pagina prima della compilazione. Sono consentiti al massimo 2048 caratteri" maxlength="2048">${sondaggio.getTestoApertura()}</textarea>                               
                                        <#else>
                                            <textarea rows="5" name="description" class="form-control" id="description" placeholder="Questa descrizione apparir&#224; sia nella preview del sondaggio che nella pagina prima della compilazione. Sono consentiti al massimo 2048 caratteri" maxlength="2048"></textarea>
                                        </#if>
                                    <#else>
                                        <textarea rows="5" name="description" class="form-control" id="description" placeholder="Questa descrizione apparir&#224; sia nella preview del sondaggio che nella pagina prima della compilazione. Sono consentiti al massimo 2048 caratteri" maxlength="2048"></textarea>
                                    </#if>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-lg-12 mb-3">
                                    <label class="h5" for="finalMessage">Messaggio di completamento (facoltativo)</label>
                                    <#if sondaggio??>
                                        <#if sondaggio.getTestoChiusura()??>
                                            <textarea rows="3" name="finalMessage" class="form-control" id="finalMessage" placeholder="Messaggio che apparir&#224; alla fine della compilazione, se non riempito apparir&#224; il messaggio di default. Sono consentiti al massimo 1024 caratteri" maxlength="1024">${sondaggio.getTestoChiusura()}</textarea>
                                        <#else>
                                            <textarea rows="3" name="finalMessage" class="form-control" id="finalMessage" placeholder="Messaggio che apparir&#224; alla fine della compilazione, se non riempito apparir&#224; il messaggio di default. Sono consentiti al massimo 1024 caratteri" maxlength="1024"></textarea>
                                        </#if>
                                    <#else>
                                        <textarea rows="3" name="finalMessage" class="form-control" id="finalMessage" placeholder="Messaggio che apparir&#224; alla fine della compilazione, se non riempito apparir&#224; il messaggio di default. Sono consentiti al massimo 1024 caratteri" maxlength="1024"></textarea>
                                    </#if>
                                </div>
                            </div>
                            <div class="border-bottom mb-3"></div>
                            <div class="row">
                                <div class="col-lg-12 mb-2">
                                    <p class="h3 mb-3 font-weight-normal">Creazione sondaggio - impostazioni</h1>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12 mb-2">
                                    <p><b>Sondaggio pubblico o privato?</b></p>
                                    <p>Se il sondaggio privato non &#232; disponibile, &#232; perch&#232; devi richiedere l'abilitazione, <a href="/PollWeb/sendRespRequest" class="text-info">clicca qui</a> per mandare la richiesta, richieder&#224; solo l'aggiunta del tuo codice fiscale!</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12">
                                    <div>
                                        <#if group?? && group=="base">
                                        <label class="checkbox-disabled mb-3">
                                            <input type="checkbox" name="private" disabled value="private"/>
                                            <span class="warning"></span>
                                        </label>
                                        &nbspSondaggio privato
                                        <#else>
                                        <label class="checkbox mb-3">
                                            <#if private?? && private=="yes">
                                                <input type="checkbox" name="private" value="private" checked>
                                            <#else>
                                                <input type="checkbox" name="private" value="private">
                                            </#if>
                                            <span class="warning"></span>
                                        </label>
                                        &nbspSondaggio privato
                                        </#if>
                                    </div>
                                    <br>
                                    <p>In caso di sondaggio privato nella sezione finale ti verr&#224; chiesto di caricare un file .csv contenente le email delle persone che intendi invitare.</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12 mb-2">
                                    <p><b>Sondaggio modificabile dopo essere stato compilato?</b></p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12">
                                    <div>
                                        <label class="checkbox mb-3">
                                            <#if modificable??>
                                                <#if modificable=="yes">
                                                    <input type="checkbox" name="modificable" value="modificable" checked>
                                                <#else>
                                                <input type="checkbox" name="modificable" value="modificable">
                                                </#if>
                                            <#else>
                                                <input type="checkbox" name="modificable" value="modificable">
                                            </#if>
                                            <span class="warning"></span>
                                        </label>
                                        &nbspSondaggio modificabile
                                    </div>
                                    <br>
                                    <p>In caso di sondaggio modificabile qualsiasi utente che ha compilato il sondaggio potr&#224; cambiare le risposte che ha dato in qualsiasi momento prima della scadenza</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12 mb-2">
                                    <label for="expiration"><b>Data di scadenza (facoltativo)</b></label>
                                    <div class="mb-2" style="width: 180px">
                                        <#if expirationDate??>
                                            <#setting date_format="yyyy-MM-dd">
                                            <#assign date = expirationDate?date>
                                            <input type="date" name="expiration" class="form-control" id="expiration" placeholder="" value="${date}">
                                        <#else>
                                            <input type="date" name="expiration" class="form-control" id="expiration" placeholder="" value="">
                                        </#if>
                                        <div class="invalid-feedback">
                                            La data inserita non è valida
                                        </div>
                                    </div>
                                    <p>In ogni caso potrai modificare o annullare tale data nella tua pagina personale dopo aver finito di creare il sondaggio.</p>
                                </div>
                            </div>
                            <button name ="buttonFirstSection" value="firstSection" class="btn btn-lg btn-warning" type="submit">Inizia con le domande</button>
                        </form>
                    </div>
                    <!-- End general information -->
                </div>
            </main>
    <@macros.footer />
    <@macros.script />
        </div>
    </body>
</html>
