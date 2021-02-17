package practica;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException; 
import java.net.ServerSocket;
import java.net.Socket; 
import java.net.SocketException;
import javax.swing.JFrame;
import javax.swing.JScrollPane; 
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;



public class Servidor extends JFrame implements  WindowListener
{ 
	private static final long serialVersionUID = -3754989941364640731L;
	static ServerSocket servidor;
	static final int puerto = 44444;
	static int CONEXIONES = 0;
	static int ACTUALES = 0;
	static int MAXIMO = 15;
	static int rnd;
	static JTextField mensaje = new JTextField("");
	static JTextField mensaje1 = new JTextField("");
	static JTextField mensaje2 = new JTextField("");
	private JScrollPane scrollpane1;
	static JTextArea textarea;
	JLabel LbJuga = new JLabel("Jugadores");
	static Socket[] tabla = new Socket[100];

	public Servidor() 
	{
		super("Servidor");
		setBackground(Color.BLUE);
		addWindowListener(this);
		this.getContentPane().setBackground(Color.GRAY);
		getContentPane().setLayout(null);
		mensaje.setBounds(10, 10, 217, 30);
		getContentPane().add(mensaje);
		mensaje.setEditable(false);
		mensaje1.setBounds(308, 15, 31, 20);
		getContentPane().add(mensaje1);
		mensaje1.setEditable(false);
		mensaje2.setBounds(10, 375, 354, 30);
		getContentPane().add(mensaje2);
		mensaje2.setEditable(false); 
		textarea = new JTextArea();
		scrollpane1 = new JScrollPane(textarea);
		scrollpane1.setBounds(10, 51, 459, 313);
		getContentPane().add(scrollpane1);
		textarea.setEditable(false);
		LbJuga.setBounds(250, 18, 69, 14);
		getContentPane().add(LbJuga);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	public static void main(String args[]) throws Exception
	{ 
		servidor = new ServerSocket(puerto);
		rnd = (int) Math.floor(Math.random() * 100 + 1);
		System.out.println("Servidor iniciado");
		Servidor pantalla = new Servidor();
		pantalla.setBounds(0, 0, 540, 450);
		pantalla.setVisible(true);
		mensaje.setText("El número para adivinar es: " + rnd);
		mensaje1.setText("");

		while(CONEXIONES < MAXIMO) 
		{ 
			Socket socket;
			try 
			{ 
				socket = servidor.accept();
			}
			catch (SocketException sex)
			{
				break;
			} 
			tabla[CONEXIONES] = socket; 
			CONEXIONES++; 
			ACTUALES++;
			HiloServidor hilo = new HiloServidor(socket);
			hilo.start(); 
		}
		if(!servidor.isClosed()) 
		{ 
			try
			{ 
				mensaje2.setForeground(Color.red);
				mensaje2.setText("Máximo Nº de conexiones establecidas: " + CONEXIONES);
				servidor.close();
			}
			catch (IOException ex) 
			{ 
				ex.printStackTrace();
			} 
		}
		else
		{ 
			System.out.println("Servidor finalizado...");
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

	}		
}