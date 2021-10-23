package pt.ua.deti.ies.lab1.weatherFinal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IpmaCity {

    @SerializedName("data")
    @Expose
    private List<City> data = null;

    public List<City> getData() {
        return data;
    }

    public void setData(List<City> data) {
        this.data = data;
    }
}
