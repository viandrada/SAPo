		var latitud;
        var longitud;
        
   //     $(document).ready(function() {
       //     localizame(); /*Cuando cargue la página, cargamos nuestra posición*/   
      //  });
        
        function localizame() {
            if (navigator.geolocation) { /* Si el navegador tiene geolocalizacion */
                navigator.geolocation.getCurrentPosition(coordenadas, errores);
            }else{
                alert('Oops! Tu navegador no soporta geolocalización. Bájate Chrome, que es gratis!');
            }
        }
        
        function coordenadas(position) {
            latitud = position.coords.latitude; /*Guardamos nuestra latitud*/
            longitud = position.coords.longitude; /*Guardamos nuestra longitud*/
            //cargarMapa();
          //  cargarCoordenadas();
        }
        
        function errores(err) {
            /*Controlamos los posibles errores */
            if (err.code == 0) {
              alert("Oops! Algo ha salido mal");
            }
            if (err.code == 1) {
              alert("Oops! No has aceptado compartir tu posición");
            }
            if (err.code == 2) {
              alert("Oops! No se puede obtener la posición actual");
            }
            if (err.code == 3) {
              alert("Oops! Hemos superado el tiempo de espera");
            }
        }





function onSignIn(googleUser) {
	
	
	//  $(document).ready(function() {
          localizame(); /*Cuando cargue la página, cargamos nuestra posición*/   
    //  });
	
	
	var counter = document.getElementById("inputHiddenForm:contadorLogin").value;
	var profile = googleUser.getBasicProfile();
	document.getElementById('loginPanel').rendered = false;

	console.log('ID: ' + profile.getId()); // Do not send to your backend! Use
											// an ID token instead.
	console.log('Name: ' + profile.getName());
	console.log('Image URL: ' + profile.getImageUrl());
	console.log('Email: ' + profile.getEmail());
	if (counter != 1) {
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'http://localhost:8080/SAPo-FrontOffice/googleLogin/'+ profile.getEmail()+'&'+latitud+'&'+longitud);
		//xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		xhr.send(null);
		xhr.onload = function() {
		  console.log('Signed in as: ' + xhr.responseText);
		  window.location.reload();
		};
		
		document.getElementById('inputHiddenForm:emailGmail').value = profile
				.getEmail();
		document.getElementById("inputHiddenForm:contadorLogin").value = 1;
		document.getElementById("inputHiddenForm").submit();
		window.location.replace(window.location.href);
	}
	window.location.replace(window.location.href);
	
}

function googleLogout(){
	var xhr = new XMLHttpRequest();
	window.location.replace("https://accounts.google.com/logout");
	//window.location.href = "https://accounts.google.com/logout";
	//window.location.reload();
	
}
