package ma.tna.ebanking.userservice.model;

import com.fasterxml.jackson.annotation.JsonView;

//@JsonView(HistoryProjection.class)
public class ModifiedField {
	
	private String fieldName;
	private String oldValue;
	private String newValue;
	
	
	
	public ModifiedField() {
		super();
		
	}
	public ModifiedField(String fieldName, String oldValue, String newValue) {
		super();
		this.fieldName = fieldName;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
}
