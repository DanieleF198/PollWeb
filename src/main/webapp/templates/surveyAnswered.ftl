<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="Quack, Duck, Poll!">
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
                <div class="container-fluid">
                    <div class="container">
                        <#if AlreadyCompiled??>
                            <div class="row">
                                <div class="col-12">
                                    <p class="h1">La risposta al sondaggio non &#232; modificabile</p>
                                    <p class="h4 text-muted mt-3">Hai gi&#224; risposto a questo sondaggio in passato, ed il sondaggio &#232; impostato per non permettere di modificare le risposte inviate</p>
                                </div>
                            </div>
                            <form id="returnHomepage" action="homepage" method="GET"></form>
                            <div class="row mt-5">
                                <div class="col-lg-12 mb-2 pr-1 pl-1 custom-right">
                                        <button type="submit" name="returnHomepage" value="returnHomepageButton" form="returnHomepage" class="btn btn-lg btn-warning custom-block">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-house-door" viewBox="0 0 16 16">
                                            <path d="M8.354 1.146a.5.5 0 0 0-.708 0l-6 6A.5.5 0 0 0 1.5 7.5v7a.5.5 0 0 0 .5.5h4.5a.5.5 0 0 0 .5-.5v-4h2v4a.5.5 0 0 0 .5.5H14a.5.5 0 0 0 .5-.5v-7a.5.5 0 0 0-.146-.354L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.354 1.146zM2.5 14V7.707l5.5-5.5 5.5 5.5V14H10v-4a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5v4H2.5z"/>
                                        </svg>
                                        Ritorna alla homepage
                                    </button>
                                </div>
                            </div>
                        <#else>
                            <div class="row">
                                <div class="col-12">
                                    <p class="h1">La risposta &#232; stata registrata</p>
                                    <#if sondaggio.getTestoChiusura()?? && sondaggio.getTestoChiusura()!="">
                                        <p class="h4 text-muted mt-3">${sondaggio.getTestoChiusura()}</p>
                                    <#else>
                                        <p class="h4 text-muted mt-3">La ringraziamo per averci concesso il tempo necessario per rispondere a questo sondaggio</p>
                                    </#if>
                                </div>
                            </div>
                            <#if sondaggio.isModificabile()>
                                <form id="returnSurvey" action="survey" method="GET"></form>
                                <form id="returnHomepage" action="homepage" method="GET"></form>
                                <div class="row mt-5">
                                    <div class="col-lg-6 mb-2 pr-1 pl-1 custom-left">
                                        <button type="submit" name="returnSurvey" value="${sondaggio.getKey()}" form="returnSurvey" class="btn btn-lg btn-warning btn-block">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-return-left" viewBox="0 0 16 16">
                                                <path fill-rule="evenodd" d="M14.5 1.5a.5.5 0 0 1 .5.5v4.8a2.5 2.5 0 0 1-2.5 2.5H2.707l3.347 3.346a.5.5 0 0 1-.708.708l-4.2-4.2a.5.5 0 0 1 0-.708l4-4a.5.5 0 1 1 .708.708L2.707 8.3H12.5A1.5 1.5 0 0 0 14 6.8V2a.5.5 0 0 1 .5-.5z"/>
                                            </svg>
                                            Modifica risposte
                                        </button>
                                    </div>
                                    <div class="col-lg-6 mb-2 pr-1 pl-1 custom-right">
                                            <button type="submit" name="returnHomepage" value="returnHomepageButton" form="returnHomepage" class="btn btn-lg btn-warning btn-block">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-house-door" viewBox="0 0 16 16">
                                                <path d="M8.354 1.146a.5.5 0 0 0-.708 0l-6 6A.5.5 0 0 0 1.5 7.5v7a.5.5 0 0 0 .5.5h4.5a.5.5 0 0 0 .5-.5v-4h2v4a.5.5 0 0 0 .5.5H14a.5.5 0 0 0 .5-.5v-7a.5.5 0 0 0-.146-.354L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.354 1.146zM2.5 14V7.707l5.5-5.5 5.5 5.5V14H10v-4a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5v4H2.5z"/>
                                            </svg>
                                            Ritorna alla homepage
                                        </button>
                                    </div>
                                </div>
                            <#else>
                                <form id="returnHomepage" action="homepage" method="GET"></form>
                                <div class="row mt-5">
                                    <div class="col-lg-12 mb-2 pr-1 pl-1 custom-right">
                                            <button type="submit" name="returnHomepage" value="returnHomepageButton" form="returnHomepage" class="btn btn-lg btn-warning custom-block">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-house-door" viewBox="0 0 16 16">
                                                <path d="M8.354 1.146a.5.5 0 0 0-.708 0l-6 6A.5.5 0 0 0 1.5 7.5v7a.5.5 0 0 0 .5.5h4.5a.5.5 0 0 0 .5-.5v-4h2v4a.5.5 0 0 0 .5.5H14a.5.5 0 0 0 .5-.5v-7a.5.5 0 0 0-.146-.354L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.354 1.146zM2.5 14V7.707l5.5-5.5 5.5 5.5V14H10v-4a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5v4H2.5z"/>
                                            </svg>
                                            Ritorna alla homepage
                                        </button>
                                    </div>
                                </div>
                            </#if>
                        </#if>
                    </div>
                </div>
            </main>
            <@macros.footer />
            <@macros.script />
        </div>
    </body>
</html>

