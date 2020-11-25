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
                <div class="container-fluid" style="background-color: #FFFBC9">
                    
                    <!-- Mini-banners section -->
                    <div class="container">
                        <div class="row pt-5 pb-5">
                            
                            <div class="col-lg-1 align-self-center">
                                <svg width="64" height="64" viewBox="0 0 16 16" class="bi bi-file-text" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                    <path fill-rule="evenodd" d="M4 0h8a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2zm0 1a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H4z"/>
                                    <path fill-rule="evenodd" d="M4.5 10.5A.5.5 0 0 1 5 10h3a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zm0-2A.5.5 0 0 1 5 8h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zm0-2A.5.5 0 0 1 5 6h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zm0-2A.5.5 0 0 1 5 4h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5z"/>
                                </svg>
                            </div>
                            <div class="col-lg-3 align-self-center">
                                <p style="font-size: 20px; font-weight: 500;">Espording dei dati</p>
                                <p>Esporta e analizza i risultati del tuo sondaggio tramite un file .csv</p>
                            </div>
                            
                            <div class="col-lg-1 align-self-center">
                                <svg width="64" height="64" viewBox="0 0 16 16" class="bi bi-lock" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                    <path fill-rule="evenodd" d="M11.5 8h-7a1 1 0 0 0-1 1v5a1 1 0 0 0 1 1h7a1 1 0 0 0 1-1V9a1 1 0 0 0-1-1zm-7-1a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h7a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2h-7zm0-3a3.5 3.5 0 1 1 7 0v3h-1V4a2.5 2.5 0 0 0-5 0v3h-1V4z"/>
                                </svg>
                            </div>
                            <div class="col-lg-3 align-self-center">
                                <p style="font-size: 20px; font-weight: 500;">Sondaggi privati</p>
                                <p>Crea un sondaggio e decidi chi pu&#242; rispondere (oppure invitalo!)</p>                                
                            </div>
                            
                            <div class="col-lg-1 align-self-center">
                                <svg width="64" height="64" viewBox="0 0 16 16" class="bi bi-check2-circle" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                    <path fill-rule="evenodd" d="M15.354 2.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3-3a.5.5 0 1 1 .708-.708L8 9.293l6.646-6.647a.5.5 0 0 1 .708 0z"/>
                                    <path fill-rule="evenodd" d="M8 2.5A5.5 5.5 0 1 0 13.5 8a.5.5 0 0 1 1 0 6.5 6.5 0 1 1-3.25-5.63.5.5 0 1 1-.5.865A5.472 5.472 0 0 0 8 2.5z"/>
                                </svg>
                            </div>
                            <div class="col-lg-3 align-self-center">
                                <p style="font-size: 20px; font-weight: 500;">Crea dei quiz!</p>
                                <p>Rendi il tuo sondaggio un quiz con calcolo del punteggio totale</p>
                            </div>
                        </div>
                    </div>
                    <!-- End mini-banners section -->
                    
                </div>
            </main>
            <@macros.footer />
            <@macros.script />
        </div>
    </body>
</html>
