# Multi-threaded Dictionary Server


The project was about building a client-server architecture to implement a Multithreaded
Dictionary Server that allowed concurrent clients to add words with
meaning(s), search for the meaning(s) of a word and delete existing words from the
dictionary. The architecture makes use of TCP Sockets and Threads at the lowest level
of abstraction for the purpose of network communication and concurrency.
How to run instruction:

- To start the server:

> java –jar DictionaryServer.jar <port> <dictionary-file>

Where, <port> is the port to which the server will be listening to for incoming connections and the <dictionary-file> is the path of the dictionary file.

Please note, the whole path of the dictionary needs be specified and both the <port> and <dictionary-file> need to be valid and in the right order.

- To start the client:

> java –jar DictionaryClient.jar <server-address> <server-port>

Where, <server-address> is the address of the server to which the client needs to connect to and the <server-port> is the port number.

Please note, both <server-address> and <server-port> need to be valid and in the right order. 

