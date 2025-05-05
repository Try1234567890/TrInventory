package me.tr.trInventory.helper.time;

import me.tr.trInventory.TrInventory;

public class TimeManager {
    private TrInventory main = TrInventory.getMain();

    public TimeUnit getTimeUnitFromString(String timeStr) {
        char[] chars = timeStr.toCharArray();
        for (char character : chars) {
            switch (character) {
                case 't':
                    return TimeUnit.TICK;
                case 's':
                    return TimeUnit.SECOND;
                case 'm':
                    return TimeUnit.MINUTE;
                case 'h':
                    return TimeUnit.HOUR;
                case 'd':
                    return TimeUnit.DAY;
                case 'w':
                    return TimeUnit.WEEK;
                case 'M':
                    return TimeUnit.MONTH;
            }
        }
        return TimeUnit.SECOND;
    }

    public long getTimeFromString(String timeStr) {
        StringBuilder builder = new StringBuilder();
        char[] chars = timeStr.toCharArray();
        for (char character : chars) {
            if (Character.isDigit(character) || character == '.' || character == ',') {
                builder.append(character == ',' ? '.' : character);
            }
        }
        try {
            return Long.parseLong(builder.toString());
        } catch (NumberFormatException e) {
            main.getTrLogger().error("Invalid number format in: " + timeStr);
            return 1;
        }
    }
}
