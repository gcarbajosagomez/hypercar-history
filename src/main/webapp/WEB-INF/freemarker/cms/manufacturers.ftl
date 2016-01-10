<#include "../applicationMacros/genericFunctionalities.ftl">

<@startPage getTextSource('title.allManufacturers')/>   

<div id="main-container" class="container panel panel-default main-container main-panel"> 
     <div class="row">
        <div class="col-lg-2">
			<div class="thumbnail list-group">
				<#list manufacturers as manufacturer>
    				<a class="list-group-item" href='<@spring.url "/${cmsContext}${manufacturersURL}/${editURL}?${manufacturerId}=${manufacturer.id}"/>'>
    					<h5 class="text-center list-group-element">${manufacturer.name}</h5>
    				</a>
  				</#list> 
			</div>
		</div>
		 
        <div id="main-manufacturer-list-div" class="col-lg-10 thumbnail">
			<#if (manufacturersPerPageData >= 1)>
				<div id="manufacturer-list-div" class="col-lg-12">	
					<ul class="grid preview">		
						<#list manufacturers?chunk(2) as row>	
							<div id="manufacturer-list-row" class="row">
								<#list row as manufacturer>
									<div class="col-lg-6 col-md-6 col-sm-12 preview-outer" id="${manufacturer.name}-div">	
										<#assign zIndex = (manufacturer_index + 1) * (row_index + 1)>
										<#--the Z-index of the elements on top must be higher than those below, threrfore the figure must be inverted -->		
										<#assign zIndex = zIndex + (manufacturers?size - ((manufacturer_index + 1) * (row_index + 1)) - zIndex)>		
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
				
				<#assign chunkedManufacturersList = manufacturers?chunk(manufacturersPerPageData)>
				
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
    								${getTextSource('pagination.manufacturersPerPage')}
    								<span class="caret"></span>
  								</button>
  								<ul class="dropdown-menu dropdown-menu-right" role="menu" aria-labelledby="manufacturers-per-page-menu">
									<li role="presentation"><a role="menuitem" href="${manufacturersURL}?${pagNum}=1&${manufacturersPerPage}=5">5</a></li>
    								<li role="presentation"><a role="menuitem" href="${manufacturersURL}?${pagNum}=1&${manufacturersPerPage}=10">10</a></li>
    								<li role="presentation"><a role="menuitem" href="${manufacturersURL}?${pagNum}=1&${manufacturersPerPage}=15">15</a></li>
    								<li role="presentation"><a role="menuitem" href="${manufacturersURL}?${pagNum}=1&${manufacturersPerPage}=20">20</a></li>
   									<li role="presentation" class="divider"></li>
	    							<li role="presentation"><a role="menuitem" href="${manufacturersURL}?${pagNum}=1&${manufacturersPerPage}=${manufacturers?size}">${getTextSource('pagination.allManufacturers')}</a></li>
  								</ul>
  							</div>
  						</div>
  					</div>	
				</div>	
 			</#if>
		</div>
     </div>
</div>
<@endPage/>

<#macro printManufacturerPreview manufacturer zIndex> 
	<div class="thumbnail preview-div">		
    	<li style="z-index: <#if zIndex??>${zIndex}<#else>1</#if>">
        	<figure>
				<div class="caption vertically-aligned-div vertically-aligned-preview-div">
               		<img class="img-thumbnail preview-img" src='<@spring.url "/${picturesURL}/${loadManufacturerLogoAction}?${manufacturerId}=${manufacturer.id}"/>' alt="${manufacturer.name}">
				</div>
				<figcaption>
			 		<a href='<@spring.url "/${cmsContext}${manufacturersURL}/${editURL}?${manufacturerId}=${manufacturer.id}"/>' style="padding-bottom: 0px; padding-top: 0px;">
						<h3 class="text-center model-name">${manufacturer.name}</h3>
					</a>
				</figcaption>
			</figure>
    	</li>
	</div>
</#macro>  