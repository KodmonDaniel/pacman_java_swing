package pacman;

public interface GameListener {
    void death();
    void lastDeath(int score);
    void won(int score);
}
