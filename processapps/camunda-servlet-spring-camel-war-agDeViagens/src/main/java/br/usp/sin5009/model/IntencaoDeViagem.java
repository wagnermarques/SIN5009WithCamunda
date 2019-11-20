package br.usp.sin5009.model;

import java.io.Serializable;
import java.util.*;

public class IntencaoDeViagem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private Date dataInicio;
    private Date dataFim;
    private Double vlrEstimadoDeGasto;
    private String locais;

    public void IntencaoDeViagem(
             Date dataInicio,
             Date dataFim,
             Double vlrEstimadoDeGasto, String locais){

        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.vlrEstimadoDeGasto = vlrEstimadoDeGasto;
        this.locais = locais;
    }
    

}
