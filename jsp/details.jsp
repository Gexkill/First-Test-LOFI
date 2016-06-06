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

	String indirizzoNegozio = "";
    String cittaNegozio = "";
    String orarioApertura = "";
    String nomeNegozio = "";
    String imgOfferta="";
    String prezzoOfferta = "";
    String distanzaOfferta = "";
    String descProdotto = "";
    
	session = request.getSession();
	
	session.setAttribute("valoreMaxPrezzo", request.getParameter("inputFiltroPrezzo"));
	session.setAttribute("valoreMaxDistance", request.getParameter("inputFiltroDistanza"));

	char doppiApici = '"';
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="detailsStileJSP.css" />
	<title>Details</title>
	<script type="text/javascript" src="funzioniJSP.js"></script>
</head>
<nav id="menuApp">
	<h1 id="t1">LOFI</h1>
	<h2 id="t2">Local Deals Finder</h2>
</nav>
<body>
	<div id="wrapper">

<%
	try {
		synchronized(dbcon) {
		    		String queryInfoNegozio = "SELECT * FROM \"NEGOZIO\" WHERE \"PARTITAIVANUMERIC\"="
		            		+Integer.parseInt(request.getParameter("partitaIva"));
		    		
		    		System.out.println("La query che è stata"
		    				+ " eseguita è la seguente: "
		    				+queryInfoNegozio);
		    		
		    		PreparedStatement statement = dbcon.prepareStatement(queryInfoNegozio);
		            ResultSet rs = statement.executeQuery();
		            
		    		if(rs.next()) {
		    			
		    			indirizzoNegozio = rs.getString(2);
	            		cittaNegozio = rs.getString(3);
	            		orarioApertura = rs.getString(4);
	            		nomeNegozio = rs.getString(1);
		    		}
		    		
		    		String queryInfoOfferta = "SELECT * FROM \"OFFERTA\" WHERE \"CODPRODOTTO\"="
			        		+Integer.parseInt(request.getParameter("codProdotto"))
			        		+" AND \"PARTITAIVANEGOZIO\"="+Integer.parseInt(request.getParameter("partitaIva"));
		    		
		    		System.out.println("La query che è stata"
		    				+ " eseguita è la seguente: "
		    				+queryInfoOfferta);
		    		
		    		statement = dbcon.prepareStatement(queryInfoOfferta);
			        rs = statement.executeQuery();
			        
			        	if (rs.next()) {
			        		
			        		imgOfferta = rs.getString(4);
			        		prezzoOfferta = rs.getString(5);
			        		distanzaOfferta = rs.getString(6);
			        		descProdotto = rs.getString(7);
			        	}
		            	
		            	
		            rs.close();
		            statement.close();

		}
	}	
	catch(Exception ex){
		ex.printStackTrace();
		System.out.println("La query ha avuto esito negativo"
				+ "nessun dato è stato recuperato dal DB");
		return;
	}
%>

	<table>
		<tr>
		<td>
			<img src="<%=imgOfferta%>"
				alt="<%=nomeNegozio%>" 
				id="imgOfferta">
		</td>
		<td>
			<p id=prezzoOfferta><%=prezzoOfferta%></p>
		</td>
		<td>
			<img src="http://img.stockfresh.com/files/n/nickylarson974/x/87/4846156_18842284.jpg"
				alt="logo distanza" id=impronta>
		</td>
		<td>
			<p id=distanzaOfferta><%=distanzaOfferta%></p>
		</td>
		</tr>
		<tr>
		<td>
			<p id=address>
				<%=indirizzoNegozio%>
				<br>
				<%=cittaNegozio%>
			</p>
		</td>
		<td>
		<img src="http://bestwindows8apps.s3.amazonaws.com/icons/Icon.478518.png"
			alt="MapPosition logo" id=mapPosition>
		</td>
		</tr>
		<tr>
		<td>
			<img src="http://aifos.org/inst/aifos/public/data/general/images/associazione/contatti/orologio%202.jpg"
				alt="clock logo" id=clock>
		</td>
		<td>
			<p id=orarioApertura>
				<%=orarioApertura%>
			</p>
		</td>
		</tr>
		<tr>
		<td>
			<p id=descProdottoOfferta>
				<%=descProdotto%>
			</p>
		</td>
		<td>
			<img src="http://www.buyhydrogrow.com/images/ok.png" 
				alt="OkHand logo" id=logoOk>
		</td>
		</tr>
		</table>
		</div>
</body>
<footer id="filtriApp">
	<FORM METHOD=POST ACTION="overview.jsp">
		<button id=homeButton type=submit>
			<img src="http://23cose.wikispaces.com/file/view/Wiki%20Home.png/395752830/Wiki%20Home.png"
				alt="home logo" id=homeLogo>
		</button>
	</FORM>
	<FORM METHOD=POST ACTION="reserve.jsp">
		<input type=submit value="Make The Deal" id=bottoneOfferta>
		<input type=hidden value="<%=request.getParameter("partitaIva")%>"
			name=partitaIva>
		<input type=hidden value="<%=request.getParameter("codProdotto")%>"
			name=codProdotto>
		<input type=hidden value="<%=prezzoOfferta%>"
			name=prezzoOfferta>
		<input type=hidden value="<%=nomeNegozio%>"
			name=nomeNegozio>
	</FORM>
</footer>
</html>
	






