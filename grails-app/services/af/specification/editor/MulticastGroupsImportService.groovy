package af.specification.editor

import grails.transaction.Transactional

@Transactional
class MulticastGroupsImportService {

    def importMulticastGroupsSpecification(String xmlString){
        def xml = new XmlSlurper().parseText(xmlString);

        xml.multicast_groups.multicast_grouping.each{multicast_grouping ->
            
            def multicastGrouping  = new MulticastGrouping();
            multicastGrouping.id = multicast_grouping.@id.toInteger()
            multicastGrouping.name = multicast_grouping.@name
            multicastGrouping.description = multicast_grouping.@desc

            if (!multicastGrouping.save())
                throw new Exception("Failed to import multicastGrouping: ${multicastGrouping.errors.allErrors}")
            
            multicast_grouping.multicast_group.each{ multicast_group ->
                def multicastGroup  = new MulticastGroup()
                multicastGroup.id = multicast_group.@id.toInteger();
                multicastGroup.multicastGroupId = multicastGroup.id
                multicastGroup.name = multicast_group.@name
                multicastGroup.description = multicast_group.@desc
                
                multicastGrouping.addToMulticastGroups(multicastGroup);

                if (!multicastGroup.save())
                    throw new Exception("Failed to import multicastGroup: ${multicastGroup.errors.allErrors}")
                
                multicast_group.multicast_environments.multicast_environment.each{ multicast_environment ->
                    
                    String multicastEnvironmentTypeName = multicast_environment.@type
                    
                    MulticastEnvironmentType type = MulticastEnvironmentType.findByName(multicastEnvironmentTypeName);
                    
                    if (!type){
                        type = new MulticastEnvironmentType(
                                name: multicastEnvironmentTypeName, 
                                description: multicastEnvironmentTypeName);
                        
                        if (!type.save())
                            throw new Exception("Failed to import multicast environment type: ${type.errors.allErrors}")
                    }
                    
                    MulticastEnvironment multicastEnvironment = new MulticastEnvironment();
                    multicastEnvironment.id = multicast_environment.@id.toInteger()
                    multicastEnvironment.multicastEnvironmentType = type
                    multicastEnvironment.beta = multicast_environment.@is_beta.toBoolean()
                    multicastEnvironment.address = multicast_environment.@address
                    multicastEnvironment.port = multicast_environment.@port.toInteger()
                    
                    multicastGroup.addToMulticastEnvironments(multicastEnvironment);

                    if (!multicastEnvironment.save())
                        throw new Exception("Failed to import multicastEnvironment: ${multicastEnvironment.errors.allErrors}")
                    
                    multicast_environment.sources.source.each{source ->
                        def sourceText = source.toString();
                        
                        MulticastSource multicastSource = MulticastSource.findByAddress(sourceText);
                        
                        if (!multicastSource){
                            multicastSource = new MulticastSource(
                                    address: sourceText,
                                    name: InetAddress.getByName(sourceText).getHostName()
                            )
                            
                            if (!multicastSource.save())
                                throw new Exception("Failed to save multicast source ${multicastSource.errors.allErrors}")
                        }
                        
                        multicastEnvironment.addToMulticastSources(multicastSource);

                    }
                    
                    
                }
                
            }

        }
    }
}
