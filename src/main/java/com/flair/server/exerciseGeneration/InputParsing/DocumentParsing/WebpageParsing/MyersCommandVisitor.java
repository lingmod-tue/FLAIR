package com.flair.server.exerciseGeneration.InputParsing.DocumentParsing.WebpageParsing;

import org.apache.commons.text.diff.CommandVisitor;

import java.util.ArrayList;

public class MyersCommandVisitor implements CommandVisitor<Character> {

    private final String flairPlainText;
    private int htmlIndex = 0;
    private int flairIndex = 0;
    private boolean inFragment = false;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    public MyersCommandVisitor(String flairPlainText) {
        this.flairPlainText = flairPlainText;
    }

    public ArrayList<Fragment> getFragments() { return fragments; }

    @Override
    public void visitInsertCommand(Character object) {
        flairIndex++;
        inFragment = false;
    }

    @Override
    public void visitKeepCommand(Character object) {
        if(!inFragment) {
            fragments.add(new Fragment(flairPlainText.charAt(flairIndex) + "", htmlIndex, flairIndex));
        } else {
            fragments.get(fragments.size() - 1).setText(fragments.get(fragments.size() - 1).getText() + flairPlainText.charAt(flairIndex));
        }
        fragments.get(fragments.size() - 1).setEndIndex(htmlIndex + 1);

        inFragment = true;
        htmlIndex++;
        flairIndex++;
    }

    @Override
    public void visitDeleteCommand(Character object) {
        htmlIndex++;
        inFragment = false;
    }

}
