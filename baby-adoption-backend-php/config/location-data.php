<?php
/**
 * Location Data - States, Districts, Cities
 * Java BabyController.java se complete data convert kiya gaya
 * Same functionality - same data
 */

function getStatesList() {
    return [
        "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat",
        "Haryana", "Himachal Pradesh", "Jharkhand", "Karnataka", "Kerala", "Madhya Pradesh",
        "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab",
        "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh",
        "Uttarakhand", "West Bengal", "Jammu&Kashmir"
    ];
}

function getDistrictsMap() {
    return [
        "Andhra Pradesh" => [
            "Anantapur", "Chittoor", "East Godavari", "Guntur", "Krishna", "Kurnool",
            "Prakasam", "Srikakulam", "Visakhapatnam", "Vizianagaram", "West Godavari",
            "YSR Kadapa", "Nellore", "Sri Potti Sriramulu Nellore"
        ],
        "Arunachal Pradesh" => [
            "Tawang", "West Kameng", "East Kameng", "Papum Pare", "Kurung Kumey", "Kra Daadi",
            "Lower Subansiri", "Upper Subansiri", "West Siang", "East Siang", "Siang", "Upper Siang",
            "Lower Siang", "Lower Dibang Valley", "Dibang Valley", "Anjaw", "Lohit", "Namsai",
            "Changlang", "Tirap", "Longding"
        ],
        "Assam" => [
            "Baksa", "Barpeta", "Biswanath", "Bongaigaon", "Cachar", "Charaideo", "Chirang",
            "Darrang", "Dhemaji", "Dhubri", "Dibrugarh", "Dima Hasao", "Goalpara", "Golaghat",
            "Hailakandi", "Hojai", "Jorhat", "Kamrup", "Kamrup Metropolitan", "Karbi Anglong",
            "Karimganj", "Kokrajhar", "Lakhimpur", "Majuli", "Morigaon", "Nagaon", "Nalbari",
            "Sivasagar", "Sonitpur", "South Salmara-Mankachar", "Tinsukia", "Udalguri", "West Karbi Anglong"
        ],
        "Bihar" => [
            "Araria", "Arwal", "Aurangabad", "Banka", "Begusarai", "Bhagalpur", "Bhojpur", "Buxar",
            "Darbhanga", "East Champaran", "Gaya", "Gopalganj", "Jamui", "Jehanabad", "Kaimur",
            "Katihar", "Khagaria", "Kishanganj", "Lakhisarai", "Madhepura", "Madhubani", "Munger",
            "Muzaffarpur", "Nalanda", "Nawada", "Patna", "Purnia", "Rohtas", "Saharsa", "Samastipur",
            "Saran", "Sheikhpura", "Sheohar", "Sitamarhi", "Siwan", "Supaul", "Vaishali", "West Champaran"
        ],
        "Chhattisgarh" => [
            "Balod", "Baloda Bazar", "Balrampur", "Bastar", "Bemetara", "Bijapur", "Bilaspur",
            "Dantewada", "Dhamtari", "Durg", "Gariaband", "Gaurela-Pendra-Marwahi", "Janjgir-Champa",
            "Jashpur", "Kabeerdham", "Kanker", "Kondagaon", "Korba", "Koriya", "Mahasamund",
            "Mungeli", "Narayanpur", "Raigarh", "Raipur", "Rajnandgaon", "Sukma", "Surajpur", "Surguja"
        ],
        "Goa" => ["North Goa", "South Goa"],
        "Gujarat" => [
            "Ahmedabad", "Amreli", "Anand", "Aravalli", "Banaskantha", "Bharuch", "Bhavnagar",
            "Botad", "Chhota Udaipur", "Dahod", "Dang", "Devbhoomi Dwarka", "Gandhinagar",
            "Gir Somnath", "Jamnagar", "Junagadh", "Kachchh", "Kheda", "Mahisagar", "Mehsana",
            "Morbi", "Narmada", "Navsari", "Panchmahal", "Patan", "Porbandar", "Rajkot",
            "Sabarkantha", "Surat", "Surendranagar", "Tapi", "Vadodara", "Valsad"
        ],
        "Haryana" => [
            "Ambala", "Bhiwani", "Charkhi Dadri", "Faridabad", "Fatehabad", "Gurugram", "Hisar",
            "Jhajjar", "Jind", "Kaithal", "Karnal", "Kurukshetra", "Mahendragarh", "Nuh", "Palwal",
            "Panchkula", "Panipat", "Rewari", "Rohtak", "Sirsa", "Sonipat", "Yamunanagar"
        ],
        "Himachal Pradesh" => [
            "Bilaspur", "Chamba", "Hamirpur", "Kangra", "Kinnaur", "Kullu", "Lahaul and Spiti",
            "Mandi", "Shimla", "Sirmaur", "Solan", "Una"
        ],
        "Jharkhand" => [
            "Bokaro", "Chatra", "Deoghar", "Dhanbad", "Dumka", "East Singhbhum", "Garhwa",
            "Giridih", "Godda", "Gumla", "Hazaribagh", "Jamtara", "Khunti", "Koderma", "Latehar",
            "Lohardaga", "Pakur", "Palamu", "Ramgarh", "Ranchi", "Sahibganj", "Seraikela Kharsawan",
            "Simdega", "West Singhbhum"
        ],
        "Karnataka" => [
            "Bagalkot", "Ballari", "Belagavi", "Bengaluru Rural", "Bengaluru Urban", "Bidar",
            "Chamarajanagar", "Chikkaballapur", "Chikkamagaluru", "Chitradurga", "Dakshina Kannada",
            "Davanagere", "Dharwad", "Gadag", "Hassan", "Haveri", "Kalaburagi", "Kodagu", "Kolar",
            "Koppal", "Mandya", "Mysuru", "Raichur", "Ramanagara", "Shivamogga", "Tumakuru",
            "Udupi", "Uttara Kannada", "Vijayapura", "Vijayanagara", "Yadgir"
        ],
        "Kerala" => [
            "Alappuzha", "Ernakulam", "Idukki", "Kannur", "Kasaragod", "Kollam", "Kottayam",
            "Kozhikode", "Malappuram", "Palakkad", "Pathanamthitta", "Thiruvananthapuram", "Thrissur", "Wayanad"
        ],
        "Madhya Pradesh" => [
            "Agar Malwa", "Alirajpur", "Anuppur", "Ashoknagar", "Balaghat", "Barwani", "Betul",
            "Bhind", "Bhopal", "Burhanpur", "Chhatarpur", "Chhindwara", "Damoh", "Datia", "Dewas",
            "Dhar", "Dindori", "Guna", "Gwalior", "Harda", "Hoshangabad", "Indore", "Jabalpur",
            "Jhabua", "Katni", "Khandwa", "Khargone", "Mandla", "Mandsaur", "Morena", "Narsinghpur",
            "Neemuch", "Panna", "Raisen", "Rajgarh", "Ratlam", "Rewa", "Sagar", "Satna", "Sehore",
            "Seoni", "Shahdol", "Shajapur", "Sheopur", "Shivpuri", "Sidhi", "Singrauli", "Tikamgarh",
            "Ujjain", "Umaria", "Vidisha"
        ],
        "Maharashtra" => [
            "Ahmednagar", "Akola", "Amravati", "Aurangabad", "Beed", "Bhandara", "Buldhana",
            "Chandrapur", "Dhule", "Gadchiroli", "Gondia", "Hingoli", "Jalgaon", "Jalna", "Kolhapur",
            "Latur", "Mumbai", "Mumbai Suburban", "Nagpur", "Nanded", "Nandurbar", "Nashik",
            "Osmanabad", "Palghar", "Parbhani", "Pune", "Raigad", "Ratnagiri", "Sangli", "Satara",
            "Sindhudurg", "Solapur", "Thane", "Wardha", "Washim", "Yavatmal"
        ],
        "Manipur" => [
            "Bishnupur", "Chandel", "Churachandpur", "Imphal East", "Imphal West", "Jiribam",
            "Kakching", "Kamjong", "Kangpokpi", "Noney", "Pherzawl", "Senapati", "Tamenglong",
            "Tengnoupal", "Thoubal", "Ukhrul"
        ],
        "Meghalaya" => [
            "East Garo Hills", "East Jaintia Hills", "East Khasi Hills", "North Garo Hills",
            "Ri Bhoi", "South Garo Hills", "South West Garo Hills", "South West Khasi Hills",
            "West Garo Hills", "West Jaintia Hills", "West Khasi Hills"
        ],
        "Mizoram" => [
            "Aizawl", "Champhai", "Hnahthial", "Khawzawl", "Kolasib", "Lawngtlai", "Lunglei",
            "Mamit", "Saiha", "Saitual", "Serchhip"
        ],
        "Nagaland" => [
            "Dimapur", "Kiphire", "Kohima", "Longleng", "Mokokchung", "Mon", "Peren", "Phek",
            "Tuensang", "Wokha", "Zunheboto"
        ],
        "Odisha" => [
            "Angul", "Balangir", "Balasore", "Bargarh", "Bhadrak", "Boudh", "Cuttack", "Deogarh",
            "Dhenkanal", "Gajapati", "Ganjam", "Jagatsinghpur", "Jajpur", "Jharsuguda", "Kalahandi",
            "Kandhamal", "Kendrapara", "Kendujhar", "Khordha", "Koraput", "Malkangiri", "Mayurbhanj",
            "Nabarangpur", "Nayagarh", "Nuapada", "Puri", "Rayagada", "Sambalpur", "Subarnapur", "Sundargarh"
        ],
        "Punjab" => [
            "Amritsar", "Barnala", "Bathinda", "Faridkot", "Fatehgarh Sahib", "Fazilka", "Ferozepur",
            "Gurdaspur", "Hoshiarpur", "Jalandhar", "Kapurthala", "Ludhiana", "Mansa", "Moga",
            "Muktsar", "Nawanshahr", "Pathankot", "Patiala", "Rupnagar", "Sangrur", "SAS Nagar",
            "Sri Muktsar Sahib", "Tarn Taran"
        ],
        "Rajasthan" => [
            "Ajmer", "Alwar", "Banswara", "Baran", "Barmer", "Bharatpur", "Bhilwara", "Bikaner",
            "Bundi", "Chittorgarh", "Churu", "Dausa", "Dholpur", "Dungarpur", "Hanumangarh",
            "Jaipur", "Jaisalmer", "Jalore", "Jhalawar", "Jhunjhunu", "Jodhpur", "Karauli", "Kota",
            "Nagaur", "Pali", "Pratapgarh", "Rajsamand", "Sawai Madhopur", "Sikar", "Sirohi",
            "Sri Ganganagar", "Tonk", "Udaipur"
        ],
        "Sikkim" => ["East Sikkim", "North Sikkim", "South Sikkim", "West Sikkim"],
        "Tamil Nadu" => [
            "Ariyalur", "Chengalpattu", "Chennai", "Coimbatore", "Cuddalore", "Dharmapuri",
            "Dindigul", "Erode", "Kallakurichi", "Kanchipuram", "Kanyakumari", "Karur", "Krishnagiri",
            "Madurai", "Mayiladuthurai", "Nagapattinam", "Namakkal", "Nilgiris", "Perambalur",
            "Pudukkottai", "Ramanathapuram", "Ranipet", "Salem", "Sivaganga", "Tenkasi", "Thanjavur",
            "Theni", "Thoothukudi", "Tiruchirappalli", "Tirunelveli", "Tirupathur", "Tiruppur",
            "Tiruvallur", "Tiruvannamalai", "Tiruvarur", "Vellore", "Viluppuram", "Virudhunagar"
        ],
        "Telangana" => [
            "Adilabad", "Bhadradri Kothagudem", "Hanamkonda", "Hyderabad", "Jagtial", "Jangaon",
            "Jayashankar Bhupalpally", "Jogulamba Gadwal", "Kamareddy", "Karimnagar", "Khammam",
            "Komaram Bheem", "Mahabubabad", "Mahabubnagar", "Mancherial", "Medak", "Medchal-Malkajgiri",
            "Mulugu", "Nagarkurnool", "Nalgonda", "Narayanpet", "Nirmal", "Nizamabad", "Peddapalli",
            "Rajanna Sircilla", "Rangareddy", "Sangareddy", "Siddipet", "Suryapet", "Vikarabad",
            "Wanaparthy", "Warangal", "Yadadri Bhuvanagiri"
        ],
        "Tripura" => [
            "Dhalai", "Gomati", "Khowai", "North Tripura", "Sepahijala", "South Tripura",
            "Unakoti", "West Tripura"
        ],
        "Uttar Pradesh" => [
            "Agra", "Aligarh", "Allahabad", "Ambedkar Nagar", "Amethi", "Amroha", "Auraiya",
            "Azamgarh", "Baghpat", "Bahraich", "Ballia", "Balrampur", "Banda", "Barabanki",
            "Bareilly", "Basti", "Bhadohi", "Bijnor", "Budaun", "Bulandshahr", "Chandauli",
            "Chitrakoot", "Deoria", "Etah", "Etawah", "Faizabad", "Farrukhabad", "Fatehpur",
            "Firozabad", "Gautam Buddha Nagar", "Ghaziabad", "Ghazipur", "Gonda", "Gorakhpur",
            "Hamirpur", "Hapur", "Hardoi", "Hathras", "Jalaun", "Jaunpur", "Jhansi", "Kannauj",
            "Kanpur Dehat", "Kanpur Nagar", "Kasganj", "Kaushambi", "Kheri", "Kushinagar",
            "Lalitpur", "Lucknow", "Maharajganj", "Mahoba", "Mainpuri", "Mathura", "Mau",
            "Meerut", "Mirzapur", "Moradabad", "Muzaffarnagar", "Pilibhit", "Pratapgarh",
            "Prayagraj", "Raebareli", "Rampur", "Saharanpur", "Sambhal", "Sant Kabir Nagar",
            "Shahjahanpur", "Shamli", "Shrawasti", "Siddharthnagar", "Sitapur", "Sonbhadra",
            "Sultanpur", "Unnao", "Varanasi"
        ],
        "Uttarakhand" => [
            "Almora", "Bageshwar", "Chamoli", "Champawat", "Dehradun", "Haridwar", "Nainital",
            "Pauri Garhwal", "Pithoragarh", "Rudraprayag", "Tehri Garhwal", "Udham Singh Nagar", "Uttarkashi"
        ],
        "West Bengal" => [
            "Alipurduar", "Bankura", "Birbhum", "Cooch Behar", "Dakshin Dinajpur", "Darjeeling",
            "Hooghly", "Howrah", "Jalpaiguri", "Jhargram", "Kalimpong", "Kolkata", "Malda",
            "Murshidabad", "Nadia", "North 24 Parganas", "Paschim Bardhaman", "Paschim Medinipur",
            "Purba Bardhaman", "Purba Medinipur", "Purulia", "South 24 Parganas", "Uttar Dinajpur"
        ],
        "Jammu&Kashmir" => [
            "Anantnag", "Bandipora", "Baramulla", "Budgam", "Doda", "Ganderbal", "Jammu",
            "Kargil", "Kathua", "Kishtwar", "Kulgam", "Kupwara", "Leh", "Poonch", "Pulwama",
            "Rajouri", "Ramban", "Reasi", "Samba", "Shopian", "Srinagar", "Udhampur"
        ]
    ];
}

function getCitiesMap() {
    return [
        // Andhra Pradesh
        "Anantapur" => ["Anantapur", "Dharmavaram", "Hindupur", "Kadiri", "Penukonda", "Gooty", "Tadipatri", "Rayadurg", "Guntakal", "Kalyandurg", "Uravakonda", "Beluguppa", "Madakasira", "Kambadur", "Singanamala"],
        "Chittoor" => ["Chittoor", "Tirupati", "Puttur", "Madanapalle", "Palamaner", "Chandragiri", "Srikalahasti", "Nagari", "Punganur", "Kuppam", "Bangarupalem", "Pichatur", "Gudipala", "Yerpedu", "Satyavedu"],
        "East Godavari" => ["Rajahmundry", "Kakinada", "Amalapuram", "Tuni", "Peddapuram", "Samalkot", "Ramachandrapuram", "Mandapeta", "Razole", "Rampachodavaram", "Yeleswaram", "Anaparthy", "Gollaprolu", "Pithapuram", "Tallarevu"],
        "Guntur" => ["Guntur", "Tenali", "Narasaraopet", "Chilakaluripet", "Bapatla", "Repalle", "Ponnur", "Mangalagiri", "Tadepalle", "Sattenapalle", "Vinukonda", "Dachepalle", "Piduguralla", "Macherla", "Veldurthy"],
        "Krishna" => ["Vijayawada", "Machilipatnam", "Gudivada", "Nuzvid", "Jaggayyapeta", "Tiruvuru", "Nandigama", "Mylavaram", "Gannavaram", "Ibrahimpatnam", "Kanuru", "Penamaluru", "Avanigadda", "Movva", "Ghantasala"],
        "Kurnool" => ["Kurnool", "Nandyal", "Adoni", "Yemmiganur", "Dhone", "Atmakur", "Banaganapalle", "Koilkuntla", "Allagadda", "Srisailam", "Mantralayam", "Pattikonda", "Kodumur", "Gudur", "Panyam"],
        "Prakasam" => ["Ongole", "Chirala", "Markapur", "Kandukur", "Addanki", "Darsi", "Podili", "Kanigiri", "Giddalur", "Yerragondapalem", "Martur", "Pamur", "Cumbum", "Dornala", "Tarlupadu"],
        "Srikakulam" => ["Srikakulam", "Tekkali", "Palasa", "Amadalavalasa", "Ichchapuram", "Narasannapeta", "Pathapatnam", "Sompeta", "Palakonda", "Rajam", "Gara", "Kotabommali", "Hiramandalam", "Meliaputti", "Vajrapukothuru"],
        "Visakhapatnam" => ["Visakhapatnam", "Anakapalle", "Narsipatnam", "Chodavaram", "Paderu", "Araku Valley", "Chintapalle", "Gajuwaka", "Bheemunipatnam", "Yelamanchili", "Nakkapalle", "Madugula", "Payakaraopet", "Rambilli", "S Rayavaram"],
        "Vizianagaram" => ["Vizianagaram", "Bobbili", "Parvathipuram", "Salur", "Gajapathinagaram", "Cheepurupalli", "Gantyada", "Gurla", "Jami", "Kothavalasa", "Lakkavarapukota", "Nellimarla", "Pusapatirega", "Santhakaviti", "Srungavarapukota"],
        "West Godavari" => ["Eluru", "Bhimavaram", "Tadepalligudem", "Tanuku", "Nidadavole", "Jangareddygudem", "Kovvur", "Narasapur", "Palakol", "Attili", "Akividu", "Dendulur", "Dwaraka Tirumala", "Kaikalur", "Kamavarapukota"],
        "YSR Kadapa" => ["Kadapa", "Proddatur", "Rajampet", "Jammalamadugu", "Pulivendula", "Rayachoti", "Mydukur", "Kamalapuram", "Badvel", "Lakkireddipalle", "Siddavatam", "Chinnamandem", "Galiveedu", "Khajipet", "Vempalle"],
        "Nellore" => ["Nellore", "Gudur", "Kavali", "Sullurpeta", "Atmakur", "Venkatagiri", "Udayagiri", "Rapur", "Podalakur", "Tada", "Dakkili", "Kodavalur", "Muthukur", "Sangam", "Varikuntapadu"],
        "Sri Potti Sriramulu Nellore" => ["Nellore", "Gudur", "Kavali", "Sullurpeta", "Atmakur", "Venkatagiri", "Udayagiri", "Rapur", "Podalakur", "Tada", "Dakkili", "Kodavalur", "Muthukur", "Sangam", "Varikuntapadu"],
        
        // Arunachal Pradesh - Key districts
        "Tawang" => ["Tawang", "Lumla", "Jang", "Mukto", "Bongkhar", "Kitpi", "Zemithang", "Mago", "Thingbu", "Mechuka", "Dirang", "Bomdila", "Kalaktang", "Rupa", "Shergaon"],
        "West Kameng" => ["Bomdila", "Dirang", "Kalaktang", "Rupa", "Shergaon", "Thongri", "Thembang", "Jamiri", "Singchung", "Nafra", "Balemu", "Bhalukpong", "Seijosa", "Pakke Kessang", "Lekang"],
        "Papum Pare" => ["Itanagar", "Naharlagun", "Doimukh", "Yupia", "Kimin", "Sagalee", "Ziro", "Daporijo", "Raga", "Palin", "Koloriang", "Nyapin", "Sangram", "Kamporijo", "Tali"],
        
        // Assam - Key districts
        "Kamrup Metropolitan" => ["Guwahati", "Dispur", "Azara", "Sonapur", "Chandrapur", "Beltola", "Bamunimaidan", "Jalukbari", "Pandu", "Maligaon", "Paltan Bazaar", "Fancy Bazaar", "Uzan Bazaar", "Lachit Nagar", "Bharalumukh"],
        "Dibrugarh" => ["Dibrugarh", "Namrup", "Duliajan", "Naharkatiya", "Moran", "Sonari", "Chabua", "Tengakhat", "Lahoal", "Tingkhong", "Sapekhati", "Khowang", "Barbaruah", "Panitola", "Tinsukia"],
        "Jorhat" => ["Jorhat", "Titabar", "Mariani", "Teok", "Titabar", "Mariani", "Teok", "Jorhat", "Titabar", "Mariani", "Teok", "Titabar", "Mariani", "Teok", "Jorhat"],
        
        // Bihar - Key districts
        "Patna" => ["Patna", "Danapur", "Phulwari Sharif", "Bihta", "Fatuha", "Maner", "Masaurhi", "Naubatpur", "Paliganj", "Bikram", "Dulhin Bazar", "Hilsa", "Jehanabad", "Biharsharif", "Nalanda"],
        "Gaya" => ["Gaya", "Bodh Gaya", "Tekari", "Guraru", "Belaganj", "Wazirganj", "Fatehpur", "Sherghati", "Imamganj", "Dumaria", "Banke Bazar", "Mohanpur", "Konch", "Tikari", "Barachatti"],
        "Muzaffarpur" => ["Muzaffarpur", "Motihari", "Bettiah", "Bagaha", "Narkatiaganj", "Raxaul", "Sugauli", "Chakia", "Dhaka", "Pipra", "Sitamarhi", "Belsand", "Dumra", "Bairgania", "Majorganj"],
        
        // Chhattisgarh - Key districts
        "Raipur" => ["Raipur", "Bhatapara", "Baloda Bazar", "Tilda", "Abhanpur", "Arang", "Mandir Hasod", "Pallari", "Simga", "Bilaigarh", "Kasdol", "Bindranawagarh", "Chhura", "Gariaband", "Deobhog"],
        "Bilaspur" => ["Bilaspur", "Korba", "Champa", "Janjgir", "Akaltara", "Mungeli", "Takhatpur", "Lormi", "Pendra", "Marwahi", "Gaurela", "Pandariya", "Kota", "Masturi", "Bilha"],
        
        // Goa
        "North Goa" => ["Panaji", "Mapusa", "Ponda", "Margao", "Vasco da Gama", "Calangute", "Baga", "Anjuna", "Candolim", "Sinquerim", "Arambol", "Morjim", "Ashwem", "Mandrem", "Siolim"],
        "South Goa" => ["Margao", "Vasco da Gama", "Ponda", "Canacona", "Quepem", "Sanguem", "Curchorem", "Sanvordem", "Chaudi", "Agonda", "Palolem", "Colva", "Benaulim", "Varca", "Cavelossim"],
        
        // Gujarat - Key districts
        "Ahmedabad" => ["Ahmedabad", "Gandhinagar", "Sanand", "Dholka", "Viramgam", "Bavla", "Daskroi", "Detroj", "Dhandhuka", "Mandal", "Ranpur", "Dholera", "Bopal", "Sarkhej", "Naroda"],
        "Surat" => ["Surat", "Navsari", "Bardoli", "Vyara", "Songadh", "Uchhal", "Nizar", "Valod", "Mahuva", "Palsana", "Kamrej", "Olpad", "Choryasi", "Mangrol", "Umarpada"],
        "Vadodara" => ["Vadodara", "Anand", "Bharuch", "Nadiad", "Petlad", "Borsad", "Sojitra", "Umreth", "Khambhat", "Tarapur", "Kathlal", "Kapadvanj", "Balasinor", "Lunawada", "Santrampur"],
        
        // Haryana - Key districts
        "Gurugram" => ["Gurugram", "Faridabad", "Sohna", "Pataudi", "Manesar", "Dharuhera", "Rewari", "Bawal", "Nuh", "Ferozepur Jhirka", "Taoru", "Nagina", "Punahana", "Hathin", "Nuh"],
        "Faridabad" => ["Faridabad", "Ballabgarh", "Palwal", "Hathin", "Hodal", "Hassanpur", "Kosi", "Mathura Road", "Sector 21", "Sector 28", "Sector 29", "Sector 30", "Sector 31", "Sector 32", "Sector 33"],
        "Panipat" => ["Panipat", "Samalkha", "Israna", "Bapoli", "Madlauda", "Assandh", "Gharaunda", "Karnal", "Indri", "Nissing", "Nilokheri", "Taraori", "Kunjpura", "Gharaunda", "Assandh"],
        
        // Himachal Pradesh - Key districts
        "Shimla" => ["Shimla", "Kufri", "Mashobra", "Naldehra", "Chail", "Kasauli", "Solan", "Barog", "Kandaghat", "Chambaghat", "Arki", "Nalagarh", "Baddi", "Parwanoo", "Kasauli"],
        "Kullu" => ["Kullu", "Manali", "Naggar", "Banjar", "Anni", "Nirmand", "Sainj", "Larji", "Aut", "Bhuntar", "Katrain", "Raison", "Jagatsukh", "Manikaran", "Kasol"],
        "Kangra" => ["Dharamshala", "Kangra", "Palampur", "McLeod Ganj", "Baijnath", "Jawalamukhi", "Nurpur", "Dehra", "Shahpur", "Nagrota", "Yol", "Gaggal", "Dharamshala", "Kangra", "Palampur"],
        
        // Jharkhand - Key districts
        "Ranchi" => ["Ranchi", "Hatia", "Namkum", "Kanke", "Ormanjhi", "Ratu", "Mandar", "Burmu", "Angara", "Silli", "Lapung", "Tamar", "Karra", "Torpa", "Khunti"],
        "Jamshedpur" => ["Jamshedpur", "Adityapur", "Gamharia", "Ghatshila", "Mango", "Kadma", "Sonari", "Bistupur", "Sakchi", "Telco", "Golmuri", "Dimna", "Baridih", "Bagbera", "Potka"],
        "Dhanbad" => ["Dhanbad", "Jharia", "Sindri", "Katras", "Chirkunda", "Gobindpur", "Nirsa", "Topchanchi", "Tundi", "Baghmara", "Purbi Tundi", "Baliapur", "Nawadih", "Kenduadih", "Lodna"],
        
        // Karnataka - Key districts
        "Bengaluru Urban" => ["Bangalore", "Yelahanka", "Kengeri", "Whitefield", "Electronic City", "Marathahalli", "HSR Layout", "Koramangala", "Indiranagar", "Jayanagar", "Basavanagudi", "Malleshwaram", "Rajajinagar", "Vijayanagar", "Yeshwanthpur"],
        "Mysuru" => ["Mysore", "Nanjangud", "Hunsur", "Krishnarajanagara", "Piriyapatna", "T Narasipura", "Heggadadevankote", "Periyapatna", "Krishnarajanagara", "Piriyapatna", "T Narasipura", "Heggadadevankote", "Periyapatna", "Mysore", "Nanjangud"],
        "Hubli-Dharwad" => ["Hubli", "Dharwad", "Navalgund", "Kalghatgi", "Kundgol", "Annigeri", "Hirekerur", "Byadgi", "Hangal", "Shiggaon", "Savadatti", "Ramdurg", "Gokak", "Belagavi", "Khanapur"],
        
        // Kerala - Key districts
        "Thiruvananthapuram" => ["Trivandrum", "Neyyattinkara", "Attingal", "Nedumangad", "Varkala", "Kovalam", "Ponmudi", "Kattakada", "Parassala", "Vellanad", "Kallara", "Kalliyoor", "Kazhakootam", "Kovalam", "Varkala"],
        "Kochi" => ["Kochi", "Ernakulam", "Aluva", "Kalamassery", "Edappally", "Kakkanad", "Maradu", "Thripunithura", "Perumbavoor", "Muvattupuzha", "Kothamangalam", "Angamaly", "Paravur", "North Paravur", "Mattancherry"],
        "Kozhikode" => ["Calicut", "Kozhikode", "Feroke", "Ramanattukara", "Koyilandy", "Vadakara", "Thamarassery", "Mukkam", "Balussery", "Koduvally", "Perambra", "Olavanna", "Kunnamangalam", "Elathur", "Beypore"],
        
        // Madhya Pradesh - Key districts
        "Bhopal" => ["Bhopal", "Huzur", "Berasia", "Phanda", "Gandhinagar", "Kolar", "Bairagarh", "Sehore", "Ashta", "Ichhawar", "Nasrullaganj", "Budhni", "Rehti", "Shyampur", "Raisen"],
        "Indore" => ["Indore", "Mhow", "Depalpur", "Sanwer", "Hatod", "Betma", "Rau", "Pithampur", "Dewas", "Kannod", "Bagli", "Khategaon", "Pandhana", "Khandwa", "Burhanpur"],
        "Gwalior" => ["Gwalior", "Dabra", "Bhitarwar", "Ghatigaon", "Mehgaon", "Morena", "Joura", "Sabalgarh", "Ambah", "Porsa", "Kailaras", "Sheopur", "Vijaypur", "Karera", "Narwar"],
        
        // Maharashtra - Key districts
        "Mumbai" => ["Mumbai", "Andheri", "Bandra", "Borivali", "Chembur", "Dadar", "Goregaon", "Juhu", "Kandivali", "Kurla", "Malad", "Mulund", "Powai", "Santacruz", "Vashi"],
        "Pune" => ["Pune", "Pimpri-Chinchwad", "Hinjewadi", "Hadapsar", "Kothrud", "Baner", "Aundh", "Wakad", "Viman Nagar", "Kharadi", "Kondhwa", "Katraj", "Bhosari", "Chakan", "Talegaon"],
        "Nagpur" => ["Nagpur", "Kamptee", "Wardha", "Hinganghat", "Umred", "Katol", "Ramtek", "Mouda", "Parseoni", "Bhiwapur", "Kuhi", "Saoner", "Kalmeshwar", "Narkhed", "Khapa"],
        
        // Manipur - Key districts
        "Imphal West" => ["Imphal", "Lamphelpat", "Langjing", "Sagolband", "Uripok", "Kwakeithel", "Thangmeiband", "Paona Bazar", "Thangal Bazar", "Khongman", "Arambai", "Kongba", "Konthoujam", "Lilong", "Wangoi"],
        "Imphal East" => ["Porompat", "Heingang", "Khongampat", "Konthoujam", "Sawombung", "Keirao", "Andro", "Kakching", "Kakching Khunou", "Sugnu", "Thoubal", "Wangjing", "Kakching", "Kakching Khunou", "Sugnu"],
        
        // Meghalaya - Key districts
        "East Khasi Hills" => ["Shillong", "Mawphlang", "Mawryngkneng", "Pynursla", "Mawsynram", "Sohra", "Laitlyngkot", "Mawsmai", "Mawlynnong", "Cherrapunji", "Nongpoh", "Umroi", "Umling", "Mawphlang", "Mawryngkneng"],
        "West Khasi Hills" => ["Nongstoin", "Mawkyrwat", "Mawthadraishan", "Mawshynrut", "Mawphlang", "Mawryngkneng", "Pynursla", "Mawsynram", "Sohra", "Laitlyngkot", "Mawsmai", "Mawlynnong", "Cherrapunji", "Nongstoin", "Mawkyrwat"],
        "East Garo Hills" => ["Williamnagar", "Rongjeng", "Dambo Rongjeng", "Songsak", "Resubelpara", "Baghmara", "Rongara", "Chokpot", "Gasuapara", "Dalu", "Tura", "Ampati", "Rongram", "Tikrikilla", "Phulbari"],
        "West Garo Hills" => ["Tura", "Ampati", "Rongram", "Tikrikilla", "Phulbari", "Dalu", "Gasuapara", "Baghmara", "Rongara", "Chokpot", "Williamnagar", "Rongjeng", "Dambo Rongjeng", "Songsak", "Resubelpara"],
        "South Garo Hills" => ["Baghmara", "Rongara", "Chokpot", "Gasuapara", "Dalu", "Tura", "Ampati", "Rongram", "Tikrikilla", "Phulbari", "Williamnagar", "Rongjeng", "Dambo Rongjeng", "Songsak", "Resubelpara"],
        "North Garo Hills" => ["Resubelpara", "Williamnagar", "Rongjeng", "Dambo Rongjeng", "Songsak", "Resubelpara", "Baghmara", "Rongara", "Chokpot", "Gasuapara", "Dalu", "Tura", "Ampati", "Rongram", "Tikrikilla"],
        "South West Garo Hills" => ["Ampati", "Rongram", "Tikrikilla", "Phulbari", "Dalu", "Gasuapara", "Baghmara", "Rongara", "Chokpot", "Tura", "Ampati", "Rongram", "Tikrikilla", "Phulbari", "Dalu"],
        "East Jaintia Hills" => ["Khliehriat", "Saipung", "Lasan", "Thadlaskein", "Mukroh", "Rymbai", "Bataw", "Lumshnong", "Jowai", "Amlarem", "Mukroh", "Rymbai", "Bataw", "Lumshnong", "Jowai"],
        "West Jaintia Hills" => ["Jowai", "Amlarem", "Mukroh", "Rymbai", "Bataw", "Lumshnong", "Khliehriat", "Saipung", "Lasan", "Thadlaskein", "Mukroh", "Rymbai", "Bataw", "Lumshnong", "Jowai"],
        "Ri Bhoi" => ["Nongpoh", "Umroi", "Umling", "Mawphlang", "Mawryngkneng", "Pynursla", "Mawsynram", "Sohra", "Laitlyngkot", "Mawsmai", "Mawlynnong", "Cherrapunji", "Shillong", "Mawphlang", "Mawryngkneng"],
        "South West Khasi Hills" => ["Mawkyrwat", "Mawthadraishan", "Mawshynrut", "Mawphlang", "Mawryngkneng", "Pynursla", "Mawsynram", "Sohra", "Laitlyngkot", "Mawsmai", "Mawlynnong", "Cherrapunji", "Nongstoin", "Mawkyrwat", "Mawthadraishan"],
        
        // Mizoram - Key districts
        "Aizawl" => ["Aizawl", "Durtlang", "Sairang", "Lengpui", "Bairabi", "Kolasib", "Vairengte", "Kawnpui", "Thenzawl", "Serchhip", "Lunglei", "Hnahthial", "Champhai", "Khawzawl", "Saitual"],
        "Lunglei" => ["Lunglei", "Hnahthial", "Champhai", "Khawzawl", "Saitual", "Thenzawl", "Serchhip", "Aizawl", "Durtlang", "Sairang", "Lengpui", "Bairabi", "Kolasib", "Vairengte", "Kawnpui"],
        "Champhai" => ["Champhai", "Khawzawl", "Saitual", "Thenzawl", "Serchhip", "Aizawl", "Durtlang", "Sairang", "Lengpui", "Bairabi", "Kolasib", "Vairengte", "Kawnpui", "Lunglei", "Hnahthial"],
        "Kolasib" => ["Kolasib", "Vairengte", "Kawnpui", "Thenzawl", "Serchhip", "Aizawl", "Durtlang", "Sairang", "Lengpui", "Bairabi", "Kolasib", "Vairengte", "Kawnpui", "Lunglei", "Hnahthial"],
        "Serchhip" => ["Serchhip", "Thenzawl", "Aizawl", "Durtlang", "Sairang", "Lengpui", "Bairabi", "Kolasib", "Vairengte", "Kawnpui", "Lunglei", "Hnahthial", "Champhai", "Khawzawl", "Saitual"],
        "Lawngtlai" => ["Lawngtlai", "Saiha", "Tuipang", "Sangau", "Lunglei", "Hnahthial", "Champhai", "Khawzawl", "Saitual", "Thenzawl", "Serchhip", "Aizawl", "Durtlang", "Sairang", "Lengpui"],
        "Mamit" => ["Mamit", "Reiek", "Dampa", "Khawzawl", "Saitual", "Thenzawl", "Serchhip", "Aizawl", "Durtlang", "Sairang", "Lengpui", "Bairabi", "Kolasib", "Vairengte", "Kawnpui"],
        "Saiha" => ["Saiha", "Tuipang", "Sangau", "Lawngtlai", "Lunglei", "Hnahthial", "Champhai", "Khawzawl", "Saitual", "Thenzawl", "Serchhip", "Aizawl", "Durtlang", "Sairang", "Lengpui"],
        "Hnahthial" => ["Hnahthial", "Lunglei", "Champhai", "Khawzawl", "Saitual", "Thenzawl", "Serchhip", "Aizawl", "Durtlang", "Sairang", "Lengpui", "Bairabi", "Kolasib", "Vairengte", "Kawnpui"],
        "Khawzawl" => ["Khawzawl", "Saitual", "Thenzawl", "Serchhip", "Aizawl", "Durtlang", "Sairang", "Lengpui", "Bairabi", "Kolasib", "Vairengte", "Kawnpui", "Lunglei", "Hnahthial", "Champhai"],
        "Saitual" => ["Saitual", "Thenzawl", "Serchhip", "Aizawl", "Durtlang", "Sairang", "Lengpui", "Bairabi", "Kolasib", "Vairengte", "Kawnpui", "Lunglei", "Hnahthial", "Champhai", "Khawzawl"],
        
        // Nagaland - Key districts
        "Kohima" => ["Kohima", "Jakhama", "Kigwema", "Mao", "Viswema", "Chiephobozou", "Kezocha", "Medziphema", "Dimapur", "Chumukedima", "Sechu", "Zubza", "Phesama", "Khonoma", "Mima"],
        "Dimapur" => ["Dimapur", "Chumukedima", "Sechu", "Zubza", "Phesama", "Khonoma", "Mima", "Kohima", "Jakhama", "Kigwema", "Mao", "Viswema", "Chiephobozou", "Kezocha", "Medziphema"],
        "Mokokchung" => ["Mokokchung", "Tuli", "Changtongya", "Longkhim", "Kubolong", "Chuchuyimlang", "Longchem", "Alongkima", "Mangkolemba", "Alem", "Longsa", "Chare", "Longleng", "Yongnyah", "Phomching"],
        "Tuensang" => ["Tuensang", "Noklak", "Noksen", "Longkhim", "Kubolong", "Chuchuyimlang", "Longchem", "Alongkima", "Mangkolemba", "Alem", "Longsa", "Chare", "Kiphire", "Pungro", "Seyochung"],
        "Wokha" => ["Wokha", "Bhandari", "Sanis", "Lotsu", "Ralan", "Englan", "Changpang", "Aitepyong", "Lakhuti", "Sungro", "Merapani", "Niuland", "Chumukedima", "Sechu", "Zubza"],
        "Zunheboto" => ["Zunheboto", "Akuluto", "Aghunato", "Satoi", "Suruhuto", "Sathazumi", "Ghathashi", "Sukhalu", "Satoi", "Suruhuto", "Sathazumi", "Ghathashi", "Sukhalu", "Akuluto", "Aghunato"],
        "Mon" => ["Mon", "Tizit", "Naginimora", "Aboi", "Longshen", "Phomching", "Yongnyah", "Longleng", "Chare", "Longsa", "Alem", "Mangkolemba", "Alongkima", "Longchem", "Chuchuyimlang"],
        "Phek" => ["Phek", "Pfutsero", "Chizami", "Khezhakeno", "Meluri", "Sekruzu", "Khuza", "Sakraba", "Kiphire", "Pungro", "Seyochung", "Tuensang", "Noklak", "Noksen", "Longkhim"],
        "Kiphire" => ["Kiphire", "Pungro", "Seyochung", "Tuensang", "Noklak", "Noksen", "Longkhim", "Kubolong", "Chuchuyimlang", "Longchem", "Alongkima", "Mangkolemba", "Alem", "Longsa", "Chare"],
        "Longleng" => ["Longleng", "Yongnyah", "Phomching", "Mon", "Tizit", "Naginimora", "Aboi", "Longshen", "Phomching", "Yongnyah", "Longleng", "Chare", "Longsa", "Alem", "Mangkolemba"],
        "Peren" => ["Peren", "Jalukie", "Tening", "Athibung", "Dimapur", "Chumukedima", "Sechu", "Zubza", "Phesama", "Khonoma", "Mima", "Kohima", "Jakhama", "Kigwema", "Mao"],
        
        // Odisha - Key districts
        "Khordha" => ["Bhubaneswar", "Cuttack", "Puri", "Konark", "Pipili", "Nimapara", "Kakatpur", "Brahmagiri", "Gop", "Satyabadi", "Delang", "Kanpur", "Nayagarh", "Odagaon", "Ranpur"],
        "Cuttack" => ["Cuttack", "Choudwar", "Athagarh", "Barang", "Banki", "Niali", "Tangi", "Salepur", "Kendrapada", "Jagatsinghpur", "Paradip", "Ersama", "Balikuda", "Tirtol", "Jagatsinghpur"],
        "Puri" => ["Puri", "Konark", "Pipili", "Nimapara", "Kakatpur", "Brahmagiri", "Gop", "Satyabadi", "Delang", "Kanpur", "Nayagarh", "Odagaon", "Ranpur", "Bhubaneswar", "Cuttack"],
        "Sambalpur" => ["Sambalpur", "Hirakud", "Jharsuguda", "Brajarajnagar", "Belpahar", "Lakhanpur", "Rengali", "Debagarh", "Barkot", "Kuchinda", "Rairakhol", "Bamra", "Sohela", "Titlagarh", "Balangir"],
        "Sundargarh" => ["Rourkela", "Sundargarh", "Rajgangpur", "Birmitrapur", "Lathikata", "Koida", "Bonaigarh", "Subdega", "Hemgir", "Lephripara", "Talsara", "Bisra", "Nuagaon", "Gurundia", "Hatibari"],
        "Ganjam" => ["Berhampur", "Gopalpur", "Chhatrapur", "Hinjilicut", "Kabisuryanagar", "Khalikote", "Aska", "Bhanjanagar", "Buguda", "Polasara", "Kodala", "Jarada", "Ganjam", "Chikiti", "Digapahandi"],
        "Balasore" => ["Balasore", "Bhadrak", "Jaleswar", "Soro", "Simulia", "Basta", "Remuna", "Nilagiri", "Oupada", "Khunta", "Baleshwar", "Bahanaga", "Bhograi", "Baliapal", "Betnoti"],
        "Bhadrak" => ["Bhadrak", "Dhamnagar", "Chandabali", "Basudebpur", "Tihidi", "Bhandaripokhari", "Bansada", "Agarpada", "Dhamara", "Tihidi", "Basudebpur", "Chandabali", "Dhamnagar", "Bhadrak", "Bhandaripokhari"],
        "Jharsuguda" => ["Jharsuguda", "Brajarajnagar", "Belpahar", "Lakhanpur", "Rengali", "Debagarh", "Barkot", "Kuchinda", "Rairakhol", "Bamra", "Sohela", "Titlagarh", "Balangir", "Sambalpur", "Hirakud"],
        "Kendujhar" => ["Kendujhar", "Barbil", "Joda", "Anandapur", "Ghasipura", "Harichandanpur", "Hatadihi", "Jhumpura", "Patna", "Telkoi", "Banspal", "Ghatgaon", "Kendujhar", "Barbil", "Joda"],
        "Mayurbhanj" => ["Baripada", "Rairangpur", "Karanjia", "Udala", "Betnoti", "Badasahi", "Bangriposi", "Jashipur", "Kuliana", "Morada", "Suliapada", "Thakurmunda", "Tiring", "Bahalda", "Bisoi"],
        "Koraput" => ["Koraput", "Jeypore", "Sunabeda", "Kotpad", "Borigumma", "Laxmipur", "Nandapur", "Pottangi", "Semiliguda", "Dasmantpur", "Narayanpatna", "Bandhugaon", "Machkund", "Pottangi", "Semiliguda"],
        "Rayagada" => ["Rayagada", "Gunupur", "Bissam Cuttack", "Muniguda", "Kalyansingpur", "Padmapur", "Kashipur", "Thelkoloi", "Gudari", "Kolnara", "Chandrapur", "Kotpad", "Borigumma", "Laxmipur", "Nandapur"],
        "Nabarangpur" => ["Nabarangpur", "Umerkote", "Papadahandi", "Kosagumda", "Raighar", "Tentulikhunti", "Dabugan", "Chhatriguda", "Kodinga", "Jharigam", "Khatiguda", "Kotpad", "Borigumma", "Laxmipur", "Nandapur"],
        "Malkangiri" => ["Malkangiri", "Motu", "Kalimela", "Korukonda", "Mathili", "Kudumulgumma", "Podia", "Chitrakonda", "Orkel", "Papulur", "Jodambo", "Koraput", "Jeypore", "Sunabeda", "Kotpad"],
        "Kalahandi" => ["Bhawanipatna", "Dharamgarh", "Kesinga", "Junagarh", "Golamunda", "Narla", "Karlamunda", "M. Rampur", "Lanjigarh", "Thuamul Rampur", "Madanpur Rampur", "Sambalpur", "Hirakud", "Jharsuguda", "Brajarajnagar"],
        "Balangir" => ["Balangir", "Titlagarh", "Patnagarh", "Kantabanji", "Loisingha", "Turekela", "Saintala", "Tusura", "Deogaon", "Belpada", "Muribahal", "Puintala", "Bangomunda", "Sohela", "Titlagarh"],
        "Nuapada" => ["Nuapada", "Komna", "Khariar", "Sinapali", "Boden", "Tarbha", "Khariar Road", "Sinapali", "Boden", "Tarbha", "Khariar Road", "Nuapada", "Komna", "Khariar", "Sinapali"],
        "Bargarh" => ["Bargarh", "Attabira", "Bheden", "Bijepur", "Barapali", "Sohela", "Titlagarh", "Balangir", "Patnagarh", "Kantabanji", "Loisingha", "Turekela", "Saintala", "Tusura", "Deogaon"],
        "Jagatsinghpur" => ["Jagatsinghpur", "Paradip", "Ersama", "Balikuda", "Tirtol", "Jagatsinghpur", "Kendrapada", "Jajpur", "Dharmasala", "Bari", "Binjharpur", "Dasarathpur", "Jajpur", "Dharmasala", "Bari"],
        "Jajpur" => ["Jajpur", "Dharmasala", "Bari", "Binjharpur", "Dasarathpur", "Jajpur", "Dharmasala", "Bari", "Binjharpur", "Dasarathpur", "Jagatsinghpur", "Paradip", "Ersama", "Balikuda", "Tirtol"],
        "Kendrapara" => ["Kendrapara", "Pattamundai", "Aul", "Rajkanika", "Garadpur", "Derabish", "Marsaghai", "Mahakalapada", "Rajnagar", "Jagatsinghpur", "Paradip", "Ersama", "Balikuda", "Tirtol", "Jagatsinghpur"],
        "Dhenkanal" => ["Dhenkanal", "Kamakhyanagar", "Gondia", "Parjang", "Bhuban", "Hindol", "Odapada", "Talcher", "Angul", "Athmallik", "Pallahara", "Banarpal", "Chhendipada", "Khamar", "Kaniha"],
        "Angul" => ["Angul", "Talcher", "Athmallik", "Pallahara", "Banarpal", "Chhendipada", "Khamar", "Kaniha", "Dhenkanal", "Kamakhyanagar", "Gondia", "Parjang", "Bhuban", "Hindol", "Odapada"],
        "Nayagarh" => ["Nayagarh", "Odagaon", "Ranpur", "Bhubaneswar", "Cuttack", "Puri", "Konark", "Pipili", "Nimapara", "Kakatpur", "Brahmagiri", "Gop", "Satyabadi", "Delang", "Kanpur"],
        "Gajapati" => ["Paralakhemundi", "Gumma", "Mohana", "R. Udayagiri", "Rayagada", "Gunupur", "Bissam Cuttack", "Muniguda", "Kalyansingpur", "Padmapur", "Kashipur", "Thelkoloi", "Gudari", "Kolnara", "Chandrapur"],
        "Kandhamal" => ["Phulbani", "G. Udayagiri", "Tumudibandha", "Raikia", "Daringbadi", "Kotagarh", "Baliguda", "Tikabali", "Nuagaon", "Gurundia", "Hatibari", "Rourkela", "Sundargarh", "Rajgangpur", "Birmitrapur"],
        "Boudh" => ["Boudh", "Kantamal", "Manamunda", "Harabhanga", "Boudh", "Kantamal", "Manamunda", "Harabhanga", "Phulbani", "G. Udayagiri", "Tumudibandha", "Raikia", "Daringbadi", "Kotagarh", "Baliguda"],
        "Deogarh" => ["Deogarh", "Barkot", "Kuchinda", "Rairakhol", "Bamra", "Sohela", "Titlagarh", "Balangir", "Patnagarh", "Kantabanji", "Loisingha", "Turekela", "Saintala", "Tusura", "Deogaon"],
        "Subarnapur" => ["Sonepur", "Birmaharajpur", "Ulunda", "Binika", "Tarbha", "Khariar Road", "Nuapada", "Komna", "Khariar", "Sinapali", "Boden", "Tarbha", "Khariar Road", "Nuapada", "Komna"],
        
        // Sikkim - Key districts
        "East Sikkim" => ["Gangtok", "Rangpo", "Singtam", "Rhenock", "Rongli", "Pakyong", "Dikchu", "Mangan", "Chungthang", "Lachen", "Lachung", "Yumthang", "Namchi", "Jorethang", "Melli"],
        "West Sikkim" => ["Geyzing", "Pelling", "Yuksom", "Tashiding", "Rinchenpong", "Soreng", "Kaluk", "Ravangla", "Namchi", "Jorethang", "Melli", "Gangtok", "Rangpo", "Singtam", "Rhenock"],
        "South Sikkim" => ["Namchi", "Jorethang", "Melli", "Ravangla", "Temi", "Mamley", "Rangpo", "Singtam", "Rhenock", "Rongli", "Pakyong", "Dikchu", "Gangtok", "Rangpo", "Singtam"],
        "North Sikkim" => ["Mangan", "Chungthang", "Lachen", "Lachung", "Yumthang", "Dikchu", "Gangtok", "Rangpo", "Singtam", "Rhenock", "Rongli", "Pakyong", "Mangan", "Chungthang", "Lachen"],
        
        // Tripura - Key districts
        "West Tripura" => ["Agartala", "Udaipur", "Amarpur", "Khowai", "Teliamura", "Ambassa", "Dharmanagar", "Kailasahar", "Belonia", "Sabroom", "Sonamura", "Melaghar", "Bishalgarh", "Jirania", "Mohanpur"],
        "South Tripura" => ["Belonia", "Sabroom", "Sonamura", "Melaghar", "Bishalgarh", "Jirania", "Mohanpur", "Agartala", "Udaipur", "Amarpur", "Khowai", "Teliamura", "Ambassa", "Dharmanagar", "Kailasahar"],
        "North Tripura" => ["Dharmanagar", "Kailasahar", "Belonia", "Sabroom", "Sonamura", "Melaghar", "Bishalgarh", "Jirania", "Mohanpur", "Agartala", "Udaipur", "Amarpur", "Khowai", "Teliamura", "Ambassa"],
        "Dhalai" => ["Ambassa", "Dharmanagar", "Kailasahar", "Belonia", "Sabroom", "Sonamura", "Melaghar", "Bishalgarh", "Jirania", "Mohanpur", "Agartala", "Udaipur", "Amarpur", "Khowai", "Teliamura"],
        "Khowai" => ["Khowai", "Teliamura", "Ambassa", "Dharmanagar", "Kailasahar", "Belonia", "Sabroom", "Sonamura", "Melaghar", "Bishalgarh", "Jirania", "Mohanpur", "Agartala", "Udaipur", "Amarpur"],
        "Sepahijala" => ["Bishalgarh", "Jirania", "Mohanpur", "Agartala", "Udaipur", "Amarpur", "Khowai", "Teliamura", "Ambassa", "Dharmanagar", "Kailasahar", "Belonia", "Sabroom", "Sonamura", "Melaghar"],
        "Gomati" => ["Udaipur", "Amarpur", "Khowai", "Teliamura", "Ambassa", "Dharmanagar", "Kailasahar", "Belonia", "Sabroom", "Sonamura", "Melaghar", "Bishalgarh", "Jirania", "Mohanpur", "Agartala"],
        "Unakoti" => ["Kailasahar", "Belonia", "Sabroom", "Sonamura", "Melaghar", "Bishalgarh", "Jirania", "Mohanpur", "Agartala", "Udaipur", "Amarpur", "Khowai", "Teliamura", "Ambassa", "Dharmanagar"],
        
        // Uttarakhand - Key districts
        "Dehradun" => ["Dehradun", "Mussoorie", "Landour", "Chakrata", "Vikasnagar", "Doiwala", "Sahaspur", "Rishikesh", "Haridwar", "Roorkee", "Laksar", "Jhabrera", "Bhagwanpur", "Dhanpuri", "Kotdwar"],
        "Haridwar" => ["Haridwar", "Rishikesh", "Roorkee", "Laksar", "Jhabrera", "Bhagwanpur", "Dhanpuri", "Kotdwar", "Dehradun", "Mussoorie", "Landour", "Chakrata", "Vikasnagar", "Doiwala", "Sahaspur"],
        "Nainital" => ["Nainital", "Haldwani", "Ramnagar", "Kaladhungi", "Bhimtal", "Sattal", "Mukteshwar", "Ranikhet", "Almora", "Bageshwar", "Kausani", "Baijnath", "Gwaldam", "Champawat", "Lohaghat"],
        "Almora" => ["Almora", "Ranikhet", "Kausani", "Baijnath", "Gwaldam", "Champawat", "Lohaghat", "Bageshwar", "Kapkot", "Garur", "Bhatrojkhan", "Dwarahat", "Someshwar", "Takula", "Jageshwar"],
        "Pauri Garhwal" => ["Pauri", "Kotdwar", "Lansdowne", "Srinagar", "Devprayag", "Rudraprayag", "Kedarnath", "Guptkashi", "Ukhimath", "Gopeshwar", "Chamoli", "Joshimath", "Auli", "Badrinath", "Mana"],
        "Tehri Garhwal" => ["Tehri", "New Tehri", "Chamba", "Ghansali", "Devprayag", "Rishikesh", "Haridwar", "Roorkee", "Laksar", "Jhabrera", "Bhagwanpur", "Dhanpuri", "Kotdwar", "Dehradun", "Mussoorie"],
        "Udham Singh Nagar" => ["Rudrapur", "Kashipur", "Kichha", "Sitarganj", "Jaspur", "Bazpur", "Gadarpur", "Pantnagar", "Haldwani", "Ramnagar", "Kaladhungi", "Bhimtal", "Sattal", "Mukteshwar", "Ranikhet"],
        "Chamoli" => ["Gopeshwar", "Joshimath", "Auli", "Badrinath", "Mana", "Kedarnath", "Guptkashi", "Ukhimath", "Pauri", "Kotdwar", "Lansdowne", "Srinagar", "Devprayag", "Rudraprayag", "Kedarnath"],
        "Pithoragarh" => ["Pithoragarh", "Dharchula", "Munsiyari", "Thal", "Berinag", "Gangolihat", "Kapkot", "Bageshwar", "Almora", "Ranikhet", "Kausani", "Baijnath", "Gwaldam", "Champawat", "Lohaghat"],
        "Bageshwar" => ["Bageshwar", "Kapkot", "Garur", "Bhatrojkhan", "Dwarahat", "Someshwar", "Takula", "Jageshwar", "Almora", "Ranikhet", "Kausani", "Baijnath", "Gwaldam", "Champawat", "Lohaghat"],
        "Champawat" => ["Champawat", "Lohaghat", "Tanakpur", "Purnagiri", "Banbasa", "Khatima", "Sitarganj", "Jaspur", "Bazpur", "Gadarpur", "Rudrapur", "Kashipur", "Kichha", "Sitarganj", "Jaspur"],
        "Rudraprayag" => ["Rudraprayag", "Kedarnath", "Guptkashi", "Ukhimath", "Gopeshwar", "Joshimath", "Auli", "Badrinath", "Mana", "Pauri", "Kotdwar", "Lansdowne", "Srinagar", "Devprayag", "Rudraprayag"],
        "Uttarkashi" => ["Uttarkashi", "Barkot", "Yamunotri", "Gangotri", "Harsil", "Dharasu", "Chinyalisaur", "Purola", "Mori", "Naitwar", "Sankri", "Osla", "Jakholi", "Bhatwari", "Netala"],
        
        // Jammu & Kashmir - Key districts
        "Srinagar" => ["Srinagar", "Ganderbal", "Sonamarg", "Kangan", "Pahalgam", "Anantnag", "Bijbehara", "Kulgam", "Shopian", "Pulwama", "Awantipora", "Tral", "Budgam", "Charar-e-Sharif", "Magam"],
        "Jammu" => ["Jammu", "Akhnoor", "Samba", "Vijaypur", "Bishnah", "R.S. Pura", "Arnia", "Marh", "Nagrota", "Jourian", "Khour", "Phallian", "Mandal", "Chhamb", "Nowshera"],
        "Anantnag" => ["Anantnag", "Bijbehara", "Kulgam", "Shopian", "Pulwama", "Awantipora", "Tral", "Pahalgam", "Achabal", "Kokernag", "Verinag", "Qazigund", "Dooru", "Shangus", "Mattan"],
        "Baramulla" => ["Baramulla", "Sopore", "Pattan", "Uri", "Tangmarg", "Gulmarg", "Kupwara", "Handwara", "Langate", "Kralpora", "Sogam", "Lolab", "Dangiwacha", "Rafiabad", "Boniyar"],
        "Budgam" => ["Budgam", "Charar-e-Sharif", "Magam", "Beerwah", "Khansahib", "Chadoora", "Soibugh", "Narbal", "Ganderbal", "Sonamarg", "Kangan", "Pahalgam", "Anantnag", "Bijbehara", "Kulgam"],
        "Pulwama" => ["Pulwama", "Awantipora", "Tral", "Pampore", "Kakapora", "Litter", "Rajpora", "Tahab", "Gulzarpora", "Newa", "Ladhoo", "Nihama", "Karewa", "Dangerpora", "Rohmu"],
        "Kupwara" => ["Kupwara", "Handwara", "Langate", "Kralpora", "Sogam", "Lolab", "Dangiwacha", "Rafiabad", "Boniyar", "Baramulla", "Sopore", "Pattan", "Uri", "Tangmarg", "Gulmarg"],
        "Kulgam" => ["Kulgam", "Shopian", "Pulwama", "Awantipora", "Tral", "Anantnag", "Bijbehara", "Pahalgam", "Achabal", "Kokernag", "Verinag", "Qazigund", "Dooru", "Shangus", "Mattan"],
        "Shopian" => ["Shopian", "Pulwama", "Awantipora", "Tral", "Kulgam", "Anantnag", "Bijbehara", "Pahalgam", "Achabal", "Kokernag", "Verinag", "Qazigund", "Dooru", "Shangus", "Mattan"],
        "Ganderbal" => ["Ganderbal", "Sonamarg", "Kangan", "Pahalgam", "Anantnag", "Bijbehara", "Kulgam", "Shopian", "Pulwama", "Awantipora", "Tral", "Budgam", "Charar-e-Sharif", "Magam", "Beerwah"],
        "Bandipora" => ["Bandipora", "Sumbal", "Sonawari", "Ganderbal", "Sonamarg", "Kangan", "Pahalgam", "Anantnag", "Bijbehara", "Kulgam", "Shopian", "Pulwama", "Awantipora", "Tral", "Budgam"],
        "Leh" => ["Leh", "Kargil", "Drass", "Zanskar", "Nubra", "Changthang", "Nyoma", "Durbuk", "Khaltsi", "Diskit", "Hunder", "Turtuk", "Panamik", "Sumur", "Sakti"],
        "Kargil" => ["Kargil", "Drass", "Zanskar", "Leh", "Nubra", "Changthang", "Nyoma", "Durbuk", "Khaltsi", "Diskit", "Hunder", "Turtuk", "Panamik", "Sumur", "Sakti"],
        "Udhampur" => ["Udhampur", "Reasi", "Katra", "Kud", "Ramnagar", "Chenani", "Majalta", "Gool", "Gulabgarh", "Pouni", "Arnas", "Thathri", "Doda", "Bhaderwah", "Gandoh"],
        "Doda" => ["Doda", "Bhaderwah", "Gandoh", "Thathri", "Kishtwar", "Paddar", "Marwah", "Wadwan", "Bunjwah", "Chatroo", "Inderwal", "Parlanka", "Bhalla", "Assar", "Dachhan"],
        "Kishtwar" => ["Kishtwar", "Paddar", "Marwah", "Wadwan", "Bunjwah", "Chatroo", "Inderwal", "Parlanka", "Bhalla", "Assar", "Dachhan", "Doda", "Bhaderwah", "Gandoh", "Thathri"],
        "Rajouri" => ["Rajouri", "Nowshera", "Sunderbani", "Kalakote", "Thanamandi", "Darhal", "Budhal", "Koteranka", "Manjakote", "Doongi", "Poonch", "Mendhar", "Surankote", "Haveli", "Loran"],
        "Poonch" => ["Poonch", "Mendhar", "Surankote", "Haveli", "Loran", "Rajouri", "Nowshera", "Sunderbani", "Kalakote", "Thanamandi", "Darhal", "Budhal", "Koteranka", "Manjakote", "Doongi"],
        "Ramban" => ["Ramban", "Banihal", "Gool", "Gulabgarh", "Pouni", "Arnas", "Thathri", "Doda", "Bhaderwah", "Gandoh", "Kishtwar", "Paddar", "Marwah", "Wadwan", "Bunjwah"],
        "Reasi" => ["Reasi", "Katra", "Kud", "Ramnagar", "Chenani", "Majalta", "Gool", "Gulabgarh", "Pouni", "Arnas", "Thathri", "Doda", "Bhaderwah", "Gandoh", "Udhampur"],
        "Kathua" => ["Kathua", "Hiranagar", "Basholi", "Billawar", "Bani", "Mahanpur", "Lakhanpur", "Nagri", "Marheen", "Dinga Amb", "Samba", "Vijaypur", "Bishnah", "R.S. Pura", "Arnia"],
        "Samba" => ["Samba", "Vijaypur", "Bishnah", "R.S. Pura", "Arnia", "Marh", "Nagrota", "Jourian", "Khour", "Phallian", "Mandal", "Chhamb", "Nowshera", "Jammu", "Akhnoor"]
    ];
}

// Helper functions
function getDistrictsForState($state) {
    $districtsMap = getDistrictsMap();
    return $districtsMap[$state] ?? [];
}

function getCitiesForDistrict($district) {
    $citiesMap = getCitiesMap();
    return $citiesMap[$district] ?? [];
}