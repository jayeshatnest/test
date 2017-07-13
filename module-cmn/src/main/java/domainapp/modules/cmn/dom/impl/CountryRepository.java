package domainapp.modules.cmn.dom.impl;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;


@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Country.class
)
public class CountryRepository extends AbstractLookupByCodeRepository<Country> {

	public CountryRepository() {
		super(Country.class);
	}
	
	public Country create(Country countryTemplate ) {
		final Country country = super.create(countryTemplate.getCode(), countryTemplate.getName());
		return country;
	}

}
