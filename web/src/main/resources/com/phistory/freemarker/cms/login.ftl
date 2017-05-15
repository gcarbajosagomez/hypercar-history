<#import "../applicationMacros/genericFunctionalities.ftl" as generic/>
<#import "../applicationMacros/pageLanguage.ftl" as language/>
<#import "../applicationMacros/crudOperations.ftl" as crudOperations/>

<@generic.startPage language.getTextSource('meta.title.login')/>

<div id="main-container" class="row col-sm-offset-2">
	<div id="main-login-div" class="col-md-offset-2 col-md-offset-0 col-lg-8 col-sm-10 col-xs-12">
		<div class="panel panel-default">
			<div class="panel-body form-horizontal">
				<#assign resultDTO = {"errorMessages" : [error!""],"successMessage" : logout!""}>
				<@crudOperations.addOperationResultMessage resultDTO/>
				<#if loggedIn?? && loggedIn == false>
  					<div class="form-group">
    					<label for="username-input" class="col-sm-2 control-label">${language.getTextSource('cms.login.user')}</label>
	    				<div class="col-sm-10">
    	  					<input id="username-input" name="username" type="text" class="form-control" placeholder="${language.getTextSource('cms.login.user')}">
    					</div>
  					</div>
  					<div class="form-group">
    					<label for="password-input" class="col-sm-2 control-label">${language.getTextSource('cms.login.password')}</label>
    					<div class="col-sm-10">
	      					<input id="password-input" name="password" type="password" class="form-control" placeholder="${language.getTextSource('cms.login.password')}">
    					</div>
  					</div>
  					<div class="form-group">
    					<div class="col-sm-offset-2 col-sm-10">
      						<button id="login-button" class="btn btn-success" onClick="submitLoginForm(true);">${language.getTextSource('cms.login')}</button>
    					</div>
  					</div>
                </#if>
				<#if loggedIn?? && loggedIn == true>
					<button id="logout-button" class="btn btn-danger" onClick="submitLoginForm(false);">${language.getTextSource('cms.logout')}</button>
				</#if>
			</div>
		</div>
	</div>
</div>
<@generic.endPage/>