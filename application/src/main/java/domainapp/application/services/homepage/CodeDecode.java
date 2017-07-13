package domainapp.application.services.homepage;

import java.util.HashMap;
import java.util.Map;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class CodeDecode {
	
	private Map<Character, String> monthCodeMap;
	
	public Map<Character, String> getMonthCodeValue() {
		if (monthCodeMap == null) {
			monthCodeMap = new HashMap<Character, String>();
			setMonthCodeValue();
		}
		return monthCodeMap;
	}
	
	public void setMonthCodeValue() {
		monthCodeMap.put('F', "January");
		monthCodeMap.put('G', "February");
		monthCodeMap.put('H', "March");
		monthCodeMap.put('J', "April");
		monthCodeMap.put('K', "May");
		monthCodeMap.put('M', "June");
		monthCodeMap.put('N', "July");
		monthCodeMap.put('Q', "August");
		monthCodeMap.put('U', "September");
		monthCodeMap.put('V', "October");
		monthCodeMap.put('X', "November");
		monthCodeMap.put('Z', "December");
	}
	
}
