<#import "applicationMacros/genericFunctionalities.ftl" as generic/>
<#import "applicationMacros/pageLanguage.ftl" as language/>

<@generic.startPage language.getTextSource('title.index')/>

<div id="main-container" class="container">
	<div class="row">
		<div id="index-jumbotron" class="jumbotron">
			<div class="thumbnail vertically-aligned-div car-pictures-carousel-div">
				<#if (carNamesToPictureIds?size > 0)>
					<div id="car-pictures-carousel" class="carousel slide center-block vertically-aligned-div" data-ride="carousel">
						<#-- Indicators -->
	  					<ol class="carousel-indicators">
                            <#assign pictureIndex = 0>
  							<#list carNamesToPictureIds?keys as carName>
                                <#list carNamesToPictureIds[carName] as pictureId>
    							    <li data-target="#car-pictures-carousel" data-slide-to="${pictureIndex}" class="<#if pictureIndex == 0>active</#if>"></li>
                                    <#assign pictureIndex++>
                                </#list>
    						</#list>
	 					</ol>
						<#-- Wrapper for slides -->
  						<div class="carousel-inner">
    						<#list carNamesToPictureIds?keys as carName>
    						    <#list carNamesToPictureIds[carName] as pictureId>
    							    <div id="pic-div-${pictureId}" class="item <#if carName_index == 0 && pictureId_index == 0> active </#if>">
      								    <img src="${picturesURL}/${loadCarPictureAction}?${picId}=${pictureId}" alt="${carName}">
      								    <div class="carousel-caption">${carName}</div>
		      					    </div>
    		  				    </#list>
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
<@generic.endPage/>

<script type="text/javascript">

$(function() {
	$('.carousel').carousel({
  		interval: 8000
	});
})
	
</script>