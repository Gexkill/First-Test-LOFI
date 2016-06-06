<%-- 
	import required package
--%>
<%@  
	page import="java.sql.*,java.io.*" 
%>

<% 
char doppiApici = '"';
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8>
	<link rel="stylesheet" href="reserveStileJSP.css" />
	<title>Reserve</title>
	<script type="text/javascript" src="funzioniJSP.js"></script>
</head>
<nav id=menuApp>
	<h1 id=t1>LOFI</h1>
	<h2 id=t2>Local Deals Finder</h2>
</nav>
<body onload=pickup()>
	<div id="wrapper">
		<p id="name">Name</p>
		<input id="valoreNome" type="text">
		<p id="address">Address</p>
		<input id="valoreIndirizzo" type="text">
		<p id="paymentMethod">Payment method</p>
		<button id="paypal" type="button" 
			onclick=SelezionaMetodoPagamento("paypal")>
			<img src="https://static.webshopapp.com/shops/098694/files/045308334/paypal-logo.png"
				alt="paypal logo" id="paypalLogo">
		</button>
		<button id="visa" type="button" 
			onclick=SelezionaMetodoPagamento("visa")>
			<img src="https://qph.is.quoracdn.net/main-thumb-t-3075-50-ohPNxD2uu8xYpb1Wm4MOQ53VFLQ3Ir82.jpeg" 
				alt="visa logo" id="visaLogo">
		</button>
		<button id="mastercard" type="button" 
			onclick=SelezionaMetodoPagamento("mastercard")>
			<img src="http://www.bacchusthairestaurant.com.au/images/icon-mastercard.png"
				alt="mastercard logo" id="mastercardLogo">
		</button>
		<button id="postepay" type="button" 
			onclick=SelezionaMetodoPagamento("postepay")>
			<img src="http://www.microtechmobile.it/SMS/images/icons/payment/postepay.png"
				alt="postepay logo" id="postepayLogo">
		</button>
		<button id="contanti" type="button" 
			onclick=SelezionaMetodoPagamento("contanti")>
			<img src="http://www.ferraragroup.it/file/2014/08/icon-money.png" 
				alt="contanti logo" id="contantiLogo">
		</button>
		<p id="pickupIn">
			Pickup in
		</p>
		<input id="stimaTempo" type="text" readonly >
	</div>
</body>
<footer id="filtriApp">>
	<FORM METHOD=POST ACTION="overview.jsp">
		<button id="homeButton" type="submit">
			<img src="http://23cose.wikispaces.com/file/view/Wiki%20Home.png/395752830/Wiki%20Home.png" 
				alt="home logo" id="homeLogo">
		</button>
	</FORM>
	<FORM METHOD=POST ACTION="inviaDatiCompany.jsp" id="inviaDati">
		<input type=submit value="Send data to shop" id="bottoneOfferta" 
			onclick="checkInputValues()">
		<input type=hidden value="<%=request.getParameter("partitaIva")%>"
	    	name="partitaIva">
	    <input type=hidden value="<%=request.getParameter("codProdotto")%>"
	    	name="codProdotto">
	    <input type=hidden value="<%=request.getParameter("prezzoOfferta")%>"
	   		name="prezzoOfferta">
	   	<input type=hidden value="<%=request.getParameter("nomeNegozio")%>"
	   		name="nomeNegozio">
	</FORM>
</footer>
</html>
		
		
		
		
		
		