<#import "../applicationMacros/pageLanguage.ftl" as language/>

<#macro addAddInternetContentFunctionScript>

    <script type="application/javascript">
        function addInternetContent() {
            var internetContentNum = $("input[id^=editForms][id$=link").length;
            var newInternetContentCarFormIdHiddenInput = $('<input>', {
                'type': 'hidden',
                'id': 'editForms' + internetContentNum + '.car',
                'name': 'editForms[' + internetContentNum + '].car',
                'value': '<#if CEFC.editForm.id??>${CEFC.editForm.id}</#if>'
            });

            var newInternetContentWellDiv = $('<div>', {'class': 'well well-lg'});
            var newInternetContentDl = $('<dl>', {'class': 'dl-horizontal dl-horizontal-edit text-left'});
            var newInternetContentLinkDt = $('<dt>');
            newInternetContentLinkDt.append('${language.getTextSource('cms.car.internetContent.link')}');
            newInternetContentDl.append(newInternetContentLinkDt);
            var newInternetContentLinkDd = $('<dd>');
            newInternetContentLinkDd.append(newInternetContentCarFormIdHiddenInput);
            newInternetContentLinkDd.append($('<input>', {
                'type': 'text',
                'id': 'editForms' + internetContentNum + '.link',
                'name': 'editForms[' + internetContentNum + '].link',
                'class': 'form-control'
            }));

            newInternetContentDl.append(newInternetContentLinkDd);

            var newInternetContentTypeDt = $('<dt>');
            newInternetContentTypeDt.append('${language.getTextSource('cms.car.internetContent.type')}');
            newInternetContentDl.append(newInternetContentTypeDt);
            var newInternetContentTypeDd = $('<dd>');
            var newInternetContentTypeSelect = $('<select>', {
                'id': 'editForms' + internetContentNum + '.type',
                'name': 'editForms[' + internetContentNum + '].type',
                'class': 'form-control'
            });

            <#list carInternetContentTypes as carInternetContentType>
                newInternetContentTypeSelect.append($('<option>', {'value': '${carInternetContentType}'}).text('${language.getTextSource('cms.car.internetContent.type.${carInternetContentType}')}'));
            </#list>

            newInternetContentTypeDd.append(newInternetContentTypeSelect);
            newInternetContentDl.append(newInternetContentTypeDd);

            var newInternetContentLanguageDt = $('<dt>');
            newInternetContentLanguageDt.append('${language.getTextSource('cms.car.internetContent.contentLanguage')}');
            newInternetContentDl.append(newInternetContentLanguageDt);
            var newInternetContentLanguageDd = $('<dd>');
            var newInternetContentLanguageSelect = $('<select>', {
                'id': 'editForms' + internetContentNum + '.contentLanguage',
                'name': 'editForms[' + internetContentNum + '].contentLanguage',
                'class': 'form-control'
            });

            <#list carInternetContentLanguages as contentLanguage>
                newInternetContentLanguageSelect.append($('<option>', {'value': '${contentLanguage}'}).text('${language.getTextSource('cms.car.internetContent.contentLanguage.${contentLanguage.getName()}')}'));
            </#list>

            newInternetContentLanguageDd.append(newInternetContentLanguageSelect);
            newInternetContentDl.append(newInternetContentLanguageDd);

            newInternetContentWellDiv.append(newInternetContentDl);
            $('#internet-contents-main-panel-body').append(newInternetContentWellDiv);
        }
    </script>
</#macro>