<#include "../applicationMacros/genericFunctionalities.ftl">

<@startPage/>    
     <div class="shadowBorderedDiv carDetailsInfo">      
        <table>
            <#if manufacturers?? && manufacturers?has_content>
                <#list manufacturers as manufacturer>
                    <tr>
                        <td>
                            <table>
                                <tr>
                                    <td style="vertical-align : middle"><a href="manufacturerEdit.tcp?manufacturerId=${manufacturer.id}" title="${manufacturer.story?default("")}"><b>${manufacturer.name}</b></a></td>
                               </tr>
                            </table>
                        </td> 
                    </tr>
                </#list>
            </#if>
        </table>
     </div>

<@endPage/> 