<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="homepage">
<html>
    <head>
        <title>${title}</title>
        <meta charset="${charset}">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <@macros.style />
    </head>
    <body>
        <div style="height: 500px;">
            <div class="container mt-5 h-100">
                <div class="row shadow-lg h-100">
                    <div class="col-lg-4 bg-success"></div>
                    <div class="col-lg-4 bg-light"><div style="text-align: center; margin-top:50%;">CIAO!<br/> questa e' giusto una scheramata di prova che ho creato per fare testing e per riprendere la mano con bootstrap.</div></div>
                    <div class="col-lg-4 bg-danger"></div>
                </div>
            </div>
        </div>
        <@globalTemplate.script />
    </body>
</html>
