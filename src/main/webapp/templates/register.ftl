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
            <form class="needs-validation" novalidate>
                <div class="row">
                    <div class="col-lg-4 mb-3">
                        <label for="firstName">Nome</label>
                        <input type="text" class="form-control" id="firstName" placeholder="" value="" required> 
                        <div class="invalid-feedback"> <!-- non so esattamente come funzioni ma per il momento ce lo lascio -->
                            Il nome inserito non è valido.
                        </div>
                    </div>
                    <div class="col-lg-4 mb-3">
                        <label for="lastName">Cognome</label>
                        <input type="text" class="form-control" id="lastName" placeholder="" value="" required>
                        <div class="invalid-feedback">
                            Il cognome inserito non è valido
                        </div>
                    </div>
                    <div class="col-lg-4 mb-3">
                        <label for="age">Data di nascita</label>
                        <input type="date" class="form-control" id="date" placeholder="" value="" required>
                        <div class="invalid-feedback">
                            La data inserita non è valida
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 mb-3">
                        <label for="email">Indirizzo e-mail</label>
                        <input type="email" class="form-control" id="email" placeholder="" value="" required>
                        <div class="invalid-feedback">
                            L'indirizzo e-mail inserito non è valido
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 mb-3">
                        <label for="confirmEmail">Conferma indirizzo e-mail</label>
                        <input type="confirmEmail" class="form-control" id="confirmEmail" placeholder="" value="" required>
                        <div class="invalid-feedback">
                            L'indirizzo e-mail inserito non coincide e/o non è valido
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" placeholder="" value="" required>
                        <div class="invalid-feedback">
                            La password inserita non è valida
                        </div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="confirmPassword">Conferma password</label>
                        <input type="password" class="form-control" id="lastName" placeholder="" value="" required>
                        <div class="invalid-feedback">
                            La password inserita non coincide e/o non è valida
                        </div>
                    </div>
                </div>
                <div class="mb-4"></div>
                <button class="btn btn-warning btn-lg btn-block" type="submit">Unisciti alla community</button>
                <div class="mt-2 mb-3 text-center">
                    <a href="homepage" class="text-muted">Torna alla homepage</a>
                </div>
            </form>
        </div>
        <!-- End register form -->
    <@macros.footer />
    <@macros.script />
    </body>
</html>