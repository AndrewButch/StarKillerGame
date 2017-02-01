package com.game;


import com.game.Objects.Enemy;

public class Level {
    public static final String TAG = Level.class.getSimpleName();
    public static final int ENEMIES_COUNT_MAX = 100;
    private int id;
    private int enemyCount;

    private Enemy[] enemies;

    public Level(int id) {
        this.id = id;
        this.enemyCount = id + 1;
        this.enemies = new Enemy[enemyCount];
        for (int i = 0; i < enemyCount; i++) {

        }
    }
}
