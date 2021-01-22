package CounterStriker.models.guns;

public class Pistol extends GunImpl {

    private static final int BULLETS_FIRE = 1;

    public Pistol(String name, int bulletsCount) {
        super(name, bulletsCount);
    }

    @Override
    public int fire() {
        int result = super.getBulletsCount() - BULLETS_FIRE;
        super.setBulletsCount( Math.max(result,0));
        return super.getBulletsCount() == 0 ? 0 : BULLETS_FIRE;
    }
}
