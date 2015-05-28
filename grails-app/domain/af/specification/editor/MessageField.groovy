package af.specification.editor

class MessageField {
    
    Datum datum
    FieldType fieldType
    String exampleValue
    
    static belongsTo = ['message':Message]

    static constraints = {
    }
}
