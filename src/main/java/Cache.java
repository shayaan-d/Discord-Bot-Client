import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;

import java.util.*;

public final class Cache {

	private static HashMap<Server, List<ServerTextChannel>> cache = new HashMap<>();

	public static void cache(DiscordApi api) {
		Set<Server> servers = api.getServers();
		for (Server server : servers) {
			ArrayList<ServerTextChannel> channels = new ArrayList<>();
			cache.put(server, server.getTextChannels());
		}
	}

	public static List<ServerTextChannel> getChannels(Server server) {
		if (!cache.containsKey(server)) {
			throw new NoSuchElementException("Tried to access non-cached server. Most likely attempted to choose non-existent/non-joined server.");
		}
		return cache.get(server);
	}

	public static Set<Server> getServers() {
		return cache.keySet();
	}
}
