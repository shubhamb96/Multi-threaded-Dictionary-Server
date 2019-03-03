import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * @author Shubham Bawa
 * Student ID: 936127
 * 
 * Creates a graphical user interface for 
 * the Server side that can read the dictionary.
 */
public class ServerGUI 
{
	public JFrame frmServerDictionary;
	public JButton loadButton;
	public JTextArea textArea;
	public JTextArea errorReport;
	
	public void createWindow(String filepath) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				ServerGUI window = new ServerGUI(filepath);
				window.frmServerDictionary.setVisible(true);
			}	
		});
	}
	
	public ServerGUI(String filepath) 
	{
		initialize(filepath);
	}
	
	private void initialize(String filepath) 
	{
		frmServerDictionary = new JFrame();
		frmServerDictionary.setTitle("Server - Dictionary");
		frmServerDictionary.setBounds(100, 100, 456, 423);
		frmServerDictionary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServerDictionary.getContentPane().setLayout(null);
		
		JLabel lblDictionary = new JLabel("Word:Meaning");
		lblDictionary.setBounds(10, 6, 98, 16);
		frmServerDictionary.getContentPane().add(lblDictionary);
		
		textArea = new JTextArea();
		JScrollPane sp = new JScrollPane(textArea);
		sp.setBounds(10,27,434,245);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		textArea.setWrapStyleWord(true);
		textArea.setBounds(6, 26, 438, 246);
		frmServerDictionary.getContentPane().add(sp);
		
		loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				readDict(filepath);
			}
		});
		loadButton.setBounds(327, 1, 117, 29);
		frmServerDictionary.getContentPane().add(loadButton);
		
		errorReport = new JTextArea();
		JScrollPane sp1 = new JScrollPane(errorReport);
		sp1.setBounds(10,284,434,91);
		sp1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		errorReport.setBounds(10, 284, 434, 91);
		frmServerDictionary.getContentPane().add(sp1);
	}
	
		public void readDict(String filepath)
		{
			try 
			{
				FileReader reader = new FileReader(filepath);
				BufferedReader br = new BufferedReader(reader);
       
				textArea.read( br, null);
				br.close();
			}
			catch (IOException e1) 
			{
				errorReport.setText("Error: File not found at: "+filepath);
			}
			textArea.requestFocus();
			return;
	}
}
		
