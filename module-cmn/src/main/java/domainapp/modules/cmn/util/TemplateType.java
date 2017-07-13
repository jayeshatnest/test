package domainapp.modules.cmn.util;

public enum TemplateType {

	MAIL("Mail"),
	DOCUMENT("Document");
	
	private String type;
	
	TemplateType() {
		
	}
	
	TemplateType(String type) {
		this.type = type;
	}
	
	public String getString() {
		return this.type;
	}
}
