/*function readURL(input) {

    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#altaAlmacen\\:inputFotoPreview').attr('url', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}

$("#altaAlmacen\\imagenAlmacen").change(function(){
    readURL(this);
});*/

var loadFile = function(event){
	var output = document.getElementById('inputFotoPreview');
	output.src = URL.createObjectURL(event.target.files[0]);
};