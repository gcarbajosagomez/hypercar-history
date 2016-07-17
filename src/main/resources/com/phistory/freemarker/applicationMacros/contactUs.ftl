<#import "pageLanguage.ftl" as language/>

<#macro createContactUsDialog>
	<div id="contact-us-modal-div" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="contact-us-label" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<div class="row">
						<div class="col-md-8 col-md-8 col-sm-10 col-xs-10">
							<h3 class="modal-title">${language.getTextSource('footer.contactUs')}</h3>
						</div>
						<div class="col-md-4 col-sm-2 col-xs-2">                            
							<button type="button" class="close pull-right" data-dismiss="modal" aria-hidden="true">&times;</button>			
						</div>
					</div>
				</div>
				<div class="modal-body" style="background-color: rgba(0, 0, 0, 0.611765);">
					<div id="contact-us-main-div" class="thumbnail row" style="margin-bottom: 0px;">
						<div class="col-lg-12" style="padding-top: 10px;">    			    								  	
							<div id="contact-us-error-alert-div" class="col-xs-12 alert alert-danger hidden" role="alert">
								<span id="contact-us-error-alert-span" class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
								<span class="sr-only"></span>
							</div>
							<div id="contact-us-success-alert-div" class="col-xs-12 alert alert-success hidden" role="info">
								<span id="contact-us-success-alert-span" class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span>
								<span class="sr-only"></span>
							</div>													    							
							<div class="form-group">
								<label for="contact-us-subject">${language.getTextSource('footer.contactUs.subject')}:</label>
								<input id="contact-us-subject" type="text" class="form-control">	    		    										
							</div>
							<div class="form-group">
								<label for="contact-us-sender-name">${language.getTextSource('footer.contactUs.name')}:</label>
								<input id="contact-us-sender-name" type="text" class="form-control" placeholder="${language.getTextSource('optional')}">	 										
							</div>
							<div class="form-group">
								<label for="contact-us-sender-email">${language.getTextSource('footer.contactUs.email')}:</label>
								<input id="contact-us-sender-email" type="email" class="form-control" placeholder="${language.getTextSource('optional')}">			    										
							</div>
							<div class="form-group">
								<label for="contact-us-message">${language.getTextSource('footer.contactUs.message')}:</label>
							  	<textarea id="contact-us-message" class="form-control" rows="12"></textarea>
							</div>
							<div class="form-group">    														
								<button id="contact-us-send-message-button" class="btn btn-success" onClick="sendContactUsMessage('${language.getTextSource('footer.contactUs.confirmSend')}');">${language.getTextSource('footer.contactUs.sendMessage')}</button>  						
							</div>
						</div>
					</div>
				</div>      											
			</div>
		</div>	
	</div> 

	<script type='text/javascript'>
		function sendContactUsMessage(confirmSendMessage)
		{
			bootbox.confirm(confirmSendMessage, function(result)
			{
				//OK button
				if (result == true)
				{	
					var message =
					{
				       	"subject" : 	$('#contact-us-subject')[0].value,
				       	"senderName" : 	$('#contact-us-sender-name')[0].value,
				       	"senderEmail" : $('#contact-us-sender-email')[0].value,
				       	"message" : 	$('#contact-us-message')[0].value
			        }
					
					$.ajax({            
					  	type: 'POST',
						url: 'contactUs',
						contentType: 'application/json; charset=UTF-8',
						dataType: 'json',
				        data: JSON.stringify(message),
						beforeSend: function(xhr)
					    {
							$('#contact-us-main-div').block({ 
								css: {         										
			        					border:         '0px solid', 
			        					backgroundColor:'rgba(94, 92, 92, 0)'
			    				},
			                	message: '<div class="row"><h1 class="col-lg-6 col-md-6 col-sm-12 col-xs-12" style="color: #fff">${language.getTextSource('loading')}</h1><i id="pagination-loading-gif" class="col-lg-4 col-md-4 col-sm-12 col-xs-12 fa fa-circle-o-notch fa-4x fa-spin blue"></i></div>' 
			            	});
							
							addCrsfTokenToAjaxRequest(xhr);
					    }
					})
					.done(function(data)
					{
						var exceptionMessage = data['contactUsExceptionMessage'];
						var successMessage = data['contactUsSuccessMessage'];
						
						if (exceptionMessage != null)
						{
							
							$('#contact-us-success-alert-span').text('');
							$('#contact-us-success-alert-div').addClass('hidden');
							
							$('#contact-us-error-alert-span').text(' ' + exceptionMessage);
							$('#contact-us-error-alert-div').removeClass('hidden');
						}
						else if (successMessage != null)
						{
							$('#contact-us-error-alert-span').text('');
							$('#contact-us-error-alert-div').addClass('hidden');
							
							$('#contact-us-success-alert-span').text(' ' + successMessage);
							$('#contact-us-success-alert-div').removeClass('hidden');
						}
						
						$('#contact-us-main-div').unblock(); 				
					});
				}
			});	   
		}
	</script>
</#macro>