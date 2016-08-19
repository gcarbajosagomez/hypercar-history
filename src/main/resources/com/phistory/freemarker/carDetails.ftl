<#import "applicationMacros/genericFunctionalities.ftl" as generic/>
<#import "applicationMacros/pageLanguage.ftl" as language/>
<#import "applicationMacros/picture.ftl" as picture/>

<#if car??>
	<#assign title>${language.getTextSource('pagani')} ${car.model} ${language.getTextSource('car.details.dataAndPictures')?lower_case}</#assign>
<#else>
	<#assign title>${language.getTextSource('title.noCarFound')}</#assign>
</#if>

<@generic.startPage title/> 

<div id="main-container" class="container">
	<div class="panel panel-default main-panel row" style="border:0px;">
		<#if car??>
			<div class="panel-heading">
				 <h1 class="text-left">${car.manufacturer.name}<br/> ${car.model} (${generic.getCarProductionLifeTime (car)})</h1>
			</div>			
			<#if youtubeVideoIds?? && (youtubeVideoIds?size > 0)>
				<#assign youtubeVideosPresent = true>						
			<#else>
				<#assign youtubeVideosPresent = false>
			</#if>
			<div class="<#if pictureIds?? && (pictureIds?size > 0)>thumbnail vertically-aligned-div car-pictures-carousel-div</#if>">
				<#if pictureIds?? && (pictureIds?size > 0)>
					<div id="car-pictures-carousel" class="carousel slide center-block vertically-aligned-div container" data-ride="carousel">
						<#-- Indicators -->
	  					<ol class="carousel-indicators">
  							<#list pictureIds as pictureId>
    							<li data-target="#car-pictures-carousel" data-slide-to="${pictureId?index}" class="<#if pictureId?is_first>active</#if>"></li>
    						</#list>
	 					</ol>
						<#-- Wrapper for slides -->
  						<div class="carousel-inner">    						
    						<#list pictureIds as pictureId>
    							<div id="pic-div-${pictureId}" class="item <#if pictureId?is_first>active</#if>">
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
					<#if youtubeVideosPresent == true>
  						<div id="car-videos-carousel" class="carousel slide center-block vertically-aligned-div hidden container" data-ride="carousel"  style="height: 100%;">  						
							<ol class="carousel-indicators">
  								<#list youtubeVideoIds as videoId>
    								<li data-target="#car-videos-carousel" data-slide-to="${videoId?index}" class="<#if videoId?is_first>active</#if>"></li>
    							</#list>
	 						</ol>
							<div class="carousel-inner">							
	    						<#list youtubeVideoIds as videoId>
									<div id="${videoId}-video-div" class="item <#if videoId?is_first>active</#if>">
	    								<div id="${videoId}-iframe-div" class="background"></div>	
	    							</div>			
		      					</#list>
				    		</div>				    			
							<#-- Controls -->
	  						<a class="left carousel-control" href="#car-videos-carousel" data-slide="prev" style="height: 50%; top: 25%;">
	    						<span class="glyphicon glyphicon-chevron-left"></span>
	  						</a>
		  					<a class="right carousel-control" href="#car-videos-carousel" data-slide="next" style="height: 50%; top: 25%;">
	    						<span class="glyphicon glyphicon-chevron-right"></span>
		  					</a>
						</div>	
					</#if>
					<@picture.addBlueImpGallery/>
  				<#else>
  					<h2 class="text-center">${language.getTextSource('noPicturesAvailable')}</h2>
  				</#if>
			</div>
			<#if youtubeVideosPresent = true>
				<ul class="nav nav-tabs">
					<li id="show-pictures-tab" class="active cursor-pointer" role="presentation"><a onClick="hideOrShowPictures(true)">${language.getTextSource('car.pictures')}</a></li>
					<li id="show-videos-tab"  class="cursor-pointer" role="presentation"><a onClick="hideOrShowPictures(false)">${language.getTextSource('car.videos')}</a></li>
				</ul>
			</#if>
			<div id="main-car-details-div" class="panel-body col-lg-12">	
				<div class="panel panel-default">
					<div class="panel-body row">
						<div class="col-lg-6 col-sm-12">						
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="row"> 
										<h2 class="col-lg-12 text-left">${car.model}</h2>
										
										<div class="col-lg-12 dropdown">
											<button id="units-of-measure-dropdown" class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="true">
												${language.getTextSource('unitsOfMeasure')} <span class="caret"></span>
  											</button>
          									<ul id="units-of-measure-dropdown-menu" class="dropdown-menu dropdown-menu" role="menu" aria-labelledby="units-of-measure-dropdown">
          										<li role="presentation">
													<a class="cursor-pointer" role="menuitem" tabindex="-1" onClick="setUnitsOfMeasure('${unitsOfMeasureMetric}', $('#main-form')[0]);" style="padding-right: 0px; padding-left: 0px;">
          												<div class="row" style="margin: 0px;">
          											    	<div class="col-lg-7 col-md-7 col-sm-7 col-xs-7" style="padding-right: 0px;">									
	          													${language.getTextSource('unitsOfMeasure.metric')}		
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
																${language.getTextSource('unitsOfMeasure.imperial')} 
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
		  									${language.getTextSource('car.productionType')} :
  										</dt>
  										<dd>
  											<p class="text-muted">  										
  												${language.getTextSource('car.productionType.${car.productionType.getName()}')}  												
				  							</p>
  										</dd>
		  								<dt>
			  								${language.getTextSource('car.driveWheelType')} :
  										</dt>
  										<dd>
	  										<p class="text-muted">
  												<#if car.driveWheelType??>
  													${language.getTextSource('car.driveWheelType.${car.driveWheelType}')}
  												<#else>
													${language.getTextSource('undefined')}
												</#if>
		  									</p>
	  									</dd>
  										<dt>
											${language.getTextSource('car.bodyShape')} :
										</dt>
										<dd>
											<p class="text-muted">
												<#if car.carSeatsConfig??>
													${language.getTextSource('car.seatsConfig.${car.carSeatsConfig}')}
												</#if>
												<#if car.bodyShape??>
													${language.getTextSource('car.bodyShape.${car.bodyShape}')?lower_case}
												</#if>
											</p>
										</dd>										
  										<dt>
											${language.getTextSource('car.roadLegal')} :
										</dt>
										<dd>
											<p class="text-muted">
												<#if car.roadLegal == true>
													${language.getTextSource('yes')}	
												<#else>		
													${language.getTextSource('no')}								
												</#if>
											</p>
										</dd>
  										<dt class="double-height">
  											${language.getTextSource('car.acceleration')} :<p class="text-muted">(
  											<#if unitsOfMeasure == unitsOfMeasureMetric>	
													${language.getTextSource('0-100Km/h')}
											<#elseif unitsOfMeasure == unitsOfMeasureImperial>
													${language.getTextSource('0-62Mph')}
											</#if>
											)</p> 
										</dt>								
										<dd class="double-height">
											<p class="text-muted">
												<@writeCarNumericData car.acceleration?default(-1)/>
												<#if car.acceleration??>
													<em class="measure-unit-text">${language.getTextSource('S')}</em>
												</#if>
											</p>
										</dd>										
										<dt>
											${language.getTextSource('car.topSpeed')} :
										</dt>
										<dd>
											<p class="text-muted">
												<#if unitsOfMeasure == unitsOfMeasureMetric>	
													<@writeCarNumericData car.topSpeed?default(-1)/>	
													<#if car.topSpeed??>		
														<em class="measure-unit-text">${language.getTextSource('Km/h')}</em>
													</#if>		
												<#elseif unitsOfMeasure == unitsOfMeasureImperial>
													<@writeNonDecimalCarNumericData (car.topSpeed*0.621371)?default(-1)/>
													<#if car.topSpeed??>		
														<em class="measure-unit-text">${language.getTextSource('Mph')}</em>
													</#if>
												</#if>	
											</p>
										</dd>
										<dt>
		  									${language.getTextSource('car.engineLayout')} :
  										</dt>
  										<dd>
  											<p class="text-muted">
  												<#if car.engineLayout??>
  													${language.getTextSource('car.engineLayout.${car.engineLayout}')}
  												<#else>
													${language.getTextSource('undefined')}
												</#if>
				  							</p>
  										</dd>
  										<dt>
		  									${language.getTextSource('car.specificPower')} :
  										</dt>
  										<dd>
  											<p class="text-muted"> 
  												<#if car.weight??>
													<#if unitsOfMeasure == unitsOfMeasureMetric>	
														<@writeNonDecimalCarNumericData (car.engine.maxPower/(car.weight/1000))?default(-1)/><em class="measure-unit-text">${language.getTextSource('cvPerTonne')}</em>		
													<#elseif unitsOfMeasure == unitsOfMeasureImperial>
														<@writeNonDecimalCarNumericData ((car.engine.maxPower*0.9863)/(car.weight/1000))?default(-1)/><em class="measure-unit-text">${language.getTextSource('hpPerTonne')}</em>	
													</#if>						  											
  												<#else>
													${language.getTextSource('undefined')}
												</#if>
  											</p>
  										</dd>
 			 							<dt>
 			 								${language.getTextSource('car.weight')} :
 		 								</dt>
	  									<dd>
  											<p class="text-muted">
  												<#if unitsOfMeasure == unitsOfMeasureMetric>	
													<@writeCarNumericData car.weight?default(-1)/>
													<#if car.weight??>			
														<em class="measure-unit-text">${language.getTextSource('Kg')}</em>	
													</#if>		
												<#elseif unitsOfMeasure == unitsOfMeasureImperial>
													<@writeNonDecimalCarNumericData (car.weight*2.20462)?default(-1)/>		
													<#if car.weight??>			
														<em class="measure-unit-text">${language.getTextSource('Lb')}</em>
													</#if>	
												</#if>
  											</p>
		  								</dd>
										<dt class="double-height">
											${language.getTextSource('car.dimensions')} :<p class="text-muted">(${language.getTextSource('dimension.length')}/${language.getTextSource('dimension.width')}/${language.getTextSource('dimension.height')})</p>
										</dt>										
										<dd class="double-height">
											<p class="text-muted">
												<#if car.length??>
													${car.length}<em class="measure-unit-text">${language.getTextSource('MM')}</em>
												<#else>
													${language.getTextSource('undefined')}
												</#if>
													/
												<#if car.width??>
													${car.width}<em class="measure-unit-text">${language.getTextSource('MM')}</em>
												<#else>
													${language.getTextSource('undefined')}
												</#if>
													/
												<#if car.height??>
													${car.height}<em class="measure-unit-text">${language.getTextSource('MM')}</em>
												<#else>
													${language.getTextSource('undefined')}
												</#if>
											</p>
										</dd>										
										<dt class="double-height">
											${language.getTextSource('car.fuelConsumption')} :<p class="text-muted">(
  											<#if unitsOfMeasure == unitsOfMeasureMetric>	
													${language.getTextSource('L/100Km')}
											<#elseif unitsOfMeasure == unitsOfMeasureImperial>
													${language.getTextSource('MPG')}
											</#if>
											)</p> 
										</dt>
										<dd class="double-height">
											<p class="text-muted">
												<#if unitsOfMeasure == unitsOfMeasureMetric>	
													<@writeCarNumericData car.fuelConsumption?default(-1)/>
													<#if car.fuelConsumption??>
														<em class="measure-unit-text">${language.getTextSource('L/100Km')}</em>
													</#if>	
												<#elseif unitsOfMeasure == unitsOfMeasureImperial>
													<@writeNonDecimalCarNumericData (car.fuelConsumption/282.481)?default(-1)/>
													<#if car.fuelConsumption??>
														<em class="measure-unit-text">${language.getTextSource('MPG')}</em>
													</#if>	
												</#if>											
											</p>
										</dd>										
										<dt>
											${language.getTextSource('car.fuelTankCapacity')} :
										</dt>
										<dd>
											<p class="text-muted">
												<#if unitsOfMeasure == unitsOfMeasureMetric>	
													<@writeCarNumericData car.fuelTankCapacity?default(-1)/>
													<#if car.fuelTankCapacity??>
														<em class="measure-unit-text">${language.getTextSource('L')}</em>
													</#if>
												<#elseif unitsOfMeasure == unitsOfMeasureImperial>
													<@writeNonDecimalCarNumericData (car.fuelTankCapacity*0.219969)?default(-1)/>
													<#if car.fuelTankCapacity??>
														<em class="measure-unit-text">${language.getTextSource('Gal')}</em>
													</#if>
												</#if>												
											</p>
										</dd>
										<dt>
											${language.getTextSource('car.autonomy')} :
										</dt>
										<dd>
											<p class="text-muted">
												<#if unitsOfMeasure == unitsOfMeasureMetric>	
													<#if car.fuelConsumption?? && car.fuelTankCapacity??>													
														<@writeNonDecimalCarNumericData ((car.fuelTankCapacity)/(car.fuelConsumption)*100)?default(-1)/><em class="measure-unit-text">${language.getTextSource('Km')}</em>
													<#else>
														${language.getTextSource('undefined')}
													</#if>
												<#elseif unitsOfMeasure == unitsOfMeasureImperial>
													<#if car.fuelConsumption?? && car.fuelTankCapacity??>													
														<@writeNonDecimalCarNumericData ((car.fuelTankCapacity*0.219969)/(car.fuelConsumption/282.481)*100)?default(-1)/><em class="measure-unit-text">${language.getTextSource('Miles')}</em>
													<#else>
														${language.getTextSource('undefined')}
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
									<h3 class="text-left car-details-panel-heading">${language.getTextSource('engine')}<em class="text-muted"> (${car.engine.code})</em></h3>
								</div>
								<div class="panel-body">
									<dl class="dl-horizontal text-left">
										<dt>
  											${language.getTextSource('engine.displacement')} :
			  							</dt>
  										<dd>
  											<#if car.engine.size??>
  												<p class="text-muted">  	
  													${(car.engine.size/1000)?string("0.#")} <em class="measure-unit-text">${language.getTextSource('L')}</em> (${car.engine.size} <em class="measure-unit-text">${language.getTextSource('CM3')}</em>)
  												</p>
	 		 								</#if>
  										</dd>
  										<dt>
  											${language.getTextSource('engine.type')} :
			  							</dt>
  										<dd>
  											<p class="text-muted">  												
  												<#if car.engine.type??>
  													${language.getTextSource('engine.type.${car.engine.type}')}
  												</#if>
  												<#if car.engine.cylinderDisposition?? && car.engine.numberOfCylinders??>
		  											${car.engine.cylinderDisposition}${car.engine.numberOfCylinders}
	  											</#if>
		  									</p>
	  									</dd>
  										<dt>
  											${language.getTextSource('engine.numberOfValves')} :
  										</dt>	
	 		 							<dd>
  											<p class="text-muted">
  												<@writeCarNumericData car.engine.numberOfValves?default(-1)/>
  												<#if car.engine.numberOfValves??>
  													 (${car.engine.numberOfValves/car.engine.numberOfCylinders} ${language.getTextSource('engine.valvesPerCylinder')})
 		 										</#if>
	  										</p>
  										</dd>
  										<dt>
		  									${language.getTextSource('engine.maxPower')} :
	  									</dt>
  										<dd>
  											<p class="text-muted">
  												<#if unitsOfMeasure == unitsOfMeasureMetric>	
													<@writeCarNumericData car.engine.maxPower?default(-1)/>
		  											<#if car.engine.maxPower??>
		  												<em class="measure-unit-text">${language.getTextSource('CV')}</em>
		  											</#if>
												<#elseif unitsOfMeasure == unitsOfMeasureImperial>
													<@writeNonDecimalCarNumericData (car.engine.maxPower*0.9863)?default(-1)/>
		  											<#if car.engine.maxPower??>
		  												<em class="measure-unit-text">${language.getTextSource('HP')}</em>
		  											</#if>
												</#if>												
  												<#if car.engine.maxPower??>	
  												    (<@writeNonDecimalCarNumericData (car.engine.maxPower*0.736)?default(-1)/><em class="measure-unit-text">${language.getTextSource('KW')}</em>)			
													<#if car.engine.maxPowerRPM??>
  														<b>@</b> <@writeCarNumericData car.engine.maxPowerRPM?default(-1)/><em class="measure-unit-text">${language.getTextSource('RPM')}</em>
  													</#if>
  												</#if>						  										
	  										</p>
  										</dd>
	  									<dt>
				  							${language.getTextSource('engine.maxTorque')} :
  										</dt>
  										<dd>
  											<p class="text-muted">  
												<#if unitsOfMeasure == unitsOfMeasureMetric>	
													<@writeCarNumericData car.engine.maxTorque?default(-1)/>
		  											<#if car.engine.maxTorque??>
		  												<em class="measure-unit-text">${language.getTextSource('NM')}</em>
		  											</#if>
  													<#if car.engine.maxTorqueRPM??>
  														<b>@</b> <@writeCarNumericData car.engine.maxTorqueRPM?default(-1)/><em class="measure-unit-text">${language.getTextSource('RPM')}</em>
  													</#if>	
												<#elseif unitsOfMeasure == unitsOfMeasureImperial>
													<@writeNonDecimalCarNumericData (car.engine.maxTorque*0.737562149)?default(-1)/>
		  											<#if car.engine.maxTorque??>
		  												<em class="measure-unit-text">${language.getTextSource('LbFt')}</em>
		  											</#if>
  													<#if car.engine.maxTorqueRPM??>
  														<b>@</b> <@writeCarNumericData car.engine.maxTorqueRPM?default(-1)/><em class="measure-unit-text">${language.getTextSource('RPM')}</em>
  													</#if>	
												</#if>							  									
		  									</p>
  										</dd>
  										<dt>
		  									${language.getTextSource('engine.maxRPM')} :
  										</dt>
  										<dd>
  											<p class="text-muted">
  												<@writeCarNumericData car.engine.maxRPM?default(-1)/>
		  										<#if car.engine.maxRPM??>
  													 <em class="measure-unit-text">${language.getTextSource('RPM')}</em>
  												</#if>
				  							</p>
  										</dd>
  										<dt>
		  									${language.getTextSource('engine.specificOutput')} :
  										</dt>
  										<dd>
  											<p class="text-muted"> 												
		  										<#if unitsOfMeasure == unitsOfMeasureMetric>	
													<@writeNonDecimalCarNumericData (car.engine.maxPower/(car.engine.size/1000))?default(-1)/><em class="measure-unit-text">${language.getTextSource('cvPerLitre')}</em>	 
												<#elseif unitsOfMeasure == unitsOfMeasureImperial>
													<@writeNonDecimalCarNumericData ((car.engine.maxPower*0.9863)/(car.engine.size/1000))?default(-1)/><em class="measure-unit-text">${language.getTextSource('hpPerLitre')}</em>	 
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
						<h3 class="text-left car-details-panel-heading">${language.getTextSource('brakes')}</h3>
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
									<h3 class="text-left car-details-panel-heading">${language.getTextSource('transmission')}</h3>
								</div>
								<div class="panel-body">
									<dl class="dl-horizontal text-left">
  										<dt>
  											${language.getTextSource('transmission.type')} :
  										</dt>
			  							<dd>
  											 <#if (car.transmission??) && (car.transmission.type??)>
  											 	<p class="text-muted">
  											 		${language.getTextSource('transmission.type.${car.transmission.type}')}
 		 									 	</p>
	  										 </#if>
  										</dd>
				  						<dt>
  											${language.getTextSource('transmission.numOfGears')} :
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
									<h3 class="text-left car-details-panel-heading">${language.getTextSource('tyres')}</h3>
								</div>
								<div class="panel-body">
									<dl class="dl-horizontal text-left">
			  							<dt>
  											${language.getTextSource('tyreSet.frontTyre')} :
  										</dt>
  										<dd>
		  									 <p class="text-muted">
  											 	<#if (car.tyreSet??) && (car.tyreSet.frontTyre??)>
  											 		<@writeCarNumericData car.tyreSet.frontTyre.width?default(-1)/>/<@writeCarNumericData car.tyreSet.frontTyre.profile?default(-1)/>/ <em class="measure-unit-text">R</em><@writeCarNumericData car.tyreSet.frontTyre.rimDiameter?default(-1)/>
  										 		</#if>
	  										 </p>
				  						</dd>
  										<dt>
  											${language.getTextSource('tyreSet.backTyre')} :
  										</dt>
 		 								<dd>
  											<p class="text-muted">
  												<#if (car.tyreSet??) && (car.tyreSet.backTyre??)>
  													<@writeCarNumericData car.tyreSet.backTyre.width?default(-1)/>/<@writeCarNumericData car.tyreSet.backTyre.profile?default(-1)/>/ <em class="measure-unit-text">R</em><@writeCarNumericData car.tyreSet.backTyre.rimDiameter?default(-1)/>
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
				<p class="col-lg-12 text-muted text-center">${language.getTextSource('car.noCarFound')}</p>
			</div>			
		</#if>		
	</div>
</div>

<@generic.endPage/>

<#macro writeCarNumericData data>
    <#if data != -1>
        ${data?string["0.#"]}
    <#else>
        ${language.getTextSource('undefined')}
    </#if>
</#macro>

<#macro writeNonDecimalCarNumericData data>
    <#if data != -1>
        ${data?string["0"]}
    <#else>
        ${language.getTextSource('undefined')}
    </#if>
</#macro>

<#macro writeCarStringData data>
    <#if data != "">
        ${data}
    <#else>
        ${language.getTextSource('undefined')}
    </#if>
</#macro>

<#macro writeCarBrakeInfo brake>	
	<div class="panel panel-default">
		<div class="panel-heading">
			<#if brake.train = "FRONT">
				<h3 class="text-left car-details-panel-heading">${language.getTextSource('brakeSet.front')}</h3>
			<#else>
				<h3 class="text-left car-details-panel-heading">${language.getTextSource('brakeSet.back')}</h3>
			</#if>			
		</div>
		<div class="panel-body">
			<dl class="dl-horizontal text-left">
  				<dt>
  					${language.getTextSource('brake.disc.diameter')} :		
  				</dt>
  				<dd>
  					<p class="text-muted">
  						<@writeCarNumericData brake.discDiameter?default(-1)/>
  						<#if brake.discDiameter??>
  							<em class="measure-unit-text">${language.getTextSource('MM')}</em>
  						</#if>
  					</p>
  				</dd>
  				<dt>
  					${language.getTextSource('brake.disc.material')} :		
  				</dt>
  				<dd>
  					<#if brake.discMaterial??>
  						<p class="text-muted">
  							${language.getTextSource('brake.disc.material.${brake.discMaterial}')}
  						</p>
  					</#if>
  				</dd>
  				<dt>
  					${language.getTextSource('brake.caliper.numOfPistons')} :		
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

<script type="application/javascript">

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

                        <@generic.addLoadingSpinnerToComponentScript "main-car-details-div"/>

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
	<#if youtubeVideosPresent == true>
		function hideOrShowPictures(show)
		{
			if (show)
			{
				$('#car-pictures-carousel').removeClass('hidden');
				$('#show-pictures-tab').addClass('active');
				$('#car-videos-carousel').addClass('hidden');
				$('#show-videos-tab').removeClass('active');
			}
			else
			{
				$('#car-pictures-carousel').addClass('hidden');
				$('#show-pictures-tab').removeClass('active');
				$('#car-videos-carousel').removeClass('hidden');
				$('#show-videos-tab').addClass('active');
			}
		}
	</script>
	
	<script src="/resources/javascript/lib/youtube-iframe-api.min.js"></script>
	<script type="application/javascript">
		 <#list youtubeVideoIds as videoId>
		 	var player${videoId?index};
		 </#list>		
		 function onYouTubeIframeAPIReady() {		 	
		 	<#list youtubeVideoIds as videoId>
		     	player${videoId?index} = new YT.Player('${videoId}-iframe-div', {         
					videoId: '${videoId}',
					width: '100%',
    				height: '600',					
					events: {
						'onStateChange': on${videoId?index}PlayerStateChange
					}
				});		 	
		 	
			 	function on${videoId?index}PlayerStateChange(event) {
					switch(event.data){
						case 1:
							$('#car-videos-carousel').carousel('pause');
							break;
						case 2:
							$('#car-videos-carousel').carousel({pause: false, interval: 8000});
							break;
					}
				}
			</#list>
		}
		
		$('#car-videos-carousel').bind('slide.bs.carousel', function (e) {
    		<#list youtubeVideoIds as videoId>		
				player${videoId?index}.pauseVideo();
    		</#list>
		});
	</#if>
	
	$(function()
	{
		$('.carousel').carousel({
	  		interval: 8000
		});
		
		$('.modal').on('show.bs.modal', function (e)
		{
	  		$('.carousel').carousel('pause');
		});
		
		$('.modal').on('hidden.bs.modal', function (e)
		{
	  		$('.carousel').carousel({
	  			interval: 8000
			});
		});
	})
</script>