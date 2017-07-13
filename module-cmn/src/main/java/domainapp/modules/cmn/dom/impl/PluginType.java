/**
 * 
 */
package domainapp.modules.cmn.dom.impl;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Title;

import domainapp.modules.cmn.dom.api.ILookupEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jayesh
 *
 */
@javax.jdo.annotations.PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        table = "CMN_PLUGIN_TYPE_DEF"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.INCREMENT,
        column="id")
@javax.jdo.annotations.Inheritance(
		strategy = InheritanceStrategy.NEW_TABLE
		)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByCode",
                value = "SELECT "
                        + "FROM domainapp.modules.cmn.dom.impl.PluginType "
                        + "WHERE code.indexOf(:code) >= 0 ")
})
@javax.jdo.annotations.Unique(name="PluginType_code_UNQ", members = {"code"})
@DomainObject(
        objectType = "cmn.PluginType",
        auditing = Auditing.DISABLED,
        editing = Editing.DISABLED,
        bounded = true
)
public class PluginType implements ILookupEntity {

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private String code;

    @javax.jdo.annotations.Column(allowsNull = "false", length = 100)
    @Property(editing = Editing.ENABLED)
    @Getter @Setter
    @Title(prepend = "")
    private String name;

}
