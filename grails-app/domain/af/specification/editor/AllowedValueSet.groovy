package af.specification.editor

class AllowedValueSet {
    
    String description
    
    static hasMany = ["allowedValues":AllowedValue,
                        "messageFields":MessageField]

    static constraints = {
    }
}
