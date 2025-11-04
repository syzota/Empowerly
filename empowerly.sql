-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 30, 2025 at 05:25 AM
-- Server version: 8.4.3
-- PHP Version: 8.3.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `empowerly`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id_user` int NOT NULL,
  `status` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id_user`, `status`) VALUES
(1, 'Hukum'),
(2, 'Pendidikan'),
(3, 'Kesehatan'),
(4, 'Sosial'),
(5, 'Ekonomi'),
(6, 'Komunitas');

-- --------------------------------------------------------

--
-- Table structure for table `forum`
--

CREATE TABLE `forum` (
  `id_forum` int NOT NULL,
  `judul` varchar(150) NOT NULL,
  `deskripsi` text,
  `id_user` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `forum`
--

INSERT INTO `forum` (`id_forum`, `judul`, `deskripsi`, `id_user`) VALUES
(1, 'Apakah Hukuman untuk Pelaku Kekerasan Sudah Adil?', 'Diskusi tentang pasal-pasal hukum terkait kekerasan.', 6),
(2, 'Perlindungan Pekerja di Era Modern', 'Bahas tantangan implementasi hukum ketenagakerjaan di lapangan.', 6),
(3, 'Penerapan Pendidikan Inklusif di Sekolah', 'Bagaimana kesiapan sekolah terhadap peserta didik dengan kebutuhan khusus?', 6),
(4, 'Menumbuhkan Karakter Disiplin di Sekolah', 'Strategi pendidikan karakter yang efektif di lingkungan sekolah.', 6),
(5, 'Kesehatan Mental Remaja', 'Diskusi terbuka mengenai tantangan kesehatan mental di kalangan pelajar.', 6),
(6, 'Pola Hidup Sehat Sehari-hari', 'Tips sederhana menjaga kesehatan fisik.', 6),
(7, 'Peran Masyarakat dalam Mencegah Kekerasan Sosial', 'Sharing pengalaman dan inisiatif warga.', 6),
(8, 'Peluang Ekonomi Kreatif di Era Digital', 'Bagaimana cara memulai usaha kecil berbasis ide kreatif?', 6),
(9, 'Manajemen Keuangan Rumah Tangga', 'Cara sederhana mengatur keuangan keluarga agar stabil.', 6),
(10, 'Etika Diskusi di Dunia Maya', 'Diskusi santai soal tata krama dalam forum dan media sosial.', 6),
(11, 'Kesehatan Mental Mahasiswa', 'About the problems we face', 6),
(12, 'Politik Oligraki', 'blahbaa', 6);

-- --------------------------------------------------------

--
-- Table structure for table `kategori`
--

CREATE TABLE `kategori` (
  `id_kategori` int NOT NULL,
  `nama_kategori` varchar(100) NOT NULL,
  `id_user` int NOT NULL,
  `deskripsi` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `kategori`
--

INSERT INTO `kategori` (`id_kategori`, `nama_kategori`, `id_user`, `deskripsi`) VALUES
(1, 'Hukum Kekerasan', 1, 'Membahas hukum dan peraturan terkait kekerasan.'),
(2, 'Hukum Ketenagakerjaan', 1, 'Isu dan hak pekerja di dunia kerja.'),
(3, 'Pendidikan Inklusif', 2, 'Pembahasan sistem pendidikan yang terbuka untuk semua.'),
(4, 'Pendidikan Karakter', 2, 'Materi pembentukan karakter generasi muda.'),
(5, 'Kesehatan Mental', 3, 'Kesehatan jiwa dan keseimbangan emosi.'),
(6, 'Kesehatan Fisik', 3, 'Pola hidup sehat dan pencegahan penyakit.'),
(7, 'Sosial Kemasyarakatan', 4, 'Dinamika dan peran masyarakat dalam kehidupan sosial.'),
(8, 'Ekonomi Kreatif', 5, 'Topik terkait usaha dan bisnis kreatif.'),
(9, 'Ekonomi Keluarga', 5, 'Manajemen keuangan keluarga dan rumah tangga.');

-- --------------------------------------------------------

--
-- Table structure for table `komen`
--

CREATE TABLE `komen` (
  `id_komen` int NOT NULL,
  `isi` text NOT NULL,
  `waktu` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `id_forum` int NOT NULL,
  `id_user` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `komen`
--

INSERT INTO `komen` (`id_komen`, `isi`, `waktu`, `id_forum`, `id_user`) VALUES
(1, 'Menurutku hukum masih terlalu ringan buat pelaku kekerasan, apalagi yang korbannya anak-anak.', '2025-10-19 13:40:46', 1, 9),
(2, 'Korban harusnya dilindungi, bukan disalahkan.', '2025-10-19 13:40:46', 1, 11),
(3, 'Sistem hukum harus lebih berpihak pada korban sih, bukan pelaku.', '2025-10-19 13:40:46', 1, 13),
(4, 'Aku pernah lihat kasus serupa di berita, miris banget sumpah.', '2025-10-19 13:40:46', 1, 10),
(5, 'Sekolah inklusif itu penting, biar semua anak punya kesempatan yang sama.', '2025-10-19 13:40:46', 2, 8),
(6, 'Tapi emang tantangannya di tenaga pengajar yang belum terlatih.', '2025-10-19 13:40:46', 2, 14),
(7, 'Kurikulum juga harus disesuaikan, gak bisa disamain sama sekolah umum.', '2025-10-19 13:40:46', 2, 15),
(8, 'Aku setuju banget, teman-teman difabel harus punya ruang belajar yang aman.', '2025-10-19 13:40:46', 2, 12),
(9, 'Aku biasa meditasi sebelum belajar, lumayan ngurangin stres ujian.', '2025-10-19 13:40:46', 3, 15),
(10, 'Kalau aku sih lebih suka journaling, nulis hal-hal yang bikin cemas.', '2025-10-19 13:40:46', 3, 16),
(11, 'Musik instrumental juga ngebantu banget buat fokus.', '2025-10-19 13:40:46', 3, 9),
(12, 'Aku coba yoga minggu lalu, ternyata efektif banget buat ngatur napas.', '2025-10-19 13:40:46', 3, 10),
(13, 'Bisnis thrift dan digital art lagi rame banget sih sekarang.', '2025-10-19 13:40:46', 4, 13),
(14, 'Iya, apalagi kalau tau cara promosi di media sosial.', '2025-10-19 13:40:46', 4, 7),
(15, 'Menurutku penting juga ngerti dasar hak cipta biar gak kena plagiarisme.', '2025-10-19 13:40:46', 4, 12),
(16, 'Aku sempat jualan totebag hasil desain sendiri, seru banget prosesnya.', '2025-10-19 13:40:46', 4, 8),
(17, 'Setuju banget, kadang orang debat online malah nyerang pribadi bukannya argumen.', '2025-10-19 13:40:46', 5, 12),
(18, 'Iya, kadang niatnya diskusi malah jadi toxic.', '2025-10-19 13:40:46', 5, 10),
(19, 'Aku sekarang pilih skip aja kalo udah mulai panas suasananya.', '2025-10-19 13:40:46', 5, 11),
(20, 'Perlu banget etika digital diajarin dari sekolah.', '2025-10-19 13:40:46', 5, 14),
(21, 'Sekolahku kerja sama sama pihak kepolisian buat sosialisasi anti kekerasan, keren banget.', '2025-10-19 13:40:46', 6, 10),
(22, 'Wah keren! Harusnya semua sekolah juga kayak gitu.', '2025-10-19 13:40:46', 6, 13),
(23, 'Anak-anak jadi lebih paham batasan perilaku yang bisa berujung ke hukum.', '2025-10-19 13:40:46', 6, 15),
(24, 'Kampanye kesadaran kayak gini harus sering diadain.', '2025-10-19 13:40:46', 6, 9),
(25, 'Aku mulai biasain makan sayur tiap hari, susah di awal tapi sekarang udah terbiasa.', '2025-10-19 13:40:46', 7, 8),
(26, 'Keren! Aku masih berjuang buat ngurangin junk food nih.', '2025-10-19 13:40:46', 7, 7),
(27, 'Aku juga mulai rutin minum air putih 2 liter per hari.', '2025-10-19 13:40:46', 7, 11),
(28, 'Kesehatan mental juga penting dijaga, bukan cuma fisik.', '2025-10-19 13:40:46', 7, 16),
(29, 'Menurutku disiplin bisa dibentuk dari contoh orang tua dan guru juga sih.', '2025-10-19 13:40:46', 8, 14),
(30, 'Setuju, anak-anak bakal nuru dari kebiasaan di rumah.', '2025-10-19 13:40:46', 8, 9),
(31, 'Disiplin tuh gak bisa instan, harus dibiasain pelan-pelan.', '2025-10-19 13:40:46', 8, 12),
(32, 'Kadang lingkungan juga pengaruh banget, apalagi pergaulan.', '2025-10-19 13:40:46', 8, 15),
(33, 'Di komplekku ada kegiatan bersih-bersih tiap minggu, tapi yang datang cuma itu-itu aja.', '2025-10-19 13:40:46', 9, 16),
(34, 'Kayaknya butuh sistem reward biar warga lebih semangat.', '2025-10-19 13:40:46', 9, 13),
(35, 'Iya, kalau ada kompetisi kecil antar RT pasti lebih rame.', '2025-10-19 13:40:46', 9, 7),
(36, 'Sampah organik dan anorganik juga sebaiknya dipisah dari rumah.', '2025-10-19 13:40:46', 9, 11),
(37, 'Aku bagi uang jajan mingguan ke amplop kecil, jadi gak gampang habis hehe.', '2025-10-19 13:40:46', 10, 7),
(38, 'Cara bagus tuh, aku masih suka impulsif kalo lihat promo ?.', '2025-10-19 13:40:46', 10, 8),
(39, 'Aku mulai catat pengeluaran harian di spreadsheet, lumayan ngebantu banget.', '2025-10-19 13:40:46', 10, 10),
(40, 'Budgeting tuh penting banget biar gak nyesel di akhir bulan.', '2025-10-19 13:40:46', 10, 12),
(44, 'Kalo ada teman, cerita', '2025-10-23 14:52:06', 11, 46),
(45, 'tes komun', '2025-10-24 17:41:09', 6, 6),
(46, 'Diskusi kok ngegas!!!', '2025-10-26 23:44:59', 5, 41),
(47, 'Jangan lupa tidur karena laprak banyak!', '2025-10-27 04:20:04', 6, 41),
(48, 'Bahaya ini', '2025-10-27 06:34:07', 12, 41),
(49, 'belum, karena masih banyak pelaku yang masih mendapatkan hukuman yang belum setimpal dan banyak yangg menggunakan uang', '2025-10-28 07:10:16', 1, 48),
(51, '123331', '2025-10-28 07:26:56', 3, 49);

-- --------------------------------------------------------

--
-- Table structure for table `materi`
--

CREATE TABLE `materi` (
  `id_materi` int NOT NULL,
  `judul` varchar(150) NOT NULL,
  `konten` text NOT NULL,
  `tipe` enum('modul','video','infografis','tautan') DEFAULT 'tautan',
  `id_kategori` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `materi`
--

INSERT INTO `materi` (`id_materi`, `judul`, `konten`, `tipe`, `id_kategori`) VALUES
(1, 'Dasar-Dasar Hukum Perlindungan Anak', 'https://perpustakaan.bappenas.go.id/e-library/file_upload/koleksi/migrasi-data-publikasi/file/Unit_Kerja/Deputi_Bidang_Pembangunan_Manusia_Masyarakat_dan_Kebudayaan/Direktorat-Keluarga-Perempuan-Anak-Pemuda-Olahraga/OUTPUT-DIREKTORAT-KPAPO-TAHUN-2016-2020/Modul-Sistem-Perlindungan-Anak/SPA_MODUL_1.pdf', 'modul', 1),
(2, 'Analisis Kasus Kekerasan seksual', 'https://www.youtube.com/watch?v=OxiwR2dRA-w', 'video', 1),
(3, 'UU Perlindungan Anak (Ringkasan)', 'https://www.google.com/imgres?imgurl=https%3A%2F%2Fcdn.antaranews.com%2Fcache%2Finfografis%2F800x533%2F2023%2F12%2F30%2F20231230-CATATAN_KOMNAS_PERLINDUNGAN_ANAK_2023.jpg&tbnid=nCm-v2XnAcFHsM&vet=1&imgrefurl=https%3A%2F%2Fwww.antaranews.com%2Finfografik%2F3893148%2Fcatatan-komnas-perlindungan-anak-2023&docid=V779QhawgFU9GM&w=800&h=1467&source=sh%2Fx%2Fim%2Fm1%2F1&kgs=d58ced4749661b8e&shem=isst', 'infografis', 2),
(4, 'Hak & Kewajiban Pendidik', 'https://share.google/Shs8OGObFc5y1r995', 'modul', 4),
(5, 'Panduan Pendidikan Inklusif', 'https://www.google.com/imgres?imgurl=https%3A%2F%2Frahma.id%2Fwp-content%2Fuploads%2F2020%2F07%2Fliterasi-digital-muslimah-819x1024.png&tbnid=IZIRy_P60hUQtM&vet=1&imgrefurl=https%3A%2F%2Frahma.id%2Furgensi-perempuan-muslim-dalam-literasi-digital%2F&docid=oxBnqqoX_pORPM&w=819&h=1024&source=sh%2Fx%2Fim%2Fm1%2F1&kgs=786d36d80d8a629d&shem=isst', 'infografis', 3),
(6, 'Video: Praktik Pengajaran pencegahan Inklusif', 'youtube.com/watch?si=M-VnmPYYQ5mYeSiE&v=XcAe9xm_9Hc&feature=youtu.be', 'video', 3),
(7, 'Panduan Kesehatan Reproduksi', 'https://share.google/qI90t6qhxkTGznN67 ', 'modul', 5),
(8, 'Infografis: Menjaga Kesehatan Mental', 'https://share.google/images/2P4FzqlITaldIEMtj ', 'infografis', 5),
(9, 'Tips UMKM Kreatif', 'https://www.youtube.com/watch?v=Fnio8miqiuc', 'video', 8),
(12, 'Oligarki Politik', 'https://youtu.be/895Cqij7i00?si=Z6N252MTtxcs_4UO', 'tautan', 2),
(13, 'Dasar-Dasar Hukum', 'https://www.youtube.com/watch?v=5aS6DCFMJLY', 'tautan', 2),
(14, 'Antropologi', 'https://kesimankertalangu.desa.id/files/perpustakaan/antropologi-5-2021-05-03.pdf', 'modul', 7);

-- --------------------------------------------------------

--
-- Table structure for table `member`
--

CREATE TABLE `member` (
  `id_user` int NOT NULL,
  `umur` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `member`
--

INSERT INTO `member` (`id_user`, `umur`) VALUES
(1, 24),
(2, 26),
(3, 25),
(4, 27),
(5, 22),
(6, 18),
(7, 22),
(8, 25),
(9, 19),
(10, 23),
(11, 24),
(12, 26),
(13, 21),
(14, 20),
(15, 27),
(16, 22),
(17, 20),
(18, 25),
(19, 27),
(20, 25),
(21, 24),
(22, 19),
(23, 23),
(24, 24),
(25, 21),
(26, 18),
(27, 26),
(28, 21),
(29, 19),
(30, 26),
(31, 25),
(32, 20),
(33, 21),
(34, 24),
(35, 22),
(36, 21),
(37, 20),
(38, 20),
(39, 23),
(40, 18),
(41, 20),
(43, 18),
(44, 29),
(46, 15),
(47, 19),
(48, 21),
(49, 21);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` int NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `username`, `password`) VALUES
(1, 'admin_hukum', 'pass_hukum'),
(2, 'admin_pendidikan', 'pass_pendidikan'),
(3, 'admin_kesehatan', 'pass_kesehatan'),
(4, 'admin_sosial', 'pass_sosial'),
(5, 'admin_ekonomi', 'pass_ekonomi'),
(6, 'admin_komunitas', 'pass_komunitas'),
(7, 'aisyah', 'pass123'),
(8, 'budi', 'pass123'),
(9, 'citra', 'pass123'),
(10, 'damar', 'pass123'),
(11, 'eka', 'pass123'),
(12, 'faris', 'pass123'),
(13, 'gina', 'pass123'),
(14, 'hasan', 'pass123'),
(15, 'ina', 'pass123'),
(16, 'member_joko', 'pass123'),
(17, 'member_kirana', 'pass123'),
(18, 'member_luthfi', 'pass123'),
(19, 'member_mira', 'pass123'),
(20, 'member_nino', 'pass123'),
(21, 'member_oki', 'pass123'),
(22, 'member_putri', 'pass123'),
(23, 'member_qori', 'pass123'),
(24, 'member_raka', 'pass123'),
(25, 'member_salsa', 'pass123'),
(26, 'member_tyo', 'pass123'),
(27, 'member_umi', 'pass123'),
(28, 'member_vina', 'pass123'),
(29, 'member_wira', 'pass123'),
(30, 'member_xena', 'pass123'),
(31, 'member_yoga', 'pass123'),
(32, 'member_zahra', 'pass123'),
(33, 'member_adel', 'pass123'),
(34, 'member_bagas', 'pass123'),
(35, 'member_chika', 'pass123'),
(36, 'member_doni', 'pass123'),
(37, 'member_elsa', 'pass123'),
(38, 'member_fajar', 'pass123'),
(39, 'member_gilang', 'pass123'),
(40, 'member_hana', 'pass123'),
(41, 'denkikah', '123'),
(42, 'orm_user', '12345'),
(43, 'bakugo', 'die'),
(44, 'lukehemmings', '123'),
(45, 'calum', '123'),
(46, 'powerpuff', '1213'),
(47, 'markhas', 'kopi'),
(48, 'angie', '123'),
(49, 'novi123', '12345');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_user`);

--
-- Indexes for table `forum`
--
ALTER TABLE `forum`
  ADD PRIMARY KEY (`id_forum`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `kategori`
--
ALTER TABLE `kategori`
  ADD PRIMARY KEY (`id_kategori`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `komen`
--
ALTER TABLE `komen`
  ADD PRIMARY KEY (`id_komen`),
  ADD KEY `id_forum` (`id_forum`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `materi`
--
ALTER TABLE `materi`
  ADD PRIMARY KEY (`id_materi`),
  ADD KEY `id_kategori` (`id_kategori`);

--
-- Indexes for table `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`id_user`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `forum`
--
ALTER TABLE `forum`
  MODIFY `id_forum` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `kategori`
--
ALTER TABLE `kategori`
  MODIFY `id_kategori` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `komen`
--
ALTER TABLE `komen`
  MODIFY `id_komen` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT for table `materi`
--
ALTER TABLE `materi`
  MODIFY `id_materi` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `forum`
--
ALTER TABLE `forum`
  ADD CONSTRAINT `forum_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `admin` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `kategori`
--
ALTER TABLE `kategori`
  ADD CONSTRAINT `kategori_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `admin` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `komen`
--
ALTER TABLE `komen`
  ADD CONSTRAINT `komen_ibfk_1` FOREIGN KEY (`id_forum`) REFERENCES `forum` (`id_forum`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `komen_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `materi`
--
ALTER TABLE `materi`
  ADD CONSTRAINT `materi_ibfk_1` FOREIGN KEY (`id_kategori`) REFERENCES `kategori` (`id_kategori`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `member`
--
ALTER TABLE `member`
  ADD CONSTRAINT `member_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
