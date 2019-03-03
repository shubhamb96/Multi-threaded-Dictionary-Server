import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author Shubham Bawa
 * Student ID: 936127
 * 
 * Creates a graphical user interface on the
 * client side, invokes the client.java when
 * a button is pressed and displays results.
 * 
 */
public class ClientGUI
{
	Client objClient = new Client();
	JFrame clientInterface;
	JTextField Word_textField;
	
	static String ip;
	static int port;
	
	public void getIP(String ip)
	{
		this.ip = ip;
	}
	public void getPort(int port)
	{
		this.port = port;	
	}
	
	public static void ClientWindow(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI();
					window.clientInterface.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public ClientGUI() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		clientInterface = new JFrame();
		clientInterface.setTitle("Multi-threaded Dictionary Server");
		clientInterface.setBounds(100, 100, 450, 400);
		clientInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Word_textField = new JTextField();
		Word_textField.setBounds(80, 6, 205, 26);
		Word_textField.setColumns(10);
		
		JLabel lblWord = new JLabel("Word");
		lblWord.setBounds(15, 11, 32, 16);
		
		final JTextArea Meaning_textArea = new JTextArea();
		JScrollPane sp = new JScrollPane(Meaning_textArea);
		sp.setBounds(80,41,205,130);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		Meaning_textArea.setWrapStyleWord(true);
		Meaning_textArea.setLineWrap(true);
		Meaning_textArea.setBounds(80, 41, 205, 130);
		clientInterface.getContentPane().add(sp);
		
		JLabel lblNewLabel = new JLabel("Meaning");
		lblNewLabel.setBounds(15, 50, 53, 16);
		
		final JTextArea Result_textArea = new JTextArea();
		Result_textArea.setLineWrap(true);
		Result_textArea.setWrapStyleWord(true);
		Result_textArea.setBounds(80, 189, 205, 149);
		
		//Three buttons: Query, Insert and Remove
		JButton btnQuery = new JButton("Query");
		btnQuery.setBounds(330, 6, 81, 29);
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(331, 112, 80, 29);
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(330, 142, 93, 29);
		
		clientInterface.getContentPane().setLayout(null);
		clientInterface.getContentPane().add(lblNewLabel);
		clientInterface.getContentPane().add(lblWord);
		clientInterface.getContentPane().add(Word_textField);
		clientInterface.getContentPane().add(Result_textArea);

		clientInterface.getContentPane().add(btnQuery);
		clientInterface.getContentPane().add(btnAdd);
		clientInterface.getContentPane().add(btnRemove);
		
		JLabel lblNewLabel_1 = new JLabel("Result");
		lblNewLabel_1.setBounds(15, 200, 39, 16);
		clientInterface.getContentPane().add(lblNewLabel_1);
		
		//Query button listener
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				
				//add event for Query button
				String word = Word_textField.getText();
				if(word.isEmpty())
				{
					Result_textArea.setText("Please type a word.");
					return;
				}
				String meaning = Meaning_textArea.getText();
				
				String displayText = objClient.query(ip,port,word,meaning);
				if(displayText.equals("Word does not exist in the dictionary")) 
				{
					Meaning_textArea.setText("");
					Word_textField.setText("");
					Result_textArea.setText(displayText);
				}
				else if(displayText.equals("Server Error: Word could not be searched for 'Query' operation."))
				{
					Result_textArea.setText(displayText);
					Meaning_textArea.setText("");
					Word_textField.setText("");
				}
				else
				{
					Result_textArea.setText("");
					Meaning_textArea.setText(displayText);
				}
			}
			
		});
	
		
		//Add button listener
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//add event for add button
				String word = Word_textField.getText();
				String meaning = Meaning_textArea.getText();
				if(word.isEmpty() && meaning.isEmpty())
				{
					Result_textArea.setText("Please type the Word & Meaning.");
					return;
				}
				else if(word.isEmpty())
				{
					Result_textArea.setText("Please type a word.");
					return;
				}
				else if(meaning.isEmpty())
				{
					Result_textArea.setText("Please type the meaning.");
					return;
				}
				else if(word.matches("[a-zA-Z]+") != true)
				{
					Result_textArea.setText("Please type a word with Uppercase or Lowercase letters.");
					Word_textField.setText("");
					Meaning_textArea.setText("");
					return;
				}
				
				String displayText = objClient.add(ip,port,word,meaning);
				
				Result_textArea.setText(displayText);
				Word_textField.setText("");
				Meaning_textArea.setText("");
			}
		});
		
		
		//Remove button listener
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//add event for Remove button
				String word = Word_textField.getText();
				if(word.isEmpty())
				{
					Result_textArea.setText("Please type a word.");
					return;
				}
				String meaning = Meaning_textArea.getText();
				String displayText = objClient.remove(ip,port,word,meaning);
				
				Word_textField.setText("");
				Meaning_textArea.setText("");
				Result_textArea.setText(displayText);
			}
		});
	
	}
}
