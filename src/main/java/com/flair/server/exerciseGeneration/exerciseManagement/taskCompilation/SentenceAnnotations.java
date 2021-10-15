package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;

import java.util.Collection;
import java.util.List;

public class SentenceAnnotations {
    private CoreMap sentence;
    private Tree constituentTree;
    private List<CoreLabel> tokens;
    private Collection<TypedDependency> dependencyGraph;

    public SentenceAnnotations(CoreMap sentence, Tree constituentTree, List<CoreLabel> tokens,
                               Collection<TypedDependency> dependencyGraph) {
        this.sentence = sentence;
        this.constituentTree = constituentTree;
        this.tokens = tokens;
        this.dependencyGraph = dependencyGraph;
    }

    public CoreMap getSentence() {
        return sentence;
    }

    public Tree getConstituentTree() {
        return constituentTree;
    }

    public List<CoreLabel> getTokens() {
        return tokens;
    }

    public Collection<TypedDependency> getDependencyGraph() {
        return dependencyGraph;
    }

}
