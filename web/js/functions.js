var loaded_docs = [];
var docs = [];
var doc_num = -1;
var settings = [];
var constructions = [];
var colors = ["lightgreen", "lightblue", "lightpink", "lightcyan", "lightsalmon", "lightgrey", "lightyellow"];
var doc_filter = [];
var b_parameter = 0;
var k_parameter = 1.7;
var excluded_constructions = [];
var pathToConstructionDataCSV = "";
var contextRootPath = "";

if (document.getElementById("sidebar_text") === null) {
    $("#snapshot").html("<div id='empty_sidebar_info'>Click on a search result <br>to display text here.</div>");
}

// display common searches
var common_searches = "";
($.ajax({
    url: "common_searches.txt",
    dataType: "text",
    success: function (data) {
        var lines = data.split("\n");

        for (var i = 0; i < lines.length; i++) {
            var person = lines[i].split("\t")[0];
            var event = lines[i].split("\t")[1];
            common_searches += '<tr><td onclick="javascript:void()">' + person + '</td><td onclick="javascript:void()">' + event + '</td></tr>';
        }
    },
    complete: function () {
        $("#results_cache > tbody").html(common_searches);
    }
}));






///////// SLIDERS /////////

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
        refreshRanking();
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
        refreshRanking();
    }
});

function initRanker(pathToJSON, numFiles, pathToCSV)
{
    if (pathToJSON === "")
        return;
    else if (numFiles === 0)
    {
        document.getElementById("results_table").innerHTML = "<br>No results! Search for something else.</br>";
        return;
    }
    
    var the_query = document.getElementById("search_field").value.trim().split(" ").join("_");
    var search = the_query.split("_").join(" ");
    b_parameter = ($(".lengthSlider").slider("option", "value")) / 5;
    if (b_parameter === null) {
        b_parameter = 0;
    }

    if ($("#recent_searches").html().indexOf(search) === -1) {
        $("#recent_searches").prepend('<p onclick="javascript:void()">' + search + '</p>');
    }

    $("#sidebar-wrapper-right").addClass("active");
    $("#page-content-wrapper").addClass("active");

    if (the_query === "") {
        return;
    }

    hideSnapshot();
    
    loaded_docs = [];
    docs = [];
    pathToConstructionDataCSV = pathToCSV;
    var loadedCount = 0;
    for (var i = 1; i <= numFiles; i++)
    {
        var loadpath = pathToJSON + '\\' + leftPad(i, 3) + '.json';
        console.log(loadpath);
        ($.ajax({
            url: loadpath,
            dataType: "json",
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            success: function(data) {
                loaded_docs.push(data);
                loadedCount++;
            },
            error: function() {
                console.log('no doc file: ' + loadpath);
            },
            complete: function() {
              if (loadedCount === numFiles)
                  refreshRanking();
            }
        }));
    }
}

function refreshRanking()
{
    if (loaded_docs.length === 0)
        return;
    
    docs = [];
    get_settings();
    var avDocLen = -1;

    for (var k = 0; k < loaded_docs.length; k++) 
    {
        var doc = loaded_docs[k];
        var appropriateDoc = true;

        for (var j = 0; j < constructions.length; j++)
        {
            if (constructions[j]["name"].indexOf("LEVEL") > -1) {
                if (constructions[j]["weight"] === 0) {
                    $("#" + constructions[j]["name"]).prop('checked', false);
                    if (doc["readabilityLevel"] === constructions[j]["name"]) {
                        appropriateDoc = false;
                    }
                } else if (constructions[j]["weight"] === 1) {
                    $("#" + constructions[j]["name"]).prop('checked', true);
                }

            }
        }

        doc["gramScore"] = 0.0;

        // check if the doc contains excluded constructions
        for (var t = 0; t < excluded_constructions.length; t++) {
            var constr_ind = doc.constructions.indexOf(excluded_constructions[t]);
            if (doc.frequencies[constr_ind] > 0) {
                appropriateDoc = false;
            }
        }

        // add data to object
        if (appropriateDoc && ($.inArray(k, doc_filter) === -1)) {
            docs.push(doc);
        }
    }
    
    // calculate tf-idf of the grammar "query"
    // get df of each (selected) construction (in the settings)
    for (var i in constructions) 
    {
        var name = constructions[i]["name"];
        var count = 0; // number of docs with constructions[i] (this construction)
        for (var j in docs)
        {
            if (name.indexOf("LEVEL") > -1) {
                var lev = docs[j]["readabilityLevel"];
                if (lev === name) {
                    count++;
                }
            } 
            else {
                var cs = docs[j]["constructions"];
                for (var k in cs)
                {
                    if (cs[k] === name) {
                        if (docs[j]["frequencies"][k] > 0) {
                            count++;
                        }
                        break;
                    }
                }
            }
        }
        // add df (document count) and idf (inverse document frequency) to each construction in the settings
        constructions[i]["df"] = count;
        constructions[i]["idf"] = Math.log((docs.length + 1) / count);
    }

    // calculate average doc length
    for (var d in docs)
        avDocLen += docs[d]["docLength"];
    
    if (docs.length > 0)
        avDocLen = avDocLen / docs.length;
    else
        avDocLen = 0;

    //////// - end of tf, idf calculations


    // calculate totalWeight for each doc: BM25
    for (var d in docs)
    {
        var dTotal = 0.0;
        for (var constr in constructions)
        {
            var name = constructions[constr]["name"];

            if ((constructions[constr]["weight"] > 0 || constructions[constr]["weight"] < 0) && constructions[constr]["df"] > 0) { // cannot be NaN
                var dConstrInd = docs[d]["constructions"].indexOf(name);
                // if this construction is in this doc
                if (dConstrInd > -1 && docs[d]["frequencies"][dConstrInd] > 0) {

                    var tf = docs[d]["frequencies"][dConstrInd];
                    var idf = constructions[constr]["idf"];
                    var tfNorm = ((k_parameter + 1) * tf) / (tf + k_parameter * (1 - b_parameter + b_parameter * (docs[d]["docLength"] / avDocLen)));
                    var gramScore = tfNorm * idf;

                    dTotal += gramScore * constructions[constr]["weight"];
                }
            }
        }
        
        docs[d]["gramScore"] = dTotal; // grammar score
        docs[d]["totalWeight"] = docs[d]["gramScore"]; // total weight : TODO add rankWeight and textWeight
    }
    //// - end of calculating the total weight


    if (settings.length < 4) {
        if (b_parameter === 0) {
            docs.sort(function (a, b) {
                return parseInt(a.preRank) - parseInt(b.preRank);
            });
        } 
        else {
            docs.sort(function (a, b) {
                return parseInt(a.docLength) - parseInt(b.docLength);
            });
        }
    }
    else {
        docs.sort(function (a, b) {
            return Number(b.totalWeight) - Number(a.totalWeight);
        });
    }

    document.getElementById("docs_info").innerHTML = (docs.length) + " results";
    for (var s in constructions) 
    {
        if (constructions[s]["name"].startsWith("LEVEL")) {
            if (document.getElementById(constructions[s]["name"]).checked) {
                document.getElementById(constructions[s]["name"] + "-df").innerHTML = "(" + constructions[s]["df"] + " / " + docs.length + " results)";
            }
            else {
                document.getElementById(constructions[s]["name"] + "-df").innerHTML = "";
            }
        } 
        else {
            document.getElementById(constructions[s]["name"] + "-df").innerHTML = "(" + constructions[s]["df"] + " / " + docs.length + ")";
        }
    }

    var out = "";
    var i;
    for (i = 0; i < docs.length; i++) {
        // show each object in a row of 3 cells: html / titles, urls and snippets / text
        out += '<tr><td class="num_cell" style="font-size:x-large;">' +
                (i + 1) + '&nbsp;<span style="color:lightgrey;font-size:small" title="original position in the rank">(' + docs[i].preRank + ')</span><br>' +
                '</td><td  class="url_cell" style="width:40%;"><div><a href="' + docs[i].url + '" target="_blank"><b>' + docs[i].title + '</b></a></div>' +
                '<div id="show_text_cell" title="Click to show text" onclick="showText(' + i + ');"><span style="color:grey;font-size:smaller;">' + docs[i].urlToDisplay + '</span><br><span>' + docs[i].snippet + '</span></div></td></tr>';

    }

    document.getElementById("results_table").innerHTML = out;
    visualize();
}




function leftPad(number, targetLength) {
    var output = number + '';
    while (output.length < targetLength) {
        output = '0' + output;
    }
    return output;
}


function hideSnapshot() {
    // clear the snapshot area
    $("#snapshot").html("<div id='empty_sidebar_info'>Click on a search result <br>to display text here.</div>");

    // remove the highlight from the results
    if (doc_num > -1) {
        $("#results_table tr:nth-child(" + (doc_num + 1) + ")").css("background-color", "white");
    }
    //doc_num = -1;
}


function showText(doc_number)
{
    if (docs.length === 0)
        return;
    
    doc_num = doc_number;
    var doc = docs[doc_num];
    
    hideText(); // remove the previous one

    // show Settings sidebar
    $("#sidebar-wrapper-right").addClass("active");
    $("#page-content-wrapper").addClass("active");

    if (document.getElementById("results_table").childNodes.length > 0) {
        // highlight the corresponding result
        $("#results_table tr:nth-child(" + (doc_num + 1) + ")").css("background-color", "#fdf6e6");


        // show a modal if text has not been extracted
        var info_box_1 = "<table style='width:100%'><tr>";
        if (doc.readabilityLevel !== null && doc.readabilityLevel.length > 3) {
            info_box_1 += "<td class='text-cell'><b>" + formatLevel(doc.readabilityLevel) + "</b></td>";
        }
        if (doc.numSents > 0) {
            info_box_1 += "<td class='text-cell'>~" + doc.numSents + " sentences</td>";
        }
        if (doc.docLength > 0 || doc.numDeps > 0) {
            info_box_1 += "<td class='text-cell'>~" + doc.numDeps + " words</td>";
        }
        info_box_1 += "</tr></table>";

        var info_box_2 = "<table id='constructions-table'><thead><tr><td><b>Construct</b></td><td><b>Count</b></td><td><b>Weight</b></td></tr></thead><tbody>";
        var info_box_3 = "<div id='show_all_constructions' hidden><table><thead><tr><td><b>Construct</b></td><td><b>Count</b></td><td><b>%</b></td></tr></thead><tbody>";

        var count_col = 0;
        for (var i in constructions) {
            var name = constructions[i]["name"];
            var name_to_show = name;
            var g = document.getElementById(constructions[i]["name"] + "-df");
            if (g !== null) {
                name_to_show = g.parentNode.textContent;
                if (name_to_show.indexOf("\n") > 0) {
                    name_to_show = name_to_show.substring(0, name_to_show.indexOf("\n"));
                }
                if (name_to_show.indexOf("(") > 0) {
                    name_to_show = name_to_show.substring(0, name_to_show.indexOf("("));
                }
            }
            var all = doc["constructions"];

            for (var j in all) {
                if (all[j] === name) {
                    var ind = j;

                    if ((constructions[i]["weight"] > 0 || constructions[i]["weight"] < 0)) {
                        var col = "lightyellow";
                        if (count_col < colors.length) {
                            col = colors[count_col];
                            count_col++;
                        }
                        info_box_2 += "<tr class='constructions_line'><td style='background-color:" + col + "'>" + name_to_show + "</td><td class='text-cell'>" + doc["frequencies"][ind] + "</td><td class='text-cell'>(" + constructions[i]["weight"] + ")</td></tr>";
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
        text_string += highlightText(doc);
        text_string += "</div><br>";

        document.getElementById("snapshot").innerHTML = info_box_1 + text_string + info_box_2 + info_box_3;

        var lines = document.getElementsByClassName("constructions_line");
        if (lines.length > 0) {
            document.getElementById("constructions-table").hidden = false;
            if (lines.length === 1) {
                document.getElementById("info-highlights").innerHTML = "";
                document.getElementById("info-highlights").innerHTML = "<div class='panel panel-default' style='text-align: center'><a href='javascript:show_all_constructions();' style='color:darkgrey;'><span class='caret'></span> all constructions <span class='caret'></span></a></div><br>";
            }
            else {
                document.getElementById("info-highlights").innerHTML = "* Highlights may overlap. Mouse over a highlight to see a tooltip<br> with the names of all embedded constructions.<br><br>"
                        + "<div class='panel panel-default' style='text-align: center'><a href='javascript:show_all_constructions();' style='color:darkgreen;'> all constructions </a></div>";
            }
        } else {
            document.getElementById("info-highlights").innerHTML = "<button type='button' class='btn btn-warning' onclick='toggle_left_sidebar(false)' id='sidebar_grammar_button'>GRAMMAR SETTINGS</button>";
            document.getElementById("constructions-table").hidden = true;
        }
    }
}

function show_all_constructions() {
    document.getElementById("show_all_constructions").hidden = (!document.getElementById("show_all_constructions").hidden);
}

function toggle_left_sidebar(show_parsing_window) {
    if (show_parsing_window) {
        $("#myModal_Parse").modal('show');
    } else
        $("#wrapper").toggleClass("toggled");
}

/**
 * Turns a string "LEVEL-c" into "C1-C2"
 * @param {type} level
 * @returns {undefined}
 */
function formatLevel(level) {
    var l = level.substring(level.indexOf("-") + 1);
    l = l.toUpperCase();
    return (l + "1-" + l + "2");
}

function hideText() {
    // clear the snapshot area
    document.getElementById("snapshot").innerHTML = "";
    // remove the highlight from the results
    if (doc_num > -1 && document.getElementById("results_table").childNodes.length > 0) {
        $("#results_table tr:nth-child(" + (doc_num + 1) + ")").css("background-color", "white");
    }
}


function rerank(called_from_search, docs_path) {
    if (the_query === null || the_query === "") {
        the_query = document.getElementById("search_field").value.trim().split(" ").join("_");
    }
    
    var search = the_query.split("_").join(" ");
    b_parameter = ($(".lengthSlider").slider("option", "value")) / 5;
    if (b_parameter === null) {
        b_parameter = 0;
    }

    if ($("#recent_searches").html().indexOf(search) === -1) {
        $("#recent_searches").prepend('<p onclick="javascript:void()">' + search + '</p>');
    }

// hide the right sidebar
    $("#sidebar-wrapper-right").removeClass("active");
    $("#page-content-wrapper").removeClass("active");

    if (the_query === "") {
        return;
    }

    hideSnapshot();
    // get the settings
    get_settings(false); // do not read from file
    
    docs = [];
    if (called_from_search) {
        doc_filter = [];
        get_documents(false, docs_path);
    } else {
        addDocs(true, false); // true: reranked; false: not from cache
    }

    // generate parallel coordinates - visualization
    // TODO: uncomment:
    // visualize();
}

function reset(what) {
    if (what === "all") {
        $(".gradientSlider").each(function () {
            if ($(this).slider("option", "value") !== 0) {
                $(this).slider("option", "value", 0);
            }
        });
    } else {
        $("#collapse_" + what + " div[id$='-gradientSlider']").each(function () {
            if ($(this).slider("option", "value") !== 0) {
                $(this).slider("option", "value", 0);
            }
        });
    }
}


function highlightText(doc) {
    var newText = ""; // String

    var occs = []; // occurrences of THESE constructionS in this doc

    var color_count = 0;

    for (var i in settings) { // for each String

        var name = settings[i]["name"];

        var color = "lightyellow";

        if (name.indexOf("LEVEL") < 0) {
            // assign colors
            color = colors[color_count];
            color_count++;
        }

        for (var j in doc["highlights"]) { // for each Occurrence
            var o = doc["highlights"][j];
            if (o["construction"] === name && (settings[i]["weight"] < 0 || settings[i]["weight"] > 0)) {
                o["color"] = color;
                var contains = false;
                for (var k in occs) {
                    var occ = occs[k];
                    if (occ["construction"] === name && occ["start"] === o["start"] && occ["end"] === o["end"]) {
                        contains = true;
                        break;
                    }
                }
                if (!contains) {
                    occs.push(o);
                }

            }
        }
    }

    // // sort the occurrences based on their (end) indices
    var allIndices = []; // String[][]

    for (var j = 0; j < occs.length * 2; j++) {
        allIndices.push({});
    }

    for (var i = 0; i < occs.length; i++) {
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
    allIndices.sort(function (ind1, ind2) {

        var indexSt1 = (ind1["index"]).toString(); // String
        var indexSt2 = (ind2["index"]).toString(); // String
        var indexEnd1 = ""; // String
        var indexEnd2 = ""; // String

        var s1 = -5; // int
        var s2 = -5; // int
        var e1 = -5; // int
        var e2 = -5; // int

        // indexSt1 can be either of form start-end or just end
        if (indexSt1.indexOf("-") > -1) {
            indexEnd1 = indexSt1.substring(indexSt1.indexOf("-") + 1);
            indexSt1 = indexSt1.substring(0, indexSt1.indexOf("-"));
        }
        if (indexSt2.indexOf("-") > -1) {
            indexEnd2 = indexSt2.substring(indexSt2.indexOf("-") + 1);
            indexSt2 = indexSt2.substring(0, indexSt2.indexOf("-"));
        }

        s1 = parseInt(indexSt1); // int
        s2 = parseInt(indexSt2); // int

        var sComp = s2 - s1; // int

        if (sComp !== 0) {
            return sComp;
        }
        else {
            if (indexEnd1.length > 0) {

                if (indexEnd2.length > 0) {
                    e1 = parseInt(indexEnd1); // int
                    e2 = parseInt(indexEnd2); // int

                    var l1 = Math.abs(e1 - s1); // int
                    var l2 = Math.abs(e2 - s2); // int

                    return (l1 - l2);
                } else {
                    return sComp;
                }
            } else if (indexEnd2.length > 0) {
                if (indexEnd1.length > 0) {
                    e1 = parseInt(indexEnd1);
                    e2 = parseInt(indexEnd2);

                    var l1 = Math.abs(e1 - s1); // int
                    var l2 = Math.abs(e2 - s2); // int

                    return (l1 - l2);
                } else {
                    return sComp;
                }
            } else {
                return sComp;
            }
        }
    });


    // insert span tags into the text : start from the end
    var docText = doc["text"]; // String

// get rid of html tags inside of text
    var tmp = document.createElement("DIV");
    tmp.innerHTML = docText;
    docText = tmp.textContent || tmp.innerText;

    var prevStartInd = -1; // int // prev ind of a start-tag
    var prevEndInd = -1; // int // prev ind of a start-tag
    var prevConstruct = ""; // String
    var prevStartTag = ""; // String

    for (var ind in allIndices) { // for String[]

        var insertHere = -10; // int

        var curItem = (allIndices[ind]["index"]).toString(); // String
        var tmpEnd = -1; // int
        var tmpStart = -1; // int
        // indexSt1 can be either of form start-end or just end
        if (curItem.indexOf("-") > -1) {
            tmpStart = parseInt(curItem.substring(0, curItem.indexOf("-")));
            tmpEnd = parseInt(curItem.substring(curItem.indexOf("-") + 1));
            insertHere = tmpStart;
        } else {
            insertHere = parseInt(curItem);
        }

        var tag = allIndices[ind]["tag"]; // String

        // show several constructions on mouseover, ONLY if they fully overlap (e.g., complex sentence, direct question)
        if (tag.indexOf("<span") > -1) {

            var toInsertBefore = ""; // String // to take care in more than 2 overlapping constructions

            if (prevStartInd === insertHere && prevEndInd === tmpEnd) {
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



function get_settings() {
    // save the settings
    settings = [];
    constructions = [];
    // save the level info
    var levels = document.getElementById("settings_levels").getElementsByTagName("input");
    for (var k = 0; k < levels.length; k++) {
        var setObj = {};
        setObj["name"] = levels[k].id;
        if (levels[k].checked) {
            setObj["weight"] = 1;
        } else {
            setObj["weight"] = 0;
        }
        settings.push(setObj);
        constructions.push(setObj);
    }

    // save the weights for constructions
    $(".gradientSlider").each(function () {
        var w = $(this).slider("option", "value");
        w = w / 5; // turn into the scale from 0 to 1

        var n = this.id.substring(0, this.id.indexOf("-"));
        var setObj = {};
        setObj["name"] = n;
        setObj["weight"] = w;
        if (w !== 0) {
            settings.push(setObj);
        }
        constructions.push(setObj);
    });
}

function hideRightSidebar() {
    $("#sidebar-wrapper-right").toggleClass("toggled");
}

function show_visual() {
    $('#myModal_Visualize').modal('show');
}

;


function log_feedback(ranked_position) {
    var num = ranked_position.substring(ranked_position.indexOf("-") + 1);
    if ($("#star_" + num).hasClass("glyphicon-star-empty")) { // "positive"
        $("#star_" + num).removeClass("glyphicon-star-empty");
        $("#star_" + num).addClass("glyphicon-star");

    } else { // "negative"
        $("#star_" + num).removeClass("glyphicon-star");
        $("#star_" + num).addClass("glyphicon-star-empty");
    }
    $("#thanks_" + num).show(100);
    $(function () {
        // setTimeout() function will be fired after page is loaded
        // it will wait for 5 sec. and then will fire
        // $("#successMessage").hide() function
        setTimeout(function () {
            $("#thanks_" + num).hide('blind', {}, 200);
        }, 500);
    });
}
;

/**
 * 
 * @param {String} action : search/click
 * @param {String} content : query/number of clicked doc in list
 * @returns {String} log
 */
function log(action, content) {
    var logged = "";
    logged += action;
    logged += "\t" + content;
}



function filter_visual() {
    doc_filter = [];
    // populate doc_filter
    // go through "path" in "svg", select those withOUT style=display:none
    $(".foreground > path").each(function (d) {
        if (this.style.display === "none") {
            doc_filter.push(d); // store the number
        }
    });

    refreshRanking();

    // close the modal
    $('#myModal_Visualize').modal('hide');

}


function exclude(element) {

    var slider_id = element.parentElement.lastElementChild.firstElementChild.id;
    var construct_name = slider_id.substring(0, slider_id.indexOf("-gradientSlider"));

    // Get the state of the corresponding slider
    var disabled = $("#" + slider_id).slider("option", "disabled");

    // Disable/enable the slider
    $("#" + slider_id).slider("option", "disabled", !disabled);

    // TODO: change the tooltip (uncheck to exclude / check to include)
    if (disabled) {
        // change into "uncheck to exclude"
    } else {
        // change into "check to include"
    }

    // add to/remove from excluded constructions
    if (excluded_constructions.indexOf(construct_name) === -1) {
        // add
        excluded_constructions.push(construct_name);
    } else {
        // remove
        var ind = excluded_constructions.indexOf(construct_name);
        excluded_constructions.splice(ind, 1);
    }


    // rerank the results on change
    refreshRanking();
}