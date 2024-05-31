public class Stats {
    private int wins;
    private int losses;

    public Stats() {
        wins = 0;
        losses = 0;
    }

    public void incrementWins() {
        wins++;
    }

    public void incrementLosses() {
        losses++;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }
}
