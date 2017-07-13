package domainapp.modules.cmn.util;

import java.util.List;

import org.apache.isis.applib.security.RoleMemento;
import org.apache.isis.applib.security.UserMemento;

public class Util {

	public static boolean isAdmin(UserMemento user) {
		
		boolean response = false;
		List<RoleMemento> roles = user.getRoles();
		
		for(RoleMemento role : roles) {
			if(role.getName().contains("admin_role")) {
				response = true;
				break;
			}
		}
		return response;
	}
}
