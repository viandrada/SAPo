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

var loadFile2 = function(event){
	var output = document.getElementById('inputFotoPreview2');
	output.src = URL.createObjectURL(event.target.files[0]);
};

var loadFile3 = function(event){
	var output = document.getElementById('inputFotoPreview3');
	output.src = URL.createObjectURL(event.target.files[0]);
};

var loadFile4 = function(event){
	var output = document.getElementById('inputFotoPreview4');
	output.src = URL.createObjectURL(event.target.files[0]);
};