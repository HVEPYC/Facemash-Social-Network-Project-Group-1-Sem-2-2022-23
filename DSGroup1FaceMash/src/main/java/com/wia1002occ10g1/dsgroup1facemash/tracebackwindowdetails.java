package com.wia1002occ10g1.dsgroup1facemash;

/**
 *
 * @author Harishankar Vinod
 */

//A method made to store window info, and person whose info was being viewed, for the Traceback
public class tracebackwindowdetails {
    
    private int window_id;
    private Person person_which_was_being_viewed;
    private String searchQuery;
    
    //To initialize window with no person info
    public tracebackwindowdetails(int window_id) {
        this.window_id = window_id;
    }
    
    //To initialize window with some person info as well!
    public tracebackwindowdetails(int window_id, Person person_being_viewed) {
        this.window_id = window_id;
        this.person_which_was_being_viewed = person_being_viewed;
    }
    
    //To initialize window with search query info (For Search Results)
    public tracebackwindowdetails(int window_id, String searchquery) {
        this.window_id = window_id;
        this.searchQuery = searchquery;
    }
    
    //Methods to retrieve Data for each Object
    public int getWindowID() {
        return this.window_id;
    }
    
    public Person getPersonBeingViewed() {
        return this.person_which_was_being_viewed;
    }
    
    public String getSearchQuery() {
        return this.searchQuery;
    }
    
    //Traceback Window Generators for Each Window
    public static tracebackwindowdetails TraceBackWindowGen(int window_id) {
        return new tracebackwindowdetails(window_id);
    }
    
    public static tracebackwindowdetails TraceBackWindowGen(int window_id, Person person_being_viewed) {
        return new tracebackwindowdetails(window_id, person_being_viewed);
    }
    
    public static tracebackwindowdetails TraceBackWindowGen(int window_id, String SearchQuery) {
        return new tracebackwindowdetails(window_id, SearchQuery);
    }
}
