

# Behaviors of the specification model

## Immutability of Published Items

Once an item has been published with a given category and datum id, the meaning of this should not be changed. A datum is this state is **locked**.

##### Once a datum is locked, the following fields cannot be changed:

* Datum ID
* Datum Scale
* Datum Type

##### The following fields can be changed, but should not change in meaning

* Description
* Short Description
* Reporter Heading

##### The following can change at any time

* Display order

There is a time in between when an item is created and when it is published where it can be left 'open' - so if it has not been seen by anybody, it may be convenient to change its meaning. An indication of a category or datums "published" status may be gotten by seeing if it is reference in any **Published Specification Version**.


## Message Versioning

Some changes require the message version to change, some don't. When should the message version be changed?

When the wire format changes. Different change types should be seperated along these lines.

### Requires a version increment

* A field is added
* A field is removed
* A field type is changed (eg, from float to double)

### Does NOT require a version increment

* Category or datum labels change
* Multicast group changes
* Constraints change
* Allowed values change
* Changes to the menu location for reporter app

# XML Import/Export

XML Import and export will be provided to consume existing specification versions, and produce new ones.

#### Export

Export should produce a specification using the latest version of all active categories.

#### Import 

The import functionality will be used in initial development and migration from xml maintainence, as well as initial acceptance testing.

For Import, multiple versions can be imported, to populate historical message records. When importing, the latest specification will be considered first, and previous versions will be added going backwards in time.

##### Acceptance testing through import and export

The import/export function will be tested by importing a specification, then exporting it, and comparing the results with [XmlUnit](http://www.xmlunit.org/)

# Deploying the Specification

The database will be exported to a **Specification Version** for deployment. This is a snapshot of the database. When a specification version is exported, it is considered a release candidate, and can undergo downstream integration and acceptance testing. Once a version has been approved downstream, that version will be recored as a **Published Specification Version**.

### Process Story

The admin starts the export process from the admin panel.

They are shown all object changes, as derived from database metadata. These will be compared to the last production version. If these are ok, they continue.

They are shown an xml-file view of the diff between the last production verion and the current version. They can view an index that shows if there are changes to a file, then open that file for an xml-diff. If thats ok, they approve the specification. 

The exported specification is now available as a release candidate. The specification is run against the current unit and integration test suites in Jenkins. If those pass, the deployment artifacts (RPMs and MSIs) are deployed to acceptance test environments, so reporters can approve. 

When approved, the production deployment process records that that specification version has been promoted to production.

# UI Implementation

## For simple objects

For some simple object types, generic scaffolding will be used for management. This will be for types that are simple, and don't change often.

Objects that will be managed by scaffolding:

* DatumScale
* DatumType
* FieldType
* MessageType

## For Category/Datum/Messages

This set of objects is more complicated, and modified more frequently. It will have custom UI built for management.


# Application Roles

The application will used role based security.

## Editor

This is a reporter. They have the domain knowledge for the meaning of items.

## Administrator

This is a technical role.

