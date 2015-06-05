package af.specification.editor

import grails.rest.RestfulController

class DataController extends RestfulController {

    DataController(){
        super(Datum)
    }
    
    @Override
    def  index(Integer max) {

        Category category = Category.get(params.categoryId);
        respond Datum.findAllByCategory(category), model: [("${resourceName}Count".toString()): countResources()]
        
    }
}
