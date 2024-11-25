import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class Main {

	public static Color blurple = new Color(0x5865F2);
	private static JFrame frame;
	private static String token;
	private static DiscordApi api;

	public static void main(String[] args) {
		System.setProperty("apple.awt.application.name", "Discord Bot Client");
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("token.txt"));
		} catch (FileNotFoundException e) {
			throw new TokenNotFoundException(e);
		}
		token = scanner.nextLine();

		api = new DiscordApiBuilder()
				.setToken(token)
				.setAllNonPrivilegedIntents()
				.login().join();
		Cache.cache(api);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Class<Main> mainClass = Main.class;


				// create form
				frame = new JFrame();
				frame.setLocationRelativeTo(null);
				frame.add(new MainPanel());
				frame.setTitle("Discord Bot Client");
				frame.setSize(800, 450);
				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

				Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
				URL imageResource = mainClass.getClassLoader().getResource("electron.png");
				Image image = defaultToolkit.getImage(imageResource);


				// Code from https://stackoverflow.com/questions/6006173/how-do-you-change-the-dock-icon-of-a-java-program
				Taskbar taskbar = Taskbar.getTaskbar();
				try {
					//set icon for macOS (and other systems which do support this method)
					taskbar.setIconImage(image);
				} catch (UnsupportedOperationException e) {
					System.out.println("The os does not support: 'taskbar.setIconImage'");
				} catch (SecurityException e) {
					System.out.println("There was a security exception for: 'taskbar.setIconImage'");
				}

				//set icon for Windows os (and other systems which do support this method)
				frame.setIconImage(image);


				frame.setVisible(true);
			}
		});
	}

	public static Server getServerFromName(String name) {
		Set<Server> servers = api.getServersByName(name);
		if (servers.size() > 1) {
			String input = JOptionPane.showInputDialog(frame,
					"Enter Server ID:",
					"Duplicate Servers",
					JOptionPane.WARNING_MESSAGE);
			Optional<Server> server = api.getServerById(input);
			while (server.isEmpty()) {
				input = JOptionPane.showInputDialog(frame,
						"Invalid ID. Enter Server ID",
						"Invalid ID",
						JOptionPane.ERROR_MESSAGE);
				server = api.getServerById(input);
			}
			return server.get();
		} else if (servers.isEmpty()) {
			throw new RuntimeException("Server Not Found. Please Reload");
		}
		return servers.toArray(new Server[0])[0];
	}

	public static ServerTextChannel getChannelFromName(Server server, String name) {
		List<ServerTextChannel> channels = server.getTextChannelsByName(name);
		if (channels.size() > 1) {
			String input = JOptionPane.showInputDialog(frame,
					"Enter Channel ID:",
					"Invalid ID.",
					JOptionPane.WARNING_MESSAGE);
			Optional<ServerTextChannel> channel = server.getTextChannelById(input);
			while (channel.isEmpty()) {
				input = JOptionPane.showInputDialog(frame,
						"Invalid ID. Enter Channel ID",
						"Invalid ID.",
						JOptionPane.ERROR_MESSAGE);
				channel = server.getTextChannelById(input);
			}
		} else if (channels.isEmpty()) {
			throw new RuntimeException("Server Not Found. Please Reload");
		}
		return channels.toArray(new ServerTextChannel[0])[0];
	}

	public static JFrame getFrame() {
		return frame;
	}
}
