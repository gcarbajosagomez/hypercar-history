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
					<img src="/static/img/tech-stack/openshift-logo.png">
					<div class="row technology-stack-inner-logo">
                        <div class="thumbnail row technology-stack-inner-logo" style="margin-bottom: 10px;">
                            <div class="col-lg-6" style="padding-top:10px;">
                                <img class="center-block" src="/static/img/tech-stack/hibernate-logo.png">
                            </div>
                            <div class="col-lg-6" style="padding-top: 10px;">
                                <img class="center-block" src="/static/img/tech-stack/mysql-logo.png">
                            </div>
                        </div>
                        <div class="thumbnail row technology-stack-inner-logo" style="margin-bottom: 10px;">
                            <div class="col-lg-12">
                                <div class="col-lg-6" style="padding-top: 10px;">
                                    <div class="col-lg-12">
                                        <img class="center-block" src="/static/img/tech-stack/jquery-logo.png">
                                    </div>
                                    <div class="col-lg-12">
                                        <img class="center-block" src="/static/img/tech-stack/ajax-logo.png" style="padding-top: 10px;">
                                    </div>
                                    <div class="col-lg-12">
                                        <img class="center-block" src="/static/img/tech-stack/freemarker-logo.png" style="padding-top: 20px; padding-bottom: 10px;">
                                    </div>
                                </div>
                                <div class="col-lg-6" style="padding-top: 35px;">
                                    <img class="center-block" src="/static/img/tech-stack/html-css-js-bootstrap-logo.png">
                                </div>
                            </div>
                        </div>
                        <div class="thumbnail row technology-stack-inner-logo">
                            <div class="col-lg-6" style="padding-top:30px;">
                                <img class="center-block" src="/static/img/tech-stack/spring-framework-logo.png">
                            </div>
                            <div class="col-lg-6">
                                <img class="center-block" src="/static/img/tech-stack/java8-logo.png">
                            </div>
                        </div>
					</div>
				</div>
			</div>
		</div>      											
	</div>
</div>