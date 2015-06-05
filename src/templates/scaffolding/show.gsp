<% import grails.persistence.Event %>
<%=packageName%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		
		<div id="show-${domainClass.propertyName}" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			
			
			<div>
			<a class="btn btn-default" href="\${createLink(uri: '/')}">
				<span class="glyphicon glyphicon-home text-info" aria-hidden="true"></span>
				<g:message code="default.home.label"/>
			</a>
			<g:link class="btn btn-default" action="index">
				<span class="glyphicon glyphicon-list text-info" aria-hidden="true"></span>
				<g:message code="default.list.label" args="[entityName]" />
			</g:link>
			<g:link class="btn btn-default" action="create">
				<span class="glyphicon glyphicon-plus text-success" aria-hidden="true"></span>
				<g:message code="default.new.label" args="[entityName]" />
			</g:link>

			</div>
			<br>
			
			<g:if test="\${flash.message}">
			<div class="message alert alert-info" role="status">\${flash.message}</div>
			</g:if>
			<div class="${domainClass.propertyName}">
				<table class="table table-striped">
					<tr>
						<th>
							Published
						</th>
						<td>
							\${${propertyName}.published}
						</td>
						
					</tr>
			<%  excludedProps = Event.allEvents.toList() << 'id' << 'version'
				allowedNames = domainClass.persistentProperties*.name << 'dateCreated' << 'lastUpdated'
				props = domainClass.properties.findAll { allowedNames.contains(it.name) && !excludedProps.contains(it.name) && (domainClass.constrainedProperties[it.name] ? domainClass.constrainedProperties[it.name].display : true) }
				Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
				props.each { p -> %>
				<g:if test="\${${propertyName}?.${p.name}}">
				<tr>
					<th>
					<label id="${p.name}-label" class="property-label"><g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}" /></label>
					</th>
					<td>
					<%  if (p.isEnum()) { %>
						<span class="property-value" aria-labelledby="${p.name}-label"><g:fieldValue bean="\${${propertyName}}" field="${p.name}"/></span>
					<%  } else if (p.oneToMany || p.manyToMany) { %>
						<ul>
						<g:each in="\${${propertyName}.${p.name}}" var="${p.name[0]}">
							<li>
								<span class="property-value" aria-labelledby="${p.name}-label"><g:link controller="${p.referencedDomainClass?.propertyName}" action="show" id="\${${p.name[0]}.id}">\${${p.name[0]}?.encodeAsHTML()}</g:link></span>
							</li>
						</g:each>
						</ul>
					<%  } else if (p.manyToOne || p.oneToOne) { %>
						<span class="property-value" aria-labelledby="${p.name}-label"><g:link controller="${p.referencedDomainClass?.propertyName}" action="show" id="\${${propertyName}?.${p.name}?.id}">\${${propertyName}?.${p.name}?.encodeAsHTML()}</g:link></span>
					<%  } else if (p.type == Boolean || p.type == boolean) { %>
						<span class="property-value" aria-labelledby="${p.name}-label"><g:formatBoolean boolean="\${${propertyName}?.${p.name}}" /></span>
					<%  } else if (p.type == Date || p.type == java.sql.Date || p.type == java.sql.Time || p.type == Calendar) { %>
						<span class="property-value" aria-labelledby="${p.name}-label"><g:formatDate date="\${${propertyName}?.${p.name}}" /></span>
					<%  } else if (!p.type.isArray()) { %>
						<span class="property-value" aria-labelledby="${p.name}-label"><g:fieldValue bean="\${${propertyName}}" field="${p.name}"/></span>
					<%  } %>
					</td>
				</tr>
				</g:if>
			<%  } %>
				</table>
			</div>
			<g:form url="[resource:${propertyName}, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="btn btn-default" action="edit" resource="\${${propertyName}}">
						<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
						<g:message code="default.button.edit.label" default="Edit" />
					</g:link>
					<g:actionSubmit class="btn btn-danger"
									action="delete" 
									value="\${message(code: 'default.button.delete.label', default: 'Delete')}" 
									onclick="return confirm('\${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" >
						<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
					</g:actionSubmit>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
