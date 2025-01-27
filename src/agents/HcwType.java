package agents;

public enum HcwType {
    NURSE("NURSE"), DOCTOR("DOCTOR"), PT("PT"), RT("RT"), OT("OT");
	
	private String value;
	
	private HcwType(String v) {
		this.value = v;
	}
	
	public String getValue() {
		return value;
	}
}
