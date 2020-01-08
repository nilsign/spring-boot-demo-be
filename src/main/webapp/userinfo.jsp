<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Spring Security - OAuth 2.0 User Info</title>
        <meta charset="utf-8" />
    </head>
    <body>
        <h1>OAuth 2.0 User Info</h1>
        <div>
            <span style="font-weight:bold">User Attributes:</span>
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
        </div>
    </body>
</html>
