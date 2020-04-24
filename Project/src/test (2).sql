-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 18, 2019 at 07:47 AM
-- Server version: 10.1.40-MariaDB
-- PHP Version: 7.3.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `test`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `UserId` char(5) NOT NULL,
  `ProductID` char(5) NOT NULL,
  `ProductQty` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `lastlogin`
--

CREATE TABLE `lastlogin` (
  `UserID` char(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `ProductID` char(5) NOT NULL,
  `ProductName` text NOT NULL,
  `ProductColor` text NOT NULL,
  `ProductPrice` int(11) NOT NULL,
  `ProductQty` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`ProductID`, `ProductName`, `ProductColor`, `ProductPrice`, `ProductQty`) VALUES
('PD001', 'test', 'black', 123, 1);

-- --------------------------------------------------------

--
-- Table structure for table `transactiondetail`
--

CREATE TABLE `transactiondetail` (
  `TransactionID` char(5) NOT NULL,
  `ProductID` char(5) NOT NULL,
  `ProductQty` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transactiondetail`
--

INSERT INTO `transactiondetail` (`TransactionID`, `ProductID`, `ProductQty`) VALUES
('TH001', 'PD001', 1),
('TH002', 'PD001', 1),
('TH003', 'PD001', 1),
('TH004', 'PD001', 1),
('TH005', 'PD001', 2);

-- --------------------------------------------------------

--
-- Table structure for table `transactionheader`
--

CREATE TABLE `transactionheader` (
  `TransactionId` char(5) NOT NULL,
  `UserId` char(5) NOT NULL,
  `TransactionStatus` text NOT NULL,
  `TransactionRating` int(1) DEFAULT NULL,
  `TransactionDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transactionheader`
--

INSERT INTO `transactionheader` (`TransactionId`, `UserId`, `TransactionStatus`, `TransactionRating`, `TransactionDate`) VALUES
('TH001', 'US002', 'Finish', 4, '2019-06-07'),
('TH002', 'US003', 'Finish', 1, '2019-06-11'),
('TH003', 'US004', 'Finish', 5, '2019-06-12'),
('TH004', 'US005', 'Finish', 5, '2019-06-17'),
('TH005', 'US002', 'Finish', 2, '2019-06-17');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `UserId` char(5) NOT NULL,
  `UserEmail` varchar(30) NOT NULL,
  `UserPassword` varchar(20) NOT NULL,
  `UserDOB` date NOT NULL,
  `UserGender` varchar(10) NOT NULL,
  `UserAddress` varchar(50) NOT NULL,
  `UserRole` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`UserId`, `UserEmail`, `UserPassword`, `UserDOB`, `UserGender`, `UserAddress`, `UserRole`) VALUES
('US001', 'asd@gmail.com', 'asd123', '2019-06-18', 'Male', 'asdasdsadasdas', 'admin'),
('US002', 'aji@gmail.com', 'aji123', '1916-01-01', 'Male', 'asdsadsadasd', 'user'),
('US003', 'doyouraim@gmail.com', 'doyouraim11', '1910-01-01', 'Male', 'jlhu iyguiyagina', 'user'),
('US004', 'kontolagus@gmail.com', 'kontol123', '2004-07-05', 'Female', 'pinggir jalan', 'user'),
('US005', 'chickencollege@steam.com', 'a1', '2016-01-01', 'Male', 'aku tinggal di meikarta', 'user');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD KEY `ProductID` (`ProductID`),
  ADD KEY `UserId` (`UserId`);

--
-- Indexes for table `lastlogin`
--
ALTER TABLE `lastlogin`
  ADD PRIMARY KEY (`UserID`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`ProductID`);

--
-- Indexes for table `transactiondetail`
--
ALTER TABLE `transactiondetail`
  ADD KEY `ProductID` (`ProductID`),
  ADD KEY `TransactionID` (`TransactionID`);

--
-- Indexes for table `transactionheader`
--
ALTER TABLE `transactionheader`
  ADD PRIMARY KEY (`TransactionId`),
  ADD KEY `UserId` (`UserId`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`UserId`),
  ADD UNIQUE KEY `email` (`UserEmail`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`ProductID`) REFERENCES `products` (`ProductID`),
  ADD CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`UserId`) REFERENCES `user` (`UserId`);

--
-- Constraints for table `lastlogin`
--
ALTER TABLE `lastlogin`
  ADD CONSTRAINT `lastlogin_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserId`);

--
-- Constraints for table `transactiondetail`
--
ALTER TABLE `transactiondetail`
  ADD CONSTRAINT `transactiondetail_ibfk_1` FOREIGN KEY (`TransactionID`) REFERENCES `transactionheader` (`TransactionId`),
  ADD CONSTRAINT `transactiondetail_ibfk_2` FOREIGN KEY (`ProductID`) REFERENCES `products` (`ProductID`),
  ADD CONSTRAINT `transactiondetail_ibfk_3` FOREIGN KEY (`TransactionID`) REFERENCES `transactionheader` (`TransactionId`);

--
-- Constraints for table `transactionheader`
--
ALTER TABLE `transactionheader`
  ADD CONSTRAINT `transactionheader_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `user` (`UserId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
