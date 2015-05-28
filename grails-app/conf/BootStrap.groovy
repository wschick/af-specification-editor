import af.specification.editor.XmlImportService

class BootStrap {
    
    XmlImportService xmlImportService;

    def init = { servletContext ->
        File dir = new File("/etc/ntkn-protocol-docs");

        dir.eachFileMatch(~/LightningBoltProtocolSpecification.*xml/) { file ->
            xmlImportService.importCategorySpecificationFromXmlString(file.text);
        }

    }
    def destroy = {
    }
}
