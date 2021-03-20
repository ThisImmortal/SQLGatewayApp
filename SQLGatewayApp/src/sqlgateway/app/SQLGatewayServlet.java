package sqlgateway.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SQLGatewayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sqlStatement = request.getParameter("sqlStatement");
		String sqlResult = "";
		
		try {
			
			Properties props = new Properties();
			InputStream in = getClass().getClassLoader().getResourceAsStream("db.properties");
			props.load(in);
			in.close();
			
			
			String driver = props.getProperty("jdbc.driver");
			
			if(driver != null) {
				Class.forName(driver);
			}
			String dbURL = props.getProperty("jdbc.url");
			String username = props.getProperty("jdbc.username");
			String password = props.getProperty("jdbc.password");
			Connection connection = DriverManager.getConnection(dbURL, username, password);
			
			Statement statement = connection.createStatement();
			
			sqlStatement = sqlStatement.trim();
			if(sqlStatement.length() >= 6) {
				
				String sqlType = sqlStatement.substring(0, 6);
				if(sqlType.equalsIgnoreCase("select")) {
					
					ResultSet resultSet = statement.executeQuery(sqlStatement);
					sqlResult = SQLUtil.getHtmlTable(resultSet);
					resultSet.close();
				}
				else {
					
					int i = statement.executeUpdate(sqlStatement);
					if(i == 0) {
						sqlResult = "<p>Entered SQL Statement executed succesfully</p>";
					}
					else {
						sqlResult = "<p>Entered SQL Statement executed succesfully.<br>" + i + " row(s) affected.</p>";
						
					}
					
				}
				
			}
			
			statement.close();
			connection.close();
			
		} catch (ClassNotFoundException e) {
			sqlResult = "<p>Error loading the database driver: <br>" + e.getMessage() + "</p>";
		}
		
		catch (SQLException e) {
			sqlResult = "<p>Error executing the SQL statement: <br>" + e.getMessage() + "</p>";
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("sqlStatement", sqlStatement);
		session.setAttribute("sqlResult", sqlResult);
		
		String url = "/index.jsp";
		getServletContext().getRequestDispatcher(url).forward(request, response);
		
		
	}

}
