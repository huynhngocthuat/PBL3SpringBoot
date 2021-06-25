
//account
function checkPassword(){
    var password = document.getElementById("passWord").value;
    var re_password = document.getElementById("rePassWord").value;

    if(password != re_password){
        alert('Mật khẩu chưa khớp');
    }
}


//report
function chooseFile(fileInput){
    if(fileInput.files && fileInput.files[0]){
        var reader = new FileReader();
        reader.onload = function (e){
            $('#image').attr('src', e.target.result);
        }
        reader.readAsDataURL(fileInput.files[0]);
    }
}


function zoneChange(zone){
    $('#roomId').html("");
    $("#roomId").append("<option value=''>-Choose Room-</option>");
    $('#equipmentId').html("");
    $("#equipmentId").append("<option value=''>-Choose Equipment-</option>");
    $('#statusId').html("");
    $("#statusId").append("<option value=''>-Choose Status-</option>");
    $("#roomIdData option[id='"+zone.value+"']").each(function() {
        $("#roomId").append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");
    });
}

function roomChange(room){
    $('#equipmentId').html("");
    $("#equipmentId").append("<option value=''>-Choose Equipment-</option>");
    $('#statusId').html("");
    $("#statusId").append("<option value=''>-Choose Status-</option>");
    $("#equipmentIdData option[id='"+room.value+"']").each(function() {
        $("#equipmentId").append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");
    });
}

function equipmentChange(equipment){
    $('#statusId').html("");
    $("#statusId").append("<option value=''>-Choose Status-</option>");
    $("#statusIdData option[id='"+equipment.value+"']").each(function() {
        $("#statusId").append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");
    });
}






