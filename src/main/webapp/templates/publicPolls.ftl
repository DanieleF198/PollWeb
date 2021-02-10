<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="Quack, Duck, Poll!">
<html lang="it">
    <head>
        <title>${title}</title>
        <meta charset="${charset}">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <@macros.style imagePath="images/favicon.ico" stylePath="css/style.css" bootstrapPath="css/bootstrap.css"/>

        <@macros.carouselOvverridingStyle />
    </head>
    <body class="bg-light">
        <div class="d-flex flex-column min-vh-100">
            <@macros.header imagePath="images/logoDDP.png"/>
            <main class="flex-fill">
                <!-- Search bar -->
                <div class="container-fluid">
                    <div class="header-margin"></div>
                    <div class="container">
                            <div class="row">
                                <div class="col-lg-12">
                                <h1 class="h3 mb-3 font-weight-normal text-center">Cerca un sondaggio di tuo interesse!</h1>
                                </div>
                            </div>
                        
                        <div class="row">
                            <div class="input-group">
                                <div class="col-lg-10">
                            <form>
                                <input type="text" name="search-sondaggio" class="form-control" aria-label="Text input with dropdown button" style="border-radius: 1em 0 0 1em !important; width: 102% !important;">
                            </form>
                                </div>
                                
                                <div class="input-group-append">
                                    <div class="dropdown">
                                        <#if filtro?? && filtro=="popolari">
                                        <button class="btn btn-warning dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" style="width: 150px; border-radius: 0 1em 1em 0 !important;">
                                            Popolari
                                        </#if>
                                            
                                        <#if filtro?? && filtro=="recenti">
                                        <button class="btn btn-warning dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" style="width: 150px; border-radius: 0 1em 1em 0 !important;">
                                            Recenti
                                        </#if>
                                            
                                        <#if filtro?? && filtro=="menoRecenti">
                                        <button class="btn btn-warning dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" style="width: 150px; border-radius: 0 1em 1em 0 !important;">
                                            Meno Recenti
                                        </#if>
                                            
                                        <#if filtro?? && filtro=="no">
                                        <button class="btn btn-warning dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" style="width: 150px; border-radius: 0 1em 1em 0 !important;">
                                            Filtro
                                        </#if>
                                            
                                            <span class="caret"></span>
                                        </button>
                                        <form>
                                            <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
                                                <li><button class="dropdown-item" name="btnPopolari" type="submit">Popolari</button></li>
                                                <li><button class="dropdown-item" name="btnRecenti" type="submit">Pi&#249 recenti</button></li>
                                                <li><button class="dropdown-item" name="btnMenoRecenti" type="submit">Meno recenti</button></li>
                                                <li><div class="dropdown-divider"></div></li>
                                                <li><button class="dropdown-item" name="btnAnnulla" type="submit">Annulla filtro</button></li>
                                            </ul>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>    
                    </div>
                </div>
                <!-- End search bar -->
                <div class="container-fluid mt-5">
                    <div class="container">
                        <div class="border-top"></div>
                    </div>
                </div>
                <!-- cards -->
                <div class="javascriptVisibility">
                    <div class="container-fluid mb-5 mt-5">
                        <div class="container">
                            <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
                                <div class="carousel-inner">


                                    <#assign c = 1>
                                    <#list sondaggi as sondaggio>  

                                        <#if c%9 == 1>

                                            <#if c == 1>
                                                <div class="carousel-item active">
                                            <#else>
                                                <div class="carousel-item">
                                            </#if>

                                                    <div class="card-deck mr-1 ml-1" >
                                                        <div class="col-lg-4 mb-3 variable-height">
                                                            <div class="card h-100">
                                                                <div class="card-body">
                                                                    <#if sondaggio.getTitolo()??>
                                                                        <h5 class="card-title">${sondaggio.getTitolo()}</h5>
                                                                    <#else>
                                                                        <h5 class="card-title">No title</h5>
                                                                    </#if>
                                                                    <#if sondaggio.getTestoApertura()??>
                                                                        <p class="card-text">${sondaggio.getTestoApertura()}</p>
                                                                    <#else>
                                                                        <h5 class="card-text">No descrizione</h5>
                                                                    </#if>
                                                                </div>
                                                                <div class="card-footer bg-warning">
                                                                    <#if sondaggio.getCreazione()??>
                                                                        <small class="text-black">${sondaggio.getCreazione()}</small>
                                                                    <#else>
                                                                        <h5 class="card-black"></h5>
                                                                    </#if>
                                                                </div>
                                                            </div>
                                                        </div>

                                        </#if>
                                        <#if c%9 != 1 && c%9 != 0>

                                                        <div class="col-lg-4 mb-3 variable-height">
                                                            <div class="card h-100">
                                                                <div class="card-body">
                                                                    <#if sondaggio.getTitolo()??>
                                                                        <h5 class="card-title">${sondaggio.getTitolo()}</h5>
                                                                    <#else>
                                                                        <h5 class="card-title">No title</h5>
                                                                    </#if>
                                                                    <#if sondaggio.getTestoApertura()??>
                                                                        <p class="card-text">${sondaggio.getTestoApertura()}</p>
                                                                    <#else>
                                                                        <h5 class="card-text">No descrizione</h5>
                                                                    </#if>
                                                                </div>
                                                                <div class="card-footer bg-warning">
                                                                    <#if sondaggio.getCreazione()??>
                                                                        <small class="text-black">${sondaggio.getCreazione()}</small>
                                                                    <#else>
                                                                        <h5 class="card-black"></h5>
                                                                    </#if>
                                                                </div>
                                                            </div>
                                                        </div>


                                        </#if>
                                        <#if c%9 == 0>   

                                                        <div class="col-lg-4 mb-3 variable-height">
                                                            <div class="card h-100">
                                                                <div class="card-body">
                                                                    <#if sondaggio.getTitolo()??>
                                                                        <h5 class="card-title">${sondaggio.getTitolo()}</h5>
                                                                    <#else>
                                                                        <h5 class="card-title">No title</h5>
                                                                    </#if>
                                                                    <#if sondaggio.getTestoApertura()??>
                                                                        <p class="card-text">${sondaggio.getTestoApertura()}</p>
                                                                    <#else>
                                                                        <h5 class="card-text">No descrizione</h5>
                                                                    </#if>
                                                                </div>
                                                                <div class="card-footer bg-warning">
                                                                    <#if sondaggio.getCreazione()??>
                                                                        <small class="text-black">${sondaggio.getCreazione()}</small>
                                                                    <#else>
                                                                        <h5 class="card-black"></h5>
                                                                    </#if>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>


                                        </#if>
                                        <#assign c = c + 1>
                                    </#list>
                                    <#if c%9 != 1> 

                                                    </div>
                                                </div>
                                    </#if>


                                </div>
                                <a class="carousel-control-prev text-dark" href="#carouselExampleControls" role="button" data-slide="prev">
                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                    <span class="sr-only">Previous</span>
                                </a>
                                <a class="carousel-control-next text-dark" href="#carouselExampleControls" role="button" data-slide="next">
                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
        
                <div class="javascriptNoVisibility">
                    <div class="container-fluid mb-5 mt-5">
                        <div class="container">
                            <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
                                <div class="carousel-inner">
                                    <div class="carousel-item active">
                                        <div class="card-deck mr-1 ml-1" >

                                    <#list sondaggi as sondaggio>  


                                                   
                                            <div class="col-lg-4 mb-3 variable-height">
                                                <div class="card h-100">
                                                    <div class="card-body">
                                                        <#if sondaggio.getTitolo()??>
                                                            <h5 class="card-title">${sondaggio.getTitolo()}</h5>
                                                        <#else>
                                                            <h5 class="card-title">No title</h5>
                                                        </#if>
                                                        <#if sondaggio.getTestoApertura()??>
                                                            <p class="card-text">${sondaggio.getTestoApertura()}</p>
                                                        <#else>
                                                            <h5 class="card-title">No descrizione</h5>
                                                        </#if>
                                                    </div>
                                                    <div class="card-footer bg-warning">
                                                        <#if sondaggio.getCreazione()??>
                                                            <small class="text-black">${sondaggio.getCreazione()}</small>
                                                        <#else>
                                                            <h5 class="card-title"></h5>
                                                        </#if>
                                                    </div>
                                                </div>
                                            </div>

                                    </#list>
                                    

                                        </div>
                                    </div>
                                    


                                </div>
                                <a class="carousel-control-prev text-dark" href="#carouselExampleControls" role="button" data-slide="prev">
                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                    <span class="sr-only">Previous</span>
                                </a>
                                <a class="carousel-control-next text-dark" href="#carouselExampleControls" role="button" data-slide="next">
                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- End cards -->
                
                
                
            </main>
            <@macros.footer />
            <@macros.script />
            <script>
                $(".dropdown-menu li a").click(function(){
                    if($(this).text() == 'Annulla filtro'){
                            $(this).parents(".dropdown").find('.btn').html('Filtro  <span class="caret"></span>');
                            $(this).parents(".dropdown").find('.btn').val('Filtro');
                        }
                    else{
                            $(this).parents(".dropdown").find('.btn').html($(this).text() + ' <span class="caret"></span>');
                            $(this).parents(".dropdown").find('.btn').val($(this).data('value'));
                        }
                });
            </script>
        </div>
    </body>
</html>

