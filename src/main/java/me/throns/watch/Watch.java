package me.throns.watch;

import jdk.nashorn.internal.ir.annotations.Ignore;
import me.throns.watch.framework.Cooldown;
import me.throns.watch.interfaces.Expire;
import me.throns.watch.util.Time;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

/**
 *  Base API class used to manage cooldowns for each
 *  given UUID, whether it is a player, entity, class
 *  identified with an UUID, it can hold one or
 *  multiple cooldowns simultaneously.
 *
 *  @author throns
 *  @website http://throns.me/
 *  @date 30/05/2020.
 */

public class Watch{

	/* instance of the Bukkit plugin */
	private JavaPlugin plugin;

	/* field to hold cooldowns of each UUID */
	private ConcurrentHashMap<UUID, CopyOnWriteArraySet<Cooldown>> cooldowns;

	/**
	 * Constructor to set the plugin instance
	 * @param plugin - JavaPlugin class
	 */
	public Watch(JavaPlugin plugin){
		cooldowns = new ConcurrentHashMap<>();
	}

	/**
	 * Create a labeled cooldown for a specific uuid
	 * with a specific amount of time.
	 * @param uuid - input uuid
	 * @param label - cooldown recognition label
	 * @param duration - cooldown duration
	 * @param unit - time unit used for the duration
	 */
	public void add(UUID uuid, String label, double duration, TimeUnit unit){
		add(uuid, new Cooldown(label, Time.convertTimeUnit(duration, unit, TimeUnit.SECONDS)), null);
	}

	/**
	 * Create a labeled cooldown for a specific uuid
	 * with a specific amount of time.
	 * Interface Expire executes a void on cooldown finish.
	 * Common usage: 'Your enderpearl cooldown is over', for example
	 * @param uuid - input uuid
	 * @param label - cooldown recognition label
	 * @param duration - cooldown duration
	 * @param unit - time unit used for the duration
	 */
	public void add(UUID uuid, String label, double duration, TimeUnit unit, Expire expire){
		add(uuid, new Cooldown(label, Time.convertTimeUnit(duration, unit, TimeUnit.SECONDS)), expire);
	}

	/**
	 * Create a labeled cooldown for a specific uuid
	 * with a specific amount of time.
	 * Interface Expire executes a void on cooldown finish.
	 * Common usage: 'Your enderpearl cooldown is over', for example
	 * @param uuid - input uuid
	 * @param cooldown - cooldown object provided by another constructor
	 * @param expire - interface executed when the cooldown finishes
	 */
	public void add(UUID uuid, Cooldown cooldown, Expire expire){
		CopyOnWriteArraySet<Cooldown> set = (Objects.isNull(cooldowns.get(uuid)) ? new CopyOnWriteArraySet<>() : cooldowns.get(uuid));
		set.add(cooldown);
		cooldowns.put(uuid, set);
		Bukkit.getScheduler().runTaskLater(plugin, () -> {
			remove(uuid, cooldown);
			if(Objects.nonNull(expire))
				expire.onExpire();
		}, (long) (cooldown.getDuration() * 1000L));
	}

	/**
	 * Remove all cooldowns with the param label
	 * attached in the specified uuid
	 * @param uuid - input uuid
	 * @param label - cooldown label
	 */
	public void remove(UUID uuid, String label){
		CopyOnWriteArraySet<Cooldown> cds = cooldowns.get(uuid);
		if(cds == null || cds.isEmpty()) return;
		cds.removeIf(cd -> cd.getLabel().equals(label));
	}

	/**
	 * Remove a cooldown in case the input is a Cooldown class object
	 * @param uuid - input uuid
	 * @param cooldown - cooldown object
	 */
	public void remove(UUID uuid, Cooldown cooldown){
		CopyOnWriteArraySet<Cooldown> cds = cooldowns.get(uuid);
		if(cds == null || cds.isEmpty()) return;
		cds.removeIf(cd -> cd.equals(cooldown));
	}

	/**
	 * Check if x uuid contains any cooldown with the label
	 * @param uuid - input uuid
	 * @param label - possible cooldown's label
	 * @return true if at least one cooldown with the specified
	 * label is found for the input uuid
	 */
	public boolean contains(UUID uuid, String label){
		CopyOnWriteArraySet<Cooldown> set = cooldowns.get(uuid);
		return Objects.nonNull(set) && !set.isEmpty() && set.stream().anyMatch(cooldown -> cooldown.getLabel().equals(label));
	}

	/**
	 * Return the first cooldown that matches the label
	 * @param uuid - input uuid
	 * @param label - input label
	 * @return cooldown object
	 */
	public Cooldown get(UUID uuid, String label){
		return cooldowns.get(uuid).stream().filter(cooldown -> cooldown.getLabel().equals(label)).findFirst().get();
	}

	/**
	 * Return a set of cooldowns attached to the specified uuid
	 * @param uuid - input uuid
	 * @return set of cooldowns
	 */
	public Set<Cooldown> getCooldowns(UUID uuid){
		return cooldowns.get(uuid);
	}
}
