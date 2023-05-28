-- --------------------------------------------------------
-- Host:                         195.235.211.197
-- Versión del servidor:         10.6.12-MariaDB-0ubuntu0.22.04.1 - Ubuntu 22.04
-- SO del servidor:              debian-linux-gnu
-- HeidiSQL Versión:             12.4.0.6659
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para priperfort
CREATE DATABASE IF NOT EXISTS `priperfort` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `priperfort`;

-- Volcando estructura para tabla priperfort.chat
CREATE TABLE IF NOT EXISTS `chat` (
  `id_chat` int(11) NOT NULL AUTO_INCREMENT,
  `id_emisor` varchar(255) DEFAULT NULL,
  `id_receptor` varchar(255) DEFAULT NULL,
  `cuerpo` varchar(255) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id_chat`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla priperfort.chat: ~4 rows (aproximadamente)
INSERT INTO `chat` (`id_chat`, `id_emisor`, `id_receptor`, `cuerpo`, `fecha`) VALUES
	(1, 'jiale@gmail.com', 'paula@gmail.com', 'hola!', '2023-05-27 19:41:44'),
	(2, 'paula@gmail.com', 'jiale@gmail.com', 'hola jiale', '2023-05-27 19:42:14'),
	(3, 'ivan@gmail.com', 'Mourataglou@entrenador.com', 'hola!!', '2023-05-27 19:43:43'),
	(4, 'Mourataglou@entrenador.com', 'ivan@gmail.com', 'hola ivan', '2023-05-27 19:44:18');

-- Volcando estructura para tabla priperfort.consulta
CREATE TABLE IF NOT EXISTS `consulta` (
  `id_consulta` int(11) NOT NULL AUTO_INCREMENT,
  `remitente` varchar(255) DEFAULT NULL,
  `destinatario` varchar(255) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `tipo` varchar(255) DEFAULT NULL,
  `cuerpo` varchar(255) DEFAULT NULL,
  `fecha_objetivo` date DEFAULT NULL,
  PRIMARY KEY (`id_consulta`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla priperfort.consulta: ~10 rows (aproximadamente)
INSERT INTO `consulta` (`id_consulta`, `remitente`, `destinatario`, `fecha`, `tipo`, `cuerpo`, `fecha_objetivo`) VALUES
	(1, 'paula@gmail.com', 'maurito@gmail.com', '2023-05-24 18:49:08', 'Pulsaciones', '180ppm', '2023-05-20'),
	(2, 'paula@gmail.com', 'maurito@gmail.com', '2023-05-24 18:49:19', 'Pulsaciones', '180ppm', '2023-05-20'),
	(3, 'paula@gmail.com', 'maurito@gmail.com', '2023-05-24 18:49:42', 'Pulsaciones', '180ppm', '2023-05-20'),
	(4, 'paula@gmail.com', 'maurito@gmail.com', '2023-05-24 18:50:51', 'Pulsaciones', '180ppm', '2023-05-20'),
	(5, 'paula@gmail.com', 'maurito@gmail.com', '2023-05-24 18:51:36', 'Pulsaciones', '180ppm', '2023-05-20'),
	(6, 'paula@gmail.com', 'maurito@gmail.com', '2023-05-24 18:51:54', 'Pulsaciones', '180ppm', '2023-05-20'),
	(7, 'paula@gmail.com', 'maurito@gmail.com', '2023-05-24 18:53:04', 'Pulsaciones', '180ppm', '2023-05-20'),
	(8, 'paula@gmail.com', 'maurito@gmail.com', '2023-05-24 18:53:17', 'Pulsaciones', '180ppm', '2023-05-20'),
	(9, 'jorge@gmail.com', 'ivan@gmail.com', '2023-05-24 18:57:43', 'Ritmo', 'Rapido', '2023-05-20'),
	(10, 'jorge@gmail.com', 'jiale@gmail.com', '2023-05-24 19:12:21', 'Ritmo', 'RapidinNomas', '2023-05-20');

-- Volcando estructura para tabla priperfort.EntrenadorDeportista
CREATE TABLE IF NOT EXISTS `EntrenadorDeportista` (
  `id_entrenador` varchar(255) DEFAULT NULL,
  `id_deportista` varchar(255) DEFAULT NULL,
  KEY `id_entrenador` (`id_entrenador`),
  KEY `id_deportista` (`id_deportista`),
  CONSTRAINT `EntrenadorDeportista_ibfk_1` FOREIGN KEY (`id_entrenador`) REFERENCES `usuarios` (`correo`),
  CONSTRAINT `EntrenadorDeportista_ibfk_2` FOREIGN KEY (`id_deportista`) REFERENCES `usuarios` (`correo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla priperfort.EntrenadorDeportista: ~10 rows (aproximadamente)
INSERT INTO `EntrenadorDeportista` (`id_entrenador`, `id_deportista`) VALUES
	('paula@gmail.com', 'jiale@gmail.com'),
	('Ancelotti@ceja.com', 'rafanadal@tenis.com'),
	('mourataglou@entrenador.com', 'paulabadosa@tenis.com'),
	('pep@guardiola.com', 'lionel@messi.com'),
	('pep@guardiola.com', 'norton@gmail.com'),
	('jorge@gmail.com', 'jaime@gmail.com'),
	('jorge@gmail.com', 'maurito@gmail.com'),
	('Mourataglou@entrenador.com', 'ivan@gmail.com'),
	('Ancelotti@ceja.com', 'cr7@cristiano.com'),
	('Ancelotti@ceja.com', 'ivan@gmail.com');

-- Volcando estructura para tabla priperfort.MedicoDeportista
CREATE TABLE IF NOT EXISTS `MedicoDeportista` (
  `id_medico` varchar(255) DEFAULT NULL,
  `id_deportista` varchar(255) DEFAULT NULL,
  KEY `id_medico` (`id_medico`),
  KEY `id_deportista` (`id_deportista`),
  CONSTRAINT `MedicoDeportista_ibfk_1` FOREIGN KEY (`id_medico`) REFERENCES `usuarios` (`correo`),
  CONSTRAINT `MedicoDeportista_ibfk_2` FOREIGN KEY (`id_deportista`) REFERENCES `usuarios` (`correo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla priperfort.MedicoDeportista: ~9 rows (aproximadamente)
INSERT INTO `MedicoDeportista` (`id_medico`, `id_deportista`) VALUES
	('cristian@gmail.com', 'jiale@gmail.com'),
	('Elia@medico.com', 'jaime@gmail.com'),
	('hmhospitales@medico.com', 'maurito@gmail.com'),
	('sanitas@medico.com', 'norton@gmail.com'),
	('pedrolara@gmail.com', 'ivan@gmail.com'),
	('Elia@medico.com', 'cr7@cristiano.com'),
	('sanitas@medico.com', 'rafanadal@tenis.com'),
	('sanitas@medico.com', 'paulabadosa@tenis.com'),
	('cristian@gmail.com', 'lionel@messi.com');

-- Volcando estructura para tabla priperfort.sensor
CREATE TABLE IF NOT EXISTS `sensor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sensor` varchar(50) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `valor` float DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `correo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `correo` (`correo`),
  CONSTRAINT `sensor_ibfk_1` FOREIGN KEY (`correo`) REFERENCES `usuarios` (`correo`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla priperfort.sensor: ~111 rows (aproximadamente)
INSERT INTO `sensor` (`id`, `sensor`, `tipo`, `valor`, `fecha`, `correo`) VALUES
	(1, 'DHT11', 'temperatura (C)', 24.5, '2023-05-26 21:43:47', 'rafanadal@tenis.com'),
	(2, 'DHT11', 'humedad (%)', 53, '2023-05-26 21:43:47', 'rafanadal@tenis.com'),
	(3, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 21:44:54', 'rafanadal@tenis.com'),
	(4, 'DHT11', 'humedad (%)', 53, '2023-05-26 21:44:55', 'rafanadal@tenis.com'),
	(5, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 21:47:16', 'rafanadal@tenis.com'),
	(6, 'DHT11', 'humedad (%)', 53, '2023-05-26 21:47:16', 'rafanadal@tenis.com'),
	(7, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 21:47:49', 'rafanadal@tenis.com'),
	(8, 'DHT11', 'humedad (%)', 53, '2023-05-26 21:47:49', 'rafanadal@tenis.com'),
	(9, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 21:48:56', 'rafanadal@tenis.com'),
	(10, 'DHT11', 'humedad (%)', 53, '2023-05-26 21:48:56', 'rafanadal@tenis.com'),
	(11, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 21:50:03', 'rafanadal@tenis.com'),
	(12, 'DHT11', 'humedad (%)', 53, '2023-05-26 21:50:03', 'rafanadal@tenis.com'),
	(13, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 21:51:09', 'rafanadal@tenis.com'),
	(14, 'DHT11', 'humedad (%)', 53, '2023-05-26 21:51:10', 'rafanadal@tenis.com'),
	(15, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 21:51:52', 'paulabadosa@tenis.com'),
	(16, 'DHT11', 'humedad (%)', 52, '2023-05-26 21:51:52', 'paulabadosa@tenis.com'),
	(17, 'DHT11', 'temperatura (C)', 24.9, '2023-05-26 21:52:58', 'paulabadosa@tenis.com'),
	(18, 'DHT11', 'humedad (%)', 52, '2023-05-26 21:52:59', 'paulabadosa@tenis.com'),
	(19, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 21:54:05', 'paulabadosa@tenis.com'),
	(20, 'DHT11', 'humedad (%)', 52, '2023-05-26 21:54:05', 'paulabadosa@tenis.com'),
	(21, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 21:54:39', 'maurito@gmail.com'),
	(22, 'DHT11', 'humedad (%)', 52, '2023-05-26 21:54:39', 'maurito@gmail.com'),
	(23, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 21:55:46', 'maurito@gmail.com'),
	(24, 'DHT11', 'humedad (%)', 52, '2023-05-26 21:55:46', 'maurito@gmail.com'),
	(25, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 21:56:53', 'maurito@gmail.com'),
	(26, 'DHT11', 'humedad (%)', 52, '2023-05-26 21:56:53', 'maurito@gmail.com'),
	(27, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 21:57:32', 'cr7@cristiano.com'),
	(28, 'DHT11', 'humedad (%)', 52, '2023-05-26 21:57:32', 'cr7@cristiano.com'),
	(29, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 21:58:38', 'cr7@cristiano.com'),
	(30, 'DHT11', 'humedad (%)', 52, '2023-05-26 21:58:39', 'cr7@cristiano.com'),
	(31, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 21:59:45', 'cr7@cristiano.com'),
	(32, 'DHT11', 'humedad (%)', 53, '2023-05-26 21:59:45', 'cr7@cristiano.com'),
	(33, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 22:00:38', 'norton@gmail.com'),
	(34, 'DHT11', 'humedad (%)', 53, '2023-05-26 22:00:38', 'norton@gmail.com'),
	(35, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 22:01:45', 'norton@gmail.com'),
	(36, 'DHT11', 'humedad (%)', 52, '2023-05-26 22:01:45', 'norton@gmail.com'),
	(37, 'DHT11', 'temperatura (C)', 24.7, '2023-05-26 22:02:51', 'norton@gmail.com'),
	(38, 'DHT11', 'humedad (%)', 52, '2023-05-26 22:02:52', 'norton@gmail.com'),
	(39, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 22:04:00', 'lionel@messi.com'),
	(40, 'DHT11', 'humedad (%)', 53, '2023-05-26 22:04:00', 'lionel@messi.com'),
	(41, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 22:05:07', 'lionel@messi.com'),
	(42, 'DHT11', 'humedad (%)', 53, '2023-05-26 22:05:07', 'lionel@messi.com'),
	(43, 'DHT11', 'temperatura (C)', 24.5, '2023-05-26 22:06:13', 'lionel@messi.com'),
	(44, 'DHT11', 'humedad (%)', 52, '2023-05-26 22:06:14', 'lionel@messi.com'),
	(45, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 22:07:30', 'jiale@gmail.com'),
	(46, 'DHT11', 'humedad (%)', 53, '2023-05-26 22:07:30', 'jiale@gmail.com'),
	(47, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 22:08:36', 'jiale@gmail.com'),
	(48, 'DHT11', 'humedad (%)', 53, '2023-05-26 22:08:37', 'jiale@gmail.com'),
	(49, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 22:09:43', 'jiale@gmail.com'),
	(50, 'DHT11', 'humedad (%)', 52, '2023-05-26 22:09:43', 'jiale@gmail.com'),
	(51, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 22:10:31', 'jaime@gmail.com'),
	(52, 'DHT11', 'humedad (%)', 53, '2023-05-26 22:10:31', 'jaime@gmail.com'),
	(53, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 22:11:37', 'jaime@gmail.com'),
	(54, 'DHT11', 'humedad (%)', 52, '2023-05-26 22:11:38', 'jaime@gmail.com'),
	(55, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 22:12:44', 'jaime@gmail.com'),
	(56, 'DHT11', 'humedad (%)', 52, '2023-05-26 22:12:45', 'jaime@gmail.com'),
	(57, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 22:13:26', 'ivan@gmail.com'),
	(58, 'DHT11', 'humedad (%)', 53, '2023-05-26 22:13:26', 'ivan@gmail.com'),
	(59, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 22:14:32', 'ivan@gmail.com'),
	(60, 'DHT11', 'humedad (%)', 53, '2023-05-26 22:14:33', 'ivan@gmail.com'),
	(61, 'DHT11', 'temperatura (C)', 24.8, '2023-05-26 22:15:39', 'ivan@gmail.com'),
	(62, 'DHT11', 'humedad (%)', 52, '2023-05-26 22:15:39', 'ivan@gmail.com'),
	(63, 'AD8232', 'Electro', 133, '2023-05-26 22:42:15', 'rafanadal@tenis.com'),
	(64, 'AD8232', 'Electro', 73, '2023-05-26 22:42:48', 'rafanadal@tenis.com'),
	(65, 'AD8232', 'Electro', 75, '2023-05-26 22:46:34', 'rafanadal@tenis.com'),
	(66, 'AD8232', 'Electro', 142, '2023-05-26 22:50:20', 'rafanadal@tenis.com'),
	(67, 'AD8232', 'Electro', 171, '2023-05-26 22:50:52', 'rafanadal@tenis.com'),
	(68, 'AD8232', 'Electro', 69, '2023-05-26 23:03:32', 'rafanadal@tenis.com'),
	(69, 'HC-SR04', 'Velocidad (km/h)', 0.57, '2023-05-26 23:08:11', 'rafanadal@tenis.com'),
	(70, 'HC-SR04', 'Velocidad (km/h)', 34.64, '2023-05-26 23:09:56', 'rafanadal@tenis.com'),
	(71, 'HC-SR04', 'Velocidad (km/h)', 3.3, '2023-05-26 23:10:17', 'rafanadal@tenis.com'),
	(72, 'HC-SR04', 'Velocidad (km/h)', 0.05, '2023-05-26 23:11:02', 'rafanadal@tenis.com'),
	(73, 'HC-SR04', 'Velocidad (km/h)', 3.04, '2023-05-26 23:11:12', 'rafanadal@tenis.com'),
	(74, 'HC-SR04', 'Velocidad (km/h)', 21.58, '2023-05-26 23:11:24', 'rafanadal@tenis.com'),
	(75, 'HC-SR04', 'Velocidad (km/h)', 9.72, '2023-05-26 23:11:33', 'rafanadal@tenis.com'),
	(76, 'HC-SR04', 'Velocidad (km/h)', 67.55, '2023-05-26 23:11:39', 'rafanadal@tenis.com'),
	(77, 'HC-SR04', 'Velocidad (km/h)', 5.14, '2023-05-26 23:11:45', 'rafanadal@tenis.com'),
	(78, 'HC-SR04', 'Velocidad (km/h)', 4.23, '2023-05-26 23:11:51', 'rafanadal@tenis.com'),
	(79, 'HC-SR04', 'Velocidad (km/h)', 1.67, '2023-05-26 23:11:59', 'rafanadal@tenis.com'),
	(80, 'HC-SR04', 'Velocidad (km/h)', 9.47, '2023-05-26 23:12:04', 'rafanadal@tenis.com'),
	(81, 'HC-SR04', 'Velocidad (km/h)', 0.48, '2023-05-26 23:12:18', 'rafanadal@tenis.com'),
	(82, 'HC-SR04', 'Velocidad (km/h)', 1.32, '2023-05-26 23:12:26', 'rafanadal@tenis.com'),
	(83, 'HC-SR04', 'Velocidad (km/h)', 2.19, '2023-05-26 23:12:32', 'rafanadal@tenis.com'),
	(84, 'HC-SR04', 'Velocidad (km/h)', 0.81, '2023-05-26 23:13:31', 'ivan@gmail.com'),
	(85, 'HC-SR04', 'Velocidad (km/h)', 1.94, '2023-05-26 23:13:36', 'ivan@gmail.com'),
	(86, 'HC-SR04', 'Velocidad (km/h)', 391.87, '2023-05-26 23:13:40', 'ivan@gmail.com'),
	(87, 'HC-SR04', 'Velocidad (km/h)', 404.19, '2023-05-26 23:13:45', 'ivan@gmail.com'),
	(88, 'HC-SR04', 'Velocidad (km/h)', 0.67, '2023-05-26 23:13:52', 'ivan@gmail.com'),
	(89, 'HC-SR04', 'Velocidad (km/h)', 25.69, '2023-05-26 23:13:56', 'ivan@gmail.com'),
	(90, 'HC-SR04', 'Velocidad (km/h)', 1.35, '2023-05-26 23:14:37', 'jiale@gmail.com'),
	(91, 'HC-SR04', 'Velocidad (km/h)', 1.4, '2023-05-26 23:14:41', 'jiale@gmail.com'),
	(92, 'HC-SR04', 'Velocidad (km/h)', 4.78, '2023-05-26 23:14:45', 'jiale@gmail.com'),
	(93, 'HC-SR04', 'Velocidad (km/h)', 2.75, '2023-05-26 23:14:50', 'jiale@gmail.com'),
	(94, 'HC-SR04', 'Velocidad (km/h)', 1.73, '2023-05-26 23:14:53', 'jiale@gmail.com'),
	(95, 'HC-SR04', 'Velocidad (km/h)', 1.17, '2023-05-26 23:14:58', 'jiale@gmail.com'),
	(96, 'HC-SR04', 'Velocidad (km/h)', 8.3, '2023-05-26 23:15:02', 'jiale@gmail.com'),
	(97, 'HC-SR04', 'Velocidad (km/h)', 0.82, '2023-05-26 23:15:08', 'jiale@gmail.com'),
	(98, 'HC-SR04', 'Velocidad (km/h)', 11.01, '2023-05-26 23:15:14', 'jiale@gmail.com'),
	(99, 'HC-SR04', 'Velocidad (km/h)', 14.19, '2023-05-26 23:15:19', 'jiale@gmail.com'),
	(100, 'HC-SR04', 'Velocidad (km/h)', 5.57, '2023-05-26 23:15:58', 'norton@gmail.com'),
	(101, 'HC-SR04', 'Velocidad (km/h)', 9.73, '2023-05-26 23:16:02', 'norton@gmail.com'),
	(102, 'HC-SR04', 'Velocidad (km/h)', 13.66, '2023-05-26 23:16:05', 'norton@gmail.com'),
	(103, 'HC-SR04', 'Velocidad (km/h)', 6.67, '2023-05-26 23:16:09', 'norton@gmail.com'),
	(104, 'HC-SR04', 'Velocidad (km/h)', 5.57, '2023-05-26 23:16:13', 'norton@gmail.com'),
	(105, 'HC-SR04', 'Velocidad (km/h)', 2.05, '2023-05-26 23:16:18', 'norton@gmail.com'),
	(106, 'HC-SR04', 'Velocidad (km/h)', 5.21, '2023-05-26 23:16:22', 'norton@gmail.com'),
	(107, 'HC-SR04', 'Velocidad (km/h)', 12, '2023-05-26 23:17:53', 'jaime@gmail.com'),
	(108, 'HC-SR04', 'Velocidad (km/h)', 6.35, '2023-05-26 23:17:56', 'jaime@gmail.com'),
	(109, 'HC-SR04', 'Velocidad (km/h)', 2.73, '2023-05-26 23:18:02', 'jaime@gmail.com'),
	(110, 'HC-SR04', 'Velocidad (km/h)', 8.51, '2023-05-26 23:18:20', 'jaime@gmail.com'),
	(111, 'HC-SR04', 'Velocidad (km/h)', 385.16, '2023-05-26 23:18:24', 'jaime@gmail.com');

-- Volcando estructura para tabla priperfort.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `correo` varchar(255) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `apellidos` varchar(255) DEFAULT NULL,
  `pass` varchar(255) DEFAULT NULL,
  `roll` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`correo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla priperfort.usuarios: ~22 rows (aproximadamente)
INSERT INTO `usuarios` (`correo`, `nombre`, `apellidos`, `pass`, `roll`) VALUES
	('Ancelotti@ceja.com', 'Carlo', 'Ancelotti', '12345678', 'Entrenador'),
	('cr7@cristiano.com', 'Cristiano', 'Ronaldo dos Santos Aveiro', '12345678', 'Deportista'),
	('cristian@gmail.com', 'Cristian', 'Chucuchunay', '12345678', 'Medico'),
	('Elia@medico.com', 'Elia Maria', 'Perez', '12345678', 'Medico'),
	('hmhospitales@medico.com', 'HM', 'Hospitales', '12345678', 'Medico'),
	('ivan@gmail.com', 'Ivan', 'Moriones', '12345678', 'Deportista'),
	('jaime@gmail.com', 'Jaime', 'Garcia', '12345678', 'Deportista'),
	('jiale@gmail.com', 'Jiale', 'Ji Chen', '12345678', 'Deportista'),
	('jorge@gmail.com', 'Jorge', 'Garcia', '12345678', 'Entrenador'),
	('lionel@messi.com', 'Lionel', 'Andres Messi', '12345678', 'Deportista'),
	('maurito@gmail.com', 'Mauro', 'Severino', '12345678', 'Deportista'),
	('Mourataglou@entrenador.com', 'Mourataglou', 'Academy', '12345678', 'Entrenador'),
	('norton@gmail.com', 'Alberto', 'Garcia', '12345678', 'Deportista'),
	('paula@gmail.com', 'Paula', 'Egido', '12345678', 'Entrenador'),
	('paulabadosa@tenis.com', 'Paula', 'Badosa', '12345678', 'Deportista'),
	('pedrolara@gmail.com', 'Pedro', 'Lara', '12345678', 'Medico'),
	('pep@guardiola.com', 'Pep', 'Guardiola', '12345678', 'Entrenador'),
	('rafanadal@tenis.com', 'Rafael', 'Nadal', '12345678', 'Deportista'),
	('sanitas@medico.com', 'Sanitas', 'Privado', '12345678', 'Medico'),
	('usuariosinentrenador1@gmail.com', 'user1', 'user1', '12345678', 'Deportista'),
	('usuariosinentrenador2@gmail.com', 'user2', 'user2', '12345678', 'Deportista'),
	('usuariosinentrenador3@gmail.com', 'user2', 'user3', '12345678', 'Deportista');

-- Volcando estructura para tabla priperfort.UsuariosConsultas
CREATE TABLE IF NOT EXISTS `UsuariosConsultas` (
  `id_usuario1` varchar(255) NOT NULL,
  `id_usuario2` varchar(255) NOT NULL,
  `id_consulta` int(11) NOT NULL,
  KEY `id_consulta` (`id_consulta`),
  CONSTRAINT `UsuariosConsultas_ibfk_1` FOREIGN KEY (`id_consulta`) REFERENCES `consulta` (`id_consulta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla priperfort.UsuariosConsultas: ~3 rows (aproximadamente)
INSERT INTO `UsuariosConsultas` (`id_usuario1`, `id_usuario2`, `id_consulta`) VALUES
	('paula@gmail.com', 'maurito@gmail.com', 8),
	('jorge@gmail.com', 'ivan@gmail.com', 9),
	('jorge@gmail.com', 'jiale@gmail.com', 10);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
