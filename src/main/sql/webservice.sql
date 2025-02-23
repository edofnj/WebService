-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Feb 23, 2025 alle 15:56
-- Versione del server: 10.4.32-MariaDB
-- Versione PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `webservice`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `ordini`
--

CREATE TABLE `ordini` (
  `id` int(11) NOT NULL,
  `utente_id` int(11) NOT NULL,
  `prodotto` varchar(255) NOT NULL,
  `quantita` int(11) NOT NULL,
  `prezzo` decimal(10,2) NOT NULL,
  `data_ordine` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `ordini`
--

INSERT INTO `ordini` (`id`, `utente_id`, `prodotto`, `quantita`, `prezzo`, `data_ordine`) VALUES
(1, 1, 'Laptop Dell XPS 15', 1, 1499.99, '2025-02-23 13:16:18'),
(2, 2, 'Mouse Logitech MX Master 3', 2, 89.99, '2025-02-23 13:16:18'),
(3, 3, 'Monitor LG UltraFine 4K', 1, 399.99, '2025-02-23 13:16:18'),
(4, 4, 'Tastiera meccanica Keychron K8', 1, 99.99, '2025-02-23 13:16:18'),
(5, 2, 'SSD Samsung 980 Pro 1TB', 1, 129.99, '2025-02-23 13:16:18'),
(6, 3, 'Stampante HP LaserJet Pro', 1, 199.99, '2025-02-23 13:16:18'),
(7, 1, 'Sedia ergonomica Secretlab Titan', 1, 429.99, '2025-02-23 13:16:18'),
(8, 4, 'Docking Station USB-C Anker', 1, 79.99, '2025-02-23 13:16:18');

-- --------------------------------------------------------

--
-- Struttura della tabella `utenti`
--

CREATE TABLE `utenti` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `data_registrazione` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `utenti`
--

INSERT INTO `utenti` (`id`, `nome`, `email`, `data_registrazione`) VALUES
(1, 'Edoardo Menegazzi', 'menegazzi.st.edoardo@maxplanck.edu.it', '2025-02-23 13:09:50'),
(2, 'Antonio Cotroneo', 'cotroneo.st.antonio@maxplanck.edu.it', '2025-02-23 13:11:30'),
(3, 'Alberto Zanatta', 'zanatta.st.alberto@maxplanck.edu.it', '2025-02-23 13:11:54'),
(4, 'Matteo De Luca', 'deluca.st.matteo@maxplanck.edu.it', '2025-02-23 13:13:08');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `ordini`
--
ALTER TABLE `ordini`
  ADD PRIMARY KEY (`id`),
  ADD KEY `utente_id` (`utente_id`);

--
-- Indici per le tabelle `utenti`
--
ALTER TABLE `utenti`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `ordini`
--
ALTER TABLE `ordini`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT per la tabella `utenti`
--
ALTER TABLE `utenti`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `ordini`
--
ALTER TABLE `ordini`
  ADD CONSTRAINT `ordini_ibfk_1` FOREIGN KEY (`utente_id`) REFERENCES `utenti` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
