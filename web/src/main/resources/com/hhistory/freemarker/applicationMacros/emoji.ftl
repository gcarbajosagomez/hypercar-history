<#--
 Created by Gonzalo Carbajosa on 18/03/19.
-->
<#assign languageNameToEmojiMatrix = {
"spanish":"🇪🇸",
"english":"🇬🇧",
"italian":"🇮🇹",
"french":"🇫🇷",
"german":"🇩🇪",
"japanese":"🇯🇵",
"chinese":"🇨🇳",
"other":"🇧🇶"
}>

<#function getFlagEmojiByLanguage language>
    <#return languageNameToEmojiMatrix[language]/>
</#function>