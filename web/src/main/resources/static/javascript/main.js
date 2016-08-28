function addCRSFTokenToAjaxRequest(xhr)
{
	// CSRF token to protect against cross site attacks
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
						
	xhr.setRequestHeader(header, token);
	
	return xhr;
}

function submitLoginForm(login)
{		
	if (login)
	{
		$("#main-form")[0].action = "/cms/login";
	}
	else
	{
		$("#main-form")[0].action = "/cms/login/logout";
	}

	var csrfData = $("<input>").attr("type", "hidden").attr("name", "_csrf").val($("meta[name='_csrf']").attr("content"));
    $("#main-form").append(csrfData);
	$("#main-form")[0].method = 'POST';
	$("#main-form").submit();
}

function setUpContactUsModal()
{
	$('#contact-us-error-alert-span').text('');
	$('#contact-us-error-alert-div').addClass('hidden');
	$('#contact-us-success-alert-span').text('');
	$('#contact-us-success-alert-div').addClass('hidden');
	
	$('#contact-us-subject')[0].value = '';
   	$('#contact-us-sender-name')[0].value = '';
   	$('#contact-us-sender-email')[0].value = '';
   	$('#contact-us-message')[0].value = '';
}

function deletePicture(pictureId, deleteMessage)
{
	bootbox.confirm(deleteMessage, function(result)
    {
		//OK button
		if (result == true)
		{
			$.ajax({
			    url: '/cms/pictures/' + pictureId + '/delete',
			    type: 'DELETE',
                dataType: 'text',
			    beforeSend: function(xhr)
	    	    {
			    	addCRSFTokenToAjaxRequest(xhr);
	    	    }
			})
			.done(function(data)
			{
				if (data.indexOf('successMessage : ') !== -1) {
                    $('#' + pictureId + '-picture-row').remove();
                }
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
			var zIndex = ((data.length - (i + 1)) + 1);
			carListString = carListString.concat(writeCarListRow(auxCarRowList, zIndex));
		}
	}
					 
	$('#car-list-div')[0].innerHTML = carListString;
}

function setupContentSearchEventListeners()
{
	$("#content-search-input").keypress(function( event )
  	{  								
  		if (event.which == 13)
  			{  									
     			event.preventDefault();
     			handleContentSearch($("#content-search-input")[0].value);
  			}  	
		});
						
	$( "#content-search-input" ).click(function()
	{
  		$( "#content-search-input" ).keypress();
	});		
}

//this function is called from an <a> tag in the text sources files
function openTechnologyStackModal()
{			
	$.ajax({
	    url: "/technologyStack",
	    type: 'GET',
	    beforeSend: function(xhr)
	    {
	    	addCRSFTokenToAjaxRequest(xhr);
	    }
	})
	.done(function(data)
	{ 
		$('#technology-stack-modal-div')[0].innerHTML = data; 
		$('#technology-stack-modal-div').modal('show');
	});
}