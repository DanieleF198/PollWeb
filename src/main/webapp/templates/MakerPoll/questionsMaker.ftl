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
                        <form id="questionsMaker" method="post" action="questionsMaker" class="needs-validation" novalidate>
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
                            <div class="row mb-2">
                                <div class="col-lg-12">
                                    <#if numeroDomanda??>
                                        <p class="h5 font-weight-normal">domanda #${numeroDomanda}</h1>
                                    </#if>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-lg-12">
                                    <label class="h5" for="questionTitle">Titolo domanda</label>
                                    <#if titleQuestion?? && titleQuestion!="">
                                        <input type="text" name="questionTitle" class="form-control" id="questionTitle" placeholder=""  maxlength="128" value="${titleQuestion}" required>
                                    <#else>
                                        <input type="text" name="questionTitle" class="form-control" id="questionTitle" placeholder=""  maxlength="128" value="" required> 
                                    </#if>
                                    <div class="invalid-feedback"> <!-- non so esattamente come funzioni ma per il momento ce lo lascio -->
                                        Il titolo inserito non è valido.
                                    </div>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-lg-12">
                                    <label class="h5" for="questionDescription">Descrizione domanda (facoltativo)</label>
                                    <#if description?? && description!="">
                                        <textarea rows="3" name="questionDescription" class="form-control" id="questionDescription" placeholder="Dai maggiori informazioni all'utente per dargli modo di rispondere nella maniera pi&#250; consapevole" maxlength="245">${description}</textarea>
                                    <#else>
                                        <textarea rows="3" name="questionDescription" class="form-control" id="questionDescription" placeholder="Dai maggiori informazioni all'utente per dargli modo di rispondere nella maniera pi&#250; consapevole" maxlength="245"></textarea>
                                    </#if>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12">
                                    <div>
                                        <label class="checkbox mb-3">
                                            <#if obbligatory??>
                                                <#if obbligatory=="yes">
                                                    <input type="checkbox" name="questionObbligatory" value="obbligatory" checked/>
                                                <#else>
                                                    <input type="checkbox" name="questionObbligatory" value="obbligatory"/>
                                                </#if>
                                            <#else>
                                                <input type="checkbox" name="questionObbligatory" value="obbligatory"/>
                                            </#if>
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
                                        <#if checked??>
                                            <#if checked=="openShort">
                                                <input type="radio" value="openShort" id="openShort" name="tabGroup" class="tab" checked>
                                            <#else>
                                                <input type="radio" value="openShort" id="openShort" name="tabGroup" class="tab">
                                            </#if>
                                            <label for="openShort">Domanda aperta - breve</label>
                                            <#if checked=="openLong">
                                                <input type="radio" value="openLong" id="openLong" name="tabGroup" class="tab" checked>
                                            <#else>
                                                <input type="radio" value="openLong" id="openLong" name="tabGroup" class="tab">
                                            </#if>
                                            <label for="openLong">Domanda aperta - lunga</label>
                                            <#if checked=="openNumber">
                                                <input type="radio" value="openNumber" id="openNumber" name="tabGroup" class="tab" checked>
                                            <#else>
                                                <input type="radio" value="openNumber" id="openNumber" name="tabGroup" class="tab">
                                            </#if>
                                            <label for="openNumber">Domanda aperta - numero</label>
                                            <#if checked=="openDate">
                                                <input type="radio" value="openDate" id="openDate" name="tabGroup" class="tab" checked>
                                            <#else>
                                                <input type="radio" value="openDate" id="openDate" name="tabGroup" class="tab">
                                            </#if>
                                            <label for="openDate">Domanda aperta - data</label>
                                            <#if checked=="closeSingle">
                                                <input type="radio" value="closeSingle" id="closeSingle" name="tabGroup" class="tab" checked>
                                            <#else>
                                                <input type="radio" value="closeSingle" id="closeSingle" name="tabGroup" class="tab">
                                            </#if>
                                            <label for="closeSingle">Scelta singola</label>
                                            <#if checked=="closeMultiple">
                                                <input type="radio" value="closeMultiple" id="closeMultiple" name="tabGroup" class="tab" checked>
                                            <#else>
                                                <input type="radio" value="closeMultiple" id="closeMultiple" name="tabGroup" class="tab">
                                            </#if>
                                            <label for="closeMultiple">Scelta multipla</label>
                                        <#else>
                                            <input type="radio" value="openShort" id="openShort" name="tabGroup" class="tab" checked>
                                            <label for="openShort">Domanda aperta - breve</label>

                                            <input type="radio" value="openLong" id="openLong" name="tabGroup" class="tab">
                                            <label for="openLong">Domanda aperta - lunga</label>

                                            <input type="radio" value="openNumber" id="openNumber" name="tabGroup" class="tab">
                                            <label for="openNumber">Domanda aperta - numero</label>

                                            <input type="radio" value="openDate" id="openDate" name="tabGroup" class="tab">
                                            <label for="openDate">Domanda aperta - data</label>

                                            <input type="radio" value="closeSingle" id="closeSingle" name="tabGroup" class="tab">
                                            <label for="closeSingle">Scelta singola</label>

                                            <input type="radio" value="closeMultiple" id="closeMultiple" name="tabGroup" class="tab">
                                            <label for="closeMultiple">Scelta multipla</label>
                                        </#if>
                                        <div class="tab__content">
                                            <div class="row mt-1 mb-1">
                                                <div class="col-lg-12">
                                                <textarea rows="1" class="form-control" placeholder="Nella risposta breve, l'utente pu&#242; rispondere con un massimo di 128 caratteri" style="resize: none" max="128"></textarea>
                                                </div>
                                            </div>
                                            <div class="row mt-2">
                                                <div class="col-lg-6">
                                                    <p><b>Numero minimo di caratteri</b> (opzionale - massimo valore: 64)</p>
                                                </div>
                                            </div>
                                            <div class="row mb-1">
                                                <div class="col-lg-2 col-md-3 col-sm-4 col-4">
                                                    <#if openShortConstraint?? && openShortConstraint!="">
                                                        <input name="openShortConstraint" type="number" class="form-control" id="openShortConstraint" value="${openShortConstraint}">
                                                    <#else>
                                                        <input name="openShortConstraint" type="number" class="form-control" id="openShortConstraint">
                                                    </#if>
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <div class="tab__content">
                                            <div class="row mt-1 mb-1">
                                                <div class="col-lg-12">
                                                <textarea rows="3" class="form-control" placeholder="Nella risposta lunga, l'utente pu&#242; rispondere con un massimo di 512 caratteri" style="resize: none" max="512"></textarea>
                                                </div>
                                            </div>
                                            <div class="row mt-2">
                                                <div class="col-lg-6">
                                                    <p><b>Numero minimo di caratteri</b> (opzionale - massimo valore: 256)</p>
                                                </div>
                                            </div>
                                            <div class="row mb-1">
                                                <div class="col-lg-2 col-md-3 col-sm-4 col-4">
                                                    <#if openLongConstraint?? && openLongConstraint!="">
                                                        <input name="openLongConstraint" type="number" class="form-control" id="openLongConstraint" value="${openLongConstraint}">
                                                    <#else>
                                                        <input name="openLongConstraint" type="number" class="form-control" id="openLongConstraint">
                                                    </#if>
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
                                                    <#if openNumberConstraintMin?? && openNumberConstraintMin!="">
                                                        <input name="openNumberConstraintMin" type="number" class="form-control" id="openNumberConstraintMin" placeholder="minimo" value="${openNumberConstraintMin}">
                                                    <#else>
                                                        <input name="openNumberConstraintMin" type="number" class="form-control" id="openNumberConstraintMin" placeholder="minimo">
                                                    </#if>
                                                </div>
                                                <div class="col-lg-2 col-md-3 col-sm-4 col-4">
                                                    <#if openNumberConstraintMax?? && openNumberConstraintMax!="">
                                                        <input name="openNumberConstraintMax" type="number" class="form-control" id="openNumberConstraintMax" placeholder="massimo" value="${openNumberConstraintMax}">
                                                    <#else>
                                                        <input name="openNumberConstraintMax" type="number" class="form-control" id="openNumberConstraintMax" placeholder="massimo">
                                                    </#if>
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
                                                <div class="col-lg-9">
                                                    <p><b>Range di date</b> (opzionali - scrivere date complete altrimenti il vincolo non sar&#225 memorizzato)</p>
                                                </div>
                                            </div>
                                            <div class="row mb-1">
                                                <div class="col-lg-3 col-md-4 col-sm-5 col-5">
                                                    minimo:
                                                    <#if openDateConstraintMin?? && openDateConstraintMin!="">
                                                        <input name="openDateConstraintMin" type="date" class="form-control" id="openDateConstraintMin" value="${openDateConstraintMin}">
                                                    <#else>
                                                        <input name="openDateConstraintMin" type="date" class="form-control" id="openDateConstraintMin">
                                                    </#if>
                                                </div>
                                                <div class="col-lg-3 col-md-4 col-sm-5 col-5">
                                                    massimo: 
                                                    <#if openDateConstraintMax?? && openDateConstraintMax!="">
                                                        <input name="openDateConstraintMax" type="date" class="form-control" id="openDateConstraintMax" value="${openDateConstraintMax}">
                                                    <#else>
                                                        <input name="openDateConstraintMax" type="date" class="form-control" id="openDateConstraintMax">
                                                    </#if>
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <div class="tab__content">
                                            <div class="row mt-1 mb-1">
                                                <div class="col-lg-3">
                                                    <label for="option1">Opzione 1</label>
                                                    <#if option0?? && option0!="">
                                                        <input type="text" class="form-control" id="option1" name="option1" placehorder="Opzione" onkeyup="success()" value="${option0}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option1" name="option1" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option2">Opzione 2</label>
                                                    <#if option1?? && option1!="">
                                                        <input type="text" class="form-control" id="option2" name="option2" placehorder="Opzione" onkeyup="success()" value="${option1}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option2" name="option2" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option3">Opzione 3</label>
                                                    <#if option2?? && option2!="">
                                                        <input type="text" class="form-control" id="option3" name="option3" placehorder="Opzione" onkeyup="success()" value="${option2}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option3" name="option3" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option4">Opzione 4</label>
                                                    <#if option3?? && option3!="">
                                                        <input type="text" class="form-control" id="option4" name="option4" placehorder="Opzione" onkeyup="success()" value="${option3}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option4" name="option4" placehorder="Opzione" onkeyup="success()">
                                                    </#if>                                                
                                                </div>
                                            </div>
                                            <div class="row mt-1 mb-1">
                                                <div class="col-lg-3">
                                                    <label for="option5">Opzione 5</label>
                                                    <#if option4?? && option4!="">
                                                        <input type="text" class="form-control" id="option5" name="option5" placehorder="Opzione" onkeyup="success()" value="${option4}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option5" name="option5" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option6">Opzione 6</label>
                                                    <#if option5?? && option5!="">
                                                        <input type="text" class="form-control" id="option6" name="option6" placehorder="Opzione" onkeyup="success()" value="${option5}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option6" name="option6" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option7">Opzione 7</label>
                                                    <#if option6?? && option6!="">
                                                        <input type="text" class="form-control" id="option7" name="option7" placehorder="Opzione" onkeyup="success()" value="${option6}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option7" name="option7" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option8">Opzione 8</label>
                                                    <#if option7?? && option7!="">
                                                        <input type="text" class="form-control" id="option8" name="option8" placehorder="Opzione" onkeyup="success()" value="${option7}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option8" name="option8" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                            </div>
                                            <div class="row mt-1 mb-1">
                                                <div class="col-lg-3">
                                                    <label for="option9">Opzione 9</label>
                                                    <#if option8?? && option8!="">
                                                        <input type="text" class="form-control" id="option9" name="option9" placehorder="Opzione" onkeyup="success()" value="${option8}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option9" name="option9" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option10">Opzione 10</label>
                                                    <#if option9?? && option9!="">
                                                        <input type="text" class="form-control" id="option10" name="option10" placehorder="Opzione" onkeyup="success()" value="${option9}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option10" name="option10" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option11">Opzione 11</label>
                                                    <#if option10?? && option10!="">
                                                        <input type="text" class="form-control" id="option11" name="option11" placehorder="Opzione" onkeyup="success()" value="${option10}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option11" name="option11" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option12">Opzione 12</label>
                                                    <#if option11?? && option11!="">
                                                        <input type="text" class="form-control" id="option12" name="option12" placehorder="Opzione" onkeyup="success()" value="${option11}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option12" name="option12" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <div class="tab__content">
                                            <div class="row mt-1 mb-1">
                                                <div class="col-lg-3">
                                                    <label for="option1m">Opzione 1</label>
                                                    <#if option0m?? && option0m!="">
                                                        <input type="text" class="form-control" id="option1m" name="option1m" placehorder="Opzione" onkeyup="success()" value="${option0m}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option1m" name="option1m" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option2m">Opzione 2</label>
                                                    <#if option1m?? && option1m!="">
                                                        <input type="text" class="form-control" id="option2m" name="option2m" placehorder="Opzione" onkeyup="success()" value="${option1m}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option2m" name="option2m" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option3m">Opzione 3</label>
                                                    <#if option2m?? && option2m!="">
                                                        <input type="text" class="form-control" id="option3m" name="option3m" placehorder="Opzione" onkeyup="success()" value="${option2m}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option3m" name="option3m" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option4m">Opzione 4</label>
                                                    <#if option3m?? && option3m!="">
                                                        <input type="text" class="form-control" id="option4m" name="option4m" placehorder="Opzione" onkeyup="success()" value="${option3m}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option4m" name="option4m" placehorder="Opzione" onkeyup="success()">
                                                    </#if>                                                
                                                </div>
                                            </div>
                                            <div class="row mt-1 mb-1">
                                                <div class="col-lg-3">
                                                    <label for="option5m">Opzione 5</label>
                                                    <#if option4m?? && option4m!="">
                                                        <input type="text" class="form-control" id="option5m" name="option5m" placehorder="Opzione" onkeyup="success()" value="${option4m}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option5m" name="option5m" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option6m">Opzione 6</label>
                                                    <#if option5m?? && option5m!="">
                                                        <input type="text" class="form-control" id="option6m" name="option6m" placehorder="Opzione" onkeyup="success()" value="${option5m}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option6m" name="option6m" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option7m">Opzione 7</label>
                                                    <#if option6m?? && option6m!="">
                                                        <input type="text" class="form-control" id="option7m" name="option7m" placehorder="Opzione" onkeyup="success()" value="${option6m}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option7m" name="option7m" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option8m">Opzione 8</label>
                                                    <#if option7m?? && option7m!="">
                                                        <input type="text" class="form-control" id="option8m" name="option8m" placehorder="Opzione" onkeyup="success()" value="${option7m}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option8m" name="option8m" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                            </div>
                                            <div class="row mt-1 mb-1">
                                                <div class="col-lg-3">
                                                    <label for="option9m">Opzione 9</label>
                                                    <#if option8m?? && option8m!="">
                                                        <input type="text" class="form-control" id="option9m" name="option9m" placehorder="Opzione" onkeyup="success()" value="${option8m}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option9m" name="option9m" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option10m">Opzione 10</label>
                                                    <#if option9m?? && option9m!="">
                                                        <input type="text" class="form-control" id="option10m" name="option10m" placehorder="Opzione" onkeyup="success()" value="${option9m}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option10m" name="option10m" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option11m">Opzione 11</label>
                                                    <#if option10m?? && option10m!="">
                                                        <input type="text" class="form-control" id="option11m" name="option11m" placehorder="Opzione" onkeyup="success()" value="${option10m}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option11m" name="option11m" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="option12m">Opzione 12</label>
                                                    <#if option11m?? && option11m!="">
                                                        <input type="text" class="form-control" id="option12m" name="option12m" placehorder="Opzione" onkeyup="success()" value="${option11m}">
                                                    <#else>
                                                        <input type="text" class="form-control" id="option12m" name="option12m" placehorder="Opzione" onkeyup="success()">
                                                    </#if>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <form id="returnFirstSection" action="firstSection" method="GET"></form>
                        <div class="row mt-3">
                            <#if noPrev??>
                                <div class="col-lg-6 mb-2 pr-1 pl-1 custom-left">
                                        <button name="prevQuestion" value="prevQuestionButton" class="btn btn-lg btn-warning btn-block" type="submit" form="questionsMaker" disabled>
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-left" viewBox="0 0 16 16">
                                            <path fill-rule="evenodd" d="M12.5 15a.5.5 0 0 1-.5-.5v-13a.5.5 0 0 1 1 0v13a.5.5 0 0 1-.5.5zM10 8a.5.5 0 0 1-.5.5H3.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L3.707 7.5H9.5a.5.5 0 0 1 .5.5z"/>
                                        </svg>
                                        Domanda precedente
                                    </button>
                                </div>
                            <#else>
                                <div class="col-lg-6 mb-2 pr-1 pl-1 custom-left">
                                        <button name="prevQuestion" value="prevQuestionButton" class="btn btn-lg btn-warning btn-block" type="submit" form="questionsMaker" >
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-left" viewBox="0 0 16 16">
                                            <path fill-rule="evenodd" d="M12.5 15a.5.5 0 0 1-.5-.5v-13a.5.5 0 0 1 1 0v13a.5.5 0 0 1-.5.5zM10 8a.5.5 0 0 1-.5.5H3.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L3.707 7.5H9.5a.5.5 0 0 1 .5.5z"/>
                                        </svg>
                                        Domanda precedente
                                    </button>
                                </div>
                            </#if>
                            <div class="col-lg-6 mb-2 pr-1 pl-1 custom-right">
                                    <button type="submit" name="nextQuestion" value="nextQuestionButton" class="btn btn-lg btn-warning btn-block" form="questionsMaker" >
                                    Domanda successiva
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-left" viewBox="0 0 16 16">
                                        <path fill-rule="evenodd" d="M6 8a.5.5 0 0 0 .5.5h5.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L12.293 7.5H6.5A.5.5 0 0 0 6 8zm-2.5 7a.5.5 0 0 1-.5-.5v-13a.5.5 0 0 1 1 0v13a.5.5 0 0 1-.5.5z"/>                                    
                                    </svg>
                                </button>
                            </div>
                        </div>
                        <div class="row custom-margin-bottom">
                            <div class="col-lg-6 mb-2 pr-1 pl-1 custom-left">
                                <button type="submit" name="returnInfo" value="returnInfoButton"  form="returnFirstSection" class="btn btn-lg btn-warning btn-block">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-text" viewBox="0 0 16 16">
                                        <path d="M5 4a.5.5 0 0 0 0 1h6a.5.5 0 0 0 0-1H5zm-.5 2.5A.5.5 0 0 1 5 6h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zM5 8a.5.5 0 0 0 0 1h6a.5.5 0 0 0 0-1H5zm0 2a.5.5 0 0 0 0 1h3a.5.5 0 0 0 0-1H5z"/>
                                        <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2zm10-1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1z"/>
                                    </svg>
                                    Ritorna alle informazioni generali
                                </button>
                            </div>
                            <div class="col-lg-6 mb-2 pr-1 pl-1 custom-right">
                                    <button type="submit" name="confirm" value="confirmButton" form="questionsMaker" class="btn btn-lg btn-warning btn-block">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-left" viewBox="0 0 16 16">
                                        <path d="M3 0h10a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2v-1h1v1a1 1 0 0 0 1 1h10a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H3a1 1 0 0 0-1 1v1H1V2a2 2 0 0 1 2-2z"/>
                                        <path d="M1 5v-.5a.5.5 0 0 1 1 0V5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1zm0 3v-.5a.5.5 0 0 1 1 0V8h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1zm0 3v-.5a.5.5 0 0 1 1 0v.5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1z"/>
                                        <path fill-rule="evenodd" d="M8 11a.5.5 0 0 0 .5-.5V6.707l1.146 1.147a.5.5 0 0 0 .708-.708l-2-2a.5.5 0 0 0-.708 0l-2 2a.5.5 0 1 0 .708.708L7.5 6.707V10.5a.5.5 0 0 0 .5.5z"/>                                    
                                    </svg>
                                    Vai alla conferma
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
            <@macros.footer />
            <!--NOTA: QuestionMaker funziona tranquillamente anche con javascript disabilitato, questo script è stato messo solo per una migliore esperienza d'uso-->
            <script>
                function disable(){
                    <#if option0?? && option0!="">
                        document.getElementById('option2').disabled = false;
                    <#else>
                        document.getElementById('option2').disabled = true; 
                    </#if>
                    <#if option1?? && option1!="">
                        document.getElementById('option3').disabled = false; 
                    <#else>
                        document.getElementById('option3').disabled = true; 
                    </#if>
                    <#if option2?? && option2!="">
                        document.getElementById('option4').disabled = false; 
                    <#else>
                        document.getElementById('option4').disabled = true; 
                    </#if>
                    <#if option3?? && option3!="">
                        document.getElementById('option5').disabled = false;
                    <#else>
                        document.getElementById('option5').disabled = true; 
                    </#if>
                    <#if option4?? && option4!="">
                        document.getElementById('option6').disabled = false; 
                    <#else>
                        document.getElementById('option6').disabled = true; 
                    </#if>
                    <#if option5?? && option5!="">
                        document.getElementById('option7').disabled = false; 
                    <#else>
                        document.getElementById('option7').disabled = true; 
                    </#if>
                    <#if option6?? && option6!="">
                        document.getElementById('option8').disabled = false;
                    <#else>
                        document.getElementById('option8').disabled = true; 
                    </#if>
                    <#if option7?? && option7!="">
                        document.getElementById('option9').disabled = false; 
                    <#else>
                        document.getElementById('option9').disabled = true; 
                    </#if>
                    <#if option8?? && option8!="">
                        document.getElementById('option10').disabled = false; 
                    <#else>
                        document.getElementById('option10').disabled = true; 
                    </#if>
                    <#if option9?? && option9!="">
                        document.getElementById('option11').disabled = false; 
                    <#else>
                        document.getElementById('option11').disabled = true; 
                    </#if>
                    <#if option10?? && option10!="">
                        document.getElementById('option12').disabled = false; 
                    <#else>
                        document.getElementById('option12').disabled = true; 
                    </#if>
                        
                    <#if option0m?? && option0m!="">
                        document.getElementById('option2m').disabled = false;
                    <#else>
                        document.getElementById('option2m').disabled = true; 
                    </#if>
                    <#if option1m?? && option1m!="">
                        document.getElementById('option3m').disabled = false; 
                    <#else>
                        document.getElementById('option3m').disabled = true; 
                    </#if>
                    <#if option2m?? && option2m!="">
                        document.getElementById('option4m').disabled = false; 
                    <#else>
                        document.getElementById('option4m').disabled = true; 
                    </#if>
                    <#if option3m?? && option3m!="">
                        document.getElementById('option5m').disabled = false;
                    <#else>
                        document.getElementById('option5m').disabled = true; 
                    </#if>
                    <#if option4m?? && option4m!="">
                        document.getElementById('option6m').disabled = false; 
                    <#else>
                        document.getElementById('option6m').disabled = true; 
                    </#if>
                    <#if option5m?? && option5m!="">
                        document.getElementById('option7m').disabled = false; 
                    <#else>
                        document.getElementById('option7m').disabled = true; 
                    </#if>
                    <#if option6m?? && option6m!="">
                        document.getElementById('option8m').disabled = false;
                    <#else>
                        document.getElementById('option8m').disabled = true; 
                    </#if>
                    <#if option7m?? && option7m!="">
                        document.getElementById('option9m').disabled = false; 
                    <#else>
                        document.getElementById('option9m').disabled = true; 
                    </#if>
                    <#if option8m?? && option8m!="">
                        document.getElementById('option10m').disabled = false; 
                    <#else>
                        document.getElementById('option10m').disabled = true; 
                    </#if>
                    <#if option9m?? && option9m!="">
                        document.getElementById('option11m').disabled = false; 
                    <#else>
                        document.getElementById('option11m').disabled = true; 
                    </#if>
                    <#if option10m?? && option10m!="">
                        document.getElementById('option12m').disabled = false; 
                    <#else>
                        document.getElementById('option12m').disabled = true; 
                    </#if>
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
