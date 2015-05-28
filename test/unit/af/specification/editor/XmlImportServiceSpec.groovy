package af.specification.editor

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(XmlImportService)
@Mock([Category,Country,Datum,DatumType,DatumScale])
class XmlImportServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "importCategorySpecificationFromXmlString should add a category for each <category> tag"() {
        given:
        "a category xml blob"
        def xmlString = """
<protocol>
    <category name="NAME">
        <description>DESC</description>
        <reporter_heading>RHEAD</reporter_heading>
        <country>US</country>
        <category_id>30</category_id>
        <release_method>embargoed</release_method>
        <release_location>everywhere</release_location>
    </category>
</protocol>
"""
        when:
        "The xml is imported"
        service.importCategorySpecificationFromXmlString(xmlString);
        
        then:
        "The category should exist"
        Category.all.size() == 1
        
        and:
        "it should have all fields populated"
        Category category = Category.all[0];
        category.id == 30
        category.country == Country.findByCode("US");
        category.name == "NAME"
        category.description == "DESC"
        category.reporterHeading == "RHEAD"
        category.releaseMethod == "embargoed"
        category.releaseLocation == "everywhere"
    }

    void "importCategorySpecificationFromXmlString should add a datum for each datum contained in a category"() {
        given:
        "a category xml blob"
        def xmlString = """
<protocol>
    <category name="NAME">
        <description>DESC</description>
        <reporter_heading>RHEAD</reporter_heading>
        <country>US</country>
        <category_id>30</category_id>
        <release_method>embargoed</release_method>
        <release_location>everywhere</release_location>
        <data>
            <datum>
                <description>DDESC</description>
                <short_description>DSDESC</short_description>
                <datum_type>USD</datum_type>
                <datum_scale>B</datum_scale>
                <reporter_heading>DRHEAD</reporter_heading>
                <display_order>2</display_order>
                <datum_id>1</datum_id>
            </datum>
        </data>
    </category>
</protocol>
"""
        when:
        "The xml is imported"
        service.importCategorySpecificationFromXmlString(xmlString);

        then:
        "The category should exist"
        Category.all.size() == 1
        
        and:
        "the datum should exist"
        Datum.all.size() == 1

        and:
        "the datum should have all the fields populated"
        Category category = Category.all[0];
        Datum datum = category.data[0];
        datum.category == category;
        datum.datumId == 1
        datum.displayOrder == 2
        datum.description == "DDESC"
        datum.shortDescription == "DSDESC"
        datum.reporterHeading == "DRHEAD"
        datum.datumType == DatumType.findByCode("USD")
        datum.datumScale == DatumScale.findByCode("B")
        
        
    }

    void "importCategorySpecificationFromXmlString should ignore the display order for datum if it is not there"() {
        given:
        "a category xml blob"
        def xmlString = """
<protocol>
    <category name="NAME">
        <description>DESC</description>
        <reporter_heading>RHEAD</reporter_heading>
        <country>US</country>
        <category_id>30</category_id>
        <release_method>embargoed</release_method>
        <release_location>everywhere</release_location>
        <data>
            <datum>
                <description>DDESC</description>
                <short_description>DSDESC</short_description>
                <datum_type>USD</datum_type>
                <datum_scale>B</datum_scale>
                <reporter_heading>DRHEAD</reporter_heading>
                <datum_id>1</datum_id>
            </datum>
        </data>
    </category>
</protocol>
"""
        when:
        "The xml is imported"
        service.importCategorySpecificationFromXmlString(xmlString);

        then:
        "The category should exist"
        Category.all.size() == 1

        and:
        "the datum should exist"
        Datum.all.size() == 1

        and:
        "the datum should have all the fields populated"
        Category category = Category.all[0];
        Datum datum = category.data[0];
        datum.category == category;
        datum.datumId == 1
        datum.displayOrder == null
        datum.description == "DDESC"
        datum.shortDescription == "DSDESC"
        datum.reporterHeading == "DRHEAD"
        datum.datumType == DatumType.findByCode("USD")
        datum.datumScale == DatumScale.findByCode("B")


    }
}
