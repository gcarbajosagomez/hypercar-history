<#include "../applicationMacros/genericFunctionalities.ftl">

<@startPage/> 

 <div id="main-container" class="container">
	<div class="row">			
		<#if exceptionMessage??>
			<div class="col-xs-12 alert alert-danger" role="alert">
				<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
				<span class="sr-only">Error:</span>${exceptionMessage}
			</div>
		<#elseif sucessMessage??>
			<div class="col-xs-12 alert alert-sucess" role="info">
				<span class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span>
				<span class="sr-only">Info:</span>${sucessMessage}
			</div>
	   </#if>           	
	   <div class="col-lg-6 col-sm-6 col-xs-12">		
		   <div class="panel panel-default">
			   <div class="panel-heading">
					<h3 class="text-left">${getTextSource('manufacturer')}</h2>
						
					<input value="${getTextSource('cms.saveOrEditManufacturer')}" class="btn btn-success" onClick="saveOrEditManufacturer();" />
					<#if MEFC.manufacturerForm.id??>
						<input type="button" class="btn btn-danger" value="${getTextSource('cms.deleteManufacturer')}" onClick="deleteManufacturer();"/>
					</#if>
	       			<a href='<@spring.url "/${cmsContext}${manufacturerEditURL}${HTMLSuffix}"/>' class="btn btn-default">${getTextSource('cms.newManufacturer')}</a>             			
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
               <dd>${getTextSource('manufacturer.nationality')}</dd>
                
               <dd>
                   <@spring.formInput "MEFC.manufacturerForm.nationality", "", "text"/>
                   <@spring.showErrors '<br>'/> 
               </dd>
          </dt> 
          <dt>
               <dd>${getTextSource('manufacturer.story')}</dd>
                
               <dd>
                   <@spring.formTextarea "MEFC.manufacturerForm.story", "cols='50' rows='5'"/>
                   <@spring.showErrors '<br>'/> 
               </dd>
          </dt> 
          <dt>
               <dd>${getTextSource('manufacturer.logo')}</dd>
                
               <dd>
                   <@spring.formInput "MEFC.manufacturerForm.logoPictureFile", "accept=image/* size=20", "file"/><br/>
                   <img class="shadowBorderedDiv carPicture" <#if MEFC.manufacturerForm.id??>src="picture.tcp?action=loadManufacturerLogo&manufacturerId=${MEFC.manufacturerForm.id}"</#if>>
               </dd>
          </dt>
          <dt>
                <td colspan="2">
                    <input type="submit" value="Save or edit manufacturer"/>
                    <a href="manufacturerEdit.tcp">New manufacturer</a>
                </dd>
          </dt>
        </table>
     </div>
  </form>

<@endPage/>