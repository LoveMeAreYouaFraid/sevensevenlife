package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;
import java.util.List;

public class VoteDetailItem implements Serializable {
    /**
     * id : 1
     * title : 关于小区内露天泳池要不要加装护栏的问题，请业主们投票
     * description : 加吧，影响美观；不加吧，有安全隐患；请投票决定
     * option_type : 1
     * end_time : 2017-06-30 16:06:55
     * status : 1
     * options : [{"id":1,"option_name":" 同意"}]
     * myChoice : []
     * results :
     */

    private int id;
    private String title;
    private String description;
    private int option_type;
    private String end_time;
    private int status;
    private List<resultsInfo> results;
    private List<OptionsItem> options;
    private List<OptionsItem> myChoice;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOption_type() {
        return option_type;
    }

    public void setOption_type(int option_type) {
        this.option_type = option_type;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<resultsInfo> getResults() {
        return results;
    }

    public void setResults(List<resultsInfo> results) {
        this.results = results;
    }

    public List<OptionsItem> getOptions() {
        return options;
    }

    public void setOptions(List<OptionsItem> options) {
        this.options = options;
    }

    public List<OptionsItem> getMyChoice() {
        return myChoice;
    }

    public void setMyChoice(List<OptionsItem> myChoice) {
        this.myChoice = myChoice;
    }


}
