<#import "/spring.ftl" as spring/>
<#import "../applicationMacros/genericFunctionalities.ftl" as generic/>
<#import "../applicationMacros/pageLanguage.ftl" as language/>

<#if CEFC.carForm.id??>
	<#assign title>${CEFC.carForm.manufacturer.name} ${CEFC.carForm.model} ${language.getTextSource('car.details.dataAndPictures')?lower_case}</#assign>
<#else>
	<#assign title>${language.getTextSource('car.newCar')}</#assign>
</#if>

<@generic.startPage title/> 

<div id="main-container" class="container">
	<div class="row">
	   <@generic.addOperationResultMessage exceptionMessage!"", successMessage!""/>            	
	   <div class="col-lg-6 col-sm-6 col-xs-12">		
		   <div class="panel panel-default">
			   <div class="panel-heading">
					<h3 class="text-left">${language.getTextSource('car')}</h3>
					
					<input type="button" class="btn btn-success" value="<#if CEFC.carForm.id??>${language.getTextSource('cms.editCar')}<#else>${language.getTextSource('cms.saveCar')}</#if>" onClick="saveOrEditCar();"/>
					<#if CEFC.carForm.id??>
						<input type="button" class="btn btn-danger" value="${language.getTextSource('cms.deleteCar')}" onClick="deleteEntity('<@spring.url "/${cmsContext}${carsURL}/${CEFC.carForm.id}/${deleteURL}"/>', '${language.getTextSource('car.confirmDelete')}');"/>
					</#if>
	       			<a href='<@spring.url "/${cmsContext}${carsURL}/${editURL}"/>' class="btn btn-default">${language.getTextSource('cms.newCar')}</a>   
			   </div>
			   <div class="panel-body">
			   	   <dl class="dl-horizontal dl-horizontal-edit text-left">
			      	  <#if CEFC.carForm.id??>
                      	<dt>         
                          	${language.getTextSource('id')}           
                      	</dt>
                      	<dd>
                        	<h5 class="text-muted">${CEFC.carForm.id}</h5>
                         	<@spring.formHiddenInput "CEFC.carForm.id", ""/>
							<@spring.bind "CICEFC.carInternetContentForms[0].car"/> 
							<input type="hidden" name="${spring.status.expression}" value="${CEFC.carForm.id}">							
                        </dd>
                      </#if>
                      <dt>         
                           ${language.getTextSource('car.manufacturer')}</h5>                     
                      </dt>
                      <dd>
                           <@spring.bind "CEFC.carForm.manufacturer"/>
                       
                           <select name="${spring.status.expression}" class="form-control">
                              <#list manufacturers as manufacturer>
                                 <option value="${manufacturer.id}"<#if spring.status.value?? && manufacturer.id == spring.status.value?default(-1)?number> selected</#if>>${manufacturer.name}</option>
                              </#list>
                           </select>
                      </dd>
                      <dt>         
                           ${language.getTextSource('car.model')}  
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.carForm.model", "class=form-control", "text"/>
                           <@spring.showErrors '<br>', 'control-label has-error'/>  
                      </dd>
                      <dt>         
                           ${language.getTextSource('car.engineLayout')}
                      </dt>
                      <dd>
                           <@spring.bind "CEFC.carForm.engineLayout"/>
                           
                           <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                               <#list engineLayouts as engineLayout>
                                   <option value="${engineLayout}"<#if spring.status.value?? && engineLayout == spring.status.value?default("")>selected</#if>>${language.getTextSource('car.engineLayout.${engineLayout}')}<option>
                               </#list>
                           </select> 
                           
                           <@spring.showErrors '<br>'/>  
                      </dd>
                      <dt>         
                           ${language.getTextSource('car.bodyShape')} 
                      </dt>
                      <dd>
                           <@spring.bind "CEFC.carForm.bodyShape"/>                               
                           
                           <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                               <#list bodyShapes as bodyShape>
                                   <option value="${bodyShape}"<#if spring.status.value?? && bodyShape == spring.status.value?default("")>selected</#if>>${language.getTextSource('car.bodyShape.${bodyShape}')}</option>
                               </#list>
                           </select> 
                           
                           <@spring.showErrors '<br>'/>  
                      </dd>
                      <dt>         
                           ${language.getTextSource('car.seatsconfig')} 
                      </dt>
                      <dd>
                           <@spring.bind "CEFC.carForm.seatsConfig"/>
                            
                           <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                               <#list seatsConfigs as seatsConfig>
                                   <option value="${seatsConfig}"<#if spring.status.value?? && seatsConfig == spring.status.value?default("")>selected</#if>>${language.getTextSource('car.seatsConfig.${seatsConfig}')}</option>
                               </#list>
                           </select> 
                           
                           <@spring.showErrors '<br>'/>  
                      </dd>
                      <dt>         
                           ${language.getTextSource('car.productionType')} 
                      </dt>
                      <dd>
                           <@spring.bind "CEFC.carForm.productionType"/>                               
                           
                           <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                               <#list productionTypes as productionType>
                                   <option value="${productionType}"<#if spring.status.value?? && productionType == spring.status.value?default("")>selected</#if>>${language.getTextSource('car.productionType.${productionType.getName()}')}</option>
                               </#list>
                           </select> 
                           
                           <@spring.showErrors '<br>'/>  
                      </dd>
                      <dt>         
                           ${language.getTextSource('car.productionStartDate')}
                      </dt>
                      <dd>    
                           <div class="input-group date">                                 
                           	   <@spring.formInput "CEFC.carForm.productionStartDate", "class=form-control"/>
							   <span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
						   </div>
                      </dd>
                      <dt>         
                           ${language.getTextSource('car.productionEndDate')} 
                      </dt>
                      <dd>
                           <div class="input-group date">                                 
                           	   <@spring.formInput "CEFC.carForm.productionEndDate", "class=form-control"/>
							   <span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
						   </div>
                      </dd>
                      <dt>         
                           ${language.getTextSource('car.weight')}</td>
                      </dt>		
                      <dd>                     
							<@spring.formInput "CEFC.carForm.weight", "class=form-control placeholder=${language.getTextSource('Kg')}", "text"/>
                          	<@spring.showErrors '<br>'/>  	
                      </dd>
                      <dt>         
                           ${language.getTextSource('car.length')} 
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.carForm.length", "class=form-control placeholder=${language.getTextSource('MM')}", "text"/>	 
                           <@spring.showErrors 'br'/>  
                      </dd>                     
                      <dt>         
                           ${language.getTextSource('car.width')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.carForm.width", "class=form-control placeholder=${language.getTextSource('MM')}", "text"/>
                           <@spring.showErrors 'br'/>  
                      </dd>   
					  <dt>         
                           ${language.getTextSource('car.height')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.carForm.height", "class=form-control placeholder=${language.getTextSource('MM')}", "text"/>
                           <@spring.showErrors 'br'/>  
                      </dd>                      
                      <dt>         
                           ${language.getTextSource('car.acceleration')} 
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.carForm.acceleration", "class=form-control placeholder=${language.getTextSource('S')}", "text"/>
                           <@spring.showErrors '<br>'/>   
                      </dd>
                      <dt>         
                           ${language.getTextSource('car.topSpeed')} 
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.carForm.topSpeed", "class=form-control placeholder=${language.getTextSource('Km/h')}", "text"/>
                           <@spring.showErrors '<br>'/>  
                      </dd>
                      <dt>         
                           ${language.getTextSource('car.fuelTankCapacity')}                                
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.carForm.fuelTankCapacity", "class=form-control placeholder=${language.getTextSource('L')}", "text"/> 
                           <@spring.showErrors '<br>'/>  
                      </dd>
                      <dt>         
                           ${language.getTextSource('car.fuelConsumption')} 
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.carForm.fuelConsuption", "class=form-control placeholder=${language.getTextSource('L/100Km')}", "text"/>
                           <@spring.showErrors '<br>'/>   
                      </dd>
                      <dt>         
                           ${language.getTextSource('car.driveWheel')} 
                      </dt>
                      <dd>
                           <@spring.bind "CEFC.carForm.driveWheel"/>
                      
                           <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                               <#list driveWheelTypes as driveWheelType>
                                   <option value="${driveWheelType}"<#if spring.status.value?? && driveWheelType == spring.status.value?default("")>selected</#if>>${language.getTextSource('car.driveWheelType.${driveWheelType}')}</option>
                               </#list>
                           </select> 
                           
                           <@spring.showErrors '<br>'/>  
                      </dd>
                      <dt>         
                           ${language.getTextSource('car.roadLegal')} 
                      </dt>
                      <dd>
                           <@spring.bind "CEFC.carForm.roadLegal"/>
                      
                           <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">                              
                           		<option value="true"<#if CEFC.carForm.roadLegal == true>selected</#if>>true</option>                       
                           		<option value="false"<#if CEFC.carForm.roadLegal == false>selected</#if>>false</option>
                           </select>
                      </dd>
                      <dt>         
                           ${language.getTextSource('car.previewImage')} 
                      </dt>
                      <dd>
						   <@spring.formInput "CEFC.carForm.previewPictureEditCommand.pictureFile", "class=form-control accept=image/* size=20 onChange=displayPreviewImageWhenFileSelected(this.files[0]);", "file"/><br/>                       
                      </dd>
                          
                      <div id="car-preview-picture-area">                 	    
                      	  	<@spring.bind "CEFC.carForm.previewPictureEditCommand.picture"/>               		                      
                            <img id="car-preview-image" name="${spring.status.expression}" class="thumbnail preview-img" <#if CEFC.carForm.id??>src='<@spring.url "/${picturesURL}/${loadCarPreviewAction}?${carId}=${CEFC.carForm.id}"/>'</#if>                                              
                      </div>	                            
				   </dl>
			   </div>			
		   </div>
		   <div class="panel panel-default">
			   <div class="panel-heading">
			       <h3 class="text-left">${language.getTextSource('engine')}</h3>
					
				   <div>
				   	    <a class="btn btn-info" onClick="loadEngineFromDB();">
				   	   		<i class="fa fa-database"></i> ${language.getTextSource('cms.loadEngineFromDb')}
				   	   	</a>				   	   
				   	   	<a id="engine-save-or-edit-link" class="btn btn-success" <#if CEFC.carForm.engineForm.id??>		
																					onClick="editEngine();">
																					<span id="engine-save-or-edit-span" class="glyphicon glyphicon-plus-sign"> ${language.getTextSource('cms.editEngine')}</span> 
												   								<#else>
												   		 							onClick="saveEntity('<@spring.url "/${cmsContext}${enginesURL}/${saveURL}"/>', '${language.getTextSource('engine.confirmSave')}');">
																					<span id="engine-save-or-edit-span" class="glyphicon glyphicon-plus-sign"> ${language.getTextSource('cms.saveEngine')}</span> 
												   								</#if>
						</a>
				   	   	<a class="btn btn-default" onClick="eraseEngineFormFields();">
					   	   		<span class="glyphicon glyphicon-plus-sign"></span> ${language.getTextSource('cms.newEngine')}
					   	</a>
					   <#if CEFC.carForm.engineForm.id??>
					   		<a id="engine-delete-link" class="btn btn-danger" onClick="deleteEntity($(#main-form), '<@spring.url "/${cmsContext}${enginesURL}/${CEFC.carForm.engineForm.id}/${deleteURL}"/>', "${language.getTextSource('engine.confirmDelete')}");"/>
								<span id="engine-delete-span" class="glyphicon glyphicon-remove-sign"></span> ${language.getTextSource('cms.deleteEngine')}
							</a>
					   </#if>
				   </div>
			   </div>
			   <div id="engine-main-div" class="panel-body">	
			   	   <dl id="engine-code-selection-table" class="dl-horizontal dl-horizontal-edit text-left sr-only">
				      <dt>
				   	       ${language.getTextSource('engine.codes')}                   
                   	       <#if engines?? && (engines?size > 0)>
                               <dd>
								   <select class="form-control" onChange="loadEngineById(this.value);">
                              			<option value=""> 
                                        <#list engines as engine>
                                        	<option value="${engine.id}"<#if CEFC.carForm.engineForm.code?? && CEFC.carForm.engineForm.code == engine.code> selected</#if>>${engine.code}</option>
                                      	</#list>
                                   <select>
                               </dd>
                           </#if>
                      </dt>
                   </dl>					   
                   <dl class="dl-horizontal dl-horizontal-edit text-left">                          
                      <dt id="engine-id-dt" class="<#if !CEFC.carForm.engineForm.id??>sr-only</#if>">         
                          ${language.getTextSource('id')}           
                      </dt>
                      <dd id="engine-id-dd" class="<#if !CEFC.carForm.engineForm.id??>sr-only</#if>">
                          <@spring.bind "CEFC.carForm.engineForm.id"/>
                                    
                          <label id="${spring.status.expression}.label"> 
                          	 <#if CEFC.carForm.engineForm.id??>                                        	
                           	     <h5 class="text-muted">${CEFC.carForm.engineForm.id}</h5>
                           	 </#if>                                 	
                          </label>                              
                                                 
                          <@spring.formHiddenInput "CEFC.carForm.engineForm.id", ""/>
                      </dd>
                      <dt>         
                           ${language.getTextSource('engine.code')}      
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.carForm.engineForm.code", "class=form-control", "text"/>
                      </dd> 
                      <dt>         
                           ${language.getTextSource('engine.type')}      
                      </dt>
                      <dd> 
                           <@spring.bind "CEFC.carForm.engineForm.type"/>
                           
                           <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">                                    
                               <#list engineTypes as engineType>
                                   <option value="${engineType}"<#if spring.status.value?? && engineType == spring.status.value?default("")>selected</#if>>${language.getTextSource('engine.type.${engineType}')}</option>
                               </#list>
                           </select> 
                           
                           <@spring.showErrors '<br>'/>  
                      </dd> 
                      <dt>         
                           ${language.getTextSource('engine.cylinderDisposition')}      
                      </dt>
                      <dd>
                           <@spring.bind "CEFC.carForm.engineForm.cylinderDisposition"/>
                           
                           <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                               <#list engineCylinderDispositions as engineCylinderDisposition>
                                   <option value="${engineCylinderDisposition}"<#if spring.status.value?? && engineCylinderDisposition == spring.status.value?default("")>selected</#if>>${engineCylinderDisposition}</option>
                               </#list>
                           </select> 
                           
                           <@spring.showErrors '<br>'/>  
                      </dd> 
                      <dt>         
                           ${language.getTextSource('engine.numberOfCylinders')}      
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.carForm.engineForm.numberOfCylinders", "class=form-control", "text"/>
                           <@spring.showErrors '<br>'/>  
                      </dd> 
                      <dt>         
                           ${language.getTextSource('engine.numberOfValves')}      
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.carForm.engineForm.numberOfValves", "class=form-control", "text"/>
                           <@spring.showErrors '<br>'/>  
                      </dd> 
                      <dt>         
                           ${language.getTextSource('engine.displacement')}      
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.carForm.engineForm.size", "class=form-control placeholder=${language.getTextSource('CM3')}", "text"/>
                           <@spring.showErrors '<br>'/>  
                      </dd> 
                      <dt>         
                           ${language.getTextSource('engine.maxPower')}      
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.carForm.engineForm.maxPower", "class=form-control placeholder=${language.getTextSource('HP')}", "text"/>
                           <@spring.showErrors '<br>'/>  
                      </dd>                       
                      <dt>         
                           ${language.getTextSource('engine.maxRPM')}      
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.carForm.engineForm.maxRPM", "class=form-control placeholder=${language.getTextSource('RPM')}", "text"/>
                           <@spring.showErrors '<br>'/>  
                      </dd>
                      <dt>         
                           ${language.getTextSource('engine.maxPowerRPM')}      
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.carForm.engineForm.maxPowerRPM", "class=form-control placeholder=${language.getTextSource('RPM')}", "text"/>
                           <@spring.showErrors '<br>'/>  
                      </dd> 
                      <dt>         
                           ${language.getTextSource('engine.maxTorque')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.carForm.engineForm.maxTorque", "class=form-control placeholder=${language.getTextSource('NM')}", "text"/>
                           <@spring.showErrors '<br>'/>  
                      </dd>
                      <dt>         
                           ${language.getTextSource('engine.maxTorqueRPM')}
                      </dt>
                      <dd>
                           <@spring.formInput "CEFC.carForm.engineForm.maxTorqueRPM", "class=form-control placeholder=${language.getTextSource('RPM')}", "text"/>
                           <@spring.showErrors '<br>'/>  
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
                       <@spring.formHiddenInput "CEFC.carForm.brakeSetForm.id", ""/>
                       
                       <@writeBrakeEditFields CEFC.carForm.brakeSetForm.frontBrake "CEFC.carForm.brakeSetForm.frontBrake" "FRONT"/> 
                       <@writeBrakeEditFields CEFC.carForm.brakeSetForm.backBrake "CEFC.carForm.brakeSetForm.backBrake" "BACK"/>       
                   </dl>
               </div>
		   </div>	
		   <div class="panel panel-default">
			   <div class="panel-heading">
					<h3 class="text-left">${language.getTextSource('transmission')}</h3>
			   </div>
			   <div class="panel-body">
				   <dl class="dl-horizontal dl-horizontal-edit text-left">
				       <#if CEFC.carForm.transmissionForm?? && CEFC.carForm.transmissionForm.id??>
                           <dt>         
                               ${language.getTextSource('id')} 
                           </dt>
                           <dd>
                         	   <h5 class="text-muted">${CEFC.carForm.transmissionForm.id}</h5>
                         	   <@spring.formHiddenInput "CEFC.carForm.transmissionForm.id", ""/>
                           </dd>
                       </#if>
                       <dt>         
                             ${language.getTextSource('transmission.type')}        
                       </dt>
                       <dd>
                         	 <@spring.bind "CEFC.carForm.transmissionForm.type"/>
                       
                             <select name="${spring.status.expression}" class="form-control">
                                 <#list transmissionTypes as transmissionType>
                                     <option value="${transmissionType}"<#if spring.status.value?? && transmissionType == spring.status.value>selected</#if>><h3 class="capitalizedText">${language.getTextSource('transmission.type.${transmissionType}')}</h3></option>
                                 </#list>
                             </select>  
                             
                             <@spring.showErrors '<br>'/>  
                       </dd>
                       <dt>         
                             ${language.getTextSource('transmission.numOfGears')}   
                       </dt>
                       <dd>
                             <@spring.formInput "CEFC.carForm.transmissionForm.numOfGears", "class=form-control", "text"/>
                             <@spring.showErrors '<br>'/>  
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
                       <@spring.formHiddenInput "CEFC.carForm.tyreSetForm.id", "class=form-control"/>
                       
                       <@writeTyreEditFields CEFC.carForm.tyreSetForm.frontTyre "CEFC.carForm.tyreSetForm.frontTyre" "FRONT"/> 
                       <@writeTyreEditFields CEFC.carForm.tyreSetForm.backTyre "CEFC.carForm.tyreSetForm.backTyre" "BACK"/>           
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
				   <#if (CICEFC.carInternetContentForms?size > 0)>	
					   <#list CICEFC.carInternetContentForms as carInternetContentForm>
						   <#assign carInternetContentFormIndex = carInternetContentForm?index>
				  		   <div class="well well-lg"> 		 
			                   <dl class="dl-horizontal dl-horizontal-edit text-left">
			                   		<dt>         
			                             ${language.getTextSource('cms.car.internetContent.link')}   
			                        </dt>
									<dd>
			                   			<@spring.formInput "CICEFC.carInternetContentForms[${carInternetContentFormIndex}].link", "class=form-control", "text"/>
									</dd>
									<dt>         
			                             ${language.getTextSource('cms.car.internetContent.type')}
			                        </dt>
			                        <dd>
			                        	<@spring.bind "CICEFC.carInternetContentForms[${carInternetContentFormIndex}].type"/>
			
										<select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
			                            	<#list carInternetContentTypes as carInternetContentType>
			                                	<option value="${carInternetContentType}"<#if spring.status.value?? && carInternetContentType == spring.status.value?default("")>selected</#if>>${language.getTextSource('cms.car.internetContent.type.${carInternetContentType}')}</option>
			                               	</#list>
			                            </select> 
									</dd>
									<dt>         
			                             ${language.getTextSource('cms.car.internetContent.contentLanguage')}   
			                        </dt>
									<dd>
			                   			<@spring.bind "CICEFC.carInternetContentForms[${carInternetContentFormIndex}].contentLanguage"/>
			
										<select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
			                            	<#list carInternetContentLanguages as contentLanguage>
			                                	<option value="${contentLanguage}"<#if spring.status.value?? && contentLanguage == spring.status.value?default("")>selected</#if>>${language.getTextSource('cms.car.internetContent.contentLanguage.${contentLanguage.getName()}')}</option>
			                               	</#list>
			                            </select> 
									</dd>						
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
		                   			<@spring.formInput "CICEFC.carInternetContentForms[0].link", "class=form-control", "text"/>
								</dd>
								<dt>         
		                             ${language.getTextSource('cms.car.internetContent.type')}
		                        </dt>
		                        <dd>
		                        	<@spring.bind "CICEFC.carInternetContentForms[0].type"/>
		
									<select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
		                            	<#list carInternetContentTypes as carInternetContentType>
		                                	<option value="${carInternetContentType}"<#if spring.status.value?? && carInternetContentType == spring.status.value?default("")>selected</#if>>${language.getTextSource('cms.car.internetContent.type.${carInternetContentType}')}</option>
		                               	</#list>
		                            </select> 
								</dd>
								<dt>         
		                             ${language.getTextSource('cms.car.internetContent.contentLanguage')}   
		                        </dt>
								<dd>
		                   			<@spring.bind "CICEFC.carInternetContentForms[0].contentLanguage"/>
                           
                           			<select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                               			<#list carInternetContentLanguages as contentLanguage>
                                   			<option value="${contentLanguage}"<#if spring.status.value?? && contentLanguage == spring.status.value?default("")>selected</#if>>${language.getTextSource('cms.car.internetContent.contentLanguage.${contentLanguage.getName()}')}</option>
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
                    <#if pictureIds?has_content>
						<@generic.addBlueImpGallery/>

                        <#list pictureIds?chunk(2) as row>                       		  
                            <#list row as pictureId>                                								
                                <a href='<@spring.url "/${picturesURL}/${loadCarPictureAction}?${picId}=${pictureId}"/>' title="${CEFC.carForm.manufacturer.name}${CEFC.carForm.model}" data-gallery>
                             		<img class="col-lg-6 col-md-12 col-sm-12 thumbnail car-picture" src="/${picturesURL}/${loadCarPictureAction}?${picId}=${pictureId}" alt="${CEFC.carForm.manufacturer.name} ${CEFC.carForm.model}">
                               	</a> 
                            </#list>
                        </#list>    
                    <#elseif CEFC.carForm.id??>
                 	    <h3 class="text-left">${language.getTextSource('noPicturesAvailable')}</h3>
         			</#if>             			   
                       
                    <table id="pictureUploadInputs">
                        <@spring.bind "CEFC.carForm.pictureFiles"/>
                        <tr>      
                            <td><input type="file" id="${spring.status.expression}" name="${spring.status.expression}[0]" onChange="displayCarPictureWhenFileSelected(this.files[0]);" class="form-control" accept="image/*" size="10"/></td>
                            <td>
                            	<div id="car-picture-area-0">           		                      
                            		<img id="car-picture-0" class="thumbnail preview-img">                                           
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

<#macro writeBrakeEditFields brake objectBindingPath brakeTrain>
      <div class="panel panel-default">
	      <div class="panel-heading">
		      <h4 class="text-left">${brakeTrain}</h4>						
		  </div>
		  <div class="panel-body">					   
              <dl class="dl-horizontal dl-horizontal-edit text-left">
                  <#if brake.id??>
                      <dt>         
                          ${language.getTextSource('id')}           
                      </dt>
                      <dd>
                          <h5 class="text-muted">${brake.id}</h5>                           
                          <@spring.formHiddenInput "${objectBindingPath?string}.id", ""/>                        
                      </dd>
                  </#if>                  
                  	 <@spring.bind "${objectBindingPath?string}.train"/>  	 
                  	 <input type="hidden" id="${objectBindingPath?string?replace("CEFC.", "")}.train" name="${objectBindingPath?string?replace("CEFC.", "")}.train" class="form-control" value="${brakeTrain}">
                  <dt>         
                       ${language.getTextSource('brake.disc.diameter')}          
                  </dt>
                  <dd>
                       <@spring.formInput "${objectBindingPath?string}.discDiameter", "class=form-control placeholder=${language.getTextSource('MM')}", "text"/>
                       <@spring.showErrors '<br>'/>      	 
                  </dd>  
                  <dt>         
                       ${language.getTextSource('brake.disc.material')}          
                  </dt>
                  <dd>
                       <@spring.bind "${objectBindingPath}.discMaterial"/>
                       
                       <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                           <#list brakeDiscMaterials as brakeDiscMaterial>
                               <option value="${brakeDiscMaterial}"<#if spring.status.value?? && brakeDiscMaterial == spring.status.value?default("")>selected</#if>>${language.getTextSource('brake.disc.material.${brakeDiscMaterial}')}</option>
                           </#list>
                       </select>  
                       
                       <@spring.showErrors '<br>'/>      	 
                  </dd>  
                  <dt>         
                       ${language.getTextSource('brake.caliper.numOfPistons')}          
                  </dt>
                  <dd>
                       <@spring.formInput "${objectBindingPath?string}.caliperNumOfPistons", "class=form-control", "text"/>   
                       <@spring.showErrors '<br>'/>     	 
                  </dd>        
              </dl>
          </div>
      </div>
</#macro> 

<#macro writeTyreEditFields tyre objectBindingPath tyreTrain>
      <div class="panel panel-default">
	      <div class="panel-heading">
		      <h4 class="text-left">${tyreTrain}</h4>						
		  </div>
		  <div class="panel-body">					   
              <dl class="dl-horizontal dl-horizontal-edit text-left">
                  <#if tyre.id??>
                      <dt>         
                          ${language.getTextSource('id')}         
                      </dt>
                      <dd>
                          <h5 class="text-muted">${tyre.id}</h5>
                          <@spring.formHiddenInput "${objectBindingPath}.id", ""/>                          
                      </dd>
                  </#if>
                  	 <@spring.bind "${objectBindingPath?string}.train"/>
                     <input type="hidden" id="${objectBindingPath?string?replace("CEFC.", "")}.train" name="${objectBindingPath?string?replace("CEFC.", "")}.train" class="form-control" value="${tyreTrain}">
                  <dt>         
                        ${language.getTextSource('tyre.width')}    
                  </dt>
                  <dd>
                        <@spring.formInput "${objectBindingPath?string}.width", "class=form-control placeholder=${language.getTextSource('MM')}", "text"/>
                        <@spring.showErrors '<br>'/>  
                  </dd>
                  <dt>         
                        ${language.getTextSource('tyre.profile')}         
                  </dt>
                  <dd>
                        <@spring.formInput "${objectBindingPath?string}.profile", "class=form-control placeholder=${language.getTextSource('MM')}", "text"/>
                        <@spring.showErrors '<br>'/>  
                  </dd>
                  <dt>         
                        ${language.getTextSource('tyre.rimDiameter')}        
                  </dt>
                  <dd>
                        <@spring.formInput "${objectBindingPath}.rimDiameter", "class=form-control placeholder=${language.getTextSource('inch')}", "text"/>
                        <@spring.showErrors '<br>'/>  
                  </dd>
              </dl>
          </div>
      </div>      
</#macro> 

<script type="text/javascript">

        function loadEngineFromDB()
        {
             $('#engine-code-selection-table').removeClass('sr-only');
        }
       
        function eraseEngineFormFields()
        {     		
       		 <#--By loading a null Id we can easily erase all the inputs -->
       		 fillEngineInputValues({});
       		
       		 $('#engine-code-selection-table').addClass('sr-only');
       		 $('#engine-id-dt').addClass('sr-only');
       		 $('#engine-id-dd').addClass('sr-only');
       		 $('#engine-save-or-edit-link').attr("onClick","saveEntity('<@spring.url "/${cmsContext}${enginesURL}/${saveURL}"/>', '${language.getTextSource('engine.confirmSave')}')");
			 $('#engine-save-or-edit-span').text(" ${language.getTextSource('cms.saveEngine')}");
        }

        function loadEngineById(engineId)
        {   	
       		 if (!ajaxCallBeingProcessed)
	         {	         			
	             ajaxCallBeingProcessed = true;
	         	
	         	 $('.sr-only').removeClass('sr-only');	
       			
	           	 $.ajax({
	        			type: 'GET',
	            	    url: '<@spring.url "/${cmsContext}${enginesURL}/"/>' + engineId,
	                	contentType: 'application/json; charset=UTF-8',
						beforeSend: function(xhr)
						{
							$('#engine-main-div').block({ 
								css: {         										
										border:         '0px solid', 
										backgroundColor:'rgba(94, 92, 92, 0)'
								},
								message: '<div class="row"><h1 class="col-lg-6 col-md-6 col-sm-12 col-xs-12" style="color: #fff">${language.getTextSource('loading')}</h1><i class="col-lg-4 col-md-4 col-sm-12 col-xs-12 fa fa-circle-o-notch fa-4x fa-spin blue"></i></div>' 
							});      						
			
							addCrsfTokenToAjaxRequest(xhr);
						}
				  	  })
			          .done(function (data)
					  {
			          	 var engine = data;
						 $('#engine-main-div').unblock();       
						
		                 fillEngineInputValues(engine);		                 
       		 			 $('#engine-save-or-edit-link').attr("onClick","editEngine()");
			 			 $('#engine-save-or-edit-span').text(" ${language.getTextSource('cms.editEngine')}");
		                 ajaxCallBeingProcessed = false;
			          }); 
			}
        }   
       
        function fillEngineInputValues(engine)
        {
       	   if (engine != null)
       	   { 
       	  	 if (engine.id != null)
       	  	 {
       	  	    document.getElementById('carForm.engineForm.id.label').innerText 		= engine.id;
       	  	    document.getElementById('carForm.engineForm.id').value 					= engine.id;
       	  	 }
       	  	 else
       	  	 {
       	  	    document.getElementById('carForm.engineForm.id.label').innerText 		= "";	
       	  	    document.getElementById('carForm.engineForm.id').value 					= "";	
       	  	 }
       	  	
       	  	 if (engine.code != null)
       	  	 {
                document.getElementById('carForm.engineForm.code').value 				= engine.code;
             } 
       	  	 else
       	  	 {
       	  	    document.getElementById('carForm.engineForm.code').value 				= "";    	  	
       	  	 }
            
             if (engine.type != null)
       	  	 {
          	    document.getElementById('carForm.engineForm.type').value 				= engine.type;
          	 }
       	  	 else
       	  	 {
       	  	    document.getElementById('carForm.engineForm.type').value 				= "";    	  	
       	  	 }
          	
          	 if (engine.cylinderDisposition != null)
       	  	 {
                document.getElementById('carForm.engineForm.cylinderDisposition').value = engine.cylinderDisposition;
             }
       	  	 else
       	  	 {
       	  	    document.getElementById('carForm.engineForm.cylinderDisposition').value = "";     	  	
       	  	 }
          
             if (engine.numberOfCylinders != null)
       	     {
       	  	    document.getElementById('carForm.engineForm.numberOfCylinders').value 	= engine.numberOfCylinders;
       	  	 }
       	  	 else
       	  	 {
       	  	    document.getElementById('carForm.engineForm.numberOfCylinders').value 	= "";    	  	
       	  	 }
       	  	
       	  	 if (engine.numberOfValves != null)
       	  	 {
                document.getElementById('carForm.engineForm.numberOfValves').value 		= engine.numberOfValves;
             }
       	  	 else
       	  	 {
       	  	    document.getElementById('carForm.engineForm.numberOfValves').value 		= "";    	  	
       	  	 }
            
             if (engine.size != null)
       	  	 {
                document.getElementById('carForm.engineForm.size').value 				= engine.size;
             }
       	  	 else
       	  	 {
       	  	    document.getElementById('carForm.engineForm.size').value 				= "";    	  	
       	  	 }
            
             if (engine.maxPower != null)
       	  	 {
          	    document.getElementById('carForm.engineForm.maxPower').value 			= engine.maxPower;
          	 }
       	  	 else
       	  	 {
       	  	    document.getElementById('carForm.engineForm.maxPower').value 			= "";    	  	
       	  	 }
          	
          	 if (engine.maxPowerRPM != null)
       	  	 {
                document.getElementById('carForm.engineForm.maxPowerRPM').value 		= engine.maxPowerRPM;
             }
       	  	 else
       	  	 {
       	  	    document.getElementById('carForm.engineForm.maxRPM').value 				= "";    	  	
       	  	 }
            
             if (engine.maxTorque != null)
       	  	 {
                document.getElementById('carForm.engineForm.maxTorque').value 			= engine.maxTorque;
             }
       	  	 else
       	  	 {
       	  	    document.getElementById('carForm.engineForm.maxTorque').value 			= "";    	  	
       	  	 }
            
             if (engine.maxTorqueRPM != null)
       	  	 {
                document.getElementById('carForm.engineForm.maxTorqueRPM').value 		= engine.maxTorqueRPM;	
             }
       	  	 else
       	  	 {
       	  	    document.getElementById('carForm.engineForm.maxTorqueRPM').value 		= "";    	  	
       	  	 }
           }
       }

       function addPictureUploadBox()
       {
           var pictureUploadBoxNum = $("input[id^='carForm.pictureFiles']").length;
           var newPictureUploadBox = $('<input>', {'type' 		: 'file',
        	   								   'id' 		: 'carForm.pictureFiles[' + pictureUploadBoxNum + ']', 
                                               'name' 		: 'carForm.pictureFiles[' + pictureUploadBoxNum + ']',
                                               'onChange'	: 'displayCarPictureWhenFileSelected(this.files[0]);', 
                                               'accept' 	: 'image/*',
                                               'size' 		: '10'});

           var numberOfCarPictureAreas = $("[id^='car-picture-area']").length;
           var newCarPictureAreaDiv = $('<div>', {'id' : 'car-picture-area-' + numberOfCarPictureAreas});
           newCarPictureAreaDiv.append($('<img>', {'id' : 'car-picture-' + numberOfCarPictureAreas,
           										   'class' : "thumbnail preview-img"}));

           var pictureFileInputTd = $('<td>');
           pictureFileInputTd.append('<br>');
           pictureFileInputTd.append(newPictureUploadBox);
           var pictureAreaTd = $('<td>');
           pictureAreaTd.append('<br>');
           pictureAreaTd.append(newCarPictureAreaDiv);
           var newTr = $('<tr>');
           newTr.append(pictureFileInputTd);
           newTr.append(pictureAreaTd);
        
           $('#pictureUploadInputs').find('tbody').append(newTr);
    	}

		function displayPreviewImageWhenFileSelected(previewFile)
		{
			var reader = new FileReader();
		  
			reader.onload = function(e)
			{
				var img = $('#car-preview-image')[0];
				img.src = reader.result;
		      					
				$('#car-preview-picture-area').append(img);
			}
	  
			reader.readAsDataURL(previewFile); 
		}
		
		function displayCarPictureWhenFileSelected(previewFile)
		{
			var reader = new FileReader();
		  
			reader.onload = function(e)
			{
                var numberOfCarPictures = $("[id^='car-picture-area']").length;
				<#-- first car-picture-area is number 0 -->
				numberOfCarPictures--;
				var img = $('#car-picture-' + numberOfCarPictures)[0];
				img.src = reader.result;
		      					
				$('#car-picture-area-' + numberOfCarPictures).append(img);
			}
	  
			reader.readAsDataURL(previewFile); 
		}
		
		function saveOrEditCar()
		{
			<#if CEFC.carForm.id??>
				editEntity('<@spring.url "/${cmsContext}${carsURL}/${CEFC.carForm.id}/${editURL}"/>', '${language.getTextSource('car.confirmEdit')}');
			<#else>
				saveEntity('<@spring.url "/${cmsContext}${carsURL}/${saveURL}"/>', '${language.getTextSource('car.confirmSave')}');
			</#if>
		}
		
		function editEngine()
		{
			bootbox.confirm("${language.getTextSource('engine.confirmEdit')}", function(result)
		    {
				//OK button
				if (result == true)
				{
					$.ajax({
					    url: '/${cmsContext}${enginesURL}/${editURL}',
					    type: 'POST',
					    data: JSON.stringify(),
					    contentType: 'application/json; charset=UTF-8',
					    beforeSend: function(xhr)
			    	    {
					    	addCrsfTokenToAjaxRequest(xhr);
			    	    }
					})
					.done(function(data)
					{
						data[${engine}];
					});
				}
		    });
		}

		function addInternetContent()
		{
            var internetContentNum = $("input[id^=carInternetContentForms][id$=link").length;
            var newInternetContentCarFormIdHiddenInput = $('<input>', {'type'  : 'hidden',
																   'id'    : 'carInternetContentForms' + internetContentNum + '.car',													
													      		   'name'  : 'carInternetContentForms[' + internetContentNum + '].car',
													      		   'value' :  '<#if CEFC.carForm.id??>${CEFC.carForm.id}</#if>'});

            var newInternetContentWellDiv = $('<div>', {'class' : 'well well-lg'});
            var newInternetContentDl = $('<dl>', {'class' : 'dl-horizontal dl-horizontal-edit text-left'});
            var newInternetContentLinkDt = $('<dt>');
			newInternetContentLinkDt.append('${language.getTextSource('cms.car.internetContent.link')}');	
			newInternetContentDl.append(newInternetContentLinkDt);
            var newInternetContentLinkDd = $('<dd>');
			newInternetContentLinkDd.append(newInternetContentCarFormIdHiddenInput);	
			newInternetContentLinkDd.append($('<input>', {'type'  : 'text',
													      'id'    : 'carInternetContentForms' + internetContentNum + '.link',													
													      'name'  : 'carInternetContentForms[' + internetContentNum + '].link',
													      'class' : 'form-control'}));
													      
			newInternetContentDl.append(newInternetContentLinkDd);

            var newInternetContentTypeDt = $('<dt>');
			newInternetContentTypeDt.append('${language.getTextSource('cms.car.internetContent.type')}');
			newInternetContentDl.append(newInternetContentTypeDt);
            var newInternetContentTypeDd = $('<dd>');
            var newInternetContentTypeSelect = $('<select>', {'id'    : 'carInternetContentForms' + internetContentNum + '.type',
													      'name'  : 'carInternetContentForms[' + internetContentNum + '].type',
													      'class' : 'form-control'});
													      	
			<#list carInternetContentTypes as carInternetContentType>
				newInternetContentTypeSelect.append($('<option>', {'value' : '${carInternetContentType}'}).text('${language.getTextSource('cms.car.internetContent.type.${carInternetContentType}')}'));
			</#list>

			newInternetContentTypeDd.append(newInternetContentTypeSelect);
			newInternetContentDl.append(newInternetContentTypeDd);

            var newInternetContentLanguageDt = $('<dt>');
			newInternetContentLanguageDt.append('${language.getTextSource('cms.car.internetContent.contentLanguage')}');
			newInternetContentDl.append(newInternetContentLanguageDt);
            var newInternetContentLanguageDd = $('<dd>');
            var newInternetContentLanguageSelect = $('<select>', {'id'    : 'carInternetContentForms' + internetContentNum + '.contentLanguage',
													          'name'  : 'carInternetContentForms[' + internetContentNum + '].contentLanguage',
													          'class' : 'form-control'});														               
													               
			<#list carInternetContentLanguages as contentLanguage>
				newInternetContentLanguageSelect.append($('<option>', {'value' : '${contentLanguage}'}).text('${language.getTextSource('cms.car.internetContent.contentLanguage.${contentLanguage.getName()}')}'));
			</#list>
													          
			newInternetContentLanguageDd.append(newInternetContentLanguageSelect);
			newInternetContentDl.append(newInternetContentLanguageDd);
			
			newInternetContentWellDiv.append(newInternetContentDl);			
			$('#internet-contents-main-panel-body').append(newInternetContentWellDiv);
		}

       	$(function()
       	{
       	   $('.input-group.date').datepicker({
           							   		  format: "yyyy-mm",
    								   		  endDate: "0y",
    								   		  startView: 2,
   									   		  minViewMode: 2,
    								   		  autoclose: true
      								  		});
      								  		
		   $('#main-form')[0].enctype="multipart/form-data";
       	});
	   
</script>