<%@page import="java.net.InetAddress"%>
sirh.ws.version=${version}
sirh.ws.hostaddress=<%=InetAddress.getLocalHost().getHostAddress() %>
sirh.ws.canonicalhostname=<%=InetAddress.getLocalHost().getCanonicalHostName() %>
sirh.ws.hostname=<%=InetAddress.getLocalHost().getHostName() %>
sirh.ws.tomcat.version=<%= application.getServerInfo() %>
sirh.ws.tomcat.catalina_base=<%= System.getProperty("catalina.base") %>


<% 
HttpSession theSession = request.getSession( false );

// print out the session id
if( theSession != null ) {
  //pw.println( "<BR>Session Id: " + theSession.getId() );
  synchronized( theSession ) {
    // invalidating a session destroys it
    theSession.invalidate();
    //pw.println( "<BR>Session destroyed" );
  }
}
%>
	
