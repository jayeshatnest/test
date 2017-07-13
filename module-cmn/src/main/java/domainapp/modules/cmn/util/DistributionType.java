package domainapp.modules.cmn.util;

public enum DistributionType {

	MAIL("Mail"),
	REMOTE("Remote"),
	DOWNLOAD("Download");
	
	String type;
	
	DistributionType() {
		
	}
	
	DistributionType(String type) {
		this.type = type;
	}
	
	public String getString() {
		return this.type;
	}
}
