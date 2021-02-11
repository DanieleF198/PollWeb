<#macro style imagePath stylePath bootstrapPath>
    <!-- Style -->
        <link rel="stylesheet" type="text/css" href="${stylePath}">
        <link rel="stylesheet" type="text/css" href="${bootstrapPath}">
        <link rel="shortcut icon" href="${imagePath}">
    <!-- little piece that i need to manage javascript disabled -->
    <style>
        .javascriptNoVisibility{
            display: none;
        }
    </style>
    <noscript>
      <style>
        .javaScriptVisibility{
            display: none;
        }
        
        .javascriptNoVisibility{
            display: inline
        }
      </style>
    </noscript>
    <!-- End style -->
</#macro>
    
<#macro carouselOvverridingStyle>
    <!-- bootstrap carousel ovverriding style -->
    <style>
        .carousel-control-prev{
            margin-left: -100px
        }
        
        .carousel-control-next{
            margin-right: -100px
        }
        
        .variable-height{
                height: 230px    
        }
        
        .card-body{
            overflow: auto;
            
        }
        
        .card-body::-webkit-scrollbar {
            width: 0px;
            background: transparent; 
        }
            
        .space{
            background-color: white;
            height: 15px;
            z-index: 50;
        }
        @media screen and (max-width: 1200px){
            .carousel-control-prev {
                margin-left: -85px
            }
        
            .carousel-control-next {
                margin-right: -85px
            }
            
            .variable-height{
                height: 240px    
            }
        }
        
        @media screen and (max-width: 992px){
            .carousel-control-prev {
                margin-left: -80px
            }
        
            .carousel-control-next {
                margin-right: -80px
            }
            
            .variable-height{
                height: 170px    
            }
        }
        
        @media screen and (max-width: 768px){
            .carousel-control-prev {
                margin-left: -50px
            }
            
        
            .carousel-control-next {
                margin-right: -50px
            }
            
            .variable-height{
                height: 180px    
            }
        }
        
        @media screen and (max-width: 576px){
            .carousel-control-prev {
                margin-left: -50px
            }
        
            .carousel-control-next {
                margin-right: -50px
            }
            
            .variable-height{
                height: 200px    
            }
        }
        
        .carousel-control-prev-icon {
            background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23000' viewBox='0 0 8 8'%3E%3Cpath d='M5.25 0l-4 4 4 4 1.5-1.5-2.5-2.5 2.5-2.5-1.5-1.5z'/%3E%3C/svg%3E");
        }

        .carousel-control-next-icon {
            background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23000' viewBox='0 0 8 8'%3E%3Cpath d='M2.75 0l-1.5 1.5 2.5 2.5-2.5 2.5 1.5 1.5 4-4-4-4z'/%3E%3C/svg%3E");
        }
    </style>
    
    <noscript>
      <style>
        .carousel-control-next{
            display: none;
        }
        
        .carousel-control-prev{
            display: none;
        }
      </style>
    </noscript>
    
    <!-- Endbootstrap carousel ovverriding style -->
</#macro>

<#macro script>
    <!-- Script -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.datatables.net/1.10.23/js/jquery.dataTables.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.datatables.net/1.10.23/js/dataTables.bootstrap4.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
    <!-- End script -->
</#macro>
    
<#macro header imagePath>
    
    <style>
      #navbar-toggle-cbox {
        display: none;
      }
    </style>
    <noscript>
      <style>
        .dropdown:hover > .dropdown-menu {
          display: block;
        }

        label[for=navbar-toggle-cbox] {
          cursor: pointer;
        }

        #navbar-toggle-cbox:checked ~ .collapse {
          display: block;
        }
        
        .navbar-toggle{
            display: none;
        }
        
        @media screen and (max-width: 1200px){
            .navbar-toggle{
                display: block;
            }
        }

        #toggle-navbar {
          display: none;
        }
      </style>
    </noscript>

    <!-- Header -->
    <nav class="navbar navbar-expand-xl fixed-top navbar-light shadow bg-light" style="border-bottom:20px solid #fec107;">
        
        <a class="navbar-brand" href="#" style="pointer-events: none; cursor: default;">
            <img src="${imagePath}" width="80" height="50" alt="" loading="lazy">
        </a>
        
        <input aria-controls="navbar-main" id="navbar-toggle-cbox" role="button" type="checkbox">
        
        <noscript>
            <label class="navbar-toggle" for="navbar-toggle-cbox">
                <span class="navbar-toggler-icon"></span>
            </label>
        </noscript>
        
        <button class="navbar-toggler" type="button" id="toggle-navbar" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/PollWeb/homepage">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/PollWeb/publicPolls">sondaggi pubblici</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/PollWeb/makerPoll/firstSection">crea sondaggio</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/PollWeb/surveyExample">sondaggio d'esempio</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/PollWeb/dashboard">Zona utente</a>
                </li>
            </ul>
            <div class="row ml-1 mr-2">
                <#if sessione?? && sessione == "disattiva">
                    <a href="/PollWeb/login" class="btn btn-warning">accedi</a>
                    <a href="/PollWeb/register" class="btn btn-outline-dark ml-2">registrati</a>
                <#else>
                    <a href="/PollWeb/logout" class="btn btn-warning">logout</a>
                </#if>
            </div>
        </div>
    </nav>
    <!-- End header-->
</#macro>
    
<#macro footer>
    <!-- Footer -->
    <footer class="footer border-top mt-5 pt-4 pb-2 bg-dark">
        <div class="container">
            <div class="row">
                <div class="col-lg-8">
                    <a href="#" class="mr-3 text-light text-decoration-none">Chi siamo</a>
                    <a href="#" class="ml-3 mr-3 text-light text-decoration-none">Contattaci</a>
                    <a href="#" class="ml-3 mr-3 text-light text-decoration-none">Policy & Privacy</a>
                    <a href="#" class="ml-3 text-light text-decoration-none">FAQ</a>
                </div>
                <div class="col-lg-4">
                    <p class="text-light">mandaci una mail a <a href="#" class="text-warning text-decoration-none">QuackDuckPoll@outlook.it</a></p>
                </div>   
            </div>
        </div>
    </footer>
    <!-- End footer -->
    </#macro>
    
    <#macro publicSurveyCorousel>
    <!-- Public survey carousel -->
    <div class="javascriptVisibility">
        <div class="container-fluid mb-5 mt-5">
            <div class="container">
            <div class="row mb-1">
                <div class="col-lg-12 text-center">
                    <h3> Sondaggi pubblici </h3>
                    <p> Le possibilit&#224; che ti offriamo, direttamente da quello che hanno realizzato gli altri utenti (<a href="publicPolls" class="text-info text-decoration-none">vedi altro</a>).</p>
                </div>
            </div>
                <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
                    <div class="carousel-inner">


                        <#assign c = 1>
                        <#list sondaggi as sondaggio>  
                        <#if c%3 == 1>
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
                                                            <button class="btn brn-lg btn-light" name="btnSondaggio" type="submit" title="compila" value="${sondaggio.getKey()}" style="float: right;">
                                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-list-check" viewBox="0 0 16 16">
                                                                    <path fill-rule="evenodd" d="M5 11.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM3.854 2.146a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708L2 3.293l1.146-1.147a.5.5 0 0 1 .708 0zm0 4a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708L2 7.293l1.146-1.147a.5.5 0 0 1 .708 0zm0 4a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 0 1 .708-.708l.146.147 1.146-1.147a.5.5 0 0 1 .708 0z"/>
                                                                </svg>
                                                            </button>
                                                        <#else>
                                                            <h5 class="card-black"></h5>
                                                            <button class="btn brn-lg btn-light" name="btnSondaggio" type="submit" title="compila" value="${sondaggio.getKey()}" style="float: right;">
                                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-list-check" viewBox="0 0 16 16">
                                                                    <path fill-rule="evenodd" d="M5 11.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM3.854 2.146a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708L2 3.293l1.146-1.147a.5.5 0 0 1 .708 0zm0 4a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708L2 7.293l1.146-1.147a.5.5 0 0 1 .708 0zm0 4a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 0 1 .708-.708l.146.147 1.146-1.147a.5.5 0 0 1 .708 0z"/>
                                                                </svg>
                                                            </button>
                                                        </#if>
                                                    </div>
                                                </div>
                                            </div>
                            </#if>
                            <#if c%3 == 2>

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
                                                            <button class="btn brn-lg btn-light" name="btnSondaggio" type="submit" title="compila" value="${sondaggio.getKey()}" style="float: right;">
                                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-list-check" viewBox="0 0 16 16">
                                                                    <path fill-rule="evenodd" d="M5 11.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM3.854 2.146a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708L2 3.293l1.146-1.147a.5.5 0 0 1 .708 0zm0 4a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708L2 7.293l1.146-1.147a.5.5 0 0 1 .708 0zm0 4a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 0 1 .708-.708l.146.147 1.146-1.147a.5.5 0 0 1 .708 0z"/>
                                                                </svg>
                                                            </button>
                                                        <#else>
                                                            <h5 class="card-black"></h5>
                                                            <button class="btn brn-lg btn-light" name="btnSondaggio" type="submit" title="compila" value="${sondaggio.getKey()}" style="float: right;">
                                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-list-check" viewBox="0 0 16 16">
                                                                    <path fill-rule="evenodd" d="M5 11.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM3.854 2.146a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708L2 3.293l1.146-1.147a.5.5 0 0 1 .708 0zm0 4a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708L2 7.293l1.146-1.147a.5.5 0 0 1 .708 0zm0 4a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 0 1 .708-.708l.146.147 1.146-1.147a.5.5 0 0 1 .708 0z"/>
                                                                </svg>
                                                            </button>
                                                        </#if>
                                                    </div>
                                                </div>
                                            </div>


                            </#if>
                            <#if c%3 == 0>   

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
                                                            <button class="btn brn-lg btn-light" name="btnSondaggio" type="submit" title="compila" value="${sondaggio.getKey()}" style="float: right;">
                                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-list-check" viewBox="0 0 16 16">
                                                                    <path fill-rule="evenodd" d="M5 11.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM3.854 2.146a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708L2 3.293l1.146-1.147a.5.5 0 0 1 .708 0zm0 4a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708L2 7.293l1.146-1.147a.5.5 0 0 1 .708 0zm0 4a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 0 1 .708-.708l.146.147 1.146-1.147a.5.5 0 0 1 .708 0z"/>
                                                                </svg>
                                                            </button>
                                                        <#else>
                                                            <h5 class="card-black"></h5>
                                                            <button class="btn brn-lg btn-light" name="btnSondaggio" type="submit" title="compila" value="${sondaggio.getKey()}" style="float: right;">
                                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-list-check" viewBox="0 0 16 16">
                                                                    <path fill-rule="evenodd" d="M5 11.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM3.854 2.146a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708L2 3.293l1.146-1.147a.5.5 0 0 1 .708 0zm0 4a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708L2 7.293l1.146-1.147a.5.5 0 0 1 .708 0zm0 4a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 0 1 .708-.708l.146.147 1.146-1.147a.5.5 0 0 1 .708 0z"/>
                                                                </svg>
                                                            </button>
                                                        </#if>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>


                                </#if>
                            <#assign c = c + 1>
                        </#list>
                    <#if c%3 != 1> 
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
                                                <button class="btn brn-lg btn-light" name="btnSondaggio" type="submit" title="compila" value="${sondaggio.getKey()}" style="float: right;">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-list-check" viewBox="0 0 16 16">
                                                        <path fill-rule="evenodd" d="M5 11.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM3.854 2.146a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708L2 3.293l1.146-1.147a.5.5 0 0 1 .708 0zm0 4a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708L2 7.293l1.146-1.147a.5.5 0 0 1 .708 0zm0 4a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 0 1 .708-.708l.146.147 1.146-1.147a.5.5 0 0 1 .708 0z"/>
                                                    </svg>
                                                </button>
                                            <#else>
                                                <h5 class="card-title"></h5>
                                                <button class="btn brn-lg btn-light" name="btnSondaggio" type="submit" title="compila" value="${sondaggio.getKey()}" style="float: right;">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-list-check" viewBox="0 0 16 16">
                                                        <path fill-rule="evenodd" d="M5 11.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM3.854 2.146a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708L2 3.293l1.146-1.147a.5.5 0 0 1 .708 0zm0 4a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708L2 7.293l1.146-1.147a.5.5 0 0 1 .708 0zm0 4a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 0 1 .708-.708l.146.147 1.146-1.147a.5.5 0 0 1 .708 0z"/>
                                                    </svg>
                                                </button>
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
    <!-- End public survey carousel -->
    </#macro>

    <#macro openShort domanda="">
        <#if domanda!="">
            <p>ciao</p>
        <#else>
            <div class="row">
                <div class="col-12">
                    <p class="h4">Domanda - risposta breve<span class="text-danger">*<span></p>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <p class="text-muted"> Questa &#232; la descrizione della domanda, qui puoi dare delle informazioni in pi&#249; per aiutare l'utente a rispondere nella maniera pi&#249; corretta possibile.</p>
                </div>
            </div>
            <div class="row mb-3">
                <div class="col-12">
                    <input type="text" class="form-control" placeholder="massimo numero di caratteri: 128" maxlength="128">
                </div>
            </div>
        </#if>
    </#macro>
            
    <#macro openLong domanda="">
        <#if domanda!="">
            <p>ciao</p>
        <#else>
            <div class="row">
                <div class="col-12">
                    <p class="h4">Domanda - risposta lunga<span class="text-danger">*<span></p>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <p class="text-muted"> Questa &#232; la descrizione della domanda, qui puoi dare delle informazioni in pi&#249; per aiutare l'utente a rispondere nella maniera pi&#249; corretta possibile.</p>
                </div>
            </div>
            <div class="row mb-3">
                <div class="col-12">
                    <textarea rows="3" class="form-control" placeholder="massimo numero di caratteri: 512" style="resize: none" max="512"></textarea>
                </div>
            </div>
        </#if>
    </#macro>
            
    <#macro openNumber domanda="">
        <#if domanda!="">
            <p>ciao</p>
        <#else>
            <div class="row">
                <div class="col-12">
                    <p class="h4">Domanda - risposta numerica<span class="text-danger">*<span></p>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <p class="text-muted"> Questa &#232; la descrizione della domanda, qui puoi dare delle informazioni in pi&#249; per aiutare l'utente a rispondere nella maniera pi&#249; corretta possibile.</p>
                </div>
            </div>
            <div class="row mb-3">
                <div class="col-lg-4 col-md-5 col-sm-12">
                    <input type="number" class="form-control" placeholder="Numero compreso tra x ed y">
                </div>
            </div>
        </#if>
    </#macro>
            
    <#macro openDate domanda="">
        <#if domanda!="">
            <p>ciao</p>
        <#else>
            <div class="row">
                <div class="col-12">
                    <p class="h4">Domanda - risposta con data<span class="text-danger">*<span></p>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <p class="text-muted"> Questa &#232; la descrizione della domanda, qui puoi dare delle informazioni in pi&#249; per aiutare l'utente a rispondere nella maniera pi&#249; corretta possibile.</p>
                </div>
            </div>
            <div class="row mb-3">
                <div class="col-lg-4 col-md-5 col-sm-12">
                    <input type="date" class="form-control">
                </div>
            </div>
        </#if>
    </#macro>
            
    <#macro closeSingle domanda="">
        <#if domanda!="">
            <p>ciao</p>
        <#else>
            <div class="row">
                <div class="col-12">
                    <p class="h4">Domanda - risposta con scelta singola<span class="text-danger">*<span></p>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <p class="text-muted"> Questa &#232; la descrizione della domanda, qui puoi dare delle informazioni in pi&#249; per aiutare l'utente a rispondere nella maniera pi&#249; corretta possibile.</p>
                </div>
            </div>
            <div class="row mb-3">
                <div class="col-12">
                    <div class="custom-control custom-radio">
                        <input type="radio" class="custom-control-input" id="opzione1" name="opzioni" value="opzione1">
                        <label for="opzione1" class="custom-control-label">Opzione 1</label><br>
                    </div>
                    <div class="custom-control custom-radio">
                        <input type="radio" class="custom-control-input" id="opzione2" name="opzioni" value="opzione2">
                        <label for="opzione2" class="custom-control-label">Opzione 2</label><br>
                    </div>
                    <div class="custom-control custom-radio">
                        <input type="radio" class="custom-control-input" id="opzione3" name="opzioni" value="opzione3">
                        <label for="opzione3" class="custom-control-label">Opzione 3</label><br>
                    </div>
                    <div class="custom-control custom-radio">
                        <input type="radio" class="custom-control-input" id="opzione4" name="opzioni" value="opzione4">
                        <label for="opzione4" class="custom-control-label">Opzione 4</label><br>
                    </div>
                </div>
            </div>
        </#if>
    </#macro>
            
    <#macro closeMultiple domanda="">
        <#if domanda!="">
            <p>ciao</p>
        <#else>
            <div class="row">
                <div class="col-12">
                    <p class="h4">Domanda - risposta con scelta singola<span class="text-danger">*<span></p>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <p class="text-muted"> Questa &#232; la descrizione della domanda, qui puoi dare delle informazioni in pi&#249; per aiutare l'utente a rispondere nella maniera pi&#249; corretta possibile.</p>
                </div>
            </div>
            <div class="row mb-3">
                <div class="col-12">
                    <div>
                        <label class="checkbox mb-3">
                            <input type="checkbox" name="opzioneM1"/>
                            <span class="warning"></span>
                        </label>
                        &nbspOpzione 1 
                    </div>
                </div>
                <div class="col-12">
                    <div>
                        <label class="checkbox mb-3">
                            <input type="checkbox" name="opzioneM2"/>
                            <span class="warning"></span>
                        </label>
                        &nbspOpzione 2 
                    </div>
                </div>
                <div class="col-12">
                    <div>
                        <label class="checkbox mb-3">
                            <input type="checkbox" name="opzioneM3"/>
                            <span class="warning"></span>
                        </label>
                        &nbspOpzione 3 
                    </div>
                </div>
                <div class="col-12">
                    <div>
                        <label class="checkbox mb-3">
                            <input type="checkbox" name="opzioneM4"/>
                            <span class="warning"></span>
                        </label>
                        &nbspOpzione 4 
                    </div>
                </div>
            </div>
        </#if>
    </#macro>