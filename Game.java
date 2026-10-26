public class Game {
    private Player player;
    private Map playerMap;
    private Monster monster;
    private Map monsterMap;
    private int gameId;


    public Game(Player player, Map playerMap, Monster monster, Map monsterMap, Map masterMap, int gameId) {
        this.player = player;
        this.playerMap = playerMap;
        this.monster = monster;
        this.monsterMap = monsterMap;
        this.masterMap = masterMap;
        this.gameId = gameId;
    }


 // GETTERS
    // Get Player
    public Player getPlayer() {
        return player;
    }

    // Get Player Map
    public Map getPlayerMap() {
        return playerMap;
    }

    // Get Monster
    public Monster getMonster() {
        return monster;
    }

    // Get Monster Map
    public Map getMonsterMap() {
        return monsterMap;
    }

    // Get Master Map
    public Map getMasterMap() {
        return masterMap;
    }

    // Get Game Id
    public int getGameId() {
        return gameId;
    }



 // SETTERS
    // Set Player
    public void setPlayer(Player player) {
        this.player = player;
    }

    // Set Player Map
    public void setPlayerMap(Map playerMap) {
        this.playerMap = playerMap;
    }

    // Set Monster
    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    // Set Monster Map
    public void setMonsterMap(Map monsterMap) {
        this.monsterMap = monsterMap;
    }

    // Set Master Map
    public void setMasterMap(Map masterMap) {
        this.masterMap = masterMap;
    }

    // Set Game Id
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
