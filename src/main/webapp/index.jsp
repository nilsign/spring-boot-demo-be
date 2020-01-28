<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Spring Security - OAuth 2.0 Login</title>
        <meta charset="utf-8" />
        <link rel="apple-touch-icon" sizes="180x180" href="/apple-touch-icon.png">
        <link rel="icon" type="image/png" sizes="32x32" href="/favicon-32x32.png">
        <link rel="icon" type="image/png" sizes="16x16" href="/favicon-16x16.png">
        <link rel="manifest" href="/site.webmanifest">
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
            <a href="logged-in-user-info">Display User Info</a>
        </div>
        <div>&nbsp;</div>
        <div>
            <a href="auth/logout">Logout</a>
        </div>
    </body>
</html>
