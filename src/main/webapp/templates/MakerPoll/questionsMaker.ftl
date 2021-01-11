<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="Creazione sondaggi - creazione domande">
<html lang="it">
    <head>
        <title>${title}</title>
        <meta charset="${charset}">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <@macros.style imagePath="../images/favicon.ico" stylePath="../css/style.css" bootstrapPath="../css/bootstrap.css"/>
        <style>
            @media screen and (max-width: 991px){
                .footer{
                    position: fixed;
                    bottom: 0;
                    width: 100%;
                    z-index: 100;
                }
            }
            
            .custom-margin-bottom{
                margin-bottom:0%;
            }
            
            @media screen and (max-width: 991px){
                .custom-margin-bottom{
                    margin-bottom:15%;
                }
            }
            
            @media screen and (max-width: 768px){
                .custom-margin-bottom{
                    margin-bottom:25%;
                }
            }
                
        </style>
    </head>
    <body class="bg-light" onload="disable();">
        <div class="d-flex flex-column min-vh-100">
            <@macros.header imagePath="../images/logoDDP.png"/>
            <main class="flex-fill">
                <div class="header-margin"></div>
                <div class="container-fluid">
                    <div class="container">
                        <form method="post" action="questionsMaker" class="needs-validation" novalidate>
                            <#if noError??>
                                <div class="row mb-3">
                                    <div class="col-lg-12">
                                        <h1 class="h3 mb-3 font-weight-normal">Creazione sondaggio - creazione domande</h1>
                                    </div>   
                                </div>
                            <#else>
                                <div class="row mb-3">
                                    <div class="col-lg-8">
                                        <h1 class="h3 mb-3 font-weight-normal">Creazione sondaggio - creazione domande</h1>
                                    </div>
                                    <div class="col-lg-4">
                                        <button type="submit" name="removeQuestion" value="removeQuestionButton" class="btn btn-lg btn-danger float-lg-right custom-block">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-x" viewBox="0 0 16 16">
                                                <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
                                            </svg>
                                            Rimuovi domanda
                                        </button>
                                    </div>
                                </div>
                            </#if>
                            <div class="row mb-3">
                                <div class="col-lg-12">
                                    <label class="h5" for="questionTitle">Titolo domanda</label>
                                    <input type="text" class="form-control" id="questionTitle" placeholder="" value="" required> 
                                    <div class="invalid-feedback"> <!-- non so esattamente come funzioni ma per il momento ce lo lascio -->
                                        Il titolo inserito non è valido.
                                    </div>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-lg-12">
                                    <label class="h5" for="questionDescription">Descrizione domanda (facoltativo)</label>
                                    <textarea rows="3" class="form-control" id="questionDescription" placeholder="Dai maggiori informazioni all'utente per dargli modo di rispondere nella maniera pi&#250; consapevole" value=""></textarea>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12">
                                    <div>
                                        <label class="checkbox mb-3">
                                            <input type="checkbox" value="obbligatory"/>
                                            <span class="warning"></span>
                                        </label>
                                        &nbspRisposta obbligatoria
                                    </div>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-lg-12 mb-3">
                                    <p class="h5">Opzioni risposta</p>
                                    <div class="tab-wrap">
                                        <input type="radio" id="openShort" name="tabGroup" class="tab" checked>
                                        <label for="openShort">Domanda aperta - breve</label>

                                        <input type="radio" id="openLong" name="tabGroup" class="tab">
                                        <label for="openLong">Domanda aperta - lunga</label>
                                        
                                        <input type="radio" id="openNumber" name="tabGroup" class="tab">
                                        <label for="openNumber">Domanda aperta - numero</label>
                                        
                                        <input type="radio" id="openDate" name="tabGroup" class="tab">
                                        <label for="openDate">Domanda aperta - data</label>
                                        
                                        <input type="radio" id="closeSingle" name="tabGroup" class="tab">
                                        <label for="closeSingle">Scelta singola</label>
                                        
                                        <input type="radio" id="closeMultiple" name="tabGroup" class="tab">
                                        <label for="closeMultiple">Scelta multipla</label>

                                        <div class="tab__content">
                                            <div class="row mt-1 mb-1">
                                                <div class="col-lg-12">
                                                <textarea rows="1" class="form-control" placeholder="Nella risposta breve, l'utente pu&#242; rispondere con un massimo di X caratteri" style="resize: none"></textarea>
                                                </div>
                                            </div>
                                            <div class="row mt-2">
                                                <div class="col-lg-4">
                                                    <p><b>Numero minimo di caratteri</b> (opzionale)</p>
                                                </div>
                                            </div>
                                            <div class="row mb-1">
                                                <div class="col-lg-2 col-md-3 col-sm-4 col-4">
                                                    <input type="number" class="form-control" id="openShortConstraint">
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <div class="tab__content">
                                            <div class="row mt-1 mb-1">
                                                <div class="col-lg-12">
                                                <textarea rows="3" class="form-control" placeholder="Nella risposta lunga, l'utente pu&#242; rispondere con un massimo di X+Y caratteri" style="resize: none"></textarea>
                                                </div>
                                            </div>
                                            <div class="row mt-2">
                                                <div class="col-lg-4">
                                                    <p><b>Numero minimo di caratteri</b> (opzionale)</p>
                                                </div>
                                            </div>
                                            <div class="row mb-1">
                                                <div class="col-lg-2 col-md-3 col-sm-4 col-4">
                                                    <input type="number" class="form-control" id="openLongConstraint">
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <div class="tab__content">
                                            <div class="row mt-1 mb-1">
                                                <div class="col-lg-2 col-md-3 col-sm-4 col-4">
                                                <input type="number" class="form-control" placeholder="Numero">
                                                </div>
                                            </div>
                                            <div class="row mt-2">
                                                <div class="col-lg-4">
                                                    <p><b>Range di valori</b> (opzionali)</p>
                                                </div>
                                            </div>
                                            <div class="row mb-1">
                                                <div class="col-lg-2 col-md-3 col-sm-4 col-4">
                                                    <input type="number" class="form-control" id="openNumberConstraintMin" placeholder="minimo">
                                                </div>
                                                <div class="col-lg-2 col-md-3 col-sm-4 col-4">
                                                    <input type="number" class="form-control" id="openNumberConstraintMax" placeholder="massimo">
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <div class="tab__content">
                                            <div class="row mt-1 mb-1">
                                                <div class="col-lg-3 col-md-4 col-sm-5 col-5">
                                                <input type="date" class="form-control">
                                                </div>
                                            </div>
                                            <div class="row mt-2">
                                                <div class="col-lg-4">
                                                    <p><b>Range di date</b> (opzionali)</p>
                                                </div>
                                            </div>
                                            <div class="row mb-1">
                                                <div class="col-lg-3 col-md-4 col-sm-5 col-5">
                                                    minimo: 
                                                    <input type="date" class="form-control" id="openDateConstraintMin">
                                                </div>
                                                <div class="col-lg-3 col-md-4 col-sm-5 col-5">
                                                    massimo: 
                                                    <input type="date" class="form-control" id="openDateConstraintMax">
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <div class="tab__content">
                                            <div class="row mt-1 mb-1">
                                                <div class="col-lg-3">
                                                    <label for="option1">Opzione 1</label>
                                                    <input type="text" class="form-control" id="option1" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option2">Opzione 2</label>
                                                    <input type="text" class="form-control" id="option2" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option3">Opzione 3</label>
                                                    <input type="text" class="form-control" id="option3" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option4">Opzione 4</label>
                                                    <input type="text" class="form-control" id="option4" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                            </div>
                                            <div class="row mt-1 mb-1">
                                                <div class="col-lg-3">
                                                    <label for="option5">Opzione 5</label>
                                                    <input type="text" class="form-control" id="option5" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option6">Opzione 6</label>
                                                    <input type="text" class="form-control" id="option6" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option7">Opzione 7</label>
                                                    <input type="text" class="form-control" id="option7" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option8">Opzione 8</label>
                                                    <input type="text" class="form-control" id="option8" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                            </div>
                                            <div class="row mt-1 mb-1">
                                                <div class="col-lg-3">
                                                    <label for="option9">Opzione 9</label>
                                                    <input type="text" class="form-control" id="option9" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option10">Opzione 10</label>
                                                    <input type="text" class="form-control" id="option10" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option11">Opzione 11</label>
                                                    <input type="text" class="form-control" id="option11" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option12">Opzione 12</label>
                                                    <input type="text" class="form-control" id="option12" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <div class="tab__content">
                                            <div class="row mt-1 mb-1">
                                                <div class="col-lg-3">
                                                    <label for="option1m">Opzione 1</label>
                                                    <input type="text" class="form-control" id="option1m" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option2m">Opzione 2</label>
                                                    <input type="text" class="form-control" id="option2m" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option3m">Opzione 3</label>
                                                    <input type="text" class="form-control" id="option3m" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option4m">Opzione 4</label>
                                                    <input type="text" class="form-control" id="option4m" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                            </div>
                                            <div class="row mt-1 mb-1">
                                                <div class="col-lg-3">
                                                    <label for="option5m">Opzione 5</label>
                                                    <input type="text" class="form-control" id="option5m" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option6m">Opzione 6</label>
                                                    <input type="text" class="form-control" id="option6m" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option7m">Opzione 7</label>
                                                    <input type="text" class="form-control" id="option7m" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option8m">Opzione 8</label>
                                                    <input type="text" class="form-control" id="option8m" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                            </div>
                                            <div class="row mt-1 mb-1">
                                                <div class="col-lg-3">
                                                    <label for="option9m">Opzione 9</label>
                                                    <input type="text" class="form-control" id="option9m" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option10m">Opzione 10</label>
                                                    <input type="text" class="form-control" id="option10m" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option11m">Opzione 11</label>
                                                    <input type="text" class="form-control" id="option11m" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option12m">Opzione 12</label>
                                                    <input type="text" class="form-control" id="option12m" placehorder="Opzione" onkeyup="success()">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div> 
                            <div class="row mt-3 custom-margin-bottom">
                                <#if noPrev??>
                                    <div class="col-lg-4 mb-2 custom-left">
                                        <button name="prevQuestion" value="prevQuestionButton" class="btn btn-lg btn-warning custom-block" type="submit" disabled>
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-left" viewBox="0 0 16 16">
                                                <path fill-rule="evenodd" d="M12.5 15a.5.5 0 0 1-.5-.5v-13a.5.5 0 0 1 1 0v13a.5.5 0 0 1-.5.5zM10 8a.5.5 0 0 1-.5.5H3.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L3.707 7.5H9.5a.5.5 0 0 1 .5.5z"/>
                                            </svg>
                                            Domanda precedente
                                        </button>
                                    </div>
                                <#else>
                                    <div class="col-lg-4 mb-2 custom-left">
                                        <button name="prevQuestion" value="prevQuestionButton" class="btn btn-lg btn-warning custom-block" type="submit" >
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-left" viewBox="0 0 16 16">
                                                <path fill-rule="evenodd" d="M12.5 15a.5.5 0 0 1-.5-.5v-13a.5.5 0 0 1 1 0v13a.5.5 0 0 1-.5.5zM10 8a.5.5 0 0 1-.5.5H3.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L3.707 7.5H9.5a.5.5 0 0 1 .5.5z"/>
                                            </svg>
                                            Domanda precedente
                                        </button>
                                    </div>
                                </#if>
                                <#if noConf??>
                                    <div class="col-lg-4 mb-2 custom-center">
                                        <button type="submit" name="confirm" value="confirmButton" class="btn btn-lg btn-warning custom-block" disabled>
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-left" viewBox="0 0 16 16">
                                                <path d="M3 0h10a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2v-1h1v1a1 1 0 0 0 1 1h10a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H3a1 1 0 0 0-1 1v1H1V2a2 2 0 0 1 2-2z"/>
                                                <path d="M1 5v-.5a.5.5 0 0 1 1 0V5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1zm0 3v-.5a.5.5 0 0 1 1 0V8h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1zm0 3v-.5a.5.5 0 0 1 1 0v.5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1z"/>
                                                <path fill-rule="evenodd" d="M8 11a.5.5 0 0 0 .5-.5V6.707l1.146 1.147a.5.5 0 0 0 .708-.708l-2-2a.5.5 0 0 0-.708 0l-2 2a.5.5 0 1 0 .708.708L7.5 6.707V10.5a.5.5 0 0 0 .5.5z"/>                                    
                                            </svg>
                                            Vai alla conferma
                                        </button>
                                    </div>
                                <#else>
                                    <div class="col-lg-4 mb-2 custom-center">
                                        <button type="submit" name="confirm" value="confirmButton" class="btn btn-lg btn-warning custom-block">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-left" viewBox="0 0 16 16">
                                                <path d="M3 0h10a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2v-1h1v1a1 1 0 0 0 1 1h10a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H3a1 1 0 0 0-1 1v1H1V2a2 2 0 0 1 2-2z"/>
                                                <path d="M1 5v-.5a.5.5 0 0 1 1 0V5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1zm0 3v-.5a.5.5 0 0 1 1 0V8h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1zm0 3v-.5a.5.5 0 0 1 1 0v.5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1z"/>
                                                <path fill-rule="evenodd" d="M8 11a.5.5 0 0 0 .5-.5V6.707l1.146 1.147a.5.5 0 0 0 .708-.708l-2-2a.5.5 0 0 0-.708 0l-2 2a.5.5 0 1 0 .708.708L7.5 6.707V10.5a.5.5 0 0 0 .5.5z"/>                                    
                                            </svg>
                                            Vai alla conferma
                                        </button>
                                    </div>
                                </#if>
                                <div class="col-lg-4 mb-2 custom-right">
                                    <button type="submit" name="nextQuestion" value="nextQuestionButton" class="btn btn-lg btn-warning custom-block">
                                        Domanda successiva
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-left" viewBox="0 0 16 16">
                                            <path fill-rule="evenodd" d="M6 8a.5.5 0 0 0 .5.5h5.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L12.293 7.5H6.5A.5.5 0 0 0 6 8zm-2.5 7a.5.5 0 0 1-.5-.5v-13a.5.5 0 0 1 1 0v13a.5.5 0 0 1-.5.5z"/>                                    
                                        </svg>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
            <@macros.footer />
            <script> //aggiustare codice javascipt per casi errore (disabled rosso + alert)
                function disable(){
                    document.getElementById('option2').disabled = true; 
                    document.getElementById('option3').disabled = true; 
                    document.getElementById('option4').disabled = true; 
                    document.getElementById('option5').disabled = true; 
                    document.getElementById('option6').disabled = true; 
                    document.getElementById('option7').disabled = true; 
                    document.getElementById('option8').disabled = true; 
                    document.getElementById('option9').disabled = true; 
                    document.getElementById('option10').disabled = true; 
                    document.getElementById('option11').disabled = true; 
                    document.getElementById('option12').disabled = true; 
                    document.getElementById('option2m').disabled = true; 
                    document.getElementById('option3m').disabled = true; 
                    document.getElementById('option4m').disabled = true; 
                    document.getElementById('option5m').disabled = true; 
                    document.getElementById('option6m').disabled = true; 
                    document.getElementById('option7m').disabled = true; 
                    document.getElementById('option8m').disabled = true; 
                    document.getElementById('option9m').disabled = true; 
                    document.getElementById('option10m').disabled = true; 
                    document.getElementById('option11m').disabled = true; 
                    document.getElementById('option12m').disabled = true; 
                }
                    
                function success() {
                        if(document.getElementById("option1").value==="") { 
                           document.getElementById('option2').disabled = true; 
                        } else { 
                           document.getElementById('option2').disabled = false;
                        }
                        if(document.getElementById("option2").value==="") { 
                           document.getElementById('option3').disabled = true; 
                        } else { 
                           document.getElementById('option3').disabled = false;
                        }
                        if(document.getElementById("option3").value==="") { 
                           document.getElementById('option4').disabled = true; 
                        } else { 
                           document.getElementById('option4').disabled = false;
                        }
                        if(document.getElementById("option4").value==="") { 
                           document.getElementById('option5').disabled = true; 
                        } else { 
                           document.getElementById('option5').disabled = false;
                        }
                        if(document.getElementById("option5").value==="") { 
                           document.getElementById('option6').disabled = true; 
                        } else { 
                           document.getElementById('option6').disabled = false;
                        }
                        if(document.getElementById("option6").value==="") { 
                           document.getElementById('option7').disabled = true; 
                        } else { 
                           document.getElementById('option7').disabled = false;
                        }
                        if(document.getElementById("option7").value==="") { 
                           document.getElementById('option8').disabled = true; 
                        } else { 
                           document.getElementById('option8').disabled = false;
                        }
                        if(document.getElementById("option8").value==="") { 
                           document.getElementById('option9').disabled = true; 
                        } else { 
                           document.getElementById('option9').disabled = false;
                        }
                        if(document.getElementById("option9").value==="") { 
                           document.getElementById('option10').disabled = true; 
                        } else { 
                           document.getElementById('option10').disabled = false;
                        }
                        if(document.getElementById("option10").value==="") { 
                           document.getElementById('option11').disabled = true; 
                        } else { 
                           document.getElementById('option11').disabled = false;
                        }
                        if(document.getElementById("option11").value==="") { 
                           document.getElementById('option12').disabled = true; 
                        } else { 
                           document.getElementById('option12').disabled = false;
                        }
                        if(document.getElementById("option1m").value==="") { 
                           document.getElementById('option2m').disabled = true; 
                        } else { 
                           document.getElementById('option2m').disabled = false;
                        }
                        if(document.getElementById("option2m").value==="") { 
                           document.getElementById('option3m').disabled = true; 
                        } else { 
                           document.getElementById('option3m').disabled = false;
                        }
                        if(document.getElementById("option3m").value==="") { 
                           document.getElementById('option4m').disabled = true; 
                        } else { 
                           document.getElementById('option4m').disabled = false;
                        }
                        if(document.getElementById("option4m").value==="") { 
                           document.getElementById('option5m').disabled = true; 
                        } else { 
                           document.getElementById('option5m').disabled = false;
                        }
                        if(document.getElementById("option5m").value==="") { 
                           document.getElementById('option6m').disabled = true; 
                        } else { 
                           document.getElementById('option6m').disabled = false;
                        }
                        if(document.getElementById("option6m").value==="") { 
                           document.getElementById('option7m').disabled = true; 
                        } else { 
                           document.getElementById('option7m').disabled = false;
                        }
                        if(document.getElementById("option7m").value==="") { 
                           document.getElementById('option8m').disabled = true; 
                        } else { 
                           document.getElementById('option8m').disabled = false;
                        }
                        if(document.getElementById("option8m").value==="") { 
                           document.getElementById('option9m').disabled = true; 
                        } else { 
                           document.getElementById('option9m').disabled = false;
                        }
                        if(document.getElementById("option9m").value==="") { 
                           document.getElementById('option10m').disabled = true; 
                        } else { 
                           document.getElementById('option10m').disabled = false;
                        }
                        if(document.getElementById("option10m").value==="") { 
                           document.getElementById('option11m').disabled = true; 
                        } else { 
                           document.getElementById('option11m').disabled = false;
                        }
                        if(document.getElementById("option11m").value==="") { 
                           document.getElementById('option12m').disabled = true; 
                        } else { 
                           document.getElementById('option12m').disabled = false;
                        }
                    }
            </script>
            <@macros.script />
        </div>
    </body>
</html>
