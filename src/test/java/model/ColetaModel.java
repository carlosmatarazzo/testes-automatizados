package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class ColetaModel {
    @Expose(serialize = false)
    private int coletaId;
    @Expose
    private String recipienteId;
    @Expose
    private String data;
}