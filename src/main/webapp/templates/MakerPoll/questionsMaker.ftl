<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="Creazione sondaggi">
<html lang="it">
    <head>
        <title>${title}</title>
        <meta charset="${charset}">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <@macros.style imagePath="../images/favicon.ico" stylePath="../css/style.css"/>
    </head>
    <body class="bg-light">
        <div class="d-flex flex-column min-vh-100">
            <@macros.header imagePath="../images/logoDDP.png"/>
            <main class="flex-fill">
                <div class="header-margin"></div>
                <div class="container-fluid">
                    <!-- General information -->
                    <div class="container">
                        <h1 class="h3 mb-3 font-weight-normal">Creazione sondaggio - creazione domande</h1>
                        <form method="post" action="goToQuestions" class="needs-validation" novalidate>
                            <div class="row mb-3">
                                <div class="col-lg-12 mb-3">
                                    <div class="tab-wrap">
                                        <input type="radio" id="tab1" name="tabGroup1" class="tab" checked>
                                        <label for="tab1">Short</label>

                                        <input type="radio" id="tab2" name="tabGroup1" class="tab">
                                        <label for="tab2">Medium</label>

                                        <input type="radio" id="tab3" name="tabGroup1" class="tab">
                                        <label for="tab3">Long</label>

                                        <div class="tab__content">
                                            TAB ONE CONTENT
                                        </div>
                                        <div class="tab__content">
                                            TAB Two CONTENT
                                        </div>
                                        <div class="tab__content">
                                            TAB THREE CONTENT
                                        </div>
                                    </div>
                                </div>
                            </div>
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
