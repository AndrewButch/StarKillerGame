package com.game.Objects;


import com.badlogic.gdx.utils.Pool;



public class ShootPool extends Pool<Shoot> {

    public ShootPool(int initialCapacity, int max) {
        super(initialCapacity, max);
    }

    @Override
    protected Shoot newObject() {
        return new Shoot();
    }


}
