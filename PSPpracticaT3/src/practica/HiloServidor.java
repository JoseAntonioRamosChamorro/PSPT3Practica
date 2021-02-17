package practica;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;



public class HiloServidor extends Thread
{
	DataInputStream entrada;
	Socket socket;

	public HiloServidor(Socket socket)
	{
		this.socket = socket;
		try 
		{
			entrada = new DataInputStream(socket.getInputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace(); 
		}
	}


	public synchronized void run()
	{
		Servidor.mensaje.setText("El número para adivinar es: " + Servidor.rnd);
		Servidor.mensaje1.setText(" " + Servidor.ACTUALES);
		String texto = Servidor.textarea.getText();
		EnviarMensajes(texto);

		while (true)
		{
			String tex = "";
			try
			{
				tex = entrada.readUTF();
				if(tex.trim().equals("*"))
				{
					Servidor.ACTUALES--;
					Servidor.mensaje.setText("El número para adivinar es: " + Servidor.rnd);
					Servidor.mensaje1.setText(" " + Servidor.ACTUALES);

					break;
				}

				//comprueba el número del jugador si es menor, igual o mayor
				else
				{
					if(tex.contains("."))
					{
						Servidor.textarea.append(tex + "\n"); 
					}
					else
					{
						String[] arrayJuego = tex.split("> ");
						String nombreJugador = arrayJuego[0];
						String numeroDadoJugador = arrayJuego[1];


						Thread.sleep(3000);
						if (Integer.parseInt(numeroDadoJugador) < Servidor.rnd)
						{
							Servidor.textarea.append(nombreJugador + " piensa que el número es el " + numeroDadoJugador + ", pero el número es mayor a " + numeroDadoJugador + ". \n");
						}
						else if (Integer.parseInt(numeroDadoJugador) > Servidor.rnd)
						{
							Servidor.textarea.append(nombreJugador + " piensa que el número es el " + numeroDadoJugador + ", pero el número es menor a " + numeroDadoJugador + ". \n");
						}
						else if (Integer.parseInt(numeroDadoJugador) == Servidor.rnd)
						{
							Servidor.textarea.append( nombreJugador + " piensa que el número es el " + numeroDadoJugador + ", y ha ACERTADOOOO!!!!. \n" + "\n Partida terminada");
							texto = Servidor.textarea.getText();
							EnviarMensajes(texto);
						}

					}
				}
				texto = Servidor.textarea.getText();
				EnviarMensajes(texto);
			}
			catch (Exception ex)
			{
				try
				{
					//cerrar la conexion
					entrada.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				ex.printStackTrace();
				break;
			}
		}
	}

	private void EnviarMensajes(String texto)
	{ 
		for(int i=0; i<Servidor.CONEXIONES; i++)
		{
			Socket socket = Servidor.tabla[i];
			try 
			{ 
				DataOutputStream fSalida = new DataOutputStream(socket.getOutputStream());
				fSalida.writeUTF(texto);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			} 
		}
	}	
}