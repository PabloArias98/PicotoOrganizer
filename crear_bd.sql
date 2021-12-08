-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-12-2021 a las 14:29:09
-- Versión del servidor: 10.4.21-MariaDB
-- Versión de PHP: 7.4.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `organizer`
--
DROP DATABASE IF EXISTS `organizer`;
CREATE DATABASE IF NOT EXISTS `organizer` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `organizer`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estados_civiles`
--

DROP TABLE IF EXISTS `estados_civiles`;
CREATE TABLE `estados_civiles` (
  `id` int(11) NOT NULL,
  `estado` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `estados_civiles`
--

INSERT INTO `estados_civiles` (`id`, `estado`) VALUES
(0, 'Viudo/a'),
(1, 'Casado/a'),
(2, 'Soltero/a'),
(3, 'Divorciado/a');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personas`
--

DROP TABLE IF EXISTS `personas`;
CREATE TABLE `personas` (
  `id` int(11) NOT NULL,
  `nombre` varchar(250) NOT NULL,
  `apellidos` varchar(250) NOT NULL,
  `dni` varchar(250) NOT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `fecha_fallecimiento` date DEFAULT NULL,
  `genero` varchar(30) NOT NULL,
  `email` varchar(250) NOT NULL,
  `telefono` int(11) NOT NULL,
  `direccion` varchar(500) NOT NULL,
  `poblacion` varchar(100) NOT NULL,
  `provincia` varchar(100) NOT NULL,
  `region` varchar(100) NOT NULL,
  `cod_postal` varchar(50) NOT NULL,
  `pais` varchar(100) NOT NULL,
  `estado_civil` int(11) NOT NULL,
  `estado_laboral` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `personas`
--

INSERT INTO `personas` (`id`, `nombre`, `apellidos`, `dni`, `fecha_nacimiento`, `fecha_fallecimiento`, `genero`, `email`, `telefono`, `direccion`, `poblacion`, `provincia`, `region`, `cod_postal`, `pais`, `estado_civil`, `estado_laboral`) VALUES
(31, 'Jorge', 'Pampillón Vazquez', '3423423E', '1965-10-10', '2010-10-10', 'Hombre', 'jorgito@hotmail.com', 52552325, 'Calle Xurxo Parderrubias', 'Salceda de Caselas', 'Pontevedra', 'Galicia', '34324', 'España', 1, 'Trabajando'),
(32, 'Maria', 'Martinez Martinez', '423423E', '2020-10-10', NULL, 'Mujer', 'maria@yahoo.es', 4234323, 'Calle Milagros', 'Carballiño, O', 'Orense', 'Galicia', '34234234', 'España', 2, 'Trabajando'),
(34, 'Juanjo', 'Juanjek24', '34234E', '2020-10-10', NULL, 'Hombre', 'juanjose@gmail.com', 234234, 'Calle Michigan', 'Detroit', 'N/D', 'Michigan', '23423', 'Estados Unidos', 2, 'Trabajando'),
(35, 'José Miguel', 'Arrutegui', '23423E', '1969-10-10', '2011-04-23', 'Hombre', 'josejose@yahoo.es', 2342334, 'Calle Calelel', 'Aantnt', 'Vizcaya', 'País Vasco', '34234', 'España', 2, 'En paro'),
(36, 'Hermisinda', 'Gomez', '23423E', '2020-10-10', NULL, 'Mujer', 'PAPAPAPA@YEWRYYR.COM', 2342345, 'Calle Verano', 'Ciujera', 'Albacete', 'Castilla-La Mancha', '3243', 'España', 2, 'En paro'),
(37, 'Motumbo', 'Don Pepe', '423234', '2020-10-10', NULL, 'Hombre', 'FSDFSDFSDF', 2342323, 'Camiño da Arrubial', 'Mos', 'Pontevedra', 'Galicia', '34234', 'España', 0, 'Jubilado/a'),
(38, 'erwerwe', 'werwerwe', '234234', '2020-10-10', NULL, 'Hombre', 'erwerwer', 2342345, 'fsdfsdf', 'rewrr', 'Badajoz', 'Extremadura', '23423', 'España', 1, 'Trabajando'),
(39, 'Marta', 'Dominguin', '32434', '2020-10-10', NULL, 'Mujer', 'erwer', 3234, 'ghgfh', 'Ciudad de Guatemala', 'N/D', 'Guatemala', '64545', 'Guatemala', 0, 'Trabajando'),
(40, 'José José', 'Rivera Dominguez', '423525234', '2020-11-20', NULL, 'Hombre', 'josejose@google.com', 342423234, 'Calle Las Americas', 'Durango', '', 'Durango', '2323', 'Mexico', 2, 'Jubilado/a'),
(41, 'María Dolores', 'Gonzalez Hermoso', '2342334F', '2020-10-23', NULL, 'Mujer', 'mariamaria@yahoo.es', 23423, 'Calle Papurri', 'Castrillo', 'N/D', 'Mexico', '34234', 'Mexico', 2, 'Jubilado/a'),
(42, 'Juan', 'Juanin Dominguin', '342343', '2020-10-10', NULL, 'Hombre', 'juanez@yahoo.es', 34324, 'CALLE', 'WERWE', 'Asturias, Principado de', 'Asturias, Principado de', '42342', 'España', 2, 'Jubilado/a'),
(43, 'Julian', 'Contreras', '4234', '1967-10-10', NULL, 'Hombre', 'julianjulian@yahoo.es', 3242342, 'Calle Esperanza', 'Chiclana', 'Cádiz', 'Andalucía', '42343', 'España', 1, 'Trabajando');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `estados_civiles`
--
ALTER TABLE `estados_civiles`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `personas`
--
ALTER TABLE `personas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `estado_civil` (`estado_civil`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `personas`
--
ALTER TABLE `personas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `personas`
--
ALTER TABLE `personas`
  ADD CONSTRAINT `personas_ibfk_1` FOREIGN KEY (`estado_civil`) REFERENCES `estados_civiles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
