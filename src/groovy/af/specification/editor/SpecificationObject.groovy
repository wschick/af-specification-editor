package af.specification.editor
/**
 * Created by will.schick on 6/2/15.
 */
abstract class SpecificationObject {
    
    Date dateCreated
    Date lastUpdated
    String lastUpdatedBy = "anonymous"
    String createdBy = "anonymous"
    static constraints = {
        lastUpdatedBy editable:false, display: false
        createdBy editable:false, display: false
    }
    
    abstract boolean isPublished()
    
    def beforeDelete = {
        if (isPublished())
            return false;
    }
    
    @Override
    boolean equals(Object o){
        if (this.class != o.class)
            return false;
        
        println("Comparing ${o.id} ${this.id}")
        
        return o.id == this.id;
        
    }
}
