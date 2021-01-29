<#ftl strip_whitespace = true>

<#setting boolean_format=computer>
<#import "/libs/mylib.ftl" as my>

<#assign charset="UTF-8">
<#assign title="Example">
<#assign content>
This is content
</#assign>
<!DOCTYPE html>
<html>
    <head>
        <title>${title}</title>
        <meta charset="${charset}">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <@macros.style imagePath="images/favicon.ico" stylePath="css/style.css" bootstrapPath="css/bootstrap.css"/>
        <@macros.carouselOvverridingStyle />
    </head>
    <body class="bg-light">
        <div class="d-flex flex-column min-vh-100">
            <@macros.header imagePath="../images/logoDDP.png"/>
            <main class="flex-fill">
                <div class="header-margin"></div>
                <div class="container-fluid">
                    <!-- General information -->
                    <div class="container">
                        <div class="mt-5"></div>
                        <div class="row justify-content-center">
                            <div class="col-6">
                                <p class="h1 text-center">Pare che tu sia stato temporaneamente bloccato, verrai reindirizzato alla home page<p>
                            </div>
                        </div>
                        <form method="post" action="firstSection">
                            <div class="row justify-content-center">
                                <div class="col-6">
                                    <button name ="buttonContinue" value="continue" class="btn btn-warning btn-lg btn-block">HomePage</button>
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