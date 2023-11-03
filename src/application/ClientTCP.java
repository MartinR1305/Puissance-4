package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingUtilities;

import controller.Game.ChoicePlayerPvPController;
import controller.Game.GameControllerPvPOnlineController;

public class ClientTCP implements AutoCloseable {

	private Socket clientSocket;
	private PrintWriter writer;
	private BufferedReader reader;

	private ChoicePlayerPvPController connectController;
	private GameControllerPvPOnlineController gameController;

	private boolean isConnectedToServer;
	private boolean is2ndClientConnected;
	private boolean isClientOpened;
	private boolean isReconnectedToServerInProgress;
	private boolean isPlayerStarts;

	private int port;
	private String IP;
	private int numClient;

	/**
	 * Default Constructor for a ClientTCP
	 * 
	 * @param IP
	 * @param port
	 * @param clientController
	 * @throws IOException
	 */
	public ClientTCP(String IP, int port, ChoicePlayerPvPController connectController, GameControllerPvPOnlineController gameController) throws IOException {
		this.isConnectedToServer = false;
		this.is2ndClientConnected = false;
		this.isClientOpened = true;
		this.isReconnectedToServerInProgress = false;
		this.isPlayerStarts = false;

		this.numClient = -1;

		this.port = port;
		this.IP = IP;
		this.connectController = connectController;
		this.gameController = gameController;
	}

	/**
	 * Getter for the client's number
	 * 
	 * @return
	 */
	public int getNumClient() {
		return this.numClient;
	}

	/**
	 * Getter for the client's IP
	 * 
	 * @return
	 */
	public String getIP() {
		return this.IP;
	}

	/**
	 * Getter for the client's IP
	 * 
	 * @return
	 */
	public int getPort() {
		return this.port;
	}
	
	/**
	 * Getter for the writer
	 * 
	 * @return
	 */
	public PrintWriter getWriter() {
		return writer;
	}

	/**
	 * Setter for the boolean isClientOpened
	 * 
	 * @param isClientOpened
	 */
	public void setClientOpened(boolean isClientOpened) {
		this.isClientOpened = isClientOpened;
	}

	/**
	 * Method of launching the connection with the server
	 * 
	 * @throws IOException
	 */
	public void connectToServer() throws IOException {
		try {
			// For close reconnect () when program is closing
			if (isClientOpened) {

				// Initialization of communication
				this.clientSocket = new Socket(IP, port);

				// Actualization of data
				isConnectedToServer = true;
				System.out.println("Connected to the server !");
				connectController.actualizeState("Connected");
				
				// Creation of writer and reader
				this.writer = new PrintWriter(this.clientSocket.getOutputStream(), true);
				this.reader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

				// Get Message of server 
				while (isConnectedToServer && isClientOpened) {
					receiveMessage();
				}
			}
		} catch (IOException IOE) {
			// If the server is not open
			if (this.clientSocket == null) {
				if (isClientOpened) {
					System.err.println("Server not open !");
					
					// We  try a reconnection
					reconnect();
				}
			} else {
				IOE.printStackTrace();
			}
		}
	}

	/**
	 * Use to retry a connection with the server
	 */
	private void reconnect() {
		// if the port is being modified
		if (isReconnectedToServerInProgress) {
			return;
		}

		// Start the Thread to take a break
		Thread waitThread = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(5000);
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							if (!isConnectedToServer || isClientOpened) {
								try {
									// Retry the connection
									connectToServer(); 
								} catch (IOException IOE) {
									IOE.printStackTrace();
								}
							}
						}
					});
				} catch (InterruptedException IE) {
					IE.printStackTrace();
				}
			}
		});

		// Starting the Thread
		waitThread.start();
	}

	/**
	 * Method use to close socket, Reader and Writer
	 */
	public void disconnect() {
		try {
			if (this.clientSocket != null) {
				// Reader
				if (this.reader != null) {
					this.reader.close();
				}

				// Writer
				if (this.writer != null) {
					this.writer.close();
				}

				System.out.println("Socket Closed");
				this.clientSocket.close();
				connectController.actualizeState("Disconnected");
			}

			// If the server is close before the Scoring Machine
			this.clientSocket = null;

			isConnectedToServer = false;

		} catch (IOException IOE) {
			System.err.println("Error disconnecting from the server: " + IOE.getMessage());
		}
	}

	/**
	 * Method the receive Messages from the Server
	 */
	public void receiveMessage() {
		String finalMessage = "";
		try {
			// Read Message
			finalMessage = this.reader.readLine();

			// If the connection have been stopped
			if (finalMessage.equals("STOP")) {
				System.out.println("Connection to the server lost !");

				// Update status in FXML page
				connectController.actualizeState("Disconnected");
				gameController.actualize2PlayersBoolean(false);

				// Closure of socket / in / out
				disconnect();

				// If the program are stopping -> don't retry the connection
				if (isClientOpened) {
					reconnect();
				}

			}
			
			// We are looking if the message is type of "N + integer"
			Pattern pattern = Pattern.compile("N(\\d+)");
			Matcher matcher = pattern.matcher(finalMessage);
			
			// Message for client's number assignment
			if (matcher.find()) {
				String integerPart = matcher.group(1);
				numClient = Integer.parseInt(integerPart) - 1;
				System.out.println("The player has the number : " + numClient);
			}
			
			// Message that says two players are connected to the server
			if (finalMessage.equals("2 Players Connected")) {
				is2ndClientConnected = true;
				connectController.actualize2PlayersBoolean(is2ndClientConnected);
			}
			
			// Message that says two players are connected to the server
			if (finalMessage.equals("Other Player Left")) {
				is2ndClientConnected = false;
				gameController.actualize2PlayersBoolean(is2ndClientConnected);
			}
			
			// Message that says the player will start the game
			if (finalMessage.equals("You Start")) {
				isPlayerStarts = true;
				gameController.actualizePlayerStarting(isPlayerStarts);
			}
			
			// Message that says the player will not start the game
			if (finalMessage.equals("Opponent Will Start")) {
				isPlayerStarts = false;
				gameController.actualizePlayerStarting(isPlayerStarts);
			}
			

		} catch (IOException IOE) {
			IOE.printStackTrace();
			disconnect();
		}
	}

	/**
	 * Using to change Port / IP of the connection to Server
	 * 
	 * @param IP   : IP for connect to the server
	 * @param port : Port for connect to the server
	 */
	public void changeIP_Port(String IP, String Port) {

		System.out.println("Connection to : " + IP + " - " + Port);

		// Actualization of attributes
		this.IP = IP;
		this.port = Integer.parseInt(Port);
		
		isReconnectedToServerInProgress = true;

		// Stop A potential Thread
		if (isConnectedToServer) {
			this.writer.println("STOP");
		}

		// Update status in FXML page -> potential new server
		connectController.actualizeState("Disconnected");

		// Reconnect with new informations
		reconnect();

		isReconnectedToServerInProgress = false;
	}

	/**
	 * Method that allows to close the client
	 */
	@Override
	public void close() throws Exception {
		isClientOpened = false;

		if (writer != null) {
			writer.println("STOP");
		}

		System.out.println("Client closed");
	}
}
