package af.specification.editor

class ChangeTagLib {
    static defaultEncodeAs = [taglib:'raw']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
    
    def changes ={
        
        out <<"<table class='table table-default table-striped'>"
        
        grailsApplication.getArtefacts("Domain").each {

            SpecificationObject latestPublishedSpecification = Specification.findAllByPublished(true, [max: 1, sort: "lastUpdated", order: "desc"])[0]

            def c = it.clazz

            if (SpecificationObject.isAssignableFrom(c)) {
                def result = c.findAllByDateCreatedGreaterThanOrLastUpdatedGreaterThan(latestPublishedSpecification.lastUpdated, latestPublishedSpecification.lastUpdated);

                out << "<tr>"
                result.each {
                    
                    out << "<td>${it}</td>"
                    out << "<td>${it.lastUpdated}</td>"

                }
                out << "</tr>"
            }

        }

        out <<"</table>"
        
        
    }
}
