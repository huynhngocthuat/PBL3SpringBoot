
function chooseFile(fileInput){
    if(fileInput.files && fileInput.files[0]){
        var reader = new FileReader();
        reader.onload = function (e){
            $('#image').attr('src', e.target.result);
        }
        reader.readAsDataURL(fileInput.files[0]);
    }
}

function checkPassword(){
    var password = document.getElementById("passWord").value;
    var re_password = document.getElementById("rePassWord").value;

    if(password != re_password){
        alert('Mật khẩu chưa khớp');
    }
}