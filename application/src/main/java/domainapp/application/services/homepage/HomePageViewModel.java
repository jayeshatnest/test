/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.application.services.homepage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.security.UserMemento;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.factory.FactoryService;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.user.UserService;
import org.apache.isis.applib.value.Blob;

import domainapp.modules.cmn.dom.impl.Document;
import domainapp.modules.cmn.dom.impl.DocumentRepository;
import domainapp.modules.cmn.dom.impl.Plugin;
import domainapp.modules.cmn.dom.impl.Profile;
import domainapp.modules.cmn.dom.impl.ProfileRepository;
import domainapp.modules.cmn.dom.impl.ReportingProfile;
import domainapp.modules.cmn.dom.impl.ReportingProfileRepository;
import domainapp.modules.cmn.dom.impl.Template;
import domainapp.modules.cmn.dom.impl.TemplateEngine;
import domainapp.modules.cmn.dom.impl.TemplateRepository;
import domainapp.modules.cmn.util.DistributionFrequency;
import domainapp.modules.cmn.util.DistributionType;
import domainapp.modules.cmn.util.ReportType;
import domainapp.modules.cmn.util.TemplateType;
import domainapp.modules.cmn.util.Util;
import lombok.extern.slf4j.Slf4j;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        objectType = "homepage.HomePageViewModel"
)
@Slf4j
public class HomePageViewModel {

	@Inject
	ServiceRegistry2 serviceRegistry2;
	
    @Inject
    TemplateRepository templateRepository;
	
    @Inject
    DocumentRepository documentRepository;
	/**
	 * @return
	 */
    @Inject
    FactoryService factoryService;
    
/*    @Inject
    DomainObjectContainer container;
*/
    @Inject
    UserService userService;
   // ServletContextService contextService;
    
	public TranslatableString title() {
        return TranslatableString.tr("{project}", "project", "Test");
    }
	
	@Inject
	ReportingProfileRepository reportingProfileRepository;
	
	@Inject
	ProfileRepository profileRepository;
	
	public List<ReportingProfile> getReportingProfiles() {
		
		List<ReportingProfile> responseList = new ArrayList<>();
		UserMemento user = userService.getUser();
		
		if(Util.isAdmin(user)) {
			responseList = reportingProfileRepository.listAll();
		} else {
			responseList = reportingProfileRepository.findByUser(user.getName());
		}
		
		return responseList;
	}

	@SuppressWarnings("serial")
	public static class CreateReportingProfileDomainEvent extends ActionDomainEvent<ReportingProfile> {}

	@Action(
            domainEvent = CreateReportingProfileDomainEvent.class
    )
	@ActionLayout(named="Create Reporting Profile")
	public HomePageViewModel createReportingProfile(
    		@ParameterLayout(named="Name")
    		String name, 

    		@Parameter(optionality=Optionality.OPTIONAL)
    		@ParameterLayout(named="Description")
    		String description, 
			
    		@ParameterLayout(named="Profile")
    		Profile profile, 

    		@ParameterLayout(named="Frequency")
			DistributionFrequency frequency,

    		@ParameterLayout(named="Distribution Type")
			DistributionType distributionType, 

			@Parameter(regexPattern="^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$",optionality=Optionality.OPTIONAL)
    		@ParameterLayout(named="E-mail")
    		String email,

			@Parameter(regexPattern="^(sftp|ftp|ftps)://", optionality=Optionality.OPTIONAL)
    		@ParameterLayout(named="FTP URL")
    		String url,
    		
    		@ParameterLayout(named="Report Template")
    		Template reportTemplate,

    		ReportType reportType
		) {
		UserMemento user = userService.getUser();
		reportingProfileRepository.create(user.getName()+"_"+name,name,description,profile,frequency,distributionType,email, url, reportTemplate, reportType, user.getName());
		
		return factoryService.instantiate(HomePageViewModel.class);
    }
	
	public List<Template> choices7CreateReportingProfile(String name, String description, 
			Profile profile, DistributionFrequency frequency, DistributionType distributionType, 
    		String email, String url){
		
		List<Template> responseList = new ArrayList<>();
		if(distributionType==null) {
			return responseList;
		}
		UserMemento user = userService.getUser();
		List<Template> reportTemplateList = null;
		if(Util.isAdmin(user)) {
			reportTemplateList = templateRepository.listAll();
		} else {
			reportTemplateList = templateRepository.findByUser(user.getName());
		}
		
		for(Template template : reportTemplateList) {
			if(distributionType==DistributionType.MAIL) {
				if(template.getTemplateType() == TemplateType.MAIL) {
					responseList.add(template);
				}
			} else {
				if(template.getTemplateType()!= TemplateType.MAIL) {
					responseList.add(template);
				}
			}
		}
		
		return responseList;
	}
	
	public String validateCreateReportingProfile(String name, String description, 
			Profile profile, DistributionFrequency frequency, DistributionType distributionType, 
    		String email,String url,Template reportTemplate,ReportType reportType){
		String response = null;
		if(distributionType==DistributionType.MAIL && email==null) {
			response="If Distribution Type is 'Mail' then Email needs to be specified !";
		} else if(distributionType==DistributionType.REMOTE && url==null) {
			response="If Distribution Type is 'Remote' then FTP URL needs to be specified !";
		}
		
		return response;
	}
	
	@SuppressWarnings("serial")
	public static class CreateReportDocumentDomainEvent extends ActionDomainEvent<Document> {}

	@Action(
            domainEvent = CreateReportDocumentDomainEvent.class
    )
	@ActionLayout(named="Setup Report Document")
	public HomePageViewModel createReportDocument(
				@ParameterLayout(named="Report Document Name")
				String name,
				@ParameterLayout(named="Report Document Filename")
				String filename,
				@ParameterLayout(named="Report Template")
				Template template) {

		UserMemento user = userService.getUser();
		documentRepository.create(name+"_code", name, filename, template, user.getName());
		return factoryService.instantiate(HomePageViewModel.class);
	}
	
	public List<Template> choices2CreateReportDocument() {
		UserMemento user = userService.getUser();
		if(Util.isAdmin(user)) {
			return templateRepository.listAll();
		} else {
			return templateRepository.findByUser(user.getName());
		}
	}
	
	@SuppressWarnings("serial")
	public static class CreateReportTemplateDomainEvent extends ActionDomainEvent<Template> {}

	@Action(
            domainEvent = CreateReportTemplateDomainEvent.class
    )
	@ActionLayout(named="Create Report Template")
	public HomePageViewModel createReportTemplate(
			@ParameterLayout(named="Report Template Name")
			String templateName,
			@ParameterLayout(named="Report Template Type")
			TemplateType templateType,	
			@ParameterLayout(named="Report Template Engine")
			TemplateEngine engine,
			@ParameterLayout(named="Report Template File")
			Blob templateFile,
			@Parameter(optionality=Optionality.OPTIONAL)
			@ParameterLayout(named="Email Subject")
			String subject,
			@Parameter(optionality=Optionality.OPTIONAL)
			@ParameterLayout(named="Template Engine Plugin")
			Plugin plugin){
			
			UserMemento user = userService.getUser();
		
			templateRepository.create(templateName+"_code", templateName, subject, 
					templateFile.getName(), plugin, templateType, engine.getCode(), user.getName());
			
			return factoryService.instantiate(HomePageViewModel.class);
	}
	
	public String validateCreateReportTemplate(
			String templateName,
			TemplateType templateType,	
			TemplateEngine engine,
			Blob templateFile,
			String subject,
			Plugin plugin){

		String response = null;
			
		if(templateType==TemplateType.MAIL && subject==null) {
			response="If Report Template Type is 'Mail' then Email Subject needs to be specified !";
		} else if (("Plugin").equalsIgnoreCase(engine.getName()) && plugin==null) {
			response="If Report Template Engine is 'Plugin' then plugin needs to be selected !";
		}
		
		return response;
	}

	@Action(semantics=SemanticsOf.IDEMPOTENT)
    public HomePageViewModel testMethod( List<ReportingProfile> rpList) {
          return factoryService.instantiate(HomePageViewModel.class);
    }

	public List<ReportingProfile> choices0TestMethod() {
        List<ReportingProfile>  l = new ArrayList<>();
        l.addAll(reportingProfileRepository.listAll());
        
        return l;
    }
}
