package af.specification.editor

import grails.transaction.Transactional

@Transactional
class XmlImportService {
    
    def importProtocolValidationSpecification(String xmlString){
        
        
    }

    def importCategorySpecificationFromXmlString(String xmlString) {
        
        def xml = new XmlSlurper().parseText(xmlString);
        
        
        xml.category.each{categoryXml ->
            Category category = new Category();
            category.categoryId = categoryXml.category_id.toInteger();
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

                
                datum.description = datumXml.description
                datum.shortDescription = datumXml.short_description
                datum.reporterHeading = datumXml.reporter_heading
                
                String datumTypeCode = datumXml.datum_type;
                String datumScaleCode = datumXml.datum_scale;
                
                datum.datumType = DatumType.findByName(datumTypeCode)?:new DatumType(name: datumTypeCode).save();
                datum.datumScale = DatumScale.findByName(datumScaleCode)?:new DatumScale(name: datumScaleCode).save();
                
                datum.category = category;
                
                category.addToData(datum);

                if(!datum.save(flush: true))
                    throw new Exception("Failed to save datum: ${category.id} ${datum.datumId} ${datum.errors.allErrors}")
                
            }
            
            categoryXml.messages.message.each{messageXml ->
                Message message = new Message();
                message.category = category;
                println(messageXml.message_type)
                println(messageXml.multicast_group)
                message.messageType = MessageType.findByName(messageXml.message_type.toString())
                message.multicastGroup = MulticastGroup.findByName(messageXml.multicast_group.toString())
                message.messageVersion = messageXml.message_version.toInteger()
                
                if (! message.save())
                    throw new Exception("Failed to save message: ${category.id} ${message} ${message.errors.allErrors}")
                
                messageXml.fields.field.each({ field_xml->
                    MessageField messageField = new MessageField()
                    messageField.message = message
                    messageField.datum = Datum.findByCategoryAndDatumId(category,field_xml.datum_id.toInteger());
                    messageField.displayOrder =1
                    messageField.exampleValue = field_xml.example_value
                    messageField.fieldType = FieldType.findByName(field_xml.field_type);

                    if (! messageField.save())
                        throw new Exception("Failed to save message field: ${category.id} ${message} ${messageField.errors.allErrors}")
                    
                    
                })
            }

            
            
        
            
        }
        
    }
}
