package pt.ua.deti.ies.lab1.weatherFinal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("local")
    @Expose
    private String local;

    @SerializedName("globalIdLocal")
    @Expose
    private Integer globalIdLocal;

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Integer getGlobalIdLocal() {
        return globalIdLocal;
    }

    public void setGlobalIdLocal(Integer globalIdLocal) {
        this.globalIdLocal = globalIdLocal;
    }
    
}
