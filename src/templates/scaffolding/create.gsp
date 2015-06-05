<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<!--a href="#create-${domainClass.propertyName}" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="\${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div-->
		<div id="create-${domainClass.propertyName}" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>

			<div>
				<a class="btn btn-default" href="\${createLink(uri: '/')}">
					<span class="glyphicon glyphicon-home text-info" aria-hidden="true"></span>
					<g:message code="default.home.label"/>
				</a>
				<g:link class="btn btn-default" action="index">
					<span class="glyphicon glyphicon-list text-info" aria-hidden="true"></span>
					<g:message code="default.list.label" args="[entityName]" />
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
					<g:form class="form-horizontal" url="[resource:${propertyName}, action:'save']" <%= multiPart ? ' enctype="multipart/form-data"' : '' %>>
						<fieldset class="form">
							<g:render template="form"/>
						</fieldset>
						<fieldset class="buttons">
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<g:submitButton name="create" class="btn btn-default" value="\${message(code: 'default.button.create.label', default: 'Create')}" >
									</g:submitButton>
								</div>
							</div>
						</fieldset>
					</g:form>
				</div>
			</div>
		</div>
	</body>
</html>
