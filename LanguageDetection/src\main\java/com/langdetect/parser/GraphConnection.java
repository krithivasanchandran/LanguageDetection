package com.langdetect.parser;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.neo4j.jdbc.Driver;

public class GraphConnection {
	
	public static Connection establishDBconnectivity(){
		
		try{
			Driver driver = new org.neo4j.jdbc.Driver();
			DriverManager.registerDriver(driver);
			Connection con = DriverManager.getConnection("jdbc:neo4j:http://localhost:11001", "neo4j", "krithi");
		    return con;
		    
		    
		    /*************** ARCHIVE *************/
		    /* 
			String query = "CREATE ( n:url {name :'" + root + "'})";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeQuery();
			stmt.close();
			
			Connection conn = dbconnect.establishDBconnectivity();

			if (conn == null)
				System.out.println(" DB connection is returning NULL !!! ");
			
			if (!check) {
				String qe = "CREATE ( n:url {name :'" + head + "'})";
				PreparedStatement s = conn.prepareStatement(qe);
				s.executeQuery();

			}
			
			queueA.forEach((linkBuilder) -> {
				try {

					buffer.append("CREATE ( r:url {name :'" + linkBuilder + "'})");
					PreparedStatement st;
					st = connection.prepareStatement(buffer.toString());
					st.executeQuery();
					buffer.setLength(0);

					buffer.append("MATCH (n:url),(r:url) WHERE n.name = '" + head + "' AND r.name = '" + linkBuilder
							+ "'");
					buffer.append("CREATE (n)-[:en]->(r)");
					PreparedStatement st_1;
					st_1 = connection.prepareStatement(buffer.toString());
					st_1.executeQuery();
					buffer.setLength(0);

				} catch (Exception e) {
					e.printStackTrace();
				}

			});
			connection.close();
		}
		check = false;
		*/
			
		    /*************** ARCHIVE *************/
		  
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		return null;
	}
}
