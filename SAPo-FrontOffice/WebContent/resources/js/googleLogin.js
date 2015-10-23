function onSignIn(googleUser) {
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
		xhr.open('GET', 'http://localhost:8080/SAPo-FrontOffice/googleLogin/'+ profile.getEmail());
		//xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		xhr.send(null);
		xhr.onload = function() {
		  console.log('Signed in as: ' + xhr.responseText);
		};
		
		document.getElementById('inputHiddenForm:emailGmail').value = profile
				.getEmail();
		document.getElementById("inputHiddenForm:contadorLogin").value = 1;
		document.getElementById("inputHiddenForm").submit();
		window.location.replace(window.location.href);
	}
	window.location.replace(window.location.href);
}
