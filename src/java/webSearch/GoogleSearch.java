///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package webSearch;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Scanner;
//
///**
// *
// * @author Maria
// */
//public class GoogleSearch {
//
//    private String address;
//    private String query;
//    private String charset;
//    private String start;
//    private Site site_ted;
//    private Site site_bigthink;
//    private Site site_all;
//    
//    public static String QUERY_PREFIX = "allintext: ";
//
//    public GoogleSearch() {
//        address = "";
//        query = "";
//        charset = "";
//        start = "";
//        site_ted = null;
//        site_bigthink = null;
//        site_all = null;
//    }
//
//    public GoogleSearch(String address, String query, String charset, String start) {
//        this();
//        this.query = query;
//        this.charset = charset;
//        this.start = start;
//        System.out.println("Start: " + this.start);
//        //this.address = address;
//        this.address = address.substring(0, address.indexOf("start=")) + "start=" + this.start + address.substring(address.indexOf("&q="));
//        //System.out.println("Address: " + this.address);
//
//        //this.site_ted = new Site("site:ted.com/talks", this.address, this.query, this.charset, this.start);
//        //this.site_bigthink = new Site("site:bigthink.com/videos", this.address, this.query, this.charset, this.start);
//        this.site_all = new Site("", this.address, this.query, this.charset, this.start);
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getQuery() {
//        return query;
//    }
//
//    public void setQuery(String query) {
//        this.query = query;
//    }
//
//    public String getCharset() {
//        return charset;
//    }
//
//    public void setCharset(String charset) {
//        this.charset = charset;
//    }
//
//    public String getStart() {
//        return start;
//    }
//
//    public void setStart(String start) {
//        this.start = start;
//    }
//
//    public Site getSite_ted() {
//        return site_ted;
//    }
//
//    public void setSite_ted(Site site_ted) {
//        this.site_ted = site_ted;
//    }
//
//    public Site getSite_bigthink() {
//        return site_bigthink;
//    }
//
//    public void setSite_bigthink(Site site_bigthink) {
//        this.site_bigthink = site_bigthink;
//    }
//
//    public Site getSite_all() {
//        return site_all;
//    }
//
//    public void setSite_all(Site site_all) {
//        this.site_all = site_all;
//    }
//
//
//    
//    
//    
//    // main
//    public static void main(String[] args) throws IOException {
//
//        // 1. Create a Scanner using the InputStream available.
//        Scanner scanner = new Scanner(System.in);
//
//        // 2. Don't forget to prompt the user
//        System.out.print("Type in the query: ");
//        // 3. Use the Scanner to read a line of text from the user.
//        String query = QUERY_PREFIX + scanner.nextLine().trim();
//        
//        System.out.print("Start from: ");
//        String start = scanner.nextLine().trim();
//
//        String address = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&lr=lang_en&start=0&q=";
//        //String start = "0";
//
//        //String query = "travel";
//        String charset = "UTF-8";
//
//        GoogleSearch search = new GoogleSearch(address, query, charset, start);
//        
//        
//        // TODO only grab the main content of the page!!!
//
//    }
//
//}
//
//class GoogleResults {
//
//    private ResponseData responseData;
//
//    public ResponseData getResponseData() {
//        return responseData;
//    }
//
//    public void setResponseData(ResponseData responseData) {
//        this.responseData = responseData;
//    }
//
//    public String toString() {
//        return "ResponseData[" + responseData + "]";
//    }
//
//    static class ResponseData {
//
//        private List<Result> results;
//
//        public List<Result> getResults() {
//            return results;
//        }
//
//        public void setResults(List<Result> results) {
//            this.results = results;
//        }
//
//        public String toString() {
//            return "Results[" + results + "]";
//        }
//    }
//
//    static class Result {
//
//        private String url;
//        private String title;
//
//        public String getUrl() {
//            return url;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setUrl(String url) {
//            this.url = url;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public String toString() {
//            return "Result[url:" + url + ",title:" + title + "]";
//        }
//    }
//}
