<%@page import="java.net.InetAddress"%>
sirh.ws.version=${version}<br/>
sirh.ws.hostaddress=<%=InetAddress.getLocalHost().getHostAddress() %><br/>
sirh.ws.canonicalhostname=<%=InetAddress.getLocalHost().getCanonicalHostName() %><br/>
sirh.ws.hostname=<%=InetAddress.getLocalHost().getHostName() %><br/>
sirh.ws.tomcat.version=<%= application.getServerInfo() %><br/>
sirh.ws.tomcat.catalina_base=<%= System.getProperty("catalina.base") %><br/>


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
	
