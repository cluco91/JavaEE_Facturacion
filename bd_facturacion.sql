-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         5.6.25-log - MySQL Community Server (GPL)
-- SO del servidor:              Win64
-- HeidiSQL Versión:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Volcando estructura para tabla facturacion.cliente
CREATE TABLE IF NOT EXISTS `cliente` (
  `codCliente` int(11) NOT NULL AUTO_INCREMENT,
  `nombres` varchar(40) NOT NULL,
  `apellidos` varchar(40) NOT NULL,
  `direccion` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`codCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.
-- Volcando estructura para tabla facturacion.detallefactura
CREATE TABLE IF NOT EXISTS `detallefactura` (
  `codDetalle` int(11) NOT NULL AUTO_INCREMENT,
  `codFactura` int(11) NOT NULL,
  `codProducto` int(11) NOT NULL,
  `codBarra` varchar(10) NOT NULL,
  `nombreProducto` varchar(75) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precioVenta` decimal(10,2) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  PRIMARY KEY (`codDetalle`),
  KEY `codProducto` (`codProducto`),
  KEY `codFactura` (`codFactura`),
  CONSTRAINT `detallefactura_ibfk_1` FOREIGN KEY (`codProducto`) REFERENCES `producto` (`codProducto`),
  CONSTRAINT `detallefactura_ibfk_2` FOREIGN KEY (`codFactura`) REFERENCES `factura` (`codFactura`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.
-- Volcando estructura para tabla facturacion.factura
CREATE TABLE IF NOT EXISTS `factura` (
  `codFactura` int(11) NOT NULL AUTO_INCREMENT,
  `numeroFactura` varchar(10) NOT NULL,
  `codVendedor` int(11) NOT NULL,
  `codCliente` int(11) NOT NULL,
  `totalVenta` decimal(10,2) NOT NULL,
  `fechaRegistro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`codFactura`),
  KEY `codVendedor` (`codVendedor`),
  KEY `codCliente` (`codCliente`),
  CONSTRAINT `factura_ibfk_1` FOREIGN KEY (`codVendedor`) REFERENCES `vendedor` (`codVendedor`),
  CONSTRAINT `factura_ibfk_2` FOREIGN KEY (`codCliente`) REFERENCES `cliente` (`codCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.
-- Volcando estructura para tabla facturacion.producto
CREATE TABLE IF NOT EXISTS `producto` (
  `codProducto` int(11) NOT NULL AUTO_INCREMENT,
  `nombreProducto` varchar(75) NOT NULL,
  `precioVenta` decimal(10,2) NOT NULL,
  `stockMinimo` int(11) NOT NULL,
  `stockActual` int(50) NOT NULL,
  `codBarra` varchar(50) NOT NULL,
  PRIMARY KEY (`codProducto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.
-- Volcando estructura para tabla facturacion.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `codUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombreUsuario` varchar(50) NOT NULL,
  `password` varchar(35) NOT NULL,
  `codVendedor` int(11) NOT NULL,
  PRIMARY KEY (`codUsuario`),
  KEY `codVendedor` (`codVendedor`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`codVendedor`) REFERENCES `vendedor` (`codVendedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.
-- Volcando estructura para tabla facturacion.vendedor
CREATE TABLE IF NOT EXISTS `vendedor` (
  `codVendedor` int(11) NOT NULL AUTO_INCREMENT,
  `nombres` varchar(40) NOT NULL,
  `apellidos` varchar(40) NOT NULL,
  `cid` varchar(10) NOT NULL,
  `celular` varchar(8) NOT NULL,
  `direccion` varchar(150) NOT NULL,
  PRIMARY KEY (`codVendedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.
-- Volcando estructura para disparador facturacion.tgrActualizaStock
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tgrActualizaStock` BEFORE INSERT ON `detallefactura` FOR EACH ROW BEGIN
SET @stockAntiguo = (SELECT stockActual FROM producto WHERE codProducto = new.codProducto);
UPDATE producto
SET stockActual = @stockAntiguo-new.cantidad WHERE codProducto = new.codProducto;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
