import af.specification.editor.Category
import af.specification.editor.ImmutabilityConstraint
import af.specification.editor.Message
import af.specification.editor.ProtocolValidationImportService
import af.specification.editor.Specification
import af.specification.editor.XmlImportService
import org.codehaus.groovy.grails.validation.ConstrainedProperty
import org.spockframework.compiler.model.Spec

class BootStrap {
    
    XmlImportService xmlImportService;
    ProtocolValidationImportService protocolValidationImportService

    def init = { servletContext ->


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
