<#compress>
	<#import "applicationMacros/genericFunctionalities.ftl" as generic/>
	<#import "applicationMacros/pageLanguage.ftl" as language/>

	<@generic.startPage language.getTextSource('meta.title.cookiesPolicy')/>
		<div id="main-container" class="container cookies-policy-main-container">
			<div class="panel panel-body col-lg-12">
				<h1>${language.getTextSource('meta.title.cookiesPolicy')}<h1>
				<br/>

				<h2>${language.getTextSource('cookiesPolicy.whatAreCookies')}</h2>
				<p class="text-muted text-left">
					${language.getTextSource('${manufacturerName}.cookiesPolicy.whatAreCookies.text')}
				</p>
				<br/>

				<h2>${language.getTextSource('cookiesPolicy.howWeUseCookies')}</h2>
				<p class="text-muted text-left">
					${language.getTextSource('cookiesPolicy.howWeUseCookies.text')}
				</p>
				<br/>

				<h2>${language.getTextSource('cookiesPolicy.ourCookies')}</h2>
				<p class="text-muted text-left">
					${language.getTextSource('cookiesPolicy.ourCookies.text')}
				</p>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>${language.getTextSource('cookiesPolicy.ourCookies.cookieName')}</th>
							<th>${language.getTextSource('cookiesPolicy.ourCookies.cookieFunctionality')}</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${language.getTextSource('cookiesPolicy.ourCookies.language')}</td>
							<td>${language.getTextSource('cookiesPolicy.ourCookies.language.functionality')}</td>
						</tr>
						<tr>
							<td>${language.getTextSource('cookiesPolicy.ourCookies.unitsOfMeasure')}</td>
							<td>${language.getTextSource('cookiesPolicy.ourCookies.unitsOfMeasure.functionality')}</td>
						</tr>
						<tr>
							<td>${language.getTextSource('cookiesPolicy.ourCookies.cookiesDirective')}</td>
							<td>${language.getTextSource('cookiesPolicy.ourCookies.cookiesDirective.functionality')}</td>
						</tr>
					</tbody>
				</table>
				<br/>

				<h2>${language.getTextSource('cookiesPolicy.thirdPartyCookies')}</h2>
				<p class="text-muted text-left">
					${language.getTextSource('cookiesPolicy.thirdPartyCookies.text')}
				</p>
				<br/>

				<h2>${language.getTextSource('cookiesPolicy.moreInformation')}</h2>
				<p class="text-muted text-left">
					${language.getTextSource('cookiesPolicy.moreInformation.text')}
				</p>
				<br/>
			</div>
		</div>
	<@generic.endPage/>
</#compress>