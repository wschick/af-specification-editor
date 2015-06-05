package af.specification.editor

class Specification extends SpecificationObject{
    
    boolean published
    Date publicationDate
    
    static hasMany = ['messages':Message]
    static belongsTo = Message

    static constraints = {
        publicationDate nullable: true
    }
}
