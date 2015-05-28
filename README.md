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


# Message Versions

## Should be incremented when:

Anything that affects the wire format of a message changes, including:
    
* Datum within message change (field added/removed)
* Field type changed
    
 

# Specification Versions

A specification version


* All Changes should be visible
    * Implementation - check lastModified time of all objects?
* An XML diff should be viewable
    * Show any files changes since the last published version