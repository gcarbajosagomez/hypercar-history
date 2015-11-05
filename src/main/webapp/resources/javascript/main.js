function addCrsfTokenToAjaxRequest(xhr)
{
	<!-- CSRF token to protect against cross site attacks -->
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
						
	xhr.setRequestHeader(header, token);
	
	return xhr;
}

function submitLoginForm(login)
{		
	var csrfData = $("<input>").attr("type", "hidden").attr("name", "_csrf").val($("meta[name='_csrf']").attr("content"));
	
	if (login)
	{
		$("#main-form")[0].action = "/pagani-history-web/cms/login.html";
	}
	else
	{
		$("#main-form")[0].action = "/pagani-history-web/cms/logout.html";
	}
    
    $("#main-form").append(csrfData);
	$("#main-form").submit();
}