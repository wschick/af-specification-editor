package af.specification.editor

class AllowedValue {

    Integer value
    String description
    
    static belongsTo = ['allowedValueSet':AllowedValueSet]
    static constraints = {
    }
}
