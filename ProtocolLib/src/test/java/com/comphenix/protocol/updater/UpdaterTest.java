/**
 * (c) 2016 dmulloy2
 */
package com.comphenix.protocol.updater;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.junit.BeforeClass;
import org.junit.Test;

import com.comphenix.protocol.updater.Updater.UpdateType;

/**
 * @author dmulloy2
 */

public class UpdaterTest {
	private static final int BUKKIT_DEV_ID = 45564;
	private static Plugin plugin;

	@BeforeClass
	public static void preparePlugin() {
		Server server = mock(Server.class);
		when(server.getUpdateFolder()).thenReturn(null);

		plugin = mock(Plugin.class);
		when(plugin.getDescription()).thenReturn(new PluginDescriptionFile("ProtocolLib", System.getProperty("projectVersion"), null));
		when(plugin.getLogger()).thenReturn(Logger.getLogger("ProtocolLib"));
		when(plugin.getDataFolder()).thenReturn(null);
		when(plugin.getServer()).thenReturn(server);
	}

	@Test
	public void testSpigotUpdater() {
		SpigotUpdater updater = new SpigotUpdater(plugin, UpdateType.NO_DOWNLOAD, true);

		String remote = null;

		try {
			remote = updater.getSpigotVersion();
		} catch (Throwable ex) {
			ex.printStackTrace();
			fail("Failed to check for updates");
		}

		System.out.println("Determined remote Spigot version: " + remote);

		updater.versionCheck(remote);
	}

	@Test
	public void testBukkitUpdater() {
		BukkitUpdater updater = new BukkitUpdater(plugin, BUKKIT_DEV_ID, null, UpdateType.NO_DOWNLOAD, true);
		if (! updater.read()) {
			fail("Failed to check for updates");
		}

		String remote = updater.getLatestName();
		System.out.println("Determined remote Bukkit Dev version: " + remote);

		updater.versionCheck(remote);		
	}
}