package domainapp.modules.cmn.util;

public enum ReportType {

	REPORTING_LEVEL_POSITION_LIMIT_BREACH_REPORT("Reporting Level Position Limit Breach Report"),
	ACTUAL_LEVEL_POSITION_LIMIT_BREACH_REPORT("Actual Level Position Limit Breach Report");
	
	String type;
	
	ReportType() {}
	
	ReportType(String type) {
		this.type = type;
	}
	
	public String getString() {
		return this.type;
	}
}
