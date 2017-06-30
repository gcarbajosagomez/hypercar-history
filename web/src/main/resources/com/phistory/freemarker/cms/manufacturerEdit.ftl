<#import "/spring.ftl" as spring/>
<#import "../applicationMacros/genericFunctionalities.ftl" as generic/>
<#import "../applicationMacros/pageLanguage.ftl" as language/>
<#import "../applicationMacros/crudOperations.ftl" as crudOperations/>
<#import "../applicationMacros/uriUtils.ftl" as uriUtils/>

<#if MEFC.editForm.id??>
	<#assign title>${MEFC.editForm.name}</#assign>
<#else>
	<#assign title>${language.getTextSource('manufacturer.newManufacturer')}</#assign>
</#if>

<@generic.startPage title/>

<div id="main-container" class="container">
	<div class="row">
		<#if crudOperationDTO??>
			<@crudOperations.addOperationResultMessage crudOperationDTO/>
		</#if>
	  	<div class="col-lg-12">
		   <div class="panel panel-default">
			   <div class="panel-heading">
					<h3 class="text-left"><#if MEFC.editForm.id??>${MEFC.editForm.name}<#else>${language.getTextSource('manufacturer')}</#if></h3>

					<input id="save-manufacturer-button" type="button" class="btn btn-success" value="<#if MEFC.editForm.id??>
					                                                                                        ${language.getTextSource('cms.editManufacturer')}
					                                                                                  <#else>
					                                                                                        ${language.getTextSource('cms.saveManufacturer')}
					                                                                                  </#if>"
                                                                                               onClick="<#if MEFC.editForm.id??>
                                                                                                            editEntity('${uriUtils.buildDomainURI("/${cmsContext}${manufacturersURL}/${MEFC.editForm.id}/${editURL}")}', '${language.getTextSource('manufacturer.confirmEdit')}');
                                                                                                        <#else>
                                                                                                            saveEntity('${uriUtils.buildDomainURI("/${cmsContext}${manufacturersURL}/${saveURL}")}', '${language.getTextSource('manufacturer.confirmSave')}');
                                                                                                        </#if>"/>
					<#if MEFC.editForm.id??>
                        <a class="btn btn-danger" onClick="deleteEntity('${uriUtils.buildDomainURI("/${cmsContext}${manufacturersURL}/${MEFC.editForm.id}/${deleteURL}")}',
																		'${language.getTextSource('manufacturer.confirmDelete')}');">
                            <span class="glyphicon glyphicon-remove-sign"></span> ${language.getTextSource('cms.deleteManufacturer')}
						</a>
					</#if>
                   	<a class="btn btn-default" href='${uriUtils.buildDomainURI("/${cmsContext}${manufacturersURL}/${editURL}")}'>
                    	<span class="glyphicon glyphicon-plus-sign"></span> ${language.getTextSource('cms.newManufacturer')}
                   	</a>
			   </div>
			   <div class="panel-body">
			   	   <dl class="dl-horizontal dl-horizontal-edit text-left">
          		   	  <#if MEFC.editForm.id??>
            			  <dt>
                			  ${language.getTextSource('id')}
                		  </dt>
                		  <dd>
							  <h5 class="entity-id text-muted">${MEFC.editForm.id}</h5>
                			  <@spring.formHiddenInput "MEFC.editForm.id", ""/>
						  </dd>
          		      </#if>
          			  <dt>
               			   <@spring.message 'manufacturer.name'/>
                	  </dt>
               		  <dd>
						   <@spring.formInput "MEFC.editForm.name", "class=form-control", "text"/>
               		  </dd>
          			  <dt>
             			   ${language.getTextSource('manufacturer.nationality')}
               		  </dt>
               		  <dd>
                   		   <@spring.formInput "MEFC.editForm.nationality", "class=form-control", "text"/>
               		  </dd>
                      <dt>
						  ${language.getTextSource('manufacturer.historyES')}
                      </dt>
                      <dd class="resizable-dd">
						  <@spring.formTextarea "MEFC.editForm.historyES", "class=form-control cols='60' rows='60'"/>
                      </dd>
                      <dt>
						  ${language.getTextSource('manufacturer.historyEN')}
                      </dt>
                      <dd class="resizable-dd">
						  <@spring.formTextarea "MEFC.editForm.historyEN", "class=form-control cols='60' rows='60'"/>
                      </dd>
          			  <dt>
               			   ${language.getTextSource('manufacturer.logo')}
                	  </dt>
              		  <dd>
                   	 	   <@spring.formInput "MEFC.editForm.previewPictureEditCommand.pictureFile", "class='form-control' accept='image/*' onChange=displayPreviewImageWhenFileSelected(this.files[0]);", "file"/><br/>
               		  </dd>

					  <div id="manufacturer-preview-picture-area">
                      	  	<@spring.bind "MEFC.editForm.previewPictureEditCommand.picture"/>
                            <img id="manufacturer-preview-image" name="${spring.status.expression}" class="thumbnail preview-img pull-right" <#if MEFC.editForm.id??>src='<@spring.url "/${picturesURL}/${loadManufacturerLogoAction}?${id}=${MEFC.editForm.id}"/>'</#if>
                      </div>  
             	 </dl>
     	   </div>
        </div>
    </div>
</div>

<@generic.endPage/>

<script type="application/javascript">

    function displayPreviewImageWhenFileSelected(previewFile) {
        var reader = new FileReader();

        reader.onload = function (e) {
            var img = $('#manufacturer-preview-image')[0];
            img.src = reader.result;

            $('#manufacturer-preview-picture-area').append(img);
        }

        reader.readAsDataURL(previewFile);
    }

    $(function () {
        $('#main-form')[0].enctype = "multipart/form-data";
    });

</script>

<@crudOperations.addEditEntityFunctionScript/>
<@crudOperations.addDeleteEntityFunctionScript/>
<@crudOperations.addCreateEntityFunctionScript/>