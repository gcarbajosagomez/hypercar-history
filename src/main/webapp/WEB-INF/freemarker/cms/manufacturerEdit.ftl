<#include "../applicationMacros/genericFunctionalities.ftl">

<#if MEFC.manufacturerForm.id??>
	<#assign title>${MEFC.manufacturerForm.name}</#assign>
<#else>
	<#assign title>${getTextSource('manufacturer.newManufacturer')}</#assign>
</#if>

<@startPage title/> 

<div id="main-container" class="container">
	<div class="row">		
		<@addEditOperationResultMessage exceptionMessage!"", successMessage!""/>   
	  	<div class="col-lg-9 col-sm-12 col-xs-12">		
		   <div class="panel panel-default">
			   <div class="panel-heading">
					<h3 class="text-left">${getTextSource('manufacturer')}</h2>
						
					<input type="button" class="btn btn-success" value="${getTextSource('cms.saveOrEditManufacturer')}" onClick="saveOrEditEntity($('#main-form'), '${getTextSource('manufacturer.confirmSaveOrEdit')}');" />
					<#if MEFC.manufacturerForm.id??>
						<input type="button" class="btn btn-danger" value="${getTextSource('cms.deleteManufacturer')}" onClick="deleteEntity('<@spring.url "/${cmsContext}${manufacturersURL}/${MEFC.manufacturerForm.id}/${deleteURL}"/>', '${getTextSource('manufacturer.confirmDelete')}');"/>
					</#if>
	       			<a href='<@spring.url "/${cmsContext}${manufacturersURL}/${editURL}"/>' class="btn btn-default">${getTextSource('cms.newManufacturer')}</a>             			
			   </div>
			   <div class="panel-body">
			   	   <dl class="dl-horizontal text-left">
          		   	  <#if MEFC.manufacturerForm.id??>
            			  <dt>          
                			  ${getTextSource('id')}
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
             			   ${getTextSource('manufacturer.nationality')}
               		  </dt>                
               		  <dd>
                   		   <@spring.formInput "MEFC.manufacturerForm.nationality", "class=form-control", "text"/>
                   		   <@spring.showErrors '<br>'/> 
               		  </dd>           
          			  <dt>
              			   ${getTextSource('manufacturer.story')}
                 	  </dt>
                 	  <div>
               		      <dd>               		  	   
                   		   		<@spring.formTextarea "MEFC.manufacturerForm.story", "class=form-control cols='50' rows='1'"/>
                   		   		<@spring.showErrors '<br>'/>                   		 
               		      </dd>          
               		  </div> 
          			  <dt>
               			   ${getTextSource('manufacturer.logo')}
                	  </dt>
              		  <dd>
                   	 	   <@spring.formInput "MEFC.manufacturerForm.previewPictureEditCommand.pictureFile", "class='form-control' accept='image/*' size=20 onChange=displayPreviewImageWhenFileSelected(this.files[0]);", "file"/><br/>
               		  	   <@spring.showErrors '<br>'/>    
               		  </dd>

					  <div id="manufacturer-preview-picture-area">                 	    
                      	  	<@spring.bind "MEFC.manufacturerForm.previewPictureEditCommand.picture"/>               		                      
                            <img id="manufacturer-preview-image" name="${spring.status.expression}" class="thumbnail preview-img" <#if MEFC.manufacturerForm.id??>src='<@spring.url "/${picturesURL}/${loadManufacturerLogoAction}?${manufacturerId}=${MEFC.manufacturerForm.id}"/>'</#if>                                                 
                      </div>  
             	 </dl>
     	   </div>
        </div>
    </div>
</div>

<@endPage/>

<script type="text/javascript">

	function displayPreviewImageWhenFileSelected(previewFile)
	{
		var reader = new FileReader();
	  
		reader.onload = function(e)
		{
			var img = $('#manufacturer-preview-image')[0];
			img.src = reader.result;
		      					
			$('#manufacturer-preview-picture-area').append(img);
		}
	  
		reader.readAsDataURL(previewFile); 
	}
	
	$(function()
    {              								  		
		$('#main-form')[0].enctype="multipart/form-data";
    });

</script>