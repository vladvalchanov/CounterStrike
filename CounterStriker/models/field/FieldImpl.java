package CounterStriker.models.field;

import CounterStriker.common.OutputMessages;
import CounterStriker.models.players.CounterTerrorist;
import CounterStriker.models.players.Player;
import CounterStriker.models.players.Terrorist;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static CounterStriker.common.OutputMessages.COUNTER_TERRORIST_WINS;
import static CounterStriker.common.OutputMessages.TERRORIST_WINS;

public class FieldImpl implements Field {


    @Override
    public String start(Collection<Player> players) {
        List<Player> terrorists = players.stream()
                .filter(p -> p instanceof  Terrorist).collect(Collectors.toList());
        List<Player> counterTerrorists = players.stream()
                .filter(p -> p instanceof CounterTerrorist).collect(Collectors.toList());

        while (terrorists.stream().allMatch(Player::isAlive)
                && counterTerrorists.stream().allMatch(Player::isAlive) ){

            for (Player terrorist : terrorists) {
                for (Player counterTerrorist : counterTerrorists) {
                    counterTerrorist.takeDamage(terrorist.getGun().fire());
                }
            }
            counterTerrorists = counterTerrorists.stream().filter(Player::isAlive).collect(Collectors.toList());
            if (counterTerrorists.isEmpty()){
                return TERRORIST_WINS;
            }

            for (Player counterTerrorist : counterTerrorists) {
                for (Player terrorist : terrorists) {
                    terrorist.takeDamage(counterTerrorist.getGun().fire());
                }
            }
            terrorists = terrorists.stream().filter(Player::isAlive).collect(Collectors.toList());

            if (terrorists.isEmpty()){
                return COUNTER_TERRORIST_WINS;
            }

        }

            return "";


    }
}
