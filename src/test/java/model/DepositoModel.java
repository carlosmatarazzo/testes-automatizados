package model;
import com.google.gson.annotations.Expose;
import lombok.Data;

import java.util.Date;

@Data
public class DepositoModel {
    @Expose(serialize = false)
    private int depositoId;
    @Expose
    private String recipienteId;
    @Expose
    private int peso;
    @Expose
    private String data;
}