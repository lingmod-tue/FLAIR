<%--
    Document   : visual
    Created on : Apr 16, 2015, 7:07:08 PM
    Author     : Maria
--%>

<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

    String query = "";
    String docs_path = "";
    if (request != null && request.getSession() != null) {
        Object q = request.getSession().getAttribute("query");
        if (q != null && !((String) q).trim().isEmpty()) {
            query = (String) request.getSession().getAttribute("query");
        }
    }


%>

<!DOCTYPE html>
<html lang="en"><head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <link rel="stylesheet" href="js/libs/jqueryui/jquery-ui.css">
        <link rel="stylesheet" href="js/libs/jqueryui/jquery.ui.slider.css">


        <link rel='stylesheet' href='js/libs/bootstrap-3.3.4-dist/css/bootstrap.css'>
        <link rel='stylesheet' href="js/libs/bootstrap-3.3.4-dist/css/bootstrap.min.css">
        <link rel='stylesheet' href='js/libs/bootstrap-3.3.4-dist/css/bootstrap-theme.css'>

        <!-- Custom CSS -->

        <link href="css/simple-sidebar.css" rel="stylesheet">
        <link href="css/simple-sidebar-right.css" rel="stylesheet">




        <link rel="stylesheet" href="css/dashboard.css" />

        <link rel="stylesheet" href="css/docs.min.css" />

        <link rel="stylesheet" href="css/number-polyfill.css" />

        <link rel="stylesheet" href="css/flair_visual.css" />


        <title>FLAIR</title>
    </head>
    <body  style="background-color: white;">



        <div id="wrapper" >

            <div class="container">


                <div class="navbar navbar-default navbar-fixed-top" role="navigation">
                    <div class="container-fluid">

                        <div class="row" hidden>
                            <div style="margin-top: 2%; text-align: center;">
                                <h2><label>Hello<span id="hello_name"><% if (request != null
                                            && request.getSession() != null
                                            && request.getSession().getAttribute("user") != null
                                            && request.getSession().getAttribute("hello_name") != null) {
                                            %>,
                                            <%= request.getSession().getAttribute("hello_name")%><%}%></span>!</label></h2>
                            </div>
                        </div>



                        <div class="row"  style="margin-top: 2%;">
                            <div class="col-lg-3">
                                <a href="#menu-toggle" class="btn btn-warning" id="menu-toggle"><b>SETTINGS</b></a><br><br>
                            </div>
                            <div class="col-lg-6">
                                <form role="form" method="POST" action="SearchServletTeacher" name="search_form" id="search_form">
                                    <div class="input-group">

                                        <input type="text" id="search_field" name="query" class="form-control" value="<%=query%>">
                                        <div class="input-group-btn" >
                                            <button class="btn btn-default" type="submit" id="search_button" data-loading-text="<img style='height:18px;' src='img/ajax-loader.gif' alt='...'/>">GO!</button>
                                        </div>
                                    </div><!-- /input-group -->
                                </form>
                            </div><!-- /.col-lg-6 -->

                            <!--                            <div class="col-lg-1" style="text-align:right;" id="logout" onclick="<%-- if (request != null && request.getSession() != null) {
                                                                request.getSession().invalidate();
                                                            } --%>"><span class="glyphicon glyphicon-log-out"></span></div>-->

                            <div class="col-lg-3" style="text-align:right;"><span id="right-menu-toggle"><img src="img/glyphicons-517-menu-hamburger.png" alt=">"></span></div>

                        </div> <!-- row -->


                        <div class="row">
                            <div style="text-align: center;">
                                <a href="#cache-toggle" id="cache-toggle">Recent searches / Common searches</a><br><br>
                            </div>
                        </div>

                    </div>

                </div>

                <!-- Settings SIDE BAR -->
                <div id="sidebar-wrapper">

                    <div  class="sidebar-nav" id="sidebar">


                        <div style="width:90%; padding-left: 10%;">

                            <br>
                            <div class="df" id="docs_info"></div>
                            <br><div class="panel panel-default" style="text-align: center">
                                <a href="javascript:show_visual();" style="color:orange" >VISUALIZE</a>
                            </div>

                            <!-- Shorter/longer documents slider -->
                            <hr>
                            <h4 style="color:grey">Text characteristics:</h4> 
                            <div class="row">
                                <div class="col-md-2">
                                    Length: <br><br>
                                    <div class="ui-widget-content-len">
                                        <div class="lengthSlider" id="length-slider"></div>
                                    </div> 
                                </div>
                                <div class="col-md-2">&nbsp;</div>
                                <div class="col-md-8" id="settings_levels" style="border-left:1px lightgrey solid;">
                                    Levels:  <br><br>
                                    <div><input type="checkbox" aria-label="A1-A2" onclick="rerank(false)" id="LEVEL-a" checked> A1-A2
                                        <br><span class="df" id="LEVEL-a-df"></span><br>
                                    </div>
                                    <div><input type="checkbox" aria-label="B1-B2" onclick="rerank(false)" id="LEVEL-b" checked> B1-B2
                                        <br><span class="df" id="LEVEL-b-df"></span><br>
                                    </div>
                                    <div><input type="checkbox" aria-label="C1-C2" onclick="rerank(false)" id="LEVEL-c" checked> C1-C2
                                        <br><span class="df" id="LEVEL-c-df"></span><br>
                                    </div>
                                </div>
                            </div>
                            <hr>
                            <div id="settings_panel">
                                <div class="panel panel-default" style="text-align: center">
                                    <a href="javascript:reset('all');" style="color:grey;">RESET ALL</a>
                                </div>
                                <div class="panel panel-warning">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <button type="button" class="close" style="font-size: 12px" onclick="reset('sentences')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                            <a data-toggle="collapse" data-parent="#accordion"
                                               href="#collapse_sentences">
                                                sentences
                                            </a>

                                        </h4>
                                    </div>
                                    <div id="collapse_sentences" class="panel-collapse collapse">
                                        <div class="panel-body">
                                            <br>
                                            <div class="panel panel-info">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <button type="button" class="close" style="font-size: 12px" onclick="reset('questions')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_questions">
                                                            questions
                                                        </a>

                                                    </h4>
                                                </div>

                                                <div id="collapse_questions" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;direct questions<br><span class="df" id="directQuestions-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="directQuestions-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;wh- questions
                                                            <br><span class="df" id="whQuestions-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="whQuestions-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;do- questions<br><span class="df" id="toDoQuestions-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="toDoQuestions-gradientSlider"></div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;be- questions<br><span class="df" id="toBeQuestions-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="toBeQuestions-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;have- questions<br><span class="df" id="toHaveQuestions-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="toHaveQuestions-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;yes/no questions<br><span class="df" id="yesNoQuestions-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="yesNoQuestions-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;tag questions<br><span class="df" id="tagQuestions-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="tagQuestions-gradientSlider"></div>
                                                                </div>
                                                            </div>

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>


                                            <div class="panel panel-info">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <button type="button" class="close" style="font-size: 12px" onclick="reset('structure')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_structure">
                                                            sentence types
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_structure" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;simple <br><span class="df" id="simpleSentence-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="simpleSentence-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;coordinate <br><span class="df" id="compoundSentence-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="compoundSentence-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;subordinate <br><span class="df" id="subordinateClause-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="subordinateClause-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;incomplete sentences <br><span class="df" id="incompleteSentence-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="incompleteSentence-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>


                                            <div class="panel panel-info">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <button type="button" class="close" style="font-size: 12px" onclick="reset('clauses')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_clauses">
                                                            clause types
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_clauses" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;relative<br><span class="df" id="relativeClause-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="relativeClause-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;adverbial<br><span class="df" id="adverbialClause-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="adverbialClause-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;real conditional<br><span class="df" id="condReal-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="condReal-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;unreal conditional<br><span class="df" id="condUnreal-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="condUnreal-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;there is/are <br><span class="df" id="thereIsAre-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="thereIsAre-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;there was/were <br><span class="df" id="thereWasWere-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="thereWasWere-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>

                                <div class="panel panel-warning">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <button type="button" class="close" style="font-size: 12px" onclick="reset('pos')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                            <a data-toggle="collapse" data-parent="#accordion"
                                               href="#collapse_pos">
                                                parts of speech
                                            </a>

                                        </h4>
                                    </div>
                                    <div id="collapse_pos" class="panel-collapse collapse">
                                        <div class="panel-body">
                                            <br>
                                            <div class="panel panel-info">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <button type="button" class="close" style="font-size: 12px" onclick="reset('verbs')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_verbs">
                                                            verbs
                                                        </a>

                                                    </h4>
                                                </div>
                                                <div id="collapse_verbs" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <br>
                                                        <div class="panel panel-success">
                                                            <div class="panel-heading">
                                                                <h4 class="panel-title">
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="reset('verbForms')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_verbForms">
                                                                        verb forms
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_verbForms" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;contracted (to be and to have: 'm, 's, 'd) <br><span class="df" id="shortVerbForms-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="shortVerbForms-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;full (to be and to have: is, are, had) <br><span class="df" id="longVerbForms-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="longVerbForms-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;auxiliaries (to be and to have: short and full forms) <br><span class="df" id="auxiliariesBeDoHave-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="auxiliariesBeDoHave-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;copula (be, seem, look, stay, etc.: "She looks upset.") <br><span class="df" id="copularVerbs-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="copularVerbs-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;-ing (gerund and present participle) <br><span class="df" id="ingVerbForms-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="ingVerbForms-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;to- infinitive <br><span class="df" id="toInfinitiveForms-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="toInfinitiveForms-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;emphatic do ("I did tell the truth") <br><span class="df" id="emphaticDo-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="emphaticDo-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;irregular (2nd and 3rd form) <br><span class="df" id="irregularVerbs-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="irregularVerbs-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;regular (2nd and 3rd form) <br><span class="df" id="regularVerbs-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="regularVerbs-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="panel panel-success">
                                                            <div class="panel-heading">
                                                                <h4 class="panel-title">
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="reset('tenses')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_tenses">
                                                                        tenses
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_tenses" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Present Simple <br><span class="df" id="presentSimple-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="presentSimple-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Present Progressive <br><span class="df" id="presentProgressive-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="presentProgressive-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Past Simple <br><span class="df" id="pastSimple-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pastSimple-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Past Progressive <br><span class="df" id="pastProgressive-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pastProgressive-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Future Simple <br><span class="df" id="futureSimple-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="futureSimple-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Future Progressive <br><span class="df" id="futureProgressive-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="futureProgressive-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Present Perfect <br><span class="df" id="presentPerfect-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="presentPerfect-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Past Perfect <br><span class="df" id="pastPerfect-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pastPerfect-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Future Perfect <br><span class="df" id="futurePerfect-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="futurePerfect-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Present Perfect Progressive <br><span class="df" id="presentPerfProg-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="presentPerfProg-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Past Perfect Progressive <br><span class="df" id="pastPerfProg-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pastPerfProg-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Future Perfect Progressive <br><span class="df" id="futurePerfProg-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="futurePerfProg-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="panel panel-success">
                                                            <div class="panel-heading">
                                                                <h4 class="panel-title">
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="reset('aspects')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_aspects">
                                                                        aspect
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_aspects" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;simple <br><span class="df" id="simpleAspect-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="simpleAspect-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;progressive <br><span class="df" id="progressiveAspect-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="progressiveAspect-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;perfect <br><span class="df" id="perfectAspect-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="perfectAspect-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;perfect progressive <br><span class="df" id="perfProgAspect-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="perfProgAspect-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="panel panel-success">
                                                            <div class="panel-heading">
                                                                <h4 class="panel-title">
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="reset('times')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_times">
                                                                        time
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_times" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;present <br><span class="df" id="presentTime-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="presentTime-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;past <br><span class="df" id="pastTime-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pastTime-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;future <br><span class="df" id="futureTime-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="futureTime-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="panel panel-success">
                                                            <div class="panel-heading">
                                                                <h4 class="panel-title">
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="reset('voice')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_voice">
                                                                        voice
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_voice" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;passive <br><span class="df" id="passiveVoice-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="passiveVoice-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>


                                                        <div class="panel panel-success">
                                                            <div class="panel-heading">
                                                                <h4 class="panel-title">
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="reset('phrasalVerbs')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_phrasalVerbs">
                                                                        phrasal
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_phrasalVerbs" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;phrasal verbs <br><span class="df" id="phrasalVerbs-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="phrasalVerbs-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>


                                                        <div class="panel panel-success">
                                                            <div class="panel-heading">
                                                                <h4 class="panel-title">
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="reset('modals')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_modals">
                                                                        modal
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_modals" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;simple (can, must, need, may) <br><span class="df" id="simpleModals-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="simpleModals-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>


                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;advanced <br><span class="df" id="advancedModals-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="advancedModals-gradientSlider"></div>
                                                                        </div>
                                                                    </div>

                                                                </div>
                                                            </div>
                                                        </div>


                                                        <div class="panel panel-success">
                                                            <div class="panel-heading">
                                                                <h4 class="panel-title">
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="reset('transitive')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_transitive">
                                                                        transitive
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_transitive" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;transitive (drive a car) <br><span class="df" id="directObject-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="directObject-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;ditransitive (give it to me) <br><span class="df" id="indirectObject-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="indirectObject-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="panel panel-success">
                                                            <div class="panel-heading">
                                                                <h4 class="panel-title">
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="reset('imperative')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_imperative">
                                                                        imperatives
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_imperative" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;imperatives<br><span class="df" id="imperatives-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="imperatives-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>



                                                    </div>
                                                </div>
                                            </div>




                                            <div class="panel panel-info">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <button type="button" class="close" style="font-size: 12px" onclick="reset('negation')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_negation">
                                                            negation
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_negation" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;all negation (nothing, nowhere, no, etc.) <br><span class="df" id="negAll-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="negAll-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;partial negation (hardly, barely, scarcely, rarely, seldom) <br><span class="df" id="partialNegation-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="partialNegation-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;no, not, never <br><span class="df" id="noNotNever-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="noNotNever-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;not (full form) <br><span class="df" id="not-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="not-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;n't (contracted form) <br><span class="df" id="nt-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="nt-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                    </div>
                                                </div>
                                            </div>



                                            <div class="panel panel-info">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <button type="button" class="close" style="font-size: 12px" onclick="reset('articles')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_articles">
                                                            articles
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_articles" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;all articles <br><span class="df" id="articles-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="articles-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;a <br><span class="df" id="aArticle-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="aArticle-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;an <br><span class="df" id="anArticle-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="anArticle-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;the <br><span class="df" id="theArticle-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="theArticle-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="panel panel-info">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <button type="button" class="close" style="font-size: 12px" onclick="reset('quantifiers')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_quantifiers">
                                                            quantifiers
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_quantifiers" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div class="panel-body">
                                                            <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;some <br><span class="df" id="someDet-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="someDet-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;any <br><span class="df" id="anyDet-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="anyDet-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;much <br><span class="df" id="muchDet-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="muchDet-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;many <br><span class="df" id="manyDet-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="manyDet-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>


                                            <div class="panel panel-info">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <button type="button" class="close" style="font-size: 12px" onclick="reset('adjectives')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_adjectives">
                                                            adjectives
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_adjectives" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;positive (nice) <br><span class="df" id="positiveAdj-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="positiveAdj-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;comparative short (nicer) <br><span class="df" id="comparativeAdjShort-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="comparativeAdjShort-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;superlative short (nicest) <br><span class="df" id="superlativeAdjShort-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="superlativeAdjShort-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;comparative long (more difficult) <br><span class="df" id="comparativeAdjLong-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="comparativeAdjLong-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;superlative long (most difficult) <br><span class="df" id="superlativeAdjLong-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="superlativeAdjLong-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>






                                            <div class="panel panel-info">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <button type="button" class="close" style="font-size: 12px" onclick="reset('adverbs')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_adverbs">
                                                            adverbs
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_adverbs" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;positive (fast) <br><span class="df" id="positiveAdv-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="positiveAdv-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;comparative short (faster) <br><span class="df" id="comparativeAdvShort-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="comparativeAdvShort-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;superlative short (fastest) <br><span class="df" id="superlativeAdvShort-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="superlativeAdvShort-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;comparative long (more easily) <br><span class="df" id="comparativeAdvLong-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="comparativeAdvLong-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;superlative long (most easily) <br><span class="df" id="superlativeAdvLong-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="superlativeAdvLong-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>








                                            <div class="panel panel-info">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <button type="button" class="close" style="font-size: 12px" onclick="reset('pronouns')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_pronouns">
                                                            pronouns
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_pronouns" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;subject (I) <br><span class="df" id="pronounsSubjective-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pronounsSubjective-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;object (me)<br><span class="df" id="pronounsObjective-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pronounsObjective-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;possessive (my)<br><span class="df" id="pronounsPossessive-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pronounsPossessive-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;absolute possessive (mine)<br><span class="df" id="pronounsPossessiveAbsolute-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pronounsPossessiveAbsolute-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;reflexive (myself) <br><span class="df" id="pronounsReflexive-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pronounsReflexive-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="panel panel-info">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <button type="button" class="close" style="font-size: 12px" onclick="reset('conjunctions')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_conjunctions">
                                                            conjunctions
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_conjunctions" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;simple <br> (and, or, but, because, so) <br><span class="df" id="simpleConjunctions-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="simpleConjunctions-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;advanced <br> (therefore, until, besides, etc.) <br><span class="df" id="advancedConjunctions-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="advancedConjunctions-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>


                                            <div class="panel panel-info">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <button type="button" class="close" style="font-size: 12px" onclick="reset('prepositions')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_prepositions">
                                                            prepositions
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_prepositions" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;simple <br>(at, on, in, to, with, after) <br><span class="df" id="simplePrepositions-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="simplePrepositions-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;advanced <br><span class="df" id="complexPrepositions-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="complexPrepositions-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="panel panel-info">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <button type="button" class="close" style="font-size: 12px" onclick="reset('nouns')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_nouns">
                                                            nouns
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_nouns" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div class="panel-body">
                                                            <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;plural regular <br>(cars, flowers, etc.) <br><span class="df" id="pluralRegular-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pluralRegular-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;plural irregular <br>(children, women, etc.) <br><span class="df" id="pluralIrregular-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pluralIrregular-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="exclude(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;-ing forms <br>(skiing, being, etc. ALSO building BUT NOT king, something) <br><span class="df" id="ingNounForms-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="ingNounForms-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="panel panel-default" style="text-align: center">
                                    <a href="javascript:reset('all');" style="color:grey;">RESET ALL</a>
                                </div>


                                <br><br><hr>

                                <div id="report-problem">
                                    <span class="glyphicon glyphicon-envelope"></span><a href="mailto:maria.chinkina@gmail.com?Subject=Feedback%20(FLAIR)" target="_top" title="Report a problem or just share your feedback."> Report a problem</a>
                                </div>








                                <br><br><br><br><br><br>
                            </div>


                            <!--                            <button     type="button" id="show-btn"  data-loading-text="Searching..." class="btn btn-lg btn-warning" onclick="rerank()">Save & Rerank</button>-->

                        </div>

                    </div>

                </div>


                <!-- MAIN AREA -->
                <div id="page-content-wrapper">
                    <div class="mainArea" style="margin-top: 60px;"><br>
                        <!--                    <h1 class="text_heading" id="heading"><span id="display_query"></span></h1>-->

                        <div class="row">
                            <!-- show results here -->
                            <div class="col-md-8 results">
                                <table class="table table-hover" style="width:100%; margin-left:10px;padding-right:10px;">
                                    <tbody id="results_table">
                                        <% if (request != null &&
                                               request.getSession() != null &&
                                               request.getSession().getAttribute("output") != null) {%>
                                        <%= request.getSession().getAttribute("output")%>
                                        <% } else {%>
                                        <%= "1. Search <br>2. Parse <br>3. Explore"%>
                                        <%}%> 
                                    </tbody>
                                </table>
                            </div>

                        </div>


                        <!--            <div class="row" id="statistics" style="width:100%; background-color: white;" >
                                        <hr>

                                         d3 visualization??? graph!
                                         visualization: a small picture of a text with highlighted constructions!
                                         OR: show small pics as small multiples - next to each link, to show the distribution

                                        <div class="col-lg-3" style="text-align: left; padding-left: 25px;">
                                            <div>
                                                <span id="stats_popover" class="popover-element stats" data-html=true data-container="body" data-toggle="popover" data-placement="right" data-trigger="hover" data-content="Select a construction to see its distribution across the top N(40) results." > &nbsp; Show distribution: <span class="glyphicon glyphicon-question-sign"></span></span>
                                            </div>
                                            <div class="form-group">
                                                <select class="form-control" id="stats-select">
                                                    <option value="no_thanks" selected="selected">No, thanks</option>
                                                    <option value="yes_please">Yes, please</option>
                                                </select>
                                            </div>
                                                                <input id="slider" data-slider-id='slider1' data-slider-min="-5" data-slider-max="10" data-slider-step="1" data-slider-value="0" type="text" style="width:30%;"/>
                                        </div>

                                        <div class="col-lg-7">
                                            <div id="alertStatistics" class="alert alert-danger" style="width:60%;position: relative;" hidden></div>
                                        </div>

                                        <br><br><br><br><br>
                                    </div>

                                    <br>

                                     to actually show the statistics (and texts?)
                                    <div class="row" id="lowRow">
                                         show graphs
                                        <div class="col-md-9">
                                            <svg >
                                            <g class="graphArea" id="graph_multiples">
                                            </g>
                                            </svg>
                                        </div>
                                         nothing here
                                        <div class="col-md-2">
                                        </div>
                                    </div>-->

                        <!--                     REPORT A PROBLEM. Copyright.
                                            <div class="row">
                                                <div style="text-align: center; color:orange; margin-top:40px;" id="report-problem" class="col-md-2">
                                                    <span class="glyphicon glyphicon-envelope"></span><a href="mailto:maria.chinkina@gmail.com?Subject=Feedback%20(FLAIR)" target="_top" title="Report a problem or just share your feedback." style="color:orange;"> Report a problem</a>
                                                </div>
                                                <div style=" text-align: center; margin-left: 60px;" class="col-md-7 text_license">
                                                    <a rel="license" href="http://creativecommons.org/licenses/by-nc/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-nc/4.0/88x31.png" /></a><br/> <span xmlns:dct="http://purl.org/dc/terms/" href="http://purl.org/dc/dcmitype/InteractiveResource" property="dct:title" rel="dct:type">FLAIR</span> by <span xmlns:cc="http://creativecommons.org/ns#" property="cc:attributionName">Maria Chinkina</span><br/> is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc/4.0/">Creative Commons Attribution-NonCommercial 4.0 International License</a>.
                                                </div>
                                            </div>-->
                    </div>
                </div>
            </div>
        </div>

        <div id="sidebar-wrapper-right">
            <div  class="sidebar-nav-right" id="sidebar-right">
                <br><br><br><br>

                <div id="snapshot" style="padding-left: 5%; padding-right: 5%;">


                </div>

            </div>
        </div>



        <div class="modal fade bs-modal-sm" id="myModal_Login" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-body">
                        <div class="tab-pane fade active in">
                            <form class="form-horizontal" role="form" method="POST" action="LoginServletTeacher">
                                <fieldset>
                                    <center>
                                        <!-- Sign In Form -->
                                        <!-- Text input-->
                                        <div class="control-group">
                                            <label class="control-label" for="first_name">First name:</label>
                                            <div class="controls">
                                                <input required="" id="first_name" name="first_name" type="text" class="form-control input-medium" placeholder="John">
                                            </div>
                                        </div>

                                        <!-- Password input-->
                                        <div class="control-group">
                                            <label class="control-label" for="last_name">Last name:</label>
                                            <div class="controls">
                                                <input required="" id="last_name" name="last_name" class="form-control input-medium" type="text" placeholder="Smith">
                                            </div>
                                        </div>


                                        <div class="control-group">
                                            <label class="control-label" for="login"></label>
                                            <div class="controls">
                                                <button id="login" name="login" class="btn btn-success" >Log In</button>
                                            </div>
                                        </div>
                                    </center>

                                </fieldset>
                            </form>
                        </div>
                    </div>

                </div>
            </div>
        </div>


        <div class="modal fade modal-xl" id="myModal_Visualize" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-xl">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Select characteristics</h4>
                    </div>
                    <div class="modal-body">
                        <div class="tab-pane fade active in">
                            <center>
                                <svg >
                                </svg>
                            </center>

                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" onclick="filter_visual();">Apply</button>
                    </div>

                </div>
            </div>
        </div>



        <div class="modal fade modal-lg" id="myModal_Cache" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-xl">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <br>
                    </div>
                    <div class="modal-body">
                        <div class="tab-pane fade active in">
                            <h4 style="color:grey;text-align: center;">Recent searches</h4>
                            <div id="recent_searches">

                            </div>
                            <hr>
                            <h4 style="color:grey;text-align: center;">Common searches</h4>
                            <br>
                            <table id="results_cache">
                                <thead>
                                    <tr><td><b>People</b></td><td><b>Events</b></td></tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>

                        </div>
                    </div>

                </div>
            </div>
        </div>




        <div class="modal fade modal-lg" id="myModal_Parse" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <!--                        <h4 class="modal-title"></h4>-->
                    </div>
                    <div class="modal-body">
                        <div class="tab-pane fade active in">
                            <center>
                                <p>Would you like to get the information about grammatical constructions?</p>
                                <p>It might take about 10 minutes.</p>
                            </center>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">NO, thanks</button>
                        <button type="button" class="btn btn-primary" id="parse_button"  data-dismiss="modal">YES, please</button>
                    </div>

                </div>
            </div>
        </div>                           




        <div class="modal fade modal-lg" id="myModal_Extract" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <!--                        <h4 class="modal-title"></h4>-->
                    </div>
                    <div class="modal-body">
                        <div class="tab-pane fade active in">
                            <center>
                                <p>Would you like to get access to text?</p>
                                <p>It might take up to a minute.</p>
                            </center>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">NO, thanks</button>
                        <button type="button" class="btn btn-primary" id="extract_button"  data-dismiss="modal">YES, please</button>
                    </div>

                </div>
            </div>
        </div> 



        <script type="text/javascript">
            var CONTEXT_ROOT = "<%= request.getContextPath()%>";
        </script>


        <script type="text/javascript" src="js/libs/jquery/jquery.js"></script>
        <script type="text/javascript" src="js/libs/jquery/jquery.min.js"></script>


        <!--            <script src="//code.jquery.com/jquery-1.10.2.js"></script>-->
        <script src="js/libs/jqueryui/jquery-ui.js"></script>

        <script type="text/javascript" src="js/libs/twitter-bootstrap/js/bootstrap.js"></script>
        <script type="text/javascript" src="js/libs/twitter-bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/libs/twitter-bootstrap/js/bootstrap-confirmation.js"></script>

        <script type="text/javascript" src="js/libs/number-polyfill.js"></script>

        <script type="text/javascript" src="js/libs/d3/d3.js"></script>
        <script type="text/javascript" src="js/libs/d3/d3.v3.js"></script>
        <script type="text/javascript" src="js/libs/d3/d3.v3.min.js"></script>



        <script type="text/javascript" src="js/functions.js"></script>
        <script type="text/javascript" src="js/visualization.js"></script>

        <!--        <script type="text/javascript" src="js/libs/html2canvas.js"></script>-->





        <script type="text/javascript">




//                            $("#search_form").submit(function () {
//                                // first check the cache
//
//                                // search
//
//                            });

            <%-- if (request.getSession().getAttribute("stage") == null || ((String) request.getSession().getAttribute("stage")).isEmpty()) {
            %>

                                // check the cache
                                // simply crawl                            
                                return true;

// if crawled
            <%               } else if (request.getSession().getAttribute("stage") != null && ((String) request.getSession().getAttribute("stage")).equalsIgnoreCase("crawled")) {
            %>
                                //$("#search_button").click();
                                // show dialog window
                                $('#myModal_Extract').modal('show');
// if extracted
            <%
            } else if (request.getSession().getAttribute("stage") != null && ((String) request.getSession().getAttribute("stage")).equalsIgnoreCase("extracted")) {
            %>
                                $('#myModal_Parse').modal('show');
// if parsed
            <%  } else if (request.getSession().getAttribute("stage") != null && ((String) request.getSession().getAttribute("stage")).equalsIgnoreCase("parsed")) {
            %>
                                $("#sidebar_grammar_button").click(function (e) {
                                    e.preventDefault();
                                    toggle_left_sidebar(false);
                                });
            <% }--%>


//////////////////


            <%--  if (request != null && request.getSession() != null
                        && request.getSession().getAttribute("stage") != null
                        && (((String) request.getSession().getAttribute("stage")).equalsIgnoreCase("extracted")
                        || ((String) request.getSession().getAttribute("stage")).equalsIgnoreCase("crawled"))) {
                    // TODO: why ""?    
                    request.getSession().setAttribute("stage", "");
                }
            --%>


//////////////////
            // Settings Menu Toggle Script
            <%                if (request != null && request.getSession() != null
                        && request.getSession().getAttribute("stage") != null
                        && ((String) request.getSession().getAttribute("stage")).equalsIgnoreCase("parsed")) {
            %>
            $("#menu-toggle").click(function (e) {
                e.preventDefault();
                toggle_left_sidebar(false);
            });
            <% } else { %>
            $("#menu-toggle").click(function (e) {
                e.preventDefault();
                toggle_left_sidebar(true);
            });
            <% } %>

///////////////


            $("#right-menu-toggle").click(function (e) {
                e.preventDefault();
                $("#sidebar-wrapper-right").toggleClass("active");
                $("#page-content-wrapper").toggleClass("active");
            });
            $("#cache-toggle").click(function (e) {
                e.preventDefault();
                $("#myModal_Cache").modal('show');
            });
            $('.popover-element').popover(); // for hints
//                            $('#custom_smooth').inputNumber(); // for input numbers
//                            $('#stats-select').on('change', function () {
//                                // call a js method?
//                            });
//                            $(document).ready(function () {
            $('.dropdown-toggle').dropdown();
//                            });






            $('button[data-loading-text]')
                    .on('click', function () {
                        var btn = $(this);
                        btn.button('loading');
                        setTimeout(function () {
                            btn.button('reset');
                        }, 3000);
                    });

            if ($("#hello_name").html().trim().length < 2) {
                $(window).load(function () {
                    $('#myModal_Login').modal('show');
                });
            }
            ;



            <%--                if (request.getSession().getAttribute("docs_path") != null) {
                    docs_path = (String) request.getSession().getAttribute("docs_path");
            %>
                                window.onload = rerank(true, "<%=docs_path%>");
            <%
                }
            --%>




            <%--                if (request.getSession().getAttribute("stage") != null && ((String) request.getSession().getAttribute("stage")).equalsIgnoreCase("crawled")) {
            %>
                            //$("#search_button").click();
                            // show dialog window
                            $('#myModal_Extract').modal('show');
            <%
            } else if (request.getSession().getAttribute("stage") != null && ((String) request.getSession().getAttribute("stage")).equalsIgnoreCase("parsed")) {
            %>
                            $("#sidebar_grammar_button").click(function (e) {
                                e.preventDefault();
                                toggle_left_sidebar(false);
                            });
            <% }--%>

            // MAIN: get the docs path, read docs from there and re-rank
            <%
                if (request != null && request.getSession() != null
                        && request.getSession().getAttribute("docs_path") != null) {
                    docs_path = (String) request.getSession().getAttribute("docs_path");
            %>

            window.onload = rerank(true, "<%=docs_path%>");
            <%
                }
            %>



// confirm-buttons
            $("#extract_button").click(function (e) {
                $("#search_button").click();
            });
            $("#parse_button").click(function (e) {
                $("#search_button").click();
            });



        </script>
    </body>
</html>
