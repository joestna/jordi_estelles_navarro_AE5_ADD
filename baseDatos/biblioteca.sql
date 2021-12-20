-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 20, 2021 at 11:50 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `biblioteca`
--

-- --------------------------------------------------------

--
-- Table structure for table `libro`
--

CREATE TABLE `libro` (
  `titulo` varchar(30) DEFAULT NULL,
  `autor` varchar(30) DEFAULT NULL,
  `anyo_nacimiento` varchar(30) DEFAULT NULL,
  `anyo_publicacion` int(11) DEFAULT NULL,
  `editorial` varchar(30) DEFAULT NULL,
  `numero_paginas` int(11) DEFAULT NULL,
  `identificador` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `libro`
--

INSERT INTO `libro` (`titulo`, `autor`, `anyo_nacimiento`, `anyo_publicacion`, `editorial`, `numero_paginas`, `identificador`) VALUES
('El se�or de los anillos', 'J.R.R. Tolkien', '1890', 1950, 'Minotauro', 1392, 1),
('El juego de Ender', 'Orson Scott Card', '1951', 1977, 'Ediciones B', 509, 2),
('El se�or de los anillos', 'J.R.R. Tolkien', '1890', 1950, 'Minotauro', 1392, 3),
('El juego de Ender', 'Orson Scott Card', '1951', 1977, 'Ediciones B', 509, 4),
('Las uvas de la ira', 'John Steinbeck', '1902', 1939, 'Alianza', 619, 6),
('Watchmen', 'Alan Moore', '1953', 1980, 'ECC', 416, 7),
('La hoguera de las vanidades', 'Tom Wolfe', '1930', 1980, 'Anagrama', 636, 8),
('La familia de Pascual Duarte', 'Camilo Jos� Cela', '1916', 1942, 'Destino', 165, 9),
('El se�or de las moscas', 'William Golding', '1911', 1972, 'Alianza', 236, 10),
('La ciudad de los prodigios', 'Eduardo Mendoza', '1943', 1986, 'Seix Barral', 541, 11),
('Ensayo sobre la ceguera', 'Jos� Saramago', '1922', 1995, 'Santillana', 439, 12),
('Los surcos del azar', 'Paco Roca', '1969', 2013, 'Astiberri', 349, 13),
('hola', 'hola', '123', 12, 'asd', 12, 14);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `libro`
--
ALTER TABLE `libro`
  ADD PRIMARY KEY (`identificador`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `libro`
--
ALTER TABLE `libro`
  MODIFY `identificador` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
