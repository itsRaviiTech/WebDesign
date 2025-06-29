<%-- 
    Document   : updateFlagStatus
    Created on : 29 Jun 2025, 11:12:18 pm
    Author     : User
--%>

<%@ page import="java.util.*, javax.servlet.http.*, javax.servlet.*" %>
<%
    // Get the questionId and flag status from the request
    String questionIdStr = request.getParameter("questionId");
    String flagStatus = request.getParameter("flag");

    // Convert questionId to an integer
    int questionId = Integer.parseInt(questionIdStr);

    // Get the current session or create one if it doesn't exist
    HttpSession session = request.getSession();
    
    // Retrieve the list of flagged questions from the session
    List<Integer> flaggedQuestions = (List<Integer>) session.getAttribute("flaggedQuestions");

    if (flaggedQuestions == null) {
        flaggedQuestions = new ArrayList<>();
    }

    // Update the list of flagged questions in the session
    if ("1".equals(flagStatus)) {
        // If flag is set to 1, add the question ID to the flagged questions list
        if (!flaggedQuestions.contains(questionId)) {
            flaggedQuestions.add(questionId);
        }
    } else {
        // If flag is set to 0, remove the question ID from the flagged questions list
        flaggedQuestions.remove(Integer.valueOf(questionId));
    }

    // Save the updated list back in the session
    session.setAttribute("flaggedQuestions", flaggedQuestions);
    
    // Optionally, send a response back to JavaScript (e.g., success message)
    out.println("Flag status updated.");
%>

