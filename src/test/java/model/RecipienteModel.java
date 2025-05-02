package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class RecipienteModel {
    @Expose(serialize = false)
    private int recipienteId;
    @Expose
    private String tipo;
    @Expose
    private int capacidadeTotal;
    @Expose(serialize = false)
    private int capacidadeAtual;
    @Expose
    private String localizacao;
}