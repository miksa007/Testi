/******************************************************************
 * 
 * 25.11.2011
 * 
 * @author miksa
 * 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.Scanner;
import java.sql.PreparedStatement;
import java.util.concurrent.CountDownLatch;

public class Tietokanta {
	private Connection con;

	/**
	 * Luodaan yhteys tietokantaan
	 * 
	 * @param schema
	 * @param user
	 * @param pass
	 * @throws Exception
	 */
	public void createConnection(String schema, String user, String pass)
			throws Exception {
		try {
			//this use mysql
			//Class.forName("com.mysql.jdbc.Driver").newInstance();
			//this is for MariaDB
			Class.forName("org.mariadb.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.out.println("The mariadb-java-client-*.*.*.jar doesn't exist");
			System.out.println("Download it from https://downloads.mariadb.org/connector-java/");
			System.out.println("and include it to build path");
			
			e.printStackTrace();
			
			throw new Exception(
					"createConnection: JDBC-ajurin rekisterainti epaonnistui.");
			
		}
		try {
			// String url = "jdbc:mysql://localhost/olioJavatesti";
			//String url = "jdbc:mysql://localhost/" + schema;
			
			String url="jdbc:mysql://";//+palvelimen_ip_osoite;
			con = DriverManager.getConnection(url, user, pass);
			
			//con = DriverManager.getConnection(url, miksa,)
			
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("createConnection: Login error");

		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	void luoBookTaulu() throws Exception {
		String sql;
		Statement stmt = null;

		try {
			stmt = con.createStatement();

			sql = "CREATE TABLE BOOKS ("
					+ " BNO   INTEGER   NOT NULL AUTO_INCREMENT PRIMARY KEY,"
					+ " NAME VARCHAR(40) NOT NULL," + " ORIGNAME VARCHAR(40) ,"
					+ " LANGUAGE VARCHAR(40) ," + " ISBN1 VARCHAR(40) ,"
					+ " FIELD1 VARCHAR(40)," + " FIELD2 VARCHAR(40),"
					+ " YEAR INTEGER," + " PURCHASEPRICE FLOAT NOT NULL,"
					+ " SELLPRICE FLOAT NOT NULL," + " WNO INTEGER NOT NULL)";
			System.out.println(sql);
			stmt.executeUpdate(sql);
		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception("Taulujen luonti epaonnistui.");
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	void poistaBookTaulu() throws Exception {
		String sql;
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			sql = "DROP TABLE BOOKS";
			stmt.executeUpdate(sql);
		} catch (SQLException se) {
			se.printStackTrace();
			// Tulostetaan virhe, kenties tauluja ei ole viela edes olemassa!
			System.err.println("Taulujen poistaminen epaonnistui.");
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	void luoWriterTaulu() throws Exception {
		String sql;
		Statement stmt = null;

		try {
			stmt = con.createStatement();

			sql = "CREATE TABLE WRITER ("
					+ " WNO   INTEGER   NOT NULL AUTO_INCREMENT PRIMARY KEY,"
					+ " FNAME VARCHAR(40) NOT NULL,"
					+ " LNAME VARCHAR(40) NOT NULL," + " FIELD1 VARCHAR(40) ,"
					+ " FIELD2 VARCHAR(40) ," + " IFIELD INTEGER )";
			System.out.println(sql);
			stmt.executeUpdate(sql);
		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception("Taulujen luonti epaonnistui.");
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	void poistaWriterTaulu() throws Exception {
		String sql;
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			sql = "DROP TABLE WRITER";
			stmt.executeUpdate(sql);
		} catch (SQLException se) {
			se.printStackTrace();
			// Tulostetaan virhe, kenties tauluja ei ole viela edes olemassa!
			System.err.println("Taulujen poistaminen epaonnistui.");
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	public void addBook(String nimi, String isbn1, int year, float price,
			int wno) throws Exception {

		PreparedStatement addbook = null;
		try {
			addbook = con
					.prepareStatement("INSERT INTO BOOKS (NAME, ISBN1, FIELD1, FIELD2, YEAR, PURCHASEPRICE, SELLPRICE, WNO) VALUES (?,?,?,?,?,?,?,?)");

			addbook.setString(1, nimi);
			addbook.setString(2, isbn1);
			addbook.setString(3, " ");
			addbook.setString(4, " ");
			addbook.setInt(5, year);
			addbook.setFloat(6, price);
			addbook.setFloat(7, price);
			addbook.setInt(8, wno);

			addbook.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception(
					"addBook:Tiedon lisaaminen Book Tauluun epaonnistui .");
		} finally {
			if (addbook != null)
				addbook.close();
		}
	}

	public void addWriter(String enimi, String snimi) throws Exception {

		PreparedStatement addwriter = null;
		try {
			addwriter = con
					.prepareStatement("INSERT INTO WRITER (FNAME, LNAME, FIELD1, FIELD2, IFIELD) VALUES (?,?,?,?,?)");
			addwriter.setString(1, enimi);
			addwriter.setString(2, snimi);
			addwriter.setString(3, " ");
			addwriter.setString(4, " ");
			addwriter.setInt(5, 0);

			addwriter.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception(
					"addWriter:Tiedon lisaaminen writer Tauluun epaonnistui .");
		} finally {
			if (addwriter != null)
				addwriter.close();
		}
	}
/**
 * 
 * date18.10.2012
 * 
 * @param wno
 * @return
 * @throws Exception
 */
	public String findWriter(int wno) throws Exception {
		String arvo = null;
		String sql;
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			// SQL Lause parametreilla varustettuna
			sql = "SELECT * FROM WRITER WHERE WNO='" + wno + "'";
			rs = stmt.executeQuery(sql);
			// silmukka palautta vai yhden tai ei mitaan
			while (rs.next()) {
				int id = rs.getInt("WNO");
				String enimi = rs.getString("FNAME");
				String snimi = rs.getString("LNAME");
				arvo = (id + "\n" + enimi + "\n" + snimi);
			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception(
					"Error findWriter: Data search error");
		} finally {
			if (stmt != null)
				stmt.close();
		}
		System.out.println("Debug findWriter: " + arvo);
		return arvo;
	}
	/**
	 * 
	 * date 18.10.2012
	 * 
	 * @param bno
	 * @return
	 * @throws Exception
	 */
	public String findBook(int bno) throws Exception {
		String arvo = null;
		String sql;
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			// SQL Lause parametreilla varustettuna
			sql = "SELECT * FROM BOOKS WHERE BNO='" + bno + "'";
			rs = stmt.executeQuery(sql);
			// silmukka palautta vai yhden tai ei mitaan
			while (rs.next()) {
				int id = rs.getInt("BNO");
				String nimi = rs.getString("NAME");
				String onimi = rs.getString("ORIGNAME");
				int wno = rs.getInt("WNO");
				arvo = (id + "\n" + nimi + "\n" + onimi+"\n"+wno);
			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception(
					"Error findBook: Data search error");
		} finally {
			if (stmt != null)
				stmt.close();
		}
		System.out.println("Debug findBook: " + arvo);
		return arvo;
	}
	/**
	 * 
	 * date 18.10.2012
	 * 
	 * @param wno
	 * @return
	 * @throws Exception
	 */
	public void updateWriter(int wno, String fName, String lName) throws Exception {
		String sql;
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			sql = "UPDATE WRITER SET FNAME='"+fName+"',LNAME='"+lName+"' WHERE WNO='" + wno + "'";
			stmt.executeUpdate(sql);
			
		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception(
					"Error updateWriter: Data  error");
		} finally {
			if (stmt != null)
				stmt.close();
		}
		System.out.println("Debug updateWriter: ");
		
	}
	/**
	 * 
	 * date 18.10.2012
	 * 
	 * @param wno
	 * @param fName
	 * @param lName
	 * @param bno
	 * @throws Exception
	 */
	public void updateBook(int wno, String name, String oName,int bno) throws Exception {
		String sql;
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			sql = "UPDATE BOOKS SET NAME='"+name+"',ORIGNAME='"+oName+"',WNO='"+wno+"' WHERE BNO='" + bno + "'";
			stmt.executeUpdate(sql);
			
		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception(
					"Error updateBook: Data  error");
		} finally {
			if (stmt != null)
				stmt.close();
		}
		System.out.println("Debug updateBook: ");
		
	}
	/**
	 * 
	 * date 18.10.2012
	 * 
	 * @param wno
	 * @throws Exception
	 */
	public void deleteWriter(int wno) throws Exception {
		String sql;
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			sql = "DELETE FROM WRITER WHERE WNO='" + wno + "'";
			stmt.executeUpdate(sql);
			
		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception(
					"Error deleteWriter: Data  error");
		} finally {
			if (stmt != null)
				stmt.close();
		}
		System.out.println("Debug deleteWriter: ");
		
	}
	/**
	 * 
	 * date 18.10.2012
	 * 
	 * @param bno
	 * @throws Exception
	 */
	public void deleteBook(int bno) throws Exception {
		String sql;
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			sql = "DELETE FROM BOOKS WHERE BNO='" + bno + "'";
			stmt.executeUpdate(sql);
			
		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception(
					"Error deleteBook: Data  error");
		} finally {
			if (stmt != null)
				stmt.close();
		}
		System.out.println("Debug deleteBook: ");
		
	}
	
	/**
	 * LisaaTietoa metodia kaytetaan testaukseen. Tassa paaasiassa lisaillaan
	 * vain erilaisiin tauluihin satunnaista tietoa
	 * 
	 * @author miksa
	 * @throws Exception
	 */
	public void lisaaTietoa() throws Exception {
		String sql;
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			/*
			 * sql = "INSERT INTO ASIAKKAAT VALUES (3, 'Jaska', 'Jokunen')";
			 * stmt.executeUpdate(sql); sql = "INSERT INTO ASIAKKAAT VALUES (4,
			 * 'Matti', 'Virtanen')"; stmt.executeUpdate(sql); sql = "INSERT
			 * INTO TUOTTEET VALUES (4, 'Monitori', 150.0, 10)";
			 * stmt.executeUpdate(sql); sql = "INSERT INTO TUOTTEET VALUES (5,
			 * 'Nappis', 15.0, 560)"; stmt.executeUpdate(sql); sql = "INSERT
			 * INTO TILAUKSET VALUES (1003, 3, '2008-04-04')";
			 * stmt.executeUpdate(sql); sql = "INSERT INTO TILAUKSET VALUES
			 * (1004, 4, '2008-03-04')"; stmt.executeUpdate(sql); sql = "INSERT
			 * INTO TRIVI VALUES (1004, 2, 15,30)"; stmt.executeUpdate(sql);
			 */
		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception("Tiedon lisaaminen epaonnistui.");
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	public void suljeYhteys() {
		System.out.println("DEBUG: tietokantayhteys suljettiin");
		try {
			if (con != null)
				con.close();
		} catch (SQLException ex) {
		}
	}

	public void printBooks() throws Exception {
		// Kysely kantaan
		String sql;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			sql = "SELECT * FROM BOOKS";
			rs = stmt.executeQuery(sql);
			System.out.println("Tulostus alkoi");
			while (rs.next()) {
				int id = rs.getInt("BNO");
				String enimi = rs.getString("NAME");
				// String snimi = rs.getString("SNIMI");

				System.out.println(id + " " + enimi);

			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("printBooks: Virhe");

		}
	}

	public void printWriters() throws Exception {
		// Kysely kantaan
		String sql;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			sql = "SELECT * FROM WRITER";
			rs = stmt.executeQuery(sql);
			System.out.println("Tulostus alkoi");
			while (rs.next()) {
				int id = rs.getInt("WNO");
				String enimi = rs.getString("FNAME");
				String snimi = rs.getString("LNAME");

				System.out.println(id + " " + enimi + " " + snimi);

			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("printWriters: Virhe");

		}
	}

	public void printBooksOrderedByWriter() throws Exception {
		// Kysely kantaan
		String sql;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			sql = "SELECT Bookshelf.BOOKS.BNO, Bookshelf.BOOKS.NAME,"
					+ " Bookshelf.BOOKS.WNO, Bookshelf.WRITER.FNAME, "
					+ "Bookshelf.WRITER.LNAME FROM Bookshelf.BOOKS, "
					+ "Bookshelf.WRITER where Bookshelf.BOOKS.WNO = "
					+ "Bookshelf.WRITER.WNO ORDER BY Bookshelf.WRITER.LNAME; ";

			rs = stmt.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				int id = rs.getInt("BNO");
				String enimi = rs.getString("NAME");
				String snimi = rs.getString("FNAME");
				String nimi = rs.getString("LNAME");

				System.out.println(id + "\t" + enimi + "\t" + snimi + "\t"
						+ nimi);

			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("Virhe");

		}
	}

	public String[] getWriters() throws Exception {
		int count=0;
		//Haetaan rivien lukumaara
		String sql = "SELECT COUNT(*) AS rowcount FROM Bookshelf.WRITER";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("rowcount") ;
			System.out.println("DEBUG:"+count);
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("Virhe");
		}
		
		String data[]=new String[count];
		// Kysely kantaan

		stmt = null;
		rs = null;
		try {
			int i=0;
			stmt = con.createStatement();
			sql = "SELECT Bookshelf.WRITER.FNAME, "
					+ "Bookshelf.WRITER.LNAME," +
					"Bookshelf.WRITER.WNO FROM Bookshelf.WRITER ORDER BY Bookshelf.WRITER.LNAME ASC";
			
			rs = stmt.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				int wno=rs.getInt("WNO");
				String enimi = rs.getString("FNAME");
				String snimi = rs.getString("LNAME");

				data[i]= (wno+" "+enimi + "  " + snimi);
				System.out.println(i+". (WNO="+wno+") "+ enimi + " " + snimi);
				i++;
			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("Virhe");

		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}

		return data;
	}
	/**
	 * gets all books fron database and returns String array
	 * @return Books (String[])
	 * @throws Exception
	 */
	public String[] getBooks() throws Exception {
		int count=0;
		//Haetaan rivien lukumaara
		String sql = "SELECT COUNT(*) AS rowcount FROM Bookshelf.BOOKS";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("rowcount") ;
			System.out.println("DEBUG BOOKS rowcount:"+count);
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("Error getBooks(): getting BOOKS rowcount ");
		}
		
		String data[]=new String[count];
		// Kysely kantaan

		stmt = null;
		rs = null;
		try {
			int i=0;
			stmt = con.createStatement();
			sql = "SELECT Bookshelf.BOOKS.NAME, Bookshelf.BOOKS.BNO "
					+ " FROM Bookshelf.BOOKS ORDER BY Bookshelf.BOOKS.NAME ASC";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				int bno=rs.getInt("BNO");
				String nimi = rs.getString("NAME");
				
				//String snimi = rs.getString("LNAME");

				data[i]= (bno+" "+nimi );
				System.out.println(bno+". "+ nimi);
				i++;
			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("Error getBooks(): getting books");

		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}

		return data;
	}
	

	/**
	 * Voidaan muuttaa taulujen arvoja
	 * 
	 * http://docs.oracle.com/javase/tutorial/jdbc/basics/retrieving.html
	 * 
	 * @param percentage
	 * @throws SQLException
	 */
	public void modifyPrices(float percentage) throws SQLException {

		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet uprs = stmt.executeQuery("SELECT * FROM TUOTTEET");
			System.out.println(uprs);
			while (uprs.next()) {
				float f = uprs.getFloat("HINTA");
				uprs.updateFloat("HINTA", f * percentage);
				uprs.updateRow();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// JDBCTutorialUtilities.printSQLException(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public void tulostaTuotteet() throws Exception {
		// Kysely kantaan
		String sql;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			sql = "SELECT * FROM TUOTTEET";
			rs = stmt.executeQuery(sql);
			System.out.println("Tulostus alkoi");
			while (rs.next()) {
				int tid = rs.getInt("TID");
				String nimi = rs.getString("NIMI");
				float hinta = rs.getFloat("HINTA");
				int lkm = rs.getInt("LKM");

				System.out.println(tid + " " + nimi + " " + hinta + " " + lkm);

			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("Virhe");

		}
	}

	public void muutaTuotteenHinta(int tid, float uusiHinta) throws Exception {
		String sql;
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			sql = "UPDATE TUOTTEET SET HINTA = " + uusiHinta + " WHERE TID="
					+ tid + " ";
			System.out.println(sql);
			stmt.executeUpdate(sql);
		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception("Tiedon muokkaus epaonnistui.");
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}
}
