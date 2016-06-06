package Lofi;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;



public class Details extends HttpServlet {

  /** 
   * servlet container of details.html page
   */
	private static final long serialVersionUID = 7710556062344753891L;
	
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
		    
			HttpSession session = req.getSession();
			
			session.setAttribute("valoreMaxPrezzo", req.getParameter("inputFiltroPrezzo"));
			session.setAttribute("valoreMaxDistance", req.getParameter("inputFiltroDistanza"));
		    
		    String indirizzoNegozio = "";
		    String cittaNegozio = "";
		    String orarioApertura = "";
		    String nomeNegozio = "";
		    String imgOfferta="";
		    String prezzoOfferta = "";
		    String distanzaOfferta = "";
		    String descProdotto = "";
		    try {
		          synchronized(dbcon) {
		            
		            String queryInfoNegozio = "SELECT * FROM \"NEGOZIO\" WHERE \"PARTITAIVANUMERIC\"="
		            		+Integer.parseInt(req.getParameter("partitaIva"));
		            
		    		System.out.println("La query che è stata"
		    				+ " eseguita è la seguente: "
		    				+queryInfoNegozio);
		            
		            PreparedStatement statement = dbcon.prepareStatement(queryInfoNegozio);
		            ResultSet rs = statement.executeQuery();
		            	
		            if (rs.next()) {
		            		
		            		indirizzoNegozio = rs.getString(2);
		            		cittaNegozio = rs.getString(3);
		            		orarioApertura = rs.getString(4);
		            		nomeNegozio = rs.getString(1);
		            	}
		            
			        String queryInfoOfferta = "SELECT * FROM \"OFFERTA\" WHERE \"CODPRODOTTO\"="
			        		+Integer.parseInt(req.getParameter("codProdotto"))
			        		+" AND \"PARTITAIVANEGOZIO\"="+Integer.parseInt(req.getParameter("partitaIva"));
			        
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
		    
		    char doppiApici = '"';
		    
		    out.println("<!DOCTYPE html>");
		    out.println("<html>");
		    out.println("<head>");
		    out.println("<meta charset=UTF-8>");
		    out.println("<link rel=\"stylesheet\" href=\"detailsStile.css\" />");
		    out.println("<title>Details</title>");
		    out.println("<script type=text/javascript src=funzioni.js></script>");
		    out.println("</head>");
		    out.println("<nav id=menuApp>");
		    out.println("<h1 id=t1>LOFI</h1>");
		    out.println("<h2 id=t2>Local Deals Finder</h2>");
		    out.println("</nav>");
		    out.println("<body>");
		    out.println("<div id=wrapper>");
		    out.println("<table>"); 
		    out.println("<tr>");
		    out.println("<td>");
		    out.println("<img src="+doppiApici + imgOfferta + doppiApici+" alt="+doppiApici+nomeNegozio+doppiApici+"id=imgOfferta>");
		    out.println("</td>");
		    out.println("<td>");
		    out.println("<p id=prezzoOfferta>"+prezzoOfferta+"</p>");
		    out.println("</td>");
		    out.println("<td>");
		    out.println("<img src=\"http://img.stockfresh.com/files/n/nickylarson974/x/87/4846156_18842284.jpg\""
					+" alt=\"logo distanza\" id=impronta>");
		    out.println("</td>");
		    out.println("<td>");
		    out.println("<p id=distanzaOfferta>"+distanzaOfferta+"</p>");
		    out.println("</td>");
		    out.println("</tr>");
		    out.println("<tr>");
		    out.println("<td>");
		    out.println("<p id=address>"+indirizzoNegozio+"<br>"+cittaNegozio+"</p>");
		    out.println("</td>");
		    out.println("<td>");
		    out.println("<img src=\"http://bestwindows8apps.s3.amazonaws.com/icons/Icon.478518.png\""
					+" alt=\"MapPosition logo\" id=mapPosition>");
		    out.println("</td>");
		    out.println("</tr>");
		    out.println("<tr>");
		    out.println("<td>");
		    out.println("<img src=\"http://aifos.org/inst/aifos/public/data/general/images/associazione/contatti/orologio%202.jpg\""
					+" alt=\"clock logo\" id=clock>");
		    out.println("</td>");
		    out.println("<td>");
		    out.println("<p id=orarioApertura>"+orarioApertura+"</p>");
		    out.println("</td>");
		    out.println("</tr>");
		    out.println("<tr>");
		    out.println("<td>");
		    out.println("<p id=descProdottoOfferta>"+descProdotto+"</p>");
		    out.println("</td>");
		    out.println("<td>");
		    out.println("<img src=\"http://www.buyhydrogrow.com/images/ok.png\"" 
					+" alt=\"OkHand logo\" id=logoOk>");
		    out.println("</td>");
		    out.println("</tr>");
		    out.println("</table>");
		    out.println("</div>");
		    out.println("</body>");
		    out.println("<footer id=filtriApp>");
		    out.println("<FORM METHOD=POST ACTION=overview.html>");
		    out.println("<button id=homeButton type=submit>");
		    out.println("<img src=\"http://23cose.wikispaces.com/file/view/Wiki%20Home.png/395752830/Wiki%20Home.png\" "
			+" alt=\"home logo\" id=homeLogo>");
		    out.println("</button>");
		    out.println("</FORM>");
		    out.println("<FORM METHOD=POST ACTION=reserve.html>");
		    out.println("<input type=submit value=\"Make The Deal\" id=bottoneOfferta>");
		    out.println("<input type=hidden value="+req.getParameter("partitaIva")
		    +" name=partitaIva>");
		    out.println("<input type=hidden value="+req.getParameter("codProdotto")
		    +" name=codProdotto>");
		    out.println("<input type=hidden value="+doppiApici+ prezzoOfferta + doppiApici
		    +" name=prezzoOfferta>");
		    out.println("<input type=hidden value="+doppiApici+ nomeNegozio + doppiApici
		    +" name=nomeNegozio>");
		    out.println("</FORM>");
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
	        } catch (SQLException sqle) {}
	    }
}
