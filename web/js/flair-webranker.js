/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */

//==================== FLAIR WEBRANKER ======================//
FLAIR.createNS("FLAIR.WEBRANKER");
FLAIR.createNS("FLAIR.WEBRANKER.CONSTANTS");
FLAIR.createNS("FLAIR.WEBRANKER.UTIL");
FLAIR.createNS("FLAIR.WEBRANKER.UTIL.TOGGLE");
FLAIR.createNS("FLAIR.WEBRANKER.UTIL.TOAST");
FLAIR.createNS("FLAIR.WEBRANKER.UTIL.WAIT");

//=================== FLAIR.WEBRANKER.CONSTANTS =============//
FLAIR.WEBRANKER.CONSTANTS.DEFAULT_NUM_RESULTS = 20;
FLAIR.WEBRANKER.CONSTANTS.DEFAULT_LANGUAGE = "ENGLISH";
FLAIR.WEBRANKER.CONSTANTS.HIGHLIGHT_COLORS = ["lightgreen", "lightblue", "lightpink", "lightcyan", "lightsalmon", "lightgrey", "lightyellow"];

//=================== FLAIR.WEBRANKER.UTIL =============//
FLAIR.WEBRANKER.UTIL.formatDocLevel = function(level) {
    // turns a string "LEVEL-c" into "C1-C2"
    var l = level.substring(level.indexOf("-") + 1);
    l = l.toUpperCase();
    return (l + "1-" + l + "2");
};
FLAIR.WEBRANKER.UTIL.generateConstructionName = function(parent, name_to_show) {
    var name = name_to_show;
    if (parent !== null)
    {
	parent = parent.parentNode; // div panel-body
	if (parent !== null)
	{
	    parent = parent.parentNode; // div 
	    if (parent !== null) 
	    {
		var grandparent = parent.parentNode;
		parent = parent.id; // div panel-collapse collapse
		if (parent.indexOf("collapse_") > -1)
		{
		    parent = parent.substring(9);
		    // add parent_name to name
		    name = parent + "  > " + name;

		    if (grandparent !== null && grandparent.className.indexOf("success") > -1)
		    {	// e.g. verbs
			// one more layer of constructs
			grandparent = grandparent.parentNode; // div panel-body
			if (grandparent !== null)
			{
			    grandparent = grandparent.parentNode; // div panel-collapse collapse
			    if (grandparent !== null)
			    {
				grandparent = grandparent.id;
				if (grandparent.indexOf("collapse_") > -1)
				{
				    grandparent = grandparent.substring(9);
				    // add parent_name to name
				    name = grandparent + "  > " + name;
				}
			    }
			}
		    }
		}
	    }
	}
    }
    return name.replace("_", " ");
};
FLAIR.WEBRANKER.UTIL.resetSlider = function(name) {
    if (name === "all")
    {
        $(".gradientSlider").each(function () {
            if ($(this).slider("option", "value") !== 0) {
                $(this).slider("option", "value", 0);
            }
	    
	    var construct_name = this.id.substring(0, this.id.indexOf("-gradientSlider"));
	    if ( construct_name === "customVocabList")
	    {
		if ($("#tgl-" + construct_name).prop("checked") === true)
		    $("#tgl-" + construct_name).trigger("click");
	    }
	    else if ($("#tgl-" + construct_name).prop("checked") === false)
		$("#tgl-" + construct_name).trigger("click");
        });
    } 
    else
    {
        $("#collapse_" + name + " div[id$='-gradientSlider']").each(function () {
            if ($(this).slider("option", "value") !== 0) {
                $(this).slider("option", "value", 0);
            }
	    
	    var construct_name = this.id.substring(0, this.id.indexOf("-gradientSlider"));
	    if ( construct_name === "customVocabList")
	    {
		if ($("#tgl-" + construct_name).prop("checked") === true)
		    $("#tgl-" + construct_name).trigger("click");
	    }
	    else if ($("#tgl-" + construct_name).prop("checked") === false)
		$("#tgl-" + construct_name).trigger("click");
        });
    }
};
FLAIR.WEBRANKER.UTIL.resetTextCharacteristics = function() {
    // reset the readability settings
    $("#LEVEL-a").prop('checked', true);
    $("#LEVEL-b").prop('checked', true);
    $("#LEVEL-c").prop('checked', true);
    // reset the length slider
    $(".lengthSlider").slider("value", 0);
};
FLAIR.WEBRANKER.UTIL.resetUI = function(leftSidebar, rightSidebar, waitDialog, sliders, snapshot, resultsTable) {
    if (leftSidebar === true || leftSidebar === undefined)
	FLAIR.WEBRANKER.UTIL.TOGGLE.leftSidebar(false);
    if (rightSidebar === true || rightSidebar === undefined)
	FLAIR.WEBRANKER.UTIL.TOGGLE.rightSidebar(false);
    if (waitDialog === true || waitDialog === undefined)
	FLAIR.WEBRANKER.UTIL.WAIT.singleton.clear();
    if (sliders === true || sliders === undefined)
	FLAIR.WEBRANKER.UTIL.resetSlider("all");
    if (snapshot === true || snapshot === undefined)
	$("#snapshot").html("<div id='empty_sidebar_info'>Click on a search result <br>to display text here.</div>");    
    if (resultsTable === true || resultsTable === undefined) {
        
        document.getElementById("results_table").innerHTML = "<div class=\"panel panel-info\"> <div class=\"panel-heading\" style=\"text-align: center\"> <h3 class=\"panel-title\">SEARCH <span class=\"glyphicon glyphicon-search\"></span></h3> </div> <div class=\"panel-body\"> Type in a search query. <br> FLAIR will fetch the <b>top results</b> from the Bing search engine. </div> </div> <br><br> <div class=\"panel panel-warning\"> <div class=\"panel-heading\" style=\"text-align: center\"> <h3 class=\"panel-title\">CONFIGURE <span class=\"glyphicon glyphicon-cog\"></span></h3> </div> <div class=\"panel-body\"> Configure the settings: <b>text</b> (complexity, length) and <b>language</b> (the passive, wh- questions, academic vocabulary, ...) <br> You can <b>export</b> the settings to apply them to all further searches. </div> </div> <br><br> <div class=\"panel panel-info\" > <div class=\"panel-heading\" style=\"text-align: center\"> <h3 class=\"panel-title\">READ <span class=\"glyphicon glyphicon-menu-hamburger\"></span></h3> </div> <div class=\"panel-body\"> FLAIR will re-rank the documents according to the configured settings. <br> Click on the link to open the page in a new tab or read the <b>enhanced text</b> in the right-side panel. </div> </div>";
    }
    
    FLAIR.WEBRANKER.UTIL.TOGGLE.customCorpusDialog(false);
};
FLAIR.WEBRANKER.UTIL.cancelCurrentOperation = function() {
    FLAIR.WEBRANKER.singleton.cancelOperation();
    FLAIR.WEBRANKER.UTIL.TOAST.clear(true);
    FLAIR.WEBRANKER.UTIL.TOAST.warning("The operation was cancelled.", true, 4000);
};
FLAIR.WEBRANKER.UTIL.showExportSettingsDialog = function(exportURL) {
    $("#exported_settings_url").val(exportURL);
    $("#modal_ExportSettings").modal('show'); 
};
FLAIR.WEBRANKER.UTIL.copyToClipboard = function(input_element) {
    $(input_element).select();
    document.execCommand("copy");
};

//=================== FLAIR.WEBRANKER.UTIL.WAIT =============//
FLAIR.WEBRANKER.UTIL.WAIT.INSTANCE = function() {
  // PRIVATE VARS
  var handlers = {
    onClick_yes: null,
    onClick_no: null,
    onClick_cancel: null
  };
  var content = null;
  
  // PRIVATE INTERFACE
  var reset = function() {
    handlers.onClick_cancel = null;
    handlers.onClick_yes = null;
    handlers.onClick_no = null;
    content = "DEAD DOVE. DO NOT EAT";
    
    $("#modal_waitIdle_buttonCancel").hide();
    $("#modal_waitIdle_buttonYes").hide();
    $("#modal_waitIdle_buttonNo").hide();

    $("#modal_waitIdle_buttonCancel").click(function(e) {
	e.preventDefault();
    });
    $("#modal_waitIdle_buttonYes").click(function(e) {
	e.preventDefault();
    });
    $("#modal_waitIdle_buttonNo").click(function(e) {
	e.preventDefault();
    });
  };
  var showDialog = function(show) {
    if (show)
	$("#modal_WaitIdle").modal('show');
    else
	$("#modal_WaitIdle").modal('hide');
  };
  
  // PUBLIC INTERFACE
  this.showYesNo = function(displayContent, handler_yes, handler_no) {
    reset();
    
    content = displayContent;
    handlers.onClick_yes = handler_yes;
    handlers.onClick_no = handler_no;

    $("#modal_waitIdle_buttonYes").click(function(e) {
	if (handlers.onClick_yes !== null)
	{
	    e.preventDefault();
	    showDialog(false);
	    
	    handlers.onClick_yes();
	}
    });
    $("#modal_waitIdle_buttonNo").click(function(e) {
	if (handlers.onClick_no !== null)
	{
	    e.preventDefault();
   	    showDialog(false);

	    handlers.onClick_no();
	}
    });
    
    $("#modal_waitIdle_buttonYes").show();
    $("#modal_waitIdle_buttonNo").show();
    $("#modal_waitIdle_body").html(content);
    
    showDialog(true);
  };
  this.showCancel = function(displayContent, handler_cancel) {
    reset();
    
    content = displayContent;
    handlers.onClick_cancel = handler_cancel;

    $("#modal_waitIdle_buttonCancel").click(function(e) {
	if (handlers.onClick_cancel !== null)
	{
	    e.preventDefault();
	    showDialog(false);

	    handlers.onClick_cancel();
	}
    });
    
    $("#modal_waitIdle_buttonCancel").show();
    $("#modal_waitIdle_body").html(content);
    
    showDialog(true);
  };
  this.showNonClosable = function(displayContent) {
    reset();
    
    content = displayContent;
    
    $("#modal_waitIdle_body").html(content);
    
    showDialog(true);
  };
  this.clear = function() {
      reset();
      showDialog(false);
  };
};
FLAIR.WEBRANKER.UTIL.WAIT.singleton = new FLAIR.WEBRANKER.UTIL.WAIT.INSTANCE();

//=================== FLAIR.WEBRANKER.UTIL.TOAST =============//
FLAIR.WEBRANKER.UTIL.TOAST.info = function(message, dismissOnTap, timeout) {
    toastr.options.closeButton = false;
    toastr.options.tapToDismiss = dismissOnTap;
    toastr.options.timeOut = timeout;
    toastr.options.extendedTimeOut = timeout;
    toastr.options.positionClass = "toast-bottom-center";
    toastr.info(message);
};
FLAIR.WEBRANKER.UTIL.TOAST.success = function(message, dismissOnTap, timeout) {
    toastr.options.closeButton = false;
    toastr.options.tapToDismiss = dismissOnTap;
    toastr.options.timeOut = timeout;
    toastr.options.extendedTimeOut = timeout;
    toastr.options.positionClass = "toast-bottom-center";
    toastr.success(message);
};
FLAIR.WEBRANKER.UTIL.TOAST.error = function(message, dismissOnTap, timeout) {
    toastr.options.closeButton = false;
    toastr.options.tapToDismiss = dismissOnTap;
    toastr.options.timeOut = timeout;
    toastr.options.extendedTimeOut = timeout;
    toastr.options.positionClass = "toast-bottom-center";
    toastr.error(message);
};
FLAIR.WEBRANKER.UTIL.TOAST.warning = function(message, dismissOnTap, timeout) {
    toastr.options.closeButton = false;
    toastr.options.tapToDismiss = dismissOnTap;
    toastr.options.timeOut = timeout;
    toastr.options.extendedTimeOut = timeout;
    toastr.options.positionClass = "toast-bottom-center";
    toastr.warning(message);
};
FLAIR.WEBRANKER.UTIL.TOAST.clear = function(immediate) {
    if (immediate === false)
	toastr.clear();  
    else
	toastr.remove();
};

//=================== FLAIR.WEBRANKER.UTIL.TOGGLE =============//
FLAIR.WEBRANKER.UTIL.TOGGLE.constructionsList = function() {
    if (document.getElementById("show_all_constructions").hidden)
        document.getElementsByClassName("caret")[0].parentNode.className = "dropup";
    else
        document.getElementsByClassName("caret")[0].parentNode.className = "";

    document.getElementById("show_all_constructions").hidden = (!document.getElementById("show_all_constructions").hidden);
};
FLAIR.WEBRANKER.UTIL.TOGGLE.leftSidebar = function(show) {
    if (show)
	$("#wrapper").addClass("toggled");
    else
	$("#wrapper").removeClass("toggled");
};
FLAIR.WEBRANKER.UTIL.TOGGLE.rightSidebar = function(show) {
    if (show)
	$("#sidebar-wrapper-right").addClass("active");
    else
	$("#sidebar-wrapper-right").removeClass("active");
};
FLAIR.WEBRANKER.UTIL.TOGGLE.visualiserDialog = function(show) {
    if (show)
        $("#myModal_Visualize").modal('show');
    else
	$("#myModal_Visualize").modal('hide');
};
FLAIR.WEBRANKER.UTIL.TOGGLE.customCorpusDialog = function(show) {
    if (show)
    {
	$("#modal_CustomCorpus_fileSelect").val(null);
	$("#modal_CustomCorpus").modal('show');
    }
    else
	$("#modal_CustomCorpus").modal('hide');
};

//=================== FLAIR.WEBRANKER =============//
FLAIR.WEBRANKER.CUSTOMVOCAB = function(server_pipeline) {
    // PRIVATE VARS
    var wordList = "";		    // comma separated word list that was applied last
    var inUse = false;		    // set to false when the default (academic) word list is used
    var loadingFile = false;	    // set when loading a local file
    var pipeline = server_pipeline;

    // PRIVATE INTERFACE
    var readLocalFile = function(path) {
	var rawFile = new XMLHttpRequest();
	rawFile.open("GET", path, false);
	rawFile.onreadystatechange = function() {
	    if (rawFile.readyState === 4)
	    {
		if (rawFile.status === 200 || rawFile.status === 0)
		{
		    var allText = rawFile.responseText;
		    allText = allText.replace("\n", ",");
		    $("#custom_vocab_textarea").val(allText);
		    FLAIR.WEBRANKER.UTIL.TOAST.success("Loaded custom vocabulary from disk.", true, 3000);
		    loadingFile = false;
		}
		else if (rawFile.status >= 400)
		{
		    FLAIR.WEBRANKER.UTIL.TOAST.error("Couldn't load file. Error code - " + rawFile.status + ".", true, 3000);
		    loadingFile = false;
		}
	    }
	};
	rawFile.ontimeout = function(e) {
	    FLAIR.WEBRANKER.UTIL.TOAST.error("Couldn't load file - Operation timed out.", true, 3000);
	    loadingFile = false;
	};
	rawFile.onerror = function(e) {
	    FLAIR.WEBRANKER.UTIL.TOAST.error("Couldn't load file.", true, 3000);
	    loadingFile = false;
	};
	
	loadingFile = true;
	rawFile.send(null);
    };
    var showDialog = function(show) {
	if (show)
	    $("#modal_CustomVocab").modal('show');
	else
	    $("#modal_CustomVocab").modal('hide');
    };
    var apply = function() {
	if (loadingFile)
	{
	    FLAIR.WEBRANKER.UTIL.TOAST.warning("Please wait until the previous operation is complete.", true, 2500);
	    return;
	}
	
	var tempList = $("#custom_vocab_textarea").val();
	tempList = tempList.replaceAll("\n", ",");
	tempList = tempList.replaceAll(" ", ",");
	var wordArray = tempList.split(",");
	if (tempList.trim().length < 2 || wordArray.length < 1)
	{
	    FLAIR.WEBRANKER.UTIL.TOAST.warning("The word list is empty.", true, 2500);
	    return;
	}
	
	wordList = tempList;
	inUse = true;
	
	if (pipeline.setKeywords(wordArray) === false)
	    pipeline_onError();
	else
	{
	    $("#customVocabList-label").html("Custom");
	    FLAIR.WEBRANKER.UTIL.TOAST.success("The new vocabulary will be applied to the next search operation.", true, 3000);
	    return true;
	}
	
	return false;
    };
    
    // PUBLIC INTERFACE
    this.show = function() {
	showDialog(true);
    };
    this.disable = function() {
	if (inUse)
	{
	    var throwaway = [];
	    if (pipeline.setKeywords(throwaway) === false)
		pipeline_onError();
	    else
	    {
		inUse = false;
		$("#customVocabList-label").html("Academic");
		FLAIR.WEBRANKER.UTIL.TOAST.info("Academic vocabulary will be applied to the next search.", true, 3000);
	    }
	}
    };
    
    // C'TOR
    $("#modal_customVocab_buttonYes").click(function (e) {
	e.preventDefault();
	if (apply())
	    showDialog(false);
    });
    
    $("#modal_customVocab_buttonCancel").click(function (e) {
	e.preventDefault();
	showDialog(false);
    });
};

FLAIR.WEBRANKER.STATE = function() {
    // PRIVATE VARS
    var query = "";
    var language = FLAIR.WEBRANKER.CONSTANTS.DEFAULT_LANGUAGE;
    var totalResults = FLAIR.WEBRANKER.CONSTANTS.DEFAULT_NUM_RESULTS;		// no of results to search for, updated with the result count returned by the server

    var searchResults = [];			    // collection of all the search results returned by the server
    var parsedDocs = [];			    // collection of all the parsed docs sent by the server
    var displayedDocs = [];			    // collection of all the docs being displayed
    var filteredDocs = [];			    // collection of the docs indices (in the parsed docs collection) that shouldn't be displayed

    var selection = -1;				    // index of the current selection in the displayed docs collection

    var weightSettings_docLevel = [];		    // collection of weight data for the doc levels (A1-C2) and all constructions
    var weightSettings_constructions = [];	    // collection of weight data for grammatical constructions (### redundant?)
    var weightSettings_customVocabList = null;	    // weight data for the vocab list

    var filteredConstructions = [];		    // collection of the grammatical constructions that are ignored when ranking

    var bParam = 0;				    // used with the doc length slider
    var kParam = 1.7;				    // ### ?
    
    var parsedVisData = "";			    // CSV string of the parsed docs' construction data, used for visualisation
    
    var searchResultsFetched = false;		    // set to true after the search results are returned by the server
    var parsedDataFetched = false;		    // set to true after all the parsed data has been received by the client
    var busy = false;				    // set to true at the start of a search/upload op, reset at its end/cancellation
    
    var teacherMode = false;			    // when true, the imported settings are applied after each query is successfully parsed
    var urlQueryString = "";			    // url query string
    var importedSettings = {};			    // key-value pairs of the query string
    var applyingImportedSettings = false;	    // set to true when settings are applied
    
    var highlightKeywords = false;
    var customVocab = false;			    // set to true when a custom vocab is in use
    var customCorpus = true;			    // set to true when parsing a cutom corpus
    
    // PRIVATE INTERFACE
    var createWeightSettingPrototype = function() {
	var weightSetting = {
	    name: "",
	    weight: 0,
	    df: 0,
	    idf: 0,
	    color: ""
	};
	return weightSetting;
    };
    var refreshWeightSettings = function() {
	weightSettings_docLevel = [];
	weightSettings_constructions = [];
	weightSettings_customVocabList = null;

	// doc levels
	var levels = document.getElementById("settings_levels").getElementsByTagName("input");
	for (var k = 0; k < levels.length; k++) 
	{
	    var weightSetting = createWeightSettingPrototype();
	    weightSetting.name = levels[k].id;
	    if (levels[k].checked)
		weightSetting.weight = 1;
	    else
		weightSetting.weight = 0;

	    weightSettings_docLevel.push(weightSetting);
	    weightSettings_constructions.push(weightSetting);
	}
	
	// custom vocab
	var vocab = $("#customVocabList-gradientSlider").slider("option", "value");
	vocab = vocab / 5; // normalize
	weightSettings_customVocabList = createWeightSettingPrototype();
	weightSettings_customVocabList.name = "customVocabList";
	weightSettings_customVocabList.weight = vocab;

	// rest of the constructions
	$(".gradientSlider").each(function () {
	    var w = $(this).slider("option", "value");
	    w = w / 5; // turn into the scale from 0 to 1

	    var n = this.id.substring(0, this.id.indexOf("-"));
	    if (n.startsWith("customVocabList"))
		return;
	    
	    var weightSetting = createWeightSettingPrototype();
	    weightSetting.name = n;
	    weightSetting.weight = w;
	    if (w !== 0)
		weightSettings_docLevel.push(weightSetting);

	    weightSettings_constructions.push(weightSetting);
	});    
    };
    var getHighlightedDocHTML = function(doc) {
	var newText = ""; // String
	var occs = []; // occurrences of THESE constructionS in this doc
	var color_count = 0;
	
	for (var i in weightSettings_docLevel)
	{ 
	    var name = weightSettings_docLevel[i]["name"];
	    var color = "lightyellow";

	    if (name.indexOf("LEVEL") < 0 && weightSettings_docLevel[i]["df"] > 0)
	    {
		// assign colors
		color = FLAIR.WEBRANKER.CONSTANTS.HIGHLIGHT_COLORS[color_count];
		color_count++;
	    }
	    
	    weightSettings_docLevel[i]["color"] = color;
	    
	    for (var j in doc["highlights"]) 
	    {
		var o = doc["highlights"][j];
		if (o["construction"] === name && (weightSettings_docLevel[i]["weight"] < 0 || weightSettings_docLevel[i]["weight"] > 0))
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
	
	// highlight keywords
	if (highlightKeywords)
	{
	    for (var j in doc.keywords)
	    {
		var o = doc.keywords[j];
		o["color"] = "gold";
		if (hasCustomVocab() === false)
		    o["construction"] = "academic keyword";
		else
		    o["construction"] = "keyword";

		occs.push(o);
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
    };
    var parseQueryString = function() {
	importedSettings = {};
	var match, 
	    pl     = /\+/g,  // Regex for replacing addition symbol with a space
	    search = /([^&=]+)=?([^&]*)/g,
	    decode = function (s) { return decodeURIComponent(s.replace(pl, " ")); },
	    query  = urlQueryString;

	var propCount = 0;
	if (query !== "")
	{
	    while (match = search.exec(query)) 
	    {
		importedSettings[decode(match[1])] = decode(match[2]);
		propCount++;
	    }
	}
	
	if (propCount > 3)
	    return true;
	else
	    return false;
    };
    var hasCustomVocab = function() {
	return customVocab;
    };
    
    // PUBLIC INTERFACE
    this.reset = function(full_reset) {
	query = "";
	language = FLAIR.WEBRANKER.CONSTANTS.DEFAULT_LANGUAGE;
	totalResults = FLAIR.WEBRANKER.CONSTANTS.DEFAULT_NUM_RESULTS;
	
	searchResults = [];
	parsedDocs = [];
	displayedDocs = [];
	filteredDocs = [];

	selection = -1;

	weightSettings_docLevel = [];
	weightSettings_constructions = [];
	weightSettings_customVocabList = null;

	filteredConstructions = [];

	bParam = 0;
	kParam = 1.7;
	
	parsedVisData = "";
	
	searchResultsFetched = false;
	parsedDataFetched = false;
	busy = false;
	customCorpus = false;
	highlightKeywords = false;

	// reset the custom vocab state for each search
	var label = $("#customVocabList-label").text();
	label = label.toLowerCase();
	if (label.startsWith("acad"))
	    customVocab = false;
	else
	    customVocab = true;
	
	// "Teacher mode" state persists for the entire session, so we don't reset it by default
	if (full_reset === true)
	{
	    teacherMode = false;
	    importedSettings = {};
	    applyingImportedSettings = false;
	}
    };
    this.displayDocText = function(index) {
	if (searchResultsFetched === false)
	    return;
	
	if (index === -1)
	    index = selection;
	if (index === -1)
	    return;
	
	if (parsedDataFetched === true)
	{
	    // read from the diplayed docs
	    if (displayedDocs.length === 0)
		return;
	    else if (displayedDocs.length <= index)
	    {
		console.log("Invalid selection for text display. Must satisfy 0 < " + index + " < " + displayedDocs.length);
		return;
	    }

	    var previousSelection = selection;
	    selection = index;
	    var doc = displayedDocs[index];

	    document.getElementById("snapshot").innerHTML = "";
	    // remove the highlight from the results
	    if (previousSelection > -1 && document.getElementById("results_table").childNodes.length > 0)
		$("#results_table tr:nth-child(" + (previousSelection + 1) + ")").css("background-color", "white");

	    FLAIR.WEBRANKER.UTIL.TOGGLE.rightSidebar(true);

	    if (document.getElementById("results_table").childNodes.length > 0)
	    {
		// highlight the corresponding result
		$("#results_table tr:nth-child(" + (index + 1) + ")").css("background-color", "#fdf6e6");
		
		// get highlights
		var highlightedText = "<br>";
		highlightedText += "<div id='sidebar_text' readonly>";
		highlightedText += getHighlightedDocHTML(doc);
		highlightedText += "</div><br>";

		var info_box_1 = "<table style='width:100%'><tr>";
		if (doc.readabilityLevel !== null && doc.readabilityLevel.length > 3)
		    info_box_1 += "<td class='text-cell'><b>" + FLAIR.WEBRANKER.UTIL.formatDocLevel(doc.readabilityLevel) + "</b></td>";
		if (doc.numSents > 0)
		    info_box_1 += "<td class='text-cell'>~" + doc.numSents + " sentences</td>";
		if (doc.docLength > 0 || doc.numDeps > 0)
		    info_box_1 += "<td class='text-cell'>~" + doc.numDeps + " words (";
		if (hasCustomVocab() === false)
		    info_box_1 += "Academic: ~";
		else
		    info_box_1 += "Keywords: ~";
		info_box_1 += Math.floor(doc.relFreqKeywords * 100) + "%)</td></tr></table>";

		var info_box_2 = "<table id='constructions-table'><thead><tr><td><b>Construction</b></td><td><b>Count</b></td><td><b>Weight</b></td></tr></thead><tbody>";
		var info_box_3 = "<div id='show_all_constructions' hidden><table id='all_constructions_table' class='tablesorter'><thead><tr><td><b>Construction</b></td><td><b>Count</b></td><td><b>Relative Frequency %</b></td></tr></thead><tbody>";
		
		// keyword data
		if (highlightKeywords)
		{
		    info_box_2 += "<tr class='constructions_line'><td style='background-color:gold'>";
		    if (hasCustomVocab() === false)
			info_box_2 += "academic words";
		    else
			info_box_2 += "keywords";

		    info_box_2 += "</td><td class='text-cell'>" + doc.totalKeywords + "</td><td class='text-cell'>(" + weightSettings_customVocabList.weight + ")</td></tr>";
		}

		var count_col = 0;
		for (var i in weightSettings_constructions)
		{
		    var name = weightSettings_constructions[i]["name"];
		    var name_to_show = name;
		    var g = document.getElementById(weightSettings_constructions[i]["name"] + "-df");
		    if (g !== null)
		    {
			name_to_show = g.parentNode.textContent;
			if (name_to_show.indexOf("\n") > 0)
			    name_to_show = name_to_show.substring(0, name_to_show.indexOf("\n"));

			if (name_to_show.indexOf("(") > 0)
			    name_to_show = name_to_show.substring(0, name_to_show.indexOf("("));

			// one more layer of constructs
			var parent_name = g.parentNode; // div 
			if (parent_name !== null)
			    name_to_show = FLAIR.WEBRANKER.UTIL.generateConstructionName(parent_name, name_to_show);
		    }

		    var all = doc["constructions"];
		    for (var j in all)
		    {
			if (all[j] === name) 
			{
			    var ind = j;
			    if ((weightSettings_constructions[i]["weight"] > 0 ||
				 weightSettings_constructions[i]["weight"] < 0))
			    {
				var col = "lightyellow";
				for (var ws in weightSettings_docLevel)
				{ 
				    if (weightSettings_docLevel[ws]["name"] === weightSettings_constructions[i]["name"])
				    {
					col = weightSettings_docLevel[ws]["color"];
					break;
				    }
				}			
				
				info_box_2 += "<tr class='constructions_line'><td style='background-color:" + col + "'>" + name_to_show + "</td><td class='text-cell'>" + doc["frequencies"][ind] + "</td><td class='text-cell'>(" + weightSettings_constructions[i]["weight"] + ")</td></tr>";
			    }

			    info_box_3 += "<tr><td>" + name_to_show + "</td><td class='text-cell'>" + doc["frequencies"][ind] + "</td><td class='text-cell'>(" + ((Number(doc["relFrequencies"][ind])) * 100).toFixed(4) + ")</td></tr>";
			}
		    }
		}

		info_box_2 += "</tbody></table><br> <div id='info-highlights'></div>";
		info_box_3 += "</tbody></table><br></div><br><br><br>";

		document.getElementById("snapshot").innerHTML = info_box_1 + highlightedText + info_box_2 + info_box_3;

		var lines = document.getElementsByClassName("constructions_line");
		if (lines.length > 0)
		{
		    document.getElementById("constructions-table").hidden = false;
		    if (lines.length === 1)
		    {
			document.getElementById("info-highlights").innerHTML = "";
			document.getElementById("info-highlights").innerHTML = "<div class='panel panel-default' style='text-align: center'><a href='javascript:FLAIR.WEBRANKER.UTIL.TOGGLE.constructionsList();' style='color:darkgrey;'><span class='caret'></span> all constructions <span class='caret'></span></a></div><br>";
		    }
		    else
		    {
			document.getElementById("info-highlights").innerHTML = "* Highlights may overlap. Mouse over a highlight to see a tooltip<br> with the names of all embedded constructions.<br><br>"
				+ "<div class='panel panel-default' style='text-align: center'><a href='javascript:FLAIR.WEBRANKER.UTIL.TOGGLE.constructionsList();' style='color:darkgreen;'><span class='caret'></span> all constructions <span class='caret'></span></a></div>";
		    }
		} 
		else
		{
		    document.getElementById("info-highlights").innerHTML = "<button type='button' class='btn btn-warning' onclick='FLAIR.WEBRANKER.UTIL.TOGGLE.leftSidebar(true)' id='sidebar_grammar_button'>GRAMMAR SETTINGS</button>";
		    document.getElementById("constructions-table").hidden = true;
		}
	    }

	    var theVar = document.getElementById("all_constructions_table");
	    if (theVar)
		$("#all_constructions_table").tablesorter();
	}
	else
	{
	    // read from the search results
	    if (searchResults.length === 0)
		return;
	    else if (searchResults.length <= index)
	    {
		console.log("Invalid selection for text display. Must satisfy 0 < " + index + " < " + searchResults.length);
		return;
	    }
	    
	    var previousSelection = selection;
	    selection = index;
	    var doc = searchResults[index];

	    document.getElementById("snapshot").innerHTML = "";
	    // remove the highlight from the results
	    if (previousSelection > -1 && document.getElementById("results_table").childNodes.length > 0)
		$("#results_table tr:nth-child(" + (previousSelection + 1) + ")").css("background-color", "white");

	    FLAIR.WEBRANKER.UTIL.TOGGLE.rightSidebar(true);

	    if (document.getElementById("results_table").childNodes.length > 0)
	    {
		// highlight the corresponding result
		$("#results_table tr:nth-child(" + (index + 1) + ")").css("background-color", "#fdf6e6");

		var info_box_1 = "<table style='width:100%'><tr>";
		info_box_1 += "<td class='text-cell'><b>" + doc["title"] + "</b></td>";
		info_box_1 += "</tr></table>";

		var text_string = "<br>";
		text_string += "<div id='sidebar_text' readonly>";
		text_string += doc["text"];
		text_string += "</div><br>";

		document.getElementById("snapshot").innerHTML = info_box_1 + text_string;
		if (document.getElementById("info-highlights") !== null)
		    document.getElementById("info-highlights").innerHTML = "";
		if (document.getElementById("constructions-table") !== null)
		    document.getElementById("constructions-table").hidden = true;
	    }
	}
	
    };
    this.rerank = function() {
	if (parsedDataFetched === false || applyingImportedSettings === true)
	    return;
	    
	bParam = ($(".lengthSlider").slider("option", "value")) / 10;

	if (parsedDocs.length === 0)
	    return;

	displayedDocs = [];
	refreshWeightSettings();

	var avDocLen = -1;

	// check if doc can be displayed
	for (var k = 0; k < parsedDocs.length; k++) 
	{
	    var doc = parsedDocs[k];
	    var appropriateDoc = true;

	    for (var j = 0; j < weightSettings_constructions.length; j++)
	    {
		if (weightSettings_constructions[j]["name"].indexOf("LEVEL") > -1) 
		{
		    if (weightSettings_constructions[j]["weight"] === 0) 
		    {
			$("#" + weightSettings_constructions[j]["name"]).prop('checked', false);

			if (doc["readabilityLevel"] === weightSettings_constructions[j]["name"]) 
			    appropriateDoc = false;
		    } 
		    else if (weightSettings_constructions[j]["weight"] === 1)
			$("#" + weightSettings_constructions[j]["name"]).prop('checked', true);
		}
	    }

	    doc["gramScore"] = 0.0;

	    // check if the doc contains excluded constructions
	    for (var t = 0; t < filteredConstructions.length; t++)
	    {
		var constr_ind = doc.constructions.indexOf(filteredConstructions[t]);
		if (doc.frequencies[constr_ind] > 0)
		    appropriateDoc = false;
	    }
	    
	    if ($.inArray(k.toString(), filteredDocs) !== -1)
		appropriateDoc = false;
	    
	    // add data to object
	    if (appropriateDoc)
		displayedDocs.push(doc);
	}

	// calculate tf-idf of the grammar "query"
	// get df of each (selected) construction (in the settings)
	for (var i in weightSettings_constructions) 
	{
	    var name = weightSettings_constructions[i]["name"];
	    var count = 0; // number of docs with constructions[i] (this construction)
	    for (var j in displayedDocs)
	    {
		if (name.indexOf("LEVEL") > -1)
		{
		    var lev = displayedDocs[j]["readabilityLevel"];
		    if (lev === name)
			count++;
		}
		else
		{
		    var cs = displayedDocs[j]["constructions"];
		    for (var k in cs)
		    {
			if (cs[k] === name) 
			{
			    if (displayedDocs[j]["frequencies"][k] > 0)
				count++;

			    break;
			}
		    }
		}
	    }
	    // add df (document count) and idf (inverse document frequency) to each construction in the settings
	    weightSettings_constructions[i]["df"] = count;	    
	    weightSettings_constructions[i]["idf"] = Math.log((displayedDocs.length + 1) / count);
	    
	    for (var ws in weightSettings_docLevel)
	    { 
		if (weightSettings_docLevel[ws]["name"] === name)
		    weightSettings_docLevel[ws]["df"] = count;
	    }
	}
	
	// update vocab weight data and calc avg do length
	var count = 0;
	for (var j in displayedDocs)
	{
	    avDocLen += displayedDocs[j]["docLength"];
	    if (displayedDocs[j].totalKeywords > 0)
		count++;
	}
	weightSettings_customVocabList["df"] = count;
	weightSettings_customVocabList["idf"] = Math.log((displayedDocs.length + 1) / count);

	if (displayedDocs.length > 0)
	    avDocLen = avDocLen / displayedDocs.length;
	else
	    avDocLen = 0;

	//////// - end of tf, idf calculations


	// calculate totalWeight for each doc: BM25
	for (var d in displayedDocs)
	{
	    var dTotal = 0.0;
	    for (var constr in weightSettings_constructions)
	    {
		var name = weightSettings_constructions[constr]["name"];

		if ((weightSettings_constructions[constr]["weight"] > 0 ||
		     weightSettings_constructions[constr]["weight"] < 0) && weightSettings_constructions[constr]["df"] > 0)
		{ 
		    // cannot be NaN
		    var dConstrInd = displayedDocs[d]["constructions"].indexOf(name);
		    // if this construction is in this doc
		    if (dConstrInd > -1 && displayedDocs[d]["frequencies"][dConstrInd] > 0)
		    {
			var tf = displayedDocs[d]["frequencies"][dConstrInd];
			var idf = weightSettings_constructions[constr]["idf"];
			var tfNorm = ((kParam + 1) * tf) / (tf + kParam * (1 - bParam + bParam * (displayedDocs[d]["docLength"] / avDocLen)));
			var gramScore = tfNorm * idf;

			dTotal += gramScore * weightSettings_constructions[constr]["weight"];
		    }
		}
	    }
	    
	    // apply vocab weight
	    var tf = displayedDocs[d].totalKeywords;
	    var idf = weightSettings_customVocabList["idf"];
	    var tfNorm = ((kParam + 1) * tf) / (tf + kParam * (1 - bParam + bParam * (displayedDocs[d]["docLength"] / avDocLen)));
	    var gramScore = tfNorm * idf;
	    dTotal += gramScore * weightSettings_customVocabList["weight"];
            
	    displayedDocs[d]["gramScore"] = dTotal; // grammar score
	    displayedDocs[d]["totalWeight"] = displayedDocs[d]["gramScore"]; // total weight : TODO add rankWeight and textWeight
	}
	//// - end of calculating the total weight

	if (weightSettings_docLevel.length < 4 && weightSettings_customVocabList.weight === 0)
	{
	    if (bParam === 0)
	    {
		displayedDocs.sort(function (a, b) {
		    return parseInt(a.preRank) - parseInt(b.preRank);
		});
	    } 
	    else
	    {
		displayedDocs.sort(function (a, b) {
		    return parseInt(a.docLength) - parseInt(b.docLength);
		});
	    }
	}
	else
	{
	    displayedDocs.sort(function (a, b) {
		return Number(b.totalWeight) - Number(a.totalWeight);
	    });
	}

	document.getElementById("docs_info").innerHTML = (displayedDocs.length) + " results";
	for (var s in weightSettings_constructions) 
	{
	    if (weightSettings_constructions[s]["name"].startsWith("LEVEL"))
	    {
		if (document.getElementById(weightSettings_constructions[s]["name"]).checked)
		    document.getElementById(weightSettings_constructions[s]["name"] + "-df").innerHTML = "(" + weightSettings_constructions[s]["df"] + " / " + displayedDocs.length + " results)";
		else
		    document.getElementById(weightSettings_constructions[s]["name"] + "-df").innerHTML = "";
	    }
	    else
		document.getElementById(weightSettings_constructions[s]["name"] + "-df").innerHTML = "(" + weightSettings_constructions[s]["df"] + " / " + displayedDocs.length + ")";
	}

	var out = "";
	var i;
	for (i = 0; i < displayedDocs.length; i++)
	{
	    // show each object in a row of 3 cells: html / titles, urls and snippets / text
	    out += '<tr><td class="num_cell" style="font-size:x-large;">' +
		    (i + 1) + '&nbsp;<span style="color:lightgrey;font-size:small" title="original position in the rank">(' + displayedDocs[i].preRank + ')</span><br>' +
		    '</td><td  class="url_cell" style="width:40%;"><div><a href="' + displayedDocs[i].url + '" target="_blank"><b>' + displayedDocs[i].title + '</b></a></div>' +
		    '<div id="show_text_cell" title="Click to show text" onclick="FLAIR.WEBRANKER.singleton.displayDetails(' + i + ');"><span style="color:grey;font-size:smaller;">' + displayedDocs[i].urlToDisplay + '</span><br><span>' + displayedDocs[i].snippet + '</span></div></td></tr>';

	}

	document.getElementById("results_table").innerHTML = out;
	FLAIR.WEBRANKER.UTIL.resetUI(false, false, false, false, true, false);
    };
    
    this.setSearchParams = function(searchQuery, lang, numResults) {
	query = searchQuery;
	language = lang;
	totalResults = numResults;
    };
    this.getQuery = function() {
	return query;
    };
    this.getLanguage = function() {
	return language;
    };
    this.getTotalResults = function() {
	return totalResults;
    };
    this.setTotalResults = function(totalCount) {
	totalResults = totalCount;
    };
    
    this.addSearchResult = function(result) {
	searchResults.push(result);
    };
    this.getSearchResultCount = function() {
	return searchResults.length;
    };
    this.displaySearchResults = function() {
	if (searchResultsFetched === false || searchResults.length === 0)
	    return;
	
	var out = "";
	var i;
	for (i = 0; i < searchResults.length; i++)
	{
	    out += '<tr><td class="num_cell" style="font-size:x-large;">' +
		    (searchResults[i].rank) + '&nbsp;<br>' +
		    '</td><td  class="url_cell" style="width:40%;"><div><a href="' + searchResults[i].URL + '" target="_blank"><b>' + searchResults[i].title + '</b></a></div>' +
		    '<div id="show_text_cell" title="Click to show text" onclick="FLAIR.WEBRANKER.singleton.displayDetails(' + i + ');"><span style="color:grey;font-size:smaller;">' + searchResults[i].displayURL + '</span><br><span>' + searchResults[i].snippet + '</span></div></td></tr>';

	}

	document.getElementById("results_table").innerHTML = out;
    };
    
    this.addParsedDoc = function(doc) {
	parsedDocs.push(doc);
    };
    this.getParsedDocCount = function() {
	return parsedDocs.length;
    };
    
    this.getParsedVisData = function() {
	return parsedVisData;
    };
    this.setParsedVisData = function(csv_string) {
	parsedVisData = csv_string;
    };
    
    this.toggleConstructionExclusion = function(const_element) {
	var slider_id = const_element.parentElement.lastElementChild.firstElementChild.id;
	var construct_name = slider_id.substring(0, slider_id.indexOf("-gradientSlider"));

	// Get the state of the corresponding slider
	var disabled = $("#" + slider_id).slider("option", "disabled");

	// Disable/enable the slider
	$("#" + slider_id).slider("option", "disabled", !disabled);

	// add to/remove from excluded constructions
	if (filteredConstructions.indexOf(construct_name) === -1)
	{
	    // add
	    filteredConstructions.push(construct_name);	    
	}
	else
	{
	    // remove
	    var ind = filteredConstructions.indexOf(construct_name);
	    filteredConstructions.splice(ind, 1);
	}
    };
    this.isConstructionEnabled = function(name) {
	for (var i in weightSettings_docLevel)
	{
	    if (weightSettings_docLevel[i]["name"] === name)
		return true;
	}
	
	return false;
    };
    this.clearExcludedConstructions = function() {
	filteredConstructions = [];
    };
    
    this.addFilteredDoc = function(index) {
	if (filteredDocs.indexOf(index) === -1)
	    filteredDocs.push(index);
    };
    this.clearFilteredDocs = function() {
	filteredDocs = [];
    };
    this.isDocFiltered = function(index) {
	if (filteredDocs.indexOf(index) === -1)
	    return false;
	else
	    return true;
    };
    
    this.flagAsSearchResultsFetched = function() {
	searchResultsFetched = true;
    };
    this.flagAsParsedDataFetched = function() {
	parsedDataFetched = true;
	busy = false;
    };
    this.hasSearchResults = function() {
	return searchResultsFetched;
    };
    this.hasParsedData = function() {
	return parsedDataFetched;
    };
    
    this.exportSettings = function() {
	var settingQueryString = "";
	
	// doc levels first
	var levels = document.getElementById("settings_levels").getElementsByTagName("input");
	for (var k = 0; k < levels.length; k++) 
	{
	    settingQueryString += levels[k].id + "=";
	    if (levels[k].checked)
		settingQueryString += "1&";
	    else
		settingQueryString += "0&";
	}
	
	// the gradient sliders
	$(".gradientSlider").each(function () {
	    var value = $(this).slider("option", "value");
	    var name = this.id.substring(0, this.id.indexOf("-"));
	    var disabled = $(this).slider("option", "disabled");
	    
	    // only add if it's different from the default settings
	    if (disabled === true || value !== 0)
		settingQueryString += name + "=" + value + "_" + disabled + "&";
	});
	
	// remove the trailing ampersand
	settingQueryString = settingQueryString.slice(0, -1);
	
	return settingQueryString;
    };
    
    this.tryEnableTeacherMode = function() {
	if (teacherMode === true)
	{
	    console.log("Teacher mode was already enabled");
	    return;
	}
	
	urlQueryString = window.location.search.substring(1);
	if (parseQueryString() === true)
	{
	    teacherMode = true;
	    return true;
	}
	else
	    return false;
    };
    this.applyImportedSettings = function() {
	if (teacherMode === false)
	    return false;
	
	try 
	{
	    applyingImportedSettings = true;
	  
	    // set the doc levels
	    var levels = document.getElementById("settings_levels").getElementsByTagName("input");
	    for (var i = 0; i < levels.length; i++)
	    {
		if (importedSettings[levels[i].id] === "0")
		    $("#" + levels[i].id).prop("checked", false);
		else
		    $("#" + levels[i].id).prop("checked", true);
	    }

	    // gradient sliders
	    $("[id$=gradientSlider]").each(function () {
		 var name = this.id.substring(0, this.id.indexOf("-"));
		 if (name in importedSettings)
		 {
		     var param = importedSettings[name];
		     var value = param.substring(0, param.indexOf("_"));
		     var disabled = param.substring(param.indexOf("_") + 1, param.length);
		     
		     if (disabled === "true")
		     {
			var checkbox = document.getElementById("tgl-" + name);
			FLAIR.WEBRANKER.singleton.toggleConstruction(checkbox);
			$("#tgl-" + name).prop("checked", false);
		     }

		     $(this).slider("value", +value);
		 }
	     });
	    
	    applyingImportedSettings = false;
	    return true;
	}
	catch (err)
	{
	    console.log("Exception encountered whilst applying imported settings: " + err.message);
	    applyingImportedSettings = false;
	    teacherMode = false;
	    
	    FLAIR.WEBRANKER.UTIL.TOAST.error("FLAIR encountered an error while applying your custom settings.", true, 3000);
	    return false;
	}
    };
    
    this.toggleKeywordHighlighting = function() {
	highlightKeywords = highlightKeywords === false;
	this.displayDocText(-1);
    };
    
    this.flagAsBusy = function() {
	busy = true;
    };
    this.isBusy = function() {
	return busy;
    };
    
    this.flagAsCustomCorpus = function() {
	customCorpus = true;
    };
    this.isCustomCorpus = function() {
	return customCorpus;
    };
};

FLAIR.WEBRANKER.VISUALISATION = function(delegate_isDocFiltered, delegate_isConstructionEnabled) {
    // PRIVATE VARS
    var margin = {
	top: 55,
	right: 10,
	bottom: 5,
	left: 10
    };
    var width = 800 - margin.left - margin.right;
    var height = 370 - margin.top - margin.bottom;
    
    var x = d3.scale.ordinal().rangePoints([0, width], 1);
    var y = {};
    var dragging = {};

    var line = d3.svg.line();
    var axis = d3.svg.axis().orient("left");
    var background = null;
    var foreground = null;
    var dimensions = null;
    var svg = d3.select("body").select("svg")
	    .attr("width", width + margin.left + margin.right)
	    .attr("height", height + margin.top + margin.bottom)
	    .append("g")
	    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
    
    var dimension_names = ["document", "# of words"];
    
    var delegates = {
	isDocFiltered: delegate_isDocFiltered,
	isConstructionEnabled: delegate_isConstructionEnabled
    };
    var cache_csvTable = null;	    // the last cached data
    var updatingAxes = false;
    
    // PRIVATE INTERFACE
    var position = function(d) {
	var v = dragging[d];
	return (v === undefined || v === null) ? x(d) : v;
    };
    var transition = function(g) {
	return g.transition().duration(500);
    };
    var path = function(d) {
	// returns the path for a given data point
	return line(dimensions.map(function (p) {
	    return [position(p), y[p](+d[p])];
	}));
    };
    var brushstart = function() {
	d3.event.sourceEvent.stopPropagation();
    };
    var brush = function() {
	// handles a brush event, toggling the display of foreground lines
	var actives = dimensions.filter(function (p) {
	    return !y[p].brush.empty();
	});
	var extents = actives.map(function (p) {
	    return y[p].brush.extent();
	});
	
	foreground.style("display", function (d) {
	    return actives.every(function (p, i) {
		return extents[i][0] <= d[p] && d[p] <= extents[i][1];
	    }) ? null : "none";
	});
    };
    
    // PUBLIC INTERFACE
    this.visualise = function(csv_table) {
	svg.selectAll("*").remove();
	if (csv_table === undefined && cache_csvTable === null)
	    return;
	
	var parsedArray = null;
	if (csv_table === undefined && cache_csvTable !== null)
	    parsedArray = cache_csvTable;
	else
	    parsedArray = cache_csvTable = d3.csv.parse(csv_table);
	
	// extract the list of dimensions and create a scale for each
	x.domain(dimensions = d3.keys(parsedArray[0]).filter(function (d) {
            return d !== "construction" && (delegates.isConstructionEnabled(d) || ($.inArray(d, dimension_names) !== -1))
                    && (y[d] = d3.scale.linear()
                            .domain(d3.extent(parsedArray, function (p) {
                                return +p[d];
                            }))
                            .range([0, height]));
        }));
	
	// add grey background lines for context
        background = svg.append("g")
                .attr("class", "background")
                .selectAll("path")
                .data(parsedArray)
                .enter().append("path")
                .attr("d", path)
                .attr("stroke-width", 2);
	
        // add blue foreground lines for focus
        foreground = svg.append("g")
                .attr("class", "foreground")
                .selectAll("path")
                .data(parsedArray)
                .enter().append("path")
                .attr("d", path)
                .attr("stroke-width", 2);
	
	// add a group element for each dimension
        var g = svg.selectAll(".dimension")
                .data(dimensions)
                .enter().append("g")
                .attr("class", "dimension")
                .attr("transform", function (d) {
                    return "translate(" + x(d) + ")";
                })
                .call(d3.behavior.drag()
                        .origin(function (d) {
                            return {x: x(d)};
                        })
                        .on("dragstart", function (d) {
                            dragging[d] = x(d);
                            background.attr("visibility", "hidden");
                        })
                        .on("drag", function (d) {
                            dragging[d] = Math.min(width, Math.max(0, d3.event.x));
                            foreground.attr("d", path);
                            dimensions.sort(function (a, b) {
                                return position(a) - position(b);
                            });
                            x.domain(dimensions);
                            g.attr("transform", function (d) {
                                return "translate(" + position(d) + ")";
                            });
                        })
                        .on("dragend", function (d) {
                            delete dragging[d];
                            transition(d3.select(this)).attr("transform", "translate(" + x(d) + ")");
                            transition(foreground).attr("d", path);
                            background
                                    .attr("d", path)
                                    .transition()
                                    .delay(500)
                                    .duration(0)
                                    .attr("visibility", null);
                        }));
			
	// add an axis and title
        g.append("g")
                .attr("class", "axis")
                .each(function (d) {
                    d3.select(this).call(axis.scale(y[d]));
                })
                .append("text")
                .style("text-anchor", "middle")
                .style("font-size", "14px")
                .style("font-weight", "bold")
                .attr("y", -25)
                .attr("transform", "rotate(-20)")
                .text(function (d) {
                    // TODO set the text from the interface!
                    if (d === "document") {
                        d = "result";
                    } else if (d === "readability score") {
                        d = "complexity";
                    }

                    var name_to_show = d;
                    var g = document.getElementById(name_to_show + "-df");
                    if (g !== null) {
                        name_to_show = g.parentNode.textContent;
                        if (name_to_show.indexOf("\n") > 0) {
                            name_to_show = name_to_show.substring(0, name_to_show.indexOf("\n"));
                        }
                        if (name_to_show.indexOf("(") > 0) {
                            name_to_show = name_to_show.substring(0, name_to_show.indexOf("("));
                        }
                    }

                    return name_to_show;
                });
	
	// add and store a brush for each axis
        g.append("g")
                .attr("class", "brush")
                .each(function (d) {
                    d3.select(this).call(y[d].brush = d3.svg.brush().y(y[d]).on("brushstart", brushstart).on("brush", brush));
                })
                .selectAll("rect")
                .attr("x", -8)
                .attr("width", 16);
	
	// foreground
        svg.selectAll(".foreground > path")
                .each(function (d) {
                    // do not highlight filtered out docs
                    if (delegates.isDocFiltered(d["document"]))
                        this.style.display = "none";
                });
    };
    this.getFilteredDocs = function() {
	var outArray = [];
	// go through "path" in "svg", select those withOUT style=display:none
	$("path").each(function (d) {
	    if (this.style.display === "none" && delegates.isDocFiltered(this.__data__.document) === false)
		outArray.push(this.__data__.document);
	});
	
	return outArray;
    };
    this.toggleAxis = function(axis_element, state) {
	if (updatingAxes === true)
	    return;
	
	updatingAxes = true;
	
	var element_id = axis_element.id;
	// axis_name for the 4 default cases: "document", "sentences", "words", "score"
	// for others: e.g., whQuestions, etc.
	var axis_name = element_id.substring(0, element_id.indexOf("-vis"));

	// for default dimensions: TODO
	if (axis_name.indexOf("-def") === axis_name.length - 4)
	{
	    var tmp_name = axis_name.substring(0, axis_name.indexOf("-def"));
	    if (tmp_name === "document")
	    {
		if ($.inArray(tmp_name, dimension_names) === -1 && (state === undefined || state === true))
		    dimension_names.push(tmp_name);
		else if (state === undefined || state === false)
		{
		    // the axis is shown
		    var ind = dimension_names.indexOf(tmp_name);
		    dimension_names.splice(ind, 1);
		}
	    }

	    if (tmp_name === "score")
	    {
		tmp_name = "readability " + tmp_name;
		if ($.inArray(tmp_name, dimension_names) === -1 && (state === undefined || state === true))
		    dimension_names.push(tmp_name);
		else if (state === undefined || state === false)
		{
		    var ind = dimension_names.indexOf(tmp_name);
		    dimension_names.splice(ind, 1);
		}
	    }


	    if (tmp_name === "sentences" || tmp_name === "words")
	    {
		tmp_name = "# of " + tmp_name;
		if ($.inArray(tmp_name, dimension_names) === -1 && (state === undefined || state === true))
		    dimension_names.push(tmp_name);
		else if (state === undefined || state === false)
		{
		    var ind = dimension_names.indexOf(tmp_name);
		    dimension_names.splice(ind, 1);
		}
	    }

	}
	else
	{ 
	    // for constructions:
	    // set the corresponding slider to max
	    // will visualize automatically from rerank() when the value is changed
	    var slider_id = axis_name + "-gradientSlider";
	    // add/remove the axis name to/from the list
	    if ($.inArray(axis_name, dimension_names) === -1 && (state === undefined || state === true))
	    { 
		// the axis is removed
		dimension_names.push(axis_name);
		if ($("#" + slider_id).slider("option", "value") === 0)
		    $("#" + slider_id).slider("value", 5);
	    }
	    else if (state === undefined || state === false)
	    { 
		// the axis is shown
		var ind = dimension_names.indexOf(axis_name);
		dimension_names.splice(ind, 1);
		
		if ($("#" + slider_id).slider("option", "value") !== 0)
		    $("#" + slider_id).slider("value", 0);
	    }
	}
	
	updatingAxes = false;
    };
    this.resetAxes = function() {
	dimension_names = ["document", "# of words"];
        $("input[id$='-vis']").prop("checked", false);
        $("input[id$='-def-vis']").prop("checked", false);
	
	$("#document-def-vis").prop("checked", true);
	$("#words-def-vis").prop("checked", true);
    };
};

FLAIR.WEBRANKER.INSTANCE = function() {
    // HANDLERS
    var pipeline_noResults = function() {
	FLAIR.WEBRANKER.UTIL.resetUI();	

	FLAIR.WEBRANKER.UTIL.TOAST.error("No results for '" + state.getQuery() + "'.", true, 6000);
	state.reset();
    };
    var pipeline_onError = function() {
	FLAIR.WEBRANKER.UTIL.resetUI();
	state.reset();
	self.deinit();
	
	FLAIR.WEBRANKER.UTIL.TOAST.clear(true);
	FLAIR.WEBRANKER.UTIL.TOAST.error("FLAIR encountered a fatal error. Please refresh your browser.", false, 0);
    };
    var pipeline_onClose = function() {
	if (initialized === false)
	    return;
	
	FLAIR.WEBRANKER.UTIL.resetUI();
	state.reset();
	
	FLAIR.WEBRANKER.UTIL.WAIT.singleton.showNonClosable("Your session has expired. Please refresh your web browser.");
    };
    var complete_webSearch = function(jobID, totalResults) {
	if (totalResults === 0)
	{
	    pipeline_noResults();
	    return;
	}
	
	if (totalResults < state.getTotalResults())
	    FLAIR.WEBRANKER.UTIL.TOAST.warning("Some web pages couldn't be analyzed due to connectivity issues.", true, 4000);
	
	state.setTotalResults(totalResults);
	for (var i = 1; i <= totalResults; i++)
	{
	    if (pipeline.fetchSearchResults(i, 1) === false)
	    {
		pipeline_onError();
		break;
	    }
	}
    }; 
    var complete_parseSearchResults = function(jobID, totalDocs) {
	if (totalDocs === 0)
	{
	    pipeline_noResults();
	    return;
	}
	
	state.setTotalResults(totalDocs);
	for (var i = 1; i <= state.getTotalResults(); i++)
	{
	    if (pipeline.fetchParsedData(i, 1) === false)
	    {
		pipeline_onError();
		break;
	    }
	}
    };
    var complete_customCorpusUploaded = function(uploadCount) {
	if (uploadCount === 0)
	{
	    FLAIR.WEBRANKER.UTIL.resetUI();
	    state.reset();

	    FLAIR.WEBRANKER.UTIL.TOAST.info("No files were uploaded.", true, 3000);
	    return;
	}
	
	FLAIR.WEBRANKER.UTIL.WAIT.singleton.clear();
	FLAIR.WEBRANKER.UTIL.WAIT.singleton.showCancel("<h4>Analyzing custom corpus - Please wait...</h4>", 
	    function() {
		FLAIR.WEBRANKER.UTIL.cancelCurrentOperation();
	    });
	
	state.flagAsCustomCorpus();
	if (pipeline.parseCustomCorpus(FLAIR.WEBRANKER.CONSTANTS.DEFAULT_LANGUAGE) === false)
	    pipeline_onError();
    };
    var complete_parseCustomCorpus = function(jobID, totalDocs) {
	if (totalDocs === 0)
	{
	    FLAIR.WEBRANKER.UTIL.resetUI();
	    state.reset();

	    FLAIR.WEBRANKER.UTIL.TOAST.error("FLAIR couldn't parse any of the files.", true, 6000);
	    return;
	}
	
	state.setTotalResults(totalDocs);
	state.flagAsSearchResultsFetched();
	for (var i = 1; i <= state.getTotalResults(); i++)
	{
	    if (pipeline.fetchParsedData(i, 1) === false)
	    {
		pipeline_onError();
		break;
	    }
	}
    };
    var fetch_searchResults = function(jobID, searchResults) {
	for (var i = 0; i < searchResults.length; i++)
	    state.addSearchResult(searchResults[i]);
	
	if (state.getSearchResultCount() === state.getTotalResults())
	{
	    state.flagAsSearchResultsFetched();
	    state.displaySearchResults();
	    FLAIR.WEBRANKER.UTIL.WAIT.singleton.clear();
	    
	    FLAIR.WEBRANKER.UTIL.TOAST.info('<div style="text-align: center;">The search results can be reviewed whilst they are being analyzed in the background.<br/><br/><button type="button" class="btn btn-primary" onClick="FLAIR.WEBRANKER.UTIL.cancelCurrentOperation()">Cancel</button></div>', false, 0);
	    
	    if (pipeline.parseSearchResults(jobID) === false)
		pipeline_onError();
	}
    };
    var fetch_parsedData = function(jobID, parsedDocs) {
	for (var i = 0; i < parsedDocs.length; i++)
	{
	    if (parsedDocs[i].preRank === -1)
		parsedDocs[i].preRank = state.getParsedDocCount() + 1;
	    
	    state.addParsedDoc(parsedDocs[i]);
	}
	
	if (state.getParsedDocCount() === state.getTotalResults())
	{
	    if (pipeline.fetchParsedVisData() === false)
		pipeline_onError();
	}
    };
    var fetch_parsedVisData = function(jobID, csvTable) {
	state.setParsedVisData(csvTable);
	state.flagAsParsedDataFetched();
	self.resetAllSettingsAndFilters(true, false, true, true);
	
	FLAIR.WEBRANKER.UTIL.resetUI(false, false, true, false, true, false);
	FLAIR.WEBRANKER.UTIL.TOGGLE.leftSidebar(true);
	FLAIR.WEBRANKER.UTIL.TOGGLE.rightSidebar(true);

	FLAIR.WEBRANKER.UTIL.TOAST.clear(false);
	FLAIR.WEBRANKER.UTIL.TOAST.success("Analysis complete!", true, 4000);
	
	if (state.applyImportedSettings() === true)
	    FLAIR.WEBRANKER.UTIL.TOAST.info("Applied custom settings.", true, 4500);
	
	visualiser.visualise(csvTable);
	state.rerank();
    };
    
    // PRIVATE VARS
    var state = new FLAIR.WEBRANKER.STATE();
    var pipeline = new FLAIR.PLUMBING.PIPELINE(complete_webSearch, complete_parseSearchResults, complete_parseCustomCorpus, complete_customCorpusUploaded, fetch_searchResults, fetch_parsedData, fetch_parsedVisData, pipeline_onClose, pipeline_onError);
    var visualiser = new FLAIR.WEBRANKER.VISUALISATION(state.isDocFiltered, state.isConstructionEnabled);
    var customVocab = new FLAIR.WEBRANKER.CUSTOMVOCAB(pipeline);
    var initialized = false;
    var self = this;
    
    // PRIVATE INTERFACE
    var queueSearchOp = function() {
	FLAIR.WEBRANKER.UTIL.resetUI();
	FLAIR.WEBRANKER.UTIL.TOAST.clear(false);
	
	state.reset();
	
	var query = document.getElementById("search_field").value.trim();
	if (query.length === 0)
	{
	    FLAIR.WEBRANKER.UTIL.TOAST.error("Please enter a valid search query.", true, 5000);
	    return;
	}
	
	state.setSearchParams(query, FLAIR.WEBRANKER.CONSTANTS.DEFAULT_LANGUAGE, document.getElementById("fetch_result_count").value);
	state.flagAsBusy();
	
	FLAIR.WEBRANKER.UTIL.WAIT.singleton.showCancel("<h4>Searching the web - Please wait...</h4>", 
	    function() {
		FLAIR.WEBRANKER.UTIL.cancelCurrentOperation();
	    });
	    
	if (pipeline.performSearch(state.getQuery(), state.getLanguage(), state.getTotalResults()) === false)
	    pipeline_onError();
    };
    
    // PUBLIC INTERFACE
    this.init = function() {
	if (pipeline.init() === false)
	    return;
	
	if (state.tryEnableTeacherMode() === true)
	{
	    FLAIR.WEBRANKER.UTIL.TOAST.info("FLAIR will automatically apply your custom settings.", true, 5000);
	    console.log("Teacher mode enabled");
	}

	initialized = true;	
    };
    this.deinit = function() {
	initialized = false;
	pipeline.deinit();
	state.reset();
	
	pipeline = null;
	state = null;
    };
    
    this.cancelOperation = function() {
	FLAIR.WEBRANKER.UTIL.resetUI();
    
	pipeline.cancelLastJob();
	state.reset();
    };
    this.beginSearch = function() {
	if (state.hasSearchResults() === true && state.hasParsedData() === false)
	{
	    FLAIR.WEBRANKER.UTIL.WAIT.singleton.showYesNo("<br/><h4>The current search results are being analyzed in the background. Are you sure you want to begin a new search?</h4>",
		function() {
		    FLAIR.WEBRANKER.singleton.cancelOperation();
		    FLAIR.WEBRANKER.UTIL.TOAST.clear(true);
		    FLAIR.WEBRANKER.singleton.beginSearch();
		},
		function() {
		    // nothing to do here
		});
		
	    return;
	}
	
	queueSearchOp();
    };
    this.refreshRanking = function() {
	state.rerank();
    };
    this.toggleConstruction = function(const_element) {
	state.toggleConstructionExclusion(const_element);
	
	visualiser.visualise();
	state.rerank();
    };
    this.displayDetails = function(index) {
	state.displayDocText(index);
    };
    this.resetAllSettingsAndFilters = function(sliders, text_characteristics, visualiser_axes, doc_filter) {
	if (sliders === true)
	{
	    FLAIR.WEBRANKER.UTIL.resetSlider("all");
	    state.clearExcludedConstructions();
	}
	
	if (text_characteristics === true)
	    FLAIR.WEBRANKER.UTIL.resetTextCharacteristics();
	
	if (doc_filter === true)
	    state.clearFilteredDocs();
	
	if (visualiser_axes === true)
	{
	    visualiser.resetAxes();
	    visualiser.visualise();
	}
	
	state.rerank();
    };
    this.toggleVisualiserAxis = function(axis_element, state) {
	visualiser.toggleAxis(axis_element, state);
	visualiser.visualise();
    };
    this.applyVisualiserFilter = function() {
	var docsToFilter = visualiser.getFilteredDocs();
	state.clearFilteredDocs();
	for (var i = 0; i < docsToFilter.length; i++)
	    state.addFilteredDoc(docsToFilter[i]);
	state.rerank();
	FLAIR.WEBRANKER.UTIL.TOGGLE.visualiserDialog(false);
    };
    this.exportSettings = function() {
	var exportString = document.location.host + document.location.pathname + "?" + state.exportSettings();
	FLAIR.WEBRANKER.UTIL.showExportSettingsDialog(exportString);
    };
    this.toggleKeywordHighlighting = function() {
	state.toggleKeywordHighlighting();
    };
    this.showCustomVocab = function() {
	customVocab.show();
    };
    this.disableCustomVocab = function() {
	customVocab.disable();
    };
    this.showCustomCorpus = function() {
	if (state.isBusy() === true)
	{
	    FLAIR.WEBRANKER.UTIL.TOAST.info("Please wait until the current operation is complete.", true, 2500);
	    return;
	}
	
	FLAIR.WEBRANKER.UTIL.TOGGLE.customCorpusDialog(true);
    };
    this.uploadCustomCorpus = function() {
	FLAIR.WEBRANKER.UTIL.TOGGLE.customCorpusDialog(false);
	FLAIR.WEBRANKER.UTIL.WAIT.singleton.showNonClosable("Uploading files...");	
	FLAIR.WEBRANKER.UTIL.resetUI(true, true, false,  true, true, true);
	
	state.reset();
	state.flagAsBusy();
	FLAIR.WEBRANKER.UTIL.TOAST.clear(false);
    };
    this.showVisualiser = function() {
//	FLAIR.WEBRANKER.UTIL.resetSlider('all');
	
	if (state.isCustomCorpus() === false && state.getQuery() !== "") 
            $("#query_vis").html("\"" + state.getQuery() + "\" (" + state.getTotalResults() + " web pages)");
	else
            $("#query_vis").html("Interactive Visualization");
	
	visualiser.visualise();
	FLAIR.WEBRANKER.UTIL.TOGGLE.visualiserDialog(true);
    };
};

FLAIR.WEBRANKER.singleton = new FLAIR.WEBRANKER.INSTANCE();

window.onload = function() {
    $("#menu-toggle").click(function (e) {
	e.preventDefault();
	$("#wrapper").toggleClass("toggled");
    });

    $("#right-menu-toggle").click(function (e) {
	e.preventDefault();
	$("#sidebar-wrapper-right").toggleClass("active");
	$("#page-content-wrapper").toggleClass("active");
    });

    $("#constructs-toggle").click(function (e) {
	e.preventDefault();
	$("#myModal_Constructs").modal('show');
    });
    
    $("#about-toggle").click(function (e) {
	e.preventDefault();
	$("#myModal_About").modal('show');
    });
    
    $("#customVocabList-upload").click(function (e) {
	e.preventDefault();
	FLAIR.WEBRANKER.singleton.showCustomVocab();
    });
    
    $("#customVocabList-reset").click(function (e) {
	e.preventDefault();
	FLAIR.WEBRANKER.singleton.disableCustomVocab();
    });
    
    $("#customCorpus-toggle").click(function (e) {
	e.preventDefault();
	FLAIR.WEBRANKER.singleton.showCustomCorpus();
    });
    
    $("#modal_CustomCorpus_buttonSelectFiles").click(function (e) {
	e.preventDefault();
	$("#modal_CustomCorpus_fileSelect").trigger('click');
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
	var vis_id = this.id;
	vis_id = vis_id.substring(0, vis_id.indexOf("-gradientSlider"));
	
	if (vis_id !== "customVocabList")
	{
		vis_id = vis_id  + "-vis";
		
		if ($(this).slider("option", "value") !== 0)
		{
			$("#" + vis_id).prop("checked", true);
			FLAIR.WEBRANKER.singleton.toggleVisualiserAxis($("#" + vis_id)[0], true);
		}
		else
		{
			$("#" + vis_id).prop("checked", false);	
			FLAIR.WEBRANKER.singleton.toggleVisualiserAxis($("#" + vis_id)[0], false);
		}
	}	
	
	FLAIR.WEBRANKER.singleton.refreshRanking();
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
	    FLAIR.WEBRANKER.singleton.refreshRanking();
       }
    });
    
    $("#search_form").submit(function() {
	// prevent the enter key from reloading the page
	FLAIR.WEBRANKER.singleton.beginSearch();
	return false;
    });

    $(document).ready(function () {
	var theVar = document.getElementById("all_constructions_table");
	if (theVar)
	    $("#all_constructions_table").tablesorter();
	
	$('[data-toggle="popover"]').popover({ html: true });
    });

    $("#tgl-customVocabList").prop('checked', false);
    
    FLAIR.WEBRANKER.UTIL.resetUI();
    FLAIR.WEBRANKER.singleton.init();
};
window.onbeforeunload = function() {
    FLAIR.WEBRANKER.singleton.deinit();
};

