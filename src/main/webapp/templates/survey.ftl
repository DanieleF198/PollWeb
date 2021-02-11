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
                                <p class="h5 text-muted">${sondaggio.getDescrizione()}</p>
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

