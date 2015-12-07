/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var NUM_OF_RESULTS = 60;
//var PATH_TO_RESULTS = "/tmp/FLAIR_results/";
var jsFileLocation = $('script[src*=functions_logger]').attr('src');  // the js file path
var PATH_TO_RESULTS = jsFileLocation.replace('functions.js', '../results');   // the js folder path
var nonempty_docs = 0;
var docs = [];
var the_query = "";
var doc_num = -1;
var search_engine = "Bing";
//var settings_file = "/tmp/FLAIR_results/settings.json";
var settings_file = jsFileLocation.replace('functions.js', '../settings.json');   // the js folder path
var settings = [];
var constructions = [];
var from_show_distribution = false;


/// Workaround Windows

if (navigator.userAgent.match(/IEMobile\/10\.0/)) {
  var msViewportStyle = document.createElement('style')
  msViewportStyle.appendChild(
    document.createTextNode(
      '@-ms-viewport{width:auto!important}'
    )
  )
  document.querySelector('head').appendChild(msViewportStyle)
}

//////////////// EXPERIMENT //////////////////

function search_experiment(query) {

    the_query = query;

    if (the_query === "") {
        the_query = "Tuebingen";
    }

    log("search", the_query);

    docs = [];
    //document.getElementById("results_table").innerHTML = "";
//    document.getElementById("display_query").innerHTML = query.split("_").join(" ");
    addDocs_experiment(the_query); // false: not reranked

}


function addDocs_experiment(query) {
    
    var results = "";
    
    for (var i = 1; i < NUM_OF_RESULTS + 1; i++) {
        var path = PATH_TO_RESULTS + query + '/weights/' + leftPad(i, 3) + '.json';


        ($.ajax({
            url: path,
            dataType: "json",
            success: function (data) {
                docs.push(data);
            },
            error: function () {
            },
            complete: function () {
                results = displayEach_experiment(docs);
            }
        }));
        
        return results;
        
    }
}



function displayEach_experiment(jsonList) {

    jsonList.sort(function (a, b) {
        return parseInt(a.preRank) - parseInt(b.preRank);
    });

    var out = "";
    var i;
    for (i = 0; i < jsonList.length; i++) {
        // show each object in a row of 3 cells: html / titles, urls and snippets / text
        var html = jsonList[i].html;
        var text = jsonList[i].text;
//        out += '<tr onclick="showSnapshot(' + i + ');"><td class="num_cell" style="width:2%;text-align:center;font-size:x-large;">' + jsonList[i].postRank + '<br><span style="color:lightgrey;font-size:small">(' + jsonList[i].preRank + ')</span></td><td class="text_cell" style="width:10%;"><div style="font-size:4pt;height:100px;overflow:scroll;color:lightblue;border:solid 1px lightgrey;border-radius:5px;padding:5px;">' + text + '</div></td><td  class="url_cell" style="width:50%"><a href="' + jsonList[i].url + '" target="_blank"><b>' + jsonList[i].title + '</b></a><br><span style="color:grey;font-size:smaller;">' + jsonList[i].urlToDisplay + '</span><br><span>' + jsonList[i].snippet + '</span></td></tr>';
        out += '<tr onclick="log(\'click\',\'' + (i + 1) + '\')"><td class="num_cell" style="font-size:x-large;">' + (i + 1) + '&nbsp;</td><td class="url_cell" style="width:40%"><a href="' + jsonList[i].url + '" target="_blank"><b>' + jsonList[i].title + '</b></a><br><span style="color:grey;font-size:smaller;">' + jsonList[i].urlToDisplay + '</span><br><span>' + jsonList[i].snippet + '</span></td></tr>';

    }
    
    return out;

}

//////////////////// END ////////////////////






/**
 * @param {boolean} reranked 
 * @param {Array} jsonList array of objects (docs)
 */
function displayEach(jsonList, reranked) {

    jsonList.sort(function (a, b) {
        return parseInt(a.preRank) - parseInt(b.preRank);
    });

    var out = "";
    var i;
    for (i = 0; i < jsonList.length; i++) {
        // show each object in a row of 3 cells: html / titles, urls and snippets / text
        var html = jsonList[i].html;
        var text = jsonList[i].text;
//        out += '<tr onclick="showSnapshot(' + i + ');"><td class="num_cell" style="width:2%;text-align:center;font-size:x-large;">' + jsonList[i].postRank + '<br><span style="color:lightgrey;font-size:small">(' + jsonList[i].preRank + ')</span></td><td class="text_cell" style="width:10%;"><div style="font-size:4pt;height:100px;overflow:scroll;color:lightblue;border:solid 1px lightgrey;border-radius:5px;padding:5px;">' + text + '</div></td><td  class="url_cell" style="width:50%"><a href="' + jsonList[i].url + '" target="_blank"><b>' + jsonList[i].title + '</b></a><br><span style="color:grey;font-size:smaller;">' + jsonList[i].urlToDisplay + '</span><br><span>' + jsonList[i].snippet + '</span></td></tr>';
        out += '<tr onclick="log(\'click\',\'' + (i + 1) + '\')"><td class="num_cell" style="font-size:x-large;">' + (i + 1) + '&nbsp;</td><td class="url_cell" style="width:40%"><a href="' + jsonList[i].url + '" target="_blank"><b>' + jsonList[i].title + '</b></a><br><span style="color:grey;font-size:smaller;">' + jsonList[i].urlToDisplay + '</span><br><span>' + jsonList[i].snippet + '</span></td></tr>';

    }

    document.getElementById("results_table").innerHTML = out;

}

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

/**
 * Mainly for debugging.
 * but also in the following scenario:
 * the teacher configures the settings and then lets the students use the tool
 * (but they do not have access to the settings so the rerank function is not called)
 * @param {boolean} yield_reranked If true, reads the settings from the "settings.json" file, and yields the reranked results
 * @returns {Array} docs
 */
function search(yield_reranked) {

    the_query = document.getElementById("search_field").value.trim().split(" ").join("_");

    if (the_query === "") {
        alert("default query: Tuebingen");
        console.log("default query: Tuebingen");
        the_query = "Tuebingen";
        document.getElementById("search_field").value = the_query.split("_").join(" ");
    }

    log("search", document.getElementById("search_field").value.trim());

    docs = [];
    document.getElementById("results_table").innerHTML = "";
//    document.getElementById("display_query").innerHTML = query.split("_").join(" ");
    addDocs(the_query, yield_reranked); // false: not reranked

}



/**
 * Read .json files into the object "docs"
 * @param {String} the_query The string with "_" instead of white spaces
 * @param {boolean} reranked
 * @returns {undefined}
 */
function addDocs(the_query, reranked) {
    for (var i = 1; i < NUM_OF_RESULTS + 1; i++) {
        if (reranked) {
            var path = PATH_TO_RESULTS + the_query + '/weights/' + leftPad(i, 3) + '.json';
        } else {
            var path = PATH_TO_RESULTS + the_query + '/parsed/' + leftPad(i, 3) + '.json';
        }
        console.log(path);

        ($.ajax({
            url: path,
            dataType: "json",
            success: function (data) {
                docs.push(data);
            },
            error: function () {
                console.log('no doc file: ' + path);
            },
            complete: function () {
                displayEach(docs, reranked);
            }
        }));
    }
}

function leftPad(number, targetLength) {
    var output = number + '';
    while (output.length < targetLength) {
        output = '0' + output;
    }
    return output;
}

/**
 * Use html2canvas library to take a snapshot
 * @param {type} doc_number
 * @returns {undefined}
 */
function showSnapshot(doc_number) {
    hideSnapshot(); // remove the previous one

    doc_num = doc_number;

    // highlight the corresponding result
    document.getElementById("results_table").childNodes[doc_num].style.backgroundColor = "#fdf6e6";

    var html_string = docs[doc_num].html;
    var iframe = document.createElement('iframe');
    document.getElementById("snapshot").appendChild(iframe);
    setTimeout(function () {
        var iframedoc = iframe.contentDocument || iframe.contentWindow.document;
        iframedoc.body.innerHTML = html_string;
        html2canvas(iframedoc.body, {
            onrendered: function (canvas) {
                document.getElementById("snapshot").appendChild(canvas);
                document.getElementById("snapshot").removeChild(iframe);
            }
        });
    }, 10);
}



function showText(doc_number) {
    hideText(); // remove the previous one

    doc_num = doc_number;
    var doc = docs[doc_num];

    // highlight the corresponding result
    document.getElementById("results_table").childNodes[doc_num].style.backgroundColor = "#fdf6e6";


    var info_box = "<table><tr><td style='padding-left:10px;'>";
    info_box += "<p>Number of sentences: " + doc.numSents + " sentences</p>";
    info_box += "<p>Number of words: " + doc.numDeps + " words</p>";
    info_box += "<p>Average sentence length: " + Number(doc.avSentLength).toFixed(2) + " words</p>";
    info_box += "<p>Average word length: " + Number(doc.avWordLength).toFixed(2) + " letters</p>";
    info_box += "<p>Average tree depth: " + Number(doc.avTreeDepth).toFixed(2) + " levels</p>";

    info_box += "</td><td style='padding-left:20px;'>";

    info_box += "<p>Grammar weight: " + Number(doc.gramScore).toFixed(2) + "</p>";
    info_box += "<p>Rank weight: " + Number(doc.rankWeight).toFixed(2) + "</p>";
    info_box += "<p>Text length weight: " + Number(doc.textWeight).toFixed(2) + "</p>";
    info_box += "<p>Readability score: " + Number(doc.readabilityScore).toFixed(2) + "</p>";
    info_box += "<p>Total weight: " + Number(doc.totalWeight).toFixed(2) + "</p>";

    info_box += "</td></tr></table><hr>";

    info_box += "<p> <strong>Grammatical constructions </strong></p>";
    info_box += "<table><tbody>";

    for (var i in settings) {
        var name = settings[i]["name"];
        var all = doc["constructions"];
        for (var j in all) {
            if (all[j] === name && (settings[i]["weight"] > 0 || settings[i]["weight"] < 0)) {
                var ind = j;
                info_box += "<tr><td>" + name + ": </td><td style='padding-left:20px;'>" + doc["frequencies"][ind] + "</td></tr>";
            }
        }
    }
    info_box += "</tbody></table><hr>";

    var text_string = doc.text;
    document.getElementById("snapshot").innerHTML = info_box + text_string + "<br><br><br><br><br><br><hr>";
}

function hideText() {
    // clear the snapshot area
    document.getElementById("snapshot").innerHTML = "";
    // remove the highlight from the results
    if (doc_num > -1) {
        document.getElementById("results_table").childNodes[doc_num].style.backgroundColor = "white";
    }
    doc_num = -1;
}



function rerank() {
    from_show_distribution = false;

    the_query = document.getElementById("search_field").value.trim().split(" ").join("_");

    if (the_query === "") {
        console.log("Reranking: default query: Jennifer Lawrence");
        the_query = "Jennifer_Lawrence";
    }

    docs = [];
    hideSnapshot();
    document.getElementById("results_table").innerHTML = "";

    // save the settings
    settings = [];
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
    }

    // save the weights for constructions
    var g = document.getElementById("settings_panel").getElementsByTagName("input");
    for (var i = 0; i < g.length; i++) {
        if (g[i].value !== 0) {
            var setObj = {};
            setObj["name"] = g[i].id;
            setObj["weight"] = g[i].value;
            settings.push(setObj);
        }
    }

    // write into the settings file
    $.ajax({
        type: 'POST',
        url: settings_file,
        dataType: "json",
        data: settings,
        success: function () {
            console.log('successfully written into: ' + settings_file);
        },
        error: function () {
            console.log('no settings file found: ' + settings_file);
        }
    });

    addDocs(the_query, true); // true: reranked
}


function reset(what) {
    var g;
    if (what === "all") {
        g = document.getElementById("settings_panel").getElementsByTagName("input");
    } else {
        g = document.getElementById(what).getElementsByTagName("input");
    }
    for (var i = 0; i < g.length; i++) {
        g[i].value = 0;
    }
}

function setSearchEngine(searchEngine) {
    document.getElementById("search_engine").innerHTML = searchEngine + ' <span class="caret">';
    search_engine = searchEngine; // set the global variable
}



/**
 * Highlight the text given a set of constructions
 *
 * @param {Object} doc 
 * 
 * @return
 */
function highlightText(doc) {
    var newText = ""; // String

    var occs = []; // occurrences of THESE constructionS in this doc

    for (var i in settings) { // for each String
        var name = settings[i]["name"];

        for (var j in doc["highlights"]) { // for each Occurrence
            var o = doc["highlights"][j];
            if (o["construction"] === name && (settings[i]["weight"] < 0 || settings[i]["weight"] > 0)) {
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

        var spanStart = "<span style='background-color:lightyellow;' title='" + o["construction"] + "'>"; // String
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
            tmpStart = parseInt(curItem.substring(0, curItem.indexOf("-"))); // STRING!!! TODO: convert to INT?
            tmpEnd = parseInt(curItem.substring(curItem.indexOf("-") + 1)); // STRING!!! TODO: convert to INT?
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

/**
 * Resets the order of docs 
 * @returns {undefined}
 */
function show_distribution() {

    from_show_distribution = true;

    if (the_query === "") {
        alert("default query: Jennifer Lawrence");
        console.log("default query: Jennifer Lawrence");
        the_query = "Jennifer_Lawrence";
    }


    // save the settings
    settings = [];
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
    }

    // save the weights for constructions
    var g = document.getElementById("settings_panel").getElementsByTagName("input");
    for (var i = 0; i < g.length; i++) {
        if (g[i].value !== 0) {
            var setObj = {};
            setObj["name"] = g[i].id;
            setObj["weight"] = g[i].value;
            settings.push(setObj);
        }
    }


    docs = [];
    hideSnapshot();
    document.getElementById("results_table").innerHTML = "";
//    document.getElementById("display_query").innerHTML = query.split("_").join(" ");
    addDocs(the_query, true); // false: ranked (but from_show_distribution)
}

