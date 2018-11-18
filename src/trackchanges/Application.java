package trackchanges;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.joda.time.DateTime;

public class Application {
	
	private static final long serialVersionUID = 1L;
	
	// Variables:
	/*
	 * This contains the function will store the global url we will 
	 * use to connect to the SQL database that is storing all the data 
	 * necessary for the application, for example:  
	 * “jdbc:mysql://localhost:3306/CalendarApp?user=root&password=&useSSL=false”;
	 */
	private static final String DATABASE_CONNECTION_URL = "jdbc:mysql://localhost:3306/CSCI201ProjectDatabase?user=root&password=root&useSSL=false";
	
	// Functions:
	/*
	 * This function will be responsible for adding new users into 
	 * the database with “INSERT” statements after a connection using 
	 * the JDBC DriverManager is established. Insertion will also be 
	 * surrounded by Try, Catch blocks to ensure a minimum level of 
	 * error handling. Function will return “True” if user is successfully 
	 * added and “False” otherwise.
	 */
	public boolean addUser(User newUser) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean result = false;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
			ps = conn.prepareStatement(
					"INSERT INTO User ("
					+ "user_id, "
					+ "user_displayname, "
					+ "user_logintimestamp, "
					+ "user_imageurl"
					+ ") VALUES ('" 
					+ newUser.getUserId() + "', '"
					+ newUser.getUserDisplayName() + "', '"
					+ newUser.getUserLoginTimeStamp() + "', '"
					+ newUser.getUserImageUrl()
					+ "');");
			result = ps.execute();
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			// You always need to close the connection to the database
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(SQLException sqle) {
				System.out.println("sqle closing error: " + sqle.getMessage());
			}
		}
		return result;
	}

	/*
	 * This function will be responsible for adding a new follower relationship 
	 * into the database with “INSERT” statements after a connection using the 
	 * JDBC DriverManager is established. Insertion will also be surrounded by Try, 
	 * Catch blocks to ensure a minimum level of error handling. Function will return 
	 * “True” if follower is successfully added and “False” otherwise.
	 */
	public boolean follow(String user_id, String follower_id) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean result = false;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
			ps = conn.prepareStatement(
					"INSERT INTO Follow (user_id, "
					+ "follow_id) VALUES ('" 
					+ user_id + "', '"  
					+ follower_id + "');");
			result = ps.execute();
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			// You always need to close the connection to the database
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(SQLException sqle) {
				System.out.println("sqle closing error: " + sqle.getMessage());
			}
		}
		return result;
	}

	/*
	 * This function will be responsible for deleting a follower relationship permanently 
	 * using the â€œDELETEâ€� statement after a connection using the JDBC DriverManager is 
	 * established. Deletion will also be surrounded by Try, Catch blocks to ensure a 
	 * minimum level of error handling. Function will return â€œTrueâ€� if relationship is 
	 * successfully deleted and â€œFalseâ€� otherwise.
	 */
	public boolean unfollow(String user_id, String follower_id) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean result = false;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
			// not sure how to delete based off two parameters
			ps = conn.prepareStatement(
					"DELETE FROM Follow WHERE user_id=" + user_id + " AND " + "follower_id=" + follower_id);
			result = ps.execute();
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			// You always need to close the connection to the database
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(SQLException sqle) {
				System.out.println("sqle closing error: " + sqle.getMessage());
			}
		}
		return result;
	}

	/*
	 * This function will be responsible for retrieving all the followers of a user 
	 * using the â€œSELECTâ€� statement after a connection using the JDBC DriverManager 
	 * is established. Deletion will also be surrounded by Try, Catch blocks to ensure 
	 * a minimum level of error handling. Function will return an array of â€œuser_idâ€�(s) 
	 * corresponding to each follower. Size of array will be the number of followers a user has.
	 */
	public ArrayList<User> getFollowers(String user_id) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<String> result = new ArrayList<String>(); 
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
			ps = conn.prepareStatement("SELECT f.follower_id FROM Follow f WHERE f.user_id = '" + user_id + "';");
			rs = ps.executeQuery();
			while(rs.next()){
				result.add(rs.getString("follower_id"));
			}
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			// You always need to close the connection to the database
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(SQLException sqle) {
				System.out.println("sqle closing error: " + sqle.getMessage());
			}
		}
		
		ArrayList<User> followers = new ArrayList<User>();
		for(String follower : result) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
				ps = conn.prepareStatement("SELECT "
						+ "u.user_id, "
						+ "u.user_displayname, "
						+ "u.user_imageurl, "
						+ "u.user_logintimestamp "
						+ "FROM User u "
						+ "WHERE u.user_id = '" + follower + "';");
				rs = ps.executeQuery();
				while(rs.next()){
					User followerUser = new User();
					followerUser.setUserId(rs.getString("user_id"));
					followerUser.setUserDisplayName(rs.getString("user_displayname"));
					followerUser.setUserImageUrl(rs.getString("user_imageurl"));
					followerUser.setUserLoginTimeStamp(rs.getString("user_logintimestamp"));
					followers.add(followerUser);
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			} catch (ClassNotFoundException cnfe) {
				System.out.println("cnfe: " + cnfe.getMessage());
			} finally {
				// You always need to close the connection to the database
				try {
					if (rs != null) {
						rs.close();
					}
					if (st != null) {
						st.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch(SQLException sqle) {
					System.out.println("sqle closing error: " + sqle.getMessage());
				}
			}
		}
		
		return followers;
	}

	/*
	 * This function will be responsible for retrieving all the users that the current 
	 * user is following using the â€œSELECTâ€� statement after a connection using the JDBC 
	 * DriverManager is established. Deletion will also be surrounded by Try, Catch blocks 
	 * to ensure a minimum level of error handling. Function will return an array of â€œuser_idâ€�(s) 
	 * corresponding to each user the current user is following. Size of array will be the 
	 * number of users the user specified is following.
	 */
	public ArrayList<User> getFollowings(String user_id) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<String> result = new ArrayList<String>(); 
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
			ps = conn.prepareStatement("SELECT f.user_id FROM Follow f WHERE f.follower_id = '" + user_id + "';");
			rs = ps.executeQuery();
			while(rs.next()){
				result.add(rs.getString("user_id"));
			}
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			// You always need to close the connection to the database
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(SQLException sqle) {
				System.out.println("sqle closing error: " + sqle.getMessage());
			}
		}
		
		ArrayList<User> followings = new ArrayList<User>();
		for(String following : result) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
				ps = conn.prepareStatement("SELECT "
						+ "u.user_id, "
						+ "u.user_displayname, "
						+ "u.user_imageurl, "
						+ "u.user_logintimestamp "
						+ "FROM User u "
						+ "WHERE u.user_id = '" + following + "';");
				rs = ps.executeQuery();
				while(rs.next()){
					User followingUser = new User();
					followingUser.setUserId(rs.getString("user_id"));
					followingUser.setUserDisplayName(rs.getString("user_displayname"));
					followingUser.setUserImageUrl(rs.getString("user_imageurl"));
					followingUser.setUserLoginTimeStamp(rs.getString("user_logintimestamp"));
					followings.add(followingUser);
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			} catch (ClassNotFoundException cnfe) {
				System.out.println("cnfe: " + cnfe.getMessage());
			} finally {
				// You always need to close the connection to the database
				try {
					if (rs != null) {
						rs.close();
					}
					if (st != null) {
						st.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch(SQLException sqle) {
					System.out.println("sqle closing error: " + sqle.getMessage());
				}
			}
		}
		
		return followings;
	}

	/*
	 * This function will be responsible for adding new albums, album artist(s), and 
	 * all the songs in it  into the database with â€œINSERTâ€� statements after a connection 
	 * using the JDBC DriverManager is established. Insertion will also be surrounded by 
	 * Try, Catch blocks to ensure a minimum level of error handling. Function will return 
	 * â€œTrueâ€� if album is successfully added and â€œFalseâ€� otherwise.
	 */
	public boolean addAlbum(String album_id) {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean result = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
			ps = conn.prepareStatement("INSERT INTO Album (album_id) VALUES ('" + album_id+ "');");
			result = ps.execute();
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			// You always need to close the connection to the database
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(SQLException sqle) {
				System.out.println("sqle closing error: " + sqle.getMessage());
			}
		}
		return result;
	}

	/*
	 * This function will be responsible for deleting existing albums, and all posts that 
	 * included those albums and the songs inside the album from the database permanently 
	 * using the â€œDELETEâ€� statement after a connection using the JDBC DriverManager is 
	 * established. Deletion will also be surrounded by Try, Catch blocks to ensure a 
	 * minimum level of error handling. Function will return â€œTrueâ€� if album is successfully 
	 * deleted and â€œFalseâ€� otherwise.
	 */
	public boolean deleteAlbum(String album_id) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean result = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
			
			ps = conn.prepareStatement(
					"DELETE FROM Album WHERE album_id=?");
			ps.setString(1,  album_id);
			result = ps.execute();
			
			ps = conn.prepareStatement(
					"DELETE FROM PostAlbum WHERE album_id=?");
			ps.setString(1,  album_id);
			ps.execute();
			
			
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			// You always need to close the connection to the database
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(SQLException sqle) {
				System.out.println("sqle closing error: " + sqle.getMessage());
			}
		}
		return result;
	}

	/*
	 * This function will be responsible for adding new songs and its corresponding artist(s), 
	 * as well as the updating the â€œAlbumSongâ€� relationship (storing the fact that the song 
	 * belongs to the album whose â€œalbum_idâ€� is specified) in the database with â€œINSERTâ€� 
	 * statements after a connection using the JDBC DriverManager is established. Insertion 
	 * will also be surrounded by Try, Catch blocks to ensure a minimum level of error 
	 * handling. Function will return â€œTrueâ€� if song is successfully added and â€œFalseâ€� otherwise.
	 */
	public boolean addSong(String song_id) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean result = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
			ps = conn.prepareStatement("INSERT INTO Song (song_id) VALUES ('" + song_id+ "');");
			result = ps.execute();
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			// You always need to close the connection to the database
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(SQLException sqle) {
				System.out.println("sqle closing error: " + sqle.getMessage());
			}
		}
		return result;
	}


	/*
	 * This function will be responsible for tracking which users like a particular song 
	 * in the database with â€œINSERTâ€� statements to the â€œAlbumSongâ€� table after a connection 
	 * using the JDBC DriverManager is established. Insertion will also be surrounded by 
	 * Try, Catch blocks to ensure a minimum level of error handling. Function will return 
	 * â€œTrueâ€� if addition is successful and â€œFalseâ€� otherwise.
	 */
	public boolean likeSong(String song_id, String user_id) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean result = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
			ps = conn.prepareStatement(
					"INSERT INTO SongLike (song_id, "
					
					+ "user_id) VALUES ('" 
					+ song_id + "', '" 
				
					+ user_id + "');");
			result = ps.execute();
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			// You always need to close the connection to the database
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(SQLException sqle) {
				System.out.println("sqle closing error: " + sqle.getMessage());
			}
		}
		return result;
	}

	/*
	 * This function will be responsible for deleting the relationship of the user liking 
	 * the song permanently using the â€œDELETEâ€� statement after a connection using the JDBC 
	 * DriverManager is established. Deletion will also be surrounded by Try, Catch blocks 
	 * to ensure a minimum level of error handling. Function will return â€œTrueâ€� if 
	 * relationship is successfully deleted and â€œFalseâ€� otherwise.
	 */
	public boolean unlikeSong(String song_id, String user_id) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean result = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
			// not sure how to delete based off two parameters
			ps = conn.prepareStatement(
					"DELETE FROM SongLike WHERE song_id=" + song_id + " AND " + "user_id=" + user_id);
			result = ps.execute();
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			// You always need to close the connection to the database
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(SQLException sqle) {
				System.out.println("sqle closing error: " + sqle.getMessage());
			}
		}
		return result;
	}

	/*
	 * This function will be responsible for deleting existing songs, the â€œAlbumSongâ€� 
	 * relationships,and all posts that included those songs inside the album from the 
	 * database permanently using the â€œDELETEâ€� statement after a connection using the JDBC 
	 * DriverManager is established. Deletion will also be surrounded by Try, Catch blocks 
	 * to ensure a minimum level of error handling. Function will return â€œTrueâ€� if song is 
	 * successfully deleted and â€œFalseâ€� otherwise.
	 */
	public boolean deleteSong(String song_id) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean result = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
			ps = conn.prepareStatement(
					"DELETE FROM Song WHERE song_id=?");
			ps.setString(1,  song_id);
			result = ps.execute();
			
			ps = conn.prepareStatement(
					"DELETE FROM PostSong WHERE song_id=?");
			ps.setString(1,  song_id);
			ps.execute();
			
			ps = conn.prepareStatement(
					"DELETE FROM SongLike WHERE song_id=?");
			ps.setString(1,  song_id);
			ps.execute();
			
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			// You always need to close the connection to the database
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(SQLException sqle) {
				System.out.println("sqle closing error: " + sqle.getMessage());
			}
		}
		return result;
	}

	/*
	 * This function will be responsible for adding a new post in the database with 
	 * â€œINSERTâ€� statements after a connection using the JDBC DriverManager is 
	 * established (and also updates the â€œPostAlbumâ€� or â€œPostSongâ€� table if required). 
	 * Insertion will also be surrounded by Try, Catch blocks to ensure a minimum level 
	 * of error handling. Function will return â€œTrueâ€� if post is successfully added and 
	 * â€œFalseâ€� otherwise.
	 */
	public boolean addPost(Post newPost) {
		// time stamp needs to figured out here
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean result = false;
		try {
			System.out.println(newPost.getPostId());
			System.out.println(newPost.getPostMessage());
			System.out.println(newPost.getPostUserId());
			System.out.println(newPost.getPostTimeStamp());
			System.out.println(newPost.getPostSongId());
			System.out.println(newPost.getPostAlbumId());
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
			System.out.println("3");
			System.out.println(newPost.getPostUserId());
			System.out.println("4");
			ps = conn.prepareStatement(
					"INSERT INTO Post ("
					+ "post_timestamp, "
					+ "user_id, "
					+ "song_id, "
					+ "album_id, "
					+ "post_message) VALUES ('" 
					+ newPost.getPostTimeStamp().toString() + "', '" 
					+ newPost.getPostUserId() + "', '" 
					+ newPost.getPostSongId() + "', '" 
					+ newPost.getPostAlbumId() + "', '" 
					+ newPost.getPostMessage() + "');");
			result = ps.execute();
			
			// insert into post song id table
//			if(newPost.getPostSongId() != null) {
//				ps = conn.prepareStatement("INSERT INTO PostSong(song_id, "
//						+ "post_id) VALUES ('"
//						+ newPost.getPostSongId() + "', '" 
//						+ newPost.getPostId() + "');");
//				
//			}
//			else { // insert into post album id table
//				ps = conn.prepareStatement("INSERT INTO PostAlbum(album_id, "
//						+ "post_id) VALUES ('"
//						+ newPost.getPostAlbumId() + "', '" 
//						+ newPost.getPostId() + "');");
//			}
			result = ps.execute();	
			
			
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			// You always need to close the connection to the database
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(SQLException sqle) {
				System.out.println("sqle closing error: " + sqle.getMessage());
			}
		}
		return result;
	}

	/*
	 * This function will be responsible for retrieving the posts from the database 
	 * with â€œSELECTâ€� statements after a connection using the JDBC DriverManager is 
	 * established. Retrieval will also be surrounded by Try, Catch blocks to ensure 
	 * a minimum level of error handling. Function will return an array of Post objects 
	 * and null if no posts are found.
	 */
	public Post[] getPosts(String user_id) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<Post> tempRes = new ArrayList<Post>(); 
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
			// not sure how to delete based off two parameters
			ps = conn.prepareStatement(
					"SELECT * from Post WHERE user_id LIKE?");
			ps.setString(1, "%" + user_id + "%");
	    	  
	    	  rs = ps.executeQuery();
	    	  while(rs.next()){
	    		  Post tempPost = new Post();
	    		  
	    		  String tempPostId = rs.getString("post_id");
	    		  String tempPostTimeStamp = rs.getString("post_timestamp");
	    		  String tempUserId = rs.getString("user_id");
	    		  String tempPostMessage = rs.getString("post_message");
	    		  
	    		  tempPost.setPostId(tempPostId);
	    		  tempPost.setPostTimeStamp(tempPostTimeStamp);
	    		  tempPost.setPostUserId(tempUserId);
	    		  tempPost.setPostMessage(tempPostMessage);
	    		  
	    		  PreparedStatement ps2 = conn.prepareStatement(
	    				  "SELECT * from PostAlbum WHERE user_id LIKE?");
	    		  ps2.setString(1, "%" + user_id + "%");
	    		  ResultSet rs2 = ps2.executeQuery();
	    		  
	    		  PreparedStatement ps3 = conn.prepareStatement(
	    				  "SELECT * from SongAlbum WHERE user_id LIKE?");
	    		  ps3.setString(1, "%" + user_id + "%");
	    		  ResultSet rs3 = ps3.executeQuery();
	    		  
	    		  if(ps2 != null) {
	    			  tempPost.setPostAlbumId(rs2.getString("album_id"));
	    		  }
	    		  else {
	    			  tempPost.setPostAlbumId(rs2.getString(null));
	    		  }
	    		  
	    		  if(ps3 != null) {
	    			  tempPost.setPostSongId(rs3.getString("song_id"));
	    		  }
	    		  else {
	    			  tempPost.setPostSongId(rs3.getString(null));
	    		  }
	    		  tempRes.add(tempPost);
	    	  }
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			// You always need to close the connection to the database
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(SQLException sqle) {
				System.out.println("sqle closing error: " + sqle.getMessage());
			}
		}
		
		Post [] res = new Post[tempRes.size()];
		for(int i = 0; i < tempRes.size(); ++i) {
			res[i] = tempRes.get(i);
		}
		return res;
	}

	/*
	 * This function will be responsible for retrieving the posts from the users 
	 * that the user specified is following through the database with â€œSELECTâ€� 
	 * statements after a connection using the JDBC DriverManager is established. 
	 * Retrieval will also be surrounded by Try, Catch blocks to ensure a minimum 
	 * level of error handling. Function will return an array of Post objects and 
	 * null if no posts are found.
	 */
	public Post[] getFeed(String user_id) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<Post> tempRes = new ArrayList<Post>(); 
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
			
			
			
			// first select the users that the target user is following
			ps = conn.prepareStatement(
					"SELECT * from Follow WHERE follower_id LIKE?");
			ps.setString(1, "%" + user_id + "%");
	    	  
	    	  rs = ps.executeQuery();
	    	  ArrayList<String> following = new ArrayList<String>();
	    	  while(rs.next()) {
	    		  following.add(rs.getString("user_id"));
	    	  }
	    	  
	    	  // do we have to reset rs, ps, etc. as null
	    	  // iterate through the users that the target user is following 
	    	  // add posts accordingly
	    	  for(int i = 0; i < following.size(); ++i) {
		  			ps = conn.prepareStatement(
							"SELECT * from Post WHERE user_id LIKE?");
					ps.setString(1, "%" + following.get(i) + "%");
					 rs = ps.executeQuery();
		    	  while(rs.next()){
		    		  Post tempPost = new Post();
		    		  
		    		  String tempPostId = rs.getString("post_id");
		    		  String tempPostTimeStamp = rs.getString("post_timestamp");
		    		  String tempUserId = rs.getString("user_id");
		    		  String tempPostMessage = rs.getString("post_message");
		    		  
		    		  tempPost.setPostId(tempPostId);
		    		  tempPost.setPostTimeStamp(tempPostTimeStamp);
		    		  tempPost.setPostUserId(tempUserId);
		    		  tempPost.setPostMessage(tempPostMessage);
		    		  
		    		  PreparedStatement ps2 = conn.prepareStatement(
		    				  "SELECT * from PostAlbum WHERE user_id LIKE?");
		    		  ps2.setString(1, "%" + user_id + "%");
		    		  ResultSet rs2 = ps2.executeQuery();
		    		  
		    		  PreparedStatement ps3 = conn.prepareStatement(
		    				  "SELECT * from SongAlbum WHERE user_id LIKE?");
		    		  ps3.setString(1, "%" + user_id + "%");
		    		  ResultSet rs3 = ps3.executeQuery();
		    		  
		    		  if(ps2 != null) {
		    			  tempPost.setPostAlbumId(rs2.getString("album_id"));
		    		  }
		    		  else {
		    			  tempPost.setPostAlbumId(rs2.getString(null));
		    		  }
		    		  
		    		  if(ps3 != null) {
		    			  tempPost.setPostSongId(rs3.getString("song_id"));
		    		  }
		    		  else {
		    			  tempPost.setPostSongId(rs3.getString(null));
		    		  }
		    		  tempRes.add(tempPost);
		    	  }
	    	  }

		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			// You always need to close the connection to the database
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(SQLException sqle) {
				System.out.println("sqle closing error: " + sqle.getMessage());
			}
		}
		
		Post [] res = new Post[tempRes.size()];
		for(int i = 0; i < tempRes.size(); ++i) {
			res[i] = tempRes.get(i);
		}
		return res;
	}

	/*
	 * This function will be responsible for tracking which users like a particular 
	 * post in the database with â€œINSERTâ€� statements to the â€œPostLikeâ€� table after 
	 * a connection using the JDBC DriverManager is established. Insertion will also 
	 * be surrounded by Try, Catch blocks to ensure a minimum level of error handling. 
	 * Function will return â€œTrueâ€� if addition is successful and â€œFalseâ€� otherwise.
	 */
	public boolean likePost(String post_id, String user_id) {
		// time stamp needs to figured out here
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean result = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
			ps = conn.prepareStatement(
					"INSERT INTO PostLike (post_id, "
					+ "user_id) VALUES ('" 
					
					+ post_id + "', '" 
					+ user_id + "');");
			result = ps.execute();
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			// You always need to close the connection to the database
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(SQLException sqle) {
				System.out.println("sqle closing error: " + sqle.getMessage());
			}
		}
		return result;
	}

	/*
	 * This function will be responsible for deleting the relationship of the user 
	 * liking the post permanently using the â€œDELETEâ€� statement after a connection 
	 * using the JDBC DriverManager is established. Deletion will also be surrounded 
	 * by Try, Catch blocks to ensure a minimum level of error handling. Function will 
	 * return â€œTrueâ€� if relationship is successfully deleted and â€œFalseâ€� otherwise.
	 */
	public boolean unlikePost(String post_id, String user_id) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean result = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
			ps = conn.prepareStatement(
					"DELETE FROM PostLike WHERE post_id=" + post_id + " AND user_id=" + user_id);
			result = ps.execute();
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			// You always need to close the connection to the database
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(SQLException sqle) {
				System.out.println("sqle closing error: " + sqle.getMessage());
			}
		}
		return result;
	}

	/*
	 * This function will be responsible for tracking which users shared a particular 
	 * post in the database with â€œINSERTâ€� statements to the â€œPostShareâ€� table and also 
	 * adds the same post to â€œPostâ€� table (but under current user) after a connection 
	 * using the JDBC DriverManager is established. Insertion will also be surrounded by 
	 * Try, Catch blocks to ensure a minimum level of error handling. Function will return 
	 * â€œTrueâ€� if addition is successful and â€œFalseâ€� otherwise.
	 */
	public boolean sharePost(String post_id, String user_id, String timeStamp) {
		// ? look at this

		// time stamp needs to figured out here
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean result = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
			ps = conn.prepareStatement(
					"SELECT * from Post WHERE user_id LIKE?");
			ps.setString(1, "%" + post_id + "%");
			rs = ps.executeQuery(); // check for exception here?
			
			
			String postMessage = rs.getString("post_message");
			 
			ps = conn.prepareStatement(
					"INSERT INTO Post (post_id, "
					+ "post_timestamp, "
					+ "user_id, "
					+ "post_message) VALUES ('" 
					
					+ post_id + "', '" 
					+ timeStamp.toString() + "', '" 
					+ user_id + "', '" 
					+ postMessage + "');");
			result = ps.execute();
			
			ps = conn.prepareStatement(
					"INSERT INTO PostShare (post_id, "
					+ "user_id) VALUES ('" 
					
					+ post_id + "', '" 
					+ user_id + "');");
			result = ps.execute();
			
			
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			// You always need to close the connection to the database
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(SQLException sqle) {
				System.out.println("sqle closing error: " + sqle.getMessage());
			}
		}
		return result;
	}

	/* 
	 * This function will be responsible for deleting existing songs, the â€œAlbumSongâ€� 
	 * relationships,and all posts that included those songs inside the album from the 
	 * database permanently using the â€œDELETEâ€� statement after a connection using the 
	 * JDBC DriverManager is established. Deletion will also be surrounded by Try, Catch 
	 * blocks to ensure a minimum level of error handling. Function will return â€œTrueâ€� 
	 * if song is successfully deleted and â€œFalseâ€� otherwise.
	 */
	public boolean deletePost(String post_id) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean result = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_URL);
			ps = conn.prepareStatement(
					"DELETE FROM Post WHERE post_id=" + post_id);
			result = ps.execute();
			
			ps = conn.prepareStatement(
					"DELETE FROM PostLike WHERE post_id=" + post_id);
			ps.execute();
			
			ps = conn.prepareStatement(
					"DELETE FROM PostShare WHERE post_id=" + post_id);
			ps.execute();
			
			ps = conn.prepareStatement(
					"DELETE FROM PostSong WHERE post_id=" + post_id);
			ps.execute();
			
			ps = conn.prepareStatement(
					"DELETE FROM PostAlbum WHERE post_id=" + post_id);
			ps.execute();
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			// You always need to close the connection to the database
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(SQLException sqle) {
				System.out.println("sqle closing error: " + sqle.getMessage());
			}
		}
		return result;
	}

}
