package com.example.makeup.model;

import java.util.List;

public class Fodation {

    /**
     * success : 1
     * massage : Load data success
     * contents : [{"colorcode":"13","found_code":"108","name_found":"CaramelBeige","name_brand":"L'oreal","name_ver":"Infallibel","color":"black","avgtone":"101.0661"},{"colorcode":"7","found_code":"322","name_found":"WarmHoney","name_brand":"Maybelline","name_ver":"Fitme","color":"black","avgtone":"119.5245"},{"colorcode":"12","found_code":"107","name_found":"FreshBeige","name_brand":"L'oreal","name_ver":"Infallibel","color":"black","avgtone":"124.1606"},{"colorcode":"11","found_code":"105.5","name_found":"GoldenSand","name_brand":"L'oreal","name_ver":"Infallibel","color":"yellow","avgtone":"125.1024"},{"colorcode":"6","found_code":"310","name_found":"Sun","name_brand":"Maybelline","name_ver":"Fitme","color":"black","avgtone":"135.3333"},{"colorcode":"10","found_code":"104","name_found":"GoldenBeige","name_brand":"L'oreal","name_ver":"Infallibel","color":"yellow","avgtone":"148.2261"},{"colorcode":"9","found_code":"103","name_found":"NaturalBuff","name_brand":"L'oreal","name_ver":"Infallibel","color":"pink","avgtone":"156.8215"},{"colorcode":"8","found_code":"101.5","name_found":"NaturalIvory","name_brand":"L'oreal","name_ver":"Infallibel","color":"pink","avgtone":"156.8625"},{"colorcode":"3","found_code":"122","name_found":"Creamy","name_brand":"Maybelline","name_ver":"Fitme","color":"pink","avgtone":"159.0612"},{"colorcode":"4","found_code":"125","name_found":"Nude","name_brand":"Maybelline","name_ver":"Fitme","color":"pink","avgtone":"162"},{"colorcode":"5","found_code":"128","name_found":"Warm","name_brand":"Maybelline","name_ver":"Fitme","color":"yellow","avgtone":"177.1572"},{"colorcode":"2","found_code":"120","name_found":"Classic","name_brand":"Maybelline","name_ver":"Fitme","color":"yellow","avgtone":"188"}]
     */

    private int success;
    private String massage;
    private List<ContentsBean> contents;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public List<ContentsBean> getContents() {
        return contents;
    }

    public void setContents(List<ContentsBean> contents) {
        this.contents = contents;
    }

    public static class ContentsBean {
        /**
         * colorcode : 13
         * found_code : 108
         * name_found : CaramelBeige
         * name_brand : L'oreal
         * name_ver : Infallibel
         * color : black
         * avgtone : 101.0661
         */

        private String colorcode;

        public ContentsBean(String colorcode, String found_code, String name_found, String name_brand, String name_ver, String color, String avgtone) {
            this.colorcode = colorcode;
            this.found_code = found_code;
            this.name_found = name_found;
            this.name_brand = name_brand;
            this.name_ver = name_ver;
            this.color = color;
            this.avgtone = avgtone;
        }

        private String found_code;
        private String name_found;
        private String name_brand;
        private String name_ver;
        private String color;
        private String avgtone;

        public String getColorcode() {
            return colorcode;
        }

        public void setColorcode(String colorcode) {
            this.colorcode = colorcode;
        }

        public String getFound_code() {
            return found_code;
        }

        public void setFound_code(String found_code) {
            this.found_code = found_code;
        }

        public String getName_found() {
            return name_found;
        }

        public void setName_found(String name_found) {
            this.name_found = name_found;
        }

        public String getName_brand() {
            return name_brand;
        }

        public void setName_brand(String name_brand) {
            this.name_brand = name_brand;
        }

        public String getName_ver() {
            return name_ver;
        }

        public void setName_ver(String name_ver) {
            this.name_ver = name_ver;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getAvgtone() {
            return avgtone;
        }

        public void setAvgtone(String avgtone) {
            this.avgtone = avgtone;
        }
    }
}
