package me.throns.watch;
/*
 *  created by throns on 22/05/2020
 */

import me.throns.watch.framework.Cooldown;
import me.throns.watch.interfaces.Expire;
import me.throns.watch.util.Time;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

public class Watch{

	private JavaPlugin plugin;
	private ConcurrentHashMap<UUID, CopyOnWriteArraySet<Cooldown>> cooldowns;

	public Watch(JavaPlugin plugin){
		cooldowns = new ConcurrentHashMap<>();
	}

	public void add(UUID uuid, String label, double duration, TimeUnit unit){
		add(uuid, new Cooldown(label, Time.convertTimeUnit(duration, unit, TimeUnit.SECONDS)), null);
	}

	public void add(UUID uuid, String label, double duration, TimeUnit unit, Expire expire){
		add(uuid, new Cooldown(label, Time.convertTimeUnit(duration, unit, TimeUnit.SECONDS)), expire);
	}

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

	public void remove(UUID uuid, String label){
		CopyOnWriteArraySet<Cooldown> cds = cooldowns.get(uuid);
		if(cds == null || cds.isEmpty()) return;
		cds.removeIf(cd -> cd.getLabel().equals(label));
	}

	public void remove(UUID uuid, Cooldown cooldown){
		CopyOnWriteArraySet<Cooldown> cds = cooldowns.get(uuid);
		if(cds == null || cds.isEmpty()) return;
		cds.removeIf(cd -> cd.equals(cooldown));
	}

	public boolean contains(UUID uuid, String label){
		CopyOnWriteArraySet<Cooldown> set = cooldowns.get(uuid);
		return Objects.nonNull(set) && !set.isEmpty() && set.stream().anyMatch(cooldown -> cooldown.getLabel().equals(label));
	}

	/**
	 *
	 * @param uuid
	 * @param label
	 * @return
	 */
	public Cooldown get(UUID uuid, String label){
		return cooldowns.get(uuid).stream().filter(cooldown -> cooldown.getLabel().equals(label)).findFirst().get();
	}
}
