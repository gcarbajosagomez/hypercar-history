function addCrsfTokenToAjaxRequest(xhr)
{
	// CSRF token to protect against cross site attacks
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
		$("#main-form")[0].action = "/pagani-history-web/cms/login";
	}
	else
	{
		$("#main-form")[0].action = "/pagani-history-web/cms/login?logout";
	}
    
    $("#main-form").append(csrfData);
	$("#main-form").submit();
}

function saveOrEditEntity(form, saveMessage)
{		
	bootbox.confirm(saveMessage, function(result)
    {
		//OK button
		if (result == true)
		{
			var csrfData = $("<input>").attr("type", "hidden").attr("name", "_csrf").val($("meta[name='_csrf']").attr("content"));
		
			form.append(csrfData);
			form.enctype="multipart/form-data";
			form.submit();
		}
    });	   
}

function deleteCar(form, deleteUrl, deleteMessage)
{
	bootbox.confirm(deleteMessage, function(result)
    {
		//OK button
		if (result == true)
		{
			var csrfData = $("<input>").attr("type", "hidden").attr("name", "_csrf").val($("meta[name='_csrf']").attr("content"));

			form.append(csrfData);
			form.enctype="multipart/form-data";
			form[0].action = deleteUrl;
			form.submit();
		}
    });
}