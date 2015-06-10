package af.specification.editor

class OuterRangeConstraint {

    Float mayBeBiggerThan
    Float mayBeSmallerThan
    Boolean allowZero = false
    
    static belongsTo = ['datum':Datum]
    
    static constraints = {
        mayBeBiggerThan nullable: true
        mayBeSmallerThan nullable: true
    }
}
