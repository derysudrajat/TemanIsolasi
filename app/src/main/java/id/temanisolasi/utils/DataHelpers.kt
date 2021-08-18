package id.temanisolasi.utils

import id.temanisolasi.R
import id.temanisolasi.data.model.*

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
        "{0}, Kamu tau ga? Tertawa itu bisa jadi obat terbaik.",
        "Hallo {0}, udah malem jangan scroll medsos terus"
    )

    val notificationMessage = listOf(
        "Yuk, jangan lupa isi data kesehatanmu hari ini!",
        "Tapi kalau tertawa tanpa alasan yang jelas, mungkin kamu butuh obat, Yuk diminum obatnya.",
        "Yuk, minum obat malamnya, daripada nanti udah ngantuk mending sekarang aja"
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

}