package trackchanges;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONException;
import org.json.JSONObject;

@ServerEndpoint (value="/endpoint")
public class WebSocketEndpoint {
	
	// Store users and their sessions in the map
	private static final Map<String, Session> sessions = new HashMap<String, Session>();
	private static ApplicationLayer application = new ApplicationLayer();
	//private static Lock lock = new ReentrantLock();
	// Set up logger for debugging
	private static final Logger log = Logger.getLogger("TrackChanges");
	
	
	//Called when swift client connects
   @OnOpen
    public void onOpen(Session session) {
       System.out.println("onOpen::" + session.getId());        
	   log.info("Connection openend by id: " + session.getId());
	  // sessions.put(session.getId(), session);
    }
   
  
	//Called when a connection is closed
	@OnClose
	public void close(Session session) {
		 System.out.println("onClose:: " + session.getId());	
		 log.info("Connection closed by id: " + session.getId());
	}
	
//	//Called when a message is received by the client
	
	@OnMessage
	public void onMessage (byte[] b, Session session) {
		String printMe ="";
		System.out.println("onMessage::From= " + session.getId() + "Message");
		try {
			printMe = new String(b, "US-ASCII");
			
		}catch(UnsupportedEncodingException uee) {
			uee.printStackTrace();
		}
		JSONObject json = null;
	
		try {
			json = new JSONObject("");
		}catch(JSONException e) {
			e.printStackTrace();
		}
		
		
		application.parseJSON(json, session);
		
	
	}
	
//  @OnMessage
//    public void onMessage(String message, Session session) {
//        System.out.println("onMessage::From=" + session.getId() + " Message=" + message);
//        
//        try {
//            session.getBasicRemote().sendText("Hello Client " + session.getId() + "!");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
  
  //Called when an error for this endpoint occurred
   @OnError
    public void onError(Throwable e) {
       System.out.println("onError::" + e.getMessage());

    }
   
   public void sendToSession(Session session, byte[] data) {
	   
	   try {
		   ByteBuffer tobeSent = ByteBuffer.wrap(data);
		   session.getBasicRemote().sendBinary(tobeSent);
		   
	   }catch(IOException ioe) {
		   sessions.remove(session.getId());   
	   }
	   
   }
//   
//   public void sendToAllSessions(Session session, byte[] data) {
//	 
//	   lock.lock();
//	   try {
//		   ByteBuffer tobeSent = ByteBuffer.wrap(data);
//		   //loop through sessions
//		   for (String key : sessions.keySet()) {
//			    if (sessions.get(key)!=session) {
//			    	sessions.get(key).getBasicRemote().sendBinary(tobeSent);
//			    }
//			}		   
//	   }catch(IOException ioe) {   
//		   ioe.printStackTrace();
//	   }
//	   lock.unlock();
//	   
//   }
   
  	
}