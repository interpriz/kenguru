package com.kenguru.demowebapp.dto;

import java.util.List;

public class SearchingObjects {

    private List<UsersWord> searchUserWords;
    private List<UsersPhrasalVerb> searchUserPhrasalVerbs;

    public SearchingObjects(List<UsersWord> searchUserWords, List<UsersPhrasalVerb> searchUserPhrasalVerbs) {
        this.searchUserWords = searchUserWords;
        this.searchUserPhrasalVerbs = searchUserPhrasalVerbs;
    }

    public List<UsersWord> getSearchUserWords() {
        return searchUserWords;
    }

    public void setSearchUserWords(List<UsersWord> searchUserWords) {
        this.searchUserWords = searchUserWords;
    }

    public List<UsersPhrasalVerb> getSearchUserPhrasalVerbs() {
        return searchUserPhrasalVerbs;
    }

    public void setSearchUserPhrasalVerbs(List<UsersPhrasalVerb> searchUserPhrasalVerbs) {
        this.searchUserPhrasalVerbs = searchUserPhrasalVerbs;
    }
}
