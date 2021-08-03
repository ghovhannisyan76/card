#Card Application

This application implements a REST API to return a feed of "cards" - a simple JSON representation of the payload.

Cards can be of different types and include different values. The below logic is implemented:
>     Daily quote card - will show a different quote every day. The list of quotes will be stored in the database. The card will include:
>         Title - "Daily Quote"
>         Message - the quote content.
>         Author - the author of the daily quote.
>     Status update card - will be used to show updates to customers. This card will include:
>         Title - String - "Important Security Update"
>         Icon - String - e.g. https://tinyurl.com/y5mdph2g
>         Message - String - e.g. "For your security, we support 2 factor authentication, and would recommend that you turn it on."
>         Button - String - e.g. "Got it, thanks"

Each card type can have multiple implementations.
Each implementation will allow for a pre-defined condition to determine whether the card should be presented to a given customer

## Endpoint

GET {host}/card?username=&cardtype=&cardsubtype=

* username and cardtype are mandatory fields. The acceptable values for <b>cardtype</b> are <i>DAILY</i> and <i>STATUS</i>.
* <b>cardsubtype</b> makes sense if cardtype=STATUS. The acceptable values are <i>FULL</i>, <i>ICON</i> and <i>BUTTON</i> 

The application uses in-memory DB. As only one endpoint is implemented the db should be initialized manually.

The DB can be access in the browser. The URL is http://localhost:8080/h2-console/login.jsp

Use the following settings to login:
* Saved Settings - Generic H2 (Embedded)
* Driver Class- org.h2.Driver
* JDBC URL - jdbc:h2:mem:db
* User Name - super
* Password - secret

