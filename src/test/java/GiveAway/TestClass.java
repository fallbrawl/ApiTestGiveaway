package GiveAway;

import java.util.Random;

/**
 * Created by paul on 07.04.16.
 */
public class TestClass {

    public Object getTypeOfField(String whatType) {
        switch (whatType) {

            case "name": {
                return fillName();

            }
            case "date": {
                return fillDate();

            }
            case "image": {
                return uploadImage();

            }
            case "city": {
                return fillCity();

            }
            case "house": {
                return fillHouse();

            }
            case "category": {
                return fillRandomCategory();

            }
            case "description": {
                return fillRandomDescription();

            }

            default:
                System.out.println("pop");
        }
        return null;
    }


    private Object fillRandomDescription() {

        String[] descriptions = {

                "Я помню чудное мгновенье:",
                "Передо мной явилась ты,",
                "Как мимолетное виденье,",
                "Как гений чистой красоты.",

                "В томленьях грусти безнадежной",
                "В тревогах шумной суеты,",
                "Звучал мне долго голос нежный",
                "И снились милые черты.",

                "Шли годы. Бурь порыв мятежный",
                "Рассеял прежние мечты,",
                "И я забыл твой голос нежный,",
                "Твои небесные черты."
        };

        String description = descriptions[new Random().nextInt(descriptions.length - 1)];
        System.out.println(description);

        return description;
    }

    private Object fillName() {

        String[] names = new String[21];

        names[0] = "РусскоеИмя";
        names[1] = ".";
        names[2] = "namewithnumber123";
        names[3] = "okname";
        names[4] = "111111";
        names[5] = "256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256sym";
        names[6] = "&*^*&(**)()&^%&^%&*&**&*&^*(*&";
        names[7] = "lol";
        names[8] = "noway";
        names[9] = "1";
        names[10] = "namewith65536symbols";
        names[11] = "àáâãäåçèéêëìíîðñòôõöö";
        names[12] = " ‘select * from customer";
        names[13] = " ";
        names[14] = "&nbsp;";
        names[15] = "                                                                                                   .";
        names[16] = ".                                                                                                   ";
        names[17] = "^M";
        names[18] = "\n\n\n\n\n\n";
        names[19] = "\"><script>alert()</script>";
        names[20] = "232 + 1";

        String name = names[new Random().nextInt(21)];
        System.out.println(name);

        return name;
    }

    private Object fillRandomCategory() {

        String[] categories = {

                "56f01ac6cb219685122cee4d",
                "56f01ac6cb219685122cee4f",
                "56f01ac6cb219685122cee51",
                "56f01ac6cb219685122cee52",
                "56f01ac6cb219685122cee53",
                "56f01ac6cb219685122cee54",
                "56f01ac6cb219685122cee55",
                "56f01ac6cb219685122cee56",
                "56f01ac6cb219685122cee57",
                "56f01ac6cb219685122cee58",
                "56f01ac6cb219685122cee4e",
                "56f01ac6cb219685122cee50"
        };

        String category = categories[new Random().nextInt(categories.length - 1)];
        System.out.println(category);

        return category;
    }

    private Object uploadImage() {

        return null;
    }

    private Object fillDate() {

        return null;
    }

    private Object fillCity() {
        String[] cities = new String[21];

        cities[0] = "Odessa";
        cities[1] = "Dubky";
        cities[2] = "Oman";
        cities[3] = "Bratsk";
        cities[4] = "Isfahan";
        cities[5] = "Casper";
        cities[6] = "Toronto";
        cities[7] = "Merida";
        cities[8] = "Guatemala";
        cities[9] = "El Paso";
        cities[10] = "Anchorage";

        String name = cities[new Random().nextInt(11)];
        System.out.println(name);
        return name;
    }

    private Object fillStreet() {
        String[] streets = new String[21];

        streets[0] = "street";
        streets[1] = ".";
        streets[2] = "namewithnumber123";
        streets[3] = "okname";
        streets[4] = "111111";
        streets[5] = "256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256symbols256sym";

        String name = streets[new Random().nextInt(21)];
        System.out.println(name);
        return name;
    }

    private Object fillHouse() {
        String[] houses = new String[21];

        houses[0] = "5";
        houses[1] = "6";
        houses[2] = "7";
        houses[3] = "8";
        houses[4] = "9";
        houses[5] = "10";

        String name = houses[new Random().nextInt(6)];
        System.out.println(name);
        return name;
    }
}
