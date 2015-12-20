<#include "applicationMacros/genericFunctionalities.ftl">

<@startPage getTextSource('title.index')/> 

<div id="main-container" class="container">
	<div class="row">
		<div id="index-jumbotron" class="jumbotron"> 			
			<div class="<#if carNamesToPictureIds?? && (carNamesToPictureIds?size > 0)>thumbnail vertically-aligned-div car-pictures-carousel-div</#if>">
				<#if carNamesToPictureIds?? && (carNamesToPictureIds?size > 0)>
					<div id="car-pictures-carousel" class="carousel slide center-block vertically-aligned-div" data-ride="carousel">
						<#-- Indicators -->
	  					<ol class="carousel-indicators">
  							<#list carNamesToPictureIds?keys as carName>
    							<li data-target="#car-pictures-carousel" data-slide-to="${carName_index}" class="<#if carName_index == 0> active </#if>"></li>
    						</#list>
	 					</ol>						
						<#-- Wrapper for slides -->
  						<div class="carousel-inner">    						
    						<#list carNamesToPictureIds?keys as carName>
    							<div class="item <#if carName_index == 0> active </#if>" id="pic-div-${carNamesToPictureIds[carName]}">
      								<img src="${pictureURL}?${action}=${loadCarPictureAction}&${picId}=${carNamesToPictureIds[carName]}" alt="${carName}" >
      								<div class="carousel-caption">
        								${carName}		
	      							</div>
		      					</div>
    		  				</#list>
      					</div>
      					<#-- Controls -->
  						<a id="left-arrow-control" class="left carousel-control" href="#car-pictures-carousel" data-slide="prev">
    						<span class="glyphicon glyphicon-chevron-left"></span>
	  					</a>
  						<a id="right-arrow-control" class="right carousel-control" href="#car-pictures-carousel" data-slide="next">
    						<span class="glyphicon glyphicon-chevron-right"></span>
	  					</a>
      				</div>
 	     		</#if>   
			</div> 
		</div>
	</div>
</div>
<@endPage/> 

<script type="text/javascript">

$(function() {
	$('.carousel').carousel({
  		interval: 8000
	});
})
	
</script>