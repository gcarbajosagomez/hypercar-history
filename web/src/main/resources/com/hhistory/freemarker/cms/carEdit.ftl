<#import "/spring.ftl" as spring/>
<#import "engine.ftl" as engine/>
<#import "brake.ftl" as brake/>
<#import "tyre.ftl" as tyre/>
<#import "internetContent.ftl" as internetContent/>
<#import "crudOperations.ftl" as crudOperations/>
<#import "picture.ftl" as cmsPictureUtil/>
<#import "../applicationMacros/picture.ftl" as pictureUtil/>
<#import "../applicationMacros/genericFunctionalities.ftl" as generic/>
<#import "../applicationMacros/pageLanguage.ftl" as language/>
<#import "../applicationMacros/uriUtils.ftl" as uriUtils/>

<#if CEFC.editForm.id??>
    <#assign numberOfPictures = CEFC.editForm.pictureFileEditCommands?size/>

    <#assign numberOfVideos = 0/>
    <#if (CICEFC.editForms?size > 0)>
        <#assign numberOfVideos = CICEFC.editForms?size/>
    </#if>

	<#assign title>${CEFC.editForm.manufacturer.name} ${CEFC.editForm.model} ${language.getTextSource('car.details.title', [numberOfPictures, numberOfVideos])?lower_case}</#assign>
<#else>
	<#assign title>${language.getTextSource('car.newCar')}</#assign>
</#if>

<@generic.startPage title/>

<div id="main-container" class="container car-edit-container">
	<div class="row">
        <#if crudOperationDTO??>
	        <@crudOperations.addOperationResultMessage crudOperationDTO/>
        </#if>
        <#if carInternetContentsCrudOperationDTO??>
	        <@crudOperations.addOperationResultMessage carInternetContentsCrudOperationDTO/>
        </#if>
	    <div class="col-lg-6 col-sm-6 col-xs-12">
		   <div class="panel panel-default">
			   <div class="panel-heading">
					<h3 class="text-left"><#if CEFC.editForm.id??>${CEFC.editForm.model}<#else>${language.getTextSource('car')}</#if></h3>

                        <input type="button" class="btn btn-success" value="<#if CEFC.editForm.id??>
                                                                                ${language.getTextSource('cms.editCar')}
                                                                            <#else>
                                                                                ${language.getTextSource('cms.saveCar')}
                                                                            </#if>"
                                                                     onClick="<#if CEFC.editForm.id??>
                                                                                    editEntity('${uriUtils.buildDomainURI("/${cmsContext}${carsURL}/${CEFC.editForm.id}/${editURL}")}', '${language.getTextSource('car.confirmEdit')}')
                                                                              <#else>
                                                                                    saveEntity('${uriUtils.buildDomainURI("/${cmsContext}${carsURL}/${saveURL}")}', '${language.getTextSource('car.confirmSave')}')
                                                                              </#if>;"/>

                        <#if CEFC.editForm.id??>
                            <a class="btn btn-danger" onClick="deleteEntity('${uriUtils.buildDomainURI("/${cmsContext}${carsURL}/${CEFC.editForm.id}/${deleteURL}")}', '${language.getTextSource('car.confirmDelete')}');"/>
                                <span class="glyphicon glyphicon-remove-sign"></span> ${language.getTextSource('cms.deleteCar')}
                            </a>
                        </#if>
                        <a class="btn btn-default" href='${uriUtils.buildDomainURI("/${cmsContext}${carsURL}/${editURL}")}'>
                            <span class="glyphicon glyphicon-plus-sign"></span> ${language.getTextSource('cms.newCar')}
                        </a>
			   </div>
			   <div class="panel-body">
			   	   <dl class="dl-horizontal dl-horizontal-edit text-left">
			      	  <#if CEFC.editForm.id??>
                        <dt>
                          	${language.getTextSource('id')}
                      	</dt>
                      	<dd>
                        	<h5 class="entity-id text-muted">${CEFC.editForm.id}</h5>
                            <@spring.formHiddenInput "CEFC.editForm.id", ""/>
                            <@spring.bind "CICEFC.editForms[0].car"/>
                            <input type="hidden" name="${spring.status.expression}" value="${CEFC.editForm.id}">
                        </dd>
                      </#if>
                      <dt>
                            ${language.getTextSource('car.visible')}
                      </dt>
                      <dd>
                            <@spring.bind "CEFC.editForm.visible"/>

                            <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                                <option value="true" <#if CEFC.editForm.visible == true>selected</#if>>${language.getTextSource('yes')}</option>
                                <option value="false" <#if CEFC.editForm.visible == false>selected</#if>>${language.getTextSource('no')}</option>
                            </select>
                      </dd>
                      <dt>
                            ${language.getTextSource('car.manufacturer')}
                      </dt>
                      <dd>
                           <@spring.bind "CEFC.editForm.manufacturer"/>

                           <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                              <#list manufacturerEntities as manufacturer>
                                 <option value="${manufacturer.id}" <#if spring.status.value?? && manufacturer.id == spring.status.value?default(-1)?number> selected</#if>>${manufacturer.name}</option>
                              </#list>
                           </select>
                      </dd>
                      <dt>
                           ${language.getTextSource('car.model')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.editForm.model", "class=form-control", "text"/>
                      </dd>
                      <dt>
                           ${language.getTextSource('car.engineLayout')}
                      </dt>
                      <dd>
                           <@spring.bind "CEFC.editForm.engineLayout"/>

                           <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                               <#list engineLayouts as engineLayout>
                                   <option value="${engineLayout}" <#if spring.status.value?? && engineLayout == spring.status.value?default("")> selected</#if>>${language.getTextSource('car.engineLayout.${engineLayout.getName()}')}</option>
                               </#list>
                           </select>
                      </dd>
                      <dd>
                           <@spring.bind "CEFC.editForm.engineDisposition"/>

                           <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                               <#list engineDispositions as engineDisposition>
                                   <option value="${engineDisposition}" <#if spring.status.value?? && engineDisposition == spring.status.value?default("")> selected</#if>>${language.getTextSource('car.engineDisposition.${engineDisposition.getName()}')}</option>
                               </#list>
                           </select>
                      </dd>
                      <dt>
                           ${language.getTextSource('car.chassisMaterials')}
                      </dt>
                      <dd class="car-materials-dd">
                          <div class="well">
                              <dl class="dl-horizontal dl-horizontal-edit text-left">
                                    <dt class="car-material-dt">
                                        1
                                    </dt>
                                    <dd class="car-material-dd">
                                        <@spring.bind "CEFC.editForm.chassisMaterials"/>
                                        <select id="${spring.status.expression}[0]" name="${spring.status.expression}[0]" class="form-control">
                                            <option value="" selected></option>

                                            <#list carMaterials as material>
                                                <option value="${material}" <#if spring.status.value?? && (spring.status.value?length > 0) && material == CEFC.editForm.chassisMaterials[0]?default("")> selected</#if>>${language.getTextSource('car.material.${material.getName()}')}</option>
                                            </#list>
                                        </select>
                                    </dd>
                                    <dt class="car-material-dt">
                                        2
                                    </dt>
                                    <dd class="car-material-dd">
                                        <select id="${spring.status.expression}[1]" name="${spring.status.expression}[1]" class="form-control">
                                            <option value="" selected></option>

                                            <#list carMaterials as material>
                                               <option value="${material}" <#if spring.status.value?? && (spring.status.value?length > 1) && material == CEFC.editForm.chassisMaterials[1]?default("")> selected</#if>>${language.getTextSource('car.material.${material.getName()}')}</option>
                                            </#list>
                                        </select>
                                    </dd>
                              </dl>
                          </div>
                      </dd>
                      <dt>
                           ${language.getTextSource('car.bodyMaterials')}
                      </dt>
                      <dd class="car-materials-dd">
                          <div class="well">
                              <dl class="dl-horizontal dl-horizontal-edit text-left">
                                    <dt class="car-material-dt">
                                        1
                                    </dt>
                                    <dd class="car-material-dd">
                                        <@spring.bind "CEFC.editForm.bodyMaterials"/>
                                        <select id="${spring.status.expression}[0]" name="${spring.status.expression}[0]" class="form-control">
                                            <option value="" selected></option>

                                            <#list carMaterials as material>
                                                <option value="${material}" <#if spring.status.value?? && (spring.status.value?length > 0) && material == CEFC.editForm.bodyMaterials[0]?default("")> selected</#if>>${language.getTextSource('car.material.${material.getName()}')}</option>
                                            </#list>
                                        </select>
                                    </dd>
                                    <dt class="car-material-dt">
                                        2
                                    </dt>
                                    <dd class="car-material-dd">
                                        <select id="${spring.status.expression}[1]" name="${spring.status.expression}[1]" class="form-control">
                                            <option value="" selected></option>

                                            <#list carMaterials as material>
                                               <option value="${material}" <#if spring.status.value?? && (spring.status.value?length > 1) && material == CEFC.editForm.bodyMaterials[1]?default("")> selected</#if>>${language.getTextSource('car.material.${material.getName()}')}</option>
                                            </#list>
                                        </select>
                                    </dd>
                              </dl>
                          </div>
                      </dd>
                      <dt>
                           ${language.getTextSource('car.bodyShape')}
                      </dt>
                      <dd>
                           <@spring.bind "CEFC.editForm.bodyShape"/>

                           <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                               <#list bodyShapes as bodyShape>
                                   <option value="${bodyShape}" <#if spring.status.value?? && bodyShape == spring.status.value?default("")> selected</#if>>${language.getTextSource('car.bodyShape.${bodyShape.getName()}')}</option>
                               </#list>
                           </select>
                      </dd>
                      <dt>
                           ${language.getTextSource('car.seatsconfig')}
                      </dt>
                      <dd>
                           <@spring.bind "CEFC.editForm.seatsConfig"/>

                           <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                               <#list seatsConfigs as seatsConfig>
                                   <option value="${seatsConfig}" <#if spring.status.value?? && seatsConfig == spring.status.value?default("")> selected</#if>>${language.getTextSource('car.seatsConfig.${seatsConfig}')}</option>
                               </#list>
                           </select>
                      </dd>
                      <dt>
                           ${language.getTextSource('car.productionType')}
                      </dt>
                      <dd>
                           <@spring.bind "CEFC.editForm.productionType"/>

                           <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                               <#list productionTypes as productionType>
                                   <option value="${productionType}" <#if spring.status.value?? && productionType == spring.status.value?default("")> selected</#if>>${language.getTextSource('car.productionType.${productionType.getName()}')}</option>
                               </#list>
                           </select>
                      </dd>
                      <dt>
                           ${language.getTextSource('car.productionStartDate')}
                      </dt>
                      <dd>
                           <div class="input-group date">
                           	   <@spring.formInput "CEFC.editForm.productionStartDate", "class=form-control"/>
							   <span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
						   </div>
                      </dd>
                      <dt>
                           ${language.getTextSource('car.productionEndDate')}
                      </dt>
                      <dd>
                           <div class="input-group date">
                           	   <@spring.formInput "CEFC.editForm.productionEndDate", "class=form-control"/>
							   <span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
						   </div>
                      </dd>
                      <dt>
                           ${language.getTextSource('car.weight')}
                      </dt>
                      <dd>
							<@spring.formInput "CEFC.editForm.weight", "class=form-control placeholder=${language.getTextSource('Kg')}", "text"/>
                      </dd>
                      <dt>
                           ${language.getTextSource('car.length')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.editForm.length", "class=form-control placeholder=${language.getTextSource('MM')}", "text"/>
                      </dd>
                      <dt>
                           ${language.getTextSource('car.width')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.editForm.width", "class=form-control placeholder=${language.getTextSource('MM')}", "text"/>
                      </dd>
					  <dt>
                           ${language.getTextSource('car.height')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.editForm.height", "class=form-control placeholder=${language.getTextSource('MM')}", "text"/>
                      </dd>
					  <dt>
                           ${language.getTextSource('car.wheelbase')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.editForm.wheelbase", "class=form-control placeholder=${language.getTextSource('MM')}", "text"/>
                      </dd>
                      <dt>
                           ${language.getTextSource('car.acceleration')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.editForm.acceleration", "class=form-control placeholder=${language.getTextSource('S')}", "text"/>
                      </dd>
                      <dt>
                           ${language.getTextSource('car.topSpeed')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.editForm.topSpeed", "class=form-control placeholder=${language.getTextSource('Km/h')}", "text"/>
                      </dd>
                      <dt>
                           ${language.getTextSource('car.fuelTankCapacity')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.editForm.fuelTankCapacity", "class=form-control placeholder=${language.getTextSource('L')}", "text"/>
                      </dd>
                      <dt>
                           ${language.getTextSource('car.fuelConsumption')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.editForm.fuelConsumption", "class=form-control placeholder=${language.getTextSource('L/100Km')}", "text"/>
                      </dd>
                      <dt>
                           ${language.getTextSource('car.driveWheel')}
                      </dt>
                      <dd>
                           <@spring.bind "CEFC.editForm.driveWheel"/>

                           <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                               <#list driveWheelTypes as driveWheelType>
                                   <option value="${driveWheelType}" <#if spring.status.value?? && driveWheelType == spring.status.value?default("")> selected</#if>>${language.getTextSource('car.driveWheelType.${driveWheelType}')}</option>
                               </#list>
                           </select>
                      </dd>
                      <dt>
                           ${language.getTextSource('car.roadLegal')}
                      </dt>
                      <dd>
                           <@spring.bind "CEFC.editForm.roadLegal"/>

                           <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                                <option value="true" <#if CEFC.editForm.roadLegal == true>selected</#if>>${language.getTextSource('yes')}</option>
                           		<option value="false" <#if CEFC.editForm.roadLegal == false>selected</#if>>${language.getTextSource('no')}</option>
                           </select>
                      </dd>
                       <dt>
                            ${language.getTextSource('cms.car.descriptionES')}
                       </dt>
                       <dd class="resizable-dd">
                            <@spring.formTextarea "CEFC.editForm.descriptionES", "class=form-control cols='50' rows='15'"/>
                       </dd>
                       <dt>
                            ${language.getTextSource('cms.car.descriptionEN')}
                       </dt>
                       <dd class="resizable-dd">
                            <@spring.formTextarea "CEFC.editForm.descriptionEN", "class=form-control cols='50' rows='15'"/>
                       </dd>
				   </dl>
			   </div>
		   </div>
		   <div class="panel panel-default">
			   <div class="panel-heading">
			       <h3 class="text-left">${language.getTextSource('engine')}</h3>

				   <div>
                       <table>
                           <tr>
                                <td>
                                    <a class="btn btn-info" onClick="loadEngineFromDB();">
				   	   		            <i class="fa fa-database"></i> ${language.getTextSource('cms.loadEngineFromDb')}
				   	   	            </a>
                                </td>
                           </tr>
                           <tr>
                               <td colspan="3"><br/></td>
                           </tr>
                           <tr>
                                <td>
                                   <a id="engine-save-or-edit-link" class="btn btn-success" <#if CEFC.editForm.engineEditForm.id??>
																					            onClick="editEngine(${CEFC.editForm.engineEditForm.id});">
																					            <span id="engine-save-or-edit-span" class="glyphicon glyphicon-plus-sign"> ${language.getTextSource('cms.editEngine')}</span>
												   								            <#else>
												   		 							            onClick="saveEngine();">
																					            <span id="engine-save-or-edit-span" class="glyphicon glyphicon-plus-sign"> ${language.getTextSource('cms.saveEngine')}</span>
												   								            </#if>
						            </a>
                                </td>
                                <#if CEFC.editForm.engineEditForm.id??>
                                    <td>
                                        <a id="engine-delete-link" class="btn btn-danger" onClick="deleteEngine($('#editForm\\.engineEditForm\\.id')[0].value);"/>
                                            <span class="glyphicon glyphicon-remove-sign"></span> ${language.getTextSource('cms.deleteEngine')}
                                        </a>
                                    </td>
                                </#if>
                                <td>
				   	   	            <a class="btn btn-default" onClick="eraseEngineFormFields();">
					   	   		        <span class="glyphicon glyphicon-plus-sign"></span> ${language.getTextSource('cms.newEngine')}
					   	            </a>
                                </td>
                           </tr>
                       </table>
				   </div>
			   </div>
			   <div id="engine-main-div" class="panel-body">
			   	   <dl id="engine-code-selection-table" class="dl-horizontal dl-horizontal-edit text-left sr-only">
				      <dt>
                            ${language.getTextSource('engine.codes')}
                   	        <#if engines?? && (engines?size > 0)>
                               <dd>
								   <select id="load-engine-by-id-select" class="form-control" onChange="loadEngineById(this.value);">
                                        <#list engines as engine>
                                        	<option value="${engine.id}" <#if CEFC.editForm.engineEditForm.toString()?? && CEFC.editForm.engineEditForm.toString() == engine.toString()> selected</#if>>${engine.toString()}</option>
                                      	</#list>
                                   <select>
                               </dd>
                            </#if>
                      </dt>
                   </dl>
                   <dl class="dl-horizontal dl-horizontal-edit text-left">
                      <dt id="engine-id-dt" class="<#if !CEFC.editForm.engineEditForm.id??>sr-only</#if>">
                            ${language.getTextSource('id')}
                      </dt>
                      <dd id="engine-id-dd" class="<#if !CEFC.editForm.engineEditForm.id??>sr-only</#if>">
                          <@spring.bind "CEFC.editForm.engineEditForm.id"/>

                          <label id="${spring.status.expression}.label">
                          	 <#if CEFC.editForm.engineEditForm.id??>
                           	     <h5 class="entity-id text-muted">${CEFC.editForm.engineEditForm.id}</h5>
                           	 </#if>
                          </label>

                          <@spring.formHiddenInput "CEFC.editForm.engineEditForm.id", ""/>
                      </dd>
                      <dt>
                           ${language.getTextSource('engine.code')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.editForm.engineEditForm.code", "class=form-control", "text"/>
                      </dd>
                      <dt>
                           ${language.getTextSource('engine.type')}
                      </dt>
                      <dd>
                           <@spring.bind "CEFC.editForm.engineEditForm.type"/>

                           <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                               <#list engineTypes as engineType>
                                   <option value="${engineType}" <#if spring.status.value?? && engineType == spring.status.value!""> selected</#if>>${language.getTextSource('engine.type.${engineType}')}</option>
                               </#list>
                           </select>
                      </dd>
                      <dt>
                           ${language.getTextSource('engine.cylinderDisposition')}
                      </dt>
                      <dd>
                           <@spring.bind "CEFC.editForm.engineEditForm.cylinderDisposition"/>

                           <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                               <#list engineCylinderDispositions as engineCylinderDisposition>
                                   <option value="${engineCylinderDisposition}" <#if spring.status.value?? && engineCylinderDisposition == spring.status.value?default("")> selected</#if>>${engineCylinderDisposition}</option>
                               </#list>
                           </select>
                      </dd>
                      <dt>
                           ${language.getTextSource('engine.cylinderBankAngle')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.editForm.engineEditForm.cylinderBankAngle", "class=form-control", "text"/>
                      </dd>
                      <dt>
                           ${language.getTextSource('engine.numberOfCylinders')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.editForm.engineEditForm.numberOfCylinders", "class=form-control", "text"/>
                      </dd>
                      <dt>
                           ${language.getTextSource('engine.numberOfValves')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.editForm.engineEditForm.numberOfValves", "class=form-control", "text"/>
                      </dd>
                      <dt>
                           ${language.getTextSource('engine.displacement')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.editForm.engineEditForm.size", "class=form-control placeholder=${language.getTextSource('CM3')}", "text"/>
                      </dd>
                      <dt>
                           ${language.getTextSource('engine.maxPower')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.editForm.engineEditForm.maxPower", "class=form-control placeholder=${language.getTextSource('CV')}", "text"/>
                      </dd>
                      <dt>
                           ${language.getTextSource('engine.maxRPM')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.editForm.engineEditForm.maxRPM", "class=form-control placeholder=${language.getTextSource('RPM')}", "text"/>
                      </dd>
                      <dt>
                           ${language.getTextSource('engine.maxPowerRPM')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.editForm.engineEditForm.maxPowerRPM", "class=form-control placeholder=${language.getTextSource('RPM')}", "text"/>
                      </dd>
                      <dt>
                           ${language.getTextSource('engine.maxTorque')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.editForm.engineEditForm.maxTorque", "class=form-control placeholder=${language.getTextSource('NM')}", "text"/>
                      </dd>
                      <dt>
                           ${language.getTextSource('engine.maxTorqueRPM')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.editForm.engineEditForm.maxTorqueRPM", "class=form-control placeholder=${language.getTextSource('RPM')}", "text"/>
                      </dd>
				   </dl>
			   </div>
		   </div>
		   <div class="panel panel-default">
			   <div class="panel-heading">
					<h3 class="text-left">${language.getTextSource('brakeSet')}</h3>
			   </div>
			   <div class="panel-body">
                   <dl class="dl-horizontal dl-horizontal-edit text-left">
                       <@spring.formHiddenInput "CEFC.editForm.brakeSetEditForm.id", ""/>

                       <@brake.writeBrakeEditFields CEFC.editForm.brakeSetEditForm.frontDiscBrake "CEFC.editForm.brakeSetEditForm." brakeTrains[0] CEFC.editForm.brakeSetEditForm.frontBrakeType/>
                       <@brake.writeBrakeEditFields CEFC.editForm.brakeSetEditForm.rearDiscBrake "CEFC.editForm.brakeSetEditForm." brakeTrains[1] CEFC.editForm.brakeSetEditForm.rearBrakeType/>
                   </dl>
               </div>
		   </div>
		   <div class="panel panel-default">
			   <div class="panel-heading">
					<h3 class="text-left">${language.getTextSource('transmission')}</h3>
			   </div>
			   <div class="panel-body">
				   <dl class="dl-horizontal dl-horizontal-edit text-left">
				       <#if CEFC.editForm.transmissionEditForm?? && CEFC.editForm.transmissionEditForm.id??>
                           <dt>
                               ${language.getTextSource('id')}
                           </dt>
                           <dd>
                         	   <h5 class="entity-id text-muted">${CEFC.editForm.transmissionEditForm.id}</h5>
                         	   <@spring.formHiddenInput "CEFC.editForm.transmissionEditForm.id", ""/>
                           </dd>
                       </#if>
                       <dt>
                             ${language.getTextSource('transmission.type')}
                       </dt>
                       <dd>
                         	 <@spring.bind "CEFC.editForm.transmissionEditForm.type"/>

                             <select name="${spring.status.expression}" class="form-control">
                                 <#list transmissionTypes as transmissionType>
                                     <option value="${transmissionType}" <#if spring.status.value?? && transmissionType == spring.status.value>selected</#if>><h3 class="capitalizedText">${language.getTextSource('transmission.type.${transmissionType}')}</h3></option>
                                 </#list>
                             </select>
                       </dd>
                       <dt>
                             ${language.getTextSource('transmission.numOfGears')}
                       </dt>
                       <dd>
                             <@spring.formInput "CEFC.editForm.transmissionEditForm.numOfGears", "class=form-control", "text"/>
                       </dd>
				   </dl>
			   </div>
		   </div>
		   <div class="panel panel-default">
			   <div class="panel-heading">
					<h3 class="text-left">${language.getTextSource('tyreSet')}</h3>
			   </div>
			   <div class="panel-body">
                   <dl class="dl-horizontal dl-horizontal-edit text-left">
                       <dt>
                            ${language.getTextSource('tyreSet.manufacturer')}
                       </dt>
                       <dd>
                           <@spring.bind "CEFC.editForm.tyreSetEditForm.manufacturer"/>

                           <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                                <option value="" selected></option>
                                <#list tyreManufacturers as manufacturer>
                                    <option value="${manufacturer}" <#if spring.status.value?? && manufacturer == spring.status.value?default("")> selected</#if>>${manufacturer.getName()}</option>
                                </#list>
                           </select>
                       </dd>
                       <dt>
                            ${language.getTextSource('tyreSet.model')}
                       </dt>
                       <dd>
                            <@spring.formInput "CEFC.editForm.tyreSetEditForm.model", "class=form-control", "text"/>
                       </dd>
                       <dt>
                            ${language.getTextSource('tyreSet.type')}
                       </dt>
                       <dd>
                           <@spring.bind "CEFC.editForm.tyreSetEditForm.type"/>

                           <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                                <option value="" selected></option>
                                <#list tyreTypes as tyreType>
                                    <option value="${tyreType}" <#if spring.status.value?? && tyreType == spring.status.value?default("")> selected</#if>>${language.getTextSource('tyreSet.type.${tyreType.getName()}')}</option>
                                </#list>
                           </select>
                       </dd>
                   </dl>
                   <dl class="dl-horizontal dl-horizontal-edit text-left">
                       <@spring.formHiddenInput "CEFC.editForm.tyreSetEditForm.id"/>

                       <@tyre.writeTyreEditFields CEFC.editForm.tyreSetEditForm.frontTyre "CEFC.editForm.tyreSetEditForm.frontTyre" tyreTrains[0]/>
                       <@tyre.writeTyreEditFields CEFC.editForm.tyreSetEditForm.rearTyre "CEFC.editForm.tyreSetEditForm.rearTyre" tyreTrains[0]/>
                   </dl>
               </div>
		   </div>
		   <div class="panel panel-default">
			   <div class="panel-heading">
					<h3 class="text-left">${language.getTextSource('car.internetContent')}</h3>

					<a class="btn btn-info" onClick="addInternetContent()">
      					<span class="glyphicon glyphicon-plus-sign"></span> ${language.getTextSource('cms.addCarInternetContent')}
   					</a>
			   </div>
			   <div id="internet-contents-main-panel-body" class="panel-body">
				   <#if (CICEFC.editForms?size > 0)>
					   <#list CICEFC.editForms as carInternetContentForm>
						   <#assign carInternetContentFormIndex = carInternetContentForm?index>
				  		   <div id="car-internet-content-div<#if carInternetContentForm.id??>-${carInternetContentForm.id}</#if>" class="well well-lg">
			                   <dl class="dl-horizontal dl-horizontal-edit text-left">
                                   <#if CICEFC.editForms[carInternetContentFormIndex].id ??>
                                       <dt>
                                            ${language.getTextSource('id')}
                                       </dt>
                                       <dd>
                                            <h5 class="entity-id text-muted">${CICEFC.editForms[carInternetContentFormIndex].id}</h5>
                                            <@spring.formHiddenInput "CICEFC.editForms[${carInternetContentFormIndex}].id", ""/>
                                       </dd>
                                   </#if>
			                   		<dt>
			                             ${language.getTextSource('cms.car.internetContent.link')}
			                        </dt>
									<dd>
			                   			<@spring.formInput "CICEFC.editForms[${carInternetContentFormIndex}].link", "class=form-control", "text"/>
									</dd>
									<dt>
			                             ${language.getTextSource('cms.car.internetContent.type')}
			                        </dt>
			                        <dd>
			                        	<@spring.bind "CICEFC.editForms[${carInternetContentFormIndex}].type"/>

										<select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
			                            	<#list carInternetContentTypes as carInternetContentType>
			                                	<option value="${carInternetContentType}" <#if spring.status.value?? && carInternetContentType == spring.status.value?default("")> selected</#if>>${language.getTextSource('cms.car.internetContent.type.${carInternetContentType}')}</option>
			                               	</#list>
			                            </select>
									</dd>
									<dt>
			                             ${language.getTextSource('cms.car.internetContent.contentLanguage')}
			                        </dt>
									<dd>
			                   			<@spring.bind "CICEFC.editForms[${carInternetContentFormIndex}].contentLanguage"/>

										<select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
			                            	<#list carInternetContentLanguages as contentLanguage>
			                                	<option value="${contentLanguage}" <#if spring.status.value?? && contentLanguage == spring.status.value?default("")> selected</#if>>${language.getTextSource('cms.car.internetContent.contentLanguage.${contentLanguage.getName()}')}</option>
			                               	</#list>
			                            </select>
									</dd>
                                    <#if carInternetContentForm.id??>
                                        <dd>
                                            <a class="btn btn-danger" onClick="deleteCarInternetContent('${carInternetContentForm.id}', '${language.getTextSource('cms.car.internetContent.confirmDelete')}');"/>
                                                <span class="glyphicon glyphicon-remove-sign"></span> ${language.getTextSource('cms.deleteCarInternetContent')}
                                            </a>
                                        </dd>
                                    </#if>
			                   </dl>
						   </div>
					   </#list>
				   <#else>
				   	   <div class="well well-lg">
		                   <dl class="dl-horizontal dl-horizontal-edit text-left">
		                   		<dt>
		                             ${language.getTextSource('cms.car.internetContent.link')}
		                        </dt>
								<dd>
		                   			<@spring.formInput "CICEFC.editForms[0].link", "class=form-control", "text"/>
								</dd>
								<dt>
		                             ${language.getTextSource('cms.car.internetContent.type')}
		                        </dt>
		                        <dd>
		                        	<@spring.bind "CICEFC.editForms[0].type"/>

									<select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
		                            	<#list carInternetContentTypes as carInternetContentType>
		                                	<option value="${carInternetContentType}" <#if spring.status.value?? && carInternetContentType == spring.status.value?default("")> selected</#if>>${language.getTextSource('cms.car.internetContent.type.${carInternetContentType}')}</option>
		                               	</#list>
		                            </select>
								</dd>
								<dt>
		                             ${language.getTextSource('cms.car.internetContent.contentLanguage')}
		                        </dt>
								<dd>
		                   			<@spring.bind "CICEFC.editForms[0].contentLanguage"/>

                           			<select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                               			<#list carInternetContentLanguages as contentLanguage>
                                   			<option value="${contentLanguage}" <#if spring.status.value?? && contentLanguage == spring.status.value?default("")> selected</#if>>${language.getTextSource('cms.car.internetContent.contentLanguage.${contentLanguage.getName()}')}</option>
                               			</#list>
                           			</select>
								</dd>
		                   </dl>
					   </div>
				   </#if>
               </div>
		   </div>
	    </div>
	    <div class="col-lg-6 col-sm-6 col-xs-12">
	       <div class="panel panel-default">
			   <div class="panel-heading">
					<h3 class="text-left">${language.getTextSource('pictureSet')}</h3>

					<a class="btn btn-info" onClick="addPictureUploadBox();">
      					<span class="glyphicon glyphicon-plus-sign"></span> ${language.getTextSource('cms.addPicture')}
   					</a>
			   </div>
			   <div class="panel-body row">
                    <#if CEFC.editForm.pictureFileEditCommands?has_content>
                        <table style="width:100%">
                            <#list CEFC.editForm.pictureFileEditCommands as pictureCommand>
                                <#assign commandIndex = pictureCommand?index/>
                                <#if pictureCommand.picture??>
                                    <#assign picture = pictureCommand.picture/>
                                    <#if picture.id?? && (picture.id > 0)>
                                        <#assign pictureId = picture.id/>
                                        <tr id="${picture.id}-picture-row">
                                            <td style="width:70%">
                                                <a href='/${picturesURL}/${loadCarPictureAction}?${id}=${picture.id}' title="${CEFC.editForm.manufacturer.name}${CEFC.editForm.model}" gallery="#images-gallery">
                                                    <img class="col-lg-6 col-md-12 col-sm-12 thumbnail preview-img resizable-img car-picture" src="/${picturesURL}/${loadCarPictureAction}?${id}=${picture.id}" alt="${CEFC.editForm.manufacturer.name} ${CEFC.editForm.model}">
                                                </a>
                                            </td>
                                            <td style="width:30%">
                                                <dl class="dl-vertical text-left">
                                                    <dt>
                                                        ${language.getTextSource('id')}
                                                    </dt>
                                                    <dd>
                                                        <h5 class="entity-id text-muted">${CEFC.editForm.pictureFileEditCommands[commandIndex].picture.id}</h5>
                                                        <@spring.formHiddenInput "CEFC.editForm.pictureFileEditCommands[${commandIndex}].picture.id", ""/>
                                                    </dd>
                                                </dl>
                                                <dl class="dl-vertical text-left">
                                                    <dt>
                                                        ${language.getTextSource('picture.galleryPosition')}
                                                    </dt>
                                                    <dd>
                                                        <@spring.bind "CEFC.editForm.pictureFileEditCommands[${commandIndex}].picture.galleryPosition"/>
                                                        <input id="${spring.status.expression}" name="${spring.status.expression}" class="pull-right" type="text" <#if picture.galleryPosition??>value="${picture.galleryPosition}"</#if> size="10"/>
                                                    </dd>
                                                </dl>
                                                <dl class="dl-vertical text-left">
                                                    <dt>
                                                        ${language.getTextSource('picture.eligibleForPreview')}
                                                    </dt>
                                                    <dd>
                                                        <@spring.bind "CEFC.editForm.pictureFileEditCommands[${commandIndex}].picture.eligibleForPreview"/>
                                                        <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                                                            <option value="false" <#if CEFC.editForm.pictureFileEditCommands[commandIndex].picture.eligibleForPreview == false>selected</#if>>${language.getTextSource('no')}</option>
                                                            <option value="true" <#if CEFC.editForm.pictureFileEditCommands[commandIndex].picture.eligibleForPreview == true>selected</#if>>${language.getTextSource('yes')}</option>
                                                        </select>
                                                    </dd>
                                                </dl>

                                                <a id="picture-delete-link" class="btn btn-danger pull-right delete-picture-btn" onClick="deletePicture('${picture.id}', '${language.getTextSource('cms.picture.confirmDelete')}');"/>
                                                    <span class="glyphicon glyphicon-remove-sign"></span> ${language.getTextSource('cms.deletePicture')}
                                                </a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="2"><hr></td>
                                        </tr>
                                    </#if>
                                </#if>
                            </#list>
                        </table>
                        <@pictureUtil.addPicturesGallery "images-gallery" "car-picture"/>

                    <#elseif CEFC.editForm.id??>
                 	    <h3 class="text-left">${language.getTextSource('noPicturesAvailable')}</h3>
         			</#if>

                    <table id="pictureUploadInputs" style="width:100%">
                        <#assign pictureIndex>${CEFC.editForm.pictureFileEditCommands?size}</#assign>
                        <@spring.bind "CEFC.editForm.pictureFileEditCommands"/>
                        <tr>
                            <td style="width:65%">
                                <input type="file" id="${spring.status.expression}[${pictureIndex}].pictureFile" name="${spring.status.expression}[${pictureIndex}].pictureFile" onChange="displayCarPictureWhenFileSelected(this.files[0], 0);" class="form-control" accept="image/*" size="10"/>
                            </td>
                            <td rowspan="2" style="width:35%; padding-left:40px">
                                <dl class="dl-vertical text-left">
                                    <dt>
                                        ${language.getTextSource('picture.galleryPosition')}
                                    </dt>
                                    <dd>
                                        <@spring.bind "CEFC.editForm.pictureFileEditCommands[${pictureIndex}].picture.galleryPosition"/>
                                        <input id="${spring.status.expression}" name="${spring.status.expression}" type="text" class="pull-right">
                                    </dd>
                                </dl>
                                <dl class="dl-vertical text-left">
                                    <dt>
                                        ${language.getTextSource('picture.eligibleForPreview')}
                                    </dt>
                                    <dd>
                                        <@spring.bind "CEFC.editForm.pictureFileEditCommands[${pictureIndex}].picture.eligibleForPreview"/>
                                        <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                                            <option value="false">${language.getTextSource('no')}</option>
                                            <option value="true">${language.getTextSource('yes')}</option>
                                        </select>
                                    </dd>
                                </dl>
                            </td>
                        </tr>
                        <tr>
                            <td>
                            	<div id="car-picture-area-0">
                            		<img id="car-picture-0" class="thumbnail resizable-img edit-car-picture">
                      			</div>
                      		</td>
                        </tr>
                    </table>
               </div>
		   </div>
	    </div>
   </div>
</div>

<@generic.endPage/>

<script type="application/javascript">
    $(function() {
        addDatePicker();
        addBrakeTypeSelectListener();
    });

    function addDatePicker(){
        $('.input-group.date').datepicker({
            format: "yyyy-mm",
            startView: 1,
            minViewMode: 1,
            maxViewMode: 2,
            autoclose: true
        });

        setupPictureGalleryPositionInputs();
        $('#main-form')[0].enctype = "multipart/form-data";
    }
</script>

<@crudOperations.addEditEntityFunctionScript/>
<@crudOperations.addDeleteEntityFunctionScript/>
<@crudOperations.addCreateEntityFunctionScript/>

<@engine.addLoadEngineFromDBScriptFunction/>
<@engine.addEraseEngineFormFieldsScriptFunction/>
<@engine.addLoadEngineByIdFunctionScript/>
<@engine.addFillEngineInputValuesFunctionScript/>
<@engine.addSaveEngineFunctionScript/>
<@engine.addEditEngineFunctionScript/>
<@engine.addDeleteEngineFunctionScript/>

<@internetContent.addAddInternetContentFunctionScript/>

<@cmsPictureUtil.addPictureUploadBoxFunctionScript/>
<@cmsPictureUtil.addDisplayCarPictureWhenFileSelectedFunctionScript/>

<@brake.addLoadBrakeDiscFieldsScriptFunction/>
<@brake.addDiscBrakeInputsFunctionsScript/>