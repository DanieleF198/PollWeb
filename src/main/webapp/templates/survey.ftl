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
                    <div class="container border-bottom">
                        <div class="row">
                            <div class="col-12">
                                <p class="h1">${sondaggio.getTitolo()}</p>
                            </div>
                        </div>
                        <div class="row mb-2">
                            <div class="col-12">
                                <#if sondaggio.getTestoApertura()??>
                                    <p class="h5 text-muted">${sondaggio.getTestoApertura()}</p>
                                </#if>
                            </div>
                        </div>
                    </div>
                    <#if errors??>
                        <div class="container border-bottom">
                            <div class="row">
                                <div class="col-12">
                                    <p class="text-danger"><b>Ci sono degli errori:</b></p>
                                    <#list errors as error>
                                        <p class="text-danger"><b>-</b>${error}</p>
                                    </#list>
                                </div>
                            </div>
                        </div>
                    </#if>
                    <form id="sendAnswer" action="survey" method="POST">
                        <#list domande as domandas>
                            <#if domandas.getTipo()=="openShort">
                                <div class="container border-bottom mt-4">
                                    <@macros.openShort domanda=domandas/>
                                </div>
                            </#if>
                            <#if domandas.getTipo()=="openLong">
                                <div class="container border-bottom mt-4">
                                    <@macros.openLong domanda=domandas/>
                                </div>
                            </#if>
                            <#if domandas.getTipo()=="openNumber">
                                <div class="container border-bottom mt-4">
                                    <@macros.openNumber domanda=domandas/>
                                </div>
                            </#if>
                            <#if domandas.getTipo()=="openDate">
                                <div class="container border-bottom mt-4">
                                    <@macros.openDate domanda=domandas/>
                                </div>
                            </#if>
                            <#if domandas.getTipo()=="closeSingle">
                                <div class="container border-bottom mt-4">
                                    <@macros.closeSingle domanda=domandas/>
                                </div>
                            </#if>
                            <#if domandas.getTipo()=="closeMultiple">
                                <div class="container border-bottom mt-4">
                                    <@macros.closeMultiple domanda=domandas/>
                                </div>
                            </#if>
                        </#list>
                        <div class="container mt-4">
                            <div class="row">
                                <div class="col-lg-9 col-md-8">
                                    <p class="pt-3"><b>tutte le domande con l'<span class="text-danger">*</span> sono obbligatorie</b></p>
                                </div>
                                <div class="col-lg-3 col-md-4">
                                    <#if modRisposta??>
                                        <button type="submit" name="ModAnswer" value="ModAnswer${sondaggio.getKey()}" class="btn btn-lg btn-warning btn-block">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check-circle" viewBox="0 0 16 16">
                                            <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                            <path d="M10.97 4.97a.235.235 0 0 0-.02.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-1.071-1.05z"/>
                                        </svg>
                                        Invia modifica
                                        </button>
                                    <#else>
                                        <button type="submit" name="sendAnswer" value="sendAnswer${sondaggio.getKey()}" class="btn btn-lg btn-warning btn-block">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check-circle" viewBox="0 0 16 16">
                                            <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                            <path d="M10.97 4.97a.235.235 0 0 0-.02.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-1.071-1.05z"/>
                                        </svg>
                                        Invia risposta
                                        </button>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </main>
            <@macros.footer />
            <@macros.script />
        </div>
    </body>
</html>

