/*
 * LibraryModel.java
 * Author: Tian Bai
 * Student ID: 300343541
 */

import java.sql.*;
import javax.swing.*;

public class LibraryModel {

    private JFrame dialogParent;
		private Connection connection = null;

    public LibraryModel(JFrame parent, String userid, String password) {
    	dialogParent = parent;

    	try {
    		Class.forName("org.postgresql.Driver");
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	}

    	// address for connect to postgresql
    	String url = "jdbc:postgresql:" + "//db.ecs.vuw.ac.nz/" + userid + "_jdbc";

    	// connect to DB
    	try {
    		connection = DriverManager.getConnection(url, userid, password);
    		System.out.println("Connected to database: " + url);
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }


    boolean booklookup = false;
    public String bookLookup(int isbn) {
    	String result = "Book Lookup Result: \n";


    	String selectBookIsbn = ("SELECT * FROM book WHERE book_isbn = " + isbn + ";");
			String selectAba = ("SELECT a.name, a.surname, " +
							"ba.authorseqno FROM book_author ba, author a WHERE ba.isbn =" +
							isbn + "AND a.authorid = ba.authorid ORDER BY authorseqno;");

		// implement query
    	try {
			Statement st = connection.createStatement();
			Statement st2 = connection.createStatement();

			ResultSet rs = st.executeQuery(selectBookIsbn);
			ResultSet rs2 = st2.executeQuery(selectAba);

			if (rs.next()) {
				result += "\n Book ISBN: "       + rs.getInt(1) +
						  "\n Book title: "     + rs.getString(2) +
						  "\n Edition number: " + rs.getString(3) +
						  "\n Number of copy: " + rs.getString(4) +
						  "\n Number left: "    + rs.getString(5);
				result += "\n Authors: ";

				while (rs2.next()) {
					result += rs2.getInt("authorseqno") + ". " + rs2.getString("name") + rs2.getString("surname") + "\n";
				}

				booklookup = true;
			} else {
				booklookup = false;
				result += "Cannot find it! \n";
			}
    	} catch (SQLException e) {
    		JOptionPane.showConfirmDialog(
    				dialogParent, "Confirmation", e.getMessage(), JOptionPane.YES_NO_OPTION);
    	}

    	return result;
    }


    public String showCatalogue() {
    	String result = "Show Catalogue: \n";
    	boolean showcatalogue = false;


    	String selectBook = ("SELECT * FROM book;");


    	try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(selectBook);

			while (rs.next()) {
				result += "\n Book ISBN: " +       rs.getInt(1) +
						  "\n Book title: " +     rs.getString(2) +
						  "\n Edition number: " + rs.getString(3) +
						  "\n Number of copy: " + rs.getString(4) +
						  "\n Number left: " +    rs.getString(5) +
						  "\n ********************************** \n";
				showcatalogue = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return showcatalogue?result + "no catalogue in database":result;
    }


    public String showLoanedBooks() {
    	String result = "Show Loaned Books: \n";
    	boolean showloanedbooks = false;

    	String selectCustBookIsbn =  ("SELECT cb.isbn, cb.duedate, cb.customerid, c.l_name, c.f_name, b.title" +
				"FROM cust_book cb, book b, customer c WHERE cb.isbn = book.isbn AND cb.customerid = c.customerid;");


    	try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(selectCustBookIsbn);

			while (rs.next()) {
				result += "\n Book ISBN: " +    rs.getInt(1) +
						  "\n Duedate: " +     rs.getString(2) +
						  "\n Customer Id: " + rs.getString(3) +
						  "\n Last name: " +   rs.getString(4) +
						  "\n First name: " +  rs.getString(5) +
						  "\n Book title: " +  rs.getString(6) +
						  "\n ********************************** \n";
				showloanedbooks = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return showloanedbooks?result + "no Loaned Books":result;
    }


    public String showAuthor(int authorID) {
    	String result = "Show Author: \n";
    	boolean showauthor = false;

    	String selectAuthor = ("SELECT * FROM author WHERE authorid = " + authorID + ";");

    	try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(selectAuthor);

			if (rs.next()) {
				result += "\n Author ID: " +   rs.getInt(1) +
						  "\n First name: " + rs.getString(2) +
						  "\n Last name: " +  rs.getString(3) +
						  "\n ********************************** \n";
				showauthor = true;
			}
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(
					dialogParent, "Confirmation", e.getMessage(), JOptionPane.YES_NO_OPTION);
			e.printStackTrace();
		}

		return showauthor?result + "no author with given author id ":result;
    }

    public String showAllAuthors() {
    	String result = "Show All Authors: \n";
    	boolean showallauthors = false;

    	String selectAllAuthor = ("SELECT * FROM author;");

		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(selectAllAuthor);

			while (rs.next()) {
				result += "\n Author ID: " + rs.getInt(1) +
						  "\n First name: " +  rs.getString(2) +
						  "\n Last name: " +   rs.getString(3) +
						  "\n ********************************** \n";
				showallauthors = true;
			}
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(
					dialogParent, "Confirmation", e.getMessage(), JOptionPane.YES_NO_OPTION);
			e.printStackTrace();
		}

		return showallauthors?result + "no arthor with given author id":result;
    }


    boolean showcustomer = false;
    public String showCustomer(int customerID) {
    	String result = "Show Customer: \n";

    	String selectCustomer = ("SELECT * FROM customer WHERE customerid = " + customerID + ";");

    	try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(selectCustomer);

			if (rs.next()) {
				result += "\n Customer ID: " + rs.getInt(1) +
						  "\n First name: " +    rs.getString(3) +
						  "\n Last name: " +     rs.getString(2) +
						  "\n City: " +          rs.getString(4) +
						  "\n ********************************** \n";
				showcustomer = true;
			}
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(
					dialogParent, "Confirmation", e.getMessage(), JOptionPane.YES_NO_OPTION);
			e.printStackTrace();
		}

		return showcustomer?result + "no customer with given customer id":result;
    }

    public String showAllCustomers() {
    	String result = "Show All Customers: \n";
    	boolean showallcustomers = false;

    	String selectAllCustomer = ("SELECT * FROM customer;");

    	try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(selectAllCustomer);

			while (rs.next()) {
				result += "\n Customer ID: " + rs.getInt(1) +
						  "\n First name: "    + rs.getString(3) +
						  "\n Last name: "     + rs.getString(2) +
						  "\n City: "          + rs.getString(4) +
						  "\n ********************************** \n";
				showallcustomers = true;
			}
		}catch (SQLException e) {
			JOptionPane.showConfirmDialog(
					dialogParent, "Confirmation", e.getMessage(), JOptionPane.YES_NO_OPTION);
			e.printStackTrace();
		}
		return showallcustomers?result + "no customers in database":result;
    }


    public String borrowBook(int isbn, int customerID, int day, int month, int year) {
    	showCustomer(customerID);
		if (!showcustomer) {
			return "customer ID not in database";
		}

		bookLookup(isbn);
		if (!booklookup) {
			return "book isbn not in the database";
		}

		Statement st;
		int numLeft = -1;

		try {
			// check number of book left
			st = connection.createStatement();
			ResultSet rs1 = st.executeQuery("SELECT numleft FROM book WHERE isbn = " + isbn + ";");
			rs1.next();
			numLeft = rs1.getInt(1);
			if (numLeft < 1) {
				return "No book left with ISBN: "+ isbn + "!";
			}

			// update numleft to -1
			st = connection.createStatement();
			int rs2 = st.executeUpdate("UPDATE book SET numleft =" + (numLeft - 1) + ";");
			if (rs2 == 0) {
				return "Update # of book fail (numleft)!";
			}

			// update cust_book
			Statement st1 = connection.createStatement();
			int rs3 = st1.executeUpdate("INSERT INTO cust_book VALUE (" + isbn + ",date(\'"
					+ year + "-" + month + "-" + day + "\')," + customerID + ");");
			if (rs3 == 0) {
				return "Insert fail (cust_book)!";
			}
			return bookLookup(isbn);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "Borrowed Book Successfully!";
    }


    public String returnBook(int isbn, int customerid) {
    	showCustomer(customerid);
		if (!showcustomer) {
			return "customer ID not in database";
		}

		bookLookup(isbn);
		if (!booklookup) {
			return "book isbn not in the database";
		}

		try {
	    	Statement st;
			st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM cust_book WHERE isbn =" +
		    			 isbn + " AND customerid = " + customerid + "FOR UPDATE;");

			if (!rs.next()) {
				return "Ops...Seems like you did bot borrow this book!";
			}

	    	// delete the book from cust_book
	    	st = connection.createStatement();
	    	int result = st.executeUpdate("DELETE FROM cust_book WHERE isbn = " + isbn  + " AND customerid = " + customerid + ";");

	    	if (result == 0) {
	    		return "Delete fail (cust_book)!";
	    	}

	    	// update the numleft +1
	    	st = connection.createStatement();
			ResultSet rsforNum = st.executeQuery("SELECT numleft FROM book WHERE isbn = " + isbn + ";");
	    	rsforNum.next();
	    	int numLeft = rsforNum.getInt(1);

	    	st = connection.createStatement();
			int rst = st.executeUpdate("UPDATE book SET numleft = "+ (numLeft + 1) + " WHERE isbn = " + isbn + ";");

			if(rst == 0){
				return "Update fail (numleft)!";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "Return Book Successfully!";
    }

    public void closeDBConnection() {
    	try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public String deleteCus(int customerID) {
    	String result = "Delete Customer: \n";

    	try {
    		connection.setAutoCommit(false);

    		Statement st;
			st = connection.createStatement();

			ResultSet checkCustomer = st.executeQuery("SELECT * FROM customer WHERE customerid = " + customerID + " FOR UPDATE;");
			if (!checkCustomer.isBeforeFirst()) {
				connection.rollback();
				return result + "\n Cannot find customer with ID: " + customerID;
			}

			ResultSet checkBook = st.executeQuery("SELECT * FROM cust_book WHERE customerid = " + customerID + ";");
			if (checkBook.isBeforeFirst()){
				connection.rollback();
				return result + "\n Customer (" + customerID + ") cannot be deleted due to he/she did not return the book";
			}

			st.executeQuery("DELETE FROM customer WHERE customerid = " + customerID + ";");

			connection.commit();
			connection.setAutoCommit(true);
			st.close();

			return result + "\n Customer (ID: " + customerID + ") has been successfully deleted!";

    	} catch (SQLException e) {
    		e.printStackTrace();
    	}

    	try {
    		connection.rollback();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} finally {
    		try {
    			if (connection != null) {
    				connection.setAutoCommit(true);
				}
    		} catch (SQLException e) {
        		e.printStackTrace();
    		}
    	}
    	return result + "\n Customer (ID: " + customerID + ") cannot delete!";
    }

    public String deleteAuthor(int authorID) {
    	String result = "Delete Author: \n";

    	try {
    		connection.setAutoCommit(false);

	    	Statement st;
			st = connection.createStatement();

			ResultSet checkAuthor = st.executeQuery("SELECT * FROM author WHERE authorid = " + authorID + " FOR UPDATE;");
			if (!checkAuthor.isBeforeFirst()){
				connection.rollback();
				return result + "\n Cannot find author with ID: : " + authorID;
			}

			st.executeUpdate("DELETE FROM author WHERE authorid = " + authorID + ";");
			st.execute("UPDATE book_author SET authorid = DEFAULT, authorseqno = DEFAULT WHERE authorid = " + authorID + ";");

			connection.commit();
			connection.setAutoCommit(true);
			st.close();

			return result + "\n Author (ID: " + authorID + ") has been successfully deleted!";

    	} catch (SQLException e) {
    		e.printStackTrace();
    	}

    	try {
    		connection.rollback();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} finally {
    		try {
    			if (connection != null) {
    				connection.setAutoCommit(true);
				}
    		} catch (SQLException e) {
        		e.printStackTrace();
    		}
    	}

    	return result + "\n Author (ID: " + authorID + ") cannot delete!";
    }

		/**
		 * first, check the book exist or not
		 * then, check does the book has been borrowed or not
		 * if the book has been borrowed, then it cannot be delete
		 * finally, we can delete the book
		 * if cannot delete the book, then rollback
		 */
    public String deleteBook(int isbn) {
    	String result = "Delete Book: \n";

    	try {
    		connection.setAutoCommit(false);
    		Statement st;
    		st = connection.createStatement();

    		ResultSet checkBook = st.executeQuery("SELECT * FROM book WHERE isbn = " + isbn + " FOR UPDATE;");
    		if (!checkBook.isBeforeFirst()){
				connection.rollback();
				return result + "\n Cannot find the book with ISBN: " + isbn;
			}

    		ResultSet checkCustomerBook = st.executeQuery("SELECT * FROM cust_book WHERE isbn = " + isbn + ";");
    		if (checkCustomerBook.isBeforeFirst()){
				connection.rollback();
				return result + "\n Book (ISBN: " + isbn + ") cannot be deleted! Someone has borrowed it.";
			}

    		st.executeUpdate("DELETE FROM book WHERE isbn = " + isbn + ";");
    		st.execute("UPDATE book_author SET isbn = DEFAULT WHERE isbn = " + isbn + ";");

    		connection.commit();
    		connection.setAutoCommit(true);
    		st.close();

    		return result + "\n Book (ISBN: " + isbn + ") has been successfully deleted!";

    	} catch (SQLException e) {
    		e.printStackTrace();
    	}

    	try {
    		connection.rollback();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} finally {
    		try {
    			if (connection != null) {
    				connection.setAutoCommit(true);
				}
    		} catch (SQLException e) {
        		e.printStackTrace();
    		}
    	}

    	return result + "\n Book (ISBN: " + isbn + ") cannot delete!";
    }
}
