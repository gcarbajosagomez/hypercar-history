<#import "applicationMacros/pageLanguage.ftl" as language/>

<div class="modal-dialog modal-lg">
	<div class="modal-content">
		<div class="modal-header">
			<div class="row">
				<div class="col-md-8 col-md-8 col-sm-10 col-xs-10">
					<h3 class="modal-title">${language.getTextSource('footer.technologyStack')}</h3>
				</div>
				<div class="col-md-4 col-sm-2 col-xs-2">                            
					<button id="dismiss-technology-stack-modal-button" type="button" class="close pull-right" data-dismiss="modal" aria-hidden="true">&times;</button>			
				</div>
			</div>
		</div>
		<div class="modal-body" style="background-color: rgba(0, 0, 0, 0.611765);">
			<div class="thumbnail row technology-stack" style="margin-bottom: 0px;">
				<div class="col-lg-12" style="padding-top: 10px;">     		    									
					<img src="/resources/img/tech-stack/openshift-logo.png">   										
					<div class="thumbnail row technology-stack-inner-logo">
						<div class="col-lg-12" style="margin-top: 10px;"> 
							<div class="row" style="margin-top: 10px;"> 
								<div class="col-lg-4" style="padding-bottom: 10px;">
									<img class="center-block" src="/resources/img/tech-stack/wildfly-logo.png">
								</div>  			    												
								<div class="col-lg-4" style="padding-bottom: 10px;">
									<img class="center-block" src="/resources/img/tech-stack/git-logo.png">
								</div>
								<div class="col-lg-4" style="padding: 10px;">
									<img class="center-block" src="/resources/img/tech-stack/maven-logo.png">
								</div>
							</div>
							<div class="thumbnail">
								<div class="thumbnail row technology-stack-inner-logo" style="margin-top: 10px; margin-bottom: 10px;">   		    														
									<div class="col-lg-6"> 
										<img class="center-block" src="/resources/img/tech-stack/hibernate-logo.png">
									</div>
									<div class="col-lg-6" style="padding-top: 10px;"> 
										<img class="center-block" src="/resources/img/tech-stack/mysql-logo.png">
									</div>
								</div>
								<div class="thumbnail row technology-stack-inner-logo" style="margin-top: 10px; margin-bottom: 10px;">
									<div class="col-lg-12">  														
										<div class="col-lg-6" style="padding-top: 10px;">
											<div class="col-lg-12">
												<img class="center-block" src="/resources/img/tech-stack/jquery-logo.png">
											</div>
												<div class="col-lg-12">
												<img class="center-block" src="/resources/img/tech-stack/ajax-logo.png" style="padding-top: 10px;">	
											</div>  
											<div class="col-lg-12"> 
												<img class="center-block" src="/resources/img/tech-stack/freemarker-logo.png" style="padding-top: 20px; padding-bottom: 10px;">				
											</div> 	
										</div>    		    													
										<div class="col-lg-6" style="padding-top: 10px;"> 
											<img class="center-block" src="/resources/img/tech-stack/html-css-js-bootstrap-logo.png">				
										</div>    
									</div> 		    												
								</div>
								<div class="thumbnail row technology-stack-inner-logo" style="margin-top: 10px;">   			    													  		    												
									<div class="col-lg-6"> 
										<img class="center-block" src="/resources/img/tech-stack/spring-framework-logo.png">				
									</div>    		    	
									<div class="col-lg-6"> 
										<img class="center-block" src="/resources/img/tech-stack/javaee-logo.png">				
									</div>    		    												
								</div>    		    												
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>      											
	</div>
</div>