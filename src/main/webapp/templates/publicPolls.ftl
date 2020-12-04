<!DOCTYPE html>
<#import "/macros.ftl" as macros>
<#assign charset="UTF-8">
<#assign title="Quack, Duck, Poll!">
<html>
    <head>
        <title>${title}</title>
        <meta charset="${charset}">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <@macros.style />

        <@macros.carouselOvverridingStyle />
    </head>
    <body class="bg-light">
        <div class="d-flex flex-column min-vh-100">
            <@macros.header />
            <main class="flex-fill">
                <!-- Search bar -->
                <div class="container-fluid">
                    <div style="margin-top:5%"></div>
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-12">
                            <h1 class="h3 mb-3 font-weight-normal text-center">Cerca un sondaggio di tuo interesse!</h1>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-group">
                                <input type="text" class="form-control" aria-label="Text input with dropdown button" style="border-radius: 1em 0 0 1em !important;">
                                <div class="input-group-append">
                                    <div class="dropdown">
                                        <button class="btn btn-warning dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" style="width: 150px; border-radius: 0 1em 1em 0 !important;">
                                            Filtro
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
                                            <li><a class="dropdown-item" href="#" data-value="popolari">Popolari</a></li>
                                            <li><a class="dropdown-item" href="#" data-value="piu recenti">Pi&#249 recenti</a></li>
                                            <li><a class="dropdown-item" href="#" data-value="meno recenti">Meno recenti</a></li>
                                            <li><div class="dropdown-divider"></div></li>
                                            <li><a class="dropdown-item" href="#" data-value="filtro">Annulla filtro</a></li>
                                        </ul>
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
                <div class="container-fluid mt-5">
                    <div class="container">
                        <div class="card-deck mr-1 ml-1">
                            <div class="row no-gutters">
                                <div class="col-lg-4 mb-3 variable-height">
                                    <div class="card h-100">
                                        <div class="card-body">
                                            <h5 class="card-title">Card title</h5>
                                            <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                                        </div>
                                        <div class="card-footer bg-warning">
                                            <small class="text-black">Last updated 3 mins ago</small>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-4 mb-3 variable-height">
                                    <div class="card h-100">
                                        <div class="card-body">
                                            <h5 class="card-title">Card title</h5>
                                            <p class="card-text">This card has supporting text below as a natural lead-in to additional content.</p>
                                        </div>
                                        <div class="card-footer bg-warning">
                                            <small class="text-black">Last updated 3 mins ago</small>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-4 variable-height">
                                    <div class="card h-100">
                                        <div class="card-body">
                                            <h5 class="card-title">Card title</h5>
                                            <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This card has even longer content than the first to show that equal height action. (MAX 200 char)</p>
                                        </div>
                                        <div class="card-footer bg-warning">
                                            <small class="text-black">Last updated 3 mins ago</small>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="container-fluid mt-5">
                    <div class="container">
                        <div class="card-deck mr-1 ml-1">
                            <div class="row no-gutters">
                                <div class="col-lg-4 mb-3 variable-height">
                                    <div class="card h-100">
                                        <div class="card-body">
                                            <h5 class="card-title">Card title</h5>
                                            <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                                        </div>
                                        <div class="card-footer bg-warning">
                                            <small class="text-black">Last updated 3 mins ago</small>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-4 mb-3 variable-height">
                                    <div class="card h-100">
                                        <div class="card-body">
                                            <h5 class="card-title">Card title</h5>
                                            <p class="card-text">This card has supporting text below as a natural lead-in to additional content.</p>
                                        </div>
                                        <div class="card-footer bg-warning">
                                            <small class="text-black">Last updated 3 mins ago</small>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-4 variable-height">
                                    <div class="card h-100">
                                        <div class="card-body">
                                            <h5 class="card-title">Card title</h5>
                                            <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This card has even longer content than the first to show that equal height action. (MAX 200 char)</p>
                                        </div>
                                        <div class="card-footer bg-warning">
                                            <small class="text-black">Last updated 3 mins ago</small>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="container-fluid mt-5">
                    <div class="container">
                        <div class="card-deck mr-1 ml-1">
                            <div class="row no-gutters">
                                <div class="col-lg-4 mb-3 variable-height">
                                    <div class="card h-100">
                                        <div class="card-body">
                                            <h5 class="card-title">Card title</h5>
                                            <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                                        </div>
                                        <div class="card-footer bg-warning">
                                            <small class="text-black">Last updated 3 mins ago</small>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-4 mb-3 variable-height">
                                    <div class="card h-100">
                                        <div class="card-body">
                                            <h5 class="card-title">Card title</h5>
                                            <p class="card-text">This card has supporting text below as a natural lead-in to additional content.</p>
                                        </div>
                                        <div class="card-footer bg-warning">
                                            <small class="text-black">Last updated 3 mins ago</small>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-4 variable-height">
                                    <div class="card h-100">
                                        <div class="card-body">
                                            <h5 class="card-title">Card title</h5>
                                            <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This card has even longer content than the first to show that equal height action. (MAX 200 char)</p>
                                        </div>
                                        <div class="card-footer bg-warning">
                                            <small class="text-black">Last updated 3 mins ago</small>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="container-fluid mt-5">
                    <div class="container">
                        <div class="card-deck mr-1 ml-1">
                            <div class="row no-gutters">
                                <div class="col-lg-4 mb-3 variable-height">
                                    <div class="card h-100">
                                        <div class="card-body">
                                            <h5 class="card-title">Card title</h5>
                                            <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                                        </div>
                                        <div class="card-footer bg-warning">
                                            <small class="text-black">Last updated 3 mins ago</small>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-4 mb-3 variable-height">
                                    <div class="card h-100">
                                        <div class="card-body">
                                            <h5 class="card-title">Card title</h5>
                                            <p class="card-text">This card has supporting text below as a natural lead-in to additional content.</p>
                                        </div>
                                        <div class="card-footer bg-warning">
                                            <small class="text-black">Last updated 3 mins ago</small>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-4 variable-height">
                                    <div class="card h-100">
                                        <div class="card-body">
                                            <h5 class="card-title">Card title</h5>
                                            <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This card has even longer content than the first to show that equal height action. (MAX 200 char)</p>
                                        </div>
                                        <div class="card-footer bg-warning">
                                            <small class="text-black">Last updated 3 mins ago</small>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- End cards -->
                
                <div class="container-fluid mt-5">
                    <div class="container">
                        <nav class="mt-3" aria-label="Page navigation example">
                            <ul class="pagination justify-content-center">
                                <li class="page-item">
                                    <a class="page-link" href="#" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                        <span class="sr-only">Previous</span>
                                    </a>
                                </li>
                                <li class="page-item"><a class="page-link" href="#">1</a></li>
                                <li class="page-item"><a class="page-link" href="#">2</a></li>
                                <li class="page-item"><a class="page-link" href="#">3</a></li>
                                <li class="page-item">
                                    <a class="page-link" href="#" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                        <span class="sr-only">Next</span>
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
                
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

