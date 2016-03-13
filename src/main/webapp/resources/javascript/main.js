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

function deleteEntity(url, deleteMessage)
{
	bootbox.confirm(deleteMessage, function(result)
    {
		//OK button
		if (result == true)
		{
			$.ajax({
			    url: url,
			    type: 'DELETE',
			    beforeSend: function(xhr)
	    	    {
			    	xhr = addCrsfTokenToAjaxRequest(xhr);
	    	    }
			})
			.done(function(data)
			{
				document.children[0].innerHTML = data; 	
			});
		}
    });
}

function writeCarPreviews(data)
{
	var auxCarRowList = new Array();
		var carListString = "";
							 
		for (var i=0 ; i< data.length; i++)
	{
    	if (i%2 == 0)
		{
			auxCarRowList = new Array()
			auxCarRowList[0] = data[i];
								
			if((i + 1) <= (data.length - 1))
			{
				auxCarRowList[1] = data[i + 1];
			}
			
    		i++;
    		
    		zIndex = ((data.length - (i + 1)) + 1);
			carListString = carListString.concat(writeCarListRow(auxCarRowList, zIndex));
		}
	}
					 
	$('#car-list-div')[0].innerHTML = carListString;
}