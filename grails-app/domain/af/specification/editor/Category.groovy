package af.specification.editor

import grails.converters.JSON
import grails.rest.Resource

class Category extends SpecificationObject {

    Integer categoryId;
    String name;
    String description;
    String reporterHeading;
    Country country;
    String releaseMethod;
    String releaseLocation;
    List<Datum> data;
    
    static hasMany = ['messages':Message,
                        'data': Datum]

   
    static constraints = {
        description maxSize: 2048
        categoryId unique: true, immutable:true
        name unique: true , immutable:true
    }
    
    static transients = ['published']
    
    boolean isPublished(){
        for(Message message: messages)
            if (message.isPublished())
                return true;
    }
    
    @Override
    String toString(){
        return "${categoryId}: ${name}"
        
    }
    

}
