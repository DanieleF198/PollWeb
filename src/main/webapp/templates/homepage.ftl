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
        <div class="d-flex flex-column min-vh-100">
            <@macros.header />
            <main class="flex-fill">
                <img src="images/logoDDP.png" alt="" loading="lazy" class ="img-fluid" style="margin-top:6%; height: auto">
            </main>
            <@macros.footer />
            <@macros.script />
        </div>
    </body>
</html>
