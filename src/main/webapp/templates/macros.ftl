<#macro style imagePath stylePath>
    <!-- Style -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <link rel="stylesheet" href="${stylePath}">
    <link rel="shortcut icon" href="${imagePath}">
    <style>
        @keyframes check {
            0% {height: 0;width: 0;}
            25% {height: 0;width: 8px;}
            50% {height: 18px;width: 8px;}
        }
        
        .checkbox{
            background-color:#fff;
            display:inline-block;
            height:26px;
            margin:0 .25em;
            width:26px;
            border-radius:4px;
            border:1px solid #ccc;
            float:left
        }
        
        .checkbox span{
            display:block;
            height:26px;
            position:relative;
            width:26px;
            padding:0
        }
        
        .checkbox span:after{
            -moz-transform:scaleX(-1) rotate(135deg);
            -ms-transform:scaleX(-1) rotate(135deg);
            -webkit-transform:scaleX(-1) rotate(135deg);
            transform:scaleX(-1) rotate(135deg);
            -moz-transform-origin:left top;
            -ms-transform-origin:left top;
            -webkit-transform-origin:left top;
            transform-origin:left top;
            border-right:4px solid #fff;
            border-top:4px solid #fff;
            content:'';
            display:block;
            height:18px;
            left:3px;
            position:absolute;
            top:15px;
            width:8px
        }
        
        .checkbox span:hover:after{
            border-color:#999
        }
        
        .checkbox input{
            display:none
        }
        
        .checkbox input:checked + span:after{
            -webkit-animation:check .8s;
            -moz-animation:check .8s;
            -o-animation:check .8s;
            animation:check .8s;
            border-color:#555
        }
        
        .checkbox input:checked + .default:after{
            border-color:#444
        }
        
        .checkbox input:checked + .primary:after{
            border-color:#2196F3
        }
        
        .checkbox input:checked + .success:after{
            border-color:#8bc34a
        }
        
        .checkbox input:checked + .info:after{
            border-color:#3de0f5
        }
        
        .checkbox input:checked + .warning:after{
            border-color:#f0AD4E
        }
        
        .checkbox input:checked + .danger:after{
            border-color:#f44336
        }
        
        .form-control:focus {
            border-color: #f0ad4e;
            box-shadow: inset 0 1px 1px rgba(240, 172, 78, 0.075), 0 0 8px rgba(237, 188, 121, 0.6);
        }
        
        /* Chrome, Safari, Edge, Opera */
        input::-webkit-outer-spin-button,
        input::-webkit-inner-spin-button {
            -webkit-appearance: none;
            margin: 0;
        }

        /* Firefox */
        input[type=number] {
            -moz-appearance: textfield;
        }
        
        /* This is the default state */
        .custom-radio .custom-control-label::before {
            background-color: white;
        }

        /* This is the checked state */
        .custom-radio .custom-control-input:checked~.custom-control-label::before,
        .custom-radio .custom-control-input:checked~.custom-control-label::after {
            background-color: #f0AD4E; 
            border-radius: 50%;
        }

        /* active state i.e. displayed while the mouse is being pressed down */
        .custom-radio .custom-control-input:active ~ .custom-control-label::before {
            border-color: #f0AD4E;
            background-color: white;
        }

        /* the shadow; displayed while the element is in focus */
        .custom-radio .custom-control-input:focus ~ .custom-control-label::before {
            box-shadow: inset 0 1px 1px rgba(240, 172, 78, 0.075), 0 0 8px rgba(237, 188, 121, 0.6);
        }

        .custom-control-label::before {
            background-color: darkorange;
        }
        
        .pagination > li > a{
            background-color: white;
            color: #353a40;
        }

        .pagination > li > a:focus,
        .pagination > li > a:hover,
        .pagination > li > span:focus,
        .pagination > li > span:hover{
            color: #33373d;
            background-color: #eee;
            border-color: #ddd;
            border-color: #f0ad4e;
            box-shadow: inset 0 1px 1px rgba(240, 172, 78, 0.075), 0 0 8px rgba(237, 188, 121, 0.6);
        }

        .pagination > .active > a{
            color: white;
            background-color: #5A4181 !Important;
            border: solid 1px #5A4181 !Important;
            border-color: #f0ad4e;
            box-shadow: inset 0 1px 1px rgba(240, 172, 78, 0.075), 0 0 8px rgba(237, 188, 121, 0.6);
        }

        .pagination > .active > a:hover{
            background-color: #5A4181 !Important;
            border: solid 1px #5A4181;
            border-color: #f0ad4e;
            box-shadow: inset 0 1px 1px rgba(240, 172, 78, 0.075), 0 0 8px rgba(237, 188, 121, 0.6);
        }
        
        .table-striped > tbody > tr:nth-child(2n+1) > td, .table-striped > tbody > tr:nth-child(2n+1) > th {
            background-color: #fcf9e3;
        }
        
        #header-search {
            border-radius: 1em 1em 1em 1em;
            padding-right: 39px;
        }

        #search-field svg {
            width: 30px;
            position: absolute;
            top: 4px;
            bottom: 4px;
            right: 8px;
        }
        
        #search-field{
            display: inline-block; 
            width: 100%;
            position: relative
        }
        
        .header-margin{
            margin-top:5%;
        }
        
        .margin-pagination-scroll-bar{
            margin-top:0%;
        }

        @media screen and (max-width: 1200px){
            .header-margin{
                margin-top:7.5%
            }
        }

        @media screen and (max-width: 992px){
            .header-margin{
                margin-top:10%
            }
        }
        
        @media screen and (max-width: 768px){
            .header-margin{
                margin-top:12.5%
            }
        }
        
        @media screen and (max-width: 576px){
                .header-margin{
                margin-top:15%
            }
            .margin-pagination-scroll-bar{
                margin-top:2%;
            }
        }
        
        /*TAB with CSS - for now support a max of 6 */
        
        .tab-wrap {
            -webkit-transition: 0.4s box-shadow ease;
            transition: 0.4s box-shadow ease;
            border-radius: 3px;
            max-width: 100%;
            display: -webkit-box;
            display: -ms-flexbox;
            display: flex;
            -ms-flex-wrap: wrap;
                flex-wrap: wrap;
            position: relative;
            list-style: none;
            background-color: #fff;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
        }

        .tab {
            display: none;
        }
        
        .tab:checked:nth-of-type(1) ~ .tab__content:nth-of-type(1) {
            opacity: 1;
            -webkit-transition: 0.5s opacity ease-in, 0.8s -webkit-transform ease;
            transition: 0.5s opacity ease-in, 0.8s -webkit-transform ease;
            transition: 0.5s opacity ease-in, 0.8s transform ease;
            transition: 0.5s opacity ease-in, 0.8s transform ease, 0.8s -webkit-transform ease;
            position: relative;
            top: 0;
            z-index: 99;
            -webkit-transform: translateY(0px);
                    transform: translateY(0px);
            text-shadow: 0 0 0;
        }
        
        .tab:checked:nth-of-type(2) ~ .tab__content:nth-of-type(2) {
            opacity: 1;
            -webkit-transition: 0.5s opacity ease-in, 0.8s -webkit-transform ease;
            transition: 0.5s opacity ease-in, 0.8s -webkit-transform ease;
            transition: 0.5s opacity ease-in, 0.8s transform ease;
            transition: 0.5s opacity ease-in, 0.8s transform ease, 0.8s -webkit-transform ease;
            position: relative;
            top: 0;
            z-index: 100;
            -webkit-transform: translateY(0px);
                    transform: translateY(0px);
            text-shadow: 0 0 0;
        }
        
        .tab:checked:nth-of-type(3) ~ .tab__content:nth-of-type(3) {
            opacity: 1;
            -webkit-transition: 0.5s opacity ease-in, 0.8s -webkit-transform ease;
            transition: 0.5s opacity ease-in, 0.8s -webkit-transform ease;
            transition: 0.5s opacity ease-in, 0.8s transform ease;
            transition: 0.5s opacity ease-in, 0.8s transform ease, 0.8s -webkit-transform ease;
            position: relative;
            top: 0;
            z-index: 100;
            -webkit-transform: translateY(0px);
                    transform: translateY(0px);
            text-shadow: 0 0 0;
        }
        
        .tab:checked:nth-of-type(4) ~ .tab__content:nth-of-type(4) {
            opacity: 1;
            -webkit-transition: 0.5s opacity ease-in, 0.8s -webkit-transform ease;
            transition: 0.5s opacity ease-in, 0.8s -webkit-transform ease;
            transition: 0.5s opacity ease-in, 0.8s transform ease;
            transition: 0.5s opacity ease-in, 0.8s transform ease, 0.8s -webkit-transform ease;
            position: relative;
            top: 0;
            z-index: 100;
            -webkit-transform: translateY(0px);
                    transform: translateY(0px);
            text-shadow: 0 0 0;
        }
        .tab:checked:nth-of-type(5) ~ .tab__content:nth-of-type(5) {
            opacity: 1;
            -webkit-transition: 0.5s opacity ease-in, 0.8s -webkit-transform ease;
            transition: 0.5s opacity ease-in, 0.8s -webkit-transform ease;
            transition: 0.5s opacity ease-in, 0.8s transform ease;
            transition: 0.5s opacity ease-in, 0.8s transform ease, 0.8s -webkit-transform ease;
            position: relative;
            top: 0;
            z-index: 100;
            -webkit-transform: translateY(0px);
                    transform: translateY(0px);
            text-shadow: 0 0 0;
        }
        .tab:checked:nth-of-type(6) ~ .tab__content:nth-of-type(6) {
            opacity: 1;
            -webkit-transition: 0.5s opacity ease-in, 0.8s -webkit-transform ease;
            transition: 0.5s opacity ease-in, 0.8s -webkit-transform ease;
            transition: 0.5s opacity ease-in, 0.8s transform ease;
            transition: 0.5s opacity ease-in, 0.8s transform ease, 0.8s -webkit-transform ease;
            position: relative;
            top: 0;
            z-index: 100;
            -webkit-transform: translateY(0px);
                    transform: translateY(0px);
            text-shadow: 0 0 0;
        }
        .tab:first-of-type:not(:last-of-type) + label {
            border-top-right-radius: 0;
            border-bottom-right-radius: 0;
        }
        .tab:not(:first-of-type):not(:last-of-type) + label {
            border-radius: 0;
        }
        .tab:last-of-type:not(:first-of-type) + label {
            border-top-left-radius: 0;
            border-bottom-left-radius: 0;
        }
        .tab:checked + label {
            background-color: #FEC107;
            box-shadow: 0 -1px 0 #fff inset;
            cursor: default;
        }
        .tab:checked + label:hover {
            box-shadow: 0 -1px 0 #fff inset;
            background-color: #FEC107;
        }
        .tab + label {
            box-shadow: 0 -1px 0 #eee inset;
            border-radius: 6px 6px 0 0;
            cursor: pointer;
            display: block;
            text-decoration: none;
            color: #333;
            -webkit-box-flex: 3;
                -ms-flex-positive: 3;
                    flex-grow: 3;
            text-align: center;
            background-color: #FFF5D9;
            -webkit-user-select: none;
               -moz-user-select: none;
                -ms-user-select: none;
                    user-select: none;
            text-align: center;
            -webkit-transition: 0.3s background-color ease, 0.3s box-shadow ease;
            transition: 0.3s background-color ease, 0.3s box-shadow ease;
            height: 50px;
            box-sizing: border-box;
            padding: 15px;
            margin-bottom: 0px;
        }
        .tab + label:hover {
            background-color: #FFECB5;
            box-shadow: 0 1px 0 #f4f4f4 inset;
        }
        .tab__content {
            padding: 10px 25px;
            background-color: white;
            position: absolute;
            width: 100%;
            z-index: -1;
            opacity: 0;
            left: 0;
            -webkit-transform: translateY(-3px);
                    transform: translateY(-3px);
            border-radius: 6px;
        }
        
        .custom-left{
            display: flex;
            align-items: flex-start;
            justify-content: flex-start;
        }
        
        .custom-center{
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        .custom-right{
            display: flex;
            align-items: flex-end;
            justify-content: flex-end;
        }
        
        
        @media screen and (max-width: 991px){
            .custom-left{
                display: flex;
                align-items: center;
                justify-content: center;
            }
        
            .custom-right{
                display: flex;
                align-items: center;
                justify-content: center;
            }
            
                .custom-block{
                width: 100%; 
                display:block;
            }
        }
    </style>
    <!-- End style -->
</#macro>
    
<#macro carouselOvverridingStyle>
    <!-- bootstrap carousel ovverriding style -->
    <style>
        .carousel-control-prev  {
            margin-left: -100px
            }
        
        .carousel-control-next {
            margin-right: -100px
            }
        
        .variable-height{
                height: 230px    
            }
       
        @media screen and (max-width: 1200px){
            .carousel-control-prev {
                margin-left: -85px
            }
        
            .carousel-control-next {
                margin-right: -85px
            }
            
            .variable-height{
                height: 230px    
            }
        }
        
        @media screen and (max-width: 992px){ //considerare questo breakpoint per il massimo numero di caratteri nella descrione del carosello
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
        
        @media screen and (max-width: 991px){
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
    <nav class="navbar navbar-expand-lg fixed-top navbar-light shadow bg-light" style="border-bottom:20px solid #fec107;">
        
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
                    <a class="nav-link" href="/PollWeb/makerPoll/firsSection">crea sondaggio</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">sondaggio d'esempio</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/PollWeb/dashboard">Zona utente (template)</a>
                </li>
            </ul>
            <div class="row ml-1 mr-2">
                <a href="/PollWeb/login" class="btn btn-warning">accedi</a>
                <a href="/PollWeb/register" class="btn btn-outline-dark ml-2">registrati</a>
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
    <div class="container-fluid mb-5">
        <div class="container">
            <div class="row mb-1">
                <div class="col-lg-12 text-center">
                    <h3> Sondaggi pubblici </h3>
                    <p> Le possibilit&#224; che ti offriamo, direttamente da quello che hanno realizzato gli altri utenti (<a href="publicPolls" class="text-info text-decoration-none">vedi altro</a>).</p>
                </div>
            </div>
            <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
                <div class="carousel-inner">
                    <div class="carousel-item active">
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
                    <div class="carousel-item">
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
                                            <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This card has even longer content than the first to show that equal height action.</p>
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
    <!-- End public survey carousel -->
    </#macro>