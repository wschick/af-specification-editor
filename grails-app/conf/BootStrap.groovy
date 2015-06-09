
import af.specification.editor.Message
import af.specification.editor.MulticastGroupsImportService
import af.specification.editor.ProtocolValidationImportService
import af.specification.editor.Specification
import af.specification.editor.SpecificationObject
import af.specification.editor.XmlImportService
import org.codehaus.groovy.grails.commons.DefaultGrailsControllerClass
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsApplication

class BootStrap {
    
    XmlImportService xmlImportService;
    ProtocolValidationImportService protocolValidationImportService
    MulticastGroupsImportService multicastGroupsImportService
    
    GrailsApplication grailsApplication

    def init = { servletContext ->

        grailsApplication.domainClasses.each { DefaultGrailsDomainClass c ->
            if (!grailsApplication.controllerClasses.find{ DefaultGrailsControllerClass a -> a.getStaticPropertyValue("scaffold",Class)== c.getClazz()})
                throw new Exception("No scaffold for ${c}")
            else
                println "Have scaffold for ${c.clazz}"
            
            if (!SpecificationObject.isAssignableFrom(c.clazz))
                throw new Exception("Class ${c.clazz} must extend SpecificationObject")

            def pattern = ~/^String toString$/
            if (c.clazz.getMethod("toString").declaringClass != c.clazz)
                throw new Exception("Implement toString for ${c.clazz}")

            if (new DefaultGrailsDomainClass(c.clazz).constrainedProperties.lastUpdatedBy.editable == null ||
                new DefaultGrailsDomainClass(c.clazz).constrainedProperties.lastUpdatedBy.editable != false)
                throw new Exception("Metadata should not be editable for ${c.clazz}")
        }

        multicastGroupsImportService.importMulticastGroupsSpecification(new File("/etc/ntkn-protocol-docs/MulticastGroups.xml").text)
        protocolValidationImportService.importProtocolValidationSpecification(new File("/etc/ntkn-protocol-docs/ProtocolValidationSpecification.xml").text)
        
        File dir = new File("/etc/ntkn-protocol-docs");

        dir.eachFileMatch(~/LightningBoltProtocolSpecification.*xml/) { file ->
            xmlImportService.importCategorySpecificationFromXmlString(file.text);
        }
        
        Specification specification = new Specification();
        specification.published = true;
        Message.all.each {
            specification.addToMessages(it);
        }
        

        specification.save(flush: true);

        specification = new Specification();
        specification.published = true;


        specification.save();
        


    }
    def destroy = {
    }
}
