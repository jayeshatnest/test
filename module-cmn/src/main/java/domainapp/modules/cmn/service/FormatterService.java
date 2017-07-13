package domainapp.modules.cmn.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.joda.time.DateTime;


@DomainService(
        nature = NatureOfService.DOMAIN
)
@lombok.extern.slf4j.Slf4j
public class FormatterService {
	
	public boolean isNull(String str) {
		if (str == null) {
			return true;
		}
		if (str.contains("N/A")) {
			return true;
		}
		if ("".equals(str)) {
			return true;
		}
		if ("//".equals(str)) {
			log.info("INSIDE THIS == "+str);
			return true;
		}
		return false;
	}
	
	public String makeNumber(String str) {
		return str.replaceAll("[^\\d.]", "");
	}
	
	public Long toLong(String lot) {
		lot = lot.trim();
		try {
			if (!isNull(lot)) {
				lot = makeNumber(lot);
				Number boardLot = NumberFormat.getNumberInstance(Locale.US).parse(lot);
				return boardLot.longValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Date toDate(String dateStr) {
		dateStr = dateStr.trim();
		try {
			if (isNull(dateStr)) {
				return null;
			} else {
				SimpleDateFormat formatter = new SimpleDateFormat();
				formatter.applyPattern("dd/MM/yyyy");
				return formatter.parse(dateStr);
			}
		} catch (ParseException e) {
			
		}
		return null;
	}
	
	public String toDateString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(date);
	}
	
	public DateTime toDateTime(String dateStr) {
		Date date = toDate(dateStr);
		return new DateTime(date);
	}
	
	public BigDecimal toBigDecimal(String oop) {
		oop = oop.trim();
		if (isNull(oop)) {
			return new BigDecimal(0);
		} else {
			oop = makeNumber(oop);
			return new BigDecimal(oop);
		}
	}
	
	public BigInteger toBigInteger(String units) {
		units = units.trim();
		try {
			if (!isNull(units)) {
				units = makeNumber(units);
				Number outstandingUnits= NumberFormat.getNumberInstance(Locale.US).parse(units);
				return new BigInteger(outstandingUnits.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Double toDouble(String string) {
		string = string.trim();
		try {
			if (!isNull(string)) {
				string = makeNumber(string);
				Number boardLot = NumberFormat.getNumberInstance(Locale.US).parse(string);
				return boardLot.doubleValue();
			}
		} catch (Exception e) {
			
		}
		return null;
	}
	
	public String replace(String expr) {
		expr = expr.replace(":", "");
		expr = expr.replace("#", "");
		return expr.trim();
	}
}
