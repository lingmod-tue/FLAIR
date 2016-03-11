<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
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
        
        <link href="css/simple-sidebar.css" rel="stylesheet">
        <link href="css/simple-sidebar-right.css" rel="stylesheet">
        <link rel="stylesheet" href="css/dashboard.css" />
        <link rel="stylesheet" href="css/docs.min.css" />
        <link rel="stylesheet" href="css/number-polyfill.css" />
        <link rel="stylesheet" href="css/flair_visual.css" />


        <title>FLAIR</title>
    </head>

    <body style="background-color: white;">
        <div id="wrapper" >
            <div class="container">
                <div class="navbar navbar-default navbar-fixed-top" role="navigation">
                    <div class="container-fluid">
                        <div class="row"  style="margin-top: 2%;">
                            <div class="col-lg-3">
                                <a href="#menu-toggle" class="btn btn-warning" id="menu-toggle"><b>SETTINGS</b></a><br><br>
                            </div>
                            <div class="col-lg-6">
                                <form name="search_form" id="search_form" onkeypress="return event.keyCode != 13;">
                                    <div class="input-group">
                                        <input type="text" id="search_field" name="query" class="form-control">
                                        <div class="input-group-btn" >
                                            <button type="button" class="btn" onclick="FLAIR.WEBRANKER.singleton.beginSearch()" id="search_button" data-loading-text="<img style='height:18px;' src='img/ajax-loader.gif' alt='...'/>">GO</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="col-lg-3" style="text-align:right;"><span id="right-menu-toggle"><img src="img/glyphicons-517-menu-hamburger.png" alt=">"></span></div>
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
                                <a href="javascript:FLAIR.WEBRANKER.UTIL.TOGGLE.visualiserDialog(true)" style="color:orange" >VISUALIZE</a>
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
                                    <div><input type="checkbox" aria-label="A1-A2" onclick="FLAIR.WEBRANKER.singleton.refreshRanking()" id="LEVEL-a" checked> A1-A2
                                        <br><span class="df" id="LEVEL-a-df"></span><br>
                                    </div>
                                    <div><input type="checkbox" aria-label="B1-B2" onclick="FLAIR.WEBRANKER.singleton.refreshRanking()" id="LEVEL-b" checked> B1-B2
                                        <br><span class="df" id="LEVEL-b-df"></span><br>
                                    </div>
                                    <div><input type="checkbox" aria-label="C1-C2" onclick="FLAIR.WEBRANKER.singleton.refreshRanking()" id="LEVEL-c" checked> C1-C2
                                        <br><span class="df" id="LEVEL-c-df"></span><br>
                                    </div>
                                </div>
                            </div>
                            <hr>                            
                            <div id="settings_panel">
                                <!-- a list of all constructions -->
                                <div style="text-align: center;">
                                    <a href="#constructs-toggle" id="constructs-toggle"><b>LIST OF CONSTRUCTIONS</b></a><br><br>
                                </div>
                                
                                <div class="panel panel-warning">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('sentences')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('questions')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_questions">
                                                            questions
                                                        </a>

                                                    </h4>
                                                </div>

                                                <div id="collapse_questions" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;direct questions<br><span class="df" id="directQuestions-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="directQuestions-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;wh- questions
                                                            <br><span class="df" id="whQuestions-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="whQuestions-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;do- questions<br><span class="df" id="toDoQuestions-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="toDoQuestions-gradientSlider"></div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;be- questions<br><span class="df" id="toBeQuestions-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="toBeQuestions-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;have- questions<br><span class="df" id="toHaveQuestions-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="toHaveQuestions-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;yes/no questions<br><span class="df" id="yesNoQuestions-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="yesNoQuestions-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;tag questions<br><span class="df" id="tagQuestions-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('structure')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_structure">
                                                            sentence types
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_structure" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;simple <br><span class="df" id="simpleSentence-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="simpleSentence-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;coordinate <br><span class="df" id="compoundSentence-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="compoundSentence-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;subordinate <br><span class="df" id="subordinateClause-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="subordinateClause-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;incomplete sentences <br><span class="df" id="incompleteSentence-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('clauses')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_clauses">
                                                            clause types
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_clauses" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;relative<br><span class="df" id="relativeClause-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="relativeClause-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;adverbial<br><span class="df" id="adverbialClause-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="adverbialClause-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;real conditional<br><span class="df" id="condReal-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="condReal-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;unreal conditional<br><span class="df" id="condUnreal-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="condUnreal-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;there is/are <br><span class="df" id="thereIsAre-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="thereIsAre-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;there was/were <br><span class="df" id="thereWasWere-df"></span>
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
                                            <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('pos')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('verbs')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
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
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('verbForms')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_verbForms">
                                                                        verb forms
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_verbForms" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;contracted (to be and to have: 'm, 's, 'd) <br><span class="df" id="shortVerbForms-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="shortVerbForms-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;full (to be and to have: is, are, had) <br><span class="df" id="longVerbForms-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="longVerbForms-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;auxiliaries (to be and to have: short and full forms) <br><span class="df" id="auxiliariesBeDoHave-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="auxiliariesBeDoHave-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;copula (be, seem, look, stay, etc.: "She looks upset.") <br><span class="df" id="copularVerbs-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="copularVerbs-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;-ing (gerund and present participle) <br><span class="df" id="ingVerbForms-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="ingVerbForms-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;to- infinitive <br><span class="df" id="toInfinitiveForms-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="toInfinitiveForms-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;emphatic do ("I did tell the truth") <br><span class="df" id="emphaticDo-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="emphaticDo-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;irregular (2nd and 3rd form) <br><span class="df" id="irregularVerbs-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="irregularVerbs-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;regular (2nd and 3rd form) <br><span class="df" id="regularVerbs-df"></span>
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
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('tenses')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_tenses">
                                                                        tenses
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_tenses" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Present Simple <br><span class="df" id="presentSimple-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="presentSimple-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Present Progressive <br><span class="df" id="presentProgressive-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="presentProgressive-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Past Simple <br><span class="df" id="pastSimple-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pastSimple-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Past Progressive <br><span class="df" id="pastProgressive-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pastProgressive-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Future Simple <br><span class="df" id="futureSimple-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="futureSimple-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Future Progressive <br><span class="df" id="futureProgressive-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="futureProgressive-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Present Perfect <br><span class="df" id="presentPerfect-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="presentPerfect-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Past Perfect <br><span class="df" id="pastPerfect-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pastPerfect-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Future Perfect <br><span class="df" id="futurePerfect-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="futurePerfect-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Present Perfect Progressive <br><span class="df" id="presentPerfProg-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="presentPerfProg-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Past Perfect Progressive <br><span class="df" id="pastPerfProg-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pastPerfProg-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Future Perfect Progressive <br><span class="df" id="futurePerfProg-df"></span>
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
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('aspects')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_aspects">
                                                                        aspect
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_aspects" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;simple <br><span class="df" id="simpleAspect-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="simpleAspect-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;progressive <br><span class="df" id="progressiveAspect-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="progressiveAspect-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;perfect <br><span class="df" id="perfectAspect-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="perfectAspect-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;perfect progressive <br><span class="df" id="perfProgAspect-df"></span>
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
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('times')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_times">
                                                                        time
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_times" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;present <br><span class="df" id="presentTime-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="presentTime-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;past <br><span class="df" id="pastTime-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pastTime-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;future <br><span class="df" id="futureTime-df"></span>
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
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('voice')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_voice">
                                                                        voice
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_voice" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;passive <br><span class="df" id="passiveVoice-df"></span>
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
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('phrasalVerbs')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_phrasalVerbs">
                                                                        phrasal
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_phrasalVerbs" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;phrasal verbs <br><span class="df" id="phrasalVerbs-df"></span>
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
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('modals')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_modals">
                                                                        modal
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_modals" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;simple (can, must, need, may) <br><span class="df" id="simpleModals-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="simpleModals-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>


                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;advanced <br><span class="df" id="advancedModals-df"></span>
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
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('transitive')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_transitive">
                                                                        transitive
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_transitive" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;transitive (drive a car) <br><span class="df" id="directObject-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="directObject-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;ditransitive (give it to me) <br><span class="df" id="indirectObject-df"></span>
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
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('imperative')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_imperative">
                                                                        imperatives
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_imperative" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;imperatives<br><span class="df" id="imperatives-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('negation')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_negation">
                                                            negation
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_negation" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;all negation (nothing, nowhere, no, etc.) <br><span class="df" id="negAll-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="negAll-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;partial negation (hardly, barely, scarcely, rarely, seldom) <br><span class="df" id="partialNegation-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="partialNegation-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;no, not, never <br><span class="df" id="noNotNever-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="noNotNever-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;not (full form) <br><span class="df" id="not-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="not-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;n't (contracted form) <br><span class="df" id="nt-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('articles')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_articles">
                                                            articles
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_articles" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;all articles <br><span class="df" id="articles-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="articles-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;a <br><span class="df" id="aArticle-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="aArticle-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;an <br><span class="df" id="anArticle-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="anArticle-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;the <br><span class="df" id="theArticle-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('quantifiers')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_quantifiers">
                                                            quantifiers
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_quantifiers" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div class="panel-body">
                                                            <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;some <br><span class="df" id="someDet-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="someDet-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;any <br><span class="df" id="anyDet-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="anyDet-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;much <br><span class="df" id="muchDet-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="muchDet-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;many <br><span class="df" id="manyDet-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('adjectives')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_adjectives">
                                                            adjectives
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_adjectives" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;positive (nice) <br><span class="df" id="positiveAdj-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="positiveAdj-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;comparative short (nicer) <br><span class="df" id="comparativeAdjShort-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="comparativeAdjShort-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;superlative short (nicest) <br><span class="df" id="superlativeAdjShort-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="superlativeAdjShort-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;comparative long (more difficult) <br><span class="df" id="comparativeAdjLong-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="comparativeAdjLong-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;superlative long (most difficult) <br><span class="df" id="superlativeAdjLong-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('adverbs')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_adverbs">
                                                            adverbs
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_adverbs" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;positive (fast) <br><span class="df" id="positiveAdv-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="positiveAdv-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;comparative short (faster) <br><span class="df" id="comparativeAdvShort-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="comparativeAdvShort-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;superlative short (fastest) <br><span class="df" id="superlativeAdvShort-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="superlativeAdvShort-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;comparative long (more easily) <br><span class="df" id="comparativeAdvLong-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="comparativeAdvLong-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;superlative long (most easily) <br><span class="df" id="superlativeAdvLong-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('pronouns')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_pronouns">
                                                            pronouns
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_pronouns" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;subject (I) <br><span class="df" id="pronounsSubjective-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pronounsSubjective-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;object (me)<br><span class="df" id="pronounsObjective-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pronounsObjective-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;possessive (my)<br><span class="df" id="pronounsPossessive-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pronounsPossessive-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;absolute possessive (mine)<br><span class="df" id="pronounsPossessiveAbsolute-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pronounsPossessiveAbsolute-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;reflexive (myself) <br><span class="df" id="pronounsReflexive-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('conjunctions')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_conjunctions">
                                                            conjunctions
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_conjunctions" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;simple <br> (and, or, but, because, so) <br><span class="df" id="simpleConjunctions-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="simpleConjunctions-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;advanced <br> (therefore, until, besides, etc.) <br><span class="df" id="advancedConjunctions-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('prepositions')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_prepositions">
                                                            prepositions
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_prepositions" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;simple <br>(at, on, in, to, with, after) <br><span class="df" id="simplePrepositions-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="simplePrepositions-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;advanced <br><span class="df" id="complexPrepositions-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="FLAIR.WEBRANKER.UTIL.resetSlider('nouns')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_nouns">
                                                            nouns
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_nouns" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div class="panel-body">
                                                            <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;plural regular <br>(cars, flowers, etc.) <br><span class="df" id="pluralRegular-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pluralRegular-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;plural irregular <br>(children, women, etc.) <br><span class="df" id="pluralIrregular-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pluralIrregular-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;-ing forms <br>(skiing, being, etc. ALSO building BUT NOT king, something) <br><span class="df" id="ingNounForms-df"></span>
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
                                    <a href="javascript:FLAIR.WEBRANKER.singleton.resetAllSettingsAndFilters(true, true, true, true);" style="color:grey;">RESET ALL</a>
                                </div>


                                <br><br><hr>

                                <div id="report-problem" hidden>
                                    <span class="glyphicon glyphicon-envelope"></span><a href="mailto:maria.chinkina@gmail.com?Subject=Feedback%20(FLAIR)" target="_top" title="Report a problem or just share your feedback."> Report a problem</a>
                                </div>

                                <br><br><br><br><br><br>
                            </div>
                        </div>
                    </div>
                </div>


                <!-- MAIN AREA -->
                <div id="page-content-wrapper">
                    <div class="mainArea" style="margin-top: 60px;"><br>
                        <div class="row">
                            <!-- show results here -->
                            <div class="col-md-8 results">
                                <table class="table table-hover" style="width:100%; margin-left:10px;padding-right:10px;">
                                    <tbody id="results_table">
                                    </tbody>
                                </table>
                            </div>
                        </div>
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

        <div class="modal fade modal-xl" id="myModal_Visualize" tabindex="-1" role="dialog" aria-hidden="true" style="margin:0 auto;">
            <div class="modal-dialog modal-xl">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <center><h3 class="modal-title">Interactive visualization of results </h3>
                            | <b>select ranges</b> by dragging the pointers up and down |<br>
                            | <b>change the order of axes</b> by dragging them left and right |<br>
                            | <b>add or remove axes</b> via checkboxes on the right |
                        </center>
                    </div>
                    <div class="modal-body">
                        <div class="tab-pane fade active in">
                            <div class="row">
                                <div class="col-md-8">
                                    <svg id="svg_area">
                                    </svg>
                                </div>
                                <div class="col-md-1">&nbsp;</div>
                                <div class="col-md-3" style="border-left:1px lightgrey solid; padding-left: 20px;">
                                    <div id="list_of_constructs_vis" style="height:370px;overflow-y: scroll;">
                                        <div style="text-align: right;">
                                            <a href="javascript:FLAIR.WEBRANKER.singleton.resetAllSettingsAndFilters(false, false, true, true);" style="color:grey;">RESET ALL <span class="glyphicon glyphicon-erase" title="reset"></span></a>

                                        </div>
                                        <table class="table table-hover table-condensed">
                                            <tbody>
                                                <tr class="active">
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="document-def-vis" checked>
                                                        result
                                                    </td>
                                                </tr>
                                                <tr class="active">
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="sentences-def-vis" checked>
                                                        # of sentences
                                                    </td>
                                                </tr>
                                                <tr class="active">
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="words-def-vis" checked>
                                                        # of words
                                                    </td>
                                                </tr>
                                                <tr class="active">
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="score-def-vis" checked>
                                                        complexity
                                                    </td>
                                                </tr>
                                                <tr><td></td></tr>
                                                <tr class="warning">
                                                    <td>
                                                        SENTENCES
                                                    </td>

                                                </tr>

                                                <tr class="info">
                                                    <td>
                                                        SENTENCES&nbsp;&nbsp;>&nbsp;&nbsp;Questions
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="directQuestions-vis">
                                                        all questions
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="whQuestions-vis">
                                                        wh- questions
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="toDoQuestions-vis">
                                                        do- questions
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="toBeQuestions-vis">
                                                        be- questions
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="toHaveQuestions-vis">
                                                        have- questions
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="yesNoQuestions-vis">
                                                        yes/no questions
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="tagQuestions-vis">
                                                        tag questions
                                                    </td>

                                                </tr>

                                                <tr class="info">
                                                    <td>
                                                        SENTENCES&nbsp;&nbsp;>&nbsp;&nbsp;Sentence types
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="simpleSentence-vis">
                                                        simple
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="compoundSentence-vis">
                                                        coordinate
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="subordinateClause-vis">
                                                        subordinate
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="incompleteSentence-vis">
                                                        incomplete
                                                    </td>

                                                </tr>

                                                <tr class="info">
                                                    <td>
                                                        SENTENCES&nbsp;&nbsp;>&nbsp;&nbsp;Clause types
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="relativeClause-vis">
                                                        relative
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="adverbialClause-vis">
                                                        adverbial
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="condReal-vis">
                                                        real conditional (0, I)
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="condUnreal-vis">
                                                        unreal conditional (II, III)
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="thereIsAre-vis">
                                                        there is/are
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="thereWasWere-vis">
                                                        there was/were
                                                    </td>

                                                </tr>
                                                <tr><td></td></tr>
                                                <tr class="warning">
                                                    <td>
                                                        PARTS OF SPEECH
                                                    </td>

                                                </tr>
                                                <tr class="info">
                                                    <td>
                                                        PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Verbs
                                                    </td>

                                                </tr>
                                                <tr class="success">
                                                    <td>
                                                        PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Verbs&nbsp;&nbsp;>&nbsp;&nbsp;verb forms
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="shortVerbForms-vis">
                                                        contracted (be, have)
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="longVerbForms-vis">
                                                        full (be, have)
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="auxiliariesBeDoHave-vis">
                                                        auxiliaries (be, have)
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="copularVerbs-vis">
                                                        copula (be, seem, look, etc.)
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="ingVerbForms-vis">
                                                        -ing forms (gerund, present participle)
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="toInfinitiveForms-vis">
                                                        to- infinitive
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="emphaticDo-vis">
                                                        emphatic "do"
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="irregularVerbs-vis">
                                                        irregular verbs (2nd, 3rd form)
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="regularVerbs-vis">
                                                        regular verbs (2nd, 3rd form)
                                                    </td>

                                                </tr>

                                                <tr class="success">
                                                    <td>
                                                        PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Verbs&nbsp;&nbsp;>&nbsp;&nbsp;tenses
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="presentSimple-vis">
                                                        Present Simple
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="presentProgressive-vis">
                                                        Present Progressive
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="pastSimple-vis">
                                                        Past Simple
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="pastProgressive-vis">
                                                        Past Progressive
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="futureSimple-vis">
                                                        Future Simple
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="futureProgressive-vis">
                                                        Future Progressive
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="presentPerfect-vis">
                                                        Present Perfect
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="pastPerfect-vis">
                                                        Past Perfect
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="futurePerfect-vis">
                                                        Future Perfect
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="presentPerfProg-vis">
                                                        Present Perfect Progressive
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="pastPerfProg-vis">
                                                        Past Perfect Progressive
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="futurePerfProg-vis">
                                                        Future Perfect Progressive
                                                    </td>

                                                </tr>

                                                <tr class="success">
                                                    <td>
                                                        PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Verbs&nbsp;&nbsp;>&nbsp;&nbsp;aspect
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="simpleAspect-vis">
                                                        simple
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="progressiveAspect-vis">
                                                        progressive
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="perfectAspect-vis">
                                                        perfect
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="perfProgAspect-vis">
                                                        perfect progressive
                                                    </td>

                                                </tr>

                                                <tr class="success">
                                                    <td>
                                                        PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Verbs&nbsp;&nbsp;>&nbsp;&nbsp;time
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="presentTime-vis">
                                                        present
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="pastTime-vis">
                                                        past
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="futureTime-vis">
                                                        future
                                                    </td>

                                                </tr>

                                                <tr class="success">
                                                    <td>
                                                        PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Verbs&nbsp;&nbsp;>&nbsp;&nbsp;voice
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="passiveVoice-vis">
                                                        passive
                                                    </td>

                                                </tr>

                                                <tr class="success">
                                                    <td>
                                                        PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Verbs&nbsp;&nbsp;>&nbsp;&nbsp;phrasal
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="phrasalVerbs-vis">
                                                        phrasal
                                                    </td>

                                                </tr>

                                                <tr class="success">
                                                    <td>
                                                        PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Verbs&nbsp;&nbsp;>&nbsp;&nbsp;modal
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="simpleModals-vis">
                                                        simple
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="advancedModals-vis">
                                                        advanced
                                                    </td>

                                                </tr>

                                                <tr class="success">
                                                    <td>
                                                        PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Verbs&nbsp;&nbsp;>&nbsp;&nbsp;transitive
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="directObject-vis">
                                                        transitive
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="indirectObject-vis">
                                                        ditransitive
                                                    </td>

                                                </tr>

                                                <tr class="success">
                                                    <td>
                                                        PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Verbs&nbsp;&nbsp;>&nbsp;&nbsp;imperatives
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="imperatives-vis">
                                                        imperatives
                                                    </td>

                                                </tr>


                                                <tr class="info">
                                                    <td>
                                                        PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Negation
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="negAll-vis">
                                                        full 
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="partialNegation-vis">
                                                        partial 
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="noNotNever-vis">
                                                        no, not, never
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="not-vis">
                                                        not (full form)
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="nt-vis">
                                                        n't (contracted form)
                                                    </td>

                                                </tr>

                                                <tr class="info">
                                                    <td>
                                                        PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Articles
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="articles-vis">
                                                        all 
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="aArticle-vis">
                                                        a
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="anArticle-vis">
                                                        an
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="theArticle-vis">
                                                        the
                                                    </td>

                                                </tr>

                                                <tr class="info">
                                                    <td>
                                                        PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Quantifiers
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="someDet-vis">
                                                        some
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="anyDet-vis">
                                                        any
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="muchDet-vis">
                                                        much
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="manyDet-vis">
                                                        many
                                                    </td>

                                                </tr>

                                                <tr class="info">
                                                    <td>
                                                        PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Adjectives
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="positiveAdj-vis">
                                                        positive
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="comparativeAdjShort-vis">
                                                        comparative short
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="superlativeAdjShort-vis">
                                                        superlative short
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="comparativeAdjLong-vis">
                                                        comparative long
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="superlativeAdjLong-vis">
                                                        superlative long
                                                    </td>

                                                </tr>

                                                <tr class="info">
                                                    <td>
                                                        PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Adverbs
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="positiveAdv-vis">
                                                        positive
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="comparativeAdvShort-vis">
                                                        comparative short
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="superlativeAdvShort-vis">
                                                        superlative short
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="comparativeAdvLong-vis">
                                                        comparative long
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="superlativeAdvLong-vis">
                                                        superlative long
                                                    </td>

                                                </tr>

                                                <tr class="info">
                                                    <td>
                                                        PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Pronouns
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="pronounsSubjective-vis">
                                                        subjective 
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="pronounsObjective-vis">
                                                        objective 
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="pronounsPossessive-vis">
                                                        possessive 
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="pronounsPossessiveAbsolute-vis">
                                                        absolute possessive
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="pronounsReflexive-vis">
                                                        reflexive 
                                                    </td>

                                                </tr>

                                                <tr class="info">
                                                    <td>
                                                        PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Conjunctions
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="simpleConjunctions-vis">
                                                        simple 
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="advancedConjunctions-vis">
                                                        advanced
                                                    </td>

                                                </tr>

                                                <tr class="info">
                                                    <td>
                                                        PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Prepositions
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="simplePrepositions-vis">
                                                        simple
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="complexPrepositions-vis">
                                                        advanced
                                                    </td>

                                                </tr>

                                                <tr class="info">
                                                    <td>
                                                        PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Nouns
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="pluralRegular-vis">
                                                        plural regular 
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="pluralIrregular-vis">
                                                        plural irregular
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td class="construct"> <input type="checkbox" onclick="FLAIR.WEBRANKER.singleton.toggleVisualiserAxis(this)" title="check/uncheck to add/remove an axis" id="ingNounForms-vis">
                                                        -ing forms
                                                    </td>

                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <center>
                            <button type="button" class="btn btn-primary" onclick="FLAIR.WEBRANKER.singleton.applyVisualiserFilter()">Apply</button>
                        </center>
                    </div>
                </div>
            </div>
        </div>


        <div class="modal fade modal-lg" id="modal_WaitIdle" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false" style="margin:0 auto;">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <center>
                    <div class="modal-body">
                        <br><img style='height:30px;' src='img/ajax-loader.gif' alt='...'/>&nbsp;&nbsp;&nbsp;
                        <div class="tab-pane fade active in" id="modal_waitIdle_body">
                                <p>Please wait while we process your meat...</p>
                        </div>
                    </div>
                    </center>
                    <div class="modal-footer">
                        <div style="text-align: center;">
                            <button type="button" class="btn btn-primary" id="modal_waitIdle_buttonCancel" onclick="FLAIR.WEBRANKER.singleton.cancelOperation()">Cancel</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
         <div class="modal fade modal-lg" id="myModal_Constructs" tabindex="-1" role="dialog" aria-hidden="true" style="margin:0 auto;">
            <div class="modal-dialog modal-xl">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <br>
                    </div>
                    <div class="modal-body">
                        <div class="tab-pane fade active in">
                            <h4 style="color:grey;text-align: center;">List of constructions</h4>
                            <div id="list_of_constructs">
                                <br>
                                <table class="table table-hover">
                                    <tbody>
                                        <tr class="warning">
                                            <td>
                                                SENTENCES
                                            </td>
                                            <td>

                                            </td>
                                        </tr>

                                        <tr class="info">
                                            <td>
                                                SENTENCES&nbsp;&nbsp;>&nbsp;&nbsp;Questions
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                all questions
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                wh- questions
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                do- questions
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                be- questions
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                have- questions
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                yes/no questions
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                tag questions
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>

                                        <tr class="info">
                                            <td>
                                                SENTENCES&nbsp;&nbsp;>&nbsp;&nbsp;Sentence types
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                simple
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                coordinate
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                subordinate
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                incomplete
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>

                                        <tr class="info">
                                            <td>
                                                SENTENCES&nbsp;&nbsp;>&nbsp;&nbsp;Clause types
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                relative
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                adverbial
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                real conditional (0, I)
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                unreal conditional (II, III)
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                there is/are
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                there was/were
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <br><br>
                                <table class="table table-hover">
                                    <tbody>
                                        <tr class="warning">
                                            <td>
                                                PARTS OF SPEECH
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="info">
                                            <td>
                                                PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Verbs
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="success">
                                            <td>
                                                PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Verbs&nbsp;&nbsp;>&nbsp;&nbsp;verb forms
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                contracted (be, have)
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                full (be, have)
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                auxiliaries (be, have)
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                copula (be, seem, look, etc.)
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                -ing forms (gerund, present participle)
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                to- infinitive
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                emphatic "do"
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                irregular verbs (2nd, 3rd form)
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                regular verbs (2nd, 3rd form)
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>

                                        <tr class="success">
                                            <td>
                                                PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Verbs&nbsp;&nbsp;>&nbsp;&nbsp;tenses
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                Present Simple
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                Present Progressive
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                Past Simple
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                Past Progressive
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                Future Simple
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                Future Progressive
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                Present Perfect
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                Past Perfect
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                Future Perfect
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                Present Perfect Progressive
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                Past Perfect Progressive
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                Future Perfect Progressive
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>

                                        <tr class="success">
                                            <td>
                                                PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Verbs&nbsp;&nbsp;>&nbsp;&nbsp;aspect
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                simple
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                progressive
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                perfect
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                perfect progressive
                                            </td>
                                            <td class="example"></td>
                                        </tr>

                                        <tr class="success">
                                            <td>
                                                PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Verbs&nbsp;&nbsp;>&nbsp;&nbsp;time
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                present
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                past
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                future
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>

                                        <tr class="success">
                                            <td>
                                                PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Verbs&nbsp;&nbsp;>&nbsp;&nbsp;voice
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                passive
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>

                                        <tr class="success">
                                            <td>
                                                PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Verbs&nbsp;&nbsp;>&nbsp;&nbsp;phrasal
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                phrasal
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>

                                        <tr class="success">
                                            <td>
                                                PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Verbs&nbsp;&nbsp;>&nbsp;&nbsp;modal
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                simple
                                            </td>
                                            <td class="example">
                                                can, must, need, may
                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                advanced
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>

                                        <tr class="success">
                                            <td>
                                                PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Verbs&nbsp;&nbsp;>&nbsp;&nbsp;transitive
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                transitive
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                ditransitive
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>

                                        <tr class="success">
                                            <td>
                                                PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Verbs&nbsp;&nbsp;>&nbsp;&nbsp;imperatives
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                imperatives
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>


                                        <tr class="info">
                                            <td>
                                                PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Negation
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                full 
                                            </td>
                                            <td class="example">
                                                nothing, nowhere, no, etc.
                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                partial 
                                            </td>
                                            <td class="example">
                                                hardly, barely, rarely, seldom, etc.
                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                no, not, never
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                not (full form)
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                n't (contracted form)
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>

                                        <tr class="info">
                                            <td>
                                                PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Articles
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                all 
                                            </td>
                                            <td class="example">
                                                a, an, the
                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                a
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                an
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                the
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>

                                        <tr class="info">
                                            <td>
                                                PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Quantifiers
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                some
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                any
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                much
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                many
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>

                                        <tr class="info">
                                            <td>
                                                PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Adjectives
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                positive
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                comparative short
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                superlative short
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                comparative long
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                superlative long
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>

                                        <tr class="info">
                                            <td>
                                                PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Adverbs
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                positive
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                comparative short
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                superlative short
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                comparative long
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                superlative long
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>

                                        <tr class="info">
                                            <td>
                                                PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Pronouns
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                subjective 
                                            </td>
                                            <td class="example">
                                                I
                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                objective 
                                            </td>
                                            <td class="example">
                                                me
                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                possessive 
                                            </td>
                                            <td class="example">
                                                my
                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                absolute possessive
                                            </td>
                                            <td class="example">
                                                mine
                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                reflexive 
                                            </td>
                                            <td class="example">
                                                myself
                                            </td>
                                        </tr>

                                        <tr class="info">
                                            <td>
                                                PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Conjunctions
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                simple 
                                            </td>
                                            <td class="example">
                                                and, or, but, because, so
                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                advanced
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>

                                        <tr class="info">
                                            <td>
                                                PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Prepositions
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                simple
                                            </td>
                                            <td class="example">
                                                at, on, in, to, with, after
                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                advanced
                                            </td>
                                            <td class="example">

                                            </td>
                                        </tr>

                                        <tr class="info">
                                            <td>
                                                PARTS OF SPEECH&nbsp;&nbsp;>&nbsp;&nbsp;Nouns
                                            </td>
                                            <td>

                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                plural regular 
                                            </td>
                                            <td class="example">
                                                cars, flowers, etc.
                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                plural irregular
                                            </td>
                                            <td class="example">
                                                children, women, etc.
                                            </td>
                                        </tr>
                                        <tr class="simple_row">
                                            <td class="construct">
                                                -ing forms
                                            </td>
                                            <td class="example">
                                                skiing, building, etc. BUT NOT! king, something
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>



        <script type="text/javascript" src="js/libs/jquery/jquery.js"></script>
        <script type="text/javascript" src="js/libs/jquery/jquery.min.js"></script>
        <script type="text/javascript" src="js/libs/jqueryui/jquery-ui.js"></script>
        <script type="text/javascript" src="js/libs/tablesorter/jquery.tablesorter.js"></script>
        
        <script type="text/javascript" src="js/libs/twitter-bootstrap/js/bootstrap.js"></script>
        <script type="text/javascript" src="js/libs/twitter-bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/libs/twitter-bootstrap/js/bootstrap-confirmation.js"></script>
        
        <script type="text/javascript" src="js/libs/number-polyfill.js"></script>
        <script type="text/javascript" src="js/libs/d3/d3.js"></script>
        <script type="text/javascript" src="js/libs/d3/d3.v3.js"></script>
        <script type="text/javascript" src="js/libs/d3/d3.v3.min.js"></script>

        <script type="text/javascript" src="js/flair-core.js"></script>
	<script type="text/javascript" src="js/flair-plumbing.js"></script>
        <script type="text/javascript" src="js/flair-webranker.js"></script>
    </body>
</html>
