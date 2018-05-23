package com.omar.qantastest.Common.network.domain.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by omz on 23/5/18
 */
public class RecipeResponse implements Serializable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("version")
    @Expose
    private float version;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("results")
    @Expose
    private List<Recipe> results = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public RecipeResponse() {
    }

    /**
     *
     * @param title
     * @param results
     * @param href
     * @param version
     */
    public RecipeResponse(String title, float version, String href, List<Recipe> results) {
        super();
        this.title = title;
        this.version = version;
        this.href = href;
        this.results = results;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getVersion() {
        return version;
    }

    public void setVersion(float version) {
        this.version = version;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<Recipe> getResults() {
        return results;
    }

    public void setResults(List<Recipe> results) {
        this.results = results;
    }

}