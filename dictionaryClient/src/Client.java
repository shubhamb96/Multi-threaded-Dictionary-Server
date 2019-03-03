import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Shubham Bawa
 * Student ID: 936127
 * 
 * Gets the <server-address> and <server-port>.
 * Invokes the client's user interface and gets
 * invoked when a button is pressed in the interface,
 * requests server on the basis of the press and gets 
 * back the result to display them.
 * 
 */
public class Client
{
	static ClientGUI objGUI = new ClientGUI();
	static String errorMessage = "";
	
	public static void main(String []args)
	{
		try 
		{
			String ip = args[0];
			int port = Integer.parseInt(args[1]);
			
			objGUI.ClientWindow();
			objGUI.getIP(ip);
			objGUI.getPort(port);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Please enter the 'Server address' followed by the 'Server port'.", "Connection Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	public String add(String ip, int port, String word, String meaning)
	{
		String addResult ="";
		String displayText = "";
		try(Socket socket = new Socket(ip, port);){
			// Output and Input Stream
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			 
			//to send data to the server
		    JSONObject data = new JSONObject();
		    String type = "add";
		    data.put("queryType", type);
		    data.put("userWord",word);
		    data.put("meaning", meaning);
		    
		    writer.write(data.toString());
		    writer.newLine();
		    writer.flush();
		    
		    //to get results from the server
		    addResult = reader.readLine();
		    JSONObject addObj = new JSONObject(addResult);
		    displayText = addObj.getString("display");
		   
			//Disconnecting
			reader.close();
			writer.close();
			socket.close();
		} 
		catch (UnknownHostException e) 
		{
			JOptionPane.showMessageDialog(null, "Cannot connect to Server at Address: "+ip+" , Port: "+port, "Connection Error", JOptionPane.ERROR_MESSAGE);
		} 
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null, "Cannot connect to Server at Address: "+ip+" , Port: "+port, "Connection Error", JOptionPane.ERROR_MESSAGE);
		} 
		catch (JSONException e) 
		{
			JOptionPane.showMessageDialog(null, "Cannot send data to Server at Address", "Connection Error", JOptionPane.ERROR_MESSAGE);	
		} 
		
		return displayText;
	}
	public String query(String ip, int port, String word, String meaning)
	{
		String queryResult ="";
		String displayText = "";
		
		try(Socket socket = new Socket(ip, port);){
			// Output and Input Stream
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			 
			//to send data to the server
		    JSONObject data = new JSONObject();
		    String type = "query";
		    data.put("queryType", type);
		    data.put("userWord",word);
		    data.put("meaning", meaning);
		    
		    writer.write(data.toString());
		    writer.newLine();
		    writer.flush();
		    
		    //to get results from the server
		    queryResult = reader.readLine();
		    JSONObject queryObj = new JSONObject(queryResult);
		    displayText = queryObj.getString("display");
		   
			//Disconnecting
			reader.close();
			writer.close();
			socket.close();
		}
		catch (UnknownHostException e) 
		{
			JOptionPane.showMessageDialog(null, "Cannot connect to Server at Address: "+ip+" , Port: "+port, "Connection Error", JOptionPane.ERROR_MESSAGE);
		} 
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null, "Cannot connect to Server at Address: "+ip+" , Port: "+port, "Connection Error", JOptionPane.ERROR_MESSAGE);
		} 
		catch (JSONException e) 
		{
			JOptionPane.showMessageDialog(null, "Cannot send data to Server at Address", "Connection Error", JOptionPane.ERROR_MESSAGE);	
		} 
		return displayText;
	}
	public String remove(String ip, int port, String word, String meaning)
	{
		String removeResult ="";
		String displayText = "";
		try(Socket socket = new Socket(ip, port);){
			// Output and Input Stream
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			 
			//to send data
		    JSONObject data = new JSONObject();
		    String type = "remove";
		    data.put("queryType", type);
		    data.put("userWord",word);
		    data.put("meaning", meaning);
		    
		    writer.write(data.toString());
		    writer.newLine();
		    writer.flush();
		    
		    //to get results from the server
		    removeResult = reader.readLine();
		    JSONObject queryObj = new JSONObject(removeResult);
		    displayText = queryObj.getString("display");
		   
			//Disconnecting
			reader.close();
			writer.close();
			socket.close();
		 
		} 
		catch (UnknownHostException e) 
		{
			JOptionPane.showMessageDialog(null, "Cannot connect to Server at Address: "+ip+" , Port: "+port, "Connection Error", JOptionPane.ERROR_MESSAGE);
		} 
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null, "Cannot connect to Server at Address: "+ip+" , Port: "+port, "Connection Error", JOptionPane.ERROR_MESSAGE);
		} 
		catch (JSONException e) 
		{
			JOptionPane.showMessageDialog(null, "Cannot send data to Server at Address", "Connection Error", JOptionPane.ERROR_MESSAGE);	
		} 
		return displayText;
	}
}
