package Lofi;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class InviaDatiCompany extends HttpServlet{

	  /** 
	   * servlet container that it send data to the database company 
	   * and rendering to the overview.html page
	   */
		private static final long serialVersionUID = 7710556462344753891L;
		
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
			
			public void doPost(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {

				res.setContentType("text/html");
			    PrintWriter out = res.getWriter();
			    
				Integer numTransazione=0;
				String query = "";
				try {
					synchronized(dbcon) {
			            query = "SELECT * FROM \"NUMERATORITRANSAZIONI\" WHERE \"PARTITAIVACOMPANY\"="
			            		+Integer.parseInt(req.getParameter("partitaIva"));
			            
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
			            statement.setInt(1, Integer.parseInt(req.getParameter("partitaIva")));
			            statement.setInt(2, numTransazione++);
			            statement.setString(3, (String) req.getParameter("nomeNegozio"));
			            statement.executeUpdate();
			            statement.close();
					}
					catch (Exception ex2) {
						ex2.printStackTrace();
			            out.println("<HTML>" +
			                        "<Head><Title>" +
			                        "Db Test: Error" +
			                        "</Title></Head>\n<Body>" +
			                        "<P>SQL error in doPost: " +
			                        ex2.getMessage() + "</P></Body></HTML>");
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
			            statement.setInt(1, Integer.parseInt(req.getParameter("partitaIva")));
			            statement.setInt(2, numTransazione);
			            statement.setString(3, (String) req.getParameter("nomeCliente"));
			            statement.setString(4, (String) req.getParameter("indirizzoCliente"));
			            statement.setString(5, (String) req.getParameter("metodoPagamento"));
			            statement.setString(6, (String) req.getParameter("prezzoOfferta"));
			            statement.setInt(7, Integer.parseInt(req.getParameter("codProdotto")));
			            
			            statement.executeUpdate();
			            
			            if (numTransazione == 1) {
				            query = "INSERT INTO \"NUMERATORITRANSAZIONI\" values (?,?,?)";
				            statement = dbcon.prepareStatement(query);
				            statement.setInt(1, Integer.parseInt(req.getParameter("partitaIva")));
				            statement.setInt(2, numTransazione);
				            statement.setString(3, (String) req.getParameter("nomeNegozio"));
			            }
			            else {
			            	query = "UPDATE \"NUMERATORITRANSAZIONI\" SET \"NUMERATOREPROGRESSIVO\"="+numTransazione
				            		+" where \"PARTITAIVACOMPANY\"="+Integer.parseInt(req.getParameter("partitaIva"));
			            	statement = dbcon.prepareStatement(query);
				            
			            }
			            
			            statement.executeUpdate();
			            statement.close();
			          }
			    } catch(Exception ex) {   
			            ex.printStackTrace();
			            out.println("<HTML>" +
			                        "<Head><Title>" +
			                        "Db Test: Error" +
			                        "</Title></Head>\n<Body>" +
			                        "<P>SQL error in doPost: " +
			                        ex.getMessage() + "</P></Body></HTML>");
			            return;
			    }
				
				res.sendRedirect("overview.html");
		        
		        out.close();
			}
			
		    public void destroy() {
		        // Clean up
		        try {
		          if (dbcon != null){
		            dbcon.close();
		          }
		        } catch (SQLException sqle) {}
		    }
}
