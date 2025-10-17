-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 17, 2025 at 04:52 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_product`
--

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `no` int(11) NOT NULL,
  `id` varchar(255) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `harga` double NOT NULL,
  `kategori` varchar(255) NOT NULL,
  `kondisi` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`no`, `id`, `nama`, `harga`, `kategori`, `kondisi`) VALUES
(1, 'PRD001', 'Laptop ASUS', 8500000, 'Elektronik', 'Baru'),
(2, 'PRD002', 'Mouse Wireless', 150000, 'Elektronik', 'Baru'),
(3, 'PRD003', 'Keyboard Gaming', 450000, 'Elektronik', 'Bekas Bagus'),
(4, 'PRD004', 'Monitor 24 inch', 2200000, 'Elektronik', 'Baru'),
(5, 'PRD005', 'Headset Gaming', 350000, 'Elektronik', 'Bekas'),
(6, 'PRD006', 'Smartphone Samsung', 4500000, 'Elektronik', 'Baru'),
(7, 'PRD007', 'Charger USB-C', 85000, 'Aksesoris', 'Baru'),
(8, 'PRD008', 'Power Bank', 250000, 'Aksesoris', 'Baru'),
(9, 'PRD009', 'Webcam HD', 180000, 'Elektronik', 'Bekas Bagus'),
(10, 'PRD010', 'Speaker Bluetooth', 320000, 'Elektronik', 'Bekas'),
(11, 'PRD011', 'Tablet Android', 2800000, 'Elektronik', 'Baru'),
(12, 'PRD012', 'Smartwatch', 1200000, 'Aksesoris', 'Bekas Bagus'),
(13, 'PRD013', 'Flash Drive 32GB', 65000, 'Penyimpanan', 'Baru'),
(14, 'PRD014', 'Hard Disk 1TB', 750000, 'Penyimpanan', 'Baru'),
(15, 'PRD015', 'Router WiFi', 420000, 'Jaringan', 'Bekas'),
(16, 'PRD016', 'Kabel HDMI', 45000, 'Aksesoris', 'Baru'),
(17, 'PRD017', 'Printer Inkjet', 850000, 'Perangkat Kantor', 'Baru'),
(18, 'PRD018', 'Scanner Document', 650000, 'Perangkat Kantor', 'Bekas Bagus'),
(19, 'PRD019', 'Cooling Pad', 120000, 'Aksesoris', 'Baru'),
(20, 'PRD020', 'Gaming Chair', 1800000, 'Furniture', 'Bekas Bagus'),
(21, 'PRD021', 'makanan', 20000, 'Makanan', 'Lama');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`no`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `no` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
