-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 10, 2026 at 05:01 PM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sistem_produksi_roti_kue`
--

-- --------------------------------------------------------

--
-- Table structure for table `produk`
--

CREATE TABLE `produk` (
  `id_produk` int NOT NULL,
  `nama_produk` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `stok` int NOT NULL,
  `harga_produk` double NOT NULL,
  `jumlah_produksi` int NOT NULL,
  `tanggal_produksi` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `status_produksi` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `jenis_produk` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `rasa` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ukuran_roti` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `jenis_kue` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ukuran_kue` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `produk`
--

INSERT INTO `produk` (`id_produk`, `nama_produk`, `stok`, `harga_produk`, `jumlah_produksi`, `tanggal_produksi`, `status_produksi`, `jenis_produk`, `rasa`, `ukuran_roti`, `jenis_kue`, `ukuran_kue`) VALUES
(17, 'Brownies Coklat', 75, 25000, 50, '19-09-2026', 'Diproses', 'Kue', NULL, NULL, 'Brownies', 'Sedang'),
(20, 'Roti Keju', 100, 15000, 50, '20-09-2026', 'Selesai', 'Roti', 'Keju', 'Medium', NULL, NULL),
(90, 'Kue Balok', 85, 20000, 100, '10-10-2026', 'Dibatalkan', 'Kue', NULL, NULL, 'bolu', 'medium');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `produk`
--
ALTER TABLE `produk`
  ADD PRIMARY KEY (`id_produk`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
