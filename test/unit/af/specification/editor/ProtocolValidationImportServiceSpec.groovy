package af.specification.editor

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ProtocolValidationImportService)
@Mock([MessageType,MulticastGroup, FieldType, DatumType])
class ProtocolValidationImportServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "it should import all multicast groups"() {
        given:
        "an xml val spec"
        def xml = """
<validations>
    <multicast_groups>
        <multicast_group id="34" name="SYSTEM_HEARTBEAT" address="233.52.220.1" port="25001"/>
    </multicast_groups>
</validations>
"""
        when:
        "the specification is imported"
        service.importProtocolValidationSpecification(xml);
        
        then:
        "a multicast group should be created"
        MulticastGroup.all.size() == 1
        
        and:
        "it should have the correct fields"
        MulticastGroup.get(1).name == "SYSTEM_HEARTBEAT"
        MulticastGroup.get(1).multicastGroupId == 34
    }

    void "it should throw an exception if multicast group is missing an attribute"() {
        given:
        "an malformed xml val spec"
        def xml = """
<validations>
    <multicast_groups>
        <multicast_group id="34"  address="233.52.220.1" port="25001"/>
    </multicast_groups>
</validations>
"""
        when:
        "the specification is imported"
        service.importProtocolValidationSpecification(xml);

        then:
        "an exception should be thrown"
       thrown(Exception)

        and:
        "no multicast group should be created"
        MulticastGroup.all.size() == 0

    }
    
    void "it should import the message types"(){
        given:
        "an xml spec"
        def xml = """
<validations>
    <message_types>
        <message_type name="release" value="0"/>
    </message_types>
</validations>
"""
        when:
        "the spec is imported"
        service.importProtocolValidationSpecification(xml);
        
        then:
        "a message type should be created"
        MessageType.all.size() == 1
        
        and:
        "it should have the correct fields"
        MessageType.get(1).name == "release"
        MessageType.get(1).description == "release"
        MessageType.get(1).value == 0
        
    }
    
    void "it should import field types"(){
        given:
        "an xml spec"
        def xml = """
<validations>
    <field_types>
        <field_type name="float" id="0" value="0" length="4"/>
    </field_types>
</validations>
"""
        when:
        "the spec is imported"
        service.importProtocolValidationSpecification(xml)
        
        then:
        "a field type should be created"
        FieldType.all.size() == 1

        and:
        "all fields should be populated"
        FieldType.get(1).name == "float"
        FieldType.get(1).description == "float"
        FieldType.get(1).fieldTypeId == 0
        FieldType.get(1).length == 4
        
        
    }

    void "it should throw exception for malformed field type"(){
        given:
        "an xml spec"
        def xml = """
<validations>
    <field_types>
        <field_type  id="0" value="0" length="4"/>
    </field_types>
</validations>
"""
        when:
        "the spec is imported"
        service.importProtocolValidationSpecification(xml)

        then:
        "an exception should be thrown"
        thrown(Exception)


    }

    void "it should import datum types"(){
        given:
        "an xml spec"
        def xml = """
<validations>
    <data_types>
        <data_type id="1" name="CAD" display="CAD " description="Canadian Dollar"/>
    </data_types>
</validations>
"""
        when:
        "the spec is imported"
        service.importProtocolValidationSpecification(xml)

        then:
        "a datum type should be created"
        DatumType.all.size() == 1

        and:
        "all fields should be populated"
        DatumType.get(1).name == "CAD"
        DatumType.get(1).description == "Canadian Dollar"
        DatumType.get(1).display == "CAD "


    }

    void "it should throw exception for malformed datum type"(){
        given:
        "an xml spec"
        def xml = """
<validations>
     <data_types>
        <data_type id="1"  display="CAD " description="Canadian Dollar"/>
    </data_types>
</validations>
"""
        when:
        "the spec is imported"
        service.importProtocolValidationSpecification(xml)

        then:
        "an exception should be thrown"
        thrown(Exception)


    }

    void "it should make sure that datum type ids match up"(){
        given:
        "an xml spec"
        def xml = """
<validations>
     <data_types>
        <data_type id="4" name="CAD" display="CAD " description="Canadian Dollar"/>
    </data_types>
</validations>
"""
        when:
        "the spec is imported"
        service.importProtocolValidationSpecification(xml)

        then:
        "a datum type should be created"
        DatumType.all.size() == 1

        and:
        "it should have the right id"
        DatumType.get(4).name == "CAD"
        DatumType.get(4).description == "Canadian Dollar"
        DatumType.get(4).display == "CAD "


    }
}
