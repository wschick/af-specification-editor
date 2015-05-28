package af.specification.editor

import grails.transaction.Transactional

@Transactional
class XmlImportService {

    def importCategorySpecificationFromXmlString(String xmlString) {
        
        def xml = new XmlSlurper().parseText(xmlString);
        
        
        xml.category.each{categoryXml ->
            Category category = new Category();
            category.id = categoryXml.category_id.toInteger();
            category.name = categoryXml.@name;
            category.description = categoryXml.description;
            category.reporterHeading = categoryXml.reporter_heading;
            category.releaseMethod = categoryXml.release_method;
            category.releaseLocation = categoryXml.release_location;

            String countryCode =  categoryXml.country;
            
            category.country = Country.findByCode(countryCode)?:new Country(code:countryCode).save();

            if(!category.save(flush: true))
                throw new Exception("Failed to save category: ${category.id}")
            
            categoryXml.data.datum.each{datumXml ->
                Datum datum = new Datum();
                datum.datumId = datumXml.datum_id.toInteger();
                
                if (datumXml.display_order.size())
                    datum.displayOrder = datumXml.display_order.toInteger()
                
                datum.description = datumXml.description
                datum.shortDescription = datumXml.short_description
                datum.reporterHeading = datumXml.reporter_heading
                
                String datumTypeCode = datumXml.datum_type;
                String datumScaleCode = datumXml.datum_scale;
                
                datum.datumType = DatumType.findByCode(datumTypeCode)?:new DatumType(code: datumTypeCode).save();
                datum.datumScale = DatumScale.findByCode(datumScaleCode)?:new DatumScale(code: datumScaleCode).save();
                
                datum.category = category;

                if(!datum.save(flush: true))
                    throw new Exception("Failed to save datum: ${category.id} ${datum.datumId} ${datum.errors.allErrors}")
                
            }

            
            
        
            
        }
        
    }
}
