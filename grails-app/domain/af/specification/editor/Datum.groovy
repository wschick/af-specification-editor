package af.specification.editor

import org.apache.commons.lang.builder.HashCodeBuilder

class Datum extends SpecificationObject{
    
    int datumId;
    Category category;
    String description;
    String shortDescription;
    String reporterHeading;
    DatumType datumType;
    DatumScale datumScale;


    static hasMany = ['messageFields':MessageField]
    
    static belongsTo = ['category':Category]

    static constraints = {
        description maxSize: 2048
        datumScale nullable: true
        reporterHeading maxSize: 2048
        shortDescription maxSize: 2048
        datumId unique: ['category']
    }
    
    @Override
    boolean isPublished(){
        for(MessageField messageField: messageFields )
            if (messageField.isPublished())
                return true;
        
    }
    
    @Override
    String toString(){
        return "${datumId}: ${description}"
    }



}
