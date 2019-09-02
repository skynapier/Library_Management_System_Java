# Library Management System
Implement database transaction processing by JDBC

## Background
To do the project you will use
- PostgreSQL Database Management System to build a database,
- Java programming language to write a transaction programs, and
- Java Database Connectivity (JDBC) package to exchange data between your database and the transaction program.

## The Structure of the Database Transaction Application
The database transaction application has the following components:
- A library database (in the file Project2_15.data)
- A Java program named LibraryUI.java that contains the code for the GUI and the main method.
- A Java program named LibraryModel.java that should implement methods that provide functionality to the options offered by the GUI in LibraryUI.java.

A dump of the schema and data for the database, and the two java files are in the Assignment web page. You will need to complete the LibraryModel.java program, but you should not need to modify LibraryUI.java at all.

## How to running it
$ javac LibraryUI.java

and run using

$ java LibraryUI

## What should you do
1. Load your database from the file Project2_15.data.
2. Complete the implementation of the LibraryModel class:

a) Define the constructor to open a connection to the database

b) Define each of the methods to perform an appropriate SQL query (or queries) and return the result of the query as a string to be displayed.

When implementing methods of the LibraryModel class, pay attention to commands for handling transactions correctly (for starting and ending a transaction, and locking), and to JDBC commands (for acquiring a driver object, establishing a connection to the database, controlling the transaction environment, and executing SQL statements using Statement and PreparedStatement objects). For the sake of efficiency, allow the read only programs to read all database items, regardless whether they are locked or not. Most of your methods will need to handle exceptions raised by the JDBC commands. The most important goal of your transaction program is to leave the database in a consistent state after executing each of the requested database operations.

An important part of the database application you are going to implement is the method for the “BorrowBook” transaction. After receiving ISBN, CustomerId, and DueDate values from the GUI that method needs to perform the following actions (assuming that none of the actions fails):
1. Check whether the customer exists (and lock him/her as if the delete option were available).
2. Lock the book (if it exists, and if a copy is available).
3. Insert an appropriate tuple in the Cust_Book table.
4. Update the Book table, and
5. Commit the transaction (if actions were all successful, otherwise rollback)

This transaction is intended to work in a multi user transaction-processing environment where there are multiple copies of your program running at the same time. To allow you (and the marker) to test whether your program works correctly, insert an interaction command between steps 3 and 4 above. This interaction command should put up a dialog box and ask the user to click YES/OK to continue. The effect of this is to stall the processing of the program while you run another copy of the program and invoke a transaction that should interfere with the transaction that is part way through. This way, you can simulate contention for the same database items, and check whether your program:
- Avoids lost update, dirty and unrepeatable read for database update transactions and
- Allows reading all database items for read only transactions.

Apply the steps, similar to steps 1 to 5 above also when implementing the Return Book function. Of course, this time you need to delete an appropriate tuple from the Cust_Book table.

## What my program can do
### Compulsory part:
- Implement the database.
- Implement the Book Lookup Function (show book authors sorted according to AuthSeqNo).
- Implement the Show Catalogue function.
- Implement the Show Loaned Books function.
- Implement the Borrow Book function.
- Implement the Exit function.
- Implement the Show Author function.
- Implement the Show All Authors function.
- Implement the Show Customer function.
- Implement the Show All Customers function.
- Implement the Return Book function.

### Optional part:
- Implement Delete customer function.
- Implement Delete author function.
- Implement Delete book function. 
