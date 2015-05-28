package af.specification.editor

class Datum implements Serializable{
    
    int datumId;
    Category category;
    String description;
    String shortDescription;
    String reporterHeading;
    Integer displayOrder;
    DatumType datumType;
    DatumScale datumScale;

    
    static belongsTo = ['category':Category]

    static constraints = {
        datumScale nullable: true
        displayOrder nullable: true
        description maxSize: 2048
        reporterHeading maxSize: 2048
        shortDescription maxSize: 2048
        datumId unique: ['category']
    }

}
