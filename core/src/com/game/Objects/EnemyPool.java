package com.game.Objects;

import com.badlogic.gdx.utils.Pool;



public class EnemyPool extends Pool<Enemy> {


    public EnemyPool(int initialCapacity) {
        super(initialCapacity);
    }

    public EnemyPool(int initialCapacity, int max) {
        super(initialCapacity, max);
    }

    @Override
    protected Enemy newObject() {
        return new Enemy();
    }

}
