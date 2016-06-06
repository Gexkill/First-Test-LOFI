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
	
	Integer numTransazione=0;
	String query = "";
	
	try {
		synchronized(dbcon) {
            query = "SELECT * FROM \"NUMERATORITRANSAZIONI\" WHERE \"PARTITAIVACOMPANY\"="
            		+Integer.parseInt(request.getParameter("partitaIva"));
            
            System.out.println("La query che è stata"
    				+ " eseguita è la seguente: "
    				+query);
            
            PreparedStatement statement = dbcon.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            	
            if (rs.next()) {
            	numTransazione = rs.getInt(2);
            	numTransazione++;
            	}
            
            rs.close();
            statement.close();					
		}
	}
	catch (Exception ex1) {
		try {
            query = "INSERT INTO \"NUMERATORITRANSAZIONI\" values (?,?,?)";
            PreparedStatement statement = dbcon.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(request.getParameter("partitaIva")));
            statement.setInt(2, numTransazione++);
            statement.setString(3, (String) request.getParameter("nomeNegozio"));
            statement.executeUpdate();
            statement.close();
		}
		catch (Exception ex2) {
			ex2.printStackTrace();
            return;
		}
	}
	
	try {
        synchronized(dbcon) {  
          
      	if (numTransazione == 0) {
      		numTransazione++;
      	}
          query = "INSERT INTO \"ORDINE\" values (?,?,?,?,?,?,?)";
          PreparedStatement statement = dbcon.prepareStatement(query);
          statement.setInt(1, Integer.parseInt(request.getParameter("partitaIva")));
          statement.setInt(2, numTransazione);
          statement.setString(3, (String) request.getParameter("nomeCliente"));
          statement.setString(4, (String) request.getParameter("indirizzoCliente"));
          statement.setString(5, (String) request.getParameter("metodoPagamento"));
          statement.setString(6, (String) request.getParameter("prezzoOfferta"));
          statement.setInt(7, Integer.parseInt(request.getParameter("codProdotto")));
          
          statement.executeUpdate();
          
          if (numTransazione == 1) {
	            query = "INSERT INTO \"NUMERATORITRANSAZIONI\" values (?,?,?)";
	            statement = dbcon.prepareStatement(query);
	            statement.setInt(1, Integer.parseInt(request.getParameter("partitaIva")));
	            statement.setInt(2, numTransazione);
	            statement.setString(3, (String) request.getParameter("nomeNegozio"));
          }
          else {
          	query = "UPDATE \"NUMERATORITRANSAZIONI\" SET \"NUMERATOREPROGRESSIVO\"="+numTransazione
	            		+" where \"PARTITAIVACOMPANY\"="+Integer.parseInt(request.getParameter("partitaIva"));
          	statement = dbcon.prepareStatement(query);
	            
          }
          
          statement.executeUpdate();
          statement.close();
        }
  } catch(Exception ex) {   
          ex.printStackTrace();
          return;
  }
	
	response.sendRedirect("overview.jsp");	
%>




