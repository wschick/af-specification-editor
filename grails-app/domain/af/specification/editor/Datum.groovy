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


    static hasMany = ['messageFields':MessageField,
                      'rangeConstraints':RangeConstraint,
                      'outerRangeConstraints':OuterRangeConstraint ]
    
    static belongsTo = ['category':Category]

    static constraints = {
        description maxSize: 2048
        datumId unique: ['category'], immutable:true
        category()
        datumType immutable:true
        datumScale nullable: true, immutable:true
        reporterHeading maxSize: 2048
        shortDescription maxSize: 2048
        
    }
    
    @Override
    boolean isPublished(){
        for(MessageField messageField: messageFields )
            if (messageField.isPublished())
                return true;
        
    }
    
    @Override
    String toString(){
        return "Category: ${category.categoryId} Datum: ${datumId} - ${description}"
    }



}
