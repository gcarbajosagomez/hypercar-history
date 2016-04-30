<#include "applicationMacros/genericFunctionalities.ftl">

<#if car??>
	<#assign title>${getTextSource('pagani')} ${car.model} ${getTextSource('car.details.dataAndPictures')}</#assign>
<#else>
	<#assign title>${getTextSource('title.noCarFound')}</#assign>
</#if>

<@startPage title/> 

<div id="main-container" class="container">
	<div class="panel panel-default main-panel row">
		<#if car??>
			<div class="panel-heading">
				 <h1 class="text-left">${car.manufacturer.name} ${car.model} (<@getCarProductionLife car/>)</h2>  
			</div>
			<div class="<#if pictureIds?? && (pictureIds?size > 0)>thumbnail vertically-aligned-div car-pictures-carousel-div</#if>">
				<#if pictureIds?? && (pictureIds?size > 0)>
					<div id="car-pictures-carousel" class="carousel slide center-block vertically-aligned-div" data-ride="carousel">
						<#-- Indicators -->
	  					<ol class="carousel-indicators">
  							<#list pictureIds as pictureId>
    							<li data-target="#car-pictures-carousel" data-slide-to="${pictureId_index}" class="<#if pictureId_index == 0> active </#if>"></li>
    						</#list>
	 					</ol>			
						<#-- Wrapper for slides -->
  						<div class="carousel-inner">    						
    						<#list pictureIds as pictureId>
    							<div class="item <#if pictureId_index == 0> active </#if>" id="pic-div-${pictureId}">
      								<a href="/${picturesURL}/${loadCarPictureAction}?${picId}=${pictureId}" title="${car.manufacturer.name} ${car.model}" data-gallery>
										<img src="/${picturesURL}/${loadCarPictureAction}?${picId}=${pictureId}" alt="${car.manufacturer.name} ${car.model}"> 
									</a> 														
    							</div>
	      					</#list>
		    			</div>					
				
						<#-- Controls -->
  						<a class="left carousel-control" href="#car-pictures-carousel" data-slide="prev">
    						<span class="glyphicon glyphicon-chevron-left"></span>
  						</a>
	  					<a class="right carousel-control" href="#car-pictures-carousel" data-slide="next">
    						<span class="glyphicon glyphicon-chevron-right"></span>
	  					</a>
  					</div>

					<@addBlueImpGallery/>
  				<#else>
  					<h2 class="text-center">${getTextSource('noPicturesAvailable')}</h2>
  				</#if>
			</div>
			<div id="main-car-details-div" class="panel-body col-lg-12">	
				<div class="panel panel-default">
					<div class="panel-body row">
						<div class="col-lg-6 col-sm-12">		
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="row">
										<h2 class="col-lg-8 col-md-6 col-sm-6 col-xs-12 text-left">${car.model}</h2>
										
										<div class="col-lg-4 col-md-6 col-sm-6 col-xs-12 dropdown" style="margin-top: 20px; margin-bottom: 10px; padding-left: 0px;">										
											<button class="btn btn-default dropdown-toggle" type="button" id="units-of-measure-dropdown" data-toggle="dropdown" aria-expanded="true">
												${getTextSource('unitsOfMeasure')} <span class="caret"></span>
  											</button>
          									<ul class="dropdown-menu dropdown-menu" role="menu" aria-labelledby="units-of-measure-dropdown"> 
          										<li role="presentation">
													<a class="cursor-pointer" role="menuitem" tabindex="-1" onClick="setUnitsOfMeasure('${unitsOfMeasureMetric}', $('#main-form')[0]);" style="padding-right: 0px; padding-left: 0px;">
          												<div class="row" style="margin: 0px;">
          											    	<div class="col-lg-7 col-md-7 col-sm-7 col-xs-7" style="padding-right: 0px;">									
	          													${getTextSource('unitsOfMeasure.metric')}		
															</div>
															<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5 center-block" style="padding-right: 0px; padding-top: 5px;">    										
          														<i id="metric-units-loading-gif" class="fa fa-circle-o-notch fa-lg fa-spin blue sr-only"></i>  
          													</div>
														</div>
													</a> 
												</li>
												<li class="divider"></li> 
          										<li role="presentation"> 
													<a class="cursor-pointer" role="menuitem" tabindex="-1" onClick="setUnitsOfMeasure('${unitsOfMeasureImperial}', $('#main-form')[0]);" style="padding-right: 0px; padding-left: 0px;">
														<div class="row" style="margin: 0px;">        								
          													<div class="col-lg-7 col-md-7 col-sm-7 col-xs-7" style="padding-right: 0px;">
																${getTextSource('unitsOfMeasure.imperial')} 
															</div>												
															<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5 center-block" style="padding-right: 0px; padding-top: 5px;">    										
          														<i id="imperial-units-loading-gif" class="fa fa-circle-o-notch fa-lg fa-spin blue sr-only"></i>  
          													</div> 
														</div>
													</a>
												</li>   							       
          									</ul>
          								</div>
          							</div>				 				
								</div>
								<div class="panel-body">
									<dl class="dl-horizontal text-left">
 			 							<dt>
 			 								${getTextSource('car.weight')} :
 		 								</dt>
	  									<dd>
  											<p class="text-muted">
  												<#if unitsOfMeasure == unitsOfMeasureMetric>	
													<@writeCarNumericData car.weight?default(-1)/>
													<#if car.weight??>			
														<em class="measure-unit-text">${getTextSource('Kg')}</em>	
													</#if>		
												<#elseif unitsOfMeasure == unitsOfMeasureImperial>
													<@writeNonDecimalCarNumericData (car.weight*2.20462)?default(-1)/>		
													<#if car.weight??>			
														<em class="measure-unit-text">${getTextSource('Lb')}</em>
													</#if>	
												</#if>
  											</p>
		  								</dd>
		  								<dt>
			  								${getTextSource('car.driveWheelType')} :
  										</dt>
  										<dd>
	  										<p class="text-muted">
  												<#if car.driveWheelType??>
  													${getTextSource('car.driveWheelType.${car.driveWheelType}')}
  												<#else>
													${getTextSource('undefined')}
												</#if>
		  									</p>
	  									</dd>
  										<dt>
											${getTextSource('car.bodyShape')} :
										</dt>
										<dd>
											<p class="text-muted">
												<#if car.bodyShape??>
													${getTextSource('car.bodyShape.${car.bodyShape}')}										
												</#if>
												<#if car.carSeatsConfig??>
													${getTextSource('car.seatsConfig.${car.carSeatsConfig}')?lower_case}											
												</#if>
											</p>
										</dd>
  										<dt class="double-height">
  											${getTextSource('car.acceleration')} :<p class="text-muted">(
  											<#if unitsOfMeasure == unitsOfMeasureMetric>	
													${getTextSource('0-100Km/h')}
											<#elseif unitsOfMeasure == unitsOfMeasureImperial>
													${getTextSource('0-62Mph')}
											</#if>
											)</p> 
										</dt>								
										<dd class="double-height">
											<p class="text-muted">
												<@writeCarNumericData car.acceleration?default(-1)/>
												<#if car.acceleration??>
													<em class="measure-unit-text">${getTextSource('S')}</em>
												</#if>
											</p>
										</dd>										
										<dt>
											${getTextSource('car.topSpeed')} :
										</dt>
										<dd>
											<p class="text-muted">
												<#if unitsOfMeasure == unitsOfMeasureMetric>	
													<@writeCarNumericData car.topSpeed?default(-1)/>	
													<#if car.topSpeed??>		
														<em class="measure-unit-text">${getTextSource('Km/h')}</em>
													</#if>		
												<#elseif unitsOfMeasure == unitsOfMeasureImperial>
													<@writeNonDecimalCarNumericData (car.topSpeed*0.621371)?default(-1)/>
													<#if car.topSpeed??>		
														<em class="measure-unit-text">${getTextSource('Mph')}</em>
													</#if>
												</#if>	
											</p>
										</dd>
										<dt>
		  									${getTextSource('car.engineLayout')} :
  										</dt>
  										<dd>
  											<p class="text-muted">
  												<#if car.engineLayout??>
  													${getTextSource('car.engineLayout.${car.engineLayout}')}
  												<#else>
													${getTextSource('undefined')}
												</#if>
				  							</p>
  										</dd>
  										<dt>
		  									${getTextSource('car.specificPower')} :
  										</dt>
  										<dd>
  											<p class="text-muted"> 
  												<#if car.weight??>
													<#if unitsOfMeasure == unitsOfMeasureMetric>	
														<@writeNonDecimalCarNumericData (car.engine.maxPower/(car.weight/1000))?default(-1)/><em class="measure-unit-text">${getTextSource('cvPerTonne')}</em>		
													<#elseif unitsOfMeasure == unitsOfMeasureImperial>
														<@writeNonDecimalCarNumericData ((car.engine.maxPower*0.9863)/(car.weight/1000))?default(-1)/><em class="measure-unit-text">${getTextSource('hpPerTonne')}</em>	
													</#if>						  											
  												<#else>
													${getTextSource('undefined')}
												</#if>
  											</p>
  										</dd>
										<dt class="double-height">
											${getTextSource('car.dimensions')} :<p class="text-muted">(${getTextSource('dimension.length')}/${getTextSource('dimension.width')}/${getTextSource('dimension.height')})</p>
										</dt>										
										<dd class="double-height">
											<p class="text-muted">
												<#if car.length??>
													${car.length}<em class="measure-unit-text">${getTextSource('MM')}</em>
												<#else>
													${getTextSource('undefined')}
												</#if>
													/
												<#if car.width??>
													${car.width}<em class="measure-unit-text">${getTextSource('MM')}</em>
												<#else>
													${getTextSource('undefined')}
												</#if>
													/
												<#if car.height??>
													${car.height}<em class="measure-unit-text">${getTextSource('MM')}</em>
												<#else>
													${getTextSource('undefined')}
												</#if>
											</p>
										</dd>										
										<dt class="double-height">
											${getTextSource('car.fuelConsumption')} :<p class="text-muted">(
  											<#if unitsOfMeasure == unitsOfMeasureMetric>	
													${getTextSource('L/100Km')}
											<#elseif unitsOfMeasure == unitsOfMeasureImperial>
													${getTextSource('MPG')}
											</#if>
											)</p> 
										</dt>
										<dd class="double-height">
											<p class="text-muted">
												<#if unitsOfMeasure == unitsOfMeasureMetric>	
													<@writeCarNumericData car.fuelConsumption?default(-1)/>
													<#if car.fuelConsumption??>
														<em class="measure-unit-text">${getTextSource('L/100Km')}</em>
													</#if>	
												<#elseif unitsOfMeasure == unitsOfMeasureImperial>
													<@writeNonDecimalCarNumericData (car.fuelConsumption/282.481)?default(-1)/>
													<#if car.fuelConsumption??>
														<em class="measure-unit-text">${getTextSource('MPG')}</em>
													</#if>	
												</#if>											
											</p>
										</dd>										
										<dt>
											${getTextSource('car.fuelTankCapacity')} :
										</dt>
										<dd>
											<p class="text-muted">
												<#if unitsOfMeasure == unitsOfMeasureMetric>	
													<@writeCarNumericData car.fuelTankCapacity?default(-1)/>
													<#if car.fuelTankCapacity??>
														<em class="measure-unit-text">${getTextSource('L')}</em>
													</#if>
												<#elseif unitsOfMeasure == unitsOfMeasureImperial>
													<@writeNonDecimalCarNumericData (car.fuelTankCapacity*0.219969)?default(-1)/>
													<#if car.fuelTankCapacity??>
														<em class="measure-unit-text">${getTextSource('Gal')}</em>
													</#if>
												</#if>												
											</p>
										</dd>
										<dt>
											${getTextSource('car.autonomy')} :
										</dt>
										<dd>
											<p class="text-muted">
												<#if unitsOfMeasure == unitsOfMeasureMetric>	
													<#if car.fuelConsumption?? && car.fuelTankCapacity??>													
														<@writeNonDecimalCarNumericData ((car.fuelTankCapacity)/(car.fuelConsumption)*100)?default(-1)/><em class="measure-unit-text">${getTextSource('Km')}</em>
													<#else>
														${getTextSource('undefined')}
													</#if>
												<#elseif unitsOfMeasure == unitsOfMeasureImperial>
													<#if car.fuelConsumption?? && car.fuelTankCapacity??>													
														<@writeNonDecimalCarNumericData ((car.fuelTankCapacity*0.219969)/(car.fuelConsumption/282.481)*100)?default(-1)/><em class="measure-unit-text">${getTextSource('Miles')}</em>
													<#else>
														${getTextSource('undefined')}
													</#if>
												</#if>												
											</p>
										</dd>										
									</dl>
								</div>	
							</div>
						</div>
						<div class="col-lg-6 col-sm-12">	
							<div class="panel panel-default">
								<div class="panel-heading" style="min-height: 84px">
									<h3 class="text-left car-details-panel-heading">${getTextSource('engine')}<em class="text-muted"> (${car.engine.code})</em></h3>
								</div>
								<div class="panel-body">
									<dl class="dl-horizontal text-left">
										<dt>
  											${getTextSource('engine.displacement')} :
			  							</dt>
  										<dd>
  											<#if car.engine.size??>
  												<p class="text-muted">  	
  													${(car.engine.size/1000)?string("0.#")} <em class="measure-unit-text">${getTextSource('L')}</em> (${car.engine.size} <em class="measure-unit-text">${getTextSource('CM3')}</em>)
  												</p>
	 		 								</#if>
  										</dd>
  										<dt>
  											${getTextSource('engine.type')} :
			  							</dt>
  										<dd>
  											<p class="text-muted">  												
  												<#if car.engine.type??>
  													${getTextSource('engine.type.${car.engine.type}')}
  												</#if>
  												<#if car.engine.cylinderDisposition?? && car.engine.numberOfCylinders??>
		  											${car.engine.cylinderDisposition}${car.engine.numberOfCylinders}
	  											</#if>
		  									</p>
	  									</dd>
  										<dt>
  											${getTextSource('engine.numberOfValves')} :
  										</dt>	
	 		 							<dd>
  											<p class="text-muted">
  												<@writeCarNumericData car.engine.numberOfValves?default(-1)/>
  												<#if car.engine.numberOfValves??>
  													 (${car.engine.numberOfValves/car.engine.numberOfCylinders} ${getTextSource('engine.valvesPerCylinder')})
 		 										</#if>
	  										</p>
  										</dd>
  										<dt>
		  									${getTextSource('engine.maxPower')} :
	  									</dt>
  										<dd>
  											<p class="text-muted">
  												<#if unitsOfMeasure == unitsOfMeasureMetric>	
													<@writeCarNumericData car.engine.maxPower?default(-1)/>
		  											<#if car.engine.maxPower??>
		  												<em class="measure-unit-text">${getTextSource('CV')}</em>
		  											</#if>
												<#elseif unitsOfMeasure == unitsOfMeasureImperial>
													<@writeNonDecimalCarNumericData (car.engine.maxPower*0.9863)?default(-1)/>
		  											<#if car.engine.maxPower??>
		  												<em class="measure-unit-text">${getTextSource('HP')}</em>
		  											</#if>
												</#if>												
  												<#if car.engine.maxPower??>	
  												    (<@writeNonDecimalCarNumericData (car.engine.maxPower*0.736)?default(-1)/><em class="measure-unit-text">${getTextSource('KW')}</em>)			
													<#if car.engine.maxPowerRPM??>
  														<b>@</b> <@writeCarNumericData car.engine.maxPowerRPM?default(-1)/><em class="measure-unit-text">${getTextSource('RPM')}</em>
  													</#if>
  												</#if>						  										
	  										</p>
  										</dd>
	  									<dt>
				  							${getTextSource('engine.maxTorque')} :
  										</dt>
  										<dd>
  											<p class="text-muted">  
												<#if unitsOfMeasure == unitsOfMeasureMetric>	
													<@writeCarNumericData car.engine.maxTorque?default(-1)/>
		  											<#if car.engine.maxTorque??>
		  												<em class="measure-unit-text">${getTextSource('NM')}</em>
		  											</#if>
  													<#if car.engine.maxTorqueRPM??>
  														<b>@</b> <@writeCarNumericData car.engine.maxTorqueRPM?default(-1)/><em class="measure-unit-text">${getTextSource('RPM')}</em>
  													</#if>	
												<#elseif unitsOfMeasure == unitsOfMeasureImperial>
													<@writeNonDecimalCarNumericData (car.engine.maxTorque*0.737562149)?default(-1)/>
		  											<#if car.engine.maxTorque??>
		  												<em class="measure-unit-text">${getTextSource('LbFt')}</em>
		  											</#if>
  													<#if car.engine.maxTorqueRPM??>
  														<b>@</b> <@writeCarNumericData car.engine.maxTorqueRPM?default(-1)/><em class="measure-unit-text">${getTextSource('RPM')}</em>
  													</#if>	
												</#if>							  									
		  									</p>
  										</dd>
  										<dt>
		  									${getTextSource('engine.maxRPM')} :
  										</dt>
  										<dd>
  											<p class="text-muted">
  												<@writeCarNumericData car.engine.maxRPM?default(-1)/>
		  										<#if car.engine.maxRPM??>
  													 <em class="measure-unit-text">${getTextSource('RPM')}</em>
  												</#if>
				  							</p>
  										</dd>
  										<dt>
		  									${getTextSource('engine.specificOutput')} :
  										</dt>
  										<dd>
  											<p class="text-muted"> 												
		  										<#if unitsOfMeasure == unitsOfMeasureMetric>	
													<@writeNonDecimalCarNumericData (car.engine.maxPower/(car.engine.size/1000))?default(-1)/><em class="measure-unit-text">${getTextSource('cvPerLitre')}</em>	 
												<#elseif unitsOfMeasure == unitsOfMeasureImperial>
													<@writeNonDecimalCarNumericData ((car.engine.maxPower*0.9863)/(car.engine.size/1000))?default(-1)/><em class="measure-unit-text">${getTextSource('hpPerLitre')}</em>	 
												</#if>	 											
  											</p>
  										</dd>  										
  									</dl>
  								</div>
  							</div>
	  					</div>
	  				</div>
	  			</div>
	  			<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="text-left car-details-panel-heading">${getTextSource('brakes')}</h3>
					</div>
					<div class="panel-body row">
						<div class="col-lg-1"></div>
						<div class="col-lg-5 col-sm-12 col-xs-12">		
							<#if (car.brakeSet??) && (car.brakeSet.frontBrake??)>
								<@writeCarBrakeInfo car.brakeSet.frontBrake/>
							</#if>
						</div>
						<div class="col-lg-5 col-sm-12 col-xs-12">
							<#if (car.brakeSet??) && (car.brakeSet.backBrake??)>
                				<@writeCarBrakeInfo car.brakeSet.backBrake/>
                			</#if>
	                	</div>
    	            </div>
  				</div>
  				<div class="panel panel-default">
					<div class="panel-body row">
						<div class="col-lg-1"></div>
						<div class="col-lg-5 col-sm-12 col-xs-12">
	  						<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="text-left car-details-panel-heading">${getTextSource('transmission')}</h3>
								</div>
								<div class="panel-body">
									<dl class="dl-horizontal text-left">
  										<dt>
  											${getTextSource('transmission.type')} :
  										</dt>
			  							<dd>
  											 <#if (car.transmission??) && (car.transmission.type??)>
  											 	<p class="text-muted">
  											 		${getTextSource('transmission.type.${car.transmission.type}')}
 		 									 	</p>
	  										 </#if>
  										</dd>
				  						<dt>
  											${getTextSource('transmission.numOfGears')} :
  										</dt>
  										<dd>
		  									<p class="text-muted">
		  										<#if car.transmission??>
  													<@writeCarNumericData car.transmission.numOfGears?default(-1)/>
  												</#if>
  											</p>
				  						</dd>
  									</dl>
  								</div>
 		 					</div>
	  					</div>
	  					<div class="col-lg-5 col-sm-12 col-xs-12">
			  				<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="text-left car-details-panel-heading">${getTextSource('tyres')}</h3>
								</div>
								<div class="panel-body">
									<dl class="dl-horizontal text-left">
			  							<dt>
  											${getTextSource('tyreSet.frontTyre')} :
  										</dt>
  										<dd>
		  									 <p class="text-muted">
  											 	<#if (car.tyreSet??) && (car.tyreSet.frontTyre??)>
  											 		<@writeCarNumericData car.tyreSet.frontTyre.width?default(-1)/>/<@writeCarNumericData car.tyreSet.frontTyre.profile?default(-1)/>/ R<@writeCarNumericData car.tyreSet.frontTyre.rimDiameter?default(-1)/>
  										 		</#if>
	  										 </p>
				  						</dd>
  										<dt>
  											${getTextSource('tyreSet.backTyre')} :
  										</dt>
 		 								<dd>
  											<p class="text-muted">
  												<#if (car.tyreSet??) && (car.tyreSet.backTyre??)>
  													<@writeCarNumericData car.tyreSet.backTyre.width?default(-1)/>/<@writeCarNumericData car.tyreSet.backTyre.profile?default(-1)/>/ R<@writeCarNumericData car.tyreSet.backTyre.rimDiameter?default(-1)/>
  												</#if>
  											</p>
				  						</dd>
  									</dl>
  								</div>
  							</div>
  						</div>
	 	 			</div>
	  			</div>
			</div>
		<#else>
			<div class="panel panel-body col-lg-12">
				<p class="col-lg-12 text-muted text-center">${getTextSource('car.noCarFound')}</p>
			</div>			
		</#if>		
	</div>
</div>

<@endPage/>

<#macro writeCarNumericData data>
    <#if data != -1>
        ${data?string["0.#"]}
    <#else>
        ${getTextSource('undefined')}
    </#if>
</#macro>

<#macro writeNonDecimalCarNumericData data>
    <#if data != -1>
        ${data?string["0"]}
    <#else>
        ${getTextSource('undefined')}
    </#if>
</#macro>

<#macro writeCarStringData data>
    <#if data != "">
        ${data}
    <#else>
        ${getTextSource('undefined')}
    </#if>
</#macro>

<#macro writeCarBrakeInfo brake>	
	<div class="panel panel-default">
		<div class="panel-heading">
			<#if brake.train = "FRONT">
				<h3 class="text-left car-details-panel-heading">${getTextSource('brakeSet.front')}</h3>
			<#else>
				<h3 class="text-left car-details-panel-heading">${getTextSource('brakeSet.back')}</h3>
			</#if>			
		</div>
		<div class="panel-body">
			<dl class="dl-horizontal text-left">
  				<dt>
  					${getTextSource('brake.disc.diameter')} :		
  				</dt>
  				<dd>
  					<p class="text-muted">
  						<@writeCarNumericData brake.discDiameter?default(-1)/>
  						<#if brake.discDiameter??>
  							<em class="measure-unit-text">${getTextSource('MM')}</em>
  						</#if>
  					</p>
  				</dd>
  				<dt>
  					${getTextSource('brake.disc.material')} :		
  				</dt>
  				<dd>
  					<#if brake.discMaterial??>
  						<p class="text-muted">
  							${getTextSource('brake.disc.material.${brake.discMaterial}')}
  						</p>
  					</#if>
  				</dd>
  				<dt>
  					${getTextSource('brake.caliper.numOfPistons')} :		
  				</dt>
  				<dd>
  					<p class="text-muted">
  						<@writeCarNumericData brake.caliperNumOfPistons?default(-1)/>
  					</p>
  				</dd>
  			</dl>
  		</div>
  	</div>
</#macro>

<script type="text/javascript">

function setUnitsOfMeasure(unitsOfMeasure, mainForm)
{
	if ($.cookie('${unitsOfMeasureCookieName}') != unitsOfMeasure && !ajaxCallBeingProcessed)
	{
		$.cookie('${unitsOfMeasureCookieName}', unitsOfMeasure, {path : '/', expires : 14});
		ajaxCallBeingProcessed = true;
	
		$.ajax({
             	type:'GET',
             	url: mainForm.action,            
             	dataType: 'html',
				beforeSend: function()
    	    	{
        	    	if(unitsOfMeasure == '${unitsOfMeasureMetric}')
            		{
	                	$('#metric-units-loading-gif').removeClass('sr-only');
    	        	}
        	    	else if(unitsOfMeasure == '${unitsOfMeasureImperial}')
            		{
                		$('#imperial-units-loading-gif').removeClass('sr-only');
	            	}

					$('#main-car-details-div').block({ 
						css: {         										
        						border:         '0px solid', 
        						backgroundColor:'rgba(94, 92, 92, 0)'
    						 },
                		message: '<i id="metric-units-loading-gif" class="fa fa-circle-o-notch fa-4x fa-spin blue"></i>' 
            		});
   				}
   		})
    	.done(function(data)
    	{
        	document.body.innerHTML = data;
			ajaxCallBeingProcessed = false; 
			$('#main-car-details-div').unblock();  
    	});
    }
}

$(function()
{
	$('.carousel').carousel({
  		interval: 8000
	});
	
	$('.modal').on('show.bs.modal', function (e)
	{
  		$('.carousel').carousel('pause')
	});
	
	$('.modal').on('hidden.bs.modal', function (e)
	{
  		$('.carousel').carousel({
  			interval: 8000
		});
	});
})	
</script>