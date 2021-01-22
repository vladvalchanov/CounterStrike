package CounterStriker.repositories;


import CounterStriker.models.guns.Gun;
import CounterStriker.models.players.Player;

import java.util.ArrayList;
import java.util.Collection;

import static CounterStriker.common.ExceptionMessages.*;

public class PlayerRepository implements Repository<Player> {

    private ArrayList<Player> players;

    public PlayerRepository() {

        this.players = new ArrayList<>();
    }

    @Override
    public Collection<Player> getModels() {


        return this.players;
    }

    @Override
    public void add(Player model) {
        if (model == null){
            throw new NullPointerException(INVALID_PLAYER_REPOSITORY);
        }
        this.players.add(model);

    }

    @Override
    public boolean remove(Player model) {

        return this.getModels().remove(model);
    }

    @Override
    public Player findByName(String name) {
        return this.players.stream().filter(g -> g.getClass().getName().equals(name)).findFirst().orElse(null);
    }
}
