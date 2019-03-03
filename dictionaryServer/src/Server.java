import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Shubham Bawa
 * Student ID: 936127
 * 
 * Gets the <port> and <dictionary-file> and invokes the user interface,
 * creates a connection, gets request, processes it and replies back
 * to the client.
 */
public class Server 
{
	static int port;
	static String filepath;

	static ServerGUI sg = new ServerGUI(filepath);
	
	static String type = "";
	static String word = "";
	static String meaning = "";
	
	static Scanner fileScanner;
	static String searchResult = "";
 
 	public static void main(String []args)
 	{
	 	try 
	 	{
			port = Integer.parseInt(args[0]); //get port
	 		filepath = args[1]; //get filepath
	 		sg.createWindow(filepath); //create server's graphical user interface
	 	}
	 	catch(Exception e)
	 	{
	 		JOptionPane.showMessageDialog(null, "Please enter the 'Port number' followed by the 'File path'.", "Server Error", JOptionPane.ERROR_MESSAGE);
 		}
	 	
	 	try 
	 	{
	 		ServerSocket ss = new ServerSocket(port);
			while(true)
			{
				Socket s = ss.accept();
				
				// Start a new thread
				Thread t = new Thread(() -> 
				{
	
						serveClient(s,filepath);
					
				});
				t.start();
			}
		} 
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null, "Please enter a valid port number", "Server Error", JOptionPane.ERROR_MESSAGE);
		}
 	}
	public static void serveClient(Socket client,String filepath)
	{
		try(Socket clientSocket = client)
		{
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(),"UTF-8"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream(),"UTF-8"));
			
			String line = reader.readLine();
					
			JSONObject fromClient = null;
			try 
			{
				fromClient = new JSONObject(line);
				type = fromClient.getString("queryType");
				word = fromClient.getString("userWord");
				meaning = fromClient.getString("meaning");
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "Server cannot receive data from client", "Server Error", JOptionPane.ERROR_MESSAGE);
			}
				
			if(type.equals("add"))
			{
				String display = addRecord(word,meaning,filepath,type);
				JSONObject toClient = new JSONObject();		
				
				toClient.put("display", display);
				
				writer.write(toClient.toString());
				writer.newLine();
				writer.flush();	
			}
			else if(type.equals("query"))
			{
				String display = queryRecord(word,meaning,filepath,type);
				JSONObject toClient = new JSONObject();	
				toClient.put("display", display);
				
				writer.write(toClient.toString());
				writer.newLine();
				writer.flush();
			}	
			else if(type.equals("remove"))
			{
				String display = removeRecord(word,filepath,type);
				JSONObject toClient = new JSONObject();	
				toClient.put("display", display);
		    	
				writer.write(toClient.toString());
		   		writer.newLine();
		   		writer.flush();	
			}
		} 
		catch (IOException e1) 
		{
			JOptionPane.showMessageDialog(null, "Server cannot connect to client", "Server Error", JOptionPane.ERROR_MESSAGE);
		} 
		catch (JSONException e) 
		{
			JOptionPane.showMessageDialog(null, "Server cannot send data to client", "Server Error", JOptionPane.ERROR_MESSAGE);
		} 
		
	}
		 	public static String addRecord(String word, String meaning, String filepath, String type)
			{
		 		String addResult = "";
		 		try 
		 		{
					searchRecord(word,filepath,type);
				} 
		 		catch (Exception e)
		 		{
		 			addResult = "Server Error: Word could not be added to dictionary";
		 			return addResult;
				}
		 		if(searchResult.equals("not found"))
		 		{
		 			try
		 			{
		 				FileWriter fw = new FileWriter(filepath,true);
		 				BufferedWriter bw = new BufferedWriter(fw);
		 				PrintWriter pw = new PrintWriter(bw);
					
		 				pw.println(word+":"+meaning);
		 				pw.flush();
		 				pw.close();
		 				
		 				addResult = "Word added to dictionary";
		 				return addResult;
		 			}
		 			catch(Exception E)
		 			{
		 				addResult = "Server Error: Word could not be added to dictionary";
		 				return addResult;
		 			}
		 		}
		 		else if(searchResult.equals("found"))
		 		{
		 			addResult = "Word already exists in the dictionary";
		 			return addResult;
		 		}
				return addResult;
			}
		 	public static String queryRecord(String word, String meaning, String filepath, String type)
		 	{
		 		String queryResult = "";
		 		try 
		 		{
					searchRecord(word,filepath,type);
				} 
		 		catch (Exception e) 
		 		{
		 			queryResult = "Server Error: Word could not be searched for 'Query' operation.";
		 			return queryResult;
				}
		 		if(searchResult.equals("not found"))
		 		{
		 			queryResult = "Word does not exist in the dictionary";
		 			return queryResult;
		 		}
		 		else //found
		 		{
		 			return searchResult;
		 		}
		 	}
		 	public static String removeRecord(String removeWord, String filepath,String type) 
			{
		 		String removeResult = "";
		 		try 
		 		{
					searchRecord(word,filepath,type);
				} 
		 		catch (Exception e)
		 		{
		 			removeResult = "Server Error: Word could not be searched for 'Remove' operation.";
		 			return removeResult;
				}
		 		if(searchResult.equals("found"))
		 		{
		 			String tempFile = "temp.txt";
		 			File oldFile = new File(filepath);
		 			File newFile = new File(tempFile);
		 		
		 			String word = ""; 
		 			String meaning = "";
		 		
		 			try
		 			{
		 				FileWriter fw = new FileWriter(tempFile, true);
		 				BufferedWriter bw = new BufferedWriter(fw);
		 				PrintWriter pw = new PrintWriter(bw);
		 			
		 				fileScanner = new Scanner(new File(filepath));
		 				fileScanner.useDelimiter("[:\n]");
		 			
		 				while(fileScanner.hasNext())
		 				{
		 					word = fileScanner.next();
		 					meaning = fileScanner.next();
		 				
		 					if(!word.equalsIgnoreCase(removeWord))
		 					{
		 						pw.println(word+":"+meaning);
		 					}	
		 				}
		 				fileScanner.close();
		 				pw.flush();
		 				pw.close();
		 				oldFile.delete();
		 				File rename = new File(filepath);
		 				newFile.renameTo(rename);
		 				
		 				removeResult = "Word deleted";
		 				return removeResult;
		 				
		 			}
		 			catch(Exception e)
		 			{
		 				removeResult = "Server Error: Word could not be deleted";
		 				return removeResult;
		 			}	
		 		}
		 		else if(searchResult.equals("not found"))
		 		{
		 			removeResult = "Word does not exist in the dictionary";
	 				return removeResult;
		 		}
				return removeResult;
			}
		 	public static String searchRecord(String searchWord, String filepath, String type)
		 	{
		 		boolean found = false;
		 		String word = "";
		 		String meaning = "";
		 		
				try 
				{
					fileScanner = new Scanner(new File(filepath));
				} 
				catch (FileNotFoundException e) 
				{
					if(type.equals("add"))
					{
						JOptionPane.showMessageDialog(null, "Server is connected to client but cannot perfrom the 'Add' operation", "Server Error", JOptionPane.ERROR_MESSAGE);
					}
					else if(type.equals("query"))
					{
						JOptionPane.showMessageDialog(null, "Server is connected to client but cannot perform the 'Query' operation", "Server Error", JOptionPane.ERROR_MESSAGE);
					}
					else if(type.equals("remove"))
					{
						JOptionPane.showMessageDialog(null, "Server is connected to client but cannot perform the 'Remove' operation", "Server Error", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Server cannot search a word in the dictionary", "Server Error", JOptionPane.ERROR_MESSAGE);
					}
				}
		 		
				fileScanner.useDelimiter("[:\n]");
		 			
	 			while(fileScanner.hasNext() && !found)
		 		{
		 			word = fileScanner.next();
					meaning = fileScanner.next();
		 			
	 				if(word.equalsIgnoreCase(searchWord))
	 				{
		 				found = true;
		 			}
		 		}
				if(type.equals("add") || type.equals("remove"))
	 			{
					if(found == true)
		 			{
						return searchResult = "found";
		 			}
		 			else 
		 			{
		 				return searchResult = "not found";
		 			}
		 		}
		 		else if(type.equals("query"))
		 		{
		 			if(found == true)
					{
	 					return searchResult = meaning;
	 				}
		 			else
		 			{
		 				return searchResult = "not found";
					}	
	 			}	
			return searchResult;
		 	}		
}

