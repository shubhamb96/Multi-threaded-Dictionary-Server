# Multi-threaded Dictionary Server

How to run instruction:

- To start the server:

> java –jar DictionaryServer.jar <port> <dictionary-file>

Where, <port> is the port to which the server will be listening to for incoming connections and the <dictionary-file> is the path of the dictionary file.

Please note, the whole path of the dictionary needs be specified and both the <port> and <dictionary-file> need to be valid and in the right order.

- To start the client:

> java –jar DictionaryClient.jar <server-address> <server-port>

Where, <server-address> is the address of the server to which the client needs to connect to and the <server-port> is the port number.

Please note, both <server-address> and <server-port> need to be valid and in the right order. 

