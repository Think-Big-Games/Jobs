package com.gamingmesh.jobs.listeners;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.actions.EntityActionInfo;
import com.gamingmesh.jobs.container.ActionType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEntityEvent;

public class JobsPayment16Listener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityBucketed(PlayerBucketEntityEvent event) {
        Player player = event.getPlayer();

        if (!Jobs.getGCManager().canPerformActionInWorld(player.getWorld())) {
            return;
        }

        // check if in creative
        if (!JobsPaymentListener.payIfCreative(player)) {
            return;
        }

        if (!Jobs.getPermissionHandler().hasWorldPermission(player, player.getLocation().getWorld().getName())) {
            return;
        }

        // check if player is riding
        if (Jobs.getGCManager().disablePaymentIfRiding && player.isInsideVehicle() && !player.getVehicle().getType().equals(EntityType.BOAT)) {
            return;
        }

        Jobs.action(Jobs.getPlayerManager().getJobsPlayer(player), new EntityActionInfo(event.getEntity(), ActionType.BUCKET));
    }
}
