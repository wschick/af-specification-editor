package af.specification.editor

class RangeConstraint {
    
    Float upperbound
    Float lowerbound
    
    static belongsTo = ['datum':Datum]
    

    static constraints = {
    }
}
