package me.tr.trInventory.helper.time;

public enum TimeUnit {
    TICK(0.05f),
    SECOND(1),
    MINUTE(60),
    HOUR(3600),
    DAY(86400),
    WEEK(604800),
    MONTH(2592000);

    private final float seconds;

    TimeUnit(float seconds) {
        this.seconds = seconds;
    }

    public float getSeconds() {
        return seconds;
    }

    public float toSeconds(float value) {
        return value * getSeconds();
    }

    public float fromSeconds(float seconds) {
        return seconds / getSeconds();
    }

    public float to(TimeUnit target, float value) {
        float inSeconds = value * getSeconds();
        return inSeconds / target.getSeconds();
    }

    public float convertTime(float value, TimeUnit from, TimeUnit to) {
        float inSeconds = from.toSeconds(value);
        return to.fromSeconds(inSeconds);
    }


}
