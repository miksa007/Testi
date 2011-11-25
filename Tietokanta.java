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

public class Tietokanta {
	private Connection con;

	public void createConnection(String schema, String user, String pass)
			throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(
					"createConnection: JDBC-ajurin rekisterainti epaonnistui.");
		}
		try {
			// String url = "jdbc:mysql://localhost/olioJavatesti";
			String url = "jdbc:mysql://localhost/" + schema;
			con = DriverManager.getConnection(url, user, pass);
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

			sql = "CREATE TABLE BOOK ("
					+ " BNO   INTEGER   NOT NULL AUTO_INCREMENT PRIMARY KEY,"
					+ " NIMI VARCHAR(40) NOT NULL,"
					+ " ISBN1 VARCHAR(40) NOT NULL," + " ISBN2 VARCHAR(40),"
					+ " ISBN3 VARCHAR(40)," + " YEAR INTEGER,"
					+ " PRICE FLOAT NOT NULL," + " WNO INTEGER NOT NULL)";
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
			sql = "DROP TABLE BOOK";
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
					+ " WNO   INTEGER   NOT NULL PRIMARY KEY,"
					+ " FNIMI VARCHAR(40) NOT NULL,"
					+ " LNIMI VARCHAR(40) NOT NULL,"
					+ " FIELD1 VARCHAR(40) NOT NULL,"
					+ " FIELD2 VARCHAR(40) NOT NULL,"
					+ " IFIELD INTEGER NOT NULL)";
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
		String sql;
		PreparedStatement addbook = null;
		try {
			addbook = con
					.prepareStatement("INSERT INTO BOOK (NIMI, ISBN1, ISBN2, ISBN3, YEAR, PRICE, WNO) VALUES (?,?,?,?,?,?,?)");

			addbook.setString(1, nimi);
			addbook.setString(2, isbn1);
			addbook.setString(3, " ");
			addbook.setString(4, " ");
			addbook.setInt(5, year);
			addbook.setFloat(6, price);
			addbook.setInt(7, wno);

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

	public String etsiAsiakasNumero(int ano) throws Exception {
		String arvo = null;
		String sql;
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			// SQL Lause parametreilla varustettuna
			sql = "SELECT * FROM ASIAKKAAT WHERE ANO='" + ano + "'";
			rs = stmt.executeQuery(sql);
			// silmukka palautta vai yhden tai ei mitaan
			while (rs.next()) {
				int id = rs.getInt("ANO");
				String enimi = rs.getString("ENIMI");
				String snimi = rs.getString("SNIMI");

				arvo = (id + " " + enimi + " " + snimi);

			}
			rs.close();

		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception(
					"Tiedon etsiminen Asiakas Taulusta epaonnistui .");
		} finally {
			if (stmt != null)
				stmt.close();
		}
		return arvo;
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
		try {
			if (con != null)
				con.close();
		} catch (SQLException ex) {
		}
	}

	public void tulostaAsiakkaat() throws Exception {
		// Kysely kantaan
		String sql;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			sql = "SELECT * FROM ASIAKKAAT";
			rs = stmt.executeQuery(sql);
			System.out.println("Tulostus alkoi");
			while (rs.next()) {
				int id = rs.getInt("ANO");
				String enimi = rs.getString("ENIMI");
				String snimi = rs.getString("SNIMI");

				System.out.println(id + " " + enimi + " " + snimi);

			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("Virhe");

		}
	}

	public void tulostaAsiakkaanTuotteet() throws Exception {
		// Kysely kantaan
		String sql;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			sql = "SELECT ENIMI, SNIMI, NIMI, TR.LKM FROM ASIAKKAAT A, TILAUKSET TI, TRIVI TR, TUOTTEET TU "
					+ "WHERE A.ANO=TI.ANO AND TI.TNO=TR.TNO AND TR.TID=TU.TID";
			rs = stmt.executeQuery(sql);
			System.out.println("Tulostus alkoi");
			while (rs.next()) {
				// int id = rs.getInt("ANO");
				String enimi = rs.getString("ENIMI");
				String snimi = rs.getString("SNIMI");
				String nimi = rs.getString("NIMI");
				int lkm = rs.getInt("LKM");
				System.out
						.println(enimi + " " + snimi + " " + nimi + " " + lkm);

			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("Virhe");

		}
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
