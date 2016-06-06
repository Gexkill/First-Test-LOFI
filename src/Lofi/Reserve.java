package Lofi;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Reserve extends HttpServlet  {


	  /** 
	   * servlet container of reserve.html page
	   */
	private static final long serialVersionUID = 7110556062344753891L;
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
		
		res.setContentType("text/html");
	    PrintWriter out = res.getWriter();
	    
	    char doppiApici = '"';
	    
		out.println("<!DOCTYPE html>");
	    out.println("<html>");
	    out.println("<head>");
	    out.println("<meta charset=UTF-8>");
	    out.println("<link rel=\"stylesheet\" href=\"reserveStile.css\" />");
	    out.println("<title>Local Deals Finder</title>");
	    out.println("<script type=text/javascript src=funzioni.js></script>");
	    out.println("</head>");
	    out.println("<nav id=menuApp>");
	    out.println("<h1 id=t1>LOFI</h1>");
	    out.println("<h2 id=t2>Local Deals Finder</h2>");
	    out.println("</nav>");
	    out.println("<body onload=pickup()>");
	    out.println("<div id=\"wrapper\">");
	    out.println("<p id=\"name\">Name</p>");
	    out.println("<input id=\"valoreNome\" type=\"text\">");
	    out.println("<p id=\"address\">Address</p>");
	    out.println("<input id=\"valoreIndirizzo\" type=\"text\">");
	    out.println("<p id=\"paymentMethod\">Payment method</p>");
	    out.println("<button id=\"paypal\" type=\"button\" onclick=SelezionaMetodoPagamento(\"paypal\")>");
	    out.println("<img src=\"https://static.webshopapp.com/shops/098694/files/045308334/paypal-logo.png\""
			+"alt=\"paypal logo\" id=\"paypalLogo\">");
	    out.println("</button>");
	    out.println("<button id=\"visa\" type=\"button\" onclick=SelezionaMetodoPagamento(\"visa\")> ");
	    out.println("<img src=\"https://qph.is.quoracdn.net/main-thumb-t-3075-50-ohPNxD2uu8xYpb1Wm4MOQ53VFLQ3Ir82.jpeg\"" 
			+"alt=\"visa logo\" id=\"visaLogo\">");
	    out.println("</button>");
	    out.println("<button id=\"mastercard\" type=\"button\" onclick=SelezionaMetodoPagamento(\"mastercard\")>");
	    out.println("<img src=\"http://www.bacchusthairestaurant.com.au/images/icon-mastercard.png\""
			+"alt=\"mastercard logo\" id=\"mastercardLogo\">");
	    out.println("</button>");
	    out.println("<button id=\"postepay\" type=\"button\" onclick=SelezionaMetodoPagamento(\"postepay\")>");
	    out.println("<img src=\"http://www.microtechmobile.it/SMS/images/icons/payment/postepay.png\""
				+"alt=\"postepay logo\" id=\"postepayLogo\">");
	    out.println("</button>");
	    out.println("<button id=\"contanti\" type=\"button\" onclick=SelezionaMetodoPagamento(\"contanti\")>");
	    out.println("<img src=\"http://www.ferraragroup.it/file/2014/08/icon-money.png\"" 
			+"alt=\"contanti logo\" id=\"contantiLogo\">");
	    out.println("</button>");
	    out.println("<p id=\"pickupIn\">Pickup in</p>");
	    out.println("<input id=\"stimaTempo\" type=\"text\" readonly >");
	    out.println("</div>");
	    out.println("</body>");
	    out.println("<footer id=\"filtriApp\">");
	    out.println("<FORM METHOD=POST ACTION=\"overview.html\">");
	    out.println("<button id=\"homeButton\" type=\"submit\">");
	    out.println("<img src=\"http://23cose.wikispaces.com/file/view/Wiki%20Home.png/395752830/Wiki%20Home.png\"" 
			+"alt=\"home logo\" id=\"homeLogo\">");
	    out.println("</button>");
	    out.println("</FORM>");
	    out.println("<FORM METHOD=POST ACTION=\"inviaDatiCompany.html\" id=\"inviaDati\">");
	    out.println("<input type=submit value=\"Send data to shop\" id=\"bottoneOfferta\" onclick=checkInputValues()>");
	    out.println("<input type=hidden value="+req.getParameter("partitaIva")
	    +" name=partitaIva>");
	    out.println("<input type=hidden value="+req.getParameter("codProdotto")
	    +" name=codProdotto>");
	    out.println("<input type=hidden value="+doppiApici+ req.getParameter("prezzoOfferta") + doppiApici
	    +" name=prezzoOfferta>");
	    out.println("<input type=hidden value="+doppiApici+ req.getParameter("nomeNegozio") + doppiApici
	    +" name=nomeNegozio>");
	    out.println("</FORM>");
	    out.println("</footer>");
	    out.println("</html>");
	}
}
