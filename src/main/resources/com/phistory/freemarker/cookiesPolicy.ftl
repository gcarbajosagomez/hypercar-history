<#include "applicationMacros/genericFunctionalities.ftl">

<@startPage getTextSource('title.cookiesPolicy')/>
	<div id="main-container" class="container">
		<div class="panel panel-body col-lg-12">
			<h1>${getTextSource('title.cookiesPolicy')}<h1>
			<br/>
						
			<h2>${getTextSource('cookiesPolicy.whatAreCookies')}</h2>
			<p class="text-muted text-left">
				${getTextSource('cookiesPolicy.whatAreCookies.text')}
			</p>
			<br/>
			
			<h2>${getTextSource('cookiesPolicy.howWeUseCookies')}</h2>
			<p class="text-muted text-left">
				${getTextSource('cookiesPolicy.howWeUseCookies.text')}
			</p>				
			<br/>
				
			<h2>${getTextSource('cookiesPolicy.ourCookies')}</h2>
			<p class="text-muted text-left">
				${getTextSource('cookiesPolicy.ourCookies.text')}
			</p>
			<table class="table table-bordered">
  				<thead>
  					<tr>
  						<th>${getTextSource('cookiesPolicy.ourCookies.cookieName')}</th>
  						<th>${getTextSource('cookiesPolicy.ourCookies.cookieFunctionality')}</th>
  					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${getTextSource('cookiesPolicy.ourCookies.language')}</td>
						<td>${getTextSource('cookiesPolicy.ourCookies.language.functionality')}</td>
					</tr>
					<tr>
						<td>${getTextSource('cookiesPolicy.ourCookies.unitsOfMeasure')}</td>
						<td>${getTextSource('cookiesPolicy.ourCookies.unitsOfMeasure.functionality')}</td>
					</tr>
					<tr>
						<td>${getTextSource('cookiesPolicy.ourCookies.cookiesDirective')}</td>
						<td>${getTextSource('cookiesPolicy.ourCookies.cookiesDirective.functionality')}</td>
					</tr>
				</tbody>
			</table>
			<br/>
			
			<h2>${getTextSource('cookiesPolicy.thirdPartyCookies')}</h2>
			<p class="text-muted text-left">
				${getTextSource('cookiesPolicy.thirdPartyCookies.text')}
			</p>	
			<br/>
			
			<h2>${getTextSource('cookiesPolicy.moreInformation')}</h2>
			<p class="text-muted text-left">
				${getTextSource('cookiesPolicy.moreInformation.text')}
			</p>	
			<br/>		
		</div>
	</div>
<@endPage/> 