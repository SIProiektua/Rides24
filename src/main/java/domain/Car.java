package domain;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.persistence.*;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Car {
	@Id
	private String numberPlate;
	private String insuranceId;

    public Car(String numberPlate, String insuranceId){
        this.numberPlate = numberPlate;
        this.insuranceId = insuranceId;
    }
	/**
	 * @return the numberPlate
	 */
	public String getNumberPlate() {
		return numberPlate;
	}
	/**
	 * @param numberPlate the numberPlate to set
	 */
	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}

	/**
	 * @return the insuranceId
	 */
	public String getInsuranceId() {
		return insuranceId;
	}

	/**
	 * @param insuranceId the insuranceId to set
	 */
	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}

	@Override
	public String toString() {
		return ("[" + "Number Plate: " +numberPlate + ", Insurance ID" + insuranceId + "]");
	}
}
