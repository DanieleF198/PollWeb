<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="Responsable request">
<html lang="it">
    <head>
        <title>${title}</title>
        <meta charset="${charset}">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <@macros.style />
        <style>
            .form-signin {
                width: 100%;
                max-width: 330px;
                padding: 15px;
                margin: auto;
            }
            .form-signin {
                font-weight: 400;
            }
            .form-signin .form-control {
                position: relative;
                box-sizing: border-box;
                height: auto;
                padding: 10px;
                font-size: 16px;
            }
            .form-signin .form-control:focus {
                z-index: 2;
            }
            .form-signin input[type="email"] {
                margin-bottom: -1px;
                border-bottom-right-radius: 0;
                border-bottom-left-radius: 0;
            }
            .form-signin input[type="password"] {
                margin-bottom: 10px;
                border-top-left-radius: 0;
                border-top-right-radius: 0;
            }
            
            .footer {
                position: absolute;
                width: 100%;
                bottom: 0;
            }    
            
            @media screen and (max-height: 558px){
            .footer{
                position: relative;
            }
      }
        </style>
    </head>
    <body class="bg-light">
        <!-- Login form -->
        <form class="form-signin">
            <div class="text-center">
                <img class="mb-4" src="images/logoDDP.png" alt="" width="140" height="90">
            </div>
            <h1 class="h3 mb-3 font-weight-normal text-center">PAGINA DA SCRIVERE</h1>
            <label for="inputEmail" class="sr-only">Indirizzo e-mail</label>
            <input type="email" id="inputEmail" class="form-control" placeholder="Indirizzo e-mail" required autofocus>
            <label for="inputPassword" class="sr-only">Password</label>
            <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
            <div>
                <label class="checkbox mb-3">
                    <input type="checkbox" value="remember-me"/>
                    <span class="warning"></span>
                </label>
                &nbspRicordami
            </div>
            <button class="btn btn-lg btn-warning btn-block" type="submit">Accedi!</button>
            <div class="mt-2 mb-3 text-center">
                <a href="homepage" class="text-muted">Torna alla homepage</a>
            </div>
        </form>
        <!-- End login form -->
        <@macros.footer />
        <@macros.script />
    </body>
</html>