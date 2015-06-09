package af.specification.editor

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(MulticastGroupsImportService)
@Mock([MulticastGrouping,MulticastGroup,MulticastEnvironmentType,MulticastEnvironment,MulticastSource])
class MulticastGroupsImportServiceSpec extends Specification {

    /*
        given:
        "some xml"
        def xml = """
<multicast_groups_config>
    <multicast_groups>
        <multicast_grouping id="1" name="System" desc="">
            <multicast_group id="1" name="SYSTEM_HEARTBEAT" desc="">
                <multicast_environments>
                    <multicast_environment id="1" is_beta="false" type="production" address="233.52.220.1" port="25001">
                        <sources>
                            <source>199.30.153.84</source>
                        </sources>
                    </multicast_environment>
                    <multicast_environment id="2" is_beta="false" type="private_test" address="239.250.250.1" port="29001">
                        <sources></sources>
                    </multicast_environment>
                </multicast_environments>
            </multicast_group>
        </multicast_grouping>
    </multicast_groups>
</multicast_groups_config>
"""
     */
    def setup() {
    }

    def cleanup() {
    }

    void "it should import multicast groupings"() {
        given:
        "some xml"
        def xml = """
<multicast_groups_config>
    <multicast_groups>
        <multicast_grouping id="4" name="System" desc="">
        </multicast_grouping>
    </multicast_groups>
</multicast_groups_config>
"""
        when:
        "the xml is imported"
        service.importMulticastGroupsSpecification(xml);
        
        then:
        "there should be a multicast grouping"
        MulticastGrouping.all.size() == 1
        
        and:
        "it should have the correct fields"
        MulticastGrouping.get(4).name == "System"
        MulticastGrouping.get(4).description == ""
        
    }
    
    void "it should import multicast group environments and sources"(){
        given:
        "some xml"
        def xml = """
<multicast_groups_config>
    <multicast_groups>
        <multicast_grouping id="4" name="System" desc="arrrg">
            <multicast_group id="6" name="SYSTEM_HEARTBEAT" desc="bargle">
                <multicast_environments>
                    <multicast_environment id="14" is_beta="true" type="production" address="233.52.220.1" port="25001">
                        <sources>
                            <source>199.30.153.84</source>
                            <source>199.30.153.34</source>
                        </sources>
                    </multicast_environment>
                    <multicast_environment id="15" is_beta="false" type="private_test" address="239.250.250.1" port="29001">
                        <sources>
                            <source>199.30.153.84</source>
                        </sources>
                    </multicast_environment>
                </multicast_environments>
            </multicast_group>
        </multicast_grouping>
    </multicast_groups>
</multicast_groups_config>
"""
        when:
        "the xml is imported"
        service.importMulticastGroupsSpecification(xml);
        
        then:
        "there should be a multicast grouping"
        MulticastGrouping.all.size() == 1
        
        and:
        "it should have id, name and description fields"
        MulticastGrouping.get(4).name == "System"
        MulticastGrouping.get(4).description == "arrrg"
        
        and:
        "it should have a saved multicast group"
        MulticastGrouping.get(4).multicastGroups.size() == 1
        MulticastGroup.all.size() == 1
        MulticastGroup.get(6) == MulticastGrouping.get(4).multicastGroups.first()
        
        and:
        "the multicast group should have the correct fields"
        MulticastGroup.get(6).name == "SYSTEM_HEARTBEAT"
        MulticastGroup.get(6).description == "bargle"
        
        and:
        "and the environment types should be imported"
        MulticastEnvironmentType.all.size() == 2
        MulticastEnvironmentType.findByName("production").description == "production"
        MulticastEnvironmentType.findByName("private_test").description == "private_test"
        
        and:
        "the group should contain the correct number of environments, which should be saved"
        MulticastEnvironment.all.size() == 2
        MulticastGroup.get(6).multicastEnvironments.containsAll(MulticastEnvironment.all);
        
        and:
        "the multicast environments should have the correct fields"
        MulticastEnvironment.get(14).beta == true
        MulticastEnvironment.get(14).port == 25001
        MulticastEnvironment.get(14).address == "233.52.220.1"
        MulticastEnvironment.get(14).multicastEnvironmentType == MulticastEnvironmentType.findByName("production")

        MulticastEnvironment.get(15).beta == false
        MulticastEnvironment.get(15).port == 29001
        MulticastEnvironment.get(15).address == "239.250.250.1"
        MulticastEnvironment.get(15).multicastEnvironmentType == MulticastEnvironmentType.findByName("private_test")
        
        and:
        "the multicast sources should be saved and in correct environments"
        MulticastSource.all.size() == 2
        MulticastEnvironment.get(14).multicastSources.size() == 2
        MulticastEnvironment.get(15).multicastSources.size() == 1
        
        
        
    }
}
