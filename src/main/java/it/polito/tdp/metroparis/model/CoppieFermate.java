package it.polito.tdp.metroparis.model;

import java.util.Objects;

public class CoppieFermate {
	
	private Fermata partenza;
	private Fermata arrivo;
		
	public CoppieFermate(Fermata partenza, Fermata arrivo) {
		this.partenza = partenza;
		this.arrivo = arrivo;
	}
	
	
	
	public Fermata getPartenza() {
		return partenza;
	}
	public Fermata getArrivo() {
		return arrivo;
	}
	public void setPartenza(Fermata partenza) {
		this.partenza = partenza;
	}
	public void setArrivo(Fermata arrivo) {
		this.arrivo = arrivo;
	}
	@Override
	public int hashCode() {
		return Objects.hash(arrivo, partenza);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoppieFermate other = (CoppieFermate) obj;
		return Objects.equals(arrivo, other.arrivo) && Objects.equals(partenza, other.partenza);
	}
	
	

}
