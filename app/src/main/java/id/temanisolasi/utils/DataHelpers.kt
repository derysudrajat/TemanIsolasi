package id.temanisolasi.utils

import id.temanisolasi.R
import id.temanisolasi.data.model.InputData

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

    val notificationMessage = listOf(
        "Selamat Pagi, Jangan lupa buat input data kamu hari ini ya",
        "Waktunya minum obat siang, jangan lupa untuk diminum obatnya dan input datanya ya...",
        "Ayo sudah waktunya minum obat malam, jangan lupa untuk input datanya juga ya..."
    )

}