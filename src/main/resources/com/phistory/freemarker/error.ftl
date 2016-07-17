<#import "applicationMacros/genericFunctionalities.ftl" as generic/>
<#import "applicationMacros/pageLanguage.ftl" as language/>

<@generic.startPage language.getTextSource('title.error')/>
	<div id="main-container" class="container">
		<div class="panel panel-body col-lg-12">
			<p class="col-lg-12 text-muted text-center">${language.getTextSource('error.undefinedError')}</p>
		</div>
	</div>
<@generic.endPage/>