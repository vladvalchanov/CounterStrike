package CounterStriker.core;

import CounterStriker.models.field.Field;
import CounterStriker.models.field.FieldImpl;
import CounterStriker.models.guns.Gun;
import CounterStriker.models.guns.GunImpl;
import CounterStriker.models.guns.Pistol;
import CounterStriker.models.guns.Rifle;
import CounterStriker.models.players.CounterTerrorist;
import CounterStriker.models.players.Player;
import CounterStriker.models.players.Terrorist;
import CounterStriker.repositories.GunRepository;
import CounterStriker.repositories.PlayerRepository;
import CounterStriker.repositories.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static CounterStriker.common.ExceptionMessages.*;
import static CounterStriker.common.OutputMessages.*;

public class ControllerImpl implements Controller {

    private GunRepository guns;
    private PlayerRepository players ;
    private Field field;

    public ControllerImpl() {
        this.guns = new GunRepository();
        this.players = new PlayerRepository();
        this.field = new FieldImpl();

    }

    @Override
    public String addGun(String type, String name, int bulletsCount) {
        Gun gun;
        if (type.equals("Pistol")){
            gun = new Pistol(name,bulletsCount);
        }else if (type.equals("Rifle")){
            gun = new Rifle(name,bulletsCount);
        }else {
            throw new IllegalArgumentException(INVALID_GUN_TYPE);
        }

        this.guns.add(gun);

        return String.format(SUCCESSFULLY_ADDED_GUN,name);
    }

    @Override
    public String addPlayer(String type, String username, int health, int armor, String gunName) {
        if (this.guns.findByName(gunName) == null){
            throw new NullPointerException(GUN_CANNOT_BE_FOUND);
        }
        Gun gun = this.guns.findByName(gunName);

        switch (type){
            case "Terrorist":
                this.players.add(new Terrorist(username,health,armor,gun));
                break;
            case "CounterTerrorist":
                this.players.add(new CounterTerrorist(username,health,armor,gun));
                break;
                default: throw new IllegalArgumentException(INVALID_PLAYER_TYPE);
        }

        return String.format(SUCCESSFULLY_ADDED_PLAYER,username);
    }



    @Override
    public String startGame()
    {
        return this.field.start(this.players.getModels());
    }



    @Override
    public String report() {
        List<Player> ct = this.players.getModels().stream().filter(p -> p instanceof CounterTerrorist)
                .collect(Collectors.toCollection(ArrayList::new));

        List<Player> terrorist = this.players.getModels().stream().filter(p -> p instanceof Terrorist)
                .collect(Collectors.toCollection(ArrayList::new));


        ct = ct.stream().sorted(Comparator.comparingInt(Player::getHealth).reversed()
                .thenComparing(Player::getUsername)).collect(Collectors.toCollection(ArrayList::new));
        terrorist = terrorist.stream().sorted(Comparator.comparingInt(Player::getHealth).reversed()
                .thenComparing(Player::getUsername)).collect(Collectors.toCollection(ArrayList::new));

        StringBuilder sb = new StringBuilder();
        for (Player model : ct) {
            sb.append(model).append(System.lineSeparator());
        }
        for (Player model : terrorist) {
            sb.append(model).append(System.lineSeparator());
        }


        return sb.toString().trim();
    }

}
