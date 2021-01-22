package CounterStriker.models.guns;

public class Rifle extends GunImpl {

    private static final int BULLETS_FIRE = 10;

    public Rifle(String name, int bulletsCount) {

        super(name, bulletsCount);
    }


    @Override
    public int fire() {
        int result = super.getBulletsCount() - BULLETS_FIRE;
        super.setBulletsCount(Math.max(result,0));
        return super.getBulletsCount() == 0 ? 0 : BULLETS_FIRE;
    }
}
