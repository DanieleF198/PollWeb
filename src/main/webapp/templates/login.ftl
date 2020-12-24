<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="login">
<html lang="it">
    <head>
        <title>${title}</title>
        <meta charset="${charset}">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <@macros.style imagePath="images/favicon.ico" stylePath="css/style.css" bootstrapPath="css/bootstrap.css"/>
        <style>
            .footer {
                position: absolute;
                width: 100%;
                bottom: 0;
            }    
            
            @media screen and (max-height: 592px){
                .footer{
                    position: relative;
                }
            }
        </style>
    </head>
    <body class="bg-light">
        <!-- Login form -->
        <form method="post" action="login" class="form-signin">
            <div class="text-center">
                <img class="mb-4" src="images/logoDDP.png" alt="" width="140" height="90">
            </div>
            <h1 class="h3 mb-3 font-weight-normal text-center">Effettua l'accesso</h1>
            <#if error?? && error=="Credenziali errate">
                <div>
                    <p class="text-danger d-flex justify-content-center">credenziali errate</p>
                </div>
            </#if>
            <label for="inputUsername" class="sr-only">Username</label>
            <input type="text" id="inputUsername" name="inputUsername" class="form-control" placeholder="Indirizzo e-mail" required autofocus>
            <label for="inputPassword" class="sr-only">Password</label>
            <input type="password" id="inputPassword" name="inputPassword" class="form-control" placeholder="Password" required>
            <div>
                <label class="checkbox mb-3">
                    <input type="checkbox" name="remember" value="remember-me"/>
                    <span class="warning"></span>
                </label>
                &nbspRicordami - 
                <a href="#" class="text-info"> Password dimenticata?</a>
            </div>
            <#if referrer??>
                <input type="hidden" name="referrer" value="${referrer}">
            </#if>
            <button name="buttonLogin" value="login" class="btn btn-lg btn-warning btn-block" type="submit">Accedi!</button>
            <div class="mt-2 mb-3 text-center">
                <a href="homepage" class="text-muted">Torna alla homepage</a>
            </div>
            <div class="mt-2 mb-3 text-center">
                <a href="register" class="text-muted">Non hai un account? registrati</a>
            </div>
        </form>
        <!-- End login form -->
        <@macros.footer />
        <@macros.script />
    </body>
</html>