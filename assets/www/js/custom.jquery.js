function classLocalStorage() {
	this.logged = 0;
	this.oid = 0;
};

function onDeviceReady() {	
	var $responseHolder2 = $('#response-holder2');
	var $responseHolder3 = $('#response-holder3');
	var messages = '';
	
	$('#acasa, #cont, #contact, #comenzi').on('click', function(e) {
		//window.open($(this).data('url'),'_self');
		window.location.href = this.href;
		return false;
	});
	
		/* Class Local Storage definitions */
		classLocalStorage.prototype.IsLogged = function() {
			/* Check if it's logged */
			/*
			$responseHolder2.append('<p class="warning">window.localStorage.getItem("logged"): ' + window.localStorage.getItem("logged") + '</p>')
							.fadeIn();
			*/

			this.logged = 0;
			if (typeof window.localStorage.getItem("logged") != 'undefined' && window.localStorage.getItem("logged") == 1) {
				this.logged = 1;
			}
			return this.logged;
		};

		classLocalStorage.prototype.SetLoggedData = function(data, email) {
			window.localStorage.clear();
			/* Set data */
			var noError = true;
			if (typeof data == 'undefined') { noError = false; };
			if (typeof data.firstname != 'undefined') { window.localStorage.setItem('firstname', data.firstname); } else { noError = false; };
			if (typeof data.lastname != 'undefined') { window.localStorage.setItem('lastname', data.lastname); } else { noError = false; };
			if (typeof data.telefon != 'undefined') { window.localStorage.setItem('telefon', data.telefon); } else { noError = false; };
			if (typeof email != 'undefined') { window.localStorage.setItem('email', email); } else { noError = false; };
			this.logged = 1;
			window.localStorage.setItem('logged', this.logged);
			
			if (!this.GetLoggedData()) { 
				noError = false; 
				this.logged = 0;
				window.localStorage.setItem('logged', this.logged);
			};

			/*
			messages += '<p class="warning">data.firstname: ' + data.firstname + '</p>'
							+ '<p class="warning">data.lastname: ' + data.lastname + '</p>'
							+ '<p class="warning">data.telefon: ' + data.telefon + '</p>'
							+ '<p class="warning">email: ' + email + '</p>'
							+ '<p class="warning">noError: ' + noError + '</p>';
			$responseHolder2.append(messages).fadeIn();
			*/
			return noError;
		};
		

		classLocalStorage.prototype.SetCurrentOrder = function(oid) {
			/* Set current order data */
			var noError = true;
			if (!this.IsLogged()) {
				noError = false; 
			} else {
				if (typeof oid == 'undefined') { 
					noError = false; 
					this.oid = 0;
				} else { 
					window.localStorage.setItem('oid', oid); 
					this.oid = oid;
				};
			}

			/*
			messages += ''
						+ <p class="warning">data.oid: ' + data.oid + '</p>'
						;
			$responseHolder2.append(messages).fadeIn();
			*/
			return noError;
		};
		
		classLocalStorage.prototype.GetCurrentOrder = function() {
			/* Check if it's logged */
			this.oid = window.localStorage.getItem("oid");
			this.logged = window.localStorage.getItem('logged');

			messages += ''
						+ '<p class="error">1 this.oid: ' + this.oid + '</p>'
						+ '<p class="error">1 this.logged: ' + this.logged + '</p>';
			//$responseHolder3.append(messages).fadeIn();

			/* Check if saved local storage userData is ok */
			if (typeof this.oid == 'undefined') { return false; };
			if (typeof this.logged == 'undefined') { this.ClearSession(); return false; };
			
			messages += ''
						+ '<p class="error">2 this.oid: ' + this.oid + '</p>'
						+ '<p class="error">2 this.logged: ' + this.logged + '</p>';
			//$responseHolder3.append(messages).fadeIn();

			return true;
		};

		classLocalStorage.prototype.GetLoggedData = function() {
			/* Check if it's logged */
			this.email = window.localStorage.getItem("email");
			this.firstname = window.localStorage.getItem('firstname');
			this.lastname = window.localStorage.getItem('lastname');
			this.telefon = window.localStorage.getItem('telefon');
			this.logged = window.localStorage.getItem('logged');

			messages += '<p class="error">1 this.firstname: ' + this.firstname + '</p>'
						+ '<p class="error">1 this.lastname: ' + this.lastname + '</p>'
						+ '<p class="error">1 this.telefon: ' + this.telefon + '</p>'
						+ '<p class="error">1 this.email: ' + this.email + '</p>'
						+ '<p class="error">1 window.localStorage.getItem(logged): ' + window.localStorage.getItem('logged') + '</p>';
						+ '<p class="error">1 this.logged: ' + this.logged + '</p>';
			//$responseHolder3.append(messages).fadeIn();

			/* Check if saved local storage userData is ok */
			if (typeof this.firstname == 'undefined') { this.ClearSession(); return false; };
			if (typeof this.lastname == 'undefined') { this.ClearSession(); return false; };
			if (typeof this.telefon == 'undefined') { this.ClearSession(); return false; };
			if (typeof this.email == 'undefined') { this.ClearSession(); return false; };
			if (typeof this.logged == 'undefined') { this.ClearSession(); return false; };
			
			messages += '<p class="error">2 this.firstname: ' + this.firstname + '</p>'
						+ '<p class="error">2 this.lastname: ' + this.lastname + '</p>'
						+ '<p class="error">2 this.telefon: ' + this.telefon + '</p>'
						+ '<p class="error">2 this.email: ' + this.email + '</p>'
						+ '<p class="error">2 this.logged: ' + this.logged + '</p>';
			//$responseHolder3.append(messages).fadeIn();

			return true;
		};

		classLocalStorage.prototype.ClearSession = function() {
			/* Clear everything */
			window.localStorage.clear();
			this.logged = 0;
			if (typeof window.localStorage.getItem("logged") == 'undefined') { return true } else { return false };
		};
		/* End Local Storage */
		
		//var oLocalStorage = new classLocalStorage();
};

$(function() {
    document.addEventListener("deviceready", onDeviceReady, true);       
});