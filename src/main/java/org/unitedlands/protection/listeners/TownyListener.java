package org.unitedlands.protection.listeners;

import com.palmergames.bukkit.towny.event.PlotClearEvent;
import com.palmergames.bukkit.towny.event.town.TownRuinedEvent;
import com.palmergames.bukkit.towny.event.town.TownUnclaimEvent;
import com.palmergames.bukkit.towny.object.TownBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.unitedlands.protection.UnitedProtection;
import org.unitedlands.protection.utils.Utils;

public class TownyListener implements Listener {

    @EventHandler
    public void onTownUnclaim(final TownUnclaimEvent event) {
        var worldCoord = event.getWorldCoord();
        var plugin = UnitedProtection.getPlugin();

        plugin.getServer().getGlobalRegionScheduler().run(plugin,
                task -> Utils.removeProtections(worldCoord)
        );
    }

    @EventHandler
    public void onPlotClear(final PlotClearEvent event) {
        var townBlock = event.getTownBlock();
        if (townBlock == null)
            return;

        var worldCoord = townBlock.getWorldCoord();
        var plugin = UnitedProtection.getPlugin();
        plugin.getServer().getGlobalRegionScheduler().run(plugin,
                task -> Utils.removeProtections(worldCoord)
        );
    }

    @EventHandler
    public void onTownRuin(final TownRuinedEvent event) {
        var plugin = UnitedProtection.getPlugin();
        var coords = event.getTown().getTownBlocks().stream()
                .map(TownBlock::getWorldCoord)
                .toList();

        plugin.getServer().getGlobalRegionScheduler().run(plugin,
                task -> coords.forEach(Utils::removeProtections)
        );
    }

}
