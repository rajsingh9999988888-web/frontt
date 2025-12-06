package com.babyadoption.controller;

import com.babyadoption.model.BabyPost;
import com.babyadoption.model.BabyPost.PostStatus;
import io.jsonwebtoken.Jwts;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/babies")
public class BabyController {

        private static final List<String> states = Arrays.asList(
                        "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat",
                        "Haryana", "Himachal Pradesh", "Jharkhand", "Karnataka", "Kerala", "Madhya Pradesh",
                        "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab",
                        "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh",
                        "Uttarakhand", "West Bengal", "Jammu&Kashmir");

        private static final Map<String, List<String>> districts = new HashMap<>();
        private static final Map<String, List<String>> cities = new HashMap<>();
        private static final List<BabyPost> babyPosts = new ArrayList<>();

        static {
                initializeDistrictsAndCities();

                // Sample baby posts with state, district, city info
                babyPosts.add(new BabyPost(1, "Baby 1", "Lovely baby girl 1", "1111111111", "1111111111",
                                "https://i.postimg.cc/KzN4zW1m/pexels-pixabay-459957.jpg", "Andhra Pradesh",
                                "Anantapur", "Anantapur",
                                "Call Girls", "Address 1", "123456", 25, "Nick1", "Title1", "Text1", "Indian", "Indian",
                                "Slim",
                                "Service1", "Place1", LocalDateTime.now().minusDays(5), PostStatus.APPROVED, 1));
                babyPosts.add(new BabyPost(2, "Baby 2", "Lovely baby girl 2", "2222222222", "2222222222",
                                "https://i.postimg.cc/KzN4zW1m/pexels-pixabay-459957.jpg", "Andhra Pradesh", "Chittoor",
                                "Tirupati",
                                "Other", "Address 2", "654321", 30, "Nick2", "Title2", "Text2", "Other", "Other",
                                "Medium", "Service2",
                                "Place2", LocalDateTime.now().minusDays(10), PostStatus.EXPIRED, 2));
                // Add more baby posts as needed
        }

        private static void initializeDistrictsAndCities() {
                initializeStates1_7(); // Andhra Pradesh through Gujarat
                initializeStates8_14(); // Haryana through Maharashtra
                initializeStates15_21(); // Manipur through Punjab
                initializeStates22_28(); // Rajasthan through West Bengal
                initializeRemainingStates(); // Manipur, Meghalaya, Mizoram, Nagaland, Odisha, Sikkim, Tripura, Uttarakhand, Jammu & Kashmir
        }

        private static void initializeStates1_7() {
                // Andhra Pradesh
                districts.put("Andhra Pradesh", Arrays.asList("Anantapur", "Chittoor", "East Godavari", "Guntur", 
                                "Krishna", "Kurnool", "Prakasam", "Srikakulam", "Visakhapatnam", "Vizianagaram", 
                                "West Godavari", "YSR Kadapa", "Nellore", "Sri Potti Sriramulu Nellore"));
                
                cities.put("Anantapur", Arrays.asList("Anantapur", "Dharmavaram", "Hindupur", "Kadiri", "Penukonda", 
                                "Gooty", "Tadipatri", "Rayadurg", "Guntakal", "Kalyandurg", "Uravakonda", "Beluguppa", 
                                "Madakasira", "Kambadur", "Singanamala"));
                cities.put("Chittoor", Arrays.asList("Chittoor", "Tirupati", "Puttur", "Madanapalle", "Palamaner", 
                                "Chandragiri", "Srikalahasti", "Nagari", "Punganur", "Kuppam", "Bangarupalem", 
                                "Pichatur", "Gudipala", "Yerpedu", "Satyavedu"));
                cities.put("East Godavari", Arrays.asList("Rajahmundry", "Kakinada", "Amalapuram", "Tuni", "Peddapuram", 
                                "Samalkot", "Ramachandrapuram", "Mandapeta", "Razole", "Rampachodavaram", "Yeleswaram", 
                                "Anaparthy", "Gollaprolu", "Pithapuram", "Tallarevu"));
                cities.put("Guntur", Arrays.asList("Guntur", "Tenali", "Narasaraopet", "Chilakaluripet", "Bapatla", 
                                "Repalle", "Ponnur", "Mangalagiri", "Tadepalle", "Sattenapalle", "Vinukonda", 
                                "Dachepalle", "Piduguralla", "Macherla", "Veldurthy"));
                cities.put("Krishna", Arrays.asList("Vijayawada", "Machilipatnam", "Gudivada", "Nuzvid", "Jaggayyapeta", 
                                "Tiruvuru", "Nandigama", "Mylavaram", "Gannavaram", "Ibrahimpatnam", "Kanuru", 
                                "Penamaluru", "Avanigadda", "Movva", "Ghantasala"));
                cities.put("Kurnool", Arrays.asList("Kurnool", "Nandyal", "Adoni", "Yemmiganur", "Dhone", "Atmakur", 
                                "Banaganapalle", "Koilkuntla", "Allagadda", "Srisailam", "Mantralayam", "Pattikonda", 
                                "Kodumur", "Gudur", "Panyam"));
                cities.put("Prakasam", Arrays.asList("Ongole", "Chirala", "Markapur", "Kandukur", "Addanki", "Darsi", 
                                "Podili", "Kanigiri", "Giddalur", "Yerragondapalem", "Martur", "Pamur", "Cumbum", 
                                "Dornala", "Tarlupadu"));
                cities.put("Srikakulam", Arrays.asList("Srikakulam", "Tekkali", "Palasa", "Amadalavalasa", "Ichchapuram", 
                                "Narasannapeta", "Pathapatnam", "Sompeta", "Palakonda", "Rajam", "Gara", "Kotabommali", 
                                "Hiramandalam", "Meliaputti", "Vajrapukothuru"));
                cities.put("Visakhapatnam", Arrays.asList("Visakhapatnam", "Anakapalle", "Narsipatnam", "Chodavaram", 
                                "Paderu", "Araku Valley", "Chintapalle", "Gajuwaka", "Bheemunipatnam", "Yelamanchili", 
                                "Nakkapalle", "Madugula", "Payakaraopet", "Rambilli", "S Rayavaram"));
                cities.put("Vizianagaram", Arrays.asList("Vizianagaram", "Bobbili", "Parvathipuram", "Salur", "Gajapathinagaram", 
                                "Cheepurupalli", "Gantyada", "Gurla", "Jami", "Kothavalasa", "Lakkavarapukota", "Nellimarla", 
                                "Pusapatirega", "Santhakaviti", "Srungavarapukota"));
                cities.put("West Godavari", Arrays.asList("Eluru", "Bhimavaram", "Tadepalligudem", "Tanuku", "Nidadavole", 
                                "Jangareddygudem", "Kovvur", "Narasapur", "Palakol", "Attili", "Akividu", "Dendulur", 
                                "Dwaraka Tirumala", "Kaikalur", "Kamavarapukota"));
                cities.put("YSR Kadapa", Arrays.asList("Kadapa", "Proddatur", "Rajampet", "Jammalamadugu", "Pulivendula", 
                                "Rayachoti", "Mydukur", "Kamalapuram", "Badvel", "Lakkireddipalle", "Siddavatam", 
                                "Chinnamandem", "Galiveedu", "Khajipet", "Vempalle"));
                cities.put("Nellore", Arrays.asList("Nellore", "Gudur", "Kavali", "Sullurpeta", "Atmakur", "Venkatagiri", 
                                "Udayagiri", "Rapur", "Podalakur", "Tada", "Dakkili", "Kodavalur", "Muthukur", "Sangam", 
                                "Varikuntapadu"));
                cities.put("Sri Potti Sriramulu Nellore", Arrays.asList("Nellore", "Gudur", "Kavali", "Sullurpeta", 
                                "Atmakur", "Venkatagiri", "Udayagiri", "Rapur", "Podalakur", "Tada", "Dakkili", 
                                "Kodavalur", "Muthukur", "Sangam", "Varikuntapadu"));

                // Arunachal Pradesh
                districts.put("Arunachal Pradesh", Arrays.asList("Tawang", "West Kameng", "East Kameng", "Papum Pare", 
                                "Kurung Kumey", "Kra Daadi", "Lower Subansiri", "Upper Subansiri", "West Siang", 
                                "East Siang", "Siang", "Upper Siang", "Lower Siang", "Lower Dibang Valley", 
                                "Dibang Valley", "Anjaw", "Lohit", "Namsai", "Changlang", "Tirap", "Longding"));
                
                cities.put("Tawang", Arrays.asList("Tawang", "Lumla", "Jang", "Mukto", "Bongkhar", "Kitpi", "Zemithang", 
                                "Mago", "Thingbu", "Mechuka", "Dirang", "Bomdila", "Kalaktang", "Rupa", "Shergaon"));
                cities.put("West Kameng", Arrays.asList("Bomdila", "Dirang", "Kalaktang", "Rupa", "Shergaon", "Thongri", 
                                "Thembang", "Jamiri", "Singchung", "Nafra", "Balemu", "Bhalukpong", "Seijosa", "Pakke Kessang", 
                                "Lekang"));
                cities.put("East Kameng", Arrays.asList("Seppa", "Chayangtajo", "Bameng", "Lada", "Sawa", "Richukrong", 
                                "Pipu", "Khenewa", "Pijerang", "Pachin", "Pakke Kessang", "Lekang", "Bhalukpong", "Seijosa", 
                                "Balemu"));
                cities.put("Papum Pare", Arrays.asList("Itanagar", "Naharlagun", "Doimukh", "Yupia", "Kimin", "Sagalee", 
                                "Ziro", "Daporijo", "Raga", "Palin", "Koloriang", "Nyapin", "Sangram", "Kamporijo", "Tali"));
                cities.put("Kurung Kumey", Arrays.asList("Koloriang", "Nyapin", "Sangram", "Kamporijo", "Tali", "Damin", 
                                "Parsi Parlo", "Longding Koling", "Palin", "Yachuli", "Raga", "Daporijo", "Ziro", "Yazali", 
                                "Pistana"));
                cities.put("Kra Daadi", Arrays.asList("Palin", "Yachuli", "Raga", "Daporijo", "Ziro", "Yazali", "Pistana", 
                                "Tamen", "Longding Koling", "Parsi Parlo", "Damin", "Tali", "Kamporijo", "Sangram", 
                                "Nyapin"));
                cities.put("Lower Subansiri", Arrays.asList("Ziro", "Yazali", "Pistana", "Tamen", "Raga", "Daporijo", 
                                "Palin", "Yachuli", "Koloriang", "Nyapin", "Sangram", "Kamporijo", "Tali", "Damin", 
                                "Parsi Parlo"));
                cities.put("Upper Subansiri", Arrays.asList("Daporijo", "Raga", "Palin", "Yachuli", "Koloriang", "Nyapin", 
                                "Sangram", "Kamporijo", "Tali", "Damin", "Parsi Parlo", "Longding Koling", "Tamen", 
                                "Pistana", "Yazali"));
                cities.put("West Siang", Arrays.asList("Aalo", "Along", "Basar", "Daring", "Kaying", "Likabali", "Mechuka", 
                                "Monigong", "Payum", "Pangin", "Tato", "Yomcha", "Gensi", "Nacho", "Tuting"));
                cities.put("East Siang", Arrays.asList("Pasighat", "Mebo", "Nari", "Ruksin", "Sille", "Yingkiong", 
                                "Jengging", "Koyu", "Pangin", "Boleng", "Riga", "Seren", "Kebang", "Migging", "Pangin"));
                cities.put("Siang", Arrays.asList("Boleng", "Riga", "Seren", "Kebang", "Migging", "Pangin", "Yingkiong", 
                                "Jengging", "Koyu", "Nari", "Ruksin", "Sille", "Mebo", "Pasighat", "Pangin"));
                cities.put("Upper Siang", Arrays.asList("Yingkiong", "Jengging", "Koyu", "Pangin", "Boleng", "Riga", 
                                "Seren", "Kebang", "Migging", "Tuting", "Gensi", "Nacho", "Tato", "Yomcha", "Pangin"));
                cities.put("Lower Siang", Arrays.asList("Likabali", "Basar", "Along", "Aalo", "Kaying", "Daring", 
                                "Mechuka", "Monigong", "Payum", "Pangin", "Tato", "Yomcha", "Gensi", "Nacho", "Tuting"));
                cities.put("Lower Dibang Valley", Arrays.asList("Roing", "Dambuk", "Koronu", "Hunli", "Desali", "Anini", 
                                "Etalin", "Mipi", "Anelih", "Koronu", "Dambuk", "Hunli", "Desali", "Anini", "Etalin"));
                cities.put("Dibang Valley", Arrays.asList("Anini", "Etalin", "Mipi", "Anelih", "Koronu", "Dambuk", 
                                "Hunli", "Desali", "Roing", "Dambuk", "Koronu", "Hunli", "Desali", "Anini", "Etalin"));
                cities.put("Anjaw", Arrays.asList("Hayuliang", "Chaglagam", "Kibithoo", "Walong", "Chowkham", "Namsai", 
                                "Lekang", "Chongkham", "Piyong", "Namsai", "Lathao", "Mahadevpur", "Namsai", "Chongkham", 
                                "Lekang"));
                cities.put("Lohit", Arrays.asList("Tezu", "Namsai", "Chongkham", "Lekang", "Hayuliang", "Chaglagam", 
                                "Kibithoo", "Walong", "Chowkham", "Piyong", "Lathao", "Mahadevpur", "Namsai", "Chongkham", 
                                "Lekang"));
                cities.put("Namsai", Arrays.asList("Namsai", "Chongkham", "Lekang", "Piyong", "Lathao", "Mahadevpur", 
                                "Tezu", "Hayuliang", "Chaglagam", "Kibithoo", "Walong", "Chowkham", "Namsai", "Chongkham", 
                                "Lekang"));
                cities.put("Changlang", Arrays.asList("Changlang", "Khonsa", "Longding", "Nampong", "Jairampur", "Miao", 
                                "Vijoynagar", "Nampong", "Jairampur", "Miao", "Vijoynagar", "Khonsa", "Longding", 
                                "Changlang", "Nampong"));
                cities.put("Tirap", Arrays.asList("Khonsa", "Longding", "Nampong", "Jairampur", "Miao", "Vijoynagar", 
                                "Changlang", "Nampong", "Jairampur", "Miao", "Vijoynagar", "Khonsa", "Longding", 
                                "Changlang", "Nampong"));
                cities.put("Longding", Arrays.asList("Longding", "Nampong", "Jairampur", "Miao", "Vijoynagar", "Khonsa", 
                                "Changlang", "Nampong", "Jairampur", "Miao", "Vijoynagar", "Khonsa", "Longding", 
                                "Changlang", "Nampong"));

                // Assam
                districts.put("Assam", Arrays.asList("Baksa", "Barpeta", "Biswanath", "Bongaigaon", "Cachar", "Charaideo", 
                                "Chirang", "Darrang", "Dhemaji", "Dhubri", "Dibrugarh", "Dima Hasao", "Goalpara", 
                                "Golaghat", "Hailakandi", "Hojai", "Jorhat", "Kamrup", "Kamrup Metropolitan", "Karbi Anglong", 
                                "Karimganj", "Kokrajhar", "Lakhimpur", "Majuli", "Morigaon", "Nagaon", "Nalbari", 
                                "Sivasagar", "Sonitpur", "South Salmara-Mankachar", "Tinsukia", "Udalguri", "West Karbi Anglong"));
                
                cities.put("Baksa", Arrays.asList("Mushalpur", "Barpeta Road", "Tamulpur", "Rangia", "Nalbari", "Baihata", 
                                "Chamaria", "Bongaigaon", "Bijni", "Abhayapuri", "Howly", "Pathsala", "Sarupeta", 
                                "Goreswar", "Tihu"));
                cities.put("Barpeta", Arrays.asList("Barpeta", "Howly", "Pathsala", "Sarupeta", "Bajali", "Baghbar", 
                                "Chenga", "Jalah", "Kalitakuchi", "Mandia", "Rupahi", "Sarthebari", "Bahari", "Bamunbari", 
                                "Barpeta Road"));
                cities.put("Biswanath", Arrays.asList("Biswanath Chariali", "Gohpur", "Helem", "Behali", "Sootea", "Rangapara", 
                                "Dhekiajuli", "Tezpur", "Bihaguri", "Balipara", "Darrang", "Mangaldai", "Kalaigaon", 
                                "Udalguri", "Mazbat"));
                cities.put("Bongaigaon", Arrays.asList("Bongaigaon", "Abhayapuri", "Bijni", "Sidli", "Chirang", "Kajalgaon", 
                                "Basugaon", "Kokrajhar", "Gossaigaon", "Fakiragram", "Dhubri", "Bilasipara", "Gauripur", 
                                "Goalpara", "Lakhipur"));
                cities.put("Cachar", Arrays.asList("Silchar", "Lakhipur", "Katigorah", "Sonai", "Udharbond", "Borkhola", 
                                "Dholai", "Lakhipur", "Katigorah", "Sonai", "Udharbond", "Borkhola", "Dholai", "Silchar", 
                                "Lakhipur"));
                cities.put("Charaideo", Arrays.asList("Sonari", "Moran", "Naharkatiya", "Duliajan", "Digboi", "Tinsukia", 
                                "Doomdooma", "Sadiya", "Margherita", "Ledo", "Makum", "Dibrugarh", "Namrup", "Duliajan", 
                                "Naharkatiya"));
                cities.put("Chirang", Arrays.asList("Kajalgaon", "Basugaon", "Bijni", "Sidli", "Bongaigaon", "Abhayapuri", 
                                "Kokrajhar", "Gossaigaon", "Fakiragram", "Dhubri", "Bilasipara", "Gauripur", "Goalpara", 
                                "Lakhipur", "Bongaigaon"));
                cities.put("Darrang", Arrays.asList("Mangaldai", "Kalaigaon", "Udalguri", "Mazbat", "Dhekiajuli", "Tezpur", 
                                "Bihaguri", "Balipara", "Rangapara", "Sootea", "Helem", "Behali", "Gohpur", "Biswanath Chariali", 
                                "Mangaldai"));
                cities.put("Dhemaji", Arrays.asList("Dhemaji", "Jonai", "Sissiborgaon", "Gogamukh", "Machkhowa", "Silapathar", 
                                "North Lakhimpur", "Bihpuria", "Naoboicha", "Lakhimpur", "Dhakuakhana", "Majuli", "Jorhat", 
                                "Titabar", "Mariani"));
                cities.put("Dhubri", Arrays.asList("Dhubri", "Bilasipara", "Gauripur", "Goalpara", "Lakhipur", "Bongaigaon", 
                                "Abhayapuri", "Bijni", "Sidli", "Chirang", "Kajalgaon", "Basugaon", "Kokrajhar", "Gossaigaon", 
                                "Fakiragram"));
                cities.put("Dibrugarh", Arrays.asList("Dibrugarh", "Namrup", "Duliajan", "Naharkatiya", "Moran", "Sonari", 
                                "Chabua", "Tengakhat", "Lahoal", "Tingkhong", "Sapekhati", "Khowang", "Barbaruah", "Panitola", 
                                "Tinsukia"));
                cities.put("Dima Hasao", Arrays.asList("Haflong", "Maibong", "Umrangso", "Mahur", "Diungbra", "Harangajao", 
                                "Jatinga", "Laisong", "Langting", "Lanka", "Maibong", "Mahur", "Umrangso", "Haflong", 
                                "Diungbra"));
                cities.put("Goalpara", Arrays.asList("Goalpara", "Lakhipur", "Bongaigaon", "Abhayapuri", "Bijni", "Sidli", 
                                "Chirang", "Kajalgaon", "Basugaon", "Kokrajhar", "Gossaigaon", "Fakiragram", "Dhubri", 
                                "Bilasipara", "Gauripur"));
                cities.put("Golaghat", Arrays.asList("Golaghat", "Dergaon", "Morongi", "Numaligarh", "Sarupathar", "Bokakhat", 
                                "Khumtai", "Doyang", "Merapani", "Furkating", "Jorhat", "Titabar", "Mariani", "Teok", 
                                "Titabar"));
                cities.put("Hailakandi", Arrays.asList("Hailakandi", "Katlicherra", "Lala", "Algapur", "Katlicherra", "Lala", 
                                "Algapur", "Hailakandi", "Katlicherra", "Lala", "Algapur", "Hailakandi", "Katlicherra", 
                                "Lala", "Algapur"));
                cities.put("Hojai", Arrays.asList("Hojai", "Lanka", "Doboka", "Kampur", "Jamunamukh", "Lumding", "Lanka", 
                                "Doboka", "Kampur", "Jamunamukh", "Lumding", "Hojai", "Lanka", "Doboka", "Kampur"));
                cities.put("Jorhat", Arrays.asList("Jorhat", "Titabar", "Mariani", "Teok", "Titabar", "Mariani", "Teok", 
                                "Jorhat", "Titabar", "Mariani", "Teok", "Titabar", "Mariani", "Teok", "Jorhat"));
                cities.put("Kamrup", Arrays.asList("Rangia", "Baihata", "Chamaria", "Bongaigaon", "Bijni", "Abhayapuri", 
                                "Howly", "Pathsala", "Sarupeta", "Goreswar", "Tihu", "Nalbari", "Mushalpur", "Tamulpur", 
                                "Barpeta Road"));
                cities.put("Kamrup Metropolitan", Arrays.asList("Guwahati", "Dispur", "Azara", "Sonapur", "Chandrapur", 
                                "Beltola", "Bamunimaidan", "Jalukbari", "Pandu", "Maligaon", "Paltan Bazaar", "Fancy Bazaar", 
                                "Uzan Bazaar", "Lachit Nagar", "Bharalumukh"));
                cities.put("Karbi Anglong", Arrays.asList("Diphu", "Bokajan", "Howraghat", "Donkamokam", "Manja", "Phuloni", 
                                "Silonijan", "Langhin", "Rongmongwe", "Hamren", "Baithalangso", "Doboka", "Lanka", "Kampur", 
                                "Jamunamukh"));
                cities.put("Karimganj", Arrays.asList("Karimganj", "Badarpur", "Nilambazar", "Patharkandi", "Ramkrishna Nagar", 
                                "Ratabari", "Kaliganj", "Bilasipara", "Gauripur", "Goalpara", "Lakhipur", "Bongaigaon", 
                                "Abhayapuri", "Bijni", "Sidli"));
                cities.put("Kokrajhar", Arrays.asList("Kokrajhar", "Gossaigaon", "Fakiragram", "Dhubri", "Bilasipara", 
                                "Gauripur", "Goalpara", "Lakhipur", "Bongaigaon", "Abhayapuri", "Bijni", "Sidli", "Chirang", 
                                "Kajalgaon", "Basugaon"));
                cities.put("Lakhimpur", Arrays.asList("North Lakhimpur", "Bihpuria", "Naoboicha", "Lakhimpur", "Dhakuakhana", 
                                "Majuli", "Jorhat", "Titabar", "Mariani", "Teok", "Titabar", "Mariani", "Teok", "Jorhat", 
                                "Titabar"));
                cities.put("Majuli", Arrays.asList("Majuli", "Jorhat", "Titabar", "Mariani", "Teok", "Titabar", "Mariani", 
                                "Teok", "Jorhat", "Titabar", "Mariani", "Teok", "Titabar", "Mariani", "Teok"));
                cities.put("Morigaon", Arrays.asList("Morigaon", "Jagiroad", "Laharighat", "Bhuragaon", "Mayang", "Kapili", 
                                "Lumding", "Hojai", "Lanka", "Doboka", "Kampur", "Jamunamukh", "Lumding", "Hojai", "Lanka"));
                cities.put("Nagaon", Arrays.asList("Nagaon", "Kampur", "Jamunamukh", "Lumding", "Hojai", "Lanka", "Doboka", 
                                "Kampur", "Jamunamukh", "Lumding", "Hojai", "Lanka", "Doboka", "Kampur", "Jamunamukh"));
                cities.put("Nalbari", Arrays.asList("Nalbari", "Mushalpur", "Tamulpur", "Barpeta Road", "Rangia", "Baihata", 
                                "Chamaria", "Bongaigaon", "Bijni", "Abhayapuri", "Howly", "Pathsala", "Sarupeta", "Goreswar", 
                                "Tihu"));
                cities.put("Sivasagar", Arrays.asList("Sivasagar", "Nazira", "Amguri", "Sonari", "Moran", "Naharkatiya", 
                                "Duliajan", "Digboi", "Tinsukia", "Doomdooma", "Sadiya", "Margherita", "Ledo", "Makum", 
                                "Dibrugarh"));
                cities.put("Sonitpur", Arrays.asList("Tezpur", "Bihaguri", "Balipara", "Rangapara", "Sootea", "Helem", 
                                "Behali", "Gohpur", "Biswanath Chariali", "Mangaldai", "Kalaigaon", "Udalguri", "Mazbat", 
                                "Dhekiajuli", "Tezpur"));
                cities.put("South Salmara-Mankachar", Arrays.asList("Hatsingimari", "Mankachar", "Lakhipur", "Bongaigaon", 
                                "Abhayapuri", "Bijni", "Sidli", "Chirang", "Kajalgaon", "Basugaon", "Kokrajhar", "Gossaigaon", 
                                "Fakiragram", "Dhubri", "Bilasipara"));
                cities.put("Tinsukia", Arrays.asList("Tinsukia", "Doomdooma", "Sadiya", "Margherita", "Ledo", "Makum", 
                                "Dibrugarh", "Namrup", "Duliajan", "Naharkatiya", "Moran", "Sonari", "Chabua", "Tengakhat", 
                                "Lahoal"));
                cities.put("Udalguri", Arrays.asList("Udalguri", "Mazbat", "Dhekiajuli", "Tezpur", "Bihaguri", "Balipara", 
                                "Rangapara", "Sootea", "Helem", "Behali", "Gohpur", "Biswanath Chariali", "Mangaldai", 
                                "Kalaigaon", "Udalguri"));
                cities.put("West Karbi Anglong", Arrays.asList("Hamren", "Baithalangso", "Doboka", "Lanka", "Kampur", 
                                "Jamunamukh", "Lumding", "Hojai", "Lanka", "Doboka", "Kampur", "Jamunamukh", "Lumding", 
                                "Hojai", "Lanka"));

                // Bihar
                districts.put("Bihar", Arrays.asList("Araria", "Arwal", "Aurangabad", "Banka", "Begusarai", "Bhagalpur", 
                                "Bhojpur", "Buxar", "Darbhanga", "East Champaran", "Gaya", "Gopalganj", "Jamui", "Jehanabad", 
                                "Kaimur", "Katihar", "Khagaria", "Kishanganj", "Lakhisarai", "Madhepura", "Madhubani", 
                                "Munger", "Muzaffarpur", "Nalanda", "Nawada", "Patna", "Purnia", "Rohtas", "Saharsa", 
                                "Samastipur", "Saran", "Sheikhpura", "Sheohar", "Sitamarhi", "Siwan", "Supaul", "Vaishali", 
                                "West Champaran"));
                
                // Adding cities for key Bihar districts (sample - can expand all)
                cities.put("Patna", Arrays.asList("Patna", "Danapur", "Phulwari Sharif", "Bihta", "Fatuha", "Maner", 
                                "Masaurhi", "Naubatpur", "Paliganj", "Bikram", "Dulhin Bazar", "Hilsa", "Jehanabad", 
                                "Biharsharif", "Nalanda"));
                cities.put("Gaya", Arrays.asList("Gaya", "Bodh Gaya", "Tekari", "Guraru", "Belaganj", "Wazirganj", "Fatehpur", 
                                "Sherghati", "Imamganj", "Dumaria", "Banke Bazar", "Mohanpur", "Konch", "Tikari", "Barachatti"));
                cities.put("Bhagalpur", Arrays.asList("Bhagalpur", "Kahalgaon", "Naugachhia", "Colgong", "Banka", "Amarpur", 
                                "Sultanganj", "Jagdishpur", "Shahkund", "Goradih", "Kharik", "Nathnagar", "Sabour", "Pirpainti", 
                                "Bihpur"));
                cities.put("Muzaffarpur", Arrays.asList("Muzaffarpur", "Motihari", "Bettiah", "Bagaha", "Narkatiaganj", 
                                "Raxaul", "Sugauli", "Chakia", "Dhaka", "Pipra", "Sitamarhi", "Belsand", "Dumra", "Bairgania", 
                                "Majorganj"));
                cities.put("Darbhanga", Arrays.asList("Darbhanga", "Madhubani", "Jhanjharpur", "Benipatti", "Phulparas", 
                                "Laukahi", "Babubarhi", "Rajnagar", "Jale", "Khutauna", "Biraul", "Keoti", "Singhwara", 
                                "Tardih", "Baheri"));
                cities.put("Purnia", Arrays.asList("Purnia", "Katihar", "Kishanganj", "Araria", "Forbesganj", "Bhaptiyahi", 
                                "Dalkola", "Barsoi", "Korha", "Manihari", "Amdabad", "Bahadurganj", "Thakurganj", "Kochadhaman", 
                                "Raniganj"));
                cities.put("Saran", Arrays.asList("Chhapra", "Siwan", "Gopalganj", "Barauli", "Darauli", "Goriakothi", 
                                "Mashrakh", "Raghunathpur", "Revelganj", "Marhaura", "Parsa", "Dighwara", "Ekma", "Jalalpur", 
                                "Manjhi"));
                cities.put("Rohtas", Arrays.asList("Sasaram", "Bikramganj", "Dehri", "Dalmianagar", "Nokha", "Kochas", 
                                "Nasriganj", "Dawath", "Chenari", "Nauhatta", "Sheosagar", "Tilouthu", "Sanjhauli", "Akorhi", 
                                "Dariyapur"));
                cities.put("Nalanda", Arrays.asList("Biharsharif", "Rajgir", "Hilsa", "Islampur", "Harnaut", "Chandi", 
                                "Asthawan", "Rahui", "Sarmera", "Karai Parsurai", "Nagar Nausa", "Ekangarsarai", "Silao", 
                                "Giriyak", "Bind"));
                cities.put("Vaishali", Arrays.asList("Hajipur", "Lalganj", "Mahnar", "Raghopur", "Jandaha", "Patepur", 
                                "Desari", "Sahdei Buzurg", "Mahnar Bazar", "Bidupur", "Vaishali", "Chehra Kalan", "Bhagwanpur", 
                                "Goraul", "Mahuwa"));

                // Chhattisgarh
                districts.put("Chhattisgarh", Arrays.asList("Balod", "Baloda Bazar", "Balrampur", "Bastar", "Bemetara", 
                                "Bijapur", "Bilaspur", "Dantewada", "Dhamtari", "Durg", "Gariaband", "Gaurela-Pendra-Marwahi", 
                                "Janjgir-Champa", "Jashpur", "Kabeerdham", "Kanker", "Kondagaon", "Korba", "Koriya", "Mahasamund", 
                                "Mungeli", "Narayanpur", "Raigarh", "Raipur", "Rajnandgaon", "Sukma", "Surajpur", "Surguja"));
                
                cities.put("Raipur", Arrays.asList("Raipur", "Bhatapara", "Baloda Bazar", "Tilda", "Abhanpur", "Arang", 
                                "Mandir Hasod", "Pallari", "Simga", "Bilaigarh", "Kasdol", "Bindranawagarh", "Chhura", "Gariaband", 
                                "Deobhog"));
                cities.put("Bilaspur", Arrays.asList("Bilaspur", "Korba", "Champa", "Janjgir", "Akaltara", "Mungeli", 
                                "Takhatpur", "Lormi", "Pendra", "Marwahi", "Gaurela", "Pandariya", "Kota", "Masturi", "Bilha"));
                cities.put("Durg", Arrays.asList("Durg", "Bhilai", "Rajnandgaon", "Dongargarh", "Khairgarh", "Dongargaon", 
                                "Pandaria", "Mohla", "Ambagarh", "Chhuriya", "Khairagarh", "Chhuikhadan", "Gandai", "Pandaria", 
                                "Dongargaon"));
                cities.put("Raigarh", Arrays.asList("Raigarh", "Gharghoda", "Lailunga", "Sarangarh", "Tamnar", "Dharamjaigarh", 
                                "Pusour", "Baramkela", "Kotra", "Gharghoda", "Lailunga", "Sarangarh", "Tamnar", "Dharamjaigarh", 
                                "Pusour"));
                cities.put("Korba", Arrays.asList("Korba", "Katghora", "Pali", "Kartala", "Pondi", "Dipka", "Chirimiri", 
                                "Manendragarh", "Khadgawa", "Bishrampur", "Surajpur", "Premnagar", "Baikunthpur", "Ramanujganj", 
                                "Samri"));
                cities.put("Jagdalpur", Arrays.asList("Jagdalpur", "Kondagaon", "Narayanpur", "Dantewada", "Bijapur", 
                                "Sukma", "Kanker", "Antagarh", "Bhanupratappur", "Narharpur", "Charama", "Bhopalpatnam", 
                                "Konta", "Chitrakoot", "Darbha"));
                cities.put("Ambikapur", Arrays.asList("Ambikapur", "Surajpur", "Premnagar", "Baikunthpur", "Ramanujganj", 
                                "Samri", "Lakhanpur", "Pratappur", "Sitapur", "Odgi", "Bhaiyathan", "Ramanujnagar", "Wadrafnagar", 
                                "Kusmi", "Shankargarh"));
                cities.put("Mahasamund", Arrays.asList("Mahasamund", "Saraipali", "Basna", "Bagbahra", "Pithora", "Saraipali", 
                                "Basna", "Bagbahra", "Pithora", "Saraipali", "Basna", "Bagbahra", "Pithora", "Saraipali", 
                                "Basna"));
                cities.put("Dhamtari", Arrays.asList("Dhamtari", "Kurud", "Nagri", "Magarlod", "Sihawa", "Kurud", "Nagri", 
                                "Magarlod", "Sihawa", "Kurud", "Nagri", "Magarlod", "Sihawa", "Kurud", "Nagri"));
                cities.put("Kanker", Arrays.asList("Kanker", "Antagarh", "Bhanupratappur", "Narharpur", "Charama", "Bhopalpatnam", 
                                "Konta", "Chitrakoot", "Darbha", "Jagdalpur", "Kondagaon", "Narayanpur", "Dantewada", "Bijapur", 
                                "Sukma"));

                // Goa
                districts.put("Goa", Arrays.asList("North Goa", "South Goa"));
                
                cities.put("North Goa", Arrays.asList("Panaji", "Mapusa", "Ponda", "Margao", "Vasco da Gama", "Calangute", 
                                "Baga", "Anjuna", "Candolim", "Sinquerim", "Arambol", "Morjim", "Ashwem", "Mandrem", "Siolim"));
                cities.put("South Goa", Arrays.asList("Margao", "Vasco da Gama", "Ponda", "Canacona", "Quepem", "Sanguem", 
                                "Curchorem", "Sanvordem", "Chaudi", "Agonda", "Palolem", "Colva", "Benaulim", "Varca", "Cavelossim"));

                // Gujarat
                districts.put("Gujarat", Arrays.asList("Ahmedabad", "Amreli", "Anand", "Aravalli", "Banaskantha", "Bharuch", 
                                "Bhavnagar", "Botad", "Chhota Udaipur", "Dahod", "Dang", "Devbhoomi Dwarka", "Gandhinagar", 
                                "Gir Somnath", "Jamnagar", "Junagadh", "Kachchh", "Kheda", "Mahisagar", "Mehsana", "Morbi", 
                                "Narmada", "Navsari", "Panchmahal", "Patan", "Porbandar", "Rajkot", "Sabarkantha", "Surat", 
                                "Surendranagar", "Tapi", "Vadodara", "Valsad"));
                
                cities.put("Ahmedabad", Arrays.asList("Ahmedabad", "Gandhinagar", "Sanand", "Dholka", "Viramgam", "Bavla", 
                                "Daskroi", "Detroj", "Dhandhuka", "Mandal", "Ranpur", "Dholera", "Bopal", "Sarkhej", "Naroda"));
                cities.put("Surat", Arrays.asList("Surat", "Navsari", "Bardoli", "Vyara", "Songadh", "Uchhal", "Nizar", 
                                "Valod", "Mahuva", "Palsana", "Kamrej", "Olpad", "Choryasi", "Mangrol", "Umarpada"));
                cities.put("Vadodara", Arrays.asList("Vadodara", "Anand", "Bharuch", "Nadiad", "Petlad", "Borsad", "Sojitra", 
                                "Umreth", "Khambhat", "Tarapur", "Kathlal", "Kapadvanj", "Balasinor", "Lunawada", "Santrampur"));
                cities.put("Rajkot", Arrays.asList("Rajkot", "Jamnagar", "Gondal", "Jetpur", "Upleta", "Dhoraji", "Wankaner", 
                                "Morbi", "Tankara", "Lodhika", "Jasdan", "Kotda Sangani", "Vinchhiya", "Paddhari", "Vinchhiya"));
                cities.put("Bhavnagar", Arrays.asList("Bhavnagar", "Palitana", "Mahuva", "Talaja", "Gariadhar", "Vallabhipur", 
                                "Ghogha", "Sihor", "Umrala", "Botad", "Gadhada", "Lathi", "Jesar", "Gariadhar", "Vallabhipur"));
                cities.put("Junagadh", Arrays.asList("Junagadh", "Porbandar", "Veraval", "Keshod", "Mangrol", "Sutrapada", 
                                "Kodinar", "Una", "Visavadar", "Manavadar", "Bhesan", "Mendarda", "Vanthali", "Talala", "Dhoraji"));
                cities.put("Gandhinagar", Arrays.asList("Gandhinagar", "Kalol", "Mansa", "Dehgam", "Dabhoda", "Sanand", 
                                "Bavla", "Daskroi", "Detroj", "Dhandhuka", "Mandal", "Ranpur", "Dholera", "Bopal", "Sarkhej"));
                cities.put("Mehsana", Arrays.asList("Mehsana", "Patan", "Unjha", "Visnagar", "Kadi", "Vijapur", "Kheralu", 
                                "Becharaji", "Vadnagar", "Sidhpur", "Chanasma", "Radhanpur", "Santalpur", "Tharad", "Dhanera"));
                cities.put("Anand", Arrays.asList("Anand", "Nadiad", "Petlad", "Borsad", "Sojitra", "Umreth", "Khambhat", 
                                "Tarapur", "Kathlal", "Kapadvanj", "Balasinor", "Lunawada", "Santrampur", "Vadodara", "Bharuch"));
                cities.put("Bharuch", Arrays.asList("Bharuch", "Ankleshwar", "Jambusar", "Amod", "Jhagadia", "Valia", "Hansot", 
                                "Dahej", "Vagra", "Kosamba", "Panoli", "Sachin", "Kadodara", "Surat", "Navsari"));
        }

        private static void initializeStates8_14() {
                // Haryana
                districts.put("Haryana", Arrays.asList("Ambala", "Bhiwani", "Charkhi Dadri", "Faridabad", "Fatehabad", 
                                "Gurugram", "Hisar", "Jhajjar", "Jind", "Kaithal", "Karnal", "Kurukshetra", "Mahendragarh", 
                                "Nuh", "Palwal", "Panchkula", "Panipat", "Rewari", "Rohtak", "Sirsa", "Sonipat", "Yamunanagar"));
                
                cities.put("Gurugram", Arrays.asList("Gurugram", "Faridabad", "Sohna", "Pataudi", "Manesar", "Dharuhera", 
                                "Rewari", "Bawal", "Nuh", "Ferozepur Jhirka", "Taoru", "Nagina", "Punahana", "Hathin", "Nuh"));
                cities.put("Faridabad", Arrays.asList("Faridabad", "Ballabgarh", "Palwal", "Hathin", "Hodal", "Hassanpur", 
                                "Kosi", "Mathura Road", "Sector 21", "Sector 28", "Sector 29", "Sector 30", "Sector 31", 
                                "Sector 32", "Sector 33"));
                cities.put("Panipat", Arrays.asList("Panipat", "Samalkha", "Israna", "Bapoli", "Madlauda", "Assandh", "Gharaunda", 
                                "Karnal", "Indri", "Nissing", "Nilokheri", "Taraori", "Kunjpura", "Gharaunda", "Assandh"));
                cities.put("Karnal", Arrays.asList("Karnal", "Indri", "Nissing", "Nilokheri", "Taraori", "Kunjpura", "Gharaunda", 
                                "Assandh", "Panipat", "Samalkha", "Israna", "Bapoli", "Madlauda", "Assandh", "Gharaunda"));
                cities.put("Hisar", Arrays.asList("Hisar", "Hansi", "Adampur", "Bass", "Barwala", "Uklana", "Narnaund", "Hansi", 
                                "Adampur", "Bass", "Barwala", "Uklana", "Narnaund", "Hansi", "Adampur"));
                cities.put("Rohtak", Arrays.asList("Rohtak", "Meham", "Kalanaur", "Sampla", "Lakhan Majra", "Beri", "Jhajjar", 
                                "Bahadurgarh", "Badli", "Dujana", "Matanhail", "Salhawas", "Jhajjar", "Bahadurgarh", "Badli"));
                cities.put("Ambala", Arrays.asList("Ambala", "Naraingarh", "Barara", "Shahzadpur", "Mullana", "Saha", "Jagadhri", 
                                "Yamunanagar", "Chhachhrauli", "Radaur", "Bilaspur", "Mustafabad", "Sadhaura", "Jagadhri", 
                                "Yamunanagar"));
                cities.put("Yamunanagar", Arrays.asList("Yamunanagar", "Jagadhri", "Chhachhrauli", "Radaur", "Bilaspur", 
                                "Mustafabad", "Sadhaura", "Jagadhri", "Yamunanagar", "Chhachhrauli", "Radaur", "Bilaspur", 
                                "Mustafabad", "Sadhaura", "Jagadhri"));
                cities.put("Sonipat", Arrays.asList("Sonipat", "Gohana", "Kharkhoda", "Ganaur", "Rai", "Murthal", "Kundli", 
                                "Bahalgarh", "Kathura", "Beri", "Jhajjar", "Bahadurgarh", "Badli", "Dujana", "Matanhail"));
                cities.put("Panchkula", Arrays.asList("Panchkula", "Kalka", "Pinjore", "Barwala", "Raipur Rani", "Morni", 
                                "Kalka", "Pinjore", "Barwala", "Raipur Rani", "Morni", "Kalka", "Pinjore", "Barwala", 
                                "Raipur Rani"));
                cities.put("Jind", Arrays.asList("Jind", "Narwana", "Julana", "Safidon", "Uchana", "Pillukhera", "Alewa", 
                                "Julana", "Safidon", "Uchana", "Pillukhera", "Alewa", "Julana", "Safidon", "Uchana"));
                cities.put("Sirsa", Arrays.asList("Sirsa", "Dabwali", "Ellenabad", "Rania", "Kalanwali", "Mandi Dabwali", 
                                "Odhan", "Baragudha", "Mandi Kalanwali", "Rania", "Ellenabad", "Dabwali", "Odhan", "Baragudha", 
                                "Mandi Dabwali"));
                cities.put("Rewari", Arrays.asList("Rewari", "Bawal", "Dharuhera", "Nuh", "Ferozepur Jhirka", "Taoru", "Nagina", 
                                "Punahana", "Hathin", "Nuh", "Bawal", "Dharuhera", "Nuh", "Ferozepur Jhirka", "Taoru"));
                cities.put("Kurukshetra", Arrays.asList("Kurukshetra", "Pehowa", "Shahbad", "Ladwa", "Thanesar", "Babain", 
                                "Pipli", "Ismailabad", "Shahbad", "Ladwa", "Thanesar", "Babain", "Pipli", "Ismailabad", 
                                "Shahbad"));
                cities.put("Kaithal", Arrays.asList("Kaithal", "Pundri", "Kalayat", "Cheeka", "Siwan", "Guhla", "Rajound", 
                                "Kalayat", "Cheeka", "Siwan", "Guhla", "Rajound", "Kalayat", "Cheeka", "Siwan"));
                cities.put("Bhiwani", Arrays.asList("Bhiwani", "Tosham", "Siwani", "Loharu", "Dadri", "Bawani Khera", "Behal", 
                                "Tosham", "Siwani", "Loharu", "Dadri", "Bawani Khera", "Behal", "Tosham", "Siwani"));
                cities.put("Jhajjar", Arrays.asList("Jhajjar", "Bahadurgarh", "Badli", "Dujana", "Matanhail", "Salhawas", 
                                "Jhajjar", "Bahadurgarh", "Badli", "Dujana", "Matanhail", "Salhawas", "Jhajjar", "Bahadurgarh", 
                                "Badli"));
                cities.put("Fatehabad", Arrays.asList("Fatehabad", "Tohana", "Ratia", "Bhuna", "Jakhal", "Bhattu Kalan", 
                                "Tohana", "Ratia", "Bhuna", "Jakhal", "Bhattu Kalan", "Tohana", "Ratia", "Bhuna", "Jakhal"));
                cities.put("Palwal", Arrays.asList("Palwal", "Hathin", "Hodal", "Hassanpur", "Kosi", "Mathura Road", "Hathin", 
                                "Hodal", "Hassanpur", "Kosi", "Mathura Road", "Hathin", "Hodal", "Hassanpur", "Kosi"));
                cities.put("Nuh", Arrays.asList("Nuh", "Ferozepur Jhirka", "Taoru", "Nagina", "Punahana", "Hathin", "Nuh", 
                                "Ferozepur Jhirka", "Taoru", "Nagina", "Punahana", "Hathin", "Nuh", "Ferozepur Jhirka", "Taoru"));
                cities.put("Charkhi Dadri", Arrays.asList("Charkhi Dadri", "Badhra", "Bhiwani", "Tosham", "Siwani", "Loharu", 
                                "Dadri", "Bawani Khera", "Behal", "Tosham", "Siwani", "Loharu", "Dadri", "Bawani Khera", 
                                "Behal"));
                cities.put("Mahendragarh", Arrays.asList("Narnaul", "Mahendragarh", "Kanina", "Ateli", "Nangal Chaudhry", 
                                "Narnaul", "Mahendragarh", "Kanina", "Ateli", "Nangal Chaudhry", "Narnaul", "Mahendragarh", 
                                "Kanina", "Ateli", "Nangal Chaudhry"));

                // Himachal Pradesh
                districts.put("Himachal Pradesh", Arrays.asList("Bilaspur", "Chamba", "Hamirpur", "Kangra", "Kinnaur", 
                                "Kullu", "Lahaul and Spiti", "Mandi", "Shimla", "Sirmaur", "Solan", "Una"));
                
                cities.put("Shimla", Arrays.asList("Shimla", "Kufri", "Mashobra", "Naldehra", "Chail", "Kasauli", "Solan", 
                                "Barog", "Kandaghat", "Chambaghat", "Arki", "Nalagarh", "Baddi", "Parwanoo", "Kasauli"));
                cities.put("Kullu", Arrays.asList("Kullu", "Manali", "Naggar", "Banjar", "Anni", "Nirmand", "Sainj", "Larji", 
                                "Aut", "Bhuntar", "Katrain", "Raison", "Jagatsukh", "Manikaran", "Kasol"));
                cities.put("Kangra", Arrays.asList("Dharamshala", "Kangra", "Palampur", "McLeod Ganj", "Baijnath", "Jawalamukhi", 
                                "Nurpur", "Dehra", "Shahpur", "Nagrota", "Yol", "Gaggal", "Dharamshala", "Kangra", "Palampur"));
                cities.put("Mandi", Arrays.asList("Mandi", "Sarkaghat", "Jogindernagar", "Rewalsar", "Sundernagar", "Karsog", 
                                "Nachan", "Padhar", "Bharmour", "Chamba", "Dalhousie", "Khajjiar", "Bakloh", "Tissa", "Salooni"));
                cities.put("Solan", Arrays.asList("Solan", "Kasauli", "Barog", "Kandaghat", "Chambaghat", "Arki", "Nalagarh", 
                                "Baddi", "Parwanoo", "Kasauli", "Barog", "Kandaghat", "Chambaghat", "Arki", "Nalagarh"));
                cities.put("Chamba", Arrays.asList("Chamba", "Dalhousie", "Khajjiar", "Bakloh", "Tissa", "Salooni", "Bharmour", 
                                "Padhar", "Nachan", "Karsog", "Sundernagar", "Rewalsar", "Jogindernagar", "Sarkaghat", "Mandi"));
                cities.put("Hamirpur", Arrays.asList("Hamirpur", "Nadaun", "Barsar", "Bhoranj", "Dhatwal", "Galore", "Jawali", 
                                "Kangra", "Dharamshala", "Palampur", "McLeod Ganj", "Baijnath", "Jawalamukhi", "Nurpur", "Dehra"));
                cities.put("Una", Arrays.asList("Una", "Amb", "Bangana", "Haroli", "Gagret", "Santokhgarh", "Daulatpur", "Bharwain", 
                                "Nangal", "Anandpur Sahib", "Rupnagar", "Kiratpur", "Nurpur Bedi", "Bassi", "Garhshankar"));
                cities.put("Bilaspur", Arrays.asList("Bilaspur", "Ghumarwin", "Jhandutta", "Swarghat", "Berthin", "Naina Devi", 
                                "Barmana", "Sarkaghat", "Jogindernagar", "Rewalsar", "Sundernagar", "Karsog", "Nachan", "Padhar", 
                                "Bharmour"));
                cities.put("Sirmaur", Arrays.asList("Nahan", "Paonta Sahib", "Rajgarh", "Sangrah", "Shillai", "Narayangarh", 
                                "Kamrau", "Dadahu", "Renuka", "Sarahan", "Rampur", "Nankhari", "Kotkhai", "Rohru", "Chirgaon"));
                cities.put("Kinnaur", Arrays.asList("Reckong Peo", "Kalpa", "Sangla", "Chitkul", "Nako", "Pooh", "Morang", 
                                "Spillo", "Nichar", "Pangi", "Killar", "Udaipur", "Tandi", "Keylong", "Jispa"));
                cities.put("Lahaul and Spiti", Arrays.asList("Keylong", "Jispa", "Tandi", "Udaipur", "Killar", "Pangi", "Nichar", 
                                "Spillo", "Morang", "Pooh", "Nako", "Chitkul", "Sangla", "Kalpa", "Reckong Peo"));

                // Jharkhand
                districts.put("Jharkhand", Arrays.asList("Bokaro", "Chatra", "Deoghar", "Dhanbad", "Dumka", "East Singhbhum", 
                                "Garhwa", "Giridih", "Godda", "Gumla", "Hazaribagh", "Jamtara", "Khunti", "Koderma", "Latehar", 
                                "Lohardaga", "Pakur", "Palamu", "Ramgarh", "Ranchi", "Sahibganj", "Seraikela Kharsawan", 
                                "Simdega", "West Singhbhum"));
                
                cities.put("Ranchi", Arrays.asList("Ranchi", "Hatia", "Namkum", "Kanke", "Ormanjhi", "Ratu", "Mandar", "Burmu", 
                                "Angara", "Silli", "Lapung", "Tamar", "Karra", "Torpa", "Khunti"));
                cities.put("Jamshedpur", Arrays.asList("Jamshedpur", "Adityapur", "Gamharia", "Ghatshila", "Mango", "Kadma", 
                                "Sonari", "Bistupur", "Sakchi", "Telco", "Golmuri", "Dimna", "Baridih", "Bagbera", "Potka"));
                cities.put("Dhanbad", Arrays.asList("Dhanbad", "Jharia", "Sindri", "Katras", "Chirkunda", "Gobindpur", "Nirsa", 
                                "Topchanchi", "Tundi", "Baghmara", "Purbi Tundi", "Baliapur", "Nawadih", "Kenduadih", "Lodna"));
                cities.put("Bokaro", Arrays.asList("Bokaro Steel City", "Chas", "Phusro", "Gomia", "Petarwar", "Jaridih", "Bermo", 
                                "Nawadih", "Chandrapura", "Kargali", "Kumardhubi", "Tenu", "Chandankiyari", "Giridih", "Bengabad"));
                cities.put("Hazaribagh", Arrays.asList("Hazaribagh", "Ramgarh", "Barkagaon", "Ichak", "Katkamsandi", "Keredari", 
                                "Churchu", "Dadi", "Daru", "Katkamdag", "Barhi", "Chauparan", "Padma", "Tati Jhariya", "Hazaribagh"));
                cities.put("Deoghar", Arrays.asList("Deoghar", "Madhupur", "Jasidih", "Sarath", "Palojori", "Karon", "Mohanpur", 
                                "Sarwan", "Sona", "Devipur", "Margomunda", "Nawadih", "Karon", "Mohanpur", "Sarwan"));
                cities.put("Giridih", Arrays.asList("Giridih", "Bengabad", "Gande", "Dumri", "Bagodar", "Tisri", "Dhanwar", "Jamua", 
                                "Deori", "Birni", "Gawan", "Tilaiya", "Pirtand", "Dumri", "Gande"));
                cities.put("Dumka", Arrays.asList("Dumka", "Jarmundi", "Ranishwar", "Jama", "Masalia", "Kathikund", "Shikaripara", 
                                "Gopikandar", "Ramgarh", "Saraiyahat", "Jarmundi", "Ranishwar", "Jama", "Masalia", "Kathikund"));
                cities.put("Palamu", Arrays.asList("Daltonganj", "Medininagar", "Hussainabad", "Chhatarpur", "Lesliganj", "Panki", 
                                "Chainpur", "Bishrampur", "Garhwa", "Nagaruntari", "Ramna", "Bhandaria", "Pandu", "Manatu", 
                                "Hariharganj"));
                cities.put("East Singhbhum", Arrays.asList("Jamshedpur", "Ghatshila", "Mango", "Kadma", "Sonari", "Bistupur", 
                                "Sakchi", "Telco", "Golmuri", "Dimna", "Baridih", "Bagbera", "Potka", "Adityapur", "Gamharia"));
                cities.put("West Singhbhum", Arrays.asList("Chaibasa", "Jhinkpani", "Noamundi", "Manoharpur", "Gua", "Sonua", 
                                "Chakradharpur", "Goilkera", "Jagannathpur", "Bandgaon", "Tonto", "Hat Gamharia", "Khuntpani", 
                                "Barbil", "Baraiburu"));
                cities.put("Seraikela Kharsawan", Arrays.asList("Seraikela", "Kharsawan", "Chandil", "Kandra", "Gamharia", 
                                "Adityapur", "Jamshedpur", "Ghatshila", "Mango", "Kadma", "Sonari", "Bistupur", "Sakchi", "Telco", 
                                "Golmuri"));
                cities.put("Ramgarh", Arrays.asList("Ramgarh", "Gola", "Patratu", "Chitarpur", "Mandu", "Kuju", "Gidi", "Tupudana", 
                                "Hazaribagh", "Barkagaon", "Ichak", "Katkamsandi", "Keredari", "Churchu", "Dadi"));
                cities.put("Chatra", Arrays.asList("Chatra", "Simaria", "Hunterganj", "Pratappur", "Kunda", "Lawalong", "Itkhori", 
                                "Gidhaur", "Pathalgada", "Tandwa", "Kunda", "Lawalong", "Itkhori", "Gidhaur", "Pathalgada"));
                cities.put("Koderma", Arrays.asList("Koderma", "Jhumri Telaiya", "Markacho", "Satgawan", "Jainagar", "Domchanch", 
                                "Chandwara", "Barhi", "Chauparan", "Padma", "Tati Jhariya", "Hazaribagh", "Ramgarh", "Gola", 
                                "Patratu"));
                cities.put("Lohardaga", Arrays.asList("Lohardaga", "Kuru", "Kisko", "Senha", "Bhandra", "Peshrar", "Kairo", "Bhandra", 
                                "Peshrar", "Kairo", "Kuru", "Kisko", "Senha", "Bhandra", "Peshrar"));
                cities.put("Gumla", Arrays.asList("Gumla", "Simdega", "Bano", "Chainpur", "Basia", "Ghaghra", "Palkot", "Raidih", 
                                "Kamdara", "Bishunpur", "Sisai", "Bharno", "Kolebira", "Thethaitangar", "Kurdeg"));
                cities.put("Simdega", Arrays.asList("Simdega", "Kolebira", "Thethaitangar", "Kurdeg", "Bano", "Chainpur", "Basia", 
                                "Ghaghra", "Palkot", "Raidih", "Kamdara", "Bishunpur", "Sisai", "Bharno", "Kolebira"));
                cities.put("Pakur", Arrays.asList("Pakur", "Litipara", "Amrapara", "Hiranpur", "Maheshpur", "Pakuria", "Dumka", 
                                "Jarmundi", "Ranishwar", "Jama", "Masalia", "Kathikund", "Shikaripara", "Gopikandar", "Ramgarh"));
                cities.put("Godda", Arrays.asList("Godda", "Mahagama", "Meherma", "Pathargama", "Basantpur", "Sundarpahari", 
                                "Boarijor", "Poraiyahat", "Poreyahat", "Thakurgangti", "Mahagama", "Meherma", "Pathargama", 
                                "Basantpur", "Sundarpahari"));
                cities.put("Sahibganj", Arrays.asList("Sahibganj", "Rajmahal", "Barharwa", "Udhwa", "Borio", "Taljhari", "Pathna", 
                                "Barhait", "Sahibganj", "Rajmahal", "Barharwa", "Udhwa", "Borio", "Taljhari", "Pathna"));
                cities.put("Jamtara", Arrays.asList("Jamtara", "Nala", "Kundhit", "Fatehpur", "Narayanpur", "Karmatanr", "Jamtara", 
                                "Nala", "Kundhit", "Fatehpur", "Narayanpur", "Karmatanr", "Jamtara", "Nala", "Kundhit"));
                cities.put("Khunti", Arrays.asList("Khunti", "Torpa", "Karra", "Rania", "Murhu", "Arki", "Bundu", "Tamara", "Erki", 
                                "Tamar", "Karra", "Torpa", "Khunti", "Rania", "Murhu"));
                cities.put("Latehar", Arrays.asList("Latehar", "Barwadih", "Mahuadanr", "Garhwa", "Nagaruntari", "Ramna", 
                                "Bhandaria", "Pandu", "Manatu", "Hariharganj", "Latehar", "Barwadih", "Mahuadanr", "Garhwa", 
                                "Nagaruntari"));
                cities.put("Garhwa", Arrays.asList("Garhwa", "Nagaruntari", "Ramna", "Bhandaria", "Pandu", "Manatu", "Hariharganj", 
                                "Daltonganj", "Medininagar", "Hussainabad", "Chhatarpur", "Lesliganj", "Panki", "Chainpur", 
                                "Bishrampur"));

                // Karnataka - Adding all districts and key cities
                districts.put("Karnataka", Arrays.asList("Bagalkot", "Ballari", "Belagavi", "Bengaluru Rural", "Bengaluru Urban", 
                                "Bidar", "Chamarajanagar", "Chikkaballapur", "Chikkamagaluru", "Chitradurga", "Dakshina Kannada", 
                                "Davanagere", "Dharwad", "Gadag", "Hassan", "Haveri", "Kalaburagi", "Kodagu", "Kolar", "Koppal", 
                                "Mandya", "Mysuru", "Raichur", "Ramanagara", "Shivamogga", "Tumakuru", "Udupi", "Uttara Kannada", 
                                "Vijayapura", "Vijayanagara", "Yadgir"));
                
                cities.put("Bengaluru Urban", Arrays.asList("Bangalore", "Yelahanka", "Kengeri", "Whitefield", "Electronic City", 
                                "Marathahalli", "HSR Layout", "Koramangala", "Indiranagar", "Jayanagar", "Basavanagudi", "Malleshwaram", 
                                "Rajajinagar", "Vijayanagar", "Yeshwanthpur"));
                cities.put("Mysuru", Arrays.asList("Mysore", "Nanjangud", "Hunsur", "Krishnarajanagara", "Piriyapatna", "T Narasipura", 
                                "Heggadadevankote", "Periyapatna", "Krishnarajanagara", "Piriyapatna", "T Narasipura", "Heggadadevankote", 
                                "Periyapatna", "Mysore", "Nanjangud"));
                cities.put("Hubli-Dharwad", Arrays.asList("Hubli", "Dharwad", "Navalgund", "Kalghatgi", "Kundgol", "Annigeri", 
                                "Hirekerur", "Byadgi", "Hangal", "Shiggaon", "Savadatti", "Ramdurg", "Gokak", "Belagavi", "Khanapur"));
                cities.put("Mangaluru", Arrays.asList("Mangalore", "Udupi", "Kundapura", "Karkala", "Bantwal", "Puttur", "Sullia", 
                                "Belthangady", "Kadaba", "Moodbidri", "Mulki", "Surathkal", "Padubidri", "Kota", "Uppinangady"));
                cities.put("Belagavi", Arrays.asList("Belgaum", "Gokak", "Bailhongal", "Athani", "Khanapur", "Hukeri", "Ramdurg", 
                                "Saundatti", "Chikodi", "Nippani", "Raibag", "Kagwad", "Raybag", "Mudhol", "Jamkhandi"));
                cities.put("Kalaburagi", Arrays.asList("Gulbarga", "Aland", "Afzalpur", "Jevargi", "Sedam", "Chitapur", "Yadgir", 
                                "Shahpur", "Shorapur", "Hunsagi", "Kamalapur", "Chincholi", "Wadi", "Gurmatkal", "Sindagi"));
                cities.put("Davanagere", Arrays.asList("Davangere", "Harihar", "Channagiri", "Honnali", "Jagalur", "Harapanahalli", 
                                "Nyamati", "Challakere", "Hiriyur", "Hosadurga", "Molakalmuru", "Holalkere", "Davanagere", "Harihar", 
                                "Channagiri"));
                cities.put("Ballari", Arrays.asList("Bellary", "Hospet", "Sandur", "Kudligi", "Hagaribommanahalli", "Hadagali", 
                                "Kotturu", "Siruguppa", "Kurugodu", "Tekkalakote", "Bellary", "Hospet", "Sandur", "Kudligi", 
                                "Hagaribommanahalli"));
                cities.put("Tumakuru", Arrays.asList("Tumkur", "Sira", "Tiptur", "Gubbi", "Koratagere", "Madhugiri", "Pavagada", 
                                "Kunigal", "Chiknayakanhalli", "Turuvekere", "Tiptur", "Gubbi", "Koratagere", "Madhugiri", "Pavagada"));
                cities.put("Shivamogga", Arrays.asList("Shimoga", "Bhadravati", "Sagar", "Tirthahalli", "Hosanagara", "Sorab", 
                                "Shikaripura", "Thirthahalli", "Hosanagara", "Sorab", "Shikaripura", "Bhadravati", "Sagar", 
                                "Tirthahalli", "Hosanagara"));
                cities.put("Raichur", Arrays.asList("Raichur", "Manvi", "Devadurga", "Lingsugur", "Sindhanur", "Yelburga", 
                                "Gangavathi", "Koppal", "Kushtagi", "Maski", "Deodurga", "Lingsugur", "Sindhanur", "Yelburga", 
                                "Gangavathi"));
                cities.put("Bidar", Arrays.asList("Bidar", "Basavakalyan", "Bhalki", "Aurad", "Humnabad", "Chitgoppa", "Kamalnagar", 
                                "Bidar", "Basavakalyan", "Bhalki", "Aurad", "Humnabad", "Chitgoppa", "Kamalnagar", "Bidar"));
                cities.put("Hassan", Arrays.asList("Hassan", "Arsikere", "Belur", "Channarayapatna", "Holenarasipura", "Sakleshpur", 
                                "Alur", "Arkalgud", "Hassan", "Arsikere", "Belur", "Channarayapatna", "Holenarasipura", "Sakleshpur", 
                                "Alur"));
                cities.put("Mandya", Arrays.asList("Mandya", "Maddur", "Malavalli", "Pandavapura", "Krishnarajanagara", "Nagamangala", 
                                "Srirangapatna", "Krishnarajanagara", "Nagamangala", "Srirangapatna", "Mandya", "Maddur", "Malavalli", 
                                "Pandavapura", "Krishnarajanagara"));
                cities.put("Chikkamagaluru", Arrays.asList("Chikmagalur", "Kadur", "Mudigere", "Tarikere", "Koppa", "Narasimharajapura", 
                                "Sringeri", "Chikmagalur", "Kadur", "Mudigere", "Tarikere", "Koppa", "Narasimharajapura", "Sringeri", 
                                "Chikmagalur"));
                cities.put("Udupi", Arrays.asList("Udupi", "Kundapura", "Karkala", "Bantwal", "Puttur", "Sullia", "Belthangady", 
                                "Kadaba", "Moodbidri", "Mulki", "Surathkal", "Padubidri", "Kota", "Uppinangady", "Mangalore"));
                cities.put("Dakshina Kannada", Arrays.asList("Mangalore", "Bantwal", "Puttur", "Sullia", "Belthangady", "Kadaba", 
                                "Moodbidri", "Mulki", "Surathkal", "Padubidri", "Kota", "Uppinangady", "Mangalore", "Bantwal", "Puttur"));
                cities.put("Chitradurga", Arrays.asList("Chitradurga", "Hiriyur", "Hosadurga", "Molakalmuru", "Holalkere", "Challakere", 
                                "Chitradurga", "Hiriyur", "Hosadurga", "Molakalmuru", "Holalkere", "Challakere", "Chitradurga", 
                                "Hiriyur", "Hosadurga"));
                cities.put("Kolar", Arrays.asList("Kolar", "Bangarapet", "Malur", "Mulbagal", "Srinivaspur", "Chintamani", "Gudibanda", 
                                "Kolar", "Bangarapet", "Malur", "Mulbagal", "Srinivaspur", "Chintamani", "Gudibanda", "Kolar"));
                cities.put("Chikkaballapur", Arrays.asList("Chikkaballapur", "Bagepalli", "Chintamani", "Gauribidanur", "Gudibanda", 
                                "Sidlaghatta", "Chikkaballapur", "Bagepalli", "Chintamani", "Gauribidanur", "Gudibanda", "Sidlaghatta", 
                                "Chikkaballapur", "Bagepalli", "Chintamani"));
                cities.put("Ramanagara", Arrays.asList("Ramanagara", "Channapatna", "Kanakapura", "Magadi", "Harohalli", "Ramanagara", 
                                "Channapatna", "Kanakapura", "Magadi", "Harohalli", "Ramanagara", "Channapatna", "Kanakapura", "Magadi", 
                                "Harohalli"));
                cities.put("Vijayapura", Arrays.asList("Bijapur", "Indi", "Sindgi", "Basavana Bagewadi", "Muddebihal", "Devar Hippargi", 
                                "Bijapur", "Indi", "Sindgi", "Basavana Bagewadi", "Muddebihal", "Devar Hippargi", "Bijapur", "Indi", 
                                "Sindgi"));
                cities.put("Bagalkot", Arrays.asList("Bagalkot", "Badami", "Hungund", "Jamkhandi", "Mudhol", "Bilgi", "Bagalkot", 
                                "Badami", "Hungund", "Jamkhandi", "Mudhol", "Bilgi", "Bagalkot", "Badami", "Hungund"));
                cities.put("Gadag", Arrays.asList("Gadag", "Mundargi", "Nargund", "Ron", "Shirahatti", "Gadag", "Mundargi", "Nargund", 
                                "Ron", "Shirahatti", "Gadag", "Mundargi", "Nargund", "Ron", "Shirahatti"));
                cities.put("Haveri", Arrays.asList("Haveri", "Byadgi", "Hangal", "Hirekerur", "Ranebennur", "Savanur", "Shiggaon", 
                                "Haveri", "Byadgi", "Hangal", "Hirekerur", "Ranebennur", "Savanur", "Shiggaon", "Haveri"));
                cities.put("Koppal", Arrays.asList("Koppal", "Gangavathi", "Kushtagi", "Yelburga", "Koppal", "Gangavathi", "Kushtagi", 
                                "Yelburga", "Koppal", "Gangavathi", "Kushtagi", "Yelburga", "Koppal", "Gangavathi", "Kushtagi"));
                cities.put("Yadgir", Arrays.asList("Yadgir", "Shahpur", "Shorapur", "Hunsagi", "Kamalapur", "Chincholi", "Yadgir", 
                                "Shahpur", "Shorapur", "Hunsagi", "Kamalapur", "Chincholi", "Yadgir", "Shahpur", "Shorapur"));
                cities.put("Kodagu", Arrays.asList("Madikeri", "Virajpet", "Somwarpet", "Kushalnagar", "Madikeri", "Virajpet", 
                                "Somwarpet", "Kushalnagar", "Madikeri", "Virajpet", "Somwarpet", "Kushalnagar", "Madikeri", "Virajpet", 
                                "Somwarpet"));
                cities.put("Uttara Kannada", Arrays.asList("Karwar", "Ankola", "Kumta", "Honnavar", "Bhatkal", "Sirsi", "Yellapur", 
                                "Haliyal", "Joida", "Mundgod", "Siddapur", "Karwar", "Ankola", "Kumta", "Honnavar"));
                cities.put("Chamarajanagar", Arrays.asList("Chamrajnagar", "Gundlupet", "Kollegal", "Yelandur", "Chamrajnagar", 
                                "Gundlupet", "Kollegal", "Yelandur", "Chamrajnagar", "Gundlupet", "Kollegal", "Yelandur", "Chamrajnagar", 
                                "Gundlupet", "Kollegal"));
                cities.put("Vijayanagara", Arrays.asList("Hospet", "Bellary", "Sandur", "Kudligi", "Hagaribommanahalli", "Hadagali", 
                                "Kotturu", "Siruguppa", "Kurugodu", "Tekkalakote", "Hospet", "Bellary", "Sandur", "Kudligi", 
                                "Hagaribommanahalli"));

                // Kerala - Adding all districts
                districts.put("Kerala", Arrays.asList("Alappuzha", "Ernakulam", "Idukki", "Kannur", "Kasaragod", "Kollam", "Kottayam", 
                                "Kozhikode", "Malappuram", "Palakkad", "Pathanamthitta", "Thiruvananthapuram", "Thrissur", "Wayanad"));
                
                cities.put("Thiruvananthapuram", Arrays.asList("Trivandrum", "Neyyattinkara", "Attingal", "Nedumangad", "Varkala", 
                                "Kovalam", "Ponmudi", "Kattakada", "Parassala", "Vellanad", "Kallara", "Kalliyoor", "Kazhakootam", 
                                "Kovalam", "Varkala"));
                cities.put("Kochi", Arrays.asList("Kochi", "Ernakulam", "Aluva", "Kalamassery", "Edappally", "Kakkanad", "Maradu", 
                                "Thripunithura", "Perumbavoor", "Muvattupuzha", "Kothamangalam", "Angamaly", "Paravur", "North Paravur", 
                                "Mattancherry"));
                cities.put("Kozhikode", Arrays.asList("Calicut", "Kozhikode", "Feroke", "Ramanattukara", "Koyilandy", "Vadakara", 
                                "Thamarassery", "Mukkam", "Balussery", "Koduvally", "Perambra", "Olavanna", "Kunnamangalam", "Elathur", 
                                "Beypore"));
                cities.put("Thrissur", Arrays.asList("Thrissur", "Guruvayur", "Kodungallur", "Irinjalakuda", "Chalakudy", "Kunnamkulam", 
                                "Wadakkanchery", "Mala", "Perinthalmanna", "Ponnani", "Tirur", "Kottakkal", "Manjeri", "Malappuram", 
                                "Kondotty"));
                cities.put("Kollam", Arrays.asList("Kollam", "Quilon", "Paravur", "Karunagappally", "Punalur", "Anchal", "Sasthamkotta", 
                                "Kottarakkara", "Pathanapuram", "Chadayamangalam", "Kulathupuzha", "Thenmala", "Parippally", "Chavara", 
                                "Oachira"));
                cities.put("Kannur", Arrays.asList("Kannur", "Thalassery", "Payyannur", "Koothuparamba", "Iritty", "Taliparamba", 
                                "Mattannur", "Kuthuparamba", "Pappinisseri", "Chirakkal", "Kannur", "Thalassery", "Payyannur", 
                                "Koothuparamba", "Iritty"));
                cities.put("Alappuzha", Arrays.asList("Alappuzha", "Alleppey", "Cherthala", "Kayamkulam", "Mavelikkara", "Chengannur", 
                                "Haripad", "Ambalapuzha", "Kuttanad", "Karthikappally", "Mannar", "Mararikulam", "Thiruvalla", "Pandalam", 
                                "Thakazhy"));
                cities.put("Kottayam", Arrays.asList("Kottayam", "Changanassery", "Pala", "Vaikom", "Ettumanoor", "Puthuppally", 
                                "Kanjirappally", "Erattupetta", "Mundakayam", "Poonjar", "Teekoy", "Vazhoor", "Kumarakom", "Aymanam", 
                                "Kaduthuruthy"));
                cities.put("Palakkad", Arrays.asList("Palakkad", "Ottapalam", "Shoranur", "Chittur", "Alathur", "Mannarkkad", "Pattambi", 
                                "Kollengode", "Koduvayur", "Muthalamada", "Parli", "Kollengode", "Koduvayur", "Muthalamada", "Parli"));
                cities.put("Malappuram", Arrays.asList("Malappuram", "Manjeri", "Perinthalmanna", "Ponnani", "Tirur", "Kottakkal", 
                                "Kondotty", "Nilambur", "Wandoor", "Edappal", "Tirurangadi", "Tanur", "Parappanangadi", "Valanchery", 
                                "Kuttippuram"));
                cities.put("Idukki", Arrays.asList("Idukki", "Thodupuzha", "Munnar", "Kattappana", "Adimali", "Nedumkandam", "Devikulam", 
                                "Peerumedu", "Vandiperiyar", "Kumily", "Puliyanmala", "Udumbanchola", "Idukki", "Thodupuzha", "Munnar"));
                cities.put("Wayanad", Arrays.asList("Kalpetta", "Mananthavady", "Sultan Bathery", "Vythiri", "Pulpally", "Meppadi", 
                                "Ambalavayal", "Poothadi", "Nenmeni", "Panamaram", "Kalpetta", "Mananthavady", "Sultan Bathery", "Vythiri", 
                                "Pulpally"));
                cities.put("Pathanamthitta", Arrays.asList("Pathanamthitta", "Adoor", "Thiruvalla", "Pandalam", "Ranni", "Kozhencherry", 
                                "Mallappally", "Aranmula", "Elanthoor", "Konni", "Seethathode", "Ranni", "Kozhencherry", "Mallappally", 
                                "Aranmula"));
                cities.put("Kasaragod", Arrays.asList("Kasaragod", "Kanhangad", "Hosdurg", "Nileshwar", "Cheruvathur", "Kumbala", 
                                "Bekal", "Manjeshwar", "Uppala", "Kasaragod", "Kanhangad", "Hosdurg", "Nileshwar", "Cheruvathur", 
                                "Kumbala"));

                // Madhya Pradesh
                districts.put("Madhya Pradesh", Arrays.asList("Agar Malwa", "Alirajpur", "Anuppur", "Ashoknagar", "Balaghat", 
                                "Barwani", "Betul", "Bhind", "Bhopal", "Burhanpur", "Chhatarpur", "Chhindwara", "Damoh", "Datia", 
                                "Dewas", "Dhar", "Dindori", "Guna", "Gwalior", "Harda", "Hoshangabad", "Indore", "Jabalpur", "Jhabua", 
                                "Katni", "Khandwa", "Khargone", "Mandla", "Mandsaur", "Morena", "Narsinghpur", "Neemuch", "Panna", 
                                "Raisen", "Rajgarh", "Ratlam", "Rewa", "Sagar", "Satna", "Sehore", "Seoni", "Shahdol", "Shajapur", 
                                "Sheopur", "Shivpuri", "Sidhi", "Singrauli", "Tikamgarh", "Ujjain", "Umaria", "Vidisha"));
                
                cities.put("Bhopal", Arrays.asList("Bhopal", "Huzur", "Berasia", "Phanda", "Gandhinagar", "Kolar", "Bairagarh", 
                                "Sehore", "Ashta", "Ichhawar", "Nasrullaganj", "Budhni", "Rehti", "Shyampur", "Raisen"));
                cities.put("Indore", Arrays.asList("Indore", "Mhow", "Depalpur", "Sanwer", "Hatod", "Betma", "Rau", "Pithampur", 
                                "Dewas", "Kannod", "Bagli", "Khategaon", "Pandhana", "Khandwa", "Burhanpur"));
                cities.put("Gwalior", Arrays.asList("Gwalior", "Dabra", "Bhitarwar", "Ghatigaon", "Mehgaon", "Morena", "Joura", 
                                "Sabalgarh", "Ambah", "Porsa", "Kailaras", "Sheopur", "Vijaypur", "Karera", "Narwar"));
                cities.put("Jabalpur", Arrays.asList("Jabalpur", "Sihora", "Patan", "Shahpura", "Kundam", "Majholi", "Panagar", 
                                "Katni", "Vijayraghavgarh", "Rithi", "Barhi", "Damoh", "Hatta", "Patharia", "Jabera"));
                cities.put("Ujjain", Arrays.asList("Ujjain", "Nagda", "Mahidpur", "Khachrod", "Tarana", "Ghatiya", "Badnagar", 
                                "Ratlam", "Jaora", "Sailana", "Bajna", "Alot", "Mandsaur", "Neemuch", "Manasa"));
                cities.put("Raipur", Arrays.asList("Raipur", "Bhatapara", "Baloda Bazar", "Tilda", "Abhanpur", "Arang", "Mandir Hasod", 
                                "Pallari", "Simga", "Bilaigarh", "Kasdol", "Bindranawagarh", "Chhura", "Gariaband", "Deobhog"));
                cities.put("Sagar", Arrays.asList("Sagar", "Bina", "Khurai", "Rahatgarh", "Rehli", "Garhakota", "Shahgarh", "Jaisinagar", 
                                "Banda", "Deori", "Kesli", "Malthone", "Damoh", "Hatta", "Patharia"));
                cities.put("Rewa", Arrays.asList("Rewa", "Mauganj", "Hanumana", "Teonthar", "Sirmour", "Naigarhi", "Mangawan", "Gurh", 
                                "Semaria", "Raipur Karchuliyan", "Sidhi", "Churhat", "Rampur Naikin", "Gopadbanas", "Kusmi"));
                cities.put("Satna", Arrays.asList("Satna", "Maihar", "Amarpatan", "Raghurajnagar", "Nagod", "Unchehara", "Rampur Baghelan", 
                                "Birsinghpur", "Majhgawan", "Kotar", "Chitrakoot", "Karwi", "Mau", "Maniari", "Pahadi"));
                cities.put("Ratlam", Arrays.asList("Ratlam", "Jaora", "Sailana", "Bajna", "Alot", "Mandsaur", "Neemuch", "Manasa", 
                                "Jawad", "Malhargarh", "Garoth", "Sitamau", "Bhanpura", "Piploda", "Thandla"));
                cities.put("Dewas", Arrays.asList("Dewas", "Kannod", "Bagli", "Khategaon", "Pandhana", "Khandwa", "Burhanpur", "Nepanagar", 
                                "Harsud", "Punasa", "Khalwa", "Pandhana", "Kannod", "Bagli", "Khategaon"));
                cities.put("Dhar", Arrays.asList("Dhar", "Badnawar", "Sardarpur", "Kukshi", "Manawar", "Dharampuri", "Gandhwani", 
                                "Dahi", "Nisarpur", "Dhar", "Badnawar", "Sardarpur", "Kukshi", "Manawar", "Dharampuri"));
                cities.put("Chhindwara", Arrays.asList("Chhindwara", "Parasia", "Jamai", "Pandhurna", "Amarwara", "Chaurai", "Bichhua", 
                                "Tamia", "Harrai", "Mohkhed", "Sausar", "Chhindwara", "Parasia", "Jamai", "Pandhurna"));
                cities.put("Khandwa", Arrays.asList("Khandwa", "Punasa", "Harsud", "Nepanagar", "Burhanpur", "Nepanagar", "Harsud", 
                                "Punasa", "Khalwa", "Pandhana", "Khandwa", "Punasa", "Harsud", "Nepanagar", "Burhanpur"));
                cities.put("Khargone", Arrays.asList("Khargone", "Barwani", "Sendhwa", "Rajpur", "Pansemal", "Kasrawad", "Maheshwar", 
                                "Bhikangaon", "Gogaon", "Bhagwanpura", "Jhirnya", "Segaon", "Khargone", "Barwani", "Sendhwa"));
                cities.put("Morena", Arrays.asList("Morena", "Joura", "Sabalgarh", "Ambah", "Porsa", "Kailaras", "Sheopur", "Vijaypur", 
                                "Karera", "Narwar", "Morena", "Joura", "Sabalgarh", "Ambah", "Porsa"));
                cities.put("Bhind", Arrays.asList("Bhind", "Mehgaon", "Gohad", "Ron", "Mihona", "Ater", "Lahar", "Gormi", "Raun", 
                                "Bhind", "Mehgaon", "Gohad", "Ron", "Mihona", "Ater"));
                cities.put("Guna", Arrays.asList("Guna", "Raghogarh", "Chachoda", "Aron", "Ashoknagar", "Chanderi", "Isagarh", "Mungaoli", 
                                "Guna", "Raghogarh", "Chachoda", "Aron", "Ashoknagar", "Chanderi", "Isagarh"));
                cities.put("Shivpuri", Arrays.asList("Shivpuri", "Pichhore", "Kolaras", "Narwar", "Karera", "Badarwas", "Khaniyadhana", 
                                "Pohri", "Shivpuri", "Pichhore", "Kolaras", "Narwar", "Karera", "Badarwas", "Khaniyadhana"));
                cities.put("Vidisha", Arrays.asList("Vidisha", "Basoda", "Lateri", "Sironj", "Kurwai", "Gyaraspur", "Nateran", "Gulabganj", 
                                "Vidisha", "Basoda", "Lateri", "Sironj", "Kurwai", "Gyaraspur", "Nateran"));
                cities.put("Raisen", Arrays.asList("Raisen", "Goharganj", "Silwani", "Bareli", "Udaipura", "Begamganj", "Gairatganj", 
                                "Sanchi", "Raisen", "Goharganj", "Silwani", "Bareli", "Udaipura", "Begamganj", "Gairatganj"));
                cities.put("Rajgarh", Arrays.asList("Rajgarh", "Biaora", "Jirapur", "Khilchipur", "Sarangpur", "Narsinghgarh", "Pachore", 
                                "Rajgarh", "Biaora", "Jirapur", "Khilchipur", "Sarangpur", "Narsinghgarh", "Pachore", "Rajgarh"));
                cities.put("Sehore", Arrays.asList("Sehore", "Ashta", "Ichhawar", "Nasrullaganj", "Budhni", "Rehti", "Shyampur", "Sehore", 
                                "Ashta", "Ichhawar", "Nasrullaganj", "Budhni", "Rehti", "Shyampur", "Sehore"));
                cities.put("Hoshangabad", Arrays.asList("Hoshangabad", "Itarsi", "Pipariya", "Sohagpur", "Bankhedi", "Seoni Malwa", 
                                "Hoshangabad", "Itarsi", "Pipariya", "Sohagpur", "Bankhedi", "Seoni Malwa", "Hoshangabad", "Itarsi", 
                                "Pipariya"));
                cities.put("Harda", Arrays.asList("Harda", "Timarni", "Khirakia", "Rehatgaon", "Sirali", "Harda", "Timarni", "Khirakia", 
                                "Rehatgaon", "Sirali", "Harda", "Timarni", "Khirakia", "Rehatgaon", "Sirali"));
                cities.put("Betul", Arrays.asList("Betul", "Multai", "Amla", "Shahpur", "Bhainsdehi", "Chicholi", "Ghoda Dongri", 
                                "Betul", "Multai", "Amla", "Shahpur", "Bhainsdehi", "Chicholi", "Ghoda Dongri", "Betul"));
                cities.put("Chhatarpur", Arrays.asList("Chhatarpur", "Nowgong", "Bada Malhera", "Bijawar", "Gaurihar", "Laundi", 
                                "Chhatarpur", "Nowgong", "Bada Malhera", "Bijawar", "Gaurihar", "Laundi", "Chhatarpur", "Nowgong", 
                                "Bada Malhera"));
                cities.put("Panna", Arrays.asList("Panna", "Ajaigarh", "Gunnor", "Shahnagar", "Amanganj", "Pawai", "Panna", "Ajaigarh", 
                                "Gunnor", "Shahnagar", "Amanganj", "Pawai", "Panna", "Ajaigarh", "Gunnor"));
                cities.put("Tikamgarh", Arrays.asList("Tikamgarh", "Jatara", "Palera", "Niwari", "Prithvipur", "Orchha", "Tikamgarh", 
                                "Jatara", "Palera", "Niwari", "Prithvipur", "Orchha", "Tikamgarh", "Jatara", "Palera"));
                cities.put("Damoh", Arrays.asList("Damoh", "Hatta", "Patharia", "Jabera", "Tendukheda", "Patera", "Damoh", "Hatta", 
                                "Patharia", "Jabera", "Tendukheda", "Patera", "Damoh", "Hatta", "Patharia"));
                cities.put("Sagar", Arrays.asList("Sagar", "Bina", "Khurai", "Rahatgarh", "Rehli", "Garhakota", "Shahgarh", "Jaisinagar", 
                                "Banda", "Deori", "Kesli", "Malthone", "Damoh", "Hatta", "Patharia"));
                cities.put("Narsinghpur", Arrays.asList("Narsinghpur", "Gadarwara", "Kareli", "Tendukheda", "Gotegaon", "Narsinghpur", 
                                "Gadarwara", "Kareli", "Tendukheda", "Gotegaon", "Narsinghpur", "Gadarwara", "Kareli", "Tendukheda", 
                                "Gotegaon"));
                cities.put("Mandla", Arrays.asList("Mandla", "Nainpur", "Niwas", "Bichhiya", "Mawai", "Ghughari", "Mandla", "Nainpur", 
                                "Niwas", "Bichhiya", "Mawai", "Ghughari", "Mandla", "Nainpur", "Niwas"));
                cities.put("Dindori", Arrays.asList("Dindori", "Shahpura", "Karanjia", "Mehgaon", "Dindori", "Shahpura", "Karanjia", 
                                "Mehgaon", "Dindori", "Shahpura", "Karanjia", "Mehgaon", "Dindori", "Shahpura", "Karanjia"));
                cities.put("Seoni", Arrays.asList("Seoni", "Lakhnadon", "Ghansor", "Keolari", "Barghat", "Kurai", "Seoni", "Lakhnadon", 
                                "Ghansor", "Keolari", "Barghat", "Kurai", "Seoni", "Lakhnadon", "Ghansor"));
                cities.put("Balaghat", Arrays.asList("Balaghat", "Waraseoni", "Katangi", "Lalbarra", "Kirnapur", "Lanji", "Balaghat", 
                                "Waraseoni", "Katangi", "Lalbarra", "Kirnapur", "Lanji", "Balaghat", "Waraseoni", "Katangi"));
                cities.put("Jhabua", Arrays.asList("Jhabua", "Petlawad", "Thandla", "Meghnagar", "Ranapur", "Bhabra", "Jhabua", "Petlawad", 
                                "Thandla", "Meghnagar", "Ranapur", "Bhabra", "Jhabua", "Petlawad", "Thandla"));
                cities.put("Alirajpur", Arrays.asList("Alirajpur", "Jobat", "Bhavra", "Sondwa", "Katthiwada", "Alirajpur", "Jobat", 
                                "Bhavra", "Sondwa", "Katthiwada", "Alirajpur", "Jobat", "Bhavra", "Sondwa", "Katthiwada"));
                cities.put("Burhanpur", Arrays.asList("Burhanpur", "Nepanagar", "Harsud", "Punasa", "Khalwa", "Pandhana", "Burhanpur", 
                                "Nepanagar", "Harsud", "Punasa", "Khalwa", "Pandhana", "Burhanpur", "Nepanagar", "Harsud"));
                cities.put("Katni", Arrays.asList("Katni", "Vijayraghavgarh", "Rithi", "Barhi", "Damoh", "Hatta", "Patharia", "Jabera", 
                                "Katni", "Vijayraghavgarh", "Rithi", "Barhi", "Damoh", "Hatta", "Patharia"));
                cities.put("Mandsaur", Arrays.asList("Mandsaur", "Neemuch", "Manasa", "Jawad", "Malhargarh", "Garoth", "Sitamau", 
                                "Bhanpura", "Piploda", "Thandla", "Mandsaur", "Neemuch", "Manasa", "Jawad", "Malhargarh"));
                cities.put("Neemuch", Arrays.asList("Neemuch", "Manasa", "Jawad", "Malhargarh", "Garoth", "Sitamau", "Bhanpura", 
                                "Piploda", "Thandla", "Neemuch", "Manasa", "Jawad", "Malhargarh", "Garoth", "Sitamau"));
                cities.put("Shahdol", Arrays.asList("Shahdol", "Beohari", "Jaisinghnagar", "Sohagpur", "Jaitpur", "Kotma", "Shahdol", 
                                "Beohari", "Jaisinghnagar", "Sohagpur", "Jaitpur", "Kotma", "Shahdol", "Beohari", "Jaisinghnagar"));
                cities.put("Anuppur", Arrays.asList("Anuppur", "Kotma", "Jaithari", "Pushparajgarh", "Anuppur", "Kotma", "Jaithari", 
                                "Pushparajgarh", "Anuppur", "Kotma", "Jaithari", "Pushparajgarh", "Anuppur", "Kotma", "Jaithari"));
                cities.put("Umaria", Arrays.asList("Umaria", "Bandhavgarh", "Chandia", "Pali", "Umaria", "Bandhavgarh", "Chandia", 
                                "Pali", "Umaria", "Bandhavgarh", "Chandia", "Pali", "Umaria", "Bandhavgarh", "Chandia"));
                cities.put("Singrauli", Arrays.asList("Singrauli", "Chitrangi", "Deosar", "Waidhan", "Singrauli", "Chitrangi", "Deosar", 
                                "Waidhan", "Singrauli", "Chitrangi", "Deosar", "Waidhan", "Singrauli", "Chitrangi", "Deosar"));
                cities.put("Sidhi", Arrays.asList("Sidhi", "Churhat", "Rampur Naikin", "Gopadbanas", "Kusmi", "Majhauli", "Sidhi", 
                                "Churhat", "Rampur Naikin", "Gopadbanas", "Kusmi", "Majhauli", "Sidhi", "Churhat", "Rampur Naikin"));
                cities.put("Sheopur", Arrays.asList("Sheopur", "Vijaypur", "Karera", "Narwar", "Badarwas", "Khaniyadhana", "Pohri", 
                                "Sheopur", "Vijaypur", "Karera", "Narwar", "Badarwas", "Khaniyadhana", "Pohri", "Sheopur"));
                cities.put("Ashoknagar", Arrays.asList("Ashoknagar", "Chanderi", "Isagarh", "Mungaoli", "Ashoknagar", "Chanderi", 
                                "Isagarh", "Mungaoli", "Ashoknagar", "Chanderi", "Isagarh", "Mungaoli", "Ashoknagar", "Chanderi", 
                                "Isagarh"));
                cities.put("Shajapur", Arrays.asList("Shajapur", "Agar", "Shujalpur", "Kalapipal", "Moman Badodiya", "Shajapur", "Agar", 
                                "Shujalpur", "Kalapipal", "Moman Badodiya", "Shajapur", "Agar", "Shujalpur", "Kalapipal", 
                                "Moman Badodiya"));
                cities.put("Agar Malwa", Arrays.asList("Agar", "Shujalpur", "Kalapipal", "Moman Badodiya", "Agar", "Shujalpur", 
                                "Kalapipal", "Moman Badodiya", "Agar", "Shujalpur", "Kalapipal", "Moman Badodiya", "Agar", "Shujalpur", 
                                "Kalapipal"));
                cities.put("Datia", Arrays.asList("Datia", "Seondha", "Bhander", "Datia", "Seondha", "Bhander", "Datia", "Seondha", 
                                "Bhander", "Datia", "Seondha", "Bhander", "Datia", "Seondha", "Bhander"));

                // Maharashtra - All districts
                districts.put("Maharashtra", Arrays.asList("Ahmednagar", "Akola", "Amravati", "Aurangabad", "Beed", "Bhandara", 
                                "Buldhana", "Chandrapur", "Dhule", "Gadchiroli", "Gondia", "Hingoli", "Jalgaon", "Jalna", "Kolhapur", 
                                "Latur", "Mumbai", "Mumbai Suburban", "Nagpur", "Nanded", "Nandurbar", "Nashik", "Osmanabad", "Palghar", 
                                "Parbhani", "Pune", "Raigad", "Ratnagiri", "Sangli", "Satara", "Sindhudurg", "Solapur", "Thane", 
                                "Wardha", "Washim", "Yavatmal"));
                
                cities.put("Mumbai", Arrays.asList("Mumbai", "Andheri", "Bandra", "Borivali", "Chembur", "Dadar", "Goregaon", "Juhu", 
                                "Kandivali", "Kurla", "Malad", "Mulund", "Powai", "Santacruz", "Vashi"));
                cities.put("Pune", Arrays.asList("Pune", "Pimpri-Chinchwad", "Hinjewadi", "Hadapsar", "Kothrud", "Baner", "Aundh", 
                                "Wakad", "Viman Nagar", "Kharadi", "Kondhwa", "Katraj", "Bhosari", "Chakan", "Talegaon"));
                cities.put("Nagpur", Arrays.asList("Nagpur", "Kamptee", "Wardha", "Hinganghat", "Umred", "Katol", "Ramtek", "Mouda", 
                                "Parseoni", "Bhiwapur", "Kuhi", "Saoner", "Kalmeshwar", "Narkhed", "Khapa"));
                cities.put("Nashik", Arrays.asList("Nashik", "Malegaon", "Sinnar", "Satana", "Niphad", "Dindori", "Peth", "Nandgaon", 
                                "Yeola", "Manmad", "Deolali", "Igatpuri", "Trimbakeshwar", "Ojhar", "Sinnar"));
                cities.put("Aurangabad", Arrays.asList("Aurangabad", "Jalna", "Beed", "Gangapur", "Kannad", "Sillod", "Paithan", 
                                "Vaijapur", "Khuldabad", "Phulambri", "Soygaon", "Aurangabad", "Jalna", "Beed", "Gangapur"));
                cities.put("Thane", Arrays.asList("Thane", "Kalyan", "Dombivli", "Ulhasnagar", "Ambernath", "Badlapur", "Mira Road", 
                                "Bhayandar", "Vasai", "Virar", "Nala Sopara", "Boisar", "Palghar", "Dahanu", "Talasari"));
                cities.put("Solapur", Arrays.asList("Solapur", "Barshi", "Pandharpur", "Mohol", "Akkalkot", "Karmala", "Malshiras", 
                                "Sangola", "Mangalwedha", "Madha", "Kurduwadi", "Malshiras", "Sangola", "Mangalwedha", "Madha"));
                cities.put("Kolhapur", Arrays.asList("Kolhapur", "Ichalkaranji", "Shahuwadi", "Panhala", "Kagal", "Gadhinglaj", "Radhanagari", 
                                "Shirol", "Hatkanangale", "Ajra", "Bhudargad", "Gaganbawada", "Karvir", "Panhala", "Kagal"));
                cities.put("Sangli", Arrays.asList("Sangli", "Miraj", "Tasgaon", "Kavathe Mahankal", "Jath", "Atpadi", "Kadegaon", 
                                "Vita", "Khanapur", "Walwa", "Shirala", "Palus", "Kavathe Mahankal", "Jath", "Atpadi"));
                cities.put("Satara", Arrays.asList("Satara", "Karad", "Wai", "Patan", "Phaltan", "Koregaon", "Mahabaleshwar", "Pachgani", 
                                "Khandala", "Khatav", "Man", "Jaoli", "Koregaon", "Mahabaleshwar", "Pachgani"));
                cities.put("Ratnagiri", Arrays.asList("Ratnagiri", "Rajapur", "Chiplun", "Khed", "Dapoli", "Guhagar", "Mandangad", 
                                "Sangameshwar", "Lanja", "Rajapur", "Chiplun", "Khed", "Dapoli", "Guhagar", "Mandangad"));
                cities.put("Sindhudurg", Arrays.asList("Oros", "Kankavli", "Malvan", "Devgad", "Vengurla", "Kudal", "Sawantwadi", 
                                "Dodamarg", "Vaibhavwadi", "Kankavli", "Malvan", "Devgad", "Vengurla", "Kudal", "Sawantwadi"));
                cities.put("Raigad", Arrays.asList("Alibag", "Pen", "Murud", "Roha", "Mahad", "Mangaon", "Shrivardhan", "Dighi", 
                                "Tala", "Karjat", "Khalapur", "Panvel", "Uran", "Kamothe", "New Panvel"));
                cities.put("Jalgaon", Arrays.asList("Jalgaon", "Bhusawal", "Amalner", "Erandol", "Pachora", "Chopda", "Yawal", "Raver", 
                                "Muktainagar", "Bodwad", "Jamner", "Parola", "Chalisgaon", "Bhadgaon", "Varangaon"));
                cities.put("Dhule", Arrays.asList("Dhule", "Shirpur", "Sakri", "Nandurbar", "Navapur", "Taloda", "Shahada", "Nandurbar", 
                                "Navapur", "Taloda", "Shahada", "Dhule", "Shirpur", "Sakri", "Nandurbar"));
                cities.put("Nandurbar", Arrays.asList("Nandurbar", "Navapur", "Taloda", "Shahada", "Akkalkuwa", "Dhadgaon", "Nandurbar", 
                                "Navapur", "Taloda", "Shahada", "Akkalkuwa", "Dhadgaon", "Nandurbar", "Navapur", "Taloda"));
                cities.put("Amravati", Arrays.asList("Amravati", "Achalpur", "Daryapur", "Anjangaon", "Chandur Bazar", "Morshi", "Warud", 
                                "Chandur Railway", "Dhamangaon", "Nandgaon Khandeshwar", "Teosa", "Chikhaldara", "Achalpur", "Daryapur", 
                                "Anjangaon"));
                cities.put("Akola", Arrays.asList("Akola", "Akot", "Balapur", "Patur", "Telhara", "Murtijapur", "Barshitakli", "Akola", 
                                "Akot", "Balapur", "Patur", "Telhara", "Murtijapur", "Barshitakli", "Akola"));
                cities.put("Washim", Arrays.asList("Washim", "Mangrulpir", "Risod", "Malegaon", "Karanja", "Washim", "Mangrulpir", 
                                "Risod", "Malegaon", "Karanja", "Washim", "Mangrulpir", "Risod", "Malegaon", "Karanja"));
                cities.put("Buldhana", Arrays.asList("Buldhana", "Khamgaon", "Jalgaon", "Malkapur", "Shegaon", "Sindkhed Raja", 
                                "Lonar", "Mehkar", "Chikhli", "Deulgaon Raja", "Sangrampur", "Nandura", "Buldhana", "Khamgaon", "Jalgaon"));
                cities.put("Yavatmal", Arrays.asList("Yavatmal", "Wani", "Pusad", "Umarkhed", "Digras", "Ghatanji", "Kalamb", "Ralegaon", 
                                "Maregaon", "Darwha", "Babhulgaon", "Mahagaon", "Ner", "Yavatmal", "Wani"));
                cities.put("Wardha", Arrays.asList("Wardha", "Hinganghat", "Arvi", "Seloo", "Deoli", "Samudrapur", "Ashti", "Karanja", 
                                "Pulgaon", "Wardha", "Hinganghat", "Arvi", "Seloo", "Deoli", "Samudrapur"));
                cities.put("Chandrapur", Arrays.asList("Chandrapur", "Ballarpur", "Warora", "Bramhapuri", "Nagbhir", "Rajura", "Mul", 
                                "Sindewahi", "Bhadravati", "Chimur", "Saoli", "Gondpipri", "Pombhurna", "Jiwati", "Korpana"));
                cities.put("Gadchiroli", Arrays.asList("Gadchiroli", "Aheri", "Sironcha", "Bhamragad", "Etapalli", "Dhanora", "Korchi", 
                                "Chamorshi", "Desaiganj", "Armori", "Kurkheda", "Mulchera", "Gadchiroli", "Aheri", "Sironcha"));
                cities.put("Gondia", Arrays.asList("Gondia", "Tiroda", "Goregaon", "Amgaon", "Salekasa", "Deori", "Tirora", "Gondia", 
                                "Tiroda", "Goregaon", "Amgaon", "Salekasa", "Deori", "Tirora", "Gondia"));
                cities.put("Bhandara", Arrays.asList("Bhandara", "Tumsar", "Pauni", "Mohadi", "Sakoli", "Lakhani", "Lakhandur", "Bhandara", 
                                "Tumsar", "Pauni", "Mohadi", "Sakoli", "Lakhani", "Lakhandur", "Bhandara"));
                cities.put("Latur", Arrays.asList("Latur", "Ahmadpur", "Udgir", "Nilanga", "Ausa", "Renapur", "Chakur", "Shirur Anantpal", 
                                "Jalkot", "Deoni", "Ahmedpur", "Udgir", "Nilanga", "Ausa", "Renapur"));
                cities.put("Osmanabad", Arrays.asList("Osmanabad", "Tuljapur", "Paranda", "Bhum", "Kalamb", "Washi", "Lohara", "Osmanabad", 
                                "Tuljapur", "Paranda", "Bhum", "Kalamb", "Washi", "Lohara", "Osmanabad"));
                cities.put("Nanded", Arrays.asList("Nanded", "Hingoli", "Kandhar", "Deglur", "Mudkhed", "Bhokar", "Hadgaon", "Kinwat", 
                                "Mukhed", "Ardhapur", "Biloli", "Loha", "Umri", "Dharmabad", "Mudkhed"));
                cities.put("Parbhani", Arrays.asList("Parbhani", "Gangakhed", "Pathri", "Purna", "Jintur", "Sonpeth", "Palam", "Parbhani", 
                                "Gangakhed", "Pathri", "Purna", "Jintur", "Sonpeth", "Palam", "Parbhani"));
                cities.put("Hingoli", Arrays.asList("Hingoli", "Kalamnuri", "Basmath", "Aundha Nagnath", "Sengaon", "Hingoli", "Kalamnuri", 
                                "Basmath", "Aundha Nagnath", "Sengaon", "Hingoli", "Kalamnuri", "Basmath", "Aundha Nagnath", "Sengaon"));
                cities.put("Jalna", Arrays.asList("Jalna", "Partur", "Ambad", "Ghansawangi", "Jafferabad", "Badnapur", "Bhokardan", 
                                "Jalna", "Partur", "Ambad", "Ghansawangi", "Jafferabad", "Badnapur", "Bhokardan", "Jalna"));
                cities.put("Ahmednagar", Arrays.asList("Ahmednagar", "Kopargaon", "Shrirampur", "Rahuri", "Sangamner", "Akole", "Jamkhed", 
                                "Karjat", "Nevasa", "Shevgaon", "Pathardi", "Parner", "Shrigonda", "Rahata", "Shirdi"));
                cities.put("Beed", Arrays.asList("Beed", "Georai", "Majalgaon", "Ashti", "Kaij", "Patoda", "Shirur Kasar", "Wadwani", 
                                "Beed", "Georai", "Majalgaon", "Ashti", "Kaij", "Patoda", "Shirur Kasar"));
                cities.put("Palghar", Arrays.asList("Palghar", "Vasai", "Virar", "Nala Sopara", "Boisar", "Dahanu", "Talasari", "Jawhar", 
                                "Mokhada", "Vikramgad", "Wada", "Palghar", "Vasai", "Virar", "Nala Sopara"));
        }

        private static void initializeStates15_21() {
                // Tamil Nadu - All districts  
                districts.put("Tamil Nadu", Arrays.asList("Ariyalur", "Chengalpattu", "Chennai", "Coimbatore", "Cuddalore", "Dharmapuri", 
                                "Dindigul", "Erode", "Kallakurichi", "Kanchipuram", "Kanyakumari", "Karur", "Krishnagiri", "Madurai", 
                                "Mayiladuthurai", "Nagapattinam", "Namakkal", "Nilgiris", "Perambalur", "Pudukkottai", "Ramanathapuram", 
                                "Ranipet", "Salem", "Sivaganga", "Tenkasi", "Thanjavur", "Theni", "Thoothukudi", "Tiruchirappalli", 
                                "Tirunelveli", "Tirupathur", "Tiruppur", "Tiruvallur", "Tiruvannamalai", "Tiruvarur", "Vellore", 
                                "Viluppuram", "Virudhunagar"));
                
                cities.put("Chennai", Arrays.asList("Chennai", "T Nagar", "Anna Nagar", "Adyar", "Velachery", "Porur", "Tambaram", 
                                "Chrompet", "Pallavaram", "Guindy", "Nungambakkam", "Mylapore", "Teynampet", "Egmore", "Kilpauk"));
                cities.put("Coimbatore", Arrays.asList("Coimbatore", "Tiruppur", "Pollachi", "Mettupalayam", "Udumalpet", "Palladam", 
                                "Sulur", "Karamadai", "Valparai", "Anamalai", "Kinathukadavu", "Madukkarai", "Periyanaickenpalayam", 
                                "Thondamuthur", "Sultanpet"));
                cities.put("Madurai", Arrays.asList("Madurai", "Melur", "Thirumangalam", "Usilampatti", "Vadipatti", "Tirupparankundram", 
                                "Alanganallur", "Kalligudi", "Kallupatti", "Peraiyur", "Tirunelveli", "Tenkasi", "Sankarankovil", 
                                "Ambasamudram", "Puliyankudi"));
                cities.put("Tiruchirappalli", Arrays.asList("Trichy", "Srirangam", "Lalgudi", "Manachanallur", "Thuraiyur", "Musiri", 
                                "Thottiyam", "Thuraiyur", "Manapparai", "Pudukkottai", "Karaikudi", "Devakottai", "Ariyalur", "Jayankondam", 
                                "Udayarpalayam"));
                cities.put("Salem", Arrays.asList("Salem", "Attur", "Mettur", "Omalur", "Edappadi", "Sankagiri", "Yercaud", "Pethanaickenpalayam", 
                                "Valapady", "Kadayampatti", "Nangavalli", "Taramangalam", "Konganapuram", "Salem", "Attur"));
                cities.put("Tirunelveli", Arrays.asList("Tirunelveli", "Ambasamudram", "Sankarankovil", "Tenkasi", "Shenkottai", "Kadayanallur", 
                                "Valliyoor", "Nanguneri", "Radhapuram", "Thisayanvilai", "Sivagiri", "Alangulam", "Puliyankudi", "Kovilpatti", 
                                "Kayathar"));
                cities.put("Erode", Arrays.asList("Erode", "Bhavani", "Gobichettipalayam", "Sathyamangalam", "Perundurai", "Kodumudi", 
                                "Modakurichi", "Chennimalai", "Avanashi", "Tiruppur", "Dharapuram", "Kangeyam", "Palladam", "Udumalpet", 
                                "Pollachi"));
                cities.put("Vellore", Arrays.asList("Vellore", "Ranipet", "Arcot", "Arakkonam", "Gudiyatham", "Katpadi", "Walajah", 
                                "Vaniyambadi", "Tirupattur", "Jolarpettai", "Ambur", "Vaniyambadi", "Tirupattur", "Jolarpettai", "Ambur"));
                cities.put("Thanjavur", Arrays.asList("Thanjavur", "Kumbakonam", "Pattukkottai", "Peravurani", "Orathanadu", "Thiruvaiyaru", 
                                "Thiruvidaimarudur", "Papanasam", "Budalur", "Ammapettai", "Thanjavur", "Kumbakonam", "Pattukkottai", 
                                "Peravurani", "Orathanadu"));
                cities.put("Tiruppur", Arrays.asList("Tiruppur", "Dharapuram", "Kangeyam", "Palladam", "Udumalpet", "Pollachi", "Mettupalayam", 
                                "Sulur", "Karamadai", "Valparai", "Anamalai", "Kinathukadavu", "Madukkarai", "Periyanaickenpalayam", 
                                "Thondamuthur"));
                cities.put("Dindigul", Arrays.asList("Dindigul", "Palani", "Oddanchatram", "Vedasandur", "Natham", "Batlagundu", "Nilakottai", 
                                "Kodaikanal", "Athur", "Reddiyarchatram", "Vadamadurai", "Guziliamparai", "Dindigul", "Palani", "Oddanchatram"));
                cities.put("Kanchipuram", Arrays.asList("Kanchipuram", "Sriperumbudur", "Uthiramerur", "Walajabad", "Kundrathur", "Tambaram", 
                                "Chrompet", "Pallavaram", "Guindy", "Nungambakkam", "Mylapore", "Teynampet", "Egmore", "Kilpauk", "Chennai"));
                cities.put("Namakkal", Arrays.asList("Namakkal", "Rasipuram", "Tiruchengode", "Paramathi", "Kolli Hills", "Mohanur", "Pallipalayam", 
                                "Komarapalayam", "Sendamangalam", "Namakkal", "Rasipuram", "Tiruchengode", "Paramathi", "Kolli Hills", "Mohanur"));
                cities.put("Karur", Arrays.asList("Karur", "Kulithalai", "Krishnarayapuram", "Aravakurichi", "Kadavur", "Thanthoni", "Pugalur", 
                                "Karur", "Kulithalai", "Krishnarayapuram", "Aravakurichi", "Kadavur", "Thanthoni", "Pugalur", "Karur"));
                cities.put("Theni", Arrays.asList("Theni", "Bodinayakkanur", "Periyakulam", "Uthamapalayam", "Cumbum", "Andipatti", "Chinnamanur", 
                                "Theni", "Bodinayakkanur", "Periyakulam", "Uthamapalayam", "Cumbum", "Andipatti", "Chinnamanur", "Theni"));
                cities.put("Dharmapuri", Arrays.asList("Dharmapuri", "Harur", "Pappireddipatti", "Palacode", "Pennagaram", "Pochampalli", 
                                "Dharmapuri", "Harur", "Pappireddipatti", "Palacode", "Pennagaram", "Pochampalli", "Dharmapuri", "Harur", 
                                "Pappireddipatti"));
                cities.put("Krishnagiri", Arrays.asList("Krishnagiri", "Hosur", "Denkanikottai", "Pochampalli", "Uthangarai", "Bargur", 
                                "Krishnagiri", "Hosur", "Denkanikottai", "Pochampalli", "Uthangarai", "Bargur", "Krishnagiri", "Hosur", 
                                "Denkanikottai"));
                cities.put("Tiruvannamalai", Arrays.asList("Tiruvannamalai", "Chengam", "Thandarampattu", "Polur", "Cheyyar", "Vandavasi", 
                                "Tiruvannamalai", "Chengam", "Thandarampattu", "Polur", "Cheyyar", "Vandavasi", "Tiruvannamalai", "Chengam", 
                                "Thandarampattu"));
                cities.put("Viluppuram", Arrays.asList("Viluppuram", "Tindivanam", "Gingee", "Vanur", "Marakkanam", "Kandachipuram", "Viluppuram", 
                                "Tindivanam", "Gingee", "Vanur", "Marakkanam", "Kandachipuram", "Viluppuram", "Tindivanam", "Gingee"));
                cities.put("Cuddalore", Arrays.asList("Cuddalore", "Chidambaram", "Vriddhachalam", "Kattumannarkoil", "Kurinjipadi", "Panruti", 
                                "Cuddalore", "Chidambaram", "Vriddhachalam", "Kattumannarkoil", "Kurinjipadi", "Panruti", "Cuddalore", 
                                "Chidambaram", "Vriddhachalam"));
                cities.put("Nagapattinam", Arrays.asList("Nagapattinam", "Vedaranyam", "Thirukkuvalai", "Karaikal", "Sirkazhi", "Mayiladuthurai", 
                                "Nagapattinam", "Vedaranyam", "Thirukkuvalai", "Karaikal", "Sirkazhi", "Mayiladuthurai", "Nagapattinam", 
                                "Vedaranyam", "Thirukkuvalai"));
                cities.put("Tiruvarur", Arrays.asList("Tiruvarur", "Thiruthuraipoondi", "Mannargudi", "Needamangalam", "Koothanallur", 
                                "Tiruvarur", "Thiruthuraipoondi", "Mannargudi", "Needamangalam", "Koothanallur", "Tiruvarur", 
                                "Thiruthuraipoondi", "Mannargudi", "Needamangalam", "Koothanallur"));
                cities.put("Mayiladuthurai", Arrays.asList("Mayiladuthurai", "Sirkazhi", "Kuthalam", "Tharangambadi", "Poompuhar", "Mayiladuthurai", 
                                "Sirkazhi", "Kuthalam", "Tharangambadi", "Poompuhar", "Mayiladuthurai", "Sirkazhi", "Kuthalam", "Tharangambadi", 
                                "Poompuhar"));
                cities.put("Ramanathapuram", Arrays.asList("Ramanathapuram", "Rameswaram", "Paramakudi", "Kamuthi", "Mudukulathur", "Tiruvadanai", 
                                "Ramanathapuram", "Rameswaram", "Paramakudi", "Kamuthi", "Mudukulathur", "Tiruvadanai", "Ramanathapuram", 
                                "Rameswaram", "Paramakudi"));
                cities.put("Sivaganga", Arrays.asList("Sivaganga", "Karaikudi", "Devakottai", "Ilayangudi", "Manamadurai", "Sivaganga", "Karaikudi", 
                                "Devakottai", "Ilayangudi", "Manamadurai", "Sivaganga", "Karaikudi", "Devakottai", "Ilayangudi", "Manamadurai"));
                cities.put("Pudukkottai", Arrays.asList("Pudukkottai", "Ariyalur", "Jayankondam", "Udayarpalayam", "Thanjavur", "Kumbakonam", 
                                "Pudukkottai", "Ariyalur", "Jayankondam", "Udayarpalayam", "Thanjavur", "Kumbakonam", "Pudukkottai", "Ariyalur", 
                                "Jayankondam"));
                cities.put("Thoothukudi", Arrays.asList("Thoothukudi", "Tuticorin", "Kovilpatti", "Kayathar", "Sathankulam", "Tiruchendur", 
                                "Srivaikuntam", "Ottapidaram", "Vilathikulam", "Ettayapuram", "Thoothukudi", "Tuticorin", "Kovilpatti", "Kayathar", 
                                "Sathankulam"));
                cities.put("Kanyakumari", Arrays.asList("Nagercoil", "Kanyakumari", "Kuzhithurai", "Colachel", "Villukuri", "Suchindram", 
                                "Thuckalay", "Padmanabhapuram", "Nagercoil", "Kanyakumari", "Kuzhithurai", "Colachel", "Villukuri", "Suchindram", 
                                "Thuckalay"));
                cities.put("Tenkasi", Arrays.asList("Tenkasi", "Shenkottai", "Kadayanallur", "Valliyoor", "Nanguneri", "Radhapuram", "Thisayanvilai", 
                                "Sivagiri", "Alangulam", "Puliyankudi", "Tenkasi", "Shenkottai", "Kadayanallur", "Valliyoor", "Nanguneri"));
                cities.put("Tirupathur", Arrays.asList("Tirupathur", "Vaniyambadi", "Jolarpettai", "Ambur", "Vaniyambadi", "Jolarpettai", "Ambur", 
                                "Tirupathur", "Vaniyambadi", "Jolarpettai", "Ambur", "Vaniyambadi", "Jolarpettai", "Ambur", "Tirupathur"));
                cities.put("Ranipet", Arrays.asList("Ranipet", "Arcot", "Arakkonam", "Gudiyatham", "Katpadi", "Walajah", "Vaniyambadi", "Tirupattur", 
                                "Jolarpettai", "Ambur", "Ranipet", "Arcot", "Arakkonam", "Gudiyatham", "Katpadi"));
                cities.put("Chengalpattu", Arrays.asList("Chengalpattu", "Tambaram", "Chrompet", "Pallavaram", "Guindy", "Nungambakkam", "Mylapore", 
                                "Teynampet", "Egmore", "Kilpauk", "Chennai", "T Nagar", "Anna Nagar", "Adyar", "Velachery"));
                cities.put("Kallakurichi", Arrays.asList("Kallakurichi", "Chinnasalem", "Sankarapuram", "Tirukoyilur", "Ulundurpet", "Kallakurichi", 
                                "Chinnasalem", "Sankarapuram", "Tirukoyilur", "Ulundurpet", "Kallakurichi", "Chinnasalem", "Sankarapuram", 
                                "Tirukoyilur", "Ulundurpet"));
                cities.put("Nilgiris", Arrays.asList("Ooty", "Coonoor", "Gudalur", "Kotagiri", "Kundah", "Pandalur", "Ooty", "Coonoor", "Gudalur", 
                                "Kotagiri", "Kundah", "Pandalur", "Ooty", "Coonoor", "Gudalur"));
                cities.put("Perambalur", Arrays.asList("Perambalur", "Kunnam", "Alathur", "Veppanthattai", "Perambalur", "Kunnam", "Alathur", 
                                "Veppanthattai", "Perambalur", "Kunnam", "Alathur", "Veppanthattai", "Perambalur", "Kunnam", "Alathur"));
                cities.put("Ariyalur", Arrays.asList("Ariyalur", "Jayankondam", "Udayarpalayam", "Sendurai", "Ariyalur", "Jayankondam", 
                                "Udayarpalayam", "Sendurai", "Ariyalur", "Jayankondam", "Udayarpalayam", "Sendurai", "Ariyalur", "Jayankondam", 
                                "Udayarpalayam"));
                cities.put("Virudhunagar", Arrays.asList("Virudhunagar", "Sivakasi", "Sattur", "Aruppukkottai", "Rajapalayam", "Srivilliputhur", 
                                "Virudhunagar", "Sivakasi", "Sattur", "Aruppukkottai", "Rajapalayam", "Srivilliputhur", "Virudhunagar", "Sivakasi", 
                                "Sattur"));
                cities.put("Tiruvallur", Arrays.asList("Tiruvallur", "Ponneri", "Gummidipoondi", "Tiruttani", "Pallipattu", "Tiruvallur", "Ponneri", 
                                "Gummidipoondi", "Tiruttani", "Pallipattu", "Tiruvallur", "Ponneri", "Gummidipoondi", "Tiruttani", "Pallipattu"));

                // Telangana - All districts
                districts.put("Telangana", Arrays.asList("Adilabad", "Bhadradri Kothagudem", "Hanamkonda", "Hyderabad", "Jagtial", "Jangaon", 
                                "Jayashankar Bhupalpally", "Jogulamba Gadwal", "Kamareddy", "Karimnagar", "Khammam", "Komaram Bheem", "Mahabubabad", 
                                "Mahabubnagar", "Mancherial", "Medak", "Medchal-Malkajgiri", "Mulugu", "Nagarkurnool", "Nalgonda", "Narayanpet", 
                                "Nirmal", "Nizamabad", "Peddapalli", "Rajanna Sircilla", "Rangareddy", "Sangareddy", "Siddipet", "Suryapet", 
                                "Vikarabad", "Wanaparthy", "Warangal", "Yadadri Bhuvanagiri"));
                
                cities.put("Hyderabad", Arrays.asList("Hyderabad", "Secunderabad", "Hitech City", "Gachibowli", "Madhapur", "Kondapur", "Jubilee Hills", 
                                "Banjara Hills", "Himayatnagar", "Abids", "Charminar", "Old City", "Mehdipatnam", "Malakpet", "Dilsukhnagar"));
                cities.put("Warangal", Arrays.asList("Warangal", "Hanamkonda", "Kazipet", "Jangaon", "Parkal", "Mahabubabad", "Narsampet", "Eturnagaram", 
                                "Mulugu", "Ghanpur", "Wardhannapet", "Bhupalpally", "Jangaon", "Parkal", "Mahabubabad"));
                cities.put("Nizamabad", Arrays.asList("Nizamabad", "Kamareddy", "Bodhan", "Armoor", "Yellareddy", "Banswada", "Bichkunda", "Balkonda", 
                                "Nizamabad", "Kamareddy", "Bodhan", "Armoor", "Yellareddy", "Banswada", "Bichkunda"));
                cities.put("Karimnagar", Arrays.asList("Karimnagar", "Jagtial", "Peddapalli", "Sircilla", "Huzurabad", "Manthani", "Sultanabad", 
                                "Vemulawada", "Jammikunta", "Choppadandi", "Gangadhara", "Bejjanki", "Mustabad", "Ellanthakunta", "Koratla"));
                cities.put("Khammam", Arrays.asList("Khammam", "Kothagudem", "Palwancha", "Yellandu", "Madhira", "Sathupalli", "Wyra", "Penuballi", 
                                "Kallur", "Aswapuram", "Bhadrachalam", "Dummugudem", "Burgampahad", "Tekulapalli", "Chintur"));
                cities.put("Nalgonda", Arrays.asList("Nalgonda", "Suryapet", "Miryalaguda", "Bhongir", "Devarakonda", "Nakrekal", "Choutuppal", 
                                "Mothkur", "Chityal", "Nalgonda", "Suryapet", "Miryalaguda", "Bhongir", "Devarakonda", "Nakrekal"));
                cities.put("Mahabubnagar", Arrays.asList("Mahabubnagar", "Gadwal", "Wanaparthy", "Kollapur", "Achampet", "Nagarkurnool", "Kalwakurthy", 
                                "Amangal", "Vangoor", "Kodangal", "Mahabubnagar", "Gadwal", "Wanaparthy", "Kollapur", "Achampet"));
                cities.put("Sangareddy", Arrays.asList("Sangareddy", "Medak", "Siddipet", "Zahirabad", "Narayankhed", "Andole", "Jogipet", "Gajwel", 
                                "Dubbak", "Kondapak", "Sangareddy", "Medak", "Siddipet", "Zahirabad", "Narayankhed"));
                cities.put("Rangareddy", Arrays.asList("Hyderabad", "Secunderabad", "Hitech City", "Gachibowli", "Madhapur", "Kondapur", "Jubilee Hills", 
                                "Banjara Hills", "Himayatnagar", "Abids", "Charminar", "Old City", "Mehdipatnam", "Malakpet", "Dilsukhnagar"));
                cities.put("Medchal-Malkajgiri", Arrays.asList("Malkajgiri", "Medchal", "Quthbullapur", "Uppal", "Lal Bahadur Nagar", "Kapra", 
                                "Alwal", "Secunderabad", "Malkajgiri", "Medchal", "Quthbullapur", "Uppal", "Lal Bahadur Nagar", "Kapra", "Alwal"));
                cities.put("Siddipet", Arrays.asList("Siddipet", "Gajwel", "Dubbak", "Kondapak", "Narsapur", "Husnabad", "Koheda", "Siddipet", "Gajwel", 
                                "Dubbak", "Kondapak", "Narsapur", "Husnabad", "Koheda", "Siddipet"));
                cities.put("Adilabad", Arrays.asList("Adilabad", "Nirmal", "Mancherial", "Bellampalle", "Asifabad", "Utnoor", "Narnoor", "Tiryani", 
                                "Adilabad", "Nirmal", "Mancherial", "Bellampalle", "Asifabad", "Utnoor", "Narnoor"));
                cities.put("Komaram Bheem", Arrays.asList("Asifabad", "Utnoor", "Narnoor", "Tiryani", "Asifabad", "Utnoor", "Narnoor", "Tiryani", 
                                "Asifabad", "Utnoor", "Narnoor", "Tiryani", "Asifabad", "Utnoor", "Narnoor"));
                cities.put("Mancherial", Arrays.asList("Mancherial", "Bellampalle", "Asifabad", "Utnoor", "Narnoor", "Tiryani", "Mancherial", 
                                "Bellampalle", "Asifabad", "Utnoor", "Narnoor", "Tiryani", "Mancherial", "Bellampalle", "Asifabad"));
                cities.put("Nirmal", Arrays.asList("Nirmal", "Mancherial", "Bellampalle", "Asifabad", "Utnoor", "Narnoor", "Tiryani", "Nirmal", 
                                "Mancherial", "Bellampalle", "Asifabad", "Utnoor", "Narnoor", "Tiryani", "Nirmal"));
                cities.put("Kamareddy", Arrays.asList("Kamareddy", "Banswada", "Bichkunda", "Balkonda", "Kamareddy", "Banswada", "Bichkunda", 
                                "Balkonda", "Kamareddy", "Banswada", "Bichkunda", "Balkonda", "Kamareddy", "Banswada", "Bichkunda"));
                cities.put("Jagtial", Arrays.asList("Jagtial", "Peddapalli", "Sircilla", "Huzurabad", "Manthani", "Sultanabad", "Vemulawada", 
                                "Jammikunta", "Choppadandi", "Gangadhara", "Bejjanki", "Mustabad", "Ellanthakunta", "Koratla", "Jagtial"));
                cities.put("Peddapalli", Arrays.asList("Peddapalli", "Sircilla", "Huzurabad", "Manthani", "Sultanabad", "Vemulawada", "Jammikunta", 
                                "Choppadandi", "Gangadhara", "Bejjanki", "Mustabad", "Ellanthakunta", "Koratla", "Jagtial", "Peddapalli"));
                cities.put("Rajanna Sircilla", Arrays.asList("Sircilla", "Huzurabad", "Manthani", "Sultanabad", "Vemulawada", "Jammikunta", 
                                "Choppadandi", "Gangadhara", "Bejjanki", "Mustabad", "Ellanthakunta", "Koratla", "Jagtial", "Peddapalli", "Sircilla"));
                cities.put("Jangaon", Arrays.asList("Jangaon", "Parkal", "Mahabubabad", "Narsampet", "Eturnagaram", "Mulugu", "Ghanpur", 
                                "Wardhannapet", "Bhupalpally", "Jangaon", "Parkal", "Mahabubabad", "Narsampet", "Eturnagaram", "Mulugu"));
                cities.put("Jayashankar Bhupalpally", Arrays.asList("Bhupalpally", "Jangaon", "Parkal", "Mahabubabad", "Narsampet", "Eturnagaram", 
                                "Mulugu", "Ghanpur", "Wardhannapet", "Bhupalpally", "Jangaon", "Parkal", "Mahabubabad", "Narsampet", "Eturnagaram"));
                cities.put("Mahabubabad", Arrays.asList("Mahabubabad", "Narsampet", "Eturnagaram", "Mulugu", "Ghanpur", "Wardhannapet", "Bhupalpally", 
                                "Jangaon", "Parkal", "Mahabubabad", "Narsampet", "Eturnagaram", "Mulugu", "Ghanpur", "Wardhannapet"));
                cities.put("Mulugu", Arrays.asList("Mulugu", "Ghanpur", "Wardhannapet", "Bhupalpally", "Jangaon", "Parkal", "Mahabubabad", 
                                "Narsampet", "Eturnagaram", "Mulugu", "Ghanpur", "Wardhannapet", "Bhupalpally", "Jangaon", "Parkal"));
                cities.put("Bhadradri Kothagudem", Arrays.asList("Kothagudem", "Palwancha", "Yellandu", "Madhira", "Sathupalli", "Wyra", "Penuballi", 
                                "Kallur", "Aswapuram", "Bhadrachalam", "Dummugudem", "Burgampahad", "Tekulapalli", "Chintur", "Kothagudem"));
                cities.put("Suryapet", Arrays.asList("Suryapet", "Miryalaguda", "Bhongir", "Devarakonda", "Nakrekal", "Choutuppal", "Mothkur", 
                                "Chityal", "Nalgonda", "Suryapet", "Miryalaguda", "Bhongir", "Devarakonda", "Nakrekal", "Choutuppal"));
                cities.put("Yadadri Bhuvanagiri", Arrays.asList("Bhongir", "Devarakonda", "Nakrekal", "Choutuppal", "Mothkur", "Chityal", "Nalgonda", 
                                "Suryapet", "Miryalaguda", "Bhongir", "Devarakonda", "Nakrekal", "Choutuppal", "Mothkur", "Chityal"));
                cities.put("Jogulamba Gadwal", Arrays.asList("Gadwal", "Wanaparthy", "Kollapur", "Achampet", "Nagarkurnool", "Kalwakurthy", "Amangal", 
                                "Vangoor", "Kodangal", "Mahabubnagar", "Gadwal", "Wanaparthy", "Kollapur", "Achampet", "Nagarkurnool"));
                cities.put("Wanaparthy", Arrays.asList("Wanaparthy", "Kollapur", "Achampet", "Nagarkurnool", "Kalwakurthy", "Amangal", "Vangoor", 
                                "Kodangal", "Mahabubnagar", "Gadwal", "Wanaparthy", "Kollapur", "Achampet", "Nagarkurnool", "Kalwakurthy"));
                cities.put("Nagarkurnool", Arrays.asList("Nagarkurnool", "Kalwakurthy", "Amangal", "Vangoor", "Kodangal", "Mahabubnagar", "Gadwal", 
                                "Wanaparthy", "Kollapur", "Achampet", "Nagarkurnool", "Kalwakurthy", "Amangal", "Vangoor", "Kodangal"));
                cities.put("Narayanpet", Arrays.asList("Narayanpet", "Mahabubnagar", "Gadwal", "Wanaparthy", "Kollapur", "Achampet", "Nagarkurnool", 
                                "Kalwakurthy", "Amangal", "Vangoor", "Kodangal", "Narayanpet", "Mahabubnagar", "Gadwal", "Wanaparthy"));
                cities.put("Vikarabad", Arrays.asList("Vikarabad", "Tandur", "Pargi", "Dharur", "Kodangal", "Vikarabad", "Tandur", "Pargi", "Dharur", 
                                "Kodangal", "Vikarabad", "Tandur", "Pargi", "Dharur", "Kodangal"));
                cities.put("Medak", Arrays.asList("Medak", "Siddipet", "Zahirabad", "Narayankhed", "Andole", "Jogipet", "Gajwel", "Dubbak", 
                                "Kondapak", "Sangareddy", "Medak", "Siddipet", "Zahirabad", "Narayankhed", "Andole"));
                cities.put("Hanamkonda", Arrays.asList("Hanamkonda", "Warangal", "Kazipet", "Jangaon", "Parkal", "Mahabubabad", "Narsampet", 
                                "Eturnagaram", "Mulugu", "Ghanpur", "Wardhannapet", "Bhupalpally", "Hanamkonda", "Warangal", "Kazipet"));
        }

        private static void initializeStates22_28() {
                // Uttar Pradesh - All districts
                districts.put("Uttar Pradesh", Arrays.asList("Agra", "Aligarh", "Allahabad", "Ambedkar Nagar", "Amethi", "Amroha", "Auraiya", 
                "Azamgarh", "Baghpat", "Bahraich", "Ballia", "Balrampur", "Banda", "Barabanki", "Bareilly", "Basti", "Bhadohi", 
                "Bijnor", "Budaun", "Bulandshahr", "Chandauli", "Chitrakoot", "Deoria", "Etah", "Etawah", "Faizabad", "Farrukhabad", 
                "Fatehpur", "Firozabad", "Gautam Buddha Nagar", "Ghaziabad", "Ghazipur", "Gonda", "Gorakhpur", "Hamirpur", "Hapur", 
                "Hardoi", "Hathras", "Jalaun", "Jaunpur", "Jhansi", "Kannauj", "Kanpur Dehat", "Kanpur Nagar", "Kasganj", "Kaushambi", 
                "Kheri", "Kushinagar", "Lalitpur", "Lucknow", "Maharajganj", "Mahoba", "Mainpuri", "Mathura", "Mau", "Meerut", 
                "Mirzapur", "Moradabad", "Muzaffarnagar", "Pilibhit", "Pratapgarh", "Prayagraj", "Raebareli", "Rampur", "Saharanpur", 
                "Sambhal", "Sant Kabir Nagar", "Shahjahanpur", "Shamli", "Shrawasti", "Siddharthnagar", "Sitapur", "Sonbhadra", 
                "Sultanpur", "Unnao", "Varanasi"));

cities.put("Lucknow", Arrays.asList("Lucknow", "Gomti Nagar", "Hazratganj", "Alambagh", "Aminabad", "Chowk", "Indira Nagar", 
                "Aliganj", "Rajajipuram", "Mahanagar", "Vikas Nagar", "Sitapur Road", "Charbagh", "Cantonment", "Gomti Nagar"));
cities.put("Kanpur Nagar", Arrays.asList("Kanpur", "Panki", "Jajmau", "Kalyanpur", "Govind Nagar", "Kidwai Nagar", "Shyam Nagar", 
                "Arya Nagar", "Swaroop Nagar", "Kakadeo", "Barra", "Unnao", "Bithoor", "Ghatampur", "Bilhaur"));
cities.put("Ghaziabad", Arrays.asList("Ghaziabad", "Noida", "Greater Noida", "Indirapuram", "Vaishali", "Kaushambi", "Vasundhara", 
                "Raj Nagar", "Sahibabad", "Loni", "Modinagar", "Muradnagar", "Hapur", "Pilkhua", "Dhaulana"));
cities.put("Agra", Arrays.asList("Agra", "Firozabad", "Mathura", "Vrindavan", "Gokul", "Barsana", "Govardhan", "Etmadpur", "Fatehabad", 
                "Kiraoli", "Bah", "Jagner", "Achhnera", "Kheragarh", "Fatehpur Sikri"));
cities.put("Varanasi", Arrays.asList("Varanasi", "Sarnath", "Ramnagar", "Chandauli", "Mughalsarai", "Chunar", "Mirzapur", "Robertsganj", 
                "Chakia", "Pindra", "Sevapuri", "Baragaon", "Arajiline", "Badagaon", "Kashi"));
cities.put("Meerut", Arrays.asList("Meerut", "Modinagar", "Hapur", "Pilkhua", "Dhaulana", "Sardhana", "Mawana", "Kharkhoda", 
                "Sardhana", "Mawana", "Kharkhoda", "Meerut", "Modinagar", "Hapur", "Pilkhua"));
cities.put("Prayagraj", Arrays.asList("Allahabad", "Prayagraj", "Naini", "Phulpur", "Soraon", "Karchana", "Handia", "Meja", 
                "Koraon", "Bara", "Chail", "Karachhana", "Allahabad", "Prayagraj", "Naini"));
cities.put("Bareilly", Arrays.asList("Bareilly", "Aonla", "Baheri", "Nawabganj", "Faridpur", "Meerganj", "Bareilly", "Aonla", 
                "Baheri", "Nawabganj", "Faridpur", "Meerganj", "Bareilly", "Aonla", "Baheri"));
cities.put("Aligarh", Arrays.asList("Aligarh", "Khair", "Atrauli", "Iglas", "Koil", "Gabhana", "Aligarh", "Khair", "Atrauli", 
                "Iglas", "Koil", "Gabhana", "Aligarh", "Khair", "Atrauli"));
cities.put("Moradabad", Arrays.asList("Moradabad", "Rampur", "Bilaspur", "Chandausi", "Thakurdwara", "Sambhal", "Bahjoi", "Kanth", 
                "Moradabad", "Rampur", "Bilaspur", "Chandausi", "Thakurdwara", "Sambhal", "Bahjoi"));
cities.put("Gorakhpur", Arrays.asList("Gorakhpur", "Maharajganj", "Padrauna", "Khalilabad", "Bansgaon", "Chauri Chaura", "Gola", 
                "Gorakhpur", "Maharajganj", "Padrauna", "Khalilabad", "Bansgaon", "Chauri Chaura", "Gola"));
cities.put("Jhansi", Arrays.asList("Jhansi", "Orai", "Moth", "Mauranipur", "Garautha", "Tahrauli", "Jhansi", "Orai", "Moth", 
                "Mauranipur", "Garautha", "Tahrauli", "Jhansi", "Orai", "Moth"));
cities.put("Saharanpur", Arrays.asList("Saharanpur", "Muzaffarnagar", "Deoband", "Roorkee", "Haridwar", "Rishikesh", "Behat", 
                "Nakur", "Gangoh", "Saharanpur", "Muzaffarnagar", "Deoband", "Roorkee", "Haridwar", "Rishikesh"));
cities.put("Noida", Arrays.asList("Noida", "Greater Noida", "Dadri", "Jewar", "Dankaur", "Bisrakh", "Noida", "Greater Noida", 
                "Dadri", "Jewar", "Dankaur", "Bisrakh", "Noida", "Greater Noida", "Dadri"));
cities.put("Mathura", Arrays.asList("Mathura", "Vrindavan", "Gokul", "Barsana", "Govardhan", "Radha Kund", "Nandgaon", "Baldeo", 
                "Mathura", "Vrindavan", "Gokul", "Barsana", "Govardhan", "Radha Kund", "Nandgaon"));
cities.put("Muzaffarnagar", Arrays.asList("Muzaffarnagar", "Shamli", "Kairana", "Budhana", "Jansath", "Khatauli", "Muzaffarnagar", 
                "Shamli", "Kairana", "Budhana", "Jansath", "Khatauli", "Muzaffarnagar", "Shamli", "Kairana"));
cities.put("Rampur", Arrays.asList("Rampur", "Bilaspur", "Chandausi", "Thakurdwara", "Sambhal", "Bahjoi", "Kanth", "Moradabad", 
                "Rampur", "Bilaspur", "Chandausi", "Thakurdwara", "Sambhal", "Bahjoi", "Kanth"));
cities.put("Sitapur", Arrays.asList("Sitapur", "Biswan", "Laharpur", "Mahmudabad", "Misrikh", "Sidhauli", "Sitapur", "Biswan", 
                "Laharpur", "Mahmudabad", "Misrikh", "Sidhauli", "Sitapur", "Biswan", "Laharpur"));
cities.put("Hardoi", Arrays.asList("Hardoi", "Shahabad", "Bilgram", "Sandi", "Mallawan", "Pihani", "Hardoi", "Shahabad", "Bilgram", 
                "Sandi", "Mallawan", "Pihani", "Hardoi", "Shahabad", "Bilgram"));
cities.put("Unnao", Arrays.asList("Unnao", "Bighapur", "Hasanganj", "Safipur", "Purwa", "Asoha", "Unnao", "Bighapur", "Hasanganj", 
                "Safipur", "Purwa", "Asoha", "Unnao", "Bighapur", "Hasanganj"));
cities.put("Raebareli", Arrays.asList("Raebareli", "Dalmau", "Lalganj", "Unchahar", "Bachhrawan", "Salon", "Raebareli", "Dalmau", 
                "Lalganj", "Unchahar", "Bachhrawan", "Salon", "Raebareli", "Dalmau", "Lalganj"));
cities.put("Sultanpur", Arrays.asList("Sultanpur", "Kadipur", "Lambhua", "Jaisinghpur", "Amethi", "Gauriganj", "Sultanpur", "Kadipur", 
                "Lambhua", "Jaisinghpur", "Amethi", "Gauriganj", "Sultanpur", "Kadipur", "Lambhua"));
cities.put("Barabanki", Arrays.asList("Barabanki", "Fatehpur", "Haidergarh", "Ramnagar", "Sirauli Gauspur", "Barabanki", "Fatehpur", 
                "Haidergarh", "Ramnagar", "Sirauli Gauspur", "Barabanki", "Fatehpur", "Haidergarh", "Ramnagar", "Sirauli Gauspur"));
cities.put("Faizabad", Arrays.asList("Ayodhya", "Faizabad", "Bikapur", "Rudauli", "Milkipur", "Ayodhya", "Faizabad", "Bikapur", 
                "Rudauli", "Milkipur", "Ayodhya", "Faizabad", "Bikapur", "Rudauli", "Milkipur"));
cities.put("Ambedkar Nagar", Arrays.asList("Akbarpur", "Jalalpur", "Tanda", "Bilariyaganj", "Akbarpur", "Jalalpur", "Tanda", 
                "Bilariyaganj", "Akbarpur", "Jalalpur", "Tanda", "Bilariyaganj", "Akbarpur", "Jalalpur", "Tanda"));
cities.put("Bahraich", Arrays.asList("Bahraich", "Nanpara", "Kaiserganj", "Mahasi", "Bahraich", "Nanpara", "Kaiserganj", "Mahasi", 
                "Bahraich", "Nanpara", "Kaiserganj", "Mahasi", "Bahraich", "Nanpara", "Kaiserganj"));
cities.put("Shrawasti", Arrays.asList("Shrawasti", "Bhinga", "Ikauna", "Shrawasti", "Bhinga", "Ikauna", "Shrawasti", "Bhinga", 
                "Ikauna", "Shrawasti", "Bhinga", "Ikauna", "Shrawasti", "Bhinga", "Ikauna"));
cities.put("Balrampur", Arrays.asList("Balrampur", "Tulsipur", "Utraula", "Balrampur", "Tulsipur", "Utraula", "Balrampur", "Tulsipur", 
                "Utraula", "Balrampur", "Tulsipur", "Utraula", "Balrampur", "Tulsipur", "Utraula"));
cities.put("Gonda", Arrays.asList("Gonda", "Colonelganj", "Tarabganj", "Mankapur", "Gonda", "Colonelganj", "Tarabganj", "Mankapur", 
                "Gonda", "Colonelganj", "Tarabganj", "Mankapur", "Gonda", "Colonelganj", "Tarabganj"));
cities.put("Siddharthnagar", Arrays.asList("Naugarh", "Bansi", "Domariyaganj", "Itwa", "Naugarh", "Bansi", "Domariyaganj", "Itwa", 
                "Naugarh", "Bansi", "Domariyaganj", "Itwa", "Naugarh", "Bansi", "Domariyaganj"));
cities.put("Basti", Arrays.asList("Basti", "Harraiya", "Bhanpur", "Rudhauli", "Basti", "Harraiya", "Bhanpur", "Rudhauli", "Basti", 
                "Harraiya", "Bhanpur", "Rudhauli", "Basti", "Harraiya", "Bhanpur"));
cities.put("Sant Kabir Nagar", Arrays.asList("Khalilabad", "Mehdawal", "Ghanghata", "Khalilabad", "Mehdawal", "Ghanghata", "Khalilabad", 
                "Mehdawal", "Ghanghata", "Khalilabad", "Mehdawal", "Ghanghata", "Khalilabad", "Mehdawal", "Ghanghata"));
cities.put("Maharajganj", Arrays.asList("Maharajganj", "Nautanwa", "Nichlaul", "Pharenda", "Maharajganj", "Nautanwa", "Nichlaul", 
                "Pharenda", "Maharajganj", "Nautanwa", "Nichlaul", "Pharenda", "Maharajganj", "Nautanwa", "Nichlaul"));
cities.put("Kushinagar", Arrays.asList("Kushinagar", "Padrauna", "Kasya", "Tamkuhi Raj", "Kushinagar", "Padrauna", "Kasya", 
                "Tamkuhi Raj", "Kushinagar", "Padrauna", "Kasya", "Tamkuhi Raj", "Kushinagar", "Padrauna", "Kasya"));
cities.put("Deoria", Arrays.asList("Deoria", "Salempur", "Barhaj", "Bhatpar Rani", "Deoria", "Salempur", "Barhaj", "Bhatpar Rani", 
                "Deoria", "Salempur", "Barhaj", "Bhatpar Rani", "Deoria", "Salempur", "Barhaj"));
cities.put("Ballia", Arrays.asList("Ballia", "Rasra", "Bansdih", "Belthara Road", "Ballia", "Rasra", "Bansdih", "Belthara Road", 
                "Ballia", "Rasra", "Bansdih", "Belthara Road", "Ballia", "Rasra", "Bansdih"));
cities.put("Azamgarh", Arrays.asList("Azamgarh", "Mubarakpur", "Phulpur", "Sagri", "Azamgarh", "Mubarakpur", "Phulpur", "Sagri", 
                "Azamgarh", "Mubarakpur", "Phulpur", "Sagri", "Azamgarh", "Mubarakpur", "Phulpur"));
cities.put("Mau", Arrays.asList("Mau", "Ghosi", "Muhammadabad", "Kopaganj", "Mau", "Ghosi", "Muhammadabad", "Kopaganj", "Mau", 
                "Ghosi", "Muhammadabad", "Kopaganj", "Mau", "Ghosi", "Muhammadabad"));
cities.put("Jaunpur", Arrays.asList("Jaunpur", "Shahganj", "Machhlishahr", "Mariahu", "Jaunpur", "Shahganj", "Machhlishahr", "Mariahu", 
                "Jaunpur", "Shahganj", "Machhlishahr", "Mariahu", "Jaunpur", "Shahganj", "Machhlishahr"));
cities.put("Ghazipur", Arrays.asList("Ghazipur", "Zamania", "Mohammadabad", "Saidpur", "Ghazipur", "Zamania", "Mohammadabad", "Saidpur", 
                "Ghazipur", "Zamania", "Mohammadabad", "Saidpur", "Ghazipur", "Zamania", "Mohammadabad"));
cities.put("Chandauli", Arrays.asList("Chandauli", "Mughalsarai", "Chunar", "Sakaldiha", "Chandauli", "Mughalsarai", "Chunar", 
                "Sakaldiha", "Chandauli", "Mughalsarai", "Chunar", "Sakaldiha", "Chandauli", "Mughalsarai", "Chunar"));
cities.put("Bhadohi", Arrays.asList("Bhadohi", "Gyanpur", "Aurai", "Bhadohi", "Gyanpur", "Aurai", "Bhadohi", "Gyanpur", "Aurai", 
                "Bhadohi", "Gyanpur", "Aurai", "Bhadohi", "Gyanpur", "Aurai"));
cities.put("Mirzapur", Arrays.asList("Mirzapur", "Chunar", "Lalganj", "Robertsganj", "Mirzapur", "Chunar", "Lalganj", "Robertsganj", 
                "Mirzapur", "Chunar", "Lalganj", "Robertsganj", "Mirzapur", "Chunar", "Lalganj"));
cities.put("Sonbhadra", Arrays.asList("Robertsganj", "Dudhi", "Ghorawal", "Robertsganj", "Dudhi", "Ghorawal", "Robertsganj", "Dudhi", 
                "Ghorawal", "Robertsganj", "Dudhi", "Ghorawal", "Robertsganj", "Dudhi", "Ghorawal"));
cities.put("Allahabad", Arrays.asList("Allahabad", "Prayagraj", "Naini", "Phulpur", "Soraon", "Karchana", "Handia", "Meja", "Koraon", 
                "Bara", "Chail", "Karachhana", "Allahabad", "Prayagraj", "Naini"));
cities.put("Kaushambi", Arrays.asList("Manjhanpur", "Chail", "Sirathu", "Manjhanpur", "Chail", "Sirathu", "Manjhanpur", "Chail", 
                "Sirathu", "Manjhanpur", "Chail", "Sirathu", "Manjhanpur", "Chail", "Sirathu"));
cities.put("Fatehpur", Arrays.asList("Fatehpur", "Khaga", "Bindki", "Fatehpur", "Khaga", "Bindki", "Fatehpur", "Khaga", "Bindki", 
                "Fatehpur", "Khaga", "Bindki", "Fatehpur", "Khaga", "Bindki"));
cities.put("Pratapgarh", Arrays.asList("Pratapgarh", "Kunda", "Patti", "Lalganj", "Pratapgarh", "Kunda", "Patti", "Lalganj", 
                "Pratapgarh", "Kunda", "Patti", "Lalganj", "Pratapgarh", "Kunda", "Patti"));
cities.put("Kaushambi", Arrays.asList("Manjhanpur", "Chail", "Sirathu", "Manjhanpur", "Chail", "Sirathu", "Manjhanpur", "Chail", 
                "Sirathu", "Manjhanpur", "Chail", "Sirathu", "Manjhanpur", "Chail", "Sirathu"));
cities.put("Banda", Arrays.asList("Banda", "Atarra", "Naraini", "Banda", "Atarra", "Naraini", "Banda", "Atarra", "Naraini", "Banda", 
                "Atarra", "Naraini", "Banda", "Atarra", "Naraini"));
cities.put("Chitrakoot", Arrays.asList("Karwi", "Mau", "Maniari", "Pahadi", "Karwi", "Mau", "Maniari", "Pahadi", "Karwi", "Mau", 
                "Maniari", "Pahadi", "Karwi", "Mau", "Maniari"));
cities.put("Hamirpur", Arrays.asList("Hamirpur", "Maudaha", "Rath", "Hamirpur", "Maudaha", "Rath", "Hamirpur", "Maudaha", "Rath", 
                "Hamirpur", "Maudaha", "Rath", "Hamirpur", "Maudaha", "Rath"));
cities.put("Mahoba", Arrays.asList("Mahoba", "Kulpahar", "Charkhari", "Mahoba", "Kulpahar", "Charkhari", "Mahoba", "Kulpahar", 
                "Charkhari", "Mahoba", "Kulpahar", "Charkhari", "Mahoba", "Kulpahar", "Charkhari"));
cities.put("Lalitpur", Arrays.asList("Lalitpur", "Mehroni", "Talbehat", "Lalitpur", "Mehroni", "Talbehat", "Lalitpur", "Mehroni", 
                "Talbehat", "Lalitpur", "Mehroni", "Talbehat", "Lalitpur", "Mehroni", "Talbehat"));
cities.put("Jalaun", Arrays.asList("Orai", "Kalpi", "Konch", "Orai", "Kalpi", "Konch", "Orai", "Kalpi", "Konch", "Orai", "Kalpi", 
                "Konch", "Orai", "Kalpi", "Konch"));
cities.put("Jhansi", Arrays.asList("Jhansi", "Mauranipur", "Garautha", "Tahrauli", "Jhansi", "Mauranipur", "Garautha", "Tahrauli", 
                "Jhansi", "Mauranipur", "Garautha", "Tahrauli", "Jhansi", "Mauranipur", "Garautha"));
cities.put("Etawah", Arrays.asList("Etawah", "Jaswantnagar", "Saifai", "Etawah", "Jaswantnagar", "Saifai", "Etawah", "Jaswantnagar", 
                "Saifai", "Etawah", "Jaswantnagar", "Saifai", "Etawah", "Jaswantnagar", "Saifai"));
cities.put("Auraiya", Arrays.asList("Auraiya", "Bidhuna", "Auraiya", "Bidhuna", "Auraiya", "Bidhuna", "Auraiya", "Bidhuna", "Auraiya", 
                "Bidhuna", "Auraiya", "Bidhuna", "Auraiya", "Bidhuna", "Auraiya"));
cities.put("Kannauj", Arrays.asList("Kannauj", "Chhibramau", "Tirwa", "Kannauj", "Chhibramau", "Tirwa", "Kannauj", "Chhibramau", 
                "Tirwa", "Kannauj", "Chhibramau", "Tirwa", "Kannauj", "Chhibramau", "Tirwa"));
cities.put("Farrukhabad", Arrays.asList("Farrukhabad", "Fatehgarh", "Kaimganj", "Farrukhabad", "Fatehgarh", "Kaimganj", "Farrukhabad", 
                "Fatehgarh", "Kaimganj", "Farrukhabad", "Fatehgarh", "Kaimganj", "Farrukhabad", "Fatehgarh", "Kaimganj"));
cities.put("Mainpuri", Arrays.asList("Mainpuri", "Bhongaon", "Karhal", "Mainpuri", "Bhongaon", "Karhal", "Mainpuri", "Bhongaon", 
                "Karhal", "Mainpuri", "Bhongaon", "Karhal", "Mainpuri", "Bhongaon", "Karhal"));
cities.put("Etah", Arrays.asList("Etah", "Kasganj", "Aliganj", "Etah", "Kasganj", "Aliganj", "Etah", "Kasganj", "Aliganj", "Etah", 
                "Kasganj", "Aliganj", "Etah", "Kasganj", "Aliganj"));
cities.put("Kasganj", Arrays.asList("Kasganj", "Patiyali", "Sahawar", "Kasganj", "Patiyali", "Sahawar", "Kasganj", "Patiyali", 
                "Sahawar", "Kasganj", "Patiyali", "Sahawar", "Kasganj", "Patiyali", "Sahawar"));
cities.put("Hathras", Arrays.asList("Hathras", "Sasni", "Sadabad", "Hathras", "Sasni", "Sadabad", "Hathras", "Sasni", "Sadabad", 
                "Hathras", "Sasni", "Sadabad", "Hathras", "Sasni", "Sadabad"));
cities.put("Firozabad", Arrays.asList("Firozabad", "Tundla", "Shikohabad", "Firozabad", "Tundla", "Shikohabad", "Firozabad", "Tundla", 
                "Shikohabad", "Firozabad", "Tundla", "Shikohabad", "Firozabad", "Tundla", "Shikohabad"));
cities.put("Bulandshahr", Arrays.asList("Bulandshahr", "Khurja", "Sikandrabad", "Anupshahr", "Debai", "Bulandshahr", "Khurja", 
                "Sikandrabad", "Anupshahr", "Debai", "Bulandshahr", "Khurja", "Sikandrabad", "Anupshahr", "Debai"));
cities.put("Gautam Buddha Nagar", Arrays.asList("Noida", "Greater Noida", "Dadri", "Jewar", "Dankaur", "Bisrakh", "Noida", 
                "Greater Noida", "Dadri", "Jewar", "Dankaur", "Bisrakh", "Noida", "Greater Noida", "Dadri"));
cities.put("Hapur", Arrays.asList("Hapur", "Pilkhua", "Dhaulana", "Garhmukteshwar", "Hapur", "Pilkhua", "Dhaulana", "Garhmukteshwar", 
                "Hapur", "Pilkhua", "Dhaulana", "Garhmukteshwar", "Hapur", "Pilkhua", "Dhaulana"));
cities.put("Baghpat", Arrays.asList("Baghpat", "Baraut", "Khekra", "Baghpat", "Baraut", "Khekra", "Baghpat", "Baraut", "Khekra", 
                "Baghpat", "Baraut", "Khekra", "Baghpat", "Baraut", "Khekra"));
cities.put("Shamli", Arrays.asList("Shamli", "Kairana", "Budhana", "Jansath", "Shamli", "Kairana", "Budhana", "Jansath", "Shamli", 
                "Kairana", "Budhana", "Jansath", "Shamli", "Kairana", "Budhana"));
cities.put("Bijnor", Arrays.asList("Bijnor", "Najibabad", "Dhampur", "Nagina", "Bijnor", "Najibabad", "Dhampur", "Nagina", "Bijnor", 
                "Najibabad", "Dhampur", "Nagina", "Bijnor", "Najibabad", "Dhampur"));
cities.put("Amroha", Arrays.asList("Amroha", "Gajraula", "Hasanpur", "Dhanaura", "Amroha", "Gajraula", "Hasanpur", "Dhanaura", 
                "Amroha", "Gajraula", "Hasanpur", "Dhanaura", "Amroha", "Gajraula", "Hasanpur"));
cities.put("Sambhal", Arrays.asList("Sambhal", "Chandausi", "Gunnaur", "Sambhal", "Chandausi", "Gunnaur", "Sambhal", "Chandausi", 
                "Gunnaur", "Sambhal", "Chandausi", "Gunnaur", "Sambhal", "Chandausi", "Gunnaur"));
cities.put("Budaun", Arrays.asList("Budaun", "Bisauli", "Dataganj", "Sahaswan", "Budaun", "Bisauli", "Dataganj", "Sahaswan", 
                "Budaun", "Bisauli", "Dataganj", "Sahaswan", "Budaun", "Bisauli", "Dataganj"));
cities.put("Pilibhit", Arrays.asList("Pilibhit", "Bisalpur", "Puranpur", "Pilibhit", "Bisalpur", "Puranpur", "Pilibhit", "Bisalpur", 
                "Puranpur", "Pilibhit", "Bisalpur", "Puranpur", "Pilibhit", "Bisalpur", "Puranpur"));
cities.put("Shahjahanpur", Arrays.asList("Shahjahanpur", "Tilhar", "Jalalabad", "Powayan", "Shahjahanpur", "Tilhar", "Jalalabad", 
                "Powayan", "Shahjahanpur", "Tilhar", "Jalalabad", "Powayan", "Shahjahanpur", "Tilhar", "Jalalabad"));
cities.put("Kheri", Arrays.asList("Lakhimpur", "Gola Gokaran Nath", "Mohammadi", "Nighasan", "Lakhimpur", "Gola Gokaran Nath", 
                "Mohammadi", "Nighasan", "Lakhimpur", "Gola Gokaran Nath", "Mohammadi", "Nighasan", "Lakhimpur", "Gola Gokaran Nath", 
                "Mohammadi"));

// West Bengal - All districts
districts.put("West Bengal", Arrays.asList("Alipurduar", "Bankura", "Birbhum", "Cooch Behar", "Dakshin Dinajpur", "Darjeeling", 
                "Hooghly", "Howrah", "Jalpaiguri", "Jhargram", "Kalimpong", "Kolkata", "Malda", "Murshidabad", "Nadia", "North 24 Parganas", 
                "Paschim Bardhaman", "Paschim Medinipur", "Purba Bardhaman", "Purba Medinipur", "Purulia", "South 24 Parganas", 
                "Uttar Dinajpur"));

cities.put("Kolkata", Arrays.asList("Kolkata", "Salt Lake", "New Town", "Howrah", "Dum Dum", "Barasat", "Kalyani", "Bidhannagar", 
                "Rajarhat", "Behala", "Alipore", "Park Street", "Esplanade", "Ballygunge", "Tollygunge"));
cities.put("Howrah", Arrays.asList("Howrah", "Bally", "Uluberia", "Amta", "Domjur", "Jagatballavpur", "Panchla", "Sankrail", 
                "Howrah", "Bally", "Uluberia", "Amta", "Domjur", "Jagatballavpur", "Panchla"));
cities.put("North 24 Parganas", Arrays.asList("Barasat", "Kalyani", "Bidhannagar", "Rajarhat", "New Town", "Salt Lake", "Dum Dum", 
                "Barrackpore", "Naihati", "Kanchrapara", "Halishahar", "Bongaon", "Basirhat", "Baduria", "Habra"));
cities.put("South 24 Parganas", Arrays.asList("Alipore", "Diamond Harbour", "Baruipur", "Canning", "Kakdwip", "Namkhana", "Sagar", 
                "Kulpi", "Magrahat", "Falta", "Budge Budge", "Maheshtala", "Pujali", "Sonarpur", "Rajarhat"));
cities.put("Hooghly", Arrays.asList("Chinsurah", "Serampore", "Rishra", "Uttarpara", "Bhadreswar", "Champdani", "Baidyabati", 
                "Konnagar", "Arambagh", "Tarakeswar", "Goghat", "Pursurah", "Dhaniakhali", "Pandua", "Singur"));
cities.put("Bardhaman", Arrays.asList("Bardhaman", "Durgapur", "Asansol", "Raniganj", "Jamuria", "Kulti", "Burnpur", "Faridpur", 
                "Pandabeswar", "Ondal", "Galsi", "Katwa", "Kalna", "Memari", "Bhatar"));
cities.put("Paschim Bardhaman", Arrays.asList("Asansol", "Durgapur", "Raniganj", "Jamuria", "Kulti", "Burnpur", "Faridpur", 
                "Pandabeswar", "Ondal", "Asansol", "Durgapur", "Raniganj", "Jamuria", "Kulti", "Burnpur"));
cities.put("Purba Bardhaman", Arrays.asList("Bardhaman", "Katwa", "Kalna", "Memari", "Bhatar", "Galsi", "Bardhaman", "Katwa", 
                "Kalna", "Memari", "Bhatar", "Galsi", "Bardhaman", "Katwa", "Kalna"));
cities.put("Nadia", Arrays.asList("Krishnanagar", "Nabadwip", "Ranaghat", "Kalyani", "Shantipur", "Chakdaha", "Haringhata", 
                "Krishnanagar", "Nabadwip", "Ranaghat", "Kalyani", "Shantipur", "Chakdaha", "Haringhata", "Krishnanagar"));
cities.put("Murshidabad", Arrays.asList("Berhampore", "Baharampur", "Kandi", "Jangipur", "Lalbagh", "Murshidabad", "Domkal", "Nabagram", 
                "Suti", "Sagardighi", "Raghunathganj", "Farakka", "Samserganj", "Suti", "Jangipur"));
cities.put("Malda", Arrays.asList("Malda", "English Bazar", "Chanchal", "Harishchandrapur", "Ratua", "Manikchak", "Kaliachak", 
                "Malda", "English Bazar", "Chanchal", "Harishchandrapur", "Ratua", "Manikchak", "Kaliachak", "Malda"));
cities.put("Birbhum", Arrays.asList("Suri", "Bolpur", "Rampurhat", "Sainthia", "Dubrajpur", "Nanoor", "Labpur", "Suri", "Bolpur", 
                "Rampurhat", "Sainthia", "Dubrajpur", "Nanoor", "Labpur", "Suri"));
cities.put("Bankura", Arrays.asList("Bankura", "Bishnupur", "Khatra", "Raipur", "Taldangra", "Indas", "Sonamukhi", "Bankura", 
                "Bishnupur", "Khatra", "Raipur", "Taldangra", "Indas", "Sonamukhi", "Bankura"));
cities.put("Purulia", Arrays.asList("Purulia", "Jhalda", "Raghunathpur", "Manbazar", "Balarampur", "Baghmundi", "Purulia", "Jhalda", 
                "Raghunathpur", "Manbazar", "Balarampur", "Baghmundi", "Purulia", "Jhalda", "Raghunathpur"));
cities.put("Jhargram", Arrays.asList("Jhargram", "Gopiballavpur", "Nayagram", "Sankrail", "Jhargram", "Gopiballavpur", "Nayagram", 
                "Sankrail", "Jhargram", "Gopiballavpur", "Nayagram", "Sankrail", "Jhargram", "Gopiballavpur", "Nayagram"));
cities.put("Paschim Medinipur", Arrays.asList("Midnapore", "Kharagpur", "Jhargram", "Ghatal", "Daspur", "Sabang", "Narayangarh", 
                "Midnapore", "Kharagpur", "Jhargram", "Ghatal", "Daspur", "Sabang", "Narayangarh", "Midnapore"));
cities.put("Purba Medinipur", Arrays.asList("Tamluk", "Haldia", "Contai", "Egra", "Panskura", "Nandigram", "Tamluk", "Haldia", 
                "Contai", "Egra", "Panskura", "Nandigram", "Tamluk", "Haldia", "Contai"));
cities.put("Darjeeling", Arrays.asList("Darjeeling", "Kurseong", "Kalimpong", "Mirik", "Sukna", "Siliguri", "Matigara", "Naxalbari", 
                "Darjeeling", "Kurseong", "Kalimpong", "Mirik", "Sukna", "Siliguri", "Matigara"));
cities.put("Kalimpong", Arrays.asList("Kalimpong", "Gorubathan", "Lava", "Pedong", "Kalimpong", "Gorubathan", "Lava", "Pedong", 
                "Kalimpong", "Gorubathan", "Lava", "Pedong", "Kalimpong", "Gorubathan", "Lava"));
cities.put("Jalpaiguri", Arrays.asList("Jalpaiguri", "Siliguri", "Mal", "Dhupguri", "Maynaguri", "Nagrakata", "Jalpaiguri", "Siliguri", 
                "Mal", "Dhupguri", "Maynaguri", "Nagrakata", "Jalpaiguri", "Siliguri", "Mal"));
cities.put("Alipurduar", Arrays.asList("Alipurduar", "Falakata", "Kumargram", "Madarihat", "Alipurduar", "Falakata", "Kumargram", 
                "Madarihat", "Alipurduar", "Falakata", "Kumargram", "Madarihat", "Alipurduar", "Falakata", "Kumargram"));
cities.put("Cooch Behar", Arrays.asList("Cooch Behar", "Dinhata", "Mathabhanga", "Tufanganj", "Sitalkuchi", "Cooch Behar", "Dinhata", 
                "Mathabhanga", "Tufanganj", "Sitalkuchi", "Cooch Behar", "Dinhata", "Mathabhanga", "Tufanganj", "Sitalkuchi"));
cities.put("Uttar Dinajpur", Arrays.asList("Raiganj", "Islampur", "Kaliaganj", "Gangarampur", "Raiganj", "Islampur", "Kaliaganj", 
                "Gangarampur", "Raiganj", "Islampur", "Kaliaganj", "Gangarampur", "Raiganj", "Islampur", "Kaliaganj"));
cities.put("Dakshin Dinajpur", Arrays.asList("Balurghat", "Gangarampur", "Hili", "Tapan", "Balurghat", "Gangarampur", "Hili", "Tapan", 
                "Balurghat", "Gangarampur", "Hili", "Tapan", "Balurghat", "Gangarampur", "Hili"));

// Rajasthan - Key districts
districts.put("Rajasthan", Arrays.asList("Ajmer", "Alwar", "Banswara", "Baran", "Barmer", "Bharatpur", "Bhilwara", "Bikaner", 
                "Bundi", "Chittorgarh", "Churu", "Dausa", "Dholpur", "Dungarpur", "Hanumangarh", "Jaipur", "Jaisalmer", "Jalore", 
                "Jhalawar", "Jhunjhunu", "Jodhpur", "Karauli", "Kota", "Nagaur", "Pali", "Pratapgarh", "Rajsamand", "Sawai Madhopur", 
                "Sikar", "Sirohi", "Sri Ganganagar", "Tonk", "Udaipur"));

cities.put("Jaipur", Arrays.asList("Jaipur", "Sanganer", "Amer", "Chomu", "Phulera", "Kotputli", "Shahpura", "Viratnagar", 
                "Jaipur", "Sanganer", "Amer", "Chomu", "Phulera", "Kotputli", "Shahpura"));
cities.put("Jodhpur", Arrays.asList("Jodhpur", "Phalodi", "Osian", "Bilara", "Bhopalgarh", "Jodhpur", "Phalodi", "Osian", "Bilara", 
                "Bhopalgarh", "Jodhpur", "Phalodi", "Osian", "Bilara", "Bhopalgarh"));
cities.put("Kota", Arrays.asList("Kota", "Baran", "Atru", "Antah", "Kota", "Baran", "Atru", "Antah", "Kota", "Baran", "Atru", 
                "Antah", "Kota", "Baran", "Atru"));
cities.put("Udaipur", Arrays.asList("Udaipur", "Rajsamand", "Nathdwara", "Salumbar", "Girwa", "Vallabhnagar", "Udaipur", "Rajsamand", 
                "Nathdwara", "Salumbar", "Girwa", "Vallabhnagar", "Udaipur", "Rajsamand", "Nathdwara"));
cities.put("Ajmer", Arrays.asList("Ajmer", "Kishangarh", "Beawar", "Nasirabad", "Ajmer", "Kishangarh", "Beawar", "Nasirabad", 
                "Ajmer", "Kishangarh", "Beawar", "Nasirabad", "Ajmer", "Kishangarh", "Beawar"));
cities.put("Bikaner", Arrays.asList("Bikaner", "Nokha", "Lunkaransar", "Bikaner", "Nokha", "Lunkaransar", "Bikaner", "Nokha", 
                "Lunkaransar", "Bikaner", "Nokha", "Lunkaransar", "Bikaner", "Nokha", "Lunkaransar"));
cities.put("Alwar", Arrays.asList("Alwar", "Bharatpur", "Dausa", "Rajgarh", "Alwar", "Bharatpur", "Dausa", "Rajgarh", "Alwar", 
                "Bharatpur", "Dausa", "Rajgarh", "Alwar", "Bharatpur", "Dausa"));
cities.put("Bharatpur", Arrays.asList("Bharatpur", "Deeg", "Kumher", "Nadbai", "Bharatpur", "Deeg", "Kumher", "Nadbai", "Bharatpur", 
                "Deeg", "Kumher", "Nadbai", "Bharatpur", "Deeg", "Kumher"));
cities.put("Sikar", Arrays.asList("Sikar", "Fatehpur", "Lachhmangarh", "Sikar", "Fatehpur", "Lachhmangarh", "Sikar", "Fatehpur", 
                "Lachhmangarh", "Sikar", "Fatehpur", "Lachhmangarh", "Sikar", "Fatehpur", "Lachhmangarh"));
cities.put("Jhunjhunu", Arrays.asList("Jhunjhunu", "Churu", "Sujangarh", "Jhunjhunu", "Churu", "Sujangarh", "Jhunjhunu", "Churu", 
                "Sujangarh", "Jhunjhunu", "Churu", "Sujangarh", "Jhunjhunu", "Churu", "Sujangarh"));
cities.put("Pali", Arrays.asList("Pali", "Sojat", "Jaitaran", "Pali", "Sojat", "Jaitaran", "Pali", "Sojat", "Jaitaran", "Pali", 
                "Sojat", "Jaitaran", "Pali", "Sojat", "Jaitaran"));
cities.put("Bhilwara", Arrays.asList("Bhilwara", "Asind", "Mandal", "Bhilwara", "Asind", "Mandal", "Bhilwara", "Asind", "Mandal", 
                "Bhilwara", "Asind", "Mandal", "Bhilwara", "Asind", "Mandal"));
cities.put("Chittorgarh", Arrays.asList("Chittorgarh", "Nimbahera", "Bari Sadri", "Chittorgarh", "Nimbahera", "Bari Sadri", 
                "Chittorgarh", "Nimbahera", "Bari Sadri", "Chittorgarh", "Nimbahera", "Bari Sadri", "Chittorgarh", "Nimbahera", 
                "Bari Sadri"));
cities.put("Tonk", Arrays.asList("Tonk", "Uniara", "Deoli", "Tonk", "Uniara", "Deoli", "Tonk", "Uniara", "Deoli", "Tonk", "Uniara", 
                "Deoli", "Tonk", "Uniara", "Deoli"));
cities.put("Nagaur", Arrays.asList("Nagaur", "Merta", "Degana", "Nagaur", "Merta", "Degana", "Nagaur", "Merta", "Degana", "Nagaur", 
                "Merta", "Degana", "Nagaur", "Merta", "Degana"));
cities.put("Barmer", Arrays.asList("Barmer", "Balotra", "Siwana", "Barmer", "Balotra", "Siwana", "Barmer", "Balotra", "Siwana", 
                "Barmer", "Balotra", "Siwana", "Barmer", "Balotra", "Siwana"));
cities.put("Jaisalmer", Arrays.asList("Jaisalmer", "Pokaran", "Fatehgarh", "Jaisalmer", "Pokaran", "Fatehgarh", "Jaisalmer", 
                "Pokaran", "Fatehgarh", "Jaisalmer", "Pokaran", "Fatehgarh", "Jaisalmer", "Pokaran", "Fatehgarh"));
cities.put("Jalore", Arrays.asList("Jalore", "Bhinmal", "Ahore", "Jalore", "Bhinmal", "Ahore", "Jalore", "Bhinmal", "Ahore", 
                "Jalore", "Bhinmal", "Ahore", "Jalore", "Bhinmal", "Ahore"));
cities.put("Sirohi", Arrays.asList("Sirohi", "Abu Road", "Pindwara", "Sirohi", "Abu Road", "Pindwara", "Sirohi", "Abu Road", 
                "Pindwara", "Sirohi", "Abu Road", "Pindwara", "Sirohi", "Abu Road", "Pindwara"));
cities.put("Dungarpur", Arrays.asList("Dungarpur", "Sagwara", "Aspur", "Dungarpur", "Sagwara", "Aspur", "Dungarpur", "Sagwara", 
                "Aspur", "Dungarpur", "Sagwara", "Aspur", "Dungarpur", "Sagwara", "Aspur"));
cities.put("Banswara", Arrays.asList("Banswara", "Kushalgarh", "Ghatol", "Banswara", "Kushalgarh", "Ghatol", "Banswara", "Kushalgarh", 
                "Ghatol", "Banswara", "Kushalgarh", "Ghatol", "Banswara", "Kushalgarh", "Ghatol"));
cities.put("Pratapgarh", Arrays.asList("Pratapgarh", "Chhoti Sadri", "Arnod", "Pratapgarh", "Chhoti Sadri", "Arnod", "Pratapgarh", 
                "Chhoti Sadri", "Arnod", "Pratapgarh", "Chhoti Sadri", "Arnod", "Pratapgarh", "Chhoti Sadri", "Arnod"));
cities.put("Rajsamand", Arrays.asList("Rajsamand", "Nathdwara", "Kumbhalgarh", "Rajsamand", "Nathdwara", "Kumbhalgarh", "Rajsamand", 
                "Nathdwara", "Kumbhalgarh", "Rajsamand", "Nathdwara", "Kumbhalgarh", "Rajsamand", "Nathdwara", "Kumbhalgarh"));
cities.put("Bundi", Arrays.asList("Bundi", "Keshoraipatan", "Indragarh", "Bundi", "Keshoraipatan", "Indragarh", "Bundi", 
                "Keshoraipatan", "Indragarh", "Bundi", "Keshoraipatan", "Indragarh", "Bundi", "Keshoraipatan", "Indragarh"));
cities.put("Jhalawar", Arrays.asList("Jhalawar", "Jhalrapatan", "Aklera", "Jhalawar", "Jhalrapatan", "Aklera", "Jhalawar", 
                "Jhalrapatan", "Aklera", "Jhalawar", "Jhalrapatan", "Aklera", "Jhalawar", "Jhalrapatan", "Aklera"));
cities.put("Baran", Arrays.asList("Baran", "Atru", "Antah", "Baran", "Atru", "Antah", "Baran", "Atru", "Antah", "Baran", "Atru", 
                "Antah", "Baran", "Atru", "Antah"));
cities.put("Dausa", Arrays.asList("Dausa", "Lalsot", "Sikrai", "Dausa", "Lalsot", "Sikrai", "Dausa", "Lalsot", "Sikrai", "Dausa", 
                "Lalsot", "Sikrai", "Dausa", "Lalsot", "Sikrai"));
cities.put("Dholpur", Arrays.asList("Dholpur", "Bari", "Rajakhera", "Dholpur", "Bari", "Rajakhera", "Dholpur", "Bari", "Rajakhera", 
                "Dholpur", "Bari", "Rajakhera", "Dholpur", "Bari", "Rajakhera"));
cities.put("Karauli", Arrays.asList("Karauli", "Sapotra", "Todabhim", "Karauli", "Sapotra", "Todabhim", "Karauli", "Sapotra", 
                "Todabhim", "Karauli", "Sapotra", "Todabhim", "Karauli", "Sapotra", "Todabhim"));
cities.put("Sawai Madhopur", Arrays.asList("Sawai Madhopur", "Gangapur", "Bamanwas", "Sawai Madhopur", "Gangapur", "Bamanwas", 
                "Sawai Madhopur", "Gangapur", "Bamanwas", "Sawai Madhopur", "Gangapur", "Bamanwas", "Sawai Madhopur", "Gangapur", 
                "Bamanwas"));
cities.put("Churu", Arrays.asList("Churu", "Sujangarh", "Ratangarh", "Churu", "Sujangarh", "Ratangarh", "Churu", "Sujangarh", 
                "Ratangarh", "Churu", "Sujangarh", "Ratangarh", "Churu", "Sujangarh", "Ratangarh"));
cities.put("Hanumangarh", Arrays.asList("Hanumangarh", "Sangaria", "Rawatsar", "Hanumangarh", "Sangaria", "Rawatsar", "Hanumangarh", 
                "Sangaria", "Rawatsar", "Hanumangarh", "Sangaria", "Rawatsar", "Hanumangarh", "Sangaria", "Rawatsar"));
cities.put("Sri Ganganagar", Arrays.asList("Sri Ganganagar", "Suratgarh", "Raisinghnagar", "Sri Ganganagar", "Suratgarh", 
                "Raisinghnagar", "Sri Ganganagar", "Suratgarh", "Raisinghnagar", "Sri Ganganagar", "Suratgarh", "Raisinghnagar", 
                "Sri Ganganagar", "Suratgarh", "Raisinghnagar"));

// Punjab - All districts
districts.put("Punjab", Arrays.asList("Amritsar", "Barnala", "Bathinda", "Faridkot", "Fatehgarh Sahib", "Fazilka", "Ferozepur", 
                "Gurdaspur", "Hoshiarpur", "Jalandhar", "Kapurthala", "Ludhiana", "Mansa", "Moga", "Muktsar", "Nawanshahr", 
                "Pathankot", "Patiala", "Rupnagar", "Sangrur", "SAS Nagar", "Sri Muktsar Sahib", "Tarn Taran"));

cities.put("Ludhiana", Arrays.asList("Ludhiana", "Jagraon", "Khanna", "Samrala", "Raikot", "Payal", "Doraha", "Mullanpur", 
                "Ludhiana", "Jagraon", "Khanna", "Samrala", "Raikot", "Payal", "Doraha"));
cities.put("Amritsar", Arrays.asList("Amritsar", "Tarn Taran", "Patti", "Ajnala", "Attari", "Amritsar", "Tarn Taran", "Patti", 
                "Ajnala", "Attari", "Amritsar", "Tarn Taran", "Patti", "Ajnala", "Attari"));
cities.put("Jalandhar", Arrays.asList("Jalandhar", "Nakodar", "Phillaur", "Nurmahal", "Jalandhar", "Nakodar", "Phillaur", 
                "Nurmahal", "Jalandhar", "Nakodar", "Phillaur", "Nurmahal", "Jalandhar", "Nakodar", "Phillaur"));
cities.put("Patiala", Arrays.asList("Patiala", "Rajpura", "Nabha", "Samana", "Patiala", "Rajpura", "Nabha", "Samana", "Patiala", 
                "Rajpura", "Nabha", "Samana", "Patiala", "Rajpura", "Nabha"));
cities.put("Bathinda", Arrays.asList("Bathinda", "Mansa", "Sardulgarh", "Bathinda", "Mansa", "Sardulgarh", "Bathinda", "Mansa", 
                "Sardulgarh", "Bathinda", "Mansa", "Sardulgarh", "Bathinda", "Mansa", "Sardulgarh"));
cities.put("SAS Nagar", Arrays.asList("Mohali", "Kharar", "Dera Bassi", "Mohali", "Kharar", "Dera Bassi", "Mohali", "Kharar", 
                "Dera Bassi", "Mohali", "Kharar", "Dera Bassi", "Mohali", "Kharar", "Dera Bassi"));
cities.put("Hoshiarpur", Arrays.asList("Hoshiarpur", "Dasuya", "Mukerian", "Hoshiarpur", "Dasuya", "Mukerian", "Hoshiarpur", 
                "Dasuya", "Mukerian", "Hoshiarpur", "Dasuya", "Mukerian", "Hoshiarpur", "Dasuya", "Mukerian"));
cities.put("Gurdaspur", Arrays.asList("Gurdaspur", "Batala", "Pathankot", "Dinanagar", "Gurdaspur", "Batala", "Pathankot", 
                "Dinanagar", "Gurdaspur", "Batala", "Pathankot", "Dinanagar", "Gurdaspur", "Batala", "Pathankot"));
cities.put("Ferozepur", Arrays.asList("Ferozepur", "Fazilka", "Abohar", "Ferozepur", "Fazilka", "Abohar", "Ferozepur", "Fazilka", 
                "Abohar", "Ferozepur", "Fazilka", "Abohar", "Ferozepur", "Fazilka", "Abohar"));
cities.put("Sangrur", Arrays.asList("Sangrur", "Barnala", "Sunam", "Sangrur", "Barnala", "Sunam", "Sangrur", "Barnala", "Sunam", 
                "Sangrur", "Barnala", "Sunam", "Sangrur", "Barnala", "Sunam"));
cities.put("Moga", Arrays.asList("Moga", "Bagha Purana", "Dharamkot", "Moga", "Bagha Purana", "Dharamkot", "Moga", "Bagha Purana", 
                "Dharamkot", "Moga", "Bagha Purana", "Dharamkot", "Moga", "Bagha Purana", "Dharamkot"));
cities.put("Kapurthala", Arrays.asList("Kapurthala", "Sultanpur Lodhi", "Phagwara", "Kapurthala", "Sultanpur Lodhi", "Phagwara", 
                "Kapurthala", "Sultanpur Lodhi", "Phagwara", "Kapurthala", "Sultanpur Lodhi", "Phagwara", "Kapurthala", 
                "Sultanpur Lodhi", "Phagwara"));
cities.put("Rupnagar", Arrays.asList("Rupnagar", "Anandpur Sahib", "Nangal", "Rupnagar", "Anandpur Sahib", "Nangal", "Rupnagar", 
                "Anandpur Sahib", "Nangal", "Rupnagar", "Anandpur Sahib", "Nangal", "Rupnagar", "Anandpur Sahib", "Nangal"));
cities.put("Fatehgarh Sahib", Arrays.asList("Fatehgarh Sahib", "Sirhind", "Bassi Pathana", "Fatehgarh Sahib", "Sirhind", "Bassi Pathana", 
                "Fatehgarh Sahib", "Sirhind", "Bassi Pathana", "Fatehgarh Sahib", "Sirhind", "Bassi Pathana", "Fatehgarh Sahib", 
                "Sirhind", "Bassi Pathana"));
cities.put("Muktsar", Arrays.asList("Muktsar", "Malout", "Gidderbaha", "Muktsar", "Malout", "Gidderbaha", "Muktsar", "Malout", 
                "Gidderbaha", "Muktsar", "Malout", "Gidderbaha", "Muktsar", "Malout", "Gidderbaha"));
cities.put("Faridkot", Arrays.asList("Faridkot", "Kotkapura", "Jaitu", "Faridkot", "Kotkapura", "Jaitu", "Faridkot", "Kotkapura", 
                "Jaitu", "Faridkot", "Kotkapura", "Jaitu", "Faridkot", "Kotkapura", "Jaitu"));
cities.put("Tarn Taran", Arrays.asList("Tarn Taran", "Patti", "Khem Karan", "Tarn Taran", "Patti", "Khem Karan", "Tarn Taran", 
                "Patti", "Khem Karan", "Tarn Taran", "Patti", "Khem Karan", "Tarn Taran", "Patti", "Khem Karan"));
cities.put("Mansa", Arrays.asList("Mansa", "Sardulgarh", "Budhlada", "Mansa", "Sardulgarh", "Budhlada", "Mansa", "Sardulgarh", 
                "Budhlada", "Mansa", "Sardulgarh", "Budhlada", "Mansa", "Sardulgarh", "Budhlada"));
cities.put("Barnala", Arrays.asList("Barnala", "Tapa", "Handiaya", "Barnala", "Tapa", "Handiaya", "Barnala", "Tapa", "Handiaya", 
                "Barnala", "Tapa", "Handiaya", "Barnala", "Tapa", "Handiaya"));
cities.put("Fazilka", Arrays.asList("Fazilka", "Abohar", "Jalalabad", "Fazilka", "Abohar", "Jalalabad", "Fazilka", "Abohar", 
                "Jalalabad", "Fazilka", "Abohar", "Jalalabad", "Fazilka", "Abohar", "Jalalabad"));
cities.put("Pathankot", Arrays.asList("Pathankot", "Dinanagar", "Nurpur", "Pathankot", "Dinanagar", "Nurpur", "Pathankot", "Dinanagar", 
                "Nurpur", "Pathankot", "Dinanagar", "Nurpur", "Pathankot", "Dinanagar", "Nurpur"));
cities.put("Nawanshahr", Arrays.asList("Nawanshahr", "Balachaur", "Nawanshahr", "Balachaur", "Nawanshahr", "Balachaur", "Nawanshahr", 
                "Balachaur", "Nawanshahr", "Balachaur", "Nawanshahr", "Balachaur", "Nawanshahr", "Balachaur", "Nawanshahr"));

        }

        private static void initializeRemainingStates() {
                // Manipur - All districts
                districts.put("Manipur", Arrays.asList("Bishnupur", "Chandel", "Churachandpur", "Imphal East", "Imphal West", 
                                "Jiribam", "Kakching", "Kamjong", "Kangpokpi", "Noney", "Pherzawl", "Senapati", "Tamenglong", 
                                "Tengnoupal", "Thoubal", "Ukhrul"));
                
                cities.put("Imphal West", Arrays.asList("Imphal", "Lamphelpat", "Langjing", "Sagolband", "Uripok", "Kwakeithel", 
                                "Thangmeiband", "Paona Bazar", "Thangal Bazar", "Khongman", "Arambai", "Kongba", "Konthoujam", 
                                "Lilong", "Wangoi"));
                cities.put("Imphal East", Arrays.asList("Porompat", "Heingang", "Khongampat", "Konthoujam", "Sawombung", "Keirao", 
                                "Andro", "Kakching", "Kakching Khunou", "Sugnu", "Thoubal", "Wangjing", "Kakching", "Kakching Khunou", 
                                "Sugnu"));
                cities.put("Bishnupur", Arrays.asList("Bishnupur", "Nambol", "Moirang", "Kumbi", "Ningthoukhong", "Oinam", "Thanga", 
                                "Khoijuman", "Nambol", "Moirang", "Kumbi", "Ningthoukhong", "Oinam", "Thanga", "Khoijuman"));
                cities.put("Thoubal", Arrays.asList("Thoubal", "Wangjing", "Kakching", "Kakching Khunou", "Sugnu", "Yairipok", 
                                "Khongjom", "Heirok", "Lilong", "Wangoi", "Kakching", "Kakching Khunou", "Sugnu", "Yairipok", 
                                "Khongjom"));
                cities.put("Churachandpur", Arrays.asList("Churachandpur", "Moirang", "Kumbi", "Ningthoukhong", "Oinam", "Thanga", 
                                "Khoijuman", "Bishnupur", "Nambol", "Moirang", "Kumbi", "Ningthoukhong", "Oinam", "Thanga", 
                                "Khoijuman"));
                cities.put("Senapati", Arrays.asList("Senapati", "Kangpokpi", "Mao", "Tadubi", "Maram", "Phuba", "Purul", "Lairouching", 
                                "Kangpokpi", "Mao", "Tadubi", "Maram", "Phuba", "Purul", "Lairouching"));
                cities.put("Ukhrul", Arrays.asList("Ukhrul", "Kamjong", "Phungyar", "Kasom Khullen", "Chingai", "Phalee", "Shamphung", 
                                "Khamasom", "Lungchung", "Nungbi", "Sihai", "Tolloi", "Khangkhui", "Khamasom", "Lungchung"));
                cities.put("Chandel", Arrays.asList("Chandel", "Moreh", "Tengnoupal", "Khengjoy", "Machi", "Chakpikarong", "Kangvai", 
                                "Khengjoy", "Machi", "Chakpikarong", "Kangvai", "Moreh", "Tengnoupal", "Khengjoy", "Machi"));
                cities.put("Tamenglong", Arrays.asList("Tamenglong", "Tamei", "Nungba", "Tousem", "Noney", "Khoupum", "Tamei", "Nungba", 
                                "Tousem", "Noney", "Khoupum", "Tamenglong", "Tamei", "Nungba", "Tousem"));
                cities.put("Kangpokpi", Arrays.asList("Kangpokpi", "Mao", "Tadubi", "Maram", "Phuba", "Purul", "Lairouching", "Senapati", 
                                "Kangpokpi", "Mao", "Tadubi", "Maram", "Phuba", "Purul", "Lairouching"));
                cities.put("Kamjong", Arrays.asList("Kamjong", "Phungyar", "Kasom Khullen", "Chingai", "Phalee", "Shamphung", "Khamasom", 
                                "Lungchung", "Nungbi", "Sihai", "Tolloi", "Khangkhui", "Ukhrul", "Kamjong", "Phungyar"));
                cities.put("Jiribam", Arrays.asList("Jiribam", "Borobekra", "Jirighat", "Nungba", "Tousem", "Noney", "Khoupum", 
                                "Tamenglong", "Tamei", "Nungba", "Tousem", "Noney", "Khoupum", "Jiribam", "Borobekra"));
                cities.put("Kakching", Arrays.asList("Kakching", "Kakching Khunou", "Sugnu", "Yairipok", "Khongjom", "Heirok", "Lilong", 
                                "Wangoi", "Thoubal", "Wangjing", "Kakching", "Kakching Khunou", "Sugnu", "Yairipok", "Khongjom"));
                cities.put("Noney", Arrays.asList("Noney", "Khoupum", "Tamenglong", "Tamei", "Nungba", "Tousem", "Noney", "Khoupum", 
                                "Tamenglong", "Tamei", "Nungba", "Tousem", "Noney", "Khoupum", "Tamenglong"));
                cities.put("Pherzawl", Arrays.asList("Pherzawl", "Tipaimukh", "Thanlon", "Parbung", "Singngat", "Thanlon", "Parbung", 
                                "Singngat", "Pherzawl", "Tipaimukh", "Thanlon", "Parbung", "Singngat", "Pherzawl", "Tipaimukh"));
                cities.put("Tengnoupal", Arrays.asList("Tengnoupal", "Moreh", "Khengjoy", "Machi", "Chakpikarong", "Kangvai", "Chandel", 
                                "Tengnoupal", "Moreh", "Khengjoy", "Machi", "Chakpikarong", "Kangvai", "Chandel", "Tengnoupal"));

                // Meghalaya - All districts
                districts.put("Meghalaya", Arrays.asList("East Garo Hills", "East Jaintia Hills", "East Khasi Hills", "North Garo Hills", 
                                "Ri Bhoi", "South Garo Hills", "South West Garo Hills", "South West Khasi Hills", "West Garo Hills", 
                                "West Jaintia Hills", "West Khasi Hills"));
                
                cities.put("East Khasi Hills", Arrays.asList("Shillong", "Mawphlang", "Mawryngkneng", "Pynursla", "Mawsynram", "Sohra", 
                                "Laitlyngkot", "Mawsmai", "Mawlynnong", "Cherrapunji", "Nongpoh", "Umroi", "Umling", "Mawphlang", 
                                "Mawryngkneng"));
                cities.put("West Khasi Hills", Arrays.asList("Nongstoin", "Mawkyrwat", "Mawthadraishan", "Mawshynrut", "Mawphlang", 
                                "Mawryngkneng", "Pynursla", "Mawsynram", "Sohra", "Laitlyngkot", "Mawsmai", "Mawlynnong", "Cherrapunji", 
                                "Nongstoin", "Mawkyrwat"));
                cities.put("East Garo Hills", Arrays.asList("Williamnagar", "Rongjeng", "Dambo Rongjeng", "Songsak", "Resubelpara", 
                                "Baghmara", "Rongara", "Chokpot", "Gasuapara", "Dalu", "Tura", "Ampati", "Rongram", "Tikrikilla", 
                                "Phulbari"));
                cities.put("West Garo Hills", Arrays.asList("Tura", "Ampati", "Rongram", "Tikrikilla", "Phulbari", "Dalu", "Gasuapara", 
                                "Baghmara", "Rongara", "Chokpot", "Williamnagar", "Rongjeng", "Dambo Rongjeng", "Songsak", "Resubelpara"));
                cities.put("South Garo Hills", Arrays.asList("Baghmara", "Rongara", "Chokpot", "Gasuapara", "Dalu", "Tura", "Ampati", 
                                "Rongram", "Tikrikilla", "Phulbari", "Williamnagar", "Rongjeng", "Dambo Rongjeng", "Songsak", 
                                "Resubelpara"));
                cities.put("North Garo Hills", Arrays.asList("Resubelpara", "Williamnagar", "Rongjeng", "Dambo Rongjeng", "Songsak", 
                                "Resubelpara", "Baghmara", "Rongara", "Chokpot", "Gasuapara", "Dalu", "Tura", "Ampati", "Rongram", 
                                "Tikrikilla"));
                cities.put("South West Garo Hills", Arrays.asList("Ampati", "Rongram", "Tikrikilla", "Phulbari", "Dalu", "Gasuapara", 
                                "Baghmara", "Rongara", "Chokpot", "Tura", "Ampati", "Rongram", "Tikrikilla", "Phulbari", "Dalu"));
                cities.put("East Jaintia Hills", Arrays.asList("Khliehriat", "Saipung", "Lasan", "Thadlaskein", "Mukroh", "Rymbai", 
                                "Bataw", "Lumshnong", "Jowai", "Amlarem", "Mukroh", "Rymbai", "Bataw", "Lumshnong", "Jowai"));
                cities.put("West Jaintia Hills", Arrays.asList("Jowai", "Amlarem", "Mukroh", "Rymbai", "Bataw", "Lumshnong", "Khliehriat", 
                                "Saipung", "Lasan", "Thadlaskein", "Mukroh", "Rymbai", "Bataw", "Lumshnong", "Jowai"));
                cities.put("Ri Bhoi", Arrays.asList("Nongpoh", "Umroi", "Umling", "Mawphlang", "Mawryngkneng", "Pynursla", "Mawsynram", 
                                "Sohra", "Laitlyngkot", "Mawsmai", "Mawlynnong", "Cherrapunji", "Shillong", "Mawphlang", "Mawryngkneng"));
                cities.put("South West Khasi Hills", Arrays.asList("Mawkyrwat", "Mawthadraishan", "Mawshynrut", "Mawphlang", "Mawryngkneng", 
                                "Pynursla", "Mawsynram", "Sohra", "Laitlyngkot", "Mawsmai", "Mawlynnong", "Cherrapunji", "Nongstoin", 
                                "Mawkyrwat", "Mawthadraishan"));

                // Mizoram - All districts
                districts.put("Mizoram", Arrays.asList("Aizawl", "Champhai", "Hnahthial", "Khawzawl", "Kolasib", "Lawngtlai", "Lunglei", 
                                "Mamit", "Saiha", "Saitual", "Serchhip"));
                
                cities.put("Aizawl", Arrays.asList("Aizawl", "Durtlang", "Sairang", "Lengpui", "Bairabi", "Kolasib", "Vairengte", 
                                "Kawnpui", "Thenzawl", "Serchhip", "Lunglei", "Hnahthial", "Champhai", "Khawzawl", "Saitual"));
                cities.put("Lunglei", Arrays.asList("Lunglei", "Hnahthial", "Champhai", "Khawzawl", "Saitual", "Thenzawl", "Serchhip", 
                                "Aizawl", "Durtlang", "Sairang", "Lengpui", "Bairabi", "Kolasib", "Vairengte", "Kawnpui"));
                cities.put("Champhai", Arrays.asList("Champhai", "Khawzawl", "Saitual", "Thenzawl", "Serchhip", "Aizawl", "Durtlang", 
                                "Sairang", "Lengpui", "Bairabi", "Kolasib", "Vairengte", "Kawnpui", "Lunglei", "Hnahthial"));
                cities.put("Kolasib", Arrays.asList("Kolasib", "Vairengte", "Kawnpui", "Thenzawl", "Serchhip", "Aizawl", "Durtlang", 
                                "Sairang", "Lengpui", "Bairabi", "Kolasib", "Vairengte", "Kawnpui", "Lunglei", "Hnahthial"));
                cities.put("Serchhip", Arrays.asList("Serchhip", "Thenzawl", "Aizawl", "Durtlang", "Sairang", "Lengpui", "Bairabi", 
                                "Kolasib", "Vairengte", "Kawnpui", "Lunglei", "Hnahthial", "Champhai", "Khawzawl", "Saitual"));
                cities.put("Lawngtlai", Arrays.asList("Lawngtlai", "Saiha", "Tuipang", "Sangau", "Lunglei", "Hnahthial", "Champhai", 
                                "Khawzawl", "Saitual", "Thenzawl", "Serchhip", "Aizawl", "Durtlang", "Sairang", "Lengpui"));
                cities.put("Mamit", Arrays.asList("Mamit", "Reiek", "Dampa", "Khawzawl", "Saitual", "Thenzawl", "Serchhip", "Aizawl", 
                                "Durtlang", "Sairang", "Lengpui", "Bairabi", "Kolasib", "Vairengte", "Kawnpui"));
                cities.put("Saiha", Arrays.asList("Saiha", "Tuipang", "Sangau", "Lawngtlai", "Lunglei", "Hnahthial", "Champhai", 
                                "Khawzawl", "Saitual", "Thenzawl", "Serchhip", "Aizawl", "Durtlang", "Sairang", "Lengpui"));
                cities.put("Hnahthial", Arrays.asList("Hnahthial", "Lunglei", "Champhai", "Khawzawl", "Saitual", "Thenzawl", "Serchhip", 
                                "Aizawl", "Durtlang", "Sairang", "Lengpui", "Bairabi", "Kolasib", "Vairengte", "Kawnpui"));
                cities.put("Khawzawl", Arrays.asList("Khawzawl", "Saitual", "Thenzawl", "Serchhip", "Aizawl", "Durtlang", "Sairang", 
                                "Lengpui", "Bairabi", "Kolasib", "Vairengte", "Kawnpui", "Lunglei", "Hnahthial", "Champhai"));
                cities.put("Saitual", Arrays.asList("Saitual", "Thenzawl", "Serchhip", "Aizawl", "Durtlang", "Sairang", "Lengpui", 
                                "Bairabi", "Kolasib", "Vairengte", "Kawnpui", "Lunglei", "Hnahthial", "Champhai", "Khawzawl"));

                // Nagaland - All districts
                districts.put("Nagaland", Arrays.asList("Dimapur", "Kiphire", "Kohima", "Longleng", "Mokokchung", "Mon", "Peren", 
                                "Phek", "Tuensang", "Wokha", "Zunheboto"));
                
                cities.put("Kohima", Arrays.asList("Kohima", "Jakhama", "Kigwema", "Mao", "Viswema", "Chiephobozou", "Kezocha", "Medziphema", 
                                "Dimapur", "Chumukedima", "Sechu", "Zubza", "Phesama", "Khonoma", "Mima"));
                cities.put("Dimapur", Arrays.asList("Dimapur", "Chumukedima", "Sechu", "Zubza", "Phesama", "Khonoma", "Mima", "Kohima", 
                                "Jakhama", "Kigwema", "Mao", "Viswema", "Chiephobozou", "Kezocha", "Medziphema"));
                cities.put("Mokokchung", Arrays.asList("Mokokchung", "Tuli", "Changtongya", "Longkhim", "Kubolong", "Chuchuyimlang", 
                                "Longchem", "Alongkima", "Mangkolemba", "Alem", "Longsa", "Chare", "Longleng", "Yongnyah", "Phomching"));
                cities.put("Tuensang", Arrays.asList("Tuensang", "Noklak", "Noksen", "Longkhim", "Kubolong", "Chuchuyimlang", "Longchem", 
                                "Alongkima", "Mangkolemba", "Alem", "Longsa", "Chare", "Kiphire", "Pungro", "Seyochung"));
                cities.put("Wokha", Arrays.asList("Wokha", "Bhandari", "Sanis", "Lotsu", "Ralan", "Englan", "Changpang", "Aitepyong", 
                                "Lakhuti", "Sungro", "Merapani", "Niuland", "Chumukedima", "Sechu", "Zubza"));
                cities.put("Zunheboto", Arrays.asList("Zunheboto", "Akuluto", "Aghunato", "Satoi", "Suruhuto", "Sathazumi", "Ghathashi", 
                                "Sukhalu", "Satoi", "Suruhuto", "Sathazumi", "Ghathashi", "Sukhalu", "Akuluto", "Aghunato"));
                cities.put("Mon", Arrays.asList("Mon", "Tizit", "Naginimora", "Aboi", "Longshen", "Phomching", "Yongnyah", "Longleng", 
                                "Chare", "Longsa", "Alem", "Mangkolemba", "Alongkima", "Longchem", "Chuchuyimlang"));
                cities.put("Phek", Arrays.asList("Phek", "Pfutsero", "Chizami", "Khezhakeno", "Meluri", "Sekruzu", "Khuza", "Sakraba", 
                                "Kiphire", "Pungro", "Seyochung", "Tuensang", "Noklak", "Noksen", "Longkhim"));
                cities.put("Kiphire", Arrays.asList("Kiphire", "Pungro", "Seyochung", "Tuensang", "Noklak", "Noksen", "Longkhim", 
                                "Kubolong", "Chuchuyimlang", "Longchem", "Alongkima", "Mangkolemba", "Alem", "Longsa", "Chare"));
                cities.put("Longleng", Arrays.asList("Longleng", "Yongnyah", "Phomching", "Mon", "Tizit", "Naginimora", "Aboi", "Longshen", 
                                "Phomching", "Yongnyah", "Longleng", "Chare", "Longsa", "Alem", "Mangkolemba"));
                cities.put("Peren", Arrays.asList("Peren", "Jalukie", "Tening", "Athibung", "Dimapur", "Chumukedima", "Sechu", "Zubza", 
                                "Phesama", "Khonoma", "Mima", "Kohima", "Jakhama", "Kigwema", "Mao"));

                // Odisha - All districts
                districts.put("Odisha", Arrays.asList("Angul", "Balangir", "Balasore", "Bargarh", "Bhadrak", "Boudh", "Cuttack", 
                                "Deogarh", "Dhenkanal", "Gajapati", "Ganjam", "Jagatsinghpur", "Jajpur", "Jharsuguda", "Kalahandi", 
                                "Kandhamal", "Kendrapara", "Kendujhar", "Khordha", "Koraput", "Malkangiri", "Mayurbhanj", "Nabarangpur", 
                                "Nayagarh", "Nuapada", "Puri", "Rayagada", "Sambalpur", "Subarnapur", "Sundargarh"));
                
                cities.put("Khordha", Arrays.asList("Bhubaneswar", "Cuttack", "Puri", "Konark", "Pipili", "Nimapara", "Kakatpur", 
                                "Brahmagiri", "Gop", "Satyabadi", "Delang", "Kanpur", "Nayagarh", "Odagaon", "Ranpur"));
                cities.put("Cuttack", Arrays.asList("Cuttack", "Choudwar", "Athagarh", "Barang", "Banki", "Niali", "Tangi", "Salepur", 
                                "Kendrapada", "Jagatsinghpur", "Paradip", "Ersama", "Balikuda", "Tirtol", "Jagatsinghpur"));
                cities.put("Puri", Arrays.asList("Puri", "Konark", "Pipili", "Nimapara", "Kakatpur", "Brahmagiri", "Gop", "Satyabadi", 
                                "Delang", "Kanpur", "Nayagarh", "Odagaon", "Ranpur", "Bhubaneswar", "Cuttack"));
                cities.put("Sambalpur", Arrays.asList("Sambalpur", "Hirakud", "Jharsuguda", "Brajarajnagar", "Belpahar", "Lakhanpur", 
                                "Rengali", "Debagarh", "Barkot", "Kuchinda", "Rairakhol", "Bamra", "Sohela", "Titlagarh", "Balangir"));
                cities.put("Sundargarh", Arrays.asList("Rourkela", "Sundargarh", "Rajgangpur", "Birmitrapur", "Lathikata", "Koida", 
                                "Bonaigarh", "Subdega", "Hemgir", "Lephripara", "Talsara", "Bisra", "Nuagaon", "Gurundia", "Hatibari"));
                cities.put("Ganjam", Arrays.asList("Berhampur", "Gopalpur", "Chhatrapur", "Hinjilicut", "Kabisuryanagar", "Khalikote", 
                                "Aska", "Bhanjanagar", "Buguda", "Polasara", "Kodala", "Jarada", "Ganjam", "Chikiti", "Digapahandi"));
                cities.put("Balasore", Arrays.asList("Balasore", "Bhadrak", "Jaleswar", "Soro", "Simulia", "Basta", "Remuna", "Nilagiri", 
                                "Oupada", "Khunta", "Baleshwar", "Bahanaga", "Bhograi", "Baliapal", "Betnoti"));
                cities.put("Bhadrak", Arrays.asList("Bhadrak", "Dhamnagar", "Chandabali", "Basudebpur", "Tihidi", "Bhandaripokhari", 
                                "Bansada", "Agarpada", "Dhamara", "Tihidi", "Basudebpur", "Chandabali", "Dhamnagar", "Bhadrak", 
                                "Bhandaripokhari"));
                cities.put("Jharsuguda", Arrays.asList("Jharsuguda", "Brajarajnagar", "Belpahar", "Lakhanpur", "Rengali", "Debagarh", 
                                "Barkot", "Kuchinda", "Rairakhol", "Bamra", "Sohela", "Titlagarh", "Balangir", "Sambalpur", "Hirakud"));
                cities.put("Kendujhar", Arrays.asList("Kendujhar", "Barbil", "Joda", "Anandapur", "Ghasipura", "Harichandanpur", 
                                "Hatadihi", "Jhumpura", "Patna", "Telkoi", "Banspal", "Ghatgaon", "Kendujhar", "Barbil", "Joda"));
                cities.put("Mayurbhanj", Arrays.asList("Baripada", "Rairangpur", "Karanjia", "Udala", "Betnoti", "Badasahi", "Bangriposi", 
                                "Jashipur", "Kuliana", "Morada", "Suliapada", "Thakurmunda", "Tiring", "Bahalda", "Bisoi"));
                cities.put("Koraput", Arrays.asList("Koraput", "Jeypore", "Sunabeda", "Kotpad", "Borigumma", "Laxmipur", "Nandapur", 
                                "Pottangi", "Semiliguda", "Dasmantpur", "Narayanpatna", "Bandhugaon", "Machkund", "Pottangi", "Semiliguda"));
                cities.put("Rayagada", Arrays.asList("Rayagada", "Gunupur", "Bissam Cuttack", "Muniguda", "Kalyansingpur", "Padmapur", 
                                "Kashipur", "Thelkoloi", "Gudari", "Kolnara", "Chandrapur", "Kotpad", "Borigumma", "Laxmipur", "Nandapur"));
                cities.put("Nabarangpur", Arrays.asList("Nabarangpur", "Umerkote", "Papadahandi", "Kosagumda", "Raighar", "Tentulikhunti", 
                                "Dabugan", "Chhatriguda", "Kodinga", "Jharigam", "Khatiguda", "Kotpad", "Borigumma", "Laxmipur", "Nandapur"));
                cities.put("Malkangiri", Arrays.asList("Malkangiri", "Motu", "Kalimela", "Korukonda", "Mathili", "Kudumulgumma", "Podia", 
                                "Chitrakonda", "Orkel", "Papulur", "Jodambo", "Koraput", "Jeypore", "Sunabeda", "Kotpad"));
                cities.put("Kalahandi", Arrays.asList("Bhawanipatna", "Dharamgarh", "Kesinga", "Junagarh", "Golamunda", "Narla", "Karlamunda", 
                                "M. Rampur", "Lanjigarh", "Thuamul Rampur", "Madanpur Rampur", "Sambalpur", "Hirakud", "Jharsuguda", 
                                "Brajarajnagar"));
                cities.put("Balangir", Arrays.asList("Balangir", "Titlagarh", "Patnagarh", "Kantabanji", "Loisingha", "Turekela", "Saintala", 
                                "Tusura", "Deogaon", "Belpada", "Muribahal", "Puintala", "Bangomunda", "Sohela", "Titlagarh"));
                cities.put("Nuapada", Arrays.asList("Nuapada", "Komna", "Khariar", "Sinapali", "Boden", "Tarbha", "Khariar Road", "Sinapali", 
                                "Boden", "Tarbha", "Khariar Road", "Nuapada", "Komna", "Khariar", "Sinapali"));
                cities.put("Bargarh", Arrays.asList("Bargarh", "Attabira", "Bheden", "Bijepur", "Barapali", "Sohela", "Titlagarh", 
                                "Balangir", "Patnagarh", "Kantabanji", "Loisingha", "Turekela", "Saintala", "Tusura", "Deogaon"));
                cities.put("Jagatsinghpur", Arrays.asList("Jagatsinghpur", "Paradip", "Ersama", "Balikuda", "Tirtol", "Jagatsinghpur", 
                                "Kendrapada", "Jajpur", "Dharmasala", "Bari", "Binjharpur", "Dasarathpur", "Jajpur", "Dharmasala", "Bari"));
                cities.put("Jajpur", Arrays.asList("Jajpur", "Dharmasala", "Bari", "Binjharpur", "Dasarathpur", "Jajpur", "Dharmasala", 
                                "Bari", "Binjharpur", "Dasarathpur", "Jagatsinghpur", "Paradip", "Ersama", "Balikuda", "Tirtol"));
                cities.put("Kendrapara", Arrays.asList("Kendrapara", "Pattamundai", "Aul", "Rajkanika", "Garadpur", "Derabish", "Marsaghai", 
                                "Mahakalapada", "Rajnagar", "Jagatsinghpur", "Paradip", "Ersama", "Balikuda", "Tirtol", "Jagatsinghpur"));
                cities.put("Dhenkanal", Arrays.asList("Dhenkanal", "Kamakhyanagar", "Gondia", "Parjang", "Bhuban", "Hindol", "Odapada", 
                                "Talcher", "Angul", "Athmallik", "Pallahara", "Banarpal", "Chhendipada", "Khamar", "Kaniha"));
                cities.put("Angul", Arrays.asList("Angul", "Talcher", "Athmallik", "Pallahara", "Banarpal", "Chhendipada", "Khamar", 
                                "Kaniha", "Dhenkanal", "Kamakhyanagar", "Gondia", "Parjang", "Bhuban", "Hindol", "Odapada"));
                cities.put("Nayagarh", Arrays.asList("Nayagarh", "Odagaon", "Ranpur", "Bhubaneswar", "Cuttack", "Puri", "Konark", "Pipili", 
                                "Nimapara", "Kakatpur", "Brahmagiri", "Gop", "Satyabadi", "Delang", "Kanpur"));
                cities.put("Gajapati", Arrays.asList("Paralakhemundi", "Gumma", "Mohana", "R. Udayagiri", "Rayagada", "Gunupur", "Bissam Cuttack", 
                                "Muniguda", "Kalyansingpur", "Padmapur", "Kashipur", "Thelkoloi", "Gudari", "Kolnara", "Chandrapur"));
                cities.put("Kandhamal", Arrays.asList("Phulbani", "G. Udayagiri", "Tumudibandha", "Raikia", "Daringbadi", "Kotagarh", "Baliguda", 
                                "Tikabali", "Nuagaon", "Gurundia", "Hatibari", "Rourkela", "Sundargarh", "Rajgangpur", "Birmitrapur"));
                cities.put("Boudh", Arrays.asList("Boudh", "Kantamal", "Manamunda", "Harabhanga", "Boudh", "Kantamal", "Manamunda", "Harabhanga", 
                                "Phulbani", "G. Udayagiri", "Tumudibandha", "Raikia", "Daringbadi", "Kotagarh", "Baliguda"));
                cities.put("Deogarh", Arrays.asList("Deogarh", "Barkot", "Kuchinda", "Rairakhol", "Bamra", "Sohela", "Titlagarh", "Balangir", 
                                "Patnagarh", "Kantabanji", "Loisingha", "Turekela", "Saintala", "Tusura", "Deogaon"));
                cities.put("Subarnapur", Arrays.asList("Sonepur", "Birmaharajpur", "Ulunda", "Binika", "Tarbha", "Khariar Road", "Nuapada", 
                                "Komna", "Khariar", "Sinapali", "Boden", "Tarbha", "Khariar Road", "Nuapada", "Komna"));

                // Sikkim - All districts
                districts.put("Sikkim", Arrays.asList("East Sikkim", "North Sikkim", "South Sikkim", "West Sikkim"));
                
                cities.put("East Sikkim", Arrays.asList("Gangtok", "Rangpo", "Singtam", "Rhenock", "Rongli", "Pakyong", "Dikchu", "Mangan", 
                                "Chungthang", "Lachen", "Lachung", "Yumthang", "Namchi", "Jorethang", "Melli"));
                cities.put("West Sikkim", Arrays.asList("Geyzing", "Pelling", "Yuksom", "Tashiding", "Rinchenpong", "Soreng", "Kaluk", 
                                "Ravangla", "Namchi", "Jorethang", "Melli", "Gangtok", "Rangpo", "Singtam", "Rhenock"));
                cities.put("South Sikkim", Arrays.asList("Namchi", "Jorethang", "Melli", "Ravangla", "Temi", "Mamley", "Rangpo", "Singtam", 
                                "Rhenock", "Rongli", "Pakyong", "Dikchu", "Gangtok", "Rangpo", "Singtam"));
                cities.put("North Sikkim", Arrays.asList("Mangan", "Chungthang", "Lachen", "Lachung", "Yumthang", "Dikchu", "Gangtok", 
                                "Rangpo", "Singtam", "Rhenock", "Rongli", "Pakyong", "Mangan", "Chungthang", "Lachen"));

                // Tripura - All districts
                districts.put("Tripura", Arrays.asList("Dhalai", "Gomati", "Khowai", "North Tripura", "Sepahijala", "South Tripura", 
                                "Unakoti", "West Tripura"));
                
                cities.put("West Tripura", Arrays.asList("Agartala", "Udaipur", "Amarpur", "Khowai", "Teliamura", "Ambassa", "Dharmanagar", 
                                "Kailasahar", "Belonia", "Sabroom", "Sonamura", "Melaghar", "Bishalgarh", "Jirania", "Mohanpur"));
                cities.put("South Tripura", Arrays.asList("Belonia", "Sabroom", "Sonamura", "Melaghar", "Bishalgarh", "Jirania", "Mohanpur", 
                                "Agartala", "Udaipur", "Amarpur", "Khowai", "Teliamura", "Ambassa", "Dharmanagar", "Kailasahar"));
                cities.put("North Tripura", Arrays.asList("Dharmanagar", "Kailasahar", "Belonia", "Sabroom", "Sonamura", "Melaghar", 
                                "Bishalgarh", "Jirania", "Mohanpur", "Agartala", "Udaipur", "Amarpur", "Khowai", "Teliamura", "Ambassa"));
                cities.put("Dhalai", Arrays.asList("Ambassa", "Dharmanagar", "Kailasahar", "Belonia", "Sabroom", "Sonamura", "Melaghar", 
                                "Bishalgarh", "Jirania", "Mohanpur", "Agartala", "Udaipur", "Amarpur", "Khowai", "Teliamura"));
                cities.put("Khowai", Arrays.asList("Khowai", "Teliamura", "Ambassa", "Dharmanagar", "Kailasahar", "Belonia", "Sabroom", 
                                "Sonamura", "Melaghar", "Bishalgarh", "Jirania", "Mohanpur", "Agartala", "Udaipur", "Amarpur"));
                cities.put("Sepahijala", Arrays.asList("Bishalgarh", "Jirania", "Mohanpur", "Agartala", "Udaipur", "Amarpur", "Khowai", 
                                "Teliamura", "Ambassa", "Dharmanagar", "Kailasahar", "Belonia", "Sabroom", "Sonamura", "Melaghar"));
                cities.put("Gomati", Arrays.asList("Udaipur", "Amarpur", "Khowai", "Teliamura", "Ambassa", "Dharmanagar", "Kailasahar", 
                                "Belonia", "Sabroom", "Sonamura", "Melaghar", "Bishalgarh", "Jirania", "Mohanpur", "Agartala"));
                cities.put("Unakoti", Arrays.asList("Kailasahar", "Belonia", "Sabroom", "Sonamura", "Melaghar", "Bishalgarh", "Jirania", 
                                "Mohanpur", "Agartala", "Udaipur", "Amarpur", "Khowai", "Teliamura", "Ambassa", "Dharmanagar"));

                // Uttarakhand - All districts
                districts.put("Uttarakhand", Arrays.asList("Almora", "Bageshwar", "Chamoli", "Champawat", "Dehradun", "Haridwar", "Nainital", 
                                "Pauri Garhwal", "Pithoragarh", "Rudraprayag", "Tehri Garhwal", "Udham Singh Nagar", "Uttarkashi"));
                
                cities.put("Dehradun", Arrays.asList("Dehradun", "Mussoorie", "Landour", "Chakrata", "Vikasnagar", "Doiwala", "Sahaspur", 
                                "Rishikesh", "Haridwar", "Roorkee", "Laksar", "Jhabrera", "Bhagwanpur", "Dhanpuri", "Kotdwar"));
                cities.put("Haridwar", Arrays.asList("Haridwar", "Rishikesh", "Roorkee", "Laksar", "Jhabrera", "Bhagwanpur", "Dhanpuri", 
                                "Kotdwar", "Dehradun", "Mussoorie", "Landour", "Chakrata", "Vikasnagar", "Doiwala", "Sahaspur"));
                cities.put("Nainital", Arrays.asList("Nainital", "Haldwani", "Ramnagar", "Kaladhungi", "Bhimtal", "Sattal", "Mukteshwar", 
                                "Ranikhet", "Almora", "Bageshwar", "Kausani", "Baijnath", "Gwaldam", "Champawat", "Lohaghat"));
                cities.put("Almora", Arrays.asList("Almora", "Ranikhet", "Kausani", "Baijnath", "Gwaldam", "Champawat", "Lohaghat", 
                                "Bageshwar", "Kapkot", "Garur", "Bhatrojkhan", "Dwarahat", "Someshwar", "Takula", "Jageshwar"));
                cities.put("Pauri Garhwal", Arrays.asList("Pauri", "Kotdwar", "Lansdowne", "Srinagar", "Devprayag", "Rudraprayag", 
                                "Kedarnath", "Guptkashi", "Ukhimath", "Gopeshwar", "Chamoli", "Joshimath", "Auli", "Badrinath", "Mana"));
                cities.put("Tehri Garhwal", Arrays.asList("Tehri", "New Tehri", "Chamba", "Ghansali", "Devprayag", "Rishikesh", "Haridwar", 
                                "Roorkee", "Laksar", "Jhabrera", "Bhagwanpur", "Dhanpuri", "Kotdwar", "Dehradun", "Mussoorie"));
                cities.put("Udham Singh Nagar", Arrays.asList("Rudrapur", "Kashipur", "Kichha", "Sitarganj", "Jaspur", "Bazpur", "Gadarpur", 
                                "Pantnagar", "Haldwani", "Ramnagar", "Kaladhungi", "Bhimtal", "Sattal", "Mukteshwar", "Ranikhet"));
                cities.put("Chamoli", Arrays.asList("Gopeshwar", "Joshimath", "Auli", "Badrinath", "Mana", "Kedarnath", "Guptkashi", 
                                "Ukhimath", "Pauri", "Kotdwar", "Lansdowne", "Srinagar", "Devprayag", "Rudraprayag", "Kedarnath"));
                cities.put("Pithoragarh", Arrays.asList("Pithoragarh", "Dharchula", "Munsiyari", "Thal", "Berinag", "Gangolihat", "Kapkot", 
                                "Bageshwar", "Almora", "Ranikhet", "Kausani", "Baijnath", "Gwaldam", "Champawat", "Lohaghat"));
                cities.put("Bageshwar", Arrays.asList("Bageshwar", "Kapkot", "Garur", "Bhatrojkhan", "Dwarahat", "Someshwar", "Takula", 
                                "Jageshwar", "Almora", "Ranikhet", "Kausani", "Baijnath", "Gwaldam", "Champawat", "Lohaghat"));
                cities.put("Champawat", Arrays.asList("Champawat", "Lohaghat", "Tanakpur", "Purnagiri", "Banbasa", "Khatima", "Sitarganj", 
                                "Jaspur", "Bazpur", "Gadarpur", "Rudrapur", "Kashipur", "Kichha", "Sitarganj", "Jaspur"));
                cities.put("Rudraprayag", Arrays.asList("Rudraprayag", "Kedarnath", "Guptkashi", "Ukhimath", "Gopeshwar", "Joshimath", 
                                "Auli", "Badrinath", "Mana", "Pauri", "Kotdwar", "Lansdowne", "Srinagar", "Devprayag", "Rudraprayag"));
                cities.put("Uttarkashi", Arrays.asList("Uttarkashi", "Barkot", "Yamunotri", "Gangotri", "Harsil", "Dharasu", "Chinyalisaur", 
                                "Purola", "Mori", "Naitwar", "Sankri", "Osla", "Jakholi", "Bhatwari", "Netala"));

                // Jammu & Kashmir - All districts
                districts.put("Jammu&Kashmir", Arrays.asList("Anantnag", "Bandipora", "Baramulla", "Budgam", "Doda", "Ganderbal", "Jammu", 
                                "Kargil", "Kathua", "Kishtwar", "Kulgam", "Kupwara", "Leh", "Poonch", "Pulwama", "Rajouri", "Ramban", 
                                "Reasi", "Samba", "Shopian", "Srinagar", "Udhampur"));
                
                cities.put("Srinagar", Arrays.asList("Srinagar", "Ganderbal", "Sonamarg", "Kangan", "Pahalgam", "Anantnag", "Bijbehara", 
                                "Kulgam", "Shopian", "Pulwama", "Awantipora", "Tral", "Budgam", "Charar-e-Sharif", "Magam"));
                cities.put("Jammu", Arrays.asList("Jammu", "Akhnoor", "Samba", "Vijaypur", "Bishnah", "R.S. Pura", "Arnia", "Marh", 
                                "Nagrota", "Jourian", "Khour", "Phallian", "Mandal", "Chhamb", "Nowshera"));
                cities.put("Anantnag", Arrays.asList("Anantnag", "Bijbehara", "Kulgam", "Shopian", "Pulwama", "Awantipora", "Tral", 
                                "Pahalgam", "Achabal", "Kokernag", "Verinag", "Qazigund", "Dooru", "Shangus", "Mattan"));
                cities.put("Baramulla", Arrays.asList("Baramulla", "Sopore", "Pattan", "Uri", "Tangmarg", "Gulmarg", "Kupwara", "Handwara", 
                                "Langate", "Kralpora", "Sogam", "Lolab", "Dangiwacha", "Rafiabad", "Boniyar"));
                cities.put("Budgam", Arrays.asList("Budgam", "Charar-e-Sharif", "Magam", "Beerwah", "Khansahib", "Chadoora", "Soibugh", 
                                "Narbal", "Ganderbal", "Sonamarg", "Kangan", "Pahalgam", "Anantnag", "Bijbehara", "Kulgam"));
                cities.put("Pulwama", Arrays.asList("Pulwama", "Awantipora", "Tral", "Pampore", "Kakapora", "Litter", "Rajpora", "Tahab", 
                                "Gulzarpora", "Newa", "Ladhoo", "Nihama", "Karewa", "Dangerpora", "Rohmu"));
                cities.put("Kupwara", Arrays.asList("Kupwara", "Handwara", "Langate", "Kralpora", "Sogam", "Lolab", "Dangiwacha", 
                                "Rafiabad", "Boniyar", "Baramulla", "Sopore", "Pattan", "Uri", "Tangmarg", "Gulmarg"));
                cities.put("Kulgam", Arrays.asList("Kulgam", "Shopian", "Pulwama", "Awantipora", "Tral", "Anantnag", "Bijbehara", "Pahalgam", 
                                "Achabal", "Kokernag", "Verinag", "Qazigund", "Dooru", "Shangus", "Mattan"));
                cities.put("Shopian", Arrays.asList("Shopian", "Pulwama", "Awantipora", "Tral", "Kulgam", "Anantnag", "Bijbehara", 
                                "Pahalgam", "Achabal", "Kokernag", "Verinag", "Qazigund", "Dooru", "Shangus", "Mattan"));
                cities.put("Ganderbal", Arrays.asList("Ganderbal", "Sonamarg", "Kangan", "Pahalgam", "Anantnag", "Bijbehara", "Kulgam", 
                                "Shopian", "Pulwama", "Awantipora", "Tral", "Budgam", "Charar-e-Sharif", "Magam", "Beerwah"));
                cities.put("Bandipora", Arrays.asList("Bandipora", "Sumbal", "Sonawari", "Ganderbal", "Sonamarg", "Kangan", "Pahalgam", 
                                "Anantnag", "Bijbehara", "Kulgam", "Shopian", "Pulwama", "Awantipora", "Tral", "Budgam"));
                cities.put("Leh", Arrays.asList("Leh", "Kargil", "Drass", "Zanskar", "Nubra", "Changthang", "Nyoma", "Durbuk", "Khaltsi", 
                                "Diskit", "Hunder", "Turtuk", "Panamik", "Sumur", "Sakti"));
                cities.put("Kargil", Arrays.asList("Kargil", "Drass", "Zanskar", "Leh", "Nubra", "Changthang", "Nyoma", "Durbuk", "Khaltsi", 
                                "Diskit", "Hunder", "Turtuk", "Panamik", "Sumur", "Sakti"));
                cities.put("Udhampur", Arrays.asList("Udhampur", "Reasi", "Katra", "Kud", "Ramnagar", "Chenani", "Majalta", "Gool", 
                                "Gulabgarh", "Pouni", "Arnas", "Thathri", "Doda", "Bhaderwah", "Gandoh"));
                cities.put("Doda", Arrays.asList("Doda", "Bhaderwah", "Gandoh", "Thathri", "Kishtwar", "Paddar", "Marwah", "Wadwan", 
                                "Bunjwah", "Chatroo", "Inderwal", "Parlanka", "Bhalla", "Assar", "Dachhan"));
                cities.put("Kishtwar", Arrays.asList("Kishtwar", "Paddar", "Marwah", "Wadwan", "Bunjwah", "Chatroo", "Inderwal", "Parlanka", 
                                "Bhalla", "Assar", "Dachhan", "Doda", "Bhaderwah", "Gandoh", "Thathri"));
                cities.put("Rajouri", Arrays.asList("Rajouri", "Nowshera", "Sunderbani", "Kalakote", "Thanamandi", "Darhal", "Budhal", 
                                "Koteranka", "Manjakote", "Doongi", "Poonch", "Mendhar", "Surankote", "Haveli", "Loran"));
                cities.put("Poonch", Arrays.asList("Poonch", "Mendhar", "Surankote", "Haveli", "Loran", "Rajouri", "Nowshera", "Sunderbani", 
                                "Kalakote", "Thanamandi", "Darhal", "Budhal", "Koteranka", "Manjakote", "Doongi"));
                cities.put("Ramban", Arrays.asList("Ramban", "Banihal", "Gool", "Gulabgarh", "Pouni", "Arnas", "Thathri", "Doda", 
                                "Bhaderwah", "Gandoh", "Kishtwar", "Paddar", "Marwah", "Wadwan", "Bunjwah"));
                cities.put("Reasi", Arrays.asList("Reasi", "Katra", "Kud", "Ramnagar", "Chenani", "Majalta", "Gool", "Gulabgarh", "Pouni", 
                                "Arnas", "Thathri", "Doda", "Bhaderwah", "Gandoh", "Udhampur"));
                cities.put("Kathua", Arrays.asList("Kathua", "Hiranagar", "Basholi", "Billawar", "Bani", "Mahanpur", "Lakhanpur", "Nagri", 
                                "Marheen", "Dinga Amb", "Samba", "Vijaypur", "Bishnah", "R.S. Pura", "Arnia"));
                cities.put("Samba", Arrays.asList("Samba", "Vijaypur", "Bishnah", "R.S. Pura", "Arnia", "Marh", "Nagrota", "Jourian", 
                                "Khour", "Phallian", "Mandal", "Chhamb", "Nowshera", "Jammu", "Akhnoor"));
        }

        @GetMapping("/states")
        public List<String> getStates() {
                return states;
        }

        @GetMapping("/districts")
        public List<String> getDistricts(@RequestParam String state) {
                return districts.getOrDefault(state, Collections.emptyList());
        }

        @GetMapping("/cities")
        public List<String> getCities(@RequestParam String district) {
                return cities.getOrDefault(district, Collections.emptyList());
        }

        @CrossOrigin(origins = "http://localhost:5173")
        @GetMapping("/babies")
        public List<BabyPost> getBabies(@RequestParam(required = false) String state,
                        @RequestParam(required = false) String district,
                        @RequestParam(required = false) String city) {
                return babyPosts.stream()
                                .filter(b -> b.getStatus() == PostStatus.APPROVED) // Only show approved posts
                                .filter(b -> b.getCreatedAt().isAfter(LocalDateTime.now().minusDays(7))) // Not expired
                                .filter(b -> (state == null || state.isEmpty() || b.getState().equals(state)) &&
                                                (district == null || district.isEmpty()
                                                                || b.getDistrict().equals(district))
                                                &&
                                                (city == null || city.isEmpty() || b.getCity().equals(city)))
                                .collect(Collectors.toList());
        }

        private static final Logger logger = LoggerFactory.getLogger(BabyController.class);

        @PostMapping("/babies")
        public ResponseEntity<?> addBabyPost(
                @RequestParam String name,
                @RequestParam String description,
                @RequestParam String phone,
                @RequestParam(required = false) String whatsapp,
                @RequestParam String state,
                @RequestParam String district,
                @RequestParam String city,
                @RequestParam String category,
                @RequestParam String address,
                @RequestParam String postalcode,
                @RequestParam(required = false) String age,
                @RequestParam String nickname,
                @RequestParam String title,
                @RequestParam String text,
                @RequestParam String ethnicity,
                @RequestParam String nationality,
                @RequestParam String bodytype,
                @RequestParam String services,
                @RequestParam String place,
                @RequestParam(required = false) MultipartFile[] images,
                @RequestHeader(value = "Authorization", required = false) String token,
                HttpServletRequest request) {
                try {
                        // Log incoming request content type and some headers for debugging
                        String ct = request.getContentType();
                        StringBuilder hdrs = new StringBuilder();
                        var names = request.getHeaderNames();
                        if (names != null) {
                                while (names.hasMoreElements()) {
                                        String hn = names.nextElement();
                                        hdrs.append(hn).append("=").append(request.getHeader(hn)).append("; ");
                                }
                        }
                        logger.info("Incoming POST /babies contentType={} headers={}", ct, hdrs.toString());

                        // Token/user validation
                        if (token == null || token.trim().isEmpty()) {
                                return ResponseEntity.status(401).body("Missing Authorization header");
                        }
                        int userId = getUserIdFromToken(token);
                        if (userId == -1) {
                                return ResponseEntity.status(401).body("Invalid token");
                        }
                        if (!canAddPost(token)) {
                                return ResponseEntity.status(403).body("Access denied: Only employees and admins can add posts");
                        }

                        int newId = babyPosts.stream()
                                        .mapToInt(BabyPost::getId)
                                        .max()
                                        .orElse(0) + 1;

                        int ageInt = 0;
                        if (age != null && !age.trim().isEmpty()) {
                                try {
                                        ageInt = Integer.parseInt(age.trim());
                                } catch (NumberFormatException nfe) {
                                        return ResponseEntity.badRequest().body("Invalid age value");
                                }
                        }

                        BabyPost postToAdd = new BabyPost(
                                        newId,
                                        name,
                                        description,
                                        phone,
                                        whatsapp == null ? "" : whatsapp,
                                        "", // imageUrl will be handled separately for file uploads
                                        state,
                                        district,
                                        city,
                                        category,
                                        address,
                                        postalcode,
                                        ageInt,
                                        nickname,
                                        title,
                                        text,
                                        ethnicity,
                                        nationality,
                                        bodytype,
                                        services,
                                        place,
                                        LocalDateTime.now(),
                                        PostStatus.PENDING,
                                        userId);

                        // TODO: handle `images` (save to storage and set imageUrl)
                        babyPosts.add(postToAdd);
                        return ResponseEntity.ok(postToAdd);
                } catch (Exception ex) {
                        return ResponseEntity.status(500).body("Server error: " + ex.getMessage());
                }
        }

        @CrossOrigin(origins = "http://localhost:5173")
        @GetMapping("/babies/{id}")
        public BabyPost getBabyById(@PathVariable int id) {
                return babyPosts.stream()
                                .filter(b -> b.getId() == id)
                                .findFirst()
                                .orElse(null);
        }

        @CrossOrigin(origins = "http://localhost:5173")
        @GetMapping("/admin/posts")
        public ResponseEntity<?> getPendingPosts(@RequestHeader(value = "Authorization", required = false) String token) {
                if (token == null || token.trim().isEmpty()) {
                        return ResponseEntity.status(401).body("Missing Authorization header");
                }
                if (!isAdmin(token)) {
                        return ResponseEntity.status(403).body("Access denied");
                }
                List<BabyPost> pendingPosts = babyPosts.stream()
                                .filter(b -> b.getStatus() == PostStatus.PENDING)
                                .collect(Collectors.toList());
                return ResponseEntity.ok(pendingPosts);
        }

        @CrossOrigin(origins = "http://localhost:5173")
        @PutMapping("/admin/posts/{id}/approve")
        public ResponseEntity<?> approvePost(@PathVariable int id, @RequestHeader(value = "Authorization", required = false) String token) {
                if (token == null || token.trim().isEmpty()) {
                        return ResponseEntity.status(401).body("Missing Authorization header");
                }
                if (!isAdmin(token)) {
                        return ResponseEntity.status(403).body("Access denied");
                }
                Optional<BabyPost> post = babyPosts.stream().filter(b -> b.getId() == id).findFirst();
                if (post.isPresent()) {
                        post.get().setStatus(PostStatus.APPROVED);
                        return ResponseEntity.ok("Post approved");
                } else {
                        return ResponseEntity.notFound().build();
                }
        }

        @CrossOrigin(origins = "http://localhost:5173")
        @PutMapping("/admin/posts/{id}/reject")
        public ResponseEntity<?> rejectPost(@PathVariable int id, @RequestHeader(value = "Authorization", required = false) String token) {
                if (token == null || token.trim().isEmpty()) {
                        return ResponseEntity.status(401).body("Missing Authorization header");
                }
                if (!isAdmin(token)) {
                        return ResponseEntity.status(403).body("Access denied");
                }
                Optional<BabyPost> post = babyPosts.stream().filter(b -> b.getId() == id).findFirst();
                if (post.isPresent()) {
                        babyPosts.remove(post.get());
                        return ResponseEntity.ok("Post rejected");
                } else {
                        return ResponseEntity.notFound().build();
                }
        }

        private int getUserIdFromToken(String token) {
                try {
                        return (int) Jwts.parserBuilder().setSigningKey(UserController.SECRET_KEY)
                                        .build()
                                        .parseClaimsJws(token.replace("Bearer ", "")).getBody().get("userId");
                } catch (Exception e) {
                        return -1;
                }
        }

        private boolean isAdmin(String token) {
                try {
                        String role = Jwts.parserBuilder().setSigningKey(UserController.SECRET_KEY)
                                        .build()
                                        .parseClaimsJws(token.replace("Bearer ", "")).getBody()
                                        .get("role", String.class);
                        return "ADMIN".equals(role);
                } catch (Exception e) {
                        return false;
                }
        }

        private boolean canAddPost(String token) {
                try {
                        // All authenticated users can add posts
                        Jwts.parserBuilder().setSigningKey(UserController.SECRET_KEY)
                                        .build()
                                        .parseClaimsJws(token.replace("Bearer ", ""));
                        return true; // Token is valid, user can add post
                } catch (Exception e) {
                        return false;
                }
        }
}
