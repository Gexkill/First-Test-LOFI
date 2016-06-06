package Lofi;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Overview extends HttpServlet {

	  /** 
	   * servlet container of overview.html page
	   */
		private static final long serialVersionUID = 7710516062344754891L;
		
		private static final String FILTRO_PREZZO = "700";
		private static final String FILTRO_DISTANZA = "1000";
		
		private Connection dbcon;  // Connection for scope of this servlet
		
			public void init(ServletConfig config) throws ServletException
			{  
				
		        ServletContext context = config.getServletContext();

		        // Load the PostgreSQL driver
		        try 
		           {
		              Class.forName("org.postgresql.Driver");
		              dbcon = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
		                      "postgres", "");
		              context.setAttribute("db-connection", dbcon);
		            }
		        catch (ClassNotFoundException ex)
		            {
		               System.err.println("ClassNotFoundException: " + ex.getMessage());
		               throw new ServletException("Class not found Error");
		            }
		        catch (SQLException ex)
		            {
		               System.err.println("SQLException: " + ex.getMessage());
		            }
			}
			
			public void doGet(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
				doPost(req,res);
			}
			
			public void doPost(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {

			    res.setContentType("text/html");
			    PrintWriter out = res.getWriter();
			    
			    Object valoreMaxPrezzo;
			    Object valoreMaxDistance;
			    Integer partitaIva = 0;
			    Integer codProdotto = 0;
			    String nomeNegozio = "";
			    String imgOfferta ="";
			    String prezzoOfferta = "";
			    String distanzaOfferta = "";
			    
				HttpSession session = req.getSession();
				
				// Recupero valori dei filtri di prezzo e distanza
				// in base alla sessione utente
				
				// Se non li trovo allora è la prima volta che l'utente
				// usa l'applicazione, quindi imposterò i parametri di default
				
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
			    
			    out.println("<!DOCTYPE html>");
			    out.println("<html>");
			    out.println("<head>");
			    out.println("<meta charset=\"UTF-8\">");
			    out.println("<link rel=\"stylesheet\" href=\"overviewStile.css\" />");
			    out.println("<title>Overview</title>");
			    out.println("<script type=\"text/javascript\" src=\"funzioni.js\"></script>");
			    out.println("</head>");
			    out.println("<nav id=\"menuApp\">");
			    out.println("<h1 id=\"t1\">LOFI</h1>");
			    out.println("<h2 id=\"t2\">Local Deals Finder</h2>");
			    out.println("</nav>");
			    out.println("<body onload=ApplicaFiltro()>");
			    out.println("<div id=wrapper>");
			    
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
					    			
					    			char doppiApici = '"';
					    			
					    			partitaIva = rs.getInt(6);
					    			codProdotto = rs.getInt(7);
								    nomeNegozio = rs.getString(1);
								    imgOfferta = rs.getString(10);
								    prezzoOfferta = rs.getString(11);
								    distanzaOfferta = rs.getString(12);
					    			
					    			out.println("<br id="+ (j+1) +">");
					    			
					    			if (i>0){
						    			out.println("<br id="+ (j+1) +">");		
					    			}
					    			out.println("<form method=post action=\"details.html\">");
					    			out.println("<button onclick=\"parFiltro("+ (i+1) +")\""
					    					+ " id=\"bottone"+ (i+1) +"\""
					    							+ " type=submit"
					    							+ " name=bottone"
					    							+ " value="+ (i+1) +">");
					    			out.println("<input type=hidden name=partitaIva"
					    					+ " value="+ partitaIva +">");
					    			out.println("<input type=hidden name=codProdotto"
					    					+ " value="+ codProdotto +">");
					    			out.println("<img src="+doppiApici+ imgOfferta +doppiApici+""
					    					+ "	alt="+doppiApici+ nomeNegozio +doppiApici+""
					    							+ " id=imgOfferta>");
					    			out.println("<p id=\"prezzoOfferta"+ (i+1) +"\""
					    					+ " style=\"display: inline-block; color: rgb(255, 102, 0); font-size: 16px; font-weight: 900; position: relative; left: -5px;\">"+prezzoOfferta+"</p>");
					    			out.println("<img src=\"http://img.stockfresh.com/files/n/nickylarson974/x/87/4846156_18842284.jpg\""
					    					+ " alt=\"logo distanza\""
					    					+ " id=impronta>");
					    			out.println("<p id=\"distanzaOfferta"+ (i+1) +"\""
					    					+ " style=\"display: inline-block; color: rgb(0, 0, 153); font-size: 14px; font-weight: 500; position: relative; left: 15p;\">"+distanzaOfferta+"</p>");
					    			out.println("</button>");
					    			out.println("</form");
					    			
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
			    
			    out.println("<br>");
			    out.println("<br>");
			    out.println("<br>");
			    out.println("<br>");
			    out.println("<br>");
			    out.println("<br>");
			    out.println("</div>");
			    out.println("</body>");
			    out.println("<footer id=filtriApp>");
			    out.println("<p id=P1>");
			    out.println("Max. price:");
			    out.println("<input id=valoreMaxPrezzo type=text"
			    		+" value="+valoreMaxPrezzo+" onchange=ApplicaFiltro()>"
			    				+ "");
			    out.println("&euro;");
			    out.println("</p>");
			    out.println("<p id=P2>");
			    out.println("Max. distance:");
			    out.println("<input id=valoreMaxDistance type=text"
			    		+" value="+valoreMaxDistance+" onchange=ApplicaFiltro()>");
			    out.println("m");
			    out.println("</p>");
			    out.println("</footer>");
			    out.println("</html>");
				
				out.close();
			}
			
		    public void destroy() {
		        // Clean up
		        try {
		          if (dbcon != null){
		            dbcon.close();
		          }
		        } catch (SQLException sqle) {
		        	
		        }
		    }
}
