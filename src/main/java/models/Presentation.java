package models;

public class Presentation {
    Meta meta;
    Slide[] slides;
    GPS gps;


    Presentation(Meta meta, Slide[] slides, GPS gps) {
        this.meta = meta;
        this.slides = slides;
        this.gps = gps;
    }

    public Presentation()
    {
        meta = new Meta();
        slides = new Slide[0];
        gps = new GPS();
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Slide[] getSlides() {
        return slides;
    }

    public void setSlides(Slide[] slides) {
        this.slides = slides;
    }

    public GPS getGps() {
        return gps;
    }

    public void setGps(GPS gps) {
        this.gps = gps;
    }

    public void addSlide(Slide slide, int i){
        this.slides[i] = slide;
    }

//    void printPresentation(Presentation pres){
//
//        System.out.println("META -  key: " + pres.meta.key + " value: " + pres.meta.value);
//        System.out.println("GPS - el: " + pres.gps.elevation + " la: " + pres.gps.latitude + " lo: " + pres.gps.longitude);
//
//
//        for(int i = 0; i < slides.length; i++)
//        {
//            System.out.println("slide number: " + i
//                    + " slide duration: " + pres.slides[i].transitions.duration);
//
//            //------------------------------text--------------------------------//
//            System.out.println("------------------------------text--------------------------------");
//            System.out.println("textFont colour: " + pres.slides[i].text.colour
//                    + " size: " + pres.slides[i].text.font.textSize
//                    + " underline: " + pres.slides[i].text.font.underline
//                    + " bold: " + pres.slides[i].text.font.bold
//                    + " italic " + pres.slides[i].text.font.italic
//                    + " font " + pres.slides[i].text.font.font);
//            System.out.println("text content: " + pres.slides[i].text.content);
//
//            //-----------------------------image--------------------------------//
//            System.out.println("-----------------------------image--------------------------------");
//            System.out.println("image path: " + pres.slides[i].image.path);
//            System.out.println("image Tx: " + pres.slides[i].image.position.xTopLeft
//                    + " Ty: " + pres.slides[i].image.position.yTopLeft
//                    + " Bx: " + pres.slides[i].image.position.xBottomRight
//                    + " By: " + pres.slides[i].image.position.yBottomRight);
//
//            //-----------------------------video--------------------------------//
//            System.out.println("-----------------------------video--------------------------------");
//            System.out.println("video path: " + pres.slides[i].video.path);
//            System.out.println(" video Tx " + pres.slides[i].video.position.xTopLeft
//                    + " Ty: " + pres.slides[i].video.position.yTopLeft
//                    + " Bx: " + pres.slides[i].video.position.xBottomRight
//                    + " By: " + pres.slides[i].video.position.yBottomRight);
//
//            //-----------------------------shape--------------------------------//
//            System.out.println("-----------------------------shape--------------------------------");
//            System.out.println("shape: " + pres.slides[i].shape.shape
//                    + " colour fill: " + pres.slides[i].shape.colour.fill
//                    + " colour: " + pres.slides[i].shape.colour.colour
//                    + " stroke: " + pres.slides[i].shape.stroke);
//            System.out.println("shape Tx: " + pres.slides[i].shape.position.xTopLeft
//                    + " Ty: " + pres.slides[i].shape.position.yTopLeft
//                    + " Bx: " + pres.slides[i].shape.position.xBottomRight
//                    + " By: " + pres.slides[i].shape.position.yBottomRight);
//
//            //-----------------------------audio--------------------------------//
//            System.out.println("-----------------------------audio--------------------------------");
//            System.out.println("audio path: " + pres.slides[i].audio.path);
//            System.out.println("audio Tx: " + pres.slides[i].audio.position.xTopLeft
//                    + " Ty: " + pres.slides[i].audio.position.yTopLeft
//                    + " Bx: " + pres.slides[i].audio.position.xBottomRight
//                    + " By: " + pres.slides[i].audio.position.yBottomRight);
//
//
//
//        }
//
//    }
}


