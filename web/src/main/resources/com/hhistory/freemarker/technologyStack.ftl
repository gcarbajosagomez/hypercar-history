<#compress>
    <#import "applicationMacros/pageLanguage.ftl" as language/>

    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="row">
                    <div class="col-md-8 col-md-8 col-sm-10 col-xs-10">
                        <h2 class="modal-title">${language.getTextSource('footer.technologyStack')}</h2>
                    </div>
                    <div class="col-md-4 col-sm-2 col-xs-2">
                        <button id="dismiss-technology-stack-modal-button" type="button" class="close pull-right" data-dismiss="modal" aria-hidden="true">&times;</button>
                    </div>
                </div>
            </div>
            <div class="modal-body">
                <div class="row technology-stack" style="margin-bottom: 0px;">
                    <div class="col-lg-12" style="padding-top: 10px;">
                        <div class="row technology-stack-inner-logo">
                            <div class="thumbnail row technology-stack-inner-logo" style="margin-bottom: 10px;">
                                <div class="col-lg-6" style="padding-top:10px;">
                                    <img class="center-block" src="/static/img/tech-stack/hibernate-logo.png" alt="Hibernate logo">
                                </div>
                                <div class="col-lg-6" style="padding-top: 10px;">
                                    <img class="center-block" src="/static/img/tech-stack/mysql-logo.png" alt="MySQL logo">
                                </div>
                            </div>
                            <div class="thumbnail row technology-stack-inner-logo" style="margin-bottom: 10px;">
                                <div class="col-lg-12">
                                    <div class="col-lg-4" style="padding-top: <#if requestIsDesktop>55<#else>15</#if>px;">
                                        <img class="center-block" src="/static/img/tech-stack/jquery-logo.png" alt="jQuery logo">
                                    </div>
                                    <div class="col-lg-4" style="padding-top: <#if requestIsDesktop>55<#else>30</#if>px;">
                                        <img class="center-block" src="/static/img/tech-stack/freemarker-logo.png" alt="Freemarker logo">
                                    </div>
                                    <div class="col-lg-4" <#if !requestIsDesktop>style="padding-top: 30px;"</#if>>
                                        <img class="center-block" src="/static/img/tech-stack/html-css-js-bootstrap-logo.png" alt="HTML - js - CSS - Bootstrap logo">
                                    </div>
                                </div>
                            </div>
                            <div class="thumbnail row technology-stack-inner-logo">
                                <div class="col-lg-6" style="padding-top:30px;">
                                    <img class="center-block" src="/static/img/tech-stack/spring-boot-logo.png" alt="Spring Boot logo">
                                </div>
                                <div class="col-lg-6">
                                    <img class="center-block" src="/static/img/tech-stack/java-logo.png" alt="Java logo">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#compress>