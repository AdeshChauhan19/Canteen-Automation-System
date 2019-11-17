
package com.example.canteenautomationsystem.Response.Food_Display;

import java.util.List;
import com.google.gson.annotations.Expose;

public class FoodDisplay {

    @Expose
    private List<Datum> data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
