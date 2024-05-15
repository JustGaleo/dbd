package com.justgaleo.dbd.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "partida")
public class Partida {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String idMapa;

	private Long idRonda;

	private String status;

	private boolean castigo;

	private String idCastigo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdMapa() {
		return idMapa;
	}

	public void setIdMapa(String idMapa) {
		this.idMapa = idMapa;
	}

	public Long getIdRonda() {
		return idRonda;
	}

	public void setIdRonda(Long idRonda) {
		this.idRonda = idRonda;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isCastigo() {
		return castigo;
	}

	public void setCastigo(boolean castigo) {
		this.castigo = castigo;
	}

	public String getIdCastigo() {
		return idCastigo;
	}

	public void setIdCastigo(String idCastigo) {
		this.idCastigo = idCastigo;
	}

}
