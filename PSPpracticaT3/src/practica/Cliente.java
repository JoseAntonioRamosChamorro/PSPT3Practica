package practica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;

public class Cliente extends JFrame implements WindowListener{

	private static final long serialVersionUID = 1L;
	Socket socket;
	DataInputStream ErrorEntrada;
	DataOutputStream ErrorSalida;
	String nombreJugador;
	static JTextField mensaje = new JTextField(); 
	private JScrollPane scrollpane; 
	static JTextArea textarea;
	JButton btnEnviar = new JButton("Enviar"); 
	boolean repetir = true;

	public Cliente(Socket socket, String nombreJugador)
	{ 
		
		super("Conexión Jugador: " + nombreJugador); 
		getContentPane().setBackground(Color.GRAY);
		addWindowListener(this);
		getContentPane().setLayout(null);
		mensaje.setBounds(10, 10, 400, 30);
		getContentPane().add(mensaje);
		textarea = new JTextArea();
		scrollpane = new JScrollPane(textarea);
		scrollpane.setBounds(10, 50, 400, 300); 
		getContentPane().add(scrollpane);
		btnEnviar.setBounds(420, 10, 100, 30);
		getContentPane().add(btnEnviar); 
		textarea.setEditable(false);
		this.getRootPane().setDefaultButton(btnEnviar);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.socket = socket; 
		this.nombreJugador = nombreJugador;

		btnEnviar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String texto = nombreJugador + "> " + mensaje.getText();
				try
				{
					mensaje.setText(""); 
					ErrorSalida.writeUTF(texto);
				} 
				catch (IOException ex)
				{ 
					ex.printStackTrace();
				}
				
			}
		});
		
		//Crear la entrada y salida de datos para indicar la entrada de un jugador al servidor
		try
		{
			ErrorEntrada = new DataInputStream(socket.getInputStream());
			ErrorSalida = new DataOutputStream(socket.getOutputStream());
			String texto =nombreJugador + " entra en el juego, por favor intruduzca un número.";
			ErrorSalida.writeUTF(texto);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static void main(String[] args) throws Exception 
	{
		int puerto = 44444; 
		String nombre = JOptionPane.showInputDialog("Introduzca su nick:"); 
		Socket socket = null;
		try 
		{
			socket = new Socket("127.0.0.1", puerto);
		}
		catch (IOException ex)
		{ 
			ex.printStackTrace(); 
			JOptionPane.showMessageDialog(null, "No se puede conectar con el servidor" + ex.getMessage(), "Error Conexión", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} 
		if(!nombre.trim().equals("")) 
		{
			Cliente client = new Cliente(socket, nombre);
			client.setBounds(0,0,540,400); 
			client.setVisible(true);
			client.ejecutar();
		} 
		else
		{ 
			System.out.println("Nombre en blanco");
		}
	}

	public void ejecutar() 
	{ 
		String texto = "";
		while(repetir) 
		{ 
			try
			{ 
				texto = ErrorEntrada.readUTF();
				textarea.setText(texto);
			}
			catch (IOException e) 
			{ 
				e.printStackTrace(); 
				JOptionPane.showMessageDialog(null, "No se puede conectar con el servidor" + e.getMessage(), "Error Conexión", JOptionPane.ERROR_MESSAGE);
				repetir = false;
			}
		}
		try
		{ 
			socket.close(); 
			System.exit(0);
		} 
		catch (IOException e)
		{ 
			e.printStackTrace();
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}}