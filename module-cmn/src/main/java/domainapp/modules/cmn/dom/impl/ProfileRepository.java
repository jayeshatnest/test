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
        repositoryFor = Profile.class
)
public class ProfileRepository extends AbstractActivableLookupByCodeRepository<Profile> {

    public ProfileRepository() {
		super(Profile.class);
	}

	@Override
	protected Profile create() {
		return new Profile();
	}
    
}
