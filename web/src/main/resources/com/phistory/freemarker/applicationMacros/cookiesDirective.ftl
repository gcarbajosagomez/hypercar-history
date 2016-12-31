<#--
 Created by Gonzalo Carbajosa on 25/12/16.
-->
<#import "pageLanguage.ftl" as language/>

<#macro loadCookiesDirectiveScript>
    $.cookiesDirective({
        privacyPolicyUri: '/${cookiesPolicyURL}<#if doNotTrack>?${doNotTrackParam}=true</#if>',
        position: 'bottom',
        message: '${language.getTextSource('cookiesDirectiveMessage')}',
        deleteAndBlockCookiesMessage: '${language.getTextSource('cookiesDirectiveMessage.deleteAndBlockCookiesMessage')}',
        privacyPolicyMessage: '${language.getTextSource('cookiesDirectiveMessage.privacyPolicyMessage')}',
        acceptCookiesMessage: '${language.getTextSource('cookiesDirectiveMessage.acceptCookies')}',
        continueButton: '${language.getTextSource('cookiesDirectiveMessage.continueButton')}',
        duration: 200,
        limit: 0,
        fontFamily: 'Helvetica Neue, Helvetica, Arial, sans-serif',
        fontColor: '#FFFFFF',
        fontSize: '13px',
        backgroundColor: 'rgba(63, 63, 63, 1)',
        backgroundOpacity: '90',
        linkColor: '#337ab7'
    });
</#macro>