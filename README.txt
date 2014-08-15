This code sample (ServerOperation.java) was based off of a JAC444 assignment.  The assignment was to create a program where a client could send commands to a server for processing. My completed assignment had a great deal of nested loops, had a great deal of repeated code and was almost impossible to work with.

My solution to this problem was to break the server down into multiple independent operations.  These operations could be nested within each other to create a full server program.  An operation is created by deriving ServerOperation.  When called, an operation will take control of client interaction until it terminates it's self.

Included for completeness is a working client-server program using ServerOperation in the folder WorkingExample.