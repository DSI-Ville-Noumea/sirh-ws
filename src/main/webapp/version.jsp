<%@page import="java.net.InetAddress"%>
sirh.version=<%= this.getServletContext().getInitParameter("VERSION") %>
sirh.localhost.hostaddress=<%=InetAddress.getLocalHost().getHostAddress() %>
sirh.localhost.canonicalhostname=<%=InetAddress.getLocalHost().getCanonicalHostName() %>
sirh.localhost.hostname=<%=InetAddress.getLocalHost().getHostName() %>
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
	
