<#compress>
<#import "pageLanguage.ftl" as language/>

<#macro addAddInternetContentFunctionScript>

    <script type="application/javascript">
        function addInternetContent() {
            var internetContentNum = $("input[id^=carInternetContentForms][id$=link").length;
            var newInternetContentCarFormIdHiddenInput = $('<input>', {
                'type': 'hidden',
                'id': 'carInternetContentForms' + internetContentNum + '.car',
                'name': 'carInternetContentForms[' + internetContentNum + '].car',
                'value': '<#if CEFC.carForm.id??>${CEFC.carForm.id}</#if>'
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
                'id': 'carInternetContentForms' + internetContentNum + '.link',
                'name': 'carInternetContentForms[' + internetContentNum + '].link',
                'class': 'form-control'
            }));

            newInternetContentDl.append(newInternetContentLinkDd);

            var newInternetContentTypeDt = $('<dt>');
            newInternetContentTypeDt.append('${language.getTextSource('cms.car.internetContent.type')}');
            newInternetContentDl.append(newInternetContentTypeDt);
            var newInternetContentTypeDd = $('<dd>');
            var newInternetContentTypeSelect = $('<select>', {
                'id': 'carInternetContentForms' + internetContentNum + '.type',
                'name': 'carInternetContentForms[' + internetContentNum + '].type',
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
                'id': 'carInternetContentForms' + internetContentNum + '.contentLanguage',
                'name': 'carInternetContentForms[' + internetContentNum + '].contentLanguage',
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
</#compress>