To build the game:
mvn clean package

To run the game:
java -jar target/connect-0.0.1-SNAPSHOT.jar server

To run acceptance tests:
Start the game and run the test AcceptanceTest, I run it from the IDE.

To solve the problem I used the documentation on dropwizard.io and https://github.com/rest-assured/rest-assured.

The board size and the number of discs to connect is only in the resource class. Replace this class to change the board
size and the number to connect, and serve it under a new path.

API:
To create a new game do a GET to /connect4. The response will have a Location header with the url for the new game.
The url is is on the form /connect4/{game-id}.

To make a move do a POST to the returned Location. The response will contain the game state.

To check the game state do a GET to the returned location.
