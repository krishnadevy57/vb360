package com.mind2web.vb360.modeles;

import com.google.gson.annotations.SerializedName;

   
public class Pagination {

   @SerializedName("current_page")
   int currentPage;

   @SerializedName("per_page")
   int perPage;

   @SerializedName("total_count")
   int totalCount;

   @SerializedName("total_pages")
   int totalPages;


    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    
    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }
    public int getPerPage() {
        return perPage;
    }
    
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    public int getTotalCount() {
        return totalCount;
    }
    
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    public int getTotalPages() {
        return totalPages;
    }
    
}