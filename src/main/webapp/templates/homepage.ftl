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
                <!-- Welcome section -->
                <div class="container-fluid" style="background-color: #FFFDEB">
                    <div class="container pl-5 pr-5">
                        <div class="row pt-5 pb-5">
                            <div class="col-lg-5 pl-5 align-self-center">
                                <h1>Quack, Duck, Poll!</h1>
                                <h2>Uno spazio confortevole dove creare sondaggi in base alle tue esigenze</h2>
                                <form class="form-inline pt-3">
                                    <button class="btn btn-warning" type="button">Comincia adesso!</button>
                                </form>
                            </div>
                            <div class="col-lg-7 pr-5">
                                <img src="images/welcomeImage.png" alt="" loading="lazy" class ="img-fluid" style="margin-top:6%; height: auto">
                            </div>
                        </div>
                    </div>
                </div>
                <!-- End welcome section -->
            </main>
            <@macros.footer />
            <@macros.script />
        </div>
    </body>
</html>
