//========================= WEBRANKER ===========================//

var DEFAULT_NUM_RESULTS = 20;		// no of web results to crawl/process
var DEFAULT_LANGUAGE = "ENGLISH";

var WEBRANKER_INITIALISED = false;	// initialization state of the webranker
var WEBRANKER_STATE = null;		// state data for the current session

var HIGHLIGHT_COLORS = ["lightgreen", "lightblue", "lightpink", "lightcyan", "lightsalmon", "lightgrey", "lightyellow"];

//=========== BEGIN WEBRANKER INTERNAL FUNCTIONS
function webRanker_internal_createPrototype_weightSetting()
{
    var weightSetting = {
	name: "",
	weight: 0
    };
    
    return weightSetting;
}

function webRanker_internal_resetState()
{
    WEBRANKER_STATE = {
	query: "",
	language: DEFAULT_LANGUAGE,
	totalResults: DEFAULT_NUM_RESULTS,
	
	parsedDocs: [],			    // collection of all the parsed docs sent by the server
	displayedDocs: [],		    // collection of all the docs being displayed
	filteredDocs: [],		    // collection of the docs that shouldn't be displayed
	
	selection: -1,			    // index of the current selection in the displayed docs collection
	
	weightSettings_docLevel: [],	    // collection of weight data for the doc levels (A1-C2) and all constructions
	weightSettings_constructions: [],   // collection of weight data for grammatical constructions (### redundant?)
	
	filteredConstructions: [],	    // collection of the grammatical constructions that are ignored when ranking
	
	bParam: 0,			    // ### what's this?
	kParam: 1.7			    // ### what's this?
    };
}

function webRanker_internal_toggleConstructionsList()
{
    document.getElementById("show_all_constructions").hidden = (!document.getElementById("show_all_constructions").hidden);
}

function webRanker_internal_formatDocLevel(level)
{
    // turns a string "LEVEL-c" into "C1-C2"
    var l = level.substring(level.indexOf("-") + 1);
    l = l.toUpperCase();
    return (l + "1-" + l + "2");
}

function webRanker_internal_resetSlider(name)
{
    if (name === "all")
    {
        $(".gradientSlider").each(function () {
            if ($(this).slider("option", "value") !== 0) {
                $(this).slider("option", "value", 0);
            }
        });
    } 
    else
    {
        $("#collapse_" + name + " div[id$='-gradientSlider']").each(function () {
            if ($(this).slider("option", "value") !== 0) {
                $(this).slider("option", "value", 0);
            }
        });
    }
}

function webRanker_internal_resetUI()
{
    webRanker_internal_toggleLeftSidebar(false);
    webRanker_internal_toggleRightSidebar(false);
    webRanker_internal_toggleWaitDialog(false);
    webRanker_internal_resetSlider("all");
    $("#snapshot").html("<div id='empty_sidebar_info'>Click on a search result <br>to display text here.</div>");    
    document.getElementById("results_table").innerHTML = "";
}

function webRanker_internal_toggleLeftSidebar(show)
{
    if (show)
	$("#wrapper").addClass("toggled");
    else
	$("#wrapper").removeClass("toggled");
}

function webRanker_internal_toggleRightSidebar(show)
{
    if (show)
	$("#sidebar-wrapper-right").addClass("active");
    else
	$("#sidebar-wrapper-right").removeClass("active");
}

function webRanker_internal_refreshWeightSettings()
{
    if (WEBRANKER_INITIALISED === false)
	return;
    
    WEBRANKER_STATE.weightSettings_docLevel = [];
    WEBRANKER_STATE.weightSettings_constructions = [];

    // doc levels
    var levels = document.getElementById("settings_levels").getElementsByTagName("input");
    for (var k = 0; k < levels.length; k++) 
    {
        var weightSetting = webRanker_internal_createPrototype_weightSetting();
        weightSetting.name = levels[k].id;
        if (levels[k].checked)
	    weightSetting.weight = 1;
	else
	    weightSetting.weight = 0;

	WEBRANKER_STATE.weightSettings_docLevel.push(weightSetting);
        WEBRANKER_STATE.weightSettings_constructions.push(weightSetting);
    }
    
    // rest of the constructions
    $(".gradientSlider").each(function () {
        var w = $(this).slider("option", "value");
        w = w / 5; // turn into the scale from 0 to 1

        var n = this.id.substring(0, this.id.indexOf("-"));
        var weightSetting = webRanker_internal_createPrototype_weightSetting();
	weightSetting.name = n;
	weightSetting.weight = w;
        if (w !== 0)
	    WEBRANKER_STATE.weightSettings_docLevel.push(weightSetting);

	WEBRANKER_STATE.weightSettings_constructions.push(weightSetting);
    });
}

function webRanker_internal_excludeConstruction(const_element)
{
    var slider_id = const_element.parentElement.lastElementChild.firstElementChild.id;
    var construct_name = slider_id.substring(0, slider_id.indexOf("-gradientSlider"));

    // Get the state of the corresponding slider
    var disabled = $("#" + slider_id).slider("option", "disabled");

    // Disable/enable the slider
    $("#" + slider_id).slider("option", "disabled", !disabled);

    // add to/remove from excluded constructions
    if (WEBRANKER_STATE.filteredConstructions.indexOf(construct_name) === -1)
    {
	// add
	WEBRANKER_STATE.filteredConstructions.push(construct_name);	    
    }
    else
    {
        // remove
        var ind = WEBRANKER_STATE.filteredConstructions.indexOf(construct_name);
        WEBRANKER_STATE.filteredConstructions.splice(ind, 1);
    }
    
    webRanker_refreshRanking();
}

function webRanker_internal_displayDocText(selection)
{
    if (WEBRANKER_STATE.displayedDocs.length === 0)
	return;
    else if (WEBRANKER_STATE.displayedDocs.length <= selection)
    {
	console.log("Invalid selection for text display. Must satisfy 0 < " + selection + " < " + WEBRANKER_STATE.displayedDocs.length);
	return;
    }
    
    var previousSelection = WEBRANKER_STATE.selection;
    WEBRANKER_STATE.selection = selection;
    var doc = WEBRANKER_STATE.displayedDocs[selection];
    
    document.getElementById("snapshot").innerHTML = "";
    // remove the highlight from the results
    if (previousSelection > -1 && document.getElementById("results_table").childNodes.length > 0)
        $("#results_table tr:nth-child(" + (previousSelection + 1) + ")").css("background-color", "white");
    
    webRanker_internal_toggleRightSidebar(true);
    
    if (document.getElementById("results_table").childNodes.length > 0)
    {
        // highlight the corresponding result
        $("#results_table tr:nth-child(" + (selection + 1) + ")").css("background-color", "#fdf6e6");

        var info_box_1 = "<table style='width:100%'><tr>";
        if (doc.readabilityLevel !== null && doc.readabilityLevel.length > 3)
            info_box_1 += "<td class='text-cell'><b>" + webRanker_internal_formatDocLevel(doc.readabilityLevel) + "</b></td>";
	if (doc.numSents > 0)
            info_box_1 += "<td class='text-cell'>~" + doc.numSents + " sentences</td>";
	if (doc.docLength > 0 || doc.numDeps > 0)
            info_box_1 += "<td class='text-cell'>~" + doc.numDeps + " words</td>";
	info_box_1 += "</tr></table>";

        var info_box_2 = "<table id='constructions-table'><thead><tr><td><b>Construct</b></td><td><b>Count</b></td><td><b>Weight</b></td></tr></thead><tbody>";
        var info_box_3 = "<div id='show_all_constructions' hidden><table><thead><tr><td><b>Construct</b></td><td><b>Count</b></td><td><b>%</b></td></tr></thead><tbody>";

        var count_col = 0;
        for (var i in WEBRANKER_STATE.weightSettings_constructions)
	{
            var name = WEBRANKER_STATE.weightSettings_constructions[i]["name"];
            var name_to_show = name;
            var g = document.getElementById(WEBRANKER_STATE.weightSettings_constructions[i]["name"] + "-df");
            if (g !== null)
	    {
                name_to_show = g.parentNode.textContent;
                if (name_to_show.indexOf("\n") > 0)
                    name_to_show = name_to_show.substring(0, name_to_show.indexOf("\n"));
               
		if (name_to_show.indexOf("(") > 0)
                    name_to_show = name_to_show.substring(0, name_to_show.indexOf("("));
            }
	    
            var all = doc["constructions"];
            for (var j in all)
	    {
                if (all[j] === name) 
		{
                    var ind = j;
                    if ((WEBRANKER_STATE.weightSettings_constructions[i]["weight"] > 0 ||
			 WEBRANKER_STATE.weightSettings_constructions[i]["weight"] < 0))
		    {
                        var col = "lightyellow";
                        if (count_col < HIGHLIGHT_COLORS.length)
			{
                            col = HIGHLIGHT_COLORS[count_col];
                            count_col++;
                        }
			
                        info_box_2 += "<tr class='constructions_line'><td style='background-color:" + col + "'>" + name_to_show + "</td><td class='text-cell'>" + doc["frequencies"][ind] + "</td><td class='text-cell'>(" + WEBRANKER_STATE.weightSettings_constructions[i]["weight"] + ")</td></tr>";
                    }
		    
                    info_box_3 += "<tr><td>" + name_to_show + "</td><td class='text-cell'>" + doc["frequencies"][ind] + "</td><td class='text-cell'>(" + ((Number(doc["relFrequencies"][ind])) * 100).toFixed(4) + ")</td></tr>";
                }
            }
        }
        
        info_box_2 += "</tbody></table><br> <div id='info-highlights'></div>";
        info_box_3 += "</tbody></table><br></div><br><br><br>";

        // add a "copy text" button
        var text_string = "<br>";
        text_string += "<div id='sidebar_text' readonly>";
        text_string += webRanker_internal_getHighlightedDocHTML(doc);
        text_string += "</div><br>";

        document.getElementById("snapshot").innerHTML = info_box_1 + text_string + info_box_2 + info_box_3;

        var lines = document.getElementsByClassName("constructions_line");
        if (lines.length > 0)
	{
            document.getElementById("constructions-table").hidden = false;
            if (lines.length === 1)
	    {
                document.getElementById("info-highlights").innerHTML = "";
                document.getElementById("info-highlights").innerHTML = "<div class='panel panel-default' style='text-align: center'><a href='javascript:webRanker_internal_toggleConstructionsList();' style='color:darkgrey;'><span class='caret'></span> all constructions <span class='caret'></span></a></div><br>";
            }
            else
	    {
                document.getElementById("info-highlights").innerHTML = "* Highlights may overlap. Mouse over a highlight to see a tooltip<br> with the names of all embedded constructions.<br><br>"
                        + "<div class='panel panel-default' style='text-align: center'><a href='javascript:webRanker_internal_toggleConstructionsList();' style='color:darkgreen;'> all constructions </a></div>";
            }
        } 
	else
	{
            document.getElementById("info-highlights").innerHTML = "<button type='button' class='btn btn-warning' onclick='webRanker_internal_toggleLeftSidebar(true)' id='sidebar_grammar_button'>GRAMMAR SETTINGS</button>";
            document.getElementById("constructions-table").hidden = true;
        }
    }
}

function webRanker_internal_getHighlightedDocHTML(doc)
{
    var newText = ""; // String
    var occs = []; // occurrences of THESE constructionS in this doc
    var color_count = 0;

    for (var i in WEBRANKER_STATE.weightSettings_docLevel)
    { 
        var name = WEBRANKER_STATE.weightSettings_docLevel[i]["name"];
        var color = "lightyellow";

        if (name.indexOf("LEVEL") < 0)
	{
            // assign colors
            color = HIGHLIGHT_COLORS[color_count];
            color_count++;
        }

        for (var j in doc["highlights"]) 
	{
            var o = doc["highlights"][j];
            if (o["construction"] === name && (WEBRANKER_STATE.weightSettings_docLevel[i]["weight"] < 0 || WEBRANKER_STATE.weightSettings_docLevel[i]["weight"] > 0))
	    {
                o["color"] = color;
                var contains = false;
                for (var k in occs) 
		{
                    var occ = occs[k];
                    if (occ["construction"] === name && occ["start"] === o["start"] && occ["end"] === o["end"]) 
		    {
                        contains = true;
                        break;
                    }
                }
                if (!contains)
                    occs.push(o);
            }
        }
    }

    // // sort the occurrences based on their (end) indices
    var allIndices = []; // String[][]
    for (var j = 0; j < occs.length * 2; j++)
        allIndices.push({});

    for (var i = 0; i < occs.length; i++) 
    {
        var o = occs[i]; // Occurrence
        var start = o["start"]; // int
        var end = o["end"]; // int

        var spanStart = "<span style='background-color:" + o["color"] + ";' title='" + o["construction"] + "'>"; // String
        var spanEnd = "</span>"; // String

        allIndices[i] = {"tag": spanStart, "index": (start + "-" + end)};
        allIndices[occs.length * 2 - 1 - i] = {"tag": spanEnd, "index": end};
    }

    // sort allIndices - start and end in descending order
    // each element is an object with keys "tag" and "index"
    allIndices.sort(function (ind1, ind2) 
    {
        var indexSt1 = (ind1["index"]).toString(); // String
        var indexSt2 = (ind2["index"]).toString(); // String
        var indexEnd1 = ""; // String
        var indexEnd2 = ""; // String

        var s1 = -5; // int
        var s2 = -5; // int
        var e1 = -5; // int
        var e2 = -5; // int

        // indexSt1 can be either of form start-end or just end
        if (indexSt1.indexOf("-") > -1) 
	{
            indexEnd1 = indexSt1.substring(indexSt1.indexOf("-") + 1);
            indexSt1 = indexSt1.substring(0, indexSt1.indexOf("-"));
        }
        if (indexSt2.indexOf("-") > -1) 
	{
            indexEnd2 = indexSt2.substring(indexSt2.indexOf("-") + 1);
            indexSt2 = indexSt2.substring(0, indexSt2.indexOf("-"));
        }

        s1 = parseInt(indexSt1); // int
        s2 = parseInt(indexSt2); // int

        var sComp = s2 - s1; // int

        if (sComp !== 0)
            return sComp;
        else 
	{
            if (indexEnd1.length > 0) 
	    {
                if (indexEnd2.length > 0) 
		{
                    e1 = parseInt(indexEnd1); // int
                    e2 = parseInt(indexEnd2); // int

                    var l1 = Math.abs(e1 - s1); // int
                    var l2 = Math.abs(e2 - s2); // int

                    return (l1 - l2);
                } 
		else
                    return sComp;
            } 
	    else if (indexEnd2.length > 0) 
	    {
                if (indexEnd1.length > 0) 
		{
                    e1 = parseInt(indexEnd1);
                    e2 = parseInt(indexEnd2);

                    var l1 = Math.abs(e1 - s1); // int
                    var l2 = Math.abs(e2 - s2); // int

                    return (l1 - l2);
                }
		else
                    return sComp;
            } 
	    else
                return sComp;
        }
    });

    // insert span tags into the text : start from the end
    var docText = doc["text"]; // String
    // get rid of html tags inside of text
    var tmp = document.createElement("DIV");
    tmp.innerHTML = docText;
    docText = tmp.textContent || tmp.innerText;
    docText.replace("\n","<br>");

    var prevStartInd = -1; // int // prev ind of a start-tag
    var prevEndInd = -1; // int // prev ind of a start-tag
    var prevConstruct = ""; // String
    var prevStartTag = ""; // String

    for (var ind in allIndices) 
    {
        var insertHere = -10; // int
        var curItem = (allIndices[ind]["index"]).toString(); // String
        var tmpEnd = -1; // int
        var tmpStart = -1; // int
        // indexSt1 can be either of form start-end or just end
        if (curItem.indexOf("-") > -1)
	{
            tmpStart = parseInt(curItem.substring(0, curItem.indexOf("-")));
            tmpEnd = parseInt(curItem.substring(curItem.indexOf("-") + 1));
            insertHere = tmpStart;
        }
	else
            insertHere = parseInt(curItem);

        var tag = allIndices[ind]["tag"]; // String
        // show several constructions on mouseover, ONLY if they fully overlap (e.g., complex sentence, direct question)
        if (tag.indexOf("<span") > -1)
	{
            var toInsertBefore = ""; // String // to take care in more than 2 overlapping constructions
            if (prevStartInd === insertHere && prevEndInd === tmpEnd)
	    {
                tag = tag.substring(0, tag.indexOf("'>")) + ", " + prevConstruct + "'>";
                insertHere += prevStartTag.length;
                toInsertBefore = prevStartTag;
            }

            prevStartInd = parseInt(curItem.substring(0, curItem.indexOf("-"))); // int
            prevEndInd = parseInt(curItem.substring(curItem.indexOf("-") + 1)); // int
            prevStartTag = toInsertBefore + tag;
            prevConstruct = tag.substring(tag.indexOf("title='") + 7, tag.indexOf("'>"));
        }
        
        docText = docText.substring(0, insertHere) + tag + docText.substring(insertHere);
    }
    
    newText = docText;
    return newText;
}

function webRanker_internal_toggleWaitDialog(show)
{
    if (show)
	$("#modal_WaitIdle").modal('show');
    else
	$("#modal_WaitIdle").modal('hide');
}

function webRanker_internal_updateWaitDialog(content, cancellable)
{
    document.getElementById("modal_waitIdle_body").innerHTML = content;
    if (cancellable === false)
	document.getElementById('modal_waitIdle_buttonCancel').style.visibility = 'hidden';
    else
	document.getElementById('modal_waitIdle_buttonCancel').style.visibility = 'visible';
}
//=========== END WEBRANKER INTERNAL FUNCTIONS
function webRanker_init()
{
    if (pipeline_init() === false)
	return;
    
    WEBRANKER_INITIALISED = true;
    webRanker_internal_resetState();
    
    $("#menu-toggle").click(function (e) {
	e.preventDefault();
	$("#wrapper").toggleClass("toggled");
    });

    $("#right-menu-toggle").click(function (e) {
	e.preventDefault();
	$("#sidebar-wrapper-right").toggleClass("active");
	$("#page-content-wrapper").toggleClass("active");
    });

    $('button[data-loading-text]')
	.on('click', function () {
	    var btn = $(this);
	    btn.button('loading');
	    setTimeout(function () {
		btn.button('reset');
	    }, 3000);
    });

    $("[id$=gradientSlider]").slider({
       orientation: "horizontal",
       range: "min",
       max: 5,
       min: 0,
       value: 0
    });
    $("[id$=gradientSlider]").slider("value", 0);
    $("[id$=gradientSlider]").slider({
       change: function (d) {
	   webRanker_refreshRanking();
       }
    });

    $(".lengthSlider").slider({
       orientation: "vertical",
       range: "min",
       max: 5,
       min: 0,
       value: 0
    });
    $(".lengthSlider").slider("value", 0);
    $(".lengthSlider").slider({
       change: function (d) {
	   webRanker_refreshRanking();
       }
    });

    if (document.getElementById("sidebar_text") === null)
	$("#snapshot").html("<div id='empty_sidebar_info'>Click on a search result <br>to display text here.</div>");

    window.onbeforeunload = function() {
	  webRanker_deinit();  
    };
}

function webRanker_deinit()
{
    WEBRANKER_INITIALISED = false;
    WEBRANKER_STATE = null;
}

function webRanker_handleCompletion_webSearch(jobID)
{
    var status = "<h3>Web search complete. Parsing search results now...</h3>";
    webRanker_internal_updateWaitDialog(status, true);
    
    if (pipeline_request_parseSearchResults(jobID) === false)
	webRanker_handlePipelineError();
}

function webRanker_handleCompletion_parseSearchResults(jobID)
{
    var status = "<h3>Parsing complete. Fetching results now...</h3>";
    webRanker_internal_updateWaitDialog(status, false);
    
    for (var i = 1; i <= WEBRANKER_STATE.totalResults; i++)
    {
	if (pipeline_request_fetchParsedData(i, 1) === false)
	{
	    webRanker_handlePipelineError();
	    break;
	}
    }
}

function webRanker_handleFetch_searchResults(searchResults)
{
    // ### nothing to do here as we fetch the parsed data directly
}

function webRanker_handleFetch_parsedData(parsedDocs)
{   
    for (var i = 0; i < parsedDocs.length; i++)
	WEBRANKER_STATE.parsedDocs.push(parsedDocs[i]);
    
    if (WEBRANKER_STATE.parsedDocs.length === WEBRANKER_STATE.totalResults)
    {
	webRanker_internal_toggleWaitDialog(false);
	webRanker_refreshRanking();
	
	webRanker_internal_toggleLeftSidebar(true);
	webRanker_internal_toggleRightSidebar(true);
    }
}

function webRanker_handlePipelineError()
{
    webRanker_internal_resetUI();
    webRanker_internal_resetState();
    
    webRanker_internal_toggleWaitDialog(true);
    var status = "<h3>Something went wrong somewhere. There's little to do now but despair.</h3>";
    webRanker_internal_updateWaitDialog(status, false);
    
    webRanker_deinit();
}

function webRanker_cancelOperation()
{
    webRanker_internal_toggleWaitDialog(false);
    webRanker_internal_resetUI();
    
    pipeline_request_cancelLastJob();    
}

function webRanker_beginSearch()
{
    webRanker_internal_resetUI();
    webRanker_internal_resetState();
    WEBRANKER_STATE.query = document.getElementById("search_field").value.trim();
    
    webRanker_internal_toggleWaitDialog(true);
    var status = "<h3>Performing web search now...</h3>";
    webRanker_internal_updateWaitDialog(status, true);
    
    if (pipeline_request_performSearch(WEBRANKER_STATE.query, WEBRANKER_STATE.language, WEBRANKER_STATE.totalResults) === false)
	webRanker_handlePipelineError();
}

function webRanker_refreshRanking()
{
    WEBRANKER_STATE.bParam = ($(".lengthSlider").slider("option", "value")) / 5;
    if (WEBRANKER_STATE.bParam === null)
        WEBRANKER_STATE.bParam = 0;
    
    if (WEBRANKER_STATE.parsedDocs.length === 0)
	return;
    
    WEBRANKER_STATE.displayedDocs = [];
    webRanker_internal_refreshWeightSettings();
    
    var avDocLen = -1;

    for (var k = 0; k < WEBRANKER_STATE.parsedDocs.length; k++) 
    {
        var doc = WEBRANKER_STATE.parsedDocs[k];
        var appropriateDoc = true;

        for (var j = 0; j < WEBRANKER_STATE.weightSettings_constructions.length; j++)
        {
            if (WEBRANKER_STATE.weightSettings_constructions[j]["name"].indexOf("LEVEL") > -1) 
	    {
                if (WEBRANKER_STATE.weightSettings_constructions[j]["weight"] === 0) 
		{
                    $("#" + WEBRANKER_STATE.weightSettings_constructions[j]["name"]).prop('checked', false);
		    
                    if (doc["readabilityLevel"] === WEBRANKER_STATE.weightSettings_constructions[j]["name"]) 
                        appropriateDoc = false;
                } 
		else if (WEBRANKER_STATE.weightSettings_constructions[j]["weight"] === 1)
                    $("#" + WEBRANKER_STATE.weightSettings_constructions[j]["name"]).prop('checked', true);
            }
        }

        doc["gramScore"] = 0.0;

        // check if the doc contains excluded constructions
        for (var t = 0; t < WEBRANKER_STATE.filteredConstructions.length; t++)
	{
            var constr_ind = doc.constructions.indexOf(WEBRANKER_STATE.filteredConstructions[t]);
            if (doc.frequencies[constr_ind] > 0)
                appropriateDoc = false;
        }

        // add data to object
        if (appropriateDoc && ($.inArray(k, WEBRANKER_STATE.filteredDocs) === -1))
            WEBRANKER_STATE.displayedDocs.push(doc);
    }
    
    // calculate tf-idf of the grammar "query"
    // get df of each (selected) construction (in the settings)
    for (var i in WEBRANKER_STATE.weightSettings_constructions) 
    {
        var name = WEBRANKER_STATE.weightSettings_constructions[i]["name"];
        var count = 0; // number of docs with constructions[i] (this construction)
        for (var j in WEBRANKER_STATE.displayedDocs)
        {
            if (name.indexOf("LEVEL") > -1)
	    {
                var lev = WEBRANKER_STATE.displayedDocs[j]["readabilityLevel"];
                if (lev === name)
                    count++;
            } 
            else
	    {
                var cs = WEBRANKER_STATE.displayedDocs[j]["constructions"];
                for (var k in cs)
                {
                    if (cs[k] === name) 
		    {
                        if (WEBRANKER_STATE.displayedDocs[j]["frequencies"][k] > 0)
                            count++;
                       
			break;
                    }
                }
            }
        }
        // add df (document count) and idf (inverse document frequency) to each construction in the settings
        WEBRANKER_STATE.weightSettings_constructions[i]["df"] = count;
        WEBRANKER_STATE.weightSettings_constructions[i]["idf"] = Math.log((WEBRANKER_STATE.displayedDocs.length + 1) / count);
    }

    // calculate average doc length
    for (var d in WEBRANKER_STATE.displayedDocs)
        avDocLen += WEBRANKER_STATE.displayedDocs[d]["docLength"];
    
    if (WEBRANKER_STATE.displayedDocs.length > 0)
        avDocLen = avDocLen / WEBRANKER_STATE.displayedDocs.length;
    else
        avDocLen = 0;

    //////// - end of tf, idf calculations


    // calculate totalWeight for each doc: BM25
    for (var d in WEBRANKER_STATE.displayedDocs)
    {
        var dTotal = 0.0;
        for (var constr in WEBRANKER_STATE.weightSettings_constructions)
        {
            var name = WEBRANKER_STATE.weightSettings_constructions[constr]["name"];

            if ((WEBRANKER_STATE.weightSettings_constructions[constr]["weight"] > 0 ||
		 WEBRANKER_STATE.weightSettings_constructions[constr]["weight"] < 0) && WEBRANKER_STATE.weightSettings_constructions[constr]["df"] > 0)
	    { 
		// cannot be NaN
                var dConstrInd = WEBRANKER_STATE.displayedDocs[d]["constructions"].indexOf(name);
                // if this construction is in this doc
                if (dConstrInd > -1 && WEBRANKER_STATE.displayedDocs[d]["frequencies"][dConstrInd] > 0)
		{
                    var tf = WEBRANKER_STATE.displayedDocs[d]["frequencies"][dConstrInd];
                    var idf = WEBRANKER_STATE.weightSettings_constructions[constr]["idf"];
                    var tfNorm = ((WEBRANKER_STATE.kParam + 1) * tf) / (tf + WEBRANKER_STATE.kParam * (1 - WEBRANKER_STATE.bParam + WEBRANKER_STATE.bParam * (WEBRANKER_STATE.displayedDocs[d]["docLength"] / avDocLen)));
                    var gramScore = tfNorm * idf;

                    dTotal += gramScore * WEBRANKER_STATE.weightSettings_constructions[constr]["weight"];
                }
            }
        }
        
        WEBRANKER_STATE.displayedDocs[d]["gramScore"] = dTotal; // grammar score
        WEBRANKER_STATE.displayedDocs[d]["totalWeight"] = WEBRANKER_STATE.displayedDocs[d]["gramScore"]; // total weight : TODO add rankWeight and textWeight
    }
    //// - end of calculating the total weight

    if (WEBRANKER_STATE.weightSettings_docLevel.length < 4)
    {
        if (WEBRANKER_STATE.bParam === 0)
	{
            WEBRANKER_STATE.displayedDocs.sort(function (a, b) {
                return parseInt(a.preRank) - parseInt(b.preRank);
            });
        } 
        else
	{
            WEBRANKER_STATE.displayedDocs.sort(function (a, b) {
                return parseInt(a.docLength) - parseInt(b.docLength);
            });
        }
    }
    else
    {
        WEBRANKER_STATE.displayedDocs.sort(function (a, b) {
            return Number(b.totalWeight) - Number(a.totalWeight);
        });
    }

    document.getElementById("docs_info").innerHTML = (WEBRANKER_STATE.displayedDocs.length) + " results";
    for (var s in WEBRANKER_STATE.weightSettings_constructions) 
    {
        if (WEBRANKER_STATE.weightSettings_constructions[s]["name"].startsWith("LEVEL"))
	{
            if (document.getElementById(WEBRANKER_STATE.weightSettings_constructions[s]["name"]).checked)
                document.getElementById(WEBRANKER_STATE.weightSettings_constructions[s]["name"] + "-df").innerHTML = "(" + WEBRANKER_STATE.weightSettings_constructions[s]["df"] + " / " + WEBRANKER_STATE.displayedDocs.length + " results)";
            else
                document.getElementById(WEBRANKER_STATE.weightSettings_constructions[s]["name"] + "-df").innerHTML = "";
        } 
        else
            document.getElementById(WEBRANKER_STATE.weightSettings_constructions[s]["name"] + "-df").innerHTML = "(" + WEBRANKER_STATE.weightSettings_constructions[s]["df"] + " / " + WEBRANKER_STATE.displayedDocs.length + ")";
    }

    var out = "";
    var i;
    for (i = 0; i < WEBRANKER_STATE.displayedDocs.length; i++)
    {
        // show each object in a row of 3 cells: html / titles, urls and snippets / text
        out += '<tr><td class="num_cell" style="font-size:x-large;">' +
                (i + 1) + '&nbsp;<span style="color:lightgrey;font-size:small" title="original position in the rank">(' + WEBRANKER_STATE.displayedDocs[i].preRank + ')</span><br>' +
                '</td><td  class="url_cell" style="width:40%;"><div><a href="' + WEBRANKER_STATE.displayedDocs[i].url + '" target="_blank"><b>' + WEBRANKER_STATE.displayedDocs[i].title + '</b></a></div>' +
                '<div id="show_text_cell" title="Click to show text" onclick="webRanker_internal_displayDocText(' + i + ');"><span style="color:grey;font-size:smaller;">' + WEBRANKER_STATE.displayedDocs[i].urlToDisplay + '</span><br><span>' + WEBRANKER_STATE.displayedDocs[i].snippet + '</span></div></td></tr>';

    }

    document.getElementById("results_table").innerHTML = out;
    
//    if (WEBRANKER_STATE.selection !== -1)
//	webRanker_internal_displayDocText(WEBRANKER_STATE.selection);
}