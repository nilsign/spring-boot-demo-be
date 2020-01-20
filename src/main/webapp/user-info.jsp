<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Spring Security - OAuth 2.0 User Info</title>
        <meta charset="utf-8" />
        <link rel="apple-touch-icon" sizes="180x180" href="/apple-touch-icon.png">
        <link rel="icon" type="image/png" sizes="32x32" href="/favicon-32x32.png">
        <link rel="icon" type="image/png" sizes="16x16" href="/favicon-16x16.png">
        <link rel="manifest" href="/site.webmanifest">
    </head>
    <body>
        <h1>OAuth 2.0 User Info</h1>
        <div>
            <h3>
                <span style="font-weight:bold">User Attributes</span>
            </h3>
            <ul>
                <c:forEach items="${userAttributes}" var="attribute">
                    <li>
                        <span style="font-weight:bold">
                            ${attribute.key}
                        </span>
                        <span>
                            :&nbsp;
                        </span>
                        <span>
                            ${attribute.value}
                        </span>
                    </li>
                </c:forEach>
            </ul>
            <h3>
                <span style="font-weight:bold">User Authorities</span>
            </h3>
            <ul>
                <c:forEach items="${userAuthorities}" var="authority">
                    <li>
                        <span>
                            ${authority}
                        </span>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </body>
</html>
