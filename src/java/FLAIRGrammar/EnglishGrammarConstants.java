/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRGrammar;

import java.util.Arrays;
import java.util.List;

/**
 * Stable constants for the English language grammar
 * @author shadeMe
 */
public class EnglishGrammarConstants
{
    public static List<String> questionWords = Arrays.asList("what", "who","how","why","where","when","whose","whom","which");
    
    public static List<String> simplePrepositions = Arrays.asList("in", "at", "on", "with", "after", "to");
    
    public static List<String> negation = Arrays.asList("neither", "nobody", "none", "nothing", "nor", "nowhere"); // "never" is counted along with "n't" and "not" in neg() dependency , "neither...nor" will be counted as 2 negations
    public static List<String> partialNegation = Arrays.asList("hardly", "scarcely", "rarely", "seldom", "barely");
    
    public static List<String> ingNouns = Arrays.asList("thing", "something", "anything", "nothing", "morning", "evening", "everything", "spring", "string", "swing", "darling", "ceiling", "clothing", "sterling", "earring", "viking", "fling"); // longer than 5 characters
    
    // around 40 advanced conjunctions
    public static List<String> advancedConjunctions = Arrays.asList("finally","nor","yet","though","although","if","while","unless","until","lest","whether","wheras","once","since","till","until","whenever","wherever","besides","further","furthermore","indeed","likewise","incidentally","moreover","however","nevertheless","nonetheless","still","conversely","instead","otherwise","accordingly","namely","consequently","hence","thus","meanwhile","therefore");
    public static List<String> simpleConjunctions = Arrays.asList("and", "or", "but", "because", "so");
    
    
    public static List<String> objectivePronouns = Arrays.asList("me", "you", "him", "her", "them", "us", "it");
    public static List<String> subjectivePronouns = Arrays.asList("I", "you", "he", "she", "they", "we", "it");
    public static List<String> possessivePronouns = Arrays.asList("my", "your", "his", "her", "their", "our", "its");
    public static List<String> possessiveAbsolutePronouns = Arrays.asList("mine", "yours", "hers", "theirs", "ours"); // his and its omitted
    public static List<String> reflexivePronouns = Arrays.asList("myself", "yourself", "himself", "herself", "themselves", "ourselves", "itself", "yourselves");
}
