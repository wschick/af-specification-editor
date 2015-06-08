<%=packageName%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="edit-${domainClass.propertyName}" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
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
			<div class="message" role="status">\${flash.message}</div>
			</g:if>
			<g:hasErrors bean="\${${propertyName}}">
				<div class="alert alert-danger">
					<ul class="errors" role="alert">
						<g:eachError bean="\${${propertyName}}" var="error">
						<li <g:if test="\${error in org.springframework.validation.FieldError}">data-field-id="\${error.field}"</g:if>><g:message error="\${error}"/></li>
						</g:eachError>
					</ul>
				</div>
			</g:hasErrors>
			<div class="panel panel-default">
				<div class="panel-body">
					<g:form class="form-horizontal" url="[resource:${propertyName}, action:'update']" method="PUT" <%= multiPart ? ' enctype="multipart/form-data"' : '' %>>
						<g:hiddenField name="version" value="\${${propertyName}?.version}" />
						<fieldset class="form">
							<g:render template="form"/>
						</fieldset>
						<fieldset class="buttons">
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<p class="help-block">*: Required †: Immutable after publication</p>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<g:actionSubmit class="save btn btn-default" action="update" value="\${message(code: 'default.button.update.label', default: 'Update')}" />
								</div>
							</div>
							
						</fieldset>
					</g:form>
				</div>
			</div>
		</div>
	</body>
</html>
