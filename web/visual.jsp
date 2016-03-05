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

    <body  style="background-color: white;">
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
                                            <button type="button" class="btn" onclick="webRanker_beginSearch()" id="search_button" data-loading-text="<img style='height:18px;' src='img/ajax-loader.gif' alt='...'/>">GO</button>
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
                                <a href="javascript:void();" style="color:orange" >VISUALIZE</a>
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
                                    <div><input type="checkbox" aria-label="A1-A2" onclick="webRanker_refreshRanking()" id="LEVEL-a" checked> A1-A2
                                        <br><span class="df" id="LEVEL-a-df"></span><br>
                                    </div>
                                    <div><input type="checkbox" aria-label="B1-B2" onclick="webRanker_refreshRanking()" id="LEVEL-b" checked> B1-B2
                                        <br><span class="df" id="LEVEL-b-df"></span><br>
                                    </div>
                                    <div><input type="checkbox" aria-label="C1-C2" onclick="webRanker_refreshRanking()" id="LEVEL-c" checked> C1-C2
                                        <br><span class="df" id="LEVEL-c-df"></span><br>
                                    </div>
                                </div>
                            </div>
                            <hr>
                            <div id="settings_panel">
                                <div class="panel panel-default" style="text-align: center">
                                    <a href="javascript:webRanker_internal_resetSlider('all');" style="color:grey;">RESET ALL</a>
                                </div>
                                <div class="panel panel-warning">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('sentences')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('questions')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_questions">
                                                            questions
                                                        </a>

                                                    </h4>
                                                </div>

                                                <div id="collapse_questions" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;direct questions<br><span class="df" id="directQuestions-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="directQuestions-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;wh- questions
                                                            <br><span class="df" id="whQuestions-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="whQuestions-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;do- questions<br><span class="df" id="toDoQuestions-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="toDoQuestions-gradientSlider"></div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;be- questions<br><span class="df" id="toBeQuestions-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="toBeQuestions-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;have- questions<br><span class="df" id="toHaveQuestions-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="toHaveQuestions-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;yes/no questions<br><span class="df" id="yesNoQuestions-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="yesNoQuestions-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;tag questions<br><span class="df" id="tagQuestions-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('structure')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_structure">
                                                            sentence types
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_structure" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;simple <br><span class="df" id="simpleSentence-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="simpleSentence-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;coordinate <br><span class="df" id="compoundSentence-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="compoundSentence-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;subordinate <br><span class="df" id="subordinateClause-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="subordinateClause-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;incomplete sentences <br><span class="df" id="incompleteSentence-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('clauses')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_clauses">
                                                            clause types
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_clauses" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;relative<br><span class="df" id="relativeClause-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="relativeClause-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;adverbial<br><span class="df" id="adverbialClause-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="adverbialClause-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;real conditional<br><span class="df" id="condReal-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="condReal-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;unreal conditional<br><span class="df" id="condUnreal-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="condUnreal-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;there is/are <br><span class="df" id="thereIsAre-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="thereIsAre-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;there was/were <br><span class="df" id="thereWasWere-df"></span>
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
                                            <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('pos')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('verbs')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
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
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('verbForms')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_verbForms">
                                                                        verb forms
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_verbForms" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;contracted (to be and to have: 'm, 's, 'd) <br><span class="df" id="shortVerbForms-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="shortVerbForms-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;full (to be and to have: is, are, had) <br><span class="df" id="longVerbForms-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="longVerbForms-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;auxiliaries (to be and to have: short and full forms) <br><span class="df" id="auxiliariesBeDoHave-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="auxiliariesBeDoHave-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;copula (be, seem, look, stay, etc.: "She looks upset.") <br><span class="df" id="copularVerbs-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="copularVerbs-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;-ing (gerund and present participle) <br><span class="df" id="ingVerbForms-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="ingVerbForms-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;to- infinitive <br><span class="df" id="toInfinitiveForms-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="toInfinitiveForms-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;emphatic do ("I did tell the truth") <br><span class="df" id="emphaticDo-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="emphaticDo-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;irregular (2nd and 3rd form) <br><span class="df" id="irregularVerbs-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="irregularVerbs-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;regular (2nd and 3rd form) <br><span class="df" id="regularVerbs-df"></span>
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
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('tenses')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_tenses">
                                                                        tenses
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_tenses" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Present Simple <br><span class="df" id="presentSimple-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="presentSimple-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Present Progressive <br><span class="df" id="presentProgressive-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="presentProgressive-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Past Simple <br><span class="df" id="pastSimple-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pastSimple-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Past Progressive <br><span class="df" id="pastProgressive-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pastProgressive-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Future Simple <br><span class="df" id="futureSimple-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="futureSimple-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Future Progressive <br><span class="df" id="futureProgressive-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="futureProgressive-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Present Perfect <br><span class="df" id="presentPerfect-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="presentPerfect-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Past Perfect <br><span class="df" id="pastPerfect-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pastPerfect-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Future Perfect <br><span class="df" id="futurePerfect-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="futurePerfect-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Present Perfect Progressive <br><span class="df" id="presentPerfProg-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="presentPerfProg-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Past Perfect Progressive <br><span class="df" id="pastPerfProg-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pastPerfProg-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;Future Perfect Progressive <br><span class="df" id="futurePerfProg-df"></span>
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
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('aspects')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_aspects">
                                                                        aspect
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_aspects" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;simple <br><span class="df" id="simpleAspect-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="simpleAspect-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;progressive <br><span class="df" id="progressiveAspect-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="progressiveAspect-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;perfect <br><span class="df" id="perfectAspect-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="perfectAspect-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;perfect progressive <br><span class="df" id="perfProgAspect-df"></span>
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
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('times')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_times">
                                                                        time
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_times" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;present <br><span class="df" id="presentTime-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="presentTime-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;past <br><span class="df" id="pastTime-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pastTime-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;future <br><span class="df" id="futureTime-df"></span>
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
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('voice')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_voice">
                                                                        voice
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_voice" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;passive <br><span class="df" id="passiveVoice-df"></span>
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
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('phrasalVerbs')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_phrasalVerbs">
                                                                        phrasal
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_phrasalVerbs" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;phrasal verbs <br><span class="df" id="phrasalVerbs-df"></span>
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
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('modals')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_modals">
                                                                        modal
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_modals" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;simple (can, must, need, may) <br><span class="df" id="simpleModals-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="simpleModals-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>


                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;advanced <br><span class="df" id="advancedModals-df"></span>
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
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('transitive')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_transitive">
                                                                        transitive
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_transitive" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;transitive (drive a car) <br><span class="df" id="directObject-df"></span>
                                                                        <div class="ui-widget-content" style="width:80%;">
                                                                            <div class="gradientSlider" title="move right to rank texts with this construct higher" id="directObject-gradientSlider"></div>
                                                                        </div>
                                                                    </div>
                                                                    <hr>
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;ditransitive (give it to me) <br><span class="df" id="indirectObject-df"></span>
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
                                                                    <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('imperative')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                                    <a data-toggle="collapse" data-parent="#accordion"
                                                                       href="#collapse_imperative">
                                                                        imperatives
                                                                    </a>
                                                                </h4>
                                                            </div>
                                                            <div id="collapse_imperative" class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;imperatives<br><span class="df" id="imperatives-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('negation')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_negation">
                                                            negation
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_negation" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;all negation (nothing, nowhere, no, etc.) <br><span class="df" id="negAll-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="negAll-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;partial negation (hardly, barely, scarcely, rarely, seldom) <br><span class="df" id="partialNegation-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="partialNegation-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;no, not, never <br><span class="df" id="noNotNever-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="noNotNever-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;not (full form) <br><span class="df" id="not-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="not-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;n't (contracted form) <br><span class="df" id="nt-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('articles')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_articles">
                                                            articles
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_articles" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;all articles <br><span class="df" id="articles-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="articles-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;a <br><span class="df" id="aArticle-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="aArticle-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;an <br><span class="df" id="anArticle-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="anArticle-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;the <br><span class="df" id="theArticle-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('quantifiers')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_quantifiers">
                                                            quantifiers
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_quantifiers" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div class="panel-body">
                                                            <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;some <br><span class="df" id="someDet-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="someDet-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;any <br><span class="df" id="anyDet-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="anyDet-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;much <br><span class="df" id="muchDet-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="muchDet-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;many <br><span class="df" id="manyDet-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('adjectives')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_adjectives">
                                                            adjectives
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_adjectives" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;positive (nice) <br><span class="df" id="positiveAdj-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="positiveAdj-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;comparative short (nicer) <br><span class="df" id="comparativeAdjShort-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="comparativeAdjShort-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;superlative short (nicest) <br><span class="df" id="superlativeAdjShort-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="superlativeAdjShort-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;comparative long (more difficult) <br><span class="df" id="comparativeAdjLong-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="comparativeAdjLong-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;superlative long (most difficult) <br><span class="df" id="superlativeAdjLong-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('adverbs')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_adverbs">
                                                            adverbs
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_adverbs" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;positive (fast) <br><span class="df" id="positiveAdv-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="positiveAdv-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;comparative short (faster) <br><span class="df" id="comparativeAdvShort-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="comparativeAdvShort-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;superlative short (fastest) <br><span class="df" id="superlativeAdvShort-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="superlativeAdvShort-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;comparative long (more easily) <br><span class="df" id="comparativeAdvLong-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="comparativeAdvLong-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;superlative long (most easily) <br><span class="df" id="superlativeAdvLong-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('pronouns')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_pronouns">
                                                            pronouns
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_pronouns" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;subject (I) <br><span class="df" id="pronounsSubjective-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pronounsSubjective-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;object (me)<br><span class="df" id="pronounsObjective-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pronounsObjective-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;possessive (my)<br><span class="df" id="pronounsPossessive-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pronounsPossessive-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;absolute possessive (mine)<br><span class="df" id="pronounsPossessiveAbsolute-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pronounsPossessiveAbsolute-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;reflexive (myself) <br><span class="df" id="pronounsReflexive-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('conjunctions')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_conjunctions">
                                                            conjunctions
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_conjunctions" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;simple <br> (and, or, but, because, so) <br><span class="df" id="simpleConjunctions-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="simpleConjunctions-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;advanced <br> (therefore, until, besides, etc.) <br><span class="df" id="advancedConjunctions-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('prepositions')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_prepositions">
                                                            prepositions
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_prepositions" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;simple <br>(at, on, in, to, with, after) <br><span class="df" id="simplePrepositions-df"></span>
                                                            <div class="ui-widget-content" style="width:80%;">
                                                                <div class="gradientSlider" title="move right to rank texts with this construct higher" id="simplePrepositions-gradientSlider"></div>
                                                            </div>
                                                        </div>
                                                        <hr>
                                                        <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;advanced <br><span class="df" id="complexPrepositions-df"></span>
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
                                                        <button type="button" class="close" style="font-size: 12px" onclick="webRanker_internal_resetSlider('nouns')"><span class="glyphicon glyphicon-erase" title="reset"></span></button>
                                                        <a data-toggle="collapse" data-parent="#accordion"
                                                           href="#collapse_nouns">
                                                            nouns
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapse_nouns" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div class="panel-body">
                                                            <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;plural regular <br>(cars, flowers, etc.) <br><span class="df" id="pluralRegular-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pluralRegular-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;plural irregular <br>(children, women, etc.) <br><span class="df" id="pluralIrregular-df"></span>
                                                                <div class="ui-widget-content" style="width:80%;">
                                                                    <div class="gradientSlider" title="move right to rank texts with this construct higher" id="pluralIrregular-gradientSlider"></div>
                                                                </div>
                                                            </div>
                                                            <hr>
                                                            <div><input type="checkbox" onclick="webRanker_internal_excludeConstruction(this)" title="uncheck to exclude texts with this construct" checked>&nbsp;-ing forms <br>(skiing, being, etc. ALSO building BUT NOT king, something) <br><span class="df" id="ingNounForms-df"></span>
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
                                    <a href="javascript:webRanker_internal_resetSlider('all');" style="color:grey;">RESET ALL</a>
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
                                    <br>Enter a query in the search box and hit Go!
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
                        <button type="button" class="btn btn-primary">Apply</button>
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
                            <button type="button" class="btn btn-primary" id="modal_waitIdle_buttonCancel" onclick="webRanker_cancelOperation()">Cancel</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <script type="text/javascript" src="js/libs/jquery/jquery.js"></script>
        <script type="text/javascript" src="js/libs/jquery/jquery.min.js"></script>
        <script type="text/javascript" src="js/libs/jqueryui/jquery-ui.js"></script>
        <script type="text/javascript" src="js/libs/twitter-bootstrap/js/bootstrap.js"></script>
        <script type="text/javascript" src="js/libs/twitter-bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/libs/twitter-bootstrap/js/bootstrap-confirmation.js"></script>
        <script type="text/javascript" src="js/libs/number-polyfill.js"></script>
        <script type="text/javascript" src="js/libs/d3/d3.js"></script>
        <script type="text/javascript" src="js/libs/d3/d3.v3.js"></script>
        <script type="text/javascript" src="js/libs/d3/d3.v3.min.js"></script>

        <script type="text/javascript" src="js/plumbing.js"></script>
        <script type="text/javascript" src="js/webranker.js"></script>

        <script type="text/javascript">
            window.onload = webRanker_init();
        </script>
    </body>
</html>
