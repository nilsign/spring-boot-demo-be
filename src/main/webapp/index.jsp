<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Spring Security - OAuth 2.0 Login</title>
        <meta charset="utf-8" />
    </head>
    <body>
        <h1>OAuth 2.0 Login with Spring Security</h1>
        <div>
            You are successfully logged in
            <span style="font-weight:bold">
                ${userName}
            </span>
            via the OAuth 2.0 Client
            <span style="font-weight:bold">
                ${clientName}
            </span>
        </div>
        <div>&nbsp;</div>
        <div>
            <a href="/userinfo">Display User Info</a>
        </div>
        <div>&nbsp;</div>
        <div>
            <a href="/home/logout">Logout</a>
        </div>
    </body>
</html>
