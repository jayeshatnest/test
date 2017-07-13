/**
 * 
 */
package domainapp.modules.cmn.dom.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;

import domainapp.modules.cmn.util.DistributionFrequency;
import domainapp.modules.cmn.util.DistributionType;
import domainapp.modules.cmn.util.ReportType;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = ReportingProfile.class
)
public class ReportingProfileRepository extends AbstractActivableLookupByCodeRepository<ReportingProfile> {

	@Inject
	DestinationProfileRepository destProfileRepository;
	
	@Inject
	TemplateRepository templateRepository;
	
    public ReportingProfileRepository() {
		super(ReportingProfile.class);
	}

    public List<ReportingProfile> findByUser(final String user) {
        return repositoryService.allMatches(
                new QueryDefault<ReportingProfile>(
                		ReportingProfile.class,
                        "findByUser",
                        "user", user));
    }
    
	@Override
	protected ReportingProfile create() {
		return new ReportingProfile();
	}

	public ReportingProfile create (String code,String name,String description,Profile profile,
			DistributionFrequency frequency,DistributionType distributionType,String email, 
			String url, Template reportTemplate, ReportType reportType, String user) {
		
		ReportingProfile reportProfile = super.createNoPersist(code, name);
		reportProfile.setDescription(description);
		reportProfile.setProfile(profile);
		reportProfile.setFrequency(frequency);
		reportProfile.setDistributionType(distributionType);

		reportProfile.setReportType(reportType);	// TODO: AJ - Need to find a better way to implement this
		
		String destProfileName = "Destination Profile";
		if(distributionType==DistributionType.MAIL) {
			destProfileName += " - Mail";
		} else if(distributionType==DistributionType.REMOTE) {
			destProfileName += " - FTP Server";
		}
		
		DestinationProfile destinationProfile = destProfileRepository.create(code+"_destination", destProfileName, email, url);
		reportProfile.setDestinationProfile(destinationProfile);
		reportProfile.setTemplate(reportTemplate);
		
		reportProfile.setUserCreation(user);
		return create(reportProfile);
		
	}
	
}
