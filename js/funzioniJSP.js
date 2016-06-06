/*
	Function that select the payment method of reserve.jsp page
*/
function SelezionaMetodoPagamento(metodo) {
	alert(metodo);
	if (document.getElementById(metodo).style.borderColor == "red") {
		document.getElementById(metodo).style.borderColor = "#DBDBDB"; 
		document.getElementById(metodo).style.borderWidth = "1px";

	}
	else {
		document.getElementById(metodo).style.borderColor = "red"; 
		document.getElementById(metodo).style.borderWidth = "3px";

		if(metodo != "paypal") {
			document.getElementById("paypal").style.borderColor = "#DBDBDB"; 
			document.getElementById("paypal").style.borderWidth = "1px";
		}

		if(metodo != "visa") {
			document.getElementById("visa").style.borderColor = "#DBDBDB"; 
			document.getElementById("visa").style.borderWidth = "1px";
		}

		if(metodo != "mastercard") {
			document.getElementById("mastercard").style.borderColor = "#DBDBDB"; 
			document.getElementById("mastercard").style.borderWidth = "1px";
		}

		if(metodo != "postepay") {
			document.getElementById("postepay").style.borderColor = "#DBDBDB"; 
			document.getElementById("postepay").style.borderWidth = "1px";
		}

		if(metodo != "contanti") {
			document.getElementById("contanti").style.borderColor = "#DBDBDB"; 
			document.getElementById("contanti").style.borderWidth = "1px";
		}
	}
}

/*	
	Function that change the displayed deals following
	the change of X and Y filters of overview.jsp page
*/
function ApplicaFiltro() {
	
	var maxPrezzo = filtriDiDefault[0].defaultPrezzo;

	var maxDistanza = filtriDiDefault[0].defaultDistanza;
	
	// Retrieve the value of the filters to apply to the deals
	
	if (document.getElementById("valoreMaxPrezzo")){
		maxPrezzo = parseFloat(document.getElementById("valoreMaxPrezzo").value).toFixed(2);
	}
	
	if (document.getElementById("valoreMaxDistance")){
		maxDistanza = parseInt(document.getElementById("valoreMaxDistance").value);
	}

	// Counter variable for the loop that will check all the deals
	var i = 1;
	var j = 1;

	while (document.getElementById("bottone"+i)) {

	// CHECK THE PRICE FILTER	
		// Retrieve the numeric value of the i-th price deal
		var tempPrezzoOfferta = document.getElementById("prezzoOfferta"+i).textContent;
		tempPrezzoOfferta = parseFloat(tempPrezzoOfferta.trim().substr(0,tempPrezzoOfferta.trim().length-1)).toFixed(2);
		
		// I check that the i-th deal's value is greater of the filter's value
		
		// For perform this checking, i'll take the difference between 
		// the filter's value and the i-th deal's value
		
		// If the difference is negative, then the deal doesn't respect the filter
		// and it'll hidden
		// else if previous checking is positive, i show the deals to the user

		//j = ( i * 2 ) - 1;

		if (maxPrezzo - tempPrezzoOfferta < 0) {
			document.getElementById("bottone"+i).style.display = "none";
			document.getElementById(j).style.display = "none";
		}
		else {
			if (document.getElementById("bottone"+i).style.display == "none") {
				document.getElementById("bottone"+i).style.display = "inline";
				document.getElementById(j).style.display = "inline";		
			}
		}

	// CHECK THE DISTANCE FILTER

		// Retrieve the numeric value of the i-th distance deal
		var tempDistanzaOfferta = document.getElementById("distanzaOfferta"+i).textContent;
		tempDistanzaOfferta = parseInt(tempDistanzaOfferta.trim().substr(0,tempDistanzaOfferta.trim().length-1));
		
		// I check that the i-th distance deal's value is greater of the filter's value

		// If previous check is positive, then i hide the deal
		
		// else i do nothing, because if i try to show the deal, i could show
		// a deal that doesn't respect the binding on the price

		//j = ( i * 2 ) - 1;

		if (tempDistanzaOfferta > maxDistanza) {
			document.getElementById("bottone"+i).style.display = "none";
			document.getElementById(j).style.display = "none";
		}

		j++;
		i++;
	}
}

/*
	DEPRECATE STRUCTURE
	Data structure client side containing the data of the retrieved deals from DB
*/
/*
var offerte = [
	  	{
	  	imgOfferta: "http://iconcessionari.it/image/loghi-inpiu/autofficina.png", 
	  	prezzoOfferta: "29.90 €",
	  	impronta: "http://img.stockfresh.com/files/n/nickylarson974/x/87/4846156_18842284.jpg",
	  	distanzaOfferta: "189 m",
	  	nomeNegozio: "Autofficina S.r.l.",
	  	indirizzoNegozio:"Via Oberdan 35",
	  	cittaNegozio: "20100 Milano (MI)",
	  	orarioApertura: "9 AM - 5 PM",
	  	descrizioneProdotto: "945 item left",
	  	partitaIva: "04729123",
	  	codProdotto: "98765"
	  	},
	  	{
	  	imgOfferta: "http://www.pitstopadvisor.com/f/marche/dodge-[50x50].jpg", 
	  	prezzoOfferta: "209.00 €",
	  	impronta: "http://img.stockfresh.com/files/n/nickylarson974/x/87/4846156_18842284.jpg",
	  	distanzaOfferta: "78 m",
	  	nomeNegozio: "Concessonaria Dodge S.r.l.",
	  	indirizzoNegozio:"Via Manin 28",
	  	cittaNegozio: "20100 Milano (MI)",
	  	orarioApertura: "9 AM - 5 PM",
	  	descrizioneProdotto: "178 item left",
	  	partitaIva: "08726128",
	  	codProdotto: "13124"
	  	},
	  	{
	  	imgOfferta: "http://www.agataprofumerie.it/thumbnail/get/50_50/paco-rabanne-one-million-cologne-eau-de-parfum-3181.jpg", 
	  	prezzoOfferta: "59.99 €",
	  	impronta: "http://img.stockfresh.com/files/n/nickylarson974/x/87/4846156_18842284.jpg",
	  	distanzaOfferta: "827 m",
	  	nomeNegozio: "Profumeria La Gardenia",
	  	indirizzoNegozio:"Via Dante 1",
	  	cittaNegozio: "20100 Milano (MI)",
	  	orarioApertura: "9 AM - 7 PM",
	  	descrizioneProdotto: "129 item left",
	  	partitaIva: "09027615",
	  	codProdotto: "91232"
	  	},
	  	{
	  	imgOfferta: "http://coolbuddy.com/icon/logos/ico_mcdonald.gif", 
	  	prezzoOfferta: "15.65 €",
	  	impronta: "http://img.stockfresh.com/files/n/nickylarson974/x/87/4846156_18842284.jpg",
	  	distanzaOfferta: "372 m",
	  	nomeNegozio: "Mc Donald's",
	  	indirizzoNegozio:"Via Leonardo da Vinci 178",
	  	cittaNegozio: "20100 Milano (MI)",
	  	orarioApertura: "9 AM - 11 PM",
	  	descrizioneProdotto: "987 item left",
	  	partitaIva: "01274837",
	  	codProdotto: "32812"
	  	},
	  	{
	  	imgOfferta: "http://www.gazzettadellavoro.com/wp-content/uploads/2014/08/zara-logo-50x50.jpg", 
	  	prezzoOfferta: "49.99 €",
	  	impronta: "http://img.stockfresh.com/files/n/nickylarson974/x/87/4846156_18842284.jpg",
	  	distanzaOfferta: "100 m",
	  	nomeNegozio: "Abbigliamento Zara",
	  	indirizzoNegozio:"Via Milani 16",
	  	cittaNegozio: "20100 Milano (MI)",
	  	orarioApertura: "9 AM - 7 PM",
	  	descrizioneProdotto: "78 item left",
	  	partitaIva: "06278972",
	  	codProdotto: "67231"
	  	},
	  	{
	  	imgOfferta: "http://www.aperture-supermercati.it/immagini/immsmall/leroymerlin.gif", 
	  	prezzoOfferta: "19.07 €",
	  	impronta: "http://img.stockfresh.com/files/n/nickylarson974/x/87/4846156_18842284.jpg",
	  	distanzaOfferta: "750 m",
	  	nomeNegozio: "Leroy Merlin",
	  	indirizzoNegozio:"Viale Sardegna 40",
	  	cittaNegozio: "20100 Milano (MI)",
	  	orarioApertura: "9 AM - 7 PM",
	  	descrizioneProdotto: "12 item left",
	  	partitaIva: "03627189",
	  	codProdotto: "98172"
	  	},
	  	{
	  	imgOfferta: "http://www.lelencodeinegozi.it/assets/mall/centro-carosello-carugate/centro-carosello-carugate.jpeg", 
	  	prezzoOfferta: "57.90 €",
	  	impronta: "http://img.stockfresh.com/files/n/nickylarson974/x/87/4846156_18842284.jpg",
	  	distanzaOfferta: "980 m",
	  	nomeNegozio: "Centro Commericale Carorello",
	  	indirizzoNegozio:"Viale Monza 10",
	  	cittaNegozio: "20100 Milano (MI)",
	  	orarioApertura: "9 AM - 10 PM",
	  	descrizioneProdotto: "93 item left",
	  	partitaIva: "02891628",
	  	codProdotto: "67381"
	  	},
	  	{
	  	imgOfferta: "http://www.correttainformazione.it/wp-content/uploads/2016/02/volantino-mediaworld-1-50x50.png", 
	  	prezzoOfferta: "515.00 €",
	  	impronta: "http://img.stockfresh.com/files/n/nickylarson974/x/87/4846156_18842284.jpg",
	  	distanzaOfferta: "523 m",
	  	nomeNegozio: "Media World",
	  	indirizzoNegozio:"Viale Abruzzi 90",
	  	cittaNegozio: "20100 Milano (MI)",
	  	orarioApertura: "9 AM - 8 PM",
	  	descrizioneProdotto: "129 item left",
	  	partitaIva: "03627182",
	  	codProdotto: "72832"
	  	},
	  	{
	  	imgOfferta: "http://images.apple.com/v/iphone/d/images/buystrip_retail_icon.png", 
	  	prezzoOfferta: "629.00 €",
	  	impronta: "http://img.stockfresh.com/files/n/nickylarson974/x/87/4846156_18842284.jpg",
	  	distanzaOfferta: "45 m",
	  	nomeNegozio: "Apple S.p.a.",
	  	indirizzoNegozio:"Via Gianicolo 71",
	  	cittaNegozio: "20100 Milano (MI)",
	  	orarioApertura: "9 AM - 10 PM",
	  	descrizioneProdotto: "87 item left",
	  	partitaIva: "02738921",
	  	codProdotto: "42681"
	  	},
	  	{
	  	imgOfferta: "http://www.hoteldelavillesalsomaggiore.it/images/servizi/img_ristorante.gif", 
	  	prezzoOfferta: "45.00 €",
	  	impronta: "http://img.stockfresh.com/files/n/nickylarson974/x/87/4846156_18842284.jpg",
	  	distanzaOfferta: "672 m",
	  	nomeNegozio: "Ristorante da Piero",
	  	indirizzoNegozio:"Via Melagnano 63",
	  	cittaNegozio: "20100 Milano (MI)",
	  	orarioApertura: "9 AM - 10 PM",
	  	descrizioneProdotto: "98 item left",
	  	partitaIva: "02174820",
	  	codProdotto: "56123"
	  	}
];
*/

/*
	default filters values
*/
var filtriDiDefault = [
	{
		defaultPrezzo: 700,
		defaultDistanza: 1000
	}
];

/*
	DEPRECATE FEATURE: Function responsible of the display of the deals that respect the X and Y filters,
	that are default set on the overview.jsp page  
	
	NEW FEATURE: Function responsible of the redirecting on overview.jsp page for the loading
	of the data directly from DB
*/
function caricaDati () {
	
	document.getElementById("carica").click();
	
	/*
	 
	  DEPRECATE CODE FOR HTML CODE GENERATION PAGE
	 
	var div  = document.createElement("div");
	div.setAttribute("id", "wrapper");

	var i = 0;
	var j = 1;

	var priceFilter = filtriDiDefault[0].defaultPrezzo;

	var distanceFilter = filtriDiDefault[0].defaultDistanza;


	while (offerte[i]) {
		
		var br = document.createElement("br");
		br.setAttribute("id",j++);
		div.appendChild(br);
		
		var form = document.createElement("form");
		form.setAttribute("method", "post");
		form.setAttribute("action", "details.html");
		
		var button = document.createElement("button");
		button.setAttribute("onclick","parFiltro("+ (i+1) +")");
		button.setAttribute("id","bottone"+(i+1));
		button.setAttribute("type","submit");
		button.setAttribute("name","bottone");
		button.setAttribute("value",(i+1));
		
		var partitaIva = document.createElement("input");
		partitaIva.setAttribute("type","hidden");
		partitaIva.setAttribute("name","partitaIva");
		partitaIva.setAttribute("value",offerte[i].partitaIva);
		
		button.appendChild(partitaIva);
		
		var codProdotto = document.createElement("input");
		codProdotto.setAttribute("type","hidden");
		codProdotto.setAttribute("name","codProdotto");
		codProdotto.setAttribute("value",offerte[i].codProdotto);
		
		button.appendChild(codProdotto);
		
		var img = document.createElement("img");	
		img.setAttribute("src",offerte[i].imgOfferta);
		img.setAttribute("alt",offerte[i].nomeNegozio);
		img.setAttribute("id","imgOfferta");
		
		button.appendChild(img);
		
		var p = document.createElement("p");
		p.setAttribute("id","prezzoOfferta"+(i+1));
		p.style.display = "inline-block";
		p.style.color = "#FF6600";
		p.style.fontSize = "16px";
		p.style.fontWeight = 900;
		p.style.position = "relative";
		p.style.left = "-5px";

		var valPrezzo = document.createTextNode(offerte[i].prezzoOfferta);

		p.appendChild(valPrezzo);

		button.appendChild(p);

		img = document.createElement("img");
		img.setAttribute("src",offerte[i].impronta);
		img.setAttribute("alt","logo distanza");
		img.setAttribute("id","impronta");
		
		button.appendChild(img);

		p = document.createElement("p");
		p.setAttribute("id","distanzaOfferta"+(i+1));
		p.style.display = "inline-block";
		p.style.color = "#000099";
		p.style.fontSize = "14px";
		p.style.fontWeight = 500;
		p.style.position = "relative";
		p.style.left = "15px";

		var valDistanza = document.createTextNode(offerte[i].distanzaOfferta);

		p.appendChild(valDistanza);

		button.appendChild(p);
		
		form.appendChild(button);
		
		div.appendChild(form);
		
		i++;
	}
	
	div.appendChild(document.createElement("br"));
	div.appendChild(document.createElement("br"));
	div.appendChild(document.createElement("br"));
	div.appendChild(document.createElement("br"));
	div.appendChild(document.createElement("br"));
	
	document.body.appendChild(div);

	var footer = document.createElement("footer");
	footer.setAttribute("id","filtriApp");

	var pp = document.createElement("p");
	pp.setAttribute("id","P1");

	var descMaxPrice = document.createTextNode("Max. price:");
	pp.appendChild(descMaxPrice);

	var input = document.createElement("input");
	input.setAttribute("id","valoreMaxPrezzo");
	input.setAttribute("type","text");
	input.setAttribute("value",priceFilter);
	input.setAttribute("onchange","ApplicaFiltro()");

	pp.appendChild(input);

	descMaxPrice = document.createTextNode("€");
	pp.appendChild(descMaxPrice);

	footer.appendChild(pp);

	pp = document.createElement("p");
	pp.setAttribute("id","P2");

	descMaxPrice = document.createTextNode("Max. distance:");
	pp.appendChild(descMaxPrice);

	input = document.createElement("input");
	input.setAttribute("id","valoreMaxDistance");
	input.setAttribute("type","text");
	input.setAttribute("value",distanceFilter);
	input.setAttribute("onchange","ApplicaFiltro()");
	
	pp.appendChild(input);

	descMaxPrice = document.createTextNode("m");
	pp.appendChild(descMaxPrice);

	footer.appendChild(pp);

	document.body.appendChild(footer);

	ApplicaFiltro();
	*/
}

/*
  	Function Random Generator of request time per retrieve the deal's product 
  	on reserve.jsp page
*/
function pickup() {
	var tempo = Math.round(20*Math.random());
	document.getElementById("stimaTempo").value = tempo + " minutes";
}

/*
	Function that check the inputs of data
	in the reserve.html page
	
	In particular it check that is valued the key fields
	Nome, Address and Payment Method.
	These fiels are mandatory for create the order to ship
	to the shop
*/
function checkInputValues() {
	var error = false;
	if (document.getElementById("valoreNome").value) {
	} 
	else {
		alert("Nome Cliente Obbligatorio");
		error = true;
	}
	
	if (document.getElementById("valoreIndirizzo").value) {
	} 
	else {
		alert("Indirizzo Cliente Obbligatorio");
		error = true;
	}

	if (document.getElementById("paypal").style.borderColor == "red"
		|| document.getElementById("visa").style.borderColor == "red"
		|| document.getElementById("mastercard").style.borderColor == "red"
		|| document.getElementById("postepay").style.borderColor == "red"
		|| document.getElementById("contanti").style.borderColor == "red") {
	}
	else {
		alert("Selezionare un metodo di pagamento");
		error = true;
	}

	if (error == true) {
		document.getElementById("inviaDati").action = "";
		document.getElementById("bottoneOfferta").type = "button";
	}
	else {
		
		var nomeCliente = document.createElement("input");
		nomeCliente.setAttribute("type","hidden");
		nomeCliente.setAttribute("name","nomeCliente");
		nomeCliente.setAttribute("value",document.getElementById("valoreNome").value);
		
		document.getElementById("inviaDati").appendChild(nomeCliente);
		
		var indirizzoCliente = document.createElement("input");
		indirizzoCliente.setAttribute("type","hidden");
		indirizzoCliente.setAttribute("name","indirizzoCliente");
		indirizzoCliente.setAttribute("value",document.getElementById("valoreIndirizzo").value);
		
		document.getElementById("inviaDati").appendChild(indirizzoCliente);
		
		var metodoPagamento = document.createElement("input");
		metodoPagamento.setAttribute("type","hidden");
		metodoPagamento.setAttribute("name","metodoPagamento");
		
		switch ("red") {
		
		case document.getElementById("paypal").style.borderColor:
			metodoPagamento.setAttribute("value","paypal");
			break;
		case document.getElementById("visa").style.borderColor:
			metodoPagamento.setAttribute("value","visa");
			break;
		case document.getElementById("mastercard").style.borderColor:
			metodoPagamento.setAttribute("value","mastercard");
			break;
		case document.getElementById("postepay").style.borderColor:
			metodoPagamento.setAttribute("value","postepay");
			break;
		case document.getElementById("contanti").style.borderColor:
			metodoPagamento.setAttribute("value","contanti");
			break;
		default:
			break;
		}
		
		document.getElementById("inviaDati").appendChild(metodoPagamento);
		
		document.getElementById("inviaDati").action = "inviaDatiCompany.jsp";
		document.getElementById("bottoneOfferta").type = "submit";
	}
	
}

/*	
	Function that pass the filters values at the details page for
	set them like session's attributes, so they are persistent
	
 */
function parFiltro(numBottone) {
	if (document.getElementById("bottone"+numBottone)) {
		
		if(document.getElementById("inputFiltroPrezzo"+numBottone)) {
			
			document.getElementById("inputFiltroPrezzo"+numBottone).setAttribute("value",
					document.getElementById("valoreMaxPrezzo").value)
		}
		else {
			var inputPrezzo = document.createElement("input");
			inputPrezzo.setAttribute("type","hidden");
			inputPrezzo.setAttribute("id","inputFiltroPrezzo"+numBottone);
			inputPrezzo.setAttribute("name","inputFiltroPrezzo");
			inputPrezzo.setAttribute("value",document.getElementById("valoreMaxPrezzo").value);
			
			document.getElementById("bottone"+numBottone).appendChild(inputPrezzo);
			
		}
		
		if(document.getElementById("inputFiltroDistanza"+numBottone)) {
			
			document.getElementById("inputFiltroDistanza"+numBottone).setAttribute("value",
					document.getElementById("valoreMaxDistance").value)
					
		}
		else {
			var inputDistanza = document.createElement("input");
			inputDistanza.setAttribute("type","hidden");
			inputDistanza.setAttribute("id","inputFiltroDistanza"+numBottone);
			inputDistanza.setAttribute("name","inputFiltroDistanza");
			inputDistanza.setAttribute("value",document.getElementById("valoreMaxDistance").value);

			
			document.getElementById("bottone"+numBottone).appendChild(inputDistanza);
		}
	}
}