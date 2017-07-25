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

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.registry.ServiceRegistry2;

import domainapp.modules.cmn.dom.impl.Country;
import domainapp.modules.cmn.dom.impl.CountryRepository;
import domainapp.modules.cmn.dom.impl.Profile;
import domainapp.modules.cmn.dom.impl.ProfileRepository;
import lombok.extern.slf4j.Slf4j;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        objectType = "homepage.HomePageViewModel"
)
@Slf4j
public class HomePageViewModel {

	@Inject
	ServiceRegistry2 serviceRegistry2;
    
	public TranslatableString title() {
        return TranslatableString.tr("{project}", "project", "Test");
    }
	
	public List<Profile> getProfiles() {
		return profileRepository.listAll();
	}
	
	public List<Country> getCountries() {
		return counryRepository.listAll();
	}
	
	@Inject
	ProfileRepository profileRepository;
	
	@Inject
	CountryRepository counryRepository;
}
