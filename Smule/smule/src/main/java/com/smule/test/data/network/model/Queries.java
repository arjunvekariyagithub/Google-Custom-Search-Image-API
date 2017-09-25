
package com.smule.test.data.network.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Queries {

    @SerializedName("request")
    @Expose
    private List<Request> request = null;
    @SerializedName("nextPage")
    @Expose
    private List<NextPage> nextPage = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Queries() {
    }

    /**
     * 
     * @param nextPage
     * @param request
     */
    public Queries(List<Request> request, List<NextPage> nextPage) {
        super();
        this.request = request;
        this.nextPage = nextPage;
    }

    public List<Request> getRequest() {
        return request;
    }

    public void setRequest(List<Request> request) {
        this.request = request;
    }

    public List<NextPage> getNextPage() {
        return nextPage;
    }

    public void setNextPage(List<NextPage> nextPage) {
        this.nextPage = nextPage;
    }

}
