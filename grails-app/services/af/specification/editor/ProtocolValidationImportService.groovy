package af.specification.editor

import grails.transaction.Transactional

@Transactional
class ProtocolValidationImportService {

    def importProtocolValidationSpecification(String xmlString){

        def xml = new XmlSlurper().parseText(xmlString);
        
        xml.multicast_groups.multicast_group.each{multicast_group ->
            
            /*def multicastGroup = new MulticastGroup();
            multicastGroup.multicastGroupId = multicast_group.@id.toInteger()
            multicastGroup.name =  multicast_group.@name;
            
            if (!multicastGroup.save())
                throw new Exception("Failed to import mutlicast group: ${multicastGroup.errors.allErrors}")
                */

        }

        xml.message_types.message_type.each{ message_type ->
            
            def messageType = new MessageType()
            messageType.value = message_type.@value.toInteger();
            messageType.name = message_type.@name
            messageType.description = message_type.@name
            
            if (!messageType.save())
                throw new Exception("Failed to import message type: ${messageType.errors.allErrors}")
        }
        
        xml.field_types.field_type.each{field_type ->
            def fieldType = new FieldType();
            fieldType.length = field_type.@length.toInteger()
            fieldType.fieldTypeId = field_type.@id.toInteger()
            fieldType.name = field_type.@name
            fieldType.description = field_type.@name

            if (!fieldType.save())
                throw new Exception("Failed to import field type: ${fieldType.errors.allErrors}")

        }

        xml.data_types.data_type.each{data_type ->

            def datumType = new DatumType()
            
            datumType.id = data_type.@id.toInteger()
            datumType.name = data_type.@name
            datumType.display = data_type.@display
            datumType.description = data_type.@description


            if (!datumType.save())
                throw new Exception("Failed to import datum type: ${datumType.errors.allErrors}")

        }

    }
}
