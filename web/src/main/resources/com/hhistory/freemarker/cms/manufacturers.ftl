<#import "/spring.ftl" as spring/>
<#import "../applicationMacros/genericFunctionalities.ftl" as generic/>
<#import "../applicationMacros/pageLanguage.ftl" as language/>
<#import "../applicationMacros/uriUtils.ftl" as uriUtils/>

<@generic.startPage language.getTextSource('meta.title.allManufacturers')/>

<div id="main-container" class="container panel panel-default">
     <div class="row">
        <div class="col-lg-2">
			<div class="list-group">
				<#list manufacturerEntities as manufacturer>
    				<a class="list-group-item" href='${uriUtils.buildDomainURI("/${cmsContext}${manufacturersURL}/${manufacturer.id}/${editURL}")}'>
    					<h5 class="text-center list-group-element">${manufacturer.name}</h5>
    				</a>
  				</#list> 
			</div>
		</div>
		 
        <div id="main-manufacturer-list-div" class="col-lg-10 thumbnail">
			<#if (manufacturersPerPageData >= 1)>
				<div id="manufacturer-list-div" class="col-lg-12">	
					<ul class="grid preview">		
						<#list manufacturerEntities?chunk(2) as row>
							<div id="manufacturer-list-row" class="row">
								<#list row as manufacturer>
									<div class="col-lg-6 col-md-6 col-sm-12 preview-outer" id="${manufacturer.name}-div">	
										<#assign zIndex = (manufacturer_index + 1) * (row_index + 1)>
										<#--the Z-index of the elements on top must be higher than those below, threrfore the figure must be inverted -->		
										<#assign zIndex = zIndex + (manufacturerEntities?size - ((manufacturer_index + 1) * (row_index + 1)) - zIndex)>
										<#if manufacturer_index == 0>
											<#assign zIndex = (zIndex) - (1 * row_index)>
										</#if>					
										<@printManufacturerPreview manufacturer zIndex/>										
									</div>
								</#list>
							</div>
						</#list>
					</ul>
				</div>
				
				<#assign chunkedManufacturersList = manufacturerEntities?chunk(manufacturersPerPageData)>
				
				<div class="col-lg-12" style="margin-top: 55px;">
					<div class="<#if (chunkedManufacturersList?size < 2)>col-lg-3 col-md-3 col-sm-5 col-xs-6<#elseif (chunkedManufacturersList?size >= 3)>col-lg-7 col-md-7 col-sm-8 col-xs-10<#else>col-lg-6 col-md-6 col-sm-7 col-xs-8</#if> center-block well well-sm">
						<div id="pagination-row-div" class="row">
							<#if (chunkedManufacturersList?size > 1)>					
								<div class="text-left <#if (chunkedManufacturersList?size < 3)>col-lg-7<#else>col-lg-8</#if> col-md-7 col-sm-7 col-xs-12">									
									<ul id="pagination-ul"></ul>
								</div>
							</#if>
							<div class="<#if (chunkedManufacturersList?size == 1)>text-center<#else>text-right</#if> <#if (chunkedManufacturersList?size < 2)>col-lg-12 col-md-12 col-sm-12 col-xs-12<#else><#if (chunkedManufacturersList?size < 3)>col-lg-5<#else>col-lg-4</#if> col-md-5 col-sm-5 col-xs-12</#if>" style="height:56px; margin-bottom: 20px;">
								<button id="manufacturers-per-page-menu" class="btn btn-default dropdown-toggle" style="padding: 10px; margin-top: 15px" type="button" data-toggle="dropdown">
    								${language.getTextSource('pagination.manufacturersPerPage')}
    								<span class="caret"></span>
  								</button>
  								<ul class="dropdown-menu dropdown-menu-right" role="menu" aria-labelledby="manufacturers-per-page-menu">
									<li role="presentation"><a role="menuitem" href="${manufacturersURL}?${pagNum}=1&${manufacturersPerPage}=6">6</a></li>
    								<li role="presentation"><a role="menuitem" href="${manufacturersURL}?${pagNum}=1&${manufacturersPerPage}=12">12</a></li>
    								<li role="presentation"><a role="menuitem" href="${manufacturersURL}?${pagNum}=1&${manufacturersPerPage}=18">18</a></li>
    								<li role="presentation"><a role="menuitem" href="${manufacturersURL}?${pagNum}=1&${manufacturersPerPage}=24">24</a></li>
   									<hr>
	    							<li role="presentation"><a role="menuitem" href="${manufacturersURL}?${pagNum}=1&${manufacturersPerPage}=${manufacturerEntities?size}">${language.getTextSource('pagination.allManufacturers')}</a></li>
  								</ul>
  							</div>
  						</div>
  					</div>	
				</div>	
 			</#if>
		</div>
     </div>
</div>
<@generic.endPage/>

<#macro printManufacturerPreview manufacturer zIndex> 
	<div class="thumbnail preview-div">		
    	<li style="z-index: <#if zIndex??>${zIndex}<#else>1</#if>">
        	<figure>
				<div class="caption vertically-aligned-div vertically-aligned-preview-div">
               		<img class="img-thumbnail preview-img" src='<@spring.url "/${picturesURL}/${loadManufacturerLogoAction}?${id}=${manufacturer.id}"/>' alt="${manufacturer.name}">
				</div>
				<figcaption>
			 		<a href='${uriUtils.buildDomainURI("/${cmsContext}${manufacturersURL}/${manufacturer.id}/${editURL}")}' style="padding-bottom: 0px; padding-top: 0px;">
						<h3 class="text-center model-name">${manufacturer.name}</h3>
					</a>
				</figcaption>
			</figure>
    	</li>
	</div>
</#macro>  