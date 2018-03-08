package models;

public class Presentation {
    Meta meta;
    Slide[] slides;


    public Presentation(Meta meta, Slide[] slides) {
        this.meta = meta;
        this.slides = slides;
    }

    public void addSlide(Slide slide, int i){
        this.slides[i] = slide;
    }
}
