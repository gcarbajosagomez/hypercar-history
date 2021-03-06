<#compress>
	<#import "applicationMacros/genericFunctionalities.ftl" as generic/>
	<#import "applicationMacros/pageLanguage.ftl" as language/>
	<#import "applicationMacros/picture.ftl" as picture/>
	<#import "applicationMacros/brake.ftl" as brake/>
	<#import "applicationMacros/tyre.ftl" as tyre/>
	<#import "applicationMacros/carUtils.ftl" as carUtils/>
	<#import "applicationMacros/metaData.ftl" as metaData/>

	<#if car??>
		<#if pictureIds??>
			<#assign numberOfPictures = pictureIds?size/>
		<#else>
			<#assign numberOfPictures = 0/>
		</#if>

		<#if youtubeVideos??>
			<#assign numberOfVideos = youtubeVideos?size/><#else><#assign numberOfVideos = 0/>
		</#if>

		<#assign manufacturerName = manufacturer.getName()/>
		<#assign title>${language.getTextSource('${manufacturerName}')} ${car.model} <#if (numberOfVideos > 0)>${language.getTextSource('car.details.title', [numberOfPictures, numberOfVideos])?lower_case}<#else>${language.getTextSource('car.details.title.noVideos', [numberOfPictures])?lower_case}</#if></#assign>
		<#assign metaKeywords>
			<#if (numberOfVideos > 0)>
				${language.getTextSource('${manufacturerName}.meta.keywords.carDetails', [car.model])}
			<#else>
				${language.getTextSource('${manufacturerName}.meta.keywords.carDetails.noVideos', [car.model])}
			</#if>
		</#assign>
	<#else>
		<#assign title>${language.getTextSource('meta.title.noCarFound')}</#assign>
	</#if>

	<@generic.startPage title
						metaKeywords
						null/>

	<div id="main-container" class="container">
		<div class="panel panel-default row car-details-main-panel">
			<#if car??>
				<div class="panel-heading car-details-model-name-panel-heading">
					 <h1 class="text-left car-details-model-name">${car.manufacturer.name}<#if !requestIsDesktop><br/></#if> ${car.model} (${carUtils.getCarProductionLifeTime (car)})</h1>
				</div>
				<div class="<#if (numberOfPictures > 0)>thumbnail vertically-aligned-div car-pictures-carousel-div</#if>">
					<#if (numberOfPictures > 0)>
						<div id="car-pictures-carousel" class="carousel slide center-block vertically-aligned-div container" data-ride="carousel">
							<ol class="carousel-indicators">
								<#list pictureIds as pictureId>
									<li data-target="#car-pictures-carousel" data-slide-to="${pictureId?index}" class="<#if pictureId?is_first>active</#if>"></li>
								</#list>
							</ol>
							<div class="carousel-inner">
								<#list pictureIds as pictureId>
									<div id="pic-div-${pictureId}" class="item <#if pictureId?is_first>active</#if>">
										<a href="/${picturesURL}/${loadCarPictureAction}?${id}=${pictureId}" title="${car.manufacturer.name} ${car.model}" gallery="#images-gallery">
											<img <#if requestIsDesktop>class="border-radiused-img img-responsive"</#if> src="/${picturesURL}/${loadCarPictureAction}?${id}=${pictureId}" alt="${car.manufacturer.name} ${car.model}" title="${car.manufacturer.name} ${car.model}">
										</a>
									</div>
								</#list>
							</div>
							<a class="left carousel-control" href="#car-pictures-carousel" data-slide="prev">
								<span class="glyphicon glyphicon-chevron-left"></span>
							</a>
							<a class="right carousel-control" href="#car-pictures-carousel" data-slide="next">
								<span class="glyphicon glyphicon-chevron-right"></span>
							</a>
						</div>

						<@picture.addPicturesGallery "images-gallery" "carousel-inner"/>

						<#if (numberOfVideos > 0)>
							<@picture.addOpenVideoGalleryFunctionScript "videos-gallery"/>
						</#if>
					<#else>
						<h2 class="text-center">${language.getTextSource('noPicturesAvailable')}</h2>
					</#if>
				</div>

				<#if (numberOfVideos > 0)>
					<ul class="nav nav-tabs">
						<li id="show-pictures-tab" class="active cursor-pointer" role="presentation"><a>${language.getTextSource('car.pictures')}</a></li>
						<li id="show-videos-tab" class="cursor-pointer" role="presentation"><a onClick="openVideoGallery()">${language.getTextSource('car.videos')}</a></li>
					</ul>
				</#if>

				<div id="main-car-details-div" class="col-lg-12 panel-body">
					<#assign showESDescription = false/>
					<#assign showENDescription = false/>
					<#if (car.descriptionES?? && car.descriptionES?length > 0) && lang == languageSpanishCode>
						<#assign showESDescription = true/>
					<#elseif (car.descriptionEN?? && car.descriptionEN?length > 0) && lang == languageEnglishCode>
						<#assign showENDescription = true/>
					</#if>
					<#if showESDescription || showENDescription>
						<div class="well car-description-well">
							<#if showESDescription>
								${generic.normalizeDatabaseString(car.descriptionES?j_string)}
							<#elseif showENDescription>
								${generic.normalizeDatabaseString(car.descriptionEN?j_string)}
							</#if>
						</div>
					</#if>

					<div class="row panel-body car-details-panel-body">
						<div class="col-lg-6 col-md-6 col-sm-12">
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="row">
										<h3 class="col-lg-12 text-left car-details-panel-heading">${car.model}</h3>

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
																	<i id="metric-units-loading-gif" class="fa fa-circle-o-notch fa-lg fa-spin manufacturer-colour sr-only"></i>
																</div>
															</div>
														</a>
													</li>
													<hr>
													<li role="presentation">
														<a class="cursor-pointer" role="menuitem" tabindex="-1" onClick="setUnitsOfMeasure('${unitsOfMeasureImperial}', $('#main-form')[0]);" style="padding-right: 0px; padding-left: 0px;">
															<div class="row" style="margin: 0px;">
																<div class="col-lg-7 col-md-7 col-sm-7 col-xs-7" style="padding-right: 0px;">
																	${language.getTextSource('unitsOfMeasure.imperial')}
																</div>
																<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5 center-block" style="padding-right: 0px; padding-top: 5px;">
																	<i id="imperial-units-loading-gif" class="fa fa-circle-o-notch fa-lg fa-spin manufacturer-colour sr-only"></i>
																</div>
															</div>
														</a>
													</li>
												</ul>
											</div>
										</div>
									</div>
									<div class="panel-body inner-car-details-panel-body">
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
														${language.getTextSource('unknown')}
													</#if>
												</p>
											</dd>
											<dt>
												${language.getTextSource('car.chassisMaterials')} :
											</dt>
											<dd>
												<p class="text-muted">
													<#if car.chassisMaterials??>
														<#assign bodyMaterials = car.chassisMaterials?split(carMaterialsStringSeparator)>
														<#list bodyMaterials as bodyMaterial>
															${language.getTextSource('car.material.${bodyMaterial}')}
															<#if bodyMaterial?has_next>
															   ${carMaterialsStringSeparator}
															</#if>
														</#list>
													<#else>
														${language.getTextSource('unknown')}
													</#if>
												</p>
											</dd>
											<dt>
												${language.getTextSource('car.bodyMaterials')} :
											</dt>
											<dd>
												<p class="text-muted">
													<#if car.bodyMaterials??>
														<#assign bodyMaterials = car.bodyMaterials?split(carMaterialsStringSeparator)>
														<#list bodyMaterials as bodyMaterial>
															${language.getTextSource('car.material.${bodyMaterial}')}
															<#if bodyMaterial?has_next>
															   ${carMaterialsStringSeparator}
															</#if>
														</#list>
													<#else>
														${language.getTextSource('unknown')}
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
														${language.getTextSource('car.bodyShape.${car.bodyShape.getName()}')?lower_case}
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
												${language.getTextSource('car.acceleration')} :
												<p class="text-muted">
													<#if unitsOfMeasure == unitsOfMeasureMetric>
														(${language.getTextSource('0-100Km/h')})
													<#elseif unitsOfMeasure == unitsOfMeasureImperial>
														(${language.getTextSource('0-62Mph')})
													</#if>
												</p>
											</dt>
											<dd class="double-height">
												<p class="text-muted">
													${carUtils.writeCarNumericData (car.acceleration!-1)}<#if car.acceleration??><em class="measure-unit-text"> ${language.getTextSource('S')}</em></#if>
												</p>
											</dd>
											<dt>
												${language.getTextSource('car.topSpeed')} :
											</dt>
											<dd>
												<p class="text-muted">
													<#if unitsOfMeasure == unitsOfMeasureMetric>
														${carUtils.writeCarNumericData (car.topSpeed!-1)}<#if car.topSpeed??><em class="measure-unit-text"> ${language.getTextSource('Km/h')}</em></#if>
													<#elseif unitsOfMeasure == unitsOfMeasureImperial>
														${carUtils.writeNonDecimalCarNumericData ((car.topSpeed*0.621371)!-1)}<#if car.topSpeed??><em class="measure-unit-text"> ${language.getTextSource('Mph')}</em></#if>
													</#if>
												</p>
											</dd>
											<dt>
												${language.getTextSource('car.engineLayout')} :
											</dt>
											<dd>
												<p class="text-muted">
													${language.getTextSource('car.engineLayout.${car.engineLayout.getName()}')} ${language.getTextSource('car.engineDisposition.${car.engineDisposition.getName()}')?lower_case}
												</p>
											</dd>
											<dt>
												${language.getTextSource('car.specificPower')} :
											</dt>
											<dd>
												<p class="text-muted">
													<#if car.weight??>
														<#if unitsOfMeasure == unitsOfMeasureMetric>
															${carUtils.writeNonDecimalCarNumericData ((car.engine.maxPower/(car.weight/1000))!-1)}<em class="measure-unit-text"> ${language.getTextSource('hpPerTonne')}</em>
														<#elseif unitsOfMeasure == unitsOfMeasureImperial>
															${carUtils.writeNonDecimalCarNumericData ((car.engine.maxPower*0.9863)/(car.weight/1000))!-1}<em class="measure-unit-text"> ${language.getTextSource('bhpPerTonne')}</em>
														</#if>
													<#else>
														${language.getTextSource('unknown')}
													</#if>
												</p>
											</dd>
											<dt>
												${language.getTextSource('car.weight')} :
											</dt>
											<dd>
												<p class="text-muted">
													<#if unitsOfMeasure == unitsOfMeasureMetric>
														${carUtils.writeCarNumericData (car.weight!-1)}<#if car.weight??><em class="measure-unit-text"> ${language.getTextSource('Kg')}</em></#if>
													<#elseif unitsOfMeasure == unitsOfMeasureImperial>
														${carUtils.writeNonDecimalCarNumericData ((car.weight*2.20462)!-1)}<#if car.weight??><em class="measure-unit-text"> ${language.getTextSource('Lb')}</em></#if>
													</#if>
												</p>
											</dd>
											<dt class="double-height">
												${language.getTextSource('car.dimensions')} :<p class="text-muted">(${language.getTextSource('dimension.length')}/${language.getTextSource('dimension.width')}/${language.getTextSource('dimension.height')})</p>
											</dt>
											<dd class="double-height">
												<p class="text-muted">
													<#if car.length??>
														${car.length}<em class="measure-unit-text"> ${language.getTextSource('MM')}</em>
													<#else>
														${language.getTextSource('unknown')}
													</#if>
														/
													<#if car.width??>
														${car.width}<em class="measure-unit-text"> ${language.getTextSource('MM')}</em>
													<#else>
														${language.getTextSource('unknown')}
													</#if>
														/
													<#if car.height??>
														${car.height}<em class="measure-unit-text"> ${language.getTextSource('MM')}</em>
													<#else>
														${language.getTextSource('unknown')}
													</#if>
												</p>
											</dd>
                                            <dt>
                                                ${language.getTextSource('car.wheelbase')} :
                                            </dt>
                                            <dd>
												<p class="text-muted">
													<#if car.wheelbase??>
														${car.wheelbase}<em class="measure-unit-text"> ${language.getTextSource('MM')}</em>
													<#else>
														${language.getTextSource('unknown')}
													</#if>
												</p>
                                            </dd>
											<dt class="double-height">
												${language.getTextSource('car.fuelConsumption')} :
												<p class="text-muted">
													<#if unitsOfMeasure == unitsOfMeasureMetric>
															(${language.getTextSource('L/100Km')})
													<#elseif unitsOfMeasure == unitsOfMeasureImperial>
															(${language.getTextSource('MPG')})
													</#if>
												</p>
											</dt>
											<dd class="double-height">
												<p class="text-muted">
													<#if unitsOfMeasure == unitsOfMeasureMetric>
														${carUtils.writeCarNumericData (car.fuelConsumption!-1)}<#if car.fuelConsumption??><em class="measure-unit-text"> ${language.getTextSource('L/100Km')}</em></#if>
													<#elseif unitsOfMeasure == unitsOfMeasureImperial>
														${carUtils.writeNonDecimalCarNumericData ((235.214/car.fuelConsumption)!-1)}<#if car.fuelConsumption??><em class="measure-unit-text"> ${language.getTextSource('MPG')}</em></#if>
													</#if>
												</p>
											</dd>
											<dt>
												${language.getTextSource('car.fuelTankCapacity')} :
											</dt>
											<dd>
												<p class="text-muted">
													<#if unitsOfMeasure == unitsOfMeasureMetric>
														${carUtils.writeCarNumericData (car.fuelTankCapacity!-1)}<#if car.fuelTankCapacity??><em class="measure-unit-text"> ${language.getTextSource('L')}</em></#if>
													<#elseif unitsOfMeasure == unitsOfMeasureImperial>
														${carUtils.writeNonDecimalCarNumericData ((car.fuelTankCapacity*0.219969)!-1)}<#if car.fuelTankCapacity??><em class="measure-unit-text"> ${language.getTextSource('Gal')}</em></#if>
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
															${carUtils.writeNonDecimalCarNumericData (((car.fuelTankCapacity)/(car.fuelConsumption)*100)!-1)}<em class="measure-unit-text"> ${language.getTextSource('Km')}</em>
														<#else>
															${language.getTextSource('unknown')}
														</#if>
													<#elseif unitsOfMeasure == unitsOfMeasureImperial>
														<#if car.fuelConsumption?? && car.fuelTankCapacity??>
															${carUtils.writeNonDecimalCarNumericData (((car.fuelTankCapacity)/(car.fuelConsumption)*100)*0.621371!-1)}<em class="measure-unit-text"> ${language.getTextSource('Miles')}</em>
														<#else>
															${language.getTextSource('unknown')}
														</#if>
													</#if>
												</p>
											</dd>
										</dl>
									</div>
								</div>
							</div>
							<div class="col-lg-6 col-md-6 col-sm-12">
								<div class="panel panel-default">
									<div class="panel-heading" style="min-height: 84px">
										<h3 class="text-left car-details-panel-heading">${language.getTextSource('engine')}</h3>
									</div>
									<div class="panel-body inner-car-details-panel-body">
										<dl class="dl-horizontal text-left">
											<dt>
												${language.getTextSource('engine.code')} :
											</dt>
											<dd>
												<p class="text-muted">
													${carUtils.writeCarStringData (car.engine.code!"")}
												</p>
											</dd>
											<dt>
												${language.getTextSource('engine.displacement')} :
											</dt>
											<dd>
												<#if car.engine.size??>
													<p class="text-muted">
														${(car.engine.size/1000)?string("0.#")}<em class="measure-unit-text">${language.getTextSource('L')}</em> (${car.engine.size}<em class="measure-unit-text"> ${language.getTextSource('CM3')}</em>)
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
													<#if car.engine.cylinderBankAngle??>
														${car.engine.cylinderBankAngle}<em class="measure-unit-text">??</em>
													</#if>
												</p>
											</dd>
											<dt>
												${language.getTextSource('engine.numberOfValves')} :
											</dt>
											<dd>
												<p class="text-muted">
													${carUtils.writeCarNumericData (car.engine.numberOfValves!-1)}
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
														${carUtils.writeCarNumericData (car.engine.maxPower!-1)}<#if car.engine.maxPower??><em class="measure-unit-text"> ${language.getTextSource('CV')}</em></#if>
													<#elseif unitsOfMeasure == unitsOfMeasureImperial>
														${carUtils.writeNonDecimalCarNumericData ((car.engine.maxPower*0.9863)!-1)}<#if car.engine.maxPower??><em class="measure-unit-text"> ${language.getTextSource('BHP')}</em></#if>
													</#if>
													<#if car.engine.maxPower??>
														(${carUtils.writeNonDecimalCarNumericData ((car.engine.maxPower*0.736)!-1)}<em class="measure-unit-text"> ${language.getTextSource('KW')}</em>)
														<#if car.engine.maxPowerRPM??>
															<b>@</b> ${carUtils.writeCarNumericData ((car.engine.maxPowerRPM!-1))}<em class="measure-unit-text"> ${language.getTextSource('RPM')}</em>
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
														${carUtils.writeCarNumericData (car.engine.maxTorque!-1)}<#if car.engine.maxTorque??><em class="measure-unit-text"> ${language.getTextSource('NM')}</em></#if>
														<#if car.engine.maxTorqueRPM??>
															<b>@</b> ${carUtils.writeCarNumericData (car.engine.maxTorqueRPM!-1)}<em class="measure-unit-text"> ${language.getTextSource('RPM')}</em>
														</#if>
													<#elseif unitsOfMeasure == unitsOfMeasureImperial>
														${carUtils.writeNonDecimalCarNumericData ((car.engine.maxTorque*0.737562149)!-1)}<#if car.engine.maxTorque??><em class="measure-unit-text"> ${language.getTextSource('LbFt')}</em></#if>
														<#if car.engine.maxTorqueRPM??>
															<b>@</b> ${carUtils.writeCarNumericData (car.engine.maxTorqueRPM!-1)}<em class="measure-unit-text"> ${language.getTextSource('RPM')}</em>
														</#if>
													</#if>
												</p>
											</dd>
											<dt>
												${language.getTextSource('engine.maxRPM')} :
											</dt>
											<dd>
												<p class="text-muted">
													${carUtils.writeCarNumericData (car.engine.maxRPM!-1)}<#if car.engine.maxRPM??><em class="measure-unit-text"> ${language.getTextSource('RPM')}</em></#if>
												</p>
											</dd>
											<dt>
												${language.getTextSource('engine.specificOutput')} :
											</dt>
											<dd>
												<p class="text-muted">
													<#if unitsOfMeasure == unitsOfMeasureMetric>
														${carUtils.writeNonDecimalCarNumericData ((car.engine.maxPower/(car.engine.size/1000))!-1)}<em class="measure-unit-text"> <#if car.engine.maxPower??>${language.getTextSource('hpPerLitre')}</#if></em>
													<#elseif unitsOfMeasure == unitsOfMeasureImperial>
														${carUtils.writeNonDecimalCarNumericData (((car.engine.maxPower*0.9863)/(car.engine.size/1000))!-1)}<em class="measure-unit-text"> <#if car.engine.maxPower??>${language.getTextSource('bhpPerLitre')}</#if></em>
													</#if>
												</p>
											</dd>
										</dl>
									</div>
								</div>
							</div>
					</div>

				<div class="row panel-body car-details-panel-body">
					<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="text-left car-details-panel-heading">${language.getTextSource('brakes')}</h3>
							</div>
							<div class="row panel-body">
								<@brake.writeCarBrakeInfo car.brakeSet.frontBrake/>
								<hr>
								<@brake.writeCarBrakeInfo car.brakeSet.rearBrake/>
							</div>
						</div>
					</div>

					<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="text-left car-details-panel-heading">${language.getTextSource('transmission')}</h3>
							</div>
							<div class="panel-body inner-car-details-panel-body">
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
												${carUtils.writeCarNumericData (car.transmission.numOfGears!-1)}
											</#if>
										</p>
									</dd>
								</dl>
							</div>
						</div>
					</div>
					<@tyre.writeTyreInfo car.tyreSet/>
				</div>
			<#else>
				<div class="panel panel-body col-lg-12">
					<p class="col-lg-12 text-muted text-center">${language.getTextSource('car.noCarFound')}</p>
				</div>
			</#if>
		</div>
	</div>

	<@generic.endPage/>

	<script type="application/javascript">

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

	<@carUtils.addSetUnitsOfMeasureFunctionScript/>
	<#if car??>
		<@metaData.addCarStructuredMetadata metaKeywords/>
	</#if>
</#compress>