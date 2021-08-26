package id.temanisolasi.utils

import id.temanisolasi.R
import id.temanisolasi.data.model.*
import id.temanisolasi.ui.base.profile.editprofile.EditProfileActivity
import id.temanisolasi.ui.settings.detail.DetailSettingsActivity

object DataHelpers {
    val errorLoginMessage =
        mapOf(
            "There is no user record corresponding to this identifier. The user may have been deleted." to "User tidak ditemukan",
            "The email address is badly formatted." to "Email tidak valid",
            "The email address is already in use by another account." to "Email sudah terdaftar",
            "The password is invalid or the user does not have a password." to "Password salah",
            "The given password is invalid. [ Password should be at least 6 characters ]" to "Password paling sedikit 6 karakter"
        )

    val jsonAnimation = listOf(
        "https://assets5.lottiefiles.com/packages/lf20_TYZsbM.json"
    )

    val listOfDataIsolation = listOf(
        "Nama", "Jenis Kelamin", "Tanggal Lahir", "Alamat",
        "Mulai Isolasi", "Golongan Darah", "Berat Badan", "Status Vaksinasi", "Tingkat Gejala"
    )

    val itemInputData = listOf(
        InputData(Helpers.dummyIconHome[0], "SUHU TUBUH", 0),
        InputData(Helpers.dummyIconHome[1], "SATURASI OKSIGEN", 1),
        InputData(Helpers.dummyIconHome[2], "OBAT-OBATAN", 2),
        InputData(Helpers.dummyIconHome[3], "KEGIATAN", 3),
    )

    val imageInputData = listOf(
        R.drawable.il_temp,
        R.drawable.il_oxy,
        R.drawable.il_med,
        R.drawable.il_task,
    )

    val cdInputData = listOf(
        "Cek suhu tubuhmu menggunakan thermometer apapun yang kamu miliki Jangan lupa cek suhu tubuh setiap pagi dan sore hari ya",
        "Saturasi oksigen adalah ukuran yang menunjukkan seberapa banyak oksigen yang terikat pada hemoglobin dalam darah. Pastikan saturasi oksigenmu diatas 95% ya",
        "Pastikan anda mengkonsumsi obat sesuai dengan anjuran dokter.",
    )

    val tdInputData = listOf(
        "Berapa suhu tubuhmu saat ini?",
        "Berapa saturasi oksigenmu saat ini?",
        "Obat apa yang anda konsumsi saat ini?",
        "Lakukan beberapa saran kegiatan di bawah ini"
    )

    val dataHomeTitle = listOf(
        "Ini hari pertamamu, istirahat cukup ya dan minimalisir kegiatanmu semangat!",
        "Pikiran yang sehat akan membawa hidup menjadi lebih sehat",
        "Jangan biarkan kekhawatiran melemahkanmu hari ini!",
        "Tetap patuhi protokol dan tetap semangat",
        "Kurang enak badan? Hentikan penyebarannya, tetap di tempat tidur!",
        "Jarak membuat kita lebih kuat, tetap jaga prokes dan semangat!",
        "Konsumsi Makanan yang sehat dan tidur dengan baik",
        "Jangan lupa makan obatmu, agar tetap terjaga imunmu!",
        "Boleh pasrah, tapi jangan menyerah!",
        "Percayalah keajaiban terjadi setiap hari, tetap semangat!",
        "Kamu telah melewati hari dengan baik!",
        "Tetap dirumah saja dan tetap patuhi protokol kesehatan!",
        "yey... tidak lama lagi, kamu akan benar-benar sehat dan tersenyum kembali!",
        "Ini hari terakhirmu, kamu lebih kuat dari yang kamu tau selamat!",
    )

    val notificationTitle = listOf(
        "Selamat Pagi, {0}",
        "{0}, Kamu tau ga?",
        "Hallo {0}, udah malem nih"
    )

    val notificationMessage = listOf(
        "Yuk, jangan lupa isi data kesehatanmu hari ini!",
        "Tertawa itu bisa jadi obat terbaik, tapi kalau tertawa tanpa alasan yang jelas, mungkin kamu butuh obat, Yuk diminum obatnya.",
        "Jangan scroll medsos terus, yuk minum obat malamnya, daripada nanti udah ngantuk mending sekarang aja"
    )

    val listOfGuide = listOf(
        Guide(
            "https://user-images.githubusercontent.com/32610660/129569534-bf302963-6e6f-4fb2-aa1e-4e183aeb1a3e.png",
            "Awal Infeksi"
        ),
        Guide(
            "https://user-images.githubusercontent.com/32610660/129569547-ba0d3134-edd5-48e9-a4b8-2b6e42d26bd5.png",
            "Masa Isolasi"
        ),
        Guide(
            "https://user-images.githubusercontent.com/32610660/129569556-97e3a26b-e3c2-4c90-9b9a-9acbdd46003a.png",
            "Selesai Isolasi"
        )
    )

    val listOfFavGames = listOf(
        FavGames(
            "Nonton Film", 0,
            "https://user-images.githubusercontent.com/32610660/129692581-c3267603-8d8f-4499-924f-221eae538ac5.png",
        ),
        FavGames(
            "Melukis", 1,
            "https://user-images.githubusercontent.com/32610660/129692586-b6a7c6d5-223a-43a3-a486-6faf4f48c2f7.png",
        ),
        FavGames(
            "Bernyanyi", 0,
            "https://user-images.githubusercontent.com/32610660/129692589-ae2386a6-cb5f-4fea-923d-ff3081a2724e.png",
        ),
        FavGames(
            "Membuat Roket", 0,
            "https://user-images.githubusercontent.com/32610660/129692593-0a5242ff-7a1b-46c3-9fc4-87b2d2128a8b.png",
        )
    )

    val listOfGames = listOf(
        Games(
            "Kita flashback memori lama kayanya seru nih, liat foto-foto lama bareng temen sekolah yuk, kalau nemu yang seru jangan lupa share ke temen-temen biar mereka juga ikut flashback juga.",
            -1, 0
        ),
        Games(
            "Kalau lagi sendiri gini kesempatan nih buat lebih mengenal diri sendiri, coba yuk cek kepribadianmu di 16Personality",
            -1, 1, "https://www.16personalities.com/id", "Kunjungi"
        ),
        Games(
            "Kamu suka tanaman ga? Dirumah ada tanaman? Coba deh pilih 1 tanaman dan rawat dia tiap hari, liat dia tiap hari tumbuh seru loo",
            -1,
            0
        ),
        Games(
            "kamu harus banget liat ini, coba search “Film Pendek KTP” di Youtube ada film pendek judulnya KTP, cuma 15 menit tapi lumayan banget bikin ketawa",
            -1,
            0
        ),
        Games(
            "Suka nonton webseries? Kamu harus nyoba nonton “Webseriesnya Radit” di Youtube ada 17 video bisa banget kamu tonton tiap hari",
            -1,
            0
        ),
        Games(
            "Bosen kan mau ngapain? Sama aku juga wkwkwkwk yuk terus cari rekomendasi di aplikasi ini, ketuk coba lagi ya",
            -1,
            0
        ),
        Games(
            "Bosan ya? Yuk coba buka e-commerce kesayangan kalian, masukin barang-barang impian ke keranjang, walaupun belum tau kapan bisa kebeli, tapi nyari barang impian itu menyenangkan lhoo",
            -1,
            0
        ),
        Games(
            "Tau origami ga? Iyaa itu seni melipat kertas jadi berbagai bentuk, yuk coba buat Origami, kamu juga boleh liat tutorial di youtube biar bisa buat bentuk lainnya.",
            -1,
            0
        ),
        Games("Apa kabar ya teman-temanmu? Yuk coba video Call group ke teman SMP mu!", -1, 0),
        Games(
            "Kayaknya kalau ketemu teman-teman asik ya, mending ketemu online dulu, yuk coba video call group ke teman SMA mu!",
            -1,
            0
        ),
        Games(
            "Teman-teman SD masih inget ga ya? Yuk coba hubungi salah satu teman SD mu, sekadar bercerita kabar atau kesibukan mereka kayaknya seru deh.\n",
            -1,
            0
        ),
        Games(
            "Ada rekomendasi film pendek lagi nih! FIlm yang terkenal baru-baru ini, yuk coba tonton film pendek “TIlik” dari Ravacana di Youtube.",
            0,
            1,
            "https://www.youtube.com/watch?v=GAyvgz8_zV8",
            "Tonton"
        ),
        Games(
            "Kamu harus coba short movie “Matchalatte” dari Kevin Hendrawan, seru banget soalnya, yuk coba tonton di Youtube, ada 2 episode ya",
            0,
            1,
            "https://www.youtube.com/watch?v=Mb8aeRuAJZw",
            "Tonton"
        ),
    )

    val listOfQuestion = listOf(
        Question(
            "Apa yang dilakukan jika sudah 10 hari isolasi mandiri pada pasien OTG  dan pasien ringan?",
            "OTG tidak harus PCR jika RINGAN harus melakukan PCR jika negative tunggu bebas gejala 10 hari dihitung dari bebas gejala",
            0
        ),
        Question(
            "Jika isoman suplemen apa yang harus dimakan dan makanan apa yang  harus  dihindari?",
            "Makanan yang harus dihindari adalah makanan berminyak yang mengandung lemak berlebihan dan kolesterol, konsumsi gula dibatasi terutama pada penderita diabetes , sediakan oksigen jika bergejala sedang. Kemudian makana makanan yang mengandung antioksidan seperti brokoli, kembang kol, tomat, wortel Malam bawang merah, bawang putih, jahe merah, air yang cukup. ",
            0
        ),
        Question(
            "Miniman paling enak apa?",
            """
                <ul>
                  <li>Coffee</li>
                  <li>Tea</li>
                  <li>Milk</li>
                </ul>  
            """.trimIndent(), 0
        ),
        Question(
            "Gambar apa hayooo...",
            "https://user-images.githubusercontent.com/87884013/129652107-2342be62-4d10-4f23-a458-dd14b24f51c3.jpg",
            type = 1
        )
    )

    val itemSettings = listOf(
        Settings("Edit Profil", EditProfileActivity::class.java),
        Settings("Credits", DetailSettingsActivity::class.java),
        Settings("Tentang Kami", DetailSettingsActivity::class.java),
        Settings("Kebijakan Privasi", DetailSettingsActivity::class.java),
        Settings("Masukan"),
        Settings("Keluar"),
    )

    val listOfTeam = listOf(
        Team(
            "https://user-images.githubusercontent.com/32610660/130554306-0969d9e2-59b6-4c98-8703-e6ce671838c2.png",
            "Dery Sudrajat",
            "Android Developer",
            "https://www.instagram.com/derysudrajat/"
        ),
        Team(
            "https://user-images.githubusercontent.com/45125149/130256512-3d328b66-f1c6-4b21-bf81-13ed0774a16b.png",
            "Thowaf Fuad Hasan",
            "UI/UX Designer",
            "https://www.instagram.com/khurun_am/"
        ),
        Team(
            "https://user-images.githubusercontent.com/87884013/130306521-eef652e1-9a80-4047-a60e-00f1843d2883.jpg",
            "Khurun 'Ain Muzaqi",
            "Content Writer",
            "https://www.instagram.com/khurun_am/"
        ),
        Team(
            "https://user-images.githubusercontent.com/87884013/130306758-70fe2951-00f7-4ae8-ac85-a63a04352e1b.jpg",
            "Janur Syahputra",
            "Content Writer",
            "https://www.instagram.com/_janursp/"
        ),
    )

    val privacyPolicy = """
        <h2>TemanIsolasi - Isolasi Mandiri di Rumah Aja &gt; Privacy Policy</h2>
        <hr>
        <br>
        <div id="content"><br><b>Privacy Policy</b><p>Dery Sudrajat built the TemanIsolasi - Isolasi Mandiri di Rumah Aja app as a Free app. This SERVICE is provided by Dery Sudrajat at no cost and is intended for use as is.</p><p>This page is used to inform visitors regarding my policies with the collection, use, and disclosure of Personal Information if anyone decided to use my Service.</p><p>If you choose to use my Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that I collect is used for providing and improving the Service. I will not use or share your information with anyone except as described in this Privacy Policy.</p><p>The terms used in this Privacy Policy have the same meanings as in our Terms and Conditions, which is accessible at TemanIsolasi - Isolasi Mandiri di Rumah Aja unless otherwise defined in this Privacy Policy.</p><p><br><b>Information Collection and Use</b></p><p>For a better experience, while using our Service, I may require you to provide us with certain personally identifiable information. The information that I request will be retained on your device and is not collected by me in any way.</p><div><p>The app does use third-party services that may collect information used to identify you.</p><p>Link to the privacy policy of third party service providers used by the app</p><ul><li><a href="https://www.google.com/policies/privacy/" rel="nofollow" target="_blank">Google Play Services</a></li><li><a href="https://firebase.google.com/policies/analytics" rel="nofollow" target="_blank">Google Analytics for Firebase</a></li><li><a href="https://firebase.google.com/support/privacy/" rel="nofollow" target="_blank">Firebase Crashlytics</a></li></ul></div><p><br><b>Log Data</b></p><p>I want to inform you that whenever you use my Service, in a case of an error in the app I collect data and information (through third-party products) on your phone called Log Data. This Log Data may include information such as your device Internet Protocol (“IP”) address, device name, operating system version, the configuration of the app when utilizing my Service, the time and date of your use of the Service, and other statistics.</p><p><br><b>Cookies</b></p><p>Cookies are files with a small amount of data that are commonly used as anonymous unique identifiers. These are sent to your browser from the websites that you visit and are stored on your device's internal memory.</p><p>This Service does not use these “cookies” explicitly. However, the app may use third-party code and libraries that use “cookies” to collect information and improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. If you choose to refuse our cookies, you may not be able to use some portions of this Service.</p><p><br><b>Service Providers</b></p><p>I may employ third-party companies and individuals due to the following reasons:</p><ul><li>To facilitate our Service;</li><li>To provide the Service on our behalf;</li><li>To perform Service-related services; or</li><li>To assist us in analyzing how our Service is used.</li></ul><p>I want to inform users of this Service that these third parties have access to your Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose.</p><p><br><b>Security</b></p><p>I value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it. But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and I cannot guarantee its absolute security.</p><p><br><b>Links to Other Sites</b></p><p>This Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated by me. Therefore, I strongly advise you to review the Privacy Policy of these websites. I have no control over and assume no responsibility for the content, privacy policies, or practices of any third-party sites or services.</p><p><br><b>Children’s Privacy</b></p><p>These Services do not address anyone under the age of 13. I do not knowingly collect personally identifiable information from children under 13 years of age. In the case I discover that a child under 13 has provided me with personal information, I immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact me so that I will be able to do the necessary actions.</p><p><br><b>Changes to This Privacy Policy</b></p><p>I may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. I will notify you of any changes by posting the new Privacy Policy on this page.</p><p>This policy is effective as of 2021-08-24</p><p><br><b>Contact Us</b></p><p>If you have any questions or suggestions about my Privacy Policy, do not hesitate to contact me at dery.sudrajat17@Gmial.com.</p></div>
        </div>
    """.trimIndent()

}