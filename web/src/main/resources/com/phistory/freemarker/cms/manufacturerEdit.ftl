<#import "/spring.ftl" as spring/>
<#import "../applicationMacros/genericFunctionalities.ftl" as generic/>
<#import "../applicationMacros/pageLanguage.ftl" as language/>
<#import "../applicationMacros/crudOperations.ftl" as crudOperations/>

<#if MEFC.manufacturerForm.id??>
	<#assign title>${MEFC.manufacturerForm.name}</#assign>
<#else>
	<#assign title>${language.getTextSource('manufacturer.newManufacturer')}</#assign>
</#if>

<@generic.startPage title/>

<div id="main-container" class="container">
	<div class="row">		
		<@crudOperations.addOperationResultMessage exceptionMessage!"", successMessage!""/>
	  	<div class="col-lg-9 col-sm-12 col-xs-12">		
		   <div class="panel panel-default">
			   <div class="panel-heading">
					<h3 class="text-left">${language.getTextSource('manufacturer')}</h3>
						
					<input id="save-manufacturer-button" type="button" class="btn btn-success" value="<#if MEFC.manufacturerForm.id??>
					                                                                                        ${language.getTextSource('cms.editManufacturer')}
					                                                                                  <#else>
					                                                                                        ${language.getTextSource('cms.saveManufacturer')}
					                                                                                  </#if>"
                                                                                               onClick="<#if MEFC.manufacturerForm.id??>
                                                                                                            editEntity('<@spring.url "/${cmsContext}${manufacturersURL}/${MEFC.manufacturerForm.id}/${editURL}"/>', '${language.getTextSource('manufacturer.confirmEdit')}');
                                                                                                        <#else>
                                                                                                            saveEntity('<@spring.url "/${cmsContext}${manufacturersURL}/${saveURL}"/>', '${language.getTextSource('manufacturer.confirmSave')}');
                                                                                                        </#if>"/>
					<#if MEFC.manufacturerForm.id??>
						<input id="delete-manufacturer-button" type="button" class="btn btn-danger" value="${language.getTextSource('cms.deleteManufacturer')}" onClick="deleteEntity('<@spring.url "/${cmsContext}${manufacturersURL}/${MEFC.manufacturerForm.id}/${deleteURL}"/>', '${language.getTextSource('manufacturer.confirmDelete')}');"/>
					</#if>
	       			<a href='<@spring.url "/${cmsContext}${manufacturersURL}/${editURL}"/>' class="btn btn-default">${language.getTextSource('cms.newManufacturer')}</a>
			   </div>
			   <div class="panel-body">
			   	   <dl class="dl-horizontal dl-horizontal-edit text-left">
          		   	  <#if MEFC.manufacturerForm.id??>
            			  <dt>          
                			  ${language.getTextSource('id')}
                		  </dt>
                		  <dd>
							  <h5 class="text-muted">${MEFC.manufacturerForm.id}</h5>
                			  <@spring.formHiddenInput "MEFC.manufacturerForm.id", ""/>
						  </dd>
          		      </#if>
          			  <dt>
               			   <@spring.message 'manufacturer.name'/>
                	  </dt>
               		  <dd> 
						   <@spring.formInput "MEFC.manufacturerForm.name", "class=form-control", "text"/>
                           <@spring.showErrors '<br>'/>  
               		  </dd>
          			  <dt>
             			   ${language.getTextSource('manufacturer.nationality')}
               		  </dt>                
               		  <dd>
                   		   <@spring.formInput "MEFC.manufacturerForm.nationality", "class=form-control", "text"/>
                   		   <@spring.showErrors '<br>'/> 
               		  </dd>
                      <dt>
						  ${language.getTextSource('manufacturer.historyES')}
                      </dt>
                      <dd class="resizable-dd">
						  <@spring.formTextarea "MEFC.manufacturerForm.historyES", "class=form-control cols='50' rows='6'"/>
                      </dd>
                      <dt>
						  ${language.getTextSource('manufacturer.historyEN')}
                      </dt>
                      <dd class="resizable-dd">
						  <@spring.formTextarea "MEFC.manufacturerForm.historyEN", "class=form-control cols='50' rows='6'"/>
                      </dd>
          			  <dt>
               			   ${language.getTextSource('manufacturer.logo')}
                	  </dt>
              		  <dd>
                   	 	   <@spring.formInput "MEFC.manufacturerForm.previewPictureEditCommand.pictureFile", "class='form-control' accept='image/*' size=20 onChange=displayPreviewImageWhenFileSelected(this.files[0]);", "file"/><br/>
               		  	   <@spring.showErrors '<br>'/>    
               		  </dd>

					  <div id="manufacturer-preview-picture-area">                 	    
                      	  	<@spring.bind "MEFC.manufacturerForm.previewPictureEditCommand.picture"/>               		                      
                            <img id="manufacturer-preview-image" name="${spring.status.expression}" class="thumbnail preview-img pull-right" <#if MEFC.manufacturerForm.id??>src='<@spring.url "/${picturesURL}/${loadManufacturerLogoAction}?${id}=${MEFC.manufacturerForm.id}"/>'</#if>
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