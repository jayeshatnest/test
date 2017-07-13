package domainapp.modules.cmn.util;

public enum DistributionFrequency {

	DAILY("Daily"),
	WEEKLY("Weekly"),
	FORTNIGHTLY("Fortnightly"),
	MONTHLY("Monthly"),
	ANNUALLY("Annually");
	
	String frequency;
	
	DistributionFrequency() {
		
	}
	
	DistributionFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	public String getString() {
		return frequency;
	}
}
