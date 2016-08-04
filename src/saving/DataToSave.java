package saving;

import java.io.Serializable;

/**
 * Created by Nishanth on 27/07/2016.
 */
public class DataToSave implements Serializable {

	private String aa;
	private String bb;
	private double value;

	public DataToSave () {
	}

	public void setAa(String aa) {
		this.aa = aa;
	}

	public void setBb(String bb) {
		this.bb = bb;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public String getAa() {
		return aa;
	}

	public String getBb() {
		return bb;
	}
}
