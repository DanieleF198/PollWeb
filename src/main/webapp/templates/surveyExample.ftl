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
                                <p class="h1">Sondaggio d'esempio - titolo</p>
                            </div>
                        </div>
                        <div class="row mb-2">
                            <div class="col-12">
                                <p class="h5 text-muted">Questo &#232; il testo d'apertura, in questa sezione ti consigliamo di scrivere una descrizione del sondaggio, qual &#232; il suo obiettivo, come verranno utilizzati i suoi risultati o qualsiasi altra cosa che pu&#242; riguardargli. Questa parte &#232; facoltativa. In ogni caso questo sondaggio d'esempio serve a mostrare tutte le possibili tipologie di domande che si possono utilizzare per creare un sondaggio in Quack,Duck,Poll. Altre funzionalit&#224; non visibili in questa pagina sono la possibilit&#224; di rendere il sondaggio privato e di invitare i partecipanti, dare la possibilit&#224; a chi risponde al sondaggio di modificare la sua risposta, impostare vincoli ed inserire una data di scadenza. Speriamo vivamente che questo strumento possa fare al caso vostro!</p>
                            </div>
                        </div>
                    </div>
                    <div class="container border-bottom mt-4">
                        <@macros.openShort/>
                    </div>
                    <div class="container border-bottom mt-4">
                        <@macros.openLong/>
                    </div>
                    <div class="container border-bottom mt-4">
                        <@macros.openNumber/>
                    </div>
                    <div class="container border-bottom mt-4">
                        <@macros.openDate/>
                    </div>
                    <div class="container border-bottom mt-4">
                        <@macros.closeSingle/>
                    </div>
                    <div class="container border-bottom mt-4">
                        <@macros.closeMultiple/>
                    </div>
                    <div class="container mt-4">
                        <div class="row">
                            <div class="col-12">
                                <p><b>tutte le domande con l'<span class="text-danger">*</span> sono obbligatorie</b></p>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
            <@macros.footer />
            <@macros.script />
        </div>
    </body>
</html>
