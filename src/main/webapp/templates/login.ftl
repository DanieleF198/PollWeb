<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="login">
<html lang="it">
    <head>
        <title>${title}</title>
        <meta charset="${charset}">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <#if urlMakerPoll??>
            <#if urlMakerPoll =="no">
                <@macros.style imagePath="images/favicon.ico" stylePath="css/style.css" bootstrapPath="css/bootstrap.css"/>
            <#else>
                <@macros.style imagePath="../images/favicon.ico" stylePath="../css/style.css" bootstrapPath="../css/bootstrap.css"/>
            </#if>
        <#else>
            <@macros.style imagePath="images/favicon.ico" stylePath="css/style.css" bootstrapPath="css/bootstrap.css"/>
        </#if>
        <style>
            .footer {
                position: absolute;
                width: 100%;
                bottom: 0;
            }    
            
            @media screen and (max-height: 690px){
                .footer{
                    position: relative;
                }
            }
        </style>
    </head>
    <body class="bg-light">
        <!-- Login form -->
        <#if urlMakerPoll??>
            <#if urlMakerPoll =="no">
                <form method="post" action="login" class="form-signin">
            <#else>
                <form method="post" action="../login" class="form-signin">
            </#if>
        <#else>
            <form method="post" action="login" class="form-signin">   
        </#if>
            <div class="text-center">
                <#if urlMakerPoll??>
                    <#if urlMakerPoll =="no">
                        <img class="mb-4" src="images/logoDDP.png" alt="" width="140" height="90">
                    <#else>
                        <img class="mb-4" src="../images/logoDDP.png" alt="" width="140" height="90">
                    </#if>
                <#else>
                    <img class="mb-4" src="images/logoDDP.png" alt="" width="140" height="90">
                </#if>
            </div>
            <h1 class="h3 mb-3 font-weight-normal text-center">Effettua l'accesso</h1>
            <#if error?? && error=="Credenziali errate">
                <div>
                    <p class="text-danger d-flex justify-content-center">credenziali errate</p>
                </div>
            </#if>
            <#if error?? && error=="ban">
                <div>
                    <p class="text-danger d-flex justify-content-center">Il tuo account &#232; temporaneamente bloccato</p>
                </div>
            </#if>
            <#if error?? && error=="partecipant">
                <div>
                    <p class="text-danger d-flex justify-content-center">Se sei registrato al sito devi accedere da qui</p>
                </div>
            </#if>
            <label for="inputUsername" class="sr-only">Username</label>
            <input type="text" id="inputUsername" name="inputUsername" class="form-control" placeholder="Username" required autofocus>
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
                <#if urlMakerPoll??>
                    <#if urlMakerPoll =="no">
                        <a href="homepage" class="text-muted">Torna alla homepage</a>
                    <#else>
                        <a href="../homepage" class="text-muted">Torna alla homepage</a>
                    </#if>
                <#else>
                    <a href="homepage" class="text-muted">Torna alla homepage</a>
                </#if>
            </div>
            <div class="mt-2 mb-3 text-center">
                <#if urlMakerPoll??>
                    <#if urlMakerPoll =="no">
                        <a href="register" class="text-muted">Non hai un account? registrati</a>
                    <#else>
                        <a href="../register" class="text-muted">Non hai un account? registrati</a>
                    </#if>
                <#else>
                    <a href="register" class="text-muted">Non hai un account? registrati</a>
                </#if>
            </div>
            <div class="mt-2 mb-3 text-center">
                <p>Hai ricevuto le credenziali tramite invito?<br>
                    <#if urlMakerPoll??>
                        <#if urlMakerPoll =="no">
                            <a href="loginForPartecipants" class="text-muted">Effettua il login da qui!</a>
                        <#else>
                            <a href="../loginForPartecipants" class="text-muted">Effettua il login da qui!</a>
                        </#if>
                    <#else>
                        <a href="loginForPartecipants" class="text-info">Effettua il login da qui!</a>
                    </#if>
                </p>
            </div>
        </form>
        <!-- End login form -->
        <@macros.footer />
        <@macros.script />
    </body>
</html>