function classLocalStorage() {
	this.logged = 0;
	this.oid = 0;
};

function onDeviceReady() {	
	var $responseHolder2 = $('#response-holder2');
	var $responseHolder3 = $('#response-holder3');
	var messages = '';
	
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

	classLocalStorage.prototype.SetLoggedData = function(data) {
		window.localStorage.clear();
		this.logged = 0;
		window.localStorage.setItem('logged', this.logged);

		/* Set data */
		var noError = true;
		if (typeof data == 'undefined') { noError = false; };
		if (typeof data.user == 'undefined') { noError = false; };
		if (typeof data.user.username != 'undefined') { window.localStorage.setItem('username', data.user.username); } else { noError = false; };
		if (typeof data.user.firstname != 'undefined') { window.localStorage.setItem('firstname', data.user.firstname); };
		if (typeof data.user.lastname != 'undefined') { window.localStorage.setItem('lastname', data.user.lastname); };
		if (typeof data.user.dobday != 'undefined') { window.localStorage.setItem('dobday', data.user.dobday); };
		if (typeof data.user.dobmonth != 'undefined') { window.localStorage.setItem('dobmonth', data.user.dobmonth); };
		if (typeof data.user.dobyear != 'undefined') { window.localStorage.setItem('dobyear', data.user.dobyear); };
		if (typeof data.user.age != 'undefined') { window.localStorage.setItem('age', data.user.age); };
		if (typeof data.user.weight != 'undefined') { window.localStorage.setItem('weight', data.user.weight); };
		if (typeof data.user.heightfeet != 'undefined') { window.localStorage.setItem('heightfeet', data.user.heightfeet); };
		if (typeof data.user.heightinches != 'undefined') { window.localStorage.setItem('heightinches', data.user.heightinches); };
		if (typeof data.user.sex != 'undefined') { window.localStorage.setItem('sex', data.user.sex); };
		if (typeof data.user.dayphone != 'undefined') { window.localStorage.setItem('dayphone', data.user.dayphone); };
		if (typeof data.user.nightphone != 'undefined') { window.localStorage.setItem('nightphone', data.user.nightphone); };
		if (typeof data.user.shipaddress != 'undefined') { window.localStorage.setItem('shipaddress', data.user.shipaddress); };
		if (typeof data.user.shipaddress2 != 'undefined') { window.localStorage.setItem('shipaddress2', data.user.shipaddress2); };
		if (typeof data.user.ShipCity != 'undefined') { window.localStorage.setItem('ShipCity', data.user.ShipCity); };
		if (typeof data.user.ShipState != 'undefined') { window.localStorage.setItem('ShipState', data.user.ShipState); };
		if (typeof data.user.ShipCountry != 'undefined') { window.localStorage.setItem('ShipCountry', data.user.ShipCountry); };
		if (typeof data.user.ShipZip != 'undefined') { window.localStorage.setItem('ShipZip', data.user.ShipZip); };
		if (typeof data.user.BillAddress != 'undefined') { window.localStorage.setItem('BillAddress', data.user.BillAddress); };
		if (typeof data.user.BillAddress2 != 'undefined') { window.localStorage.setItem('BillAddress2', data.user.BillAddress2); };
		if (typeof data.user.BillCity != 'undefined') { window.localStorage.setItem('BillCity', data.user.BillCity); };
		if (typeof data.user.BillState != 'undefined') { window.localStorage.setItem('BillState', data.user.BillState); };
		if (typeof data.user.BillCountry != 'undefined') { window.localStorage.setItem('BillCountry', data.user.BillCountry); };
		if (typeof data.user.BillZip != 'undefined') { window.localStorage.setItem('BillZip', data.user.BillZip); };
		if (typeof data.order.itemscount != 'undefined') { window.localStorage.setItem('itemscount', data.order.itemscount); } else { noError = false; };
		if (typeof data.order.items != 'undefined') { window.localStorage.setItem('items', data.order.items); } else { noError = false; };
		alert('Urmeaza this.GetSID()');
		alert(this.GetSID());
		window.localStorage.setItem('sidname', this.GetSID());
		window.localStorage.setItem('sid', this.GetSID());
		
		//console.log(data);
		/* Conditiile ramin SEPARATE! 
		In GetLoggedData se verifica this.logged, deci trebuie sa fie setat inaintea apelarii metodei
		*/
		if (noError) {
			this.logged = 1;
		}
		//console.log(data.order);
		//alert('SetLoggedData - data.order.itemscount: ' + data.order.itemscount);
		//alert('SetLoggedData - data.order.items: ' + data.order.items);
		alert('SetLoggedData - noError: ' + noError);
		alert('SetLoggedData - this.logged: ' + this.logged);
		if (this.GetLoggedData()) {
			this.logged = 1;
		}
		//alert('SetLoggedData 2 - this.logged: ' + this.logged);
		
		window.localStorage.setItem('logged', this.logged);
		return this.logged;
	};
	
	classLocalStorage.prototype.RetrieveData = function() {
		var $responseHolder = $('#response-holder');
		var $responseHolder2 = $('#response-holder2');
		var sidname = window.localStorage.getItem('sidname');
		var sid = window.localStorage.getItem('sid');
		var _this = this;
		if (typeof sidname != 'undefined' && sidname !== null) { _this.sidname = sidname; };
		if (typeof sid != 'undefined' && sid !== null) { _this.sid = sid; };
		var jqxhr = $.ajax({
			//type: $loginForm.prop('method'),
			//url: $loginForm.prop('action'),
			type: 'get',
			url: 'https://prescription.technology/___include/ajax/ajax.app.regcust.asp',
			dataType: 'html',
			beforeSend: function (request) {
				if (typeof _this.sidname != 'undefined' && _this.sidname !== null && typeof _this.sid != 'undefined' && _this.sid !== null) {
					alert('custom.jquery.js - Setting cookie ' + _this.sidname + ' to ' + _this.sid);
					console.log('custom.jquery.js - _this ');
					console.log(_this);
					//request.setRequestHeader("Cookie", sid);
				}
			},
			data: 'loginemail=georgescu_bogdan@hotmail.com&loginpassword=12345'
		}).fail(function(response) {
			// Failure
			var json = $.parseJSON(response);
			var status = json.status;
			var message = json.message;

			var messages = ''
							+ '<p class="alert error">status: ' + status + '</p>'
							+ '<p class="alert error">message: ' + message + '</p>'
							;
			$responseHolder.append(messages).show();
			_this.ClearSession();
		}).done(function(response) {
			// Success
			try {
				messages = '';
				var json = $.parseJSON(response);
				var status = json.status;
				var message = json.message;
				alert('RetrieveData - json: ' + json);
				if (status) {
					var user = json.user;
					var order = json.order;
					_this.SetLoggedData(json);
					if (_this.logged == 1) {
						return true;
					} else {
						// Error - Reading local storage data
						messages += '<p class="alert error">Could not read local storage data</p><p class="alert error">' + message + '</p>';
					}
				} else {
					// Error - Credentials error
					$responseHolder.hide().html('<p class="alert error">' + message + '</p>').fadeIn('fast');
					_this.ClearSession();
				}
			} catch (err) {
				// Error - Misc
				messages = '<p class="alert error">Call succceeded, but parsing got an error: ' + err.message + '.</p>';
				$responseHolder.hide().html(messages).fadeIn('fast');
				$responseHolder2.hide().html('<p class="alert error">response: ' + response + '</p>').fadeIn('fast');
			}
			return false;
		});
		return false;
	};

	classLocalStorage.prototype.UpdatePageElements = function() {
		var _this = this;
		$('.autoupdate').each(function() {
			var $this = $(this);			
			$this.hide();
			if ($this.is('input') || $this.is('select') || $this.is('textarea')) {
				$this.val(_this[$this.data('term')]);
			} else {
				$this.html(_this[$this.data('term')]).show();
			}
			$this.show();
		})
	}
	
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

		/*
		messages += ''
					+ '<p class="error">1 this.oid: ' + this.oid + '</p>'
					+ '<p class="error">1 this.logged: ' + this.logged + '</p>';
		$responseHolder3.append(messages).fadeIn();
		*/
		
		/* Check if saved local storage userData is ok */
		if (typeof this.oid == 'undefined') { return false; };
		if (typeof this.logged == 'undefined' || this.logged != 1) { this.ClearSession(); return false; };
		
		/*
		messages += ''
					+ '<p class="error">a2 this.oid: ' + this.oid + '</p>'
					+ '<p class="error">a2 this.logged: ' + this.logged + '</p>';
		$responseHolder3.append(messages).fadeIn();
		*/
		
		return true;
	};
	
	classLocalStorage.prototype.Set = function(name, value) {
		this[name] = value;
		window.localStorage.setItem(name, value);
	}
	
	/* Sursa: http://ko-lwin.blogspot.ro/2010/12/how-to-secure-classic-asp-session-id.html */
	classLocalStorage.prototype.GetSID = function() {
		var c_name = "ASPSESSIONID";
		console.log('document.cookie: ');
		console.log(document.cookie);
		if (document.cookie.length > 0) {
			c_start = document.cookie.indexOf(c_name);
			if (c_start!=-1) {
				c_end=document.cookie.indexOf("=",c_start);
				if (c_end==-1) c_end=document.cookie.length;
				return unescape(document.cookie.substring(c_start,c_end));
			}
			return "";
		}
		return "";
	}
	classLocalStorage.prototype.GetLoggedData = function() {
		/* Check if it's logged */
		this.username = window.localStorage.getItem("username");
		this.firstname = window.localStorage.getItem('firstname');
		this.lastname = window.localStorage.getItem('lastname');
		this.dobday = window.localStorage.getItem('dobday');
		this.dobmonth = window.localStorage.getItem('dobmonth');
		this.dobyear = window.localStorage.getItem('dobyear');
		this.age = window.localStorage.getItem('age');
		this.weight = window.localStorage.getItem('weight');
		this.heightfeet = window.localStorage.getItem('heightfeet');
		this.heightinches = window.localStorage.getItem('heightinches');
		this.sex = window.localStorage.getItem('sex');
		this.dayphone = window.localStorage.getItem('dayphone');
		this.nightphone = window.localStorage.getItem('nightphone');
		this.shipaddress = window.localStorage.getItem('shipaddress');
		this.shipaddress2 = window.localStorage.getItem('shipaddress2');
		this.ShipCity = window.localStorage.getItem('ShipCity');
		this.ShipState = window.localStorage.getItem('ShipState');
		this.ShipCountry = window.localStorage.getItem('ShipCountry');
		this.ShipZip = window.localStorage.getItem('ShipZip');
		this.BillAddress = window.localStorage.getItem('BillAddress');
		this.BillAddress2 = window.localStorage.getItem('BillAddress2');
		this.BillCity = window.localStorage.getItem('BillCity');
		this.BillState = window.localStorage.getItem('BillState');
		this.BillCountry = window.localStorage.getItem('BillCountry');
		this.BillZip = window.localStorage.getItem('BillZip');
		this.itemscount = window.localStorage.getItem('itemscount');
		this.items = window.localStorage.getItem('items');
		//this.logged = window.localStorage.getItem('logged');
		this.sidname = window.localStorage.getItem('sidname');
		this.sid = window.localStorage.getItem('sid');

		/*
		messages += '<p class="error">1 this.firstname: ' + this.firstname + '</p>'
					+ '<p class="error">1 this.lastname: ' + this.lastname + '</p>'
					+ '<p class="error">1 this.dayphone: ' + this.dayphone + '</p>'
					+ '<p class="error">1 this.username: ' + this.username + '</p>'
					+ '<p class="error">1 window.localStorage.getItem(logged): ' + window.localStorage.getItem('logged') + '</p>';
					+ '<p class="error">1 this.logged: ' + this.logged + '</p>';
		$responseHolder3.append(messages).fadeIn();
		*/
		

		/* Check if saved local storage userData is ok */
		if (typeof this.username == 'undefined') { this.ClearSession(); return false; };
		if (typeof this.items == 'undefined') { this.ClearSession(); return false; };
		if (typeof this.sidname == 'undefined') { this.ClearSession(); return false; };
		if (typeof this.sid == 'undefined') { this.ClearSession(); return false; };
		
		messages += '<p class="error">2 window.localStorage.getItem firstname: ' + window.localStorage.getItem('firstname') + '</p>'
					+ '<p class="error">2 window.localStorage.getItem lastname: ' + window.localStorage.getItem('lastname') + '</p>'
					+ '<p class="error">2 window.localStorage.getItem dayphone: ' + window.localStorage.getItem('dayphone') + '</p>'
					+ '<p class="error">2 window.localStorage.getItem username: ' + window.localStorage.getItem('username') + '</p>'
					+ '<p class="error">2 this.logged: ' + this.logged + '</p>';
		$responseHolder2.html(messages).fadeIn();
		//alert('GetLoggedData - this.logged: ' + this.logged);
		//this.logged = 1;
		//if (typeof this.logged == 'undefined' || this.logged != 1) { this.ClearSession(); return false; };
		
		return this.logged;
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
	
	/* Only for in-browser (not in Android) testing */
	onDeviceReady();
});