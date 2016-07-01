/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */

//============= FLAIR - Root namespace ================//
var FLAIR = FLAIR || {};
 
// create a general purpose namespace method
// this will allow us to create namespace a bit easier
FLAIR.createNS = function (namespace) 
{
    var nsparts = namespace.split(".");
    var parent = FLAIR;
 
    // we want to be able to include or exclude the root namespace 
    // So we strip it if it's in the namespace
    if (nsparts[0] === "FLAIR")
        nsparts = nsparts.slice(1);
    // 
    // loop through the parts and create 
    // a nested namespace if necessary
    for (var i = 0; i < nsparts.length; i++)
    {
        var partname = nsparts[i];
        // check if the current parent already has 
        // the namespace declared, if not create it
        if (typeof parent[partname] === "undefined")
            parent[partname] = {};
	
        // get a reference to the deepest element 
        // in the hierarchy so far
        parent = parent[partname];
    }
    // the parent is now completely constructed 
    // with empty namespaces and can be used.
    return parent;
};

String.prototype.replaceAll = function(search, replacement) {
    var target = this;
    return target.replace(new RegExp(search, 'g'), replacement);
};