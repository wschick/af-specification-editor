package af.specification.editor

class ChangeTagLib {
    static defaultEncodeAs = [taglib:'raw']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
    
    def changes ={
        
        out <<"<table class='table table-default table-striped'>"

        out << "<thead>"
        out << "<tr>"
        out << "<th>Type</th>"
        out << "<th>Object</th>"
        out << "<th>Date Updated</th>"
        out << "<th>Updated By</th>"
        out << "</tr>"
        out << "</thead>"
        
        boolean changes = false;
        
        grailsApplication.getArtefacts("Domain").each { domainClass ->

            SpecificationObject latestPublishedSpecification = Specification.findAllByPublished(true, [max: 1, sort: "lastUpdated", order: "desc"])[0]

            def c = domainClass.clazz

            if (SpecificationObject.isAssignableFrom(c)) {
                def result = c.findAllByDateCreatedGreaterThanOrLastUpdatedGreaterThan(latestPublishedSpecification.lastUpdated, latestPublishedSpecification.lastUpdated);


                result.each {
                    out << "<tr>"
                        out << "<td>${domainClass.naturalName}</td>"
                        out << "<td><a href='${domainClass.logicalPropertyName}/show/${it.id}'>${it}</a></td>"
                        out << "<td>${it.lastUpdated}</td>"
                        out << "<td>${it.lastUpdatedBy}</td>"
                    out << "</tr>"
                    changes = true
                }

            }

        }
        
        if (!changes){
            out << "<tr>"
            out << "<td colspan='2'>No Changes</td>"
            out << "</tr>"
        }

        out <<"</table>"
        
        
    }
}
