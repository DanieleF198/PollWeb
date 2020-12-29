<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="registration">
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
            
            @media screen and (max-width: 992px) and (max-height: 930px) , screen and (max-height: 755px), screen and (max-width: 1200px) and (max-height: 774px), screen and (max-width: 768px) and (max-height: 1030px){
                .footer{
                    position: relative;
                }
            }
        </style>
    </head>
    <body class="bg-light">
        <!-- Register form -->
        <div class="container" style="max-width: 605px;">
            <div class="text-center">
                <img class="mb-4" src="images/logoDDP.png" alt="" width="140" height="90">
            </div>
            <h1 class="h3 mb-3 font-weight-normal text-center">Compila il modulo</h1>
            <#if error?? && error!="">
                <div>
                    <p class="text-danger d-flex justify-content-center">${error}</p>
                </div>
            </#if>
            <form method="post" action="register" class="needs-validation" novalidate>
                <div class="row">
                    <div class="col-lg-6 mb-3">
                        <label for="firstName">Nome</label>
                        <input type="text" class="form-control" name="firstName" id="firstName" placeholder="" value="" required> 
                    </div>
                    <div class="col-lg-6 mb-3">
                        <label for="lastName">Cognome</label>
                        <input type="text" class="form-control" name="lastName" id="lastName" placeholder="" value="" required>
                    </div>
                </div>

                <div class="row">
                    <div class="col-lg-7 mb-3">
                        <label for="age">Username</label>
                        <input type="text" class="form-control" name="username" id="username" placeholder="" value="" required>
                    </div>
                    <div class="col-lg-5 mb-3">
                        <label for="age">Data di nascita</label>
                        <input type="date" class="form-control" name="date" id="date" placeholder="" value="" required>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-12 mb-3">
                        <label for="email">Indirizzo e-mail</label>
                        <input type="email" class="form-control" name="email" id="email" placeholder="" value="" required>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" name="password" id="password" placeholder="" value="" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="confirmPassword">Conferma password</label>
                        <input type="password" class="form-control" name="confirmPassword" id="confirmPassword" placeholder="" value="" required>
                    </div>
                </div>
                <div class="mb-4"></div>
                <button name ="buttonRegister" value="register" class="btn btn-warning btn-lg btn-block" type="submit">Unisciti alla community</button>
            </form>
        </div>
        <!-- End register form -->
    <@macros.footer />
    <@macros.script />
    </body>
</html>