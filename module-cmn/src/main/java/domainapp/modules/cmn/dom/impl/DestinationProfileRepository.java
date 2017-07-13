/**
 * 
 */
package domainapp.modules.cmn.dom.impl;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

/**
 * @author Jayesh
 *
 */
@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = DistributionProfile.class
)
public class DestinationProfileRepository extends AbstractActivableLookupByCodeRepository<DestinationProfile> {

    public DestinationProfileRepository() {
		super(DestinationProfile.class);
	}

	@Override
	protected DestinationProfile create() {
		return new DestinationProfile();
	}

	public DestinationProfile create(String code, String name,String email, String ftpUrl) {
		
		DestinationProfile destinationProfile = super.createNoPersist(code, name);
		destinationProfile.setUrl(ftpUrl);
		destinationProfile.setEmail(email);
		return create(destinationProfile);
	}
	
}
