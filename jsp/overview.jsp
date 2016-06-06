<%-- 
	import required package
--%>
<%@  
	page import="java.sql.*,java.io.*" 
%>

<%
	// Initializa DB connection 
	// and execute queries

	Connection dbcon = null;
	
	ServletContext context = config.getServletContext();

	try {
		Class.forName("org.postgresql.Driver");
		
		dbcon = DriverManager.getConnection(
		"jdbc:postgresql://localhost:5432/postgres",
		"postgres", "");
		
		context.setAttribute("db-connection", dbcon);

	}
	catch (ClassNotFoundException ex) {
		System.err.println("ClassNotFoundException: " 
		+ ex.getMessage());

		throw new ServletException("Class not found Error");
	}
	catch (SQLException ex) {
		System.err.println("SQLException: " 
		+ ex.getMessage());
	}

	final String FILTRO_PREZZO = "700";
	final String FILTRO_DISTANZA = "1000";

	Object valoreMaxPrezzo;
    Object valoreMaxDistance;
    Integer partitaIva = 0;
    Integer codProdotto = 0;
    String nomeNegozio = "";
    String imgOfferta ="";
    String prezzoOfferta = "";
    String distanzaOfferta = "";
    
	session = request.getSession();
	
	// Recupero valori dei filtri di prezzo e distanza
	// in base alla sessione utente
	
	// Se non li trovo allora è la prima volta che l'utente
	// usa l'applicazione, quindi imposterà i parametri di default
	
	if (session.getAttribute("valoreMaxPrezzo") != null){
		valoreMaxPrezzo = session.getAttribute("valoreMaxPrezzo");
	}
	else {
		valoreMaxPrezzo = FILTRO_PREZZO;
	}
	
	if (session.getAttribute("valoreMaxDistance") != null){
		valoreMaxDistance = session.getAttribute("valoreMaxDistance");
	}
	else {
		valoreMaxDistance = FILTRO_DISTANZA;
	}

	char doppiApici = '"';
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="overviewStileJSP.css" />
	<title>Overview</title>
	<script type="text/javascript" src="funzioniJSP.js"></script>
</head>
<nav id="menuApp">
	<h1 id="t1">LOFI</h1>
	<h2 id="t2">Local Deals Finder</h2>
</nav>
<body onload="ApplicaFiltro()">
	<div id="wrapper">

<%
	try {
		synchronized(dbcon) {
		    try {	    
		    	synchronized(dbcon) {
		    		String query = "select * from \"NEGOZIO\" "
		    				+ "inner join \"OFFERTA\" on \"PARTITAIVANUMERIC\" = \"PARTITAIVANEGOZIO\"";
		    		
		    		System.out.println("La query che è stata"
		    				+ " eseguita è la seguente: "
		    				+query);
		    		
		    		PreparedStatement statement = dbcon.prepareStatement(query);
		            ResultSet rs = statement.executeQuery();
		            
		            Integer i = new Integer(0);
		            Integer j = new Integer(0);
		            
		    		while(rs.next()) {
		    			
		    			partitaIva = rs.getInt(6);
		    			codProdotto = rs.getInt(7);
					    nomeNegozio = rs.getString(1);
					    imgOfferta = rs.getString(10);
					    prezzoOfferta = rs.getString(11);
					    distanzaOfferta = rs.getString(12);
%>
		<br id=<%=j+1%>>
		
		<form method=post action="details.jsp">
			<button onclick="parFiltro(<%=i+1%>)"
		 		id="bottone<%=i+1%>" type="submit"
		    	name="bottone" value="<%=i+1%>">
		    <input type="hidden" name="partitaIva"
		   		value="<%=partitaIva%>">
		    <input type="hidden" name="codProdotto"
		 		value="<%=codProdotto%>">
		    <img src="<%=imgOfferta%>"
		    	alt="<%=nomeNegozio%>"
		    	id="imgOfferta">
		    <p id="prezzoOfferta<%=i+1%>"
		    	style="display: inline-block; color: rgb(255, 102, 0); 
		    	font-size: 16px; font-weight: 900; position: relative; 
		    	left: -5px;">
		    <%=prezzoOfferta%>
		    </p>
		    <img src="http://img.stockfresh.com/files/n/nickylarson974/x/87/4846156_18842284.jpg" 
		    alt="logo distanza" id="impronta">
		    <p id="distanzaOfferta<%=i+1%>"
		    	style="display: inline-block; color: rgb(0, 0, 153); 
		    	font-size: 14px; font-weight: 500; position: relative; 
		    	left: 15p;">
		    <%=distanzaOfferta%>
		   	</p>
		    </button>
		</form>
<%
		    			j++;
		    			i++;
		    		}
		    		
		    		rs.close();
		            statement.close();
		    	}
		    }
		    catch(Exception ex){
		    	System.out.println("La query ha recuperato dei dati");
		    }
		}
	}
	catch(Exception ex){
		System.out.println("La query ha avuto esito negativo"
				+ "nessun dato è stato recuperato dal DB");
	}
%>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
	</div>
</body>
<footer id="filtriApp">
	<p id="P1">
		Max. price:
		<input id="valoreMaxPrezzo" type="text"
			value="<%=valoreMaxPrezzo%>" onchange="ApplicaFiltro()">
		&euro;
	</p>
	<p id="P2">
		Max. distance:
		<input id="valoreMaxDistance" type="text"
			value="<%=valoreMaxDistance%>" onchange="ApplicaFiltro()">
		m
	</p>
</footer>
</html>






