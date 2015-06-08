package af.specification.editor

import grails.rest.Resource
import org.apache.commons.lang.builder.HashCodeBuilder

class Message extends SpecificationObject{

    Category category;
    MessageType messageType;
    Integer messageVersion;
    
    boolean hasEstimate;
    MulticastGroup multicastGroup;
    
    static belongsTo = ['category':Category]
    static hasMany = ['messageFields':MessageField,'specifications':Specification]
    
    static constraints = {
        messageVersion unique: ['category','messageType'],immutable:true
        category validator: validateFieldsMatchCategory, immutable:true
        messageType immutable: true
        hasEstimate()
        multicastGroup()
        messageFields immutable: true
        
        specifications display: false
    }
    
    static  def validateFieldsMatchCategory = {Category category, Message message ->
        if (message.messageFields.find({ it.datum.category.id != category.id})) {
            return false
        }
    }


    @Override
    boolean isPublished(){
        for (Specification specification:specifications)
            if (specification.isPublished())
                return true;
    }
    
    @Override
    String toString(){
        return "Type: ${messageType} Version: ${messageVersion}";
        
    }
}
