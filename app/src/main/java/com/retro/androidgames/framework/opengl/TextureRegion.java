package com.retro.androidgames.framework.opengl;

public class TextureRegion {
    public final float u1,v1;
    public final float u2,v2;
    public final Texture texture;

    public TextureRegion(Texture texture, float x, float y, float width, float height){

        //Конкретно атласные координаты
        this.u1 = x / texture.width;
        this.v1 = y / texture.height;
        this.u2 = this.u1 + width / texture.width;
        this.v2 = this.v1 + height / texture.height;
        this.texture = texture;
        //Вызов: TextureRegion cannonRegion = new TextureRegion(texture, 0, 0, 64, 32);
    }
}
