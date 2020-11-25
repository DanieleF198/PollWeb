<#macro style>
    <!-- Style -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <link rel="stylesheet" href="css/style.css">
    <!-- End style -->
</#macro>

<#macro script>
    <!-- Script -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
    <!-- End script -->
</#macro>
    
<#macro header>
    <!-- Header -->
    <nav class="navbar navbar-expand-lg fixed-top navbar-light shadow bg-light" style="border-bottom:20px solid #fec107;">
        <a class="navbar-brand" href="#">
            <img src="images/logoDDP.png" width="80" height="50" alt="" loading="lazy">
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">sondaggi pubblici</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">crea sondaggio</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">sondaggio d'esempio</a>
                </li>
            </ul>
            <form class="form-inline">
                <button class="btn btn-warning" type="button">accedi</button>
                <div class="ml-1"></div>
                <button class="btn btn-outline-dark" type="button">iscriviti</button>
            </form>
        </div>
    </nav>
    <!-- End header-->
</#macro>
    
<#macro footer>
    <!-- Footer -->
    <footer class="footer border-top mt-5 pt-4 pb-2 bg-light">
        <div class="container">
            <div class="row">
                <div class="col-lg-8">
                    <a href="#" class="mr-3 text-secondary text-decoration-none">Chi siamo</a>
                    <a href="#" class="ml-3 mr-3 text-secondary text-decoration-none">Contattaci</a>
                    <a href="#" class="ml-3 mr-3 text-secondary text-decoration-none">Policy & Privacy</a>
                    <a href="#" class="ml-3 mr-3 text-secondary text-decoration-none">FAQ</a>
                </div>
                <div class="col-lg-4">
                    <p class="text-secondary">mandaci una mail a <a href="#" class="text-dark text-decoration-none">QuackDuckPoll@outlook.it</a></p>
                </div>   
            </div>
        </div>
    </footer>
    <!-- End footer -->
</#macro>