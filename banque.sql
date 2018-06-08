-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 08, 2018 at 04:04 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `banque`
--

-- --------------------------------------------------------

--
-- Table structure for table `agence`
--

CREATE TABLE IF NOT EXISTS `agence` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `adresse` int(11) NOT NULL,
  `nom` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE IF NOT EXISTS `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Nom` varchar(50) NOT NULL,
  `Prenom` varchar(50) NOT NULL,
  `adresse` varchar(200) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `idconseiller` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idconseiller` (`idconseiller`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `clientcompte`
--

CREATE TABLE IF NOT EXISTS `clientcompte` (
  `idclient` int(11) NOT NULL,
  `idcompte` int(11) NOT NULL,
  PRIMARY KEY (`idclient`,`idcompte`),
  KEY `fk_compte` (`idcompte`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `compte`
--

CREATE TABLE IF NOT EXISTS `compte` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `solde` float NOT NULL,
  `decouvertautorise` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `conseiller`
--

CREATE TABLE IF NOT EXISTS `conseiller` (
  `id` int(11) NOT NULL,
  `nom` int(11) NOT NULL,
  `prenom` int(11) NOT NULL,
  `adresse` varchar(200) NOT NULL,
  `telephone` varchar(20) NOT NULL,
  `agence` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `agence` (`agence`),
  KEY `agence_2` (`agence`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE IF NOT EXISTS `message` (
  `id` int(11) NOT NULL,
  `idclient` int(11) NOT NULL,
  `idconseiller` int(11) NOT NULL,
  `contenu` varchar(900) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idclient` (`idclient`),
  KEY `idconseiller` (`idconseiller`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE IF NOT EXISTS `transactions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idcomptedebit` int(11) NOT NULL,
  `idcomptecredit` int(11) NOT NULL,
  `somme` int(11) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idcomptedebit` (`idcomptedebit`),
  KEY `idcomptecredit` (`idcomptecredit`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `fk_conseiller` FOREIGN KEY (`idconseiller`) REFERENCES `conseiller` (`id`);

--
-- Constraints for table `clientcompte`
--
ALTER TABLE `clientcompte`
  ADD CONSTRAINT `fk_compte` FOREIGN KEY (`idcompte`) REFERENCES `compte` (`id`),
  ADD CONSTRAINT `fk_client` FOREIGN KEY (`idclient`) REFERENCES `client` (`id`);

--
-- Constraints for table `conseiller`
--
ALTER TABLE `conseiller`
  ADD CONSTRAINT `fk_agenceconseiller` FOREIGN KEY (`agence`) REFERENCES `agence` (`id`);

--
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `fk_conseillermessage` FOREIGN KEY (`idconseiller`) REFERENCES `conseiller` (`id`),
  ADD CONSTRAINT `fk_clientmessage` FOREIGN KEY (`idclient`) REFERENCES `client` (`id`);

--
-- Constraints for table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `fk_comptecredit` FOREIGN KEY (`idcomptecredit`) REFERENCES `compte` (`id`),
  ADD CONSTRAINT `fk_comptedebit` FOREIGN KEY (`idcomptedebit`) REFERENCES `compte` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
