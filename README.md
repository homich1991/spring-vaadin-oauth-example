# Spring + Vaadin + OAUTH2
This is example of using OAUTH2 authentication in Spring Boot app, definitely  when you want to use Vaadin UI.
This example contains 2 modules:
- Auth-impl - this module contains authentications server configured with Spring boot AutoConfig. Authenticated provided with DB connection.
- Auth-client - this module contains client application configured with @EnableOAuth2Sso annotation.

Both application contains Vaadin forms.
- Server - login form
- Client - simple form just to be sure that everything works correct

Start up
----
For start up and check app:
- *mvn clean install* in root folder
- start app with *java -jar <server-jar-name>.jar*
- start app with *java -jar <client-jar-name>.jar*

Then you may try to open in your browser localhost:9999/client and everything should works :)

Authorization Server important info
----
- Authorization server makes authentication with users stored in db:

User | Login | Password | Authorities
--- | --- | --- | ---
**Admin** | admin |111| ROLE_ADMIN
**User**  | user |111| ROLE_USER

- Authorization server by default starts on port *8080*

Client app important info
----
- Client app by default starts on port *9999* with context-path **/client**
