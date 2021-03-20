package sqlgateway.app;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SQLUtil {

	public static String getHtmlTable(ResultSet resultSet) throws SQLException {
		
		StringBuilder htmlTable = new StringBuilder();
		
		ResultSetMetaData metaData = resultSet.getMetaData();
		
		int columnCount = metaData.getColumnCount();
		
		htmlTable.append("<table>");
		
		htmlTable.append("<tr>");
		for(int i = 1; i<=columnCount; i++) {
			htmlTable.append("<th>");
			htmlTable.append(metaData.getColumnName(i));
			htmlTable.append("</th>");
			
		}
		htmlTable.append("</tr>");
		
		while(resultSet.next()) {
			htmlTable.append("<tr>");
			for(int i = 1; i<=columnCount; i++) {
				htmlTable.append("<td>");
				htmlTable.append(resultSet.getString(i));
				htmlTable.append("</td>");
				
			}
			htmlTable.append("</tr>");
			
		}
		
		htmlTable.append("</table");
		
		return htmlTable.toString();
	}
}
