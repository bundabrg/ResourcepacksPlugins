package de.themoep.BungeeResourcepacks.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Phoenix616 on 25.03.2015.
 */
public class PackManager {

    /**
     * packname -> ResourcePack
     */
    private Map<String, ResourcePack> packmap = new HashMap<String, ResourcePack>();

    /**
     * packhash -> packname 
     */
    private Map<String, String> hashmap = new HashMap<String, String>();
    
    /**
     * packurl -> packname 
     */
    private Map<String, String> urlmap = new HashMap<String, String>();

    /**
     * playerid -> packname 
     */
    private Map<UUID, String> usermap = new ConcurrentHashMap<UUID, String>();
    
    /**
     * servername -> packname 
     */
    private Map<String, String> servermap = new HashMap<String, String>();

    /**
     * Registeres a new resource pack with the packmanager
     * @param pack The resourcepack to register
     * @return If a pack with that name was known before it returns the past pack, null if none was known
     */
    public ResourcePack addPack(ResourcePack pack) {
        hashmap.put(pack.getHash(), pack.getName().toLowerCase());
        urlmap.put(pack.getUrl(), pack.getName().toLowerCase());
        return packmap.put(pack.getName().toLowerCase(), pack);
    }

    /**
     * Get the resourcepack by its name
     * @param name The name of the pack to get
     * @return The resourcepack with that name, null if there is none
     */
    public ResourcePack getByName(String name) {
        return packmap.get(name.toLowerCase());
    }
    
    /**
     * Get the resourcepack by its hash
     * @param hash The hash of the pack to get
     * @return The resourcepack with that hash, null if there is none
     */
    public ResourcePack getByHash(String hash) {
        String name = hashmap.get(hash);
        if(name != null) {
            return getByName(name);
        } else {
            return null;
        }
    }

    /**
     * Get the resourcepack by its url
     * @param url The url of the pack to get
     * @return The resourcepack with that url, null if there is none
     */
    public ResourcePack getByUrl(String url) {
        String name = urlmap.get(url);
        if(name != null) {
            return getByName(name);
        } else {
            return null;
        }
    }

    /**
     * Get the resourcepack of a server
     * @param server The name of the server, "!global" for the global pack
     * @return The resourcepack of the server, null if there is none
     */
    public ResourcePack getServerPack(String server) {
        String name = servermap.get(server);
        if(name != null) {
            return getByName(name);
        } else {
            return null;
        }
    }
    
    /**
     * Get the resourcepack of a user
     * @param playerid The UUID of this player
     * @return The resourcepack the player has selected, null if he has none/isn't known
     */
    public ResourcePack getUserPack(UUID playerid) {
        String name = usermap.get(playerid);
        if(name != null) {
            return getByName(name);
        } else {
            return null;
        }
    }
    
    /**
     * Set the resourcepack of a user
     * @param playerid The UUID of this player
     * @param pack The resourcepack of the user
     * @return The resourcepack the player had selected previous, null if he had none before
     */
    public ResourcePack setUserPack(UUID playerid, ResourcePack pack) {
        String previous = usermap.put(playerid, pack.getName());
        if(previous != null) {
            return getByName(previous);
        } else {
            return null;
        }
    }

    /**
     * Add a server to a resourcepack
     * @param server The server this pack should be active on
     * @param pack The resourcepack
     */
    public void addServer(String server, ResourcePack pack) {
        pack.addServer(server);
        servermap.put(server, pack.getName().toLowerCase());
    }
    
    /**
     * Add a server to a resourcepack
     * @param server The server this pack should be active on
     * @return True if the server had a pack, false if not
     */
    public boolean removeServer(String server) {
        String packname = servermap.remove(server);
        if(packname != null && packmap.containsKey(packname)) {
            return packmap.get(packname).removeServer(server);
        }
        return false;
    }

}
