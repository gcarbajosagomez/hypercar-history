function addCRSFTokenToAjaxRequest(xhr) {
    // CSRF token to protect against cross site attacks
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    xhr.setRequestHeader(header, token);

    return xhr;
}

function submitLoginForm(login) {
    if (login) {
        $("#main-form")[0].action = "/cms/login";
    }
    else {
        $("#main-form")[0].action = "/cms/login/logout";
    }

    var csrfData = $("<input>").attr("type", "hidden").attr("name", "_csrf").val($("meta[name='_csrf']").attr("content"));
    $("#main-form").append(csrfData);
    $("#main-form")[0].method = 'POST';
    $("#main-form").submit();
}

function setUpContactUsModal() {
    $('#contact-us-error-alert-span').text('');
    $('#contact-us-error-alert-div').addClass('hidden');
    $('#contact-us-success-alert-span').text('');
    $('#contact-us-success-alert-div').addClass('hidden');

    $('#contact-us-subject')[0].value = '';
    $('#contact-us-sender-name')[0].value = '';
    $('#contact-us-sender-email')[0].value = '';
    $('#contact-us-message')[0].value = '';
}

function deletePicture(pictureId, deleteMessage) {
    bootbox.confirm(deleteMessage, function (result) {
        //OK button
        if (result == true) {
            $.ajax({
                url: '/cms/pictures/' + pictureId + '/delete',
                type: 'DELETE',
                dataType: 'text',
                beforeSend: function (xhr) {
                    addCRSFTokenToAjaxRequest(xhr);
                }
            })
                .done(function (data) {
                    var crudOperationDTO = JSON.parse(data);
                    if (crudOperationDTO.successMessage) {
                        $('#' + pictureId + '-picture-row').remove();
                    }
                });
        }
    });
}

function deleteCarInternetContent(carInternetContentId, deleteMessage) {
    bootbox.confirm(deleteMessage, function (result) {
        //OK button
        if (result == true) {
            $.ajax({
                url: '/cms/carInternetContents/' + carInternetContentId + '/delete',
                type: 'DELETE',
                dataType: 'text',
                beforeSend: function (xhr) {
                    addCRSFTokenToAjaxRequest(xhr);
                }
            })
                .done(function (data) {
                    if (data.indexOf('successMessage : ') !== -1) {
                        $('#car-internet-content-div-' + carInternetContentId).remove();
                    }
                });
        }
    });
}

//this function is called from the pagination template
function writeCarPreviews(data) {
    var auxCarRowList = new Array();
    var carListString = "";

    for (var i = 0; i < data.length; i++) {
        if (i % 2 == 0) {
            auxCarRowList = new Array()
            auxCarRowList[0] = data[i];

            if ((i + 1) <= (data.length - 1)) {
                auxCarRowList[1] = data[i + 1];
            }

            i++;
            var zIndex = ((data.length - (i + 1)) + 1);
            carListString = carListString.concat(writeCarListRow(auxCarRowList, zIndex));
        }
    }

    $('#car-list-div')[0].innerHTML = carListString;
}

function setupContentSearchEventListeners() {
    $("#content-search-input").keypress(function (event) {
        if (event.which == 13) {
            event.preventDefault();
            handleContentSearch($("#content-search-input")[0].value);
        }
    });

    $("#content-search-input").click(function () {
        $("#content-search-input").keypress();
    });
}

//this function is called from an <a> tag in the text sources files
function openTechnologyStackModal() {
    $.ajax({
        url: "/technologyStack",
        type: 'GET',
        beforeSend: function (xhr) {
            addCRSFTokenToAjaxRequest(xhr);
        }
    })
        .done(function (data) {
            $('#technology-stack-modal-div')[0].innerHTML = data;
            $('#technology-stack-modal-div').modal('show');
        });
}

function setupPictureGalleryPositionInputs() {
    var pictureUploadBoxNum = $("input[id$='picture.galleryPosition']").length;

    //one iteration more for the default file chooser input
    for (var i = 0; i <= pictureUploadBoxNum; i++) {
        $("input[name='editForm.pictureFileEditCommands[" + i + "].picture.galleryPosition']").TouchSpin({
            verticalbuttons: true,
            verticalupclass: 'glyphicon glyphicon-plus',
            verticaldownclass: 'glyphicon glyphicon-minus'
        });
    }
}

function performGoogleAnalyticsRequest() {
    (function (i, s, o, g, r, a, m) {
        i['GoogleAnalyticsObject'] = r;
        i[r] = i[r] || function () {
                (i[r].q = i[r].q || []).push(arguments)
            }, i[r].l = 1 * new Date();
        a = s.createElement(o),
            m = s.getElementsByTagName(o)[0];
        a.async = 1;
        a.src = g;
        m.parentNode.insertBefore(a, m)
    })(window, document, 'script', 'https://www.google-analytics.com/analytics.js', 'ga');

    ga('create', 'UA-88763429-1', 'auto');
    ga('send', 'pageview');
}

function addErrorMessage(message, domElementId) {
    var errorDiv = $('<div>', {
        'class': 'col-xs-12 alert alert-danger',
        'role': 'alert'
    });
    var exclamationSign = $('<span>', {
        'class': 'glyphicon glyphicon-exclamation-sign',
        'aria-hidden': 'true'
    });
    errorDiv.append(exclamationSign);
    errorDiv.append(' ' + message);
    $('#' + domElementId).append(errorDiv);
}

function addSuccessMessage(message, domElementId) {
    var successDiv = $('<div>', {
        'class': 'col-xs-12 alert alert-success',
        'role': 'info'
    });
    var okSign = $('<span>', {
        'class': 'glyphicon glyphicon-ok-sign',
        'aria-hidden': 'true'
    });
    successDiv.append(okSign);
    successDiv.append(' ' + message);
    $('#' + domElementId).append(successDiv);
}

function removeAllSuccessMessages() {
    $('.alert-success').remove();
}

function removeAllErrorMessages() {
    $('.alert-danger').remove();
}

function createEngineFromDOM() {
    return {
        'editForm': {
            'id': $('#editForm\\.engineEditForm\\.id')[0].value,
            'code': $('#editForm\\.engineEditForm\\.code')[0].value,
            'size': $('#editForm\\.engineEditForm\\.size')[0].value,
            'type': $('#editForm\\.engineEditForm\\.type')[0].value,
            'cylinderDisposition': $('#editForm\\.engineEditForm\\.cylinderDisposition')[0].value,
            'cylinderBankAngle': $('#editForm\\.engineEditForm\\.cylinderBankAngle')[0].value,
            'numberOfCylinders': $('#editForm\\.engineEditForm\\.numberOfCylinders')[0].value,
            'numberOfValves': $('#editForm\\.engineEditForm\\.numberOfValves')[0].value,
            'maxPower': $('#editForm\\.engineEditForm\\.maxPower')[0].value,
            'maxRPM': $('#editForm\\.engineEditForm\\.maxRPM')[0].value,
            'maxPowerRPM': $('#editForm\\.engineEditForm\\.maxPowerRPM')[0].value,
            'maxTorque': $('#editForm\\.engineEditForm\\.maxTorque')[0].value,
            'maxTorqueRPM': $('#editForm\\.engineEditForm\\.maxTorqueRPM')[0].value,
        }
    };
}

function appendEngineCrudOperationsResultMessages(data) {
    removeAllSuccessMessages();
    removeAllErrorMessages();

    var successMessage = data.successMessage;
    if (successMessage) {
        addSuccessMessage(successMessage, 'engine-main-div')
    } else {
        var errorMessages = data.errorMessages;
        if (errorMessages) {
            errorMessages.forEach(function (message) {
                addErrorMessage(message, 'engine-main-div');
            });
        }
    }
}