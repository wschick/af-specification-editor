# Usage Scenarios

## A category is added

A user (who?) defines a category.


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

# XML Import/Export

XML Import and export will be provided to consume existing specification versions, and produce new ones.

For Import, multiple versions can be imported, to populate historical message records. When importing, the latest specification will be considered first, and previous versions will be added going backwards in time.

Export should produce a specification using the latest version of all active categories.

The import/export function will be tested by importing a specification, then exporting it, and comparing the results with [XmlUnit](http://www.xmlunit.org/)


# Message Versioning

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


# Deploying the Specification

The database will be exported to a **Specification Version** for deployment. This is a snapshot of the database. When a specification version is exported, it is considered a release candidate, and can undergo downstream integration and acceptance testing. Once a version has been approved downstream, that version will be recored as the **Latest Production Specification Version**.

### Process Story

The admin starts the export process from the admin panel.

They are shown all object changes, as derived from database metadata. These will be compared to the last production version. If these are ok, they continue.

They are shown an xml-file view of the diff between the last production verion and the current version. They can view an index that shows if there are changes to a file, then open that file for an xml-diff. If thats ok, they approve the specification. 

The exported specification is now available as a release candidate. The specification is run against the current unit and integration test suites in Jenkins. If those pass, the deployment artifacts (RPMs and MSIs) are deployed to acceptance test environments, so reporters can approve. 

When approved, the production deployment process records that that specification version has been promoted to production.


