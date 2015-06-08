package af.specification.editor

class MessageField extends SpecificationObject {
    
    Datum datum
    FieldType fieldType
    String exampleValue
    Integer displayOrder
    Message message
    
    static belongsTo = ['message':Message]

    static constraints = {
        datum unique: ['message'], validator: {val,MessageField messageField ->
            if (!messageField.message.category.data.contains(val))
                return false;
        }
    }
    
    @Override
    boolean isPublished(){
        return message.isPublished();
    }
    
    @Override
    String toString(){
        return "${fieldType} field for Datum #${datumId}${published?"":" (unpublished)"}"
        
    }
}
