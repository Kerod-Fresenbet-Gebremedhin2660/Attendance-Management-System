/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityModels;

import java.util.ArrayList;

/**
 *
 * @author Henok Gelaneh
 */
public class Request {
    private int id;
    private String name;
    private String reason;
    private int daysrequested;
    public Request(int id, String name, String reason, int daysrequested){
        this.id = id;
        this.name = name;
        this.reason = reason;
        this.daysrequested = daysrequested;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getDaysrequested() {
        return daysrequested;
    }

    public void setDaysrequested(int daysrequested) {
        this.daysrequested = daysrequested;
    }
    
     
}
