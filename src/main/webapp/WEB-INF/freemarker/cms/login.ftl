<#include "../applicationMacros/genericFunctionalities.ftl">

<@startPage getTextSource('title.login')/>

<div id="main-container" class="col-sm-offset-2">
	<div class="row">
		<div id="main-login-div" class="col-md-offset-2 col-md-offset-0 col-lg-8 col-sm-10 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-body form-horizontal">
					<#if error??>
						<div class="alert alert-danger" role="alert">
							<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  							<span class="sr-only">Error:</span>${error}
						</div>
					</#if>
					<#if logout??>
						<div class="alert alert-info" role="alert">
							<span class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span>
  							<span class="sr-only">Info:</span>${logout}
						</div>
					</#if>

  					<div class="form-group">
    					<label for="username-input" class="col-sm-2 control-label">${getTextSource('cms.login.user')}</label>
    					<div class="col-sm-10">
      						<input id="username-input" name="username" type="text" class="form-control" placeholder="Username">
    					</div>
  					</div>
  					<div class="form-group">
    					<label for="password-input" class="col-sm-2 control-label">${getTextSource('cms.login.password')}</label>
    					<div class="col-sm-10">
      						<input id="password-input" name="password" type="password" class="form-control" placeholder="Password">
    					</div>
  					</div>
  					<div class="form-group">
    					<div class="col-sm-offset-2 col-sm-10">
      						<button id="login-button" class="btn btn-success" onClick="submitLoginForm(true);">${getTextSource('cms.login')}</button> 
      						<#if loggedIn?? && loggedIn == true>   
								<button id="logout-button" class="btn btn-danger" onClick="submitLoginForm(false);">${getTextSource('cms.logout')}</button>
							</#if>  						
    					</div>
  					</div>
				</div>
			</div>
		</div>		
	</div>
</div>
<@endPage/>