## Chapter 1
### Why do we do this?

The best way to understand the need for Rx is through excruciating pain.
Therefore we have prepared some tasks which you have to implement. We start with easier tasks and continue to more
complicated tasks.

## 1. Read from a file
Write a JVM application, which reads a JSON string from a file, converts the JSON content to an object and prints it.
Use tuner.json

## 2. Read from multiple files
Write a JVM application, which reads JSON strings from a files, converts the JSON content to objects, performs some
operations and prints the result. Use files tuner.json, artists.json, albums.json to print a string, which contains
the information about what currently is playing including the artist and the album.

## 3. Read from a websocket
This is similar to task 1. except that websockets are push-based. You will get messages every time new data is available.
Read JSON string from a socket, convert the JSON content to an object and print it. Use this server:
[https://github.com/yuriykulikov/rxplayer](https://github.com/yuriykulikov/rxplayer)

## 4. Read from socket multiple times
This is similar to task 2. except that that websockets are push-based. Use the same server to get the information
about artist and album of the currently played track.

## 5. Not-so-happy-path
Now the happy that is working, but what about things which we do not like, but they happen? Try to implement error handling,
reduce the load in the application in case server sends too many updates, do not react on duplicate updates (avoid
querying the server if the track has changed, but the album did not change). Think about threading issues in your app.

## 6. Reactive solution
We will check an application implementation, which uses RxJava to solve the issues underlined in tasks 4 and 5.

## 7. Event-based systems
Short overview of event-based systems.